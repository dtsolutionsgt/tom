package com.dts.tom.Transacciones.InventarioInicial;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramoList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_tramos_inv_ini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_ini_tramos extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtTramUbic;
    private ListView listInvTramos;
    private Button cmdTraDet,cmdTraVer,cmdTraRec,btnConteo,btnVerificacion,btnBack;

    private ProgressDialog progress;
    private Dialog dialog;

    private clsBeTrans_inv_tramoList Listtramos = new clsBeTrans_inv_tramoList();
    private ArrayList<clsBeTrans_inv_tramo> BeListTramos = new ArrayList<clsBeTrans_inv_tramo>();
    private list_adapt_tramos_inv_ini adapter;
    public static clsBeTrans_inv_tramo BeInvTramo;
    public static clsBeBodega_ubicacion BeUbic = new clsBeBodega_ubicacion();

    private boolean pMoverAPanelDeConteo;
    private int TipoConteo=0;
    private int cnt_det,cnt_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_tramos);
        super.InitBase();

        txtTramUbic = (EditText)findViewById(R.id.txtTramUbic);

        listInvTramos = (ListView) findViewById(R.id.listInvTramos);

        cmdTraDet = (Button) findViewById(R.id.cmdTraDet);
        cmdTraVer = (Button) findViewById(R.id.cmdTraVer);
        cmdTraRec = (Button) findViewById(R.id.cmdTraRec);

        ws = new WebServiceHandler(frm_inv_ini_tramos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        ProgressDialog("Cargando tramos de inventario");

        setHandles();

        execws(1);

    }

    private void setHandles(){

        try{

            listInvTramos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        Object lvObj = listInvTramos.getItemAtPosition(position);
                        clsBeTrans_inv_tramo sitem = (clsBeTrans_inv_tramo) lvObj;
                        BeInvTramo = new clsBeTrans_inv_tramo();
                        BeInvTramo = BeListTramos.get(position);


                        selid = sitem.Idtramo;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        Procesa_reg();

                    }

                }

            });

            txtTramUbic.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (!txtTramUbic.getText().toString().isEmpty()){
                            execws(2);
                        }
                    }

                    return false;
                }
            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Procesa_reg(){
        Seleccion_Tipo(this);
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void Procesar_Registro(){

        try{

            if (TipoConteo==1) {
                browse=1;
                startActivity(new Intent(this, frm_inv_ini_conteo.class));
            }else{

                browse=2;
                startActivity(new Intent(this, frm_inv_ini_verificacion.class));
            }


        }catch (Exception e){
            mu.msgbox("Procesar_Registro:"+e.getMessage());
        }
    }

    private void Listar_Tramos(){
        clsBeTrans_inv_tramo vItem;
        try{

            vItem = new clsBeTrans_inv_tramo();
            BeListTramos.add(vItem);

            for (clsBeTrans_inv_tramo tramo: Listtramos.items ) {

                vItem = new clsBeTrans_inv_tramo();

                vItem = tramo;

                if (vItem.Det_estado.equals("Finalizado")){
                    cnt_det+=1;
                }

                if (vItem.Res_estado.equals("Finalizado")){
                    cnt_ver+=1;
                }

                BeListTramos.add(vItem);

            }

            int Count = BeListTramos.size()-1;
            cmdTraRec.setText("Regs:"+Count);

            cmdTraDet.setText("Det: "+cnt_det);
            cmdTraVer.setText("Ver: "+cnt_ver);

            Collections.sort(BeListTramos,new OrdenarItems());

            adapter=new list_adapt_tramos_inv_ini(this,BeListTramos);
            listInvTramos.setAdapter(adapter);

            if (Count==1){
                BeInvTramo = stream(Listtramos.items).first();
                Seleccion_Tipo(this);
            }

        }catch (Exception e){
            mu.msgbox("Listar_Tramos:"+e.getMessage());
        }
    }

    private void Seleccion_Tipo(Activity activity){

        try{

            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frm_tipo_conteo);

            btnConteo = (Button) dialog.findViewById(R.id.btnConteo);
            btnVerificacion = (Button) dialog.findViewById(R.id.btnVerificacion);
            btnBack = (Button) dialog.findViewById(R.id.btnBack);

            btnConteo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipoConteo = 1;
                    Procesar_Registro();
                }
            });

            btnVerificacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipoConteo = 2;
                    Procesar_Registro();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("Seleccion_Tipo: "+ e.getMessage());
        }
    }

    public class OrdenarItems implements Comparator<clsBeTrans_inv_tramo> {

        public int compare(clsBeTrans_inv_tramo left,clsBeTrans_inv_tramo rigth){
            //return left.CodigoProducto-rigth.IdRecepcionDet;
            return left.Idtramo-rigth.Idtramo;

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
                        callMethod("Get_All_Inventario_Tramos_By_IdTarea","pIdTarea",BeInvEnc.Idinventarioenc);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtTramUbic.getText().toString(),
                                "pIdBodega",gl.IdBodega);
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
                    processTramosInv();
                    break;
                case 2:
                    processUbic();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processTramosInv(){

        try{

            Listtramos = xobj.getresult(clsBeTrans_inv_tramoList.class,"Get_All_Inventario_Tramos_By_IdTarea");

            if (Listtramos!=null){
                if (Listtramos.items!=null){
                    Listar_Tramos();
                }else{
                    mu.msgbox("No hay tramos asignados para el inventario: "+BeInvEnc.Idinventarioenc);
                }
            }else{
                mu.msgbox("No hay tramos asignados para el inventario: "+BeInvEnc.Idinventarioenc);
            }

        }catch (Exception e){
            mu.msgbox("processTramosInv:"+e.getMessage());
        }
    }

    private void processUbic(){

        try{

            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic !=null){
                Seleccion_Tipo(this);
            }else{
                mu.msgbox("La ubicación no existe");
            }

        }catch (Exception e){
            mu.msgbox("processUbic:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void doExit(){

        BeInvTramo = new clsBeTrans_inv_tramo();
        BeUbic = new clsBeBodega_ubicacion();
        Listtramos = new clsBeTrans_inv_tramoList();
        BeListTramos = new ArrayList<clsBeTrans_inv_tramo>();
        TipoConteo = 0;

        super.finish();
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                BeInvTramo = new clsBeTrans_inv_tramo();
                BeUbic = new clsBeBodega_ubicacion();
                Listtramos = new clsBeTrans_inv_tramoList();
                BeListTramos = new ArrayList<clsBeTrans_inv_tramo>();
                TipoConteo = 0;
                execws(1);
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

}
