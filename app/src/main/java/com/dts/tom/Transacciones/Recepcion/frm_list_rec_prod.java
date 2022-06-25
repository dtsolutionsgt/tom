package com.dts.tom.Transacciones.Recepcion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dts.base.ExDialog;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.base.appGlobals;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_pallet;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_palletList;
import com.dts.classes.Mantenimientos.Configuracion_barra_pallet.clsBeConfiguracion_barra_pallet;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti.clsBeTrans_oc_ti;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_oc.clsBeTrans_re_oc;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_recList;
import com.dts.ladapt.Recepcion.list_adapt_detalle_recepcion2;
import com.dts.ladapt.Recepcion.list_adapt_detalle_recepcion3;
import com.dts.tom.DrawingView;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.list_adapt_detalle_recepcion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_list_rec_prod extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private TextView lblTituloForma, lblIdPropietarioBodega, lblNombrePropietario;
    private Button btnRegs,btnCompletaRec,btnGuardarFirma,btnSalirFirma,btnLimpiar;
    private ListView listView;
    private EditText txtCodigoProductoRecepcion;
    private DrawingView txtFirma;
    private ProgressDialog progress;
    private CheckBox chkRecepcionados;
    private RelativeLayout relbot;
    private FloatingActionButton btnTareas;
    private ImageView btnOrdenar;

    private clsBeTrans_oc_enc gBeOrdenCompra = new clsBeTrans_oc_enc();
    private clsBeTrans_re_detList pListTransRecDet = new clsBeTrans_re_detList();
    private  clsBeTrans_re_oc gBeReOC = new clsBeTrans_re_oc();
    private clsBeTrans_oc_detList pListDetalleOC = new clsBeTrans_oc_detList();
    public static clsBeConfiguracion_barra_pallet gBeConfiguracionBarraPallet =  new clsBeConfiguracion_barra_pallet();
    private static clsBeI_nav_barras_palletList lBeINavBarraPallet = new clsBeI_nav_barras_palletList();
    public static clsBeI_nav_barras_pallet BeINavBarraPallet= new clsBeI_nav_barras_pallet();
    private clsBeStock_recList pListBeStockRecPI = new clsBeStock_recList();
    public static clsBeStock_rec gBeStockRec = new clsBeStock_rec();
    private static ArrayList<clsBeTrans_oc_det> BeListDetalleOC= new ArrayList<clsBeTrans_oc_det>() ;
    public  static clsBeProducto BeProducto = new clsBeProducto();
    public static clsBeProductoList lBeProducto = new clsBeProductoList();
    private int gotop;

    private boolean Escaneo_Pallet;
    private boolean Finalizada = false, Anulada = false;
    private double Cant_Recibida_Anterior;
    private int browse, sortord;
    public String pLP="";
    private String vLP="";
    public String vCodigoBodegaBarraPallet = "";
    public String vCodigoProductoBarraPallet= "";
    public static boolean EsTransferenciaInternaWMS=false;
    private  int vIdOrdenCompra=0;
    private double vTipoDiferencia=0;
    private boolean Finalizar=false;
    //#CKFK 20211116 Agregué esta variable pora poder enviar el backorder de la OC
    private boolean backorder = false;
    private Dialog dialog;
    private Bitmap FirmaPiloto;
    private byte[] firmByte;
    private String encodedImage;

    private int Idx = -1;

    private clsBeTrans_oc_det selitem;

    private list_adapt_detalle_recepcion listdetadapter;
    private list_adapt_detalle_recepcion2 listdetadapter2;
    private list_adapt_detalle_recepcion3 listdetadpater3;

    private boolean areaprimera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        appGlobals gll;
        gll=((appGlobals) this.getApplication());
        areaprimera = gll.Mostrar_Area_En_HH;

        if (gll.TipoPantallaPicking == 3) {
            setContentView(R.layout.activity_frm_list_rec_prod3);
        } else {
            if (areaprimera) {
                setContentView(R.layout.activity_frm_list_rec_prod2);
            } else {
                setContentView(R.layout.activity_frm_list_rec_prod);
            }
        }

        super.InitBase();

        ws = new WebServiceHandler(frm_list_rec_prod.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);

        btnRegs = (Button) findViewById(R.id.btnRegs);
        btnCompletaRec = (Button)findViewById(R.id.btnCompletaRec);
        listView = (ListView)findViewById(R.id.listRec);
        chkRecepcionados =(CheckBox)findViewById(R.id.chkRecepcionados);
        btnTareas = (FloatingActionButton) findViewById(R.id.btnTareas);
        lblNombrePropietario = findViewById(R.id.lblNombrePropietario);
        lblIdPropietarioBodega = findViewById(R.id.lblIdPropietarioBodega);
        btnOrdenar = findViewById(R.id.btnOrdenar);

        gl.sortOrd = -1;

        relbot = (RelativeLayout)findViewById(R.id.relbot);

        txtCodigoProductoRecepcion = (EditText)findViewById(R.id.txtCodigoProductoRecepcion);

        browse = 0;

        setHandlers();

        ProgressDialog();

        Load();

    }

    private void Procesa_Barra_Producto(){

        int FilaActual= 0;
        boolean LongitudValida = true;
        vCodigoBodegaBarraPallet = "";
        vCodigoProductoBarraPallet= "";
        vLP="";
        pLP="";
        boolean vPalletOk = false;
        boolean TxtCantidadHasFocus = false;

        try{

            if (!txtCodigoProductoRecepcion.getText().toString().isEmpty()){

                String vStarWithParameter = "$";

                if (gBeConfiguracionBarraPallet!=null){
                    if (!gBeConfiguracionBarraPallet.IdentificadorInicio.isEmpty()){
                        vStarWithParameter = gBeConfiguracionBarraPallet.IdentificadorInicio;
                    }
                }

                if (txtCodigoProductoRecepcion.getText().toString().startsWith("$") |
                        txtCodigoProductoRecepcion.getText().toString().startsWith("(01)") |
                        txtCodigoProductoRecepcion.getText().toString().startsWith(vStarWithParameter)){

                    int vLengthBarra  = txtCodigoProductoRecepcion.getText().toString().length();

                     if(gl.gBeRecepcion.IdTipoTransaccion.equals("PICH000")
                             || gl.gBeRecepcion.IdTipoTransaccion.equals("HCOC00")
                             && vLengthBarra > 6){

                         LongitudValida = true;
                     }else{
                         LongitudValida = false;
                     }

                     if (LongitudValida){

                         Escaneo_Pallet=true;
                         gl.Escaneo_Pallet=true;

                         if (gl.gBeRecepcion.IdTipoTransaccion.equals("PICH000")){
                             pLP = txtCodigoProductoRecepcion.getText().toString().substring(4, 16);
                             gBeStockRec = stream(pListBeStockRecPI.items).where(c->c.Lic_plate.equals(pLP)).first();
                         }else{

                             int vLongitudBodegaOrigen  = 4;

                             if (gBeConfiguracionBarraPallet!=null){
                                 vLongitudBodegaOrigen = gBeConfiguracionBarraPallet.LongCodBodegaOrigen;
                             }

                             int vLongitudCodigoProducto=8;
                             if (gBeConfiguracionBarraPallet!=null){
                                 vLongitudCodigoProducto = gBeConfiguracionBarraPallet.LongCodProducto;
                             }

                             int vLongitudCodigoPallet=8;
                             if (gBeConfiguracionBarraPallet!=null){
                                 vLongitudCodigoPallet = gBeConfiguracionBarraPallet.LongLP;
                             }

                             pLP = txtCodigoProductoRecepcion.getText().toString().replace("$", "");
                             //Quitar los +1  20210924
                             vCodigoBodegaBarraPallet = pLP.substring(0, vLongitudBodegaOrigen);

                             vCodigoBodegaBarraPallet = vCodigoBodegaBarraPallet.replace("0", "");

                             vCodigoProductoBarraPallet = pLP.substring(vLongitudBodegaOrigen, vLongitudCodigoProducto+3);

                             if (gBeConfiguracionBarraPallet!=null){
                                 if (gBeConfiguracionBarraPallet.CodigoNumerico){
                                     vCodigoProductoBarraPallet =String.valueOf(Integer.parseInt(vCodigoProductoBarraPallet)); //vCodigoProductoBarraPallet.replaceFirst ("^0*", "");
                                 }else{
                                     vCodigoProductoBarraPallet = vCodigoProductoBarraPallet;
                                 }
                             }

                             int vLongitudBarraPallet=pLP.length();

                            if ((vLongitudBodegaOrigen + vLongitudCodigoProducto + vLongitudCodigoPallet)>= vLongitudBarraPallet){

                                int ln1 = vLongitudBodegaOrigen + vLongitudCodigoProducto;
                                vLP = pLP.substring(ln1,vLongitudBarraPallet);

                                if (vLP.equals("")){
                                    txtCodigoProductoRecepcion.setText("");
                                    mu.msgbox("La barra de pallet no tiene el formato correcto");
                                    return;
                                }

                            }else{
                                vLP = pLP.substring(vLongitudBodegaOrigen + vLongitudCodigoProducto, vLongitudBarraPallet - (vLongitudBodegaOrigen + vLongitudCodigoProducto));
                            }

                            if (gBeOrdenCompra.IdTipoIngresoOC == 4){
                                EsTransferenciaInternaWMS =true;
                             }

                            execws(5);

                            if (gBeStockRec!=null){
                                if(gBeStockRec.IdStockRec>0){
                                    gBeStockRec = stream(pListBeStockRecPI.items).where(c->c.Lic_plate.equals(pLP)).first();
                                }
                            }

                         }

                     }else{
                         mu.msgbox("El código de pallet : "+pLP+" no tiene la longitud válida");
                         txtCodigoProductoRecepcion.setText("");
                         txtCodigoProductoRecepcion.requestFocus();
                         return;
                     }

                }else{

                    Escaneo_Pallet = false;
                    gl.Escaneo_Pallet=false;

                    BeProducto = new clsBeProducto();

                    execws(9);

                }

            }


        }catch (Exception e){
            mu.msgbox("Procesa_Barra_Producto: "+e.getMessage());
        }

    }

    private void ValidaEstadoPallet(){

        if (gBeStockRec!=null && gBeStockRec.IdStockRec>0 ){

            mu.msgbox("El número de pallet "+ pLP+" no es válido para la recepción");
            txtCodigoProductoRecepcion.setText("");
            txtCodigoProductoRecepcion.requestFocus();
            return;

        }else{

            if (gBeStockRec.Uds_lic_plate == gBeStockRec.Cantidad){
                mu.msgbox("El pallet ya fue recibido");
                txtCodigoProductoRecepcion.setText("");
                txtCodigoProductoRecepcion.requestFocus();
                return;
            }

            if (BeProducto==null){
                execws(6);
            }

        }

        msgValidaProductoPallet("¿El pallet está completo y en buen estado?");
    }

    private void msgValidaProductoPallet(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setCancelable(false);
            dialog.setMessage( msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                   Guardar_Pallet();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(8);
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void Guardar_Pallet(){

        try{

/*            vIdOrdenCompra=0;

            if (gl.gBeRecepcion.OrdenCompraRec!=null){
                if ( gl.gBeRecepcion.OrdenCompraRec.IdOrdenCompraEnc>0){
                    vIdOrdenCompra = gl.gBeRecepcion.OrdenCompraRec.IdOrdenCompraEnc;
                }
            }
*/
            gBeStockRec.Uds_lic_plate = gBeStockRec.Cantidad;
            gBeStockRec.No_bulto = 0;

            execws(7);

        }catch (Exception e){
            mu.msgbox("Guardar_Pallet"+e.getMessage());
        }
    }

    private void Continua_Validando_Barra(){

        try{

            if (gBeOrdenCompra.ProveedorBodega.Proveedor.Codigo.trim().equals(vCodigoBodegaBarraPallet.trim()) | BeINavBarraPallet.Bodega_Destino.trim().equals(gl.gCodigoBodega)){

            List AuxList = stream(pListDetalleOC.items).select(c->c.Codigo_Producto).toList();

                Idx = AuxList.indexOf(vCodigoProductoBarraPallet);

                if (Idx>-1){
                    if (BeINavBarraPallet!=null){

                        if (!BeINavBarraPallet.Recibido){
                            if (BeINavBarraPallet.Activo){
                                gl.mode=1;
                                selitem = pListDetalleOC.items.get(Idx);
                                gl.gselitem = selitem;
                                gl.gEscaneo_Pallet = true;

                                gl.CodigoRecepcion = selitem.Producto.Codigo_barra;
                                gl.gpListDetalleOC.items = pListDetalleOC.items;

                                browse=1;
                                startActivity(new Intent(this, frm_recepcion_datos.class));

                            }else{
                                mu.msgbox("El código de pallet : "+ BeINavBarraPallet.Codigo_barra +" está inactivo: "+BeINavBarraPallet.Codigo_barra+" valide tabla de interface");
                                txtCodigoProductoRecepcion.setText("");
                                txtCodigoProductoRecepcion.requestFocus();
                                return;
                            }
                        }else{
                            mu.msgbox("El código de pallet: "+BeINavBarraPallet.Codigo_barra+" ya fue recibido con fecha: "+BeINavBarraPallet.Fecha_Agregado);
                            txtCodigoProductoRecepcion.setText("");
                            txtCodigoProductoRecepcion.requestFocus();
                            return;
                        }

                    }else{
                        mu.msgbox("El código de pallet: "+pLP+" no existe en el listado de barras válidas para ingreso");
                        txtCodigoProductoRecepcion.setText("");
                        txtCodigoProductoRecepcion.requestFocus();
                        return;
                    }
                }else{
                    mu.msgbox("El producto: "+vCodigoProductoBarraPallet+" no coincide con ningún artículo del documento");
                    txtCodigoProductoRecepcion.setText("");
                    txtCodigoProductoRecepcion.requestFocus();
                    return;
                }
            }else{
                mu.msgbox("El almacen emisor: "+vCodigoBodegaBarraPallet+" no coincide con el proveedor del documento: "+gBeOrdenCompra.ProveedorBodega.Proveedor.Codigo);
                txtCodigoProductoRecepcion.setText("");
                txtCodigoProductoRecepcion.requestFocus();
                return;
            }

        }catch (Exception e){
            mu.msgbox("Continua_Validando_Barra"+e.getMessage());
        }
    }

    public void ProgressDialog(){
        progress=new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void Load(){

        try{

            progress.setMessage("Inicializando valores");

            lblTituloForma.setText("");

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if (gl.tipoTarea==1){

                if (gl.gBeRecepcion!=null){

                    pListTransRecDet = gl.gBeRecepcion.Detalle;

                    gBeOrdenCompra = new clsBeTrans_oc_enc();
                    gBeReOC = new clsBeTrans_re_oc();
                    pListDetalleOC =new clsBeTrans_oc_detList();

                    gBeOrdenCompra = gl.gBeRecepcion.OrdenCompraRec.OC;
                    gl.gBeOrdenCompra = gBeOrdenCompra;
                    gl.PreguntarEnBackOrder= gBeOrdenCompra.TipoIngreso.Preguntar_En_BackOrder;

                    vIdOrdenCompra=0;

                    if (gl.gBeRecepcion.OrdenCompraRec!=null){
                        if ( gl.gBeRecepcion.OrdenCompraRec.IdOrdenCompraEnc>0){
                            vIdOrdenCompra = gl.gBeRecepcion.OrdenCompraRec.IdOrdenCompraEnc;
                        }
                    }

                    gBeReOC = gl.gBeRecepcion.OrdenCompraRec;
                    chkRecepcionados.setChecked(false);

                    pListDetalleOC = gl.gBeRecepcion.OrdenCompraRec.OC.DetalleOC;
                    //GT17062021 Se reordena la lista por num_linea
                    Collections.sort(pListDetalleOC.items,new OrdenarItems());

                    gl.gpListDetalleOC = pListDetalleOC;

                    if (frm_detalle_ingresos.gBeConfiguracionBarraPallet!=null){
                        if (frm_detalle_ingresos.gBeConfiguracionBarraPallet.IdConfiguracionPallet>0){
                            gBeConfiguracionBarraPallet = frm_detalle_ingresos.gBeConfiguracionBarraPallet;
                        }
                    }

                    if(gBeOrdenCompra.No_Documento!=null & gBeOrdenCompra.Referencia!=null){
                        lblTituloForma.setText("Recepción: "+gl.gBeRecepcion.IdRecepcionEnc+" - Documento: "+ gBeOrdenCompra.No_Documento+ " - "+gBeOrdenCompra.Referencia);
                    } else {
                        lblTituloForma.setText("Recepción: "+gl.gBeRecepcion.IdRecepcionEnc+" - Documento: "+ gBeOrdenCompra.No_Documento);
                    }

                    if(gBeOrdenCompra.IdEstadoOC != 3){

                        gBeOrdenCompra.EstadoOC.IdEstadoOC = 3;
                        gBeOrdenCompra.IdEstadoOC = 3;
                        gBeOrdenCompra.Fecha_Recepcion = du.getActDate()+"";
                        gBeOrdenCompra.Hora_Inicio_Recepcion =  du.getActDate()+"";
                        gBeOrdenCompra.User_Mod = gl.IdOperador+"";
                        gBeOrdenCompra.Fec_Mod =  du.getActDate()+"";

                    }

                    progress.setMessage("Inicializando Oc");
                    execws(1);

                }

            }else{
                //#EJC20220303:En algunas ocasiones, se pierde el IdtipoTarea...
                //mu.msgbox("Falta programar");
                progress.setMessage("Inicializando Oc");
                execws(1);
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+e.getMessage());
        }

    }

    private void setHandlers() {

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_oc_det sitem = (clsBeTrans_oc_det) lvObj;
                    //selitem = pListDetalleOC.items.get(position);

                    selitem  = stream(pListDetalleOC.items)
                            .where(c -> c.No_Linea == sitem.No_Linea)
                            .first();

                    selid = sitem.No_Linea;
                    selidx = position;

                    //#GT09032022: si tiene mostrar_area cealsa
                    if (gl.TipoPantallaPicking == 3) {
                        listdetadpater3.setSelectedIndex(position);
                    } else {
                        if (areaprimera) {
                            listdetadapter2.setSelectedIndex(position);
                        } else {
                            listdetadapter.setSelectedIndex(position);
                        }
                    }

                    procesar_registro();
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_oc_det sitem = (clsBeTrans_oc_det) lvObj;
                    //selitem = pListDetalleOC.items.get(position);

                    selitem  = stream(pListDetalleOC.items)
                            .where(c -> c.No_Linea == sitem.No_Linea)
                            .first();

                    selid = sitem.No_Linea;
                    selidx = position;

                    //#GT09032022: si tiene mostrar_area cealsa
                    if (gl.TipoPantallaPicking == 3) {
                        listdetadpater3.setSelectedIndex(position);
                    } else {
                        if (areaprimera) {
                            listdetadapter2.setSelectedIndex(position);
                        } else {
                            listdetadapter.setSelectedIndex(position);
                        }
                    }

                    msgIngresaDetalle("Quiere ver el detalle del código: " +selitem.Codigo_Producto);

                    return true;
                }

            });

            txtCodigoProductoRecepcion.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                        if (!txtCodigoProductoRecepcion.getText().toString().isEmpty()){
                            Procesa_Barra_Producto();
                        }

                    }

                    return false;
                }
            });

            chkRecepcionados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (chkRecepcionados.isChecked()==true) {
                        //pListDetalleOC.items = stream(pListDetalleOC.items).where(c->c.Cantidad_recibida>0).toList();
                        Lista_Detalle_Documento_Ingreso();
                    }else{
                        Lista_Detalle_Documento_Ingreso();
                    }
                }
            });

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private void doExit(){
        try{

            //LimpiaValores();
            gl.CodigoRecepcion ="";
            gl.Carga_Producto_x_Pallet = false;
            gl.gFechaVenceAnterior = "";
            gl.gLoteAnterior ="";
            gl.Escaneo_Pallet=false;
            btnTareas.setVisibility(View.VISIBLE);
            relbot.setVisibility(View.VISIBLE);
            super.finish();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void ValidaProductoForRece(){
        List AuxList = null;
        try{

            if (lBeProducto!=null){

                if(lBeProducto.items!=null){

                    if (lBeProducto.items.size()>1){

                        List TempList;
                        int j = 0;

                        for (clsBeProducto BeProd: lBeProducto.items){

                            if (j==0){
                                AuxList = stream(pListDetalleOC.items)
                                        .where(c -> c.Codigo_Producto.equals(BeProd.Codigo))
                                        .toList();
                            }else{

                                TempList = stream(pListDetalleOC.items)
                                        .where(c -> c.Codigo_Producto.equals(BeProd.Codigo))
                                        .toList();

                                if (TempList.size()>0) {
                                    for (int i = 0; i <TempList.size(); i++) {
                                        AuxList.add(AuxList.size(),TempList.get(i));
                                    }
                                }
                            }
                            j+=1;
                        }

                        if (AuxList.size()==1){

                            clsBeTrans_oc_detList pListaAux = new clsBeTrans_oc_detList();
                            clsBeTrans_oc_det vOrdenCompraDet = new clsBeTrans_oc_det();
                            pListaAux.items = AuxList;

                            String finalSelProd1  = pListaAux.items.get(0).Codigo_Producto;

                            vOrdenCompraDet  = stream(pListDetalleOC.items)
                                    .where(c -> c.getCodigo_Producto().equals(finalSelProd1))
                                    .first();

                            if (vOrdenCompraDet != null) {

                                selitem = vOrdenCompraDet;
                                gl.gselitem = selitem;

                                gl.CodigoRecepcion = selitem.Producto.Codigo_barra;
                                gl.gpListDetalleOC.items = pListDetalleOC.items;

                                gl.mode =1;
                                browse=1;

                                startActivity(new Intent(this, frm_recepcion_datos.class));

                            } else {
                                txtCodigoProductoRecepcion.setText("");
                                mu.msgbox(String.format("No existe el producto %s en esta Recepción",finalSelProd1));
                            }

                        }else if (AuxList.size() > 1){

                            //pListDetalleOC.items= gl.gpListDetalleOC.items;
                            pListDetalleOC.items= AuxList;
                            Lista_Detalle_Documento_Ingreso();
                            ordenar();

                        } else if (AuxList.size() == 0) {
                            txtCodigoProductoRecepcion.setText("");
                            toast("No existe el producto en esta Recepción.");
                        }

                    }else{

                        BeProducto = lBeProducto.items.get(0);

                        if (BeProducto!=null){

                            AuxList = stream(pListDetalleOC.items).select(c->c.Codigo_Producto).toList();

                            txtCodigoProductoRecepcion.setText(BeProducto.Codigo);

                            Idx = AuxList.indexOf(txtCodigoProductoRecepcion.getText().toString());

                            if (Idx>-1){

                                if (gBeStockRec!=null){
                                    if (gBeStockRec.IdStockRec>0){
                                        gl.Carga_Producto_x_Pallet=true;
                                    }else{
                                        gl.Carga_Producto_x_Pallet=false;
                                    }
                                }else{
                                    gl.Carga_Producto_x_Pallet= false;
                                }

                                selitem = pListDetalleOC.items.get(Idx);
                                gl.gselitem = selitem;

                                gl.CodigoRecepcion = selitem.Producto.Codigo_barra;
                                gl.gpListDetalleOC.items = pListDetalleOC.items;

                                gl.mode =1;
                                browse=1;

                                startActivity(new Intent(this, frm_recepcion_datos.class));

                            }else{

                                AuxList = stream(pListDetalleOC.items)
                                        .where(c->c.Codigo_Producto.equals( BeProducto.Codigo)).toList();

                                txtCodigoProductoRecepcion.setText(BeProducto.Codigo);



                            }

                        }else{
                            mu.msgbox("El código de producto no es válido para la recepción");
                            txtCodigoProductoRecepcion.setText("");
                            txtCodigoProductoRecepcion.requestFocus();
                            return;
                        }

                    }
                }
            }

        }catch (Exception e){
         mu.msgbox("ValidaProductoForRece:"+e.getMessage());
        }
    }

    private void procesar_registro(){

        try {


            if (Finalizada & Anulada){
                doExit();
            }else{

                gl.gEscaneo_Pallet = Escaneo_Pallet;
                gl.gselitem = selitem;

                gl.CodigoRecepcion = selitem.Producto.Codigo_barra;
                gl.gpListDetalleOC.items = pListDetalleOC.items;

                gl.mode = 1;

                browse=1;
                startActivity(new Intent(this, frm_recepcion_datos.class));

            }
        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private void procesar_registro_detalle(){

        try {


            if (Finalizada & Anulada){
                doExit();
            }else{

                if (selitem.Cantidad_recibida>0){

                    gl.gEscaneo_Pallet = Escaneo_Pallet;
                    gl.gselitem = selitem;

                    gl.CodigoRecepcion = selitem.Producto.Codigo_barra;
                    gl.gpListDetalleOC.items = pListDetalleOC.items;

                    browse=1;
                    startActivity(new Intent(this, frm_list_rec_prod_detalle.class));

                }else{
                    mu.msgbox("No existen recepciones de ese producto");
                }

            }
        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private boolean Recepcion_Completa(){
        boolean Completa=false;
        vTipoDiferencia=0;
        double Cantidad_recibida,Cantidad;

        try{

            //#CKFK 20211116 Inicializo la variable de backorder de la OC
            backorder = false;

            progress.setMessage("Validando estado de recepción");

            if (gBeOrdenCompra.DetalleOC.items!=null){

                for (clsBeTrans_oc_det Obj: gBeOrdenCompra.DetalleOC.items) {

                    Cantidad_recibida = Obj.Cantidad_recibida;
                    Cantidad = Obj.Cantidad;

                    if (Cantidad_recibida>=0){

                        vTipoDiferencia = mu.round(Cantidad_recibida-Cantidad,7);

                        if (vTipoDiferencia<0){

                            //#CKFK 20211116 Coloco la variable en true si la recepción está incompleta
                            backorder = true;
                            btnCompletaRec.setText("DIF - (NEG)");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FFA5A0"));
                            progress.cancel();
                            return false;

                        }else if(vTipoDiferencia>0){

                            btnCompletaRec.setText(" DIF - (POS)");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FF0399D5"));
                            progress.cancel();
                            return false;

                        }else if (vTipoDiferencia==0) {

                            btnCompletaRec.setText("COMPLETA");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FF99CC00"));

                            Completa= true;
                        }

                    }else{

                        btnCompletaRec.setText("DIF - (NEG)");
                        btnCompletaRec.setBackgroundColor(Color.parseColor("#FFA5A0"));
                        progress.cancel();
                        return false;
                    }

                }

            }

            progress.cancel();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

        return  Completa;

    }

    private void Lista_Detalle_Documento_Ingreso(){

        clsBeTrans_oc_det vItem;
        BeListDetalleOC.clear();

        try{

            progress.setMessage("Cargando detalle de documento de ingreso.");

            if(pListDetalleOC.items!=null){

                for (int i = pListDetalleOC.items.size()-1; i>=0; i--) {

                    vItem = new clsBeTrans_oc_det();

                    if (chkRecepcionados.isChecked()==true){

                        if (pListDetalleOC.items.get(i).Cantidad_recibida!=0){

                            vItem.No_Linea = pListDetalleOC.items.get(i).No_Linea;
                            vItem.Producto.Codigo = pListDetalleOC.items.get(i).Producto.Codigo;
                            vItem.Producto.Nombre = pListDetalleOC.items.get(i).Producto.Nombre;
                            vItem.Presentacion.Nombre = pListDetalleOC.items.get(i).Presentacion.Nombre;
                            vItem.UnidadMedida.Nombre = pListDetalleOC.items.get(i).UnidadMedida.Nombre;
                            vItem.Cantidad = pListDetalleOC.items.get(i).Cantidad;
                            vItem.Cantidad_recibida = pListDetalleOC.items.get(i).Cantidad_recibida;
                            vItem.Costo = pListDetalleOC.items.get(i).Costo;
                            vItem.FactorPresentacion = pListDetalleOC.items.get(i).FactorPresentacion;
                            vItem.IdOrdenCompraDet = pListDetalleOC.items.get(i).IdOrdenCompraDet;
                            vItem.IdOrdenCompraEnc = pListDetalleOC.items.get(i).IdOrdenCompraEnc;
                            vItem.IdPropietarioBodega = pListDetalleOC.items.get(i).IdPropietarioBodega;
                            vItem.Nombre_Propietario = pListDetalleOC.items.get(i).Nombre_Propietario;
                            vItem.Nombre_Embarcador = pListDetalleOC.items.get(i).Nombre_Embarcador;
                            vItem.Nombre_Clasificacion = pListDetalleOC.items.get(i).Producto.Clasificacion.Nombre;

                            BeListDetalleOC.add(vItem);

                        }

                    }else{

                            vItem.No_Linea = pListDetalleOC.items.get(i).No_Linea;
                            vItem.Producto.Codigo = pListDetalleOC.items.get(i).Producto.Codigo;
                            vItem.Producto.Nombre = pListDetalleOC.items.get(i).Producto.Nombre;
                            vItem.Presentacion.Nombre = pListDetalleOC.items.get(i).Presentacion.Nombre;
                            vItem.UnidadMedida.Nombre = pListDetalleOC.items.get(i).UnidadMedida.Nombre;
                            vItem.Cantidad = pListDetalleOC.items.get(i).Cantidad;
                            vItem.Cantidad_recibida = pListDetalleOC.items.get(i).Cantidad_recibida;
                            vItem.Costo = pListDetalleOC.items.get(i).Costo;
                            vItem.FactorPresentacion = pListDetalleOC.items.get(i).FactorPresentacion;
                            vItem.IdOrdenCompraDet = pListDetalleOC.items.get(i).IdOrdenCompraDet;
                            vItem.IdOrdenCompraEnc = pListDetalleOC.items.get(i).IdOrdenCompraEnc;
                            vItem.IdPropietarioBodega = pListDetalleOC.items.get(i).IdPropietarioBodega;
                            vItem.Nombre_Propietario = pListDetalleOC.items.get(i).Nombre_Propietario;
                            vItem.Nombre_Embarcador = pListDetalleOC.items.get(i).Nombre_Embarcador;
                            vItem.Nombre_Clasificacion = pListDetalleOC.items.get(i).Producto.Clasificacion.Nombre;

                            BeListDetalleOC.add(vItem);

                    }

                }

                btnRegs.setText("Registros: "+pListDetalleOC.items.size());
            }

            Collections.sort(BeListDetalleOC, new OrdenarItems());

            //#EJC20210318: Obtener el tipo de documento de ingreso para saber si es una poliza consolidada o no.
            boolean es_poliza_consolidada = false;
            clsBeTrans_oc_ti TipoIngreso=new clsBeTrans_oc_ti();
            TipoIngreso = gBeOrdenCompra.getTipoIngreso();
            if(TipoIngreso!=null) es_poliza_consolidada = TipoIngreso.Es_Poliza_Consolidada;

            if (gl.TipoPantallaPicking != 3) {
                if (!es_poliza_consolidada) {
                    lblIdPropietarioBodega.setVisibility(View.GONE);
                    lblNombrePropietario.setVisibility(View.GONE);
                }
            }

            if (gl.TipoPantallaPicking == 3) {
                listdetadpater3 = new list_adapt_detalle_recepcion3(this, BeListDetalleOC, es_poliza_consolidada, gl.gCantDecCalculo);
                listView.setAdapter(listdetadpater3);
            } else {

                if (areaprimera) {
                    listdetadapter2 = new list_adapt_detalle_recepcion2(this, BeListDetalleOC, es_poliza_consolidada, gl.gCantDecCalculo);
                    listView.setAdapter(listdetadapter2);
                } else {
                    listdetadapter = new list_adapt_detalle_recepcion(this, BeListDetalleOC, es_poliza_consolidada, gl.gCantDecCalculo);
                    listView.setAdapter(listdetadapter);
                }
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }

    }

    public void BotonFinalizarRec(View view){

        try{

           btnTareas.setVisibility(View.INVISIBLE);
           relbot.setVisibility(View.INVISIBLE);
           execws(10);

        }catch (Exception e){
            mu.msgbox("BotonFinalizarRec"+e.getMessage());
            btnTareas.setVisibility(View.VISIBLE);
            relbot.setVisibility(View.VISIBLE);
        }
    }

    public void BotonDetalle(View view){

        try{

            browse=2;
            startActivity(new Intent(this, frm_recepcion_datos.class));

        }catch (Exception e){
            mu.msgbox("BotonDetalle:"+e.getMessage());
        }
    }

    private void msgIngresaDetalle(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿"+msg+"?");

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    procesar_registro_detalle();
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

    private void Finalizar_Recepcion(){

        try{

            progress.show();
            progress.setMessage("Finalizando recepción");

            gl.gBeRecepcion.Firma_piloto = encodedImage;//Byte.parseByte(FirmaPiloto.toString());

            execws(12);

        }catch (Exception e){
            btnTareas.setVisibility(View.VISIBLE);
            relbot.setVisibility(View.VISIBLE);
            mu.msgbox("Finalizar_Recepcion:"+e.getMessage());
        }
    }

    public void GuardarFirma(View view ){
        Finalizar_Recepcion();
    }

    public class OrdenarItems implements Comparator<clsBeTrans_oc_det> {

        public int compare(clsBeTrans_oc_det left,clsBeTrans_oc_det rigth){
            return left.No_Linea-rigth.No_Linea;
            //return left.Nombre.compareTo(rigth.Nombre);
        }

    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){

            try{

                switch (ws.callback) {

                    case 1:
                        //callMethod("Iniciar_Recepcion_OC","oBeTrans_oc_enc",gBeOrdenCompra);
                        //GT0712021:Se envian por separado los 2 parametros requeridos, y no una entidad
                        callMethod("Iniciar_Recepcion_OC",
                                   "pIdOrdenCompraEnc",gBeOrdenCompra.IdOrdenCompraEnc,
                                   "pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 2:
                        callMethod("Actualizar_Estado_Recepcion",
                                   "pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                   "Estado","Pendiente");
                        break;
                    case 3:
                        callMethod("Get_Single_BeTrans_OC_Estado",
                                   "pBeTrans_oc_estado",gBeOrdenCompra.EstadoOC);
                        break;
                    case 4:
                        callMethod("Get_Banderas_Recepcion",
                                   "pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                   "pFinalizada",Finalizada,
                                   "pAnulada",Anulada);
                        break;
                    case 5:
                       callMethod("Get_All_Pallet_Ingreso_By_Barra","pCodigoBarraPallet",pLP,
                               "pIdBodega",gl.IdBodega,
                               "BeProducto",BeProducto);
                        break;
                    case 6:
                        callMethod("Get_BeProducto_By_LP_For_HH",
                                   "pLic_Plate",pLP,
                                   "IdRecepcionEnc",gl.gIdRecepcionEnc,
                                   "pBeStockRec",gBeStockRec);
                        break;
                    case 7:
                        callMethod("Finalizar_Recepcion_Parcial",
                                   "pRecEnc",gl.gBeRecepcion,
                                   "pIdOrdenCompraEnc",vIdOrdenCompra,
                                   "pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                   "pIdEmpresa",gl.IdEmpresa,
                                   "pIdBodega",gl.IdBodega,
                                   "pIdUsuario",gl.IdOperador,
                                   "pBeStockRec",gBeStockRec);
                        break;
                    case 8:
                        callMethod("Get_BeProducto_By_LP_For_HH",
                                   "pLic_Plate",pLP,
                                   "IdRecepcionEnc",gl.gIdRecepcionEnc,
                                   "pBeStockRec",gBeStockRec);
                        break;
                    case 9:
                       /* callMethod("Get_BeProducto_By_Codigo_For_HH",
                                   "pCodigo",txtCodigoProductoRecepcion.getText().toString(),
                                   "IdBodega",gl.IdBodega);*/
                        callMethod("Get_List_Product_By_CodigoBarra_By_OrdenCompraEnc",
                                   "pCodigo",txtCodigoProductoRecepcion.getText().toString(),
                                   "IdBodega",gl.IdBodega,
                                   "IdOrdenCompraEnc",gl.gBeOrdenCompra.IdOrdenCompraEnc);
                        break;
                    case 10:
                        callMethod("Get_Banderas_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"pFinalizada",Finalizada,"pAnulada",Anulada);
                        break;
                    case 11:
                        callMethod("Get_Detalle_By_IdRecepcionEnc","pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case  12:
                        callMethod("Actualizar_Estado_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"Estado","Procesado");
                        break;
                    case 13:
                        callMethod("Guarda_Firma_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"Firma_piloto",gl.gBeRecepcion.Firma_piloto);
                        break;
                    case 14:
                        callMethod("Finalizar_Recepcion",
                                            "pRecEnc",gl.gBeRecepcion,
                                            "backOrder",backorder,
                                            "pIdOrdenCompraEnc",vIdOrdenCompra,
                                            "pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                            "pIdEmpresa", gl.IdEmpresa,
                                            "pIdBodega",gl.IdBodega,
                                            "pIdUsuario",gl.IdOperador,
                                            "pListObjDetR",pListTransRecDet.items,
                                            "pHabilitarStock",gl.gBeRecepcion.Habilitar_Stock);
                        break;
                    case 15:
                        //#CKFK20220524 Agregué esta funcion para obtener el detalle de la OC
                        callMethod("Get_Detalle_OC_By_IdOrdenCompraEnc_HH","pIdOrdenCompraEnc",vIdOrdenCompra);
                        break;
                }

            }catch (Exception e){
                btnTareas.setVisibility(View.VISIBLE);
                relbot.setVisibility(View.VISIBLE);
                mu.msgbox(e.getClass()+e.getMessage());
            }

        }

    }

        @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {

            try {

                if (throwing) throw new Exception(errmsg);

                switch (ws.callback) {
                    case 1:
                        progress.setMessage("Actualizando estado de oc");
                        execws(2);
                        break;
                    case 2:
                        progress.setMessage("Obteniendo valores de OC");
                        execws(3);
                        break;
                    case 3:
                        Lista_Detalle_Documento_Ingreso();
                        Recepcion_Completa();
                        execws(4);
                        break;
                    case 4:
                        processBanderasRecep();
                        break;
                    case 5:
                        processPalletIngreso();
                        break;
                    case 6:
                        processProductoByLP();
                        break;
                    case 7:
                        mu.msgbox("Pallet procesado correctamente");
                        txtCodigoProductoRecepcion.setText("");
                        txtCodigoProductoRecepcion.requestFocus();
                        break;
                    case 8:
                        procesProductoByLPNo();
                        break;
                    case 9:
                        processGetProductoByCodigo();
                        break;
                    case 10:
                        processBanderasRecepFinaliza();
                        break;
                    case 11:
                        processGetDetalleByIdRepcionEnc();
                        break;
                    case 12:
                        execws(13);
                        break;
                    case 13:
                        execws(14);
                        break;
                    case 14:
                        //doExit();
                        process_finalizar_recepcion();
                        break;
                    case 15:
                        process_actualizar_oc();
                        break;
                }

            } catch (Exception e) {
                progress.cancel();
                //msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
                msgboxErrorOnWS2(" wsCallBack: case(" + ws.callback + ") " + e.getMessage());

            }

    }

    private void processBanderasRecep(){

        try {

            Finalizada =(Boolean) xobj.getSingle("pFinalizada",Boolean.class);
            Anulada =(Boolean) xobj.getSingle("pAnulada",Boolean.class);

            if (Finalizada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " ya fue finalizada");
            }

            if (Anulada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " fue anulada");
            }

            if (Finalizada & Anulada){
                gl.CodigoRecepcion ="";
                gl.Carga_Producto_x_Pallet = false;
                gl.gFechaVenceAnterior = "";
                gl.gLoteAnterior ="";
                gl.Escaneo_Pallet=false;
                super.finish();
            }



        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processPalletIngreso(){

        try{

            lBeINavBarraPallet = xobj.getresult(clsBeI_nav_barras_palletList.class,"Get_All_Pallet_Ingreso_By_Barra");

            BeProducto = xobj.getresultSingle(clsBeProducto.class,"BeProducto");

            ValidaEstadoPallet();

            if (lBeINavBarraPallet!=null){
                if (lBeINavBarraPallet.items!=null){

                    if (lBeINavBarraPallet.items.size()==1){
                        BeINavBarraPallet = lBeINavBarraPallet.items.get(0);
                    }else {
                        if(gBeOrdenCompra.IdTipoIngresoOC ==4){
                            BeINavBarraPallet = stream(lBeINavBarraPallet.items).where(c->c.Bodega_Origen.equals(gBeOrdenCompra.ProveedorBodega.Proveedor.Codigo) && c.Bodega_Destino.equals(gl. gCodigoBodega)).first();
                        }else{
                            mu.msgbox("Excepción no controlada por barra de pallet en tipo de documento, reporte esto a desarrollo (Desarrollo, en teoría, no debería ocurrir):"+gBeOrdenCompra.IdTipoIngresoOC);
                        return;
                        }
                    }

                }else{
                    mu.msgbox("El código de pallet : "+ pLP+" no existe en el listado de barras válidas para ingreso.");
                    return;
                }
            }else{
                mu.msgbox("El código de pallet : "+ pLP+" no existe en el listado de barras válidas para ingreso.");
                return;
            }

            Continua_Validando_Barra();

        }catch (Exception e){
            mu.msgbox("processPalletIngreso"+e.getMessage());
        }
    }

    private void processProductoByLP(){

        try{

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_LP_For_HH");

            msgValidaProductoPallet("¿El pallet está completo y en buen estado?");


        }catch (Exception e){
            mu.msgbox("processProductoByLP");
        }
    }

    private void procesProductoByLPNo(){
        try{
            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_LP_For_HH");
            ValidaProductoForRece();
        }catch (Exception e){
            mu.msgbox("procesProductoByLPNo:"+e.getMessage());
        }
    }

    private void processGetProductoByCodigo(){

        try{

          // BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");
           lBeProducto = xobj.get(clsBeProductoList.class,"Get_List_Product_By_CodigoBarra_By_OrdenCompraEnc");

           ValidaProductoForRece();

        }catch (Exception e){
            mu.msgbox("processGetProductoByCodigo");
        }
    }

    private void processBanderasRecepFinaliza(){

        try {

            progress.setMessage("Obteniendo banderas de recepción");

            Finalizada =(Boolean) xobj.getSingle("pFinalizada",Boolean.class);
            Anulada =(Boolean) xobj.getSingle("pAnulada",Boolean.class);

            if (Finalizada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " ya fue finalizada");
            }

            if (Anulada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " fue anulada");
            }

            if (Finalizada | Anulada){
                onResume();
                btnTareas.setVisibility(View.VISIBLE);
                relbot.setVisibility(View.VISIBLE);
            }else{
                pListTransRecDet = new clsBeTrans_re_detList();
                execws(11);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
            btnTareas.setVisibility(View.VISIBLE);
            relbot.setVisibility(View.VISIBLE);
        }

    }

    private void process_finalizar_recepcion(){

        try{

            String Resultado;

            progress.show();
            progress.setMessage("Finalizando proceso de guardar recepción");

            //#EJC20210321_1223:Validar si no se obtuvo error en el procesamiento.
            if(!xobj.ws.xmlresult.contains("CustomError")){

                Resultado = xobj.getresult(String.class,"Finalizar_Recepcion");

                if (Resultado!=null){
                    doExit();
                }else{
                    progress.cancel();
                    mu.msgbox("No se pudo finalizar la recepción");
                }

            }else{
                progress.cancel();
                btnTareas.setVisibility(View.VISIBLE);
                relbot.setVisibility(View.VISIBLE);
                Resultado =xobj.ws.xmlresult.replace("<DocumentElement>  <CustomError>    <Error>","").replace("</Error>  </CustomError></DocumentElement>","");
                msgboxErrorOnWS2("No se pudo finalizar la recepción: " + Resultado);
            }

        }catch (Exception e){
            progress.hide();
            mu.msgbox("process_finalizar_recepcion:"+e.getMessage());
        }
    }

    private void process_actualizar_oc(){
        try{

            gl.gpListDetalleOC = xobj.getresult(clsBeTrans_oc_detList.class,"Get_Detalle_OC_By_IdOrdenCompraEnc_HH");

            pListDetalleOC.items = gl.gpListDetalleOC.items;

            Lista_Detalle_Documento_Ingreso();
            ordenar();
            progress.cancel();

            if(Recepcion_Completa()){
                msgPreguntaFinalizar("Recepción completa. ¿Finalizar?");
            }
        }catch (Exception e){
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
            progress.cancel();
        }
    }

    public void msgboxErrorOnWS2(String msg) {
        try{

            ExDialog dialog = new ExDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(msg);

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Finalizar = false;
                    btnTareas.setVisibility(View.VISIBLE);
                    relbot.setVisibility(View.VISIBLE);
                }
            });

            dialog.show();
        }catch (Exception e){
            Log.println(Log.ERROR,"msg",e.getMessage());
            //addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void processGetDetalleByIdRepcionEnc(){
        try{

            pListTransRecDet = xobj.getresult(clsBeTrans_re_detList.class,"Get_Detalle_By_IdRecepcionEnc");

            if (pListTransRecDet!=null){
                if (pListTransRecDet.items!=null){

                    if (gl.TipoOpcion ==1){
                        if (!Recepcion_Completa()){

                            if (vTipoDiferencia!=0){
                                pListDetalleOC.items =stream(gBeOrdenCompra.DetalleOC.items).where(c->c.Cantidad-c.Cantidad_recibida!=0).toList();
                                Lista_Detalle_Documento_Ingreso();
                            }

                            if (vTipoDiferencia<0){

                                msgValidaFaltantes("La recepción aún tiene faltante de producto. ¿Finalizar de todas formas?");

                            }else if(vTipoDiferencia >0){

                                msgValidaSobrantes("La recepción tiene excedente de producto.¿finalizar de todas formas?");

                            }else if(vTipoDiferencia==0|vTipoDiferencia==0.0){

                                msgValidaFaltantes("La recepción aún tiene faltante de producto. ¿Finalizar de todas formas?");

                            }

                        }else{
                            Finalizar = true;
                            Termina_Finalizacion_Recepcion();
                        }
                    }
                }
            }

        }catch (Exception e){
            mu.msgbox("processGetDetalleByIdRepcionEnc"+e.getMessage());
        }
    }

    private void msgValidaFaltantes(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if  (gl.PreguntarEnBackOrder){
                        msgDocIngresoBackOrder("¿Dejar el documento en BackOrder?");
                    }else{
                         Finalizar = true;
                         Termina_Finalizacion_Recepcion();
                    }
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Finalizar = false;
                    btnTareas.setVisibility(View.VISIBLE);
                    relbot.setVisibility(View.VISIBLE);
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgValidaSobrantes(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Finalizar = true;
                    Termina_Finalizacion_Recepcion();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Finalizar = false;
                    btnTareas.setVisibility(View.VISIBLE);
                    relbot.setVisibility(View.VISIBLE);
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgDocIngresoBackOrder(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    backorder = true;
                    Finalizar = true;
                    Termina_Finalizacion_Recepcion();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    backorder = false;
                    Finalizar = true;
                    Termina_Finalizacion_Recepcion();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgPreguntaFinalizar(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    btnTareas.setVisibility(View.INVISIBLE);
                    relbot.setVisibility(View.INVISIBLE);
                    execws(11);
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

    private void Termina_Finalizacion_Recepcion(){

        try{

            if (Finalizar){

                if (gl.gBeRecepcion.Firma_piloto.equals("")){

                    MuestraPantallaFirma(this);

                }else{
                    Finalizar_Recepcion();
                }

            }

        }catch (Exception e){
            mu.msgbox("Termina_Finalizacion_Recepcion"+e.getMessage());
            btnTareas.setVisibility(View.VISIBLE);
            relbot.setVisibility(View.VISIBLE);
        }
    }

    public void showItemMenu(View view) {
        final AlertDialog Dialog;
        final String[] selitems = {"Codigo A-Z","Codigo Z-A",
                "Producto A-Z","Producto Z-A" ,
                "Cantidad Recibida A-Z","Cantidad Recibida Z-A"};

        AlertDialog.Builder menudlg = new AlertDialog.Builder(this);
        menudlg.setTitle("Ordenar por:");

        menudlg.setItems(selitems , new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                gl.sortOrd = item;
                ordenar();
                listSortedItems();
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

    private void listSortedItems() {
        try {
            boolean es_poliza_consolidada = false;
            clsBeTrans_oc_ti TipoIngreso=new clsBeTrans_oc_ti();
            TipoIngreso = gBeOrdenCompra.getTipoIngreso();
            if(TipoIngreso!=null) es_poliza_consolidada = TipoIngreso.Es_Poliza_Consolidada;

            if (gl.TipoPantallaPicking != 3) {
                if (!es_poliza_consolidada) {
                    lblIdPropietarioBodega.setVisibility(View.GONE);
                    lblNombrePropietario.setVisibility(View.GONE);
                }
            }


            if (gl.TipoPantallaPicking == 3) {
                listdetadpater3 = new list_adapt_detalle_recepcion3(this, BeListDetalleOC, es_poliza_consolidada, gl.gCantDecCalculo);
                listView.setAdapter(listdetadpater3);
            } else {
                if (areaprimera) {
                    listdetadapter2 = new list_adapt_detalle_recepcion2(this, BeListDetalleOC, es_poliza_consolidada, gl.gCantDecCalculo);
                    listView.setAdapter(listdetadapter2);
                } else {
                    listdetadapter = new list_adapt_detalle_recepcion(this, BeListDetalleOC, es_poliza_consolidada, gl.gCantDecCalculo);
                    listView.setAdapter(listdetadapter);
                }
            }
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void ordenar() {
        switch (gl.sortOrd) {
            case 0:
                sortord=1;
                Collections.sort(BeListDetalleOC, new frm_list_rec_prod.Sort_Codigo());break;
            case 1:
                sortord=-1;
                Collections.sort(BeListDetalleOC, new frm_list_rec_prod.Sort_Codigo());break;
            case 2:
                sortord=1;
                Collections.sort(BeListDetalleOC, new frm_list_rec_prod.Sort_Producto());break;
            case 3:
                sortord=-1;
                Collections.sort(BeListDetalleOC, new frm_list_rec_prod.Sort_Producto());break;
            case 4:
                sortord=1;
                Collections.sort(BeListDetalleOC, new frm_list_rec_prod.Sort_Cantidad());break;
            case 5:
                sortord=-1;
                Collections.sort(BeListDetalleOC, new frm_list_rec_prod.Sort_Cantidad());break;
        }
    }

    class Sort_Codigo implements Comparator<clsBeTrans_oc_det> {
        public int compare(clsBeTrans_oc_det left, clsBeTrans_oc_det right)                    {
            return sortord*left.Producto.Codigo.compareTo(right.Producto.Codigo);
        }
    }

    class Sort_Cantidad implements Comparator<clsBeTrans_oc_det> {
        public int compare(clsBeTrans_oc_det left,clsBeTrans_oc_det rigth){
            return Double.compare(sortord * left.Cantidad_recibida, sortord * rigth.Cantidad_recibida);
        }
    }

    class Sort_Producto implements Comparator<clsBeTrans_oc_det> {
        public int compare(clsBeTrans_oc_det left, clsBeTrans_oc_det right)                    {
            return sortord*left.Producto.Nombre.compareTo(right.Producto.Nombre);
        }
    }

    private void MuestraPantallaFirma(Activity activity){

        try{

            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frmfirmadig);

            txtFirma = (DrawingView)dialog.findViewById(R.id.drawV);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            txtFirma.init(metrics);

            btnGuardarFirma = (Button)dialog.findViewById(R.id.btnGuardarFirma);
            btnLimpiar = (Button)dialog.findViewById(R.id.btnLimpiar);
            btnSalirFirma = (Button)dialog.findViewById(R.id.btnSalirFirma);

            btnGuardarFirma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirmaPiloto = txtFirma.getBitmap();
                    //CM_20201130: Se obtienen los bytes de la firma para convertirlos y guardarlos.
                    firmByte = txtFirma.getBytes();
                    encodedImage = Base64.encodeToString(firmByte, Base64.DEFAULT);
                    dialog.cancel();
                    Finalizar_Recepcion();
                }
            });

            btnLimpiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtFirma.clear();
                }
            });

            btnSalirFirma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnTareas.setVisibility(View.VISIBLE);
                    relbot.setVisibility(View.VISIBLE);
                    dialog.cancel();
                }
            });

            dialog.show();

            //startActivity(new Intent(this, frmFirma.class));


        }catch (Exception e){
            mu.msgbox("MuestraPantallaFirma"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                if (!gl.gSinPresentacion){
                    pListDetalleOC.items= gl.gpListDetalleOC.items;
                    Lista_Detalle_Documento_Ingreso();
                    ordenar();
                    if(Recepcion_Completa()){
                        msgPreguntaFinalizar("Recepción completa. ¿Finalizar?");
                    }
                }else{
                    gl.gSinPresentacion=false;

                    progress.setMessage("Actualizando OC");
                    progress.show();

                    execws(15);
                }

            }

            if (browse==2){
                browse=0;
                pListDetalleOC.items= gl.gpListDetalleOC.items;
                Lista_Detalle_Documento_Ingreso();
                Recepcion_Completa();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    public void Salir(View view){
        doExit();
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

    @Override
    public void onBackPressed() {
        try{
            if (btnTareas.getVisibility()==View.VISIBLE){
                msgAskExit("Está seguro de salir");
            }
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }


}
