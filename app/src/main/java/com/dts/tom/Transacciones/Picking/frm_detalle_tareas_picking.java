package com.dts.tom.Transacciones.Picking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
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
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresaAndList;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresaBase;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_det;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.extListChkDlg;
import com.dts.ladapt.list_adapt_detalle_tareas_picking2;
import com.dts.ladapt.list_adapt_detalle_tareas_picking3;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.list_adapt_detalle_tareas_picking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_tareas_picking extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private Dialog progress;
    private ListView listView;
    private Spinner cmbOrdenadorPor;
    private Button btnPendientes,btnRes_Det;
    private EditText txtUbicacionFiltro, txtFiltro;
    private TextView  lblBodega, lblOperador, lblTituloForma;
    private ImageView btnLimpiar, btnFiltros, imgOrdenar;
    private RelativeLayout relbot, relFiltros;

    public static clsBeTrans_picking_enc gBePicking;
    public static clsBeTrans_picking_ubicList plistPickingUbi = new clsBeTrans_picking_ubicList();
    private clsBeTrans_picking_det gbePickingDet = new clsBeTrans_picking_det();
    private final clsBeTrans_pe_enc gBePedidoEnc = new clsBeTrans_pe_enc();

    private ArrayList<clsBeTrans_picking_ubic> BeListPickingUbic = new ArrayList<clsBeTrans_picking_ubic>();
    private final ArrayList<clsBeTrans_picking_ubic> AuxBePickingUbic = new ArrayList<clsBeTrans_picking_ubic>();
    private ArrayList<clsBeTrans_picking_ubic> AuxBeListPickingUbic = new ArrayList<clsBeTrans_picking_ubic>();
    private list_adapt_detalle_tareas_picking adapter;
    private list_adapt_detalle_tareas_picking2 adapter2;
    private list_adapt_detalle_tareas_picking3 adapter3;
    public static clsBeTrans_picking_ubic selitem, tmpPickingUbic;

    private final List TipoOrden = new ArrayList();

    private boolean primeravez = false;

    public static int TipoLista = 1;
    public static int pOrden=0;
    private boolean PreguntoPorDiferencia=false;
    private boolean Finalizar=true;
    private boolean areaprimera = true;
    private final boolean filtros = false;
    private boolean asignar_operador_linea_picking = false;
    private ArrayList<String> ListRack = new ArrayList<>();
    private ArrayList<String> ListRackSel = new ArrayList<>();
    private ArrayList<Integer> IdxFiltos = new ArrayList<>();
    private int sortord, TipoOrdenDetalle = 0;
    public Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            myActivity = this;

            super.InitBase();

            asignar_operador_linea_picking = false;

            areaprimera = gl.Mostrar_Area_En_HH;

            int tipo = 0;

            if(gl.TipoPantallaPicking == 3) {
                tipo = gl.TipoPantallaPicking;
            } else if(areaprimera) {
                tipo = 2;
            } else {
                tipo = 1;
            }

            cargaPantallaPicking(tipo);

            ws = new WebServiceHandler(frm_detalle_tareas_picking.this, gl.wsurl);
            xobj = new XMLObject(ws);

            listView = findViewById(R.id.listTareasPicking);
            cmbOrdenadorPor = findViewById(R.id.cmbOrdenadorPor);
            btnPendientes = findViewById(R.id.btnPendientes);
            btnRes_Det = findViewById(R.id.btnRes_Det);
            txtUbicacionFiltro = findViewById(R.id.txtUbicacionFiltro);
            lblBodega = findViewById(R.id.lblBodega);
            lblOperador = findViewById(R.id.lblOperador);
            lblTituloForma = findViewById(R.id.lblTituloForma);
            txtFiltro = findViewById(R.id.txtFiltro);
            btnLimpiar = findViewById(R.id.btnLimpiar);
            btnFiltros = findViewById(R.id.btnFiltros);
            relbot = findViewById(R.id.relbot);
            relFiltros = findViewById(R.id.relFiltros);
            imgOrdenar = findViewById(R.id.imgOrdenar);

            lblBodega.setText("Bodega: "+ gl.IdBodega + " - "+gl.gNomBodega);
            lblOperador.setText("Operador: "+gl.OperadorBodega.IdOperadorBodega+" - "+ gl.OperadorBodega.Nombre_Completo);

            gl.mostar_filtros = false;
            gl.termino = "";
            ListRackSel.clear();
            ListRack.clear();

            ProgressDialog("Obteniendo Picking Fase 1...");

            setHandlers();

            Load();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cargaPantallaPicking(int tipo){
        try {
            switch (tipo) {
                case 1:
                    setContentView(R.layout.activity_frm_detalle_tareas_picking);
                    break;
                case 2:
                    setContentView(R.layout.activity_frm_detalle_tareas_picking2);
                    break;
                case 3:
                    setContentView(R.layout.activity_frm_detalle_tareas_picking3);
                    break;
            }
        }catch(Exception e) {
            mu.msgbox("caraPantallaRecepion :"+e.getMessage());
        }
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

                        if (gl.TipoPantallaPicking == 3) {
                            adapter3.getItem(position);
                        } else {
                            if (areaprimera) {
                                adapter2.getItem(position);
                            } else {
                                adapter.getItem(position);
                            }
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

                    if (ListRackSel.size() == 0) {
                        Lista_Detalle_Picking();
                    } else {
                        Collections.sort(BeListPickingUbic,new OrdenarItems());
                        Collections.sort(plistPickingUbi.items,new OrdenarItems());

                        if (gl.TipoPantallaPicking == 3) {
                            adapter3.refreshItems();
                        } else {
                            if (areaprimera) {
                                adapter2.refreshItems();
                            } else {
                                adapter.refreshItems();
                            }
                        }
                    }
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

        btnLimpiar.setOnClickListener(view -> {
            gl.termino = "";
            txtFiltro.setText("");
            txtFiltro.requestFocus();
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

        try {
            if (!termino.isEmpty()) {
                btnLimpiar.setVisibility(View.VISIBLE);
            }

            AuxBePickingUbic.clear();
            for (clsBeTrans_picking_ubic obj : BeListPickingUbic) {

                if (obj.CodigoProducto.toLowerCase().contains(termino.toLowerCase()) || obj.NombreProducto.toLowerCase().contains(termino.toLowerCase())) {
                    AuxBePickingUbic.add(obj);
                }
            }

            if (gl.TipoPantallaPicking == 3) {
                adapter3 = new list_adapt_detalle_tareas_picking3(frm_detalle_tareas_picking.this, AuxBePickingUbic);
                listView.setAdapter(adapter3);
            } else {
                if (areaprimera) {
                    adapter2 = new list_adapt_detalle_tareas_picking2(frm_detalle_tareas_picking.this, AuxBePickingUbic);
                    listView.setAdapter(adapter2);
                } else {
                    adapter = new list_adapt_detalle_tareas_picking(frm_detalle_tareas_picking.this, AuxBePickingUbic);
                    listView.setAdapter(adapter);
                }
            }

            btnPendientes.setText("Regs: " + AuxBePickingUbic.size());
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() +" - "+ e.getMessage());
        }
    }

//    public void ProgressDialog(String mensaje) {
//
//        try {
//
//            if (progress ==null){
//                progress = new ProgressDialog(myActivity);
//            }
//
//            progress_setMessage(mensaje);
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setCancelable(false);
//            progress.setProgress(0);
//
//            new Handler().postDelayed(() -> progress.show(),100);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

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
                            }else{
                                AbreFormaDatos();
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

        String titulo = "";

        try {

            TipoOrden.add("Ubicación");
            TipoOrden.add("Codigo");
            TipoOrden.add("Vence");
            TipoOrden.add("Estado");
            TipoOrden.add("Clasificación");
            TipoOrden.add("Nombre Ubicación");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, TipoOrden);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbOrdenadorPor.setAdapter(dataAdapter);

            if (TipoOrden.size()>0) cmbOrdenadorPor.setSelection(5);

            //#GT22122022: mostrar la lista por defecto a menos que se use el filtro personalizado
            //pOrden=1;
            //#AT20230213 Se cambia el valor de 0 a 6, para que order por nombre ubicación por defecto
            //pOrden=0;
            pOrden = 6;
            TipoLista = 2;
            TipoOrdenDetalle = 1;
            btnRes_Det.setText("D.");

            //gl.gReferencia = .Referencia;

            titulo = gl.gReferencia.isEmpty() ? "Picking #" + gl.gIdPickingEnc : "Picking #" + gl.gIdPickingEnc +" - "+gl.gReferencia;
            lblTituloForma.setText(titulo);

            progress_setMessage("Obteniendo picking Fase 2...");

            if (gl.gIdPickingEnc>0){
                execws(1);
            }

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Lista_Detalle_Picking(){
        String Rack ="";
        String[] val;
        int idx;
        clsBeTrans_picking_ubic vItem;
        BeListPickingUbic.clear();
        AuxBeListPickingUbic.clear();

        try{

            progress_setMessage("Listando detalle");

            if (plistPickingUbi!=null){

                if (plistPickingUbi.items!=null){

                    ListRack.clear();

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

                            //#AT20230208 Se obtiene lista de racks del picking
                           /* Rack = obj.NombreUbicacion;
                            idx = Rack.indexOf("-");
                            Rack = Rack.substring(0, idx);*/

                            val =  obj.NombreUbicacion.split("-");

                            //#AT20240902 Solo se toma en cuenta el rack, sin agregarle nivels
                            if (val.length > 0) {
                                Rack = val[0].trim();
                            }

                            if (!ListRack.contains(Rack.trim())) {
                                ListRack.add(Rack.trim());
                            }

                            BeListPickingUbic.add(vItem);
                            AuxBeListPickingUbic.add(vItem);

                        }
                    }

                    //AT 20211222 Ya no se resta 1 para obtener el registro total
                    int count =BeListPickingUbic.size();
                    btnPendientes.setText("Regs: "+count);

                }
            }

            if (ListRack.size() > 0) {
                Collections.sort(ListRack, new ItemComparator());
            }


            if (plistPickingUbi!=null){
                if (plistPickingUbi.items!=null){

                    //#AT20230212 Para mantener orden ascendente y descendente
                    if (TipoOrdenDetalle == 1) {
                        sortord = 1;
                    } else if (TipoOrdenDetalle == 2) {
                        sortord = -1;
                    }

                    //#AT2022030212 Para mantener el filtro por racks
                    if (ListRackSel.size() != 0) {
                        FiltroPorRacks();
                    }

                    Collections.sort(BeListPickingUbic, new OrdenarItems());
                    Collections.sort(plistPickingUbi.items, new OrdenarItems());
                }
            }

            MostrarTipoOrden();

            if (gl.TipoPantallaPicking == 3) {
                adapter3 = new list_adapt_detalle_tareas_picking3(this, BeListPickingUbic);
                listView.setAdapter(adapter3);
            } else {
                if (areaprimera) {
                    adapter2 = new list_adapt_detalle_tareas_picking2(this, BeListPickingUbic);
                    listView.setAdapter(adapter2);
                } else {
                    adapter = new list_adapt_detalle_tareas_picking(this, BeListPickingUbic);
                    listView.setAdapter(adapter);
                }
            }

            //progress.cancel();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("Lista_Detalle_Picking:"+e.getMessage());
        }finally {
            progress.cancel();
        }
    }

    private void Lista_Detalle(){

        try{
            //#AT20230215 Si AuxBeListPickingUbic no es 0, no procede a  finalizar el picking.
            //AuxBeListPickingUbic es una copia del detalle de picking del ws
            if (BeListPickingUbic.size()==0 && !gBePicking.Detalle_operador && AuxBeListPickingUbic.size() == 0){

                btnPendientes.setText("Completa");
                btnPendientes.setBackgroundColor(Color.parseColor("#C8E6C9"));
                btnPendientes.setTextColor(Color.BLACK);
                relbot.setBackgroundColor(Color.parseColor("#C8E6C9"));

                Finalizar_Picking();
            } else {
                //#AT20230215 Si BeListPickingUbic filtrada queda en 0 y AuxBeListPickingUbic no esta vacía
                //recarga la lista sin filtros.
                if (BeListPickingUbic.size() == 0 && AuxBeListPickingUbic.size() != 0) {
                    ListRackSel.clear();
                    Lista_Detalle_Picking();
                }
            }
        } catch (Exception e){
            mu.msgbox("Lista_Detalle:"+e.getMessage());
        }
    }

    public void BotonFinalizar(View view){
        primeravez = true;
        msgAskFinalizarPicking("Está seguro de finalizar el Picking");
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

            progress_setMessage("Finalizando picking...");

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
            progress.cancel();
            mu.msgbox("Finalizar_Picking:"+e.getMessage());
        }
    }

    private void Continua_Finalizar_Picking(){

        try{

            if (Finalizar) {

                progress_setMessage("Actualizando estado de picking...");

                //#GT10012023: nuevo estado para aplicar procesos manufactura ligera
                //gBePicking.Estado = "Procesado";
                //#GT11012023: para finalizar el picking no es necesario llenar este objeto, solo se esta enviando el idPicking
                //y el ws le asigna un estado de Pickeado
                gBePicking.Estado = "Pickeado";
                gBePicking.Fec_mod = du.getFechaActual();
                gBePicking.User_mod = gl.OperadorBodega.IdOperador+"";
                gBePicking.Hora_fin = du.getFechaActual();

                //#CKFK20240229 Agregué esta validación para que no se actualicen los pedidos con picking 0
                if (gBePicking.IdPickingEnc!=0){
                    //Llama al método Actualizar_PickingEnc_Procesado
                    execws(4);
                }else{
                    throw new Exception("No se puede finalizar el picking el IdPickingEnc es 0 ");
                }

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

    private void msgAskFinalizarPicking(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (primeravez){
                        primeravez  = false;
                        msgAskFinalizarPicking("Está completamente seguro");
                    }else{
                        Finalizar_Picking();
                    }
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

     class OrdenarItems implements Comparator<clsBeTrans_picking_ubic> {

        public int compare(clsBeTrans_picking_ubic left,clsBeTrans_picking_ubic rigth){
            //return left.CodigoProducto-rigth.IdRecepcionDet;
            if (pOrden==1){
               return Integer.compare(sortord * left.IdUbicacion, sortord * rigth.IdUbicacion);
            }else if(pOrden==2){
                return sortord*left.CodigoProducto.compareTo(rigth.CodigoProducto);
            }else if(pOrden==3){
                return sortord*left.Fecha_Vence.compareTo(rigth.Fecha_Vence);
            }else if(pOrden==4){
                return sortord*left.ProductoEstado.compareTo(rigth.ProductoEstado);
            } else if(pOrden==5) {
                return sortord*left.NombreClasificacion.compareTo(rigth.NombreClasificacion);
            } else if(pOrden==6) {
                return sortord*left.NombreUbicacion.compareTo(rigth.NombreUbicacion);
            }

            return Integer.compare(sortord * left.IdPickingEnc, sortord * rigth.IdPickingEnc);
        }

    }

    public class ItemComparator implements Comparator<String> {
        public int compare(String left, String right) {
            return left.compareTo(right);

        }
    }

    public void showItemMenu(View view) {
        final AlertDialog Dialog;
        final String[] selitems = {cmbOrdenadorPor.getSelectedItem()+" A - Z", cmbOrdenadorPor.getSelectedItem()+" Z - A"};
        pOrden= cmbOrdenadorPor.getSelectedItemPosition()+1;


        AlertDialog.Builder menudlg = new AlertDialog.Builder(this);
        menudlg.setTitle("Ordenar por:");

        menudlg.setItems(selitems , new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                gl.sortOrd = item;
                orderar();
                if (gl.TipoPantallaPicking == 3) {
                    adapter3 = new list_adapt_detalle_tareas_picking3( frm_detalle_tareas_picking.this, BeListPickingUbic);
                    listView.setAdapter(adapter3);
                } else {
                    if (areaprimera) {
                        adapter2 = new list_adapt_detalle_tareas_picking2(frm_detalle_tareas_picking.this, BeListPickingUbic);
                        listView.setAdapter(adapter2);
                    } else {
                        adapter = new list_adapt_detalle_tareas_picking(frm_detalle_tareas_picking.this, BeListPickingUbic);
                        listView.setAdapter(adapter);
                    }
                }

                //#AT20230212 Mostrar toast con el tipo de orden ascendente o descendente
                MostrarTipoOrden();

                dialog.cancel();
            }
        });

        menudlg.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Dialog = menudlg.create();
        Dialog.show();
    }

    private void orderar() {
        //#AT20230212 TipoOrdenDetalle 1 = asc y 2 = desc
        switch (gl.sortOrd) {
            case 0:
                sortord=1;
                TipoOrdenDetalle =  1;
                Collections.sort(BeListPickingUbic,new OrdenarItems());
                Collections.sort(plistPickingUbi.items,new OrdenarItems());
                break;
            case 1:
                sortord=-1;
                TipoOrdenDetalle = 2;
                Collections.sort(BeListPickingUbic,new OrdenarItems());
                Collections.sort(plistPickingUbi.items,new OrdenarItems());
                break;
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
                        int IdOperadorBodega = 0;

                        if (asignar_operador_linea_picking) {
                            IdOperadorBodega = gl.OperadorBodega.IdOperadorBodega;
                        }

                        callMethod("Get_Picking_By_IdPickingEnc",
                                   "pIdPickingEnc",gl.gIdPickingEnc,
                                   "pIdOperadorBodega", IdOperadorBodega);
                        break;
                    case 2:
                        //#EJC20220608: Cambio en método.
                        callMethod("Actualizar_Estado_Picking_Andr","pEstadoPicking",gBePicking.Estado,"pIdPickingEnc", gBePicking.IdPickingEnc);
                        break;
                    case 3:
                        callMethod("Get_All_PickingUbic_By_IdPickingEnc_Tipo",
                                   "pIdPickingEnc",gBePicking.IdPickingEnc,
                                   "pDetalleOperador",gBePicking.Detalle_operador,
                                   "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega,
                                   "Tipo",TipoLista);
                        break;
                    case 4:
                        //#EJC20220608:Evitar enviar objetos completos.
                        //callMethod("Actualizar_PickingEnc_Procesado","oBeTrans_picking_enc",gBePicking);
                        callMethod("Actualizar_PickingEnc_Procesado_Andr",
                                "pIdPickingEnc",gBePicking.IdPickingEnc);
                        break;

                    case 5:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicacionFiltro.getText().toString(),"pIdBodega",gl.IdBodega);
                        break;
                }

                //progress.cancel();

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

            progress_setMessage("Serializando Picking...");

            gBePicking =xobj.getresult(clsBeTrans_picking_enc.class,"Get_Picking_By_IdPickingEnc");

            if (gBePicking!=null){

                progress_setMessage("Actualizando picking a estado pendiente...");

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
        }finally {
            progress.cancel();
        }
    }

    private void procesActualizarEstadoPicking(){

        try{

            plistPickingUbi = gBePicking.ListaPickingUbic;

            Lista_Detalle_Picking();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("procesActualizarEstadoPicking:"+e.getMessage());
        }finally {
            progress.cancel();
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
            progress_setMessage("Actualizando Lista de Picking...");
            progress.show();
            execws(1);
        } catch (Exception e) {
            mu.msgbox("ActualizaLista: "+e.getMessage());
        }finally {
            //progress.cancel();
        }
    }

    private void processActualizarPickingEnc(){

        try{

            progress_setMessage("Finalizando actualización de estado...");

            int Act = xobj.getresult(Integer.class,"Actualizar_PickingEnc_Procesado_Andr");

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

                //Llamar execws(3); ya que carga el detalle según el tipo de lista 1:Consolidado 2:Detallado
                if (TipoLista > 0) {
                    execws(3);
                } else {
                    execws(1);
                }
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

    private TextView txtMensajeDialog;
    private String mensaje_progress ="";

    public void ProgressDialog(String mensaje){

        progress= new Dialog(this);
        progress.setContentView(R.layout.dialog_loading);
        progress.setCancelable(false);
        Window window=progress.getWindow();

       /* if(window!=null){
            window.setBackgroundDrawable(new ColorDrawable(0));
        }*/

        runOnUiThread(() -> {
            txtMensajeDialog= progress.findViewById(R.id.txtMensajeDialog);
            if(txtMensajeDialog!=null){
                txtMensajeDialog.setText(mensaje);
            }
        });

        progress.show();

    }

    public void progress_setMessage(String mensaje){
        try {
            if(progress!=null){
                runOnUiThread(() -> {
                    txtMensajeDialog = progress.findViewById(R.id.txtMensajeDialog);
                    if(txtMensajeDialog!=null){
                        txtMensajeDialog.setText(mensaje);
                        mensaje_progress = mensaje;
                        txtMensajeDialog.postDelayed(mUpdate,100);
                    }
                });
            }else{
                Log.println(Log.DEBUG,"Progress","Isnull");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Runnable mUpdate = new Runnable() {

        public void run() {

            txtMensajeDialog.setText(mensaje_progress);
            txtMensajeDialog.postDelayed(this, 1000);

        }
    };

    private void MostrarTipoOrden() {
        if (TipoOrdenDetalle == 1) {
            toast(cmbOrdenadorPor.getSelectedItem() +" - ascendente");
        } else if (TipoOrdenDetalle == 2) {
            toast(cmbOrdenadorPor.getSelectedItem() +" - descendente");
        }
    }

    //region FiltroRacks
    public void Filtro(View view) {
        String s = "";
        boolean flag;
        try {

            extListChkDlg listdlg = new extListChkDlg();
            listdlg.buildDialog(frm_detalle_tareas_picking.this,"Lista de Racks","SALIR","TODO","APLICAR");
            listdlg.setWidth(350);listdlg.setLines(5);

            for (int i = 0; i <ListRack.size(); i++) {
                s = ListRack.get(i);
                flag = ListRackSel.contains(s);

                listdlg.add(ListRack.get(i),flag);
            }

            listdlg.setOnExitListener(v -> listdlg.dismiss());

            listdlg.setOnAddListener(v -> {
                ListRackSel.clear();

                for (int i=0; i < listdlg.items.size(); i++) {
                    if (listdlg.items.get(i).checked) {
                        ListRackSel.add(listdlg.items.get(i).text);
                    }
                }

                FiltroPorRacks();

                listdlg.dismiss();
            });

            listdlg.setOnDelListener(v -> {
                BeListPickingUbic.clear();
                for (int i= 0; i < AuxBeListPickingUbic.size(); i++) {
                    BeListPickingUbic.add(AuxBeListPickingUbic.get(i));
                }

                btnPendientes.setText("Regs: " + BeListPickingUbic.size());

                if (gl.TipoPantallaPicking == 3) {
                    adapter3 = new list_adapt_detalle_tareas_picking3(this, BeListPickingUbic);
                    listView.setAdapter(adapter3);
                } else {
                    if (areaprimera) {
                        adapter2 = new list_adapt_detalle_tareas_picking2(this, BeListPickingUbic);
                        listView.setAdapter(adapter2);
                    } else {
                        adapter = new list_adapt_detalle_tareas_picking(this, BeListPickingUbic);
                        listView.setAdapter(adapter);
                    }
                }

                ListRackSel.clear();
                listdlg.dismiss();
            });

            listdlg.show();

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void FiltroPorRacks() {
        String Racks = "";
        String val[];
        int idx, pos;
        try {

            BeListPickingUbic.clear();
            IdxFiltos.clear();
            for (int i= 0; i < AuxBeListPickingUbic.size(); i++) {
                BeListPickingUbic.add(AuxBeListPickingUbic.get(i));
            }

            for (int i = 0; i < BeListPickingUbic.size(); i ++) {

                //Racks = BeListPickingUbic.get(i).NombreUbicacion;
                /*idx = Racks.indexOf("-");
                Racks= Racks.substring(0, idx).trim();*/

                val =  BeListPickingUbic.get(i).NombreUbicacion.split("-");

                //#AT20240902 Solo se toma en cuenta el rack, sin agregarle nivels
                if (val.length > 0) {
                    Racks = val[0].trim();
                }

                if (!ListRackSel.contains(Racks)) {
                    IdxFiltos.add(i);
                }
            }

            for (int i = 0; i < IdxFiltos.size(); i++) {
                pos = IdxFiltos.size() - i - 1;
                idx = IdxFiltos.get(pos);
                BeListPickingUbic.remove(idx);
            }

            btnPendientes.setText("Regs: " + BeListPickingUbic.size() + "/" + AuxBeListPickingUbic.size());

            if (gl.TipoPantallaPicking == 3) {
                adapter3 = new list_adapt_detalle_tareas_picking3(this, BeListPickingUbic);
                listView.setAdapter(adapter3);
            } else {
                if (areaprimera) {
                    adapter2 = new list_adapt_detalle_tareas_picking2(this, BeListPickingUbic);
                    listView.setAdapter(adapter2);
                } else {
                    adapter = new list_adapt_detalle_tareas_picking(this, BeListPickingUbic);
                    listView.setAdapter(adapter);
                }
            }

        } catch (Exception e) {
            msgbox(new Object() {} .getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
        }
    }
    //endregion
}
