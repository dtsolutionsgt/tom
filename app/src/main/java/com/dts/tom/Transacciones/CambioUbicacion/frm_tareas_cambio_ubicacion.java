package com.dts.tom.Transacciones.CambioUbicacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacionList;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_enc;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_encList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.CambioUbicacion.list_view_tareas_cambio_ubic;
import com.dts.tom.Transacciones.Recepcion.frm_detalle_ingresos;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;

public class frm_tareas_cambio_ubicacion extends PBase {

    private TextView lblTituloForma,lblRegs;
    private EditText txtTarea;
    private ListView listView;
    private String wserror;
    private boolean errflag;
    private int pIdTarea=0;
    boolean Modo=false;

    private WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private clsBeMotivo_ubicacionList pListBeMotivoUbicacion = new clsBeMotivo_ubicacionList();

    private clsBeTrans_ubic_hh_encList pListBeTransUbicHhEnc = new clsBeTrans_ubic_hh_encList();
    private clsBeTrans_ubic_hh_enc pBeTareasCambioHH = new clsBeTrans_ubic_hh_enc();

    private list_view_tareas_cambio_ubic adapter;

    private ArrayList<clsBeTrans_ubic_hh_enc> pListBeTareasCambioHH= new ArrayList<clsBeTrans_ubic_hh_enc>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tareas_cambio_ubicacion);

        super.InitBase();

        ws = new WebServiceHandler(frm_tareas_cambio_ubicacion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtTarea = (EditText) findViewById(R.id.txtTarea);
        listView = (ListView) findViewById(R.id.listTareas);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblRegs = (TextView) findViewById(R.id.lblRegs);

        Modo = (gl.modo_cambio==1?true:false);

        lblTituloForma.setText(String.format("Listado de tareas de cambios de %s",(Modo==true?"ubicación":"estado")));

        setHandlers();

        clearAll();

        ProgressDialog("Cargando forma");

        Load();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void Load(){

        try{
            execws(2);
        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }
    }

    private void setHandlers(){

        try{

            txtTarea.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                pIdTarea=Integer.parseInt(txtTarea.getText().toString());
                                execws(2);
                                return true;
                        }
                    }
                    return false;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_ubic_hh_enc vItem = (clsBeTrans_ubic_hh_enc) lvObj;

                    adapter.setSelectedIndex(position);

                    Procesa_Registro(vItem.IdTareaUbicacionEnc);

                    adapter.refreshItems();

                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void clearAll() {
        try{
          /*  for (int i = 0; i < items.size(); i++ ) {
                items.get(i);
            }*/
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void Procesa_Registro(int IdTarea){

        try{

            gl.IdTareaUbicEnc =IdTarea;
            gl.tareaenc = pBeTareasCambioHH;

            Intent intent = new Intent(this, frm_detalle_cambio_ubicacion.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }


    }

    private void Llena_Tareas_Ubicacion(int pIdTarea){

        clsBeTrans_ubic_hh_enc vItem;
        pListBeTareasCambioHH.clear();

        try{

            progress.setMessage("Cargando tareas de cambio de ubicación");

           if(!pListBeTransUbicHhEnc.items.isEmpty() && pListBeTransUbicHhEnc.items!=null ){

               for (int i = pListBeTransUbicHhEnc.items.size()-1; i>=0; i--) {

                   vItem = new clsBeTrans_ubic_hh_enc();

                   vItem.IdTareaUbicacionEnc = pListBeTransUbicHhEnc.items.get(i).getIdTareaUbicacionEnc();
                   vItem.DescripcionMotivo = pListBeTransUbicHhEnc.items.get(i).getDescripcionMotivo();
                   vItem.Observacion = pListBeTransUbicHhEnc.items.get(i).getObservacion();
                   vItem.Estado = pListBeTransUbicHhEnc.items.get(i).getEstado();

                   pListBeTareasCambioHH.add(vItem);

               }

               lblRegs.setText("Regs: "+pListBeTransUbicHhEnc.items.size());
           }

            adapter=new list_view_tareas_cambio_ubic(this,pListBeTareasCambioHH);
            listView.setAdapter(adapter);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    public void Cambio_Ubicacion_Nueva(View view){

        try{

            gl.IdTareaUbicEnc =0;

            Intent intent = new Intent(this, frm_cambio_ubicacion_ciega.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
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
                        callMethod("Get_Motivos_Ubicacion_For_HH");
                        break;
                    case 2:
                        callMethod("Get_All_Cambio_Ubic_By_IdBodega_And_IdOperador","pIdBodega",gl.IdBodega,
                                "pIdOperador",gl.IdOperador,
                                "pIdTarea",pIdTarea,
                                "cambio_estado",!Modo);
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
                    processMotivosUbiHH();break;
                case 2:
                    processTareasCambioUbicacion();break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processMotivosUbiHH(){

        try {

            progress.setMessage("Obteniendo Motivos de ubicación en HH");

            pListBeMotivoUbicacion = xobj.getresult(clsBeMotivo_ubicacionList.class,"Get_Motivos_Ubicacion_For_HH");

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processTareasCambioUbicacion(){

        try {

            progress.setMessage("Obteniendo Tareas de cambio de ubicación en HH");

            pListBeTransUbicHhEnc = xobj.getresult(clsBeTrans_ubic_hh_encList.class,"Get_All_Cambio_Ubic_By_IdBodega_And_IdOperador");

            if(pListBeTransUbicHhEnc!=null){
                if(pListBeTransUbicHhEnc.items!=null){
                    Llena_Tareas_Ubicacion(pIdTarea);
                }
            }

            progress.cancel();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_tareas_cambio_ubicacion.super.finish();
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
        msgAskExit(String.format("Está seguro de salir de las tareas de cambios de %s",(Modo==true?"ubicación":"estado")));
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit(String.format("Está seguro de salir de las tareas de cambios de %s",(Modo==true?"ubicación":"estado")));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    protected void onResume() {
        try{
            super.onResume();

            //listItems(); //Actualiza los items al regresar.

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }
}
