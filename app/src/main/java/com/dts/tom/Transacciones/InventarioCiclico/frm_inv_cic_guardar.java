package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.text.ParseException;
import java.util.Calendar;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_guardar extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ImageView imgDate;
    private int year;
    private int month;
    private int day;
    private TextView lblNUbic,lblNProd,lblNPeso,lblNLote,lblNVence;
    private EditText dtpNVence,txtNUbic,txtNProd,txtNCantContada,txtNPesoContado;
    private Spinner cboNEstado,cboNPresN,cmbLoteN;
    private clsBeTrans_inv_ciclico BeTrans_inv_ciclico;
    private int idprodbod;
    private ProgressDialog progress;
    String Estado,Presentacion,Lote, fecha_vence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_guardar);
        super.InitBase();

        lblNUbic = findViewById(R.id.lblNUbic);
        lblNProd = findViewById(R.id.lblNProd);
        lblNPeso= findViewById(R.id.lblNPeso);
        lblNLote = findViewById(R.id.lblNLote);
        lblNVence = findViewById(R.id.lblNVence);

        txtNUbic = findViewById(R.id.txtNUbic);
        txtNProd = findViewById(R.id.txtNProd);
        cboNEstado = findViewById(R.id.cboNEstado);
        cboNPresN = findViewById(R.id.cboNPresN);
        cmbLoteN = findViewById(R.id.cmbLoteN);
        imgDate = findViewById(R.id.imgDate);
        dtpNVence= findViewById(R.id.dtpNVence);
        txtNCantContada = findViewById(R.id.txtNCantContada);
        txtNPesoContado = findViewById(R.id.txtNPesoContado);

        ws = new WebServiceHandler(frm_inv_cic_guardar.this,gl.wsurl);
        xobj = new XMLObject(ws);

        Load();
        
        setHandlers();

    }

    private void Load() {

        try{

            if(gl.pBeProductoNuevo != null){

                txtNProd.setText(gl.pBeProductoNuevo.Nombre);
                lblNProd.setText(gl.pBeProductoNuevo.Codigo + " " + gl.pBeProductoNuevo.Nombre);

                txtNUbic.requestFocus();

                LlenaCampos_Producto_Nuevo();

            }else{

                try {
                    fecha_vence = String.valueOf(du.getFechaActual());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dtpNVence.setText(fecha_vence);

                lblNPeso.setVisibility(TextView.INVISIBLE);
                lblNLote.setVisibility(TextView.INVISIBLE);
                lblNVence.setVisibility(TextView.INVISIBLE);
                txtNPesoContado.setVisibility(EditText.INVISIBLE);
                dtpNVence.setVisibility(EditText.INVISIBLE);
                imgDate.setVisibility(ImageView.INVISIBLE);

                txtNUbic.requestFocus();
            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
        }

    }

    private void LlenaCampos_Producto_Nuevo() {

        nllenaEstado();

    }

    private void nllenaEstado() {
    }

    private void setHandlers() {

        cboNEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Estado = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                toast("No hay estado seleccionado");
            }
        });

        cboNPresN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Presentacion = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                toast("No hay estado seleccionado");
            }
        });

        cmbLoteN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Lote = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                toast("No hay estado seleccionado");
            }
        });

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
                        //callMethod("Get_Datos_Maestros_Inv_Fam","pIdenc",BeInvEnc.Idpropietario, "pListFamilia",ctableFamilia);
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
                    //Llena_Combos_Familia();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
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

                        dtpNVence.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

}