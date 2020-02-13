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
import com.dts.classes.clsBetrans_ubic_hh_det;

import java.util.ArrayList;
import java.util.Arrays;

public class frm_detalle_cambio_ubicacion extends PBase {

    private TextView lblTituloForma,lblRegs,lblTotal;
    private EditText txtCodigo;
    private ListView listView;
    private CheckBox chkUbicados,chkTodos;


    private ArrayList<clsBeDetalleCambioUbicacion> items= new ArrayList<clsBeDetalleCambioUbicacion>();
    private list_adapter_detalle_cambio_ubic adapter;
    private clsBeDetalleCambioUbicacion selitem;
    private ArrayList<clsBetrans_ubic_hh_det> pListBeTareasCambioDet= new ArrayList<clsBetrans_ubic_hh_det>();


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
        lblRegs = (TextView) findViewById(R.id.lblRegs);
        lblTotal = (TextView) findViewById(R.id.lblTotal);

        setHandlers();

        clearAll();
        Inicia_Tarea_Lista();


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

        clsBeDetalleCambioUbicacion vItem;

        items.clear();

        try {

            for (int i = 0; i < pListBeTareasCambioDet.size(); i++ ) {

                vItem = new clsBeDetalleCambioUbicacion();

                vItem.IdTareaDet=pListBeTareasCambioDet.get(i).IdTareaUbicacionDet;
                vItem.IdStock=pListBeTareasCambioDet.get(i).IdStock;
                vItem.Codigo=pListBeTareasCambioDet.get(i).Producto.codigo;
                vItem.NombreProducto = pListBeTareasCambioDet.get(i).Producto.nombre;
                vItem.NombrePresentacion = pListBeTareasCambioDet.get(i).ProductoPresentacion.nombre;
                vItem.UbicacionOrigen = pListBeTareasCambioDet.get(i).UbicacionOrigen.Descripcion;
                vItem.UbicacionDestino = pListBeTareasCambioDet.get(i).UbicacionDestino.Descripcion;
                vItem.Cantidad = pListBeTareasCambioDet.get(i).cantidad;
                vItem.Recibido = pListBeTareasCambioDet.get(i).recibido;
                vItem.NombreOperador = String.valueOf(pListBeTareasCambioDet.get(i).IdOperador);

                items.add(vItem);

            }

            lblRegs.setText("Regs: "+ items.size());

            adapter=new list_adapter_detalle_cambio_ubic(this,items);
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

    private void Procesa_Registro(int IdTareaDet){

        try{

            gl.IdTareaUbicDet = 0;
            gl.IdTareaUbicDet = IdTareaDet;
            gl.tareadet = pListBeTareasCambioDet.get(IdTareaDet);

            Intent intent = new Intent(this,frm_cambio_ubicacion_dirigida.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void Inicia_Tarea_Lista(){

        try{

            pListBeTareasCambioDet = new ArrayList<clsBetrans_ubic_hh_det>();

            pListBeTareasCambioDet = null; //Get_All_By_IdTransUbicEnc_And_IdOperador(gl.IdTarea, iif(chkTodos.isChecked(), 0, gl.IdOperador))

            if (pListBeTareasCambioDet.size() > 0){

                listItems();

            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

}
