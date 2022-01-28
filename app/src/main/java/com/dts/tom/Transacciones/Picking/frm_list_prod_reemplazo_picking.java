package com.dts.tom.Transacciones.Picking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Picking.clsBeStockReemplazo;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStockList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.list_adapt_detalle_reemplazo_picking;

import java.util.ArrayList;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_danado_picking.IdEstadoDanadoSelect;
import static com.dts.tom.Transacciones.Picking.frm_danado_picking.IdUbicacionDestino;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.Tipo;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.TipoLista;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBePickingUbic;

public class frm_list_prod_reemplazo_picking extends PBase {

    private TextView lblTituloForma,lblCantRegs, lbldDetProducto;
    private ListView listDispProd;
    private Button btnActualizaPickingDet,btnBack;
    private ProgressDialog progress;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeStockList vListaStock = new clsBeStockList();
    private clsBeStockList vStock = new clsBeStockList();
    private clsBeStock_resList  BeListStockRes = new clsBeStock_resList();
    private clsBeStock_res StockResC=new clsBeStock_res();
    private clsBeStock_res StockResReemplazo=new clsBeStock_res();
    private clsBeStock_res tmpBeStock_Res  = new clsBeStock_res();

    private ArrayList<clsBeStockReemplazo> BeListStock= new ArrayList<clsBeStockReemplazo>();
    private list_adapt_detalle_reemplazo_picking adapter;
    private clsBeStockReemplazo selitem;

    private clsBeTrans_picking_ubic BePickingUbic = new clsBeTrans_picking_ubic();
    private clsBeStock_res lBeStockRes = new clsBeStock_res();
    private clsBeStock_resList lBeStockResAux = new clsBeStock_resList();

    private int CantRes=0, IdUbicDest = 0, IdEstDanadoSelect = 0;;
    private double vCant=0;
    private boolean Completo=false;
    private boolean Distinto=false;
    private String resultado="";
    private double CantidadPendiente=0, CantPendSel = 0;

    private Cursor DT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_prod_reemplazo_picking);
        super.InitBase();

        ws = new WebServiceHandler(frm_list_prod_reemplazo_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView)findViewById(R.id.lblTituloForma);
        lblCantRegs = (TextView)findViewById(R.id.lblCantRegs);
        lbldDetProducto = (TextView) findViewById(R.id.lbldDetProducto);

        listDispProd = (ListView)findViewById(R.id.listDispProd);

        btnActualizaPickingDet = (Button)findViewById(R.id.btnActualizaPickingDet);
        btnBack = (Button)findViewById(R.id.btnBack);

        lbldDetProducto.setText(gBePickingUbic.CodigoProducto+" - "+gBePickingUbic.NombreProducto+
                "\n Cant. Reemplazar: "+CantReemplazar+" "+gBePickingUbic.ProductoUnidadMedida);
        setHandles();

        ProgressDialog("Listando existencias de producto:"+gBePickingUbic.CodigoProducto);

        // #AT 20211228 Creacón de objeto para obtener el stock para reemplazo
        StockResReemplazo = new clsBeStock_res();

        StockResReemplazo.IdProductoBodega = gBePickingUbic.IdProductoBodega;
        StockResReemplazo.Lote = gBePickingUbic.Lote;
        StockResReemplazo.IdPresentacion = gBePickingUbic.IdPresentacion;
        StockResReemplazo.IdUnidadMedida = gBePickingUbic.IdUnidadMedida;
        StockResReemplazo.IdProductoEstado = gBePickingUbic.IdProductoEstado;
        try {
            if (! gBePickingUbic.Fecha_Vence.contains("T")){
                StockResReemplazo.Fecha_vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
                gBePickingUbic.Fecha_Vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StockResReemplazo.IdBodega=gBePickingUbic.IdBodega;
        StockResReemplazo.IdUbicacion=gBePickingUbic.IdUbicacion;

        //AT 20220118 Se utiliza para el reemplazo simple y para producto no encontrado (Lista Resumida)
        if (Tipo == 1) {
            IdUbicDest = IdUbicacionDestino;
            IdEstDanadoSelect = IdEstadoDanadoSelect;
        }

        execws(1);
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void setHandles(){

        try{

            listDispProd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        Object lvObj = listDispProd.getItemAtPosition(position);
                        clsBeStockReemplazo sitem = (clsBeStockReemplazo) lvObj;
                        selitem = new clsBeStockReemplazo();
                        selitem = BeListStock.get(position);

                        selid = sitem.IdStock;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        procesar_registro();
                    }

                }

            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
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
                    frm_list_prod_reemplazo_picking.super.finish();
                    startActivity(new Intent(frm_list_prod_reemplazo_picking.this, frm_picking_datos.class));
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

    public void Regresar(View view){
        msgAskExit("Está seguro de cancelar este proceso");
    }

    private void procesar_registro(){

        try{

            if (CantReemplazar>0){

                if (selitem.Despachar.equals("No")){
                    msgNoDespachar("Se recomienda no despachar de este producto, ¿continuar de todas formas?");
                }else{
                    Continua_procesando_registro();
                }

            }

        }catch (Exception e){
            mu.msgbox("procesar_registro:"+e.getMessage());
        }
    }

    private void Continua_procesando_registro(){

        try {

            CantPendSel = 0;

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

                if (selitem.Cant>=CantReemplazar) {
                    Distinto = false;
                } else {
                    Distinto = true;
                    CantPendSel = CantReemplazar - selitem.Cant;
                    CantReemplazar = selitem.Cant;
                }

                progress.show();
                progress.setMessage("Reservando el stock seleccionado...");

                //TipoLista 1 Resumido, 2 Detallado
                if (TipoLista == 1) {
                    //Reemplazar_ListaPu_By_Stock
                    execws(4);
                } else {
                    //Reservar_Stock_By_IdStock
                    execws(3);
                }
            }

        }catch (Exception e){
            mu.msgbox("Continua_procesando_registro:"+e.getMessage());
        }
    }

    private void msgNoDespachar(String msg) {

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

    private void msgMarcarDanado(String msg) {

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

    private void msgMarcarNoEncontrado(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Marcar_No_Encontrado();
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

    private void Marcar_Danado(){
        execws(7);
    }

    private void Marcar_No_Encontrado(){
        execws(8);
    }

    private void Load(){

        try{

            progress.setMessage("Procesando datos de StockRes...");

            lblTituloForma.setText("Picking List No: "+ gBePickingUbic.IdPickingEnc+
                    "\n Lista de Productos");

            Lista_Inventario_Disponible();

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Lista_Inventario_Disponible(){

        clsBeStockReemplazo vItem;

        try{

            BeListStock.clear();

            progress.setMessage("Listando stock disponible...");

            if (DT.getCount()>0) {

                /*vItem=new clsBeStockReemplazo();
                BeListStock.add(vItem);*/

                DT.moveToFirst();

                while (!DT.isAfterLast()) {

                    vItem=new clsBeStockReemplazo();

                    vItem.Codigo = DT.getString(0);
                    vItem.Producto = DT.getString(1);
                    vItem.Presentacion = DT.getString(2);
                    vItem.UMBas = DT.getString(3);
                    vItem.Cant = DT.getDouble(4);
                    vItem.IdUbicacion = DT.getInt(5);

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
            }

            adapter=new list_adapt_detalle_reemplazo_picking(this,BeListStock);
            listDispProd.setAdapter(adapter);

            progress.cancel();

        } catch (Exception e){
            progress.cancel();
            mu.msgbox("Lista_Inventario_Disponible:"+e.getMessage());
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
                        callMethod("Get_All_Stock_Especifico_By_IdPedidoDet",
                                   "pBeStockRes", StockResReemplazo,
                                   "IdPedidoEnc", gBePickingUbic.IdPedidoEnc,
                                   "gIdBodega", gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Reservar_Stock_By_IdStock",
                                "pBeStock_res",tmpBeStock_Res,
                                "CantSol",CantReemplazar,
                                "MaquinaQueSolicita","1",
                                "IdPickingEnc",gBePickingUbic.IdPickingEnc,
                                "IdPedidoEnc",gBePickingUbic.IdPedidoEnc,
                                "IdUsuarioHH",gl.OperadorBodega.IdOperador,
                                "IdPedidoDet",gBePickingUbic.IdPedidoDet,
                                "IdPickingUbic",gBePickingUbic.IdPickingUbic,
                                "EsPicking",true,
                                "Tipo", Tipo,
                                "IdUbicDestino",IdUbicDest,
                                "IdEstDestino",IdEstDanadoSelect);
                        break;
                    case 4:
                        callMethod("Reemplazar_ListaPu_By_Stock",
                                "pBeStock_res",tmpBeStock_Res,
                                "CantSol",CantReemplazar,
                                "MaquinaQueSolicita","1",
                                "IdPickingEnc",gBePickingUbic.IdPickingEnc,
                                "IdPedidoEnc",gBePickingUbic.IdPedidoEnc,
                                "IdUsuarioHH",gl.OperadorBodega.IdOperador,
                                "IdPedidoDet",gBePickingUbic.IdPedidoDet,
                                "BePickingUbic",gBePickingUbic,
                                "EsPicking",true,
                                "Tipo", Tipo,
                                "IdUbicDestino",IdUbicacionDestino,
                                "IdEstDestino",IdEstadoDanadoSelect);
                        break;
                    case 6:
                        callMethod("Sustituir_Producto_NE_Picking",
                                "IdPickingEnc",gBePickingUbic.IdPickingEnc,
                                "IdPickingDet",gBePickingUbic.IdPickingDet,
                                "CantSol",CantReemplazar,
                                "MaquinaQueSolicita","1",
                                "UsuarioHH",gl.OperadorBodega.IdOperador,
                                "lBeStockRes",lBeStockResAux.items,
                                "IdBodega",gl.IdBodega,
                                "IdEmpresa",gl.IdEmpresa,
                                "IdStockProductoNE",gBePickingUbic.IdStock,
                                "IdStockResProductoNE",gBePickingUbic.IdStockRes,
                                "Resultado",resultado);
                        break;
                    case 8:
                        callMethod("Marcar_No_Encontrado",
                                "IdBodega",gl.IdBodega,
                                "IdEmpresa",gl.IdEmpresa,
                                "IdStock",gBePickingUbic.IdStock,
                                "IdStockRes",gBePickingUbic.IdStockRes,
                                "UsuarioHH",gl.OperadorBodega.IdOperador,
                                "CantNoEncontrada",CantReemplazar,
                                "IdPropietarioBodega",gl.OperadorBodega.IdOperador,
                                "IdPickingUbic",gBePickingUbic.IdPickingUbic);
                        break;
                }

            } catch (Exception e) {
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
                    processListStockRes();
                    break;
                case 2:
                    processGetAllStock();
                    break;
                case 3:
                    processReservaStock();
                    break;
                case 4:
                    processReservaStock();
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    if (!Distinto){
                        processNoEncontrado();
                        progress.cancel();
                        doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void  processListStockRes(){

        try{

            DT = xobj.filldt();

            //#CKFK 20211228 Después tenemos que analizar si necesitamos esta variable
            //vCant = xobj.getresultSingle(Double.class,"vCant");

            if (DT.getCount()>0){
                Load();
            }else{
                progress.cancel();
            }

            if (DT.getCount() == 0) {

                if (Tipo==1){
                    msgMarcarDanado("No hay existencia de este producto en otra ubicación"+
                                    "\n¿Marcar como producto para reemplazo de todas formas?");
                    return;
                }else{
                    msgMarcarNoEncontrado("No hay existencia de este producto en otra ubicación"+
                                           "\n¿Marcar como producto No Encontrado de todas formas?");
                    return;
                }

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processListStockRes:"+e.getMessage());
        }
    }

    private void processGetAllStock(){

        try{

            progress.setMessage("Obteniendo stock...");

            if (!Completo){

                DT = xobj.filldt();

                if (DT.getCount()>0) {

                    DT.moveToFirst();

                    while (!DT.isAfterLast()) {

                        vCant += DT.getDouble(22);

                        if ( vCant >=CantReemplazar && vCant>0){
                            Completo=true;
                            Lista_Inventario_Disponible();
                            DT.moveToLast();
                            break;
                        }

                        DT.moveToNext();
                    }
                }
            }else{
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processGetAllStock:"+e.getMessage());
        }
    }

    private void processReservaStock(){

        try {

            progress.cancel();

            boolean StockReservado = false;

            if (TipoLista == 1) {
                StockReservado = xobj.getresult(Boolean.class,"Reemplazar_ListaPu_By_Stock");
            } else {
                StockReservado = xobj.getresult(Boolean.class,"Reservar_Stock_By_IdStock");
            }
            CantidadPendiente = (Double) xobj.getSingle("CantPend",Double.class);

            if (CantidadPendiente == 0 && CantPendSel > 0) {
                CantidadPendiente = CantPendSel;
            }

            if (StockReservado) {
                if (CantidadPendiente == 0) {
                    msgAskReemplazado(Tipo == 1? "Stock reemplazado correctamente":"Stock reemplazado(No Encontrado) correctamente");
                } else {
                    CantReemplazar = CantidadPendiente;
                    msgAskCantPendiente(Tipo == 1 ? "Cantidad de reemplazo pendiente para completar el proceso: "+ "("+CantReemplazar+")": "Cantidad de reemplazo(No encontrado) pendiente para completar el proceso: "+ "("+CantReemplazar+")");

                    lbldDetProducto.setText(gBePickingUbic.CodigoProducto+" - "+gBePickingUbic.NombreProducto+
                            "\n Cant. Reemplazar: "+CantReemplazar+" "+gBePickingUbic.ProductoUnidadMedida);

                    progress.setMessage(Tipo == 1? "Obteniendo inventario disponible para reemplazo":"Obteniendo inventario disponible para reemplazo(No Encontrado)");
                    execws(1);
                }
            }

        }catch (Exception e){
            mu.msgbox("processReservaStock:"+e.getMessage());
        }
    }

    private void processNoEncontrado(){

        try{

            String resultado = xobj.getresultSingle(String.class,"resultado");

            msgbox(resultado);

        }catch (Exception e){
            mu.msgbox("processUbicacion:"+e.getMessage());
        }
    }

    private void procesStockResAux() {

        try{

            progress.setMessage("Obteniendo el stock reservado...");

            lBeStockResAux = xobj.getresult(clsBeStock_resList.class,"Get_All_Reemplazo_By_IdPedidoDet");

            if (lBeStockResAux!=null){
                if (lBeStockResAux.items!=null){

                    CantRes = lBeStockResAux.items.size()-1;

                    for (clsBeStock_res StockRes: lBeStockResAux.items){

                        lBeStockRes = StockRes;

                    }

                    if (Tipo==1){

                        progress.setMessage("Reemplazando el stock...");
                        execws(5);

                    }else{
                        progress.setMessage("Sustituyendo el stock no encontrado...");
                        execws(6);
                    }


                }
            }

        }catch (Exception e){

        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
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
                    doExit();
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

    private void doExit(){
        super.finish();
    }

    @Override
    public void onBackPressed() {
        msgAskExit("Está seguro de cancelar este proceso");
    }
}
