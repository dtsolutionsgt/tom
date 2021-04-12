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
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.DecimalDigitsInputFilter;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_pallet;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaBase;
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
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote.clsBeTrans_oc_det_loteList;
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
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Recepcion.frm_list_rec_prod.EsTransferenciaInternaWMS;
import static com.dts.tom.Transacciones.Recepcion.frm_list_rec_prod.gBeStockRec;

public class frm_recepcion_datos extends PBase {

    Calendar calendario = Calendar.getInstance();

    private Spinner cmbEstadoProductoRec,cmbPresRec, cmbVence, cmbLote;
    private EditText txtBarra,txtLoteRec,txtUmbasRec,txtCantidadRec,txtPeso,txtPesoUnitario,txtCostoReal,txtCostoOC,cmbVenceRec;
    private TextView lblDatosProd,lblPropPrd,lblPeso,lblPUn,lblCosto,lblCReal,lblPres,lblLote,lblVence, lblEstiba, lblUbicacion;
    private Button btnCantPendiente,btnCantRecibida,btnBack,btnIr;
    private ProgressDialog progress;
    private DatePicker dpResult;
    private ImageView imgDate, cmdImprimir;
    private CheckBox chkPaletizar, chkPalletNoEstandar, chkEstiba;
    private TableRow tblEstiba, tbLPeso, tblPres,tblLote, tblVence, tblUbicacion;
    private Dialog dialog;

    private boolean imprimirDesdeBoton=false;

    //Objeto para dialogo de parametros
    private TextView lblSerieTit,lblSerialP,lblPesoTit,lblTempTit,lblPrducto,lblLicPlate,lblFManufact,lblAnada,lblTempEsta,lblTempReal,lblPresParam,lblPesoEsta,lblPesoReal,lblSerialIni,lblSerialFin;
    private EditText txtLicPlate,txtSerial,txtAnada,txtFechaManu,txtFechaIng,txtTempEsta,txtTempReal,txtPesoEsta,txtPesoReal,txtSerieIni,txtSerieFin;
    private Spinner cmbPresParams;
    int pIndexStock=-1;
    double Cant_Recibida_Actual=0;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private boolean Mostro_Propiedades,Escaneo_Pallet;
    private boolean Mostrar_Propiedades_Parametros = false;
    private double Cant_Recibida_Anterior = 0,Cant_Recibida,Cant_A_Recibir,Cant_Pendiente;
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
    private String pLp="";
    private boolean Escaneo_Pallet_Interno;
    private boolean Existe_Lp=false;
    private String ubiDetLote="";

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
    private clsBeTrans_oc_det_lote BeDetalleLotes= new clsBeTrans_oc_det_lote();
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

    private int pIdPropietarioBodega=0;

    double vFactorNuevaRec=0;
    double vCantNuevaRec=0;

    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto_estadoList LProductoEstado = new clsBeProducto_estadoList();

    private ArrayList<String> EstadoList= new ArrayList<String>();
    private ArrayList<String> PresList= new ArrayList<String>();
    private ArrayList<String> VenceList= new ArrayList<String>();
    private ArrayList<String> LotesList = new ArrayList<String>();
    private ArrayList<String> UbicLotesList = new ArrayList<String>();

    // date
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;
    private Object[] a1;

    double CajasPorCama = 0;
    double CamasPorTarima = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_recepcion_datos);

        super.InitBase();

        ws = new WebServiceHandler(frm_recepcion_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        cmbEstadoProductoRec = (Spinner)findViewById(R.id.cmbEstadoProductoRec);
        cmbPresRec = (Spinner) findViewById(R.id.cmbPresRec);
        cmbVence = (Spinner) findViewById(R.id.cmbVence);
        cmbLote = (Spinner) findViewById(R.id.cmbLote);
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
        lblEstiba = (TextView) findViewById(R.id.lblEstiba);
        lblUbicacion = (TextView) findViewById(R.id.lblUbicacion);

        btnCantRecibida = (Button)findViewById(R.id.btnCantRecibida);
        btnCantPendiente = (Button)findViewById(R.id.btnCantPendiente);

        dpResult = (DatePicker) findViewById(R.id.datePicker);

        imgDate = (ImageView)findViewById(R.id.imgDate);
        cmdImprimir = (ImageView)findViewById(R.id.cmdImprimir);

        chkPaletizar = (CheckBox)findViewById(R.id.chkPaletizar);
        chkPalletNoEstandar = (CheckBox)findViewById(R.id.chkPalletNoEstandar);
        chkEstiba = (CheckBox)findViewById(R.id.chkEstiba);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnIr = (Button)findViewById(R.id.btnIr);

        tblEstiba  = (TableRow)findViewById(R.id.tblEstiba);
        tbLPeso  = (TableRow)findViewById(R.id.tbLPeso);
        tblPres  = (TableRow)findViewById(R.id.tblPres);
        tblLote  = (TableRow)findViewById(R.id.tblLote);
        tblVence  = (TableRow)findViewById(R.id.tblVence);
        tblUbicacion = (TableRow)findViewById(R.id.tblUbicacion);

        tblEstiba.setVisibility(View.GONE);
        tblUbicacion.setVisibility(View.GONE);
        chkPaletizar.setVisibility(View.GONE);
        chkPalletNoEstandar.setVisibility(View.GONE);
        chkEstiba.setVisibility(View.GONE);

        lblUbicacion.setText("");

        setCurrentDateOnView();

        setHandlers();

        ProgressDialog();

        progress.setMessage("Inicializando valores");

        if (gl.gselitem != null) {
            BeOcDet=gl.gselitem;
        }

        if(gl.gBeOrdenCompra.TipoIngreso.getEs_Poliza_Consolidada()){
            pIdPropietarioBodega =  BeOcDet.IdPropietarioBodega;
        }else{
            pIdPropietarioBodega = gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
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
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    public void ChangeDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    public void Imprimir(View view){
        msgAskImprimir("Imprimir Licencia");
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
                    CajasPorCama = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.CajasPorCama).first();
                    CamasPorTarima = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.CamasPorTarima).first();

                    if (EsPallet||PermitePaletizar){
                        chkPaletizar.setVisibility(View.GONE);
                    }else if(PermitePaletizar && CajasPorCama>0 && CamasPorTarima>0) {
                       // chkPaletizar.setVisibility(View.VISIBLE);
                    }else{
                        chkPaletizar.setVisibility(View.GONE);
                    }

                    if (CajasPorCama > 0 && CamasPorTarima > 0){
                        tblEstiba.setVisibility(View.VISIBLE);
                        lblEstiba.setText("Camas por Tarima: " + CamasPorTarima + " Cajas por cama: " +  CajasPorCama);
                    }else{
                        tblEstiba.setVisibility(View.GONE);
                        lblEstiba.setText("");
                    }

                    if (EsPallet){
                        chkPalletNoEstandar.setVisibility(View.VISIBLE);
                        if (CajasPorCama==0 && CamasPorTarima==0){
                            chkEstiba.setVisibility(View.VISIBLE);
                        }else{
                            chkEstiba.setVisibility(View.GONE);
                        }
                    }else{
                        chkPalletNoEstandar.setVisibility(View.GONE);
                    }

                    BeProducto.Presentacion = BeProducto.Presentaciones.items.get(position);

                    if (BeProducto.Presentacion != null){
                        if (BeProducto.Presentacion.Genera_lp_auto) {
                            progress.setMessage("Buscando License Plate");
                            progress.show();
                            execws(6);
                            progress.cancel();
                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }


            });

            txtBarra.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (BeProducto.Control_vencimiento){
                            cmbVenceRec.setSelectAllOnFocus(true);
                            cmbVenceRec.requestFocus();
                        }else if (BeProducto.Control_lote){
                            txtLoteRec.setSelectAllOnFocus(true);
                            txtLoteRec.requestFocus();
                        }else if (!BeProducto.Control_lote&&!BeProducto.Control_vencimiento){
                           txtCantidadRec.requestFocus();
                        }

                        Procesa_Barra_Producto();

                    }

                    return false;
                }
            });

            txtCantidadRec.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                        if (tbLPeso.getVisibility()==View.VISIBLE){
                            txtPeso.requestFocus();
                        }else{
                            ValidaCampos();
                        }
                    }

                    return false;
                }
            });

            txtPeso.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                        double pesoTotal = Double.valueOf(txtPeso.getText().toString()).doubleValue();
                        double cant = Double.valueOf(txtCantidadRec.getText().toString()).doubleValue();
                        if (cant>0){
                            double pesoUni = mu.round(pesoTotal/cant,gl.gCantDecCalculo);
                            txtPesoUnitario.setText(String.valueOf(pesoUni));
                        }
                        ValidaCampos();
                    }

                    return false;
                }
            });

            cmbVenceRec .addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String valor= cmbVenceRec .getText().toString();
                    try{

                 /*       if (!du.EsFecha(valor)){
                            toast("No es una fecha válida");
                            cmbVenceRec.getText().delete(valor.length() - 1, valor.length());
                        };*/

                       // du.EsFecha(valor);
                    }catch(Exception e){
                        if(valor.length() != 0){
                            cmbVenceRec.getText().delete(valor.length() - 1, valor.length());
                        }
                    }
                }
            });

            cmdImprimir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imprimirDesdeBoton=true;
                    Imprimir(v);
                }
            });

            chkEstiba.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(((CompoundButton) view).isChecked()){
                         setEstiba();
                     } else {

                         CajasPorCama=0;
                         CamasPorTarima=0;

                         if (CajasPorCama > 0 && CamasPorTarima > 0){
                             tblEstiba.setVisibility(View.VISIBLE);
                             lblEstiba.setText("Camas por Tarima: " + CamasPorTarima + " Cajas por cama: " +  CajasPorCama);
                         }else{
                             tblEstiba.setVisibility(View.GONE);
                             lblEstiba.setText("");
                         }

                     }
                 }
             });

            cmbVence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        cmbVenceRec.setText(cmbVence.getSelectedItem().toString());
                        fillLotes();

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

            cmbLote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        txtLoteRec.setText(cmbLote.getSelectedItem().toString());
                        fillUbicacion();
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

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
            progress.cancel();
        }

    }

    //endregion

    //region validaciones

    private void ValidaCampos(){
        double Cant_Recibida = 0;

        try{

            if (BeProducto.Presentaciones != null) {
                if (BeProducto.Presentaciones.items != null){

                    if (IdPreseSelect!=-1){
                        boolean EsPallet = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.EsPallet).first();
                        if (EsPallet){
                            if (CajasPorCama==0 && CamasPorTarima==0){
                                throw new Exception("Debe ingresar la definición de la estiba");
                            }
                        }
                    }

                }
            }

            if (!txtCantidadRec.getText().toString().isEmpty() &&
                !txtCantidadRec.getText().toString().equals("")&&
                txtCantidadRec.getText().toString()!=null){
                Cant_Recibida = Double.parseDouble(txtCantidadRec.getText().toString());

                Cant_Recibida = mu.round(Cant_Recibida,6);

                if (BeProducto!=null){
                    if (ValidaDatosIngresados()){
                        //#CKFK20200816 Quité esta validacion porque no estó correcta
                   /* if (Cant_Recibida_Anterior!=Cant_Recibida && Cant_Recibida_Anterior!=0){
                        Mostro_Propiedades = false;
                        return;
                    }*/
                        Mostrar_Propiedades_Parametros();
                    }
                }
            }else{
               throw new Exception("La cantidad no puede ser vacía");
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
                }else{
                    double pesoTotal = Double.valueOf(txtPeso.getText().toString()).doubleValue();
                    double cant = Double.valueOf(txtCantidadRec.getText().toString()).doubleValue();
                    if (cant>0){
                        double pesoUni = mu.round(pesoTotal/cant,gl.gCantDecCalculo);
                        txtPesoUnitario.setText(String.valueOf(pesoUni));
                    }
                }
            }

            if (txtCantidadRec.getText().toString().isEmpty()){
                mu.msgbox("La cantidad no puede estar vacía");
                txtCantidadRec.requestFocus();
                return false;
            }

            if (Double.parseDouble(txtCantidadRec.getText().toString())<=0){
                mu.msgbox("La cantidad debe ser mayor a 0");
                txtCantidadRec.requestFocus();
                return false;
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
//                    txtCantidadRec.setText(mu.frmdecimal(1,gl.gCantDecDespliegue)+"");
                    txtCantidadRec.setText(1+"");
                    txtCantidadRec.requestFocus();
                    return;
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
//                    txtCantidadRec.setText(mu.frmdecimal(bePresentacion.CajasPorCama * bePresentacion.CamasPorTarima,gl.gCantDecDespliegue)+"");
                    txtCantidadRec.setText(bePresentacion.CajasPorCama * bePresentacion.CamasPorTarima+"");
                    txtCantidadRec.requestFocus();
                    return;
                }

            }

            //#CKFK 20210308 Agregué este cambio para que los parámetros solo se muestren cuando sea necesario
           if (existen_parametros_para_mostrar()){
                MuestraParametros1(this );
            }else{
               Guardar_Recepcion_Nueva();
            }

        }catch (Exception e){
            mu.msgbox("Muestra_Propiedades_Producto: "+e.getMessage());
        }

    }

    private boolean existen_parametros_para_mostrar(){

        int datos_ingresar = 0;
        boolean mostrar = false;

        try{

            if (BeProducto.Fechamanufactura && BeProducto.Materia_prima){
                datos_ingresar +=1;

            }

            if (BeProducto.Capturar_aniada){
                datos_ingresar +=1;
            }

            if (BeProducto.Peso_recepcion){
                datos_ingresar +=1;
            }

            if (BeProducto.Temperatura_recepcion){
                datos_ingresar +=1;
            }

            if (BeProducto.Serializado){
                datos_ingresar +=1;
            }

            if (BeProducto.Temperatura_recepcion){
                datos_ingresar +=1;
            }

            if (pListBEProductoParametro!=null){
                if (pListBEProductoParametro.items!=null){
                    datos_ingresar +=1;
                }
            }

            mostrar = datos_ingresar>0;

        }catch (Exception ex){
            mu.msgbox("existen_parametros_para_mostrar: "+ex.getMessage());
        }

        return mostrar;
    }

    private void MuestraParametros1(Activity activity){

        try{
            int IndexPresentacion=-1;
            Cant_Recibida_Actual=0;

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
            txtTempReal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPesoReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoReal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtTempEsta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtTempEsta.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPesoEsta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoEsta.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});

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

            if (pListBeStockRec!=null){
                if (pListBeStockRec.items!=null){
                    List AuxStock=stream(pListBeStockRec.items).select(c->c.IdRecepcionDet).toList();
                    pIndexStock =AuxStock.indexOf(pIdRecepcionDet);
                }
            }

            IndexPresentacion = IndexPresSelected;

              clsBeProducto_Presentacion bePresentacion= new clsBeProducto_Presentacion();

            if (IndexPresentacion!= -1){
                bePresentacion = BeProducto.Presentaciones.items.get(IndexPresentacion);
            }

            if (IndexPresentacion!=-1){

                if((bePresentacion.EsPallet||chkPaletizar.isChecked())&&
                        (bePresentacion.CamasPorTarima ==0|| bePresentacion.CajasPorCama==0)){
                   // mu.msgbox("La presentación no tiene los valores necesarios para recepcionar pallets");
                    txtLicPlate.setFocusable(false);
                    txtLicPlate.setFocusableInTouchMode(false);
                    txtLicPlate.setClickable(false);
                    txtLicPlate.setVisibility(View.GONE);
                    lblLicPlate.setVisibility(View.GONE);
                }else{
                    if (bePresentacion.EsPallet ||chkPaletizar.isChecked()|| bePresentacion.Permitir_paletizar){

                        if (bePresentacion.Genera_lp_auto){

                            txtLicPlate.setFocusable(true);
                            txtLicPlate.setFocusableInTouchMode(true);
                            txtLicPlate.setClickable(true);

                            if (!Existe_Lp){
                                execws(6);
                            }else{
                                pNumeroLP = pLp;

                                //#CKFK 20201229 Agregué esta condición de que si la barra tiene información se coloca eso como LP
                                if (!txtBarra.getText().toString().isEmpty()){
                                    txtLicPlate.setText(txtBarra.getText().toString().replace("$",""));
                                }else{
                                    txtLicPlate.setText(pNumeroLP);
                                }

                            }

                        }

                    } else if (!bePresentacion.EsPallet || bePresentacion.Permitir_paletizar||chkPaletizar.isChecked()) {

                        if (Cant_Recibida==0){
                            if (pIndexStock<0){
                                if (!Existe_Lp){
                                    execws(6);
                                }else{
                                    pNumeroLP = pLp;

                                    //#CKFK 20201229 Agregué esta condición de que si la barra tiene información se coloca eso como LP
                                    if (!txtBarra.getText().toString().isEmpty()){
                                        txtLicPlate.setText(txtBarra.getText().toString().replace("$",""));
                                    }else{
                                        txtLicPlate.setText(pNumeroLP);
                                    }

                                }
                            }
                        }else if(Cant_Recibida_Actual>bePresentacion.Factor){
                            if (pIndexStock<0){
                                execws(7);
                            }

                        }
                    }else{
                        lblLicPlate.setVisibility(View.GONE);
                        txtLicPlate.setFocusable(false);
                        txtLicPlate.setFocusableInTouchMode(false);
                        txtLicPlate.setClickable(false);
                        txtLicPlate.setVisibility(View.GONE);
                    }

                }

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

                pNumeroLP = pListBeStockRec.items.get(pIndexStock).Lic_plate;

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
                txtAnada.setText(pListBeStockRec.items.get(pIndexStock).Anada+"");

                if (BeProducto.Peso_recepcion){
                    txtPesoReal.setText(mu.round(pListBeStockRec.items.get(pIndexStock).Peso, gl.gCantDecCalculo)+"");
                }else{
                    txtPesoReal.setText(mu.round(0,  gl.gCantDecCalculo)+"");
                }

                if (BeProducto.Temperatura_recepcion){
                txtTempReal.setText(mu.round(pListBeStockRec.items.get(pIndexStock).Temperatura,  gl.gCantDecCalculo)+"");
                }

            }

            Button btnIr = (Button)dialog.findViewById(R.id.btnIr);
            Button btnBack = (Button)dialog.findViewById(R.id.btnBack);

            btnIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BotonIrGuardarParametros();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SalirPantallaParametros();
                }
            });

            dialog.show();

            mu.msgbox(MensajeParam);

        }catch (Exception e){
        mu.msgbox("MuestraParametros1: "+ e.getMessage());
        }
    }


    private void setEstiba(){

        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Definición de Estiba");
            alert.setMessage("Total de cajas: " + (CajasPorCama * CamasPorTarima));

            alert.setIcon(R.drawable.palet);

            final EditText txtCamasxtarima = new EditText(this);
            final EditText txtCajasxcama = new EditText(this);
            final TextView lblCamasxtarima = new TextView(this);
            final TextView lblCajasxcama = new TextView(this);
            final LinearLayout llContent = new LinearLayout(this);
            final TableRow trCajasxcama = new TableRow(this);
            final TableRow trCamasxtarima = new TableRow(this);
            llContent.setOrientation(LinearLayout.VERTICAL);

            trCamasxtarima.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            trCajasxcama.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            final float scale = this.getResources().getDisplayMetrics().density;

            lblCamasxtarima.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,(int) (1 * scale + 0.5f)));
            txtCamasxtarima.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,(int) (1 * scale + 0.5f)));

            lblCajasxcama.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,(int) (1 * scale + 0.5f)));
            txtCajasxcama.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,(int) (1 * scale + 0.5f)));

            txtCamasxtarima.setText("" + CamasPorTarima);
            txtCajasxcama.setText("" + CajasPorCama);

            txtCamasxtarima.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCajasxcama.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            lblCajasxcama.setTextSize(16);
            lblCajasxcama.setText("Cajas por Cama");

            lblCamasxtarima.setTextSize(16);
            lblCamasxtarima.setText("Camas por Tarima");

            trCamasxtarima.addView(lblCamasxtarima);
            trCamasxtarima.addView(txtCamasxtarima);

            trCajasxcama.addView(lblCajasxcama);
            trCajasxcama.addView(txtCajasxcama);

            llContent.addView(trCamasxtarima);
            llContent.addView(trCajasxcama);

            alert.setView(llContent);

            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) lblCamasxtarima.getLayoutParams();
            marginParams.setMargins(10,
                    marginParams.topMargin,
                    marginParams.rightMargin,
                    marginParams.bottomMargin);

            ViewGroup.MarginLayoutParams mrglCamasxtarima = (ViewGroup.MarginLayoutParams) lblCamasxtarima.getLayoutParams();
            mrglCamasxtarima.setMargins(10,
                    marginParams.topMargin,
                    marginParams.rightMargin,
                    marginParams.bottomMargin);

            ViewGroup.MarginLayoutParams mrgtCamasxtarima = (ViewGroup.MarginLayoutParams) txtCamasxtarima.getLayoutParams();
            mrgtCamasxtarima.setMargins(10,
                    marginParams.topMargin,
                    marginParams.rightMargin,
                    marginParams.bottomMargin);

            //txtCamasxtarima.setWidth(100);
            txtCamasxtarima.setSelectAllOnFocus(true);

            ViewGroup.MarginLayoutParams mrglCajasxcama = (ViewGroup.MarginLayoutParams) lblCajasxcama.getLayoutParams();
            mrglCajasxcama.setMargins(10,
                    marginParams.topMargin,
                    marginParams.rightMargin,
                    marginParams.bottomMargin);

            ViewGroup.MarginLayoutParams mrgtCajasxcama = (ViewGroup.MarginLayoutParams) txtCajasxcama.getLayoutParams();
            mrgtCajasxcama.setMargins(10,
                    marginParams.topMargin,
                    marginParams.rightMargin,
                    marginParams.bottomMargin);

            //txtCajasxcama.setWidth(100);
            txtCajasxcama.setSelectAllOnFocus(true);

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    chkEstiba.setChecked(false);

                    if (CajasPorCama > 0 && CamasPorTarima > 0){
                        tblEstiba.setVisibility(View.VISIBLE);
                        lblEstiba.setText("Camas por Tarima: " + CamasPorTarima + " Cajas por cama: " +  CajasPorCama);
                    }else{
                        tblEstiba.setVisibility(View.GONE);
                        lblEstiba.setText("");
                    }

                }
            });

            alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    CamasPorTarima=Double.valueOf(txtCamasxtarima.getText().toString());
                    CajasPorCama=Double.valueOf(txtCajasxcama.getText().toString());
                    alert.setMessage("Total de cajas: " + (CajasPorCama * CamasPorTarima));

                    if (CajasPorCama > 0 && CamasPorTarima > 0){
                        tblEstiba.setVisibility(View.VISIBLE);
                        lblEstiba.setText("Camas por Tarima: " + CamasPorTarima + " Cajas por cama: " +  CajasPorCama);
                    }else{
                        tblEstiba.setVisibility(View.GONE);
                        lblEstiba.setText("");
                    }

                    if(CamasPorTarima==0 || CajasPorCama==0){
                       toastlong("Debe ingresar la definición de estiba");
                       llContent.removeAllViews();
                       setEstiba();
                    }else{
                        llContent.removeAllViews();
                    }
                }
            });

            final AlertDialog dialog = alert.create();
            dialog.show();

            txtCamasxtarima.requestFocus();
            showkeyb();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    //View view
    public void BotonIrGuardarParametros(){
        GuardarParametros();
    }

    //View view
    public void SalirPantallaParametros(){
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
                txtTempEsta.setText( mu.round(BeProducto.Temperatura_referencia,  gl.gCantDecCalculo)+"");
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

                txtPesoEsta.setText(mu.round(BeProducto.Peso_referencia,  gl.gCantDecCalculo)+"");

                if (!txtPesoUnitario.getText().toString().isEmpty() &&
                    !txtPesoUnitario.getText().toString().equals("") &&
                    txtPesoUnitario.getText().toString()!=null){
                    txtPesoReal.setText(txtPesoUnitario.getText()+"");
                }else{
                    throw new Exception("Debe ingresar el peso del producto");
                }

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
        MensajeParam="";

        try{

            Parametros_Ingresados =Parametros_Obligatorios_Ingresados(Detalle);

            if (!Parametros_Ingresados){
                mu.msgbox("¿Está seguro de que no va a ingresar los parámetros obligatorios del producto?");

                Parametros_Ingresados = true;
            }

            if (Parametros_Ingresados){

                if (BeProducto.getControl_peso()){
                    Peso_Correcto();
                }
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

                double PorcentajeToleranciaTemp = mu.round(BeProducto.Temperatura_referencia * BeProducto.Temperatura_tolerancia,  gl.gCantDecCalculo) / 100;
                double TemperaturaMax  = mu.round(BeProducto.Temperatura_referencia + PorcentajeToleranciaTemp,  gl.gCantDecCalculo);
                double TemperaturaMin  = mu.round(BeProducto.Temperatura_referencia - PorcentajeToleranciaTemp,  gl.gCantDecCalculo);
                double vTemp=0;
                if (!txtTempReal.getText().toString().isEmpty()){
                    vTemp = Double.parseDouble(txtTempReal.getText().toString());
                }
                double ValorTemperatura  = mu.round(vTemp,  gl.gCantDecCalculo);

                if ((ValorTemperatura < TemperaturaMin)||(ValorTemperatura > TemperaturaMax)){
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
                        MensajeParam +="Debe ingresar peso \n";
                        return false;
                    }else if(Double.parseDouble(txtPesoReal.getText().toString())<=0){
                        MensajeParam+="El peso debe ser mayor a 0 \n";
                        return false;
                    }

                }

                Double PorcentajeToleranciaPeso = (Double.parseDouble(txtPesoEsta.getText().toString()) * (BeProducto.Peso_tolerancia));
                Double PesoMaximoReferencia = mu.round( Double.parseDouble(txtPesoEsta.getText().toString()) + PorcentajeToleranciaPeso,  gl.gCantDecCalculo);
                Double PesoMinimoReferencia = mu.round(Double.parseDouble(txtPesoEsta.getText().toString()) - PorcentajeToleranciaPeso,  gl.gCantDecCalculo);
                Double ValorPeso  = mu.round(Double.parseDouble(txtPesoReal.getText().toString()),  gl.gCantDecCalculo);

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
                    //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                    BeStock_rec.IdPropietarioBodega = pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                    BeStock_rec.IdProductoBodega = BeProducto.IdProductoBodega;
                    BeStock_rec.IdUnidadMedida = BeProducto.IdUnidadMedidaBasica;
                    if(IdPreseSelect>0){
                        BeStock_rec.Presentacion = new clsBeProducto_Presentacion();
                    }
                    if (gl.mode==1){
                        BeStock_rec.IsNew = true;
                    }else{
                        BeStock_rec.IsNew = false;
                    }

                   pListBeStockRec.items.add(BeStock_rec);
                   pIndexStock = pListBeStockRec.items.size()-1;

                   if (IdPreseSelect!=-1){
                       if  ((BeProducto.Presentaciones!=null)){

                           if (BeProducto.Presentaciones.items!=null) {

                               List AuxLisPres = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();

                               int IndexPresentacion = AuxLisPres.indexOf(IdPreseSelect);

                               clsBeProducto_Presentacion bePresentacion = new clsBeProducto_Presentacion();

                               bePresentacion = BeProducto.Presentaciones.items.get(IndexPresentacion);

                               BeStock_rec.Presentacion = bePresentacion;

                               if (BeStock_rec.Presentacion.Imprime_barra){

                                   clsBeProducto_pallet BeProdPallet = new clsBeProducto_pallet();
                                   //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                                   BeProdPallet.IdPropietarioBodega =  pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                                   BeProdPallet  .IdProductoBodega = BeProducto.IdProductoBodega;
                                   BeProdPallet.IdOperadorBodega = gl.OperadorBodega.IdOperadorBodega;
                                   BeProdPallet.IdPresentacion = IdPreseSelect;
                                   BeProdPallet.IdRecepcionDet = pIdRecepcionDet;
                                   BeProdPallet.Impreso = false;
                                   BeProdPallet.IdImpresora = 1;
                                   BeProdPallet.Activo = true;
                                   BeProdPallet.Fecha_ingreso = String.valueOf(du.getFechaActual());
                                   BeProdPallet.Codigo_Barra = txtLicPlate.getText().toString();
                                   BeProdPallet.Codigo_Producto = BeProducto.Codigo;
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
                        if (gl.mode==1){
                            ObjNS.IsNew = true;
                        }else{
                            ObjNS.IsNew = false;
                        }

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

                    //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                    pListBeStockRec.items.get(pIndexStock).IdPropietarioBodega = pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
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

                                //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                                pListBeProductoPallet.items.get(pIndexProdPallet).IdPropietarioBodega=pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
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

            //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
            ObjS.IdPropietarioBodega = pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
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
    private void Procesa_Barra_Producto(){

        boolean LongitudValida = true;

        try{

            pLp = "";
            Escaneo_Pallet_Interno = false;

            if (!txtBarra.getText().toString().isEmpty()){

               /* String vStarWithParameter = "$";

                if (gBeConfiguracionBarraPallet!=null){
                    if (!gBeConfiguracionBarraPallet.IdentificadorInicio.isEmpty()){
                        vStarWithParameter = gBeConfiguracionBarraPallet.IdentificadorInicio;
                    }
                }*/

                /*if (txtBarra.getText().toString().startsWith("$") |
                        txtBarra.getText().toString().startsWith("(01)") |
                        txtBarra.getText().toString().startsWith(vStarWithParameter)){*/

                    //int vLengthBarra  = txtBarra.getText().toString().length();

                    pLp = txtBarra.getText().toString().replace("$", "");

                    Escaneo_Pallet_Interno = true;

                    //Llama al método del WS Existe_Lp
                    execws(24);
                //}
            }

        }catch (Exception e){
            mu.msgbox("Procesa_Barra_Producto: "+e.getMessage());
        }
    }

    private void Load(){

        try{

            progress.setMessage("Cargando valores de tarea");

            if (gl.gselitem != null) {

                Escaneo_Pallet = gl.gEscaneo_Pallet;

                BeProducto.IdProductoBodega = BeOcDet.IdProductoBodega;
                pIdOrdenCompraDet= BeOcDet.IdOrdenCompraDet;
                pIdOrdenCompraEnc = BeOcDet.IdOrdenCompraEnc;
                pLineaOC = BeOcDet.No_Linea;

                if (BeProducto.getControl_vencimiento()){

                    tblVence.setVisibility(View.VISIBLE);
                    cmbVence.setVisibility(View.GONE);
                    cmbVenceRec.setVisibility(View.VISIBLE);
                    imgDate.setVisibility(View.VISIBLE);

                    if (BeOcDet!=null) {

                        if (gl.gBeOrdenCompra.DetalleLotes.items != null) {

                            if (gl.gBeOrdenCompra.DetalleLotes.items.size() > 0) {

                                cmbVence.setVisibility(View.VISIBLE);
                                cmbVenceRec.setVisibility(View.GONE);
                                imgDate.setVisibility(View.GONE);
                                fillFechaVence();
                            }
                        }
                    }
                }else{
                    tblVence.setVisibility(View.GONE);
                }

                if (BeProducto.getControl_lote()){

                    cmbLote.setVisibility(View.GONE);
                    tblUbicacion.setVisibility(View.GONE);

                    txtLoteRec.setVisibility(View.VISIBLE);

                    if (BeOcDet!=null) {

                        if (gl.gBeOrdenCompra.DetalleLotes.items != null) {

                            if (gl.gBeOrdenCompra.DetalleLotes.items.size() > 0) {

                                cmbLote.setVisibility(View.VISIBLE);
                                txtLoteRec.setVisibility(View.GONE);
                                tblUbicacion.setVisibility(View.VISIBLE);
                                fillLotes();
                            }
                        }
                    }
                }

                if (Escaneo_Pallet){

                    BeINavBarraPallet = frm_list_rec_prod.BeINavBarraPallet;

                    if (frm_list_rec_prod.BeProducto!=null){
                        if (frm_list_rec_prod.BeProducto.IdProducto>0){
                            BeProducto = frm_list_rec_prod.BeProducto;
                            BeProducto.Presentaciones.items = stream(BeProducto.Presentaciones.items).where(c->c.Codigo_barra.equals(BeINavBarraPallet.UM_Producto)).toList();

                            if (BeProducto.Presentaciones.items.size()>0){
                                BeProducto.Presentacion = BeProducto.Presentaciones.items.get(0);
                            }
                        }
                    }

                    if (gl.mode==1){
                        Carga_Datos_Producto();
                    }else{
                        Carga_Datos_Producto_Existente();
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
                        if (gl.mode==1){
                            Carga_Datos_Producto();
                        }else{
                            Carga_Datos_Producto_Existente();
                        }

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

                    if (BeProducto.Presentacion != null){
                        if (BeProducto.Presentacion.Genera_lp_auto) {
                            txtLicPlate.setFocusable(true);
                            txtLicPlate.setFocusableInTouchMode(true);
                            txtLicPlate.setClickable(true);

                            execws(6);
                        }
                    }

                }else{

                    pListTransRecDet.items = new ArrayList<clsBeTrans_re_det>();

                    execws(19);

                }

            }

        }catch (Exception e){
            progress.cancel();
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

                    if (BeProducto.Presentacion != null){
                        if (BeProducto.Presentacion.Genera_lp_auto) {
                            txtLicPlate.setFocusable(true);
                            txtLicPlate.setFocusableInTouchMode(true);
                            txtLicPlate.setClickable(true);

                            execws(6);
                        }
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

    private void Carga_Datos_Producto_Existente(){

        int IndiceOCDet;
        int vPresentacion;
        int IndicePres;
        double Factor;
        double CostoOC=0;

        try{

            progress.setMessage("Cargando datos de producto existente");

            if (BeProducto.IdProducto!=0){

                Mostro_Propiedades = false;

                plistBeReDetParametros = new clsBeTrans_re_det_parametrosList();
                lBeStockRec = new clsBeStock_recList();
                lBeProdPallet = new clsBeProducto_palletList();
                pListBeStockSeRec = new clsBeStock_se_recList();
                pListBeStockRec = new clsBeStock_recList();
                pListBeProductoPallet = new clsBeProducto_palletList();
                pListTransRecDet.items = gl.gListTransRecDet.items;

                if (pListTransRecDet.items!=null){

                    pIdRecepcionDet = pListTransRecDet.items.get(0).IdRecepcionDet;

                    pLineaOC = pListTransRecDet.items.get(0).No_Linea;

                    execws(23);

                }else{
                    pListTransRecDet.items = new ArrayList<clsBeTrans_re_det>();

                    execws(19);

                    //Listar_Producto_Presentaciones();

                }

            }


        }catch (Exception e){
            mu.msgbox("CargarDatos_Existentes: "+e.getMessage());
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
                txtBarra.setText("");
            }

            if(BeProducto.IdProductoBodega>0){
                pIdProductoBodega = BeProducto.IdProductoBodega;
            }

            lblDatosProd.setText(BeProducto.Codigo + " - " + BeProducto.Nombre);
            lblPropPrd.setText(BeProducto.Propietario.Nombre_comercial);

            if (BeProducto.Control_vencimiento){

                tblVence.setVisibility(View.VISIBLE);

                if (!gl.gFechaVenceAnterior.equals("")){
                    cmbVenceRec.setText(gl.gFechaVenceAnterior);
                }

            }else{

                tblVence.setVisibility(View.GONE);
            }

            Valida_Lote();

            clsBeTrans_oc_det BeOcDet =
                    stream(gl.gpListDetalleOC.items)
                            .where(c -> c.IdOrdenCompraDet == pIdOrdenCompraDet)
                            .first();

            vPresentacion = Get_Presentacion_A_Recibir();

            if (vPresentacion>0){

                Factor =Get_Factor_Presentacion(vPresentacion);

                //#EJC20201008: Da error de NoSuchElementException cuando no encuentra la presentación por el ID,
                //Por eso agregué este try catch así.

                clsBeProducto_Presentacion a= new clsBeProducto_Presentacion();

                try {
                    EsPallet = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.EsPallet).first();
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }


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

                Cant_Recibida = gl.gselitem.Cantidad_recibida;//stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet ==pIdOrdenCompraDet).select(c->c.Cantidad_recibida).first();
                Cant_A_Recibir = gl.gselitem.Cantidad; //stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet ==pIdOrdenCompraDet).select(c->c.Cantidad).first();
                if(Cant_A_Recibir - Cant_Recibida<0){
                    Cant_Pendiente = 0;
                }else{
                    Cant_Pendiente = mu.round(Cant_A_Recibir - Cant_Recibida,gl.gCantDecCalculo);
                }

               // txtCantidadRec.setText(mu.frmdecimal(Cant_Pendiente,gl.gCantDecDespliegue)+"");
               // txtCantidadRec.setText(Cant_Pendiente+"");
               // #CKFK 20210218 Quité la cantidad pendiente de recibir en la CantidadRec por instrucción de Erik
                txtCantidadRec.setText("");

                txtBarra.requestFocus();
                txtBarra.selectAll();

                clsBeTrans_oc_det_lote BeLoteLinea;

                /*if (BeOcDet!=null){

                    if (gl.gBeOrdenCompra.DetalleLotes.items!=null){

                        if (gl.gBeOrdenCompra.DetalleLotes.items.size()>0){

                            BeLoteLinea = stream(gl.gBeOrdenCompra.DetalleLotes.items)
                                    .where(c->c.No_linea == BeOcDet.No_Linea &&
                                            c.IdOrdenCompraDet == pIdOrdenCompraDet
                                            && c.Codigo_producto.equals(BeOcDet.Codigo_Producto)).first();

                            if (BeLoteLinea!=null){
                                txtLoteRec.setText(BeLoteLinea.Lote);

                                if (!BeLoteLinea.Fecha_vence.isEmpty()){

                                    VenceList.clear();

                                    BeLoteLinea.Fecha_vence =du.convierteFechaMostar(BeLoteLinea.Fecha_vence);

                                    VenceList.add(BeLoteLinea.Fecha_vence);

                                    cmbVenceRec.setText(VenceList.get(0));

                                }
                            }

                        }
                    }
                }*/

                FinalizCargaProductos();

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    private void LlenaDatosFaltantes_Existente(){

        vPresentacion=0;
        CostoOC=0;
        double Factor=0;
        boolean EsPallet=false;
        int Indx=-1;

        try{

            progress.setMessage("Terminando de cargar datos y validando valores");

            if (Escaneo_Pallet){
                txtBarra.setText(pListTransRecDet.items.get(0).Lic_plate);
            }else{
                txtBarra.setText(Get_Codigo_Barra(gl.CodigoRecepcion));
                txtBarra.setText("");
            }

            if(BeProducto.IdProductoBodega>0){
                pIdProductoBodega = pListTransRecDet.items.get(0).IdProductoBodega;
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

            vPresentacion = pListTransRecDet.items.get(0).IdPresentacion;

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

                Cant_Recibida = gl.gselitem.Cantidad_recibida;//stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet ==pIdOrdenCompraDet).select(c->c.Cantidad_recibida).first();
                Cant_A_Recibir = gl.gselitem.Cantidad; //stream(gl.gpListDetalleOC.items).where(c->c.IdOrdenCompraDet ==pIdOrdenCompraDet).select(c->c.Cantidad).first();
                if(Cant_A_Recibir - Cant_Recibida<0){
                    Cant_Pendiente = 0;
                }else{
                    Cant_Pendiente = mu.round(Cant_A_Recibir - Cant_Recibida,gl.gCantDecCalculo);
                }

//                txtCantidadRec.setText(mu.frmdecimal(pListTransRecDet.items.get(0).cantidad_recibida,gl.gCantDecDespliegue)+"");
                txtCantidadRec.setText(pListTransRecDet.items.get(0).cantidad_recibida+"");
                txtCantidadRec.requestFocus();

                Cant_Recibida_Anterior = pListTransRecDet.items.get(0).cantidad_recibida;

                txtLoteRec.setText(pListTransRecDet.items.get(0).Lote);

                cmbVenceRec.setText(du.convierteFechaMostar(pListTransRecDet.items.get(0).Fecha_vence));

                txtCostoReal.setText(pListTransRecDet.items.get(0).Costo+"");

                List AxuLisEsta = stream(pListTransRecDet.items).select(c->c.IdProductoEstado).toList();
                Indx =AxuLisEsta.indexOf(pListTransRecDet.items.get(0).IdProductoEstado);

                cmbPresRec.setSelection(Indx);

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
                if (gl.mode==1){
                    if (!gl.gLoteAnterior.isEmpty() && gl.gProductoAnterior.equals(BeProducto.getCodigo())){
                        txtLoteRec.setText(gl.gLoteAnterior);
                    }
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

            if (!gl.gProductoAnterior.equals(BeProducto.getCodigo())){
                if (cmbLote.getVisibility()!=View.VISIBLE){
                    txtLoteRec.setText("");
                }
                if (cmbVence.getVisibility()!=View.VISIBLE){
                    cmbVenceRec.setText(du.convierteFechaMostar(du.getFechaActual()));
                }

                cmbEstadoProductoRec.setSelection(0);
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
                    txtCostoOC.setText(mu.round(CostoOC,  gl.gCantDecCalculo)+"");
                }
                else {
                    txtCostoOC.setText(mu.round(BeProducto.Costo, gl.gCantDecCalculo)+"");
                }

            } else {
                txtCostoOC.setText(mu.round(BeProducto.Costo, gl.gCantDecCalculo)+"");
            }

            txtCostoOC.setText(mu.round(BeProducto.Costo, gl.gCantDecCalculo)+"");

            txtCostoReal.setText(CostoOC+"");

            txtUmbasRec.setFocusable(false);
            txtUmbasRec.setFocusableInTouchMode(false);
            txtUmbasRec.setClickable(false);

            if (gl.mode==1){
                txtBarra.setText(BeProducto.Codigo);
                txtBarra.setText("");
            }

            /*txtBarra.setFocusable(false);
            txtBarra.setFocusableInTouchMode(false);
            txtBarra.setClickable(false);*/

            txtCantidadRec.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCantidadRec.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtCostoOC.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCostoOC.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPeso.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPeso.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtCostoReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCostoReal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPesoUnitario.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoUnitario.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            cmbVenceRec.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            btnCantPendiente.setText("Pendiente: "+mu.frmdecimal(Cant_Pendiente, gl.gCantDecDespliegue));
            btnCantRecibida.setText("Recibido: "+mu.frmdecimal(Cant_Recibida, gl.gCantDecDespliegue));

            if (gl.Carga_Producto_x_Pallet){

                txtLoteRec.setText(gBeStockRec.Lote);

                txtLoteRec.setFocusable(false);
                txtLoteRec.setFocusableInTouchMode(false);
                txtLoteRec.setClickable(false);

                cmbVenceRec.setText(du.convierteFechaMostar(gBeStockRec.Fecha_vence));

                cmbVenceRec.setFocusable(false);
                cmbVenceRec.setFocusableInTouchMode(false);
                cmbVenceRec.setClickable(false);

            }

            if (gl.mode==1){
                if (Escaneo_Pallet){
                    LlenaCamposEsPallet();
                }
            }else{
                Llena_beStock_Anterior();
            }

            progress.cancel();

        }catch (Exception e){
            mu.msgbox("FinalizCargaProductos:"+e.getMessage());
        }
    }

    private void Llena_beStock_Anterior(){

        try{

            gBeStockAnt = new  clsBeStock();

            gBeStockAnt.IdProductoBodega = pListTransRecDet.items.get(0).IdProductoBodega;
            if (pListTransRecDet.items.get(0).Presentacion.Factor>0){
                gBeStockAnt.Cantidad = pListTransRecDet.items.get(0).cantidad_recibida*pListTransRecDet.items.get(0).Presentacion.Factor;
            }else{
                gBeStockAnt.Cantidad = pListTransRecDet.items.get(0).cantidad_recibida;
            }

            if ( pListTransRecDet.items.get(0).ProductoEstado!=null&&pListTransRecDet.items.get(0).ProductoEstado.IdEstado>0){
                gBeStockAnt.IdProductoEstado = pListTransRecDet.items.get(0).ProductoEstado.IdEstado;
            }else{
                gBeStockAnt.IdProductoEstado = pListTransRecDet.items.get(0).IdProductoEstado;
            }

            gBeStockAnt.Lote = pListTransRecDet.items.get(0).Lote;
            gBeStockAnt.IdUnidadMedida = pListTransRecDet.items.get(0).IdUnidadMedida;
            gBeStockAnt.IdPresentacion = pListTransRecDet.items.get(0).IdPresentacion;
            gBeStockAnt.Fecha_vence = pListTransRecDet.items.get(0).Fecha_vence;
            gBeStockAnt.IdRecepcionDet = pListTransRecDet.items.get(0).IdRecepcionDet;
            gBeStockAnt.IdRecepcionEnc = pListTransRecDet.items.get(0).IdRecepcionEnc;
            gBeStockAnt.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;

        }catch (Exception e){
            mu.msgbox("Llena_beStock_Anterior:"+e.getMessage());
        }
    }

    private void LlenaCamposEsPallet(){

        try{

            txtLoteRec.setText(BeINavBarraPallet.Lote);
            cmbVenceRec.setText(du.convierteFechaMostar(BeINavBarraPallet.Fecha_Vence));

            if (!EsTransferenciaInternaWMS){
//                txtCantidadRec.setText(mu.frmdecimal(BeINavBarraPallet.Cantidad_UMP,gl.gCantDecDespliegue)+"");
                txtCantidadRec.setText(BeINavBarraPallet.Cantidad_UMP+"");
                txtCantidadRec.requestFocus();
            }else{
//                txtCantidadRec.setText(mu.frmdecimal(BeINavBarraPallet.Cantidad_Presentacion,gl.gCantDecDespliegue));
                txtCantidadRec.setText(BeINavBarraPallet.Cantidad_Presentacion+"");
                txtCantidadRec.requestFocus();
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

    private void fillFechaVence() {

        String valor;

        try {

            VenceList.clear();

            clsBeTrans_oc_det_loteList vence = new clsBeTrans_oc_det_loteList();
            vence=gl.gBeOrdenCompra.DetalleLotes;
            List<clsBeTrans_oc_det_lote> BeVence =  stream(vence.items)
                    .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet)
                    .toList();

            double CantRec =0;

            for (int i = 0; i <BeVence.size(); i++)
            {
                valor = du.convierteFechaMostar(BeVence.get(i).Fecha_vence);
                CantRec = BeVence.get(i).Cantidad_recibida;

                if (VenceList.indexOf(valor)==-1){
                    if (CantRec==0){
                        VenceList.add(valor);
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, VenceList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbVence.setAdapter(dataAdapter);

            if (VenceList.size()>0) cmbVence.setSelection(0);
            cmbVenceRec.setText(cmbVence.getSelectedItem().toString());

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void fillLotes() {

        String valor;

        try {

            LotesList.clear();

            clsBeTrans_oc_det_loteList lotes = new clsBeTrans_oc_det_loteList();
            lotes=gl.gBeOrdenCompra.DetalleLotes;
            List<clsBeTrans_oc_det_lote> BeLotes;

            if (BeProducto.getControl_vencimiento()){
                BeLotes = stream(lotes.items)
                        .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                c.Fecha_vence.equals(du.convierteFecha(cmbVence.getSelectedItem().toString())))
                        .toList();

            }else{
                BeLotes = stream(lotes.items)
                        .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet)
                        .toList();
            }

            double CantRec =0;

            for (int i = 0; i <BeLotes.size(); i++)
            {

                valor = BeLotes.get(i).Lote;
               CantRec = BeLotes.get(i).Cantidad_recibida;

                if (LotesList.indexOf(valor)==-1){
                    if (CantRec==0){
                        LotesList.add(valor);
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, LotesList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbLote.setAdapter(dataAdapter);

            if (LotesList.size()>0) cmbLote.setSelection(0);

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    //#EJC20210412: Funcion para llenar ubicacion de NAV.
    private void fillUbicacion() {

        String valor;

        try {

            UbicLotesList.clear();

            clsBeTrans_oc_det_loteList ubic = new clsBeTrans_oc_det_loteList();
            ubic=gl.gBeOrdenCompra.DetalleLotes;
            List<clsBeTrans_oc_det_lote> BeUbicaciones;

            String SelectedLote ="";
            String FechaVence ="";

            SelectedLote = txtLoteRec.getText().toString().trim();
            FechaVence = du.convierteFecha(cmbVenceRec.getText().toString());

            String finalSelectedLote = SelectedLote;
            String finalFechaVence = FechaVence;

            if (BeProducto.getControl_vencimiento()){

                BeUbicaciones = stream(ubic.items)
                        .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                c.Lote.equals(finalSelectedLote)  &&
                                c.Fecha_vence.equals(finalFechaVence))
                        .toList();

            }else{
                BeUbicaciones = stream(ubic.items)
                        .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.Lote.equals(finalSelectedLote)  &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet)
                        .toList();
            }

            double CantRec =0;

            for (int i = 0; i <BeUbicaciones.size(); i++)
            {
                valor = BeUbicaciones.get(i).Ubicacion;
                CantRec =BeUbicaciones.get(i).Cantidad_recibida;

                if (UbicLotesList.indexOf(valor)==-1){
                    if (CantRec ==0){
                        UbicLotesList.add(valor);
                    }
                }
            }

            if (UbicLotesList.size()>0){
                ubiDetLote = UbicLotesList.get(0);
                if(!ubiDetLote.isEmpty()) lblUbicacion.setText("Doc: -> " + ubiDetLote);
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
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
                //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                vBeStockRecPallet.IdPropietarioBodega = pIdPropietarioBodega;//gl.gBeOrdenCompra.IdPropietarioBodega;
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
                vBeStockRecPallet.Fecha_vence = BeINavBarraPallet.Fecha_Vence;
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

                vBeStockRecPallet.Lote = BeINavBarraPallet.Lote;
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

            vBeStockRecPallet.IdRecepcionEnc = gl.gBeRecepcion.IdRecepcionEnc;
            vBeStockRecPallet.ProductoValidado = true;
            vBeStockRecPallet.No_linea = pLineaOC;

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
                        if (Escaneo_Pallet){
                            IdPreseSelect=BeProducto.Presentaciones.items.get(i).IdPresentacion;
                        }
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

            if (Escaneo_Pallet){
                IdEstadoSelect = gl.gIdProductoBuenEstadoPorDefecto;
                List AuxEst = stream(LProductoEstado.items).select(c->c.IdEstado).toList();
                int indx = AuxEst.indexOf(IdEstadoSelect);
                if (EstadoList.size()>0) cmbEstadoProductoRec.setSelection(indx);
            }else{
                if (EstadoList.size()>0) cmbEstadoProductoRec.setSelection(0);
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    //endregion

    //region GuardarRecepcion

    private void Guardar_Recepcion_Nueva(){

        try{

            if (BeProducto!=null){
                if(ValidaDatosIngresados()){
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
                }
            }else{
                msgbox("No está definido el producto que se va a recepcionar");
            }

        }catch (Exception e){
            mu.msgbox("Guardar_Recepcion: "+ e.getMessage());
        }

    }

    public void BotonGuardarRecepcion(View view){

        try{

            if (BeProducto.Presentaciones != null) {
                if (BeProducto.Presentaciones.items != null){

                    if (IdPreseSelect!=-1){
                        boolean EsPallet = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.EsPallet).first();

                        if (EsPallet){
                            if (CajasPorCama==0 && CamasPorTarima==0){
                                msgbox("Debe ingresar la definición de la estiba");
                                return;
                            }
                        }
                    }

                }
            }

            gl.gProductoAnterior = BeProducto.getCodigo();

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
                //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                BeStock_rec.IdPropietarioBodega =pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
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

                //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                pListBeStockRec.items.get(pIndiceListaStock).IdPropietarioBodega = pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                pListBeStockRec.items.get(pIndiceListaStock).IdProductoBodega = BeProducto.IdProductoBodega;

                //#CKFK 20210308 Agregué esta validación para ingresar el LP que ingresaron manualmente
                if (!pLp.equals("")){
                    pListBeStockRec.items.get(pIndiceListaStock).Lic_plate = pLp;
                }else{
                    if (pListBeStockRec.items.get(pIndiceListaStock).Lic_plate!=null){
                        pListBeStockRec.items.get(pIndiceListaStock).Lic_plate = pListBeStockRec.items.get(pIndiceListaStock).Lic_plate;
                    }else{
                        pListBeStockRec.items.get(pIndiceListaStock).Lic_plate = "";
                    }
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
                pListBeStockRec.items.get(pIndiceListaStock).Pallet_No_Estandar = false;
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

        progress.setMessage("Guardando recepción");
        progress.show();

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

    public void valida_fecha_vencimiento(){

        try{

            if (BeProducto.Control_vencimiento){

                if (cmbVenceRec.getText().toString().isEmpty()){
                    mu.msgbox("Ingrese fecha de vencimiento para el producto "+BeProducto.Codigo);
                    return;
                }else{

                    BeTransReDet.Fecha_vence =du.convierteFecha(cmbVenceRec.getText().toString().trim());
                    gl.gFechaVenceAnterior = cmbVenceRec.getText().toString().trim();

                    String FechaVence=BeTransReDet.Fecha_vence;
                    String FechaActual=du.getFechaActual();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date strDate = sdf.parse(FechaVence);
                    Date strDateNow = sdf.parse(FechaActual);

                    if (strDate.getTime()  <=  strDateNow.getTime() ) {
                        msgValidaFechaVence("La fecha de vencimiento del producto "+BeProducto.Codigo+ " es igual o menor a la fecha de hoy. ¿Desea ingresar un producto ya vencido?");
                    }else{
                        DespuesDeValidarCantidad();
                    }

                    //#CKFK 20201007 quité esta validacion de fecha porque no era correcta
                   /* if (FechaVence.equals(String.valueOf(du.getFechaActual()))){

                    }*/

                    //#CKFK 20200917 Puse esto en comentario porque la validación no se hacía correctamente
                       /* if (!Valida_Fecha_Vencimiento()){
                            return;
                        }else{
                            Continua_Llenando_Detalle_Recepcion_Nueva();
                        }*/
                }
            }else{
                DespuesDeValidarCantidad();
            };

        }catch (Exception ex){

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

            progress.setMessage("Llenando detalle de recepción");

            switch (gl.TipoOpcion){

                case 1:

                    if (gl.mode==1){
                        Llena_Detalle_Recepcion_Nueva();
                    }else{
                        Llena_Detalle_Recepcion_Existente();

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
            if (auxListTransRecDet.items!=null)
            {
                //#EJC20200325: Aquí da error en algunas ocasiones

                try{

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
            }/*else{
               DespuesDeValidarCantidad();
            }*/

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

            progress.show();
            progress.setMessage("Validando imprimir barra");

            if (gl.IdImpresora>0){

                progress.cancel();

                imprimirDesdeBoton=false;
                msgAskImprimir("Seleccione una opción para imprimir");

            }else{
                Actualiza_Valores_Despues_Imprimir();
            }

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.msgbox("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
                Actualiza_Valores_Despues_Imprimir();
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }
        }

    }

    private void Actualiza_Valores_Despues_Imprimir(){
        try{

            //CM_20210125: Actualiza valores de la OC después imprimir
            switch (gl.TipoOpcion){

                case 1:
                    progress.setMessage("Actualizando valores OCDet");
                    progress.show();
                    beTransOCDet =new clsBeTrans_oc_det();
                    beTransOCDet.IdOrdenCompraEnc = pIdOrdenCompraEnc;
                    beTransOCDet.IdOrdenCompraDet = pIdOrdenCompraDet;
                    execws(18);
                    break;

                case 2:
                    progress.cancel();
                    break;

            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
        }
        progress.cancel();
    }

    //#EJC20210125: Dejé solo la función de Tzirin puse en comentario la de Jaros..
    private void msgAskImprimir(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(true);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Código de Producto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Barra();
                }
            });

            dialog.setNegativeButton("Licencia de Producto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Licencia();
                }
            });

            dialog.setNeutralButton("No imprimir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (!imprimirDesdeBoton){
                        progress.setMessage("Actualizando valores OC");
                        progress.show();
                        Actualiza_Valores_Despues_Imprimir();
                    }
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }


    private void Imprimir_Licencia(){
        try{

            //CM_20210112: Impresión de barras.
            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);
            printerIns.open();

            if (printerIns.isConnected()){
                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);
                //zPrinterIns.sendCommand("! U1 setvar \"device.languages\" \"zpl\"\r\n");


                String zpl = String.format("^XA \n" +
                                "^MMT \n" +
                                "^PW700 \n" +
                                "^LL0406 \n" +
                                "^LS0 \n" +
                                "^FT171,61^A0I,25,14^FH^FD%1$s^FS \n" +
                                "^FT550,61^A0I,25,14^FH^FD%2$s^FS \n" +
                                "^FT670,306^A0I,25,14^FH^FD%3$s^FS \n" +
                                "^FT292,61^A0I,25,24^FH^FDBodega:^FS \n" +
                                "^FT670,61^A0I,25,24^FH^FDEmpresa:^FS \n" +
                                "^FT670,367^A0I,25,24^FH^FDTOMIMS, WMS.  Product Barcode^FS \n" +
                                "^FO2,340^GB670,0,14^FS \n" +
                                "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                "^FD%4$s^FS \n" +
                                "^PQ1,0,1,Y " +
                                "^XZ",gl.CodigoBodega, gl.gNomEmpresa,
                        BeProducto.Codigo+" - "+BeProducto.Nombre,
                        "$"+pNumeroLP);

                zPrinterIns.sendCommand(zpl);

                Thread.sleep(500);

                // Close the connection to release resources.
                printerIns.close();

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

            Actualiza_Valores_Despues_Imprimir();

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.msgbox("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
                Actualiza_Valores_Despues_Imprimir();
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }


        }
    }

    private void Imprimir_Barra(){

        try{

                //CM_20210112: Impresión de barras.
                BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);
                printerIns.open();

                if (printerIns.isConnected()){
                    ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);
                    //zPrinterIns.sendCommand("! U1 setvar \"device.languages\" \"zpl\"\r\n");


                    String zpl = String.format("^XA \n" +
                                    "^MMT \n" +
                                    "^PW700 \n" +
                                    "^LL0406 \n" +
                                    "^LS0 \n" +
                                    "^FT171,61^A0I,25,14^FH^FD%1$s^FS \n" +
                                    "^FT550,61^A0I,25,14^FH^FD%2$s^FS \n" +
                                    "^FT670,306^A0I,25,14^FH^FD%3$s^FS \n" +
                                    "^FT292,61^A0I,25,24^FH^FDBodega:^FS \n" +
                                    "^FT670,61^A0I,25,24^FH^FDEmpresa:^FS \n" +
                                    "^FT670,367^A0I,25,24^FH^FDTOMIMS, WMS.  Product Barcode^FS \n" +
                                    "^FO2,340^GB670,0,14^FS \n" +
                                    "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                    "^FD%4$s^FS \n" +
                                    "^PQ1,0,1,Y " +
                                    "^XZ",gl.CodigoBodega, gl.gNomEmpresa,
                            BeProducto.Codigo+" - "+BeProducto.Nombre,
                            (pNumeroLP!="")?"$"+pNumeroLP:BeProducto.Codigo);

                    zPrinterIns.sendCommand(zpl);

                    Thread.sleep(500);

                    // Close the connection to release resources.
                    printerIns.close();

                }else{
                    mu.msgbox("No se pudo obtener conexión con la impresora");
                }

            Actualiza_Valores_Despues_Imprimir();

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.msgbox("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
                Actualiza_Valores_Despues_Imprimir();
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }
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
                //#CKFK 20210322 Corregí el IdPropietarioBodega colocando el de la orden de compra
                BeTransReDet.IdPropietarioBodega =  pIdPropietarioBodega;

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

                    BeTransReDet.Presentacion = bePresentacion;

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
                    BeTransReDet.Nombre_presentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.Nombre).first();
                }else{
                    BeTransReDet.Nombre_presentacion = "";
                }
                BeTransReDet.Nombre_unidad_medida = BeProducto.UnidadMedida.Nombre;
                BeTransReDet.Codigo_Producto = BeProducto.Codigo;

                //#CKFK 20201228 Agregué la funcionalidad de poder determinar si el pallet es o no estandar
                BeTransReDet.Pallet_No_Estandar=(chkPalletNoEstandar.isChecked()?true:false);

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

                if (!BeProducto.Control_vencimiento){
                    BeTransReDet.Fecha_vence = "";
                }else{
                    BeTransReDet.Fecha_vence = du.convierteFecha(cmbVenceRec.getText().toString().trim());
                }

                //Llamado 1
                Continua_Llenando_Detalle_Recepcion_Nueva();

            }


        }catch (Exception e){
            mu.msgbox("Llena_Detalle_Recepcion_Nueva:"+e.getMessage());
        }
    }

    private void Llena_Detalle_Recepcion_Existente(){
        Factor=0;
        TotalLinea=0;
        vCant =0;

        try{

            if (BeProducto!=null){
                BeTransReDet = new clsBeTrans_re_det();
                BeTransReDet = pListTransRecDet.items.get(0);

                //#CKFK 20210322 Corregí el IdPropietarioBodega colocando el de la orden de compra
                BeTransReDet.IdPropietarioBodega =  pIdPropietarioBodega;

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

                //#CKFK 20201228 Agregué la funcionalidad de poder determinar si el pallet es o no estandar
                BeTransReDet.Pallet_No_Estandar=(chkPalletNoEstandar.isChecked()?true:false);

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
                    BeTransReDet.Nombre_presentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).select(c->c.Nombre).first();
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

                if (!BeProducto.Control_vencimiento){
                    BeTransReDet.Fecha_vence = "";
                }else{
                    BeTransReDet.Fecha_vence = du.convierteFecha(cmbVenceRec.getText().toString().trim());
                }

                //Llamado 2
                Continua_Llenando_Detalle_Recepcion_Nueva();

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

            if (!txtCostoReal.getText().toString().isEmpty()) {
                BeTransReDet.Costo = Double.parseDouble(txtCostoReal.getText().toString());
                BeTransReDet.Costo_Oc = Double.parseDouble(txtCostoOC.getText().toString());
                BeTransReDet.Costo_Estadistico = 0;
            }else{
                BeTransReDet.Costo = 0;
                BeTransReDet.Costo_Oc =0;
                BeTransReDet.Costo_Estadistico = 0;
            }

            BeTransReDet.IdOperadorBodega = gl.OperadorBodega.IdOperadorBodega;
            BeTransReDet.Nombre_producto_estado = stream(LProductoEstado.items).where(c->c.IdEstado==IdEstadoSelect).select(c->c.Nombre).first();

            if (!txtCantidadRec.getText().toString().isEmpty()){
                vCant =Double.parseDouble(txtCantidadRec.getText().toString());
            }

            if (gl.gBeRecepcion.Muestra_precio){
                TotalLinea = Double.parseDouble(txtCostoReal.getText().toString()) * vCant;
            }else{
                TotalLinea = vCant;
            }

            //#CKFK 20210412 Llenar tabla de detalle lotes
            clsBeTrans_oc_det_loteList detalle_lotes = new clsBeTrans_oc_det_loteList();
            detalle_lotes=gl.gBeOrdenCompra.DetalleLotes;

            if (detalle_lotes.items.size()>0){

                BeDetalleLotes = stream(detalle_lotes.items)
                        .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                c.Fecha_vence.equals(du.convierteFecha(cmbVence.getSelectedItem().toString())) &&
                                c.Ubicacion.equals(ubiDetLote) &&
                                c.Lote.equals(cmbLote.getSelectedItem().toString()))
                        .first();
                BeDetalleLotes.Cantidad_recibida = Integer.valueOf(txtCantidadRec.getText().toString());
                BeDetalleLotes.IsNew = true;
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
                                BePP.Fecha_Vence = du.convierteFecha(cmbVenceRec.getText().toString().trim());
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
                    return;
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

                            if (BeEstado.IdUbicacionBodegaDefecto>0){
                                BeStockRecNuevaRec = BeStockRec;
                                vCantNuevaRec = vCant;
                                vFactorNuevaRec = Factor;
                                BeStockRecNuevaRec.IdUbicacion =  BeEstado.IdUbicacionBodegaDefecto;
                                BeStockRec.IdUbicacion =  BeEstado.IdUbicacionBodegaDefecto;
                            }else{
                                BeStockRec.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                            }
                            /*if (BeEstado.Danado){
                                BeStockRecNuevaRec = BeStockRec;
                                vCantNuevaRec = vCant;
                                vFactorNuevaRec = Factor;
                                BeStockRecNuevaRec.IdUbicacion =  BeEstado.IdUbicacionBodegaDefecto;
                                BeStockRec.IdUbicacion =  BeEstado.IdUbicacionBodegaDefecto;
                                *//*execws(13); //m_proxy.Get_IdUbicMerma_By_IdBodega(gIdBodega)
                                return;*//*
                            }else{
                                BeStockRec.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;//CInt(txtIdUbicacion.Text.Trim)
                            }*/

                        }

                    }

                    Continua_Guardando_Rec_Nueva(BeStockRec,Factor,vCant);

                    if (gl.mode==1){
                        pListTransRecDet.items.add(BeTransReDet);
                    }
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

                if (!(BeStockRec.Presentacion.EsPallet||chkPaletizar.isChecked()) && BeStockRec.Lic_plate.isEmpty()){
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
                //#EJC20201217:Si es UMBA y el LicPlate no es vacío asignar.
                if (!txtBarra.getText().toString().isEmpty()){
                    BeStockRec.Lic_plate= txtBarra.getText().toString();
                }else{
                    if (!pNumeroLP.equals("") && !pNumeroLP.isEmpty()){
                        txtBarra.setText(pNumeroLP);
                        BeStockRec.Lic_plate= txtBarra.getText().toString();
                    }
                }
            }

            if (BeProducto.Control_vencimiento){
                BeStockRec.Fecha_vence = du.convierteFecha(cmbVenceRec.getText().toString().trim());
            }else{
                BeStockRec.Fecha_vence = "";
            }

            BeStockRec.ProductoValidado = true;
            BeStockRec.No_linea = pLineaOC;

            //#CKFK 20201228 Agregué la funcionalidad de poder determinar si el pallet es o no estandar
            BeStockRec.Pallet_No_Estandar = (chkPalletNoEstandar.isChecked()?true:false);

            if (Escaneo_Pallet){
                BeStockRec.Lic_plate = BeINavBarraPallet.Codigo_barra;
                BeStockRec.Uds_lic_plate = BeINavBarraPallet.Cantidad_UMP;
                BeStockRec.No_bulto = 0;
                BeTransReDet.Lic_plate = BeINavBarraPallet.Codigo_barra;
            }else{
                if (BeTransReDet.Lic_plate!=null){
                    if (BeStockRec!=null){
                        if(BeStockRec.Lic_plate==""){
                            if (!txtBarra.getText().toString().isEmpty()){
                                BeStockRec.Lic_plate= txtBarra.getText().toString();
                            }else{
                                if (!pNumeroLP.equals("") && !pNumeroLP.isEmpty()){
                                    txtBarra.setText(pNumeroLP);
                                    BeStockRec.Lic_plate= txtBarra.getText().toString();
                                }
                            }
                        }
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
                            toastlong("La cantidad de pallets es > 1 y genera_lp_auto es Falso, debe recibir los pallets de forma unitaria (Cantidad = 1)");
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
                        if(BeStockRec.Lic_plate==""){
                            if (!txtBarra.getText().toString().isEmpty()){
                                BeStockRec.Lic_plate= txtBarra.getText().toString();
                            }else{
                                if (!pNumeroLP.equals("") && !pNumeroLP.isEmpty()){
                                    txtBarra.setText(pNumeroLP);
                                    BeStockRec.Lic_plate= txtBarra.getText().toString();
                                }
                            }
                        }
                        BeProdPallet.Codigo_Barra = BeStockRec.Lic_plate;
                        BeProdPallet.Codigo_Producto = BeProducto.Codigo;
                        BeProdPallet.Reimpresiones = 0;
                        BeProdPallet.Cantidad = BeStockRec.Cantidad;
                        BeProdPallet.Fecha_Vence = BeStockRec.Fecha_vence;
                        BeProdPallet.Fec_agr =String.valueOf(du.getFechaActual());
                        BeProdPallet.Fec_mod = String.valueOf(du.getFechaActual());
                        BeProdPallet.Lote = BeStockRec.Lote;
                        BeProdPallet.User_agr = gl.OperadorBodega.IdOperadorBodega+"";
                        BeProdPallet.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                        if (gl.mode==1){
                            BeProdPallet.IsNew = true;
                        }else{
                            BeProdPallet.IsNew = false;
                        }

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
                BeProdPallet.Codigo_Producto = BeProducto.Codigo;
                BeProdPallet.Reimpresiones = 0;
                BeProdPallet.Cantidad = vBeStockRec.Cantidad;
                if (BeProducto.Control_vencimiento){
                    BeProdPallet.Fecha_Vence = vBeStockRec.Fecha_vence;
                }else{
                    BeProdPallet.Fecha_Vence = "";
                }
                BeProdPallet.Fec_agr = String.valueOf(du.getFechaActual());
                BeProdPallet.Fec_mod = String.valueOf(du.getFechaActual());
                BeProdPallet.Lote = vBeStockRec.Lote;
                BeProdPallet.User_agr = gl.OperadorBodega+"";
                BeProdPallet.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                if (gl.mode==1){
                    BeProdPallet.IsNew = true;
                }else{
                    BeProdPallet.IsNew = false;
                }

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

            if (txtCostoReal.getText().toString().isEmpty()||txtCostoReal.getText().toString().equals("0")||txtCostoReal.getText().toString().equals("0.0")){

                    if (IdPreseSelect>0){
                        txtCostoReal.setText(Get_Costo_Presentacion()+"");
                    }

                    if (txtCostoReal.getText().toString().equals("0")||txtCostoReal.getText().toString().equals("0.0")){
                        txtCostoReal.setText(txtCostoOC.getText().toString());
                    }

                }else if(Double.parseDouble(txtCostoReal.getText().toString())<=0){
                    mu.msgbox("El costo debe ser mayor a 0");
                    return;
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

            String FechaVence=du.convierteFecha(cmbVenceRec.getText().toString().trim());

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

            progress.setMessage("Validando cantidad");

            if (gl.TipoOpcion==2){
                return true;
            }

            if (txtCantidadRec.getText().toString().isEmpty()){
                Cantidad =0;
            }else{
                Cantidad = Double.parseDouble(txtCantidadRec.getText().toString());
            }

            if (gl.mode==2){
                Cant_Pendiente =mu.round(Cant_Pendiente + Cant_Recibida_Anterior,gl.gCantDecCalculo);
            }

            if (Cant_Pendiente > Cantidad){
                msgValidaCantidad("La cantidad "+Cantidad+" ingresada es correcta para el producto "+BeProducto.Codigo);
                return  true;
            }else if(Cant_Pendiente < Cantidad){
                 msgExcedeCantidad("Excede la cantidad solicitada. ¿Recibir de todas formas esta cantidad?");
                 return true;
            }else if (BeProducto.Control_vencimiento){
                valida_fecha_vencimiento();
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
            mu.msgbox("msgValidaProductoPallet"+e.getMessage());
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
                    DespuesDeValidarCantidad();
                    //Continua_Llenando_Detalle_Recepcion_Nueva();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    progress.cancel();
                    cmbVenceRec.requestFocus();
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaFechaVence"+e.getMessage());
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
                    //DespuesDeValidarCantidad();
                    valida_fecha_vencimiento();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    progress.cancel();
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
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
                    valida_fecha_vencimiento();
                    //DespuesDeValidarCantidad();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    progress.cancel();
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaCantidad"+e.getMessage());

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
                    case 2:
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodegaHH",
                                "pIdPropietario",gl.IdPropietario,
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
                        progress.setMessage("Procesando recepción");
                        //Guardar_Recepcion_Nueva
                        callMethod("Guardar_Recepcion","pRecEnc",gl.gBeRecepcion,
                                "pRecOrdenCompra",gl.gBeRecepcion.OrdenCompraRec,
                                "pListStockRecSer",pListBeStockSeRec.items,
                                "pListStockRec",pListBeStockRec.items,
                                "pListProductoPallet",listaProdPalletsNuevos.items,
                                "pLotesRec", BeDetalleLotes,
                                "pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.IdOperador);
                        break;
                    case 17 :
                        //Guardar_Recepcion_Edita
                        callMethod("GuardarRecepcionModif","pRecEnc",gl.gBeRecepcion,
                                "pRecOrdenCompra",gl.gBeRecepcion.OrdenCompraRec,
                                "pListStockRecSer",pListBeStockSeRec.items,
                                "pListStockRec",pListBeStockRec.items,
                                "pListProductoPallet",listaProdPalletsNuevos.items,
                                "pbeStockAnt",gBeStockAnt,
                                "pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.IdOperador);
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
                    case 23:
                        callMethod("Get_Stock_By_IdRecepcionEnc_And_IdRecpecionDet","pIdRecepcionEnc",gl.gIdRecepcionEnc,"pIdRecepcionDet",pIdRecepcionDet);
                        break;
                    case 24:
                        callMethod("Existe_Lp","pLic_Plate",pLp);
                        break;
                    case 25:
                        callMethod("Push_Recepcion_To_NAV_For_BYB2",
                                   "DocumentoUbicacion", ubiDetLote,
                                   "CodigoProducto",BeProducto.Codigo,
                                   "Cantidad", txtCantidadRec.getText().toString());
                        break;
                }

            }catch (Exception e){
                mu.msgbox(e.getClass()+"WebServiceHandler:"+e.getMessage());
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
                case 23:
                    processGetStock();
                    break;
                case 24:
                    processExisteLp();
                    break;
                case 25:
                    process_Recepcion_To_Nav();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processBeProducto(){

        try {

            progress.setMessage("Obteniendo valores de producto");

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            Load();

        } catch (Exception e) {
            progress.cancel();
            msgbox(" processBeProducto: " + e.getMessage());
        }

    }

    private void processListarProductoEstado(){

        try{

            progress.setMessage("Obteniendo estados de producto");

            LProductoEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodegaHH");

            if (LProductoEstado!=null){

                Listar_Producto_Estado();

                execws(4);
            }else {
                progress.cancel();
                mu.msgbox("Estados de producto no están definidos para el propietario");
            }


        }catch (Exception e){
            mu.msgbox("processListarProductoEstado:"+e.getMessage());
        }

    }

    private void processListPresentaciones(){

        try{

            BeProducto.Presentaciones = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            Listar_Producto_Presentaciones();

            execws(4);

        }catch (Exception e){
            mu.msgbox("processListPresentaciones:"+e.getMessage());
        }

    }

    private void processCodigosBarra(){

        try{

            BeProducto.Codigos_Barra = xobj.getresult(clsBeProducto_codigos_barraList.class,"Get_All_Codigos_Barra_By_IdProducto");

            Listar_Producto_Presentaciones();

            if (gl.mode==1){
                LlenaDatosFaltantes();
            }else{
                LlenaDatosFaltantes_Existente();
            }


        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processCodigosBarra:s"+e.getMessage());
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

            if (stream(gl.gpListDetalleOC.items).where(c -> c.IdProductoBodega == pIdProductoBodega
                    && c.IdOrdenCompraDet == pIdOrdenCompraDet).select(c -> c.IdPresentacion).first()>0){
                if (BeProducto.Presentaciones!=null){
                    if (BeProducto.Presentaciones.items!=null){
                        PTiene_Pres=true;
                    }
                }
            }

            if(Pperzonalizados||PCap_Manu||PCap_Anada||PGenera_lp||PTiene_Ctrl_Peso||PTiene_Ctrl_Temp||PTiene_PorSeries||PTiene_Pres){
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

            if (gl.mode==1){
                //#CKFK 20201229 Agregué esta condición de que si la barra tiene información se coloca eso como LP
                if (!txtBarra.getText().toString().isEmpty()){
                    txtLicPlate.setText(txtBarra.getText().toString().replace("$",""));
                }else{
                    if (txtLicPlate != null){
                        txtLicPlate.setText(pNumeroLP);
                    }else{
                        txtBarra.setText(pNumeroLP);
                    }
                }
            }

        }catch (Exception e){
            mu.msgbox("processNuevoLP: "+e.getMessage());
        }

    }

    private void processLicensePallet(){

        try {

            pListBeLicensePlate = xobj.getresult(clsBeLicensePlatesList.class,"Get_Licenses_Plates_By_IdRecepcionEnc");

            pNumeroLP = "";

            if (pListBeLicensePlate.items!=null){

                for (int i = pListBeLicensePlate.items.size()-1; i>=0; i--) {
                    if(pListBeLicensePlate.items.get(i).CantidadDisponible>=Cant_Recibida_Actual){
                        if (!Existe_Lp){
                            pNumeroLP = pListBeLicensePlate.items.get(i).LicensePlates;
                        }else{
                            pNumeroLP = pLp;
                        }

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
            mu.msgbox("processListDetParametros"+e.getMessage());
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
            mu.msgbox("processAuxRec:"+e.getMessage());
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

            if (!txtBarra.getText().toString().isEmpty()){
                vBeStockRec.Lic_plate = txtBarra.getText().toString();
            }else {
                vBeStockRec.Lic_plate = xobj.getresult(String.class, "Get_Nuevo_Correlativo_LicensePlate_S");
            }

            Terminar_Guardar_Detalle_Recepcion_Nueva();

        }catch (Exception e){
            mu.msgbox("processNuevoLPS: "+e.getMessage());
        }

    }

    private void processGuardarRecNueva(){

        try{

            String Resultado="";

            progress.show();
            progress.setMessage("Finalizando proceso de guardar recepción");

            //#EJC20210321_1223:Validar si no se obtuvo error en el procesamiento.
            if(!xobj.ws.xmlresult.contains("CustomError")){

                Resultado = xobj.getresult(String.class,"Guardar_Recepcion");

                if (!Resultado.isEmpty()){

                    if (!ubiDetLote.isEmpty()){
                        execws(25);
                    }else{
                        Imprime_Barra_Despues_Guardar();
                    }

                }else{
                    progress.cancel();
                    mu.msgbox("No se pudo guardar la recepción");
                    return;
                }

            }else{
                progress.cancel();
                mu.msgbox("No se pudo guardar la recepción:  " + ws.xmlresult);
            }

        }catch (Exception e){
            progress.cancel();
        mu.msgbox("processGuardarRecNueva:"+e.getMessage());
        }
    }

    private void processGuardarRecModif(){

        try{

            String Resultado="";

            progress.show();
            progress.setMessage("Finalizando proceso de guardar recepción");

            Resultado = xobj.getresult(String.class,"GuardarRecepcionModif");

            if (!Resultado.isEmpty()){
                Imprime_Barra_Despues_Guardar();
            }else{
                progress.cancel();
                mu.msgbox("No se pudo guardar la recepción");
                return;
            }


        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processGuardarRecModif:"+e.getMessage());
        }

    }

    private void processActualizaCantidadRecibida(){

        try {

            boolean GetDetalle = false;
            int vIndex = -1;

            progress.setMessage("Actualizando cantidad recibida");
            progress.show();

            GetDetalle = xobj.getresult(Boolean.class, "Get_Detalle_OC_By_IdOrdeCompraDet");

            List AuxList = stream(gl.gpListDetalleOC.items).select(c -> c.IdOrdenCompraDet).toList();

            if (AuxList.size()>0){

                vIndex = AuxList.indexOf(beTransOCDet.IdOrdenCompraDet);

                gl.gpListDetalleOC.items.get(vIndex).Cantidad_recibida += BeTransReDet.cantidad_recibida;

            }

            gl.CantRec = beTransOCDet.Cantidad_recibida;

            gl.Carga_Producto_x_Pallet=false;

            doExit();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processActualizaCantidadRecibida"+e.getMessage());
        }

        progress.cancel();
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

    public void BotonMostrar(View view){
        BotonIrGuardarParametros();
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
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskExisteLp(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (BeProducto.Control_vencimiento){
                        cmbVenceRec.setSelectAllOnFocus(true);
                        cmbVenceRec.requestFocus();
                    }else if (BeProducto.Control_lote){
                        txtLoteRec.setSelectAllOnFocus(true);
                        txtLoteRec.requestFocus();
                    }else if (!BeProducto.Control_lote&&!BeProducto.Control_vencimiento){
                        txtCantidadRec.requestFocus();
                    }
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    txtBarra.setText("");
                    txtBarra.setSelectAllOnFocus(true);
                    txtBarra.requestFocus();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

//    private void msgAskImprimir(String msg) {
//        try{
//            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//
//            dialog.setTitle(R.string.app_name);
//            dialog.setMessage("¿" + msg + "?");
//
//            dialog.setCancelable(false);
//
//            dialog.setIcon(R.drawable.ic_quest);
//
//            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//
//            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {                }
//            });
//
//            dialog.show();
//
//        }catch (Exception e){
//            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
//        }
//
//    }

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
                Cant_Pendiente =mu.round( Cant_A_Recibir - Cant_Recibida,gl.gCantDecCalculo);
            }

            FinalizCargaProductos();

        }catch (Exception e){
            mu.msgbox("processCantRecConOC"+e.getMessage());
        }
    }

    private void processGetStock(){

        try{

            pListBeStockRec = xobj.getresult(clsBeStock_recList.class,"Get_Stock_By_IdRecepcionEnc_And_IdRecpecionDet");

            execws(2);

        }catch (Exception e){
            mu.msgbox("processGetStock:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void processExisteLp(){

        try{

            Existe_Lp = xobj.getresult(Boolean.class,"Existe_Lp");

            if (Existe_Lp){
                msgAskExisteLp("El Lp:"+pLp+" ya existe, desea agregarlo al producto:"+BeProducto.Codigo);
            }else{
                if (BeProducto.Control_vencimiento){
                    cmbVenceRec.setSelectAllOnFocus(true);
                    cmbVenceRec.requestFocus();
                }else if (BeProducto.Control_lote){
                    txtLoteRec.setSelectAllOnFocus(true);
                    txtLoteRec.requestFocus();
                }else if (!BeProducto.Control_lote&&!BeProducto.Control_vencimiento){
                    txtCantidadRec.requestFocus();
                }
            }

        }catch (Exception e){
            mu.msgbox("processExisteLp:"+e.getMessage());
        }
    }

    private void process_Recepcion_To_Nav(){

        try{

            boolean procesada = xobj.getresult(Boolean.class,"Push_Recepcion_To_NAV_For_BYB2");

            if (procesada){
                toastlong("Recepción procesada en ByB");
                Imprime_Barra_Despues_Guardar();
            }else{
                Imprime_Barra_Despues_Guardar();
            }

        }catch (Exception e){
            mu.msgbox("process_Recepcion_To_Nav:"+e.getMessage());
        }
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