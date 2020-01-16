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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.clsBeDetalleCambioUbicacion;

import java.util.ArrayList;

public class frm_detalle_cambio_ubicacion extends PBase {

    private TextView lblTituloForma;
    private EditText txtCodigo;
    private ListView listView;
    private CheckBox chkUbicados,chkTodos;


    private ArrayList<clsBeDetalleCambioUbicacion> items= new ArrayList<clsBeDetalleCambioUbicacion>();
    private list_adapter_detalle_cambio_ubic adapter;
    private clsBeDetalleCambioUbicacion selitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_detalle_cambio_ubicacion);

        super.InitBase();

        txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        listView = (ListView) findViewById(R.id.listDetalle);
        chkUbicados = (CheckBox) findViewById(R.id.chkUbicados);
        chkTodos = (CheckBox) findViewById(R.id.chkTodos);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);

        setHandlers();

        clearAll();
        listItems();


    }

    private void setHandlers(){

        try{


            txtCodigo.setOnKeyListener(new View.OnKeyListener(){

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

            chkUbicados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkUbicados.isChecked()==true){
                        //Acción cuando esté marcado en true el chkUbicados que sirve para filtrar todos los registros ya procesados.
                    }
                }
            });

            chkTodos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkTodos.isChecked()==true){
                        //Acción cuando esté marcado en true el chkTodos que sirve para filtrar todos los registros.
                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeDetalleCambioUbicacion vItem = (clsBeDetalleCambioUbicacion) lvObj;

                    adapter.setSelectedIndex(position);

                    Procesa_Registro(vItem.IdTareaDet);

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
        clsBeDetalleCambioUbicacion vItem;

        items.clear();

        try {

            DT= null; //Asignar al dt los objetos

            if (DT.getCount()==0) return;

            DT.moveToFirst();
            while (!DT.isAfterLast()) {

                vItem = new clsBeDetalleCambioUbicacion();

                vItem.IdTareaDet=1;
                vItem.IdStock=1;
                vItem.Codigo="";
                vItem.NombreProducto = "";
                vItem.NombrePresentacion = "";
                vItem.UbicacionOrigen = "";
                vItem.UbicacionDestino = "";
                vItem.Cantidad = 0.0;
                vItem.Recibido = 0.0;
                vItem.NombreOperador = "";

                items.add(vItem);

                DT.moveToNext();

            }

            adapter=new list_adapter_detalle_cambio_ubic(this,items);
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

    private void Procesa_Registro(int IdTareaDet){

        try{

            Intent intent = new Intent(this,frm_cambio_ubicacion_dirigida.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }


    }

}
