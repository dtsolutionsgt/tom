package com.dts.tom.Transacciones.InventarioCiclico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class frm_inv_cic_add extends PBase {

    private ImageView imgDate;
    private EditText cmbVence_cic;
    private EditText etubicacion_cic,etproducto,etlote_cic,txtCantContada,txtPesoContado;
    private Spinner cmbEstado_cic,cmbPresent_cic;
    private TextView txtlote_cic,lblCantStock,lblUM,txtmsg,txtprentacion_cic,txtFecha_cic,txtpeso_cic,lbltitulo_cic;
    private int idPresentacion;
    private int year;
    private int month;
    private int day;
    private ArrayList<String> bodlist= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_add);
        super.InitBase();

        cmbVence_cic = (EditText) findViewById(R.id.cmbVence_cic);
        etubicacion_cic = findViewById(R.id.etubicacion_cic);
        etproducto = findViewById(R.id.etproducto);
        txtlote_cic = findViewById(R.id.txtlote_cic);
        etlote_cic = findViewById(R.id.etlote_cic);
        txtCantContada = findViewById(R.id.txtCantContada);
        txtPesoContado = findViewById(R.id.txtPesoContado);
        cmbEstado_cic = findViewById(R.id.cmbEstado_cic);
        cmbPresent_cic = findViewById(R.id.cmbPresent_cic);
        lblCantStock = findViewById(R.id.lblCantStock);
        lblUM = findViewById(R.id.lblUM);
        txtmsg = findViewById(R.id.txtmsg);
        txtprentacion_cic = findViewById(R.id.txtprentacion_cic);
        txtFecha_cic = findViewById(R.id.txtFecha_cic);
        imgDate = findViewById(R.id.imgDate);
        txtpeso_cic = findViewById(R.id.txtpeso_cic);
        lbltitulo_cic = findViewById(R.id.lbltitulo_cic);

        idPresentacion =0;

        setHandlers();
        Load();

    }

    private void setHandlers() {
        try{

        }
        catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }
    }

    private void Load() {

        int index = 0;

        if(gl.inv_ciclico !=null){

         //validaciones para obtener lista de estados por idPropietario
            if(gl.lista_estados != null){

                if(gl.lista_estados.items != null){

                    bodlist.clear();
                    for (int i = 0; i <gl.lista_estados.items.size(); i++)
                    {
                        bodlist.add(gl.lista_estados.items.get(i).IdEstado + " - " + gl.lista_estados.items.get(i).Nombre);
                    }

                    //se busca el IdEstado según el estado del registro, para setear el spinner
                    for (int j = 0; j <gl.lista_estados.items.size(); j++)
                    {
                        if(gl.lista_estados.items.get(j).Nombre.equals(gl.inv_ciclico.Estado))
                        index= gl.lista_estados.items.get(j).IdEstado;
                    }
                }
            }

            ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist);
            EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstado_cic.setAdapter(EstadosAdapter);
            if (bodlist.size()>0) cmbEstado_cic.setSelection(index-1);

            etubicacion_cic.setText(gl.inv_ciclico.NoUbic +"");
            txtmsg.setTypeface(null, Typeface.BOLD);
            txtmsg.setText(gl.inv_ciclico.Ubic_nombre +"");
            txtprentacion_cic.setTypeface(null, Typeface.BOLD);
            txtprentacion_cic.setText(gl.inv_ciclico.Codigo +" - "+ gl.inv_ciclico.Producto_nombre);
            etlote_cic.setText(gl.inv_ciclico.Lote+"");
            cmbVence_cic.setText(gl.inv_ciclico.Fecha_Vence);

            //GT30112020 se llena spinner con presentación del producto
            List<String> spinnerArray =  new ArrayList<String>();
            spinnerArray.add(gl.inv_ciclico.Pres);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresent_cic.setAdapter(dataAdapter);

            if(gl.inv_ciclico.IdPresentacion == 0){

                lblUM.setText(gl.inv_ciclico.Pres);
            }else{


                String stringDecimal = String.format("%.6f", gl.inv_ciclico.Factor);

                lblUM.setText(gl.inv_ciclico.Pres + "->" + stringDecimal);
            }


            if( gl.pprod.Control_lote){
                txtlote_cic.setVisibility(TextView.VISIBLE);
                etlote_cic.setVisibility(TextView.VISIBLE);
            }else{
                txtlote_cic.setVisibility(TextView.INVISIBLE);
                etlote_cic.setVisibility(TextView.INVISIBLE);
            }


            if(gl.pprod.Control_vencimiento){
                txtFecha_cic.setVisibility(TextView.VISIBLE);
                imgDate.setVisibility(TextView.VISIBLE);
                cmbVence_cic.setVisibility(TextView.VISIBLE);
            }else{
                txtFecha_cic.setVisibility(TextView.INVISIBLE);
                imgDate.setVisibility(TextView.INVISIBLE);
                cmbVence_cic.setVisibility(TextView.INVISIBLE);
            }


            if(gl.inv_ciclico.control_peso){

                txtpeso_cic.setVisibility(TextView.VISIBLE);
                lblCantStock.setVisibility(TextView.VISIBLE);
                txtPesoContado.setVisibility(TextView.VISIBLE);

            }else{
                txtpeso_cic.setVisibility(TextView.INVISIBLE);
                lblCantStock.setVisibility(TextView.INVISIBLE);
                txtPesoContado.setVisibility(TextView.INVISIBLE);
            }

            lbltitulo_cic.setText("Ubic # "+ gl.inv_ciclico.NoUbic);
        }

    }


    public void ChangeDate(View view) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        cmbVence_cic.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void Exit(View view) {
        frm_inv_cic_add.super.finish();
    }

    @Override
    public void onBackPressed() {

        try{

            gl.inv_ciclico = new clsBe_inv_reconteo_data();
            gl.pprod = new clsBeProducto();

            frm_inv_cic_add.super.finish();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void backward(View view) {
    }

    public void forward(View view) {
    }
}