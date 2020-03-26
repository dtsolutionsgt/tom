package com.dts.tom.Transacciones.Recepcion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_pallet;
import com.dts.classes.Mantenimientos.Motivo_devolucion.clsBeMotivo_devolucion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_codigos_barra.clsBeProducto_codigos_barraList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.Producto_pallet.clsBeProducto_pallet;
import com.dts.classes.Mantenimientos.Producto.Producto_pallet.clsBeProducto_palletList;
import com.dts.classes.Mantenimientos.Producto.Producto_parametros.clsBeProducto_parametros;
import com.dts.classes.Mantenimientos.Producto.Producto_parametros.clsBeProducto_parametrosList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote.clsBeTrans_oc_det_lote;
import com.dts.classes.Transacciones.Recepcion.LicencePlates.clsBeLicensePlatesList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_det;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros.clsBeTrans_re_det_parametros;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros.clsBeTrans_re_det_parametrosList;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_recList;
import com.dts.classes.Transacciones.Stock.Stock_se_rec.clsBeStock_se_rec;
import com.dts.classes.Transacciones.Stock.Stock_se_rec.clsBeStock_se_recList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Recepcion.frm_list_rec_prod.EsTransferenciaInternaWMS;
import static com.dts.tom.Transacciones.Recepcion.frm_list_rec_prod.gBeStockRec;

public class frm_recepcion_datos extends PBase {

    Calendar calendario = Calendar.getInstance();

    private Spinner cmbEstadoProductoRec,cmbPresRec;
    private EditText txtBarra,txtLoteRec,txtUmbasRec,txtCantidadRec,txtPeso,txtPesoUnitario,txtCostoReal,txtCostoOC,cmbVenceRec;
    private TextView lblDatosProd,lblPropPrd,lblPeso,lblPUn,lblCosto,lblCReal,lblPres,lblLote,lblVence;
    private Button btnCantPendiente,btnCantRecibida,btnBack,btnIr;
    private ProgressDialog progress;
    private DatePicker dpResult;
    private ImageView imgDate;
    private CheckBox chkPaletizar;
    private Dialog dialog;

    //Objeto para dialogo de parametros
    private TextView lblSerieTit,lblSerialP,lblPesoTit,lblTempTit,lblPrducto,lblLicPlate,lblFManufact,lblAnada,lblTempEsta,lblTempReal,lblPresParam,lblPesoEsta,lblPesoReal,lblSerialIni,lblSerialFin;
    private EditText txtLicPlate,txtSerial,txtAnada,txtFechaManu,txtFechaIng,txtTempEsta,txtTempReal,txtPesoEsta,txtPesoReal,txtSerieIni,txtSerieFin;
    private Spinner cmbPresParams;
    int pIndexStock=-1;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private boolean Mostro_Propiedades,Escaneo_Pallet;
    private boolean Mostrar_Propiedades_Parametros = false;
    private double Cant_Recibida_Anterior,Cant_Recibida,Cant_A_Recibir,Cant_Pendiente;
    private int pIdOrdenCompraDet,pIdOrdenCompraEnc,pLineaOC,pIdRecepcionDet,pIdProductoBodega;
    private int IdEstadoSelect,IdPreseSelect=-1,IdPreseSelectParam=-1;
    private String pNumeroLP = "";
    private boolean PCorrecto=false;
    private boolean TCorrecta=false;
    private boolean PallCorrecto= false;
    private int pIndexProdPallet=-1;
    private int pIndexParam=-1;
    private int IndexPresSelected=-1;
    private String MensajeParam="";
    private int pIndiceListaStock = -1;
    private double CostoOC=0;
    private int vPresentacion;

    private clsBeTrans_oc_det BeOcDet;
    private clsBeProducto_parametrosList pListBEProductoParametro = new clsBeProducto_parametrosList();
    private clsBeTrans_re_det_parametrosList plistBeReDetParametros = new clsBeTrans_re_det_parametrosList();
    private clsBeStock_recList lBeStockRec = new clsBeStock_recList();
    private clsBeProducto_palletList lBeProdPallet = new clsBeProducto_palletList();
    private clsBeStock_se_recList pListBeStockSeRec = new clsBeStock_se_recList();
    private clsBeStock_recList pListBeStockRec = new clsBeStock_recList();
    private clsBeProducto_palletList pListBeProductoPallet = new clsBeProducto_palletList();
    private clsBeTrans_re_detList pListTransRecDet = new clsBeTrans_re_detList();
    private clsBeI_nav_barras_pallet BeINavBarraPallet = new clsBeI_nav_barras_pallet();
    private clsBeLicensePlatesList pListBeLicensePlate = new clsBeLicensePlatesList();
    private clsBeTrans_re_enc auxRec = new clsBeTrans_re_enc();
    private clsBeTrans_re_det BeTransReDet= new clsBeTrans_re_det();
    private clsBeStock_rec BeStockRecNuevaRec = new clsBeStock_rec();
    private clsBeStock_recList listaStockPalletsNuevos = new clsBeStock_recList();
    private clsBeProducto_palletList listaProdPalletsNuevos = new clsBeProducto_palletList();
    private clsBeStock_recList listaStock = new clsBeStock_recList();
    private clsBeProducto_palletList listaProdPallets = new clsBeProducto_palletList();
    private clsBeStock_rec vBeStockRec = new clsBeStock_rec();
    private clsBeStock_rec vBeStockRecPallet = new clsBeStock_rec();
    private clsBeProducto_pallet BeProdPallet  = new clsBeProducto_pallet();
    private clsBeStock gBeStockAnt;
    private clsBeTrans_oc_det beTransOCDet =new clsBeTrans_oc_det();
    private  clsBeStock_rec ObjS = new clsBeStock_rec();
    private  clsBeStock_se_rec ObjNS =new clsBeStock_se_rec();
    boolean Pperzonalizados=false,PCap_Manu=false,PCap_Anada=false,PGenera_lp=false,PTiene_Ctrl_Peso=false,PTiene_Ctrl_Temp=false,PTiene_PorSeries=false,PTiene_Pres=false;
    private   clsBeTrans_re_detList LRecepcionCantidad = new clsBeTrans_re_detList();

    double vFactorNuevaRec=0;
    double vCantNuevaRec=0;

    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto_estadoList LProductoEstado = new clsBeProducto_estadoList();

    private ArrayList<String> EstadoList= new ArrayList<String>();
    private ArrayList<String> PresList= new ArrayList<String>();
    private ArrayList<String> VenceList= new ArrayList<String>();

    // date
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;
    private Object[] a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_recepcion_datos);

        super.InitBase();

        ws = new WebServiceHandler(frm_recepcion_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        cmbEstadoProductoRec = (Spinner)findViewById(R.id.cmbEstadoProductoRec);
        cmbPresRec = (Spinner) findViewById(R.id.cmbPresRec);
        cmbVenceRec = (EditText) findViewById(R.id.cmbVenceRec);

        txtBarra = (EditText) findViewById(R.id.txtBarra);
        txtLoteRec = (EditText) findViewById(R.id.txtLoteRec);
        txtUmbasRec = (EditText) findViewById(R.id.txtUmbasRec);
        txtCantidadRec = (EditText) findViewById(R.id.txtCantidadRec);
        txtPeso= (EditText) findViewById(R.id.txtPeso);
        txtPesoUnitario = (EditText) findViewById(R.id.txtPesoUnitario);
        txtCostoReal = (EditText) findViewById(R.id.txtCostoReal);
        txtCostoOC = (EditText) findViewById(R.id.txtCostoOC);

        lblDatosProd = (TextView)findViewById(R.id.lblTituloForma);
        lblPropPrd = (TextView)findViewById(R.id.lblPropPrd);
        lblPeso = (TextView) findViewById(R.id.textView87);
        lblPUn = (TextView) findViewById(R.id.textView86);
        lblCReal = (TextView) findViewById(R.id.textView89);
        lblCosto = (TextView) findViewById(R.id.textView88);
        lblVence = (TextView) findViewById(R.id.textView81);
        lblLote = (TextView) findViewById(R.id.textView82);
        lblPres = (TextView) findViewById(R.id.textView83);

        btnCantRecibida = (Button)findViewById(R.id.btnCantRecibida);
        btnCantPendiente = (Button)findViewById(R.id.btnCantPendiente);

        dpResult = (DatePicker) findViewById(R.id.datePicker);

        imgDate = (ImageView)findViewById(R.id.imgDate);

        chkPaletizar = (CheckBox)findViewById(R.id.chkPaletizar);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnIr = (Button)findViewById(R.id.btnIr);

        setCurrentDateOnView();

        setHandlers();

        ProgressDialog();

        progress.setMessage("Inicializando valores");

        if (gl.gselitem != null) {
            BeOcDet=gl.gselitem;
        }


            if(!gl.Escaneo_Pallet){
                execws(1);
            }else{
                Load();
            }



    }

    //region Events

    public void ProgressDialog(){
        progress=new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

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

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        cmbVenceRec.setText(new StringBuilder()
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
            cmbVenceRec.setText(new StringBuilder().append(day)
                    .append("-").append(month).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

    private void setHandlers() {

        try{

            cmbEstadoProductoRec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoSelect=LProductoEstado.items.get(position).IdEstado;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbPresRec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdPreseSelect=BeProducto.Presentaciones.items.get(position).IdPresentacion;
                    IndexPresSelected=position;
                    boolean EsPallet = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.EsPallet).first();
                    boolean PermitePaletizar = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.Permitir_paletizar).first();
                    double CajasPorCama = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.CajasPorCama).first();
                    double CamasPorTarima = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.CamasPorTarima).first();

                    if (EsPallet|PermitePaletizar){
                        chkPaletizar.setVisibility(View.GONE);
                    }else if(PermitePaletizar && CajasPorCama>0 && CamasPorTarima>0) {
                        chkPaletizar.setVisibility(View.VISIBLE);
                    }else{
                        chkPaletizar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }


            });

            txtCantidadRec.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        ValidaCampos();
                    }

                    return false;
                }
            });

            txtBarra.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (BeProducto.Control_vencimiento){
                            cmbVenceRec.setSelectAllOnFocus(true);
                            cmbVenceRec.requestFocus();
                        }

                        if (BeProducto.Control_lote){
                            txtLoteRec.setSelectAllOnFocus(true);
                            txtLoteRec.requestFocus();
                        }

                        if (!BeProducto.Control_lote&&!BeProducto.Control_vencimiento){
                            txtCantidadRec.setSelectAllOnFocus(true);
                            txtCantidadRec.requestFocus();
                        }

                    }

                    return false;
                }
            });

            txtCantidadRec.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View v, boolean hasFocus){
                    if (hasFocus)
                        ((EditText)v).selectAll();
                } });



        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    //endregion

    //region validaciones

    private void ValidaCampos(){

        double Cant_Recibida = 0;

        try{

            Cant_Recibida = Double.parseDouble(txtCantidadRec.getText().toString());

            Cant_Recibida = mu.round(Cant_Recibida,6);

            if (BeProducto!=null){
                if (ValidaDatosIngresados()){
                    if (Cant_Recibida_Anterior!=Cant_Recibida && Cant_Recibida_Anterior!=0){
                        Mostro_Propiedades = false;
                        return;
                    }
                    Mostrar_Propiedades_Parametros();
                }
            }

        }catch (Exception e){
            mu.msgbox("ValidaCampos:"+e.getMessage());
        }

    }

    private boolean ValidaDatosIngresados(){

        boolean EsValido=true;

        try{

            if (BeProducto.Control_vencimiento){
                if(cmbVenceRec.getText().toString().isEmpty()){
                    msgboxValidaFechaVence("La fecha no puede estar vacía.");
                    return false;
                }
            }

            if (BeProducto.Control_lote){
                if(txtLoteRec.getText().toString().isEmpty()){
                    msgboxValidaLote("El campo de lote no puede estar vacío.");
                    return false;
                }
            }

            if(BeProducto.Control_peso){
                if (txtPeso.getText().toString().isEmpty()){
                    msgboxValidaPeso("El peso no puede estar vacío");
                    return false;
                }
            }

            if (txtCantidadRec.getText().toString().isEmpty()){
                mu.msgbox("La cantidad no puede estar vacía");
                txtCantidadRec.requestFocus();
                txtCantidadRec.selectAll();
                txtCantidadRec.setSelectAllOnFocus(true);
                return false;
            }else{
                if (Double.parseDouble(txtCantidadRec.getText().toString())<=0){
                    mu.msgbox("La cantidad debe ser mayor a 0");
                    txtCantidadRec.requestFocus();
                    txtCantidadRec.selectAll();
                    txtCantidadRec.setSelectAllOnFocus(true);
                    return false;
                }
            }

        }catch (Exception e){
            mu.msgbox("ValidaDatosIngresados:"+e.getMessage());
        }

        return EsValido;
    }

    public void msgboxValidaLote(String msg) {

        try{

            if (!(msg.isEmpty())){

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setTitle(R.string.app_name);
                dialog.setMessage(msg);
                dialog.setCancelable(false);

                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        txtLoteRec.requestFocus();
                        txtLoteRec.selectAll();
                        txtLoteRec.setSelectAllOnFocus(true);
                    }
                });
                dialog.show();

            }

        } catch (Exception ex) {
        }
    }

    public void msgboxValidaFechaVence(String msg) {

        try{

            if (!(msg.isEmpty())){

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setTitle(R.string.app_name);
                dialog.setMessage(msg);
                dialog.setCancelable(false);

                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cmbVenceRec.requestFocus();
                        cmbVenceRec.selectAll();
                        cmbVenceRec.setSelectAllOnFocus(true);
                    }
                });
                dialog.show();

            }

        } catch (Exception ex) {
        }
    }

    public void msgboxValidaPeso(String msg) {

        try{

            if (!(msg.isEmpty())){

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setTitle(R.string.app_name);
                dialog.setMessage(msg);
                dialog.setCancelable(false);

                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        txtPeso.requestFocus();
                        txtPeso.selectAll();
                        txtPeso.setSelectAllOnFocus(true);
                    }
                });
                dialog.show();

            }

        } catch (Exception ex) {
        }
    }

    private void Muestra_Propiedades_Producto(){

        int vIndiceParam = -1;
        double vCant =0;
        try{


            if (plistBeReDetParametros.items!=null){
                List AuxDetParams = stream(plistBeReDetParametros.items).select(c->c.IdParametroDet).toList();
                vIndiceParam = AuxDetParams.indexOf(pIdRecepcionDet);
            }

            pIndexParam = vIndiceParam;

            if (Mostro_Propiedades){
                Mostro_Propiedades = true;
                return;
            }

            int IndexPresentacion =IndexPresSelected;//BeProducto.Presentaciones.items.indexOf(IdPreseSelect);
            clsBeProducto_Presentacion bePresentacion = new clsBeProducto_Presentacion();

            if (IndexPresentacion!=-1){
                bePresentacion = BeProducto.Presentaciones.items.get(IndexPresentacion);
            }

            if (bePresentacion.EsPallet && bePresentacion.Genera_lp_auto){
                if(txtCantidadRec.getText().toString().isEmpty()){
                    vCant = 0;
                }else {
                    vCant = Double.parseDouble(txtCantidadRec.getText().toString());
                }

                if(vCant>1){
                    mu.msgbox("Los license plate van a ser ingresados manualmente, no puede recepcionar más de un pallet");
                    txtCantidadRec.setText(1+"");
                    txtCantidadRec.selectAll();
                }

            }else if (bePresentacion.Permitir_paletizar && chkPaletizar.isChecked() &&!bePresentacion.Genera_lp_auto){
                if(txtCantidadRec.getText().toString().isEmpty()){
                    vCant = 0;
                }else {
                    vCant = Double.parseDouble(txtCantidadRec.getText().toString());
                }

                if (vCant>bePresentacion.CajasPorCama*bePresentacion.CamasPorTarima){
                    mu.msgbox("Los license plate van a ser ingresados manualmente, no puede recepcionar más de "+bePresentacion.CajasPorCama * bePresentacion.CamasPorTarima
                            + " "+ bePresentacion.Nombre);
                    txtCantidadRec.setText(bePresentacion.CajasPorCama * bePresentacion.CamasPorTarima+"");
                    txtCantidadRec.selectAll();
                }

            }

            MuestraParametros1(this );



        }catch (Exception e){
            mu.msgbox("Muestra_Propiedades_Producto: "+e.getMessage());
        }

    }

    private void MuestraParametros1(Activity activity){

        try{
            int IndexPresentacion=-1;
            double Cant_Recibida_Actual=0;

            if (!txtCantidadRec.getText().toString().isEmpty()){
                Cant_Recibida_Actual = Double.parseDouble(txtCantidadRec.getText().toString());
            }

            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frm_parametros1);

            lblPrducto = (TextView)dialog.findViewById(R.id.lblTituloForma);
            lblSerialP = (TextView)dialog.findViewById(R.id.textView7);
            lblLicPlate = (TextView)dialog.findViewById(R.id.textView5);
            lblFManufact = (TextView)dialog.findViewById(R.id.textView77);
            lblAnada = (TextView) dialog.findViewById(R.id.label1);
            lblTempEsta = (TextView)dialog.findViewById(R.id.textView8);
            lblTempReal = (TextView)dialog.findViewById(R.id.textView91);
            lblPresParam = (TextView)dialog.findViewById(R.id.textView92);
            lblPesoEsta = (TextView)dialog.findViewById(R.id.textView93);
            lblPesoReal = (TextView)dialog.findViewById(R.id.textView94);
            lblSerialIni = (TextView)dialog.findViewById(R.id.textView95);
            lblSerialFin = (TextView)dialog.findViewById(R.id.textView96);
            lblTempTit = (TextView)dialog.findViewById(R.id.lblTempTit);
            lblPesoTit = (TextView)dialog.findViewById(R.id.lblPesoTit);
            lblSerieTit = (TextView)dialog.findViewById(R.id.lblSerieTit);

            txtLicPlate = (EditText) dialog.findViewById(R.id.txtLicPlate);
            txtSerial = (EditText)dialog.findViewById(R.id.txtSerial);
            txtAnada = (EditText)dialog.findViewById(R.id.txtAnada);
            txtFechaManu = (EditText)dialog.findViewById(R.id.txtFechaManu);
            txtFechaIng = (EditText) dialog.findViewById(R.id.txtFechaIng);
            txtTempEsta =(EditText)dialog.findViewById(R.id.txtTempEsta);
            txtTempReal =(EditText)dialog.findViewById(R.id.txtTempReal);
            txtPesoEsta =(EditText)dialog.findViewById(R.id.txtPesoEsta);
            txtPesoReal =(EditText)dialog.findViewById(R.id.txtPesoReal);
            txtSerieIni =(EditText)dialog.findViewById(R.id.txtSerieIni);
            txtSerieFin =(EditText)dialog.findViewById(R.id.txtSerieFin);

            txtTempReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtTempEsta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoEsta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            cmbPresParams = (Spinner)dialog.findViewById(R.id.cmbPresParams);

            cmbPresParams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdPreseSelectParam=BeProducto.Presentaciones.items.get(position).IdPresentacion;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }


            });

            lblPrducto.setText(BeProducto.Codigo + " - " +BeProducto.Nombre);

            if (pListBeStockRec.items!=null){
                List AuxStock=stream(pListBeStockRec.items).select(c->c.IdRecepcionDet).toList();
                pIndexStock =AuxStock.indexOf(pIdRecepcionDet);
            }

            IndexPresentacion = IndexPresSelected;

              clsBeProducto_Presentacion bePresentacion= new clsBeProducto_Presentacion();

            if (IndexPresentacion!= -1){
                bePresentacion = BeProducto.Presentaciones.items.get(IndexPresentacion);
            }

            if (IndexPresentacion!=-1){
                if((bePresentacion.EsPallet|bePresentacion.Permitir_paletizar)&&(bePresentacion.CamasPorTarima ==0| bePresentacion.CajasPorCama==0)){
                   // mu.msgbox("La presentación no tiene los valores necesarios para recepcionar pallets");
                    txtLicPlate.setFocusable(false);
                    txtLicPlate.setFocusableInTouchMode(false);
                    txtLicPlate.setClickable(false);
                    txtLicPlate.setVisibility(View.GONE);
                    lblLicPlate.setVisibility(View.GONE);
                }else{
                    if (bePresentacion.EsPallet | bePresentacion.Permitir_paletizar){

                        if (bePresentacion.Genera_lp_auto){
                            execws(6);
                            txtLicPlate.setFocusable(true);
                            txtLicPlate.setFocusableInTouchMode(true);
                            txtLicPlate.setClickable(true);
                        }

                    } else if (!bePresentacion.EsPallet | bePresentacion.Permitir_paletizar) {

                        if (Cant_Recibida==0){
                            execws(6);
                        }else if(Cant_Recibida_Actual>bePresentacion.Factor){
                            execws(7);

                            pNumeroLP = "";

                            if (pListBeLicensePlate.items!=null){

                                for (int i = pListBeLicensePlate.items.size()-1; i>=0; i--) {
                                    if(pListBeLicensePlate.items.get(i).CantidadDisponible>=Cant_Recibida_Actual){
                                        pNumeroLP = pListBeLicensePlate.items.get(i).LicensePlates;
                                        break;
                                    }
                                }

                            }

                            if (gl.mode==2){
                                pListBeStockRec.items.get(pIndexStock).Lic_plate = pNumeroLP;
                            }

                            txtLicPlate.setVisibility(View.VISIBLE);

                            txtLicPlate.setFocusable(false);
                            txtLicPlate.setFocusableInTouchMode(false);
                            txtLicPlate.setClickable(false);

                        }
                    }else{
                        lblLicPlate.setVisibility(View.GONE);
                        txtLicPlate.setFocusable(false);
                        txtLicPlate.setFocusableInTouchMode(false);
                        txtLicPlate.setClickable(false);
                        txtLicPlate.setVisibility(View.GONE);
                    }

                }

                txtLicPlate.setText(pNumeroLP);
            }else{
                lblLicPlate.setVisibility(View.GONE);
                txtLicPlate.setFocusable(false);
                txtLicPlate.setFocusableInTouchMode(false);
                txtLicPlate.setClickable(false);
                txtLicPlate.setVisibility(View.GONE);
            }

            if (BeProducto.Fechamanufactura && BeProducto.Materia_prima){
                lblFManufact.setVisibility(View.VISIBLE);
                txtFechaManu.setVisibility(View.VISIBLE);

            }else{
                lblFManufact.setVisibility(View.GONE);
                txtFechaManu.setVisibility(View.GONE);
            }

            txtAnada.setText("0");

            if (!BeProducto.Capturar_aniada){
                lblAnada.setVisibility(View.GONE);
                txtAnada.setVisibility(View.GONE);
            }else{
                lblAnada.setVisibility(View.VISIBLE);
                txtAnada.setVisibility(View.VISIBLE);
            }

            //ValidaPeso
            Valida_Peso(IndexPresentacion);

            //ValidaTemperatura
            Valida_Temperatura();

            Valida_Perfil_Serializado();

            Carga_Parametros_Personalizados();

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH)+1;
            day = c.get(Calendar.DAY_OF_MONTH);

            txtFechaIng.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(day).append("-").append(month).append("-")
                    .append(year).append(" "));

            if  (pIndexStock>=0){

                txtLicPlate.setText(pListBeStockRec.items.get(pIndexStock).Lic_plate);

                if (pListBeStockRec.items.get(pIndexStock).Fecha_Ingreso!=null){
                    if (pListBeStockRec.items.get(pIndexStock).Fecha_Ingreso.isEmpty()){
                        txtFechaIng.setText(new StringBuilder()
                                // Month is 0 based, just add 1
                                .append(day).append("-").append(month).append("-")
                                .append(year).append(" "));
                    }else{
                        txtFechaIng.setText(pListBeStockRec.items.get(pIndexStock).Fecha_Ingreso);
                    }
                }

                if (BeProducto.Fechamanufactura && BeProducto.Materia_prima){
                    txtFechaManu.setText(pListBeStockRec.items.get(pIndexStock).Fecha_Manufactura);
                }

                txtSerial.setText(pListBeStockRec.items.get(pIndexStock).Serial);
                txtAnada.setText(pListBeStockRec.items.get(pIndexStock).Anada);

                if (BeProducto.Peso_recepcion){
                    txtPesoReal.setText(mu.round(pListBeStockRec.items.get(pIndexStock).Peso, 6)+"");
                }else{
                    txtPesoReal.setText(mu.round(0, 6)+"");
                }

                if (BeProducto.Temperatura_recepcion){
                txtTempReal.setText(mu.round(pListBeStockRec.items.get(pIndexStock).Temperatura, 6)+"");
                }

            }

            dialog.show();

            mu.msgbox(MensajeParam);

        }catch (Exception e){
        mu.msgbox("MuestraParametros1: "+ e.getMessage());
        }
    }

    public void BotonIrGuardarParametros(View view){
        GuardarParametros();
    }

    public void SalirPantallaParametros(View view){
        dialog.cancel();
    }

    private  void Valida_Perfil_Serializado(){

        try{

            if (BeProducto.Serializado){
                switch (BeProducto.IdPerfilSerializado){

                    case 1:
                        lblSerialP.setVisibility(View.GONE);
                        txtSerial.setVisibility(View.GONE);
                        lblSerialIni.setVisibility(View.GONE);
                        txtSerieIni.setVisibility(View.GONE);
                        lblSerialFin.setVisibility(View.GONE);
                        txtSerieFin.setVisibility(View.GONE);
                        break;
                    case 2:
                        lblSerialP.setVisibility(View.GONE);
                        txtSerial.setVisibility(View.GONE);
                        break;
                    case 3:
                        txtSerial.setText(BeProducto.Noserie);
                    default:
                        lblSerialP.setVisibility(View.GONE);
                        txtSerial.setVisibility(View.GONE);
                        lblSerialIni.setVisibility(View.GONE);
                        txtSerieIni.setVisibility(View.GONE);
                        lblSerialFin.setVisibility(View.GONE);
                        txtSerieFin.setVisibility(View.GONE);
                        break;
                }
            }else{
                lblSerialP.setVisibility(View.GONE);
                txtSerial.setVisibility(View.GONE);
                lblSerialIni.setVisibility(View.GONE);
                txtSerieIni.setVisibility(View.GONE);
                lblSerialFin.setVisibility(View.GONE);
                txtSerieFin.setVisibility(View.GONE);
            }
        }catch (Exception e){
            mu.msgbox("Valida_Perfil_Serializado"+e.getMessage());
        }
    }

    private void Valida_Temperatura(){

        try{

            if (BeProducto.Temperatura_recepcion){
                txtTempEsta.setText( mu.round(BeProducto.Temperatura_referencia, 6)+"");
            }else{
                lblTempTit.setVisibility(View.GONE);
                lblTempEsta.setVisibility(View.GONE);
                lblTempReal.setVisibility(View.GONE);
                txtTempEsta.setVisibility(View.GONE);
                txtTempReal.setVisibility(View.GONE);
            }

        }catch (Exception e){
            mu.msgbox("Valida_Temperatura: "+e.getMessage());
        }
    }

    private void Valida_Peso(int IndexPresentacion){

        try{

            if (BeProducto.Peso_recepcion){

                txtPesoEsta.setText(mu.round(BeProducto.Peso_referencia, 6)+"");
                txtPesoReal.setText(BeProducto.Peso_tolerancia+"");

                if (BeProducto.Presentaciones.items!=null){

                    PresList.clear();

                    for (int i = 0; i <BeProducto.Presentaciones.items.size(); i++) {
                        PresList.add(BeProducto.Presentaciones.items.get(i).Nombre);
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresParams.setAdapter(dataAdapter);

                    if (PresList.size()>0) cmbPresParams.setSelection(0);

                }

                cmbPresParams.setFocusable(false);
                cmbPresParams.setFocusableInTouchMode(false);
                cmbPresParams.setClickable(false);

                if (IdPreseSelect!= -1){
                    List AuxPresParam = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                    IndexPresentacion =AuxPresParam.indexOf(IdPreseSelect);
                    cmbPresParams.setSelection(IndexPresentacion);
                }

            }else{
                lblPresParam.setVisibility(View.GONE);
                cmbPresParams.setVisibility(View.GONE);
                lblPesoTit.setVisibility(View.GONE);
                lblPesoEsta.setVisibility(View.GONE);
                txtPesoEsta.setVisibility(View.GONE);
                lblPesoReal.setVisibility(View.GONE);
                txtPesoReal.setVisibility(View.GONE);
            }

        }catch (Exception e){
            mu.msgbox("Valida Peso: "+e.getMessage());
        }

    }

    private boolean GuardarParametros(){

        String Detalle="";
        boolean Parametros_Ingresados=false;
        clsBeStock_rec BeStock_rec  = new clsBeStock_rec();
        boolean Guardar=false;

        try{

            Parametros_Ingresados =Parametros_Obligatorios_Ingresados(Detalle);

            if (!Parametros_Ingresados){
                mu.msgbox("¿Está seguro de que no va a ingresar los parámetros obligatorios del producto?");

                Parametros_Ingresados = true;
            }

            if (Parametros_Ingresados){

                Peso_Correcto();

            }

        }catch (Exception e){
            mu.msgbox("GuardarParamaetros: "+ e.getMessage());
        }

        return Guardar;
    }

    private boolean Carga_Parametros_Personalizados(){

        boolean Carga_Param=false;

        try{

            if (pIndexParam==-1){

                if (pListBEProductoParametro!=null){

                    if (pListBEProductoParametro.items!=null){
                        Carga_Param = true;
                    }else{
                        execws(10);

                        if (plistBeReDetParametros.items!=null){
                            Carga_Param = true;
                        }
                }

               }

            }

        }catch (Exception e){
            mu.msgbox("Carga_Parametros_Personalizados:"+e.getMessage());
        }

        return  Carga_Param;

    }

    private boolean Temperatura_Correcta(){

        boolean Correcta = false;

        try{

            if (BeProducto.Temperatura_recepcion){

                if (BeProducto.Temperatura_referencia>0){

                    if (txtTempReal.getText().toString().isEmpty()){
                        MensajeParam+="Debe de ingresar la temperatura \n";
                        return false;
                    }else if (Double.parseDouble(txtTempReal.getText().toString())<=0){
                        MensajeParam+="La temperatura debe ser mayor a 0 \n";
                        return false;
                    }

                }

                double PorcentajeToleranciaTemp = mu.round(BeProducto.Temperatura_referencia * BeProducto.Temperatura_tolerancia, 2) / 100;
                double TemperaturaMax  = mu.round(BeProducto.Temperatura_referencia + PorcentajeToleranciaTemp, 2);
                double TemperaturaMin  = mu.round(BeProducto.Temperatura_referencia - PorcentajeToleranciaTemp, 2);
                double vTemp=0;
                if (!txtTempReal.getText().toString().isEmpty()){
                    vTemp = Double.parseDouble(txtTempReal.getText().toString());
                }
                double ValorTemperatura  = mu.round(vTemp, 2);

                if ((ValorTemperatura < TemperaturaMin)|(ValorTemperatura > TemperaturaMax)){
                    msgContinuarTemp("La temperatura ingresada es menor a "+TemperaturaMin + " o mayor a "+TemperaturaMax + "(tolerancia permitida en base a la temperatura estadística). ¿Desea continuar?");
                    //Correcta =TCorrecta;
                    return true;
                }else {
                    if (BeProducto.Genera_lp){
                        execws(8);
                    }else{
                        PallCorrecto=true;
                        ContinuaValidandoParametros();
                    }
                }

            }else{
                if (BeProducto.Genera_lp){
                    execws(8);
                }else{
                    PallCorrecto=true;
                    ContinuaValidandoParametros();
                }
            }

        }catch (Exception e){
            mu.msgbox("Temperatura_Correcta: "+ e.getMessage());
        }

        return  Correcta;

    }

    private boolean Peso_Correcto(){

        boolean Correcto=false;
        double val=0;

        try{

            if (BeProducto.Peso_recepcion){

                val = Double.parseDouble(txtPesoEsta.getText().toString());

                if (val>0){

                    if (txtPesoReal.getText().toString().isEmpty()){
                        MensajeParam +="Debe de ingresar peso \n";
                        return false;
                    }else if(Double.parseDouble(txtPesoReal.getText().toString())<=0){
                        MensajeParam+="El peso debe ser mayor a 0 \n";
                        return false;
                    }

                }

                Double PorcentajeToleranciaPeso = (Double.parseDouble(txtPesoEsta.getText().toString()) * (BeProducto.Peso_tolerancia));
                Double PesoMaximoReferencia = mu.round( Double.parseDouble(txtPesoEsta.getText().toString()) + PorcentajeToleranciaPeso, 2);
                Double PesoMinimoReferencia = mu.round(Double.parseDouble(txtPesoEsta.getText().toString()) - PorcentajeToleranciaPeso, 2);
                Double ValorPeso  = mu.round(Double.parseDouble(txtPesoReal.getText().toString()), 2);

                if (!(ValorPeso >= PesoMinimoReferencia)&&(ValorPeso <= PesoMaximoReferencia)){
                    msgContinuarPeso("El peso ingresado es menor que "+PesoMinimoReferencia +" o mayor que "+ PesoMaximoReferencia+" (tolerancia permitida en base al peso estadístico). ¿Desea continuar?");
                    return true;
                }else{
                    Temperatura_Correcta();
                }


            }else{
                Temperatura_Correcta();
            }

        }catch (Exception e){
            mu.msgbox("Peso_Correcto: "+e.getMessage());
        }

        return Correcto;

    }

    private void ContinuaValidandoParametros(){

        clsBeStock_rec BeStock_rec  = new clsBeStock_rec();

        try {

            if (PallCorrecto){

                if (pIndexStock==-1){

                    BeStock_rec  = new clsBeStock_rec();
                    pListBeStockRec.items = new ArrayList<clsBeStock_rec>();

                    BeStock_rec.IdRecepcionDet = pIdRecepcionDet;
                    BeStock_rec.IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                    BeStock_rec.IdProductoBodega = BeProducto.IdProductoBodega;
                    BeStock_rec.IdUnidadMedida = BeProducto.IdUnidadMedidaBasica;
                    if(IdPreseSelect>0){
                        BeStock_rec.Presentacion = new clsBeProducto_Presentacion();
                    }
                    BeStock_rec.IsNew = true;

                   pListBeStockRec.items.add(BeStock_rec);
                   pIndexStock = pListBeStockRec.items.size()-1;


                    if  ((BeProducto.Presentaciones!=null)){

                        if (BeProducto.Presentaciones.items!=null) {

                            List AuxLisPres = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();

                        int IndexPresentacion = AuxLisPres.indexOf(IdPreseSelect);

                        clsBeProducto_Presentacion bePresentacion = new clsBeProducto_Presentacion();

                        bePresentacion = BeProducto.Presentaciones.items.get(IndexPresentacion);

                        BeStock_rec.Presentacion = bePresentacion;

                        if (BeStock_rec.Presentacion.Imprime_barra){

                            clsBeProducto_pallet BeProdPallet = new clsBeProducto_pallet();
                            BeProdPallet.IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                            BeProdPallet  .IdProductoBodega = BeProducto.IdProductoBodega;
                            BeProdPallet.IdOperadorBodega = gl.IdOperador;
                            BeProdPallet.IdPresentacion = IdPreseSelect;
                            BeProdPallet.IdRecepcionDet = pIdRecepcionDet;
                            BeProdPallet.Impreso = false;
                            BeProdPallet.IdImpresora = 1;
                            BeProdPallet.Activo = true;
                            BeProdPallet.Fecha_ingreso = String.valueOf(du.getFechaActual());

                            if(bePresentacion.EsPallet){
                                BeProdPallet.Codigo_Barra = txtLicPlate.getText().toString();
                            }else{
                                BeProdPallet.Codigo_Barra = bePresentacion.Codigo_barra;
                            }

                            BeProdPallet.Reimpresiones = 0;
                            BeProdPallet.Fec_agr =String.valueOf(du.getFechaActual());
                            BeProdPallet.Fec_mod = String.valueOf(du.getFechaActual());
                            BeProdPallet.IsNew = true;

                            if (pListBeProductoPallet.items!=null){
                                pListBeProductoPallet.items.add(BeProdPallet);
                                pIndexProdPallet = pListBeProductoPallet.items.size() - 1;
                            }else{

                                pListBeProductoPallet.items =  new ArrayList<clsBeProducto_pallet>();

                                pListBeProductoPallet.items.add(BeProdPallet);
                                pIndexProdPallet = pListBeProductoPallet.items.size() - 1;
                            }
                        }
                        }else{
                            BeStock_rec.Presentacion.IdPresentacion = 0;
                        }

                    }else{
                        BeStock_rec.Presentacion.IdPresentacion = 0;
                    }

                }//finpIndexStock

                if (pIndexStock >= 0){

                    if (BeProducto.IdPerfilSerializado == 1){
                        if (pListBeStockSeRec.items==null){
                            MensajeParam+="Ingrese al menos una serie \n";
                        }
                    }else if(BeProducto.IdPerfilSerializado == 2){

                        ObjNS =  new clsBeStock_se_rec();

                        if (pListBeStockSeRec.items!=null){
                            ObjNS.IdStockSeRec = stream(pListBeStockSeRec.items).max(c->c.IdStockSeRec>0).IdStockSeRec+1;
                        }else{
                            execws(9);
                        }

                        ObjNS.NoSerie = "";
                        ObjNS.NoSerieInicial =txtSerieIni.getText().toString();
                        ObjNS.NoSerieFinal = txtSerieFin.getText().toString();
                        ObjNS.User_agr = gl.IdOperador+"";
                        ObjNS.Fec_agr =String.valueOf(du.getFechaActual());
                        ObjNS.User_mod = gl.IdOperador+"";
                        ObjNS.Fec_mod = String.valueOf(du.getFechaActual());
                        ObjNS.Activo = true;
                        ObjNS.IsNew = true;

                        if (pListBeStockSeRec.items!=null){
                            pListBeStockSeRec.items.add(ObjNS);
                        }else{
                            pListBeStockSeRec.items = new ArrayList<clsBeStock_se_rec>();
                            pListBeStockSeRec.items.add(ObjNS);
                        }

                    }else if(BeProducto.IdPerfilSerializado == 3){
                        if (txtSerial.getText().toString().isEmpty()){
                            MensajeParam+="Ingrese Serial \n";
                        }
                    }else{

                        if (!txtSerial.getText().toString().isEmpty()){

                            pListBeStockRec.items.get(pIndexStock).Serial = txtSerial.getText().toString();

                            if (pListBeStockRec!=null){

                                List AuxStockRec = stream(pListBeStockRec.items).select(c->c.Serial).toList();

                                int lIndex=-1;

                                lIndex = AuxStockRec.indexOf(pListBeStockRec.items.get(pIndexStock).Serial);

                                if (lIndex>-1){
                                    mu.msgbox("El Serial " +txtSerial.getText().toString() +" se encuentra ya ingresado");
                                }


                            }

                        }

                    }//TerminaValidacionPerfilSerializado

                    pListBeStockRec.items.get(pIndexStock).IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                    pListBeStockRec.items.get(pIndexStock).IdProductoBodega = BeProducto.IdProductoBodega;
                    pListBeStockRec.items.get(pIndexStock).Lic_plate = txtLicPlate.getText().toString();
                    pListBeStockRec.items.get(pIndexStock).Fecha_Ingreso = String.valueOf(du.getFechaActual());

                    if (IdPreseSelectParam>0){
                        pListBeStockRec.items.get(pIndexStock).IdPresentacion = IdPreseSelectParam;
                        pListBeStockRec.items.get(pIndexStock).Presentacion = new clsBeProducto_Presentacion();
                        pListBeStockRec.items.get(pIndexStock).Presentacion.IdPresentacion =IdPreseSelectParam;
                    }else {
                        if (IdPreseSelect>0){
                            pListBeStockRec.items.get(pIndexStock).IdPresentacion = IdPreseSelect;
                            pListBeStockRec.items.get(pIndexStock).Presentacion = new clsBeProducto_Presentacion();
                            pListBeStockRec.items.get(pIndexStock).Presentacion.IdPresentacion =IdPreseSelect;
                        }
                    }

                    pListBeStockRec.items.get(pIndexStock).Serial = txtSerial.getText().toString();
                    pListBeStockRec.items.get(pIndexStock).Anada = Integer.parseInt(txtAnada.getText().toString());
                    pListBeStockRec.items.get(pIndexStock).Fec_agr = gl.gBeRecepcion.Fecha_recepcion;
                    pListBeStockRec.items.get(pIndexStock).User_agr = gl.IdOperador+"";
                    pListBeStockRec.items.get(pIndexStock).Fec_mod = gl.gBeRecepcion.Fecha_recepcion;
                    pListBeStockRec.items.get(pIndexStock).User_mod = gl.IdOperador+"";
                    pListBeStockRec.items.get(pIndexStock).IsNew = true;
                    pListBeStockRec.items.get(pIndexStock).Activo = true;
                    pListBeStockRec.items.get(pIndexStock).IdRecepcionDet = pIdRecepcionDet;

                    if (BeProducto.Peso_recepcion){
                        pListBeStockRec.items.get(pIndexStock).Peso = Double.parseDouble(txtPesoReal.getText().toString());
                    }else{
                        pListBeStockRec.items.get(pIndexStock).Peso = 0.0;
                    }

                    if (BeProducto.Temperatura_recepcion){
                        pListBeStockRec.items.get(pIndexStock).Temperatura = Double.parseDouble(txtTempReal.getText().toString());
                    }else{
                        pListBeStockRec.items.get(pIndexStock).Temperatura = 0.0;
                    }

                    pListBeStockRec.items.get(pIndexStock).Regularizado =false;
                    pListBeStockRec.items.get(pIndexStock).Fecha_regularizacion =du.convierteFecha("01-01-1900");

                    if (pListBeStockSeRec.items!=null){
                        for (clsBeStock_se_rec bo: pListBeStockSeRec.items){
                            bo.IdStockRec = pListBeStockRec.items.get(pIndexStock).IdStockRec;
                        }
                    }

                    if (gl.mode==2){

                        if (plistBeReDetParametros.items!=null){

                            for (clsBeTrans_re_det_parametros Obj: plistBeReDetParametros.items){

                                Obj.IdProductoParametro = Obj.IdProductoParametro;
                                Obj.IdRecepcionDet = pIdRecepcionDet;
                                Obj.IdRecepcionEnc = gl.gIdRecepcionEnc;
                                Obj.ProductoParametro.IdProductoParametro = Obj.IdProductoParametro;
                                Obj.TipoParametro = Obj.TipoParametro;
                                Obj.Valor_fecha = Obj.Valor_fecha;
                                Obj.Valor_texto = Obj.Valor_texto;
                                Obj.Valor_numerico = Obj.Valor_numerico;
                                Obj.Valor_logico = Obj.Valor_logico;
                                Obj.Valor_Unico = Obj.Valor_Unico;
                                Obj.User_agr = gl.IdOperador+"";
                                Obj.Fec_agr = String.valueOf(du.getFechaActual());
                                Obj.IsNew = true;


                            }

                        }

                    }else{

                        if (pListBEProductoParametro!=null){

                            if (pListBEProductoParametro.items!=null){

                                for (clsBeProducto_parametros obj: pListBEProductoParametro.items){

                                    clsBeTrans_re_det_parametros ObjDP = new clsBeTrans_re_det_parametros();

                                    ObjDP.IdRecepcionDet = pIdRecepcionDet;
                                    ObjDP.IdProductoParametro = obj.IdProductoParametro;

                                    ObjDP.IdParametroDet = 0;
                                    ObjDP.IdProductoParametro = obj.IdProductoParametro;
                                    ObjDP.IdRecepcionDet = pIdRecepcionDet;
                                    ObjDP.IdRecepcionEnc = gl.gIdRecepcionEnc;
                                    ObjDP.ProductoParametro = obj;
                                    ObjDP.TipoParametro = obj.TipoParametro;
                                    ObjDP.Valor_fecha = obj.Valor_fecha;
                                    ObjDP.Valor_texto = obj.Valor_texto;
                                    ObjDP.Valor_numerico = obj.Valor_numerico;
                                    ObjDP.Valor_logico = obj.Valor_logico;
                                    ObjDP.Valor_Unico = obj.Valor_Unico;
                                    ObjDP.User_agr = gl.IdOperador+"";
                                    ObjDP.Fec_agr = String.valueOf(du.getFechaActual());
                                    ObjDP.IsNew = true;

                                    if(gl.mode==1){
                                        plistBeReDetParametros.items.add(ObjDP);
                                    }

                                }

                            }
                        }

                    }

                    if (pListBeStockRec.items.get(pIndexStock).Presentacion!=null){

                        if (pListBeStockRec.items.get(pIndexStock).Presentacion.IdPresentacion!=-1){

                            clsBeProducto_Presentacion bePresentacion = new clsBeProducto_Presentacion();

                            bePresentacion = pListBeStockRec.items.get(pIndexStock).Presentacion;

                            BeStock_rec.Presentacion = bePresentacion;

                            if (BeStock_rec.Presentacion.Imprime_barra&&BeStock_rec.Presentacion.EsPallet){

                                pListBeProductoPallet.items.get(pIndexProdPallet).IdPropietarioBodega=gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                                pListBeProductoPallet.items.get(pIndexProdPallet).IdProductoBodega = BeProducto.IdProductoBodega;
                                pListBeProductoPallet.items.get(pIndexProdPallet).IdOperadorBodega = 0;
                                pListBeProductoPallet.items.get(pIndexProdPallet).IdPresentacion = IdPreseSelectParam;
                                pListBeProductoPallet.items.get(pIndexProdPallet).IdRecepcionDet = pIdRecepcionDet;
                                pListBeProductoPallet.items.get(pIndexProdPallet).Impreso = false;
                                pListBeProductoPallet.items.get(pIndexProdPallet).IdImpresora = 1;
                                pListBeProductoPallet.items.get(pIndexProdPallet).Activo = true;
                                pListBeProductoPallet.items.get(pIndexProdPallet).Fecha_ingreso = String.valueOf(du.getFechaActual());
                                pListBeProductoPallet.items.get(pIndexProdPallet).Codigo_Barra = txtLicPlate.getText().toString();
                                pListBeProductoPallet.items.get(pIndexProdPallet).Reimpresiones = 0;
                                pListBeProductoPallet.items.get(pIndexProdPallet).Fec_agr = String.valueOf(du.getFechaActual());
                                pListBeProductoPallet.items.get(pIndexProdPallet).Fec_mod = String.valueOf(du.getFechaActual());

                            }

                        }
                    }

                    dialog.cancel();
                    Mostro_Propiedades = true;
                    Guardar_Recepcion_Nueva();

                }else{

                    ObjS = new clsBeStock_rec();

                    if (BeProducto.IdPerfilSerializado == 1){
                        MensajeParam+=" Ingrese al menos una serie \n";
                        return;
                    }else if(BeProducto.IdPerfilSerializado == 2){

                        ObjNS =new clsBeStock_se_rec();

                        if (pListBeStockSeRec.items!=null){
                            ObjNS.IdStockSeRec = stream(pListBeStockSeRec.items).max(c->c.IdStockSeRec>0).IdStockSeRec+1; //pListBeStockSeRec.Max(Function(b) b.IdStockSeRec) + 1
                            ValidaParametrosDespuesSeRec();
                        }else{
                            execws(9);
                        }

                    }else if(BeProducto.IdPerfilSerializado == 3){

                        if (txtSerial.getText().toString().isEmpty()){
                            MensajeParam+="Ingrese serial \n";
                            return;
                        }

                    }else{

                        if (!txtSerial.getText().toString().isEmpty()){

                            ObjS.Serial = txtSerial.getText().toString();

                            if (pListBeStockRec.items!=null){

                                List AuxListSerial = stream(pListBeStockRec.items).select(c->c.Serial).toList();

                                int lIndex=-1;

                                lIndex = AuxListSerial.indexOf( ObjS.Serial );

                                if (lIndex>-1){
                                    MensajeParam+="El Serial "+ txtSerial.getText().toString()+" se encuentra ya ingresado\n";
                                    return;
                                }

                            }

                        }

                    }

                    UltimoEspacioDeValidar();

                }

            }

        }catch (Exception e){
            mu.msgbox("ContinuaValidandoParametros:"+e.getMessage());
        }
    }

    private void UltimoEspacioDeValidar(){
        try{
            if (pListBeStockRec!=null){
                if (pListBeStockRec.items!=null){
                    ObjS.IdStockRec = stream(pListBeStockRec.items).max(c->c.IdStockRec>0).IdStockRec+1; // pListBeStockRec.Max(Function(b) b.IdStockRec) + 1
                    SigueValidandoParametros();
                }else{
                    execws(11);
                }
            }else{
                execws(11);
            }
        }catch (Exception e){
            mu.msgbox("UltimoEspacioDeValidar"+e.getMessage());
        }
    }

    private void ValidaParametrosDespuesSeRec(){

        try{

            if(BeProducto.IdPerfilSerializado == 2){

                ObjNS.NoSerie = "";
                ObjNS.NoSerieInicial = txtSerieIni.getText().toString();
                ObjNS.NoSerieFinal = txtSerieFin.getText().toString();
                ObjNS.User_agr = gl.IdOperador+"";
                ObjNS.Fec_agr = String.valueOf(du.getFechaActual());
                ObjNS.User_mod = gl.IdOperador+"";
                ObjNS.Fec_mod = String.valueOf(du.getFechaActual());
                ObjNS.Activo = true;
                ObjNS.IsNew = true;
                pListBeStockSeRec.items.add(ObjNS);

            }

            UltimoEspacioDeValidar();

        }catch (Exception e){
            mu.msgbox("ValidaParametrosDespuesSeRec:"+e.getMessage());
        }
    }

    private void SigueValidandoParametros(){

        try{

            ObjS.IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
            ObjS.IdProductoBodega = BeProducto.IdProductoBodega;

            ObjS.Lic_plate = txtLicPlate.getText().toString();

            ObjS.Fecha_Ingreso = String.valueOf(du.getFechaActual());

            if (BeProducto.Fechamanufactura){
                ObjS.Fecha_Manufactura = txtFechaManu.getText().toString();
            }else{
                ObjS.Fecha_Manufactura = du.convierteFecha("01-01-1900");
            }

            ObjS.Serial = txtSerial.getText().toString();
            ObjS.Anada = Integer.parseInt(txtAnada.getText().toString());
            ObjS.Fec_agr = String.valueOf(du.getFechaActual());
            ObjS.User_agr = gl.IdOperador+"";
            ObjS.Fec_mod = String.valueOf(du.getFechaActual());
            ObjS.User_mod = gl.IdOperador+"";
            ObjS.IsNew = true;
            ObjS.Activo = true;
            ObjS.IdRecepcionDet = pIdRecepcionDet;

            if (IdPreseSelectParam>0){
                ObjS.IdPresentacion = IdPreseSelectParam;
                ObjS.Presentacion = new clsBeProducto_Presentacion();
                ObjS.Presentacion.IdPresentacion = IdPreseSelectParam;
            }else{
                ObjS.IdPresentacion = IdPreseSelect;
                ObjS.Presentacion = new clsBeProducto_Presentacion();
                ObjS.Presentacion.IdPresentacion = IdPreseSelect;
            }

            if (BeProducto.Peso_recepcion){
                ObjS.Peso = Double.parseDouble(txtPesoReal.getText().toString());
            }else{
                ObjS.Peso = 0.0;
            }

            if (BeProducto.Temperatura_recepcion){
                ObjS.Temperatura = Double.parseDouble(txtTempReal.getText().toString());
            }else{
                ObjS.Temperatura = 0.0;
            }

            ObjS.Regularizado = false;
            ObjS.Fecha_regularizacion = "";

            pListBeStockRec.items.add(ObjS);

            if (pListBeStockSeRec.items!=null){
                for (clsBeStock_se_rec bo:pListBeStockSeRec.items){
                    bo.IdStockRec = ObjS.IdStockRec;
                }
            }

            for (clsBeProducto_parametros Obj: pListBEProductoParametro.items){

                clsBeTrans_re_det_parametros ObjDP = new clsBeTrans_re_det_parametros();

                ObjDP.IdRecepcionDet = pIdRecepcionDet;
                ObjDP.IdProductoParametro = Obj.IdProductoParametro;
                ObjDP.IdParametroDet = 0;
                ObjDP.IdRecepcionEnc = gl.gIdRecepcionEnc;
                ObjDP.ProductoParametro = Obj;
                ObjDP.TipoParametro = Obj.TipoParametro;
                ObjDP.Valor_fecha = Obj.Valor_fecha;
                ObjDP.Valor_texto = Obj.Valor_texto;
                ObjDP.Valor_numerico = Obj.Valor_numerico;
                ObjDP.Valor_logico = Obj.Valor_logico;
                ObjDP.Valor_Unico = Obj.Valor_Unico;
                ObjDP.User_agr = gl.IdOperador+"";
                ObjDP.Fec_agr = String.valueOf(du.getFechaActual());
                ObjDP.IsNew = true;

                if (Obj.IsNew){
                    plistBeReDetParametros.items.add(ObjDP);
                }

            }

            dialog.cancel();
            Mostro_Propiedades = true;
            Guardar_Recepcion_Nueva();

        }catch (Exception e){
            mu.msgbox("SigueValidandoParametros:"+e.getMessage());
        }
    }


    private void msgContinuarTemp(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (BeProducto.Genera_lp){
                        execws(8);
                    }else{
                        PallCorrecto=true;
                        ContinuaValidandoParametros();
                    }

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                 return;
                }
            });

            dialog.show();
            dialog.wait();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgContinuarPeso(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Temperatura_Correcta();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public boolean Parametros_Obligatorios_Ingresados(String Detalle) {

        boolean Parametros=true;
        int Indice=-1;
        String Descripcion;
        int pIdProductoParametro=-1;

        try{



        }catch (Exception e){
            mu.msgbox("Parametros_Obligatorios_Ingresados: "+e.getMessage());
        }

        return Parametros;
    }

    private boolean Mostrar_Propiedades_Parametros(){

        boolean Mostrar=false;

        try{

            if (!Mostro_Propiedades){
                execws(5);
            }else{
                Guardar_Recepcion_Nueva();
            }

            Mostrar_Propiedades_Parametros = Mostrar;

        }catch (Exception e){

        }

        return Mostrar;
    }

    //endregion

    //region CargaProducto

    private void Load(){

        try{

            progress.setMessage("Cargando valores de tarea");

            if (gl.gselitem != null) {

                Escaneo_Pallet = gl.gEscaneo_Pallet;

                BeProducto.IdProductoBodega = BeOcDet.IdProductoBodega;
                pIdOrdenCompraDet= BeOcDet.IdOrdenCompraDet;
                pIdOrdenCompraEnc = BeOcDet.IdOrdenCompraEnc;
                pLineaOC = BeOcDet.No_Linea;

                if (Escaneo_Pallet){

                    BeINavBarraPallet = frm_list_rec_prod.BeINavBarraPallet;

                    if (frm_list_rec_prod.BeProducto!=null){
                        if (frm_list_rec_prod.BeProducto.IdProducto>0){
                            BeProducto = frm_list_rec_prod.BeProducto;
                        }
                    }

                }else{

                    if (frm_list_rec_prod.BeProducto!=null){
                        if (frm_list_rec_prod.BeProducto.IdProducto>0){
                            BeProducto = frm_list_rec_prod.BeProducto;
                        }
                    }

                    if (gl.Carga_Producto_x_Pallet){

                        Carga_Datos_Producto_Por_Pallet();

                    }else{
                        txtCantidadRec.requestFocus();
                        txtCantidadRec.selectAll();
                        txtCantidadRec.setSelectAllOnFocus(true);

                        Carga_Datos_Producto();
                    }

                }

            }

        }catch (Exception e){
            mu.msgbox("Load:"+ e.getMessage());
        }


    }

    private void Carga_Datos_Producto_Por_Pallet(){

        try{

            if (BeProducto.IdProducto!=0){

                Mostro_Propiedades = false;

                plistBeReDetParametros =  new clsBeTrans_re_det_parametrosList();
                lBeProdPallet = new clsBeProducto_palletList();
                pListBeStockSeRec = new clsBeStock_se_recList();

                if (pListTransRecDet.items!=null){

                    pIdRecepcionDet = stream(pListTransRecDet.items).max(c->c.IdRecepcionDet>0).IdRecepcionDet+1;

                    if (gl.TipoOpcion==2){
                        pLineaOC  = stream(pListTransRecDet.items).max(c->c.IdRecepcionDet>0).IdRecepcionDet+1;
                    }else if(pLineaOC==-1){
                        pLineaOC= stream(gl.gBeRecepcion.OrdenCompraRec.OC.DetalleOC.items).max(c->c.IdOrdenCompraDet>0).IdOrdenCompraDet+1;
                    }

                }else{

                    pListTransRecDet.items = new ArrayList<clsBeTrans_re_det>();

                    execws(19);

                }

            }

        }catch (Exception e){
            mu.msgbox("Carga_Datos_Producto_Por_Pallet:"+e.getMessage());
        }
    }

    private void Carga_Datos_Producto(){

        int IndiceOCDet;
        int vPresentacion;
        int IndicePres;
        double Factor;
        double CostoOC=0;

        try{

            progress.setMessage("Cargando datos de producto");

            if (BeProducto.IdProducto!=0){

                Mostro_Propiedades = false;

                plistBeReDetParametros = new clsBeTrans_re_det_parametrosList();
                lBeStockRec = new clsBeStock_recList();
                lBeProdPallet = new clsBeProducto_palletList();
                pListBeStockSeRec = new clsBeStock_se_recList();
                pListBeStockRec = new clsBeStock_recList();
                pListBeProductoPallet = new clsBeProducto_palletList();
                pListTransRecDet = new clsBeTrans_re_detList();

                if (pListTransRecDet.items!=null){

                    pIdRecepcionDet = stream(pListTransRecDet.items).max(c->c.IdRecepcionDet>0).IdRecepcionDet+1;

                    if (gl.TipoOpcion==2){
                        pLineaOC  = stream(pListTransRecDet.items).max(c->c.IdRecepcionDet>0).IdRecepcionDet+1;
                    }else if(pLineaOC==-1){
                        pLineaOC= stream(gl.gBeRecepcion.OrdenCompraRec.OC.DetalleOC.items).max(c->c.IdOrdenCompraDet>0).IdOrdenCompraDet+1;
                    }

                }else{
                    pListTransRecDet.items = new ArrayList<clsBeTrans_re_det>();

                    execws(19);

                    //Listar_Producto_Presentaciones();

                }

            }


        }catch (Exception e){
            mu.msgbox("CargarDatos: "+e.getMessage());
        }

    }

    private void LlenaDatosFaltantes(){

        vPresentacion=0;
        CostoOC=0;
        double Factor=0;
        boolean EsPallet=false;
        int Indx=-1;

        try{

            progress.setMessage("Terminando de cargar datos y validando valores");

            if (Escaneo_Pallet){
                txtBarra.setText(BeINavBarraPallet.Codigo_barra);
            }else{
                txtBarra.setText(Get_Codigo_Barra(gl.CodigoRecepcion));
            }

            if(BeProducto.IdProductoBodega>0){
                pIdProductoBodega = BeProducto.IdProductoBodega;
            }

            lblDatosProd.setText(BeProducto.Codigo + " - " + BeProducto.Nombre);
            lblPropPrd.setText(BeProducto.Propietario.Nombre_comercial);

            if (BeProducto.Control_vencimiento){
                lblVence.setVisibility(View.VISIBLE);
                cmbVenceRec.setVisibility(View.VISIBLE);
                imgDate.setVisibility(View.VISIBLE);

                if (!gl.gFechaVenceAnterior.equals("")){
                    cmbVenceRec.setText(gl.gFechaVenceAnterior);
                }

            }else{
                cmbVenceRec.setVisibility(View.GONE);
                lblVence.setVisibility(View.GONE);
                imgDate.setVisibility(View.GONE);
            }

            Valida_Lote();

            clsBeTrans_oc_det BeOcDet =
                    stream(gl.gpListDetalleOC.items)
                            .where(c -> c.IdOrdenCompraDet == pIdOrdenCompraDet)
                            .first();

            vPresentacion = Get_Presentacion_A_Recibir();

            if (vPresentacion>0){

                Factor =Get_Factor_Presentacion(vPresentacion);
                EsPallet = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.EsPallet).first();

                if (EsPallet){
                    Factor = Factor * stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.CajasPorCama).first() * stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.CamasPorTarima).first();
                }

                List AxuListPres = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                Indx =AxuListPres.indexOf(vPresentacion);

                cmbPresRec.setSelection(Indx);

            }else{

                if (vPresentacion>0){

                    Factor = 1;

                    if (BeProducto.Presentaciones!=null){
                        cmbPresRec.setVisibility(View.VISIBLE);
                        cmbPresRec.setSelection(0);
                    }else{
                        cmbPresRec.setVisibility(View.GONE);
                    }

                }else{
                    lblPres.setVisibility(View.GONE);
                    cmbPresRec.setVisibility(View.GONE);
                }

            }

            if (gl.Carga_Producto_x_Pallet){

                if  (BeProducto.Presentaciones.items.get(Indx).EsPallet){

                    Cant_Recibida = gBeStockRec.Uds_lic_plate;
                    Cant_A_Recibir = Factor;

                    FinalizCargaProductos();

                }else{

                execws(22);

                }

            }else{

                Cant_Recibida = stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet ==pIdOrdenCompraDet).select(c->c.Cantidad_recibida).first();
                Cant_A_Recibir = stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet ==pIdOrdenCompraDet).select(c->c.Cantidad).first();
                if(Cant_Recibida - Cant_Recibida<0){
                    Cant_Pendiente = 0;
                }else{
                    Cant_Pendiente =  Cant_A_Recibir - Cant_Recibida;
                }

            txtCantidadRec.setText(Cant_Pendiente+"");
                txtCantidadRec.selectAll();

            clsBeTrans_oc_det_lote BeLoteLinea;

            if (BeOcDet!=null){

                if (gl.gBeOrdenCompra.DetalleLotes.items!=null){

                    if (gl.gBeOrdenCompra.DetalleLotes.items.size()>0){

                        BeLoteLinea = stream(gl.gBeOrdenCompra.DetalleLotes.items)
                                .where(c->c.No_linea == BeOcDet.No_Linea &&
                                        c.IdOrdenCompraDet == pIdOrdenCompraDet
                                        && c.Codigo_producto == BeOcDet.Codigo_Producto).first();

                        if (BeLoteLinea!=null){
                            txtLoteRec.setText(BeLoteLinea.Lote);

                            if (!BeLoteLinea.Fecha_vence.isEmpty()){

                                VenceList.clear();

                                VenceList.add(BeLoteLinea.Fecha_vence);

                                cmbVenceRec.setText(VenceList.get(0));

                            }
                        }

                    }
                }
            }

                FinalizCargaProductos();

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    private void FinalizCargaProductos(){

        try{

            txtUmbasRec.setText(BeProducto.UnidadMedida.Nombre);
            txtPeso.setText("");

            if (BeProducto.Control_lote){
                if (!gl.gLoteAnterior.isEmpty()){
                    txtLoteRec.setText(gl.gLoteAnterior);
                }
            }

            if (!BeProducto.Control_peso){
                txtPeso.setVisibility(View.GONE);
                txtPesoUnitario.setVisibility(View.GONE);
                lblPeso.setVisibility(View.GONE);
                lblPUn.setVisibility(View.GONE);
            }

            if(!BeProducto.Control_vencimiento){
                cmbVenceRec.setVisibility(View.GONE);
                lblVence.setVisibility(View.GONE);
                imgDate.setVisibility(View.GONE);
            }

            if (!gl.gBeRecepcion.Muestra_precio){
                txtCostoOC.setVisibility(View.GONE);
                txtCostoReal.setVisibility(View.GONE);
                lblCosto.setVisibility(View.GONE);
                lblCReal.setVisibility(View.GONE);
            }

            CostoOC = stream(gl.gpListDetalleOC.items).where(c->c.IdProductoBodega == pIdProductoBodega
                    && c.IdPresentacion == vPresentacion).select(c->c.Costo).first();

            if (gl.gBeOrdenCompra!=null) {
                if (((gl.gBeOrdenCompra.IdOrdenCompraEnc > 0) && (CostoOC > 0))) {
                    txtCostoOC.setText(mu.round(CostoOC, 10)+"");
                }
                else {
                    txtCostoOC.setText(mu.round(BeProducto.Costo,10)+"");
                }

            } else {
                txtCostoOC.setText(mu.round(BeProducto.Costo,10)+"");
            }

            txtCostoOC.setText(mu.round(BeProducto.Costo,10)+"");

            txtUmbasRec.setFocusable(false);
            txtUmbasRec.setFocusableInTouchMode(false);
            txtUmbasRec.setClickable(false);

            txtBarra.setText(BeProducto.Codigo);

            txtBarra.setFocusable(false);
            txtBarra.setFocusableInTouchMode(false);
            txtBarra.setClickable(false);

            txtCantidadRec.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCostoOC.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPeso.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCostoReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoUnitario.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            btnCantPendiente.setText("Pendiente: "+mu.round(Cant_Pendiente,6));
            btnCantRecibida.setText("Recibido: "+mu.round(Cant_Recibida,6));

            if (gl.Carga_Producto_x_Pallet){

                txtLoteRec.setText(gBeStockRec.Lote);

                txtLoteRec.setFocusable(false);
                txtLoteRec.setFocusableInTouchMode(false);
                txtLoteRec.setClickable(false);

                cmbVenceRec.setText(gBeStockRec.Fecha_Vence);

                cmbVenceRec.setFocusable(false);
                cmbVenceRec.setFocusableInTouchMode(false);
                cmbVenceRec.setClickable(false);

            }

            if (Escaneo_Pallet){
                LlenaCamposEsPallet();
            }

            progress.cancel();

        }catch (Exception e){
            mu.msgbox("FinalizCargaProductos:"+e.getMessage());
        }
    }

    private void LlenaCamposEsPallet(){

        try{

            txtLoteRec.setText(BeINavBarraPallet.Lote);
            cmbVenceRec.setText(BeINavBarraPallet.Fecha_Vence);

            if (!EsTransferenciaInternaWMS){
                txtCantidadRec.setText(BeINavBarraPallet.Cantidad_UMP+"");
                txtCantidadRec.selectAll();
            }else{
                txtCantidadRec.setText(BeINavBarraPallet.Cantidad_Presentacion+"");
                txtCantidadRec.selectAll();
            }

            List AuxList = stream(BeProducto.Presentaciones.items).select(c->c.Codigo_barra).toList();

            int indxPres=AuxList.indexOf(BeINavBarraPallet.UM_Producto);

            if (indxPres>-1){
                cmbPresRec.setSelection(indxPres);
            }else{
                mu.msgbox("No está definida la presentación contenida en pallet para el LP: "+BeINavBarraPallet.IdPallet);
            }

            List AuxLis1=stream(LProductoEstado.items).select(c->c.IdEstado).toList();

            int indxEstado=AuxLis1.indexOf(gl.gIdProductoBuenEstadoPorDefecto);

            if(indxEstado>-1){
                cmbEstadoProductoRec.setSelection(indxEstado);
            }else{
                mu.msgbox("No existe un estado por defecto");
                return;
            }

            Llena_Detalle_Recepcion_Nueva();

            Llena_Stock_Rec_Pallet_Proveedor();

            double vCantidad  = BeINavBarraPallet.Cajas_Por_Cama * BeINavBarraPallet.Camas_Por_Tarima;

            String vMensaje1 = "Código: "+BeINavBarraPallet.Codigo+"\n "
                    +"Cant: "+vCantidad +"\n "
                    +"Pres: "+ BeProducto.Presentaciones.items.get(indxPres).Nombre +"\n"
                    +"Venc: "+BeINavBarraPallet.Fecha_Vence+"\n "
                    +"Lote: "+BeINavBarraPallet.Lote +"\n"
                    +"¿El producto está completo y en buen estado?";



            msgValidaProductoPallet(vMensaje1);

        }catch (Exception e){
            mu.msgbox("LlenaCamposEsPallet:"+e.getMessage());
        }
    }

    private void Guardar_Pallet(){

        try{

            vBeStockRecPallet.Uds_lic_plate = BeINavBarraPallet.Cantidad_UMP;
            vBeStockRecPallet.No_bulto = 0;

            execws(21);

        }catch (Exception e){
            mu.msgbox("Guardar_Pallet:"+e.getMessage());
        }

    }

    private void Llena_Stock_Rec_Pallet_Proveedor(){

        vBeStockRecPallet = new clsBeStock_rec();
        double vCantidad=0;

        try{

            if (BeINavBarraPallet!=null){

                vBeStockRecPallet.IdStockRec = 0;
                vBeStockRecPallet.IdPropietarioBodega = gl.gBeOrdenCompra.IdPropietarioBodega;
                vBeStockRecPallet.IdProductoBodega = BeProducto.IdProductoBodega;
                if (IdPreseSelect>0){
                    vBeStockRecPallet.IdPresentacion =IdPreseSelect;
                    vBeStockRecPallet.Presentacion = new  clsBeProducto_Presentacion();
                    vBeStockRecPallet.Presentacion.IdPresentacion = IdPreseSelect;
                }else{
                    vBeStockRecPallet.IdPresentacion =0;
                    vBeStockRecPallet.Presentacion = new  clsBeProducto_Presentacion();
                    vBeStockRecPallet.Presentacion.IdPresentacion = 0;
                }

                vBeStockRecPallet.IdUnidadMedida = BeProducto.IdUnidadMedidaBasica;
                vBeStockRecPallet.IdRecepcionEnc = gl.gBeRecepcion.IdRecepcionEnc;
                vBeStockRecPallet.IdRecepcionDet = pIdRecepcionDet;
                vBeStockRecPallet.Lote = BeINavBarraPallet.Lote;
                vBeStockRecPallet.Lic_plate = BeINavBarraPallet.Codigo_barra;
                vBeStockRecPallet.Serial = "";

                if (vBeStockRecPallet.IdPresentacion > 0){

                    if (!EsTransferenciaInternaWMS){
                        if (BeProducto.Presentacion.EsPallet){
                            vCantidad = BeINavBarraPallet.Cantidad_UMP * BeProducto.Presentacion.Factor * BeProducto.Presentacion.CamasPorTarima * BeProducto.Presentacion.CajasPorCama;
                        }else{
                            vCantidad = BeProducto.Presentacion.Factor * BeINavBarraPallet.Cantidad_UMP;
                        }
                    }else{

                        if (BeProducto.Presentacion.EsPallet){
                            vCantidad = BeINavBarraPallet.Cantidad_Presentacion * BeProducto.Presentacion.Factor * BeProducto.Presentacion.CamasPorTarima * BeProducto.Presentacion.CajasPorCama;
                        }else{
                            vCantidad = BeINavBarraPallet.Cantidad_Presentacion * BeProducto.Presentacion.Factor;
                        }

                    }

                }else{
                    vCantidad = BeINavBarraPallet.Cantidad_UMP;
                }

                vBeStockRecPallet.Cantidad = vCantidad;
                vBeStockRecPallet.Fecha_Ingreso = du.getFechaActual();
                vBeStockRecPallet.Fecha_Vence = BeINavBarraPallet.Fecha_Vence;
                vBeStockRecPallet.Uds_lic_plate = BeINavBarraPallet.Cantidad_Presentacion;
                vBeStockRecPallet.No_bulto = BeINavBarraPallet.IdPallet;
                vBeStockRecPallet.Fecha_Manufactura = BeINavBarraPallet.Fecha_Produccion;
                vBeStockRecPallet.Serial = "";
                vBeStockRecPallet.Anada = 0;
                vBeStockRecPallet.Fec_agr = du.getFechaActual();
                vBeStockRecPallet.User_agr = gl.IdOperador+"";
                vBeStockRecPallet.Fec_mod = du.getFechaActual();
                vBeStockRecPallet.User_mod = gl.IdOperador+"";
                vBeStockRecPallet.Peso = 0.0;
                vBeStockRecPallet.Temperatura = 0.0;
                vBeStockRecPallet.Regularizado = false;
                vBeStockRecPallet.IsNew = true;

                vBeStockRecPallet.ProductoEstado = new  clsBeProducto_estado();

                if (IdEstadoSelect>0){

                    vBeStockRecPallet.ProductoEstado.IdEstado = IdEstadoSelect;
                    vBeStockRecPallet.IdProductoEstado = IdEstadoSelect;

                    if (LProductoEstado.items.size()>0){

                        if (stream(LProductoEstado.items).where(c->c.IdEstado ==IdEstadoSelect).select(c->c.Danado).first()){
                            execws(20);
                        }else{
                            vBeStockRecPallet.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                        }

                    }

                }

                Continua_Llenando_Stock_Pallet_Proveedor();

            }

        }catch (Exception e){
            mu.msgbox("Llena_Stock_Rec_Pallet_Proveedor");
        }

    }

    private void Continua_Llenando_Stock_Pallet_Proveedor(){

        try{


            vBeStockRecPallet.IdUbicacion_anterior = vBeStockRecPallet.IdUbicacion;

            if (!txtUmbasRec.getText().toString().isEmpty()){
                vBeStockRecPallet.IdUnidadMedida = BeProducto.UnidadMedida.IdUnidadMedida;
            }

            vBeStockRec.IdRecepcionEnc = gl.gBeRecepcion.IdRecepcionEnc;
            vBeStockRec.ProductoValidado = true;
            vBeStockRec.No_linea = pLineaOC;

        }catch (Exception e){
            mu.msgbox("Continua_Llenando_Stock_Pallet_Proveedor:"+e.getMessage());
        }
    }

    private double Get_Factor_Presentacion(int vIdPresentacion){
        double Factor=0;

        try{

            if (BeProducto.Presentaciones!=null){
                Factor = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion == vIdPresentacion).select(c->c.Factor).firstOrDefault(Factor);
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }
        return Factor;

    }

    private int Get_Presentacion_A_Recibir(){

        int rPresentacion=0;

        try{

            if(gl.Carga_Producto_x_Pallet){
                rPresentacion = gBeStockRec.IdPresentacion;
            }else {
                rPresentacion = stream(gl.gpListDetalleOC.items).where(c -> c.IdProductoBodega == pIdProductoBodega && c.IdOrdenCompraDet == pIdOrdenCompraDet).select(c -> c.IdPresentacion).firstOrDefault(0);
            }
        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

        return rPresentacion;

    }

    private void Valida_Lote(){

        int dia,mes,ano;
        String lCorrelativo;

        try{

            if (BeProducto.Control_lote && BeProducto.Genera_lote){

                dia =calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH)+1;
                ano = calendario.get(Calendar.YEAR);

                lCorrelativo = (ano+mes+dia+"0001");

                txtLoteRec.setText(lCorrelativo);
            }else if(!BeProducto.Control_lote){
                txtLoteRec.setText("");
                txtLoteRec.setVisibility(View.GONE);
                lblLote.setVisibility(View.GONE);
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+""+e.getMessage());
        }

    }

    private String Get_Codigo_Barra(String valor){

        String vCodBarra="";

        try{

            if(BeProducto.Codigos_Barra!=null){

                if (BeProducto.Codigos_Barra.items!=null){

                    vCodBarra = stream(BeProducto.Codigos_Barra.items).where(c->c.Codigo_barra.equals(valor)).select(c->c.Codigo_barra).toString();

                    if(vCodBarra.isEmpty()){

                        if (BeProducto.Presentaciones!=null){

                            vCodBarra = stream(BeProducto.Presentaciones.items).where(c->c.Codigo_barra.equals(valor)).select(c->c.Codigo_barra).toString();

                            if(vCodBarra.isEmpty()){

                                if(BeProducto.Codigo_barra.equals(valor)){
                                    vCodBarra = valor;
                                }else{
                                    vCodBarra = BeProducto.Codigo_barra;
                                }

                            }

                        }

                    }

                }

            }

        }catch (Exception e){
            mu.msgbox("Get_Codigo_Barra: "+e.getMessage());
        }

        return vCodBarra;
    }

    private void Listar_Producto_Presentaciones(){

        try{

            if (BeProducto.Presentaciones!=null){

                if (BeProducto.Presentaciones.items!=null){

                    PresList.clear();

                    for (int i = 0; i <BeProducto.Presentaciones.items.size(); i++) {
                        PresList.add(BeProducto.Presentaciones.items.get(i).Nombre);
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresRec.setAdapter(dataAdapter);

                    if (PresList.size()>0) cmbPresRec.setSelection(0);

                }

            } else{
                execws(3);
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    private void Listar_Producto_Estado(){

        try{

            progress.setMessage("Listando estados de producto");

            EstadoList.clear();

            for (int i = 0; i <LProductoEstado.items.size(); i++) {
                EstadoList.add(LProductoEstado.items.get(i).Nombre);
            }


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoProductoRec.setAdapter(dataAdapter);

            if (EstadoList.size()>0) cmbEstadoProductoRec.setSelection(0);

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    //endregion

    //region GuardarRecepcion

    private void Guardar_Recepcion_Nueva(){

        try{

            if (Mostrar_Propiedades_Parametros){
                Muestra_Propiedades_Producto();
            }else{
                if (!Mostro_Propiedades){
                    Llena_Stock();
                    Mostro_Propiedades = true;
                }
            }

            if (!Mostro_Propiedades){
                Muestra_Propiedades_Producto();
                return;
            }

            if (gl.TipoOpcion==2 && gl.gBeRecepcion.IsNew){

               execws(12);

            }else{
                ContinuaGuardandoRecepcion();
            }

        }catch (Exception e){
            mu.msgbox("Guardar_Recepcion: "+ e.getMessage());
        }

    }

    public void BotonGuardarRecepcion(View view){

        try{

            if (gl.gBeRecepcion.IdTipoTransaccion.equals("PICH000")){

                if (Escaneo_Pallet){
                    //Guardar_Recepcion_Pallet
                }else{
                    ValidaCampos();
                }

            }else{
                ValidaCampos();
            }

        }catch (Exception e){
            mu.msgbox("BotonGuardarRecepcion:"+e.getMessage());
        }
    }

    private void Llena_Stock(){

        try{

            if (pListBeStockRec!=null){
                if (pListBeStockRec.items!=null){
                    List auxList= stream(pListBeStockRec.items).select(c->c.IdRecepcionDet).toList();
                    pIndiceListaStock = auxList.indexOf(pIdRecepcionDet);
                }
            }

            if (pIndiceListaStock==-1){

                clsBeStock_rec BeStock_rec = new clsBeStock_rec();
                pListBeStockRec.items = new ArrayList<clsBeStock_rec>();
                BeStock_rec.IdRecepcionDet = pIdRecepcionDet;
                BeStock_rec.IdRecepcionEnc = gl.gIdRecepcionEnc;
                BeStock_rec.IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                BeStock_rec.IdProductoBodega = BeProducto.IdProductoBodega;
                BeStock_rec.IsNew = true;

                if (IdPreseSelect>0){
                    BeStock_rec.Presentacion = new clsBeProducto_Presentacion();
                    BeStock_rec.Presentacion.IdPresentacion = IdPreseSelect;
                }

                pListBeStockRec.items.add(BeStock_rec);
                pIndiceListaStock = pListBeStockRec.items.size()-1;

            }

            if (pIndiceListaStock>=0){

                pListBeStockRec.items.get(pIndiceListaStock).IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                pListBeStockRec.items.get(pIndiceListaStock).IdProductoBodega = BeProducto.IdProductoBodega;
                if (pListBeStockRec.items.get(pIndiceListaStock).Lic_plate!=null){
                    pListBeStockRec.items.get(pIndiceListaStock).Lic_plate = pListBeStockRec.items.get(pIndiceListaStock).Lic_plate;
                }else{
                    pListBeStockRec.items.get(pIndiceListaStock).Lic_plate = "";
                }
                pListBeStockRec.items.get(pIndiceListaStock).Fecha_Ingreso = String.valueOf(du.getFechaActual());
                pListBeStockRec.items.get(pIndiceListaStock).IdRecepcionDet = pIdRecepcionDet;

                pListBeStockRec.items.get(pIndiceListaStock).Fecha_Manufactura = "1990-01-01";

                if (pListBeStockRec.items.get(pIndiceListaStock).Serial!=null){

                }else{
                    pListBeStockRec.items.get(pIndiceListaStock).Serial = "";
                }
                pListBeStockRec.items.get(pIndiceListaStock).Anada = 0;
                pListBeStockRec.items.get(pIndiceListaStock).Fec_agr = String.valueOf(du.getFechaActual());
                pListBeStockRec.items.get(pIndiceListaStock).User_agr = gl.IdOperador+"";
                pListBeStockRec.items.get(pIndiceListaStock).Fec_mod = String.valueOf(du.getFechaActual());
                pListBeStockRec.items.get(pIndiceListaStock).User_mod = gl.IdOperador+"";
                pListBeStockRec.items.get(pIndiceListaStock).IsNew = true;
                pListBeStockRec.items.get(pIndiceListaStock).Activo = true;
                pListBeStockRec.items.get(pIndiceListaStock).IdRecepcionDet = pIdRecepcionDet;
                pListBeStockRec.items.get(pIndiceListaStock).IdRecepcionEnc = gl.gIdRecepcionEnc;
                pListBeStockRec.items.get(pIndiceListaStock).IdUnidadMedida = BeProducto.IdUnidadMedidaBasica;

                if (IdPreseSelect>0){
                    pListBeStockRec.items.get(pIndiceListaStock).IdPresentacion = IdPreseSelect;
                }else{
                    pListBeStockRec.items.get(pIndiceListaStock).IdPresentacion = 0;
                }

                if (pListBeStockRec.items.get(pIndiceListaStock).Peso>0){
                    pListBeStockRec.items.get(pIndiceListaStock).Peso =pListBeStockRec.items.get(pIndiceListaStock).Peso ;
                }else{
                    pListBeStockRec.items.get(pIndiceListaStock).Peso = 0;
                }

                if (pListBeStockRec.items.get(pIndiceListaStock).Temperatura>0){
                    pListBeStockRec.items.get(pIndiceListaStock).Temperatura = pListBeStockRec.items.get(pIndiceListaStock).Temperatura;
                }else{
                    pListBeStockRec.items.get(pIndiceListaStock).Temperatura = 0.0;
                }

                pListBeStockRec.items.get(pIndiceListaStock).Regularizado = false;
                pListBeStockRec.items.get(pIndiceListaStock).Fecha_regularizacion = du.convierteFecha("01-01-1900");

                pListBeStockRec.items.get(pIndiceListaStock).Atributo_Variante_1 = "";
                pListBeStockRec.items.get(pIndiceListaStock).No_linea = pLineaOC;


            }

        }catch (Exception e){
            mu.msgbox("Llena_Stock:"+e.getMessage());
        }
    }

    private void ContinuaGuardandoRecepcion(){
        int vIndice=-1;
        String Resultado = "";
        int CantRegSeries  = 0;
        if (pListBeStockSeRec.items!=null){
            CantRegSeries = pListBeStockSeRec.items.size();
        }

        int CantRegStock=0;
        int CantRegPP=0;
        int vCont = 0;
        String msj  = "";
        clsBeTrans_re_detList auxListTransRecDet = new clsBeTrans_re_detList();

        try{

            Valida_Cantidad_Recibida();

        }catch (Exception e){
            mu.msgbox("ContinuaGuardandoRecepcion: "+e.getMessage());
        }

    }

    private void DespuesDeValidarCantidad(){
        int vIndice=-1;
        String Resultado = "";
        int CantRegSeries  = 0;
        if (pListBeStockSeRec.items!=null){
            CantRegSeries = pListBeStockSeRec.items.size();
        }

        int CantRegStock=0;
        int CantRegPP=0;
        int vCont = 0;
        String msj  = "";
        clsBeTrans_re_detList auxListTransRecDet = new clsBeTrans_re_detList();

        try{

            switch (gl.TipoOpcion){

                case 1:

                    if (gl.mode==1){
                        Llena_Detalle_Recepcion_Nueva();
                    }else{
                        List auxList =  stream(pListTransRecDet.items).select(c->c.IdRecepcionDet).toList();
                        vIndice = auxList.indexOf(pIdRecepcionDet);

                        Llena_Detalle_Recepcion_Existente(vIndice);

                    }
                    break;

                case 2:
                    //Aquí código de recepción ciega.
                    break;
            }

            int I=0;

            auxListTransRecDet = new clsBeTrans_re_detList();
            auxListTransRecDet.items = stream(pListTransRecDet.items).where(c->c.IdRecepcionDet == pIdRecepcionDet).toList();

            //Esto es el equivalente a ReDim en .net
            if (gl.gBeRecepcion.Detalle.items!=null)
            {
                //#EJC20200325: Aquí da error en algunas ocasiones

                try{

                    //System.arraycopy(gl.gBeRecepcion.Detalle.items,0,auxListTransRecDet.items,0,Math.min(gl.gBeRecepcion.Detalle.items.size(), auxListTransRecDet.items.size()));
                    //gl.gBeRecepcion.Detalle.items = auxListTransRecDet.items;

                    gl.gBeRecepcion.Detalle.items = new ArrayList<>();

                    for(clsBeTrans_re_det  det: auxListTransRecDet.items)
                    {
                        gl.gBeRecepcion.Detalle.items.add(det);
                    }

                }catch (Exception ex)
                {
                    mu.msgbox("#20200325_0338AM: When redim array -> "+ex.getMessage());
                }

            }

//            for  (clsBeTrans_re_det RD: auxListTransRecDet.items)
//            {
//                gl.gBeRecepcion.Detalle.items = new ArrayList<clsBeTrans_re_det>();
//                gl.gBeRecepcion.Detalle.items.add(I,RD);
//                I += 1;
//            }

            if (gl.gBeRecepcion.DetalleParametros.items!=null){
                System.arraycopy(gl.gBeRecepcion.DetalleParametros.items,0,plistBeReDetParametros.items,0,Math.min(gl.gBeRecepcion.DetalleParametros.items.size(), plistBeReDetParametros.items.size()));
                gl.gBeRecepcion.DetalleParametros.items = plistBeReDetParametros.items;
            }

            I = 0;

            if (plistBeReDetParametros.items!=null){
                for  (clsBeTrans_re_det_parametros RD: plistBeReDetParametros.items){
                    gl.gBeRecepcion.DetalleParametros.items.add(I,RD);
                    I += 1;
                }
            }

            if (pListBeStockRec.items.size()==0){
                mu.msgbox("No se guardó el stock, no se puede continuar");
                return;
            }

            if (pListBeStockRec.items!=null){
                CantRegStock = pListBeStockRec.items.size() - 1;
            }

            //Productos_Pallet
        if(pListBeProductoPallet.items!=null){
            CantRegPP = pListBeProductoPallet.items.size() - 1;
        }else{
            CantRegPP = 0;
        }

            if (gl.mode==1){
                execws(16);
            }else{
                execws(17);
            }

        }catch (Exception e){
            mu.msgbox("DespuesDeValidarCantidad"+e.getMessage());
        }
    }

    private void Imprime_Barra_Despues_Guardar(){

        try{

            if (gl.IdImpresora>0){

                if (BeTransReDet.Presentacion.EsPallet){

                    //ImprimirBarra

                }else{
                    if (BeTransReDet.Presentacion.Imprime_barra){

                        //ImprimirBarra

                    }
                }

            }

            switch (gl.TipoOpcion){

                case 1:
                    beTransOCDet =new clsBeTrans_oc_det();
                    beTransOCDet.IdOrdenCompraEnc = pIdOrdenCompraEnc;
                    beTransOCDet.IdOrdenCompraDet = pIdOrdenCompraDet;
                    execws(18);
                    break;

                case 2:

                    break;

            }

        }catch (Exception e){
            mu.msgbox("Imprime_Barra_Despues_Guardar: "+e.getMessage());
        }

    }

    double Factor=0;
    double TotalLinea=0;
    double vCant =0;

    private void Llena_Detalle_Recepcion_Nueva(){
        Factor=0;
        TotalLinea=0;
        vCant =0;

        try{


            if (BeProducto!=null){
                BeTransReDet = new clsBeTrans_re_det();

                BeTransReDet.IdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                BeTransReDet.Producto = new clsBeProducto();
                BeTransReDet.Producto.IdProducto = BeProducto.IdProducto;
                BeTransReDet.Producto.Codigo = BeProducto.Codigo;
                BeTransReDet.IdProductoBodega = BeProducto.IdProductoBodega;
                BeTransReDet.Nombre_producto = BeProducto.Nombre;
                BeTransReDet.IdRecepcionEnc = gl.gBeRecepcion.IdRecepcionEnc;
                BeTransReDet.IdRecepcionDet = pIdRecepcionDet;

                BeTransReDet.Presentacion = new clsBeProducto_Presentacion();

                if (IdPreseSelect>0){

                    BeTransReDet.Presentacion.IdPresentacion = IdPreseSelect;
                    BeTransReDet.Presentacion.Factor = BeProducto.Presentacion.Factor;
                    BeTransReDet.IdPresentacion = IdPreseSelect;

                    clsBeProducto_Presentacion bePresentacion = new clsBeProducto_Presentacion();

                    bePresentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).first();

                    if(!bePresentacion.EsPallet){
                        Factor = bePresentacion.Factor;
                    }else{
                        Factor = bePresentacion.Factor * bePresentacion.CamasPorTarima * bePresentacion.CajasPorCama;
                    }

                }

                BeTransReDet.No_Linea = pLineaOC;

                if (txtUmbasRec.getText().toString().isEmpty()){
                    mu.msgbox("No existe Unidad de Medida en Producto "+BeProducto.Codigo);
                    return;
                }else{
                    BeTransReDet.UnidadMedida = new clsBeUnidad_medida();
                    BeTransReDet.UnidadMedida.IdUnidadMedida = BeProducto.UnidadMedida.IdUnidadMedida;
                    BeTransReDet.IdUnidadMedida = BeProducto.UnidadMedida.IdUnidadMedida;
                }

                BeTransReDet.ProductoEstado = new clsBeProducto_estado();
                BeTransReDet.ProductoEstado.IdEstado = IdEstadoSelect;

                BeTransReDet.IdProductoEstado = IdEstadoSelect;

                BeTransReDet.IsNew = true;

                BeTransReDet.User_agr = gl.IdOperador+"";
                BeTransReDet.Fec_agr = String.valueOf(du.getFechaActual());

                BeTransReDet.MotivoDevolucion = new clsBeMotivo_devolucion();

                //VALIDACIÓN DE CANTIDADES CON REGLA SEGÚN PROPIETARIO

               if (txtCantidadRec.getText().toString().isEmpty()){
                   mu.msgbox("Ingrese la cantidad a Recibir");
                   txtCantidadRec.requestFocus();
                   return;
               }else if (Double.parseDouble(txtCantidadRec.getText().toString())==0){
                   mu.msgbox("La cantidad a Recibir debe ser mayor a 0");
                   txtCantidadRec.requestFocus();
                   return;
               }else{
                   BeTransReDet.cantidad_recibida = Double.parseDouble(txtCantidadRec.getText().toString());
               }

               if (txtPesoUnitario.getText().toString().isEmpty()){
                   BeTransReDet.peso_unitario = 0;
               }else{
                   BeTransReDet.peso_unitario = Double.parseDouble(txtPesoUnitario.getText().toString());
               }

                BeTransReDet.Nombre_producto = BeProducto.Nombre;

                if (IdPreseSelect>0){
                    BeTransReDet.Nombre_presentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.Nombre).toString();
                }else{
                    BeTransReDet.Nombre_presentacion = "";
                }
                BeTransReDet.Nombre_unidad_medida = BeProducto.UnidadMedida.Nombre;
                BeTransReDet.Codigo_Producto = BeProducto.Codigo;

                BeTransReDet.ProductoEstado = new clsBeProducto_estado();

                if (IdEstadoSelect>0) {
                    BeTransReDet.ProductoEstado.IdEstado = IdEstadoSelect;
                }else {
                    mu.msgbox("No existe Estado en Producto "+BeProducto.Codigo);
                    return;
                }

                if (BeProducto.Control_lote){

                    if (txtLoteRec.getText().toString().isEmpty()){
                         mu.msgbox("Debe ingresar el lote del producto "+BeProducto.Codigo);
                         return;
                    }else{
                        BeTransReDet.Lote = txtLoteRec.getText().toString();
                        gl.gLoteAnterior = txtLoteRec.getText().toString();
                    }

                }else{
                    if (!txtLoteRec.getText().toString().isEmpty()){
                        BeTransReDet.Lote = txtLoteRec.getText().toString();
                        gl.gLoteAnterior = txtLoteRec.getText().toString();
                    }

                }

                if (BeProducto.Control_vencimiento){

                    if (cmbVenceRec.getText().toString().isEmpty()){
                        mu.msgbox("Ingrese fecha de vencimiento para el producto "+BeProducto.Codigo);
                        return;
                    }else{
                        BeTransReDet.Fecha_vence = cmbVenceRec.getText().toString();
                        gl.gFechaVenceAnterior = cmbVenceRec.getText().toString();
                        if (!Valida_Fecha_Vencimiento()){
                            return;
                        }else{
                            Continua_Llenando_Detalle_Recepcion_Nueva();
                        }
                    }

                }else{
                    BeTransReDet.Fecha_vence = "";
                    Continua_Llenando_Detalle_Recepcion_Nueva();
                }

            }


        }catch (Exception e){
            mu.msgbox("Llena_Detalle_Recepcion_Nueva:"+e.getMessage());
        }
    }

    private void Continua_Llenando_Detalle_Recepcion_Nueva(){

        try{

            BeTransReDet.Fecha_ingreso = String.valueOf(du.getFechaActual());

            if (!txtPeso.getText().toString().isEmpty()){
                BeTransReDet.Peso = Double.parseDouble(txtPeso.getText().toString());
            }else{
                BeTransReDet.Peso= 0;
            }
            BeTransReDet.Peso_Estadistico = 0;
            BeTransReDet.Peso_Minimo = 0;
            BeTransReDet.Peso_Maximo = 0;

            BeTransReDet.Observacion = "";
            BeTransReDet.Aniada = 0;

            Valida_Costo();

            if (gl.gBeRecepcion.Muestra_precio) {
                BeTransReDet.Costo = Double.parseDouble(txtCostoReal.getText().toString());
                BeTransReDet.Costo_Oc = Double.parseDouble(txtCostoOC.getText().toString());
                BeTransReDet.Costo_Estadistico = 0;
            }else{
                BeTransReDet.Costo = 0;
                BeTransReDet.Costo_Oc =0;
                BeTransReDet.Costo_Estadistico = 0;
            }

            BeTransReDet.IdOperadorBodega = gl.IdOperador;
            BeTransReDet.Nombre_producto_estado = stream(LProductoEstado.items).where(c->c.IdEstado==IdEstadoSelect).select(c->c.Nombre).first();

            if (!txtCantidadRec.getText().toString().isEmpty()){
                vCant =Double.parseDouble(txtCantidadRec.getText().toString());
            }

            if (gl.gBeRecepcion.Muestra_precio){
                TotalLinea = Double.parseDouble(txtCostoReal.getText().toString()) * vCant;
            }else{
                TotalLinea = vCant;
            }
            listaStockPalletsNuevos = new clsBeStock_recList();
            listaProdPalletsNuevos = new clsBeProducto_palletList();

            if (pListBeStockRec.items!=null){

                listaStock = new clsBeStock_recList();
                listaProdPallets = new clsBeProducto_palletList();

                if (BeTransReDet.Presentacion.IdPresentacion!= 0){

                    listaStock.items = stream(pListBeStockRec.items).where(c->c.IdProductoBodega == BeTransReDet.IdProductoBodega  &&
                            c.IdRecepcionDet == pIdRecepcionDet && c.Presentacion.IdPresentacion == BeTransReDet.Presentacion.IdPresentacion).toList();

                    if (pListBeProductoPallet.items!=null){

                        listaProdPallets.items = stream(pListBeProductoPallet.items).where(p->p.IdRecepcionDet == BeTransReDet.IdRecepcionDet &&
                                p.IdProductoBodega == BeTransReDet.IdProductoBodega &&
                                p.IdPresentacion == BeTransReDet.Presentacion.IdPresentacion).toList();

                        for (clsBeProducto_pallet BePP : listaProdPallets.items){

                            BePP.Lote = txtLoteRec.getText().toString();
                            BePP.User_agr = gl.OperadorBodega.IdOperadorBodega+"";
                            BePP.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                            BePP.Cantidad = (1 * Factor);

                            if (BeProducto.Control_vencimiento){
                                BePP.Fecha_Vence = du.convierteFecha(cmbVenceRec.getText().toString());
                            }else {
                                BePP.Fecha_Vence = "";
                            }

                        }

                    }

                }else{

                    listaStock.items =stream(pListBeStockRec.items).where(c->c.IdProductoBodega == BeTransReDet.IdProductoBodega  &&
                            c.IdRecepcionDet == pIdRecepcionDet
                            && c.IdUnidadMedida == BeTransReDet.IdUnidadMedida).toList();



                }

                if (stream(listaStock.items).count()==0){
                    mu.msgbox("¡ERROR!, reporte al equipo de desarrollo");
                }

                for( clsBeStock_rec BeStockRec : listaStock.items){

                    BeStockRec.IsNew = true;

                    BeStockRec.ProductoEstado = new  clsBeProducto_estado();

                    if (IdEstadoSelect>0){

                        BeStockRec.ProductoEstado.IdEstado = IdEstadoSelect;
                        BeStockRec.IdProductoEstado = IdEstadoSelect;

                        if (LProductoEstado.items.size()> 0){

                            clsBeProducto_estado BeEstado = new clsBeProducto_estado();

                            BeEstado = stream(LProductoEstado.items).where(c->c.IdEstado ==IdEstadoSelect).first();

                            if (BeEstado.Danado){
                                BeStockRecNuevaRec = BeStockRec;
                                vCantNuevaRec = vCant;
                                vFactorNuevaRec = Factor;
                                execws(13); //m_proxy.Get_IdUbicMerma_By_IdBodega(gIdBodega)
                            }else{
                                BeStockRec.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;//CInt(txtIdUbicacion.Text.Trim)
                            }

                        }

                    }

                    Continua_Guardando_Rec_Nueva(BeStockRec,Factor,vCant);
                    pListTransRecDet.items.add(BeTransReDet);
                }

                if (listaStockPalletsNuevos!=null){
                    if (listaStockPalletsNuevos.items!=null){
                        for (clsBeStock_rec PN :listaStockPalletsNuevos.items){
                            pListBeStockRec.items.add(PN);
                        }
                    }
                }

                if (listaStockPalletsNuevos!=null){
                    if (listaStockPalletsNuevos.items!=null){
                        for  (clsBeProducto_pallet PP :listaProdPalletsNuevos.items){
                            pListBeProductoPallet.items.add(PP);
                        }
                    }
                }

            }

            BeTransReDet.MotivoDevolucion = new  clsBeMotivo_devolucion();

            BeTransReDet.Atributo_Variante_1 = "";


        }catch (Exception e){
            mu.msgbox("Continua:"+e.getMessage());
        }
    }

    private void Llena_Detalle_Recepcion_Existente(int vIndice){

        double Factor= 1;
        double TotalLinea;
        double vCant=0;

        try{

            if (BeProducto!=null){
                BeTransReDet = new clsBeTrans_re_det();
                BeTransReDet = pListTransRecDet.items.get(vIndice);

                BeTransReDet.Producto.IdProducto = BeProducto.IdProducto;
                BeTransReDet.Producto.Codigo = BeProducto.Codigo;
                BeTransReDet.IdProductoBodega = BeProducto.IdProductoBodega;
                BeTransReDet.Nombre_producto = BeProducto.Nombre;

                BeTransReDet.IdRecepcionEnc = gl.gBeRecepcion.IdRecepcionEnc;
                BeTransReDet.IdRecepcionDet = pIdRecepcionDet;

                BeTransReDet.Presentacion = new clsBeProducto_Presentacion();

                if (IdPreseSelect>0){

                    BeTransReDet.Presentacion.IdPresentacion = IdPreseSelect;
                    BeTransReDet.IdPresentacion = IdPreseSelect;

                    clsBeProducto_Presentacion bePresentacion = new clsBeProducto_Presentacion();

                    bePresentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion == IdPreseSelect).first();

                    if (bePresentacion.EsPallet){
                        Factor = bePresentacion.Factor * bePresentacion.CamasPorTarima * bePresentacion.CajasPorCama;
                    }else{
                        Factor = bePresentacion.Factor;
                    }


                }else{
                    Factor = 0;
                }

                BeTransReDet.No_Linea = pLineaOC;

                if (txtUmbasRec.getText().toString().isEmpty()){
                    mu.msgbox("No existe Unidad de Medida en Producto "+BeProducto.Codigo);
                    return;
                }else{
                    BeTransReDet.UnidadMedida = new  clsBeUnidad_medida();
                    BeTransReDet.UnidadMedida.IdUnidadMedida = BeProducto.UnidadMedida.IdUnidadMedida;
                    BeTransReDet.IdUnidadMedida = BeProducto.UnidadMedida.IdUnidadMedida;
                }

                BeTransReDet.ProductoEstado = new clsBeProducto_estado();
                BeTransReDet.ProductoEstado.IdEstado = IdEstadoSelect;

                BeTransReDet.IdProductoEstado = IdEstadoSelect;

                BeTransReDet.IsNew = true;

                BeTransReDet.User_agr = gl.IdOperador+"";
                BeTransReDet.Fec_agr = String.valueOf(du.getFechaActual());

                BeTransReDet.MotivoDevolucion = new clsBeMotivo_devolucion();

                if (txtCantidadRec.getText().toString().isEmpty()){
                    mu.msgbox("Ingrese la cantidad a Recibir");
                    return;
                }else if (Double.parseDouble(txtCantidadRec.getText().toString())==0){
                    mu.msgbox("La cantidad a Recibir debe ser mayor a 0");
                    return;
                }else{
                    BeTransReDet.cantidad_recibida = Double.parseDouble(txtCantidadRec.getText().toString());
                }

                BeTransReDet.peso_unitario = Double.parseDouble(txtPesoUnitario.getText().toString());

                BeTransReDet.Nombre_producto = BeProducto.Nombre;
                BeTransReDet.Nombre_presentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion == IdPreseSelect).select(c->c.Nombre).first();
                BeTransReDet.Nombre_unidad_medida = BeProducto.UnidadMedida.Nombre;
                BeTransReDet.Codigo_Producto = BeProducto.Codigo;
                BeTransReDet.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                BeTransReDet.IdUbicacionAnterior = gl.gBeRecepcion.IdUbicacionRecepcion;

                BeTransReDet.ProductoEstado = new  clsBeProducto_estado();
                if (IdEstadoSelect>0){
                    BeTransReDet.ProductoEstado.IdEstado = IdEstadoSelect;
                }else{
                    mu.msgbox("No existe Estado en Producto "+BeProducto.Codigo);
                    return;
                }

                if (BeProducto.Control_lote){
                    if (txtLoteRec.getText().toString().isEmpty()){
                        mu.msgbox("Debe ingresar el lote del producto "+BeProducto.Codigo);
                    }else{
                        BeTransReDet.Lote = txtLoteRec.getText().toString();
                    }
                }else{
                    if (!txtLoteRec.getText().toString().isEmpty()){
                        BeTransReDet.Lote = txtLoteRec.getText().toString();
                    }
                }

                if (BeProducto.Control_vencimiento){

                    if (cmbVenceRec.getText().toString().isEmpty()){
                        mu.msgbox("Ingrese fecha de vencimiento para el producto "+BeProducto.Codigo);
                        return;
                    }else{
                        BeTransReDet.Fecha_vence = du.convierteFecha(cmbVenceRec.getText().toString());
                        if (!Valida_Fecha_Vencimiento()){
                            mu.msgbox("Se debe corregir la fecha de vencimiento del producto: "+BeProducto.Codigo);
                            return;
                        }

                    }

                }else{
                    BeTransReDet.Fecha_vence ="";
                }

                BeTransReDet.Fecha_ingreso = String.valueOf(du.getFechaActual());

                if (!txtPeso.getText().toString().isEmpty()){
                    BeTransReDet.Peso = Double.parseDouble(txtPeso.getText().toString());
                }else{
                    BeTransReDet.Peso= 0;
                }
                BeTransReDet.Peso_Estadistico = 0;
                BeTransReDet.Peso_Minimo = 0;
                BeTransReDet.Peso_Maximo = 0;

                BeTransReDet.Observacion = "";
                BeTransReDet.Aniada = 0;

                Valida_Costo();

                BeTransReDet.Costo = Double.parseDouble(txtCostoReal.getText().toString());
                BeTransReDet.Costo_Oc = Double.parseDouble(txtCostoOC.getText().toString());
                BeTransReDet.Costo_Estadistico = 0;

                BeTransReDet.IdOperadorBodega = gl.IdOperador;
                BeTransReDet.Nombre_producto_estado = stream(LProductoEstado.items).where(c->c.IdEstado==IdEstadoSelect).select(c->c.Nombre).first();

                if (txtCantidadRec.getText().toString().isEmpty()){
                    vCant =Double.parseDouble(txtCantidadRec.getText().toString());
                }

                TotalLinea = Double.parseDouble(txtCostoReal.getText().toString()) * vCant;

                listaStockPalletsNuevos = new clsBeStock_recList();
                listaProdPalletsNuevos = new clsBeProducto_palletList();

                if (pListBeStockRec.items!=null){

                    listaStock = new clsBeStock_recList();
                    listaProdPallets = new clsBeProducto_palletList();

                    if (BeTransReDet.Presentacion.IdPresentacion > 0){

                        listaStock.items = stream(pListBeStockRec.items).where(c->c.IdProductoBodega == BeTransReDet.IdProductoBodega
                                && c.IdRecepcionDet == BeTransReDet.IdRecepcionDet
                                && c.Presentacion.IdPresentacion == BeTransReDet.Presentacion.IdPresentacion).toList();

                        if (pListBeProductoPallet.items!=null){

                            listaProdPallets.items = stream(pListBeProductoPallet.items).where(p->p.IdRecepcionDet == BeTransReDet.IdRecepcionDet &&
                                    p.IdProductoBodega == BeTransReDet.IdProductoBodega &&
                                    p.IdPresentacion == BeTransReDet.Presentacion.IdPresentacion).toList();

                            for (clsBeProducto_pallet BePP : listaProdPallets.items){

                                BePP.Lote = txtLoteRec.getText().toString();
                                BePP.User_agr = gl.OperadorBodega.IdOperadorBodega+"";
                                BePP.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                                BePP.Cantidad = (1 * Factor);

                                if (BeProducto.Control_vencimiento){
                                    BePP.Fecha_Vence = du.convierteFecha(cmbVenceRec.getText().toString());
                                }else {
                                    BePP.Fecha_Vence = "";
                                }

                            }

                        }

                    }else{

                        listaStock.items =stream(pListBeStockRec.items).where(c->c.IdProductoBodega == BeTransReDet.IdProductoBodega  &&
                                c.IdRecepcionDet == pIdRecepcionDet
                                && c.IdUnidadMedida == BeTransReDet.IdUnidadMedida).toList();

                    }

                    if (stream(listaStock.items).count()==0){
                        mu.msgbox("¡ERROR!, reporte al equipo de desarrollo");
                    }

                    for( clsBeStock_rec BeStockRec : listaStock.items){

                        BeStockRec.IsNew = true;

                        BeStockRec.ProductoEstado = new  clsBeProducto_estado();

                        if (IdEstadoSelect>0){

                            BeStockRec.ProductoEstado.IdEstado = IdEstadoSelect;
                            BeStockRec.IdProductoEstado = IdEstadoSelect;

                            if (LProductoEstado.items.size()> 0){

                                clsBeProducto_estado BeEstado = new clsBeProducto_estado();

                                BeEstado = stream(LProductoEstado.items).where(c->c.IdEstado ==IdEstadoSelect).first();

                                if (BeEstado.Danado){
                                    BeStockRecNuevaRec = BeStockRec;
                                    vCantNuevaRec = vCant;
                                    vFactorNuevaRec = Factor;
                                    execws(13); //m_proxy.Get_IdUbicMerma_By_IdBodega(gIdBodega)
                                }else{
                                    BeStockRec.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;//CInt(txtIdUbicacion.Text.Trim)
                                }

                            }

                        }

                        Continua_Guardando_Rec_Nueva(BeStockRec,Factor,vCant);
                    }

                    for (clsBeStock_rec PN :listaStockPalletsNuevos.items){
                        pListBeStockRec.items.add(PN);
                    }

                    for  (clsBeProducto_pallet PP :listaProdPalletsNuevos.items){
                        pListBeProductoPallet.items.add(PP);
                    }

                }

                BeTransReDet.MotivoDevolucion = new  clsBeMotivo_devolucion();

                BeTransReDet.Atributo_Variante_1 = "";

            }


        }catch (Exception e){
            mu.msgbox("Llena_Detalle_Recepcion_Existente:"+e.getMessage());
        }
    }

    private void Continua_Guardando_Rec_Nueva(clsBeStock_rec BeStockRec,double Factor,double vCant){

        try{


            BeStockRec.Presentacion = new clsBeProducto_Presentacion();

            if (IdPreseSelect>0){

                for ( clsBeProducto_Presentacion pres:BeProducto.Presentaciones.items){
                    if (pres.IdPresentacion==IdPreseSelect){
                        BeStockRec.Presentacion = pres;
                    }
                }

                BeStockRec.IdPresentacion = IdPreseSelect;

            }

            if (!txtUmbasRec.getText().toString().isEmpty()){
                BeStockRec.IdUnidadMedida = BeProducto.UnidadMedida.IdUnidadMedida;
            }

            BeStockRec.IdRecepcionEnc = gl.gBeRecepcion.IdRecepcionEnc;

            if (!txtLoteRec.getText().toString().isEmpty()){
                BeStockRec.Lote = txtLoteRec.getText().toString();
            }

            if (IdPreseSelect>0){

                if (!(BeStockRec.Presentacion.EsPallet|chkPaletizar.isChecked()) && BeStockRec.Lic_plate.isEmpty()){
                    Factor = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion == IdPreseSelect).select(c->c.Factor).first();
                    BeStockRec.Cantidad += vCant * Factor;
                }else if(chkPaletizar.isChecked()){
                    BeStockRec.Cantidad += vCant * Factor;
                }else if (BeStockRec.Presentacion.EsPallet&&BeStockRec.Presentacion.Genera_lp_auto){
                    BeStockRec.Cantidad = 1 * Factor;
                }else{
                    BeStockRec.Cantidad = BeStockRec.Cantidad;
                }

            }else{
                BeStockRec.Cantidad = vCant;
            }

            if (BeProducto.Control_vencimiento){
                BeStockRec.Fecha_Vence = du.convierteFecha(cmbVenceRec.getText().toString());
            }else{
                BeStockRec.Fecha_Vence = "";
            }

            BeStockRec.ProductoValidado = true;
            BeStockRec.No_linea = pLineaOC;

            if (Escaneo_Pallet){
                BeStockRec.Lic_plate = BeINavBarraPallet.Codigo_barra;
                BeStockRec.Uds_lic_plate = BeINavBarraPallet.Cantidad_UMP;
                BeStockRec.No_bulto = 0;
                BeTransReDet.Lic_plate = BeINavBarraPallet.Codigo_barra;
            }else{
                if (BeTransReDet.Lic_plate!=null){
                    if (BeStockRec!=null){
                        BeTransReDet.Lic_plate = BeStockRec.Lic_plate;
                    }else{
                        BeTransReDet.Lic_plate = "";
                    }
                }else{
                    BeTransReDet.Lic_plate = "";
                }
            }

            if (BeStockRec.Presentacion.IdPresentacion > 0){

                if (BeStockRec.Presentacion.EsPallet){

                    if (Double.parseDouble(txtCantidadRec.getText().toString().replace(",",""))==1){
                        BeStockRec.Cantidad = Double.parseDouble(txtCantidadRec.getText().toString().replace(",",""))*Factor;
                    }else{

                        if (BeStockRec.Presentacion.Genera_lp_auto){

                            int vCantidadPallets =Integer.parseInt(txtCantidadRec.getText().toString().replace(",",""));
                            int vIndiceLista=0;
                           vBeStockRec = new clsBeStock_rec();
                           BeProdPallet  = new clsBeProducto_pallet();

                            for (int x = 0; x <vCantidadPallets; x++) {

                                vIndiceLista = listaStockPalletsNuevos.items.size()-1;

                                if (x==1){
                                    vBeStockRec = BeStockRec;
                                    vBeStockRec.Cantidad = (1 * Factor);
                                }else{
                                    vBeStockRec = new  clsBeStock_rec();
                                    vBeStockRec = listaStockPalletsNuevos.items.get(vIndiceLista);
                                }

                                vBeStockRec.Cantidad = (1 * Factor);

                                if (!vBeStockRec.Lic_plate.isEmpty()){
                                    Pallet_Correcto();
                                }else{
                                    execws(15);
                                }

                            }

                        }else{
                            mu.msgbox("La cantidad de pallets es > 1 y genera_lp_auto es Falso, debe recibir los pallets de forma unitaria (Cantidad = 1)");
                            return;
                        }

                    }

                }else{
                    BeStockRec.Cantidad = Double.parseDouble(txtCantidadRec.getText().toString().replace(",",""))*Factor;

                    if (BeStockRec.Presentacion.Imprime_barra){

                        BeProdPallet = new clsBeProducto_pallet();

                        BeProdPallet.IdPropietarioBodega = BeTransReDet.IdPropietarioBodega;
                        BeProdPallet.IdProductoBodega = BeTransReDet.IdProductoBodega;
                        BeProdPallet.IdPresentacion = BeStockRec.Presentacion.IdPresentacion;
                        BeProdPallet.IdRecepcionDet = BeTransReDet.IdRecepcionDet;
                        BeProdPallet.IdOperadorBodega = gl.OperadorBodega.IdOperadorBodega;
                        BeProdPallet.Impreso = false;
                        BeProdPallet.IdImpresora = 1;
                        BeProdPallet.Activo = true;
                        BeProdPallet.Fecha_ingreso = String.valueOf(du.getFechaActual());
                        BeProdPallet.Codigo_Barra = BeStockRec.Presentacion.Codigo_barra;
                        BeProdPallet.Reimpresiones = 0;
                        BeProdPallet.Cantidad = BeStockRec.Cantidad;
                        BeProdPallet.Fecha_Vence = BeStockRec.Fecha_Vence;
                        BeProdPallet.Fec_agr =String.valueOf(du.getFechaActual());
                        BeProdPallet.Fec_mod = String.valueOf(du.getFechaActual());
                        BeProdPallet.Lote = BeStockRec.Lote;
                        BeProdPallet.User_agr = gl.OperadorBodega.IdOperadorBodega+"";
                        BeProdPallet.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                        BeProdPallet.IsNew = true;

                        if (listaProdPalletsNuevos.items!=null){
                            listaProdPalletsNuevos.items.add(BeProdPallet);
                        }else{
                            listaProdPalletsNuevos.items = new ArrayList<clsBeProducto_pallet>();
                            listaProdPalletsNuevos.items.add(BeProdPallet);
                        }

                    }

                }

            }else{
                BeStockRec.Cantidad = Double.parseDouble(txtCantidadRec.getText().toString().replace(",",""));
            }

        }catch (Exception e){
            mu.msgbox("Continua_Guardando_Rec_Nueva: "+e.getMessage());
        }
    }

    private void Terminar_Guardar_Detalle_Recepcion_Nueva(){

        try{

            listaStockPalletsNuevos.items.add(vBeStockRec);

            if (BeStockRecNuevaRec.Presentacion.Imprime_barra){

                BeProdPallet = new  clsBeProducto_pallet();

                BeProdPallet.IdPropietarioBodega = BeTransReDet.IdPropietarioBodega;
                BeProdPallet.IdProductoBodega = BeTransReDet.IdProductoBodega;
                BeProdPallet.IdOperadorBodega = gl.OperadorBodega.IdOperadorBodega;
                BeProdPallet.IdPresentacion = vBeStockRec.Presentacion.IdPresentacion;
                BeProdPallet.IdRecepcionDet = vBeStockRec.IdRecepcionDet;
                BeProdPallet.Impreso = false;
                BeProdPallet.IdImpresora = 1;
                BeProdPallet.Activo = true;
                BeProdPallet.Fecha_ingreso = String.valueOf(du.getFechaActual());
                BeProdPallet.Codigo_Barra = vBeStockRec.Lic_plate;
                BeProdPallet.Reimpresiones = 0;
                BeProdPallet.Cantidad = vBeStockRec.Cantidad;
                if (BeProducto.Control_vencimiento){
                    BeProdPallet.Fecha_Vence = vBeStockRec.Fecha_Vence;
                }else{
                    BeProdPallet.Fecha_Vence = "";
                }
                BeProdPallet.Fec_agr = String.valueOf(du.getFechaActual());
                BeProdPallet.Fec_mod = String.valueOf(du.getFechaActual());
                BeProdPallet.Lote = vBeStockRec.Lote;
                BeProdPallet.User_agr = gl.OperadorBodega.IdOperadorBodega+"";
                BeProdPallet.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                BeProdPallet.IsNew = true;

                if (listaProdPalletsNuevos.items!=null){
                    listaProdPalletsNuevos.items.add(BeProdPallet);
                }else{
                    listaProdPalletsNuevos.items = new ArrayList<clsBeProducto_pallet>();
                    listaProdPalletsNuevos.items.add(BeProdPallet);
                }

            }

        }catch (Exception e){
            mu.msgbox("Terminar_Guardar_Detalle_Recepcion_Nueva: "+e.getMessage());
        }
    }

    private boolean Pallet_Correcto(){

        boolean PCorrecto=false;

        try{

            execws(14);

        }catch (Exception e){
            mu.msgbox("Pallet_Correcto:"+e.getMessage());
        }

        return  PCorrecto;
    }

    private void Valida_Costo(){

        try{

            if (gl.gBeRecepcion.Muestra_precio){
                if (txtCostoReal.getText().toString().isEmpty()){

                    if (IdPreseSelect>0){
                        txtCostoReal.setText(Get_Costo_Presentacion()+"");
                    }

                    if (Double.parseDouble(txtCostoReal.getText().toString())==0){
                        txtCostoReal.setText(txtCostoOC.getText().toString());
                    }

                }else if(Double.parseDouble(txtCostoReal.getText().toString())<=0){
                    mu.msgbox("El costo debe ser mayor a 0");
                    return;
                }
            }

        }catch (Exception e){
            mu.msgbox("Valida_Costo"+e.getMessage());
        }
    }

    private double Get_Costo_Presentacion(){

        double cost=0;

        try{

            if (BeProducto.Presentaciones!=null){

                if (BeProducto.Presentaciones.items!=null){
                    cost = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion == IdPreseSelect).select(c->c.Costo).first();
                }
            }

        }catch (Exception e){
            mu.msgbox("GetCostoPresentacion"+e.getMessage());
        }

        return cost;
    }

    private boolean Valida_Fecha_Vencimiento(){

        boolean Valida_Fecha=false;

        try{

            String FechaVence=du.convierteFecha(cmbVenceRec.getText().toString());

            if (FechaVence.equals(String.valueOf(du.getFechaActual()))){
               msgValidaFechaVence("La fecha de vencimiento del producto "+BeProducto.Codigo+ " es igual o menor a la fecha de hoy. ¿Desea ingresar un producto ya vencido?");
               }else{
                   Valida_Fecha=  true;
               }

        }catch (Exception e){
            mu.msgbox("ValidaFechaVencimiento: "+e.getMessage());
        }

        return Valida_Fecha;

    }
    private boolean Valida_Cantidad_Recibida(){
        boolean valida=false;
        double Cantidad=0;

        try{

            if (gl.TipoOpcion==2){
                return true;
            }

            if (txtCantidadRec.getText().toString().isEmpty()){
                Cantidad =0;
            }else{
                Cantidad = Double.parseDouble(txtCantidadRec.getText().toString());
            }

            if (gl.mode==2){
                Cant_Pendiente = Cant_Pendiente + Cant_Recibida_Anterior;
            }

            if (Cant_Pendiente > Cantidad){
                msgValidaCantidad("La cantidad "+Cantidad+" ingresada es correcta para el producto "+BeProducto.Codigo);
                    return  true;
            }else if(Cant_Pendiente < Cantidad){
                msgExcedeCantidad("Excede la cantidad solicitada. ¿Recibir de todas formas esta cantidad?");
                    return true;
            }else{
                DespuesDeValidarCantidad();
            }


        }catch (Exception e){
            mu.msgbox("ValidaCantidadRecibida"+e.getMessage());
        }

        return valida;

    }

    private void msgValidaProductoPallet(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Guardar_Pallet();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgValidaFechaVence(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_Llenando_Detalle_Recepcion_Nueva();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgExcedeCantidad(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    DespuesDeValidarCantidad();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgValidaCantidad(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg );

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    DespuesDeValidarCantidad();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    //endregion

    //region ScaneoBarra

    //endregion

    //region WebService

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){

            try{

                switch (ws.callback) {

                    case 1:
                        callMethod("Get_Producto_By_IdProductoBodega","IdProductoBodega",BeOcDet.IdProductoBodega);
                        break;
                    case  2:
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodega","pIdPropietario",gl.IdPropietario,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",BeProducto.IdProducto,"pActivo",true);
                        break;
                    case 4:
                        callMethod("Get_All_Codigos_Barra_By_IdProducto","pIdProducto",BeProducto.IdProducto);
                        break;
                    case 5:
                        callMethod("Get_All_ProductoParametros_By_IdProducto_HH","pIdProducto",BeProducto.IdProducto,
                                "pActivo",true);
                        break;
                    case 6:
                        callMethod("Get_Nuevo_Correlativo_LicensePlate","pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,"pIdPropietario",BeProducto.Propietario.IdPropietario,
                                "pIdProducto",BeProducto.IdProducto);
                        break;
                    case 7:
                        callMethod("Get_Licenses_Plates_By_IdRecepcionEnc","pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 8:
                        callMethod("Existe_LP_By_IdRecepcionEnc_And_IdRecepcionDet","IdRecepcionEnc",gl.gIdRecepcionEnc,
                                "LicPlate",txtLicPlate.getText().toString(),"IdRecepcionDet",pIdRecepcionDet);
                        break;
                    case 9:
                        callMethod("MaxIDStockSeRec");
                        break;
                    case 10:
                        callMethod("Get_All_Params_By_IdRecepcionEnc_And_IdRecepcion_Det_For_HH",
                                "pIdRecepcionEnc",gl.gIdRecepcionEnc,"pIdRecepcionDet",pIdRecepcionDet);
                        break;
                    case 11:
                        callMethod("MaxIDStockRec");
                        break;
                    case 12:
                        callMethod("Get_BeTransReEnc_By_IdREcepcionEnc_For_HH","pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 13:
                        callMethod("Get_IdUbicMerma_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 14:
                        callMethod("Existe_LP_By_IdRecepcionEnc_And_IdRecepcionDet","IdRecepcionEnc",gl.gIdRecepcionEnc,
                                "LicPlate",vBeStockRec.Lic_plate,"IdRecepcionDet",pIdRecepcionDet);
                        break;
                    case 15:
                        callMethod("Get_Nuevo_Correlativo_LicensePlate_S","pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,"pIdPropietario",BeProducto.Propietario.IdPropietario,
                                "pIdProducto",BeProducto.IdProducto,"UltimoPalletGenerado",vBeStockRec.Lic_plate);
                        break;
                    case 16:
                        //Guardar_Recepcion_Nueva
                        callMethod("Guardar_Recepcion","pRecEnc",gl.gBeRecepcion,
                                "pRecOrdenCompra",gl.gBeRecepcion.OrdenCompraRec,
                                "pListStockRecSer",pListBeStockSeRec.items,"pListStockRec",pListBeStockRec.items,
                                "pListProductoPallet",listaProdPalletsNuevos.items,"pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,"pIdUsuario",gl.IdOperador);
                        break;
                    case 17 :
                        //Guardar_Recepcion_Edita
                        callMethod("GuardarRecepcionModif","pRecEnc",gl.gBeRecepcion,
                                "pRecOrdenCompra",gl.gBeRecepcion.OrdenCompraRec,
                                "pListStockRecSer",pListBeStockSeRec.items,"pListStockRec",pListBeStockRec.items,
                                "pListProductoPallet",listaProdPalletsNuevos.items,"pbeStockAnt",gBeStockAnt,
                                "pIdEmpresa",gl.IdEmpresa,"pIdBodega",gl.IdBodega,"pIdUsuario",gl.IdOperador);
                        break;
                    case 18:
                        callMethod("Get_Detalle_OC_By_IdOrdeCompraDet","oBeTrans_oc_det",beTransOCDet);
                        break;
                    case 19:
                        callMethod("Max_IdRecepcion_Det_By_IdRecepcionEnc","pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 20:
                        callMethod("Get_IdUbicMerma_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 21:
                        callMethod("Finalizar_Recepcion_Parcial_Pallet_Proveedor","pRecEnc",gl.gBeRecepcion,
                                "pIdOrdenCompraEnc",pIdOrdenCompraEnc,
                                "pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "pIdEmpresa", gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.IdOperador,
                                "pBeStockRec",vBeStockRecPallet,
                                "pBeRecDet",BeTransReDet,
                                "pBeBarraPallet",BeINavBarraPallet,
                                "pEsTransferencia",EsTransferenciaInternaWMS);
                        break;
                    case 22:
                        callMethod("Get_All_BeTrasReDet_By_IdOrdenCompraEnc","pIdOrdenCompraEnc",pIdOrdenCompraEnc);
                        break;
                }

            }catch (Exception e){
                mu.msgbox(e.getClass()+e.getMessage());
            }

        }

    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {

        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processBeProducto();
                    break;
                case 2:
                    processListarProductoEstado();
                    break;
                case 3:
                    processListPresentaciones();
                    break;
                case 4:
                    processCodigosBarra();
                    break;
                case 5:
                    processTieneParametrosPersonalizados();
                    break;
                case 6:
                    processNuevoLP();
                    break;
                case 7:
                    processLicensePallet();
                    break;
                case 8:
                    processValidaLicPlate();
                    break;
                case 9:
                    processMaxIdStockSeRec();
                    break;
                case 10:
                    processListDetParametros();
                    break;
                case 11:
                    processMaxIdStockRec();
                    break;
                case 12:
                    processAuxRec();
                    break;
                case 13:
                    processUbicacionMerma();
                    break;
                case 14:
                    processvBeStockRecLicPlate();
                    break;
                case 15:
                    processNuevoLPS();
                    break;
                case 16:
                    processGuardarRecNueva();
                    break;
                case 17:
                    processGuardarRecModif();
                    break;
                case 18:
                    processActualizaCantidadRecibida();
                    break;
                case 19:
                    processMaxIdRecepcionDet();
                    break;
                case 20:
                    processGetIdUbicacionMerma();
                    break;
                case 21:
                    beTransOCDet =new clsBeTrans_oc_det();
                    beTransOCDet.IdOrdenCompraEnc = pIdOrdenCompraEnc;
                    beTransOCDet.IdOrdenCompraDet = pIdOrdenCompraDet;
                    execws(18);
                    break;
                case 22:
                    processCantRecConOC();
                    break;


            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processBeProducto(){

        try {

            progress.setMessage("Obteniendo valores de producto");

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            Load();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processListarProductoEstado(){

        try{

            progress.setMessage("Obteniendo estados de producto");

            LProductoEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodega");

            Listar_Producto_Estado();

            execws(4);

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    private void processListPresentaciones(){

        try{

            BeProducto.Presentaciones = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            Listar_Producto_Presentaciones();

            execws(4);

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    private void processCodigosBarra(){

        try{

            BeProducto.Codigos_Barra = xobj.getresult(clsBeProducto_codigos_barraList.class,"Get_All_Codigos_Barra_By_IdProducto");

            Listar_Producto_Presentaciones();

            LlenaDatosFaltantes();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    private void processTieneParametrosPersonalizados(){

        try{

            Pperzonalizados=false;
            PCap_Manu=false;
            PGenera_lp=false;
            PTiene_Ctrl_Temp=false;
            PTiene_Pres=false;

            pListBEProductoParametro = xobj.getresult(clsBeProducto_parametrosList.class,"Get_All_ProductoParametros_By_IdProducto_HH");

            if (pListBEProductoParametro!=null){
                if (pListBEProductoParametro.items!=null){
                    Pperzonalizados = true;
                }
            }

            if (BeProducto.Fechamanufactura&&BeProducto.Materia_prima){
                PCap_Manu=true;
            }

            if (BeProducto.Capturar_aniada){
                PCap_Anada = true;
            }

            if (BeProducto.Genera_lp){
                PGenera_lp=true;
            }

            if (BeProducto.Peso_recepcion){
                PTiene_Ctrl_Peso = true;
            }

            if (BeProducto.Temperatura_recepcion){
                PTiene_Ctrl_Temp = true;
            }

            if (BeProducto.Serializado){
                PTiene_PorSeries=true;
            }

//            if (BeProducto.Presentaciones!=null){
//                if (BeProducto.Presentaciones.items!=null){
//                    PTiene_Pres=true;
//                }
//            }
//PTiene_Pres
            if(Pperzonalizados|PCap_Manu|PCap_Anada|PGenera_lp|PTiene_Ctrl_Peso|PTiene_Ctrl_Temp|PTiene_PorSeries){
                Muestra_Propiedades_Producto();
            }else{
                Guardar_Recepcion_Nueva();
            }

        }catch (Exception e){
            mu.msgbox("ProcessTieneParametrosPersonalizados: "+e.getMessage());
        }

    }

    private void processNuevoLP(){

        try {

            pNumeroLP = xobj.getresult(String.class,"Get_Nuevo_Correlativo_LicensePlate");

            txtLicPlate.setText(pNumeroLP);

        }catch (Exception e){
            mu.msgbox("processNuevoLP: "+e.getMessage());
        }

    }

    private void processLicensePallet(){

        try {

            pListBeLicensePlate = xobj.getresult(clsBeLicensePlatesList.class,"Get_Licenses_Plates_By_IdRecepcionEnc");

        }catch (Exception e){
            mu.msgbox("processLicensePallet: "+e.getMessage());
        }
    }

    private void processValidaLicPlate(){

        try{

            PallCorrecto  = xobj.getresult(Boolean.class,"Existe_LP_By_IdRecepcionEnc_And_IdRecepcionDet");

            if (!PallCorrecto){
                mu.msgbox("El License Plate ya existe, debe ingresar otro valor");
            }else{
                ContinuaValidandoParametros();
            }

        }catch (Exception e){
            mu.msgbox("processValidaLicPlate: "+e.getMessage());
        }

    }

    private int processMaxIdStockSeRec(){

        int MaId=0;

        try{

            MaId = xobj.getresult(Integer.class,"MaxIDStockSeRec");

            ObjNS.IdStockSeRec = MaId + 1;
            ValidaParametrosDespuesSeRec();

        }catch (Exception e){
            mu.msgbox("processMaxIdStockSeRec: "+e.getMessage());
        }

        return MaId;
    }

    private void processListDetParametros(){

        try{

            plistBeReDetParametros = xobj.getresult(clsBeTrans_re_det_parametrosList.class,"Get_All_Params_By_IdRecepcionEnc_And_IdRecepcion_Det_For_HH");

        }catch (Exception e){

        }

    }

    private int processMaxIdStockRec(){

        int IdMax=0;
        try {

            IdMax = xobj.getresult(Integer.class,"MaxIDStockRec");

            ObjS.IdStockRec = IdMax + 1;

            SigueValidandoParametros();

        }catch (Exception e){
            mu.msgbox("processMaxIdStockRec: "+e.getMessage());
        }

        return IdMax;

    }

    private void processAuxRec(){

        try{

            auxRec = xobj.getresult(clsBeTrans_re_enc.class,"Get_BeTransReEnc_By_IdREcepcionEnc_For_HH");

            if (auxRec!=null){
                gl.gBeRecepcion.IsNew = auxRec.IsNew;
            }

            ContinuaGuardandoRecepcion();

        }catch (Exception e){

        }
    }

    private void processUbicacionMerma(){

        try{

            BeStockRecNuevaRec.IdUbicacion = xobj.getresult(Integer.class,"Get_IdUbicMerma_By_IdBodega");

            Continua_Guardando_Rec_Nueva(BeStockRecNuevaRec,vFactorNuevaRec,vCantNuevaRec);

        }catch (Exception e){
            mu.msgbox("processUbicacionMerma:"+e.getMessage());
        }

    }

    private void processvBeStockRecLicPlate(){

        try{

            boolean EsCorrecto=false;

            EsCorrecto  = xobj.getresult(Boolean.class,"Existe_LP_By_IdRecepcionEnc_And_IdRecepcionDet");

            if (!EsCorrecto){
                mu.msgbox("El License Plate ya existe, debe ingresar otro valor");
                execws(15);
            }

        }catch (Exception e){
            mu.msgbox("processvBeStockRecLicPlate: "+e.getMessage());
        }

    }

    private void processNuevoLPS(){

        try {

            vBeStockRec.Lic_plate = xobj.getresult(String.class,"Get_Nuevo_Correlativo_LicensePlate_S");

            Terminar_Guardar_Detalle_Recepcion_Nueva();

        }catch (Exception e){
            mu.msgbox("processNuevoLPS: "+e.getMessage());
        }

    }

    private void processGuardarRecNueva(){

        try{

            String Resultado="";

            Resultado = xobj.getresult(String.class,"Guardar_Recepcion");

            if (!Resultado.isEmpty()){
                Imprime_Barra_Despues_Guardar();
            }else{
                mu.msgbox("No se pudo guardar la recepción");
                return;
            }

        }catch (Exception e){
        mu.msgbox("processGuardarRecNueva:"+e.getMessage());
        }
    }

    private void processGuardarRecModif(){

        try{

            String Resultado="";

            Resultado = xobj.getresult(String.class,"GuardarRecepcionModif");

            if (!Resultado.isEmpty()){
                Imprime_Barra_Despues_Guardar();
            }else{
                mu.msgbox("No se pudo guardar la recepción");
                return;
            }

        }catch (Exception e){
            mu.msgbox("processGuardarRecModif:"+e.getMessage());
        }

    }

    private void processActualizaCantidadRecibida(){

        try{

            boolean GetDetalle=false;
            int vIndex=-1;

            GetDetalle = xobj.getresult(Boolean.class,"Get_Detalle_OC_By_IdOrdeCompraDet");

            List AuxList  = stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet == beTransOCDet.IdOrdenCompraDet && c.IdPresentacion == beTransOCDet.IdPresentacion).select(c->c.IdOrdenCompraDet).toList();

            vIndex = AuxList.indexOf(beTransOCDet.IdOrdenCompraDet);

            gl.gpListDetalleOC.items.get(vIndex).Cantidad_recibida = beTransOCDet.Cantidad_recibida;

            gl.CantOC = beTransOCDet.Cantidad;
            gl.CantRec = beTransOCDet.Cantidad_recibida;

            super.finish();

            gl.Carga_Producto_x_Pallet=false;

        }catch (Exception e){
            mu.msgbox("processActualizaCantidadRecibida"+e.getMessage());
        }
    }

    private  void processMaxIdRecepcionDet(){

        try{

            pIdRecepcionDet = xobj.getresult(Integer.class,"Max_IdRecepcion_Det_By_IdRecepcionEnc")+1;

            execws(2);

        }catch (Exception e){
            mu.msgbox("processMaxIdRecepcionDet"+e.getMessage());
        }
    }

    public void ExitForm(View view){
        msgAskExit("Está seguro de salir");
    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    doExit();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void doExit(){
        try{

            //LimpiaValores();
            super.finish();
            gl.Carga_Producto_x_Pallet=false;
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void processGetIdUbicacionMerma(){

        try{

            vBeStockRecPallet.IdUbicacion = xobj.getresult(Integer.class,"Get_IdUbicMerma_By_IdBodega");

            Continua_Llenando_Stock_Pallet_Proveedor();

        }catch (Exception e){
            mu.msgbox("processGetIdUbicacionMerma"+e.getMessage());
        }
    }

    private void processCantRecConOC(){

        try{

            LRecepcionCantidad = xobj.getresult(clsBeTrans_re_detList.class,"Get_All_BeTrasReDet_By_IdOrdenCompraEnc");

            if (LRecepcionCantidad!=null){
                if (LRecepcionCantidad.items!=null){
                    Cant_Recibida = stream(LRecepcionCantidad.items).where(c->c.IdProductoBodega == pIdProductoBodega &&
                            c.IdPresentacion == vPresentacion &&
                            c.No_Linea == pLineaOC).select(c->c.cantidad_recibida).first();
                }
            }

            Cant_A_Recibir = gl.gselitem.Cantidad;

            if(Cant_Recibida - Cant_Recibida<0){
                Cant_Pendiente = 0;
            }else{
                Cant_Pendiente =  Cant_A_Recibir - Cant_Recibida;
            }

            FinalizCargaProductos();

        }catch (Exception e){
            mu.msgbox("processCantRecConOC"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }


    @Override
    public void onBackPressed() {
        try{
            msgAskExit("Está seguro de salir");
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }
    //endregion
}