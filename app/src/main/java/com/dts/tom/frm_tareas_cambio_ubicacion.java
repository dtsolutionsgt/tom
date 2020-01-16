package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.clsBeCambioUbicacion;

import java.util.ArrayList;

public class frm_tareas_cambio_ubicacion extends PBase {

    private TextView lblTituloForma;
    private EditText txtTarea;
    private ListView listView;

    private ArrayList<clsBeCambioUbicacion> items= new ArrayList<clsBeCambioUbicacion>();
    private list_view_tareas_cambio_ubic adapter;
    private clsBeCambioUbicacion selitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tareas_cambio_ubicacion);

        super.InitBase();


        txtTarea = (EditText) findViewById(R.id.txtTarea);
        listView = (ListView) findViewById(R.id.listTareas);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);

        if (gl.modo_cambio==2){
            lblTituloForma.setText("Listado de tareas de cambios de estado");
        }

        setHandlers();


        clearAll();
        listItems();

    }

    private void setHandlers(){

        try{

            txtTarea.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                //Metodo para filtrar la lista o llamar al WS
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
        Cursor DT;
        clsBeCambioUbicacion vItem;

        items.clear();

        try {

            DT= null; //Asignar al dt los objetos

            if (DT.getCount()==0) return;

            DT.moveToFirst();
            while (!DT.isAfterLast()) {

                vItem = new clsBeCambioUbicacion();

                vItem.IdTareaUbicacionEnc=1;
                vItem.DescripcionMotivo="";
                vItem.IdMotivoUbicacion=1;
                vItem.estado = "";

                items.add(vItem);

                DT.moveToNext();

            }

            adapter=new list_view_tareas_cambio_ubic(this,items);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            //addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),sql);
            //mu.msgbox("listItems: "+ e.getMessage());
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

    private void Procesa_Registro(int IdTareaEnc){

        try{

            Intent intent = new Intent(this,frm_detalle_cambio_ubicacion.class);
            startActivity(intent);

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
