package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Picking.clsBeStockReemplazo;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.ladapt.Verificacion.list_adapt_detalle_reemplazo_verif;
import com.dts.ladapt.list_adapt_detalle_reemplazo_picking;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.Tipo;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBePickingUbic;
import static com.dts.tom.Transacciones.Verificacion.frm_danado_verificacion.IdEstadoDanado;
import static com.dts.tom.Transacciones.Verificacion.frm_danado_verificacion.IdUbicacionDestino;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.AuxCantReemplazar;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.BePedidoDetVerif;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.gBeProducto;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.pSubListPickingU;


public class frm_list_prod_reemplazo_verif extends PBase {

    private frm_list_prod_reemplazo_verif.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private TextView lblTituloForma,lblCantRegs,lbldDetProducto;
    private EditText txtFiltro;
    private ListView listDispProd;
    private Button btnActualizaPickingDet,btnBack;

    private final ArrayList<clsBeStockReemplazo> BeListStock= new ArrayList<clsBeStockReemplazo>();
    private final ArrayList<clsBeStockReemplazo> TempBeListStock= new ArrayList<clsBeStockReemplazo>();
    private list_adapt_detalle_reemplazo_verif adapter;
    private clsBeStockReemplazo selitem;
    private clsBeStock_res StockResReemplazo;
    private clsBeStock_res tmpBeStock_Res  = new clsBeStock_res();
    private clsBeTrans_picking_ubic BePickingUbic = new clsBeTrans_picking_ubic();
    //private clsBeStock_res lBeStockRes = new clsBeStock_res();
    //private clsBeStock_resList lBeStockResAux = new clsBeStock_resList();

    private final double vCant=0;
    private double CantidadPendiente=0;
    private double CantPendSel=0;
    private double pCantTotal= 0;
    private final boolean Distinto=false;

    public static boolean reemplazoCorrecto=false;
    private boolean ConExistencia = false;

    private Cursor DT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_prod_reemplazo_verif);

        super.InitBase();

        ws = new WebServiceHandler(frm_list_prod_reemplazo_verif.this, gl.wsurl);
        xobj = new XMLObject(ws);
        xobj = new XMLObject(ws);

        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblCantRegs = findViewById(R.id.lblCantRegs);
        lbldDetProducto = findViewById(R.id.lbldDetProducto);

        listDispProd = findViewById(R.id.listDispProd);

        txtFiltro = findViewById(R.id.txtFiltro);

        btnActualizaPickingDet = findViewById(R.id.btnActualizaPickingDet);
        btnBack = findViewById(R.id.btnBack);

        pCantTotal = CantReemplazar;
        ConExistencia = false;
        gl.termino = "";
        setHandlers();

        ProgressDialog("Listando existencias de producto:"+gBeProducto.Codigo);


        StockResReemplazo = new clsBeStock_res();

        StockResReemplazo.IdProductoBodega = BePedidoDetVerif.IdProductoBodega;
        StockResReemplazo.Lote = BePedidoDetVerif.Lote;
        StockResReemplazo.IdPresentacion = BePedidoDetVerif.IdPresentacion;
        StockResReemplazo.IdUnidadMedida = BePedidoDetVerif.IdUnidadMedidaBasica;
        StockResReemplazo.IdProductoEstado = BePedidoDetVerif.IdProductoEstado;
        try {
            if (! BePedidoDetVerif.Fecha_Vence.contains("T")){
                StockResReemplazo.Fecha_vence = du.convierteFecha(BePedidoDetVerif.Fecha_Vence);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StockResReemplazo.IdBodega=gl.IdBodega;

        lbldDetProducto.setText(BePedidoDetVerif.Codigo+" - "+BePedidoDetVerif.Nombre_Producto+
                "\n Cant. Reemplazar: "+CantReemplazar+" "+BePedidoDetVerif.Nom_Unid_Med);

        //Llama el método del WS Get_All_Stock_Especifico_By_IdPedidoDet
        execws(1);

    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void setHandlers(){

        try{

            listDispProd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    Object lvObj = listDispProd.getItemAtPosition(position);
                    clsBeStockReemplazo sitem = (clsBeStockReemplazo) lvObj;
                    selitem = new clsBeStockReemplazo();
                    //selitem = BeListStock.get(position);

                    //#AT20220707 Ya no se obtiene de la lista directamente según la posición,
                    //ahora selitem = sitem
                    selitem = sitem;

                    selid = sitem.IdStock;
                    selidx = position;
                    adapter.setSelectedIndex(position);

                    int Ubicacion = selitem.IdUbicacion;

                    msgAskSeguroReemplazo(String.format("¿Desea reemplazar la cantidad: %s de la ubicación: %s?",
                            CantReemplazar, Ubicacion));

                }

            });

            txtFiltro.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence termino, int i, int i1, int i2) {
                    if (!txtFiltro.getText().toString().isEmpty()) {

                        //AT20220817 Si la barra comienza con $  se reemplaza
                        if (txtFiltro.getText().toString().startsWith("$")) {
                            gl.termino = txtFiltro.getText().toString().replace("$","");
                        } else {
                            gl.termino = txtFiltro.getText().toString();
                        }

                        Lista_Filtrada();
                    } else {
                        Lista_Inventario_Disponible();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_All_Stock_Especifico_By_IdPedidoDet",
                                "pBeStockRes", StockResReemplazo,
                                "IdPedidoEnc", BePedidoDetVerif.IdPedidoEnc,
                                "gIdBodega", gl.IdBodega);
                        break;
                    case 2:
                        if (ConExistencia) {
                            callMethod("Reemplazar_ListPickingUbic_Verificacion",
                                    "plistPickingUbi", BePickingUbic,
                                    "pBeStockRes", tmpBeStock_Res,
                                    "pIdPickingEnc", pSubListPickingU.items.get(0).getIdPickingEnc(),
                                    "pIdOperador", gl.OperadorBodega.getIdOperador(),
                                    "pHost", "1",
                                    "pIdBodega", gl.IdBodega,
                                    "pIdEmpresa", gl.IdEmpresa,
                                    "pIdUbicDestino", IdUbicacionDestino,
                                    "pIdEstadoDestino", IdEstadoDanado,
                                    "pCantLinea", selitem.Cant,
                                    "pCantReemplazar", CantReemplazar,
                                    "pCantTotal", pCantTotal,
                                    "ConExistencia", ConExistencia);
                        } else {
                            callMethod("Reemplazar_ListPickingUbic_Verificacion",
                                    "plistPickingUbi", pSubListPickingU.items.get(0),
                                    "pBeStockRes", tmpBeStock_Res,
                                    "pIdPickingEnc", pSubListPickingU.items.get(0).getIdPickingEnc(),
                                    "pIdOperador", gl.OperadorBodega.getIdOperador(),
                                    "pHost", "1",
                                    "pIdBodega", gl.IdBodega,
                                    "pIdEmpresa", gl.IdEmpresa,
                                    "pIdUbicDestino", IdUbicacionDestino,
                                    "pIdEstadoDestino", IdEstadoDanado,
                                    "pCantLinea", 0,
                                    "pCantReemplazar", CantReemplazar,
                                    "pCantTotal", pCantTotal,
                                    "ConExistencia", ConExistencia);
                        }

                        break;
                    case 3:
                        callMethod("Reemplaza_Producto_Dannado_Menor",
                                "plistPickingUbi", pSubListPickingU.items,
                                "pIdStock", selid,
                                "pIdPickingEnc", pSubListPickingU.items.get(0).getIdPickingEnc(),
                                "pIdOperador", gl.OperadorBodega.getIdOperador(),
                                "pHost", "1",
                                "pIdBodega", gl.gCodigoBodega,
                                "pIdEmpresa", gl.IdEmpresa,
                                "pIdUbicDestino", IdUbicacionDestino,
                                "pIdEstadoDestino", IdEstadoDanado,
                                "pCantLinea", selitem.Cant,
                                "pCantReemplazar", CantReemplazar);
                        break;
                    case 4:
                        callMethod("Marcar_Danado",
                                   "plistPickingUbi",pSubListPickingU.items,
                                   "pCantidad", vCant,
                                   "pIdBodega",gl.gCodigoBodega,
                                   "pIdEmpresa", gl.IdEmpresa,
                                   "pIdUbicDestino", IdUbicacionDestino,
                                   "pIdEstadoDestino", IdEstadoDanado,
                                   "pIdOperador",gl.OperadorBodega.getIdOperador(),
                                   "pHostSolicita",gl.devicename);
                        break;

                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                error=e.getMessage();errorflag =true;
                msgbox(error);
            }
        }
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processListStockRes();
                    break;
                case 2:
                    processReemplazo();
                    break;
                case 3:
                    processReemplazoMenor();
                    break;
                case 4:
                    processMarcaDanado();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void  processListStockRes(){

        try{

            DT = xobj.filldt();

            //vCant = xobj.getresultSingle(Double.class,"vCant");

            if (DT.getCount()>0){
                ConExistencia = true;

                progress.setMessage("Procesando datos de StockRes...");

                lblTituloForma.setText("Picking List No: "+ gl.gIdPickingEnc +
                        "\n Lista de Productos");


                Lista_Inventario_Disponible();

            } else {
                ConExistencia = false;
                msgAskMarcarDanado("No hay existencia de este producto en otra ubicación"+
                        "\n¿Marcar como producto para reemplazo de todas formas?");
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processListStockRes:"+e.getMessage());
        }
    }

    private void Lista_Inventario_Disponible(){
        clsBeStockReemplazo vItem;

        try{

            BeListStock.clear();

            progress.show();
            progress.setMessage("Listando stock disponible...");

            if (DT.getCount()>0) {

                DT.moveToFirst();

                while (!DT.isAfterLast()) {

                    vItem=new clsBeStockReemplazo();

                    vItem.Codigo = DT.getString(0);
                    vItem.Producto = DT.getString(1);
                    vItem.Presentacion = DT.getString(2);
                    vItem.UMBas = DT.getString(3);
                    vItem.Cant = DT.getDouble(4);
                    vItem.IdUbicacion = DT.getInt(5);
                    vItem.NombreUbicacion = DT.getString(40);

                    if (DT.getString(6)!=null){
                        vItem.FechaVence = du.convierteFechaMostrar(DT.getString(6));
                    }else{
                        vItem.FechaVence = "";
                    }

                    if (DT.getString(7)!=null){
                        vItem.LicPlate = DT.getString(7);
                    }else{
                        vItem.LicPlate = "";
                    }
                    if (DT.getString(8)!=null){
                        vItem.Lote = DT.getString(8);
                    }else{
                        vItem.Lote = "";
                    }
                    vItem.CodigoProducto = DT.getString(0);
                    vItem.Peso = DT.getDouble(9);
                    vItem.Estado = DT.getString(10);
                    vItem.IdStock = 0; //DT.getInt(11);
                    vItem.Despachar = DT.getString(11);
                    vItem.IdEstado = DT.getInt(12);
                    vItem.IdPresentacion = DT.getInt(13) ;
                    vItem.IdProductoBodega = DT.getInt(14);
                    vItem.IdUnidadMedida = DT.getInt(20);

                    BeListStock.add(vItem);

                    DT.moveToNext();
                }

                int count =BeListStock.size();
                lblCantRegs.setText("No.Reg: "+count);

                adapter=new list_adapt_detalle_reemplazo_verif(this,BeListStock);
                listDispProd.setAdapter(adapter);

                progress.cancel();

            }

        } catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void Lista_Filtrada(){

        clsBeStockReemplazo vItem;

        try{

            TempBeListStock.clear();

            if (DT.getCount()>0) {
                ConExistencia = true;
                DT.moveToFirst();

                while (!DT.isAfterLast()) {

                    vItem=new clsBeStockReemplazo();

                    vItem.Codigo = DT.getString(0);
                    vItem.Producto = DT.getString(1);
                    vItem.Presentacion = DT.getString(2);
                    vItem.UMBas = DT.getString(3);
                    vItem.Cant = DT.getDouble(4);
                    vItem.IdUbicacion = DT.getInt(5);
                    vItem.NombreUbicacion = DT.getString(40);

                    if (DT.getString(6)!=null){
                        vItem.FechaVence = du.convierteFechaMostrar(DT.getString(6));
                    }else{
                        vItem.FechaVence = "";
                    }

                    if (DT.getString(7)!=null){
                        vItem.LicPlate = DT.getString(7);
                    }else{
                        vItem.LicPlate = "";
                    }
                    if (DT.getString(8)!=null){
                        vItem.Lote = DT.getString(8);
                    }else{
                        vItem.Lote = "";
                    }
                    vItem.CodigoProducto = DT.getString(0);
                    vItem.Peso = DT.getDouble(9);
                    vItem.Estado = DT.getString(10);
                    vItem.IdStock = 0; //DT.getInt(11);
                    vItem.Despachar = DT.getString(11);
                    vItem.IdEstado = DT.getInt(12);
                    vItem.IdPresentacion = DT.getInt(13) ;
                    vItem.IdProductoBodega = DT.getInt(14);
                    vItem.IdUnidadMedida = DT.getInt(20);

                    if(String.valueOf(vItem.IdUbicacion).toLowerCase().contains(gl.termino.toLowerCase()) || vItem.LicPlate.toLowerCase().contains(gl.termino.toLowerCase())){
                        TempBeListStock.add(vItem);
                    }

                    DT.moveToNext();
                }

                int count =TempBeListStock.size();
                lblCantRegs.setText("No.Reg: "+count);
            }

            adapter=new list_adapt_detalle_reemplazo_verif(this,TempBeListStock);
            listDispProd.setAdapter(adapter);

            progress.cancel();

        } catch (Exception e){
            progress.cancel();
            mu.msgbox("Lista_Inventario_Disponible:"+e.getMessage());
        }

    }

    private void  processReemplazo(){
        try {
            
            boolean StockReservado = false;
            progress.cancel();

            StockReservado = xobj.getresult(Boolean.class,"Reemplazar_ListPickingUbic_Verificacion");
            CantidadPendiente = (Double) xobj.getSingle("CantPend",Double.class);

            if (CantidadPendiente == 0 && CantPendSel > 0) {
                CantidadPendiente = CantPendSel;
            }

            if (StockReservado) {
                if (CantidadPendiente == 0) {
                    reemplazoCorrecto = true;
                    msgAskReemplazado("Stock reemplazado correctamente");
                } else {
                    CantReemplazar = CantidadPendiente;
                    pCantTotal = CantidadPendiente;
                    msgAskCantPendiente("Cantidad de reemplazo pendiente para completar el proceso: "+ "("+CantReemplazar+")");

                    lbldDetProducto.setText(BePedidoDetVerif.Codigo+" - "+BePedidoDetVerif.Nombre_Producto+
                            "\n Cant. Reemplazar: "+CantReemplazar+" "+BePedidoDetVerif.Nom_Unid_Med);

                    progress.setMessage("Obteniendo inventario disponible para reemplazo");
                    execws(1);
                }
            }

        }catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void  processReemplazoMenor(){

        Boolean vResultado;

        try{

            progress.setMessage("Reemplazando producto de una idStock con una cantidad menor");

            DT = xobj.filldt();

            vResultado = xobj.getresultSingle(Boolean.class,"Reemplaza_Producto_Dannado_MenorResult");

            if (vResultado){
                //Llama el método del WS Get_All_Stock_Especifico_By_IdPedidoDet
                execws(1);
            }else{
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void  processMarcaDanado(){

        Boolean vResultado;

        try{

            progress.setMessage("Marcando producto como dañado...");

            DT = xobj.filldt();

            vResultado = xobj.getresultSingle(Boolean.class,"Marcar_DanadoResult");

            if (vResultado){
                reemplazoCorrecto = true;
                super.finish();
            }else{
                reemplazoCorrecto = false;
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void procesar_registro(){

        try{

            if (CantReemplazar>0){

                if (selitem.Despachar.equals("No")){
                    msgAskNoDespachar("Se recomienda no despachar de este producto, ¿continuar de todas formas?");
                }else{
                    Continua_procesando_registro();
                }

            }

        }catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Continua_procesando_registro(){

        try{
            CantPendSel = 0;

            progress.setMessage("Procesando el reemplazo de producto...");
            progress.show();

            BePickingUbic = pSubListPickingU.items.get(0);

            if (CantReemplazar > 0) {
                tmpBeStock_Res = new clsBeStock_res();

                tmpBeStock_Res.IdPresentacion = selitem.IdPresentacion;
                tmpBeStock_Res.IdProductoEstado = selitem.IdEstado;
                tmpBeStock_Res.IdUbicacion = selitem.IdUbicacion;
                tmpBeStock_Res.Lic_plate = selitem.LicPlate;
                tmpBeStock_Res.Lote = selitem.Lote;
                tmpBeStock_Res.Fecha_vence = du.convierteFecha(selitem.FechaVence);
                tmpBeStock_Res.IdProductoBodega = selitem.IdProductoBodega;
                tmpBeStock_Res.IdUnidadMedida = selitem.IdUnidadMedida;

                if (selitem.Cant >= CantReemplazar) {

                } else {
                    CantPendSel = CantReemplazar - selitem.Cant;
                    CantReemplazar = selitem.Cant;
                }

                execws(2);
            }

        }catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Marcar_Danado(){
        List AuxList;
        try{

            if (gl.VerificacionConsolidada) {
                AuxList = stream(pSubListPickingU.items)
                        .where(z -> z.CodigoProducto.equals(BePedidoDetVerif.Codigo))
                        .where(z -> z.getCantidad_Recibida() - z.getCantidad_Verificada() != 0)
                        .toList();
            } else {
                AuxList = stream(pSubListPickingU.items)
                        .where(z -> z.getIdPedidoDet() == BePedidoDetVerif.getIdPedidoDet())
                        .where(z -> z.getCantidad_Recibida() - z.getCantidad_Verificada() != 0)
                        .toList();
            }

            pSubListPickingU.items = AuxList;

            //Llama al método del WS Marcar_Danado
            execws(2);

        }catch (Exception ex){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void msgAskMarcarDanado(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Marcar_Danado();
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

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if (gl.VerificacionSinLoteFechaVen) {
                        BePedidoDetVerif = gl.gBePedidoDetVerif;
                        CantReemplazar = AuxCantReemplazar;
                    }

                    frm_list_prod_reemplazo_verif.super.finish();
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

    private void msgAskNoDespachar(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_procesando_registro();
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

    private void msgAskSeguroReemplazo(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    procesar_registro();
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

    private void msgAskReemplazado(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ok48);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    salir();
                }
            });

            dialog.show();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskCantPendiente(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ok48);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void salir () {
        super.finish();
    }

    public void Regresar(View view){
        msgAskExit("Está seguro de salir, no se guardará el producto para el reemplazo");
    }

    @Override
    public void onBackPressed() {

        try {
            msgAskExit("Está seguro de salir, no se guardará el producto para el reemplazo");
        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    protected void onResume() {
        try{
            super.onResume();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
