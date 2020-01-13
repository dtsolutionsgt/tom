package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class frm_tareas_cambio_ubicacion extends PBase {

    private EditText txtTarea;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tareas_cambio_ubicacion);

        super.InitBase();


        txtTarea = (EditText) findViewById(R.id.txtTarea);
        listView = (ListView) findViewById(R.id.listTareas);

        setHandlers();

    }

    private void setHandlers(){

        try{

            txtTarea.addTextChangedListener(new TextWatcher(){

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Aquí poner el método para ir a buscar al ws la tarea.

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
            );

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
