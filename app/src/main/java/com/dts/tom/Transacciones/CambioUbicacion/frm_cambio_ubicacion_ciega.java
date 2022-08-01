package com.dts.tom.Transacciones.CambioUbicacion;

import static com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res.selitem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.base.ExDialog;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.base.appGlobals;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacionList;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.classes.Transacciones.Movimiento.USUbicStrucStage5.USUbicStrucStage5List;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.tom.Mainmenu;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.ConsultaStock.frm_consulta_stock;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.ConsultaStock.frm_consulta_stock_detalleCI.CambioUbicExistencia;

public class frm_cambio_ubicacion_ciega extends PBase {

    private frm_cambio_ubicacion_ciega.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtUbicOrigen, txtCodigoPrd, txtCantidad, txtUbicDestino,txtLicPlate, txtPosiciones, txtPeso;
    private TextView lblUbicCompleta, lblDescProducto, lblLote, lblVence, lblEstadoDestino, txtUbicSug, lblCant,lblPesoEst, lblPeso,lblTituloForma,lblUbicCompDestino,lblCantidad;
    private Spinner cmbPresentacion, cmbLote, cmbVence, cmbEstadoOrigen, cmbEstadoDestino;
    private Button btnGuardarCiega;
    private TableRow trPeso,tblExplosionar,tblPresentacion;
    private CheckBox chkExplosionar;
    private RelativeLayout relbot, reltop;

    private clsBeMotivo_ubicacionList pListBeMotivoUbicacion = new clsBeMotivo_ubicacionList();

    private clsBeProducto cvProd = new clsBeProducto();
    private clsBeProductoList productoList = new clsBeProductoList();

    private clsBeVW_stock_res vStockRes = new clsBeVW_stock_res();
    private clsBeVW_stock_resList stockResList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList lotesList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList venceList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList presentacionList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList productoEstadoOrigenList = new clsBeVW_stock_resList();
    private clsBeProducto_estadoList productoEstadoDestinoList = new clsBeProducto_estadoList();
    private USUbicStrucStage5List lUbicSug = new USUbicStrucStage5List();

    private ArrayList<String> cmbPresentacionList = new ArrayList<String>();
    private ArrayList<String> cmbLoteList = new ArrayList<String>();
    private ArrayList<String> cmbVenceList = new ArrayList<String>();
    private ArrayList<String> cmbEstadoOrigenList = new ArrayList<String>();
    private ArrayList<String> cmbEstadoDestinoList = new ArrayList<String>();

    private String lote = "", fechaVence = "";

    private String  fechaVenceU;

    private int cvUbicOrigID;
    private int cvProdID;
    private int cvPresID;
    private String cvLote;
    private String cvVence;
    private int cvEstOrigen=0;
    private int cvEstDestino;
    private int cvUbicDestID=0;
    private int cvStockID;
    private String cvAtrib;
    private int cvPropID;
    private int cvUMBID;
    private double vFactorPres;
    private boolean validarDatos = false;
    private boolean datosCorrectos = false;
    private boolean vProcesar =false;
    private int tmpUbicId = 0;

    private boolean Es_Explosion = false;
    private boolean Es_Explosion_Manual = false;
    private int vIdStockNuevo = 0;
    private int vIdMovimientoNuevo= 0;
    private int vPosiciones=0;

    private boolean Existe_Lp=false;

    private boolean EsPalletNoEstandar=false;
    private int TienePosiciones=0;
    private boolean Ubicacion_Es_Valida= false;

    private boolean escaneoPallet;

    private clsBeTrans_movimientos gMovimientoDet;
    private clsBeBodega_ubicacion bodega_ubicacion_destino;
    private clsBeBodega_ubicacion bodega_ubicacion_origen;
    private clsBeProducto_estado gProdEstado;
    private clsBeVW_stock_res cvStockItem;
    private clsBeVW_stock_res BeStockPallet;
    private clsBeStock pStock;

    private clsBeProducto BeProductoUbicacion;
    private int IdProductoUbicacion;
    private String vNuevoPalletId;
    private String pLicensePlate;

    private double vCantidadAUbicar, vCantidadDisponible, vPesoAUbicar, vPesoDisponible;

    private TextToSpeech mTTS;
    private String textToSpeeach = "";
    private boolean ocultar_mensajes;
    private boolean areaprimera = true;
    private boolean inferir_origen_en_cambio_ubic = false;
    private boolean licencia_reservada_completamente = false;
    private boolean reservada_parcialmente = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);

            super.InitBase();

            setContentView(R.layout.activity_frm_cambio_ubicacion_ciega);

            ocultar_mensajes = gl.Mostrar_Area_En_HH;
            inferir_origen_en_cambio_ubic = gl.inferir_origen_en_cambio_ubic;

            ws = new frm_cambio_ubicacion_ciega.WebServiceHandler(frm_cambio_ubicacion_ciega.this, gl.wsurl);
            xobj = new XMLObject(ws);

            txtUbicOrigen = (EditText) findViewById(R.id.txtUbicOrigen);
            txtCodigoPrd = (EditText) findViewById(R.id.txtCodigoPrd);
            txtCantidad = (EditText) findViewById(R.id.txtCantidad);
            txtUbicDestino = (EditText) findViewById(R.id.txtUbicDestino);
            txtLicPlate = (EditText)findViewById(R.id.txtLipPlate);
            txtPeso = (EditText) findViewById(R.id.txtPeso);

            lblUbicCompleta = (TextView) findViewById(R.id.lblUbicCompleta);
            lblDescProducto = (TextView) findViewById(R.id.lblDescProducto);
            lblLote = (TextView) findViewById(R.id.lblLote);
            lblVence = (TextView) findViewById(R.id.lblVence);
            lblEstadoDestino = (TextView) findViewById(R.id.lblEstadoDestino);
            lblCant = (TextView) findViewById(R.id.lblCant);
            lblPesoEst = (TextView) findViewById(R.id.lblPesoEst);
            lblPeso = (TextView) findViewById(R.id.lblPeso);
            lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
            lblUbicCompDestino = (TextView) findViewById(R.id.lblUbicCompDestino);
            lblCantidad = findViewById(R.id.lblCantidad);
            txtUbicSug = (TextView) findViewById(R.id.txtUbicSug);

            cmbPresentacion = (Spinner) findViewById(R.id.cmbPresentacion);
            cmbLote = (Spinner) findViewById(R.id.cmbLote);
            cmbVence = (Spinner) findViewById(R.id.cmbVence);
            cmbEstadoOrigen = (Spinner) findViewById(R.id.cmbEstadoOrigen);
            cmbEstadoDestino = (Spinner) findViewById(R.id.cmbEstadoDestino);

            chkExplosionar = (CheckBox)  findViewById(R.id.chkExplosionar);

            btnGuardarCiega = (Button) findViewById(R.id.btnGuardarCiega);

            trPeso = (TableRow)findViewById(R.id.trPeso);
            tblExplosionar = (TableRow)findViewById(R.id.tblExplosionar);
            tblPresentacion = (TableRow)findViewById(R.id.tblPresentacion);

            relbot = (RelativeLayout) findViewById(R.id.relbot);
            reltop = (RelativeLayout) findViewById(R.id.reltop);

            tblPresentacion = findViewById(R.id.tblPresentacion);

            tblExplosionar.setVisibility(View.GONE);

            txtPosiciones = new EditText(this,null);
            txtPosiciones.setInputType(InputType.TYPE_CLASS_NUMBER);

            cmbEstadoDestino.setVisibility(gl.modo_cambio == 1 ? View.GONE : View.VISIBLE);
            lblEstadoDestino.setVisibility(gl.modo_cambio == 1 ? View.GONE : View.VISIBLE);

            lblTituloForma.setText(String.format("Cambio de %s",(gl.modo_cambio==1?"ubicación N.D.":"estado N.D")));

            //AT20220721 Si Permitir_Decimales = true  txtCantidad  cambia a decimal si no a entero
            if (gl.Permitir_Decimales) {
                txtCantidad.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            } else {
                txtCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
            }

            ProgressDialog("Cargando forma");

            setHandlers();

            Load();

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void Load() {

        try {

            if(gl.modo_cambio==1){

                //#AT20220722 Solo aplica si el cambio se hace desde la pantalla de Consulta de Existencias
                if (CambioUbicExistencia) {
                    txtUbicOrigen.setText(gl.existencia.idUbic);
                    cvUbicOrigID = Integer.valueOf(gl.existencia.idUbic);

                    lblUbicCompleta.setText(gl.existencia.Ubic);
                    txtLicPlate.setText(gl.existencia.LicPlate);
                    txtCodigoPrd.setText(gl.existencia.Codigo);

                    if (!gl.existencia.getLicPlate().isEmpty()) {
                        pLicensePlate = gl.existencia.LicPlate;
                    } else {
                        pLicensePlate = "";
                    }

                    execws(3);
                } else {

                    if (!inferir_origen_en_cambio_ubic) {
                        execws(1);
                    } else {
                        progress.cancel();
                        txtLicPlate.requestFocus();
                        txtUbicOrigen.setEnabled(false);
                    }
                }
            }else{
                txtUbicOrigen.requestFocus();
                progress.cancel();
            }
        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }

    }

    private void setHandlers() {

        cmbPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {

                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel!=null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5, 0, 0, 0);
                        spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                        //#AT20220711 Se agrega valor a cvPresId segun lo selccionado en el combo
                        //tambien se actualizan los datos según vaya cambiando la presentación
                        cvPresID = Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());
                        String NombrePres = cmbPresentacion.getSelectedItem().toString().split(" - ")[1];

                        if (cvPresID > 0) {
                            tblExplosionar.setVisibility(View.VISIBLE);
                            lblCantidad.setText("Cantidad ("+NombrePres+"): ");
                        } else {
                            tblExplosionar.setVisibility(View.GONE);
                            lblCantidad.setText("Cantidad ("+BeProductoUbicacion.UnidadMedida.Nombre+")");
                        }

                        LlenaLotes();
                    }

                } catch (Exception ex) {
                    addlog(new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
                    msgbox(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        cmbLote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel!=null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5, 0, 0, 0);
                        spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                        lote = stockResList.items.get(position).Lote;
                        LlenaFechaVence();
                    }

                } catch (Exception ex) {
                    addlog(new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
                    msgbox(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        cmbVence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel!=null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5, 0, 0, 0);
                        spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                        fechaVence = stockResList.items.get(position).getFecha_Vence();

                        LlenaEstadoOrigen();

                    }

                } catch (Exception ex) {
                    addlog(new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
                    msgbox(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        cmbEstadoOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel!=null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5, 0, 0, 0);
                        spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                        cvEstOrigen = productoEstadoOrigenList.items.get(position).getIdProductoEstado();
                    }

                } catch (Exception ex) {
                    addlog(new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
                    msgbox(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        cmbEstadoDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel!=null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5, 0, 0, 0);
                        spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                        cvEstDestino = productoEstadoDestinoList.items.get(position).IdEstado;
                    }

                } catch (Exception ex) {
                    addlog(new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
                    msgbox(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        txtCodigoPrd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //#AT 20220324 Si no se ingresa la licencia no deja buscar por codigo de producto  esto aplica si
                    //inferir_origen_en_cambio_ubic es verdadero
                    if (inferir_origen_en_cambio_ubic) {
                        if (!txtLicPlate.getText().toString().isEmpty() && !txtCodigoPrd.getText().toString().isEmpty()) {
                            pLicensePlate = txtLicPlate.getText().toString();
                            execws(17);
                        } else {
                            toast("No se puede ubicar sin licencia");
                            txtLicPlate.requestFocus();
                        }
                    } else {
                        llenaDatosProducto();
                    }
                }

                return false;
            }
        });

        txtLicPlate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    inicializaTareaLP();

                    Procesa_Lp();
                }

                return false;
            }
        });

        txtLicPlate.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
        txtCodigoPrd.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
        txtUbicDestino.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        txtUbicOrigen.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            inicializaTarea(false);
                            validaOrigen();
                    }
                }
                return false;
            }
        });

        txtUbicOrigen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (txtUbicOrigen.getText().toString().equals("") ||
                            txtUbicOrigen.getText().toString().isEmpty() ||
                            txtUbicOrigen.getText().toString()==null){

                        if(validarDatos){
                            lblUbicCompleta.setText("");
                            mu.msgbox("Debe ingresar la ubicación origen");

                            final Handler cbhandler = new Handler();
                            cbhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    txtUbicOrigen.requestFocus();
                                }
                            }, 500);
                        }
                    }
                }
            }
        });

        //#AT 20211125 Agregué este evento para que detecte cuando el usuario presione enter
        txtUbicDestino.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        txtUbicDestino.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (txtUbicDestino.getText().toString().length() > 0)
                                AplicarCambioBoton();
                            else
                                toast("Debe ingresar la ubicación destino");
                    }
                }
                return false;
            }
        });

        txtUbicDestino.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (txtUbicDestino.getText().toString().equals("") ||
                            txtUbicDestino.getText().toString().isEmpty() ||
                            txtUbicDestino.getText().toString()==null){

                        if (validarDatos){
                            mu.msgbox("Debe ingresar la ubicación destino");

                            final Handler cbhandler = new Handler();
                            cbhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    txtUbicDestino.requestFocus();
                                }
                            }, 500);
                        }
                    }
                }
            }
        });

        txtCantidad.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:

                            try {

                                String Cantwithformat =txtCantidad.getText().toString();
                                Cantwithformat = Cantwithformat.replace(",","");

                                if(Double.valueOf(Cantwithformat)>0) {

                                    //Recalcula_Peso();
                                    //#GT27072022_1450: si cambia la cantidad a reubicar, el peso se redistribuye
                                    //pero con Recalcula_peso, multiplica cantidad x el factor y no es lo deseado
                                    Redistribuye_Peso();

                                    if (trPeso.getVisibility()==View.VISIBLE){
                                        txtPeso.requestFocus();
                                    }else{
                                        txtUbicDestino.requestFocus();
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                    }
                }
                return false;
            }
        });

        txtPeso.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:

                            try {

                                String Cantwithformat =txtPeso.getText().toString();
                                Cantwithformat = Cantwithformat.replace(",","");

                                if(Double.valueOf(Cantwithformat)>0) {
                                    txtUbicDestino.requestFocus();
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                    }
                }
                return false;
            }
        });

        chkExplosionar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    Es_Explosion_Manual = true;
                   // ProgressDialog("Explosionar mercancía.");

                } else {

                    Es_Explosion_Manual = false;

                }
            }
        });

        txtUbicDestino.setOnClickListener(view -> {

        });

    }

    private void LlenaPresentaciones() {

        String valor;

        try {

            cmbPresentacionList.clear();

            List AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == cvProdID)
                    .toList();

            presentacionList.items = AuxList;

            for (int i = 0; i < presentacionList.items.size(); i++) {

                valor = presentacionList.items.get(i).getIdPresentacion() + " - " +
                        presentacionList.items.get(i).getNombre_Presentacion().toString();

                if (cmbPresentacionList.indexOf(valor)==-1){
                    cmbPresentacionList.add(valor);
                }

            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmbPresentacionList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresentacion.setAdapter(dataAdapter);

            if (cmbPresentacionList.size() > 0) {

                if (escaneoPallet && productoList !=null ){
                    int sel = cmbPresentacionList.indexOf(BeStockPallet.getIdPresentacion() + " - " +
                                                          BeStockPallet.getNombre_Presentacion());
                    cmbPresentacion.setSelection(sel);
                    cmbPresentacion.setEnabled(false);
                }else{
                    cmbPresentacion.setSelection(0);
                    if (cmbPresentacionList.size() == 1){
                        cmbPresentacion.setEnabled(false);
                    }else{
                        cmbPresentacion.setEnabled(true);
                    }
                }

                cvPresID =Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());

                if (cvPresID ==0){
                    tblExplosionar.setVisibility(View.GONE);
                }else{
                    tblExplosionar.setVisibility(View.VISIBLE);
                }

            }else{
                tblExplosionar.setVisibility(View.GONE);
                LlenaLotes();
            }

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    //#AT20220412 Cree esta función para asignar a cvPresId el idPresentación
    //sin utilizar el cmb
    private void setPresentacion() {
        String valor = "";
        List AuxList;
        try {

            //#AT20220722 Filtra lista de Stock con la presentación en gl.existencia
            if (CambioUbicExistencia && gl.existencia.IdPresentacion > 0) {
                AuxList = stream(stockResList.items)
                        .where(c -> c.IdProducto == cvProdID && c.IdPresentacion == gl.existencia.IdPresentacion)
                        .toList();
            } else {
                 AuxList = stream(stockResList.items)
                        .where(c -> c.IdProducto == cvProdID)
                        .toList();
            }

            presentacionList.items = AuxList;

            //#20220711 Si el tamaño de la lista es mayor a 1,
            //se muestra el combo con todas su presentaciones disponibles.
            if (presentacionList.items.size() > 0) {
                if (presentacionList.items.size() == 1) {
                    cvPresID = presentacionList.items.get(0).IdPresentacion;

                    if (cvPresID == 0) {
                        tblExplosionar.setVisibility(View.GONE);
                        lblCantidad.setText("Cantidad ("+BeProductoUbicacion.UnidadMedida.Nombre+"): ");
                    } else {
                        tblExplosionar.setVisibility(View.VISIBLE);
                        lblCantidad.setText("Cantidad (" + presentacionList.items.get(0).Nombre_Presentacion + "): ");
                    }
                } else {

                    tblPresentacion.setVisibility(View.VISIBLE);

                    cmbPresentacionList.clear();

                    for (int i = 0; i < presentacionList.items.size(); i++) {

                        String NombrePres = presentacionList.items.get(i).getIdPresentacion() == 0 ? "Sin Presentación": presentacionList.items.get(i).getNombre_Presentacion();
                        valor = presentacionList.items.get(i).getIdPresentacion() + " - " + NombrePres;

                        if (cmbPresentacionList.indexOf(valor)==-1){
                            cmbPresentacionList.add(valor);
                        }

                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmbPresentacionList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresentacion.setAdapter(dataAdapter);

                    //#AT20220722 Selecciona prensentación en el combo
                    if (CambioUbicExistencia) {
                        int IndexAux = cmbPresentacionList.indexOf(gl.existencia.IdPresentacion +" - "+gl.existencia.Pres);
                        cmbPresentacion.setSelection(IndexAux);
                    }
                }
            }

            LlenaLotes();

        } catch (Exception e) {
            mu.msgbox("setPresentación: "+ e.getMessage());
        }
    }

    private void LlenaLotes() {

        String valor;

        try {

            cmbLoteList.clear();

                cvLote = "";

                cmbLote.setVisibility(BeProductoUbicacion.Control_lote ? View.VISIBLE : View.GONE);
                lblLote.setVisibility(BeProductoUbicacion.Control_lote ? View.VISIBLE : View.GONE);

                if (BeProductoUbicacion.Control_lote) {

                    try {

                        List AuxList;

                        //if (escaneoPallet && productoList != null) {
                        //Quité esta validación porque en stockResList ya está filtrado por LicensePlate

                        AuxList = stream(stockResList.items)
                                .where(c -> c.IdProducto == cvProdID)
                                .where(c -> c.getIdPresentacion() == cvPresID)
                                .toList();

                        if (AuxList == null) {
                            cvLote = "";
                            LlenaFechaVence();
                        } else {

                            lotesList.items = AuxList;

                            for (int i = 0; i < lotesList.items.size(); i++) {

                                valor = lotesList.items.get(i).Lote;

                                if (cmbLoteList.indexOf(valor)==-1){
                                    cmbLoteList.add(valor);
                                }
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmbLoteList);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cmbLote.setAdapter(dataAdapter);

                            if (cmbLoteList.size() > 0) {
                                cmbLote.setSelection(0);

                                cvLote = cmbLote.getSelectedItem().toString();
                            }
                        }
                    } catch (Exception ex) {
                        cvLote = "";
                        msgbox("Llena lote : " + ex.getMessage());
                        LlenaFechaVence();
                    }

                } else {
                    LlenaFechaVence();
                }

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaFechaVence() {

        String valor;

        try {

            cmbVenceList.clear();

            cvVence = "01/01/1900";

            cmbVence.setVisibility(BeProductoUbicacion.Control_vencimiento ? View.VISIBLE : View.GONE);
            lblVence.setVisibility(BeProductoUbicacion.Control_vencimiento ? View.VISIBLE : View.GONE);

            if (BeProductoUbicacion.Control_vencimiento) {

                try {

                    Date currentTime = Calendar.getInstance().getTime();

                    if (cmbLote.getAdapter()!=null && cmbLote.getAdapter().getCount()>0){
                        cvLote = cmbLote.getSelectedItem().toString();
                    }

                    cvVence =app.strFecha(currentTime);

                    List AuxList;

                    if (BeProductoUbicacion.Control_lote) {
                        //if (escaneoPallet && productoList != null) {
                        //Quité esta validación porque en stockResList ya está filtrado por LicensePlate

                        AuxList = stream(stockResList.items)
                                .where(c -> c.IdProducto == cvProdID)
                                .where(c -> c.getIdPresentacion() == cvPresID)
                                .where(c -> c.getLote() == cvLote)
                                .toList();

                    } else {
                        //if (escaneoPallet && productoList != null) {
                        //Quité esta validación porque en stockResList ya está filtrado por LicensePlate

                        AuxList = stream(stockResList.items)
                                .where(c -> c.IdProducto == cvProdID)
                                .where(c -> c.getIdPresentacion() == cvPresID)
                                .toList();
                    }

                    if (AuxList == null) {
                        cvVence = "01/01/1900";
                        LlenaEstadoOrigen();
                    } else {

                        venceList.items = AuxList;

                        for (int i = 0; i < venceList.items.size(); i++) {

                            valor =app.strFecha(venceList.items.get(i).Fecha_Vence);

                            if (cmbVenceList.indexOf(valor)==-1){
                                cmbVenceList.add(valor);
                            }
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmbVenceList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cmbVence.setAdapter(dataAdapter);

                        if (cmbVenceList.size() > 0) {
                            cmbVence.setSelection(0);
                            cvVence = cmbVence.getSelectedItem().toString();
                        }
                    }
                } catch (Exception ex) {
                    cvVence = "01/01/1900";
                    msgbox("Llena vence : " + ex.getMessage());
                    LlenaEstadoOrigen();
                }


            } else {
               LlenaEstadoOrigen();
            }

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaEstadoOrigen() {

        String valor;

        try {

            cmbEstadoOrigenList.clear();

            List AuxList;

            if (BeProductoUbicacion.Control_vencimiento && !cvVence.equals("01/01/1900")) {

                if (BeProductoUbicacion.Control_lote) {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == cvProdID)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .where(c -> c.getLote() == cvLote)
                            .where(c -> (app.strFecha(c.Fecha_Vence).equals(cvVence)))
                            .toList();

                } else {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == cvProdID)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .where(c -> (app.strFecha(c.Fecha_Vence).equals(cvVence)))
                            .toList();
                }

            }else{

                if (BeProductoUbicacion.Control_lote) {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == cvProdID)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .where(c -> c.getLote() == cvLote)
                            .toList();

                } else {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == cvProdID)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .toList();
                }

            }

            if (AuxList == null) {
                cvEstOrigen = 0;
                muestraCantidad();
            } else {

                productoEstadoOrigenList.items = AuxList;

                for (int i = 0; i < productoEstadoOrigenList.items.size(); i++) {

                    valor = productoEstadoOrigenList.items.get(i).getIdProductoEstado() + " - " +
                            productoEstadoOrigenList.items.get(i).getNomEstado();

                    if (cmbEstadoOrigenList.indexOf(valor)==-1){
                        cmbEstadoOrigenList.add(valor);
                    }
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmbEstadoOrigenList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbEstadoOrigen.setAdapter(dataAdapter);

                if (cmbEstadoOrigenList.size() > 0) {

                    cmbEstadoOrigen.setSelection(0);
                    cvEstOrigen = Integer.valueOf(cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0].toString());
                    muestraCantidad();

                }

            }

        } catch (Exception ex) {
            cvEstOrigen = 0;
            muestraCantidad();

            addlog(new Object() {}.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaEstadoDestino(int idPropietario) {
        try {
            cmbEstadoDestinoList.clear();

            for (int i = 0; i < productoEstadoDestinoList.items.size(); i++) {
                cmbEstadoDestinoList.add(productoEstadoDestinoList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmbEstadoDestinoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDestino.setAdapter(dataAdapter);

            if (cmbEstadoDestinoList.size() > 0) cmbEstadoDestino.setSelection(0);

            if (escaneoPallet && productoList != null) {
                //LLama este procedimiento del WS Get_Productos_By_IdUbicacion_And_LicPlate
                execws(6);
            } else {
                //LLama este procedimiento del WS Get_Productos_By_IdUbicacion
                execws(7);
            }

        } catch (Exception e) {
            mu.msgbox(e.getMessage());
        }
    }

    private void Procesa_Lp() {
        try {

            progress.setMessage("Cargando datos del producto");
            progress.show();

            String pbarra;

            pbarra = txtCodigoPrd.getText().toString();

            cvLote = "";
            cvPresID = 0;
            cvEstOrigen = 0;
            cvProdID = 0;
            cvVence = "01/01/1900";

            String vStarWithParameter = "$";

            if (!txtLicPlate.getText().toString().isEmpty() && !txtLicPlate.getText().toString().equals("0")) {

               escaneoPallet = true;

                pLicensePlate = txtLicPlate.getText().toString().replace("$", "");

                //Llama al método del WS Existe_Lp_In_Stock
                execws(18);

                progress.cancel();

            } else {
                mu.msgbox("Búsqueda por licencia 0 no está permitida, realice búsqueda por código de producto.");
                txtLicPlate.setText("");
                txtCodigoPrd.requestFocus();
            }

        } catch (Exception ex) {
            progress.cancel();
            msgbox("Error " + ex.getMessage());
        }
        progress.cancel();
    }

    private void llenaDatosProducto() {
        try {

            progress.setMessage("Cargando datos del producto");
            progress.show();

            String pbarra;

            pbarra = txtCodigoPrd.getText().toString();

            cvLote = "";
            cvPresID = 0;
            cvEstOrigen = 0;
            cvProdID = 0;
            cvVence = "01/01/1900";

            String vStarWithParameter = "$";

            //Comentario: La barra de pallet puede comenzar con $ y no con (01)
            if (txtLicPlate.getText().toString().startsWith("$") ||
                    txtLicPlate.getText().toString().startsWith("(01)") ||
                    txtLicPlate.getText().toString().startsWith(vStarWithParameter)) {

                //Es una barra de pallet válida por tamaño
                int vLengthBarra = txtLicPlate.getText().toString().length();

               // if (vLengthBarra >= 16) {

                    escaneoPallet = true;

                    pLicensePlate = txtLicPlate.getText().toString().replace("$", "");

                    //Llama al método del WS Get_Stock_By_Lic_Plate_And_Codigo

                    execws(17);

                //}

            } else {
                escaneoPallet = false;

                //Llama al método del WS Get_BeProducto_By_Codigo_For_HH
                execws(3);
            }

        } catch (Exception ex) {
            progress.cancel();
            msgbox("Error " + ex.getMessage());
        }
    }

    private void muestraCantidad(){

        List AuxList;
        Date currentTime = Calendar.getInstance().getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.YEAR, 1);

        try {

            progress.setMessage("Obteniendo la cantidad disponible del producto");
            progress.show();

            if (escaneoPallet && productoList != null) {
                if(cmbVence.getAdapter() != null){
                    cvVence = cmbVence.getSelectedItem().toString();
                    if (cmbVence.getAdapter().getCount() == 1) {
                        cmbVence.setEnabled(false);
                    }
                } else{
                    cvVence = "01/01/1900";
                }
            }else {
                if(cmbVence.getAdapter() != null){
                    cvVence = cmbVence.getSelectedItem().toString();
                    cmbVence.setEnabled(true);
                }  else{
                    cvVence = "01/01/1900";
                }
            }

        }catch(Exception ex){
            cvVence = app.strFecha(calendar.getTime());
        }

        try {
            if (escaneoPallet & productoList != null) {
                if(cmbEstadoOrigen.getAdapter() != null){
                    cvEstOrigen = BeStockPallet.IdProductoEstado;
                    if (cmbEstadoOrigen.getAdapter().getCount() == 1) {
                        cmbEstadoOrigen.setEnabled(false);
                    }
                }else{
                    cvEstOrigen =0;
                }

            }else {

                if(cmbEstadoOrigen.getAdapter() != null){

                    if (!cmbEstadoOrigen.getSelectedItem().toString().isEmpty()){
                        cvEstOrigen = Integer.valueOf(cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0]);
                    }else {
                        cvEstOrigen =0;
                    }

                    cmbEstadoOrigen.setEnabled(true);
                }else{
                    cvEstOrigen =0;
                }

            }
        }catch(Exception ex){
            cvEstOrigen =0;
        }

        vCantidadAUbicar =0;
        vCantidadDisponible =0;
        lblCant.setText("Max : " + mu.frmdecimal(vCantidadDisponible, 6));

        if (gl.gCProdAnterior.equals(txtCodigoPrd.getText())  && gl.gCUbicAnterior.equals(txtUbicOrigen.getText().toString()))
        {
            try{
                int IndexAux;

                if (gl.gCEstadoAnterior != -1 && cmbEstadoOrigen.getAdapter()!=null  && cmbEstadoOrigen.getAdapter().getCount()>0) {
                    IndexAux = cmbEstadoOrigenList.indexOf(gl.gCEstadoAnterior+ " - " + gl.gCNomEstadoAnterior);
                    cmbEstadoOrigen.setSelection(IndexAux);
                }

                if (gl.gCFechaAnterior.equals("01/01/1900") && cmbVence.getAdapter()!=null  && cmbVence.getAdapter().getCount()>0) {
                    IndexAux = cmbVenceList.indexOf(gl.gCFechaAnterior);
                    cmbVence.setSelection(IndexAux);
                }

                if (!gl.gCLoteAnterior.isEmpty() && cmbLote.getAdapter()!=null  && cmbLote.getAdapter().getCount()>0) {
                    IndexAux = cmbLoteList.indexOf(gl.gCLoteAnterior);
                    cmbLote.setSelection(IndexAux);
                }

                if (gl.gCPresAnterior != -1 && cmbPresentacion.getAdapter()!=null && cmbPresentacion.getAdapter().getCount()>0) {
                    IndexAux = cmbPresentacionList.indexOf( gl.gCPresAnterior + " - " + gl.gCNomPresAnterior);
                    cmbPresentacion.setSelection(IndexAux);
                }

            }catch(Exception ex){
                addlog(new Object() {}.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
                msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
            }
        }

        try{

            //#CKFK 20200414 quité la condiciones de si se había escaneado un pallet porque el license plate se filtra al inicio
            //cuando se obtiene el stockResList
            //#AT20220713 Utilice equals en lugar de == para comprar los valores
            AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == cvProdID)
                    .where(c -> c.IdPresentacion == cvPresID)
                    .where(c -> (BeProductoUbicacion.Control_lote?c.Lote.equals(cvLote):1==1))
                    .where(c -> c.Atributo_variante_1.equals((cvAtrib == null ? "" : cvAtrib)))
                    .where(c -> (cvEstOrigen > 0 ? c.IdProductoEstado == cvEstOrigen : c.IdProductoEstado >= 0))
                    .where(c -> (BeProductoUbicacion.Control_vencimiento?app.strFecha(c.Fecha_Vence).equals(cvVence):1==1))
                    .toList();

            if (AuxList == null) {
                return;
            }

        }catch(Exception ex){
            msgbox("Llena cant : " + ex.getMessage()) ;
            return;
        }

        try{

            clsBeVW_stock_resList tmpStockResList = new clsBeVW_stock_resList();

            tmpStockResList.items = AuxList;

            if (tmpStockResList.items.size() >0){
                cvStockID = tmpStockResList.items.get(0).getIdStock();
                if (!gl.Permitir_Cambio_Ubic_Producto_Picking){
                    vCantidadAUbicar =tmpStockResList.items.get(0).getCantidadUmBas() - tmpStockResList.items.get(0).CantidadReservadaUMBas;
                } else {

                    vCantidadAUbicar =tmpStockResList.items.get(0).getCantidadUmBas();

                    if (tmpStockResList.items.get(0).IdPedido != 0) {
                        licencia_reservada_completamente = ((tmpStockResList.items.get(0).getCantidadUmBas() - tmpStockResList.items.get(0).CantidadReservadaUMBas) == 0);
                        reservada_parcialmente = tmpStockResList.items.get(0).CantidadReservadaUMBas > 0;
                    } else {
                        vCantidadAUbicar =tmpStockResList.items.get(0).getCantidadUmBas() - tmpStockResList.items.get(0).CantidadReservadaUMBas;
                    }

                }
                vFactorPres = (tmpStockResList.items.get(0).getFactor()==0?1:tmpStockResList.items.get(0).getFactor());
                vPesoAUbicar = tmpStockResList.items.get(0).getPeso();
            }else{
                vCantidadAUbicar = 0;
                cvStockID =0;
            }

            if( escaneoPallet && productoList != null) {

                //Pusimos esto en comentario porque se debe de tomar el factor de la presentacion aunque se haya escaneado pallet
                //Si genera algún problema debemos analizar que se debe hacer.
                /*if(BeStockPallet.Factor >0) {
                    vCantidadAUbicar = (vCantidadAUbicar / BeStockPallet.Factor);
                }*/

                if(vFactorPres >0) {
                    vCantidadAUbicar = (vCantidadAUbicar / vFactorPres);
                }

            }else if (cvPresID != 0) {
                if( vFactorPres >0){
                    vCantidadAUbicar = (vCantidadAUbicar / vFactorPres);
                }
            }

            vCantidadDisponible =vCantidadAUbicar;

            if (vCantidadDisponible==0){
                msgbox("No hay existencias disponibles de este producto en esta ubicación o las existentes están reservadas");
                inicializaTarea(false);
                return;
            }else{
                lblCant.setText(mu.frmdecimal(vCantidadDisponible, gl.gCantDecDespliegue));
                txtCantidad.setText(mu.frmdecimal(vCantidadAUbicar, gl.gCantDecDespliegue));
                txtPeso.setText(mu.frmdecimal(vPesoAUbicar, gl.gCantDecDespliegue));
                if (!inferir_origen_en_cambio_ubic) {
                    txtCantidad.selectAll();
                }
            }

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtPeso.setEnabled(true);

            //#EJC20220330: De momento mover licencias completas y no permitir explosión.
            if (gl.Permitir_Cambio_Ubic_Producto_Picking){
                if(licencia_reservada_completamente){
                    txtCantidad.setEnabled(false);
                    chkExplosionar.setEnabled(false);

                    //Cambiar colores de pantallita, titulo, procesar y flechita. (medium_a)
                    //Mostrar una tostada larga, que diga, ubicación temporal para stagin.
                    reltop.setBackgroundResource(R.drawable.color_medium_a);
                    relbot.setBackgroundResource(R.drawable.color_medium_a);
                    btnGuardarCiega.setBackgroundResource(R.drawable.color_medium_a);

                    toastlong("Ubicación temporal para stagin");
                }else{
                    //#EJC20220331: Este escenario falta probar...

                }
            }

            fechaVenceU = app.strFechaXMLCombo(cvVence);
            execws(15);

        }catch(Exception ex){
            msgbox("Llena cantidad " + ex.getMessage());
        }finally {
            progress.cancel();
        }

    }

    private void Recalcula_Peso(){

        double vCantidad = 0, vPesoUni = 0;

        try{

            if (BeProductoUbicacion.getControl_peso()){
                vPesoUni = BeProductoUbicacion.Peso_referencia;
                vCantidad = Double.valueOf(txtCantidad.getText().toString());
                txtPeso.setText(String.valueOf(vPesoUni*vCantidad));
            }
        }catch (Exception ex){
            mu.msgbox("Recalcula_Peso:" + ex.getMessage());
        }
    }


    private void Redistribuye_Peso(){
        double vCantidad_reubicar = 0, vPesoRef = 0, vPesoReCalculado =0, vPeso=0;


        try{

            if (BeProductoUbicacion.getControl_peso()){
                vPesoRef = BeProductoUbicacion.Peso_referencia;
                vCantidad_reubicar = Double.valueOf(txtCantidad.getText().toString());

                //#GT2772022_1530: Si no existe un peso de referencia, se distribuye el peso original total dentro
                //de la cantidad original, y se distribuye entre las unidades a reubicar
                if (vPesoRef == 0){


                    Double cantidad_original = Double.valueOf(mu.frmdecimal(vCantidadAUbicar, gl.gCantDecDespliegue)) ;
                    Double vPesoOrigen = Double.valueOf(mu.frmdecimal(vPesoAUbicar, gl.gCantDecDespliegue));

                    Double peso_calculado_x_Umbas  =  vPesoOrigen/cantidad_original ;
                    vPesoReCalculado = peso_calculado_x_Umbas*vCantidad_reubicar;

                    txtPeso.setText(String.valueOf(vPesoReCalculado));

                }else {
                    txtPeso.setText(String.valueOf(vPesoRef * vCantidad_reubicar));
                }
            }
        }catch (Exception ex){
            mu.msgbox("ReDistribuye_Peso:" + ex.getMessage());
        }
    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processUbicRecepcion();
                    break;
                case 2:
                    processMotivosUbiHH();
                    break;
                case 3:
                    processProducto();
                    break;
                case 4:
                    processProdEstado();
                    break;
                case 5:
                    processStockLP();
                    break;
                case 6:
                    processProductoUbicLP();
                    break;
                case 7:
                    processProductoUbic();
                    break;
                case 8:
                    processNuevoLPA();
                    //processNuevoCorrelativoLP();
                    break;
                case 9:
                    processIdNuevoPallet();
                    break;
                case 10:
                    processEstadosProp();
                    break;
                case 11:
                    processUbicOrigen();
                    break;
                case 12:
                    processUbicDestino();
                    break;
                case 13:
                    processCambio();
                    break;
                case 14:
                    processCambioUbicEstHH();
                    break;
                case 15:
                    processUbicacionDestSugerida();
                    break;
                case 16:
                    processUbicDestinoSug();
                    break;
                case 17:
                    processStockLP_AndCodigo();
                    break;
                case 18:
                    processExisteLp();
                    break;
                case 19:
                    processPalletNoEstandar();
                    break;
                case 20:
                    processTienePosiciones();
                    break;
                case 21:
                    processUbicacion_Valida();
                    break;
                case 22:
                    processCambioUbicacion();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
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
                    case 1://Obtiene la ubicacion por defecto de la recepción para el cambio de ubicacion o estado ciego
                        callMethod("Get_IdUbicacion_Recepcion_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 2://Obtiene los motivos de ubicación
                        callMethod("Get_Motivos_Ubicacion_For_HH");
                        break;
                    case 3://Obtiene el producto
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodigoPrd.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;
                    case 4://Obtiene el estado del producto
                        callMethod("Get_Single_By_IdEstado","pIdEstado",gl.tareadet.IdEstadoDestino);
                        break;
                    case 5://Obtiene el producto que coincide con el License Plate ingresado en una bodega
                        callMethod("Get_Stock_By_Lic_Plate","pLicensePlate",pLicensePlate,
                                "pIdBodega", gl.IdBodega);
                        break;
                    case 6://Obtiene el stock de un producto en una Ubicacion con un License Plate
                        callMethod("Get_Productos_By_IdUbicacion_And_LicPlate",
                                "pIdUbicacion", txtUbicOrigen.getText().toString(),
                                "pIdBodega", gl.IdBodega,
                                "pIdProductoBodega",BeProductoUbicacion.IdProductoBodega,
                                "pLicPlate",BeStockPallet.Lic_plate);

                        break;
                    case 7://Obtiene el stock de un producto en una ubicacion
                        callMethod("Get_Productos_By_IdUbicacion",
                                "pIdUbicacion", txtUbicOrigen.getText().toString(),
                                "pIdProductoBodega",BeProductoUbicacion.IdProductoBodega);
                        break;
                    case 8://Obtiene el nuevo correlativo de un License Plate
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador", gl.IdOperador,
                                "pIdBodega",gl.IdBodega);

                        break;
                    case 9://Actualiza la tabla trans_movimientos
                        callMethod("Set_Nuevo_Pallet_Id",
                                "pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.OperadorBodega.getIdOperadorBodega(),
                                "pLicPlateAnt",BeStockPallet.getLic_plate(),
                                "pLicPlateNuevo", vNuevoPalletId,
                                "pIdStockNuevo", vIdStockNuevo,
                                "pIdMovimientoNuevo",vIdMovimientoNuevo);
                        break;
                    case 10://Obtiene los estados por propietario
                        callMethod("Get_Estados_By_IdPropietario",
                                "pIdPropietario",BeProductoUbicacion.getIdPropietario());
                        break;
                    case 11://Valida la ubicación origen
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra",txtUbicOrigen.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 12://Valida la ubicación destino
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra",txtUbicDestino.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 13:
                        callMethod("Actualizar_Trans_Ubic_HH_Det","oBeTrans_ubic_hh_det", gl.tareadet,
                                "pMovimiento",gMovimientoDet);
                        break;
                    case 14:
                        //#CKFK 20201229 Agregué campo Pallet_No_Estandar en la vista de stock_res
                        callMethod("Aplica_Cambio_Estado_Ubic_HH",
                                "pMovimiento",gMovimientoDet,
                                "pStockRes",vStockRes,
                                "pIdStockNuevo",vIdStockNuevo,
                                "pIdMovimientoNuevo",vIdMovimientoNuevo,
                                "pPosiciones",vPosiciones);
                        break;
                    case 15:
                        callMethod("ml_get_ubicacion_sugerida","pIdProducto",cvProdID,
                                "pIdBodega",gl.IdBodega,
                                "pIdProductoBodega",cvProd.IdProductoBodega,
                                "pLote",cvLote,
                                "pFechaVence",fechaVenceU,
                                "pIdProductoEstado",cvEstOrigen,
                                "pIdUmBas",cvUMBID,
                                "pIdPresentacion",cvPresID);
                        break;
                    case 16://Obtiene descripción de la ubicación destino sugerida
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra",txtUbicSug.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 17://Obtiene el producto que coincide con el License Plate ingresado en una bodega
                        callMethod("Get_Stock_By_Lic_Plate_And_Codigo","pLicensePlate",pLicensePlate,"pCodigo",txtCodigoPrd.getText().toString(),
                                "pIdBodega", gl.IdBodega);
                        break;
                    case 18:
                        callMethod("Existe_Lp_By_Licencia_And_IdBodega",
                                "pLic_Plate",pLicensePlate,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 19:
                        callMethod("Es_Pallet_No_Estandar","pStock",pStock);
                        break;
                    case 20:
                        callMethod("Tiene_Posiciones","pStock",pStock);
                        break;
                    case 21:
                        callMethod("Ubicacion_Es_Valida",
                                "pIdProducto",BeProductoUbicacion.IdProducto,
                                "pIdUbicacion",txtUbicDestino.getText().toString(),"pIdBodega",gl.IdBodega);
                        break;
                    case 22:
                        callMethod("Actualizar_Ubicaciones_Reservadas_By_IdStock",
                                         "pIdStock", cvStockID,
                                               "pIdBodega", gl.IdBodega,
                                               "pIdUbicacion",Integer.valueOf(txtUbicDestino.getText().toString()),
                                               "pIdOperador", gl.IdOperador);
                        break;

                }

            } catch (Exception e) {
                error=e.getMessage();errorflag =true;msgbox(error);
                btnGuardarCiega.setVisibility(View.VISIBLE);
            }
        }
    }

    private void processMotivosUbiHH(){

        try {

            progress.setMessage("Obteniendo Motivos de ubicación en HH");

            pListBeMotivoUbicacion = xobj.getresult(clsBeMotivo_ubicacionList.class,"Get_Motivos_Ubicacion_For_HH");

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicRecepcion(){

        try {

            progress.setMessage("Obteniendo la ubicación por defecto de la recepción");
            progress.show();

            cvUbicOrigID = (Integer) xobj.getSingle("Get_IdUbicacion_Recepcion_By_IdBodegaResult",int.class);

            if (cvUbicOrigID > 0){
                txtUbicOrigen.setText(String.valueOf(cvUbicOrigID));
                validaOrigen();
            } else {
                if (tmpUbicId > 0) {
                    txtUbicOrigen.setText(String.valueOf(tmpUbicId));
                    validaOrigen();
                } else {
                    txtUbicOrigen.setText("");
                }
                progress.cancel();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProdEstado(){

        try {

            progress.setMessage("Obteniendo el estado del producto");
            progress.show();

            gProdEstado = xobj.getresult(clsBeProducto_estado.class,"Get_Single_By_IdEstado");

            if (gProdEstado != null){
                txtUbicDestino.setText(gProdEstado.Nombre);
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicOrigen(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            bodega_ubicacion_origen = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion_origen == null)
            {
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                throw new Exception("Ubicación no válida");
            }else{
                cvUbicOrigID=bodega_ubicacion_origen.getIdUbicacion();
                lblUbicCompleta.setText(bodega_ubicacion_origen.getDescripcion());
                txtLicPlate.requestFocus();
            }

            if (validarDatos){

                datosCorrectos=true;

                if (cvUbicOrigID == 0) {
                    msgbox("Ubicación origen no válida");
                    txtUbicOrigen.requestFocus();
                    datosCorrectos = false;
                }

                if (cvProdID == 0) {
                    msgbox("Producto no válido");
                    txtCodigoPrd.requestFocus();
                    datosCorrectos = false;
                }

                if (vCantidadDisponible == 0) {
                    msgbox("Cantidad disponible es 0, no se puede realizar el movimiento");
                    txtCodigoPrd.requestFocus();
                    datosCorrectos = false;
                }

                if (gl.modo_cambio==2) {
                    if (cvEstDestino == 0){
                        msgbox("Estado destino incorrecto");
                        cmbEstadoDestino.requestFocus();
                        datosCorrectos = false;
                    }
                }

                vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString().replace(",",""));
                vCantidadDisponible = Double.parseDouble(lblCant.getText().toString().replace(",",""));

                if (vCantidadAUbicar<0) {
                    mu.msgbox("La cantidad no puede ser negativa");
                    txtCantidad.requestFocus();
                    datosCorrectos = false;
                }

                if (vCantidadAUbicar==0) {
                    msgbox("La cantidad debe ser mayor que 0");
                    txtCantidad.requestFocus();
                    datosCorrectos = false;
                }

                if (vCantidadAUbicar> vCantidadDisponible) {
                    msgbox("Cantidad incorrecta") ;
                    txtCantidad.requestFocus();
                    datosCorrectos = false;
                }

                //#GT27072022-1610: el peso se recalcula solo si cambia la cantidad
                //a reubicar, y eso se hizo cuando se digito un valor
                //Recalcula_Peso();

                if(cvUbicDestID == 0){
                    msgbox("La ubicación de destino no puede ser vacía");
                    txtUbicDestino.requestFocus();
                    datosCorrectos = false;
                }

                if ((cvUbicOrigID == cvUbicDestID) && (gl.modo_cambio ==1)){
                    msgbox("La ubicación de destino coincide con la de origen");
                    cvUbicDestID = 0;
                    txtUbicDestino.selectAll();
                    txtUbicDestino.requestFocus();
                    datosCorrectos = false;
                }

                if (!datosCorrectos) return;

                progress.cancel();

                //#GT14032022_1348: si tiene parametro mostrarArea, se hace ubicacion no dirigida sin preguntar
                //#CKFK20220801 Modifiqué areaprimera por ocultar_mensajes
                if (ocultar_mensajes) {

                    //Llamar método para aplicar el cambio de estado
                    aplicarCambio();

                } else {
                    msgAskAplicar((gl.modo_cambio ==1? "Mover producto a ubicación: " + bodega_ubicacion_destino.Descripcion: "Aplicar cambio de estado?"));
                }



            }else{
                progress.cancel();
                txtLicPlate.requestFocus();
                txtLicPlate.selectAll();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }finally {
            progress.cancel();
        }

    }

    private void processUbicDestino(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            bodega_ubicacion_destino = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion_destino == null){
                vProcesar = false;
                cvUbicDestID = 0;
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                progress.cancel();
                msgbox("Ubicación destino incorrecta");
                //throw new Exception("Ubicación destino incorrecta");
            }else{

                if (gl.validar_disponibilidad_ubicaicon_destino){

                    if (bodega_ubicacion_destino.Disponibilidad_Ubicacion ==1){
                        progress.cancel();
                        msgAskUbicacionOcupadaCompleta();
                    }else if (bodega_ubicacion_destino.Disponibilidad_Ubicacion <1){
                        progress.cancel();
                        msgAskUbicacionParcialmenteCompleta(bodega_ubicacion_destino.Disponibilidad_Ubicacion);
                    }else{
                        execws(21);
                    }

                }else{
                    execws(21);
                }

            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicacion_Valida(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            Ubicacion_Es_Valida = xobj.getresult(Boolean.class,"Ubicacion_Es_Valida");

            if (!Ubicacion_Es_Valida){
                vProcesar = false;
                cvUbicDestID = 0;
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("La ubicación destino, no permite este tipo de producto!");
            }else{
                cvUbicDestID=bodega_ubicacion_destino.getIdUbicacion();
                lblUbicCompDestino.setText(bodega_ubicacion_destino.getDescripcion());

                if(gl.modo_cambio==2 && !vProcesar){
                    progress.cancel();
                    cmbEstadoDestino.requestFocus();
                }else{
                    datosOk();
                }
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processCambioUbicacion() {
        try {

            int CambioUbicRealizado;

            CambioUbicRealizado = (Integer) xobj.getSingle("Actualizar_Ubicaciones_Reservadas_By_IdStockResult",int.class);

            if (CambioUbicRealizado > 0) {
                msgbox("Cambio aplicado.");
                inicializaTarea(true);
            }

        }catch (Exception e) {
            msgbox(new Object() .getClass().getEnclosingMethod().getName() +" ."+ e.getMessage());
        }
    }

    private void processUbicDestinoSug(){

        try {

            progress.setMessage("Obteniendo descripción de la ubicación destino sugerida");
            progress.show();

            bodega_ubicacion_destino = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion_destino == null){
                throw new Exception("Ubicación destino sugerida incorrecta");
            }else{

                cvUbicDestID=bodega_ubicacion_destino.getIdUbicacion();
                lblUbicCompDestino.setText(bodega_ubicacion_destino.getDescripcion());

                if (bodega_ubicacion_destino.getTramo().getEs_Rack()){

                    //#CKFK 20210202: voice ubicación
                    mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {

                            if(status == TextToSpeech.SUCCESS){

                                Locale locSpanish = new Locale("spa", "MEX");
                                int result =mTTS.setLanguage(locSpanish);

                                textToSpeeach = "";

                                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                                    Log.e("tts","Lenguaje no soportado :(");
                                }else{

                                    String[] cadena_ubicacion = bodega_ubicacion_destino.getDescripcion().split("-");
                                    String rack, columna, tramo, nivel, pos, ubicacion;

                                    if (cadena_ubicacion.length==6){

                                        rack = cadena_ubicacion[0].trim().substring(0);
                                        columna = cadena_ubicacion[1].trim().substring(1);
                                        tramo = cadena_ubicacion[2].trim().substring(1);
                                        nivel = cadena_ubicacion[3].trim().substring(1);
                                        pos = cadena_ubicacion[4].trim().substring(0);
                                        ubicacion = cadena_ubicacion[5].trim().substring(1);

                                        textToSpeeach = "Lleve producto a " + rack + ". "
                                                + " Tramo: " + tramo + "."
                                                + " Columna: " + columna + "."
                                                + " Nivel: " + nivel + "."
                                                + " Posición: " + pos + "."
                                                + " Y Escanee: " + ubicacion;

                                    }else if (cadena_ubicacion.length==5){

                                        rack = cadena_ubicacion[0].trim().substring(0);
                                        columna = cadena_ubicacion[1].trim().substring(1);
                                        nivel = cadena_ubicacion[2].trim().substring(1);
                                        pos = cadena_ubicacion[3].trim().substring(0);
                                        ubicacion = cadena_ubicacion[4].trim().substring(1);

                                        textToSpeeach = "Lleve producto a " + rack + ". "
                                                + " Columna: " + columna + "."
                                                + " Nivel: " + nivel + "."
                                                + " Posición: " + pos + "."
                                                + " Y Escanee: " + ubicacion;
                                    }else if (cadena_ubicacion.length>0){

                                        pos = cadena_ubicacion[0].trim().substring(0);
                                        ubicacion = cadena_ubicacion[1].trim().substring(0);

                                        textToSpeeach = "Lleve producto a "
                                                + " Ubicación: " + pos + "."
                                                + " Y Escanee: " + ubicacion;
                                    }

                                    float speed = 1f;
                                    float pitch = 1f;
                                    mTTS.setPitch(pitch);
                                    mTTS.setSpeechRate(speed);

                                    if (cadena_ubicacion.length==0){
                                     textToSpeeach = "Enséñame por favor, donde ubicar el producto";
                                    }

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        mTTS.speak(textToSpeeach,TextToSpeech.QUEUE_FLUSH,null,null);
                                    } else {
                                        mTTS.speak(textToSpeeach, TextToSpeech.QUEUE_FLUSH, null);
                                    }

                                }
                            }else{
                                Log.e("tts","No he podido inicializar el TTS :(");
                            }
                        }
                    });

                }

            }

            if (inferir_origen_en_cambio_ubic) {
                txtUbicDestino.requestFocus();
            } else {
                if (!txtLicPlate.getText().toString().isEmpty() && !txtCodigoPrd.getText().toString().isEmpty()) {
                    txtUbicDestino.requestFocus();
                }else{
                    txtCantidad.requestFocus();
                }
            }

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProducto(){

        try {

            progress.setMessage("Cargando datos del producto");
            progress.show();

            BeProductoUbicacion = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProductoUbicacion != null){
                IdProductoUbicacion=BeProductoUbicacion.getIdProducto();

                lblDescProducto.setTextColor(Color.BLUE);
                cvProd = BeProductoUbicacion;
                cvProdID = BeProductoUbicacion.IdProducto;
                lblDescProducto.setText (BeProductoUbicacion.Nombre);
                cvPropID = BeProductoUbicacion.IdPropietario;
                cvUMBID = BeProductoUbicacion.IdUnidadMedidaBasica;

                if (BeProductoUbicacion.getControl_peso()){
                    trPeso.setVisibility(View.VISIBLE);
                }else{
                    trPeso.setVisibility(View.GONE);
                }

                //Llama al método del WS Get_Estados_By_IdPropietario
                execws(10);

            }else{
                inicializaTareaSinUbic();
                lblDescProducto.setTextColor(Color.RED);
                cvProdID = 0;
                lblDescProducto.setText ("Código no válido");
                throw new Exception("Producto no existe");
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processStockLP(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            productoList = xobj.getresult(clsBeProductoList.class,"Get_Stock_By_Lic_Plate");

            if (escaneoPallet && productoList == null) {
                lblDescProducto.setTextColor(Color.RED);
                cvProdID = 0;
                lblDescProducto.setText ("Código de licencia no válido");
                progress.cancel();
            }else{

                if (escaneoPallet && productoList != null){
                    if (inferir_origen_en_cambio_ubic) {
                        //if (txtUbicOrigen.getText().toString().isEmpty()) {
                        int ubic = productoList.items.get(0).Stock.IdUbicacion;
                        String ubicompleta = productoList.items.get(0).Stock.NombreUbicacion;

                        txtUbicOrigen.setText(String.valueOf(ubic));
                        lblUbicCompleta.setText(ubicompleta);
                        cvUbicOrigID = ubic;
                        /*} else {
                            int tmpUbic = Integer.valueOf(txtUbicOrigen.getText().toString());
                            cvUbicOrigID = tmpUbic;
                        }*/
                    }

                    List AuxList = stream(productoList.items)
                            .where(c->c.Stock.IdUbicacion==cvUbicOrigID)
                            .toList();

                    if (AuxList.size() == 0){
                        msgbox("La licencia no se encuentra en la ubicación: " + cvUbicOrigID);
                        lblDescProducto.setTextColor(Color.RED);
                        cvProdID = 0;
                        lblDescProducto.setText ("Licencia N.E.E.U");
                        progress.cancel();
                    }else{

                        productoList = new clsBeProductoList();
                        productoList.items = AuxList;

                        if (AuxList.size() == 1){

                            BeProductoUbicacion = productoList.items.get(0);
                            BeStockPallet = productoList.items.get(0).Stock;

                            txtCodigoPrd.setText(BeProductoUbicacion.getCodigo());

                            lblDescProducto.setTextColor(Color.BLUE);
                            lblDescProducto.setText(BeProductoUbicacion.getNombre());

                            cvProd = BeProductoUbicacion;
                            cvProdID = BeProductoUbicacion.getIdProducto();
                            cvPropID = BeProductoUbicacion.getIdPropietario();
                            cvUMBID = BeProductoUbicacion.getIdUnidadMedidaBasica();

                            if (BeProductoUbicacion.getControl_peso()){
                                trPeso.setVisibility(View.VISIBLE);
                            }else{
                                trPeso.setVisibility(View.GONE);
                            }

                            cvLote = BeStockPallet.Lote;
                            cvPresID = BeStockPallet.IdPresentacion;
                            //#AT20220713 Asigne valor a la variable cvAtrib
                            cvAtrib = BeStockPallet.Atributo_variante_1;
                            cvEstOrigen = BeStockPallet.IdProductoEstado;
                            cvVence = app.strFecha(BeStockPallet.Fecha_Vence);

                            //Llama al método del WS Get_Estados_By_IdPropietario
                            execws(10);

                        } else {
                            //#AT 20220421 Cuando la lista de productos es mayor a 1
                            // es decir varios productos relacionados con la misma licencia
                            productoList = new clsBeProductoList();
                            productoList.items = stream(AuxList).distinct().toList();

                            BeProductoUbicacion = productoList.items.get(0);
                            BeStockPallet = productoList.items.get(0).Stock;

                            IdProductoUbicacion=BeProductoUbicacion.getIdProducto();
                            txtCodigoPrd.setText(BeProductoUbicacion.getCodigo());

                            lblDescProducto.setTextColor(Color.BLUE);
                            cvProd = BeProductoUbicacion;
                            cvProdID = BeProductoUbicacion.IdProducto;
                            lblDescProducto.setText (BeProductoUbicacion.Nombre);
                            cvPropID = BeProductoUbicacion.IdPropietario;
                            cvUMBID = BeProductoUbicacion.IdUnidadMedidaBasica;

                            if (BeProductoUbicacion.getControl_peso()){
                                trPeso.setVisibility(View.VISIBLE);
                            }else{
                                trPeso.setVisibility(View.GONE);
                            }

                            //Llama al método del WS Get_Estados_By_IdPropietario
                            execws(10);

                            /*progress.cancel();
                            msgbox("Escanee el producto que a ubicar");
                            txtCodigoPrd.requestFocus();*/
                        }
                    }
                }else{
                    //Llama a este método del WS Get_BeProducto_By_Codigo_For_HH
                    execws(3);
                }
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProductoUbicLP(){

        try {

            progress.setMessage("Cargando stock con licencias");
            progress.show();

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion_And_LicPlate");

            if (stockResList != null){
                //LlenaPresentaciones();
                setPresentacion();
            }else{
                msgbox("No hay existencias disponibles de este producto en esta ubicación o las existentes están reservadas");
                inicializaTarea(false);
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProductoUbic(){

        try {

            progress.setMessage("Cargando producto en esta ubicación");
            progress.show();

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion");

            if (stockResList != null){
                //LlenaPresentaciones();
                setPresentacion();
            }else{

                msgbox("El producto no existe en la ubicación origen");
                txtCodigoPrd.requestFocus();
                txtCodigoPrd.selectAll();
                progress.cancel();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processNuevoCorrelativoLP(){

        try {

            progress.setMessage("Obteniendo nueva licencia");
            progress.show();

            vNuevoPalletId = (String) xobj.getSingle("Get_Nuevo_Correlativo_LicensePlateResult",String.class);

            if (!vNuevoPalletId.isEmpty()){
                //Set_Nuevo_Pallet_Id
                execws(9);
            }else{
                msgbox("Error al obtener correlativo de licencia!");
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());

        }

    }

    private void processNuevoLPA(){

        try {

            vNuevoPalletId = "";

            clsBeResolucion_lp_operador resolucionActivaLpByBodega = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");

            if (resolucionActivaLpByBodega !=null){

                gl.IdResolucionLpOperador = resolucionActivaLpByBodega.IdResolucionlp;

                float pLpSiguiente = resolucionActivaLpByBodega.Correlativo_Actual +1;
                float largoMaximo = String.valueOf(resolucionActivaLpByBodega.Correlativo_Final).length();

                int intLPSig = (int) pLpSiguiente;
                int MaxL = (int) largoMaximo;

                //#CKFK20220410 Reemplacé el código de arriba por esta línea
                String result = String.format("%0"+ MaxL + "d",intLPSig);

                 vNuevoPalletId= resolucionActivaLpByBodega.Serie + result;

            }else{
                gl.IdResolucionLpOperador =0;
                toast("Este usuario no tiene resoluciones configuradas");
            }

            if (!vNuevoPalletId.isEmpty()){
                //Set_Nuevo_Pallet_Id
                execws(9);
            }else{
                msgbox("Error al obtener correlativo de licencia!");
            }

        }catch (Exception e){
            mu.msgbox("processNuevoLP_packing: "+e.getMessage());
        }

    }

    private void processIdNuevoPallet(){

        try {

            progress.setMessage("Colocando nuevo pallet");
            progress.show();

            boolean result = (Boolean) xobj.getSingle("Set_Nuevo_Pallet_IdResult",boolean.class);

            progress.cancel();

            if (result){
                msgAskImpresoraLista("¿La impresora está lista y conectada?");
            }else{
                progress.cancel();
                msgbox("Ocurrió un error creando el Id del nuevo pallet");
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processEstadosProp(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            productoEstadoDestinoList = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario");

            if (productoEstadoDestinoList != null){
                LlenaEstadoDestino(cvPropID);
            }else{

                if (escaneoPallet && productoList != null) {
                    //LLama este procedimiento del WS Get_Productos_By_IdUbicacion_And_LicPlate
                    execws(6);
                }else{
                    //LLama este procedimiento del WS Get_Productos_By_IdUbicacion
                    execws(7);
                }

            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processCambio(){

        try {

            progress.setMessage("Procesando cambio de ubicación");
            progress.show();

            boolean actualizar = (Boolean) xobj.getSingle("Actualizar_Trans_Ubic_HH_DetResult",boolean.class);

            if (actualizar){
                msgAskExit(String.format("Cambio de %s aplicado",(gl.modo_cambio==1?"ubicación":"estado")));
            }else{
                msgbox("Ocurrió un error al procesar el cambio de ubicación");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

        progress.cancel();

    }

    private void processCambioUbicEstHH(){

        try{

            boolean resultado = false;

            progress.setMessage("Procesando cambio");
            progress.show();

            resultado = (Boolean) xobj.getSingle("Aplica_Cambio_Estado_Ubic_HHResult",boolean.class);
            vIdStockNuevo = (Integer) xobj.getSingle("pIdStockNuevo",int.class);
            vIdMovimientoNuevo= (Integer) xobj.getSingle("pIdMovimientoNuevo",int.class);

            if( resultado){

                if (CambioUbicExistencia) {
                    msgCambioUbicExistencia("Cambio de ubicación aplicado.");
                } else {

                    txtCodigoPrd.setText(gl.gCProdAnterior);
                    txtUbicOrigen.setText(gl.gCUbicAnterior);

                    if (cmbEstadoOrigen.getAdapter() != null && cmbEstadoOrigen.getAdapter().getCount() > 0) {
                        gl.gCEstadoAnterior = Integer.valueOf(cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0]);
                        gl.gCNomEstadoAnterior = cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[1];
                    } else {
                        gl.gCEstadoAnterior = -1;
                        gl.gCNomEstadoAnterior = "";
                    }

                    if (cmbVence.getAdapter() != null && cmbVence.getAdapter().getCount() > 0) {
                        gl.gCFechaAnterior = cmbVence.getSelectedItem().toString();
                    } else {
                        gl.gCFechaAnterior = "01/01/1900";
                    }

                    if (cmbLote.getAdapter() != null && cmbLote.getAdapter().getCount() > 0) {
                        gl.gCLoteAnterior = cmbLote.getSelectedItem().toString();
                    } else {
                        gl.gCLoteAnterior = "";
                    }

                    if (cmbPresentacion.getAdapter() != null && cmbPresentacion.getAdapter().getCount() > 0) {
                        gl.gCPresAnterior = Integer.valueOf(cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());
                        if (cmbPresentacion.getSelectedItem().toString().split(" - ").length > 1) {
                            gl.gCNomPresAnterior = cmbPresentacion.getSelectedItem().toString().split(" - ")[1];
                        }
                    } else {
                        gl.gCPresAnterior = -1;
                        gl.gCNomPresAnterior = "";
                    }

                    progress.cancel();

                    //#AT 202203011 Si ocultar mensajes es falso se muestran los mensajes de lo contrario se ocultan
                    if (!ocultar_mensajes) {
                        msgAsk(gl.modo_cambio == 1 ? "Cambio de ubicación aplicado" : "Cambio de estado aplicado");
                    } else {
                        completaProceso();
                    }
                }
            }
            lblCantidad.setText("Cantidad:");
        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processUbicacionDestSugerida(){

        try{

            progress.setMessage("Procesando ubicación destino sugerida");
            progress.show();

            lUbicSug = xobj.getresult(USUbicStrucStage5List.class,"ml_get_ubicacion_sugerida");

            if (lUbicSug != null){
                if(lUbicSug.items.size()>0){

                    txtUbicSug.setText(String.valueOf(lUbicSug.items.get(0).lUbicacionesVacias.items.get(0).IdUbicacion));
                    txtUbicDestino.setHint("Escanee " + lUbicSug.items.get(0).lUbicacionesVacias.items.get(0).IdUbicacion);
                    validaDestinoSug();

                }

            }else{

                cvUbicDestID = 0;
                toast("No existen ubicaciones sugeridas");
                progress.cancel();

                if (inferir_origen_en_cambio_ubic) {
                    txtUbicDestino.requestFocus();
                } else {
                    if (!txtLicPlate.getText().toString().isEmpty() && !txtCodigoPrd.getText().toString().isEmpty()) {
                        txtUbicDestino.requestFocus();
                    }else{
                        txtCantidad.requestFocus();
                    }
                }

            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processStockLP_AndCodigo(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            productoList = xobj.getresult(clsBeProductoList.class,"Get_Stock_By_Lic_Plate_And_Codigo");

            if (inferir_origen_en_cambio_ubic) {
                if (productoList == null) {
                    toast("Licencia o código ingresados no validos");
                    progress.cancel();
                    return;
                }
            }

            if (escaneoPallet && productoList == null) {
                lblDescProducto.setTextColor(Color.RED);
                cvProdID = 0;
                lblDescProducto.setText ("Licencia no válida.");
                progress.cancel();
            }else{

                if (escaneoPallet && productoList != null){

                    List AuxList = stream(productoList.items)
                            .where(c->c.Stock.IdUbicacion==cvUbicOrigID)
                            .toList();

                    if (AuxList.size() == 0){
                        msgbox("El pallet no se encuentra en la ubicación: " + cvUbicOrigID);
                        lblDescProducto.setTextColor(Color.RED);
                        cvProdID = 0;
                        lblDescProducto.setText ("LP N.E.E.U");
                        progress.cancel();
                    }else{

                        productoList = new clsBeProductoList();
                        productoList.items = AuxList;

                        BeProductoUbicacion = productoList.items.get(0);
                        BeStockPallet = productoList.items.get(0).Stock;

                        lblDescProducto.setTextColor(Color.BLUE);
                        lblDescProducto.setText(BeProductoUbicacion.getNombre());

                        cvProd = BeProductoUbicacion;
                        cvProdID = BeProductoUbicacion.getIdProducto();
                        cvPropID = BeProductoUbicacion.getIdPropietario();
                        cvUMBID = BeProductoUbicacion.getIdUnidadMedidaBasica();

                        cvLote = BeStockPallet.Lote;
                        cvPresID = BeStockPallet.IdPresentacion;
                        //#AT20220713 Asigne valor a la variable cvAtrib
                        cvAtrib = BeStockPallet.Atributo_variante_1;
                        cvEstOrigen = BeStockPallet.IdProductoEstado;
                        cvVence = app.strFecha(BeStockPallet.Fecha_Vence);

                        //Llama al método del WS Get_Estados_By_IdPropietario
                        execws(10);
                    }
                }else{
                    //Llama a este método del WS Get_BeProducto_By_Codigo_For_HH
                    //#AT 20220324 Se obtiene el idUbicacion para luego poder buscar por codigo de producto
                    if (inferir_origen_en_cambio_ubic) {
                        cvUbicOrigID = productoList.items.get(0).Stock.IdUbicacion;
                        txtUbicOrigen.setText(String.valueOf(cvUbicOrigID));
                        lblUbicCompleta.setText(productoList.items.get(0).Stock.NombreUbicacion);
                    }

                    execws(3);
                }
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processExisteLp(){

        try{

            Existe_Lp = xobj.getresult(Boolean.class,"Existe_Lp_By_Licencia_And_IdBodega");

            if (Existe_Lp){
                progress.cancel();
                txtCodigoPrd.requestFocus();
                //Get_Stock_By_Lic_Plate
                execws(5);
            }else{
                progress.cancel();
                mu.msgbox("Licencia no existe");
                txtLicPlate.selectAll();
                txtLicPlate.requestFocus();
            }

        }catch (Exception e){
            mu.msgbox("processExisteLp:"+e.getMessage());
        }
    }

    private void processPalletNoEstandar(){

        try{

            if (xobj!=null){
                EsPalletNoEstandar = xobj.getresult(Boolean.class,"Es_Pallet_No_Estandar");
            }else{
                EsPalletNoEstandar=false;
            }

            if (EsPalletNoEstandar){
                execws(20);
            }else{
                validaDestino();
            }

        }catch (Exception e){
            //error=e.getMessage();errorflag =true;msgbox(error);
            mu.msgbox("processPalletNoEstandar:"+e.getMessage());
        }
    }

    private void processTienePosiciones(){

        try{

            TienePosiciones = xobj.getresult(Integer.class,"Tiene_Posiciones");

            vPosiciones = TienePosiciones;

            validaDestino();

            //#EJC20220427: No pedir posiciones en cambio de ubicación cuando Pallet No estandar.
            //Solicitado por jefe de bodega CEALSA. en bodega 5.
//            if (TienePosiciones==0){
//               msgAskIngresePosiciones();
//            }else{
//                vPosiciones = TienePosiciones;
//                validaDestino();
//            }

        }catch (Exception e){
            mu.msgbox("processPalletNoEstandar:"+e.getMessage());
        }
    }

    private void msgAskIngresePosiciones() {

        try{

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Ingrese número de posiciones");

            vPosiciones = 0;

            final LinearLayout layout   = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            if(txtPosiciones.getParent()!= null){
                ((ViewGroup) txtPosiciones.getParent()).removeView(txtPosiciones);
            }

            txtPosiciones.setText("");
            txtPosiciones.requestFocus();

            layout.addView(txtPosiciones);

            alert.setView(layout);

            showkeyb();
            alert.setCancelable(false);
            alert.create();

            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    layout.removeAllViews();

                    vPosiciones=Integer.valueOf(txtPosiciones.getText().toString());

                    if (vPosiciones==0){
                        layout.removeAllViews();
                        msgAskIngresePosiciones();
                    }else{
                        validaDestino();
                    }

                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    layout.removeAllViews();
                }
            });

            alert.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void inicializaTarea(boolean finalizar){

        try{

            progress.setMessage("Inicializando tarea");
            progress.show();

            cvUbicOrigID = 0;
            txtCodigoPrd.setText("");
            txtLicPlate.setText("");
            lblDescProducto.setText("");
            cmbPresentacion.setAdapter(null);
            cmbLote.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbEstadoOrigen.setAdapter(null);
            cmbEstadoDestino.setAdapter(null);
            txtUbicSug.setText("");
            txtUbicDestino.setHint("");
            chkExplosionar.setChecked(false);
            tblExplosionar.setVisibility(View.GONE);

            cvProdID = 0;
            cvPresID = 0;
            cvAtrib = "";
            cvLote  = "";
            cvVence = "";
            cvUbicDestID = 0;
            cvUbicOrigID = 0;
            cvEstDestino = 0;
            cvEstOrigen = 0;
            vCantidadAUbicar = 0;
            vCantidadDisponible = 0;
            vPosiciones =0;
            licencia_reservada_completamente = false;
            reservada_parcialmente = false;

            lblUbicCompleta.setText("");

            lblCant.setText("");
            txtUbicDestino.setText("");
            lblUbicCompDestino.setText("");
            txtCantidad.setText("");
            txtPeso.setText("");
            txtCodigoPrd.setText("");

            //cmbPresentacion.setEnabled(false);
            tblPresentacion.setVisibility(View.GONE);
            cmbLote.setEnabled(true);
            cmbVence.setEnabled(true);
            cmbEstadoDestino.setEnabled(true);

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtPeso.setEnabled(true);
            txtCodigoPrd.setEnabled(true);
            txtLicPlate.setEnabled(true);

            validarDatos = false;
            vProcesar = false;

            btnGuardarCiega.setVisibility(View.VISIBLE);

            if(gl.modo_cambio==1 && finalizar){
                if (!inferir_origen_en_cambio_ubic) {
                    execws(1);
                } else {
                    progress.cancel();
                    txtLicPlate.requestFocus();
                }
            }else{
                txtUbicOrigen.requestFocus();
                progress.cancel();
            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void inicializaTareaSinUbic(){

        try{

            progress.setMessage("Inicializando tarea");
            progress.show();

            txtCodigoPrd.setText("");
            txtLicPlate.setText("");
            lblDescProducto.setText("");
            cmbPresentacion.setAdapter(null);
            cmbLote.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbEstadoOrigen.setAdapter(null);
            cmbEstadoDestino.setAdapter(null);
            txtUbicSug.setText("");
            txtUbicDestino.setHint("");
            chkExplosionar.setChecked(false);
            tblExplosionar.setVisibility(View.GONE);

            cvProdID = 0;
            cvPresID = 0;
            cvAtrib = "";
            cvLote  = "";
            cvVence = "";
            cvUbicDestID = 0;
            cvUbicOrigID = 0;
            cvEstDestino = 0;
            cvEstOrigen = 0;
            vCantidadAUbicar = 0;
            vCantidadDisponible = 0;
            licencia_reservada_completamente = false;
            reservada_parcialmente = false;

            lblCant.setText("");
            txtUbicDestino.setText("");
            txtCantidad.setText("");
            txtPeso.setText("");
            txtCodigoPrd.setText("");

            //cmbPresentacion.setEnabled(false);
            tblPresentacion.setVisibility(View.GONE);
            cmbLote.setEnabled(true);
            cmbVence.setEnabled(true);
            cmbEstadoDestino.setEnabled(true);

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtPeso.setEnabled(true);
            txtCodigoPrd.setEnabled(true);
            txtLicPlate.setEnabled(true);

            validarDatos = false;
            vProcesar = false;

            txtUbicOrigen.requestFocus();

            progress.cancel();

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void inicializaTareaLP(){

        try{

            progress.setMessage("Inicializando tarea");
            progress.show();

            txtCodigoPrd.setText("");
            lblDescProducto.setText("");
            cmbPresentacion.setAdapter(null);
            cmbLote.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbEstadoOrigen.setAdapter(null);
            cmbEstadoDestino.setAdapter(null);
            txtUbicSug.setText("");
            txtUbicDestino.setHint("");

            cvProdID = 0;
            cvPresID = 0;
            cvAtrib = "";
            cvLote  = "";
            cvVence = "";
            cvUbicDestID = 0;
            cvEstDestino = 0;
            vCantidadAUbicar = 0;
            vCantidadDisponible = 0;
            licencia_reservada_completamente = false;
            reservada_parcialmente = false;

            lblCant.setText("");
            txtUbicDestino.setText("");
            txtCantidad.setText("");
            txtPeso.setText("");
            txtCodigoPrd.setText("");

            //cmbPresentacion.setEnabled(false);
            tblPresentacion.setVisibility(View.GONE);
            cmbLote.setEnabled(true);
            cmbVence.setEnabled(true);
            cmbEstadoDestino.setEnabled(true);

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtPeso.setEnabled(true);
            txtCodigoPrd.setEnabled(true);
            txtLicPlate.setEnabled(true);

            validarDatos = false;
            vProcesar = false;

            txtUbicOrigen.requestFocus();

            progress.cancel();

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void msgAskExit(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_cambio_ubicacion_ciega.super.finish();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgCambioUbicExistencia(String msg) {
        ExDialog dialog = new ExDialog(this);
        dialog.setMessage(msg);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(frm_cambio_ubicacion_ciega.this, frm_consulta_stock.class));
                finish();
            }
        });

        dialog.show();

    }

    //#CKFK 20211215 Explosionar el producto de presentación a unidades
    private void msgAskExplosionar(String msg){

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Es_Explosion = true;
                    inicializaTarea(true);
                    msgAskImprimirEtiqueta("Imprimir etiqueta");
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    Es_Explosion = false;
                    inicializaTarea(true);
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgAskImprimirEtiqueta(String msg){

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Get_Nuevo_Correlativo_LicensePlate
                     execws(8);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){}
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgAskUbicacionOcupadaCompleta(){

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿La disponibilidad de la ubicación parece ser de 0% continuar de todas formas?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(21);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){

                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskUbicacionParcialmenteCompleta(double PorcentajeOcupacionPosicion){

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿La disponibilidad de la ubicación es de:" + PorcentajeOcupacionPosicion + "% continuar de todas formas?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(21);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){

                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskImpresoraLista(String msg){

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Imprime la etiqueta
                    Imprimir();
                    /*
                  ZPLString As String = String.Format( _
                                                    "^XA " & _
                                                    "^MMT " & _
                                                    "^PW700 " & _
                                                    "^LL0406 " & _
                                                    "^LS0 " & _
                                                    "^FT171,61^A0I,25,14^FH\^FD{0}^FS " & _
                                                    "^FT550,61^A0I,25,14^FH\^FD{1}^FS " & _
                                                    "^FT670,306^A0I,25,14^FH\^FD{2}^FS " & _
                                                    "^FT292,61^A0I,25,24^FH\^FDBodega:^FS " & _
                                                    "^FT670,61^A0I,25,24^FH\^FDEmpresa:^FS " & _
                                                    "^FT670,367^A0I,25,24^FH\^FDTOMIMS, WMS.  Product Barcode^FS " & _
                                                    "^FO2,340^GB670,0,14^FS " & _
                                                    "^BY3,3,160^FT670,131^BCI,,Y,N " & _
                                                    "^FD{3}^FS " & _
                                                    "^PQ1,0,1,Y " & _
                                                    "^XZ", gCodigoBodega, _
                                                    gNomEmpresa, _
                                                    BeStockPallet.Codigo_Producto + " - " + BeStockPallet.Nombre_Producto, _
                                                            ((vNuevoPalletId <> "", "$" + vNuevoPalletId, BeStockPallet.Codigo_Producto))

                                            frmPrintBarraPallet As New frmPrint
                                            frmPrintBarraPallet.ZplToPrint = ZPLString
                                            frmPrintBarraPallet.ShowDialog()
         */
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){}
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    //#AT20220520 Imprime etiqueta
    private void Imprimir(){

        try{

            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);

            if (!printerIns.isConnected()){
                printerIns.open();
            }

            if (printerIns.isConnected()){

                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);

                String zpl="";

                if (BeStockPallet!=null){

                    zpl = String.format("^XA \n" +
                                    "^MMT \n" +
                                    "^PW700 \n" +
                                    "^LL0406 \n" +
                                    "^LS0 \n" +
                                    "^FT231,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                    "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                    "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                                    "^FT292,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                    "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                    "^FT670,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS \n" +
                                    "^FO2,340^GB670,0,14^FS \n" +
                                    "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                    "^FD%4$s^FS \n" +
                                    "^PQ1,0,1,Y " +
                                    "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                            BeStockPallet.Codigo_Producto + " - " + BeStockPallet.Nombre_Producto,
                            (!vNuevoPalletId.isEmpty() ? "$" + vNuevoPalletId: BeStockPallet.Codigo_Producto));

                    if (!zpl.isEmpty()){
                        zPrinterIns.sendCommand(zpl);
                    }else{
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                    }

                    Thread.sleep(500);

                    // Close the connection to release resources.
                    printerIns.close();

                }else{
                    mu.msgbox("Información de producto no definida.");
                }

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

        }catch (Exception e){
            progress.cancel();
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir. No existe conexión a la impresora: "+ gl.MacPrinter);
            }else{
                mu.msgbox("Imprimir etiqueta cambio de ubicación: "+e.getMessage());
            }
        }finally {
            progress.cancel();
        }
    }

    private void validaOrigen(){

        try{

            if (!txtUbicOrigen.getText().toString().isEmpty()){

                //#AT 20220309 Se le asigna el valor de la ubicación de origen
                tmpUbicId = Integer.valueOf(txtUbicOrigen.getText().toString());
                bodega_ubicacion_origen = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar ubicacion origen
                execws(11);

            }else{
                lblUbicCompleta.setText("");
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void validaDestino(){

        try{

            progress.setMessage("Procesando ubicación destino...");
            progress.show();

            if (!txtUbicDestino.getText().toString().isEmpty()){

                bodega_ubicacion_destino = new clsBeBodega_ubicacion();
                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar ubicacion destino
                execws(12);
            }else
            {
                //#GT27072022_1635: Avisar que no se ha digitado el destino.
                mu.msgbox("No hay una úbicación destino ingresada.");
            }

            progress.cancel();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }

    }

    private void validaDestinoSug(){

        try{

            if (!txtUbicSug.getText().toString().isEmpty()){

                bodega_ubicacion_destino = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar ubicacion destino sugerida
                execws(16);
            }else{
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void datosOk(){

        try{

            boolean continua=true;
            validarDatos = true;

            execws(11);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void msgAsk(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    if( escaneoPallet && productoList != null){
                        //#CKFK20210610 agregué esta validación para que si no tiene presentación no chkExplosionarlosione el material
                        if (BeStockPallet.getIdPresentacion()!=0) {

                            if (BeStockPallet.CantidadPresentacion != vCantidadAUbicar) {
                                msgAskExplosionar("La ubicación parcial de pallet requiere explosionar el material, ¿generar nuevo palletId y continuar?");
                            } else {
                                inicializaTarea(true);
                            }

                        }else{
                            if( BeStockPallet.CantidadUmBas != vCantidadAUbicar){
                                msgAskExplosionar("La ubicación parcial de pallet requiere explosionar el material, ¿generar nuevo palletId y continuar?");
                            }else{
                                inicializaTarea(true);
                            }
                        }

                    }else{
                        inicializaTarea(true);
                    }
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void completaProceso() {

        if( escaneoPallet && productoList != null){
            //#CKFK20210610 agregué esta validación para que si no tiene presentación no explosione el material
            if (BeStockPallet.getIdPresentacion()!=0) {

                if (BeStockPallet.CantidadPresentacion != vCantidadAUbicar) {
                    Toast.makeText(frm_cambio_ubicacion_ciega.this, "La ubicación parcial de pallet requiere explosionar el material.", Toast.LENGTH_SHORT).show();
                    Es_Explosion = true;
                    inicializaTarea(true);
                    msgAskImprimirEtiqueta("Imprimir etiqueta");
                    //msgAskExplosionar("La ubicación parcial de pallet requiere explosionar el material, ¿generar nuevo palletId y continuar?");
                } else {
                    Toast.makeText(getBaseContext(), "Ubicación realizada correctamente.", Toast.LENGTH_LONG).show();
                    inicializaTarea(true);
                }

            }else{
                if( BeStockPallet.CantidadUmBas != vCantidadAUbicar){
                    Toast.makeText(frm_cambio_ubicacion_ciega.this, "La ubicación parcial de pallet requiere explosionar el material.", Toast.LENGTH_SHORT).show();
                    Es_Explosion = true;
                    inicializaTarea(true);
                    msgAskImprimirEtiqueta("Imprimir etiqueta");
                    //msgAskExplosionar("La ubicación parcial de pallet requiere explosionar el material, ¿generar nuevo palletId y continuar?");
                }else{
                    Toast.makeText(getBaseContext(), "Ubicación realizada correctamente.", Toast.LENGTH_LONG).show();
                    inicializaTarea(true);
                }
            }
        }else{
            Toast.makeText(getBaseContext(), "Ubicación realizada correctamente.", Toast.LENGTH_LONG).show();
            inicializaTarea(true);
        }
    }


    private void msgAskAplicar(String msg) {

        try{

            //#GT10032022: set en el boton Si para facilitar el ENTER del teclado
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                   this);

            // set title
            alertDialogBuilder.setTitle(R.string.app_name);

            // set dialog message
            alertDialogBuilder
                    .setMessage("¿" + msg + "?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Llamar método para aplicar el cambio de estado
                            aplicarCambio();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            {btnGuardarCiega.setVisibility(View.VISIBLE); }
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
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }

    }

    private void msgAskImprimir(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg + "\n\n Impresora: " + gl.MacPrinter);
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Código de estado", (dialog1, which) -> {
                progress.setMessage("Imprimiendo Estado");
                progress.show();
                progress.cancel();
            });

            dialog.setNeutralButton("Salir", (dialog13, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object()
            {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    public void Imprimir(View view){
        msgAskImprimir("Imprimir Estado");
    }

    private void aplicarCambio() {

        try{

            progress.setMessage("Aplicando el cambio");
            progress.show();

            if (!Crear_Movimiento_Ubicacion_ND(gl.modo_cambio == 1? false: true)){
                return;
            }

            vStockRes.IdProductoBodega = cvProd.IdProductoBodega;
            vStockRes.IdUbicacion = cvUbicOrigID;

            if( BeProductoUbicacion.Control_lote){
                vStockRes.Lote = cmbLote.getSelectedItem().toString();
            }else{
                vStockRes.Lote = "";
            }

            if( BeProductoUbicacion.Control_vencimiento){
                vStockRes.Fecha_Vence = app.strFechaXMLCombo(cmbVence.getSelectedItem().toString());
            }else{
                vStockRes.Fecha_Vence = "01/01/1900";
            }

            vStockRes.CantidadUmBas = vCantidadAUbicar;

            //vStockRes.Peso = cvStockItem.Peso;
            //#GT27072022_1655: envio el peso recalculado cuando se modificó la cantidad a reubicar.
            vStockRes.Peso = Double.valueOf(txtPeso.getText().toString());

            vStockRes.IdPresentacion =cvPresID;
            vStockRes.IdProductoEstado = cvEstOrigen;
            vStockRes.Fecha_ingreso = app.strFechaXML(du.getFechaActual());
            vStockRes.ValorFecha = app.strFechaXML(du.getFechaActual());
            vStockRes.Pallet_No_Estandar = EsPalletNoEstandar;

            if( escaneoPallet && productoList != null){

               vStockRes.Lic_plate = BeStockPallet.Lic_plate;

               //#AT20220725 Agregue la validación para que el IdPresentacion  sea diferente a 0
                if( BeStockPallet.Factor > 0 && cvPresID != 0) {
                    vStockRes.CantidadUmBas = vCantidadAUbicar * BeStockPallet.Factor;
                }

            }else if ( cvPresID != 0){
                vStockRes.CantidadUmBas = vCantidadAUbicar * vFactorPres;
            }

            if (Es_Explosion_Manual){
                gMovimientoDet.IdTipoTarea = 20;
            }

            //#EJC20220330: De momento mover licencias completas y no permitir explosión.
            //Por solicitud de mercosal se va permitir el cambio de ubicacion de licencias que esten reservadas parciales o completas
            //Por esta razón vamos a poner esto en comentario
            /*if (gl.Permitir_Cambio_Ubic_Producto_Picking){
                if(licencia_reservada_completamente){
                    vStockRes.IdUbicacionVirtual= cvUbicDestID;
                }
            }*/

            if (gl.modo_cambio == 1) {
                if (gl.Permitir_Cambio_Ubic_Producto_Picking) {
                    if (licencia_reservada_completamente || reservada_parcialmente) {
                        execws(22);
                    } else {
                        //Aplica_Cambio_Estado_Ubic_HH(gMovimientoDet, vStockRes, vIdStockNuevo, vIdMovimientoNuevo);
                        execws(14);
                    }
                } else {
                    //Aplica_Cambio_Estado_Ubic_HH(gMovimientoDet, vStockRes, vIdStockNuevo, vIdMovimientoNuevo);
                    execws(14);
                }
            } else {
                //Aplica_Cambio_Estado_Ubic_HH(gMovimientoDet, vStockRes, vIdStockNuevo, vIdMovimientoNuevo);
                execws(14);
            }

        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }
    }

    public void AplicarCambio(View view){

        AplicarCambioBoton();
    }

    public  void AplicarCambioBoton(){

        try{

            //#CKFK 20210202 Agregué estas validaciones para evitar que se guarden datos incorrectos
            //******************

            datosCorrectos=true;

            if (cvUbicOrigID == 0) {
                msgbox("Ubicación origen no válida");
                txtUbicOrigen.requestFocus();
                datosCorrectos = false;
            }

            if (cvProdID == 0) {
                msgbox("Producto no válido");
                txtCodigoPrd.requestFocus();
                datosCorrectos = false;
            }

            if (vCantidadDisponible == 0) {
                msgbox("Cantidad disponible es 0, no se puede realizar el movimiento");
                txtCodigoPrd.requestFocus();
                datosCorrectos = false;
            }

            if (gl.modo_cambio==2) {
                if (cvEstDestino == 0 || txtUbicDestino.getText().toString().contains("")){
                    msgbox("Estado destino incorrecto");
                    cmbEstadoDestino.requestFocus();
                    datosCorrectos = false;
                }



            }

            vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString().replace(",",""));
            vCantidadDisponible = Double.parseDouble(lblCant.getText().toString().replace(",",""));

            if (vCantidadAUbicar<0) {
                mu.msgbox("La cantidad no puede ser negativa");
                txtCantidad.requestFocus();
                datosCorrectos = false;
            }

            if (vCantidadAUbicar==0) {
                msgbox("La cantidad debe ser mayor que 0");
                txtCantidad.requestFocus();
                datosCorrectos = false;
            }

            if (vCantidadAUbicar> vCantidadDisponible) {
                msgbox("Cantidad incorrecta") ;
                txtCantidad.requestFocus();
                datosCorrectos = false;
            }

            //#GT2772022_1550: el recalculo del peso, se hace cuando se modifica la cantidad a reubicar
            //para guardar ya se tiene calculado en los inputs, ya no se requiere volver a hacerlo
            //Recalcula_Peso();


            if(cvUbicDestID == 0 && txtUbicDestino.getText().toString().isEmpty()){
                msgbox("La ubicación de destino no puede ser vacía");
                txtUbicDestino.requestFocus();
                datosCorrectos = false;
            }

            if ((cvUbicOrigID == cvUbicDestID) && (gl.modo_cambio ==1)) {
                msgbox("La ubicación de destino coincide con la de origen");
                txtUbicDestino.requestFocus();
                datosCorrectos = false;
            }

            if (!datosCorrectos) return;

            //********************

            progress.setMessage("Aplicando cambio de ubicación");
            progress.show();

            btnGuardarCiega.setVisibility(View.INVISIBLE);

            vProcesar = true;

            pStock = new clsBeStock();
            pStock.IdUbicacion = cvUbicOrigID;
            pStock.IdProductoBodega = BeProductoUbicacion.getIdProductoBodega();
            pStock.IdProductoEstado = cvEstOrigen;
            pStock.IdPresentacion = cvPresID;
            pStock.IdUnidadMedida = BeProductoUbicacion.IdUnidadMedidaBasica;
            pStock.Fecha_vence = du.convierteFechaDiagonal(cvVence);
            pStock.Lote = cvLote;
            //#CKFK 20210827 Se le agregó a la clase el LicPlate
            pStock.Lic_plate = pLicensePlate;

            //Es_Pallet_No_Estandar
            execws(19);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }finally {
            progress.cancel();
        }
    }

    private boolean Crear_Movimiento_Ubicacion_ND(boolean EsCambioEstado) {

        try{

            progress.setMessage("Creando el movimiento");

            // The preferred idiom for iterating over collections and arrays
            for (clsBeVW_stock_res st : stockResList.items) {

                if(escaneoPallet &&  productoList != null ) {
                    if (st.IdStock == cvStockID && st.Lic_plate.equals(BeStockPallet.Lic_plate)) {
                        cvStockItem = st;
                        break;}
                }else{
                    if(BeProductoUbicacion.Control_lote && BeProductoUbicacion.Control_vencimiento ) {
                        if (st.IdStock == cvStockID && st.Lote.equals(cvLote) && app.strFecha(st.Fecha_Vence).equals(cvVence) &&
                                st.IdPresentacion == cvPresID && st.IdProductoEstado == cvEstOrigen && st.IdUnidadMedida == cvUMBID) {
                            cvStockItem = st;
                            break;
                        }
                    }else if( !BeProductoUbicacion.Control_lote && BeProductoUbicacion.Control_vencimiento ) {
                        if (st.IdStock == cvStockID && app.strFecha(st.Fecha_Vence).equals(cvVence) &&
                                st.IdPresentacion == cvPresID && st.IdProductoEstado == cvEstOrigen) {
                            cvStockItem = st;
                            break;
                        }
                    }else if(BeProductoUbicacion.Control_lote &&  !BeProductoUbicacion.Control_vencimiento ) {
                        if (st.IdStock == cvStockID && st.Lote.equals(cvLote) && st.IdPresentacion == cvPresID &&
                                st.IdProductoEstado == cvEstOrigen && st.IdUnidadMedida == cvUMBID) {
                            cvStockItem = st;
                            break;
                        }
                    }else if(st.IdStock == cvStockID && st.IdPresentacion == cvPresID &&
                            st.IdProductoEstado == cvEstOrigen && st.IdUnidadMedida == cvUMBID ){
                        cvStockItem = st;
                        break;}
                }
            }

        }catch(Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox( ex.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
            return false;
        }

        try{

            gMovimientoDet = new clsBeTrans_movimientos();

            gMovimientoDet.IdMovimiento = 0;
            gMovimientoDet.IdEmpresa = gl.IdEmpresa;
            gMovimientoDet.IdBodegaOrigen = gl.IdBodega;
            gMovimientoDet.IdTransaccion = 1;
            gMovimientoDet.IdPropietarioBodega = cvPropID;
            gMovimientoDet.IdProductoBodega = cvProd.IdProductoBodega;
            gMovimientoDet.IdUbicacionOrigen = cvUbicOrigID;
            gMovimientoDet.IdUbicacionDestino = cvUbicDestID;

            /*if(cmbPresentacion.getAdapter()!=null  && cmbPresentacion.getAdapter().getCount()>0){
                gMovimientoDet.IdPresentacion = (Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString()) == -1? 0: Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString()));
            }else{
                gMovimientoDet.IdPresentacion = 0;
            }*/

            if (cvPresID > 0) {
                gMovimientoDet.IdPresentacion = cvPresID;
            } else {
                gMovimientoDet.IdPresentacion = 0;
            }

            if(cmbEstadoOrigen.getAdapter()!=null && cmbEstadoOrigen.getAdapter().getCount()>0){
                gMovimientoDet.IdEstadoOrigen = Integer.valueOf( cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0].toString());
            }else{
                gMovimientoDet.IdEstadoOrigen = 0;
            }

            gMovimientoDet.IdEstadoDestino = cvEstDestino;

            if(gl.modo_cambio == 1 ){
                gMovimientoDet.IdEstadoDestino = gMovimientoDet.IdEstadoOrigen;}

            gMovimientoDet.IdUnidadMedida = cvStockItem.IdUnidadMedida;

            if(EsCambioEstado ) {
                gMovimientoDet.IdTipoTarea = 3;
            }else{
                if (!Es_Explosion && !Es_Explosion_Manual){
                    gMovimientoDet.IdTipoTarea = 2;
                }else{
                    if (Es_Explosion_Manual){
                       gMovimientoDet.IdTipoTarea = 20;
                    }
                }
            }

            gMovimientoDet.IdBodegaDestino = gl.IdBodega;
            gMovimientoDet.IdRecepcion = cvStockItem.IdRecepcionEnc;
            gMovimientoDet.Cantidad = vCantidadAUbicar;
            gMovimientoDet.Serie = cvStockItem.Serial;


            //#GT2772022_1850: se envia el peso del textobox si al caso, se modificó la cantidad e hizo recalculo.
            if(BeProductoUbicacion.Control_peso){
                gMovimientoDet.Peso =  Double.valueOf(txtPeso.getText().toString());
            }else{
                gMovimientoDet.Peso = 0;
            }


            if(cmbLote.getAdapter()!=null  && cmbLote.getAdapter().getCount()>0){
                gMovimientoDet.Lote = cmbLote.getSelectedItem().toString();
            }else{
                gMovimientoDet.Lote = "";
            }

            clsBeProducto bprod = new clsBeProducto();

            bprod = BeProductoUbicacion;

            if(bprod.Control_vencimiento ){
                if(cmbVence.getAdapter()!=null && cmbVence.getAdapter().getCount()>0){
                    gMovimientoDet.Fecha_vence = app.strFechaXMLCombo(cmbVence.getSelectedItem().toString());
                }else{
                    gMovimientoDet.Fecha_vence = app.strFechaXMLCombo("01/01/1900");
                }

            }

            gMovimientoDet.Fecha = app.strFechaXML(du.getFechaActual());

            if(escaneoPallet &&  productoList != null ) {
                gMovimientoDet.Barra_pallet = BeStockPallet.Lic_plate;
            }else{
                gMovimientoDet.Barra_pallet = "";
            }

            gMovimientoDet.Hora_ini =  app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Hora_fin =  app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Fecha_agr =  app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Usuario_agr = String.valueOf(gl.IdOperador);
            gMovimientoDet.Cantidad_hist = gMovimientoDet.Cantidad;
            gMovimientoDet.Peso_hist = gMovimientoDet.Peso;
            gMovimientoDet.setIsNew(true);

            return true;

        }catch(Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox( ex.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
            return false;
        }

    }

    public void Regresar(View view){
        finish();
    }

}
