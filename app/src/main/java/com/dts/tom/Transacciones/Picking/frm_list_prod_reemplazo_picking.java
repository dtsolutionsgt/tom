package com.dts.tom.Transacciones.Picking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
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
import com.dts.tom.list_adapt_detalle_reemplazo_picking;

import java.util.ArrayList;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_danado_picking.IdEstadoDanadoSelect;
import static com.dts.tom.Transacciones.Picking.frm_danado_picking.IdUbicacionDestino;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.Tipo;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBePickingUbic;

public class frm_list_prod_reemplazo_picking extends PBase {

    private TextView lblTituloForma,lblCantRegs;
    private ListView listDispProd;
    private Button btnActualizaPickingDet,btnBack;
    private ProgressDialog progress;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeStockList vListaStock = new clsBeStockList();
    private clsBeStockList vStock = new clsBeStockList();
    private clsBeStock_resList  BeListStockRes = new clsBeStock_resList();
    private clsBeStock_res StockResC=new clsBeStock_res();

    private ArrayList<clsBeStockReemplazo> BeListStock= new ArrayList<clsBeStockReemplazo>();
    private list_adapt_detalle_reemplazo_picking adapter;
    private clsBeStockReemplazo selitem;

    private clsBeTrans_picking_ubic BePickingUbic = new clsBeTrans_picking_ubic();
    private clsBeStock_res lBeStockRes = new clsBeStock_res();
    private clsBeStock_resList lBeStockResAux = new clsBeStock_resList();

    private int CantRes=0;
    private double vCant=0;
    private boolean Completo=false;
    private boolean Distinto=false;
    private String resultado="";
    private double CantidadPendiente=0;


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

        listDispProd = (ListView)findViewById(R.id.listDispProd);

        btnActualizaPickingDet = (Button)findViewById(R.id.btnActualizaPickingDet);
        btnBack = (Button)findViewById(R.id.btnBack);

        setHandles();

        ProgressDialog("Listando existencias de producto:"+gBePickingUbic.CodigoProducto);

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

        try{

            if (selitem.Cant>=CantReemplazar){

                if (selitem.IdStock!=gBePickingUbic.IdStock){

                    Distinto =false;

                    progress.show();
                    progress.setMessage("Reservando el stock seleccionado...");

                    execws(3);

                }else{

                    progress.show();

                    if(Tipo==1){

                        progress.setMessage("Generando la tarea de cambio de estado...");
                        execws(7);

                    }else{

                        progress.setMessage("Marcando el producto no encontrado...");
                        execws(8);
                    }
                }

            }else{

                Distinto =true;

                BePickingUbic = new clsBeTrans_picking_ubic();
                BePickingUbic.IdStock = selitem.IdStock;

                CantidadPendiente = CantReemplazar - selitem.Cant;

                if (CantidadPendiente>0){
                    BePickingUbic.CantidadDanada = selitem.Cant;
                }else{
                    BePickingUbic.CantidadDanada = CantReemplazar;
                }

                CantReemplazar -=BePickingUbic.CantidadDanada;

                if (CantReemplazar!=0){

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

                vItem=new clsBeStockReemplazo();

                BeListStock.add(vItem);

                DT.moveToFirst();

                while (!DT.isAfterLast()) {

                    vItem=new clsBeStockReemplazo();

                    vItem.Codigo = DT.getString(12);
                    vItem.Producto = DT.getString(14);
                    vItem.Presentacion = DT.getString(16);
                    vItem.UMBas = DT.getString(15);
                    vItem.Cant = DT.getDouble(22);
                    vItem.IdUbicacion = DT.getInt(30);
                    vItem.FechaVence = du.convierteFechaMostar(DT.getString(19));
                    vItem.LicPlate = DT.getString(31);
                    vItem.Lote = DT.getString(17);
                    vItem.CodigoProducto = DT.getString(12);
                    vItem.Peso = DT.getDouble(23);
                    vItem.Estado = DT.getString(25);
                    vItem.IdStock = DT.getInt(5);
                    vItem.Despachar = DT.getString(56);

                    BeListStock.add(vItem);

                    DT.moveToNext();
                }

                int count =BeListStock.size()-1;
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
                        callMethod("Get_All_Stock_Especifico_By_IdPedidoDet","pIdPedidoDet",gBePickingUbic.IdPedidoDet,
                                "IdPedidoEnc",gBePickingUbic.IdPedidoEnc,"gIdBodega",gl.IdBodega,"CantReemplazar",CantReemplazar,"vCant",vCant);
                        break;
                    case 2:
                        //callMethod("Get_All_Stock_Especifico_HH","IdBodega",gl.IdBodega,"IdPedidoEnc",gBePickingUbic.IdPedidoEnc,
                          //      "pStockRes",StockResC);
                        break;
                    case 3:
                        callMethod("Reservar_Stock_By_IdStock","IdStock",selitem.IdStock,"CantSol",CantReemplazar,
                                "MaquinaQueSolicita","1","IdPickingEnc",gBePickingUbic.IdPickingEnc,"IdPedidoEnc",gBePickingUbic.IdPedidoEnc,
                                "IdUsuarioHH",gl.OperadorBodega.IdOperador,"IdPedidoDet",gBePickingUbic.IdPedidoDet,"IdPickingUbic",gBePickingUbic.IdPickingUbic,
                                "EsPicking",true,"IdPresentacionPedido",gBePickingUbic.IdPresentacion);
                        break;
                    case 4:
                        callMethod("Get_All_Reemplazo_By_IdPedidoDet","pIdPedidoDet",gBePickingUbic.IdPedidoDet,
                                "pIdPropietarioBodega",gBePickingUbic.IdPropietarioBodega,"pIdPickingEnc",gBePickingUbic.IdPickingEnc,
                                "pIdPedidoEnc",gBePickingUbic.IdPedidoEnc);
                        break;
                    case 5:
                        callMethod("Reemplazo_Producto_En_Picking","IdStockCambioEst",gBePickingUbic.IdStock,
                                "IdPickingEnc",gBePickingUbic.IdPickingEnc,"IdPickingDet",gBePickingUbic.IdPickingDet,"CantSol",CantReemplazar,
                                "MaquinaQueSolicita","1","UsuarioHH",gl.OperadorBodega.IdOperador,"lBeStockRes",lBeStockResAux.items,"IdBodega",gl.IdBodega,
                                "IdEmpresa",gl.IdEmpresa,"IdUbicDestino",IdUbicacionDestino,"IdEstDestino",IdEstadoDanadoSelect,"IdStockResCambioEst",gBePickingUbic.IdStockRes,
                                "EsPicking",true);
                        break;
                    case 6:
                        callMethod("Sustituir_Producto_NE_Picking","IdPickingEnc",gBePickingUbic.IdPickingEnc,
                                "IdPickingDet",gBePickingUbic.IdPickingDet,"CantSol",CantReemplazar,"MaquinaQueSolicita","1","UsuarioHH",gl.OperadorBodega.IdOperador,
                                "lBeStockRes",lBeStockResAux.items,"IdBodega",gl.IdBodega,"IdEmpresa",gl.IdEmpresa,"IdStockProductoNE",gBePickingUbic.IdStock,
                                "IdStockResProductoNE",gBePickingUbic.IdStockRes,"Resultado",resultado);
                        break;
                    case 7:
                        callMethod("Genera_Tarea_Cambio_Estado_Por_Producto_Dañado","IdBodega",gl.IdBodega,"IdEmpresa",gl.IdEmpresa,
                                "IdStock",gBePickingUbic.IdStock,"IdStockRes",gBePickingUbic.IdStockRes,"UsuarioHH",gl.OperadorBodega.IdOperador,
                                "CantDañada",CantReemplazar,"IdUbicDest",IdUbicacionDestino,"IdEstadoDest",IdEstadoDanadoSelect,"IdPropietarioBodega",
                                gBePickingUbic.IdPropietarioBodega,"IdPickingUbic",gBePickingUbic.IdPickingUbic,"EsPicking",true);
                        break;
                    case 8:
                        callMethod("Marcar_No_Encontrado","IdBodega",gl.IdBodega,"IdEmpresa",gl.IdEmpresa,"IdStock",gBePickingUbic.IdStock,
                                "IdStockRes",gBePickingUbic.IdStockRes,"UsuarioHH",gl.OperadorBodega.IdOperador,"CantNoEncontrada",CantReemplazar,
                                "IdPropietarioBodega",gl.OperadorBodega.IdOperador,"IdPickingUbic",gBePickingUbic.IdPickingUbic);
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
                    procesStockResAux();
                    break;
                case 5:
                    if (!Distinto){
                        progress.cancel();
                        doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
                case 6:
                    if (!Distinto){
                        progress.cancel();
                        doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
                case 7:
                    if (!Distinto){
                        progress.cancel();
                        doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
                case 8:
                    if (!Distinto){
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

            vCant = xobj.getresultSingle(Double.class,"vCant");

            if (DT.getCount()>0){
                    Load();
            }else{
                progress.cancel();
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

        try{

            execws(4);

        }catch (Exception e){
            mu.msgbox("processReservaStock:"+e.getMessage());
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

    private void doExit(){
        super.finish();
    }

}
