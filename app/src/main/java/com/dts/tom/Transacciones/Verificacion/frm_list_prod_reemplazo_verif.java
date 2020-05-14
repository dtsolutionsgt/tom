package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.dts.ladapt.Verificacion.list_adapt_detalle_reemplazo_verif;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Verificacion.frm_danado_verificacion.IdEstadoDanado;
import static com.dts.tom.Transacciones.Verificacion.frm_danado_verificacion.IdUbicacionDestino;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.BePedidoDetVerif;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.gBeProducto;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.pSubListPickingU;


public class frm_list_prod_reemplazo_verif extends PBase {

    private frm_list_prod_reemplazo_verif.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private TextView lblTituloForma,lblCantRegs;
    private ListView listDispProd;
    private Button btnActualizaPickingDet,btnBack;

    private ArrayList<clsBeStockReemplazo> BeListStock= new ArrayList<clsBeStockReemplazo>();
    private list_adapt_detalle_reemplazo_verif adapter;
    private clsBeStockReemplazo selitem;

    private clsBeTrans_picking_ubic BePickingUbic = new clsBeTrans_picking_ubic();
    //private clsBeStock_res lBeStockRes = new clsBeStock_res();
    //private clsBeStock_resList lBeStockResAux = new clsBeStock_resList();

    private double vCant=0;
    private boolean Distinto=false;

    public static boolean reemplazoCorrecto=false;

    private Cursor DT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_prod_reemplazo_verif);

        super.InitBase();

        ws = new WebServiceHandler(frm_list_prod_reemplazo_verif.this, gl.wsurl);
        xobj = new XMLObject(ws);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView)findViewById(R.id.lblTituloForma);
        lblCantRegs = (TextView)findViewById(R.id.lblCantRegs);

        listDispProd = (ListView)findViewById(R.id.listDispProd);

        btnActualizaPickingDet = (Button)findViewById(R.id.btnActualizaPickingDet);
        btnBack = (Button)findViewById(R.id.btnBack);

        setHandlers();

        ProgressDialog("Listando existencias de producto:"+gBeProducto.Codigo);

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
                    selitem = BeListStock.get(position);

                    selid = sitem.IdStock;
                    selidx = position;
                    adapter.setSelectedIndex(position);

                    int Ubicacion = selitem.IdUbicacion;

                    msgAskSeguroReemplazo(String.format("¿Desea reemplazar la cantidad: %s de la ubicación: %s?",
                            String.valueOf(CantReemplazar), String.valueOf(Ubicacion)));

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
                                    "pIdPedidoDet",BePedidoDetVerif.IdPedidoDet,
                                    "IdPedidoEnc",BePedidoDetVerif.IdPedidoEnc,
                                    "gIdBodega",gl.IdBodega,
                                    "CantReemplazar", CantReemplazar,
                                    "vCant",vCant);
                        break;
                    case 2:
                        callMethod("Reemplaza_Producto_Dannado_Mayor_Igual",
                                   "plistPickingUbi", pSubListPickingU.items,
                                   "pIdStock", selid,
                                   "pIdPickingEnc", pSubListPickingU.items.get(0).getIdPickingEnc(),
                                   "pIdOperador", gl.OperadorBodega.getIdOperador(),
                                   "pHost", "1",
                                   "pIdBodega", gl.IdBodega,
                                   "pIdEmpresa", gl.IdEmpresa,
                                   "pIdUbicDestino", IdUbicacionDestino,
                                   "pIdEstadoDestino", IdEstadoDanado,
                                   "pCantLinea", selitem.Cant,
                                   "pCantReemplazar", CantReemplazar);
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
                                   "pIdOperador",gl.OperadorBodega.getIdOperador());
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
                    processReemplazoMayorIgual();
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

            vCant = xobj.getresultSingle(Double.class,"vCant");

            if (DT.getCount()>0){

                progress.setMessage("Procesando datos de StockRes...");

                lblTituloForma.setText("Picking List No: "+ gl.gIdPickingEnc +
                        "\n Lista de Productos");


                Lista_Inventario_Disponible();

            }else{
                progress.cancel();
            }

            if (vCant == 0) {
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

                int count =BeListStock.size();
                lblCantRegs.setText("No.Reg: "+count);

                adapter=new list_adapt_detalle_reemplazo_verif(this,BeListStock);
                listDispProd.setAdapter(adapter);

                progress.cancel();

            }else{

            }

        } catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void  processReemplazoMayorIgual(){

        Boolean vResultado;

        try{

                progress.setMessage("Reemplazando producto de una idStock con una cantidad mayor o igual");

                DT = xobj.filldt();

                vResultado = xobj.getresultSingle(Boolean.class,"Reemplaza_Producto_Dannado_Mayor_IgualResult");

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

            progress.setMessage("Procesando el reemplazo de producto...");
            progress.show();

            List AuxList = stream(pSubListPickingU.items)
                    .where (z -> z.getIdPedidoDet() == BePedidoDetVerif.getIdPedidoDet())
                    .where(z -> z.getCantidad_Recibida() - z.getCantidad_Verificada() !=  0)
                    .toList();

            pSubListPickingU.items = AuxList;

            if (selitem.Cant>=CantReemplazar){

                //Llama al método del WS Reemplaza_Producto_Dannado_Mayor_Igual
                execws(2);

            }else{

                //Llama al método del WS Reemplaza_Producto_Dannado_Menor
                execws(3);

            }


        }catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Marcar_Danado(){
        try{

            List AuxList = stream(pSubListPickingU.items)
                    .where (z -> z.getIdPedidoDet() == BePedidoDetVerif.getIdPedidoDet())
                    .where(z -> z.getCantidad_Recibida() - z.getCantidad_Verificada() !=  0)
                    .toList();

            pSubListPickingU.items = AuxList;

            //Llama al método del WS Marcar_Danado
            execws(4);

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
                    frm_list_prod_reemplazo_verif.super.finish();
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

    public void Regresar(View view){
        msgAskExit("Está seguro de salir, no se guardará el producto para el reemplazo");
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir, no se guardará el producto para el reemplazo");

        }catch (Exception e){
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
