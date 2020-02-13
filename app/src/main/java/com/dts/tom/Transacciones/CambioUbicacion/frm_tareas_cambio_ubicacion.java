package com.dts.tom.Transacciones.CambioUbicacion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.clsBeCambioUbicacion;
import com.dts.classes.clsBetrans_ubic_hh_enc;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.CambioUbicacion.list_view_tareas_cambio_ubic;
import com.dts.ws.IvanWebService;

import java.util.ArrayList;

public class frm_tareas_cambio_ubicacion extends PBase {

    private TextView lblTituloForma,lblRegs;
    private EditText txtTarea;
    private ListView listView;
    private String wserror;
    private boolean errflag;
    private int pIdTarea=0;
    boolean Modo=false;

    clsBeCambioUbicacion[] BeListCambioUbicacion=new clsBeCambioUbicacion[5];

    private ArrayList<clsBeCambioUbicacion> items= new ArrayList<clsBeCambioUbicacion>();
    private list_view_tareas_cambio_ubic adapter;
    private clsBeCambioUbicacion selitem;
    private ArrayList<clsBetrans_ubic_hh_enc> pListBeTareasCambioHH= new ArrayList<clsBetrans_ubic_hh_enc>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tareas_cambio_ubicacion);

        super.InitBase();

        txtTarea = (EditText) findViewById(R.id.txtTarea);
        listView = (ListView) findViewById(R.id.listTareas);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblRegs = (TextView) findViewById(R.id.lblRegs);

        if (gl.modo_cambio==2){
            lblTituloForma.setText("Listado de tareas de cambios de estado");
        }

        if(gl.modo_cambio==2){
            Modo=true;
        }

        setHandlers();

        clearAll();
        Load_Cambio();

    }

    private void setHandlers(){

        try{

            txtTarea.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                Llena_Tareas_Ubicacion(Integer.getInteger(txtTarea.getText().toString()));
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
                    clsBeCambioUbicacion vItem = (clsBeCambioUbicacion) lvObj;

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

    private void listItems(){
        clsBeCambioUbicacion vItem;

        items.clear();

        try {

            for (int i = 0; i < BeListCambioUbicacion.length; i++ ) {

                vItem = new clsBeCambioUbicacion();

                vItem.IdTareaUbicacionEnc=BeListCambioUbicacion[i].IdTareaUbicacionEnc;
                vItem.DescripcionMotivo=BeListCambioUbicacion[i].DescripcionMotivo;
                vItem.IdMotivoUbicacion=BeListCambioUbicacion[i].IdMotivoUbicacion;
                vItem.Observacion=BeListCambioUbicacion[i].Observacion;
                vItem.Estado = BeListCambioUbicacion[i].Estado;

                items.add(vItem);
                //Load_Cambio();
            }

            lblRegs.setText("Regs: "+ items.size());

            adapter=new list_view_tareas_cambio_ubic(this,items);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),sql);
            mu.msgbox("listItems: "+ e.getMessage());
        }

    }

    private void clearAll() {
        try{
            for (int i = 0; i < items.size(); i++ ) {
                items.get(i);
            }
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void Procesa_Registro(int IdTarea){

        try{

            gl.IdTareaUbicEnc =0;
            gl.IdTareaUbicEnc =IdTarea;
            gl.tareaenc = pListBeTareasCambioHH.get(IdTarea);

            Intent intent = new Intent(this, frm_detalle_cambio_ubicacion.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }


    }

    private void Llena_Tareas_Ubicacion(int pIdTarea){

        //BeListCambioUbicacion = new clsBeCambioUbicacion[1];

        try{

            //pListBeTareasCambioHH = null;//Get_All_Cambio_Ubic_By_IdBodega_And_IdOperador(gl.IdBodega,gl.IdBodega,pIdTarea,Modo);

            if (BeListCambioUbicacion.length > 0){

                listItems();

                /*if ((pIdTarea>0) & (BeListCambioUbicacion.length==1)){

                    Procesa_Registro(pIdTarea);

                }*/

            }else{
                if (pIdTarea != 0) {
                    mu.msgbox("La tarea #" + pIdTarea + " no existe");
                }
            }

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

    private  void Load_Cambio(){
        int idUbicRecep;

        try{

            wsexecute();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    //region WebService Thread Core

    private void wsexecute() {
        wserror="";
        frm_tareas_cambio_ubicacion.AsyncCallWS wstask = new frm_tareas_cambio_ubicacion.AsyncCallWS();
        wstask.execute();
    }

    private void wsExecute() {

        IvanWebService iws;
        String mResult;

        errflag=false;wserror="";
        iws = new IvanWebService("http://192.168.1.6/WSTOMHH_QA/TOMHHWS.asmx");

        try {

            BeListCambioUbicacion = new clsBeCambioUbicacion[5];

           iws.call("Get_All_Cambio_Ubic_By_IdBodega_And_IdOperador","pIdBodega",gl.IdBodega,"pIdOperador",1,"pIdTarea",pIdTarea,"cambio_estado",Modo);

            BeListCambioUbicacion=(clsBeCambioUbicacion[]) iws.getReturnValue(BeListCambioUbicacion.getClass());


            if (BeListCambioUbicacion.length > 0){
                //Llena_Tareas_Ubicacion(pIdTarea);

            }


        }catch (Exception e)   {
            errflag=true;
            wserror=e.getMessage();
        }


        String ss=iws.mResult;
        String ast=iws.argstr;

    }

    private void wsFinished()  {
        try {
            if (errflag) msgbox(wserror);else toast("OK");
        } catch (Exception e) {
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                wsExecute();
            } catch (Exception e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                wsFinished();
                Llena_Tareas_Ubicacion(0);
            } catch (Exception e) {
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}


    }

    //endregion

    protected void onResume() {
        try{
            super.onResume();

            //listItems(); //Actualiza los items al regresar.

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }
}
