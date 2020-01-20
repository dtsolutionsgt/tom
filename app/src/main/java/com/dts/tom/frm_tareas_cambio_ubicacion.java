package com.dts.tom;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.clsBeCambioUbicacion;
import com.dts.classes.clsBetrans_ubic_hh_enc;

import java.util.ArrayList;

public class frm_tareas_cambio_ubicacion extends PBase {

    private TextView lblTituloForma,lblRegs;
    private EditText txtTarea;
    private ListView listView;

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

        setHandlers();


        clearAll();
        Llena_Tareas_Ubicacion(0);

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

            for (int i = 0; i < pListBeTareasCambioHH.size(); i++ ) {

                vItem = new clsBeCambioUbicacion();

                vItem.IdTareaUbicacionEnc=pListBeTareasCambioHH.get(i).IdTareaUbicacionEnc;
                vItem.DescripcionMotivo=pListBeTareasCambioHH.get(i).Descripcion;
                vItem.IdMotivoUbicacion=pListBeTareasCambioHH.get(i).IdMotivoUbicacion;
                vItem.estado = pListBeTareasCambioHH.get(i).estado;

                items.add(vItem);

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

            Intent intent = new Intent(this,frm_detalle_cambio_ubicacion.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }


    }

    private void Llena_Tareas_Ubicacion(int pIdTarea){

        boolean Modo=false;

        pListBeTareasCambioHH = new ArrayList<clsBetrans_ubic_hh_enc>();

        try{

            if(gl.modo_cambio==2){
                Modo=true;
            }

            pListBeTareasCambioHH = null;//Get_All_Cambio_Ubic_By_IdBodega_And_IdOperador(gl.IdBodega,gl.IdBodega,pIdTarea,Modo);

            if (pListBeTareasCambioHH.size() > 0){

                listItems();

                if ((pIdTarea>0) & (pListBeTareasCambioHH.size()==1)){

                    Procesa_Registro(pIdTarea);

                }

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

            Intent intent = new Intent(this,frm_cambio_ubicacion_ciega.class);
            startActivity(intent);


        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private  void Load_Cambio(){
        int idUbicRecep;

        try{



        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
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
