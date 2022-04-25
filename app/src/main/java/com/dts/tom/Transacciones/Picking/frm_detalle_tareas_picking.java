package com.dts.tom.Transacciones.Picking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.base.appGlobals;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_det;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.ladapt.list_adapt_detalle_tareas_picking2;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.list_adapt_detalle_tareas_picking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_tareas_picking extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private ListView listView;
    private Spinner cmbOrdenadorPor;
    private Button btnPendientes,btnRes_Det;
    private EditText txtUbicacionFiltro, txtFiltro;
    private TextView  lblBodega, lblOperador, lblTituloForma;
    private ImageView btnLimpiar, btnFiltros;
    private RelativeLayout relbot, relFiltros;

    public static clsBeTrans_picking_enc gBePicking;
    public static clsBeTrans_picking_ubicList plistPickingUbi = new clsBeTrans_picking_ubicList();
    private clsBeTrans_picking_det gbePickingDet = new clsBeTrans_picking_det();
    private clsBeTrans_pe_enc gBePedidoEnc = new clsBeTrans_pe_enc();

    private ArrayList<clsBeTrans_picking_ubic> BeListPickingUbic = new ArrayList<clsBeTrans_picking_ubic>();
    private ArrayList<clsBeTrans_picking_ubic> AuxBePickingUbic = new ArrayList<clsBeTrans_picking_ubic>();
    private list_adapt_detalle_tareas_picking adapter;
    private list_adapt_detalle_tareas_picking2 adapter2;

    public static clsBeTrans_picking_ubic selitem, tmpPickingUbic;

    private List TipoOrden = new ArrayList();

    public static int TipoLista = 1;
    public static int pOrden=1;
    private boolean PreguntoPorDiferencia=false;
    private boolean Finalizar=true;
    private boolean areaprimera = true, filtros = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appGlobals gll;
        gll=((appGlobals) this.getApplication());
        areaprimera = gll.Mostrar_Area_En_HH;

        if (areaprimera) {
            setContentView(R.layout.activity_frm_detalle_tareas_picking2);
        } else {
            setContentView(R.layout.activity_frm_detalle_tareas_picking);
        }

        super.InitBase();

        ws = new WebServiceHandler(frm_detalle_tareas_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView) findViewById(R.id.listTareasPicking);
        cmbOrdenadorPor = (Spinner) findViewById(R.id.cmbOrdenadorPor);
        btnPendientes = (Button) findViewById(R.id.btnPendientes);
        btnRes_Det = (Button) findViewById(R.id.btnRes_Det);
        txtUbicacionFiltro = (EditText) findViewById(R.id.txtUbicacionFiltro);
        lblBodega = (TextView) findViewById(R.id.lblBodega);
        lblOperador = (TextView) findViewById(R.id.lblOperador);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        txtFiltro = (EditText) findViewById(R.id.txtFiltro);
        btnLimpiar = (ImageView) findViewById(R.id.btnLimpiar);
        btnFiltros = (ImageView) findViewById(R.id.btnFiltros);
        relbot = (RelativeLayout) findViewById(R.id.relbot);
        relFiltros = findViewById(R.id.relFiltros);

        lblBodega.setText("Bodega: "+ gl.IdBodega + " - "+gl.gNomBodega);
        lblOperador.setText("Operador: "+gl.OperadorBodega.IdOperadorBodega+" - "+ gl.OperadorBodega.Nombre_Completo);

        gl.mostar_filtros = false;
        gl.termino = "";

        ProgressDialog("Cargando forma...");

        setHandlers();
        Load();

    }

    private void setHandlers() {

        try {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //#EJC20220403: Force scan location...
                    if (areaprimera) {
                        listView.setClickable(false);
                        toast("Por favor, escaneé la ubicación");
                        txtUbicacionFiltro.requestFocus();
                    } else {
                        selid = 0;
                        //AT 20211222 No importa que la posición sea = a 0
                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_picking_ubic sitem = (clsBeTrans_picking_ubic) lvObj;
                        selitem = new clsBeTrans_picking_ubic();
                        //selitem = BeListPickingUbic.get(position);
                        selitem = sitem;

                        selid = sitem.IdPickingUbic;
                        selidx = position;

                        if (areaprimera) {
                            adapter2.getItem(position);
                        } else {
                            adapter.getItem(position);
                        }

                        procesar_registro();
                    }
                }

            });

            cmbOrdenadorPor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    pOrden=position+1;
                    Lista_Detalle_Picking();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });


            txtUbicacionFiltro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtUbicacionFiltro.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        //#CKFK20220425 Se agregó validación de que la ubicación sea numérica
                        String ubicacion=txtUbicacionFiltro.getText().toString();

                        if (!ubicacion.isEmpty()){
                            if (isNumeric(ubicacion)){
                                execws(5);
                            }else{
                                msgbox("La ubicación debe ser numérica " + ubicacion );
                            }
                        }
                    }

                    return false;
                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandles:" + e.getMessage());
        }

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gl.termino = "";
                txtFiltro.setText("");
                txtFiltro.requestFocus();
            }
        });

        txtFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence termino, int i, int i1, int i2) {
                if (!txtFiltro.getText().toString().isEmpty()) {
                    gl.termino = txtFiltro.getText().toString();
                    Filtro();
                } else {
                    Lista_Detalle_Picking();
                    btnLimpiar.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //#GT07042022: faltó agregar el view de filtros en las 2 layout de detalle_tareas_picking
        btnFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gl.mostar_filtros = !gl.mostar_filtros;
                relFiltros.setVisibility(gl.mostar_filtros ? View.VISIBLE : View.GONE);
            }
        });

    }

    public  boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    private void Filtro() {
        String termino  = gl.termino;

        if (!termino.isEmpty()) {
            btnLimpiar.setVisibility(View.VISIBLE);
        }

        AuxBePickingUbic.clear();
        for (clsBeTrans_picking_ubic obj:BeListPickingUbic){

            if(obj.CodigoProducto.toLowerCase().contains(termino.toLowerCase()) || obj.NombreProducto.toLowerCase().contains(termino.toLowerCase())){
                AuxBePickingUbic.add(obj);
            }
        }

        if (areaprimera) {
            adapter2 = new list_adapt_detalle_tareas_picking2(frm_detalle_tareas_picking.this, AuxBePickingUbic);
            listView.setAdapter(adapter2);
        } else {
            adapter = new list_adapt_detalle_tareas_picking(frm_detalle_tareas_picking.this, AuxBePickingUbic);
            listView.setAdapter(adapter);
        }

        btnPendientes.setText("Regs: "+ AuxBePickingUbic.size());
    }


    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void procesar_registro() {

        try {

            clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();

            if (TipoLista == 1) {//Resumido

                clsBeTrans_picking_ubic ubi = new clsBeTrans_picking_ubic();

                txtUbicacionFiltro.setText(""+selitem.IdUbicacion);

                pSubListPickingU.items = stream(plistPickingUbi.items).where(c->c.IdUbicacion == selitem.IdUbicacion).toList();

                if (gBePicking.Detalle_operador) {

                    if (pSubListPickingU!=null){

                        if (pSubListPickingU.items!=null){

                            for (clsBeTrans_picking_ubic ubicacion: pSubListPickingU.items){

                                //ubi = new clsBeTrans_picking_ubic();
                                //ubi = ubicacion;
                                //clsBeTrans_picking_ubic finalUbi = ubi;

                                clsBeTrans_picking_ubic finalUbi = ubicacion;

                                gbePickingDet = stream(gBePicking.ListaPickingDet.items).where(c -> c.IdPickingDet == finalUbi.IdPickingDet).first();

                                if (gbePickingDet.IdOperadorBodega != gl.OperadorBodega.IdOperadorBodega) {
                                    msgIngresaDetalle("Este picking no está asignado a este operador- ¿Quiere continuar con la tarea?");
                                }

                            }

                        }

                    }

                }else{
                    AbreFormaDatos();
                }

            } else if (TipoLista == 2) {//Detallado

                txtUbicacionFiltro.setText(""+selitem.IdUbicacion);

                if (gBePicking.Detalle_operador) {

                    if (gBePicking.ListaPickingDet != null) {

                        if (gBePicking.ListaPickingDet.items != null) {

                            gbePickingDet = stream(gBePicking.ListaPickingDet.items).where(c -> c.IdPickingDet == selitem.IdPickingDet).first();

                            if (gbePickingDet.IdOperadorBodega != gl.OperadorBodega.IdOperadorBodega) {
                                msgIngresaDetalle("Este picking no está asignado a este operador- ¿Quiere continuar con la tarea?");
                            }

                        }
                    }
                }else{
                    AbreFormaDatos();
                }

            }

            txtFiltro.setText("");

        } catch (Exception e) {
            mu.msgbox("procesar_registro:" + e.getMessage());
        }

    }

    private void AbreFormaDatos(){
        browse = 1;
        startActivity(new Intent(this, frm_picking_datos.class));
    }

    private void msgIngresaDetalle(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    AbreFormaDatos();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void Load(){

        try {

            TipoOrden.add("Ubicacion");
            TipoOrden.add("Codigo");
            TipoOrden.add("Vence");
            TipoOrden.add("Estado");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, TipoOrden);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbOrdenadorPor.setAdapter(dataAdapter);

            if (TipoOrden.size()>0) cmbOrdenadorPor.setSelection(0);

            pOrden=1;
            TipoLista = 2;
            btnRes_Det.setText("D.");

            lblTituloForma.setText("Picking #"+gl.gIdPickingEnc);

            if (gl.gIdPickingEnc>0){
                execws(1);
            }

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Lista_Detalle_Picking(){

        clsBeTrans_picking_ubic vItem;
        BeListPickingUbic.clear();

        try{

            progress.setMessage("Listando detalle de picking");

            if (plistPickingUbi!=null){

                if (plistPickingUbi.items!=null){

                    //AT 20211222 Ya no se agrega un item vacío
                    //vItem = new  clsBeTrans_picking_ubic();
                    //BeListPickingUbic.add(vItem);

                    for (clsBeTrans_picking_ubic obj:plistPickingUbi.items){

                        if (obj.Cantidad_Recibida!=obj.Cantidad_Solicitada){

                            vItem = new  clsBeTrans_picking_ubic();
                            if (obj.Fecha_Vence.contains("T")) {
                                obj.Fecha_Vence = du.convierteFechaMostrar(obj.Fecha_Vence);
                            }

                            //#EJC20220403:Cambio de ubicación temporal en picking.
                            if (obj.IdUbicacionTemporal!=0) {
                                obj.IdUbicacion = obj.IdUbicacionTemporal;
                                obj.NombreUbicacion = obj.NombreUbicacionTemporal;
                            }

                            vItem = obj;

                            BeListPickingUbic.add(vItem);

                        }

                    }

                    //AT 20211222 Ya no se resta 1 para obtener el registro total
                    int count =BeListPickingUbic.size();
                    btnPendientes.setText("Regs: "+count);

                }
            }

            if (plistPickingUbi!=null){
                if (plistPickingUbi.items!=null){
                    Collections.sort(BeListPickingUbic,new OrdenarItems());
                    Collections.sort(plistPickingUbi.items,new OrdenarItems());
                }
            }

            if (areaprimera) {
                adapter2 = new list_adapt_detalle_tareas_picking2(this, BeListPickingUbic);
                listView.setAdapter(adapter2);
            } else {
                adapter = new list_adapt_detalle_tareas_picking(this, BeListPickingUbic);
                listView.setAdapter(adapter);
            }

            progress.cancel();
        }catch (Exception e){
            mu.msgbox("Lista_Detalle_Picking:"+e.getMessage());
        }
    }

    private void Lista_Detalle(){

        try{

            if (BeListPickingUbic.size()==0){

                btnPendientes.setText("Completa");
                btnPendientes.setBackgroundColor(Color.parseColor("#C8E6C9"));
                btnPendientes.setTextColor(Color.BLACK);
                relbot.setBackgroundColor(Color.parseColor("#C8E6C9"));

                Finalizar_Picking();

            }

        }catch (Exception e){
            mu.msgbox("Lista_Detalle:"+e.getMessage());
        }
    }

    public void BotonFinalizar(View view){
        Finalizar_Picking();
    }

    public void BotonR(View view){

        try{
            if (btnRes_Det.getText().toString().equals("C.")){

                btnRes_Det.setText("D.");

                TipoLista=2;


                execws(3);

            }else{
                btnRes_Det.setText("C.");

                TipoLista=1;

                execws(3);
            }

        }catch (Exception e) {
            mu.msgbox("BotonR:"+e.getMessage());
        }
    }

    private void Finalizar_Picking(){

        try{

            Finalizar=true;

            progress.show();

            progress.setMessage("Finalizando picking...");

            if (plistPickingUbi != null){
                if (plistPickingUbi.items != null){
                    for (clsBeTrans_picking_ubic ubi:plistPickingUbi.items){

                        if (ubi.Cantidad_Recibida!=ubi.Cantidad_Solicitada){

                            PreguntoPorDiferencia = true;
                            msgAskPicIncompleto("El picking está incompleto, Finalizar de todas formas?");
                            break;
                        }

                    }
                }
            }

            if (Finalizar&&!PreguntoPorDiferencia){
                Continua_Finalizar_Picking();
            }

        }catch (Exception e){
            mu.msgbox("Finalizar_Picking:"+e.getMessage());
        }
    }

    private void Continua_Finalizar_Picking(){

        try{

            if (Finalizar) {

                progress.setMessage("Actualizando estado de picking...");

                gBePicking.Estado = "Procesado";
                gBePicking.Fec_mod = du.getFechaActual();
                gBePicking.User_mod = gl.OperadorBodega.IdOperador+"";
                gBePicking.Hora_fin = du.getFechaActual();

                //Llama al método Actualizar_PickingEnc_Procesado
                execws(4);

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("Continua_Finalizar_Picking:"+e.getMessage());
        }
    }

    private void msgAskPicIncompleto(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_Finalizar_Picking();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Finalizar=false;
                    progress.cancel();
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public class OrdenarItems implements Comparator<clsBeTrans_picking_ubic> {

        public int compare(clsBeTrans_picking_ubic left,clsBeTrans_picking_ubic rigth){
            //return left.CodigoProducto-rigth.IdRecepcionDet;
            if (pOrden==1){
               return left.IdUbicacion-rigth.IdUbicacion;
            }else if(pOrden==2){
                return left.CodigoProducto.compareTo(rigth.CodigoProducto);
            }else if(pOrden==3){
                return left.Fecha_Vence.compareTo(rigth.Fecha_Vence);
            }else if(pOrden==4){
                return left.ProductoEstado.compareTo(rigth.ProductoEstado);
            }
            return left.CodigoProducto.compareTo(rigth.CodigoProducto);
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
                        callMethod("Get_Picking_By_IdPickingEnc","pIdPickingEnc",gl.gIdPickingEnc);
                        break;
                    case 2:
                        callMethod("Actualizar_Estado_Picking","oBeTrans_picking_enc",gBePicking);
                        break;
                    case 3:
                        callMethod("Get_All_PickingUbic_By_IdPickingEnc_Tipo",
                                "pIdPickingEnc",gBePicking.IdPickingEnc,
                                "pDetalleOperador",gBePicking.Detalle_operador,
                                "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega,
                                "Tipo",TipoLista);
                        break;
                    case 4:
                        callMethod("Actualizar_PickingEnc_Procesado","oBeTrans_picking_enc",gBePicking);
                        break;
                    case 5:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicacionFiltro.getText().toString(),"pIdBodega",gl.IdBodega);
                        break;
                }

                progress.cancel();

            } catch (Exception e) {
                progress.cancel();
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
                    processGetPicking();
                    break;
                case 2:
                    procesActualizarEstadoPicking();
                    break;
                case 3:
                    processGetAllPickingUbic();
                    break;
                case 4:
                    processActualizarPickingEnc();
                    break;
                case 5:
                    processGetUbicacion();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processGetPicking(){

        try{

            progress.setMessage("Obteniendo valores de picking");
            progress.show();

            //-----------------------------
            gBePicking =xobj.getresult(clsBeTrans_picking_enc.class,"Get_Picking_By_IdPickingEnc");

            if (gBePicking!=null){

                progress.setMessage("Actualizando picking a estado pendiente...");

                if (gBePicking.Estado.equals("Nuevo")){
                    gBePicking.Estado = "Pendiente";
                    execws(2);
                }else{
                    procesActualizarEstadoPicking();
                }

            }else{
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processGetPicking:"+e.getMessage());
        }
    }

    private void procesActualizarEstadoPicking(){

        try{

            progress.setMessage("Cargando detalle del picking...");

            plistPickingUbi = gBePicking.ListaPickingUbic;

            Lista_Detalle_Picking();

        }catch (Exception e){
            mu.msgbox("procesActualizarEstadoPicking:"+e.getMessage());
        }

    }

    private void processGetAllPickingUbic(){

        try{

            plistPickingUbi = xobj.getresult(clsBeTrans_picking_ubicList.class,"Get_All_PickingUbic_By_IdPickingEnc_Tipo");

            Lista_Detalle_Picking();

            Lista_Detalle();

            if (!gl.termino.isEmpty())
                Filtro();

        }catch (Exception e){
            mu.msgbox("processGetAllPickingUbic:"+e.getMessage());
        }
    }

    public void ActualizaLista(View view) {
        try {
            progress.setMessage("Actualizando Lista de Picking...");
            progress.show();
            execws(1);
        } catch (Exception e) {
            mu.msgbox("ActualizaLista: "+e.getMessage());
        }
    }

    private void processActualizarPickingEnc(){

        try{

            progress.setMessage("Finalizando actualización de estado...");

            int Act = xobj.getresult(Integer.class,"Actualizar_PickingEnc_Procesado");

            progress.cancel();

            doExit();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processActualizarPickingEnc:"+e.getMessage());
        }
    }

    private void processGetUbicacion(){

        clsBeBodega_ubicacion beUbicacion = new clsBeBodega_ubicacion();

        try{

            beUbicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (beUbicacion!=null){

                for (clsBeTrans_picking_ubic r:BeListPickingUbic){

                    if (beUbicacion.IdUbicacion==r.IdUbicacion){
                        selitem = r;
                        selid = r.IdPickingUbic;
                        procesar_registro();
                        break;
                    }
                }

            }else{

                //mu.msgbox("El código de ubicacion escaneado: "+txtUbicacionFiltro.getText().toString()+ " no es válido para la bodega: "+gl.IdBodega);
                mu.msgbox("La úbicación escaneada: "+txtUbicacionFiltro.getText().toString()+ " no esta en la ola de picking");
                txtUbicacionFiltro.setSelectAllOnFocus(true);
                txtUbicacionFiltro.requestFocus();
                txtUbicacionFiltro.setText("");
                return;
            }

        }catch (Exception e){
            mu.msgbox("processGetUbicacion:"+e.getMessage());
        }
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (!gl.termino.isEmpty()) {
                txtFiltro.setText(gl.termino);
            }

            if (browse==1){
                browse=0;
                txtUbicacionFiltro.setText("");
                execws(3);
            }

            if (browse==2){
                doExit();
            }else{
                try {
                    txtUbicacionFiltro.requestFocus();
                } catch (Exception e) {

                }
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void Salir(View view){
        doExit();
    }

    private void doExit(){
        try {
            gBePicking = new clsBeTrans_picking_enc();
            plistPickingUbi = new clsBeTrans_picking_ubicList();
            gbePickingDet = new clsBeTrans_picking_det();
            BeListPickingUbic = new ArrayList<>();
            selitem = new clsBeTrans_picking_ubic();
            browse = 2;
            //super.finish();
            finish();
        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    public void onBackPressed() {
        try{
            doExit();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}
