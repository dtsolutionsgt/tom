package com.dts.tom.Transacciones.Picking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.ExDialog;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.Producto_imagen.clsBeProducto_imagen;
import com.dts.classes.Mantenimientos.Producto.Producto_imagen.clsBeProducto_imagenList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_det;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_resList;
import com.dts.classes.clsBeImagen;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.ProcesaImagen.frm_imagenes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.TipoLista;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.gBePicking;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.plistPickingUbi;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.selitem;

//import androidx.core.R;

public class frm_picking_datos extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    public static clsBeTrans_picking_ubic gBePickingUbic;
    private clsBeTrans_picking_ubicList tmpListPickingUbi = new clsBeTrans_picking_ubicList();
    public static clsBeProducto tmpgBeProducto = new clsBeProducto();
    public static clsBeProducto gBeProducto = new clsBeProducto();
    private clsBeProducto_estadoList LProductoEstadoIngreso = new clsBeProducto_estadoList();
    private clsBeProductoList ListBeStockPalletEscaneado = new clsBeProductoList();
    private clsBeProducto BeStockPallet = new clsBeProducto();
    private clsBeTrans_picking_det BePickingDet = new clsBeTrans_picking_det();
    private clsBeStock_res BeStockRes = new clsBeStock_res();
    private ArrayList<String> EstadoList = new ArrayList<String>();
    private ArrayList<String> PresList = new ArrayList<String>();
    private clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();
    private clsBeProducto_Presentacion auxPres = new clsBeProducto_Presentacion();

    private Dialog progress;
    private TextView lblTituloForma, lblLicPlate, lblEstiba, lblPresentacion, lblCantidad, lblPresSol, lblPresRec, lblUnidadSol, lblUnidadRec;
    private Button btnDanado, btNE;
    private FloatingActionButton btnConfirmarPk;
    private EditText txtLicencia, txtFechaCad, txtLote, txtUniBas, txtCantidadPick, txtPesoPick,
                     txtCodigoProducto, txtCajas, txtUnidades, txtPreSol, txtUnidadSol, txtPresRec, txtUnidadRec;
    private Spinner cmbPresentacion, cmbEstado;
    private TableRow trCaducidad, trLP, trCodigo, trPeso, trPresentacion, trLote, tblEstiba;
    private RelativeLayout tblCajasUnidades;

    private boolean Escaneo_Pallet = false;
    private String pLP = "";
    private String pCodigo = "", vUnidadMedida="";
    private int gIdUbicacion=0;
    public static double CantReemplazar=0;
    public static boolean ReemplazoLP=false;
    public static int Tipo=0;

    public clsBeProducto_Presentacion gBePresentacion = new clsBeProducto_Presentacion();
    public double vCajasPorCama =0;
    public double vCamasPorTarima = 0;
    public double factor=0;

    private int DifDias = 0;

    private TextToSpeech mTTS;
    private boolean confirmar_codigo_en_picking;
    private boolean btnGuardar = false;
    //Imagen
    private clsBeImagen BeImagen;
    private clsBeProducto_imagen BeProductoImagen = new clsBeProducto_imagen();
    private clsBeProducto_imagenList BeListProductoImagen  =  new clsBeProducto_imagenList();
    private boolean PressEnterLp, PressEnterProducto, escaneo_licencia_diferente = false;
    private boolean btnEnterLp = false, btnEnterCod = false;
    private boolean ReubicarPickingAereo = false;

    public static int IdUbicacionPicking = 0;
    public static String NombreUbicacionPicking = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_picking_datos);

        super.InitBase();

        ws = new WebServiceHandler(frm_picking_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtLicencia = (EditText) findViewById(R.id.txtLP);
        txtFechaCad = (EditText) findViewById(R.id.txtFechaCad);
        txtLote = (EditText) findViewById(R.id.txtLote);
        txtUniBas = (EditText) findViewById(R.id.txtUniBas);
        txtCantidadPick = (EditText) findViewById(R.id.txtCantidadPick);
        txtPesoPick = (EditText) findViewById(R.id.txtPesoPick);
        txtCodigoProducto = (EditText) findViewById(R.id.txtCodigoProducto);
        txtCajas = (EditText) findViewById(R.id.txtCajas);
        txtUnidades = (EditText) findViewById(R.id.txtUnidades);
        txtPreSol = findViewById(R.id.txtPresSol);
        txtUnidadSol = findViewById(R.id.txtUnidadSol);
        txtUnidadRec = findViewById(R.id.txtUnidadRec);
        txtPresRec = findViewById(R.id.txtPresRec);
        lblPresRec = findViewById(R.id.lblPresRec);
        lblPresSol = findViewById(R.id.lblPresSol);
        lblUnidadSol = findViewById(R.id.lblUnidadSol);
        lblUnidadRec = findViewById(R.id.lblUnidadRec);

        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblLicPlate = (TextView) findViewById(R.id.lblLicPlate);
        lblEstiba = (TextView) findViewById(R.id.lblEstiba);
        lblCantidad = (TextView) findViewById(R.id.lblCantidad);

        btnConfirmarPk = (FloatingActionButton) findViewById(R.id.btnConfirmarPk);

        cmbEstado = (Spinner) findViewById(R.id.cmbEstado);

        trCaducidad = (TableRow) findViewById(R.id.trCaducidad);
        trLP = (TableRow) findViewById(R.id.trLP);
        trCodigo = (TableRow) findViewById(R.id.trCodigo);
        trPeso = (TableRow) findViewById(R.id.trPeso);
        trLote = (TableRow) findViewById(R.id.trLote);
        trPresentacion = (TableRow) findViewById(R.id.trPresentacion);
        tblEstiba = (TableRow) findViewById(R.id.tblEstiba);

        tblCajasUnidades = findViewById(R.id.tblCajasUnidades);
        lblPresentacion = findViewById(R.id.lblPresentacion);

        ProgressDialog("Cargando datos de producto picking");

        lblEstiba.setText("");
        tblEstiba.setVisibility(View.GONE);

        confirmar_codigo_en_picking = gl.confirmar_codigo_en_picking;

        if (selitem != null) {
            gBePickingUbic = selitem;

            //#AT 20220124 Se cambia el formato de fecha para mostrar
            if (gBePickingUbic.Fecha_Vence.contains("T")) {
                gBePickingUbic.Fecha_Vence = du.convierteFechaMostrar(selitem.Fecha_Vence);
            }

            gBePickingUbic.IdOperadorBodega_Pickeo = gl.OperadorBodega.IdOperadorBodega;
        }

        PressEnterLp = false;
        PressEnterProducto = false;
        escaneo_licencia_diferente = false;
        btnEnterLp = false;
        btnEnterCod = false;

        setHandlers();

        Load();

        //#EJC20210201: voice picking jajajaja :)
        //AT20220510 Si Notificacion_Voz es verdadero
        if (gl.Notificacion_Voz) {
            mTTS = new TextToSpeech(this, status -> {
                try {
                    if (status == TextToSpeech.SUCCESS) {
                        Locale locSpanish = new Locale("spa", "MEX");
                        int result = mTTS.setLanguage(locSpanish);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("tts", "Lenguaje no soportado :(");
                        } else {

                            double dif = gBePickingUbic.Cantidad_Solicitada - gBePickingUbic.Cantidad_Recibida;

                            String text = "";

                            if (!gBePickingUbic.Lic_plate.isEmpty() && !gBePickingUbic.Lic_plate.equals("0")) {

                                if (gBePickingUbic.Lic_plate.length() > 4) {
                                    String lic_short = gBePickingUbic.Lic_plate.substring(gBePickingUbic.Lic_plate.length() - 4);
                                    text = "Escanée licencia con terminación: " + lic_short + ".";

                                } else {
                                    text = "Escanée licencia: " + gBePickingUbic.Lic_plate + ".";
                                }

                            }

                            String vCantidad = "0";

                            if (dif % 1 == 0) {
                                //es entero
                                int i = (int) dif;
                                vCantidad = String.valueOf(i);
                            } else {
                                //No es entero
                                vCantidad = String.valueOf(dif);
                            }

                            if (!gBePickingUbic.Lote.isEmpty()) {
                                text += " Código: " + gBePickingUbic.CodigoProducto + "."
                                        + " Tome: " + vCantidad + "."
                                        + " Verifíque lote: " + gBePickingUbic.Lote;
                            } else {
                                text += "Código: " + gBePickingUbic.CodigoProducto + "."
                                        + " Tome: " + vCantidad + ", "
                                        + " de cualquier lote. ";
                            }

                            if (!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty()) {
                                text += " Verifique vencimiento: " + gBePickingUbic.Fecha_Vence;
                            }

                            float speed = 1f;
                            float pitch = 1f;
                            mTTS.setPitch(pitch);
                            mTTS.setSpeechRate(speed);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                            } else {
                                mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    } else {
                        Log.e("tts", "No he podido inicializar el TTS :(");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private void setHandlers() {

        try {


            if (txtLicencia !=null){
                txtLicencia.setOnClickListener(view -> {

                });
                txtLicencia.setOnKeyListener((v, keyCode, event) -> {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        Procesa_Barra();
                    }
                    return false;
                });

            }


            //GT06042022: no remover, hacen de pivote para retener el focus
            txtLicencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            //GT06042022: no remover, hacen de pivote para retener el focus
            txtCodigoProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            txtCodigoProducto.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (trLP.getVisibility() == View.GONE) {
                            Procesa_Codigo();
                        } else {
                            if (!txtLicencia.getText().toString().isEmpty()) {
                                confirmarPorCodigo();

                            } else {
                                msgCodigoProducto("Debe ingresar la licencia del producto");
                                txtLicencia.requestFocus();
                                Log.d("focus: ", "20220502_1");
                            }
                        }
                    }

                    return false;
                }
            });

            //GT06042022: no remover, hacen de pivote para retener el focus
            txtCantidadPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtCantidadPick.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        btnGuardar = true;
                        Recalcula_Peso();
                        Procesar_Registro();
                    }

                    return false;
                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandlers:" + e.getMessage());
        }
    }


    private void confirmarPorCodigo() {
        String CodigoProd = txtCodigoProducto.getText().toString();

        try {
            if (CodigoProd.length() > 0) {
                if (gBePickingUbic.CodigoProducto.equalsIgnoreCase(CodigoProd)) {
                    if (TipoLista == 1) {//Resumido
                        Cargar_Datos_Producto_Picking_Consolidado();
                    } else {
                        Cargar_Datos_Producto_Picking();

                    }
                } else {
                    msgCodigoProducto("El código del producto no es válido para esta licencia");
                    txtCodigoProducto.requestFocus();
                    txtCodigoProducto.selectAll();
                    Log.d("focus: ", "20220502_2");
                }
            } else {
                msgCodigoProducto("Debe ingresar el código del producto");
                txtCodigoProducto.requestFocus();
                Log.d("focus: ", "20220502_3");
            }
        } catch (Exception e) {
            mu.msgbox("confirmaPorCodigo "+e.getMessage());
        }
    }

    private void Recalcula_Peso(){

        double vPeso = 0, vCantidad = 0, vPesoUni = 0, vCantidadIngresada = 0;

        try{

            if (gBeProducto.getControl_peso()){

                vPeso = gBePickingUbic.Peso_solicitado;
                vCantidad = gBePickingUbic.Cantidad_Solicitada;

                vCantidadIngresada =Double.valueOf(txtCantidadPick.getText().toString().replace(",",""));

                if (vCantidad>0){
                    vPesoUni = vPeso/vCantidad;
                    txtPesoPick.setText(String.valueOf(vPesoUni*vCantidadIngresada));
                }
            }
        }catch (Exception ex){
            btnGuardar = false;
            mu.msgbox("Recalcula_Peso:" + ex.getMessage());
        }
    }

    private void Load() {

        try {
            txtCantidadPick.setEnabled(false);
            if (!gBePickingUbic.Lic_plate.isEmpty()) {
                lblLicPlate.setText(gBePickingUbic.Lic_plate);
                trLP.setVisibility(View.VISIBLE);
                txtCodigoProducto.setEnabled(false);
                txtLicencia.requestFocus();
                Log.d("focus: ", "20220502_4");
            } else {
                lblLicPlate.setText("");
                trLP.setVisibility(View.GONE);
                txtCodigoProducto.setEnabled(true);
                txtCodigoProducto.requestFocus();
                Log.d("focus: ", "20220502_5");
            }

            if(gBePickingUbic.getIdPresentacion() == 0){
                trPresentacion.setVisibility(View.GONE);
                tblEstiba.setVisibility(View.GONE);
            }

            DifDias = du.DateDiff(gBePickingUbic.Fecha_Vence);

            if(!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty()){

            }

            vUnidadMedida = gBePickingUbic.ProductoUnidadMedida;

            if (gBePickingUbic.IdPresentacion>0){
                if (gBeProducto.Presentaciones!=null){
                    if (gBeProducto.Presentaciones.items!=null){
                        List Aux = stream(gBeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                        int inx= Aux.indexOf(gBePickingUbic.IdPresentacion);
                        vUnidadMedida = gBePresentacion.Nombre;
                    }
                }
            }

            lblTituloForma.setText("Prod: " + gBePickingUbic.CodigoProducto + "-" + gBePickingUbic.NombreProducto + "\r\n"
                    + ((!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty())?
                    " Vence: " + gBePickingUbic.Fecha_Vence :"" ) +((!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty())?
                    " (" + DifDias +"d)" :"" )
                    + (!gBePickingUbic.Lote.isEmpty()?" Lote: " + gBePickingUbic.Lote:""));

            gBeProducto = new clsBeProducto();

            execws(1);

        } catch (Exception e) {
            mu.msgbox("Load:" + e.getMessage());
        }
    }

    private void Load_Cambio_Licencia() {

        try {
            txtCantidadPick.setEnabled(false);
            if (!gBePickingUbic.Lic_plate.isEmpty()) {
                lblLicPlate.setText(gBePickingUbic.Lic_plate);
                trLP.setVisibility(View.VISIBLE);
                txtCodigoProducto.setEnabled(true);
//                txtCodigoProducto.requestFocus();
//                Log.d("focus: ", "20220502_6");
                //#ejc20220502:; se hace en #20220502_47
                PressEnterLp = true;
            } else {
                lblLicPlate.setText("");
                trLP.setVisibility(View.GONE);
                txtCodigoProducto.setEnabled(true);
                txtCodigoProducto.requestFocus();
                Log.d("focus: ", "20220502_7");
            }

            if(gBePickingUbic.getIdPresentacion() == 0){
                trPresentacion.setVisibility(View.GONE);
                tblEstiba.setVisibility(View.GONE);
            }

            DifDias = du.DateDiff(gBePickingUbic.Fecha_Vence);

            if(!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty()){

            }

            vUnidadMedida = gBePickingUbic.ProductoUnidadMedida;

            if (gBePickingUbic.IdPresentacion>0){
                if (gBeProducto.Presentaciones!=null){
                    if (gBeProducto.Presentaciones.items!=null){
                        List Aux = stream(gBeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                        int inx= Aux.indexOf(gBePickingUbic.IdPresentacion);
                        vUnidadMedida = gBePresentacion.Nombre;
                    }
                }
            }

            lblTituloForma.setText("Prod: " + gBePickingUbic.CodigoProducto + "-" + gBePickingUbic.NombreProducto + "\r\n"
                    + ((!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty())?
                    " Vence: " + gBePickingUbic.Fecha_Vence :"" ) +((!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty())?
                    " (" + DifDias +"d)" :"" )
                    + (!gBePickingUbic.Lote.isEmpty()?" Lote: " + gBePickingUbic.Lote:""));

            gBeProducto = new clsBeProducto();

            execws(1);

        } catch (Exception e) {
            mu.msgbox("Load:" + e.getMessage());
        }
    }

    private void Listar_Producto_Estado() {

        try {

            progress_setMessage("Listando estados de producto");

            EstadoList.clear();

            for (int i = 0; i < LProductoEstadoIngreso.items.size(); i++) {
                EstadoList.add(LProductoEstadoIngreso.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstado.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstado.setSelection(0);


        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Listar_Producto_Estado:" + e.getMessage());
        }
    }

    private void Listar_Producto_Presentaciones() {

        try {
            if (gBeProducto.Presentaciones != null) {

                progress_setMessage("Listando presentaciones de producto");

                if (gBeProducto.Presentaciones.items != null) {

                    PresList.clear();

                    for (int i = 0; i < gBeProducto.Presentaciones.items.size(); i++) {
                        PresList.add(gBeProducto.Presentaciones.items.get(i).Nombre);
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresentacion.setAdapter(dataAdapter);

                    if (PresList.size() > 0) cmbPresentacion.setSelection(0);

                }

            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Listar_Producto_Presentaciones:" + e.getMessage());
        }
    }

    private void Bloquea_Controles() {

        try {

            if (!escaneo_licencia_diferente) {
                if (txtLicencia.getText().toString().isEmpty()){
                    txtLicencia.setSelectAllOnFocus(true);
                    txtLicencia.requestFocus();
                    Log.d("txtLicencia_focus: ", "20220502_8");
                }
            }

            txtLote.setFocusable(false);
            txtLote.setFocusableInTouchMode(false);
            txtLote.setClickable(false);

            txtUniBas.setFocusable(false);
            txtUniBas.setFocusableInTouchMode(false);
            txtUniBas.setClickable(false);

            cmbEstado.setFocusable(false);
            cmbEstado.setFocusableInTouchMode(false);
            cmbEstado.setClickable(false);
            cmbEstado.setEnabled(false);

            txtPesoPick.setFocusable(false);
            txtPesoPick.setFocusableInTouchMode(false);
            txtPesoPick.setClickable(false);

            txtFechaCad.setFocusable(false);
            txtFechaCad.setFocusableInTouchMode(false);
            txtFechaCad.setClickable(false);

            if (!escaneo_licencia_diferente) {
                Limpia_controles();
            }

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Bloquea_Controles:" + e.getMessage());
        }
    }

    private void Limpia_controles() {
        txtLicencia.setText("");
        txtLote.setText("");
        //cmbPresentacion.setSelection(-1);
        txtUniBas.setText("");
        txtCantidadPick.setText("0.00");
        cmbEstado.setSelection(-1);
        txtPesoPick.setText("0.00");
    }

    private void Set_dias_Vence() {

        try {

            progress_setMessage("Bloqueando controles");

            if(!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty()){

                trCaducidad.setVisibility(View.VISIBLE);
                txtFechaCad.setText(gBePickingUbic.Fecha_Vence);

            }else{

                trCaducidad.setVisibility(View.GONE);
                txtFechaCad.setText("");

            }

            Bloquea_Controles();

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Llena_datos_faltantes:" + e.getMessage());
        }
    }

    private void Procesa_Barra() {

        try {

            if (!txtLicencia.getText().toString().isEmpty()) {

                    int vLengthBarra = txtLicencia.getText().toString().length();

                    if (vLengthBarra > 0) {

                        Escaneo_Pallet = true;

                        pLP = txtLicencia.getText().toString().replace("$", "");

                        if ( confirmar_codigo_en_picking ){

                            //#EJC20220502 / Copiado de >  AT20220428 Se llama a la funcion ProcesaLicUbic si la licencia ingresada es diferente
                            if (!gBePickingUbic.Lic_plate.equals(pLP)) {
                                ProcesaLicUbic();
                            }else{
                                if (gBePickingUbic.Lic_plate.equals(pLP)){
                                    Continua_procesando_barra();
                                }else{
                                    mu.msgbox("Licencia no válida.");
                                    //txtBarra.setSelectAllOnFocus(true);
                                    //txtLicencia.setText("");
                                    //#GT22042022 se deja la licencia digitada, para ser editada caso cealsa, no se debe limpiar
                                    txtLicencia.requestFocus();
                                    Log.d("focus: ", "20220502_9");

                                }
                            }
                        }else
                        {
                            Continua_procesando_barra();
                        }

                    }
            }


        } catch (Exception e) {
            mu.msgbox("Procesa_Barra:" + e.getMessage());
        }
    }

    //#AT20220428 Valida si la licencia ingresada tiene la misma ubicación que
    // esta en gBePickingUbic.IdUbicacion, si es la misma actualiza los datos del producto y picking
    private void ProcesaLicUbic() {

        List AuxList = stream(plistPickingUbi.items).where(c ->c.Lic_plate.equalsIgnoreCase(pLP) &&
                                                           c.IdUbicacion == selitem.IdUbicacion &&
                                                           (c.Cantidad_Recibida < c.Cantidad_Solicitada)).toList();

        if (AuxList.size() > 0) {
            //#AT20220428 Bandera que indica que si encontró datos de la licencia ingresada
            escaneo_licencia_diferente = true;
            tmpListPickingUbi.items = AuxList;
            gBePickingUbic = tmpListPickingUbi.items.get(0);
            //#AT20220428 Actualiza los datos de gBeProducto
            Load_Cambio_Licencia();
        } else {
            mu.msgbox("El código ingresado no es el válido para la lista de picking");
            txtLicencia.setSelectAllOnFocus(true);
            txtLicencia.requestFocus();
            Log.d("focus: ", "20220502_10");
            return;
        }
    }

    private void Procesa_Codigo() {

        try {

            if (!txtCodigoProducto.getText().toString().isEmpty()) {

                int vLengthCodigo = txtCodigoProducto.getText().toString().length();

                if (vLengthCodigo > 0) {

                    Escaneo_Pallet = false;

                    pCodigo = txtCodigoProducto.getText().toString();

                    Continua_procesando_barra();

                }
            }

        } catch (Exception e) {
            mu.msgbox("Procesa_Codigo:" + e.getMessage());
        }
    }

    private boolean vPalletValido = false;

    private void Continua_procesando_barra(){

        vPalletValido = false;

        boolean vMarcarComoNoEncontrado =false;

        try{

            //#AT20220428 Se llama a la funcion ProcesaLicUbic si la licencia ingresada es diferente
            if (!gBePickingUbic.Lic_plate.equals(pLP)) {
                ProcesaLicUbic();
            }

            if (TipoLista==1){//Resumido

                if (Escaneo_Pallet){

                    if (!gBePickingUbic.Lic_plate.isEmpty()){

                        if (!gBePickingUbic.Lic_plate.equals(pLP)){

                            if (ListBeStockPalletEscaneado!=null){

                                if (ListBeStockPalletEscaneado.items!=null){

                                    BeStockPallet = stream(ListBeStockPalletEscaneado.items).where(c->c.Stock.IdUbicacion == gBePickingUbic.IdUbicacion).first();

                                    if (BeStockPallet!=null){

                                        if (BeStockPallet.Codigo.equals(gBePickingUbic.CodigoProducto)){

                                            if (BeStockPallet.Stock.Lote.equals(gBePickingUbic.Lote)){

                                                double vCantDispLP= BeStockPallet.Stock.CantidadUmBas - BeStockPallet.Stock.CantidadReservadaUMBas;

                                                if (BeStockPallet.Stock.IdPresentacion!=0 && vCantDispLP>0){
                                                    vCantDispLP = mu.round(vCantDispLP/BeStockPallet.Stock.Factor,gl.gCantDecCalculo);
                                                }

                                                if (vCantDispLP>0){

                                                    if (vCantDispLP>=gBePickingUbic.Cantidad_Solicitada){

                                                        msgContinuarDistintoLp("#LP. Solicitado <> #LP. Escaneado."+
                                                                "\n¿Continuar?");

                                                        return;

                                                    }else{
                                                        mu.msgbox("El pallet escaneado no contiene la cantidad solicitada: "+gBePickingUbic.Cantidad_Solicitada
                                                                +"\nCant. Disp:"+vCantDispLP);
                                                        txtLicencia.setSelectAllOnFocus(true);
                                                        txtLicencia.requestFocus();
                                                        Log.d("focus: ", "20220502_11");
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                    txtLicencia.setSelectAllOnFocus(true);
                                                    txtLicencia.requestFocus();
                                                    Log.d("focus: ", "20220502_12");
                                                    return;
                                                }

                                            }else{
                                                mu.msgbox("El lote escaneado de pallet: "+BeStockPallet.Stock.Lote+" no coincide con el lote solicitado: "+gBePickingUbic.Lote);
                                                txtLicencia.setSelectAllOnFocus(true);
                                                txtLicencia.requestFocus();
                                                Log.d("focus: ", "20220502_13");
                                                return;
                                            }

                                        }else{
                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtLicencia.setSelectAllOnFocus(true);
                                            txtLicencia.requestFocus();
                                            Log.d("focus: ", "20220502_14");
                                            return;
                                        }

                                    }else{

                                        BeStockPallet = stream(ListBeStockPalletEscaneado.items).where(c->c.Stock.Lic_plate.equals(pLP)).first();

                                        if (BeStockPallet!=null){

                                            List AuxIndex = stream(plistPickingUbi.items).select(c->c.Lic_plate).toList();
                                            int vTempIndex = AuxIndex.indexOf(pLP);

                                            if (vTempIndex>-1){

                                                if (plistPickingUbi.items.get(vTempIndex).Cantidad_Solicitada!=plistPickingUbi.items.get(vTempIndex).Cantidad_Recibida){

                                                    if (plistPickingUbi.items.get(vTempIndex).IdUbicacion==gIdUbicacion){
                                                        gBeProducto = BeStockPallet;
                                                        //Primer llamado
                                                        //#AT 20220225 Si confirmar_codigo_en_picking es verdadero solicita
                                                        //que se ingrese el código del producto para cargar los datos del picking
                                                        if (confirmar_codigo_en_picking) {
                                                            txtCodigoProducto.requestFocus();
                                                            txtCodigoProducto.setEnabled(true);
                                                            Log.d("focus: ", "20220502_15");
                                                        } else {
                                                            Cargar_Datos_Producto_Picking_Consolidado();
                                                        }
                                                        //Cargar_Datos_Producto_Picking_Consolidado();
                                                        return;
                                                    }else{
                                                        mu.msgbox("El pallet escaneado : "+gBePickingUbic.Lic_plate+" pertenece al picking pero está asociado a la ubicación: "+ gBePickingUbic.IdUbicacion+" y la ubicación actual es: "+gIdUbicacion);
                                                        txtLicencia.setSelectAllOnFocus(true);
                                                        txtLicencia.requestFocus();
                                                        Log.d("focus: ", "20220502_16");
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado : "+pLP+" ya fue procesado para este picking.");
                                                    txtLicencia.setSelectAllOnFocus(true);
                                                    txtLicencia.requestFocus();
                                                    Log.d("focus: ", "20220502_17");
                                                    return;
                                                }

                                            }else{

                                                if (BeStockPallet.Codigo.equals(gBePickingUbic.CodigoProducto)){

                                                    if (BeStockPallet.Lote.equals(gBePickingUbic.Lote)){

                                                        double vCantDispLP = BeStockPallet.Stock.CantidadUmBas - BeStockPallet.Stock.CantidadReservadaUMBas;

                                                        if (BeStockPallet.Stock.IdPresentacion!=0 && vCantDispLP>0){
                                                            vCantDispLP = mu.round(vCantDispLP/BeStockPallet.Stock.Factor,gl.gCantDecCalculo);
                                                        }

                                                        if (vCantDispLP>0){

                                                            if (vCantDispLP>=gBePickingUbic.Cantidad_Solicitada){

                                                                msgContinuarDistintoLp("#LP. Solicitado <> #LP. Escaneado."+
                                                                        "\n¿Continuar?");

                                                                return;

                                                            }else{
                                                                mu.msgbox("El lote escaneado: "+BeStockPallet.Stock.Lote+" del pallet no coincide con el lote solicitado: "+gBePickingUbic.Lote);
                                                                txtLicencia.setSelectAllOnFocus(true);
                                                                txtLicencia.requestFocus();
                                                                Log.d("focus: ", "20220502_18");
                                                                txtLicencia.setText("");
                                                                return;
                                                            }

                                                        }else{
                                                            mu.msgbox("El pallet escaneado no contiene la cantidad solicitada: "+gBePickingUbic.Cantidad_Solicitada+
                                                                    "\nCant. Disp: "+vCantDispLP);
                                                            txtLicencia.setSelectAllOnFocus(true);
                                                            txtLicencia.requestFocus();
                                                            Log.d("focus: ", "20220502_19");
                                                            return;
                                                        }
                                                    }else{
                                                        mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                        txtLicencia.setSelectAllOnFocus(true);
                                                        txtLicencia.requestFocus();
                                                        Log.d("focus: ", "20220502_20");
                                                        return;
                                                    }
                                                }else{
                                                    mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                                    txtLicencia.setSelectAllOnFocus(true);
                                                    txtLicencia.requestFocus();
                                                    Log.d("focus: ", "20220502_21");
                                                    txtLicencia.setText("");
                                                    return;
                                                }

                                            }

                                        }else{

                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtLicencia.setSelectAllOnFocus(true);
                                            txtLicencia.requestFocus();
                                            Log.d("focus: ", "20220502_22");
                                            txtLicencia.setText("");
                                            return;
                                        }

                                    }

                                }
                            }

                        }else{
                            vPalletValido = true;
                        }

                    }else{
                        mu.msgbox("El producto en esta ubicación no tiene barra de pallet asociada");
                        txtLicencia.setSelectAllOnFocus(true);
                        txtLicencia.requestFocus();
                        Log.d("focus: ", "20220502_23");
                        txtLicencia.setText("");
                        return;
                    }

                }

                if (Escaneo_Pallet && vPalletValido){
                    //Segundo llamado
                    //#AT 20220225 Si confirmar_codigo_en_picking es verdadero solicita
                    //que se ingrese el código del producto para cargar los datos del picking
                    if (confirmar_codigo_en_picking) {
                        txtCodigoProducto.requestFocus();
                        Log.d("focus: ", "20220502_24");
                        txtCodigoProducto.setEnabled(true);
                        txtCodigoProducto.setSelectAllOnFocus(true);
                    } else {
                        Cargar_Datos_Producto_Picking_Consolidado();
                    }

                }else if ((!Escaneo_Pallet) && (!gBePickingUbic.Lic_plate.isEmpty()) &&
                        (!gBePickingUbic.Lic_plate.equals("0")) && (gBePickingUbic.CodigoProducto.equals(txtCodigoProducto.getText().toString()))){

                    msgContinuarPorCodigo("Se requiere licencia de pallet para este producto y se ha ingresado el código, ¿está seguro de verificar por código de producto?");
                    return;

                }else{

                    /*gBeProducto = new clsBeProducto();
                    gBeProducto.Codigo = pCodigo;*/

                    tmpgBeProducto = new clsBeProducto();
                    tmpgBeProducto.Codigo = pCodigo;

                    //#EJC20210907: Corrrección para que permita pickear sin código de producto.
                    if (pCodigo.isEmpty()){
                        pCodigo = pLP;
                    }

                    //Get_BeProducto_By_Codigo_For_HH (Primera vez)
                    execws(4);
                }

            }else if(TipoLista==2){//Detallado

                if (Escaneo_Pallet){

                    if (!gBePickingUbic.Lic_plate.isEmpty()){

                        if (!gBePickingUbic.Lic_plate.equals(pLP)){

                            if (ListBeStockPalletEscaneado!=null){

                                if (ListBeStockPalletEscaneado.items!=null){

                                    BeStockPallet = stream(ListBeStockPalletEscaneado.items).where(c->c.Stock.IdUbicacion == gBePickingUbic.IdUbicacion).first();

                                    if (BeStockPallet!=null){

                                        if (BeStockPallet.Codigo.equals(gBePickingUbic.CodigoProducto)){

                                            if (BeStockPallet.Stock.Lote.equals(gBePickingUbic.Lote)){

                                                double vCantDispLP= BeStockPallet.Stock.CantidadUmBas - BeStockPallet.Stock.CantidadReservadaUMBas;

                                                if (BeStockPallet.Stock.IdPresentacion!=0 && vCantDispLP>0){
                                                    vCantDispLP = mu.round(vCantDispLP/BeStockPallet.Stock.Factor,gl.gCantDecCalculo);
                                                }

                                                if (vCantDispLP>0){

                                                    if (vCantDispLP>=gBePickingUbic.Cantidad_Solicitada){
                                                        msgContinuarDistintoLp("#LP. Solicitado <> #LP. Escaneado."+
                                                                "\n¿Continuar?");

                                                        return;
                                                    }else{
                                                        mu.msgbox("El pallet escaneado no contiene la cantidad solicitada: "+gBePickingUbic.Cantidad_Solicitada
                                                                +"\nCant. Disp:"+vCantDispLP);
                                                        txtLicencia.setSelectAllOnFocus(true);
                                                        txtLicencia.requestFocus();
                                                        Log.d("focus: ", "20220502_25");
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                    txtLicencia.setSelectAllOnFocus(true);
                                                    txtLicencia.requestFocus();
                                                    Log.d("focus: ", "20220502_26");
                                                    return;
                                                }

                                            }else{
                                                mu.msgbox("El lote escaneado de pallet: "+BeStockPallet.Stock.Lote+" no coincide con el lote solicitado: "+gBePickingUbic.Lote);
                                                txtLicencia.setSelectAllOnFocus(true);
                                                txtLicencia.requestFocus();
                                                Log.d("focus: ", "20220502_27");
                                                return;
                                            }

                                        }else{
                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtLicencia.setSelectAllOnFocus(true);
                                            txtLicencia.requestFocus();
                                            Log.d("focus: ", "20220502_28");
                                            return;
                                        }

                                    }else{

                                        BeStockPallet = stream(ListBeStockPalletEscaneado.items).where(c->c.Stock.Lic_plate.equals(pLP)).first();

                                        if (BeStockPallet!=null){

                                            List AuxIndex = stream(plistPickingUbi.items).select(c->c.Lic_plate).toList();
                                            int vTempIndex = AuxIndex.indexOf(pLP);

                                            if (vTempIndex>-1){

                                                if (plistPickingUbi.items.get(vTempIndex).Cantidad_Solicitada!=plistPickingUbi.items.get(vTempIndex).Cantidad_Recibida){

                                                    if (plistPickingUbi.items.get(vTempIndex).IdUbicacion==gIdUbicacion){
                                                        gBeProducto = BeStockPallet;
                                                        //Tercer llamado
                                                        //#AT 20220225 Si confirmar_codigo_en_picking es verdadero solicita
                                                        //que se ingrese el código del producto para cargar los datos del picking
                                                        if (confirmar_codigo_en_picking) {
                                                            txtCodigoProducto.requestFocus();
                                                            Log.d("focus: ", "20220502_29");
                                                            txtCodigoProducto.setEnabled(true);
                                                        } else {
                                                            Cargar_Datos_Producto_Picking();
                                                        }
                                                        //Cargar_Datos_Producto_Picking();
                                                        return;
                                                    }else{
                                                        mu.msgbox("El pallet escaneado : "+gBePickingUbic.Lic_plate+" pertenece al picking pero está asociado a la ubicación: "+ gBePickingUbic.IdUbicacion+" y la ubicación actual es: "+gIdUbicacion);
                                                        txtLicencia.setSelectAllOnFocus(true);
                                                        txtLicencia.requestFocus();
                                                        Log.d("focus: ", "20220502_30");
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado : "+pLP+" ya fue procesado para este picking.");
                                                    txtLicencia.setSelectAllOnFocus(true);
                                                    txtLicencia.requestFocus();
                                                    Log.d("focus: ", "20220502_31");
                                                    return;
                                                }

                                            }else{

                                                if (BeStockPallet.Codigo.equals(gBePickingUbic.CodigoProducto)){

                                                    if (BeStockPallet.Lote.equals(gBePickingUbic.Lote)){

                                                        double vCantDispLP = BeStockPallet.Stock.CantidadUmBas - BeStockPallet.Stock.CantidadReservadaUMBas;

                                                        if (BeStockPallet.Stock.IdPresentacion!=0 && vCantDispLP>0){
                                                            vCantDispLP = mu.round(vCantDispLP/BeStockPallet.Stock.Factor,gl.gCantDecCalculo);
                                                        }

                                                        if (vCantDispLP>0){

                                                            if (vCantDispLP>=gBePickingUbic.Cantidad_Solicitada){

                                                                msgContinuarDistintoLp("#LP. Solicitado <> #LP. Escaneado."+
                                                                        "\n¿Continuar?");

                                                                return;

                                                            }else{
                                                                mu.msgbox("El lote escaneado: "+BeStockPallet.Stock.Lote+" del pallet no coincide con el lote solicitado: "+gBePickingUbic.Lote);
                                                                txtLicencia.setSelectAllOnFocus(true);
                                                                txtLicencia.requestFocus();
                                                                Log.d("focus: ", "20220502_32");
                                                                txtLicencia.setText("");
                                                                return;
                                                            }

                                                        }else{
                                                            mu.msgbox("El pallet escaneado no contiene la cantidad solicitada: "+gBePickingUbic.Cantidad_Solicitada+
                                                                    "\nCant. Disp: "+vCantDispLP);
                                                            txtLicencia.setSelectAllOnFocus(true);
                                                            txtLicencia.requestFocus();
                                                            Log.d("focus: ", "20220502_33");
                                                            return;
                                                        }
                                                    }else{
                                                        mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                        txtLicencia.setSelectAllOnFocus(true);
                                                        txtLicencia.requestFocus();
                                                        Log.d("focus: ", "20220502_34");
                                                        return;
                                                    }
                                                }else{
                                                    mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                                    txtLicencia.setSelectAllOnFocus(true);
                                                    txtLicencia.requestFocus();
                                                    Log.d("focus: ", "20220502_35");
                                                    txtLicencia.setText("");
                                                    return;
                                                }

                                            }

                                        }else{

                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtLicencia.setSelectAllOnFocus(true);
                                            txtLicencia.requestFocus();
                                            Log.d("focus: ", "20220502_36");
                                            txtLicencia.setText("");
                                            return;
                                        }

                                    }

                                }
                            }

                        }else{
                            vPalletValido = true;
                        }

                    }else{
                        mu.msgbox("El producto en esta ubicación no tiene barra de pallet asociada");
                        txtLicencia.setSelectAllOnFocus(true);
                        txtLicencia.requestFocus();
                        Log.d("focus: ", "20220502_37");
                        txtLicencia.setText("");
                        return;
                    }

                }

                if (Escaneo_Pallet && vPalletValido){
                    //Cuarto llamado
                    //#AT 20220225 Si confirmar_codigo_en_picking es verdadero solicita
                    //que se ingrese el código del producto para cargar los datos del picking

                    if (confirmar_codigo_en_picking) {
                        txtLicencia.setFocusable(false);
                        txtLicencia.clearFocus();
                        txtCodigoProducto.requestFocus();
                        Log.d("focus: ", "20220502_38");
                        txtCodigoProducto.setEnabled(true);
                        //#GT05042022: aviso para que el operador entienda que debe presionar ENTER  :(
                        PressEnterLp = true;

                    } else {
                        Cargar_Datos_Producto_Picking();
                    }


                }else if ((!Escaneo_Pallet) && (!gBePickingUbic.Lic_plate.isEmpty()) &&
                        (!gBePickingUbic.Lic_plate.equals("0")) && (gBePickingUbic.CodigoProducto.equals(txtLicencia.getText().toString()))){

                    msgContinuarPorCodigo("Se requiere licencia de pallet para este producto y se ha ingresado el código, ¿está seguro de verificar por código de producto?");
                    return;

                }else{

                    //#CKFK 20210210 Puse esto en comentario porque inicializaba el producto, y daba error
                    // porque no se puede buscar en la vista VW_ProductoSI por LicPlate

                    //#AT 20220126 Cambie de gBeProducto a tmpgBeProducto para no perder los datos de gBeProducto
                    tmpgBeProducto = new clsBeProducto();
                    tmpgBeProducto.Codigo = pCodigo;

                   //#EJC20210907: Corrrección para que permita pickear sin código de producto.
                    if (pCodigo.isEmpty()){
                        pCodigo = pLP;
                    }

                    execws(4); //Get_BeProducto_By_Codigo_For_HH (Segunda vez)

//                    msgbox("El código de licencia no es válido, ingréselo nuevamente");
//                    btnConfirmarPk.setEnabled(false);
//                    txtBarra.setSelectAllOnFocus(true);
//                    txtBarra.requestFocus();
                }

            }

        }catch (Exception e){
            mu.msgbox("Continua_procesando_barra:"+e.getMessage());
        }
    }

    private void msgContinuarDistintoLp(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_Con_Lp_Distinto();
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

    private void msgContinuarPorCodigo(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (TipoLista==1){
                        Cargar_Datos_Producto_Picking_Consolidado();
                    }else{
                        //Quinto llamado
                        Cargar_Datos_Producto_Picking();
                    }
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

    private void Continua_Con_Lp_Distinto(){

        try{

            gBeProducto = BeStockPallet;

            clsBeStock_resList lBeStockRes = new clsBeStock_resList();
            clsBeStock_res BeStockRes= new clsBeStock_res();

            CantReemplazar = gBePickingUbic.Cantidad_Solicitada;

            lblLicPlate.setText("NLP:"+BeStockPallet.Stock.Lic_plate);
            ReemplazoLP = true;
            gBeProducto = BeStockPallet;

            if (confirmar_codigo_en_picking) {
                txtCodigoProducto.requestFocus();
                Log.d("focus: ", "20220502_39");
                txtCodigoProducto.setEnabled(true);
            } else {
                if (TipoLista==1){
                    Cargar_Datos_Producto_Picking_Consolidado();
                }else{
                    //Sexto llamado
                    Cargar_Datos_Producto_Picking();
                }
            }

        }catch (Exception e){
            mu.msgbox("Continua_Con_Lp_Distinto"+e.getMessage());
        }
    }

    private void calculaCajaUnidades(double CantPresentacion, double CantidadUMBas) {

        try {

            lblPresSol.setText(auxPres.Nombre+":");
            txtPreSol.setText(String.valueOf(mu.frmdec(CantPresentacion)));
            txtUnidadSol.setText(String.valueOf(mu.frmdec(CantidadUMBas)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private boolean HicimosUnaFumada=false;

    private void calculaUnidades(double CantidadUMBas) {

        try {

            lblPresSol.setText(auxPres.Nombre+":");
            txtPreSol.setText("0");
            txtUnidadSol.setText(String.valueOf(mu.frmdec(CantidadUMBas)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void Cargar_Datos_Producto_Picking(){

        double CantARec = 0;
        double vCantUniXTarima = 0;

        try{
            //#AT20220806 Valida si se confirmo la licencia o codigo
            if (trLP.getVisibility() == View.GONE) {
                btnEnterCod = true;
            } else {
                btnEnterLp = true;
            }

            txtCantidadPick.setEnabled(true);

            txtCodigoProducto.setText(gBePickingUbic.CodigoProducto);

            //#GT 18082021: si la lp digitada coincide con la del producto, se debe setear el producto a pCodigo
            // sino marcara que el producto no existe, porque pCodigo se envia vacio
            if (!txtCodigoProducto.getText().toString().isEmpty()){

                pCodigo = txtCodigoProducto.getText().toString();
            }


            CantARec = gBePickingUbic.Cantidad_Solicitada - gBePickingUbic.Cantidad_Recibida;

            if(!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty()){
                trCaducidad.setVisibility(View.VISIBLE);
                txtFechaCad.setText(gBePickingUbic.Fecha_Vence);
            }else{
                trCaducidad.setVisibility(View.GONE);
            }

            txtLote.setText(gBePickingUbic.Lote);

            if (gBeProducto==null){

                //#EJC20200610: No me gusta como se ve esto, pero tengo demo ma;ana
                gBeProducto = new clsBeProducto();

                //#EJC20210907: Corrrección para que permita pickear sin código de producto.
                if (pCodigo.isEmpty()){
                    pCodigo = pLP;
                }

                //Get_BeProducto_By_Codigo_For_HH (Tercera vez)
                execws(4);

                return;
            }

            //#AT2022048 Cree esta nueva función para poder llenar lblCantidad con la presentación
            LlenaPresentacion();

            txtUniBas.setText(gBePickingUbic.ProductoUnidadMedida);
            if (CantARec<=0){
                txtCantidadPick.setText(""+0);
            }else{

                //#CKFK 20211104 Agregué esta validacion en base a lo conversado con Erik
                if(vCajasPorCama>0 && vCamasPorTarima>0){
                    vCantUniXTarima = vCamasPorTarima*vCajasPorCama;
                }

                if (CantARec >= vCantUniXTarima && vCantUniXTarima>0){
                    txtCantidadPick.setText(""+mu.frmdecimal(vCantUniXTarima,gl.gCantDecDespliegue));
                }else{
                    txtCantidadPick.setText(""+mu.frmdecimal(CantARec,gl.gCantDecDespliegue));
                    //#EJC20220406:No formatear para aquellos casos que tienen muchos decimales.
                    txtCantidadPick.setText(""+CantARec);
                }

            }

            if (gBePickingUbic.IdProductoEstado>0){
                if (LProductoEstadoIngreso!=null){
                    List Aux = stream(LProductoEstadoIngreso.items).select(c->c.IdEstado).toList();
                    int inx= Aux.indexOf(gBePickingUbic.IdProductoEstado);
                    cmbEstado.setSelection(inx);
                }
            }

            if (gBePickingUbic.Peso_solicitado>0){
                txtPesoPick.setText(""+mu.frmdecimal(gBePickingUbic.Peso_solicitado,gl.gCantDecDespliegue));
            }else{
                txtPesoPick.setText("0");
            }

            //#GT07042022: el obj Presentaciones puede venir null
            //if (  gBeProducto.Presentaciones.items!=null) {

            /*if (  gBeProducto.Presentaciones!=null) {

                if (gBeProducto.Presentaciones.items != null){
                    // gBeProducto.Presentaciones.items.get(0).Factor;
                    double tmpCantPick = Double.valueOf(txtCantidadPick.getText().toString());

                    double CantidadDecimal = tmpCantPick % 1;
                    double CantidadPresentacion = 0;
                    CantidadPresentacion = tmpCantPick - CantidadDecimal;
                    double CantidadUMBas = CantidadDecimal * factor;

                    if (CantidadPresentacion > 0) {

                        if ((tmpCantPick % 1) > 0 || (tmpCantPick > factor)) {

                            calculaCajaUnidades(CantidadPresentacion,CantidadUMBas);
                        }

                    }else if ((tmpCantPick % 1) > 0 || (factor > 0)) {
                        calculaUnidades(CantidadUMBas);
                    }

                }
            }*/


            //#GT25042022: le quito el focus al campo código, porque retorna despues del set en cantidad.
            txtCodigoProducto.setFocusable(false);
            txtCodigoProducto.clearFocus();
            btnConfirmarPk.setEnabled(true);
            PressEnterProducto = true;

            //#EJC20220524: Si no se hace esto, el focus vuelve a la licencia
            //porque en orden, fue el primero que  hizo el request del focus.
            if (Escaneo_Pallet && vPalletValido){
                txtLicencia.setFocusable(false);
                txtLicencia.clearFocus();
                txtLicencia.setEnabled(false);
            }

            //#GT25042022: con el campo codigo sin focus, dejo el de cantidad pick
            txtCantidadPick.setEnabled(true);
            txtCantidadPick.setFocusable(true);
            txtCantidadPick.requestFocus();
            Log.d("txtCantidadPick_focus: ", "20220502_40");

        }catch (Exception e){
            mu.msgbox("Cargar_Datos_Producto_Picking:"+e.getMessage());
        }

    }

    private void Cargar_Datos_Producto_Picking_Consolidado(){

        double CantARec=0;
        double CantSol=0;
        double CantRec=0;

        try{
            //#AT20220806 Valida si se confirmo la licencia o codigo
            if (trLP.getVisibility() == View.GONE) {
                btnEnterCod = true;
            } else {
                btnEnterLp = true;
            }

            txtCantidadPick.setEnabled(true);
            txtCodigoProducto.setText(gBePickingUbic.CodigoProducto);
            /*CantSol = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Solicitada);
            CantRec = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Recibida);*/

            CantSol = gBePickingUbic.Cantidad_Solicitada;
            CantRec = gBePickingUbic.Cantidad_Recibida;

            CantARec = CantSol - CantRec;

            if(!gBePickingUbic.Fecha_Vence.equals("01-01-1900") && !gBePickingUbic.Fecha_Vence.isEmpty()){

                trCaducidad.setVisibility(View.VISIBLE);
                txtFechaCad.setText(gBePickingUbic.Fecha_Vence);

            }else{

                trCaducidad.setVisibility(View.GONE);
                txtFechaCad.setText("");

            }

            txtLote.setText(gBePickingUbic.Lote);

            if (gBePickingUbic.IdPresentacion>0){
                //#AT 20220418 Se carga la presentación en el lblCantidad ya no se utiliza cmbPresentación
                /*List Aux = stream(gBeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                int inx= Aux.indexOf(gBePickingUbic.IdPresentacion);
                cmbPresentacion.setSelection(inx);*/
                auxPres = stream(gBeProducto.Presentaciones.items).where(c-> c.IdPresentacion == gBePickingUbic.IdPresentacion).first();

                lblCantidad.setText("Cantidad ("+auxPres.Nombre+"): ");
            } else {
                lblCantidad.setText("Cantidad ("+vUnidadMedida+"): ");
            }

            txtUniBas.setText(gBePickingUbic.ProductoUnidadMedida);
            if (CantARec<=0){
                txtCantidadPick.setText(""+0);
            }else{
                txtCantidadPick.setText(""+mu.frmdecimal(CantARec,gl.gCantDecDespliegue));
                //#CKFK20220510:No formatear para aquellos casos que tienen muchos decimales.
                txtCantidadPick.setText(""+CantARec);
            }

            if (gBePickingUbic.IdProductoEstado>0){
                List Aux = stream(LProductoEstadoIngreso.items).select(c->c.IdEstado).toList();
                int inx= Aux.indexOf(gBePickingUbic.IdProductoEstado);
                cmbEstado.setSelection(inx);
            }

            if (gBePickingUbic.Peso_solicitado>0){
                txtPesoPick.setText(""+mu.frmdecimal(gBePickingUbic.Peso_solicitado,gl.gCantDecDespliegue));
            }else{
                txtPesoPick.setText("0");
            }

            /*if (gBeProducto.Presentaciones!=null) {

                if (gBeProducto.Presentaciones.items != null) {

                    double tmpCantPick = Double.valueOf(txtCantidadPick.getText().toString());
                    double CantidadDecimal = tmpCantPick % 1;
                    double CantidadPresentacion = 0;
                    CantidadPresentacion = tmpCantPick - CantidadDecimal;
                    double CantidadUMBas = CantidadDecimal * factor;

                    if (CantidadPresentacion > 0) {

                        if ((tmpCantPick % 1) > 0 || (tmpCantPick > factor)) {
                            calculaCajaUnidades(CantidadPresentacion,CantidadUMBas);
                        }

                    }else if ((tmpCantPick % 1) > 0 || (factor > 0)) {
                        calculaUnidades(CantidadUMBas);
                    }

                }
            }*/

            txtCantidadPick.selectAll();
            txtCantidadPick.setSelectAllOnFocus(true);
            txtCantidadPick.requestFocus();
            Log.d("focus: ", "20220502_41");
            btnConfirmarPk.setEnabled(true);

        }catch (Exception e){
            mu.msgbox("Cargar_Datos_Producto_Picking_Consolidado:"+e.getMessage());
        }
    }

    public void BotonGuardar(View view){

        btnGuardar = true;
        //#GT si requiere confirmar codigo valida el enter, aplica cealsa, para los demas no importa
        if (confirmar_codigo_en_picking){

            if (PressEnterLp){

                if (PressEnterProducto){

                    if (confirmar_codigo_en_picking) {
                        if (txtLicencia.getText().toString().isEmpty()) {
                            msgCodigoProducto("Ingresar licencia de producto");
                            txtLicencia.requestFocus();
                            Log.d("txtLicencia: ", "20220502_41");
                            return;
                        }

                        if (txtCodigoProducto.getText().toString().isEmpty()) {
                            msgCodigoProducto("Ingrese código de producto");
                            return;
                        }
                    }

                    Procesar_Registro();
                    //gl.termino = "";
                }else{
                    msgCodigoProducto("Confirme código de producto y luego presione Enter.");
                    txtLicencia.requestFocus();
                    Log.d("txtLicencia: ", "20220502_42");
                    return;
                }

            }else{
                msgCodigoProducto("Confirme licencia y luego presione Enter.");
                txtLicencia.requestFocus();
                Log.d("txtLicencia: ", "20220502_43");
                return;
            }

        }else{

            if (trLP.getVisibility() == View.GONE) {
                if (!btnEnterCod) {
                    mu.msgbox("Confirme código de producto.");
                    return;
                }
            } else {
                if (!btnEnterLp) {
                    mu.msgbox("Confirme licencia de producto.");
                    return;
                }
            }

            Procesar_Registro();
            //gl.termino = "";
        }

    }

    private void Procesar_Registro(){

        try{

            if (!txtCantidadPick.getText().toString().isEmpty()||!txtCantidadPick.getText().equals("0")){

                Recalcula_Peso();

                if (TipoLista==2){

                    Double vDif = gBePickingUbic.Cantidad_Solicitada - (Double.parseDouble(txtCantidadPick.getText().toString().replace(",","")) + gBePickingUbic.Cantidad_Recibida);

                    if (vDif<0){
                        mu.msgbox("La cantidad es mayor a la solicitada");
                        txtCantidadPick.selectAll();
                        txtCantidadPick.setSelectAllOnFocus(true);
                        txtCantidadPick.requestFocus();
                        Log.d("focus: ", "20220502_46");
                        return;
                    }else{
                        Guardar_Picking();
                    }

                }else{

                    double vCantidadRec = 0;
                    double vCantidadSol = 0;

                    /*vCantidadRec = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Recibida);
                    vCantidadSol = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Solicitada);*/

                    vCantidadRec = gBePickingUbic.Cantidad_Recibida;
                    vCantidadSol = gBePickingUbic.Cantidad_Solicitada;

                    if (Double.parseDouble(txtCantidadPick.getText().toString().replace(",",""))+vCantidadRec>vCantidadSol){
                        mu.msgbox("La cantidad es mayor a la solicitada");
                        txtCantidadPick.selectAll();
                        txtCantidadPick.setSelectAllOnFocus(true);
                        txtCantidadPick.requestFocus();
                        Log.d("focus: ", "20220502_42");
                        return;
                    }else{
                        Guardar_Picking();
                    }

                }

            }else{
                mu.msgbox("La cantidad no puede ser vacía o 0");
                return;
            }

        }catch (Exception e){
            mu.msgbox("Procesar_Registro:"+e.getMessage());
        }
    }

    private void Guardar_Picking(){

        double CantPendiente = 0;
        double Cantidad = 0;

        progress_setMessage("Procesando registro...");
        progress.show();

        try {
            if (btnGuardar) {
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            if (TipoLista==2){

                double vDif = gBePickingUbic.Cantidad_Solicitada - Double.parseDouble(txtCantidadPick.getText().toString().replace(",","")) + gBePickingUbic.Cantidad_Recibida;

                if (vDif<0){
                    mu.msgbox("La cantidad recibida es mayor a la cantidad solicitada, no se puede ingresar esa cantidad");
                    return;
                }

                //GT14022022: quitamos el simbolo , a la cantidad
                gBePickingUbic.Cantidad_Recibida +=Double.parseDouble(txtCantidadPick.getText().toString().replace(",",""));

                //#EJC20220303: Caputrar el Peso.
                gBePickingUbic.Peso_recibido += Double.parseDouble(txtPesoPick.getText().toString().replace(",",""));

                if (gBePicking.verifica_auto){
                    gBePickingUbic.Cantidad_Verificada = gBePickingUbic.Cantidad_Recibida;
                    gBePickingUbic.Peso_verificado = gBePickingUbic.Peso_recibido;
                }

                gBePickingUbic.Acepto = true;
                gBePickingUbic.Encontrado=true;
                //GT0612021: estaba seteado gl.OperadorBodega.IdOperador pero es IdoperadorBodega
                gBePickingUbic.IdOperadorBodega_Pickeo = gl.OperadorBodega.IdOperadorBodega;

                if (gBePickingUbic.Fecha_Vence.isEmpty()){
                    //mu.msgbox("Guardar_Picking:"+ "fecha vacia");
                    gBePickingUbic.Fecha_Vence = "1900-01-01T00:00:01";
                }


                if (gBeProducto.getControl_vencimiento()){
                    if (! gBePickingUbic.Fecha_Vence.contains("T")){
                        gBePickingUbic.Fecha_Vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
                    }
                }else{
                    if (! gBePickingUbic.Fecha_Vence.contains("T")){
                        gBePickingUbic.Fecha_Vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
                    }
                }
                gBePickingUbic.Fec_mod = du.getFechaActual();

                BePickingDet.IdPickingDet = gBePickingUbic.IdPickingDet;
                execws(5);

            }else{

                Cantidad = Double.parseDouble(txtCantidadPick.getText().toString().replace(",",""));

                pSubListPickingU = new clsBeTrans_picking_ubicList();

                pSubListPickingU.items = stream(plistPickingUbi.items).where(c->c.IdUbicacion==gBePickingUbic.IdUbicacion).toList();

                execws(9);

            }

        }catch (Exception e){
            btnGuardar = false;
            progress.cancel();
            mu.msgbox("Guardar_Picking:"+e.getMessage());
        }
    }

    //#AT 20220126 Cree esta función para validar campos y que permita realizar el reemplazo y PordNoEnc
    public boolean ValidaCampos() {

        try {

            if (!txtLicencia.getText().toString().isEmpty() || !txtCodigoProducto.getText().toString().isEmpty()) {

                if (txtCantidadPick.getText().toString().equals("0") ||
                        txtCantidadPick.getText().toString().equals("0.00") ||
                        txtCantidadPick.getText().toString().isEmpty() ||
                        txtCantidadPick.getText().toString().equals("")) {

                    mu.msgbox("Ingrese la cantidad de producto a reemplazar");
                    txtCantidadPick.setSelectAllOnFocus(true);
                    txtCantidadPick.requestFocus();
                    Log.d("focus: ", "20220502_43");
                    return false;
                } else {
                    Double vDif = gBePickingUbic.Cantidad_Solicitada - (Double.parseDouble(txtCantidadPick.getText().toString().replace(",", "")) + gBePickingUbic.Cantidad_Recibida);

                    if (vDif < 0) {
                        mu.msgbox("La cantidad es mayor a la solicitada");
                        txtCantidadPick.selectAll();
                        txtCantidadPick.setSelectAllOnFocus(true);
                        txtCantidadPick.requestFocus();
                        Log.d("focus: ", "20220502_44");
                        return false;
                    } else {
                        CantReemplazar = Double.parseDouble(txtCantidadPick.getText().toString().replace(",", ""));
                        return true;
                    }
                }
            } else {

                if (trLP.getVisibility() == View.VISIBLE && txtLicencia.getText().toString().isEmpty()) {
                    mu.msgbox("Ingrese LP del producto");
                    txtLicencia.setSelectAllOnFocus(true);
                    txtLicencia.requestFocus();
                    Log.d("txtLicencia: ", "20220502_44");
                    return false;
                }

                if (trLP.getVisibility() == View.GONE && txtCodigoProducto.getText().toString().isEmpty()) {
                    mu.msgbox("Ingrese código del producto");
                    txtCodigoProducto.setSelectAllOnFocus(true);
                    txtCodigoProducto.requestFocus();
                    Log.d("txtCodigoProducto: ", "20220502_45");
                    return false;
                }
            }

        } catch (Exception e) {
            mu.msgbox("ValidaciónCampos Reemplazo y NoEnc: " + e.getMessage());
        }

        return false;
    }

    private void processGetFotosProducto() {

        try {
            progress_setMessage("Cargando imágenes...");
            BeListProductoImagen = xobj.getresult(clsBeProducto_imagenList.class,"Get_All_Producto_Imagen");

            gl.ListImagen.clear();
            if (BeListProductoImagen != null) {
                if (BeListProductoImagen.items != null) {

                    for (int i=0; i < BeListProductoImagen.items.size(); i++) {
                        BeImagen = new clsBeImagen();
                        BeImagen.Descripcion = gBeProducto.Codigo+" - "+gBeProducto.Nombre;
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

    private void processReservaIdStock() {

        try {

            ReubicarPickingAereo = xobj.get(Boolean.class, "Get_All_Reserva_By_IdStock");

            if (ReubicarPickingAereo) {
                msgCambioUbicacion("Desea realizar un cambio de ubicación");
            }

        } catch (Exception e) {
            mu.msgbox("processReservaIdStock: "+e.getMessage());
        }
    }

    public void verImagenes(View view) {
        progress_setMessage("Cargando imágenes...");
        progress.show();
        execws(10);
    }

    public void BotonReemplazo(View view){

        try {

            Tipo=1;
            if (ValidaCampos()) {
                msgReemplazo("¿Marcar producto para reemplazo?");
            } else {
                return;
            }

        } catch (Exception e) {
            mu.msgbox("BotonReemplazo:"+e.getMessage());
        }
    }

    public void BotonNoEn(View view){

        try {

            Tipo=2;
            if (ValidaCampos()) {
                msgReemplazo("¿Marcar producto como No Encontrado?");
            } else {
                return;
            }

        } catch (Exception e) {
            mu.msgbox("BotonReemplazo:"+e.getMessage());
        }
    }

    private void Continua_reemplazo(){

        try {
            if (Tipo == 1) {
                browse = 1;
                IdUbicacionPicking = selitem.IdUbicacion;
                NombreUbicacionPicking = selitem.NombreUbicacion;
                startActivity(new Intent(this, frm_danado_picking.class));
            }else {
                browse = 1;
                //#AT Se valida si existe estado/ubicación para marcar un producto No encontrado, si no debe mostrar un mensaje
                if (gl.gUbicProdNe > 0 && gl.IdProductoEstadoNE > 0) {
                    startActivity(new Intent(this, frm_list_prod_reemplazo_picking.class));
                } else {
                    msgProdNe("No existe un estado/ubicación disponible para marcar como no encontrado al producto: "+
                            "\n \n"+gBePickingUbic.CodigoProducto+" - "+gBePickingUbic.NombreProducto);
                }
            }

        }catch (Exception e){
            mu.msgbox("Continua_reemplazo:"+e.getMessage());
        }
    }

    private void msgReemplazo(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_reemplazo();
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

    private void msgProdNe(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.back);

            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
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
                        callMethod("Get_Producto_By_IdProductoBodega","IdProductoBodega",gBePickingUbic.IdProductoBodega);
                        break;
                    case 2:
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodegaHH",
                                "pIdPropietario",gBeProducto.Propietario.IdPropietario,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",gBeProducto.IdProducto,"pActivo",true);
                        break;
                    case 4:
                        callMethod("Get_BeProducto_By_Codigo_For_HH",
                                         "pCodigo",pCodigo.replace("$", ""),
                                               "IdBodega",gl.IdBodega);
                        break;
                    case 5:
                        callMethod("ObtenerPickingDet","oBeTrans_picking_det",BePickingDet);
                        break;
                    case 6:
                        callMethod("Get_Single_StockRes","pBeStock_res",BeStockRes);
                        break;
                    case 7:
                        callMethod("Actualizar_Picking",
                                         "oBeTrans_picking_ubic",gBePickingUbic,
                                               "BeStockRes",BeStockRes,
                                               "oBeTrans_picking_det",BePickingDet,
                                               "IdBodega",gl.IdBodega);
                        break;
                    case 8:
                        callMethod("Actualizar_Picking_Con_Reemplazo_De_Pallet",
                                               "oBeTrans_picking_ubic",gBePickingUbic,
                                               "BeStockRes",BeStockRes,
                                               "oBeTrans_picking_det",BePickingDet,
                                               "IdBodega",gl.IdBodega,
                                               "pBeStockPalletReemplazo",BeStockPallet.Stock);
                        break;
                    case 9:
                        gBePickingUbic.Fecha_Vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
                        callMethod("Actualiza_Picking_Consolidado","pBePickingUbic",gBePickingUbic,
                                "pIdOperador",gl.OperadorBodega.IdOperador,"ReemplazoLP",ReemplazoLP,"pCantidad",Double.parseDouble(txtCantidadPick.getText().toString().replace(",","")),
                                "pPeso",Double.parseDouble(txtPesoPick.getText().toString()),"BeStockPallet",BeStockPallet);
                        /*callMethod("Actualiza_Picking_Consolidado","pBePickingUbicList",pSubListPickingU.items,
                                "pIdOperador",gl.OperadorBodega.IdOperador,"ReemplazoLP",ReemplazoLP,"pCantidad",Double.parseDouble(txtCantidadPick.getText().toString().replace(",","")),
                                "pPeso",Double.parseDouble(txtPesoPick.getText().toString()),"BeStockPallet",BeStockPallet);*/
                        break;
                    case 10:
                        callMethod("Get_All_Producto_Imagen","pIdProducto",gBeProducto.IdProducto);
                        break;
                    case 11:
                        callMethod("Get_All_Reserva_By_IdStock",
                                        "pIdStock", selitem.IdStock,
                                              "pIdBodega", gl.IdBodega,
                                              "pIdPedido", selitem.IdPedidoEnc);
                        break;
                }

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
                    processProducto();
                    break;
                case 2:
                    processEstadoProducto();
                    break;
                case 3:
                    processPresentacionesProducto();
                    break;
                case 4:
                    processProductoForHH();
                    break;
                case 5:
                    processBePickingDet();
                    break;
                case 6:
                    processBeStockRes();
                    break;
                case 7:
                case 8:
                    if (TipoLista==2){
                        doExit();
                    }
                    break;
                case 9:
                    doExit();
                case 10:
                    processGetFotosProducto();
                    break;
                case 11:
                    processReservaIdStock();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processProducto(){

        try{

            progress_setMessage("Cargando datos de producto");

            gBeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            if (gBeProducto!=null){

                gBeProducto.IdProductoBodega = gBePickingUbic.IdProductoBodega;

                if(gBeProducto.getControl_lote()){
                    trLote.setVisibility(View.VISIBLE);
                }else{
                    trLote.setVisibility(View.GONE);
                }

                if(gBeProducto.getControl_peso()){
                    trPeso.setVisibility(View.VISIBLE);
                }else{
                    trPeso.setVisibility(View.GONE);
                }

                if(gBeProducto.getControl_vencimiento()){
                    trCaducidad.setVisibility(View.VISIBLE);
                }else{
                    trCaducidad.setVisibility(View.GONE);
                }

                //#AT20220419 Se muestra Cajas y Unidades en cantidad solicitada y recibida
                if (gBePickingUbic.IdPresentacion>0){

                    if (gBeProducto.Presentaciones!=null){

                        if (gBeProducto.Presentaciones.items!=null){

                            auxPres = stream(gBeProducto.Presentaciones.items).where(c-> c.IdPresentacion == gBePickingUbic.IdPresentacion).first();
                            factor = auxPres.Factor;

                            double cantidad_solicitada = gBePickingUbic.Cantidad_Solicitada;

                            double CantidadDecimal = cantidad_solicitada % 1;
                            double CantidadPresentacion = 0;
                            CantidadPresentacion = cantidad_solicitada - CantidadDecimal;
                            double CantidadUMBas = CantidadDecimal * factor;

                            if (CantidadPresentacion > 0) {

                                if ((cantidad_solicitada % 1) > 0 || (cantidad_solicitada > factor)) { }

                                if (CantidadUMBas == 0) {
                                    txtUnidadSol.setVisibility(View.GONE);
                                    lblUnidadSol.setVisibility(View.GONE);
                                }

                                calculaCajaUnidades(CantidadPresentacion,CantidadUMBas);

                            } else if ((cantidad_solicitada % 1) > 0 || (factor > 0)) {

                                txtPreSol.setVisibility(View.GONE);
                                lblPresSol.setVisibility(View.GONE);

                                calculaUnidades(CantidadUMBas);
                            }

                            double CantidadRec = gBePickingUbic.Cantidad_Recibida;
                            double CantidadDecimalRec = CantidadRec % 1;
                            double CantidadPresentacionRec = 0;
                            CantidadPresentacionRec = CantidadRec - CantidadDecimalRec;
                            double CantidadUMBasRec = CantidadDecimalRec * factor;

                            if (gBePickingUbic.Cantidad_Recibida > 0) {
                                //double CantidadRec = gBePickingUbic.Cantidad_Recibida;
                                if (CantidadPresentacionRec > 0) {

                                    if ((CantidadRec % 1) > 0 || (CantidadRec >= factor)) { }

                                    if (CantidadUMBasRec == 0) {
                                        txtUnidadRec.setVisibility(View.GONE);
                                        lblUnidadRec.setVisibility(View.GONE);
                                    }

                                    lblPresRec.setText(auxPres.Nombre+":");
                                    txtPresRec.setText(String.valueOf(mu.frmdec(CantidadPresentacionRec)));
                                    txtUnidadRec.setText(String.valueOf(mu.frmdec(CantidadUMBasRec)));

                                }else if ((CantidadRec % 1) > 0 || (factor > 0)) {
                                    lblPresRec.setVisibility(View.GONE);
                                    txtPresRec.setVisibility(View.GONE);

                                    txtUnidadRec.setText(String.valueOf(mu.frmdec(CantidadUMBasRec)));
                                }

                            } else {
                                if (CantidadUMBas == 0) {
                                    lblUnidadRec.setVisibility(View.GONE);
                                    txtUnidadRec.setVisibility(View.GONE);
                                }

                                if (CantidadPresentacion == 0) {
                                    lblPresRec.setVisibility(View.GONE);
                                    txtPresRec.setVisibility(View.GONE);
                                }

                                lblPresRec.setText(auxPres.Nombre+":");
                                txtPresRec.setText("0.00");
                                txtUnidadRec.setText("0.00");
                            }
                        }
                    }
                } else {

                    if (gBeProducto.Presentaciones.items!=null){

                        factor = gBeProducto.Presentaciones.items.get(0).Factor;
                        double CantSol = gBePickingUbic.Cantidad_Solicitada;
                        double CantRec = gBePickingUbic.Cantidad_Recibida;

                        if (CantSol > factor && factor > 0) {
                            //#AT Cantidad Solicitada
                            double cantidadPresentacion = CantSol / factor;
                            double decimal = cantidadPresentacion % 1;
                            double cajas = cantidadPresentacion - decimal;
                            double unidades = decimal * factor;

                            lblPresSol.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");

                            txtUnidadSol.setVisibility(unidades > 0 ? View.VISIBLE: View.GONE);
                            lblUnidadSol.setVisibility(unidades > 0 ? View.VISIBLE: View.GONE);

                            txtPreSol.setText(String.valueOf(mu.frmdec(cajas)));
                            txtUnidadSol.setText(String.valueOf(mu.frmdec(unidades)));

                            //#AT Cantidad Recibida
                            double cantidadPresentacionRec = CantRec / factor;
                            double decimalRec = cantidadPresentacionRec % 1;
                            double cajasRec = cantidadPresentacionRec - decimalRec;
                            double unidadesRec = decimalRec * factor;

                            lblPresRec.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");

                            txtUnidadRec.setVisibility(unidadesRec > 0 ? View.VISIBLE: View.GONE);
                            lblUnidadRec.setVisibility(unidadesRec > 0 ? View.VISIBLE: View.GONE);

                            txtPresRec.setText(String.valueOf(mu.frmdec(cajasRec)));
                            txtUnidadRec.setText(String.valueOf(mu.frmdec(unidadesRec)));


                        } else {

                            lblPresSol.setVisibility(View.GONE);
                            txtPreSol.setVisibility(View.GONE);
                            lblPresRec.setVisibility(View.GONE);
                            txtPresRec.setVisibility(View.GONE);

                            txtUnidadSol.setText(""+mu.frmdec(gBePickingUbic.Cantidad_Solicitada));
                            txtUnidadRec.setText(""+mu.frmdec(gBePickingUbic.Cantidad_Recibida));
                        }

                    }else{
                        lblPresSol.setVisibility(View.GONE);
                        txtPreSol.setVisibility(View.GONE);
                        lblPresRec.setVisibility(View.GONE);
                        txtPresRec.setVisibility(View.GONE);

                        txtUnidadSol.setText(""+mu.frmdec(gBePickingUbic.Cantidad_Solicitada));
                        txtUnidadRec.setText(""+mu.frmdec(gBePickingUbic.Cantidad_Recibida));
                    }

                }

                if (escaneo_licencia_diferente) {
                    LlenaPresentacion();
                }

                execws(2);

            }else{
                progress.cancel();
                mu.msgbox("El producto no está definido");
                super.finish();
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processProducto:"+e.getMessage());
            Log.d("processProducto: ", e.getMessage());
        }
    }

    private void processEstadoProducto(){

        try{

            progress_setMessage("Obteniendo estados de producto");

            LProductoEstadoIngreso = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodegaHH");

            if (gBeProducto.Presentaciones!=null){

                if (gBeProducto.Presentaciones.items!=null){

                    factor = gBeProducto.Presentaciones.items.get(0).Factor;

                    Listar_Producto_Estado();
                    //Listar_Producto_Presentaciones();
                    Set_dias_Vence();

                    if (!gBePickingUbic.Lic_plate.isEmpty()) {
                        if (!escaneo_licencia_diferente) {
                            txtLicencia.requestFocus();
                            Log.d("txtLicencia_focus: ", "20220524_1538");
                        }
                    } else {
                        txtCodigoProducto.requestFocus();
                        Log.d("txtCodigoProducto: ", "20220524_1539");
                    }

                }else{
                    execws(3);
                }
            }else{
                execws(3);
            }

            ReubicarPickingAereo = false;
            //#EJC20220524: Validar si se reubica a posición de piso.
            if (gl.Permitir_Cambio_Ubic_Producto_Picking) {
                if (gBePickingUbic.Ubicacion.Nivel >= 2) {
                    execws(11);
                }
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processEstadoProducto:"+e.getMessage());
        }
    }

    private void processPresentacionesProducto(){

        try {

            progress_setMessage("Obteniendo presentaciones de producto");

            gBeProducto.Presentaciones = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            Listar_Producto_Estado();
            //Listar_Producto_Presentaciones();
            Set_dias_Vence();

            if (!gBePickingUbic.Lic_plate.isEmpty()) {

                if (escaneo_licencia_diferente){
                    if (confirmar_codigo_en_picking){
                        txtCodigoProducto.requestFocus();
                        Log.d("focus: ", "20220502_47");
                        txtCodigoProducto.setSelectAllOnFocus(true);
                    }else{
                        txtCantidadPick.requestFocus();
                        Log.d("focus: ", "20220502_45");
                        txtCantidadPick.setSelectAllOnFocus(true);
                    }
                }else{
                    //#EJC20220502>  Foco de dudosa procedencia según mi análisis, pero lo voy a dejar para que no se esponjen.
                    txtLicencia.requestFocus();
                }
            } else {
                txtCodigoProducto.requestFocus();
            }

        }catch (Exception e){
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private void processProductoForHH(){

        try {

            tmpgBeProducto = xobj.getresult(clsBeProducto.class, "Get_BeProducto_By_Codigo_For_HH");

            if (tmpgBeProducto!=null){
                if (tmpgBeProducto.Codigo.equals(gBePickingUbic.CodigoProducto)){
                    if (TipoLista==1){
                        Cargar_Datos_Producto_Picking_Consolidado();
                    }else{
                        //Séptimo llamado
                        Cargar_Datos_Producto_Picking();
                    }
                }else{
                    mu.msgbox("El código ingresado no es el válido para la línea de picking");
                    btnConfirmarPk.setEnabled(false);
                    txtLicencia.setSelectAllOnFocus(true);
                    txtLicencia.requestFocus();
                }

            }else{

                mu.msgbox("El código ingresado no es el válido para la línea de picking");
                btnConfirmarPk.setEnabled(false);
                txtLicencia.setSelectAllOnFocus(true);
                txtLicencia.requestFocus();
            }

        }catch (Exception e){
            mu.msgbox("processProductoForHH:"+e.getMessage());
        }
    }

    private void processBePickingDet(){

        try{

            BePickingDet = xobj.getresultSingle(clsBeTrans_picking_det.class,"oBeTrans_picking_det");
            BePickingDet.Cantidad_recibida+=Double.parseDouble(txtCantidadPick.getText().toString().replace(",",""));
            BePickingDet.User_mod = gl.OperadorBodega.IdOperador+"";
            BePickingDet.Fec_mod =  du.getFechaActual();
            BeStockRes.IdStockRes = gBePickingUbic.IdStockRes;
            execws(6);

        }catch (Exception e){
            mu.msgbox("processBePickingDet:"+e.getMessage());
        }
    }

    private void processBeStockRes(){

        try{

            BeStockRes = xobj.getresultSingle(clsBeStock_res.class,"pBeStock_res");
            BeStockRes.Estado = "PICKEADO";
            BeStockRes.User_mod = gl.OperadorBodega.IdOperador+"";
            BeStockRes.Fec_mod = du.getFechaActual();

            if (!ReemplazoLP){
                execws(7);
            }else{
                execws(8);
            }

        }catch (Exception e){
            mu.msgbox("processBeStockRes:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    //#AT20220428 Anteriormente este código estaba en Cargar_Datos_Producto_Picking
    //unicamete pase el codigo a esta función
    private void LlenaPresentacion()
    {
        if (gBePickingUbic.IdPresentacion>0){
            if (gBeProducto.Presentaciones!=null){
                if (gBeProducto.Presentaciones.items!=null){
                    //#AT 20220418 Se carga la presentación en el lblCantidad ya no se utiliza cmbPresentación
                       /* List Aux = stream(gBeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                        int inx= Aux.indexOf(gBePickingUbic.IdPresentacion);
                        cmbPresentacion.setSelection(inx);*/
                    auxPres = stream(gBeProducto.Presentaciones.items).where(c-> c.IdPresentacion == gBePickingUbic.IdPresentacion).first();

                    lblCantidad.setText("Cantidad ("+auxPres.Nombre+"): ");

                    //#CKFK 20211104 Agregué esta validacion en base a lo conversado con Erik
                    gBePresentacion= stream(gBeProducto.Presentaciones.items).
                            where(z -> z.IdPresentacion == gBePickingUbic.IdPresentacion).first();
                    vCajasPorCama = gBePresentacion.CajasPorCama;
                    vCamasPorTarima = gBePresentacion.CamasPorTarima;

                    if (vCajasPorCama > 0 && vCamasPorTarima > 0){
                        tblEstiba.setVisibility(View.VISIBLE);
                        lblEstiba.setText("Camas por Tarima: " + vCamasPorTarima + " Cajas por cama: " +  vCajasPorCama);
                    }else{
                        tblEstiba.setVisibility(View.GONE);
                        lblEstiba.setText("");
                    }
                }
            }
        }else{
            lblCantidad.setText("Cantidad ("+vUnidadMedida+"): ");
            tblEstiba.setVisibility(View.GONE);
            lblEstiba.setText("");
        }
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

    private void msgCambioUbicacion(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿"+msg+"?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_picking_datos.super.finish();
                    startActivity(new Intent(frm_picking_datos.this, frm_editar_ubicacion_picking.class));
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

    private void msgCodigoProducto(String msg) {
        ExDialog dialog = new ExDialog(this);
        dialog.setMessage(msg);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void doExit(){
        try{

          Escaneo_Pallet = false;
            super.finish();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void ExitFormPick(View view){
        msgAskExit("Está seguro de salir");
    }

    @Override
    public void onBackPressed() {
        try{
            msgAskExit("Está seguro de salir");
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    @Override
    protected void onResume() {

        try{
            super.onResume();

            if (browse==1){
                browse=0;
                super.finish();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }
    public void progress_setMessage(String mensaje){
        try {
            if(progress!=null){
                runOnUiThread(() -> {
                    txtMensajeDialog = progress.findViewById(R.id.txtMensajeDialog);
                    if(txtMensajeDialog!=null){
                        txtMensajeDialog.setText(mensaje);
                        mensaje_progress = mensaje;
                        txtMensajeDialog.postDelayed(mUpdate,0);
                    }
                });
            }else{
                Log.println(Log.DEBUG,"Progress","Isnull");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextView txtMensajeDialog;
    private String mensaje_progress ="";

    public void ProgressDialog(String mensaje){

        progress= new Dialog(this);
        progress.setContentView(R.layout.dialog_loading);
        progress.setCancelable(false);
        Window window=progress.getWindow();

        runOnUiThread(() -> {
            txtMensajeDialog= progress.findViewById(R.id.txtMensajeDialog);
            if(txtMensajeDialog!=null){
                txtMensajeDialog.setText(mensaje);
            }
        });

        progress.show();

    }

    private Runnable mUpdate = new Runnable() {

        public void run() {

            txtMensajeDialog.setText(mensaje_progress);
            txtMensajeDialog.postDelayed(this, 1000);

        }
    };

    //    public void ProgressDialog(String mensaje) {
//        progress = new ProgressDialog(this);
//        progress_setMessage(mensaje);
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setIndeterminate(true);
//        progress.setProgress(0);
//        progress.show();
//    }


}
