package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaBase;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_guardar extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ImageView imgDate;
    private int year;
    private int month;
    private int day;
    private TextView lblNUbic,lblNProd,lblNPeso,lblNLote,lblNVence,txtpresent_cic;
    private EditText dtpNVence,txtNUbic,txtNProd,txtNCantContada,txtNPesoContado;
    private Spinner cboNEstado,cboNPresN,cmbLoteN;
    private clsBeTrans_inv_ciclico BeTrans_inv_ciclico;
    private int idprodbod,nidubic;
    private ProgressDialog progress;
    String Estado,Presentacion,Lote, fecha_vence;

    private clsBeProducto_estadoList lista_estados = new clsBeProducto_estadoList();
    private ArrayList<String> bodlist= new ArrayList<String>();
    private clsBeBodega_ubicacion nubic = new clsBeBodega_ubicacion();

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
        txtpresent_cic = findViewById(R.id.txtpresent_cic);

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
    //Private Sub conteoNuevo()
        try{

            if(gl.pBeProductoNuevo != null){


                LlenaCampos_Producto_Nuevo();

                txtNProd.setText(gl.pBeProductoNuevo.Nombre);
                lblNProd.setText(gl.pBeProductoNuevo.Codigo + " " + gl.pBeProductoNuevo.Nombre);
                txtNUbic.requestFocus();

                  try {
                    fecha_vence = du.getFecha();
                    dtpNVence.setText(fecha_vence);
                  } catch (ParseException e) {
                        e.printStackTrace();
                  }



            }else{

                try {
                    fecha_vence = du.getFecha();
                    dtpNVence.setText(fecha_vence);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


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

        //Llena Estado
        execws(1);

        cboNPresN.setVisibility(View.INVISIBLE);
        txtpresent_cic.setVisibility(View.INVISIBLE);
        txtNProd.setEnabled(false);
        cmbLoteN.setVisibility(View.INVISIBLE);


        if(gl.pBeProductoNuevo.Control_peso){
            lblNPeso.setVisibility(View.VISIBLE);
            txtNPesoContado.setVisibility(View.VISIBLE);
        }else{
            lblNPeso.setVisibility(View.INVISIBLE);
            txtNPesoContado.setVisibility(View.INVISIBLE);
        }

        if(gl.pBeProductoNuevo.Control_lote){
            lblNLote.setVisibility(View.VISIBLE);


        }else{
            lblNLote.setVisibility(View.INVISIBLE);
        }

        if(gl.pBeProductoNuevo.Control_vencimiento){
            lblNVence.setVisibility(View.VISIBLE);
            dtpNVence.setVisibility(View.VISIBLE);
            imgDate.setVisibility(ImageView.VISIBLE);
        }else{
            lblNVence.setVisibility(View.INVISIBLE);
            dtpNVence.setVisibility(View.INVISIBLE);
            imgDate.setVisibility(ImageView.INVISIBLE);
        }
    }

    private void Llena_Estado() {

        class BodegaSort implements Comparator<clsBeProducto_estado>
        {
            public int compare(clsBeProducto_estado left, clsBeProducto_estado right)
            {
                return left.Nombre.compareTo(right.Nombre);
            }
        }

        try {

            lista_estados = xobj.getresult(clsBeProducto_estadoList.class, "Get_Estados_By_IdPropietario");

                Collections.sort(lista_estados.items, new BodegaSort());
                fillSpinEstado();

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void fillSpinEstado() {
        try
        {
            bodlist.clear();

            for (int i = 0; i <lista_estados.items.size(); i++)
            {
                bodlist.add(lista_estados.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboNEstado.setAdapter(dataAdapter);

            if (bodlist.size()>0) cboNEstado.setSelection(0);

        } catch (Exception e)
        {
            mu.msgbox( e.getMessage());
        }
    }

    private void valida_ubicacion() {
        int vIdUbic;

        try{
            nubic = xobj.getresult(clsBeBodega_ubicacion.class, "Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if(nubic != null){
                vIdUbic = nubic.IdUbicacion;
                nidubic = nubic.IdUbicacion;
                lblNUbic.setText(nubic.NombreCompleto);
                txtNProd.requestFocus();
            }else{
                toast("¡Ubicacion no existe!");
            }

        }catch (Exception e){
            mu.msgbox( e.getMessage());
        }
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

        txtNUbic.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {

                   if(txtNUbic.getText().toString().trim().isEmpty()){

                       toast("Ingrese una ubicación");

                   }else {

                       //valida úbicación nueva
                       execws(2);


                   }

                }

                return false;
            }
        });
    }

    public void guardar_cic(View view) {

        if(txtNUbic.getText().toString().trim().isEmpty()){
            toast("Ubicación no valida!");
        }else
        if(txtNCantContada.getText().toString().trim().isEmpty()){
            toast("Cantidad incorrecta!");
        }


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
                        callMethod("Get_Estados_By_IdPropietario","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtNUbic.getText().toString().trim(),"pIdBodega",idprodbod);
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
                    Llena_Estado();
                    break;
                case 2:
                    valida_ubicacion();
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