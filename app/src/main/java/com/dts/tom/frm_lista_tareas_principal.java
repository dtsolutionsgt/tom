package com.dts.tom;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_enc;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_encList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_encList;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHHList;
import com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking;
import com.dts.tom.Transacciones.Recepcion.frm_detalle_ingresos;
import com.dts.tom.Transacciones.Verificacion.frm_detalle_tareas_verificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_lista_tareas_principal extends PBase {

    private EditText txtTarea;
    private TextView lblTitulo;
    private Button lblRegs,btnNueva;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeTareasIngresoHHList pListBeTareasIngresoHH = new clsBeTareasIngresoHHList();
    private clsBeTrans_picking_encList pListBeTareasPickingHH = new clsBeTrans_picking_encList();
    private clsBeTrans_pe_encList pListBeTransPeEnc = new clsBeTrans_pe_encList();

    private ArrayList<clsBeTareasIngresoHH> BeListTareas = new ArrayList<clsBeTareasIngresoHH>();
    private ArrayList<clsBeTrans_picking_enc> BeListTareasPicking = new ArrayList<clsBeTrans_picking_enc>();
    private ArrayList<clsBeTrans_pe_enc> BeListTareasPedido = new ArrayList<clsBeTrans_pe_enc>();

    private list_adapter_tareashh adapter;
    private list_adapt_tareashh_picking adapterPicking;
    private list_adapt_tareas_verificacion adapterVerificacion;
    private ListView listView;
    private clsBeTareasIngresoHH selitem;
    private clsBeTrans_picking_enc selitempicking;
    private clsBeTrans_pe_enc selitempe;
    private ProgressBar pbar;
    private ObjectAnimator anim;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_tareas_principal);

        super.InitBase();

        lblTitulo = (TextView) findViewById(R.id.lblTituloForma);
        txtTarea = (EditText) findViewById(R.id.editText8);
        listView = (ListView) findViewById(R.id.listTareas);
        lblRegs = (Button) findViewById(R.id.btnRegsList);
        btnNueva = (Button)findViewById(R.id.btnNuevaTarea);
        pbar = (ProgressBar) findViewById(R.id.pgrtareas);

        anim = ObjectAnimator.ofInt(pbar, "progress", 0, 100);

        ProgressDialog("Cargando forma");

        ws = new WebServiceHandler(frm_lista_tareas_principal.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandlers();

        Load();

    }

    private void Load(){

        try{

            if (gl.tipoTarea==1){

                if (gl.tipoIngreso.equals("HCOC00")){
                    gl.TipoOpcion =1;
                    //Llama al método del WS Get_All_Recepciones_For_HH_By_IdBodega
                    execws(1);
                }else if (gl.tipoIngreso.equals("HSOC00")){
                    gl.TipoOpcion =2;
                    //Llama al método del WS Get_All_Rec_Ciegas_For_HH_By_IdBodega
                    execws(2);
                }

            }else if(gl.tipoTarea==5){
                //Llama al método del WS Get_All_Picking_For_HH_By_IdBodega_And_IdOperadorBodega
                execws(3);
            }else if(gl.tipoTarea==6){
                //Llama al método del WS Get_All_Pedidos_A_Verificar_By_IdBodega
                execws(4);
            }

            if (gl.tipoTarea==0){
                progress.cancel();
                doExit();
            }

        }catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_All_Recepciones_For_HH_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_All_Rec_Ciegas_For_HH_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_All_Picking_For_HH_By_IdBodega_And_IdOperadorBodega", "pIdBodega",gl.IdBodega,"pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                    case 4:
                        callMethod("Get_All_Pedidos_A_Verificar_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                }

                anim.cancel();

            } catch (Exception e) {
                anim.cancel();
                error=e.getMessage();errorflag =true;msgbox(error);
            }
        }
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processListRecep();break;
                case 2:
                    processListRecepCiega();break;
                case 3:
                    processListTareasPicking();
                    break;
                case 4:
                    processListTareasVerificacion();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void processListRecep(){

        try {

            progress.setMessage("Obteniendo lista de tareas");

            pListBeTareasIngresoHH=xobj.getresult(clsBeTareasIngresoHHList.class,"Get_All_Recepciones_For_HH_By_IdBodega");

            listItems();

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processListRecepCiega(){

        try {

            pListBeTareasIngresoHH=xobj.getresult(clsBeTareasIngresoHHList.class,"Get_All_Rec_Ciegas_For_HH_By_IdBodega");

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processListTareasPicking(){

        try{

            pListBeTareasPickingHH=xobj.getresult(clsBeTrans_picking_encList.class,"Get_All_Picking_For_HH_By_IdBodega_And_IdOperadorBodega");

            Llena_Tareas_Picking();

        }catch (Exception e){
            mu.msgbox("processListTareasPicking:"+e.getMessage());
        }

    }

    private void processListTareasVerificacion(){

        try{

            pListBeTransPeEnc=xobj.getresult(clsBeTrans_pe_encList.class,"Get_All_Pedidos_A_Verificar_By_IdBodega");

            Llena_Tareas_Verificacion();

        }catch (Exception e){
            mu.msgbox("processListTareasPicking:"+e.getMessage());
            progress.cancel();
        }

    }

    private void Llena_Tareas_Picking(){
        clsBeTrans_picking_enc vItem;
        BeListTareasPicking.clear();
        int count;

        try{

            progress.setMessage("Listando tareas");

            if (pListBeTareasPickingHH!=null){
                if (pListBeTareasPickingHH.items!=null){

                    vItem = new clsBeTrans_picking_enc();

                    BeListTareasPicking.add(vItem);

                    for (clsBeTrans_picking_enc BePicking:pListBeTareasPickingHH.items ){

                        vItem = new clsBeTrans_picking_enc();

                        vItem.IdPickingEnc=BePicking.IdPickingEnc;
                        vItem.NombreBodega=BePicking.NombreBodega;
                        vItem.NombrePropietarioPicking=BePicking.NombrePropietarioPicking;
                        vItem.NombreUbicacionPicking=BePicking.NombreUbicacionPicking;
                        vItem.Estado=BePicking.Estado;
                        vItem.Detalle_operador=BePicking.Detalle_operador;
                        vItem.Fecha_picking=du.convierteFechaMostar(BePicking.Fecha_picking);
                        vItem.Hora_ini=du.convierteHoraMostar(BePicking.Hora_ini);
                        vItem.Hora_fin=du.convierteHoraMostar(BePicking.Hora_fin);

                        BeListTareasPicking.add(vItem);

                    }
                    count = BeListTareasPicking.size()-1;
                    lblRegs.setText("Regs: "+ count);
                }
            }

            Collections.sort(BeListTareasPicking,new OrdenarItemsPicking());

            adapterPicking=new list_adapt_tareashh_picking(this,BeListTareasPicking);
            listView.setAdapter(adapterPicking);

            progress.cancel();

        }catch (Exception e){
            mu.msgbox("Llena_Tareas_Picking");
        }

    }

    private void Llena_Tareas_Verificacion(){
        clsBeTrans_pe_enc vItem;
        BeListTareasPedido.clear();
        int count;

        try{

            progress.setMessage("Listando tareas");
            progress.show();

            if (pListBeTransPeEnc!=null){
                if (pListBeTransPeEnc.items!=null){

                    for (clsBeTrans_pe_enc BePedEnc:pListBeTransPeEnc.items ){

                        vItem = new clsBeTrans_pe_enc();

                        vItem.IdPedidoEnc = BePedEnc.IdPedidoEnc;
                        vItem.Referencia = BePedEnc.Referencia;
                        vItem.IdMuelle = BePedEnc.IdMuelle;
                        vItem.IdCliente = BePedEnc.getIdCliente();
                        vItem.Cliente.Nombre_comercial = BePedEnc.Cliente.Nombre_comercial;
                        vItem.Estado = BePedEnc.Estado;
                        vItem.IdPickingEnc = BePedEnc.IdPickingEnc;;
                        vItem.Hora_ini = du.convierteHoraMostar(BePedEnc.Hora_ini);
                        vItem.Hora_fin = du.convierteHoraMostar(BePedEnc.Hora_fin);

                        BeListTareasPedido.add(vItem);

                    }
                    count = BeListTareasPedido.size();
                    lblRegs.setText("Regs: "+ count);
                }
            }

            Collections.sort(BeListTareasPedido,new OrdenarItemsPedido());

            adapterVerificacion=new list_adapt_tareas_verificacion(this,BeListTareasPedido);
            listView.setAdapter(adapterVerificacion);

            progress.cancel();

        }catch (Exception e){
            mu.msgbox("Llena_Tareas_Verificacion " + e.getMessage());
            progress.cancel();
        }

    }

    private void listItems(){
        clsBeTareasIngresoHH vItem;
        BeListTareas.clear();
        int count;

        try{

            progress.setMessage("Listando tareas");

            if (pListBeTareasIngresoHH!=null){
                if (pListBeTareasIngresoHH.items!=null){

                    vItem = new clsBeTareasIngresoHH();

                    BeListTareas.add(vItem);

                    for (int i = pListBeTareasIngresoHH.items.size()-1; i>=0; i--) {

                        progress.setMessage("Listando tarea #: "+pListBeTareasIngresoHH.items.get(i).IdRecepcionEnc);

                        vItem = new clsBeTareasIngresoHH();

                        vItem.IdOrderCompraEnc=pListBeTareasIngresoHH.items.get(i).IdOrderCompraEnc;
                        vItem.IdRecepcionEnc=pListBeTareasIngresoHH.items.get(i).IdRecepcionEnc;
                        vItem.NoReferenciaOC=pListBeTareasIngresoHH.items.get(i).NoReferenciaOC;
                        vItem.NoDocumentoOc=pListBeTareasIngresoHH.items.get(i).NoDocumentoOc;
                        vItem.NombreProveedor=pListBeTareasIngresoHH.items.get(i).NombreProveedor;
                        vItem.NombreTipoIngresoOC=pListBeTareasIngresoHH.items.get(i).NombreTipoIngresoOC;
                        vItem.NombreTipoRecepcion=pListBeTareasIngresoHH.items.get(i).NombreTipoRecepcion;

                        BeListTareas.add(vItem);

                    }
                    count = BeListTareas.size()-1;
                    lblRegs.setText("Regs: "+ count);

                }else{
                    btnNueva.setVisibility(View.VISIBLE);
                }
            }else{
                btnNueva.setVisibility(View.VISIBLE);
            }

            Collections.sort(BeListTareas,new OrdenarItems());

            adapter=new list_adapter_tareashh(this,BeListTareas);
            listView.setAdapter(adapter);

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox(e.getMessage());
        }
    }

    public class OrdenarItems implements Comparator<clsBeTareasIngresoHH> {

        public int compare(clsBeTareasIngresoHH left,clsBeTareasIngresoHH rigth){
            return left.IdOrderCompraEnc-rigth.IdOrderCompraEnc;
            //return left.Nombre.compareTo(rigth.Nombre);
        }

    }

    public class OrdenarItemsPicking implements Comparator<clsBeTrans_picking_enc> {

        public int compare(clsBeTrans_picking_enc left,clsBeTrans_picking_enc rigth){
            return left.IdPickingEnc-rigth.IdPickingEnc;
            //return left.Nombre.compareTo(rigth.Nombre);
        }

    }

    public class OrdenarItemsPedido implements Comparator<clsBeTrans_pe_enc> {

        public int compare(clsBeTrans_pe_enc left,clsBeTrans_pe_enc rigth){
            return rigth.IdPedidoEnc-left.IdPickingEnc;
        }

    }

    private void procesar_registro(){

        try{

            gl.gIdRecepcionEnc=0;

            switch (gl.tipoTarea) {

                case 1:
                    browse=1;
                    gl.gIdRecepcionEnc = selid;
                    startActivity(new Intent(this, frm_detalle_ingresos.class));
                break;

                case 5:
                    browse=5;
                    gl.gIdPickingEnc = selid;
                    startActivity(new Intent(this, frm_detalle_tareas_picking.class));
                    break;

                case 6:
                    browse=6;
                    gl.pIdPedidoEnc = selid;
                    startActivity(new Intent(this, frm_detalle_tareas_verificacion.class));
                    break;
            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
        }

    }

    public void BotonNueva(View view){

        try{

            gl.gIdRecepcionEnc=0;
            gl.tipoIngreso = "HSOC00";

            switch (gl.tipoTarea) {

                case 1:
                    browse=1;
                    gl.gIdRecepcionEnc = selid;
                    startActivity(new Intent(this, frm_detalle_ingresos.class));
                    break;
            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
        }
    }

    private void GetFila(){

        try{

            int vIdTarea=0;

            if (!txtTarea.getText().toString().isEmpty()){

                selid = Integer.parseInt(txtTarea.getText().toString());

                if (gl.tipoTarea==1){

                    vIdTarea = stream(pListBeTareasIngresoHH.items).where(c->c.IdRecepcionEnc == selid).select(c->c.IdRecepcionEnc).first();

                    if (vIdTarea>0){
                        procesar_registro();
                    }else{
                        mu.msgbox("No existe la tarea "+selid);
                    }

                }else if(gl.tipoTarea==5){

                    vIdTarea = stream(pListBeTareasPickingHH.items).where(c->c.IdPickingEnc == selid).select(c->c.IdPickingEnc).first();

                    if (vIdTarea>0){
                        procesar_registro();
                    }else{
                        mu.msgbox("No existe la tarea "+selid);
                    }

                }else if(gl.tipoTarea==6){

                    vIdTarea = stream(pListBeTransPeEnc.items).where(c->c.IdPedidoEnc == selid).select(c->c.IdPedidoEnc).first();

                    if (vIdTarea>0){
                        procesar_registro();
                    }else{
                        mu.msgbox("No existe la tarea "+selid);
                    }

                }

            }

        }catch (Exception e){
         mu.msgbox("GetFila:"+e.getMessage());
        }
    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void setHandlers() {

        try {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        if (gl.tipoTarea==1){

                            Object lvObj = listView.getItemAtPosition(position);
                            clsBeTareasIngresoHH sitem = (clsBeTareasIngresoHH) lvObj;
                            selitem = sitem;

                            selid = sitem.IdRecepcionEnc;
                            selidx = position;
                            adapter.setSelectedIndex(position);

                            procesar_registro();

                        }else if(gl.tipoTarea==5){

                            Object lvObj = listView.getItemAtPosition(position);
                            clsBeTrans_picking_enc sitem = (clsBeTrans_picking_enc) lvObj;
                            selitempicking = sitem;

                            selid = sitem.IdPickingEnc;
                            selidx = position;
                            adapterPicking.setSelectedIndex(position);

                            procesar_registro();

                        }else if(gl.tipoTarea==6){

                            Object lvObj = listView.getItemAtPosition(position);
                            clsBeTrans_pe_enc sitem = (clsBeTrans_pe_enc) lvObj;
                            selitempe = sitem;

                            selid = sitem.IdPedidoEnc;
                            selidx = position;
                            adapterVerificacion.setSelectedIndex(position);

                            procesar_registro();

                        }

                    }

                }

            });

            txtTarea.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        GetFila();
                    }

                    return false;
                }
            });

            }catch (Exception e){

            }

    }

    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                Load();
            }

            if (browse==5){
                browse=0;
                Load();
            }


        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    public void SalirTareas(View view){
        msgAskExit("Está seguro de salir");
    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    doExit();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void doExit(){
        try{

            pListBeTareasIngresoHH = new clsBeTareasIngresoHHList();
            BeListTareas = new ArrayList<clsBeTareasIngresoHH>();

            gl.tipoTarea=0;
            super.finish();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    public void onBackPressed() {
        try{
            //msgAskExit("Salir sin guardar datos");
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}
