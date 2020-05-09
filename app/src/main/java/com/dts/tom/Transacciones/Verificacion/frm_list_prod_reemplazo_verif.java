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
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_resList;
import com.dts.ladapt.Verificacion.list_adapt_detalle_reemplazo_verif;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.BePedidoDetVerif;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.gBeProducto;

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

    private void Load(){

        try{

            progress.setMessage("Procesando datos de StockRes...");

            lblTituloForma.setText("Picking List No: "+ gl.gIdPickingEnc +
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

            adapter=new list_adapt_detalle_reemplazo_verif(this,BeListStock);
            listDispProd.setAdapter(adapter);

            progress.cancel();

        } catch (Exception e){
            progress.cancel();
            mu.msgbox("Lista_Inventario_Disponible:"+e.getMessage());
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
                       /* callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado",
                                "IdUbicacion",BeUbicDestino.IdUbicacion,
                                "IdEstado",IdEstadoDanadoSelect,
                                "IdBodega",gl.IdBodega,
                                "pNombreUbicacion",BeUbicDestino.NombreCompleto);*/
                        break;
                    case 3:
                        /*callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado",
                                "IdUbicacion",Integer.parseInt(txtUbicDestVeri.getText().toString()),
                                "IdEstado",IdEstadoDanadoSelect,
                                "IdBodega",gl.IdBodega,
                                "pNombreUbicacion",vNomUbicDestino);*/
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
                    //processGetAllStock();
                    break;
                case 3:
                   // processReservaStock();
                    break;
                case 4:
                   // procesStockResAux();
                    break;
                case 5:
                    if (!Distinto){
                        progress.cancel();
                        //doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
                case 6:
                    if (!Distinto){
                        progress.cancel();
                        //doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
                case 7:
                    if (!Distinto){
                        progress.cancel();
                       // doExit();
                    }else{
                        BeListStock = (ArrayList<clsBeStockReemplazo>) stream(BeListStock).where(c->c.IdStock==selitem.IdStock).toList();
                        Lista_Inventario_Disponible();
                    }
                    break;
                case 8:
                    if (!Distinto){
                        progress.cancel();
                        //doExit();
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

    private void procesar_registro(){

        try{

            if (CantReemplazar>0){

                if (selitem.Despachar.equals("No")){
                   // msgNoDespachar("Se recomienda no despachar de este producto, ¿continuar de todas formas?");
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

                if (selitem.IdStock!=0){//gBePickingUbic.IdStock){ Corregir esto

                    Distinto =false;

                    progress.show();
                    progress.setMessage("Reservando el stock seleccionado...");

                    execws(3);

                }else{

                   progress.setMessage("Generando la tarea de cambio de estado...");
                    progress.show();
                   execws(7);

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

    protected void onResume() {
        try{
            super.onResume();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
