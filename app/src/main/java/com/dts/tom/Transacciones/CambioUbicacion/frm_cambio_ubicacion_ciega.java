package com.dts.tom.Transacciones.CambioUbicacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacionList;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.classes.Transacciones.Movimiento.USUbicStrucStage5.USUbicStrucStage5List;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import com.dts.base.WebService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_cambio_ubicacion_ciega extends PBase {

    private frm_cambio_ubicacion_ciega.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtUbicOrigen, txtCodigoPrd, txtCantidad, txtUbicDestino;
    private TextView lblUbicCompleta, lblDescProducto, lblLote, lblVence, lblEstadoDestino, lblCant,lblTituloForma,lblUbicCompDestino;
    private Spinner cmbPresentacion, cmbLote, cmbVence, cmbEstadoOrigen, cmbEstadoDestino;
    private Button btnGuardarCiega;

    private clsBeMotivo_ubicacionList pListBeMotivoUbicacion = new clsBeMotivo_ubicacionList();

    private clsBeProducto cvProd = new clsBeProducto();
    private clsBeProductoList productoList = new clsBeProductoList();

    private clsBeVW_stock_res vStockRes = new clsBeVW_stock_res();
    private clsBeVW_stock_resList stockResList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList lotesList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList venceList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList presentacionList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList productoEstadoOrigenList = new clsBeVW_stock_resList();

    /*private clsBeProducto producto_ubicacion = new clsBeProducto();
    private ArrayList<clsBeProducto> productoArrayList = new ArrayList<clsBeProducto>();
    private ArrayList<clsBeVW_stock_res> fechaVenceArrayList = new ArrayList<clsBeVW_stock_res>();
    private ArrayList<clsBeVW_stock_res> loteArrayList = new ArrayList<clsBeVW_stock_res>();
    private clsBeProducto_estado producto_estado_origen = new clsBeProducto_estado();
    private ArrayList<clsBeProducto_estado> productoEstadoOrigenArrayList = new ArrayList<clsBeProducto_estado>();
    private clsBeProducto_estado producto_estado_destino = new clsBeProducto_estado();
    private ArrayList<clsBeProducto_estado> productoEstadoDestinoArrayList = new ArrayList<clsBeProducto_estado>();
    private clsBeProducto_Presentacion presentacion = new clsBeProducto_Presentacion();
    private ArrayList<clsBeProducto_Presentacion> presentacionArrayList = new ArrayList<clsBeProducto_Presentacion>();*/


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
    private int cvEstOrigen;
    private int cvEstDestino;
    private int cvUbicDestID;
    private int cvStockID;
    private String cvAtrib;
    private int cvPropID;
    private int cvUMBID;
    private double vFactorPres;
    private boolean validarDatos = false;
    private boolean datosCorrectos = false;
    private boolean vProcesar =false;

    private boolean Es_Explosion = false;
    private int vIdStockNuevo = 0;
    private int vIdMovimientoNuevo= 0;

    private boolean escaneoPallet;

    private clsBeTrans_movimientos gMovimientoDet;
    private clsBeBodega_ubicacion bodega_ubicacion_destino;
    private clsBeBodega_ubicacion bodega_ubicacion_origen;
    private clsBeProducto_estado gProdEstado;
    private clsBeVW_stock_res cvStockItem;
    private clsBeVW_stock_res BeStockPallet;

    private clsBeProducto BeProductoUbicacion;
    private int IdProductoUbicacion;
    private String vNuevoPalletId;
    private String pLicensePlate;

    private double vCantidadAUbicar, vCantidadDisponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frm_cambio_ubicacion_ciega);

            super.InitBase();

            ws = new frm_cambio_ubicacion_ciega.WebServiceHandler(frm_cambio_ubicacion_ciega.this, gl.wsurl);
            xobj = new XMLObject(ws);

            txtUbicOrigen = (EditText) findViewById(R.id.txtUbicOrigen);
            txtCodigoPrd = (EditText) findViewById(R.id.txtCodigoPrd);
            txtCantidad = (EditText) findViewById(R.id.txtCantidad);
            txtUbicDestino = (EditText) findViewById(R.id.txtUbicDestino);

            lblUbicCompleta = (TextView) findViewById(R.id.lblUbicCompleta);
            lblDescProducto = (TextView) findViewById(R.id.lblDescProducto);
            lblLote = (TextView) findViewById(R.id.lblLote);
            lblVence = (TextView) findViewById(R.id.lblVence);
            lblEstadoDestino = (TextView) findViewById(R.id.lblEstadoDestino);
            lblCant = (TextView) findViewById(R.id.lblCant);
            lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
            lblUbicCompDestino = (TextView) findViewById(R.id.lblUbicCompDestino);

            cmbPresentacion = (Spinner) findViewById(R.id.cmbPresentacion);
            cmbLote = (Spinner) findViewById(R.id.cmbLote);
            cmbVence = (Spinner) findViewById(R.id.cmbVence);
            cmbEstadoOrigen = (Spinner) findViewById(R.id.cmbEstadoOrigen);
            cmbEstadoDestino = (Spinner) findViewById(R.id.cmbEstadoDestino);

            btnGuardarCiega = (Button) findViewById(R.id.btnGuardarCiega);

            cmbEstadoDestino.setVisibility(gl.modo_cambio == 1 ? View.GONE : View.VISIBLE);
            lblEstadoDestino.setVisibility(gl.modo_cambio == 1 ? View.GONE : View.VISIBLE);

            lblTituloForma.setText(String.format("Cambio de %s",(gl.modo_cambio==1?"ubicación N.D.":"estado N.D")));

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
                execws(1);
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

                        cvPresID = Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());
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
                    llenaDatosProducto();
                }

                return false;
            }
        });

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

        txtUbicDestino.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            validaDestino();
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
                            if(Double.valueOf(txtCantidad.getText().toString())>0) {
                                txtUbicDestino.requestFocus();
                            }
                    }
                }
                return false;
            }
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

            }else{
                LlenaLotes();
            }

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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
            if (txtCodigoPrd.getText().toString().startsWith("$") ||
                    txtCodigoPrd.getText().toString().startsWith("(01)") ||
                    txtCodigoPrd.getText().toString().startsWith(vStarWithParameter)) {

                //Es una barra de pallet válida por tamaño
                int vLengthBarra = txtCodigoPrd.getText().toString().length();

                if (vLengthBarra >= 16) {

                    escaneoPallet = true;

                    pLicensePlate = txtCodigoPrd.getText().toString().replace("$", "");

                    //Llama al método del WS Get_Stock_By_Lic_Plate
                    execws(5);

                }

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
                    if (cmbLote.getAdapter().getCount() == 1) {
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
            AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == cvProdID)
                    .where(c -> c.IdPresentacion == cvPresID)
                    .where(c -> c.Lote.equals(cvLote))
                    .where(c -> c.Atributo_variante_1 == (cvAtrib == null ? "" : cvAtrib))
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
                vCantidadAUbicar =tmpStockResList.items.get(0).getCantidadUmBas() - tmpStockResList.items.get(0).CantidadReservadaUMBas;
                vFactorPres = (tmpStockResList.items.get(0).getFactor()==0?1:tmpStockResList.items.get(0).getFactor());
            }else{
                vCantidadAUbicar = 0;
                cvStockID =0;
            }

            if( escaneoPallet && productoList != null) {

                if(BeStockPallet.Factor >0)
                {
                    vCantidadAUbicar = (vCantidadAUbicar / BeStockPallet.Factor);
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
                txtCantidad.selectAll();
            }

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);

            txtCantidad.requestFocus();

            fechaVenceU = app.strFechaXMLCombo(cvVence);
            execws(15);

        }catch(Exception ex){
            msgbox("Llena cantidad " + ex.getMessage());
        }finally {
            progress.cancel();
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
                    processNuevoCorrelativoLP();
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
                        callMethod("Get_IdUbic_Ciega_Recepcion_By_IdBodega","pIdBodega",gl.IdBodega);
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
                                "pIdProductoBodega",BeProductoUbicacion.IdProductoBodega,
                                "pLicPlate",BeStockPallet.Lic_plate);

                        break;
                    case 7://Obtiene el stock de un producto en una ubicacion
                        callMethod("Get_Productos_By_IdUbicacion",
                                "pIdUbicacion", txtUbicOrigen.getText().toString(),
                                "pIdProductoBodega",BeProductoUbicacion.IdProductoBodega);
                        break;
                    case 8://Obtiene el nuevo correlativo de un License Plate
                        callMethod("Get_Nuevo_Correlativo_LicensePlate",
                                "pIdEmpresa", gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,
                                "pIdPropietario",BeStockPallet.IdPropietario,
                                "pIdProducto",BeStockPallet.IdProducto);

                        break;
                    case 9://Actualiza la tabla trans_movimientos
                        callMethod("Set_Nuevo_Pallet_Id",
                                "pIdBodega",gl.IdBodega,
                                "pIdUsuario",gl.OperadorBodega.getIdOperador(),
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicOrigen.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 12://Valida la ubicación destino
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicDestino.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 13:
                        callMethod("Actualizar_Trans_Ubic_HH_Det","oBeTrans_ubic_hh_det", gl.tareadet,
                                "pMovimiento",gMovimientoDet);
                        break;
                    case 14:
                        callMethod("Aplica_Cambio_Estado_Ubic_HH",
                                "pMovimiento",gMovimientoDet,
                                "pStockRes",vStockRes,
                                "pIdStockNuevo",vIdStockNuevo,
                                "pIdMovimientoNuevo",vIdMovimientoNuevo);
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicDestino.getText().toString(),
                                "pIdBodega",gl.IdBodega);
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

            cvUbicOrigID = (Integer) xobj.getSingle("Get_IdUbic_Ciega_Recepcion_By_IdBodegaResult",int.class);

            if (cvUbicOrigID > 0){
                txtUbicOrigen.setText(String.valueOf(cvUbicOrigID));
                validaOrigen();
            }else{
                txtUbicOrigen.setText("");
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

                if ((cvUbicOrigID == cvUbicDestID) && (gl.modo_cambio ==1)){
                    msgbox("La ubicación de destino coincide con la de origen");
                    txtUbicDestino.requestFocus();
                    datosCorrectos = false;
                }

                if (!datosCorrectos) return;

                progress.cancel();
                msgAskAplicar((gl.modo_cambio ==1? "Mover producto a ubicación: " + bodega_ubicacion_destino.Descripcion: "Aplicar cambio de estado?"));

            }else{
                progress.cancel();
                txtCodigoPrd.requestFocus();
                txtCodigoPrd.selectAll();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }

    }

    private void processUbicDestino(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            bodega_ubicacion_destino = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion_destino == null){
                vProcesar = false;
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("Ubicación destino incorrecta");
            }else{
                cvUbicDestID=bodega_ubicacion_destino.getIdUbicacion();
                lblUbicCompDestino.setText(bodega_ubicacion_destino.getNombreCompleto());
                if(gl.modo_cambio==2 && !vProcesar){
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
            }

            txtCantidad.requestFocus();
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
                lblDescProducto.setText ("Código de LP no válido");
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
                        cvEstOrigen = BeStockPallet.IdProductoEstado;
                        cvVence = app.strFecha(BeStockPallet.Fecha_Vence);

                        //Llama al método del WS Get_Estados_By_IdPropietario
                        execws(10);
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

            progress.setMessage("Cargando stock de producto con License Plate");
            progress.show();

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion_And_LicPlate");

            if (stockResList != null){
                LlenaPresentaciones();
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
                LlenaPresentaciones();
            }else{
                msgbox("El producto en la ubicación origen");
                txtCodigoPrd.requestFocus();
                txtCodigoPrd.selectAll();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processNuevoCorrelativoLP(){

        try {

            progress.setMessage("Obteniendo nuevo correlativo de License Plate");
            progress.show();

            vNuevoPalletId = (String) xobj.getSingle("Get_Nuevo_Correlativo_LicensePlateResult",String.class);

            if (!vNuevoPalletId.isEmpty()){
                //Set_Nuevo_Pallet_Id
                execws(9);
            }else{
                msgbox("Ocurrió un error obteniendo el nuevo correlativo License Plate");
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());

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

                txtCodigoPrd.setText(gl.gCProdAnterior);
                txtUbicOrigen.setText(gl.gCUbicAnterior);

                if (cmbEstadoOrigen.getAdapter()!=null  && cmbEstadoOrigen.getAdapter().getCount()>0){
                    gl.gCEstadoAnterior = Integer.valueOf( cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0]);
                    gl.gCNomEstadoAnterior = cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[1];
                }else{
                    gl.gCEstadoAnterior = -1;
                    gl.gCNomEstadoAnterior = "";
                }

                if (cmbVence.getAdapter()!=null && cmbVence.getAdapter().getCount()>0){
                    gl.gCFechaAnterior = cmbVence.getSelectedItem().toString();
                }else{
                    gl.gCFechaAnterior="01/01/1900";
                }

                if (cmbLote.getAdapter()!=null  && cmbLote.getAdapter().getCount()>0){
                    gl.gCLoteAnterior = cmbLote.getSelectedItem().toString();
                }else{
                    gl.gCLoteAnterior = "";
                }

                if (cmbPresentacion.getAdapter()!=null && cmbPresentacion.getAdapter().getCount()>0){
                    gl.gCPresAnterior = Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());
                    gl.gCNomPresAnterior = cmbPresentacion.getSelectedItem().toString().split(" - ")[1];
                }else{
                    gl.gCPresAnterior = -1;
                    gl.gCNomPresAnterior = "";
                }

                inicializaTarea(true);

                progress.cancel();
                msgAsk(gl.modo_cambio ==1 ? "Cambio de ubicación aplicado": "Cambio de estado aplicado");

            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processUbicacionDestSugerida(){
        try{

            progress.setMessage("Procesando ubidacion destino sugerida");
            progress.show();

            lUbicSug = xobj.getresult(USUbicStrucStage5List.class,"ml_get_ubicacion_sugerida");

            if (lUbicSug != null){
                if(lUbicSug.items.size()>0){

                    txtUbicDestino.setText(String.valueOf(lUbicSug.items.get(0).lUbicacionesVacias.items.get(0).IdUbicacion));
                    validaDestinoSug();

                }

            }else{
                toast("No existen ubicaciones sugeridas");
            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void inicializaTarea(boolean finalizar){
        try{

            progress.setMessage("Inicializando tarea");
            progress.show();

            cvUbicOrigID = 0;
            txtCodigoPrd.setText("");
            lblDescProducto.setText("");
            cmbPresentacion.setAdapter(null);
            cmbLote.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbEstadoOrigen.setAdapter(null);
            cmbEstadoDestino.setAdapter(null);

            cvProdID = 0;
            cvPresID = 0;
            cvLote  = "";
            cvVence = "";
            cvUbicDestID = 0;
            cvUbicOrigID = 0;
            cvEstDestino = 0;
            cvEstOrigen = 0;
            vCantidadAUbicar = 0;
            vCantidadDisponible = 0;

            lblUbicCompleta.setText("");

            lblCant.setText("");
            txtUbicDestino.setText("");
            txtCantidad.setText("");
            txtCodigoPrd.setText("");

            cmbPresentacion.setEnabled(false);
            cmbLote.setEnabled(true);
            cmbVence.setEnabled(true);
            cmbEstadoDestino.setEnabled(true);

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtCodigoPrd.setEnabled(true);

            validarDatos = false;
            vProcesar = false;

            btnGuardarCiega.setVisibility(View.VISIBLE);

            if(gl.modo_cambio==1 && finalizar){
                execws(1);
            }else{
                txtUbicOrigen.requestFocus();
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
            lblDescProducto.setText("");
            cmbPresentacion.setAdapter(null);
            cmbLote.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbEstadoOrigen.setAdapter(null);
            cmbEstadoDestino.setAdapter(null);

            cvProdID = 0;
            cvPresID = 0;
            cvLote  = "";
            cvVence = "";
            cvUbicDestID = 0;
            cvUbicOrigID = 0;
            cvEstDestino = 0;
            cvEstOrigen = 0;
            vCantidadAUbicar = 0;
            vCantidadDisponible = 0;

            lblCant.setText("");
            txtUbicDestino.setText("");
            txtCantidad.setText("");
            txtCodigoPrd.setText("");

            cmbPresentacion.setEnabled(false);
            cmbLote.setEnabled(true);
            cmbVence.setEnabled(true);
            cmbEstadoDestino.setEnabled(true);

            txtUbicDestino.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtCodigoPrd.setEnabled(true);

            validarDatos = false;
            vProcesar = false;

            txtUbicOrigen.requestFocus();

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
                    msgAskImprimirEtiqueta("Imprimir etiqueta");
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    Es_Explosion = false;
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

    private void validaOrigen(){

        try{

            if (txtUbicOrigen.getText().toString() !=""){

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

            if (txtUbicDestino.getText().toString() !=""){

                bodega_ubicacion_destino = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar ubicacion destino
                execws(12);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }

    }

    private void validaDestinoSug(){

        try{

            if (txtUbicDestino.getText().toString() !=""){

                bodega_ubicacion_destino = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar ubicacion destino sugerida
                execws(16);
            }

        }catch (Exception e){
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
                        if( BeStockPallet.CantidadPresentacion != vCantidadAUbicar){
                            msgAskExplosionar("La ubicación parcial de pallet requiere explosionar el material, ¿generar nuevo palletId y continuar?");
                        }
                    }
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskAplicar(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Llamar método para aplicar el cambio de estado
                    aplicarCambio();

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {btnGuardarCiega.setVisibility(View.VISIBLE); }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }

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
            vStockRes.Peso = cvStockItem.Peso;
            vStockRes.IdPresentacion =cvPresID;
            vStockRes.IdProductoEstado = cvEstOrigen;
            vStockRes.Fecha_ingreso = app.strFechaXML(du.getFechaActual());
            vStockRes.ValorFecha = app.strFechaXML(du.getFechaActual());

            if( escaneoPallet && productoList != null){

                vStockRes.Lic_plate = BeStockPallet.Lic_plate;

                if( BeStockPallet.Factor > 0){
                    vStockRes.CantidadUmBas = vCantidadAUbicar * BeStockPallet.Factor;
                }

            }else if ( cvPresID != 0){
                vStockRes.CantidadUmBas = vCantidadAUbicar * vFactorPres;
            }

            //Aplica_Cambio_Estado_Ubic_HH(gMovimientoDet, vStockRes, vIdStockNuevo, vIdMovimientoNuevo);
            execws(14);

        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
            btnGuardarCiega.setVisibility(View.VISIBLE);
        }
    }

    public void AplicarCambio(View view){

        try{

            progress.setMessage("Aplicando cambio de ubicacion");
            progress.show();

            btnGuardarCiega.setVisibility(View.INVISIBLE);

            vProcesar = true;

            validaDestino();

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

            if(cmbPresentacion.getAdapter()!=null  && cmbPresentacion.getAdapter().getCount()>0){
                gMovimientoDet.IdPresentacion = (Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString()) == -1? 0: Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString()));
            }else{
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
                gMovimientoDet.IdTipoTarea = 2;
            }

            gMovimientoDet.IdBodegaDestino = gl.IdBodega;
            gMovimientoDet.IdRecepcion = cvStockItem.IdRecepcionEnc;
            gMovimientoDet.Cantidad = vCantidadAUbicar;
            gMovimientoDet.Serie = cvStockItem.Serial;
            gMovimientoDet.Peso = 0;

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
