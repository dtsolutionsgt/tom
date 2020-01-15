package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

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

import com.dts.classes.clsBeDetalleCambioUbicacion;

import java.util.ArrayList;

public class frm_detalle_cambio_ubicacion extends PBase {

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

        setHandlers();


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

                    //Escribir aquí la mágia.

                    adapter.refreshItems();

                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }


    }
}
