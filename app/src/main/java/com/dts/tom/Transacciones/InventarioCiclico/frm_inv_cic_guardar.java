package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prod;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prodList;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.google.common.collect.Table;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

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
    private ImageView imgDate, imgImprimir;
    private Button btnBack;
    private int year;
    private int month;
    private int day;
    private TextView lblNUbic,lblNProd,lblNPeso,lblNLote,lblNVence,txtpresent_cic, txtLicencia;
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

    private clsBeResolucion_lp_operador nBeResolucion = null;
    private String pNumeroLP = "";


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
        txtLicencia = findViewById(R.id.txtLicencia);
        imgImprimir = findViewById(R.id.imgImprimir);
        btnBack = findViewById(R.id.btnBack);

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
                txtLicencia.setEnabled(false);

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
                imgImprimir.setVisibility(View.GONE);
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

            txtNUbic.setText(gl.IdUbicInvCic+"");
            execws(2);
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

            //AT20241209 Get Resoulciones
            execws(8);
        }catch (Exception e){
            mu.msgbox( e.getMessage());
        }
    }

    private void Get_IdProdBodega() {

        try {
            idprodbod = xobj.getresult(Integer.class,"Get_IdProductoBodega_By_IdProducto_And_IdBodega");

            if (idprodbod!=0){

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
                BeTrans_inv_ciclico.IdUnidadMedida = gl.pBeProductoNuevo.IdUnidadMedidaBasica ;
                BeTrans_inv_ciclico.EsNuevo = true;

            }

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

        txtNUbic.setOnKeyListener((v, keyCode, event) -> {
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
        });

        txtNProd.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
            {

                LlenaCampos_Producto_Nuevo();

            }
            return false;
        });

        imgImprimir.setOnClickListener(view -> {
            msgImprimir("¿Impimir licencia?");
        });

        btnBack.setOnClickListener(view -> {
            gl.cerrarActividad2 = true;
            finish();
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
                BeTrans_inv_ciclico.lic_plate = "0";
                BeTrans_inv_ciclico.IdUnidadMedida = gl.pBeProductoNuevo.IdUnidadMedidaBasica;

                String fechaVencimiento = "";

                try {
                    fechaVencimiento = gl.pBeProductoNuevo.Control_vencimiento ? du.convierteFecha(dtpNVence.getText().toString().trim()) : "1900-01-01T00:00:00";
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                BeTrans_inv_ciclico.Fecha_vence = fechaVencimiento;
                BeTrans_inv_ciclico.Fecha_vence_stock = fechaVencimiento;

                BeTrans_inv_ciclico.Cantidad = Double.parseDouble(txtNCantContada.getText().toString().trim());
                BeTrans_inv_ciclico.Cant_stock = 0.00;
                BeTrans_inv_ciclico.Cant_reconteo = 0.00;

                if(gl.pBeProductoNuevo.Control_peso){

                    BeTrans_inv_ciclico.Peso = Double.parseDouble(txtNPesoContado.getText().toString().trim());
                    BeTrans_inv_ciclico.Peso_stock = Double.parseDouble(txtNPesoContado.getText().toString().trim());
                    BeTrans_inv_ciclico.Peso_reconteo = 0.00;

                }

                BeTrans_inv_ciclico.Idoperador =  gl.IdOperador;
                BeTrans_inv_ciclico.User_agr = gl.gNomOperador;
                BeTrans_inv_ciclico.lic_plate = txtLicencia.getText().toString().replace("$", "");

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

            if (Valida != null) {
                if (Valida) {
                    toast("Registro guardado");
                    imgImprimir.setVisibility(View.VISIBLE);
                } else {

                    toast("Error al registrar " + Valida);
                }
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void processLicenciaPacking() {
        try {

            if (nBeResolucion == null){
                nBeResolucion = new clsBeResolucion_lp_operador();
                if (xobj!=null){
                    nBeResolucion = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");
                }else{
                    toast("El objeto SI es nulo");
                }
            }

            if (nBeResolucion !=null){
                gl.IdResolucionLpOperador = nBeResolucion.IdResolucionlp;

                long pLpSiguiente = nBeResolucion.Correlativo_Actual +1;
                int largoMaximo = String.valueOf(nBeResolucion.Correlativo_Final).length();

                long intLPSig = pLpSiguiente;
                int MaxL = largoMaximo;

                String result = String.format("%0"+ MaxL + "d",intLPSig);

                pNumeroLP= nBeResolucion.Serie + result;
                txtLicencia.setText(pNumeroLP);
            } else {
                gl.IdResolucionLpOperador =0;
                return;
            }

        }catch (Exception e){
            mu.msgbox("processNuevoLP_RE: "+e.getMessage());
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
                        int idresolucion = 0;

                        if (nBeResolucion != null) {
                            idresolucion = nBeResolucion.IdResolucionlp;
                        }

                        callMethod("Guardar_Producto_Nuevo_Inventario",
                                "pBeProducto",gl.pBeProductoNuevo,
                                "IdBodega",gl.IdBodega,
                                "IdInventario",BeInvEnc.Idinventarioenc,
                                "EsCiclico",true,"BeInvCiclico",BeTrans_inv_ciclico,
                                "BeInvInicial",null,
                                "pIdResolucion", idresolucion);
                        break;
                    case 8:
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador",gl.IdOperador,
                                "pIdBodega",gl.IdBodega);
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
                    break;
                case 8:
                    processLicenciaPacking();
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

    private void msgImprimir(String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg + "\n\nImpresora: " + gl.MacPrinter);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setCancelable(false);

            dialog.setPositiveButton("Si", (dialog1, which) -> {
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                    Imprimir_Licencia();
                    hideProgressDialog();

                }, 300);


            });

            dialog.setNegativeButton("No", (dialog12, which) -> {});
            dialog.show();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void Imprimir_Licencia(){
        try{

            if (!txtLicencia.getText().toString().trim().isEmpty()){
                pNumeroLP = txtLicencia.getText().toString().trim().replace("$","");
            }

            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);

            if (!printerIns.isConnected()){
                printerIns.open();
            }

            if (printerIns.isConnected()){

                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);

                String zpl="";

                if (!pNumeroLP.isEmpty()) {

                    if (gl.pBeBodega.IdTipoEtiquetaLicencia == 1) {

                        zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW700 \n" +
                                        "^LL0406 \n" +
                                        "^LS0 \n" +
                                        "^FT450,21^A0I,20,14^FH^FD%5$s^FS \n" +
                                        "^FO2,40^GB670,0,5^FS \n" +
                                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                        "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                        "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                        "^FT670,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS \n" +
                                        "^FO2,340^GB670,0,14^FS \n" +
                                        "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                        "^FD%4$s^FS \n" +
                                        "^PQ1,0,1,Y " +
                                        "^XZ", gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                "",
                                "$" + pNumeroLP,
                                "");

                    } else if (gl.pBeBodega.IdTipoEtiquetaLicencia == 2) {
                        zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW600\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT450,80^A0I,20,14^FH^FD%5$s^FS\\n" +
                                        "^FO2,110^GB670,0,5^FS \n" +
                                        "^FT440,130^A0I,28,30^FH^FD%1$s^FS\n" +
                                        "^FT560,130^A0I,26,30^FH^FDBodega:^FS\n" +
                                        "^FT440,165^A0I,28,30^FH^FD%2$s^FS\n" +
                                        "^FT560,165^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                        "^FT560,220^A0I,70,70^FH^FD%3$s^FS\n" +
                                        "^BY3,3,160^FT550,300^BCI,,N,N\n" +
                                        "^FD%3$s^FS\n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT560,480^A0I,35,30^FH^FD%4$s^FS\n" +
                                        "^FO2,520^GB670,14,14^FS\n" +
                                        "^FT560,540^A0I,25,24^FH^FDTOMWMS  No. Licencia^FS\n" +
                                        "^XZ", gl.CodigoBodega + "-" + gl.gNomBodega,
                                gl.gNomEmpresa,
                                "$" + pNumeroLP,
                                "",
                                "");

                    } else if (gl.pBeBodega.IdTipoEtiquetaLicencia == 4) {
                        zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW812 \n" +
                                        "^LL0630 \n" +
                                        "^LS0 \n" +
                                        "^FT450,21^A0I,20,14^FH^FD%5$s^FS \n" +
                                        "^FO2,40^GB670,0,5^FS \n" +
                                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                        "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                        "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                        "^FT670,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS \n" +
                                        "^FO2,340^GB670,0,14^FS \n" +
                                        "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                        "^FD%4$s^FS \n" +
                                        "^PQ1,0,1,Y " +
                                        "^XZ", gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                "",
                                "$" + pNumeroLP,
                                "");

                    }else if (gl.pBeBodega.IdTipoEtiquetaLicencia == 5) {

                        zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW700\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT450,21^A0I,20,14^FH^FD%5$s^FS\n" +
                                        "^FO2,40^GB700,5,5^FS\n" +
                                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS\n" +
                                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS\n" +
                                        "^FT700,306^A0I,30,24^FH^FD%3$s^FS\n" +
                                        "^FT290,135^A0I,85,54^FH^FDV.%7$s^FS\n" +
                                        "^FT290,225^A0I,85,49^FH^FDL.%6$s^FS\n" +
                                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS\n" +
                                        "^FT700,61^A0I,30,24^FH^FDEmpresa:^FS\n" +
                                        "^FT700,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS\n" +
                                        "^FO2,340^GB700,14,14^FS\n" +
                                        "^BY3,3,160^FT700,131^BCI,,Y,N\n" +
                                        "^FD%4$s^FS\n" +
                                        "^PQ1,0,1,Y\n" +
                                        "^XZ", gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                "",
                                "$" + pNumeroLP,
                                "",
                                "");

                    }

                    if (!zpl.isEmpty()) {
                        zPrinterIns.sendCommand(zpl);
                    } else {
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido (LP)");
                    }
                }

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }
        } catch (Exception e) {
            hideProgressDialog();
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir la licencia del producto. No existe conexión a la impresora: "+ gl.MacPrinter);
            }else{
                mu.msgbox("Imprimir_licencia: "+e.getMessage());
            }
        }
    }

    public void hideProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}