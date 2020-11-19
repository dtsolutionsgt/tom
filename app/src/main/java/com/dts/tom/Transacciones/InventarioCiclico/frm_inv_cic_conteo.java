package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prodList;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBeTrans_inv_enc_reconteo;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBeTrans_inv_enc_reconteoList;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramoList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;


public class frm_inv_cic_conteo extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtBuscFiltro;
    private ListView listCiclico;
    private ProgressDialog progress;
    private Integer idreconteo,tareapos, idtarea, idubic, idprod, nidubic, nidprod, vIdProductoBodega;
    private boolean esconteo, lic_plate, noubicflag, nostockflag, ProductosMultiples;
    private String lplate, LoteSel,FechaVencSel,PresSel,ProdSel;
    private Integer ubicid, nubicid,IdUbicacionSel;

    private clsBeProducto prod = new clsBeProducto();
    private clsBeProducto pprod = new clsBeProducto();
    private clsBeProducto nprod = new clsBeProducto();
    private clsBeProducto npprod = new clsBeProducto();
    private clsBeProducto pBeProductoNuevo = new clsBeProducto();

    private clsBeProducto tProd = new clsBeProducto();
    private clsBeTrans_inv_tramoList Listtramos = new clsBeTrans_inv_tramoList();
    private clsBeTrans_inv_enc_reconteo reconteo = new clsBeTrans_inv_enc_reconteo();
    private clsBeTrans_inv_enc_reconteoList reconteos = new clsBeTrans_inv_enc_reconteoList();

    private clsBeTrans_inv_stock_prodList InvTeorico = new clsBeTrans_inv_stock_prodList();
    private clsBeTrans_inv_stock_prodList InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_conteo);

        super.InitBase();

        txtBuscFiltro = findViewById(R.id.txtBuscFiltro);
        listCiclico = findViewById(R.id.listCiclico);
        idreconteo =0;
        tareapos= 0;
        esconteo = true;
        ProductosMultiples = false;
        LoteSel="";
        FechaVencSel = "19000101";
        PresSel="";
        IdUbicacionSel=0;
        ProdSel="";

        ws = new WebServiceHandler(frm_inv_cic_conteo.this,gl.wsurl);
        xobj = new XMLObject(ws);

        ProgressDialog("Cargando conteo ciclico.");

        setHandles();

       execws(1);

    }

    private void setHandles() {

        try{

            txtBuscFiltro.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (!txtBuscFiltro.getText().toString().isEmpty()){
                            //IngUbic=true;
                            //execws(2);
                        }
                    }

                    return false;
                }
            });

        }catch(Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
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
                        callMethod("Inventario_Ciclico_ReConteos","pIdenc",BeInvEnc.Idinventarioenc);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtBuscFiltro.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                }

               progress.cancel();

            } catch (Exception e) {
                //progress.cancel();
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
                    processReConteos();
                    break;
                case 2:
                    //processUbic();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processReConteos() {
        try{

            reconteos = xobj.getresult(clsBeTrans_inv_enc_reconteoList.class,"Inventario_Ciclico_ReConteos");

            if (reconteos != null){

                if(reconteos.items != null){
                    if(reconteos.items.size()>0){

                    }else{
                        idreconteo = 0;
                    }

                    esconteo = false;
                    idreconteo = 0;

                    //set lbllblConteo ?
                }

            }

            ListaTareas();

        }
        catch (Exception e){
            mu.msgbox("processReConteos:"+e.getMessage());
        }
    }

    private void ListaTareas() {

        if(BeInvEnc.Idinventarioenc != 0){

            Integer idprodbod = 0;
            ubicid = 0;
            lic_plate = false;
            lplate = "";

            clsBeProducto tProd;

            try{

                Integer Idx = 0;

                if(txtBuscFiltro.getText().toString() !=""){

                }


            }
            catch (Exception e){
                mu.msgbox("processReConteos:"+e.getMessage());
            }

        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir?");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Exit(View view) {
        msgAskExit("Está seguro de salir");
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

                    /*BeInvTramo = new clsBeTrans_inv_tramo();
                    BeUbic = new clsBeBodega_ubicacion();
                    Listtramos = new clsBeTrans_inv_tramoList();
                    BeListTramos = new ArrayList<clsBeTrans_inv_tramo>();
                    TipoConteo = 0;*/

                    frm_inv_cic_conteo.super.finish();
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

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

}