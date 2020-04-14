package com.dts.tom.Transacciones.CambioUbicacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacionList;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import com.dts.base.WebService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_cambio_ubicacion_ciega extends PBase {

    private frm_cambio_ubicacion_ciega.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtUbicOrigen, txtCodigoPrd, txtCantidad, txtUbicDestino;
    private TextView lblUbicCompleta, lblDescProducto, lblLote, lblVence, lblEstadoDestino, lblCant;
    private Spinner cmbPresentacion, cmbLote, cmbVence, cmbEstadoOrigen, cmbEstadoDestino;

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

    private ArrayList<String> cmbPresentacionList = new ArrayList<String>();
    private ArrayList<String> cmbLoteList = new ArrayList<String>();
    private ArrayList<String> cmbVenceList = new ArrayList<String>();
    private ArrayList<String> cmbEstadoOrigenList = new ArrayList<String>();
    private ArrayList<String> cmbEstadoDestinoList = new ArrayList<String>();

    String lote = "", fechaVence = "";

    private int cvUbicOrigID;
    private int cvProdID;
    private int cvPresID;
    private String cvLote;
    private String cvVence;
    private int cvEstOrigen;
    private int cvEstDestino;
    private int cvUbicDestID;
    private double cvCant, cvCantMax;
    private int cvStockID;
    private String cvAtrib;
    private int cvPropID;
    private int cvUMBID;
    private double vFactorPres;
    private boolean validarDatos = false;
    private boolean datosCorrectos = false;

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

            cmbPresentacion = (Spinner) findViewById(R.id.cmbPresentacion);
            cmbLote = (Spinner) findViewById(R.id.cmbLote);
            cmbVence = (Spinner) findViewById(R.id.cmbVence);
            cmbEstadoOrigen = (Spinner) findViewById(R.id.cmbEstadoOrigen);
            cmbEstadoDestino = (Spinner) findViewById(R.id.cmbEstadoDestino);

            cmbEstadoDestino.setVisibility(gl.modo_cambio == 1 ? View.GONE : View.VISIBLE);
            lblEstadoDestino.setVisibility(gl.modo_cambio == 1 ? View.GONE : View.VISIBLE);

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
            execws(1);
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
                    }

                    LlenaLotes();

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
                            validaOrigen();
                    }
                }
                return false;
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

            if (cmbPresentacionList.size() > 0) cmbPresentacion.setSelection(0);

            cvPresID =Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());

            LlenaLotes();

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

                        if (escaneoPallet && productoList != null) {

                            AuxList = stream(stockResList.items)
                                    .where(c -> c.IdProducto == cvProdID)
                                    .where(c -> c.getIdPresentacion() == cvPresID)
                                    .where(c -> c.getLic_plate() == BeStockPallet.Lic_plate)
                                    .toList();

                        } else {

                            AuxList = stream(stockResList.items)
                                    .where(c -> c.IdProducto == cvProdID)
                                    .where(c -> c.getIdPresentacion() == cvPresID)
                                    .toList();
                        }

                        if (AuxList == null) {
                            cvLote = "";
                            LlenaFechaVence();
                            return;
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

                            if (cmbLoteList.size() > 0) cmbLote.setSelection(0);

                            cvLote = cmbLote.getSelectedItem().toString();

                            LlenaFechaVence();
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

                        cvLote = cmbLote.getSelectedItem().toString();
                        cvVence =app.strFecha(currentTime);

                        List AuxList;

                        if (BeProductoUbicacion.Control_lote) {
                            if (escaneoPallet && productoList != null) {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote() == cvLote)
                                        .where(c -> c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .toList();

                            } else {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote() == cvLote)
                                        .toList();
                            }
                        } else {
                            if (escaneoPallet && productoList != null) {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .toList();

                            } else {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .toList();
                            }
                        }

                        if (AuxList == null) {
                            cvVence = "01/01/1900";
                            ;
                            LlenaEstadoOrigen();
                            return;
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

                            if (cmbVenceList.size() > 0) cmbVence.setSelection(0);

                            cvVence = cmbVence.getSelectedItem().toString();

                            LlenaEstadoOrigen();
                        }
                    } catch (Exception ex) {
                        cvVence = "01/01/1900";
                        msgbox("Llena vence : " + ex.getMessage());
                        LlenaEstadoOrigen();
                        return;
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

                if (BeProductoUbicacion.Control_vencimiento) {

                    try {

                        cvLote = cmbLote.getSelectedItem().toString();
                        cvVence =cmbVence.getSelectedItem().toString();

                        List AuxList;

                        if (BeProductoUbicacion.Control_lote) {
                            if (escaneoPallet && productoList != null) {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote() == cvLote)
                                        .where(c -> c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .toList();

                            } else {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote() == cvLote)
                                        .toList();
                            }
                        } else {
                            if (escaneoPallet && productoList != null) {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .toList();

                            } else {

                                AuxList = stream(stockResList.items)
                                        .where(c -> c.IdProducto == cvProdID)
                                        .where(c -> c.getIdPresentacion() == cvPresID)
                                        .toList();
                            }
                        }

                        if (AuxList == null) {
                            cvVence = "01/01/1900";
                            txtCantidad.requestFocus();
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

                            if (cmbEstadoOrigenList.size() > 0) cmbEstadoOrigen.setSelection(0);

                            cvEstOrigen = Integer.valueOf(cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0].toString());

                            muestraCantidad();

                        }
                    } catch (Exception ex) {
                        cvVence = "01/01/1900";
                        msgbox("Llena vence : " + ex.getMessage());
                        muestraCantidad();
                        return;
                    }


                } else {
                    muestraCantidad();
                }

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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

            String pLP = "";
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

                    pLP = txtCodigoPrd.getText().toString().replace("$", "");

                    //Llama al método del WS Get_Stock_By_Lic_Plate
                    execws(5);

                }

            } else {
                cvProdID = Integer.valueOf(txtCodigoPrd.getText().toString());
                escaneoPallet = false;

                //Llama al método del WS Get_BeProducto_By_Codigo_For_HH
                execws(3);
            }

        } catch (Exception ex) {
            msgbox("Error " + ex.getMessage());
        }
    }

    private void muestraCantidad(){

        List AuxList;
        Date currentTime = Calendar.getInstance().getTime();

        try {

            if (escaneoPallet && productoList != null) {
                cvVence = cmbVence.getSelectedItem().toString();
                if (cmbLote.getAdapter().getCount() == 1) {
                    app.enabled(cmbVence,true);
                }
            }else {
                cvVence = cmbVence.getSelectedItem().toString();
                app.enabled(cmbVence,false);
            }

        }catch(Exception ex){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentTime);
            calendar.add(Calendar.YEAR, 1);
            cvVence = app.strFecha(calendar.getTime());
        }

        try {
            if (escaneoPallet & productoList != null) {
                cvEstOrigen = BeStockPallet.IdProductoEstado;
                if (cmbEstadoOrigen.getAdapter().getCount() == 1) {
                    app.enabled(cmbEstadoOrigen,true);
                }
            }else {

                if (!cmbEstadoOrigen.getSelectedItem().toString().isEmpty()){
                    cvEstOrigen = Integer.valueOf(cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0]);
                }else {
                    cvEstOrigen =0;}

                app.enabled(cmbEstadoOrigen,false);
            }
        }catch(Exception ex){
            cvEstOrigen =0;
        }

        cvCant =0;
        cvCantMax =0;
        lblCant.setText("Max : " + mu.frmdecimal(cvCantMax, 6));

        if (gl.gCProdAnterior.equals(txtCodigoPrd.getText())  && gl.gCUbicAnterior.equals(txtUbicOrigen.getText().toString()))
        {
            int IndexAux= cmbEstadoOrigenList.indexOf(gl.gCEstadoAnterior);
            cmbEstadoOrigen.setSelection(IndexAux);

            IndexAux= cmbVenceList.indexOf(gl.gCFechaAnterior);
            cmbVence.setSelection(IndexAux);

            IndexAux= cmbLoteList.indexOf(gl.gCLoteAnterior);
            cmbLote.setSelection(IndexAux);

            IndexAux= cmbPresentacionList.indexOf(gl.gCPresAnterior);
            cmbPresentacion.setSelection(IndexAux);
        }

        try{

            if( escaneoPallet    && productoList != null) {

                AuxList = stream(stockResList.items)
                        .where(c -> c.IdProducto == cvProdID)
                        .where(c -> c.IdPresentacion == cvPresID)
                        .where(c -> c.Lote == cvLote)
                        .where(c -> c.Atributo_variante_1 == (cvAtrib == null ? "" : cvAtrib))
                        .where(c -> c.Lic_plate == BeStockPallet.Lic_plate)
                        .where(c -> (cvEstOrigen > 0 ? c.IdProductoEstado == cvEstOrigen : c.IdProductoEstado >= 0))
                        .where(c -> (BeProductoUbicacion.Control_vencimiento?app.strFecha(c.Fecha_Vence).equals(cvVence):"1".equals("1")))
                        .toList();

                if (AuxList == null) {
                    return;
                }

            }else {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.getIdProducto() == cvProdID)
                            .where(c -> c.IdPresentacion == cvPresID)
                            .where(c -> c.Lote == cvLote)
                            .where(c -> c.Atributo_variante_1 == (cvAtrib == null ? "" : cvAtrib))
                            .where(c -> (cvEstOrigen > 0 ? c.IdProductoEstado == cvEstOrigen : c.IdProductoEstado >= 0))
                            .where(c -> (BeProductoUbicacion.Control_vencimiento?app.strFecha(c.Fecha_Vence).equals(cvVence):"1".equals("1")))
                            .toList();

                    if (AuxList == null) {
                        return;
                    }
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
                cvCant =tmpStockResList.items.get(0).getCantidadUmBas() - tmpStockResList.items.get(0).CantidadReservadaUMBas;
                vFactorPres = (tmpStockResList.items.get(0).getFactor()==0?1:tmpStockResList.items.get(0).getFactor());
            }else{
                cvCant = 0;
                cvStockID =0;
            }

            if( escaneoPallet && productoList != null) {

                if(BeStockPallet.Factor >0)
                {
                    cvCant = (cvCant / BeStockPallet.Factor);
                }
            }else if (cvPresID != 0) {
                if( vFactorPres >0){
                    cvCant = (cvCant / vFactorPres);
                }
            }

            cvCantMax =cvCant;
            lblCant.setText(mu.frmdecimal(cvCantMax, 6));
            txtCantidad.setText(mu.frmdecimal(cvCant, 6));
            txtCantidad.selectAll();

            app.enabled(txtUbicDestino,true);
            app.enabled(txtCantidad, true);

            txtCantidad.requestFocus();

        }catch(Exception ex){
            msgbox("Llena cantidad " + ex.getMessage());
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
                        callMethod("Get_Stock_By_Lic_Plate","pLicensePlate",txtCodigoPrd.getText().toString(),
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
                }

            } catch (Exception e) {
                error=e.getMessage();errorflag =true;msgbox(error);
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

            cvUbicOrigID = (Integer) xobj.getSingle("Get_IdUbic_Ciega_Recepcion_By_IdBodegaResult",int.class);

            if (cvUbicOrigID > 0){
                txtUbicOrigen.setText(String.valueOf(cvUbicOrigID));
                validaOrigen();
            }else{
                txtUbicOrigen.setText("");
            }

            progress.cancel();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProdEstado(){

        try {

            progress.setMessage("Obteniendo el estado del producto");

            gProdEstado = xobj.getresult(clsBeProducto_estado.class,"Get_Single_By_IdEstado");

            if (gProdEstado != null){
                txtUbicDestino.setText(gProdEstado.Nombre);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicOrigen(){

        try {

            progress.setMessage("Validando ubicación");

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

                if (cvCantMax == 0) {
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

                msgAskAplicar((gl.modo_cambio ==1? "Mover producto a ubicación: " + bodega_ubicacion_destino.NombreCompleto: "Aplicar cambio de estado?"));

            }else{
                txtCodigoPrd.requestFocus();
                txtCodigoPrd.selectAll();
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicDestino(){

        try {

            progress.setMessage("Validando ubicación");

            bodega_ubicacion_destino = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion_destino == null){
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("Ubicación destino incorrecta");
            }else{
                cvUbicDestID=bodega_ubicacion_destino.getIdUbicacion();
                if(gl.modo_cambio==2){
                    cmbEstadoDestino.requestFocus();
                }else{
                    datosOk();
                }
            }


        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProducto(){

        try {

            progress.setMessage("Cargando datos del producto");

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
                lblDescProducto.setTextColor(Color.RED);
                cvProdID = 0;
                lblDescProducto.setText ("Código no válido");
                throw new Exception("Producto no existe");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processStockLP(){

        try {

            progress.setMessage("Validando ubicación");

            productoList = xobj.getresult(clsBeProductoList.class,"Get_Stock_By_Lic_Plate");

            if (escaneoPallet && productoList == null) {
                lblDescProducto.setTextColor(Color.RED);
                cvProdID = 0;
                lblDescProducto.setText ("Código de LP no válido");
                return;
            }else{

                if (escaneoPallet && productoList != null){

                    List AuxList = stream(productoList.items).select(c->c.Stock.IdUbicacion==cvUbicOrigID).toList();

                    int Idx = AuxList.indexOf(cvUbicOrigID);

                    if (Idx == -1){
                        msgbox("El pallet no se encuentra en la ubicación: " + cvUbicOrigID);
                        lblDescProducto.setTextColor(Color.RED);
                        cvProdID = 0;
                        lblDescProducto.setText ("LP N.E.E.U");
                        return;
                    }else{
                        BeProductoUbicacion = productoList.items.get(Idx);
                        BeStockPallet = productoList.items.get(Idx).Stock;

                        cvLote = BeStockPallet.Lote;
                        cvPresID = BeStockPallet.IdPresentacion;
                        cvEstOrigen = BeStockPallet.IdProductoEstado;
                        cvVence = app.strFecha(BeStockPallet.Fecha_Vence);
                    }
                }else{
                    //Llama a este método del WS Get_BeProducto_By_Codigo_For_HH
                    execws(3);
                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProductoUbicLP(){

        try {

            progress.setMessage("Cargando stock de producto con License Plate");

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion_And_LicPlate");

            if (stockResList != null){
                LlenaPresentaciones();
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProductoUbic(){

        try {

            progress.setMessage("Cargando producto en esta ubicación");

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion");

            if (stockResList != null){
                LlenaPresentaciones();
            }else{
                msgbox("No hay existencias de ese producto en esta ubicación");
                txtCodigoPrd.requestFocus();
                txtCodigoPrd.selectAll();
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processNuevoCorrelativoLP(){

        try {

            progress.setMessage("Obteniendo nuevo correlativo de License Plate");

            vNuevoPalletId = (String) xobj.getSingle("Get_Nuevo_Correlativo_LicensePlate",String.class);

            if (!vNuevoPalletId.isEmpty()){
                //Set_Nuevo_Pallet_Id
                execws(9);
            }else{
                msgbox("Ocurrió un error obteniendo el nuevo correlativo License Plate");
            }

        } catch (Exception e) {
        }

    }

    private void processIdNuevoPallet(){

        try {

            progress.setMessage("Colocando nuevo pallet");

            boolean result = (Boolean) xobj.getSingle("Set_Nuevo_Pallet_Id",boolean.class);

            if (result){
                msgAskImpresoraLista("¿La impresora está lista y conectada?");
            }else{
                msgbox("Ocurrió un error creando el Id del nuevo pallet");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processEstadosProp(){

        try {

            progress.setMessage("Validando ubicación");

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
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processCambio(){

        try {

            progress.setMessage("Procesando cambio de ubicación");

            boolean actualizar = (Boolean) xobj.getSingle("Actualizar_Trans_Ubic_HH_DetResult",boolean.class);

            if (actualizar){
                msgAskExit(String.format("Cambio de %s aplicado",(gl.modo_cambio==1?"ubicación":"estado")));
            }else{
                msgbox("Ocurrió un error al procesar el cambio de ubicación");
            }

            progress.cancel();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processCambioUbicEstHH(){
        try{

            boolean resultado = false;

            progress.setMessage("Procesando cambio");

            resultado = (Boolean) xobj.getSingle("Aplica_Cambio_Estado_Ubic_HHResult",boolean.class);

            if( resultado){

                txtCodigoPrd.setText(gl.gCProdAnterior);
                txtUbicOrigen.setText(gl.gCUbicAnterior);
                gl.gCEstadoAnterior = Integer.valueOf( cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0].toString());
                gl.gCFechaAnterior = cmbVence.getSelectedItem().toString();
                gl.gCLoteAnterior = cmbLote.getSelectedItem().toString();
                gl.gCPresAnterior = Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString());

                inicializaTarea();

                msgAsk(gl.modo_cambio ==1 ? "Cambio de ubicación aplicado": "Cambio de estado aplicado");

            }

        }catch (Exception ex){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void inicializaTarea(){
        try{

            int cantPorUbica =0;

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
            cvCant = 0;
            cvCantMax = 0;

            lblUbicCompleta.setText("");

            lblCant.setText("");
            txtUbicDestino.setText("");
            txtCantidad.setText("");
            txtCodigoPrd.setText("");

            app.enabled(cmbPresentacion,false);
            app.enabled(cmbLote,false);
            app.enabled(cmbVence,false);
            app.enabled(cmbEstadoDestino,false);

            app.enabled(txtUbicDestino,false);
            app.enabled(txtCantidad,false);
            app.enabled(txtCodigoPrd,false);

            validarDatos = false;

            txtCodigoPrd.requestFocus();

        }catch (Exception ex){
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
        }

    }

    private void datosOk(){

        boolean continua=true;
        validarDatos = true;

        execws(11);

    }

    private void msgAsk(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if( escaneoPallet && productoList != null){
                        if( BeStockPallet.CantidadPresentacion != cvCant){
                            msgAskExplosionar("La ubicación parcial de pallet requiere explosionar el material, ¿generar nuevo palletId y continuar?");
                        }
                    }else{

                        //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar ubicacion origen
                       // execws(11);

                    }
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) { }
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
                public void onClick(DialogInterface dialog, int which) { }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void aplicarCambio() {

        try{

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

            vStockRes.CantidadUmBas = cvCant;
            vStockRes.Peso = cvStockItem.Peso;
            vStockRes.IdPresentacion =cvPresID;
            vStockRes.IdProductoEstado = cvEstOrigen;
            vStockRes.Fecha_ingreso = app.strFechaXML(du.getFechaActual());
            vStockRes.ValorFecha = app.strFechaXML(du.getFechaActual());

            if( escaneoPallet && productoList != null){

                vStockRes.Lic_plate = BeStockPallet.Lic_plate;

                if( BeStockPallet.Factor > 0){
                    vStockRes.CantidadUmBas = cvCant * BeStockPallet.Factor;
                }

            }else if ( cvPresID != 0){
                vStockRes.CantidadUmBas = cvCant * vFactorPres;
            }

            //Aplica_Cambio_Estado_Ubic_HH(gMovimientoDet, vStockRes, vIdStockNuevo, vIdMovimientoNuevo);
            execws(14);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }finally{
            txtUbicOrigen.requestFocus();
        }
    }

    public void AplicarCambio(View view){
        validaDestino();
    }

    private boolean Crear_Movimiento_Ubicacion_ND(boolean EsCambioEstado) {

        try{

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
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox( ex.getMessage());
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
            gMovimientoDet.IdPresentacion = (Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString()) == -1? 0: Integer.valueOf( cmbPresentacion.getSelectedItem().toString().split(" - ")[0].toString()));
            gMovimientoDet.IdEstadoOrigen = Integer.valueOf( cmbEstadoOrigen.getSelectedItem().toString().split(" - ")[0].toString());
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
            gMovimientoDet.Cantidad = cvCant;
            gMovimientoDet.Serie = cvStockItem.Serial;
            gMovimientoDet.Peso = 0;
            gMovimientoDet.Lote = cmbLote.getSelectedItem().toString();

            clsBeProducto bprod = new clsBeProducto();

            bprod = BeProductoUbicacion;

            if(bprod.Control_vencimiento ){
                gMovimientoDet.Fecha_vence = app.strFechaXMLCombo(cmbVence.getSelectedItem().toString());
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
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox( ex.getMessage());
            return false;
        }

    }

    public void Regresar(View view){
        finish();
    }

    @Override
    public void onBackPressed()
    {
    }

}
