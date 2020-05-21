package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.transition.Visibility;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prodList;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeUbic;

public class frm_inv_ini_conteo extends PBase {

    private EditText txtUbicInv, txtCodBarra, txtLoteInvIni, txtVenceInvIni, txtCantInvIni, txtPesoInvIni;
    private TextView lblUbicDesc, lblDescProd, lblUnidadInv, lblLote, lblPeso, lblTituloForma, lblVence;
    private Button btnGuardarConteo, btnCompletar, btnBack;
    private Spinner cmbPresInvIni, cmbEstadoInvIni;
    private DatePicker dpResult;
    private ImageView imgDate;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private boolean verlote, vervence;
    private int codestmalo = 0;
    private int IdEstadoSelect=0;
    private int IdPresSelect=0;
    public static String CodBarra="";

    // date
    private int year;
    private int month;
    private int day;

    private clsBeTrans_inv_tramo utramo = new clsBeTrans_inv_tramo();
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();
    private clsBeProducto_estadoList BeListEstado = new clsBeProducto_estadoList();
    private clsBeTrans_inv_stock_prodList InvTeorico = new clsBeTrans_inv_stock_prodList();
    private clsBeTrans_inv_stock_prodList InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();
    private clsBeTrans_inv_detalle  ditem = new clsBeTrans_inv_detalle();

    private ArrayList<String> EstadoList = new ArrayList<String>();
    private ArrayList<String> PresList = new ArrayList<String>();

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_conteo);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_conteo.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicInv = (EditText) findViewById(R.id.txtUbicInv);
        txtCodBarra = (EditText) findViewById(R.id.txtCodBarra);
        txtLoteInvIni = (EditText) findViewById(R.id.txtLoteInvIni);
        txtVenceInvIni = (EditText) findViewById(R.id.txtVenceInvIni);
        txtCantInvIni = (EditText) findViewById(R.id.txtCantInvIni);
        txtPesoInvIni = (EditText) findViewById(R.id.txtPesoInvIni);

        lblUbicDesc = (TextView) findViewById(R.id.lblUbicDesc);
        lblDescProd = (TextView) findViewById(R.id.lblDescProd);
        lblUnidadInv = (TextView) findViewById(R.id.lblUnidadInv);
        lblLote = (TextView) findViewById(R.id.textView35);
        lblPeso = (TextView) findViewById(R.id.textView38);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblVence = (TextView) findViewById(R.id.textView36);

        cmbPresInvIni = (Spinner) findViewById(R.id.cmbPresInvIni);
        cmbEstadoInvIni = (Spinner) findViewById(R.id.cmbEstadoInvIni);

        btnGuardarConteo = (Button) findViewById(R.id.btnGuardarConteo);
        btnCompletar = (Button) findViewById(R.id.btnCompletar);
        btnBack = (Button) findViewById(R.id.btnBack);

        dpResult = (DatePicker)findViewById(R.id.datePicker3);

        imgDate = (ImageView)findViewById(R.id.imgDate);

        setHandles();

        setCurrentDateOnView();

        Load();

    }

    private void setHandles() {

        try {

            txtUbicInv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicInv.getText().toString().isEmpty()) {
                            execws(2);
                        }
                    }

                    return false;
                }
            });

            txtCodBarra.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtCodBarra.getText().toString().isEmpty()) {
                            execws(3);
                        }
                    }

                    return false;
                }
            });

            txtCantInvIni.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (txtPesoInvIni.getVisibility()==View.VISIBLE){
                            txtPesoInvIni.setSelectAllOnFocus(true);
                            txtPesoInvIni.requestFocus();
                        }else{
                            Guardar_Inventario_Conteo();
                        }
                    }

                    return false;
                }
            });

            cmbEstadoInvIni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoSelect=BeListEstado.items.get(position).IdEstado;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbPresInvIni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdPresSelect=BeListPres.items.get(position).IdPresentacion;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });


        } catch (Exception e) {
            mu.msgbox("setHandles:" + e.getMessage());
        }
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        txtVenceInvIni.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            // set selected date into textview
            txtVenceInvIni.setText(new StringBuilder().append(day)
                    .append("-").append(month).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

    public void ChangeDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private void Load() {

        try {

            txtUbicInv.setText("");
            txtCodBarra.setText("");
            txtLoteInvIni.setText("");
            txtVenceInvIni.setText("");
            txtCantInvIni.setText("");
            txtPesoInvIni.setText("");

            lblUbicDesc.setText("");
            lblDescProd.setText("");
            lblUnidadInv.setText("");

            txtLoteInvIni.setVisibility(View.GONE);
            lblLote.setVisibility(View.GONE);

            lblPeso.setVisibility(View.GONE);
            txtPesoInvIni.setVisibility(View.GONE);

            txtVenceInvIni.setText(du.convierteFechaMostar(du.getFechaActual()));

            if (BeUbic.IdUbicacion > 0) {
                txtUbicInv.setText("" + BeUbic.IdTramo);
                lblUbicDesc.setText(BeUbic.NombreCompleto);
            }

            lblTituloForma.setText("TRAMO :" + BeInvTramo.Nombre_Tramo);

            txtCantInvIni.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoInvIni.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);

            execws(1);

        } catch (Exception e) {
            mu.msgbox("Load:" + e.getMessage());
        }
    }

    private void Procesa_Ubicacion() {

        try {

            if (BeUbic.Tramo.IdTramo != 0) {
                if (BeInvTramo.Idtramo != BeUbic.Tramo.IdTramo) {
                    mu.msgbox("La ubicación no partenece al tramo: " + BeUbic.Tramo.Descripcion);
                }
            }

            if (BeUbic.Nivel > 1) {

            }

            lblUbicDesc.setText("" + BeUbic.Descripcion);

            txtCodBarra.setSelectAllOnFocus(true);
            txtCodBarra.requestFocus();
            txtCodBarra.selectAll();

        } catch (Exception e) {
            mu.msgbox("Procesa_Ubicacion");
        }
    }

    private void Carga_Det_Producto() {

        try {

            lblDescProd.setText(BeProducto.Nombre);

            lblUbicDesc.setText(BeProducto.UnidadMedida.Nombre);

            execws(4);

        } catch (Exception e) {
            mu.msgbox("Carga_Det_Producto:" + e.getMessage());
        }
    }

    private void Llena_Det_Presentacion_Producto() {

        try {

            PresList.clear();

            for (clsBeProducto_Presentacion BePres : BeListPres.items) {
                PresList.add(BePres.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresInvIni.setAdapter(dataAdapter);

            if (PresList.size() > 0) cmbPresInvIni.setSelection(0);

        } catch (Exception e) {
            mu.msgbox("llenaDetPresentacionProducto:" + e.getMessage());
        }
    }

    private void Llena_Det_Estados_Producto() {

        try {

            EstadoList.clear();

            for (clsBeProducto_estado BeEstado : BeListEstado.items) {
                if (!BeEstado.Utilizable) {
                    codestmalo = BeEstado.IdEstado;
                }
                EstadoList.add(BeEstado.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoInvIni.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoInvIni.setSelection(0);

        } catch (Exception e) {
            mu.msgbox("Llena_Det_Estados_Producto:" + e.getMessage());
        }
    }

    private void Carga_Datos_Producto(){

        try{

            Carga_Det_Producto();

            mostrarlote(BeProducto.Control_lote);

            mostrarvence(BeProducto.Control_vencimiento);

            txtCantInvIni.setText("");

            lblUnidadInv.setText(BeProducto.UnidadMedida.Nombre);

            muestra_peso(BeProducto.Control_peso);

            txtPesoInvIni.setText("");

            if (InvTeorico != null) {
                if (InvTeorico.items != null) {

                    InvTeoricoPorProducto.items = new ArrayList<>();
                    InvTeoricoPorProducto.items = stream(InvTeorico.items).where(c -> c.Idinventario == BeInvEnc.Idinventarioenc &&
                            c.IdProducto == BeProducto.IdProducto).toList();

                    if (InvTeoricoPorProducto != null) {
                        if (InvTeoricoPorProducto.items != null) {
                            execws(6);
                        }
                    }
                }
            }

        }catch (Exception e){
            mu.msgbox("Carga_Datos_Producto:"+e.getMessage());
        }
    }

    private void mostrarvence(boolean Control_vence){

        try{

            if (Control_vence){
                lblVence.setVisibility(View.VISIBLE);
                txtVenceInvIni.setVisibility(View.VISIBLE);
            }else{
                lblVence.setVisibility(View.GONE);
                txtVenceInvIni.setVisibility(View.GONE);
            }


        }catch (Exception e){
            mu.msgbox("mostrarvence:"+e.getMessage());
        }
    }

    private void muestra_peso(boolean Contro_peso){

        try{

            if (Contro_peso){
                lblPeso.setVisibility(View.VISIBLE);
                txtPesoInvIni.setVisibility(View.VISIBLE);
            }else{
                lblPeso.setVisibility(View.GONE);
                txtPesoInvIni.setVisibility(View.GONE);
            }

        }catch (Exception e){
            mu.msgbox("muestra_peso:"+e.getMessage());
        }
    }

    private void mostrarlote(boolean Control_lote) {

        try {

            if (Control_lote) {
                lblLote.setVisibility(View.VISIBLE);
                txtLoteInvIni.setVisibility(View.VISIBLE);
            } else {
                lblLote.setVisibility(View.GONE);
                txtLoteInvIni.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            mu.msgbox("mostrarlote:" + e.getMessage());
        }
    }

    private void Valida_Inventario_Teorico(){

        try{

            //Falta agregar un combo de lote y llenarlo con el inventario teorico

        }catch (Exception e){
            mu.msgbox("Valida_Inventario_Teorico:"+e.getMessage());
        }
    }

    private void Agrega_Producto_Nuevo(){

        try{

            browse=1;
            CodBarra = txtCodBarra.getText().toString();
            txtCodBarra.setText("");
            startActivity(new Intent(this, frm_inv_agrega_prd.class));

        }catch (Exception e){
            mu.msgbox("Agrega_Producto_Nuevo:"+e.getMessage());
        }
    }

    private void Guardar_Inventario_Conteo(){

        try{

            boolean aceptamalo=false;
            int vest=0;

            if (IdEstadoSelect==codestmalo){
                execws(8);
            }else{
                Guardar_Item();
            }


        }catch (Exception e){
            mu.msgbox("Guardar_Inventario_Conteo:"+e.getMessage());
        }
    }

    private void Guardar_Item(){

        try{

            if (!Validar_Detalle()){
                return;
            }

            Crea_Item();

            if (ditem.IdPresentacion>0){
                execws(9);
            }else{
                Continua_Guardando_Item();
            }

        }catch (Exception e){
            mu.msgbox("Guardar_Item:"+e.getMessage());
        }
    }

    private void Continua_Guardando_Item(){

        try{

            if (ditem.Idunidadmedida==0){
                mu.msgbox("Unidad de medida básica es 0");
                return;
            }else{
                execws(10);
            }

        }catch (Exception e){
            mu.msgbox("Continua_Guardando_Item:"+e.getMessage());
        }
    }

    private void Crea_Item(){

        try{

            ditem = new clsBeTrans_inv_detalle();

            ditem.Idinventariodet=0;
            ditem.Idinventarioenc =BeInvEnc.Idinventarioenc;
            ditem.Idtramo = BeUbic.Tramo.IdTramo;
            ditem.IdUbicacion = BeUbic.IdUbicacion;
            ditem.Idoperador = gl.IdOperador;
            ditem.Idproducto = BeProducto.IdProducto;
            ditem.IdPresentacion = IdPresSelect;
            ditem.Idunidadmedida = BeProducto.IdUnidadMedidaBasica;
            if (ditem.Idunidadmedida==0){
                ditem.Idunidadmedida = -1;
            }
            if (BeProducto.Control_lote){
                ditem.Lote = txtLoteInvIni.getText().toString();
            }else{
                ditem.Lote="";
            }

            if (BeProducto.Control_vencimiento){
                ditem.Fecha_vence = du.convierteFecha(txtVenceInvIni.getText().toString());
            }else{
                ditem.Fecha_vence="1900-01-01T00:00:01";
            }

            ditem.Serie="";
            ditem.Idproductoestado = ""+IdEstadoSelect;
            ditem.Cantidad = Double.parseDouble(txtCantInvIni.getText().toString());
            ditem.Fecha_captura = du.getFechaActual();
            ditem.Host = "1";
            ditem.Nom_producto = BeProducto.Nombre;
            ditem.Nom_operador = gl.OperadorBodega.Operador.Nombres;
            ditem.Carga = 0;
            ditem.Peso = Double.parseDouble(txtPesoInvIni.getText().toString());

        }catch (Exception e){
            mu.msgbox("Crea_Item:"+e.getMessage());
        }
    }

    private boolean Validar_Detalle(){

        try{

            if (BeUbic.Tramo.IdTramo==0){
                mu.msgbox("Tramo Incorrecto");
                txtUbicInv.setFocusable(true);
                return false;
            }

            if (BeProducto.IdProducto==0){
                mu.msgbox("Producto incorrecto");
                txtCodBarra.setFocusable(true);
                return false;
            }

            if (IdEstadoSelect<=0){
                mu.msgbox("Estado incorrecto");
                return  false;
            }

            if (txtLoteInvIni.getVisibility()==View.VISIBLE){
                if(txtLoteInvIni.getText().toString().isEmpty()){
                    mu.msgbox("Lote Incorrecto");
                    txtLoteInvIni.setFocusable(true);
                    return  false;
                }
            }

            if (txtCantInvIni.getText().toString().equals("0")||
                    txtCantInvIni.getText().toString().isEmpty()||
                    txtCantInvIni.getText().toString().equals("")){
                mu.msgbox("Cantidad incorrecta");
                txtCantInvIni.setFocusable(true);
                return false;
            }

            if (txtPesoInvIni.getVisibility()==View.VISIBLE){
                if (txtPesoInvIni.getText().toString().equals("0")||
                        txtPesoInvIni.getText().toString().isEmpty()||
                        txtPesoInvIni.getText().toString().equals("")){
                mu.msgbox("Peso Incorrecto");
                txtPesoInvIni.setFocusable(true);
                return false;
                }
            }else{
                txtPesoInvIni.setText("0");
            }

        }catch (Exception e){
            mu.msgbox("Validar_Detalle:"+e.getMessage());
        }

        return  true;
    }

    private void Limpiar_Campos(){

        try{

            txtUbicInv.setText("");
            lblUbicDesc.setText("");
            txtCodBarra.setText("");
            lblDescProd.setText("");
            txtCantInvIni.setText("");
            lblUbicDesc.setText("");
            txtVenceInvIni.setText("");
            txtLoteInvIni.setText("");
            txtPesoInvIni.setText("");
            lblPeso.setVisibility(View.VISIBLE);
            txtPesoInvIni.setVisibility(View.VISIBLE);

           BeProducto = new clsBeProducto();
           BeListPres = new clsBeProducto_PresentacionList();
           BeListEstado = new clsBeProducto_estadoList();
           InvTeorico = new clsBeTrans_inv_stock_prodList();
           InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();
           ditem = new clsBeTrans_inv_detalle();

            EstadoList = new ArrayList<String>();
            PresList = new ArrayList<String>();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresInvIni.setAdapter(dataAdapter);

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoInvIni.setAdapter(dataAdapter1);

            codestmalo = 0;
            IdPresSelect = 0;
            IdEstadoSelect = 0;

        }catch (Exception e){
            mu.msgbox("Limpiar_Campos:"+e.getMessage());
        }
    }

    public void BotonCompletar(View view){

        try{

            msgCompletar("¿Completar conteo de tramo  ?");

        }catch (Exception e){
            mu.msgbox("BotonCompletar:"+e.getMessage());
        }
    }

    public void BotonDetalle(View view){

        try{

            browse=1;
            startActivity(new Intent(this, frm_inv_ini_contados.class));

        }catch (Exception e){
            mu.msgbox("BotonDetalle"+e.getMessage());
        }
    }

    public void BotonExit(View view){
        Limpiar_Campos();
        super.finish();
    }

    private void Completar_tramo(){

        try{

            utramo.Det_estado = "Finalizado";
            utramo.Det_fin = du.getFechaActual();
            utramo.Det_idoperador = gl.OperadorBodega.IdOperador;
            utramo.IdBodega = gl.IdBodega;

            execws(12);

        }catch (Exception e){
            mu.msgbox("Completar_tramo:"+e.getMessage());
        }
    }

    private void msgCompletar(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Completar_tramo();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaProductoPallet"+e.getMessage());
        }
    }

    private void msgNoExistente(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Agrega_Producto_Nuevo();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgRegConteo(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Guardar_Item();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgRegPallet(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_Guardando_Item();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent, Url);
        }

        @Override
        public void wsExecute() {

            try {

                switch (ws.callback) {

                    case 1:
                        callMethod("Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo",
                                "pidinventario", BeInvEnc.Idinventarioenc, "pidtramo", BeInvTramo.Idtramo);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega", "pBarra", txtUbicInv.getText().toString(),
                                "pIdBodega", gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_BeProducto_By_Codigo_For_HH", "pCodigo", txtCodBarra.getText().toString(),
                                "IdBodega", gl.IdBodega);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto", "pIdProducto", BeProducto.IdProducto, "pActivo", true);
                        break;
                    case 5:
                        callMethod("Get_Estados_By_IdPropietario", "pIdPropietario", BeProducto.IdPropietario);
                        break;
                    case 6:
                        callMethod("Get_Inventario_Teorico_By_Codigo","IdInventarioEnc",BeInvEnc.Idinventarioenc,
                                "IdProducto",BeProducto.IdProducto);
                        break;
                    case 7:
                        callMethod("Existe_Producto","pCodigo",txtCodBarra.getText().toString());
                        break;
                    case 8:
                        callMethod("Inventario_Inicial_Acepta_Mal_Estado_By_IdUbicacion","pIdUbicacion",BeUbic.IdUbicacion,"pEstMalo",codestmalo);
                        break;
                    case 9:
                        callMethod("validaUbicacionPalet","pidinvenc",ditem.Idinventarioenc,"pidubicacion",BeUbic.IdUbicacion,
                                "pidpresentacion",IdPresSelect);
                        break;
                    case 10:
                        callMethod("Agregar_Inventario_Inicial","pItem",ditem);
                        break;
                    case 11:
                        callMethod("Actualizar_Inventario_Inicial_By_BeTransInvTramo","pTramo",utramo);
                        break;
                    case 12:
                        callMethod("Actualizar_Inventario_Inicial_By_BeTransInvTramo","pTramo",utramo);
                }

            } catch (Exception e) {
                mu.msgbox(e.getClass() + "WebServiceHandler:" + e.getMessage());
            }

        }

    }

    @Override
    public void wsCallBack(Boolean throwing, String errmsg, int errlevel) {

        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {

                case 1:
                    processBeTramo();
                    break;
                case 2:
                    processUbic();
                    break;
                case 3:
                    processProducto();
                    break;
                case 4:
                    processPresentacion();
                    break;
                case 5:
                    processEstados();
                    break;
                case 6:
                    processInvTeorico();
                    break;
                case 7:
                    processExisteProducto();
                    break;
                case 8:
                    processAceptaMalo();
                    break;
                case 9:
                    processValidaUbicPallet();
                    break;
                case 10:
                    processAgInventario();
                    break;
                case 11:
                    Limpiar_Campos();
                    break;
                case 12:
                    Limpiar_Campos();
                    super.finish();
            }

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processBeTramo() {

        try {

            utramo = xobj.getresult(clsBeTrans_inv_tramo.class, "Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo");

            txtUbicInv.setSelectAllOnFocus(true);
            txtUbicInv.requestFocus();

        } catch (Exception e) {
            mu.msgbox("processBeTramo:" + e.getMessage());
        }
    }

    private void processUbic() {

        try {

            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class, "Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                Procesa_Ubicacion();
            } else {
                mu.msgbox("La ubicación no existe");
            }

        } catch (Exception e) {
            mu.msgbox("processUbic:" + e.getMessage());
        }
    }

    private void processProducto() {

        try {

            BeProducto = xobj.getresult(clsBeProducto.class, "Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null) {

               Carga_Datos_Producto();

            } else {

                if (BeInvEnc.Capturar_no_existente) {
                    execws(7);
                }else{
                    mu.msgbox("No se puede agregar productos nuevos");
                }

            }

        } catch (Exception e) {
            mu.msgbox("processProducto:" + e.getMessage());
        }
    }

    private void processPresentacion(){

        try{

            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (BeListPres!=null){
                if (BeListPres.items!=null){
                    Llena_Det_Presentacion_Producto();
                }
            }

            execws(5);

        }catch (Exception e){
            mu.msgbox("processPresentacion:"+e.getMessage());
        }
    }

    private void processEstados(){

        try{

            BeListEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario");

            if (BeListEstado!=null){
                if (BeListEstado.items!=null){
                    Llena_Det_Estados_Producto();
                }else{
                    mu.msgbox("Los estados no existen");
                    return;
                }
            }else{
                mu.msgbox("Los estados no existen");
                return;
            }

        }catch (Exception e){
            mu.msgbox("");
        }
    }

    private void processInvTeorico(){

        try {

            InvTeoricoPorProducto = xobj.getresult(clsBeTrans_inv_stock_prodList.class,"Get_Inventario_Teorico_By_Codigo");

            Valida_Inventario_Teorico();

        }catch (Exception e){
            mu.msgbox("processInvTeorico:"+e.getMessage());
        }
    }

    private void processExisteProducto(){

        try{

            boolean Existe=false;

            Existe = xobj.getresult(Boolean.class,"Existe_Producto");

            if (!Existe){
                msgNoExistente("El producto no existe en el maestro,¿Desea insertarlo?");
            }else{
                mu.msgbox("No existe ningún producto con ese código de barra en esta bodega");
                return;
            }



        }catch (Exception e){
            mu.msgbox("processExisteProducto: "+e.getMessage());
        }
    }

    private  void processAceptaMalo(){

        try{

            boolean aceptaMalo= xobj.getresult(Boolean.class,"Inventario_Inicial_Acepta_Mal_Estado_By_IdUbicacion");

            if (aceptaMalo){
                Guardar_Item();
            }else{
                msgRegConteo("La ubicacion no está configurada para producto dañado."
                             +"\n¿Registrar conteo en ésta ubicación de todas formas?");
            }

        }catch (Exception e){
            mu.msgbox("processAceptaMalo:"+e.getMessage());
        }
    }

    private void processValidaUbicPallet(){

        try{

            int ubPallet=0;

            ubPallet = xobj.getresult(Integer.class,"validaUbicacionPalet");

            if (ubPallet==1){
                msgRegPallet("Ya se contó un pallet en esta ubicación."
                            +"\n¿Registrar el pallet en esta ubicación de todas formas?");
            }else{
                Continua_Guardando_Item();
            }

        }catch (Exception e){
            mu.msgbox("processValidaUbicPallet:"+e.getMessage());
        }
    }

    private void processAgInventario(){

        try{

            utramo.Det_estado="En proceso";
            utramo.Det_inicio = du.getFechaActual();
            utramo.Det_idoperador = gl.IdOperador;
            utramo.IdBodega = gl.IdBodega;

            BeInvEnc.Estado = "En proceso";
            BeInvEnc.Fec_mod = du.getFechaActual();
            BeInvEnc.User_mod = gl.OperadorBodega.Operador.Nombres;

            execws(11);

        }catch (Exception e){
            mu.msgbox("processAgInventario:"+e.getMessage());
        }
    }

    private void doExit(){
        BeProducto = new clsBeProducto();
        BeListPres = new clsBeProducto_PresentacionList();
        BeListEstado = new clsBeProducto_estadoList();
        InvTeorico = new clsBeTrans_inv_stock_prodList();
        InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();
        ditem = new clsBeTrans_inv_detalle();
        CodBarra = "";
        super.finish();
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                doExit();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
