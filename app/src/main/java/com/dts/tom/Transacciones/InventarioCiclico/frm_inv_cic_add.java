package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
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
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico_vw;
import com.dts.tom.Mainmenu;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_add extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private Button btnBack_cic,btAdelante,btAtras, btGuardar;
    private ImageView imgDate;
    private EditText txtUbic,txtProd,txtLote1,txtCantContada,txtPesoContado,dtpVence;
    private Spinner cboEstado,cboPres;
    private TextView txtlote_cic,lblCantStock,lblUM,lblUbic1,lblProd,txtFecha_cic,txtpeso_cic,lbltitulo_cic;
    private int idPresentacion;
    private int year;
    private int month;
    private int day;
    private int IdProductoBodega,idubic, idstock, tam_lista;
    private double vFactor;
    private String Resultado;
    private int Index;
    //private int adelante,atras;
    private String codigo_producto;

    private ArrayList<String> bodlist= new ArrayList<String>();
    private ArrayList<String> PresList= new ArrayList<String>();
    private ArrayList<Integer> IndexPresList= new ArrayList<Integer>();

    private  clsBeTrans_inv_ciclico_vw pitem;
    private clsBeTrans_inv_ciclico BeTrans_inv_ciclico;
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();

    private boolean nuevoRegistro;

    //variables para obtener id de los combobox
    private int IdEstadoselected, IdPresentacionselected;

    //obtiene el id máximo de inventario ciclico
    private int IDInventarioCiclico;

    private boolean noubicflag = false;

    //Nueva cantidad a enviar cuando el registro no es pendiente, sino ya contado
    private double Nueva_Cantidad;

    //respuesta de validación
    boolean respuesta_producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_add);
        super.InitBase();

        btGuardar = findViewById(R.id.btnGuardar);
        btAdelante = findViewById(R.id.btAdelante);
        btAtras = findViewById(R.id.btAtras);
        btnBack_cic = findViewById(R.id.btnBack_cic);
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
        IdProductoBodega = 0;
        idubic = 0;
        IDInventarioCiclico = 0;

        Index = 0;
        tam_lista = 0;

        ws = new WebServiceHandler(frm_inv_cic_add.this,gl.wsurl);
        xobj = new XMLObject(ws);

        btGuardar.setEnabled(false);

        respuesta_producto = false;

        ValidaBotones();

        Load();

        setHandlers();
    }



    private void setHandlers() {
        try{

            txtProd.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                    {


                        codigo_producto = txtProd.getText().toString().trim();

                       if(Scan_Codigo_Producto()){

                           btGuardar.setEnabled(true);
                           respuesta_producto = false;

                       }
                       else{

                           respuesta_producto = true;
                       }
                    }

                    return respuesta_producto;
                }
            });

            cboEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        TextView spinlabel = (TextView) parentView.getChildAt(0);

                        if(spinlabel != null){
                            spinlabel.setTextColor(Color.BLACK);
                            spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                            spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                        }

                        IdEstadoselected = gl.lista_estados.items.get(position).IdEstado;
                        gl.inv_ciclico.IdProductoEst_nuevo = IdEstadoselected;

                    } catch (Exception e)
                    {
                        msgbox(e.getMessage());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cboPres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        TextView spinlabel = (TextView) parentView.getChildAt(0);

                        if(spinlabel != null){
                            spinlabel.setTextColor(Color.BLACK);
                            spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                            spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                        }

                        IdPresentacionselected = IndexPresList.get(position);

                    } catch (Exception e)
                    {
                        msgbox(e.getMessage());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            txtLote1.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                    {

                           if(txtLote1.getText().toString().trim().isEmpty()){

                               mu.msgbox("Lote no puede estar vacio!");

                           }else {

                               btGuardar.setEnabled(true);
                               imgDate.requestFocus();

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

        if(gl.inv_ciclico !=null){

            //Index para determinar el registro seleccionado de la lista para avanzar o retroceder y tam_list para saber minimo y maximo a recorrer
            Index = gl.IndexCiclico;
            tam_lista = gl.reconteo_list.size() -1;


            //index para el combobox estados
            int index = 0;

            if(gl.inv_ciclico.Factor.toString().isEmpty() || gl.inv_ciclico.Factor ==0){
                vFactor = 0;
            }else {
                vFactor = gl.inv_ciclico.Factor;
            }

            idPresentacion = gl.inv_ciclico.IdPresentacion;

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
                        //index= 2;
                    }
                }
            }

            ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist);
            EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboEstado.setAdapter(EstadosAdapter);
            //el IdEstado de la bd no funciona para multipropietario, porque cada propietario tiene su propio estado y el index cambia
            if (bodlist.size()>0 && gl.multipropietario){
                //cboEstado.setSelection(1);
            }
            else if (bodlist.size()>0 && !gl.multipropietario){
                cboEstado.setSelection(index-1);
            }

            //llama a ws para cargar spinner con presentaciones del producto
             execws(4);

            txtUbic.setText(gl.inv_ciclico.NoUbic +"");
            idubic = gl.inv_ciclico.NoUbic;
            lblUbic1.setTypeface(null, Typeface.BOLD);
            lblUbic1.setText(gl.inv_ciclico.Ubic_nombre +"");
            lblProd.setTypeface(null, Typeface.BOLD);
            lblProd.setText(gl.inv_ciclico.Codigo +" - "+ gl.inv_ciclico.Producto_nombre);
            txtLote1.setText(gl.inv_ciclico.Lote+"");
            dtpVence.setText(gl.inv_ciclico.Fecha_Vence);

            if(gl.inv_ciclico.IdPresentacion == 0){
                lblUM.setText(gl.inv_ciclico.UMBas);
            }else{

                String stringDecimal = String.format("%.6f", gl.inv_ciclico.Factor);
                lblUM.setText(gl.inv_ciclico.Pres + "->" + stringDecimal);
            }

            if( gl.pprod.Control_lote){
                txtlote_cic.setVisibility(TextView.VISIBLE);
                txtLote1.setVisibility(TextView.VISIBLE);
                //txtLote1.setEnabled(false);
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

                txtPesoContado.setVisibility(TextView.VISIBLE);

            }else{
                txtpeso_cic.setVisibility(TextView.INVISIBLE);

                txtPesoContado.setVisibility(TextView.INVISIBLE);
            }


            if(BeInvEnc.Mostrar_Cantidad_Teorica_hh){

                if(!gl.inv_ciclico.cantidad.equals(0.00)){

                    if(idPresentacion == 0){

                        lblCantStock.setVisibility(TextView.VISIBLE);
                        lblCantStock.setText(gl.inv_ciclico.Cant_Stock+"");

                    }else{

                        double resultado_ = gl.inv_ciclico.Cant_Stock / vFactor;
                        String stringDecimal = String.format("%.6f", resultado_);
                        lblCantStock.setText(stringDecimal);
                    }
                }else{

                    if(idPresentacion == 0){

                        lblCantStock.setVisibility(TextView.VISIBLE);
                        lblCantStock.setText(gl.inv_ciclico.Cant_Stock+"");

                    }else{

                        double resultado_ = gl.inv_ciclico.Cant_Stock / vFactor;
                        String stringDecimal = String.format("%.6f", resultado_);
                        lblCantStock.setText(stringDecimal);
                    }
                }

            }else{

                lblCantStock.setVisibility(TextView.INVISIBLE);
            }

            lbltitulo_cic.setText("Ubic # "+ gl.inv_ciclico.NoUbic);

            if(!gl.inv_ciclico.cantidad.equals(0.00)){

                if(idPresentacion == 0){

                    String stringDecimal = String.format("%.6f", gl.inv_ciclico.cantidad);
                    txtCantContada.setText(stringDecimal);

                }else{

                    double resultado_ = gl.inv_ciclico.cantidad / vFactor;
                    String stringDecimal = String.format("%.6f", resultado_);
                    txtCantContada.setText(stringDecimal);
                }
            }

            txtProd.requestFocus();

        }else{

            mu.msgbox( "El registro seleccionado no es válido.");
        }
    }


    private boolean Scan_Codigo_Producto(){

        boolean respuesta = false;

        try{

            if(!codigo_producto.isEmpty()){

                if(gl.inv_ciclico.Codigo.equals(codigo_producto)){

                    cboEstado.requestFocus();
                    respuesta = true;

                }else{

                    IdProductoBodega = gl.inv_ciclico.IdProductoBodega;

                    //el codigo ingresado no tiene match con el registro seleccionado, se procede a buscar en la lista
                    if(Buscar_producto(codigo_producto)){

                        respuesta = true;

                    }else{

                        respuesta = false;
                        txtProd.setText("");
                        mu.msgbox("Producto no asignado para conteo. Intente con otro!");
                    }
                }

            }else {

                mu.msgbox("No ha ingresado un código.");
            }

        }
        catch (Exception e){
            respuesta = false;
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
        return respuesta;
    }

    private boolean Buscar_producto(String codigo_producto){

        boolean respuesta = false;

        for (int i = 0; i < gl.reconteo_list.size() ; i++) {

            String codigo = gl.reconteo_list.get(i).Codigo;

            //if (codigo.equals(codigo_producto) && gl.reconteo_list.get(i).cantidad.equals(0.0) ) {
            if (codigo.equals(codigo_producto) ) {

                gl.inv_ciclico = gl.reconteo_list.get(i);
                Load();

                respuesta = true;
                break;

            }
        }

        return respuesta;
    }

/*    private void Scan_Codigo_Producto1() {

        if(gl.inv_ciclico.codigo_producto == null){
            toast("¡Producto no existe!");

        }else{

            if(!gl.inv_ciclico.Codigo.equals(txtProd.getText().toString().trim())){

                toast("El código de producto no es válido");

                txtProd.requestFocus();


            }else{

                IdProductoBodega = gl.inv_ciclico.IdProductoBodega;

                if(IdProductoBodega != gl.inv_ciclico.IdProductoBodega){

                    if(!buscaproducto(IdProductoBodega, txtProd.getText().toString().trim())){

                        toast("¿Producto no pertence a esta ubicación, Registrar de todas formas?");

                        *//*******************************************************************************//*
                        *//****** FALTA CREAR TOAST PARA CONFIRMAR Y ENVIAR A FORM_CIC_NUEVO.JAVA *******//*
                    } else{



                    }
                }

                btGuardar.setEnabled(true);
              //  if(gl.pprod.Control_lote && txtLote1.toString().isEmpty()){
                if(gl.pprod.Control_lote){

                    txtLote1.requestFocus();

                }else{
                    txtCantContada.requestFocus();
                }
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
    }*/

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
        finish();
    }

    @Override
    public void onBackPressed() {

        try{

            gl.inv_ciclico = new clsBe_inv_reconteo_data();
            pitem = new clsBeTrans_inv_ciclico_vw();
            BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
            txtUbic.setText("");
            txtProd.setText("");
            txtCantContada.setText("");
            txtPesoContado.setText("");
            gl.pprod = new clsBeProducto();

            frm_inv_cic_add.super.finish();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void backward(View view) {

        if(Index > 0){

            Index = Index -1;
            gl.inv_ciclico=  gl.reconteo_list.get(Index);

            ValidaBotones();

            Load();
            setHandlers();


        }
    }

    public void forward(View view) {

        if (Index < tam_lista){

            Index = Index+1;
            gl.inv_ciclico=  gl.reconteo_list.get(Index);

            ValidaBotones();

            Load();
            setHandlers();

        }

    }

    public void ValidaBotones(){

        if(Index == tam_lista){
            btAdelante.setEnabled(false);
        }
        if(Index == 0){

            btAtras.setEnabled(false);

        }

        if(Index > 0 && Index < tam_lista){
            btAdelante.setEnabled(true);
            btAtras.setEnabled(true);
        }
    }

    public void btnGuardar(View view) {

        if(txtUbic.getText().toString().trim().isEmpty()){
            msgbox("¡Ubicacion vacia!");
            txtUbic.requestFocus();

        }else if(txtProd.getText().toString().trim().isEmpty()){
            toast("¡Producto vacio!");
            txtProd.requestFocus();

        }else if(txtCantContada.getText().toString().trim().isEmpty() || txtCantContada.getText().toString().trim().equals("0")){
            toast("¡Cantidad incorrecta!");
            txtCantContada.requestFocus();

        }else if (gl.pprod.Control_lote && txtLote1.getText().toString().trim().isEmpty() ){
                toast("¡Lote incorrecto!");
                txtLote1.requestFocus();

        }else if(gl.inv_ciclico.control_peso && txtPesoContado.getText().toString().trim().isEmpty()){
                toast("¡Peso incorrecto!");
                txtPesoContado.requestFocus();

        }else{

            btGuardar.setEnabled(true);
            Guardar();

        }
    }

    private void Guardar(){

        if( gl.pprod.Control_lote){

            gl.inv_ciclico.Lote = txtLote1.getText().toString().trim();
        }

        if(gl.pprod.Control_vencimiento){

            try {
                gl.inv_ciclico.Fecha_Vence = du.convierteFecha(dtpVence.getText().toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(IdPresentacionselected < 0 ){

            gl.inv_ciclico.idPresentacion_nuevo = 0;

        }else{
            gl.inv_ciclico.idPresentacion_nuevo = IdPresentacionselected;
        }

        gl.inv_ciclico.cantidad = Double.valueOf(txtCantContada.getText().toString().trim());


        if(gl.inv_ciclico.control_peso){
            gl.inv_ciclico.Peso = Double.valueOf(txtPesoContado.getText().toString().trim());
        }

        if( gl.pprod.Control_lote){

            if(noubicflag){

                if(!AgregaNuevoRegistro(0)){
                    //return;
                    toast("errror con correlativo =0");
                }

            }else if(!(gl.inv_ciclico.Lote_stock.equals(txtLote1.getText().toString().trim()))){

                //se omite funcionalidad, no actualiza registro de reconteo, sino agrega otra linea en la tabla
                /*if(!AgregaNuevoRegistro(1)){
                    toast("Registro reconteo agregado!");
                }*/

                //Es registro para reconteo
                EnviarReconteo();



            }else{

                //GT 18012021 set para la clase que se envia como conteo.
                pitem= new clsBeTrans_inv_ciclico_vw();

                pitem.Idinvciclico = 0;
                pitem.IdStock=0;
                pitem.IdProductoBodega = gl.inv_ciclico.IdProductoBodega;
                pitem.IdUbicacion = gl.inv_ciclico.NoUbic;;
                pitem.Lote_stock = gl.inv_ciclico.Lote_stock;
                pitem.Fecha_vence_stock = gl.inv_ciclico.Fecha_Vence;
                pitem.Lote = gl.inv_ciclico.Lote;
                pitem.Fecha_vence = gl.inv_ciclico.Fecha_Vence;
                pitem.Idinventarioenc = gl.inv_ciclico.idinventarioenc;
                pitem.Cantidad = gl.inv_ciclico.cantidad;
                pitem.Peso = gl.inv_ciclico.Peso;
                pitem.IdPresentacion = gl.inv_ciclico.IdPresentacion;
                pitem.idPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
                pitem.IdProductoEst_nuevo = gl.inv_ciclico.IdProductoEst_nuevo;

                if(pitem.IdPresentacion > 0){

                    pitem.Cantidad = pitem.Cantidad *vFactor;
                }
                //ejecutar proceso actualización Inventario_Ciclico_Actualiza_Conteo
                execws(1);
            }

        }else{

            if(noubicflag){

                if(!AgregaNuevoRegistro(0)){

                    toast("Nuevo registro ok, idstock = 0!");
                }

                //Tarea_Conteo()
            }else{

                pitem= new clsBeTrans_inv_ciclico_vw();
                pitem.Idinvciclico = 0;
                pitem.Idinventarioenc = gl.inv_ciclico.idinventarioenc;
                pitem.IdStock=0;
                pitem.IdProductoBodega = gl.inv_ciclico.IdProductoBodega;
                pitem.IdUbicacion = gl.inv_ciclico.NoUbic;
                pitem.Lote_stock = gl.inv_ciclico.Lote_stock;
                pitem.Fecha_vence_stock = gl.inv_ciclico.Fecha_Vence;
                pitem.Lote = gl.inv_ciclico.Lote;
                pitem.Fecha_vence = gl.inv_ciclico.Fecha_Vence;

                pitem.Cantidad = gl.inv_ciclico.cantidad;
                pitem.Peso = gl.inv_ciclico.Peso;
                pitem.IdPresentacion = gl.inv_ciclico.IdPresentacion;
                pitem.idPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
                pitem.IdProductoEst_nuevo = gl.inv_ciclico.IdProductoEst_nuevo;

                if(pitem.IdPresentacion > 0){
                    pitem.Cantidad = pitem.Cantidad *vFactor;
                }
                //ejecutar proceso actualización Inventario_Ciclico_Actualiza_Conteo
                execws(1);

            }

        }

    }

    private void EnviarReconteo() {

        if(gl.inv_ciclico.Factor !=0){

            Nueva_Cantidad = Double.parseDouble(txtCantContada.getText().toString().trim()) * gl.inv_ciclico.Factor;
        }else{

            Nueva_Cantidad = Double.parseDouble(txtCantContada.getText().toString().trim());
        }

        execws(5);

    }

    private boolean AgregaNuevoRegistro(int IdStock){

        try{

            if(IdStock > 0){

                idstock = IdStock;
                //obtener el MaxIDInventarioCiclico
                execws(2);

            }else {


                BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
                BeTrans_inv_ciclico.IdInvCiclico = 0;
                BeTrans_inv_ciclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
                BeTrans_inv_ciclico.IdStock = IDInventarioCiclico;
                BeTrans_inv_ciclico.IdProductoBodega =  gl.inv_ciclico.IdProductoBodega;
                BeTrans_inv_ciclico.IdProductoEstado =  gl.inv_ciclico.IdProductoEstado;
                BeTrans_inv_ciclico.IdProductoEst_nuevo =  gl.inv_ciclico.IdProductoEst_nuevo;
                BeTrans_inv_ciclico.IdPresentacion = gl.inv_ciclico.IdPresentacion;
                BeTrans_inv_ciclico.IdPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
                BeTrans_inv_ciclico.IdUbicacion = idubic;
                BeTrans_inv_ciclico.IdUbicacion_nuevo = idubic;
                BeTrans_inv_ciclico.EsNuevo = true;

                if( gl.pprod.Control_lote){
                    BeTrans_inv_ciclico.Lote = gl.inv_ciclico.Lote;
                    if(idstock > 0){
                        BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote_stock;
                    }else {
                        BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote;
                    }
                }

                if(gl.pprod.Control_vencimiento){
                    BeTrans_inv_ciclico.Fecha_vence = gl.inv_ciclico.Fecha_Vence;
                    BeTrans_inv_ciclico.Fecha_vence_stock = gl.inv_ciclico.Fecha_Vence;
                }else {
                    BeTrans_inv_ciclico.Fecha_vence = du.convierteFecha(du.AddYearsToDate(du.getFecha().toString(), 10));
                    BeTrans_inv_ciclico.Fecha_vence_stock =  du.convierteFecha(du.AddYearsToDate(du.getFecha().toString(), 10));
                }

               // m_proxy.Inventario_Agregar_Conteo(BeTrans_inv_ciclico)
                execws(3);

                //nuevoRegistro = true;

            }

            return  nuevoRegistro;
        }
        catch (Exception e){
            mu.msgbox("inv_cic_AgregarNuevoRegistro:"+e.getMessage());
            return  false;
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
                        callMethod("Inventario_Ciclico_Actualiza_Conteo","pitem",pitem,"Resultado",Resultado);
                        break;
                    case 2:
                        callMethod("MaxIDInventarioCiclico");
                        break;
                    case 3:
                        callMethod("Inventario_Agregar_Conteo", "pBeTransInvCiclico", BeTrans_inv_ciclico);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto", "pIdProducto", gl.pprod.IdProducto, "pActivo", true);
                        break;

                    case 5:
                        callMethod("Inventario_Ciclico_Actualiza_Reconteo", "idinvreconteo", gl.inv_ciclico.idinvreconteo, "pCantidad_Reconteo", Nueva_Cantidad );
                        break;
                    }


            } catch (Exception e) {
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
                    Inv_Ciclico_Actualiza_Conteo();
                    break;
                case 2:
                    MaxIDInventarioCiclico_();
                    break;
                case 3:
                    Inventario_Agregar_Conteo_();
                    break;
                case 4:
                    processPresentacion();
                    break;
                case 5:
                    Inventario_Ciclico_Actualiza_Reconteo();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processPresentacion() {

        try {
            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (BeListPres!=null){

                if (BeListPres.items!=null){

                    Listar_Presentaciones();
                }
            }

        } catch (Exception e) {
            mu.msgbox("processPresentacion:"+e.getMessage());
        }

    }

    private void Listar_Presentaciones() {

        try{

            //crea listas con descripcion item no repetidos del ws
            PresList.clear();
            for (clsBeProducto_Presentacion BePres: BeListPres.items){

                if(PresList.contains(BePres.Nombre)){

                }else{

                    PresList.add(BePres.Nombre);

                }
            }

            //crea lista con id item no repetido del ws
            IndexPresList.clear();
            for (clsBeProducto_Presentacion BePres: BeListPres.items){

                if(IndexPresList.contains(BePres.IdPresentacion)){

                }else{

                    IndexPresList.add(BePres.IdPresentacion);

                }
            }



            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboPres.setAdapter(dataAdapter);

            if ( gl.inv_ciclico.IdPresentacion > 0){

                List AuxPres = stream(BeListPres.items).select(c->c.IdPresentacion).toList();
                int indx=AuxPres.indexOf(gl.inv_ciclico.IdPresentacion);
                cboPres.setSelection(indx);
            }else {

                cboPres.setSelection(0);
            }

        }catch (Exception e){
            mu.msgbox("cic_add_cboPres:"+e.getMessage());
        }

    }

    private void Inv_Ciclico_Actualiza_Conteo() {

        try {
            int respuesta = xobj.getresult(Integer.class,"Inventario_Ciclico_Actualiza_Conteo");

            if(respuesta !=0){

                toast("¡Todo bien, guardado!");
                frm_inv_cic_add.super.finish();
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void MaxIDInventarioCiclico_() {

        try {

            IDInventarioCiclico = xobj.getresult(Integer.class,"MaxIDInventarioCiclico");

            BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
            BeTrans_inv_ciclico.IdInvCiclico = 0;
            BeTrans_inv_ciclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
            BeTrans_inv_ciclico.IdStock = IDInventarioCiclico;
            BeTrans_inv_ciclico.IdProductoBodega =  gl.inv_ciclico.IdProductoBodega;
            BeTrans_inv_ciclico.IdProductoEstado =  gl.inv_ciclico.IdProductoEstado;
            BeTrans_inv_ciclico.IdProductoEst_nuevo =  gl.inv_ciclico.IdProductoEst_nuevo;
            BeTrans_inv_ciclico.IdPresentacion = gl.inv_ciclico.IdPresentacion;
            BeTrans_inv_ciclico.IdPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
            BeTrans_inv_ciclico.IdUbicacion = idubic;
            BeTrans_inv_ciclico.IdUbicacion_nuevo = idubic;
            BeTrans_inv_ciclico.EsNuevo = true;

            if( gl.pprod.Control_lote){
                BeTrans_inv_ciclico.Lote = gl.inv_ciclico.Lote;
                if(idstock > 0){
                    BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote_stock;
                }else {
                    BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote;
                }
            }

            if(gl.pprod.Control_vencimiento){
                BeTrans_inv_ciclico.Fecha_vence = gl.inv_ciclico.Fecha_Vence;
                BeTrans_inv_ciclico.Fecha_vence_stock = gl.inv_ciclico.Fecha_Vence;
            }else {
                BeTrans_inv_ciclico.Fecha_vence = du.convierteFecha(du.AddYearsToDate(du.getFecha().toString(), 10));
                BeTrans_inv_ciclico.Fecha_vence_stock =  du.convierteFecha(du.AddYearsToDate(du.getFecha().toString(), 10));
            }

            BeTrans_inv_ciclico.Cantidad = Double.parseDouble(txtCantContada.getText().toString().trim());
            BeTrans_inv_ciclico.Cant_stock = gl.inv_ciclico.Cant_Stock;
            BeTrans_inv_ciclico.Cant_reconteo = 0;

            if(gl.inv_ciclico.control_peso){

                BeTrans_inv_ciclico.Peso = Double.parseDouble(txtPesoContado.getText().toString().trim());
                BeTrans_inv_ciclico.Peso_stock =  gl.inv_ciclico.Peso_Stock;
                BeTrans_inv_ciclico.Peso_reconteo = 0;
            }

            BeTrans_inv_ciclico.Idoperador =  gl.IdOperador;
            BeTrans_inv_ciclico.User_agr = gl.gNomOperador;

            String fecha_vence = du.getFechaActual();
            BeTrans_inv_ciclico.Fec_agr = fecha_vence;

            //m_proxy.Inventario_Agregar_Conteo(BeTrans_inv_ciclico)
            execws(3);

        } catch (Exception e) {
            mu.msgbox("Inventario_Agregar_Conteo: "+e.getMessage());
        }
    }

    private void Inventario_Agregar_Conteo_() {

        try {

            int getrespuesta = xobj.getresult(Integer.class,"Inventario_Agregar_Conteo");

            if(getrespuesta ==1){
                nuevoRegistro = true;
            }else{
                nuevoRegistro = false;
            }

        } catch (Exception e) {
            mu.msgbox("Inventario_Agregar_Conteo getResult: "+e.getMessage());
        }

    }

    private void Inventario_Ciclico_Actualiza_Reconteo() {

        try {
            int respuesta  = xobj.getresult(Integer.class,"Inventario_Ciclico_Actualiza_Reconteo");

            if(respuesta ==1){
                toast("Reconteo registrado!");
                super.finish();

            }else if(respuesta > 1) {
                toast("Se actualizó más de un registro!");
            }else if (respuesta == 0){
                toast("Error al actualizar recongeo");
            }

        } catch (Exception e) {
            mu.msgbox("actualiza_ciclico_reconteo_: "+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }
}