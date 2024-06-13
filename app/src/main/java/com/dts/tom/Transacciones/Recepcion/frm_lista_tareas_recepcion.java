package com.dts.tom.Transacciones.Recepcion;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_enc;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_encList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_encList;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHHList;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.ladapt.Verificacion.list_adapt_tareas_verificacion;
import com.dts.ladapt.list_adapt_tareashh_picking;
import com.dts.ladapt.list_adapter_tareashh;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking;
import com.dts.tom.Transacciones.Verificacion.frm_detalle_tareas_verificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.zbra.androidlinq.Linq.stream;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapPrimitive;

public class frm_lista_tareas_recepcion extends PBase {

    private EditText txtTarea;
    private TextView lblTitulo, lblBodega, lblOperador;
    private Button lblRegs,btnNueva;
    private LinearLayout hdVerificacion, hdRecepcion, hdPicking;
    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeTareasIngresoHHList pListBeTareasIngresoHH = new clsBeTareasIngresoHHList();
    private clsBeTrans_picking_encList pListBeTareasPickingHH = new clsBeTrans_picking_encList();
    private clsBeTrans_pe_encList pListBeTransPeEnc = new clsBeTrans_pe_encList();

    private ArrayList<clsBeTareasIngresoHH> BeListTareas = new ArrayList<clsBeTareasIngresoHH>();
    private final ArrayList<clsBeTrans_picking_enc> BeListTareasPicking = new ArrayList<clsBeTrans_picking_enc>();
    private final ArrayList<clsBeTrans_pe_enc> BeListTareasPedido = new ArrayList<clsBeTrans_pe_enc>();

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
    private int IdOrdenCompra = 0, vIdTarea=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_tareas_recepcion);

        super.InitBase();

        lblTitulo = findViewById(R.id.lblTituloForma);
        lblBodega = findViewById(R.id.lblBodega);
        lblOperador = findViewById(R.id.lblOperador);
        txtTarea = findViewById(R.id.editText8);
        listView = findViewById(R.id.listTareas);
        lblRegs = findViewById(R.id.btnRegsList);
        btnNueva = findViewById(R.id.btnNuevaTarea);
        pbar = findViewById(R.id.pgrtareas);
        hdVerificacion = findViewById(R.id.hdVerificacion);
        hdRecepcion = findViewById(R.id.hdRecepcion);
        hdPicking = findViewById(R.id.hdPicking);

        anim = ObjectAnimator.ofInt(pbar, "progress", 0, 100);

        ProgressDialog("Cargando forma");
        lblBodega.setText("Bodega: "+ gl.IdBodega + " - "+gl.gNomBodega);
        lblOperador.setText("Operador: "+gl.OperadorBodega.IdOperadorBodega+" - "+ gl.OperadorBodega.Nombre_Completo);

        ws = new WebServiceHandler(frm_lista_tareas_recepcion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandlers();

        gl.gVerifCascade =false;

        Load();

    }

    private void Load(){

        try{

            progress.setMessage("Cargando tareas...");
            progress.show();

            if (gl.tipoTarea==1){
                hdRecepcion.setVisibility(View.VISIBLE);
                lblTitulo.setText("Tareas de Recepción");
                if (gl.tipoIngreso.equals("HCOC00")){
                    gl.TipoOpcion =1;
                    //Llama al método del WS Get_All_Recepciones_For_HH_By_IdBodega_By_Operador
                    execws(1);
                }else if (gl.tipoIngreso.equals("HSOC00")){
                    gl.TipoOpcion =2;
                    //Llama al método del WS Get_All_Rec_Ciegas_For_HH_By_IdBodega
                    execws(2);
                }

            }else if(gl.tipoTarea==5){
                hdPicking.setVisibility(View.VISIBLE);
                lblTitulo.setText("Tareas de Picking");
                //Llama al método del WS Get_All_Picking_For_HH_By_IdBodega_And_IdOperadorBodega
                execws(3);
            }else if(gl.tipoTarea==6){
                hdVerificacion.setVisibility(View.VISIBLE);
                lblTitulo.setText("Tareas de Verificación");
                //Llama al método del WS Get_All_Pedidos_A_Verificar_By_IdBodega
                execws(4);
            }

            if (gl.tipoTarea==0){
                progress.cancel();
                doExit();
            }

        } catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void setHandlers() {

        try {

            listView.setOnItemClickListener((parent, view, position, id) -> {

                selid = 0;

                if (gl.tipoTarea==1){

                    //if (position > 0) {
                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTareasIngresoHH sitem = (clsBeTareasIngresoHH) lvObj;
                    selitem = sitem;

                    selid = sitem.IdRecepcionEnc;
                    selidx = position;
                    adapter.setSelectedIndex(position);

                    procesar_registro();
                    //}

                }else if(gl.tipoTarea==5){

                    //if (position > 0){
                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_picking_enc sitem = (clsBeTrans_picking_enc) lvObj;
                    selitempicking = sitem;

                    selid = sitem.IdPickingEnc;
                    gl.gReferencia = selitempicking.Referencia;

                    selidx = position;
                    adapterPicking.setSelectedIndex(position);

                    procesar_registro();
                   // }

                } else if(gl.tipoTarea==6){

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_pe_enc sitem = (clsBeTrans_pe_enc) lvObj;
                    selitempe = sitem;

                    selid = sitem.IdPedidoEnc;
                    selidx = position;
                    adapterVerificacion.setSelectedIndex(position);

                    procesar_registro();

                }
            });

            txtTarea.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (!txtTarea.getText().toString().isEmpty()) {
                            if (gl.tipoTarea == 1) {
                                if (gl.Interface_SAP){
                                    execws(8);
                                }else{
                                    execws(6);
                                }
                            } else {
                                GetFila();
                            }
                        }
                    }

                    return false;
                }
            });

            lblRegs.setOnClickListener(v -> Load());

        }catch (Exception e){

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
                        callMethod("Get_All_Recepciones_For_HH_By_IdBodega_By_Operador",
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega",gl.OperadorBodega.getIdOperadorBodega());
                        break;
                    case 2:
                        callMethod("Get_All_Rec_Ciegas_For_HH_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_All_Picking_For_HH_By_IdBodega_And_IdOperadorBodega", "pIdBodega",gl.IdBodega,"pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                    case 4:
                        int vIdOperadorBodega = 0;

                        if (gl.operador_picking_realiza_verificacion){
                            vIdOperadorBodega=gl.OperadorBodega.IdOperadorBodega;
                        }
                        callMethod("Get_All_Pedidos_A_Verificar_By_IdBodega",
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega", vIdOperadorBodega);
                        break;
                    case 5:
                        callMethod("Get_Pedido_A_Verificar_By_LP","pLP",gl.gLP);
                        break;
                    case 6:
                        callMethod("Get_IdOrdenCompraEnc_By_Licencia","pLicenciaIngreso",txtTarea.getText().toString());
                        break;
                    case 7:
                        callMethod("GetSingleRec","pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 8:
                        callMethod("Get_ListOrdenCompraEnc_By_Codigo_Producto",
                                         "pCodigo",txtTarea.getText().toString().replace("$",""),
                                               "pIdOperadorBodega",gl.OperadorBodega.getIdOperadorBodega());
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
                    processListTareasVerificacion();break;
                case 5:
                    recibeLP();break;
                case 6:
                    processIdOrdenCompra();
                    break;
                case 7:
                    processIdRecepcion();
                    break;
                case 8:
                    processListaOrdenCompra();
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

            pListBeTareasIngresoHH=xobj.getresult(clsBeTareasIngresoHHList.class,"Get_All_Recepciones_For_HH_By_IdBodega_By_Operador");

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
        try {

            pListBeTransPeEnc = new clsBeTrans_pe_encList();

            pListBeTransPeEnc=xobj.getresult(clsBeTrans_pe_encList.class,"Get_All_Pedidos_A_Verificar_By_IdBodega");
            Llena_Tareas_Verificacion();
        } catch (Exception e){
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

                    //BeListTareasPicking.add(vItem);

                    for (clsBeTrans_picking_enc BePicking:pListBeTareasPickingHH.items ){

                        vItem = new clsBeTrans_picking_enc();

                        vItem.IdPickingEnc=BePicking.IdPickingEnc;
                        vItem.NombreBodega=BePicking.NombreBodega;
                        vItem.NombrePropietarioPicking=BePicking.NombrePropietarioPicking;
                        vItem.NombreUbicacionPicking=BePicking.NombreUbicacionPicking;
                        vItem.Estado=BePicking.Estado;
                        vItem.Detalle_operador=BePicking.Detalle_operador;
                        vItem.Fecha_picking=du.convierteFechaMostrar(BePicking.Fecha_picking);
                        vItem.Hora_ini=du.convierteHoraMostar(BePicking.Hora_ini);
                        vItem.Hora_fin=du.convierteHoraMostar(BePicking.Hora_fin);
                        vItem.Referencia = BePicking.Referencia;
                        vItem.IdPrioridadPicking = BePicking.IdPrioridadPicking;

                        BeListTareasPicking.add(vItem);

                    }

                    count = BeListTareasPicking.size();
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
                        vItem.Fecha_Pedido = du.convierteFechaMostrarDiagonal(BePedEnc.Fecha_Pedido);
                        vItem.Referencia = BePedEnc.Referencia;
                        vItem.IdMuelle = BePedEnc.IdMuelle;
                        vItem.NombreRutaDespacho = BePedEnc.NombreRutaDespacho;
                        vItem.Observacion = BePedEnc.Observacion;
                        vItem.Requiere_Tarimas = BePedEnc.Requiere_Tarimas;
                        vItem.IdCliente = BePedEnc.getIdCliente();
                        vItem.Cliente.Nombre_comercial = BePedEnc.Cliente.Codigo+" - "+BePedEnc.Cliente.Nombre_comercial;
                        vItem.Estado = BePedEnc.Estado;
                        vItem.IdPickingEnc = BePedEnc.IdPickingEnc;
                        vItem.Hora_ini = du.convierteHoraMostar(BePedEnc.Hora_ini);
                        vItem.Hora_fin = du.convierteHoraMostar(BePedEnc.Hora_fin);
                        vItem.RoadDirEntrega = BePedEnc.RoadDirEntrega;
                        vItem.IdPickingEnc = BePedEnc.IdPickingEnc;

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
        listView.setAdapter(null);
        int count;

        try{

            progress.setMessage("Listando tareas");

            if (pListBeTareasIngresoHH!=null){

                if (pListBeTareasIngresoHH.items!=null){

                    vItem = new clsBeTareasIngresoHH();

                    //BeListTareas.add(vItem);

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
                        vItem.NombrePropietario = pListBeTareasIngresoHH.items.get(i).NombrePropietario;
                        vItem.NumPoliza = pListBeTareasIngresoHH.items.get(i).NumPoliza;
                        vItem.NumOrden = pListBeTareasIngresoHH.items.get(i).NumOrden;
                        vItem.Muelle = pListBeTareasIngresoHH.items.get(i).Muelle;
                        BeListTareas.add(vItem);

                    }

                    count = BeListTareas.size();
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

        try {

            gl.gIdRecepcionEnc=0; gl.gVerifCascade =false;

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
                    txtTarea.setText("");
                    gl.pIdPedidoEnc = selid;
                    startActivity(new Intent(this, frm_detalle_tareas_verificacion.class));
                    break;
            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
        }

    }

    private void buscaLP(String lp) {
        gl.gLP=lp;
        execws(5);
    }

    private void recibeLP() {
        try {
            pListBeTransPeEnc=xobj.getresult(clsBeTrans_pe_encList.class,"Get_Pedido_A_Verificar_By_LP");
            progress.cancel();

            try {
                if (pListBeTransPeEnc.items.size()==0) throw new Exception();
            } catch (Exception e){
                toast("Licencia no existe");return;
            }

            gl.gVerifCascade=true;
            gl.gVCascIdEnc=pListBeTransPeEnc.items.get(0).IdPedidoEnc;

            browse=6;
            txtTarea.setText("");
            gl.pIdPedidoEnc=gl.gVCascIdEnc;
            startActivity(new Intent(this, frm_detalle_tareas_verificacion.class));

        } catch (Exception e){
            mu.msgbox("processListTareasPicking:"+e.getMessage());progress.cancel();
        }
    }

    private  void processIdOrdenCompra() {
        try {
            progress.setMessage("Obteniendo orden de compra...");
            progress.show();

            IdOrdenCompra = xobj.getresult(Integer.class,"Get_IdOrdenCompraEnc_By_Licencia");

            if (IdOrdenCompra > 0) {
                gl.gIdRecepcionEnc = stream(pListBeTareasIngresoHH.items).where(c -> c.IdOrderCompraEnc == IdOrdenCompra).select(c -> c.IdRecepcionEnc).first();

                execws(7);
            } else {
                progress.cancel();
                Toast.makeText(this, "No se ha encontrado una orden de compra válida relacionada a la licencia "+txtTarea.getText().toString()+".", Toast.LENGTH_LONG).show();
                msgLicPlate("Buscar por número de tarea");

            }
            progress.cancel();
        } catch (Exception e) {
            mu.msgbox("processIdOrdenCompra: "+e.getMessage());
        }
    }

    private void processIdRecepcion() {

        try {

            gl.gBeRecepcion = xobj.getresult(clsBeTrans_re_enc.class, "GetSingleRec");
            gl.IdPropietario = gl.gBeRecepcion.PropietarioBodega.getIdPropietario();
            gl.pTipoIngreso = gl.gBeRecepcion.OrdenCompraRec.OC.TipoIngreso;

            txtTarea.setText("");
            startActivity(new Intent(this, frm_list_rec_prod.class));

        } catch ( Exception e) {
            mu.msgbox("processIdRecepcion: "+e.getMessage());
        }
    }

    //#CKFK20240605 Agregué la funcionalidad de filtrar la orden de compra por código de producto
    private  void processListaOrdenCompra() {
        Cursor dt;
        List<Integer> ListaOC =  new ArrayList<>();;

        try {

            progress.setMessage("Obteniendo lista de orden de compra...");
            progress.show();

            dt=xobj.filldt();

            if (dt.getCount()>0) {

                dt.moveToFirst();

                while (!dt.isAfterLast()) {

                    ListaOC.add(dt.getInt(0));

                    dt.moveToNext();
                }
            }

            if (ListaOC.stream().count() > 0) {

                if (ListaOC.stream().count() == 1) {
                    IdOrdenCompra = ListaOC.get(0);
                    gl.gIdRecepcionEnc = stream(pListBeTareasIngresoHH.items).where(c -> c.IdOrderCompraEnc == IdOrdenCompra).select(c -> c.IdRecepcionEnc).first();
                    gl.Codigo_Producto = txtTarea.getText().toString().replace("$","");

                    execws(7);
                }else{

                    pListBeTareasIngresoHH.items= (ArrayList<clsBeTareasIngresoHH>) pListBeTareasIngresoHH.items.stream()
                            .filter(orden -> ListaOC.contains(orden.IdOrderCompraEnc))
                            .collect(Collectors.toList());
                    listItems();
                }

            } else {
                progress.cancel();
                Toast.makeText(this, "No se ha encontrado una orden de compra válida relacionada a la licencia "+txtTarea.getText().toString()+".", Toast.LENGTH_LONG).show();
                msgLicPlate("Buscar por número de tarea");

            }
            progress.cancel();
        } catch (Exception e) {
            mu.msgbox("processIdOrdenCompra: "+e.getMessage());
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

            if (!txtTarea.getText().toString().isEmpty()){

                selid = Integer.parseInt(txtTarea.getText().toString());

                if (gl.tipoTarea==1){

                    vIdTarea = stream(pListBeTareasIngresoHH.items).where(c->c.IdRecepcionEnc == selid).select(c->c.IdRecepcionEnc).first();

                    if (vIdTarea>0){
                        procesar_registro();
                    }else{

                        if (existe_en_recepciones(txtTarea.getText().toString())) {

                        }else{
                            mu.msgbox("No existe la tarea "+selid);
                        }
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
            if (e.getMessage() !=null){
                mu.msgbox("Error obteniendo la tarea: "+e.getMessage());
            }else{
                if(gl.tipoTarea==6) {
                    buscaLP(txtTarea.getText().toString());
                } else {
                    mu.msgbox("El número de tarea ingresado no es válido");
                    txtTarea.setText("");
                    txtTarea.requestFocus();
                }
            }
        }
    }

    //#EJC20220404:BYB Filtrar por licencia de ingreso de producción.
    private void Get_Fila_By_IdOrdenCompraEnc(){

        try{

            int vIdTarea=0;

            if (!txtTarea.getText().toString().isEmpty()){

                selid = Integer.parseInt(txtTarea.getText().toString());

                if (gl.tipoTarea==1){

                    vIdTarea = stream(pListBeTareasIngresoHH.items).where(c->c.IdOrderCompraEnc == selid).select(c->c.IdOrderCompraEnc).first();

                    if (vIdTarea>0){
                        procesar_registro();
                    }else{

                        if (existe_en_recepciones(txtTarea.getText().toString())) {

                        }else{
                            mu.msgbox("No existe la tarea "+selid);
                        }
                    }

                }
            }

        }catch (Exception e){
            if (e.getMessage() !=null){
                mu.msgbox("Error obteniendo la tarea: "+e.getMessage());
            }else{
                if(gl.tipoTarea==6) {
                    buscaLP(txtTarea.getText().toString());
                } else {
                    mu.msgbox("El número de tarea ingresado no es válido");
                    txtTarea.setText("");
                    txtTarea.requestFocus();
                }
            }
        }
    }

    private boolean existe_en_recepciones(String v_producto){

        boolean result = false;

        try{

            result = true;
            
        }catch (Exception ex){

        }

        return result;
    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    protected void onResume() {

        try{

            super.onResume();

            try {
                txtTarea.requestFocus();
            } catch (Exception e) {}

            if (browse==1){
                browse=0;
                Load();
            }

            if (browse==5){
                browse=0;
                Load();
            }

            if (browse==6){
                browse=0;
                Load();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    public void SalirTareas(View view){
        gl.gVerifCascade=false;
        doExit();
    }

    private void msgAskExit(String msg) {
        try{
           /* AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    gl.gVerifCascade=false;
                    doExit();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();*/

            //#GT10032022: set en el boton Si para facilitar el ENTER del teclado
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(R.string.app_name);

            // set dialog message
            alertDialogBuilder
                    .setMessage("¿" + msg + "?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            gl.gVerifCascade=false;
                            doExit();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(getResources().getColor(R.color.colorAccent));
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(getResources().getColor(R.color.colorAccent));
            //pbutton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            pbutton.setPadding(0, 10, 10, 0);
            //pbutton.setTextColor(Color.WHITE);

            pbutton.setFocusable(true);
            pbutton.setFocusableInTouchMode(true);
            pbutton.requestFocus();


        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgLicPlate(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setCancelable(false);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GetFila();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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
