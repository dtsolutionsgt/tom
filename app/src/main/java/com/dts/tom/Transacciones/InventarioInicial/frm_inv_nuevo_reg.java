package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_agrega_prd.pBeProductoNuevo;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeUbic;

public class frm_inv_nuevo_reg extends PBase {

    private EditText txtUbicInvN, txtCodBarraN, txtLoteInvIniN, txtVenceInvIniN, txtCantInvIniN, txtPesoInvIniN;
    private TextView lblUbicDescN, lblDescProdN, lblUnidadInvN, lblLoteN, lblPesoN, lblTituloForma, lblVenceN,lblPresN;
    private Button  btnBack;
    private Spinner cmbPresInvIniN, cmbEstadoInvIniN;
    private DatePicker dpResultN;
    private ImageView imgDateN;
    private ProgressDialog progress;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeProducto_estadoList BeListEstado = new clsBeProducto_estadoList();
    private clsBeTrans_inv_detalle nitem = new clsBeTrans_inv_detalle();

    private final ArrayList<String> EstadoList = new ArrayList<String>();

    static final int DATE_DIALOG_ID = 999;

    private int IdEstadoSelect;

    // date
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_nuevo_reg);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_nuevo_reg.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicInvN = findViewById(R.id.txtUbicInvN);
        txtCodBarraN = findViewById(R.id.txtCodBarraN);
        txtLoteInvIniN = findViewById(R.id.txtLoteInvIniN);
        txtVenceInvIniN = findViewById(R.id.txtVenceInvIniN);
        txtCantInvIniN = findViewById(R.id.txtCantInvIniN);
        txtPesoInvIniN = findViewById(R.id.txtPesoInvIniN);

        lblUbicDescN = findViewById(R.id.lblUbicDescN);
        lblDescProdN = findViewById(R.id.lblDescProdN);
        lblLoteN = findViewById(R.id.lblLoteN);
        lblPesoN = findViewById(R.id.lblPesoN);
        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblVenceN = findViewById(R.id.lblVenceN);
        lblPresN = findViewById(R.id.lblPresN);

        cmbPresInvIniN = findViewById(R.id.cmbPresInvIni);
        cmbEstadoInvIniN = findViewById(R.id.cmbEstadoInvIni);


        dpResultN = findViewById(R.id.datePicker4);

        imgDateN = findViewById(R.id.imgDateN);

        setCurrentDateOnView();

        setHandles();

        Load();

    }

    private void setHandles(){

        try{

            txtUbicInvN.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicInvN.getText().toString().isEmpty()) {
                            execws(2);
                        }
                    }

                    return false;
                }
            });

            txtCantInvIniN.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtCantInvIniN.getText().toString().isEmpty()) {
                            Guardar_Nuevo_Conteo();
                        }
                    }

                    return false;
                }
            });

            cmbEstadoInvIniN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

        }catch (Exception e){
            mu.msgbox("setHandles");
        }
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        txtVenceInvIniN.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        dpResultN.init(year, month, day, null);

    }

    public void ChangeDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            // set selected date into textview
            txtVenceInvIniN.setText(new StringBuilder().append(day)
                    .append("-").append(month).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResultN.init(year, month, day, null);

        }
    };

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

    private void Load(){

        try{

            if (pBeProductoNuevo!=null) {

                execws(1);

            }

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }

    }

    private void LlenaCampos_Producto_Nuevo(){

        try{

            cmbPresInvIniN.setVisibility(View.GONE);
            lblPresN.setVisibility(View.GONE);

            txtCodBarraN.setFocusable(false);
            txtCodBarraN.setFocusableInTouchMode(false);
            txtCodBarraN.setClickable(false);

            if (pBeProductoNuevo.Control_lote){
                lblLoteN.setVisibility(View.VISIBLE);
                txtLoteInvIniN.setVisibility(View.VISIBLE);
            }else{
                lblLoteN.setVisibility(View.GONE);
                txtLoteInvIniN.setVisibility(View.GONE);
            }

            if (pBeProductoNuevo.Control_vencimiento){
                lblVenceN.setVisibility(View.VISIBLE);
                txtVenceInvIniN.setVisibility(View.VISIBLE);
            }else{
                lblVenceN.setVisibility(View.GONE);
                txtVenceInvIniN.setVisibility(View.GONE);
            }

            if (pBeProductoNuevo.Control_peso){
                lblPesoN.setVisibility(View.VISIBLE);
                txtPesoInvIniN.setVisibility(View.VISIBLE);
            }else{
                lblPesoN.setVisibility(View.GONE);
                txtPesoInvIniN.setVisibility(View.GONE);
            }

            txtCantInvIniN.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoInvIniN.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);

            txtUbicInvN.setText(BeUbic.IdUbicacion);
            lblUbicDescN.setText(BeUbic.Descripcion);

            txtCodBarraN.setText(pBeProductoNuevo.Nombre);
            txtCantInvIniN.setText("");
            txtPesoInvIniN.setText("");

            lblDescProdN.setText(pBeProductoNuevo.Codigo +" "+ pBeProductoNuevo.Nombre);
            txtUbicInvN.setSelectAllOnFocus(true);
            txtUbicInvN.requestFocus();
            txtUbicInvN.selectAll();

        }catch (Exception e){
            mu.msgbox("LlenaCampos_Producto_Nuevo:"+e.getMessage());
        }
    }

    private void Lista_Estados_Producto(){

        try{

            EstadoList.clear();

            for (clsBeProducto_estado BeEstado : BeListEstado.items) {
                EstadoList.add(BeEstado.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoInvIniN.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoInvIniN.setSelection(0);

        }catch (Exception e){
            mu.msgbox("Lista_Estados_Producto:"+e.getMessage());
        }
    }

    public  void BotonGuardarN(View view){
        if (txtCantInvIniN.getText().toString().isEmpty()||txtCantInvIniN.getText().toString().equals("0")||txtCantInvIniN.getText().toString().equals("")){
            mu.msgbox("La cantidad no puede ser vacía o 0");
            return;
        }
        if (pBeProductoNuevo.Control_lote){
            if (txtLoteInvIniN.getText().toString().isEmpty()){
                mu.msgbox("El lote no puede ser vacío");
            }
        }
        Guardar_Nuevo_Conteo();
    }

    private void Guardar_Nuevo_Conteo(){

        try{

            nitem = new clsBeTrans_inv_detalle();

            nitem.Idinventariodet = 0;
            nitem.Idinventarioenc = BeInvEnc.Idinventarioenc;
            nitem.Idtramo = BeUbic.IdTramo;
            nitem.IdUbicacion = BeUbic.IdUbicacion;
            nitem.Idproductoestado = ""+IdEstadoSelect;
            nitem.Idproducto = 0;
            nitem.IdPresentacion = 0;
            nitem.Idunidadmedida = pBeProductoNuevo.IdUnidadMedidaBasica;

            if (pBeProductoNuevo.Control_lote){
                nitem.Lote = txtLoteInvIniN.getText().toString();
            }else{
                nitem.Lote="";
            }

            if (pBeProductoNuevo.Control_vencimiento){
                nitem.Fecha_vence = du.convierteFecha(txtVenceInvIniN.getText().toString());
            }else{
                nitem.Fecha_vence  ="1900-01-01T00:00:00";
            }

            if (pBeProductoNuevo.Control_peso){
                nitem.Peso = Double.parseDouble(txtPesoInvIniN.getText().toString());
            }else{
                nitem.Peso = 0;
            }

            nitem.Fecha_captura = du.getFechaActual();
            nitem.Nom_producto = pBeProductoNuevo.Nombre;
            nitem.Nom_operador = gl.OperadorBodega.Operador.IdOperador+"";
            nitem.Idoperador = gl.IdOperador;
            nitem.Host = "1";

            execws(3);

        }catch (Exception e){
            mu.msgbox("Guardar_Nuevo_Conteo:"+e.getMessage());
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicInvN.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Guardar_Producto_Nuevo_Inventario","pBeProducto",pBeProductoNuevo,"IdBodega",gl.IdBodega,
                                "IdInventario",BeInvEnc.Idinventarioenc,"EsCiclico",false,"BeInvCiclico",null,"BeInvInicial",nitem);
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
                    processEstadosProducto();
                    break;
                case 2:
                    processValidaUbic();
                    break;
                case 3:
                    doExit();
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void  processEstadosProducto(){

        try{

            BeListEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario");

            if (BeListEstado!=null){
                if (BeListEstado.items!=null){
                    Lista_Estados_Producto();
                    LlenaCampos_Producto_Nuevo();
                }else{
                    mu.msgbox("No existen estados asignados al propietario:"+BeInvEnc.Idpropietario);
                }
            }else{
                mu.msgbox("No existen estados asignados al propietario:"+BeInvEnc.Idpropietario);
            }

        }catch (Exception e){
            mu.msgbox("processEstadosProducto:"+e.getMessage());
        }
    }

    private void processValidaUbic(){

        try{

            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic!=null){
                lblUbicDescN.setText(BeUbic.Descripcion);

                if (pBeProductoNuevo.Control_lote){
                    txtLoteInvIniN.setSelectAllOnFocus(true);
                    txtLoteInvIniN.requestFocus();
                    txtLoteInvIniN.selectAll();
                }else if (pBeProductoNuevo.Control_vencimiento){
                    txtVenceInvIniN.setSelectAllOnFocus(true);
                    txtVenceInvIniN.requestFocus();
                    txtVenceInvIniN.selectAll();
                }else{
                    txtCantInvIniN.setSelectAllOnFocus(true);
                    txtCantInvIniN.requestFocus();
                    txtCantInvIniN.selectAll();
                }
            }else{
                mu.msgbox("¡Ubicación no existe!");
                doExit();
            }

        }catch (Exception e){
            mu.msgbox("processValidaUbic");
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void doExit(){
        super.finish();
    }

}
