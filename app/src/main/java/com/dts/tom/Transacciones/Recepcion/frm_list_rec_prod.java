package com.dts.tom.Transacciones.Recepcion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_pallet;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_palletList;
import com.dts.classes.Mantenimientos.Configuracion_barra_pallet.clsBeConfiguracion_barra_pallet;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_oc.clsBeTrans_re_oc;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_recList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_detalle_recepcion;

import java.net.StandardProtocolFamily;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_list_rec_prod extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private TextView lblNoDocumento;
    private Button btnRegs,btnCompletaRec;
    private ListView listView;
    private EditText txtCodigoProductoRecepcion;
    private ProgressDialog progress;

    private clsBeTrans_oc_enc gBeOrdenCompra = new clsBeTrans_oc_enc();
    private clsBeTrans_re_detList pListTransRecDet = new clsBeTrans_re_detList();
    private  clsBeTrans_re_oc gBeReOC = new clsBeTrans_re_oc();
    private clsBeTrans_oc_detList pListDetalleOC = new clsBeTrans_oc_detList();
    private clsBeConfiguracion_barra_pallet gBeConfiguracionBarraPallet =  new clsBeConfiguracion_barra_pallet();
    private static clsBeI_nav_barras_palletList lBeINavBarraPallet = new clsBeI_nav_barras_palletList();
    public static clsBeI_nav_barras_pallet BeINavBarraPallet= new clsBeI_nav_barras_pallet();
    private clsBeStock_recList pListBeStockRecPI = new clsBeStock_recList();
    public static clsBeStock_rec gBeStockRec = new clsBeStock_rec();
    private static ArrayList<clsBeTrans_oc_det> BeListDetalleOC= new ArrayList<clsBeTrans_oc_det>() ;
    public  static clsBeProducto BeProducto = new clsBeProducto();
    private int gotop;

    private boolean Escaneo_Pallet;
    private boolean Finalizada = false, Anulada = false;
    private double Cant_Recibida_Anterior;
    private int browse;
    public String pLP="";
    private String vLP="";
    public String vCodigoBodegaBarraPallet = "";
    public String vCodigoProductoBarraPallet= "";
    public static boolean EsTransferenciaInternaWMS=false;
    private  int vIdOrdenCompra=0;

    private int Idx = -1;

    private clsBeTrans_oc_det selitem;

    private list_adapt_detalle_recepcion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_rec_prod);

        super.InitBase();

        ws = new WebServiceHandler(frm_list_rec_prod.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblNoDocumento = (TextView) findViewById(R.id.lblNoDocumento);

        btnRegs = (Button) findViewById(R.id.btnRegs);
        btnCompletaRec = (Button)findViewById(R.id.btnCompletaRec);
        listView = (ListView)findViewById(R.id.listRec);

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
                    if (gBeConfiguracionBarraPallet.IdentificadorInicio!=null){
                        vStarWithParameter = gBeConfiguracionBarraPallet.IdentificadorInicio;
                    }
                }

                if (txtCodigoProductoRecepcion.getText().toString().startsWith("$") |
                        txtCodigoProductoRecepcion.getText().toString().startsWith("(01)") |
                        txtCodigoProductoRecepcion.getText().toString().startsWith(vStarWithParameter)){

                    int vLengthBarra  = txtCodigoProductoRecepcion.getText().toString().length();

                     if(gl.gBeRecepcion.IdTipoTransaccion.equals("PICH000")
                             | gl.gBeRecepcion.IdTipoTransaccion.equals("HCOC00")
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

                             vCodigoBodegaBarraPallet = pLP.substring(0, vLongitudBodegaOrigen);

                             vCodigoBodegaBarraPallet = vCodigoBodegaBarraPallet.replace("0", "");

                             vCodigoProductoBarraPallet = pLP.substring(vLongitudBodegaOrigen, vLongitudCodigoProducto);

                             if (gBeConfiguracionBarraPallet!=null){
                                 if (gBeConfiguracionBarraPallet.CodigoNumerico){
                                     vCodigoProductoBarraPallet = vCodigoProductoBarraPallet;
                                 }else{
                                     vCodigoProductoBarraPallet = vCodigoProductoBarraPallet;
                                 }
                             }

                             int vLongitudBarraPallet=pLP.length();

                            if ((vLongitudBodegaOrigen + vLongitudCodigoProducto + vLongitudCodigoPallet)>= vLongitudBarraPallet){

                                vLP = pLP.substring(vLongitudBodegaOrigen + vLongitudCodigoProducto, vLongitudBarraPallet - (vLongitudBodegaOrigen + vLongitudCodigoProducto));

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
                                gBeStockRec = stream(pListBeStockRecPI.items).where(c->c.Lic_plate.equals(pLP)).first();
                            }

                         }

                         if (gBeStockRec!=null){

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

    private void msgValidaProductoPallet(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
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

            vIdOrdenCompra=0;

            if (gl.gBeRecepcion.OrdenCompraRec!=null){
                if ( gl.gBeRecepcion.OrdenCompraRec.IdOrdenCompraEnc>0){
                    vIdOrdenCompra = gl.gBeRecepcion.OrdenCompraRec.IdOrdenCompraEnc;
                }
            }

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

            lblNoDocumento.setText("");

            if (gl.tipoTarea==1){

                if (gl.gBeRecepcion!=null){

                    pListTransRecDet = gl.gBeRecepcion.Detalle;

                    gBeOrdenCompra = new clsBeTrans_oc_enc();
                    gBeReOC = new clsBeTrans_re_oc();
                    pListDetalleOC =new clsBeTrans_oc_detList();

                    gBeOrdenCompra = gl.gBeRecepcion.OrdenCompraRec.OC;
                    gl.gBeOrdenCompra = gBeOrdenCompra;

                    gBeReOC = gl.gBeRecepcion.OrdenCompraRec;

                    pListDetalleOC = gl.gBeRecepcion.OrdenCompraRec.OC.DetalleOC;

                    gl.gpListDetalleOC = pListDetalleOC;

                    if(gBeOrdenCompra.No_Documento!=null & gBeOrdenCompra.Referencia!=null){
                        lblNoDocumento.setText("No. Documento:"+ gBeOrdenCompra.No_Documento+ " - "+gBeOrdenCompra.Referencia);
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
                mu.msgbox("Falta programar");
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

                    if (position>0){

                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_oc_det sitem = (clsBeTrans_oc_det) lvObj;
                        selitem = pListDetalleOC.items.get(position-1);

                        selid = sitem.No_Linea;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        procesar_registro();

                    }

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

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private void doExit(){
        try{

            //LimpiaValores();
            super.finish();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void ValidaProductoForRece(){

        try{

            if (BeProducto!=null){

                List AuxList = stream(pListDetalleOC.items).select(c->c.Codigo_Producto).toList();

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
                    mu.msgbox("El código de producto no es válido para la recepción");
                    txtCodigoProductoRecepcion.setText("");
                    txtCodigoProductoRecepcion.requestFocus();
                    return;
                }

            }else{
                mu.msgbox("El código de producto no es válido para la recepción");
                txtCodigoProductoRecepcion.setText("");
                txtCodigoProductoRecepcion.requestFocus();
                return;
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

                browse=1;
                startActivity(new Intent(this, frm_recepcion_datos.class));

            }
        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private boolean Recepcion_Completa(){

        double vTipoDiferencia;
        double Cantidad_recibida,Cantidad;

        try{

            progress.setMessage("Validando estado de recepción");

            if (gBeOrdenCompra.DetalleOC.items!=null){

                for (int i = gBeOrdenCompra.DetalleOC.items.size()-1; i>=0; i--) {

                    Cantidad_recibida = gBeOrdenCompra.DetalleOC.items.get(i).Cantidad_recibida;
                    Cantidad = gBeOrdenCompra.DetalleOC.items.get(i).Cantidad;

                    if (Cantidad_recibida>0){

                        vTipoDiferencia = mu.round(Cantidad_recibida-Cantidad,7);

                        if (vTipoDiferencia>0){

                            btnCompletaRec.setText("DIF - (NEG)");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#ffcc0000"));

                            return false;

                        }else if(vTipoDiferencia<0){

                            btnCompletaRec.setText(" DIF - (POS)");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FF0399D5"));

                            return false;

                        }else if (vTipoDiferencia==0){

                            btnCompletaRec.setText("COMPLETA");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FF99CC00"));

                            return true;
                        }

                    }else{

                        btnCompletaRec.setText("DIF - (NEG)");
                        btnCompletaRec.setBackgroundColor(Color.parseColor("#ffcc0000"));

                    }

                }

            }

            progress.cancel();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

        return  true;

    }

    private void Lista_Detalle_OC(){

        clsBeTrans_oc_det vItem;
        BeListDetalleOC.clear();

        try{

            progress.setMessage("Cargando detalle de OC");

            if(pListDetalleOC.items!=null){

                vItem = new clsBeTrans_oc_det();

                BeListDetalleOC.add(vItem);

                for (int i = pListDetalleOC.items.size()-1; i>=0; i--) {

                    vItem = new clsBeTrans_oc_det();

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

                    BeListDetalleOC.add(vItem);

                }

                btnRegs.setText("Regs: "+pListDetalleOC.items.size());
            }

            Collections.sort(BeListDetalleOC,new OrdenarItems());

            adapter=new list_adapt_detalle_recepcion(this,BeListDetalleOC);
            listView.setAdapter(adapter);

        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }

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
                        callMethod("Iniciar_Recepcion_OC","oBeTrans_oc_enc",gBeOrdenCompra);
                        break;
                    case  2:
                        callMethod("Actualizar_Estado_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "Estado","Pendiente");
                        break;
                    case 3:
                        callMethod("Get_Single_BeTrans_OC_Estado","pBeTrans_oc_estado",gBeOrdenCompra.EstadoOC);
                        break;
                    case 4:
                        callMethod("Get_Banderas_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "pFinalizada",Finalizada,
                                "pAnulada",Anulada);
                        break;
                    case 5:
                       callMethod("Get_All_Pallet_Ingreso_By_Barra","pCodigoBarraPallet",pLP,
                               "pIdBodega",gl.IdBodega,
                               "BeProducto",BeProducto);
                        break;
                    case 6:
                        callMethod("Get_BeProducto_By_LP_For_HH","pLic_Plate",pLP,
                                "IdRecepcionEnc",gl.gIdRecepcionEnc,"pBeStockRec",gBeStockRec);
                        break;
                    case 7:
                        callMethod("Finalizar_Recepcion_Parcial","pRecEnc",gl.gBeRecepcion,
                                "pIdOrdenCompraEnc",vIdOrdenCompra,"pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "pIdEmpresa",gl.IdEmpresa,"pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.IdOperador,"pBeStockRec",gBeStockRec);
                        break;
                    case 8:
                        callMethod("Get_BeProducto_By_LP_For_HH","pLic_Plate",pLP,
                                "IdRecepcionEnc",gl.gIdRecepcionEnc,"pBeStockRec",gBeStockRec);
                        break;
                    case 9:
                        callMethod("Get_BeProducto_By_Codigo_For_HH",
                                "pCodigo",txtCodigoProductoRecepcion.getText().toString(),"IdBodega",gl.IdBodega);
                        break;
                }

            }catch (Exception e){
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
                        Lista_Detalle_OC();
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
                }

            } catch (Exception e) {
                msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
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
                super.finish();
            }



        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processPalletIngreso(){

        try{

            lBeINavBarraPallet = xobj.getresult(clsBeI_nav_barras_palletList.class,"Get_All_Pallet_Ingreso_By_Barra");

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

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

           ValidaProductoForRece();

        }catch (Exception e){
            mu.msgbox("processGetProductoByCodigo");
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
                pListDetalleOC.items= gl.gpListDetalleOC.items;
                Lista_Detalle_OC();
                Recepcion_Completa();
            }


        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    public void Salir(View view){
        msgAskExit("Está seguro de salir");
    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

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
            msgAskExit("Está seguro de salir");
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }


}
