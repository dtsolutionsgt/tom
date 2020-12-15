package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico_vw;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class frm_inv_cic_add extends PBase {

    private ImageView imgDate;
    private EditText txtUbic,txtProd,txtLote1,txtCantContada,txtPesoContado,dtpVence;
    private Spinner cboEstado,cboPres;
    private TextView txtlote_cic,lblCantStock,lblUM,lblUbic1,lblProd,txtFecha_cic,txtpeso_cic,lbltitulo_cic;
    private int idPresentacion;
    private int year;
    private int month;
    private int day;
    private int IdProductoBodega,idubic;
    private double ocant, opeso,vFactor;
    private String Resultado;
    private ArrayList<String> bodlist= new ArrayList<String>();
    private  clsBeTrans_inv_ciclico_vw pitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_add);
        super.InitBase();


        txtUbic = findViewById(R.id.txtUbic);
        txtProd = findViewById(R.id.txtProd);
        lblUbic1 = findViewById(R.id.lblUbic1);
        lblProd = findViewById(R.id.lblProd);


        cboEstado = findViewById(R.id.cboEstado);
        cboPres = findViewById(R.id.cboPres);
        txtLote1 = findViewById(R.id.txtLote1);
        dtpVence = (EditText) findViewById(R.id.dtpVence);

        lblCantStock = findViewById(R.id.lblCantStock);
        txtCantContada = findViewById(R.id.txtCantContada);
        txtPesoContado = findViewById(R.id.txtPesoContado);
        lblUM = findViewById(R.id.lblUM);


        txtlote_cic = findViewById(R.id.lblNLote);
        txtFecha_cic = findViewById(R.id.lblNVence);
        imgDate = findViewById(R.id.imgDate);
        txtpeso_cic = findViewById(R.id.txtpeso_cic);
        lbltitulo_cic = findViewById(R.id.lbltitulo_cic);

        idPresentacion =0;
        vFactor = 0.00;
        //idprod=0;
        IdProductoBodega = 0;
        idubic = 0;

        Load();

        setHandlers();

    }

    private void setHandlers() {
        try{

            txtProd.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        if(txtProd.getText().toString().trim().isEmpty()){

                            toast("Ingrese código de producto");

                        }else {

                            Scan_Codigo_Producto();
                        }
                    }
                    return false;
                }

            });

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

                if(gl.inv_ciclico.Factor.toString().isEmpty() || gl.inv_ciclico.Factor ==0){
                    vFactor = 0;
                }else {
                    vFactor = gl.inv_ciclico.Factor;
                }

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
            cboEstado.setAdapter(EstadosAdapter);
            if (bodlist.size()>0) cboEstado.setSelection(index-1);

            txtUbic.setText(gl.inv_ciclico.NoUbic +"");
            idubic = gl.inv_ciclico.NoUbic;

            lblUbic1.setTypeface(null, Typeface.BOLD);
            lblUbic1.setText(gl.inv_ciclico.Ubic_nombre +"");
            lblProd.setTypeface(null, Typeface.BOLD);
            lblProd.setText(gl.inv_ciclico.Codigo +" - "+ gl.inv_ciclico.Producto_nombre);
            txtLote1.setText(gl.inv_ciclico.Lote+"");
            dtpVence.setText(gl.inv_ciclico.Fecha_Vence);

            //GT30112020 se llena spinner con presentación del producto
            List<String> spinnerArray =  new ArrayList<String>();
            spinnerArray.add(gl.inv_ciclico.Pres);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboPres.setAdapter(dataAdapter);

            if(gl.inv_ciclico.IdPresentacion == 0){

                lblUM.setText(gl.inv_ciclico.Pres);
            }else{


                String stringDecimal = String.format("%.6f", gl.inv_ciclico.Factor);

                lblUM.setText(gl.inv_ciclico.Pres + "->" + stringDecimal);
            }


            if( gl.pprod.Control_lote){
                txtlote_cic.setVisibility(TextView.VISIBLE);
                txtLote1.setVisibility(TextView.VISIBLE);
            }else{
                txtlote_cic.setVisibility(TextView.INVISIBLE);
                txtLote1.setVisibility(TextView.INVISIBLE);
            }


            if(gl.pprod.Control_vencimiento){
                txtFecha_cic.setVisibility(TextView.VISIBLE);
                imgDate.setVisibility(TextView.VISIBLE);
                dtpVence.setVisibility(TextView.VISIBLE);
            }else{
                txtFecha_cic.setVisibility(TextView.INVISIBLE);
                imgDate.setVisibility(TextView.INVISIBLE);
                dtpVence.setVisibility(TextView.INVISIBLE);
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


            txtCantContada.setText("0");

            if(!gl.inv_ciclico.cantidad.equals(0.00)){

                if(idPresentacion == 0){

                    String stringDecimal = String.format("%.6f", gl.inv_ciclico.cantidad);
                    txtCantContada.setText(stringDecimal);

                }else{

                    double resultado = gl.inv_ciclico.cantidad / vFactor;
                    String stringDecimal = String.format("%.6f", resultado);
                    txtCantContada.setText(stringDecimal);
                }
            }
        }

    }

    private void Scan_Codigo_Producto() {

        if(gl.inv_ciclico.codigo_producto == null){
            toast("¡Producto no existe!");
        }else{

            if(!gl.inv_ciclico.Codigo.equals(txtProd.getText().toString().trim())){

                toast("El código de producto no es válido");
                txtProd.requestFocus();
            }else{

                IdProductoBodega = gl.inv_ciclico.IdProductoBodega;

            }
        }


        if(IdProductoBodega == gl.inv_ciclico.IdProductoBodega){

           }
        else{
            if(!buscaproducto(IdProductoBodega, txtProd.getText().toString().trim())){

                toast("¿Producto no pertence a esta ubicación, Registrar de todas formas?");

            }
        }

    }

    private boolean buscaproducto(int idprod, String prodtxt) {

        boolean respuesta = false;
        int ii, idu, idp;

        for (ii = 0; ii < gl.reconteo_list.size() - 1; ii++) {

            if (gl.reconteo_list.get(ii).IdUbicacion == idubic && gl.reconteo_list.get(ii).IdProductoBodega == idprod) {

                txtUbic.setText(idubic + "");
                respuesta = true;
                break;
            }
        }

        return respuesta;
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

                        dtpVence.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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

    public void btnGuardar(View view) {

        if(txtUbic.getText().toString().trim().isEmpty()){
            toast("¡Ubicacion incorrecta!");
        }else if(txtProd.getText().toString().trim().isEmpty()){
            toast("¡Producto incorrecto!");
        }else if(txtCantContada.getText().toString().trim().isEmpty() || txtCantContada.getText().toString().trim().equals("0")){
            toast("¡Cantidad incorrecta!");
        }else if (gl.pprod.Control_lote){
            if(txtLote1.getText().toString().trim().isEmpty()){
                toast("¡Lote incorrecto!");
            }
        }else if(gl.inv_ciclico.control_peso){
            if(txtPesoContado.getText().toString().trim().isEmpty()){
                toast("¡Peso incorrecto!");
            }
        }else{

            if( gl.pprod.Control_lote){
              String lote= txtLote1.getText().toString().trim();
            }

            if(gl.pprod.Control_vencimiento){

                String fecha_vence = du.convierteFecha(dtpVence.getText().toString().trim());
            }

            pitem= new clsBeTrans_inv_ciclico_vw();

            toast("¡Todo bien, guardar!");
        }
    }
}