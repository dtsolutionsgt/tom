package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico_vw;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_add extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ImageView imgDate;
    private EditText txtUbic,txtProd,txtLote1,txtCantContada,txtPesoContado,dtpVence;
    private Spinner cboEstado,cboPres;
    private TextView txtlote_cic,lblCantStock,lblUM,lblUbic1,lblProd,txtFecha_cic,txtpeso_cic,lbltitulo_cic;
    private int idPresentacion;
    private int year;
    private int month;
    private int day;
    private int IdProductoBodega,idubic, idstock;
    private double ocant, opeso,vFactor;
    private String Resultado;
    private ArrayList<String> bodlist= new ArrayList<String>();
    private  clsBeTrans_inv_ciclico_vw pitem;
    private clsBeTrans_inv_ciclico BeTrans_inv_ciclico;
    private boolean nuevoRegistro;
    //variables para obtener id de los combobox
    private int IdEstadoselected, IdPresentacionselected;

    //obtiene el id máximo de inventario ciclico
    private int IDInventarioCiclico;

    private boolean noubicflag = false;


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
        IdProductoBodega = 0;
        idubic = 0;

        //respuesta del ws cuando actualiza.
        String Resultado ="";

        IDInventarioCiclico = 0;

        ws = new WebServiceHandler(frm_inv_cic_add.this,gl.wsurl);
        xobj = new XMLObject(ws);

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

                       // IdPresentacionselected = gl.lista_estados.items.get(position).IdEstado;
                        IdPresentacionselected = gl.inv_ciclico.IdPresentacion;

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

                    double resultado_ = gl.inv_ciclico.cantidad / vFactor;
                    String stringDecimal = String.format("%.6f", resultado_);
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
                txtCantContada.requestFocus();

            }
        }


        if(IdProductoBodega != gl.inv_ciclico.IdProductoBodega){

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
        }else if (gl.pprod.Control_lote && txtLote1.getText().toString().trim().isEmpty() ){
                toast("¡Lote incorrecto!");
        }else if(gl.inv_ciclico.control_peso && txtPesoContado.getText().toString().trim().isEmpty()){
                toast("¡Peso incorrecto!");
        }else{

            Guardar();

        }
    }

    private void Guardar(){

        if( gl.pprod.Control_lote){

            gl.inv_ciclico.Lote = txtLote1.getText().toString().trim();
        }

        if(gl.pprod.Control_vencimiento){

            gl.inv_ciclico.Fecha_Vence = du.convierteFecha(dtpVence.getText().toString().trim());
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

            }else if(txtLote1.getText().toString().trim() !=  gl.inv_ciclico.Lote_stock){

                if(!AgregaNuevoRegistro(1)){
                    //return;
                    toast("errror con nuevo correlativo!");
                }

            }else{

                pitem= new clsBeTrans_inv_ciclico_vw();
                pitem.IdProductoBodega = gl.inv_ciclico.IdProductoBodega;
                pitem.IdUbicacion = gl.inv_ciclico.IdUbicacion;
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

                    String stringDecimal = String.format("%.6f", pitem.Cantidad * vFactor);
                    pitem.Cantidad = Double.parseDouble(String.format("%.6f", stringDecimal));
                }
                //ejecutar proceso actualización
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
                pitem.IdProductoBodega = gl.inv_ciclico.IdProductoBodega;
                pitem.IdUbicacion = gl.inv_ciclico.IdUbicacion;
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

                    //String stringDecimal = String.format("%.6f", pitem.Cantidad * vFactor);
                    //pitem.Cantidad = Double.parseDouble(String.format("%.6f", stringDecimal));
                    pitem.Cantidad = pitem.Cantidad *vFactor;
                }
                //ejecutar proceso actualización
                execws(1);

            }

        }

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
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Inv_Ciclico_Actualiza_Conteo() {

        try {
            int respuesta = xobj.getresult(Integer.class,"Inventario_Ciclico_Actualiza_Conteo");

            if(respuesta !=0){

                toast("¡Todo bien, guardado!");
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

            //m_proxy.Inventario_Agregar_Conteo(BeTrans_inv_ciclico)
            execws(3);




        } catch (Exception e) {
            mu.msgbox( e.getMessage());
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
            mu.msgbox( e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }
}