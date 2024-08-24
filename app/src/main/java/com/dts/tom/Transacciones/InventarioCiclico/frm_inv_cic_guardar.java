package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prod;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prodList;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.google.common.collect.Table;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_guardar extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private TableRow tblote_cic, tblVence;
    private ImageView imgDate;
    private int year;
    private int month;
    private int day;
    private TextView lblNUbic,lblNProd,lblNPeso,lblNLote,lblNVence,txtpresent_cic;
    private EditText dtpNVence,txtNUbic,txtNProd,txtNCantContada,txtNPesoContado,txtNLote;
    private Spinner cboNEstado,cboNPresN,cmbLoteN;
    private int idprodbod,nidubic;
    private ProgressDialog progress;
    int Estado,Presentacion,nidprod;
    String Lote,fecha_vence;

    private clsBeProducto nprod = new clsBeProducto();
    private clsBeTrans_inv_ciclico BeTrans_inv_ciclico;
    private clsBeProducto_PresentacionList BeProducto_PresentacionList = new clsBeProducto_PresentacionList();
    private clsBeProducto_estadoList lista_estados = new clsBeProducto_estadoList();
    private clsBeBodega_ubicacion nubic = new clsBeBodega_ubicacion();

    private final clsBeTrans_inv_stock_prod InvTeoricoPorProducto = new clsBeTrans_inv_stock_prod();
    private clsBeTrans_inv_stock_prodList InvTeoricoPorProductoList = new clsBeTrans_inv_stock_prodList();

    private final ArrayList<String> bodlist= new ArrayList<String>();
    private final ArrayList<String> Preslist= new ArrayList<String>();
    private final ArrayList<String> Lotelist= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_guardar);
        super.InitBase();

        nidprod =0;

        lblNUbic = findViewById(R.id.lblUbic1);
        lblNProd = findViewById(R.id.lblProd);
        lblNPeso= findViewById(R.id.lblNPeso);
        lblNLote = findViewById(R.id.lblNLote);
        lblNVence = findViewById(R.id.lblNVence);
        txtpresent_cic = findViewById(R.id.txtpresent_cic);

        txtNUbic = findViewById(R.id.txtNUbic);
        txtNProd = findViewById(R.id.txtProd);
        txtNLote = findViewById(R.id.txtNLote);
        cboNEstado = findViewById(R.id.cboEstado);
        cboNPresN = findViewById(R.id.cboNPresN);
        cmbLoteN = findViewById(R.id.cmbLoteN);
        imgDate = findViewById(R.id.imgDate);
        dtpNVence= findViewById(R.id.dtpVence);
        txtNCantContada = findViewById(R.id.txtCantContada);
        txtNPesoContado = findViewById(R.id.txtPesoContado);

        tblote_cic = findViewById(R.id.tblote_cic);
        tblVence = findViewById(R.id.tblVence);

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
            tblote_cic.setVisibility(View.VISIBLE);
            lblNLote.setVisibility(View.VISIBLE);
            txtNLote.setVisibility(View.VISIBLE);

        }else{
            tblote_cic.setVisibility(View.GONE);
            lblNLote.setVisibility(View.INVISIBLE);
            txtNLote.setVisibility(View.INVISIBLE);
            //cmbLoteN.setVisibility(View.INVISIBLE);
        }

        if(gl.pBeProductoNuevo.Control_vencimiento){
            tblVence.setVisibility(View.VISIBLE);
            lblNVence.setVisibility(View.VISIBLE);
            dtpNVence.setVisibility(View.VISIBLE);
            imgDate.setVisibility(ImageView.VISIBLE);
        }else{
            tblVence.setVisibility(View.GONE);
            lblNVence.setVisibility(View.INVISIBLE);
            dtpNVence.setVisibility(View.INVISIBLE);
            imgDate.setVisibility(ImageView.INVISIBLE);
        }
    }

    //llena combobox
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

            if(lista_estados != null){
                Collections.sort(lista_estados.items, new BodegaSort());
                fillSpinEstado();
            }

        } catch (Exception e) {
            mu.msgbox( "spinner_Estados:"+ e.getMessage());
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

            //Llama a spinner Presentación
            //execws(3);

        } catch (Exception e)
        {
            mu.msgbox( e.getMessage());
        }
    }

    private void LlenaDetPresentacion(){

        class PresentacionSort implements Comparator<clsBeProducto_Presentacion>
        {
            public int compare(clsBeProducto_Presentacion left, clsBeProducto_Presentacion right)
            {
                return left.Nombre.compareTo(right.Nombre);
            }
        }
        try{
            BeProducto_PresentacionList = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if(BeProducto_PresentacionList != null){

                Collections.sort(BeProducto_PresentacionList.items, new PresentacionSort());
                fillSpinPresentacion();
            }

        }catch (Exception e){
            mu.msgbox("spinner_Presentacion:"+e.getMessage());
        }

    }

    private void fillSpinPresentacion() {
        try
        {
            Preslist.clear();

            for (int i = 0; i <BeProducto_PresentacionList.items.size(); i++)
            {
                Preslist.add(BeProducto_PresentacionList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Preslist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboNPresN.setAdapter(dataAdapter);

            if (Preslist.size()>0) cboNPresN.setSelection(0);

        } catch (Exception e)
        {
            mu.msgbox( e.getMessage());
        }
    }

    private void Llena_Lote() {

        class LoteSort implements Comparator<clsBeTrans_inv_stock_prod>
        {
            public int compare(clsBeTrans_inv_stock_prod left, clsBeTrans_inv_stock_prod right)
            {
                return left.Lote.compareTo(right.Lote);
            }
        }
        try{
            InvTeoricoPorProductoList = xobj.getresult(clsBeTrans_inv_stock_prodList.class,"Get_Inventario_Teorico_By_Codigo");

            if(InvTeoricoPorProductoList != null){

                Collections.sort(InvTeoricoPorProductoList.items, new LoteSort());
                fillSpinLote();
            }

        }catch (Exception e){
            mu.msgbox("spinner_Presentacion:"+e.getMessage());
        }

    }

    private void fillSpinLote(){

        try
        {
            Lotelist.clear();


            for (int i = 0; i <InvTeoricoPorProductoList.items.size(); i++)
            {
                Lotelist.add(InvTeoricoPorProductoList.items.get(i).Lote);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Lotelist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbLoteN.setAdapter(dataAdapter);

            if (Lotelist.size()>0) cmbLoteN.setSelection(0);

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
                lblNUbic.setText(nubic.Descripcion);
                //txtNProd.requestFocus();

                if (gl.pBeProductoNuevo.Control_lote) {
                    txtNLote.requestFocus();
                } else if (gl.pBeProductoNuevo.Control_vencimiento) {
                    dtpNVence.requestFocus();
                } else {
                    txtNCantContada.requestFocus();
                }

            }else{
                toast("¡Ubicacion no existe!");
            }

        }catch (Exception e){
            mu.msgbox( e.getMessage());
        }
    }

    private void Get_IdProdBodega() {

        try {
            idprodbod = xobj.getresult(Integer.class,"Get_IdProductoBodega_By_IdProducto_And_IdBodega");
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void Get_Producto() {

        try {
            nprod = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if(nprod == null){

                toast("El producto no existe");
            }else {
                lblNProd.setText(nprod.Nombre);
            }

            nidprod = nprod.IdProductoBodega;
            txtNCantContada.requestFocus();


        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }

    }

    private void setHandlers() {

        cboNEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Estado= lista_estados.items.get(position).IdEstado;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                toast("No hay estado seleccionado");
            }
        });

        cboNPresN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                //Presentacion = String.valueOf(position);
                Presentacion = BeProducto_PresentacionList.items.get(position).IdPresentacion;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                toast("No hay presentación seleccionado");
            }
        });

        cmbLoteN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Lote = InvTeoricoPorProductoList.items.get(position).Lote;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                toast("No hay Lote seleccionado");
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

        txtNProd.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {

                    LlenaCampos_Producto_Nuevo();

                }
                return false;
            }
        });

    }
    
    public void guardar_cic(View view) {

        if(txtNUbic.getText().toString().trim().isEmpty()){

            toast("Ubicación no valida!");
            txtNUbic.requestFocus();

        }else if(txtNCantContada.getText().toString().trim().isEmpty()){

            toast("Cantidad incorrecta!");
            txtNCantContada.requestFocus();

        }else {

            if(gl.pBeProductoNuevo == null){

                //Get_IdProdBodega
                execws(4);

                BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
                BeTrans_inv_ciclico.IdInvCiclico = 0;
                BeTrans_inv_ciclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
                BeTrans_inv_ciclico.IdStock = 0;
                BeTrans_inv_ciclico.IdProductoBodega = idprodbod;
                BeTrans_inv_ciclico.IdProductoEstado = Estado;
                BeTrans_inv_ciclico.IdProductoEst_nuevo = Estado;
                BeTrans_inv_ciclico.IdPresentacion = Presentacion;
                BeTrans_inv_ciclico.IdPresentacion_nuevo = Presentacion;
                BeTrans_inv_ciclico.IdUbicacion = nidubic;
                BeTrans_inv_ciclico.IdUbicacion_nuevo = nidubic;
                BeTrans_inv_ciclico.EsNuevo = true;

            } else {

                BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();

                BeTrans_inv_ciclico.IdInvCiclico = 0;
                BeTrans_inv_ciclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
                BeTrans_inv_ciclico.IdStock = 0;
                BeTrans_inv_ciclico.IdProductoBodega = 0;
                BeTrans_inv_ciclico.IdProductoEstado = Estado;
                BeTrans_inv_ciclico.IdProductoEst_nuevo = Estado;
                //BeTrans_inv_ciclico.IdPresentacion = Presentacion;
                //BeTrans_inv_ciclico.IdPresentacion_nuevo = Presentacion;
                //IdPresentacion/IdPresentacion_nuevo es igual a 0, porque no se hace carga del spinner presentación
                BeTrans_inv_ciclico.IdPresentacion = 0;
                BeTrans_inv_ciclico.IdPresentacion_nuevo = 0;
                BeTrans_inv_ciclico.IdUbicacion = nidubic;
                BeTrans_inv_ciclico.IdUbicacion_nuevo = nidubic;
                BeTrans_inv_ciclico.EsNuevo = true;

                BeTrans_inv_ciclico.Lote = txtNLote.getText().toString().trim();
                BeTrans_inv_ciclico.Lote_stock = txtNLote.getText().toString().trim();

                //BeTrans_inv_ciclico.fecha_vence = dtpNVence.getText().toString().trim();

                try {
                    BeTrans_inv_ciclico.Fecha_vence = du.convierteFecha(dtpNVence.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //BeTrans_inv_ciclico.fecha_vence_stock = dtpNVence.getText().toString().trim();
                try {
                    BeTrans_inv_ciclico.Fecha_vence_stock = du.convierteFecha(dtpNVence.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                BeTrans_inv_ciclico.Cantidad = Double.parseDouble(txtNCantContada.getText().toString().trim());
                BeTrans_inv_ciclico.Cant_stock = Double.parseDouble(txtNCantContada.getText().toString().trim());
                BeTrans_inv_ciclico.Cant_reconteo = 0.00;

                if(gl.pBeProductoNuevo.Control_peso){

                    BeTrans_inv_ciclico.Peso = Double.parseDouble(txtNPesoContado.getText().toString().trim());
                    BeTrans_inv_ciclico.Peso_stock = Double.parseDouble(txtNPesoContado.getText().toString().trim());
                    BeTrans_inv_ciclico.Peso_reconteo = 0.00;

                }

                BeTrans_inv_ciclico.Idoperador =  gl.IdOperador;
                BeTrans_inv_ciclico.User_agr = gl.gNomOperador;

                try {
                    fecha_vence = du.getFechaActual();
                    BeTrans_inv_ciclico.Fec_agr = fecha_vence;
                } catch (ParseException e) {
                    mu.msgbox("error_getFechaActual:"+e.getMessage());
                }

                //GuardarProductoNuevo
                execws(7);


            }

        }


    }

    private void GuardarProductoNuevo() {

        try {

            Boolean Valida=false;

            Valida = xobj.getresult(Boolean.class,"Guardar_Producto_Nuevo_Inventario");

            if(Valida){

                toast("Registro guardado");
                gl.cerrarActividad2=true;
                finish();

            }else{

                toast("Error al registrar " + Valida);
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtNUbic.getText().toString().trim(),"pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",gl.pprod.IdProducto,"pActivo",true);
                        break;
                    case 4:
                        callMethod("Get_IdProductoBodega_By_IdProducto_And_IdBodega","pIdProducto",gl.pprod.IdProducto,"pIdBodega",gl.IdBodega);
                        break;
                    case 5:
                        callMethod("Get_Inventario_Teorico_By_Codigo","IdInventarioEnc",BeInvEnc.Idinventarioenc,"IdProducto",gl.pprod.IdProducto);
                        break;
                    case 6:
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtNProd.getText().toString().trim(),"IdBodega",gl.IdBodega);
                        break;
                    case 7:
                        callMethod("Guardar_Producto_Nuevo_Inventario","pBeProducto",gl.pBeProductoNuevo, "IdBodega",gl.IdBodega,"IdInventario",BeInvEnc.Idinventarioenc,"EsCiclico",true,"BeInvCiclico",BeTrans_inv_ciclico,"BeInvInicial",null);
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
                case 3:
                    LlenaDetPresentacion();
                    break;
                case 4:
                    Get_IdProdBodega();
                    break;
                case 5:
                    Llena_Lote();
                    break;
                case 6:
                    Get_Producto();
                    break;
                case 7:
                    GuardarProductoNuevo();
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

/*    public void Exit(View view) {
        frm_inv_cic_guardar.super.finish();
    }*/

}