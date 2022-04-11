package com.dts.tom.Transacciones.Recepcion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.base.DecimalDigitsInputFilter;
import com.dts.base.ExDialog;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_pallet;
import com.dts.classes.Mantenimientos.CustomError.clsBeCustomError;
import com.dts.classes.Mantenimientos.Motivo_devolucion.clsBeMotivo_devolucion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_codigos_barra.clsBeProducto_codigos_barraList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.Producto_imagen.clsBeProducto_imagen;
import com.dts.classes.Mantenimientos.Producto.Producto_imagen.clsBeProducto_imagenList;
import com.dts.classes.Mantenimientos.Producto.Producto_pallet.clsBeProducto_pallet;
import com.dts.classes.Mantenimientos.Producto.Producto_pallet.clsBeProducto_palletList;
import com.dts.classes.Mantenimientos.Producto.Producto_parametros.clsBeProducto_parametros;
import com.dts.classes.Mantenimientos.Producto.Producto_parametros.clsBeProducto_parametrosList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Mantenimientos.TipoEtiqueta.clsBeTipo_etiqueta;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote.clsBeTrans_oc_det_lote;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote.clsBeTrans_oc_det_loteList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti.clsDataContractDI;
import com.dts.classes.Transacciones.Recepcion.LicencePlates.clsBeLicensePlatesList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_det;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros.clsBeTrans_re_det_parametros;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros.clsBeTrans_re_det_parametrosList;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Parametros.clsBeStock_parametro;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_recList;
import com.dts.classes.Transacciones.Stock.Stock_se_rec.clsBeStock_se_rec;
import com.dts.classes.Transacciones.Stock.Stock_se_rec.clsBeStock_se_recList;
import com.dts.classes.clsBeImagen;
import com.dts.tom.Mainmenu;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.CambioUbicacion.frm_cambio_ubicacion_ciega;
import com.dts.tom.Transacciones.CambioUbicacion.frm_tareas_cambio_ubicacion;
import com.dts.tom.Transacciones.ProcesaImagen.frm_imagenes;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Recepcion.frm_list_rec_prod.EsTransferenciaInternaWMS;
import static com.dts.tom.Transacciones.Recepcion.frm_list_rec_prod.gBeStockRec;

import androidx.core.content.FileProvider;

//import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaBase;

public class frm_recepcion_datos extends PBase {

    Calendar calendario = Calendar.getInstance();

    private Spinner cmbEstadoProductoRec,cmbPresRec, cmbVence, cmbLote;
    private EditText txtNoLP,txtLoteRec,txtUmbasRec,txtCantidadRec,txtPeso,txtPesoUnitario,txtCostoReal,txtCostoOC,cmbVenceRec;
    private TextView lblDatosProd,lblPropPrd,lblPeso,lblPUn,lblCosto,lblCReal,lblPres,lblLote,lblVence, lblEstiba, lblUbicacion,lblParametrosA,lblSerieTit,lblsinPresentacion,lblPresentacion;
    private TextView lblFecIngreso;
    private Button btnCantPendiente;
    private Button btnCantRecibida;
    private ProgressDialog progress;
    private DatePicker dpResult;
    private ImageView imgDate, imgDate_p, cmdImprimir;
    private CheckBox chkPaletizar, chkPalletNoEstandar, chkEstiba;
    private TableRow tblEstiba;
    private TableRow tbLPeso;
    private TableRow tblVence;
    private TableRow tblUbicacion;
    private CheckBox chkPresentacion;
    private Dialog dialog;

    private boolean imprimirDesdeBoton=false;

    private TextView lblSerialP;
    private TextView lblPesoTit;
    private TextView lblTempTit;
    private TextView lblTempEsta;
    private TextView lblTempReal;
    private TextView lblPresParam;
    private TextView lblPesoEsta;
    private TextView lblPesoReal;
    private TextView lblSerialIni;
    private TextView lblSerialFin;
    private EditText txtLicPlate;
    private EditText txtSerial;
    private EditText txtAnada;
    private EditText txtFechaManu;
    private EditText txtTempEsta;
    private EditText txtTempReal;
    private EditText txtPesoEsta;
    private EditText txtPesoReal;
    private EditText txtSerieIni;
    private EditText txtSerieFin;
    private Spinner cmbPresParams;
    private Spinner cmbCantidad;
    private RelativeLayout relOpciones;
    int pIndexStock=-1;
    double Cant_Recibida_Actual=0;

    private WebServiceHandler ws;
    private XMLObject xobj;
    private clsDataContractDI dataContractDI;

    private boolean Mostro_Propiedades,Escaneo_Pallet;
    private boolean mostrar_parametros_producto;
    private boolean Mostrar_Propiedades_Parametros = false;
    private double Cant_Recibida_Anterior = 0,Cant_Recibida,Cant_A_Recibir,Cant_Pendiente;
    private int pIdOrdenCompraDet,pIdOrdenCompraEnc,pLineaOC,pIdRecepcionDet,pIdProductoBodega;
    private int IdEstadoSelect,IdPreseSelect=-1,IdPreseSelectParam=-1;     
    private String pNumeroLP = "";
    private Integer CantCopias =1;

    private boolean PallCorrecto= false;
    private int pIndexProdPallet=-1;
    private int pIndexParam=-1;
    private int IndexPresSelected=-1;
    private String MensajeParam="";
    private int pIndiceListaStock = -1;
    private double CostoOC=0;
    private int vPresentacion;
    private String vLote;
    private String pLp="";
    private String pSerie="";
    private boolean Existe_Lp=false;
    private boolean Existe_Serie=false;
    private String ubiDetLote="";
    private boolean guardando_recepcion = false;

    private clsBeTrans_oc_det BeOcDet;
    private clsBeProducto_parametrosList pListBEProductoParametro = new clsBeProducto_parametrosList();
    private clsBeTrans_re_det_parametrosList plistBeReDetParametros  = new clsBeTrans_re_det_parametrosList();
    private clsBeStock_se_recList pListBeStockSeRec = new clsBeStock_se_recList();
    private clsBeStock_recList pListBeStockRec = new clsBeStock_recList();
    private clsBeProducto_palletList pListBeProductoPallet = new clsBeProducto_palletList();
    private clsBeTrans_re_detList pListTransRecDet = new clsBeTrans_re_detList();
    private clsBeI_nav_barras_pallet BeINavBarraPallet = new clsBeI_nav_barras_pallet();
    private clsBeTrans_re_det BeTransReDet= new clsBeTrans_re_det();
    private clsBeTrans_oc_det_lote BeDetalleLotes  = new clsBeTrans_oc_det_lote();
    private clsBeStock_rec BeStockRecNuevaRec = new clsBeStock_rec();
    private clsBeStock_recList listaStockPalletsNuevos = new clsBeStock_recList();
    private clsBeProducto_palletList listaProdPalletsNuevos = new clsBeProducto_palletList();
    private clsBeStock_rec vBeStockRec = new clsBeStock_rec();
    private clsBeStock_rec vBeStockRecPallet = new clsBeStock_rec();
    private clsBeProducto_pallet BeProdPallet  = new clsBeProducto_pallet();
    private clsBeStock gBeStockAnt;
    private clsBeTrans_oc_det beTransOCDet =new clsBeTrans_oc_det();
    private  clsBeStock_rec ObjS = new clsBeStock_rec();
    private  clsBeStock_se_rec ObjNS =new clsBeStock_se_rec();
    private clsBeTipo_etiqueta pBeTipo_etiqueta;
    boolean Pperzonalizados=false,PCap_Manu=false,PCap_Anada=false,PGenera_lp=false,PTiene_Ctrl_Peso=false,PTiene_Ctrl_Temp=false,PTiene_PorSeries=false,PTiene_Pres=false;

    private clsBeResolucion_lp_operador BeResolucion = new clsBeResolucion_lp_operador();

    /***** parametros personalizados *****************************************/
    Integer contar_parametros_p = 0;
    TextView lblDescripcion_parametro ;
    TextView lbltipo_numerica         ;
    TextView lbltipo_texto            ;
    TextView lbltipo_logica           ;
    TextView lbltipo_fecha            ;

    /******** tipos de valores del parametro personalizado *******************/
    EditText txtvalor_n ;
    EditText txtvalor_t ;
    EditText txtvalor_f ;
    CheckBox cb_valor_b ;

    private clsBeStock_parametro ObjStock_parametro;
    Integer IdProductoParametro;
    String tipo_parametro;
    private String pRespuesta="";

    /******* respuesta al validar si el parametro personalizado esta lleno o no, pero  no aplica para boolean ****/
    Boolean parametro_personalizado_valido;

    private int pIdPropietarioBodega=0;

    double vFactorNuevaRec=0;
    double vCantNuevaRec=0;
    double vCantAnteriorRec=0;

    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto_estadoList LProductoEstado = new clsBeProducto_estadoList();

    private final ArrayList<String> EstadoList= new ArrayList<>();
    private final ArrayList<String> PresList= new ArrayList<>();
    private final ArrayList<String> VenceList= new ArrayList<>();
    private final ArrayList<String> LotesList = new ArrayList<>();
    private final ArrayList<String> UbicLotesList = new ArrayList<>();

    // date
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    double CajasPorCama = 0;
    double CamasPorTarima = 0;

    private String MensajeAdicionalParaImpresion="";

    /**Variables para las cantidad por lotes de ubicación de las órdenes de producción**/
    private double CantRec=0;
    private double CantTotal =0;
    private double DifCantUbic =0;

    //Imagen
    private String encoded="";
    private clsBeImagen BeImagen;
    private clsBeProducto_imagen BeProductoImagen = new clsBeProducto_imagen();
    private clsBeProducto_imagenList BeListProductoImagen  =  new clsBeProducto_imagenList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_recepcion_datos);

        super.InitBase();

        ws = new WebServiceHandler(frm_recepcion_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);
        dataContractDI = new clsDataContractDI();

        cmbEstadoProductoRec = findViewById(R.id.cmbEstadoProductoRec);
        cmbPresRec = findViewById(R.id.cmbPresRec);
        cmbVence = findViewById(R.id.cmbVence);
        cmbLote = findViewById(R.id.cmbLote);
        cmbVenceRec = findViewById(R.id.cmbVenceRec);

        txtNoLP = findViewById(R.id.txtLP);
        txtLoteRec = findViewById(R.id.txtLoteRec);
        txtUmbasRec = findViewById(R.id.txtUmbasRec);
        txtCantidadRec = findViewById(R.id.txtCantidadRec);
        txtPeso= findViewById(R.id.txtPeso);
        txtPesoUnitario = findViewById(R.id.txtPesoUnitario);
        txtCostoReal = findViewById(R.id.txtCostoReal);
        txtCostoOC = findViewById(R.id.txtCostoOC);

        lblDatosProd = findViewById(R.id.lblTituloForma);
        lblPropPrd = findViewById(R.id.lblPropPrd);
        lblPeso = findViewById(R.id.textView87);
        lblPUn = findViewById(R.id.textView86);
        lblCReal = findViewById(R.id.textView89);
        lblCosto = findViewById(R.id.textView88);
        lblVence = findViewById(R.id.textView81);
        lblLote = findViewById(R.id.textView82);
        lblPres = findViewById(R.id.textView83);
        lblEstiba = findViewById(R.id.lblEstiba);
        lblUbicacion = findViewById(R.id.lblUbicacion);
        relOpciones = findViewById(R.id.relOpciones);
        chkPresentacion = findViewById(R.id.conPresentacion);
        lblPresentacion = findViewById(R.id.textView83);
        lblsinPresentacion = findViewById(R.id.lblsinPresentacion);

        btnCantRecibida = findViewById(R.id.btnCantRecibida);
        btnCantPendiente = findViewById(R.id.btnCantPendiente);

        dpResult = findViewById(R.id.datePicker);

        imgDate = findViewById(R.id.imgDate);
        cmdImprimir = findViewById(R.id.cmdImprimir);

        chkPaletizar = findViewById(R.id.chkPaletizar);
        chkPalletNoEstandar = findViewById(R.id.chkPalletNoEstandar);
        chkEstiba = findViewById(R.id.chkEstiba);

        findViewById(R.id.btnBack);
        findViewById(R.id.btnIr);

        tblEstiba  = findViewById(R.id.tblEstiba);
        tbLPeso  = findViewById(R.id.tbLPeso);
        tblVence  = findViewById(R.id.tblVence);
        tblUbicacion = findViewById(R.id.tblUbicacion);

        tblEstiba.setVisibility(View.GONE);
        tblUbicacion.setVisibility(View.GONE);
        chkPaletizar.setVisibility(View.GONE);
        chkEstiba.setVisibility(View.GONE);

        if (gl.gCapturaPalletNoEstandar){
            chkPalletNoEstandar.setVisibility(View.VISIBLE);
        }else{
            chkPalletNoEstandar.setVisibility(View.GONE);
        }

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

        if (gl.gBeRecepcion.Tomar_fotos) {
            relOpciones.setVisibility(View.VISIBLE);
        }else{
            relOpciones.setVisibility(View.INVISIBLE);
        }

        //#EJC20220325: Ocultar boton de imprimir para CEALSA.
        //Solicitud de Axel.
        if (gl.Mostrar_Area_En_HH) {
            cmdImprimir.setVisibility(View.INVISIBLE);
        }else{
            cmdImprimir.setVisibility(View.VISIBLE);
        }

        pBeTipo_etiqueta = new clsBeTipo_etiqueta();

        if (gl.bloquear_lp_hh) {
            txtNoLP.setEnabled(false);
            txtCantidadRec.requestFocus();
        } else{
            txtNoLP.setEnabled(true);
            txtNoLP.requestFocus();
        }

        if(!gl.Escaneo_Pallet){
            execws(1);
        }else{
            Load();
        }

        txtCantidadRec.requestFocus();

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
        if (id == DATE_DIALOG_ID) {// set date picker as current date
            return new DatePickerDialog(this, datePickerListener,
                    year, month, day);
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
                .append(day).append("/").append(month).append("/")
                .append(year).append(""));

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

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

                    if(gl.gCapturaEstibaIngreso) {
                        if (CajasPorCama==0 && CamasPorTarima==0){
                            chkEstiba.setVisibility(View.VISIBLE);
                        }else{
                            chkEstiba.setVisibility(View.GONE);
                        }
                    }else{
                        chkEstiba.setVisibility(View.GONE);
                    }

                    BeProducto.Presentacion = BeProducto.Presentaciones.items.get(position);

                    if (BeProducto.Presentacion != null){

                        if (BeProducto.Presentacion.Genera_lp_auto) {
                            progress.setMessage("Buscando License Plate");
                            progress.show();

                            //String valores = gl.IdOperador +"-"+ gl.IdBodega;
                            //toastlong("GT: cmb_pres resolución LP " + valores);

                            toastlong("nueva LP P1 ");
                            execws(6);
                            progress.cancel();
                        }else{
                            toastlong("La presentación no genera lp Auto..");
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }


            });
            txtNoLP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtNoLP.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    guardando_recepcion=false;

                    if (BeProducto.Control_vencimiento){
                        cmbVenceRec.setSelectAllOnFocus(true);
                        cmbVenceRec.requestFocus();
                    }else if (BeProducto.Control_lote){
                        txtLoteRec.setSelectAllOnFocus(true);
                        txtLoteRec.requestFocus();
                    }else {
                       txtCantidadRec.requestFocus();
                    }

                    Procesa_Barra_Producto();

                }

                return false;
            });

            txtCantidadRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            txtCantidadRec.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    if (tbLPeso.getVisibility()==View.VISIBLE){
                        txtPeso.requestFocus();
                    }else{
                        ValidaCampos();
                    }
                }

                return false;
            });

            txtPeso.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    double pesoTotal = Double.parseDouble(txtPeso.getText().toString());
                    double cant = Double.parseDouble(txtCantidadRec.getText().toString());
                    if (cant>0){
                        double pesoUni = mu.round(pesoTotal/cant,gl.gCantDecCalculo);
                        txtPesoUnitario.setText(String.valueOf(pesoUni));
                    }
                    ValidaCampos();
                }

                return false;
            });

            cmbVenceRec .addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

                @Override
                public void afterTextChanged(Editable s) {}


            });

            cmbVenceRec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String valor= cmbVenceRec .getText().toString();
                        //#AT 20211123 Se ajusta el formato a la fecha
                        String fecha_ajustada =  du.convierteFechaSinHora(valor);

                        try{

                            if (!du.EsFecha(fecha_ajustada)){
                                msgbox("No es una fecha válida, se colocará la fecha actual");
                                cmbVenceRec.setText(du.getActDateStr());
                            }

                            // du.EsFecha(valor);
                        }catch(Exception e){
                            cmbVenceRec.setText(du.getActDateStr());
                        }
                    }

                }
            });

            //#EJC20210612FOCUS:Solo lo hacía en el enter
            txtNoLP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String valor= txtNoLP.getText().toString();
                        try{
                            //Procesa_Barra_Producto();
                        }catch(Exception e){
                            cmbVenceRec.setText(du.getActDateStr());
                        }
                    }

                }
            });

            cmdImprimir.setOnClickListener(v -> {
                imprimirDesdeBoton=true;
                Imprimir(v);
            });


            chkEstiba.setOnClickListener(view -> {
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
                }

            });

            chkPresentacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (!isChecked) {
                        lblPresentacion.setVisibility(View.GONE);
                        cmbPresRec.setVisibility(View.GONE);
                        lblEstiba.setVisibility(View.GONE);

                        lblsinPresentacion.setVisibility(View.VISIBLE);
                        lblsinPresentacion.setText("Recibir  el producto sin presentación");
                        lblsinPresentacion.setTextColor(Color.RED);
                        lblsinPresentacion.setTypeface(lblsinPresentacion.getTypeface(), Typeface.BOLD);
                    } else {
                        lblPresentacion.setVisibility(View.VISIBLE);
                        cmbPresRec.setVisibility(View.VISIBLE);
                        lblEstiba.setVisibility(View.VISIBLE);

                        lblsinPresentacion.setVisibility(View.GONE);
                    }

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

            if (!txtCantidadRec.getText().toString().isEmpty() && !txtCantidadRec.getText().toString().equals("")){

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
                    double pesoTotal = Double.parseDouble(txtPeso.getText().toString());
                    double cant = Double.parseDouble(txtCantidadRec.getText().toString());
                    if (cant>0){
                        double pesoUni = mu.round(pesoTotal/cant,gl.gCantDecCalculo);
                        txtPesoUnitario.setText(String.valueOf(pesoUni));

                        //GT15022022: si el peso bruto al dividirse entre la cantidad es 0 o menor, entonces alertar
                        if (txtPesoUnitario.getText().toString().isEmpty() || Double.parseDouble(txtPesoUnitario.getText().toString())<=0 ){
                            mu.msgbox("el peso unitario no puede ser 0, valide el peso");
                            txtPesoUnitario.requestFocus();
                            return false;
                        }

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

        return true;
    }

    public void msgboxValidaLote(String msg) {

        try{

            if (!(msg.isEmpty())){

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setTitle(R.string.app_name);
                dialog.setMessage(msg);
                dialog.setCancelable(false);

                dialog.setNeutralButton("OK", (dialog1, which) -> {
                    txtLoteRec.requestFocus();
                    txtLoteRec.selectAll();
                    txtLoteRec.setSelectAllOnFocus(true);
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

                dialog.setNeutralButton("OK", (dialog1, which) -> {
                    cmbVenceRec.requestFocus();
                    cmbVenceRec.selectAll();
                    cmbVenceRec.setSelectAllOnFocus(true);
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

                dialog.setNeutralButton("OK", (dialog1, which) -> {
                    txtPeso.requestFocus();
                    txtPeso.selectAll();
                    txtPeso.setSelectAllOnFocus(true);
                });
                dialog.show();

            }

        } catch (Exception ex) {
        }
    }

    @SuppressLint("SetTextI18n")
    private void Muestra_Propiedades_Producto(){

        int vIndiceParam = -1;
        double vCant;
        mostrar_parametros_producto = false;

        try{


            if (plistBeReDetParametros.items!=null){
                List AuxDetParams = stream(plistBeReDetParametros.items).select(c->c.IdParametroDet).toList();
                vIndiceParam = AuxDetParams.indexOf(pIdRecepcionDet);
            }

            pIndexParam = vIndiceParam;

            if (Mostro_Propiedades){
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
            //#GT 18022021: Valida los parametros no personalizados, Personalizados ya fueron validados con Pperdonalizados
            mostrar_parametros_producto = existen_parametros_para_mostrar();

            if (mostrar_parametros_producto) {
                MuestraParametros1(this );
            }else if (Pperzonalizados){
                MuestraParametros2(this );
            }else {
                Guardar_Recepcion_Nueva();
            }

//           if (existen_parametros_para_mostrar()){
//                MuestraParametros1(this );
//            }else{
//               Guardar_Recepcion_Nueva();
//           }

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

            //#GT28022022_1707: control_peso determina el manejo de peso ya no es peso_recepción, pero aca se para para mostrar peso_estadistico
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

            //#GT 18082021: Aqui valida si hay parametro personalizado en caso no haya parametros A del BOF
            // pero esta validación se hizo en una funcionalidad previa, aqui redunda
//            if (pListBEProductoParametro!=null){
//                if (pListBEProductoParametro.items!=null){
//                    datos_ingresar +=1;
//                }
//            }

            mostrar = datos_ingresar>0;

        }catch (Exception ex){
            mu.msgbox("existen_parametros_para_mostrar: "+ex.getMessage());
        }

        return mostrar;
    }

    @SuppressLint("SetTextI18n")
    private void MuestraParametros1(Activity activity){

        try{

            int IndexPresentacion;
            Cant_Recibida_Actual=0;

            if (!txtCantidadRec.getText().toString().isEmpty()){
                Cant_Recibida_Actual = Double.parseDouble(txtCantidadRec.getText().toString());
            }

            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frm_parametros1);

            //#GT 03092021

            clsBeProducto_Presentacion bePresentacion= new clsBeProducto_Presentacion();

            /********* encabezado de la forma           ****************************/
            TextView lblPrducto = dialog.findViewById(R.id.lblTituloForma);

            /********  labels e inputs de tipo Parametros A  ***********************/

            lblParametrosA = dialog.findViewById(R.id.lblPropPrd2);
            lblParametrosA.setText("Parametros A");

            //parametro licence plate
            TextView lblLicPlate = dialog.findViewById(R.id.lblLPlate);
            txtLicPlate = dialog.findViewById(R.id.txtLicPlate);

            //parametro captura serial
            lblSerieTit = dialog.findViewById(R.id.lblSerieTit);
            lblSerialP = dialog.findViewById(R.id.lblSerial);
            txtSerial = dialog.findViewById(R.id.txtSerial);

            //parametro captura añada
            TextView lblAnada = dialog.findViewById(R.id.lblAnada);
            txtAnada = dialog.findViewById(R.id.txtAnada);

            //parametro fecha manufactura
            TextView lblFManufact = dialog.findViewById(R.id.lblFechaManu);
            txtFechaManu = dialog.findViewById(R.id.txtFechaManu);

            EditText txtFechaIng = dialog.findViewById(R.id.txtFechaIng);
            lblFecIngreso = dialog.findViewById(R.id.lblFecIngreso);

            /**************************************************************************/

            //labels e inputs de temperatura
            lblTempTit = dialog.findViewById(R.id.lblTempTit);
            lblTempEsta = dialog.findViewById(R.id.textView8);
            txtTempEsta = dialog.findViewById(R.id.txtTempEsta);
            lblTempReal = dialog.findViewById(R.id.textView91);
            txtTempReal = dialog.findViewById(R.id.txtTempReal);

            //labels e inputs de peso
            lblPesoTit = dialog.findViewById(R.id.lblPesoTit);
            lblPresParam = dialog.findViewById(R.id.textView92);
            lblPesoEsta = dialog.findViewById(R.id.textView93);
            lblPesoReal = dialog.findViewById(R.id.textView94);
            txtPesoEsta = dialog.findViewById(R.id.txtPesoEsta);
            txtPesoReal = dialog.findViewById(R.id.txtPesoReal);
            cmbPresParams = dialog.findViewById(R.id.cmbPresParams);

            //labels e inputs serie rango
            lblSerialIni = dialog.findViewById(R.id.textView95);
            lblSerialFin = dialog.findViewById(R.id.textView96);
            txtSerieIni = dialog.findViewById(R.id.txtSerieIni);
            txtSerieFin = dialog.findViewById(R.id.txtSerieFin);


            /***************************************************************************************/
            /**************************** set de los inputs ****************************************/

            lblPrducto.setText(BeProducto.Codigo + " - " +BeProducto.Nombre);

            //Objeto para dialogo de parametros
            txtTempReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtTempReal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPesoReal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoReal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtTempEsta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtTempEsta.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPesoEsta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoEsta.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});


            /********************** validar que mostrar y que ocultar  ****************************/

            if (BeProducto.Fechamanufactura && BeProducto.Materia_prima){

                lblFManufact.setVisibility(View.VISIBLE);
                txtFechaManu.setVisibility(View.VISIBLE);

            }else {
                lblFManufact.setVisibility(View.GONE);
                txtFechaManu.setVisibility(View.GONE);
            }

            if (BeProducto.Capturar_aniada){

                txtAnada.setText("0");
                lblAnada.setVisibility(View.VISIBLE);
                txtAnada.setVisibility(View.VISIBLE);

            } else {
                lblAnada.setVisibility(View.GONE);
                txtAnada.setVisibility(View.GONE);
            }

            if (BeProducto.Peso_recepcion){

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
                    }


                });
                //GT 21092021 valida que, exista un peso real y estadistico unitario asignado desde el bof
                Valida_Peso();

            }else {
                lblPresParam.setVisibility(View.GONE);
                cmbPresParams.setVisibility(View.GONE);
                lblPesoTit.setVisibility(View.GONE);
                lblPesoEsta.setVisibility(View.GONE);
                txtPesoEsta.setVisibility(View.GONE);
                lblPesoReal.setVisibility(View.GONE);
                txtPesoReal.setVisibility(View.GONE);
            }

            if (BeProducto.Serializado){
                Valida_Perfil_Serializado();
            }else {
                lblSerieTit.setVisibility(View.GONE);
                lblSerialP.setVisibility(View.GONE);
                txtSerial.setVisibility(View.GONE);
                lblSerialIni.setVisibility(View.GONE);
                txtSerieIni.setVisibility(View.GONE);
                lblSerialFin.setVisibility(View.GONE);
                txtSerieFin.setVisibility(View.GONE);
            }

            if (BeProducto.Temperatura_recepcion){
                //Valida_Temperatura();
                //GT 08092021 1609: Se elimino la funcion porque no contenia nada distinto a lo que esta en esta validación
                txtTempEsta.setText( mu.round(BeProducto.Temperatura_referencia,  gl.gCantDecCalculo)+"");
            }else{
                lblTempTit.setVisibility(View.GONE);
                lblTempEsta.setVisibility(View.GONE);
                lblTempReal.setVisibility(View.GONE);
                txtTempEsta.setVisibility(View.GONE);
                txtTempReal.setVisibility(View.GONE);
            }


            if (pListBeStockRec!=null){
                if (pListBeStockRec.items!=null){
                    List AuxStock=stream(pListBeStockRec.items).select(c->c.IdRecepcionDet).toList();
                    pIndexStock =AuxStock.indexOf(pIdRecepcionDet);
                }
            }

            IndexPresentacion = IndexPresSelected;

            if (IndexPresentacion!= -1){
                bePresentacion = BeProducto.Presentaciones.items.get(IndexPresentacion);
            }

            if (IndexPresentacion!=-1){

                if((bePresentacion.EsPallet||chkPaletizar.isChecked())&&
                        (bePresentacion.CamasPorTarima ==0|| bePresentacion.CajasPorCama==0)){

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

                                //String valores = gl.IdOperador +"-"+ gl.IdBodega;
                                //toastlong("GT: muestra_parametros resolución LP " + valores);

                                toastlong("nueva LP P2 ");
                                execws(6);
                            }else{
                                pNumeroLP = pLp;
                                //#CKFK 20201229 Agregué esta condición de que si la barra tiene información se coloca eso como LP
                                if (!txtNoLP.getText().toString().isEmpty()){
                                    txtLicPlate.setText(txtNoLP.getText().toString().replace("$",""));
                                }else{
                                    txtLicPlate.setText(pNumeroLP);
                                }

                            }

                        }

                    } else if (!bePresentacion.EsPallet || chkPaletizar.isChecked()) {

                        if (Cant_Recibida==0){
                            if (pIndexStock<0){
                                if (!Existe_Lp){

                                    //String valores = gl.IdOperador +"-"+ gl.IdBodega;
                                    //toastlong("GT: muestra_parametros2 resolución LP " + valores);
                                    toastlong("nueva LP P3 ");

                                    execws(6);
                                }else{
                                    pNumeroLP = pLp;

                                    //#CKFK 20201229 Agregué esta condición de que si la barra tiene información se coloca eso como LP
                                    if (!txtNoLP.getText().toString().isEmpty()){
                                        txtLicPlate.setText(txtNoLP.getText().toString().replace("$",""));
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

            //GT 02092021 esto ya se hizo al cargar la recepción en una validación para mostrar o no.
            //Carga_Parametros_Personalizados();

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH)+1;
            day = c.get(Calendar.DAY_OF_MONTH);

            txtFechaIng.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(day).append("-").append(month).append("-")
                    .append(year).append(" "));

            //GT 08092021: se oculta el campo de la fecha

            txtFechaIng.setVisibility(View.GONE);
            lblFecIngreso.setVisibility(View.GONE);

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

            Button btnIr = dialog.findViewById(R.id.btnIr);
            Button btnBack = dialog.findViewById(R.id.btnBack);

            btnIr.setOnClickListener(v -> BotonIrGuardarParametros());
            btnBack.setOnClickListener(v -> SalirPantallaParametros());
            dialog.show();
            mu.msgbox(MensajeParam);

        }catch (Exception e){
        mu.msgbox("MuestraParametros1: "+ e.getMessage());
        }
    }

    @SuppressLint("SetTextI18n")
    private void MuestraParametros2(Activity activity){

        try {

            parametro_personalizado_valido = false;
            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frm_parametros_p);

            /******** set de las labels de parametros personalizados *********/
            lblDescripcion_parametro = dialog.findViewById(R.id.lblDescripcion_p);

            lbltipo_numerica         = dialog.findViewById(R.id.lbltipo_n);
            lbltipo_texto            = dialog.findViewById(R.id.lbltipo_t);
            lbltipo_logica           = dialog.findViewById(R.id.lbltipo_b);
            lbltipo_fecha            = dialog.findViewById(R.id.lbltipo_f);
            imgDate_p                = dialog.findViewById(R.id.imgDate_p);

            /******** tipos de valores del parametro personalizado *******************/
            txtvalor_n = dialog.findViewById(R.id.txtvalor_n);
            txtvalor_t = dialog.findViewById(R.id.txtvalor_t);
            txtvalor_f = dialog.findViewById(R.id.txtvalor_f);
            cb_valor_b = dialog.findViewById(R.id.cb_valor_b);

            //contar_parametros_p

            for (int i = 0; i < 1; i++) {
                lblDescripcion_parametro.setText("Ingrese valor para: " + pListBEProductoParametro.items.get(i).TipoParametro.Descripcion);

                String tipo = pListBEProductoParametro.items.get(i).TipoParametro.Tipo;
                IdProductoParametro = pListBEProductoParametro.items.get(i).IdProductoParametro;

                switch(tipo) {
                    case "Númerico":
                        double valor =  pListBEProductoParametro.items.get(i).Valor_numerico;
                        txtvalor_n.setText(String.valueOf(valor));
                        ocultar_parametros_personalizados(tipo);
                        break;
                    case "Texto":
                        txtvalor_t.setText(pListBEProductoParametro.items.get(i).Valor_texto);
                        ocultar_parametros_personalizados(tipo);
                        break;
                    case "Fecha":
                        txtvalor_f.setText(pListBEProductoParametro.items.get(i).Valor_fecha);
                        ocultar_parametros_personalizados(tipo);
                        break;
                    case "Lógico":
                        cb_valor_b.setChecked(pListBEProductoParametro.items.get(i).Valor_logico);
                        ocultar_parametros_personalizados(tipo);
                        break;
                    default:
                        break;
                }

            }


            Button btnIr_p = dialog.findViewById(R.id.btnIr_p);
            Button btnBack_p = dialog.findViewById(R.id.btnBack_p);

            btnIr_p.setOnClickListener(v -> BotonIrGuardarParametros());
            btnBack_p.setOnClickListener(v -> SalirPantallaParametros());

            dialog.show();

        }catch (Exception e){
            mu.msgbox("MuestraParametros_P: "+ e.getMessage());
        }

    }


    @SuppressLint("SetTextI18n")
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

            alert.setNegativeButton("Cancelar", (dialog, whichButton) -> {
                chkEstiba.setChecked(false);

                if (CajasPorCama > 0 && CamasPorTarima > 0){
                    tblEstiba.setVisibility(View.VISIBLE);
                    lblEstiba.setText("Camas por Tarima: " + CamasPorTarima + " Cajas por cama: " +  CajasPorCama);
                }else{
                    tblEstiba.setVisibility(View.GONE);
                    lblEstiba.setText("");
                }

            });

            alert.setPositiveButton("Guardar", (dialog, whichButton) -> {

                CamasPorTarima=Double.parseDouble(txtCamasxtarima.getText().toString());
                CajasPorCama=Double.parseDouble(txtCajasxcama.getText().toString());
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
            });

            final AlertDialog dialog = alert.create();
            dialog.show();

            txtCamasxtarima.requestFocus();
            showkeyb();
        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
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
                        //lblSerialP.setVisibility(View.GONE);
                        //txtSerial.setVisibility(View.GONE);
                        lblSerieTit.setVisibility(View.GONE);
                        lblSerialIni.setVisibility(View.GONE);
                        txtSerieIni.setVisibility(View.GONE);
                        lblSerialFin.setVisibility(View.GONE);
                        txtSerieFin.setVisibility(View.GONE);

                        break;
                    case 3:
                        txtSerial.setText(BeProducto.Noserie);
                    default:
                        lblSerieTit.setVisibility(View.GONE);
                        lblSerialP.setVisibility(View.GONE);
                        txtSerial.setVisibility(View.GONE);
                        lblSerialIni.setVisibility(View.GONE);
                        txtSerieIni.setVisibility(View.GONE);
                        lblSerialFin.setVisibility(View.GONE);
                        txtSerieFin.setVisibility(View.GONE);
                        break;
                }

        }catch (Exception e){
            mu.msgbox("Valida_Perfil_Serializado"+e.getMessage());
        }
    }


    private void Valida_Peso(){

        try{

                txtPesoEsta.setText(mu.round(BeProducto.Peso_referencia,  gl.gCantDecCalculo)+"");

                if (!txtPesoUnitario.getText().toString().isEmpty() && !txtPesoUnitario.getText().toString().equals("")){
                    txtPesoReal.setText(txtPesoUnitario.getText()+"");
                }else{
                    throw new Exception("Debe ingresar el peso del producto");
                }

                if (BeProducto.Presentaciones.items!=null){

                    PresList.clear();

                    for (int i = 0; i <BeProducto.Presentaciones.items.size(); i++) {
                        PresList.add(BeProducto.Presentaciones.items.get(i).Nombre);
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PresList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresParams.setAdapter(dataAdapter);

                    if (PresList.size()>0) cmbPresParams.setSelection(0);

                }

                cmbPresParams.setFocusable(false);
                cmbPresParams.setFocusableInTouchMode(false);
                cmbPresParams.setClickable(false);

                if (IdPreseSelect!= -1){
                    List AuxPresParam = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                    int IndexPresentacion = AuxPresParam.indexOf(IdPreseSelect);
                    cmbPresParams.setSelection(IndexPresentacion);
                }

        }catch (Exception e){
            mu.msgbox("Valida Peso: "+e.getMessage());
        }

    }

    private void GuardarParametros(){

        MensajeParam="";
        plistBeReDetParametros = new clsBeTrans_re_det_parametrosList();
        ArrayList<clsBeTrans_re_det_parametros> parametros_personalizados = new ArrayList<>();


        try{

            //GT 0809202101648: Pregunta si hay parametros A o personalizados del bof
            if( !mostrar_parametros_producto && !Pperzonalizados){

                Guardar_Recepcion_Nueva();

            }else {

                //GT 08092021 1730: Si hay un parametro A del bof y la función nos dice si estón correctos o no
                if (mostrar_parametros_producto && !Parametros_Obligatorios_Ingresados() ){

                    MensajeParam= "¿El atributo tiene el valor por defecto o, no asignó ninguno, desea continuar?";
                    msgGuardarsinParametros(MensajeParam);

                    //GT 08092021 1730: Si hay un parametro personalizado, la función nos dice si se setearon correctamente.
                }else if(Pperzonalizados && !Validar_Parametros_personalizados() ){

                    MensajeParam= "¿El parametro tiene el valor por defecto o, no asigno ningúno, desea continuar?";
                    msgGuardarsinParametros(MensajeParam);

                }else {

                    parametros_personalizados.clear();

                    //GT 08092021 1732: si hubiera parametro personalizado, se asocia a la clase correspondiente, sino la clase irá en null
                    if (pListBEProductoParametro!=null){

                        if (pListBEProductoParametro.items!=null){

                            for (clsBeProducto_parametros obj: pListBEProductoParametro.items){

                                clsBeTrans_re_det_parametros ObjDP = new clsBeTrans_re_det_parametros();

                                ObjDP.IdRecepcionDet = pIdRecepcionDet;
                                ObjDP.IdProductoParametro = obj.IdProductoParametro;

                                ObjDP.IdParametroDet = 0;
                                ObjDP.IdRecepcionEnc = gl.gIdRecepcionEnc;
                                ObjDP.ProductoParametro = obj;
                                ObjDP.TipoParametro = obj.TipoParametro;

                                //#GT 20082021 Validamos que parametro es el que debe llenarse para setearlo correctamente.
                                if (tipo_parametro.equals("Texto")){
                                    ObjDP.Valor_texto = txtvalor_t.getText().toString();
                                }
                                if (tipo_parametro.equals("Númerico")){
                                    double valor = Double.parseDouble(txtvalor_n.getText().toString());
                                    ObjDP.Valor_numerico = valor;
                                }
                                if (tipo_parametro.equals("Fecha")){
                                    ObjDP.Valor_fecha = txtvalor_f.getText().toString();
                                }
                                if (tipo_parametro.equals("Lógico")){
                                    Boolean valor_cb = false;
                                    if (cb_valor_b.isChecked()){
                                        valor_cb = true;
                                    }
                                    ObjDP.Valor_logico = valor_cb;
                                }

                                ObjDP.Valor_Unico = obj.Valor_Unico;
                                ObjDP.User_agr = gl.IdOperador+"";
                                ObjDP.Fec_agr = String.valueOf(du.getFechaActual());
                                ObjDP.IsNew = true;

                                if(gl.mode==1){
                                    parametros_personalizados.add(ObjDP);
                                }

                            }

                            if( parametros_personalizados.size() !=0 ){
                                plistBeReDetParametros.items = stream(parametros_personalizados).toList();
                                //Guardar_Recepcion_Nueva();
                            }

                        }
                    }

                    if (BeProducto.Serializado){
                        if (BeProducto.IdPerfilSerializado==2){
                            pSerie = txtSerial.getText().toString();
                            //Llama al método Existe_Serie
                            execws(28);
                        }else{
                            Guardar_Recepcion_Nueva();
                        }
                    }else{
                        Guardar_Recepcion_Nueva();
                    }
                }

            }

            //#GT 18082021: no se si va aca, pero al ejecutarse, pide una nueva LP!
//            if (BeProducto.getControl_peso()){
//                Peso_Correcto();
//            }


        }catch (Exception e){
            mu.msgbox("GuardarParamaetros: "+ e.getMessage());
        }

    }

    private void Carga_Parametros_Personalizados(){

        boolean Carga_Param=false;

        try{

            if (pIndexParam==-1){

                if (pListBEProductoParametro!=null){

                    if (pListBEProductoParametro.items!=null){

                    }else{
                        execws(10);

                    }

               }

            }

        }catch (Exception e){
            mu.msgbox("Carga_Parametros_Personalizados:"+e.getMessage());
        }

    }

    private void Temperatura_Correcta(){

        try{

            if (BeProducto.Temperatura_recepcion){

                if (BeProducto.Temperatura_referencia>0){

                    if (txtTempReal.getText().toString().isEmpty()){
                        MensajeParam+="Debe de ingresar la temperatura \n";
                        return;
                    }else if (Double.parseDouble(txtTempReal.getText().toString())<=0){
                        MensajeParam+="La temperatura debe ser mayor a 0 \n";
                        return;
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

    }

    private void Peso_Correcto(){

        double val;

        try{

            if (BeProducto.Peso_recepcion){

                val = Double.parseDouble(txtPesoEsta.getText().toString());

                if (val>0){

                    if (txtPesoReal.getText().toString().isEmpty()){
                        MensajeParam +="Debe ingresar peso \n";
                        return;
                    }else if(Double.parseDouble(txtPesoReal.getText().toString())<=0){
                        MensajeParam+="El peso debe ser mayor a 0 \n";
                        return;
                    }

                }

                double PorcentajeToleranciaPeso = (Double.parseDouble(txtPesoEsta.getText().toString()) * (BeProducto.Peso_tolerancia));
                double PesoMaximoReferencia = mu.round( Double.parseDouble(txtPesoEsta.getText().toString()) + PorcentajeToleranciaPeso,  gl.gCantDecCalculo);
                double PesoMinimoReferencia = mu.round(Double.parseDouble(txtPesoEsta.getText().toString()) - PorcentajeToleranciaPeso,  gl.gCantDecCalculo);
                double ValorPeso  = mu.round(Double.parseDouble(txtPesoReal.getText().toString()),  gl.gCantDecCalculo);

                if (!(ValorPeso >= PesoMinimoReferencia)&&(ValorPeso <= PesoMaximoReferencia)){
                    msgContinuarPeso("El peso ingresado es menor que "+PesoMinimoReferencia +" o mayor que "+ PesoMaximoReferencia+" (tolerancia permitida en base al peso estadístico). ¿Desea continuar?");
                }else{
                    Temperatura_Correcta();
                }


            }else{
                Temperatura_Correcta();
            }

        }catch (Exception e){
            mu.msgbox("Peso_Correcto: "+e.getMessage());
        }

    }

    private void ContinuaValidandoParametros(){

        clsBeStock_rec BeStock_rec  = new clsBeStock_rec();

        try {

            if (PallCorrecto){

                if (pIndexStock==-1){

                    BeStock_rec  = new clsBeStock_rec();
                    pListBeStockRec.items = new ArrayList<>();

                    BeStock_rec.IdRecepcionDet = pIdRecepcionDet;
                    //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                    BeStock_rec.IdPropietarioBodega = pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                    BeStock_rec.IdProductoBodega = BeProducto.IdProductoBodega;
                    BeStock_rec.IdUnidadMedida = BeProducto.IdUnidadMedidaBasica;
                    if(IdPreseSelect>0){
                        BeStock_rec.Presentacion = new clsBeProducto_Presentacion();
                    }
                    BeStock_rec.IsNew = gl.mode == 1;

                   pListBeStockRec.items.add(BeStock_rec);
                   pIndexStock = pListBeStockRec.items.size()-1;

                   if (IdPreseSelect!=-1){
                       if  ((BeProducto.Presentaciones!=null)){

                           if (BeProducto.Presentaciones.items!=null) {

                               List AuxLisPres = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();

                               int IndexPresentacion = AuxLisPres.indexOf(IdPreseSelect);

                               clsBeProducto_Presentacion bePresentacion;

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

                                   if (pListBeProductoPallet.items == null) {

                                       pListBeProductoPallet.items = new ArrayList<>();

                                   }
                                   pListBeProductoPallet.items.add(BeProdPallet);
                                   pIndexProdPallet = pListBeProductoPallet.items.size() - 1;
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
                        ObjNS.IsNew = gl.mode == 1;

                        if (pListBeStockSeRec.items!=null){
                            pListBeStockSeRec.items.add(ObjNS);
                        }else{
                            pListBeStockSeRec.items = new ArrayList<>();
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

                                int lIndex;

                                lIndex = AuxStockRec.indexOf(pListBeStockRec.items.get(pIndexStock).Serial);

                                if (lIndex>-1){
                                    mu.msgbox("El Serial " +txtSerial.getText().toString() +" se encuentra ya ingresado");
                                }


                            }

                        }

                    }//TerminaValidacionPerfilSerializado

                    //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                    assert pListBeStockRec != null;
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
                        //#GT28022022_1436PM: El peso a enviar debe ser el bruto, porque en pedidos, toma el peso bruto y lo divide dentro de la cantidad
                        //a reservar
                        //pListBeStockRec.items.get(pIndexStock).Peso = Double.parseDouble(txtPesoReal.getText().toString());
                        pListBeStockRec.items.get(pIndexStock).Peso = Double.parseDouble(txtPeso.getText().toString());
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

                                Obj.IdRecepcionDet = pIdRecepcionDet;
                                Obj.IdRecepcionEnc = gl.gIdRecepcionEnc;
                                Obj.ProductoParametro.IdProductoParametro = Obj.IdProductoParametro;
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

                            clsBeProducto_Presentacion bePresentacion;

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

                                int lIndex;

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

                //#GT 13092021 1109: el perfil 2 es igual a solo 1 serial, no una serie?
                //ObjNS.NoSerie = "";
                ObjNS.NoSerie = txtSerial.getText().toString();
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

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                if (BeProducto.Genera_lp){
                    execws(8);
                }else{
                    PallCorrecto=true;
                    ContinuaValidandoParametros();
                }

            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();
            dialog.wait();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void msgContinuarPeso(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> Temperatura_Correcta());

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    public boolean Parametros_Obligatorios_Ingresados() {

        try {

            //Valida_Peso();


        }catch (Exception e){
            mu.msgbox("Parametros_Obligatorios_Ingresados: "+e.getMessage());
        }

        return true;
    }

    private void Mostrar_Propiedades_Parametros(){

        try{

            if (!Mostro_Propiedades){
                execws(5);
            }else{
                Guardar_Recepcion_Nueva();
            }

            //#CKFK20220217 Voy a poner esto en comentario momentaneamente
           // Mostrar_Propiedades_Parametros = false;

        }catch (Exception e){

        }

    }

    //endregion

    //region CargaProducto
    private void Procesa_Barra_Producto(){

        try{

            pLp = "";

            if (!txtNoLP.getText().toString().isEmpty()){

                pLp = txtNoLP.getText().toString().replace("$", "");

                //#EJC20210612: Este valor llegaba vacío a la impresión.
                pNumeroLP = pLp;
                //Llama al método del WS Existe_Lp
                execws(24);
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

                        if (gl.gBeOrdenCompra.Push_To_NAV &&
                                (dataContractDI.Orden_De_Produccion == gl.gBeOrdenCompra.IdTipoIngresoOC ||
                                 dataContractDI.Transferencia_de_Ingreso == gl.gBeOrdenCompra.IdTipoIngresoOC)){
                            if (gl.gBeOrdenCompra.DetalleLotes.items == null) {
                               msgSinUbicaciones("Este tipo de documentos deben tener definidos los lotes a recibir");
                               return;
                            }
                        }

                        if (gl.gBeOrdenCompra.DetalleLotes.items != null) {

                            //#CKFK 20210611 Agregué esta validación para lo documentos de ingreso
                            // tipo Orden de producción (6)
                            clsBeTrans_oc_det_loteList vence;
                            vence=gl.gBeOrdenCompra.DetalleLotes;

                            List<clsBeTrans_oc_det_lote> BeVence =  stream(vence.items)
                                    .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                            c.No_linea == BeOcDet.No_Linea &&
                                            c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                            c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                            c.Cantidad_recibida < c.Cantidad)
                                    .toList();

                            if (gl.gBeOrdenCompra.TipoIngreso.getIdTipoIngresoOC()==6 &&
                                    gl.gBeOrdenCompra.TipoIngreso.getRequerir_Documento_Ref() &&
                                    BeVence.size()==0){

                                msgSinUbicaciones("No es posible realizar la recepción del producto " + BeProducto.getCodigo() +
                                                  " porque no hay ubicaciones definidas");
                                return;

                            }else{

                                if (gl.gBeOrdenCompra.DetalleLotes.items.size() > 0) {

                                    cmbVence.setVisibility(View.VISIBLE);
                                    cmbVenceRec.setVisibility(View.GONE);
                                    imgDate.setVisibility(View.GONE);
                                    fillFechaVence();
                                }
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

                            //#CKFK 20210611 Agregué esta validación para lo documentos de ingreso tipo Orden de producción (6)
                            clsBeTrans_oc_det_loteList lotes;
                            lotes=gl.gBeOrdenCompra.DetalleLotes;

                            List<clsBeTrans_oc_det_lote> BeLote =  stream(lotes.items)
                                    .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                            c.No_linea == BeOcDet.No_Linea &&
                                            c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                            c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                            c.Cantidad_recibida < c.Cantidad)
                                    .toList();

                            if (gl.gBeOrdenCompra.TipoIngreso.getIdTipoIngresoOC()==6 &&
                                    gl.gBeOrdenCompra.TipoIngreso.getRequerir_Documento_Ref() &&
                                    BeLote.size()==0){

                                msgSinUbicaciones("No es posible realizar la recepción de este producto porque no hay ubicaciones definidas");
                                return;

                            }else{

                                if (gl.gBeOrdenCompra.DetalleLotes.items.size() > 0) {

                                    cmbLote.setVisibility(View.VISIBLE);
                                    txtLoteRec.setVisibility(View.GONE);
                                    tblUbicacion.setVisibility(View.VISIBLE);
                                    fillLotes();
                                }

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

            //#GT04042022: focus en cantidad.
            txtCantidadRec.requestFocus();
            txtCantidadRec.selectAll();

        }catch (Exception e){
            mu.msgbox("Load:"+ e.getMessage());
        }


    }

    private void Carga_Datos_Producto_Por_Pallet(){

        try{

            if (BeProducto.IdProducto!=0){

                Mostro_Propiedades = false;

                plistBeReDetParametros =  new clsBeTrans_re_det_parametrosList();
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


                            //String valores = gl.IdOperador +"-"+ gl.IdBodega;
                            //toastlong("GT: carga_prod_x_pallet resolución LP3 " + valores);

                            toastlong("nueva LP P4 ");
                            execws(6);
                        }
                    }

                }else{

                    pListTransRecDet.items = new ArrayList<>();

                    execws(19);

                }

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("Carga_Datos_Producto_Por_Pallet:"+e.getMessage());
        }
    }

    private void Carga_Datos_Producto(){

        try{

            progress.setMessage("Cargando datos de producto");

            if (BeProducto.IdProducto!=0){

                Mostro_Propiedades = false;

                plistBeReDetParametros = new clsBeTrans_re_det_parametrosList();
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

                            //String valores = gl.IdOperador +"-"+ gl.IdBodega;
                            //toastlong("GT: carga_datos_producto resolución LP " + valores);

                            execws(6);
                        }
                    }
                }else{
                    pListTransRecDet.items = new ArrayList<>();

                    execws(19);

                    //Listar_Producto_Presentaciones();

                }

            }


        }catch (Exception e){
            mu.msgbox("CargarDatos: "+e.getMessage());
        }

    }

    private void Carga_Datos_Producto_Existente(){

        try{

            progress.setMessage("Cargando datos de producto existente");

            if (BeProducto.IdProducto!=0){

                Mostro_Propiedades = false;

                plistBeReDetParametros = new clsBeTrans_re_det_parametrosList();
                pListBeStockSeRec = new clsBeStock_se_recList();
                pListBeStockRec = new clsBeStock_recList();
                pListBeProductoPallet = new clsBeProducto_palletList();

                //Asigna los items ya existentes
                pListTransRecDet.items = gl.gListTransRecDet.items;

                if (pListTransRecDet.items!=null){

                    pIdRecepcionDet = pListTransRecDet.items.get(0).IdRecepcionDet;

                    pLineaOC = pListTransRecDet.items.get(0).No_Linea;

                    vCantAnteriorRec = pListTransRecDet.items.get(0).cantidad_recibida;

                    execws(23);

                }else{
                    pListTransRecDet.items = new ArrayList<>();

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

            progress.setMessage("Consultando parámetros de producto.");

            if (Escaneo_Pallet){
                txtNoLP.setText(BeINavBarraPallet.Codigo_barra);
            }else{
                txtNoLP.setText(Get_Codigo_Barra(gl.CodigoRecepcion));
                txtNoLP.setText("");
            }

            if(BeProducto.IdProductoBodega>0){
                pIdProductoBodega = BeProducto.IdProductoBodega;
            }

            lblDatosProd.setText(BeProducto.Codigo + " - " + BeProducto.Nombre);
            lblPropPrd.setText("Propietario: "  + BeProducto.Propietario.Nombre_comercial);

            if (BeProducto.Control_vencimiento){

                tblVence.setVisibility(View.VISIBLE);

                if (!gl.gFechaVenceAnterior.equals("")){
                    //#EJC20220407:Conservar último lote, solo si no hay lotes predefinidos.
                    if (gl.gBeOrdenCompra.DetalleLotes.items.size()==0){
                        cmbVenceRec.setText(gl.gFechaVenceAnterior);
                    }
                }

            }else{

                tblVence.setVisibility(View.GONE);
            }

            Valida_Lote();

            vPresentacion = Get_Presentacion_A_Recibir();

            if (vPresentacion>0){
                chkPresentacion.setVisibility(View.VISIBLE);
                chkPresentacion.setChecked(true);
                Factor =Get_Factor_Presentacion(vPresentacion);

                //#EJC20201008: Da error de NoSuchElementException cuando no encuentra la presentación por el ID,
                //Por eso agregué este try catch así.

                try {
                    if (BeProducto.Presentaciones!=null){
                        if(BeProducto.Presentaciones.items!=null){
                            EsPallet = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.EsPallet).first();
                        }
                    }
                   } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }

                if (EsPallet){
                    Factor = Factor * stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.CajasPorCama).first() * stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==vPresentacion).select(c->c.CamasPorTarima).first();
                }

                if (BeProducto.Presentaciones!=null) {
                    if (BeProducto.Presentaciones.items != null) {
                        List AxuListPres = stream(BeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                        Indx =AxuListPres.indexOf(vPresentacion);

                        cmbPresRec.setSelection(Indx);
                    }
                }

            }else{

                lblPres.setVisibility(View.GONE);
                cmbPresRec.setVisibility(View.GONE);

            }

            if (gl.Carga_Producto_x_Pallet){

                if  (BeProducto.Presentaciones.items.get(Indx).EsPallet){

                    Cant_Recibida = gBeStockRec.Uds_lic_plate;
                    Cant_A_Recibir = Factor;

                    FinalizaCargaProductos();

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

                txtNoLP.requestFocus();
                txtNoLP.selectAll();

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

                FinalizaCargaProductos();

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
        boolean EsPallet;
        int Indx=-1;

        try{

            progress.setMessage("Procesando valores int. ref. #20210601");

            if (!pListTransRecDet.items.get(0).Lic_plate.toString().isEmpty()) {
                txtNoLP.setText(pListTransRecDet.items.get(0).Lic_plate);
            }else{
                if (Escaneo_Pallet){
                    txtNoLP.setText(pListTransRecDet.items.get(0).Lic_plate);
                }else{
                    txtNoLP.setText(Get_Codigo_Barra(gl.CodigoRecepcion));
                    txtNoLP.setText("");
                }
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

            //#CKFK 20210630 quite este llamado porque no aplica cuando el registro ya existe
            //Valida_Lote();

            vLote = pListTransRecDet.items.get(0).Lote;
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

                lblPres.setVisibility(View.GONE);
                cmbPresRec.setVisibility(View.GONE);

            }

            if (gl.Carga_Producto_x_Pallet){

                if  (BeProducto.Presentaciones.items.get(Indx).EsPallet){

                    Cant_Recibida = gBeStockRec.Uds_lic_plate;
                    Cant_A_Recibir = Factor;

                    FinalizaCargaProductos();

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

               // txtLoteRec.setText(pListTransRecDet.items.get(0).Lote);

                txtLoteRec.setText(vLote);

                cmbVenceRec.setText(du.convierteFechaMostrarDiagonal(pListTransRecDet.items.get(0).Fecha_vence));

                txtCostoReal.setText(pListTransRecDet.items.get(0).Costo+"");

                List AxuLisEsta = stream(pListTransRecDet.items).select(c->c.IdProductoEstado).toList();
                Indx =AxuLisEsta.indexOf(pListTransRecDet.items.get(0).IdProductoEstado);

                cmbPresRec.setSelection(Indx);

                FinalizaCargaProductos();

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }

    }

    @SuppressLint("SetTextI18n")
    private void FinalizaCargaProductos(){

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

            if (gl.mode==1){

                if (!gl.gProductoAnterior.equals(BeProducto.getCodigo())){
                    if (cmbLote.getVisibility()!=View.VISIBLE){
                        txtLoteRec.setText("");
                    }
                    if (cmbVence.getVisibility()!=View.VISIBLE){
                        cmbVenceRec.setText(du.convierteFechaMostrarDiagonal(du.getFechaActual()));
                    }

                    cmbEstadoProductoRec.setSelection(0);
                }
            }


            if (!gl.gBeRecepcion.Muestra_precio){
                txtCostoOC.setVisibility(View.GONE);
                txtCostoReal.setVisibility(View.GONE);
                lblCosto.setVisibility(View.GONE);
                lblCReal.setVisibility(View.GONE);
            }

            try {

                //#EJC20210729: Genera error si no devuelve nada el .first.
                try {

                    CostoOC = stream(gl.gpListDetalleOC.items).where(c->c.IdProductoBodega == pIdProductoBodega
                            && c.IdPresentacion == vPresentacion).select(c->c.Costo).first();

                } catch (Exception e) {
                    CostoOC=0;
                }

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

            } catch (Exception e) {
                mu.msgbox("fcp_CostoOC: "+e.getMessage());
            }

            txtCostoOC.setText(mu.round(BeProducto.Costo, gl.gCantDecCalculo)+"");

            txtCostoReal.setText(CostoOC+"");

            txtUmbasRec.setFocusable(false);
            txtUmbasRec.setFocusableInTouchMode(false);
            txtUmbasRec.setClickable(false);

            if (gl.mode==1){
                txtNoLP.setText(BeProducto.Codigo);
                txtNoLP.setText("");
            }

            /*txtBarra.setFocusable(false);
            txtBarra.setFocusableInTouchMode(false);
            txtBarra.setClickable(false);*/

            //txtCantidadRec.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
            //txtCantidadRec.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});

            //#GT 24082021: filtro para que el input acepte solo los decimales parametrizados en empresa
            txtCantidadRec.setFilters(new InputFilter[]{new DecimalFilter(15, gl.gCantDecCalculo)});
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

                cmbVenceRec.setText(du.convierteFechaMostrarDiagonal(gBeStockRec.Fecha_vence));

                cmbVenceRec.setFocusable(false);
                cmbVenceRec.setFocusableInTouchMode(false);
                cmbVenceRec.setClickable(false);

            }

            if (gl.mode==1){
                if (Escaneo_Pallet){
                    LlenaCamposEsPallet();
                }else{
                    if (BeProducto.Genera_lp){
                        progress.setMessage("Generando licencia..");
                        progress.show();

                        //String valores = gl.IdOperador +"-"+ gl.IdBodega;
                        //toastlong("GT: fin_carga_prod resolución LP " + valores);

                        //GT15022022: si presentacion esta null carga nuevamente NuevoLP
                        if (BeProducto.Presentaciones.items==null || BeOcDet.IdPresentacion==0){
                            execws(6);
                            progress.cancel();
                        }
                    }
                }
            }else{
                Llena_beStock_Anterior();
            }

            if (BeProducto.Genera_lp || BeProducto.Presentaciones.items.get(0).Genera_lp_auto) {
                execws(6);
                if (nBeResolucion == null) {
                    if (!txtNoLP.getText().toString().isEmpty()) {
                        txtNoLP.requestFocus();
                    }
                } else {
                    txtCantidadRec.requestFocus();
                }
            }else {
                //GT04042022: focus a cantidad
                txtCantidadRec.requestFocus();
                txtCantidadRec.selectAll();
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
            cmbVenceRec.setText(du.convierteFechaMostrarDiagonal(BeINavBarraPallet.Fecha_Vence));

            if (!EsTransferenciaInternaWMS){
                txtCantidadRec.setText(BeINavBarraPallet.Cantidad_UMP+"");
                txtCantidadRec.requestFocus();
            }else{
                txtCantidadRec.setText(BeINavBarraPallet.Cantidad_Presentacion+"");
                txtCantidadRec.requestFocus();
            }

            List AuxList = stream(BeProducto.Presentaciones.items).select(c->c.Codigo_barra).toList();

            int indxPres=-1;

            if (BeINavBarraPallet.UM_Producto != null){
                indxPres=AuxList.indexOf(BeINavBarraPallet.UM_Producto);
            }

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

            String vMensaje1 ="";

            if (indxPres !=-1){
                vMensaje1= "Código: "+BeINavBarraPallet.Codigo+"\n "
                        +"Cant: "+vCantidad +"\n "
                        +"Pres: "+ BeProducto.Presentaciones.items.get(indxPres).Nombre +"\n"
                        +"Venc: "+BeINavBarraPallet.Fecha_Vence+"\n "
                        +"Lote: "+BeINavBarraPallet.Lote +"\n"
                        +"¿El producto está completo y en buen estado?";
            }else{
                 vMensaje1 = "Código: "+BeINavBarraPallet.Codigo+"\n "
                        +"Cant: "+vCantidad +"\n "
                        +"UM: "+ BeProducto.UnidadMedida.Nombre +"\n"
                        +"Venc: "+BeINavBarraPallet.Fecha_Vence+"\n "
                        +"Lote: "+BeINavBarraPallet.Lote +"\n"
                        +"¿El producto está completo y en buen estado?";
            }


            msgValidaProductoPallet(vMensaje1);

        }catch (Exception e){
            mu.msgbox("LlenaCamposEsPallet:"+e.getMessage());
        }
    }

    private void fillFechaVence() {

        String valor;

        try {

            VenceList.clear();

            clsBeTrans_oc_det_loteList vence;
            vence=gl.gBeOrdenCompra.DetalleLotes;

            List<clsBeTrans_oc_det_lote> BeVence =  stream(vence.items)
                    .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet)
                    .toList();

            double CantRec;

            for (int i = 0; i <BeVence.size(); i++)
            {
                valor = du.convierteFechaMostrarDiagonal(BeVence.get(i).Fecha_vence);
                CantRec = BeVence.get(i).Cantidad_recibida;

                if (!VenceList.contains(valor)){
                    if (CantRec==0){
                        VenceList.add(valor);
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, VenceList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbVence.setAdapter(dataAdapter);

            if (VenceList.size()>0) {
                cmbVence.setSelection(0);
                cmbVenceRec.setText(cmbVence.getSelectedItem().toString());
            }else{
                cmbVence.setVisibility(View.GONE);
                cmbVenceRec.setVisibility(View.VISIBLE);
                imgDate.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void fillLotes() {

        String valor;

        try {

            LotesList.clear();

            clsBeTrans_oc_det_loteList lotes;
            lotes=gl.gBeOrdenCompra.DetalleLotes;
            List<clsBeTrans_oc_det_lote> BeLotes;

            if (BeProducto.getControl_vencimiento() && VenceList.size()>0){
                BeLotes = stream(lotes.items)
                        .where(c -> {
                            try {
                                return c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                        c.No_linea == BeOcDet.No_Linea &&
                                        c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                        c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                        c.Fecha_vence.equals(du.convierteFecha(cmbVence.getSelectedItem().toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .toList();

            }else{
                BeLotes = stream(lotes.items)
                        .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                c.No_linea == BeOcDet.No_Linea &&
                                c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                c.IdOrdenCompraEnc == pIdOrdenCompraEnc)
                        .toList();
            }

            double CantRec;
            double CantSol;

            for (int i = 0; i <BeLotes.size(); i++)
            {

               valor = BeLotes.get(i).Lote;
               CantRec = BeLotes.get(i).Cantidad_recibida;
               CantSol = BeLotes.get(i).Cantidad;

                if (!LotesList.contains(valor)){
                    if (CantRec!=CantSol){
                        LotesList.add(valor);
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LotesList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbLote.setAdapter(dataAdapter);

            if (LotesList.size()>0) {
                cmbLote.setSelection(0);
            }else{
                cmbLote.setVisibility(View.GONE);
                txtLoteRec.setVisibility(View.VISIBLE);
                msgSinUbicaciones("Este tipo de documentos deben tener definidos los lotes a recibir y ya todos se recibieron");
                return;
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    //#EJC20210412: Funcion para llenar ubicacion de NAV.
    private void fillUbicacion() {

        String valor;

        try {

            if (gl.gBeOrdenCompra.IdTipoIngresoOC == dataContractDI.Orden_De_Produccion){

                UbicLotesList.clear();

                clsBeTrans_oc_det_loteList ubic;
                ubic=gl.gBeOrdenCompra.DetalleLotes;
                List<clsBeTrans_oc_det_lote> BeUbicaciones;

                String SelectedLote;
                String FechaVence;

                SelectedLote = txtLoteRec.getText().toString().trim();
                FechaVence = du.convierteFecha(cmbVenceRec.getText().toString());

                String finalSelectedLote = SelectedLote;
                String finalFechaVence = FechaVence;

                String LpOrigen = pLp;
                //#CKFK 20211030 Inicialicé esta variable de ubicaciones
                BeUbicaciones = new ArrayList<clsBeTrans_oc_det_lote>();

                if (BeProducto.getControl_vencimiento() && VenceList.size()>0){

                    //#CKFK 20211030 Validé que ubic.items no fuera nulo
                    if (ubic.items!=null){
                        BeUbicaciones = stream(ubic.items)
                                .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                        c.No_linea == BeOcDet.No_Linea &&
                                        c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                        c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                        c.Lote.equals(finalSelectedLote)  &&
                                        c.Fecha_vence.equals(finalFechaVence) &&
                                        c.Lic_Plate.equals(LpOrigen))
                                .toList();
                    }

                }else{
                    //#CKFK 20211030 Validé que ubic.items no fuera nulo
                    if (ubic.items!=null){
                        BeUbicaciones = stream(ubic.items)
                                .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                        c.No_linea == BeOcDet.No_Linea &&
                                        c.Lote.equals(finalSelectedLote)  &&
                                        c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                        c.IdOrdenCompraEnc == pIdOrdenCompraEnc)
                                .toList();
                    }
                }

                CantRec=0;
                CantTotal =0;
                DifCantUbic =0;

                //#CKFK 20211030 Validé que BeUbicaciones no fuera nulo
                if (BeUbicaciones!=null){
                    //#CKFK 20211030 Validé que BeUbicaciones.size() fuera mayor que 0
                    if (BeUbicaciones.size()>0){
                        for (int i = 0; i <BeUbicaciones.size(); i++)
                        {
                            valor = BeUbicaciones.get(i).Ubicacion;
                            CantRec =BeUbicaciones.get(i).Cantidad_recibida;
                            CantTotal=BeUbicaciones.get(i).Cantidad;

                            if (BeUbicaciones.get(i).IdPresentacion!=0){
                                CantTotal=mu.round2(CantTotal/ BeProducto.Presentacion.Factor);
                                CantRec= mu.round2(CantRec/ BeProducto.Presentacion.Factor);
                            }

                            DifCantUbic = CantTotal - CantRec;

                            if (DifCantUbic>0){
                                if (!UbicLotesList.contains(valor)){
                                    UbicLotesList.add(valor);
                                    break;
                                }
                            }
                        }
                    }
                }

                DifCantUbic = CantTotal - CantRec;

                if (UbicLotesList.size()>0){

                    ubiDetLote = UbicLotesList.get(0);

                    if (ubiDetLote!=null){
                        if(!ubiDetLote.isEmpty()) {
                            lblUbicacion.setText("Ubic: " + ubiDetLote + "\n Pend:" + DifCantUbic + "\n Rec: "+ CantRec );
                            tblUbicacion.setVisibility(View.VISIBLE);
                        }
                    }else {
                        tblUbicacion.setVisibility(View.GONE);
                    }
                }else{
                    //#CKFK20220307 Coloqué este toast para cuando es una OP y se ingresa una licencia no válida
                    if (!txtNoLP.getText().toString().isEmpty()){
                        toast("La licencia ingresada no es válida");
                        txtNoLP.setText("");
                    }
                    tblUbicacion.setVisibility(View.GONE);
                }

            }

        } catch (Exception e) {
            mu.msgbox("FillUbicacion " + e.getMessage());
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
        double vCantidad;

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

                    if (LProductoEstado.items.size()> 0){

                        clsBeProducto_estado BeEstado;

                        BeEstado = stream(LProductoEstado.items).where(c->c.IdEstado ==IdEstadoSelect).first();

                        //#CKFK20220107 Busco la ubicación en base al estado del producto
                        int vIdUbicacion = 0;

                        if (BeEstado.IdUbicacionBodegaDefecto>0){
                                vIdUbicacion = BeEstado.IdUbicacionBodegaDefecto;
                        }else if (BeEstado.IdUbicacionDefecto>0){
                             vIdUbicacion = BeEstado.IdUbicacionDefecto;
                        }else{
                            if (BeEstado.Danado){
                                if (!gl.gUbicMerma.isEmpty()){
                                    vIdUbicacion = Integer.valueOf(gl.gUbicMerma);
                                }
                            }
                        }

                        //#CKFK20220106 Agregué validación gPriorizar_UbicRec_Sobre_UbicEst
                        //Si el parámetro está en true se manda la ubicación de la tarea de recepción
                        //en caso contrario se manda la ubicación del estado o la ubicación de merma
                        // si no existe ninguna de ellas se manda la ubicación de la tarea de recepción
                        if (gl.gPriorizar_UbicRec_Sobre_UbicEst){
                            vBeStockRecPallet.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                        }else{
                            //Si el parametro está en false se manda la ubicación del estado
                            //Si la ubicación del estado no existe
                            //Se manda la ubicación de la tarea de recepción
                            if (vIdUbicacion==0){
                                vBeStockRecPallet.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                            }else{
                                vBeStockRecPallet.IdUbicacion = vIdUbicacion;
                            }
                        }

                    }
/*
                    //#CKFK20220106 Agregué validación gPriorizar_UbicRec_Sobre_UbicEst
                    if (gl.gPriorizar_UbicRec_Sobre_UbicEst){
                        vBeStockRecPallet.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                    }else{
                        if (LProductoEstado.items.size()>0){

                            if (stream(LProductoEstado.items).where(c->c.IdEstado ==IdEstadoSelect).select(c->c.Danado).first()){
                                execws(20);
                                return;
                            }else{

                                vBeStockRecPallet.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                            }

                        }
                    }*/
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

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PresList);
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

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, EstadoList);
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

            imprimirDesdeBoton=false;
            guardando_recepcion=true;

            if (!txtNoLP.getText().toString().isEmpty()){
                Procesa_Barra_Producto();
            }else{

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

            }

        }catch (Exception e){
            mu.msgbox("Guardar_Recepcion: "+ e.getMessage());
        }

    }

    public void BotonGuardarRecepcion(View view){

        try{

            imprimirDesdeBoton=false;

            String valor= cmbVenceRec .getText().toString();

            String fecha_ajustada =  du.convierteFechaSinHora(valor);

            if (!du.EsFecha(fecha_ajustada)){
                msgbox("No es una fecha válida, se colocará la fecha actual");
                cmbVenceRec.setText(du.getActDateStr());
            }else
            {
                cmbVenceRec.setText(fecha_ajustada);
            }

//            try{
//
//                if (!du.EsFecha(valor)){
//                    msgbox("No es una fecha válida, se colocará la fecha actual");
//                    cmbVenceRec.setText(du.getActDateStr());
//                    return;
//                };
//
//                // du.EsFecha(valor);
//            }catch(Exception e){
//                cmbVenceRec.setText(du.getActDateStr());
//                return;
//            }

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
                pListBeStockRec.items = new ArrayList<>();
                BeStock_rec.IdRecepcionDet = pIdRecepcionDet;
                BeStock_rec.IdRecepcionEnc = gl.gIdRecepcionEnc;
                //#CKFK 20210322 Modifiqué que se envíe el IdPropietarioBodega de trans_re_det
                BeStock_rec.IdPropietarioBodega =pIdPropietarioBodega;//gl.gBeRecepcion.PropietarioBodega.IdPropietarioBodega;
                BeStock_rec.IdProductoBodega = BeProducto.IdProductoBodega;
                //#GT 13092021 Se añade el serial a la clase Stock_rec si el producto tiene parametro
                if (mostrar_parametros_producto)
                 {
                    BeStock_rec.Serial = txtSerial.getText().toString();
                    //#si hay mas atributos, se setean aca, aunque no tengan valor asignado, el layut se cargará
                     if(txtPesoReal.getText().toString().isEmpty()){
                         BeStock_rec.Peso = 0;
                     }else {
                         //#GT28022022_1452: el peso a enviar es el bruto, porque se divide dentro de la cantidad a reservar en un pedido
                         //BeStock_rec.Peso = Double.parseDouble(txtPesoReal.getText().toString());
                         BeStock_rec.Peso = Double.parseDouble(txtPeso.getText().toString());
                     }

                    if(txtTempReal.getText().toString().isEmpty()){
                        BeStock_rec.Temperatura = 0;
                    }else {
                        BeStock_rec.Temperatura = Double.parseDouble(txtTempReal.getText().toString());
                    }
                }
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
                assert Objects.requireNonNull(pListBeStockRec).items != null;
                assert pListBeStockRec.items != null;
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

                pListBeStockRec.items.get(pIndiceListaStock).IdPresentacion = Math.max(IdPreseSelect, 0);

                if (pListBeStockRec.items.get(pIndiceListaStock).Peso>0){
                    pListBeStockRec.items.get(pIndiceListaStock).Peso =pListBeStockRec.items.get(pIndiceListaStock).Peso ;
                }else{

                    //GT15022022: esta linea es la original,
                    //pListBeStockRec.items.get(pIndiceListaStock).Peso = 0;

                    //GT16022022: si hay control peso, envio el valor peso (peso bruto)
                    //de lo contario, envio valor 0
                    if(BeProducto.Control_peso){
                        pListBeStockRec.items.get(pIndiceListaStock).Peso = Double.parseDouble(txtPeso.getText().toString());
                    }else{
                        pListBeStockRec.items.get(pIndiceListaStock).Peso = 0;
                    }
                }

                if (pListBeStockRec.items.get(pIndiceListaStock).Temperatura>0){
                    pListBeStockRec.items.get(pIndiceListaStock).Temperatura = pListBeStockRec.items.get(pIndiceListaStock).Temperatura;
                }else{
                    pListBeStockRec.items.get(pIndiceListaStock).Temperatura = 0.0;
                }

                pListBeStockRec.items.get(pIndiceListaStock).Regularizado = false;
                pListBeStockRec.items.get(pIndiceListaStock).Fecha_regularizacion = du.convierteFecha("01/01/1900");

                pListBeStockRec.items.get(pIndiceListaStock).Atributo_Variante_1 = "";
                pListBeStockRec.items.get(pIndiceListaStock).No_linea = pLineaOC;
                pListBeStockRec.items.get(pIndiceListaStock).Pallet_No_Estandar = false;
            }

        }catch (Exception e){
            mu.msgbox("Llena_Stock:"+e.getMessage());
        }
    }

    private void ContinuaGuardandoRecepcion(){

        progress.setMessage("Guardando recepción");
        progress.show();

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
                }else{

                    BeTransReDet.Fecha_vence =du.convierteFecha(cmbVenceRec.getText().toString().trim());
                    gl.gFechaVenceAnterior = cmbVenceRec.getText().toString().trim();

                    String FechaVence=BeTransReDet.Fecha_vence;
                    String FechaActual=du.getFechaActual();

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date strDate = sdf.parse(FechaVence);
                    Date strDateNow = sdf.parse(FechaActual);

                    if (strDate.getTime()  <=  strDateNow.getTime() ) {
                        msgValidaFechaVence("La fecha de vencimiento del producto "+BeProducto.Codigo+ " es igual o menor a la fecha de hoy. ¿Desea ingresar un producto ya vencido?");
                    }else{
                        DespuesDeValidarCantidad();
                    }
                }
            }else{
                //#ejc20210611: Definir fecha vence por defecto null
                BeTransReDet.Fecha_vence =du.convierteFecha("01/01/1900");
                DespuesDeValidarCantidad();
            }

        }catch (Exception ex){
            progress.cancel();
            mu.msgbox("ContinuaGuardandoRecepcion: "+ex.getMessage());
        }finally{
            //progress.cancel();
        }
    }

    @SuppressWarnings("unused")
    private void DespuesDeValidarCantidad(){

        int vIndice=-1;
        String Resultado = "";

        clsBeTrans_re_detList auxListTransRecDet;

        try{

            if (!progress.isShowing()) progress.show();
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

            int I;

            auxListTransRecDet = new clsBeTrans_re_detList();
            auxListTransRecDet.items = stream(pListTransRecDet.items).where(c->c.IdRecepcionDet == pIdRecepcionDet).toList();

            //Esto es el equivalente a ReDim en .net
            if (auxListTransRecDet.items!=null)
            {
                //#EJC20200325: Aquí da error en algunas ocasiones

                try{

                    //#AT20220407 Se agregá IdOrdenCompraEnc y Det a clsBeTrans_re_detList
                    for (int i=0; i < auxListTransRecDet.items.size(); i++) {
                        auxListTransRecDet.items.get(i).IdOrdenCompraDet = pIdOrdenCompraDet;
                        auxListTransRecDet.items.get(i).IdOrdenCompraEnc = pIdOrdenCompraEnc;
                    }

                    gl.gBeRecepcion.Detalle.items = new ArrayList<>();
                    gl.gBeRecepcion.Detalle.items.addAll(auxListTransRecDet.items);

                }catch (Exception ex)
                {
                    mu.msgbox("#20200325_0338AM: When redim array -> "+ex.getMessage());
                }

            }

            if (gl.gBeRecepcion.DetalleParametros.items!=null){
                gl.gBeRecepcion.DetalleParametros.items.addAll(plistBeReDetParametros.items);
            }

            if (plistBeReDetParametros.items!=null){

                //#GT 20082021: omiti el Objects porque gl.gBeRecepcion.DetalleParametros.items da error de no inicializado!
                //Objects.requireNonNull(gl.gBeRecepcion.DetalleParametros.items).addAll(plistBeReDetParametros.items);
                gl.gBeRecepcion.DetalleParametros.items = stream(plistBeReDetParametros.items).toList();

                }

            if (pListBeStockRec.items.size()==0){
                mu.msgbox("No se guardó el stock, no se puede continuar");
                return;
            }

            //Productos_Pallet

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

                String alert1 = "Seleccione una opción para imprimir";
                String alert2 = "(" +  MensajeAdicionalParaImpresion + ")" + "\n" + "\n" + alert1;

                if (!MensajeAdicionalParaImpresion.isEmpty()){
                    msgAskImprimir(alert2);
                }else{
                    msgAskImprimir(alert1);
                }

            }else{
                Actualiza_Valores_Despues_Imprimir(true);
            }

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
                if (!imprimirDesdeBoton){
                    msgAskImprimir("Imprimir código producto");
                }
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }
        }

    }

    private void Actualiza_Valores_Despues_Imprimir(boolean salir){

        try{
            //EJC20210125: Actualiza valores de la OC después imprimir
            switch (gl.TipoOpcion){

                case 1:
                    progress.setMessage("Actualizando valores OCDet");
                    progress.show();
                    beTransOCDet =new clsBeTrans_oc_det();
                    beTransOCDet.IdOrdenCompraEnc = pIdOrdenCompraEnc;
                    beTransOCDet.IdOrdenCompraDet = pIdOrdenCompraDet;
                    if (salir) execws(18);
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

            progress.cancel();

            LayoutInflater inflater = getLayoutInflater();
            View vistaDialog = inflater.inflate(R.layout.impresion_cantidad, null, false);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            cmbCantidad = (Spinner) vistaDialog.findViewById(R.id.cmbCantidad);
            setHandlersImpresion();
            dialog.setView(vistaDialog);

            dialog.setCancelable(false);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg + "\n\nImpresora: " + gl.MacPrinter);
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Código", (dialog1, which) -> {
                progress.setMessage("Imprimiendo Código");
                progress.show();
                Imprimir_Codigo_Barra_Producto(CantCopias);
                progress.cancel();
            });

            dialog.setNegativeButton("Licencia", (dialog12, which) -> {
                progress.setMessage("Imprimiendo Licencia");
                progress.show();
                Imprimir_Licencia(CantCopias);
                progress.cancel();
            });

            dialog.setNeutralButton("Salir", (dialog13, which) -> {
                if (!imprimirDesdeBoton){
                    progress.setMessage("Actualizando valores D.I.");
                    progress.show();
                    Actualiza_Valores_Despues_Imprimir(true);
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object()
            {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void setHandlersImpresion() {
        Integer[] cantidad = {1,2,4,6,8,10};
        cmbCantidad.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,cantidad));

        cmbCantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CantCopias = Integer.valueOf(cmbCantidad.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });
    }

    private void Imprimir_Codigo_Barra_Producto(int Copias){
        
        try{

            //EJC20210112: Impresión de barras.
            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);

            if (!printerIns.isConnected()){
                printerIns.open();
            }

            if (printerIns.isConnected()){

                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);

                String zpl="";

                if (BeProducto.IdTipoEtiqueta==1){
                        zpl = String.format("^XA \n" +
                        "^MMT \n" +
                        "^PW700 \n" +
                        "^LL0406 \n" +
                        "^LS0 \n" +
                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS \n" +
                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                        "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS \n" +
                        "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                        "^FT670,367^A0I,25,24^FH^FDTOMWMS Codigo de Producto^FS \n" +
                        "^FO2,340^GB670,0,14^FS \n" +
                        "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                        "^FD%4$s^FS \n" +
                        "^PQ1,0,1,Y " +
                        "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                        BeProducto.Codigo+" - "+BeProducto.Nombre,
                        BeProducto.Codigo_barra);
                }else if (BeProducto.IdTipoEtiqueta==2){
                    zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW600\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT440,90^A0I,28,30^FH^FD%1$s^FS\n" +
                                        "^FT560,90^A0I,26,30^FH^FDBodega:^FS\n" +
                                        "^FT440,125^A0I,28,30^FH^FD%2$s^FS\n" +
                                        "^FT560,125^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                        "^BY2,3,160^FT550,200^BCI,,Y,N\n" +
                                        "^FD%3$s^FS\n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT560,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                        "^FO2,440^GB670,14,14^FS\n" +
                                        "^FT560,470^A0I,25,24^FH^FDTOMWMS  Codigo de Producto^FS\n" +
                                        "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                                        gl.gNomEmpresa,
                                        BeProducto.Codigo_barra,
                                        BeProducto.Codigo+" - "+BeProducto.Nombre);

                }else if (BeProducto.IdTipoEtiqueta==4) {
                                    zpl = String.format("^XA\n" +
                                    "^MMT\n" +
                                    "^PW812\n" +
                                    "^LL609\n" +
                                    "^LS0\n" +
                                    "^FT440,90^A0I,28,30^FH^FD%1$s^FS\n" +
                                    "^FT560,90^A0I,26,30^FH^FDBodega:^FS\n" +
                                    "^FT440,125^A0I,28,30^FH^FD%2$s^FS\n" +
                                    "^FT560,125^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                    "^BY3,3,160^FT550,200^BCI,,Y,N\n" +
                                    "^FD%3$s^FS\n" +
                                    "^PQ1,0,1,Y \n" +
                                    "^FT600,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                    "^FO2,440^GB670,14,14^FS\n" +
                                    "^FT600,470^A0I,25,24^FH^FDTOMWMS Codigo de Producto^FS\n" +
                                    "^XZ", gl.CodigoBodega + "-" + gl.gNomBodega,
                                    gl.gNomEmpresa,
                                    BeProducto.Codigo_barra,
                                    BeProducto.Codigo + " - " + BeProducto.Nombre);
                 }

                if (!zpl.isEmpty()){
                    if (Copias > 0) {
                        for (int i = 0; i < Copias; i++ ) {
                            zPrinterIns.sendCommand(zpl);
                        }
                    }
                }else{
                    msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                }

                //#EJC20220309: Que pasa si no se cierra la conexión BT?, será más rápida la próxima impresión?
                //Thread.sleep(500);
                // Close the connection to release resources.
                //printerIns.close();

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

            if (!imprimirDesdeBoton){
                msgAskImprimir("Imprimir código de barra del producto");
            }
            //Solo voy a llamar a esta opcióm al salir.
            //Actualiza_Valores_Despues_Imprimir();

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
                if (!imprimirDesdeBoton){
                    msgAskImprimir("Imprimir código producto");
                }
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }
        }finally {
            //progress.cancel();
        }
    }

    private void Imprimir_Licencia(int Copias){

        try{

            if (!txtNoLP.getText().toString().isEmpty()){
                pNumeroLP = txtNoLP.getText().toString();
            }

            if(pNumeroLP.isEmpty() || pNumeroLP.equals("")){

                msgbox("No se puede imprimir la licencia porque no está configurada como automática");

                if (!imprimirDesdeBoton){
                    progress.setMessage("Actualizando valores D.I.");
                    progress.show();
                    Actualiza_Valores_Despues_Imprimir(true);
                }
                return;
            }

            //EJC_20210112: Impresión de barras.
            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);

            if (!printerIns.isConnected()){
                printerIns.open();
            }

            if (printerIns.isConnected()){

                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);
                //zPrinterIns.sendCommand("! U1 setvar \"device.languages\" \"zpl\"\r\n");

                String zpl="";

                if (BeProducto.IdTipoEtiqueta==1){

                    zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW700 \n" +
                                        "^LL0406 \n" +
                                        "^LS0 \n" +
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
                                        "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                        BeProducto.Codigo+" - "+BeProducto.Nombre,
                                        "$"+pNumeroLP);

                }else if (BeProducto.IdTipoEtiqueta==2){
                    //#CKFK 20210804 Modificación de la impresion del LP para el tipo de etiqueta 2,
                    //Dado que la descripción salía muy pequeña
                    zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW600\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT440,20^A0I,28,30^FH^FD%1$s^FS\n" +
                                        "^FT560,20^A0I,26,30^FH^FDBodega:^FS\n" +
                                        "^FT440,55^A0I,28,30^FH^FD%2$s^FS\n" +
                                        "^FT560,55^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                        "^FT560,100^A0I,90,100^FH^FD%3$s^FS\n" +
                                        "^BY3,3,160^FT550,200^BCI,,N,N\n" +
                                        "^FD%3$s^FS\n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT560,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                        "^FO2,440^GB670,14,14^FS\n" +
                                        "^FT560,470^A0I,25,24^FH^FDTOMWMS  No. Licencia^FS\n" +
                                        "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                                        gl.gNomEmpresa,
                                        "$"+pNumeroLP,
                                        BeProducto.Codigo+" - "+BeProducto.Nombre);

                }else if (BeProducto.IdTipoEtiqueta==4){
                    zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW812 \n" +
                                        "^LL0630 \n" +
                                        "^LS0 \n" +
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
                                        "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                        BeProducto.Codigo+" - "+BeProducto.Nombre,
                                        "$"+pNumeroLP);
                }

                if (!zpl.isEmpty()){
                    if (Copias > 0) {
                        for (int i = 0; i < Copias; i++ ) {
                            zPrinterIns.sendCommand(zpl);
                        }
                    }
                }else{
                    msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                }

                //#EJC20220309: Que pasa si no se cierra la conexión BT?, será más rápida la próxima impresión?
                //Thread.sleep(500);
                // Close the connection to release resources.
                //printerIns.close();

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

            if (!imprimirDesdeBoton){
                msgAskImprimir("¿Imprimir licencia?");
            }
            //Solo voy a llamar a esta opción cuando seleccione al salir
           // Actualiza_Valores_Despues_Imprimir();

        }catch (Exception e){
            progress.cancel();
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir la licencia del producto. No existe conexión a la impresora: "+ gl.MacPrinter);
                if (!imprimirDesdeBoton){
                    msgAskImprimir("Imprimir licencia del producto");
                }
            }else{
                mu.msgbox("Imprimir_licencia: "+e.getMessage());
            }
        }finally {
            //progress.cancel();
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

            if (!progress.isShowing()) progress.show();
            progress.setMessage("Llenando detalle recepción...");

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

                    clsBeProducto_Presentacion bePresentacion;

                    bePresentacion = stream(BeProducto.Presentaciones.items).where(c->c.IdPresentacion==IdPreseSelect).first();

                    BeTransReDet.Presentacion = bePresentacion;

                    if(!bePresentacion.EsPallet){
                        Factor = bePresentacion.Factor;
                    }else{
                        bePresentacion.CamasPorTarima=CamasPorTarima;
                        bePresentacion.CajasPorCama=CajasPorCama;
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
                BeTransReDet.Pallet_No_Estandar=(chkPalletNoEstandar.isChecked());

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
            progress.cancel();
            mu.msgbox("Llena_Detalle_Recepcion_Nueva:"+e.getMessage());
        }finally{
            //progress.hide();
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

                if (gl.gBeRecepcion.IdBodega == -1){
                    gl.gBeRecepcion.IdBodega=gl.IdBodega;
                }

                BeTransReDet.Presentacion = new clsBeProducto_Presentacion();

                if (IdPreseSelect>0){

                    BeTransReDet.Presentacion.IdPresentacion = IdPreseSelect;
                    BeTransReDet.Presentacion.Factor = BeProducto.Presentacion.Factor;
                    BeTransReDet.IdPresentacion = IdPreseSelect;

                    clsBeProducto_Presentacion bePresentacion;

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
                BeTransReDet.Pallet_No_Estandar=(chkPalletNoEstandar.isChecked());

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
            }else{
                BeTransReDet.Costo = 0;
                BeTransReDet.Costo_Oc =0;
            }
            BeTransReDet.Costo_Estadistico = 0;

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

            clsBeTrans_oc_det_loteList detalle_lotes;
            detalle_lotes=gl.gBeOrdenCompra.DetalleLotes;

            if (detalle_lotes.items != null ){

                if (detalle_lotes.items.size()>0){

                    if (gl.gBeOrdenCompra.IdTipoIngresoOC == dataContractDI.Orden_De_Produccion){


                        try {
                            BeDetalleLotes = stream(detalle_lotes.items)
                                    .where(c -> {
                                        try {
                                            return c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                                    c.No_linea == BeOcDet.No_Linea &&
                                                    c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                                    c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                                    c.Fecha_vence.equals(du.convierteFecha(cmbVence.getSelectedItem().toString())) &&
                                                    c.Ubicacion.equals(ubiDetLote) &&
                                                    c.Lote.equals(cmbLote.getSelectedItem().toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return false;
                                    })
                                    .first();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    if (gl.gBeOrdenCompra.IdTipoIngresoOC == dataContractDI.Devolucion_Venta){

                        try {
                            BeDetalleLotes = stream(detalle_lotes.items)
                                    .where(c -> {
                                        try {
                                            return c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                                    c.No_linea == BeOcDet.No_Linea &&
                                                    c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                                    c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                                    c.Fecha_vence.equals(du.convierteFecha(cmbVence.getSelectedItem().toString())) &&
                                                    c.Lote.equals(cmbLote.getSelectedItem().toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return false;
                                    })
                                    .first();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    if (BeDetalleLotes != null){
                        if (BeTransReDet.IdPresentacion!=0){
                            BeDetalleLotes.Cantidad_recibida = Integer.parseInt(txtCantidadRec.getText().toString())*Factor;
                        }else{
                            BeDetalleLotes.Cantidad_recibida = Integer.parseInt(txtCantidadRec.getText().toString());
                        }

                        BeDetalleLotes.IsNew = true;
                    }
                }

            }else{
                BeDetalleLotes = null;
            }

            listaStockPalletsNuevos = new clsBeStock_recList();
            listaProdPalletsNuevos = new clsBeProducto_palletList();

            if (pListBeStockRec.items!=null){

                clsBeStock_recList listaStock = new clsBeStock_recList();
                clsBeProducto_palletList listaProdPallets = new clsBeProducto_palletList();

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
                    progress.cancel();
                    return;
                }

                for( clsBeStock_rec BeStockRec : listaStock.items){

                    BeStockRec.IsNew = true;

                    BeStockRec.ProductoEstado = new  clsBeProducto_estado();

                    if (IdEstadoSelect>0){

                        BeStockRec.ProductoEstado.IdEstado = IdEstadoSelect;
                        BeStockRec.IdProductoEstado = IdEstadoSelect;

                        if (LProductoEstado.items.size()> 0){

                            clsBeProducto_estado BeEstado;

                            BeEstado = stream(LProductoEstado.items).where(c->c.IdEstado ==IdEstadoSelect).first();

                            //#CKFK20220107 Busco la ubicación en base al estado del producto
                            int vIdUbicacion = 0;

                            if (BeEstado.IdUbicacionBodegaDefecto>0){
                                /*BeStockRecNuevaRec = BeStockRec;
                                vCantNuevaRec = vCant;
                                vFactorNuevaRec = Factor;
                                BeStockRecNuevaRec.IdUbicacion =  BeEstado.IdUbicacionBodegaDefecto;*/
                                vIdUbicacion = BeEstado.IdUbicacionBodegaDefecto;
                            }else if (BeEstado.IdUbicacionDefecto>0){
                                BeStockRecNuevaRec = BeStockRec;
                                /*vCantNuevaRec = vCant;
                                vFactorNuevaRec = Factor;
                                BeStockRecNuevaRec.IdUbicacion =  BeEstado.IdUbicacionDefecto;*/
                                vIdUbicacion = BeEstado.IdUbicacionDefecto;
                            }else{
                                if (BeEstado.Danado){
                                    if (!gl.gUbicMerma.isEmpty()){
                                        vIdUbicacion = Integer.valueOf(gl.gUbicMerma);
                                    }
                                }
                            }

                            //#CKFK20220106 Agregué validación gPriorizar_UbicRec_Sobre_UbicEst
                            //Si el parámetro está en true se manda la ubicación de la tarea de recepción
                            //en caso contrario se manda la ubicación del estado o la ubicación de merma
                            // si no existe ninguna de ellas se manda la ubicación de la tarea de recepción
                            //#AT 20220330 (CEALSA) Erik me pidio que pusiera esta validacion aca
                            if (gl.gBeRecepcion.Escanear_rec_ubic) {
                                BeStockRec.IdUbicacion = gl.recepcionIdUbicacion;
                            } else {
                                if (gl.gPriorizar_UbicRec_Sobre_UbicEst) {
                                    BeStockRec.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                                } else {
                                    //Si el parametro está en false se manda la ubicación del estado
                                    //Si la ubicación del estado no existe
                                    //Se manda la ubicación de la tarea de recepción
                                    if (vIdUbicacion == 0) {
                                        BeStockRec.IdUbicacion = gl.gBeRecepcion.IdUbicacionRecepcion;
                                    } else {
                                        BeStockRec.IdUbicacion = vIdUbicacion;
                                    }
                                }
                            }

                        }

                    }else{
                        msgbox("Debe seleccionar el estado del producto " + BeProducto.Codigo);
                        return;
                    }

                    Continua_Guardando_Rec_Nueva(BeStockRec,Factor,vCant);

                    if (gl.mode==1){
                        pListTransRecDet.items.add(BeTransReDet);
                    }
                }

                if (listaStockPalletsNuevos!=null){
                    if (listaStockPalletsNuevos.items!=null){
                        pListBeStockRec.items.addAll(listaStockPalletsNuevos.items);
                    }
                }

                if (listaStockPalletsNuevos!=null){
                    if (listaStockPalletsNuevos.items!=null){
                        pListBeProductoPallet.items.addAll(listaProdPalletsNuevos.items);
                    }
                }

            }

            BeTransReDet.MotivoDevolucion = new  clsBeMotivo_devolucion();

            BeTransReDet.Atributo_Variante_1 = "";

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("Continua:"+e.getMessage());
        }finally{
            //progress.hide();
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
                    if (Factor == 0) Factor=1;
                    BeStockRec.Cantidad = 1 * Factor;
                }

            }else{
                BeStockRec.Cantidad = vCant;
                //#EJC20201217:Si es UMBA y el LicPlate no es vacío asignar.
                if (!txtNoLP.getText().toString().isEmpty()){
                    BeStockRec.Lic_plate= txtNoLP.getText().toString();
                }else{
                    if (!pNumeroLP.equals("") && !pNumeroLP.isEmpty()){
                        txtNoLP.setText(pNumeroLP);
                        BeStockRec.Lic_plate= txtNoLP.getText().toString();
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
            BeStockRec.Pallet_No_Estandar = (chkPalletNoEstandar.isChecked());

            if (Escaneo_Pallet){
                BeStockRec.Lic_plate = BeINavBarraPallet.Codigo_barra;
                BeStockRec.Uds_lic_plate = BeINavBarraPallet.Cantidad_UMP;
                BeStockRec.No_bulto = 0;
                BeTransReDet.Lic_plate = BeINavBarraPallet.Codigo_barra;
            }else{
                if (BeTransReDet.Lic_plate!=null){
                    if(BeStockRec.Lic_plate.equals("")){
                        if (!txtNoLP.getText().toString().isEmpty()){
                            BeStockRec.Lic_plate= txtNoLP.getText().toString();
                        }else{
                            if (!pNumeroLP.equals("") && !pNumeroLP.isEmpty()){
                                txtNoLP.setText(pNumeroLP);
                                BeStockRec.Lic_plate= txtNoLP.getText().toString();
                            }
                        }
                    }
                    BeTransReDet.Lic_plate = BeStockRec.Lic_plate;
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
                            int vIndiceLista;

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
                        if(BeStockRec.Lic_plate.equals("")){
                            if (!txtNoLP.getText().toString().isEmpty()){
                                BeStockRec.Lic_plate= txtNoLP.getText().toString();
                            }else{
                                if (!pNumeroLP.equals("") && !pNumeroLP.isEmpty()){
                                    txtNoLP.setText(pNumeroLP);
                                    BeStockRec.Lic_plate= txtNoLP.getText().toString();
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
                        BeProdPallet.IsNew = gl.mode == 1;

                        if (listaProdPalletsNuevos.items == null) {
                            listaProdPalletsNuevos.items = new ArrayList<>();
                        }
                        listaProdPalletsNuevos.items.add(BeProdPallet);

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
                BeProdPallet.User_agr = gl.OperadorBodega.IdOperadorBodega+"";
                BeProdPallet.User_mod = gl.OperadorBodega.IdOperadorBodega+"";
                BeProdPallet.IsNew = gl.mode == 1;

                if (listaProdPalletsNuevos.items == null) {
                    listaProdPalletsNuevos.items = new ArrayList<>();
                }
                listaProdPalletsNuevos.items.add(BeProdPallet);

            }

        }catch (Exception e){
            mu.msgbox("Terminar_Guardar_Detalle_Recepcion_Nueva: "+e.getMessage());
        }
    }

    private void Pallet_Correcto(){

        try{

            execws(14);

        }catch (Exception e){
            mu.msgbox("Pallet_Correcto:"+e.getMessage());
        }

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

    private void Valida_Cantidad_Recibida(){

        double Cantidad;

        //#EJC20210612
        MensajeAdicionalParaImpresion ="";

        try{

            progress.setMessage("Validando cantidad");

            if (gl.TipoOpcion==2){
                return;
            }

            if (txtCantidadRec.getText().toString().isEmpty()){
                Cantidad =0;
            }else{
                Cantidad = Double.parseDouble(txtCantidadRec.getText().toString());
            }

            if (gl.mode==2){
                Cant_Pendiente =mu.round(Cant_Pendiente + Cant_Recibida_Anterior,gl.gCantDecCalculo);
            }

            //#CKFK 20211129 Agregué validación para saber si la cantidad ingresada es mayor a la
            // cantidad permitida en la ubicación
            if (gl.gBeOrdenCompra.IdTipoIngresoOC == dataContractDI.Orden_De_Produccion){
                if (ubiDetLote!=null) {
                    if (!ubiDetLote.isEmpty()) {
                        if (DifCantUbic<Cantidad){
                            msgbox("Excede la cantidad permitida en la ubicación  " + ubiDetLote);
                            return;
                        }
                    }
                }
            }

            if (Cant_Pendiente > Cantidad){
                msgValidaCantidad("La cantidad "+Cantidad+" ingresada es correcta para el producto "+BeProducto.Codigo);
            }else if(Cant_Pendiente < Cantidad){
                 msgExcedeCantidad();
            }else if (BeProducto.Control_vencimiento){
                valida_fecha_vencimiento();
            }else{
                DespuesDeValidarCantidad();
            }

        }catch (Exception e){
            mu.msgbox("ValidaCantidadRecibida"+e.getMessage());
        }

    }

    private void msgValidaProductoPallet(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog1, which) -> Guardar_Pallet());

            dialog.setNegativeButton("No", (dialog12, which) -> {
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

            dialog.setPositiveButton("Si", (dialog1, which) -> {
                DespuesDeValidarCantidad();
                //Continua_Llenando_Detalle_Recepcion_Nueva();
            });

            dialog.setNegativeButton("No", (dialog12, which) -> {
                progress.cancel();
                cmbVenceRec.requestFocus();
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaFechaVence"+e.getMessage());
        }
    }

    private void msgExcedeCantidad() {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("Excede la cantidad solicitada. ¿Recibir de todas formas esta cantidad?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //DespuesDeValidarCantidad();
                valida_fecha_vencimiento();
            });

            dialog.setNegativeButton("No", (dialog1, which) -> progress.cancel());

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgValidaCantidad(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg );

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog1, which) -> {
                valida_fecha_vencimiento();
                //DespuesDeValidarCantidad();
            });

            dialog.setNegativeButton("No", (dialog12, which) -> progress.cancel());

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaCantidad"+e.getMessage());
        }
    }

    public void ChangeDate_p(View view) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String la_fecha = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        String otra_fecha = null;
                        try {
                            otra_fecha = du.convierteFecha(la_fecha);
                            txtvalor_f.setText(otra_fecha);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //txtvalor_f.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
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

                //#EJC20210611: Calculo de cantidad en UMBAS para BYB.
                double vCantidad=0;

                if (!txtCantidadRec.getText().toString().isEmpty()){
                    if (IdPreseSelect!=-1){
                        vCantidad = BeProducto.Presentacion.getFactor()* Double.parseDouble(txtCantidadRec.getText().toString());
                    }else{
                        vCantidad = Double.parseDouble(txtCantidadRec.getText().toString());
                    }
                }

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
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador",gl.IdOperador,
                                "pIdBodega",gl.IdBodega);
                        //#EJC20210504: Optimizado, buscar la resolución asociada por el operador y bodega.
//                        callMethod("Get_Nuevo_Correlativo_LicensePlate","pIdEmpresa",gl.IdEmpresa,
//                                "pIdBodega",gl.IdBodega,"pIdPropietario",BeProducto.Propietario.IdPropietario,
//                                "pIdProducto",BeProducto.IdProducto);
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
                        //#AT 20220328 Si chkPresentacion no esta marcado, IdPresentación = 0
                        if (!chkPresentacion.isChecked() && chkPresentacion.getVisibility() == View.VISIBLE) {
                            pListBeStockRec.items.get(0).IdPresentacion = 0;
                            pListBeStockRec.items.get(0).Presentacion.IdPresentacion = 0;
                            pListBeStockRec.items.get(0).Cantidad = Double.valueOf(txtCantidadRec.getText().toString());

                            gl.gBeRecepcion.Detalle.items.get(0).IdPresentacion = 0;
                            gl.gBeRecepcion.Detalle.items.get(0).Presentacion.IdPresentacion = 0;
                            gl.gBeRecepcion.Detalle.items.get(0).Nombre_presentacion = "";

                        }

                        callMethod("Guardar_Recepcion",
                                "pRecEnc",gl.gBeRecepcion,
                                "pRecOrdenCompra",gl.gBeRecepcion.OrdenCompraRec,
                                "pListStockRecSer",pListBeStockSeRec.items,
                                "pListStockRec",pListBeStockRec.items,
                                "pListProductoPallet",listaProdPalletsNuevos.items,
                                "pLotesRec", BeDetalleLotes,
                                "pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.IdOperador,
                                "pIdResolucionLp",gl.IdResolucionLpOperador);
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
                        callMethod("Existe_Lp","pLic_Plate",pLp,
                                               "pIdBodega",gl.IdBodega,
                                               "pIdStock",(pListBeStockRec.items==null?0:pListBeStockRec.items.get(0).IdStockRec));
                        break;
                    case 25:

                        callMethod("Push_Recepcion_Produccion_To_NAV_For_BYB",
                                   "DocumentoUbicacion", ubiDetLote,
                                   "CodigoProducto",BeProducto.Codigo,
                                   "Cantidad", vCantidad,
                                   "IdRecepcionEnc",BeTransReDet.IdRecepcionEnc,
                                   "IdRecepcionDet",BeTransReDet.IdRecepcionDet,
                                   "pIdUsuario",gl.OperadorBodega.IdOperador,
                                   "pRespuesta", pRespuesta);
                        break;

                    case 26:

                        try {

                           if(BeTransReDet.Fecha_vence.isEmpty()){
                               //#EJC20210611: Definir fecha vence por defecto null
                               BeTransReDet.Fecha_vence =du.convierteFecha("01/01/1900");
                           }

                           if (gl.gBeOrdenCompra.getIdTipoIngresoOC()==dataContractDI.Ingreso){

                               //#EJC20220308: Si la recepción es en presentación enviar pres sino, UMBAS.
                               String vNombreUM ="";

                               if (BeTransReDet.IdPresentacion==0){
                                   vNombreUM=BeOcDet.Nombre_unidad_medida_basica;
                               }else{
                                   vNombreUM=BeTransReDet.Nombre_presentacion;
                               }

                               callMethod("Push_Recepcion_Pedido_Compra_To_NAV_For_BYB",
                                       "DocumentoIngreso", gl.gBeOrdenCompra.Referencia,
                                       "DocumentoRecepcion",gl.gBeOrdenCompra.No_Documento_Recepcion_ERP,
                                       "NoLinea", BeTransReDet.No_Linea,
                                       "CodigoProducto",BeProducto.Codigo,
                                       "Cantidad", BeTransReDet.cantidad_recibida,
                                       "NoLote",   BeTransReDet.Lote,
                                       "FechaVence",BeTransReDet.Fecha_vence,
                                       "NomUnidadMedida",vNombreUM,
                                       "IdRecepcionEnc",BeTransReDet.IdRecepcionEnc,
                                       "IdRecepcionDet",BeTransReDet.IdRecepcionDet,
                                       "pIdUsuario",gl.OperadorBodega.IdOperador,
                                       "pRespuesta", pRespuesta);

                           }else if (gl.gBeOrdenCompra.getIdTipoIngresoOC()==dataContractDI.Devolucion_Venta){

                               callMethod("Push_Recepcion_Devolucion_Venta_To_NAV_For_BYB",
                                       "DocumentoIngreso", gl.gBeOrdenCompra.Referencia,
                                       "DocumentoRecepcion",gl.gBeOrdenCompra.No_Documento_Recepcion_ERP,
                                       "NoLinea", BeTransReDet.No_Linea,
                                       "CodigoProducto",BeProducto.Codigo,
                                       "Cantidad", BeTransReDet.cantidad_recibida,
                                       "NoLote",   BeTransReDet.Lote,
                                       "FechaVence",BeTransReDet.Fecha_vence,
                                       "NomUnidadMedida",BeTransReDet.Nombre_unidad_medida,
                                       "IdRecepcionEnc",BeTransReDet.IdRecepcionEnc,
                                       "IdRecepcionDet",BeTransReDet.IdRecepcionDet,
                                       "pIdUsuario",gl.OperadorBodega.IdOperador,
                                       "pRespuesta", pRespuesta)
                               ;
                           }else if (gl.gBeOrdenCompra.getIdTipoIngresoOC()==dataContractDI.Transferencia_de_Ingreso){

                               callMethod("Push_Recepcion_Transferencias_Ingreso_To_NAV_For_BYB",
                                       "DocumentoIngreso", gl.gBeOrdenCompra.Referencia,
                                       "DocumentoRecepcion",gl.gBeOrdenCompra.No_Documento_Recepcion_ERP,
                                       "NoLinea", BeTransReDet.No_Linea,
                                       "CodigoProducto",BeProducto.Codigo,
                                       "Cantidad", BeTransReDet.cantidad_recibida,
                                       "NoLote",   BeTransReDet.Lote,
                                       "FechaVence",BeTransReDet.Fecha_vence,
                                       "NomUnidadMedida",BeTransReDet.Nombre_unidad_medida,
                                       "IdRecepcionEnc",BeTransReDet.IdRecepcionEnc,
                                       "IdRecepcionDet",BeTransReDet.IdRecepcionDet,
                                       "pIdUsuario",gl.OperadorBodega.IdOperador,
                                       "pRespuesta", pRespuesta)
                               ;
                           }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        break;
                    case 27://Obtiene el Tipo de Etiqueta del producto
                        callMethod("Get_Tipo_Etiqueta_By_IdTipoEtiqueta","pBeTipo_etiqueta",pBeTipo_etiqueta);
                        break;

                    case 28://Valida si la serie del producto ya existe
                        callMethod("Existe_Serie",
                                "pSerie", pSerie,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 29:
                        callMethod("Guardar_Producto_Imagen","pBeProductoImagen",BeProductoImagen);
                        break;
                    case 30:
                        callMethod("Get_All_Producto_Imagen","pIdProducto",BeProducto.IdProducto);
                        break;
                }

            }catch (Exception e){
                progress.hide();
                mu.msgbox(e.getClass()+"WebServiceHandler:"+e.getMessage());
            }finally{
                progress.cancel();
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
                    processNuevoLPA();
                    //#EJC20210504: Refactor por resolución LP
                    //processNuevoLP();
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
                    process_Recepcion_Produccion_Nav_BYB();
                    break;
                case 26:
                    process_Recepcion_Compra_Nav_BYB();
                    break;
                case 27:
                    processTipoEtiqueta();
                    break;
                case 28:
                    processExisteSerie();
                    break;
                case 29:
                    processEnviarFoto();
                    break;
                case 30:
                    processGetFotosProducto();
                    break;
            }

        } catch (Exception e) {
            msgbox(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName() + "wsCallBack: case(" + ws.callback + ") " + e.getMessage());
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


            //GT04042022: focus a cantidad
            txtCantidadRec.requestFocus();
            txtCantidadRec.selectAll();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processCodigosBarra:s"+e.getMessage());
        }

    }

    private void processTieneParametrosPersonalizados(){

        int Etapa =0;

        try{

            Pperzonalizados=false;
            PCap_Manu=false;
            PGenera_lp=false;
            PTiene_Ctrl_Temp=false;
            PTiene_Pres=false;

            Etapa ++;

            pListBEProductoParametro = xobj.getresult(clsBeProducto_parametrosList.class,"Get_All_ProductoParametros_By_IdProducto_HH");

            Etapa ++;

            if (pListBEProductoParametro!=null){
                if (pListBEProductoParametro.items!=null){
                    Pperzonalizados = true;
                    contar_parametros_p = pListBEProductoParametro.items.size();
                }
            }

            Etapa ++;

            if (BeProducto.Fechamanufactura&&BeProducto.Materia_prima){
                PCap_Manu=true;
            }

            Etapa ++;

            if (BeProducto.Capturar_aniada){
                PCap_Anada = true;
            }

            Etapa ++;

            if (BeProducto.Genera_lp){
                PGenera_lp=true;
            }

            Etapa ++;

            //#GT28022022_1706: control_peso es la validación a tomar para validar peso, pero este es el estadístico
             if (BeProducto.Peso_recepcion){
                PTiene_Ctrl_Peso = true;
            }

            Etapa ++;

            if (BeProducto.Temperatura_recepcion){
                PTiene_Ctrl_Temp = true;
            }

            Etapa ++;

            if (BeProducto.Serializado){
                PTiene_PorSeries=true;
            }

            Etapa ++;

            if (stream(gl.gpListDetalleOC.items).where(c -> c.IdProductoBodega == pIdProductoBodega
                    && c.IdOrdenCompraDet == pIdOrdenCompraDet).select(c -> c.IdPresentacion).first()>0){
                if (BeProducto.Presentaciones!=null){
                    if (BeProducto.Presentaciones.items!=null){
                        PTiene_Pres=true;
                    }
                }
            }

            Etapa ++;

            if(Pperzonalizados||PCap_Manu||PCap_Anada||PGenera_lp||PTiene_Ctrl_Peso||PTiene_Ctrl_Temp||PTiene_PorSeries||PTiene_Pres){
                Etapa ++;
                Muestra_Propiedades_Producto();
            }else{
                Etapa ++;
                Guardar_Recepcion_Nueva();
            }

            Etapa ++;

        }catch (Exception e){
            mu.msgbox("ProcessTieneParametrosPersonalizados: "+e.getMessage() + "-" + Etapa);
        }

    }

    private clsBeResolucion_lp_operador nBeResolucion = null;

    private void processNuevoLPA(){


        try {

            if (nBeResolucion == null){
                nBeResolucion = new clsBeResolucion_lp_operador();
                nBeResolucion = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");
            }

            //toastlong("nuevo lp" + nBeResolucion.Correlativo_Actual);
               if (nBeResolucion !=null){

                   gl.IdResolucionLpOperador = nBeResolucion.IdResolucionlp;

                   float pLpSiguiente = nBeResolucion.Correlativo_Actual +1;
                   float largoMaximo = String.valueOf(nBeResolucion.Correlativo_Final).length();

                   int intLPSig = (int) pLpSiguiente;
                   int MaxL = (int) largoMaximo;

                   String str = String.valueOf(intLPSig);
                   StringBuilder sb = new StringBuilder();

                   for (int toPrepend= MaxL-str.length(); toPrepend>0; toPrepend--) {
                       sb.append('0');
                   }

                   sb.append(str);
                   String result = sb.toString();

                   pNumeroLP= nBeResolucion.Serie + result;

               }else{

                   toastlong("no hay LP");
                   gl.IdResolucionLpOperador =0;
               }

               if (gl.mode==1){
                   //#CKFK 20201229 Agregué esta condición de que si la barra tiene información se coloca eso como LP
                   if (!txtNoLP.getText().toString().isEmpty()){
                       if (txtLicPlate != null){
                           txtLicPlate.setText(txtNoLP.getText().toString().replace("$",""));

                       }
                   }else{
                       if (txtLicPlate != null){
                           txtLicPlate.setText(pNumeroLP);

                       }else{
                           clsBeTrans_oc_det_loteList ubic;
                           ubic=gl.gBeOrdenCompra.DetalleLotes;
                           List<clsBeTrans_oc_det_lote> BeUbicaciones;

                           //#CKFK20220306 Agregué esta validación para el License Plate
                           BeUbicaciones = new ArrayList<clsBeTrans_oc_det_lote>();

                           if (BeProducto.getControl_vencimiento() && VenceList.size()>0){

                               //#CKFK 20211030 Validé que ubic.items no fuera nulo
                               if (ubic.items!=null){
                                   BeUbicaciones = stream(ubic.items)
                                           .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                                   c.No_linea == BeOcDet.No_Linea &&
                                                   c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                                   c.IdOrdenCompraEnc == pIdOrdenCompraEnc &&
                                                   !c.Lic_Plate.isEmpty())
                                           .toList();
                               }

                           }else{
                               //#CKFK 20211030 Validé que ubic.items no fuera nulo
                               if (ubic.items!=null){
                                   BeUbicaciones = stream(ubic.items)
                                           .where(c -> c.IdProductoBodega  == BeProducto.IdProductoBodega &&
                                                   c.No_linea == BeOcDet.No_Linea &&
                                                   !c.Lic_Plate.isEmpty()  &&
                                                   c.IdOrdenCompraDet == pIdOrdenCompraDet &&
                                                   c.IdOrdenCompraEnc == pIdOrdenCompraEnc)
                                           .toList();
                               }
                           }

                           //#CKFK 20211030 Validé que BeUbicaciones no fuera nulo
                           if (BeUbicaciones!=null){
                               //#CKFK 20211030 Validé que BeUbicaciones.size() fuera mayor que 0
                               if (BeUbicaciones.size()==0){
                                   txtNoLP.setText(pNumeroLP);
                               }
                           }else{
                               txtNoLP.setText(pNumeroLP);

                           }

                       }
                   }
               }

            pBeTipo_etiqueta.IdTipoEtiqueta=BeProducto.IdTipoEtiqueta;

            execws(27);


        }catch (Exception e){
            mu.msgbox("processNuevoLP_RE: "+e.getMessage());
        }

    }

    private void processLicensePallet(){

        try {

            clsBeLicensePlatesList pListBeLicensePlate = xobj.getresult(clsBeLicensePlatesList.class, "Get_Licenses_Plates_By_IdRecepcionEnc");

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

    private void processMaxIdStockSeRec(){

        int MaxId;

        try{

            MaxId = xobj.getresult(Integer.class,"MaxIDStockSeRec");

            ObjNS.IdStockSeRec = MaxId + 1;
            ValidaParametrosDespuesSeRec();

        }catch (Exception e){
            mu.msgbox("processMaxIdStockSeRec: "+e.getMessage());
        }

    }

    private void processListDetParametros(){

        try{

            plistBeReDetParametros = xobj.getresult(clsBeTrans_re_det_parametrosList.class,"Get_All_Params_By_IdRecepcionEnc_And_IdRecepcion_Det_For_HH");

        }catch (Exception e){
            mu.msgbox("processListDetParametros"+e.getMessage());
        }

    }

    private void processMaxIdStockRec(){

        int IdMax;
        try {

            IdMax = xobj.getresult(Integer.class,"MaxIDStockRec");

            ObjS.IdStockRec = IdMax + 1;

            SigueValidandoParametros();

        }catch (Exception e){
            mu.msgbox("processMaxIdStockRec: "+e.getMessage());
        }

    }

    private void processAuxRec(){

        try{

            clsBeTrans_re_enc auxRec = xobj.getresult(clsBeTrans_re_enc.class, "Get_BeTransReEnc_By_IdREcepcionEnc_For_HH");

            if (auxRec !=null){
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

            boolean EsCorrecto;

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

            if (!txtNoLP.getText().toString().isEmpty()){
                vBeStockRec.Lic_plate = txtNoLP.getText().toString();
            }else {
                vBeStockRec.Lic_plate = xobj.getresult(String.class, "Get_Nuevo_Correlativo_LicensePlate_S");
            }

            Terminar_Guardar_Detalle_Recepcion_Nueva();

        }catch (Exception e){
            mu.msgbox("processNuevoLPS: "+e.getMessage());
        }

    }

    private void process_recepcion_compra_nav_byb(){

        //#EJC20210611:Cuando es recepción de compra en BYB
        //Se debe enviar a registrar la compra en el WS de NAV.
        //con el número de recepción.
        if (gl.gBeOrdenCompra.No_Documento_Recepcion_ERP != null){
            if (!gl.gBeOrdenCompra.No_Documento_Recepcion_ERP.isEmpty()){
                progress.setMessage("Registrando ingreso de compra en ERP");
                execws(26);
            }else{
                Imprime_Barra_Despues_Guardar();
            }
        }else{
            Imprime_Barra_Despues_Guardar();
        }

    }

    //Probando
    private void processGuardarRecNueva(){

        try{

            String Resultado;

            progress.show();
            progress.setMessage("Finalizando proceso de guardar recepción");

            //#EJC20210321_1223:Validar si no se obtuvo error en el procesamiento.
            if(!xobj.ws.xmlresult.contains("CustomError")){

                Resultado = xobj.getresult(String.class,"Guardar_Recepcion");

                if (Resultado!=null){
                    if (ubiDetLote!=null){
                        if (!ubiDetLote.isEmpty()){
                            //#EJC20210611:Si es un ingreso de órden de producción.
                            //Se obtiene un número de ubicación de almacen generado en NAV
                            //Esa ubicación se registra en la tabla: trans_oc_det_lote campo: ubicacion
                            //Se debe enviar a registrar en esa ubicación el producto recepcionado en línea.
                            progress.setMessage("Registrando lote en ubicación de ERP");
                            execws(25);
                        }else{
                            process_recepcion_compra_nav_byb();
                        }
                    }else{
                        process_recepcion_compra_nav_byb();
                    }

                }else{
                    progress.cancel();
                    mu.msgbox("No se pudo guardar la recepción");
                }

            }else{
                progress.cancel();
                mu.msgbox("No se pudo guardar la recepción:  " + ws.xmlresult);
            }

        }catch (Exception e){
            progress.hide();
            mu.msgbox("processGuardarRecNueva:"+e.getMessage());
        }finally {
            //progress.hide();
        }
    }

    private void processGuardarRecModif(){

        try{

            String Resultado;

            progress.show();
            progress.setMessage("Finalizando proceso de guardar recepción");

            Resultado = xobj.getresult(String.class,"GuardarRecepcionModif");

            if (!Resultado.isEmpty()){
                Imprime_Barra_Despues_Guardar();
            }else{
                progress.cancel();
                mu.msgbox("No se pudo guardar la recepción");
            }


        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processGuardarRecModif:"+e.getMessage());
        }finally {
            //progress.hide();
        }

    }

    private void processActualizaCantidadRecibida(){

        try {

            int vIndex;

            progress.setMessage("Actualizando cantidad recibida");
            progress.show();

            List AuxList = stream(gl.gpListDetalleOC.items).select(c -> c.IdOrdenCompraDet).toList();

            if (AuxList.size()>0){

                vIndex = AuxList.indexOf(beTransOCDet.IdOrdenCompraDet);

                //#AT20220329 Si no esta en marcado chkPresentacion
                //BeTransReDet.cantidad_recibida es =  al valor de txtCantidadRec / Factor
                if (!chkPresentacion.isChecked() && chkPresentacion.getVisibility() == View.VISIBLE) {
                    if (BeProducto.Presentaciones.items != null) {
                         BeTransReDet.cantidad_recibida = Double.valueOf(txtCantidadRec.getText().toString()) / BeProducto.Presentaciones.items.get(0).Factor;
                    }
                }

                if (gl.mode==1){
                    gl.gpListDetalleOC.items.get(vIndex).Cantidad_recibida += BeTransReDet.cantidad_recibida;
                }else{
                    //#CKFK 20210630 Calcular la cantidad recibida
                    gl.gpListDetalleOC.items.get(vIndex).Cantidad_recibida += BeTransReDet.cantidad_recibida - vCantAnteriorRec;
                }

            }

            gl.CantRec = beTransOCDet.Cantidad_recibida;

            gl.Carga_Producto_x_Pallet=false;

            doExit();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processActualizaCantidadRecibida"+e.getMessage());
        }

        progress.hide();
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
        msgAskExit();
    }

    public void BotonMostrar(View view){
        BotonIrGuardarParametros();
    }

    private void msgAskExit() {
        try{

        /*    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + "Está seguro de salir" + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog1, which) -> doExit());

            dialog.setNegativeButton("No", (dialog12, which) -> {
            });

            dialog.show();
*/

            //#GT10032022: set en el boton Si para facilitar el ENTER del teclado
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(R.string.app_name);

            // set dialog message
            alertDialogBuilder
                    .setMessage("¿" + "Está seguro de salir" + "?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            doExit();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(getResources().getColor(R.color.colorAccent));
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(getResources().getColor(R.color.colorAccent));
            //pbutton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            pbutton.setPadding(0, 10, 10, 0);
            //pbutton.setTextColor(Color.WHITE);

            pbutton.setFocusable(true);
            pbutton.setFocusableInTouchMode(true);
            pbutton.requestFocus();


        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void msgSinUbicaciones(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Aceptar", (dialog1, which) -> doExit());

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void msgAskExisteLp(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", (dialog12, which) -> {

                if (guardando_recepcion){

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

                }else{
                    if (BeProducto.Control_vencimiento){
                        cmbVenceRec.setSelectAllOnFocus(true);
                        cmbVenceRec.requestFocus();
                    }else if (BeProducto.Control_lote){
                        txtLoteRec.setSelectAllOnFocus(true);
                        txtLoteRec.requestFocus();
                    }else {
                        txtCantidadRec.requestFocus();
                    }
                    fillUbicacion();
                }

            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
                txtNoLP.setText("");
                txtNoLP.setSelectAllOnFocus(true);
                txtNoLP.requestFocus();
            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void msgExisteLp(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("OK", (dialog12, which) -> {
                txtNoLP.setText("");
                txtNoLP.setSelectAllOnFocus(true);
                txtNoLP.requestFocus();
            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void doExit(){
        try{

            //LimpiaValores();
            super.finish();
            gl.Carga_Producto_x_Pallet=false;
        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
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

            clsBeTrans_re_detList LRecepcionCantidad = xobj.getresult(clsBeTrans_re_detList.class, "Get_All_BeTrasReDet_By_IdOrdenCompraEnc");

            if (LRecepcionCantidad !=null){
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

            FinalizaCargaProductos();

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

            try {
                Existe_Lp = xobj.getresult(Boolean.class,"Existe_Lp");
            } catch (Exception e) {
                e.printStackTrace();
                Existe_Lp = xobj.getresult(Boolean.class,"Existe_Lp");
            }

            if (Existe_Lp){
                //#CKFK20220328 Agregué esta validación para el caso en que ingresen una licencia duplicada
                if (gl.bloquear_lp_hh){
                    msgExisteLp("El Lp: "+pLp+ " ya existe, debe ingresar una nueva licencia");
                }else{
                    msgAskExisteLp("El Lp: "+pLp+ " ya existe, ¿Agregarlo nuevamente al producto: "+BeProducto.Codigo + "?");
                }
            }else{
                if (guardando_recepcion){

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

                }else{
                    if (BeProducto.Control_vencimiento){
                        cmbVenceRec.setSelectAllOnFocus(true);
                        cmbVenceRec.requestFocus();
                    }else if (BeProducto.Control_lote){
                        txtLoteRec.setSelectAllOnFocus(true);
                        txtLoteRec.requestFocus();
                    }else {
                        txtCantidadRec.requestFocus();
                    }
                    fillUbicacion();
                }
            }

        }catch (Exception e){
            mu.msgbox("processExisteLp:"+e.getMessage());
        }
    }

    private void processExisteSerie(){

        try{

            try {
                Existe_Serie = xobj.getresult(Boolean.class,"Existe_Serie");
            } catch (Exception e) {
                e.printStackTrace();
                Existe_Serie = xobj.getresult(Boolean.class,"Existe_Serie");
            }

            if (Existe_Serie){
                msgbox("La serie: "+pSerie+ " ya existe para el producto: "+BeProducto.Codigo + "?");
                txtSerial.requestFocus();
            }else{
                Guardar_Recepcion_Nueva();
            }

        }catch (Exception e){
            mu.msgbox("processExisteSerie:"+e.getMessage());
        }
    }

    private void process_Recepcion_Produccion_Nav_BYB(){

        try{

            boolean procesada = xobj.getresult(Boolean.class,"Push_Recepcion_Produccion_To_NAV_For_BYB");
            String respuesta =(String) xobj.getSingle("pRespuesta",String.class);

            if (procesada){
                MensajeAdicionalParaImpresion = "Recepción de Producción procesada en ERP";
                Imprime_Barra_Despues_Guardar();
            }else{
                if (!respuesta.isEmpty() || !respuesta.equals("")){
                    msgboxErrorPush("No se puedo registrar la recepción " + respuesta);
                }
            }

        }catch (Exception e){
            mu.msgbox("process_Recepcion_To_Nav:"+e.getMessage());
        }
    }

    private void process_Recepcion_Compra_Nav_BYB(){

        try{

            boolean procesada = false;
            clsBeCustomError ce;

            try {

                progress.setMessage("Procesando respuesta de NAV.");

                if (gl.gBeOrdenCompra.getIdTipoIngresoOC()==dataContractDI.Ingreso){

                    procesada = xobj.getresult(Boolean.class,"Push_Recepcion_Pedido_Compra_To_NAV_For_BYB");
                    MensajeAdicionalParaImpresion = "Recepción de Pedido de Compra procesada en ERP";

                }else if (gl.gBeOrdenCompra.getIdTipoIngresoOC()==dataContractDI.Devolucion_Venta){

                    procesada = xobj.getresult(Boolean.class,"Push_Recepcion_Devolucion_Venta_To_NAV_For_BYB");
                    MensajeAdicionalParaImpresion = "Recepción de Devolución de Venta procesada en ERP";

                }else if (gl.gBeOrdenCompra.getIdTipoIngresoOC()==dataContractDI.Transferencia_de_Ingreso){

                    procesada = xobj.getresult(Boolean.class,"Push_Recepcion_Transferencias_Ingreso_To_NAV_For_BYB");
                    MensajeAdicionalParaImpresion = "Recepción de Transferencia de Ingreso procesada en ERP";

                }
                String respuesta =(String) xobj.getSingle("pRespuesta",String.class);

                progress.cancel();

                if (procesada){
                    Imprime_Barra_Despues_Guardar();
                    //#CKFK20220216 Coloqué este return para que no se ejecute el de abajo
                    return;
                }else{
                    if (!respuesta.isEmpty() || !respuesta.equals("")){
                        msgboxErrorPush("No se puedo registrar la recepción " + respuesta);
                    }
                }

                Imprime_Barra_Despues_Guardar();

            } catch (Exception e) {

                String vPrimaryMessage = e.getMessage();

                //#EJC20210612:Intento obtener el customerror del WS.
                try {
                    ce = xobj.getresult(clsBeCustomError.class,"Push_Recepcion_Pedido_Compra_To_NAV_For_BYB");
                } catch (Exception exception) {
                    //#EJC20210612: Si ocurre un error al parsear, devolver el primer mensaje de error del stack.
                    throw new Exception(vPrimaryMessage);
                }
            }
        }catch (Exception e){
            progress.cancel();
            //#EJC20210612: Evitar que se quede en la pantalla para evitar error de llave duplicadas.
            msgboxErrorOnWS2("Error al procesar solicitud en ERP: " + e.getMessage());

        }
    }

    private void processTipoEtiqueta(){

        try {

            clsBeTipo_etiqueta etiqueta;
            progress.setMessage("Obteniendo tipo de etiqueta del producto");

            //GT11042022: obtengo etiqueta y valido que no venga vacia, porque al parecer... se llama 2 veces al proceso
            //y en la 2da llamada viene vacia la respuesta, similar a NuevoProcesLP
            etiqueta = xobj.getresultSingle(clsBeTipo_etiqueta.class,"pBeTipo_etiqueta");

            if (etiqueta != null){
                pBeTipo_etiqueta = etiqueta;
                //toastlong("etiqueta "+ pBeTipo_etiqueta.Nombre);
            }


        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processEnviarFoto() {
        boolean exito;

        try {
            progress.setMessage("Guardando imagen...");
            progress.show();

            exito = xobj.getresult(Boolean.class,"Guardar_Producto_Imagen");

            if (exito) {
                progress.cancel();
                toastlong("Foto guardada con éxito");
            }
        } catch (Exception e) {
            progress.cancel();
            msgbox("processEnviarFoto: " + e.getMessage());
        }

    }

    private void processGetFotosProducto() {

        try {
            progress.setMessage("Cargando imágenes...");
            BeListProductoImagen = xobj.getresult(clsBeProducto_imagenList.class,"Get_All_Producto_Imagen");

            gl.ListImagen.clear();
            if (BeListProductoImagen != null) {
                if (BeListProductoImagen.items != null) {

                    for (int i=0; i < BeListProductoImagen.items.size(); i++) {
                        BeImagen = new clsBeImagen();
                        BeImagen.Descripcion = BeProducto.Codigo+" - "+BeProducto.Nombre;
                        BeImagen.Imagen = BeListProductoImagen.items.get(i).Imagen;
                        gl.ListImagen.add(BeImagen);
                    }
                } else {
                    progress.cancel();
                    return;

                }
            } else {
                //toastlong("El producto no tiene imágenes");
                progress.cancel();
                return;

            }
            startActivity(new Intent(this, frm_imagenes.class));
            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            msgbox("processGetFotosProducto: "+ e.getMessage());
        }
    }

    public void msgboxErrorOnWS2(String msg) {
        try{
            ExDialog dialog = new ExDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(msg);

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            dialog.show();
        }catch (Exception e){
            //Log.println(1,"msg",e.getMessage());
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void msgboxErrorPush(String msg) {
        try{
            ExDialog dialog = new ExDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(msg);

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprime_Barra_Despues_Guardar();
                }
            });

            dialog.show();
        }catch (Exception e){
            //Log.println(1,"msg",e.getMessage());
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    public void onBackPressed() {
        try{
            msgAskExit();
        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }
    }
    //endregion

    private void msgGuardarsinParametros(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog1, which) -> Guardar_Recepcion_Nueva());

            dialog.setNegativeButton("No", (dialog12, which) -> {

            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void ocultar_parametros_personalizados(String parametro){
        try{

            String tipo_parametro = parametro;

            switch(tipo_parametro) {
                case "Númerico":
                    lbltipo_texto.setVisibility(View.GONE);
                    lbltipo_logica.setVisibility(View.GONE);
                    lbltipo_fecha.setVisibility(View.GONE);
                    txtvalor_t.setVisibility(View.GONE);
                    cb_valor_b.setVisibility(View.GONE);
                    txtvalor_f.setVisibility(View.GONE);
                    imgDate_p.setVisibility(View.GONE);
                    break;
                case "Texto":
                    lbltipo_numerica.setVisibility(View.GONE);
                    lbltipo_logica.setVisibility(View.GONE);
                    lbltipo_fecha.setVisibility(View.GONE);
                    txtvalor_n.setVisibility(View.GONE);
                    cb_valor_b.setVisibility(View.GONE);
                    txtvalor_f.setVisibility(View.GONE);
                    imgDate_p.setVisibility(View.GONE);
                    break;
                case "Fecha":
                    lbltipo_numerica.setVisibility(View.GONE);
                    lbltipo_texto.setVisibility(View.GONE);
                    lbltipo_logica.setVisibility(View.GONE);
                    txtvalor_n.setVisibility(View.GONE);
                    txtvalor_t.setVisibility(View.GONE);
                    cb_valor_b.setVisibility(View.GONE);
                    break;
                case "Lógico":
                    lbltipo_numerica.setVisibility(View.GONE);
                    lbltipo_texto.setVisibility(View.GONE);
                    lbltipo_fecha.setVisibility(View.GONE);
                    txtvalor_n.setVisibility(View.GONE);
                    txtvalor_t.setVisibility(View.GONE);
                    txtvalor_f.setVisibility(View.GONE);
                    imgDate_p.setVisibility(View.GONE);
                    break;
                default:
                    lbltipo_numerica.setVisibility(View.GONE);
                    lbltipo_texto.setVisibility(View.GONE);
                    lbltipo_logica.setVisibility(View.GONE);
                    lbltipo_fecha.setVisibility(View.GONE);
                    imgDate_p.setVisibility(View.GONE);

                    txtvalor_n.setVisibility(View.GONE);
                    txtvalor_t.setVisibility(View.GONE);
                    cb_valor_b.setVisibility(View.GONE);
                    txtvalor_f.setVisibility(View.GONE);
                    break;
            }
        }
        catch (Exception e){
            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private Boolean Validar_Parametros_personalizados(){

        try{

            for (int i = 0; i < 1; i++) {

                tipo_parametro = pListBEProductoParametro.items.get(i).TipoParametro.Tipo;

                switch(tipo_parametro) {
                    case "Númerico":

                        double valor =  pListBEProductoParametro.items.get(i).Valor_numerico;
                        if (txtvalor_n.getText().toString().equals(valor) || txtvalor_n.getText().toString().isEmpty()){
                            parametro_personalizado_valido = false;
                        }else{
                            parametro_personalizado_valido = true;
                        }
                        break;
                    case "Texto":

                        if (txtvalor_t.getText().toString().equals(pListBEProductoParametro.items.get(i).Valor_texto) || txtvalor_t.getText().toString().isEmpty()){
                            parametro_personalizado_valido = false;
                        }else{
                            parametro_personalizado_valido = true;
                        }

                        break;
                    case "Fecha":

                        if (txtvalor_f.getText().toString().equals(pListBEProductoParametro.items.get(i).Valor_fecha) || txtvalor_f.getText().toString().isEmpty()){
                            parametro_personalizado_valido = false;
                        }else{
                            parametro_personalizado_valido = true;
                        }
                        break;
                    case "Lógico":

                        //GT 19082021 no se valida, porque el valor por defecto podria ser el esperado, entonces no hay manera de determinar si esta correcto o no.
                        parametro_personalizado_valido = true;
                        break;
                    default:
                        parametro_personalizado_valido= false;
                        break;
                }
            }

        }
        catch (Exception e){

            addlog(Objects.requireNonNull(new Object() {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"error en validar p_personalizado");
        }

        return parametro_personalizado_valido;
    }

    public void Capturar(View view) {
        abrirCamara();
    }

    public void verImagenes(View view) {
        progress.setMessage("Cargando imágenes...");
        progress.show();
        execws(30);
    }

    private void abrirCamara() {
        try{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.dts.tom.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }catch (Exception ee){
            mu.msgbox(ee.getMessage());
        }

    }

    String currentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private File  createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            //aquí la convierto a base 64
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            //#AT 20220322 Se guarda la imagen
            BeProductoImagen = new clsBeProducto_imagen();
            BeProductoImagen.IdProducto = BeProducto.IdProducto;
            BeProductoImagen.Imagen = encoded;
            BeProductoImagen.Etiqueta = BeProducto.Codigo;

            execws(29);
        }
    }

    class DecimalFilter implements InputFilter {
        private Pattern mPattern;
        DecimalFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }
    }

}