package com.dts.tom.Transacciones.Packing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_Packing extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtUbicOr,txtPrd,txtNuevoLp,txtCantidad,txtLpAnt,txtLic_Plate;
    private TextView lblUbicOrigen,lblDesProducto,lblLpAnt,lblIdStock,lblCCant,lblVence,lblLote,
            lblNomDestino, txtUbicDestino;
    private Spinner cmbPresentacion,cmbLote,cmbVence,cmbEstado;
    private Button btnGuardarDirigida,btnBack;
    private ImageView cmdImprimir;

    private ProgressDialog progress;
    private Dialog dialog;

    private int idUbicRecep = 0;
    private int cvUbicOrigID= 0;
    private int gIdProductoOrigen=0,cvPresID=0,gIdEstadoProductoOrigen=0,cvUbicDestID=0,cvEstEst=0;
    private int cvStockID;
    private int cvPropID=0,cvUMBID=0;
    private boolean idle=false,BorrarUbicOrigen=false;
    private String gLoteOrigen="",cvVence="";
    private double cvCant=0,cvCantMax=0;
    private boolean Escaneo_Pallet=false;
    private double vCantidadAUbicar, vCantidadDisponible;
    private String cvAtrib;
    private double vFactorPres;
    private String  fechaVenceU;
    private String Lic_Plate_Ant="";
    private String NuevoLp="";

    //GT 29012021 variables para Lic Plate
    private boolean escaneoPallet;
    private String pLicensePlate;
    private int cvEstOrigen = 0;
    private int cvProdID = 0;
    private int pUbicacionLP=0;
    private String pNombreUbicacionLP="";
    private boolean Existe_Lp=false, imprimirDesdeBoton = false;

    public static clsBeBodega_ubicacion cUbicOrig = new clsBeBodega_ubicacion();
    public static clsBeBodega_ubicacion cUbicDest = new clsBeBodega_ubicacion();
    private clsBeProductoList ListBeStockPallet = new clsBeProductoList();
    private clsBeProducto BeProductoUbicacionOrigen;
    private clsBeVW_stock_res BeStockPallet;
    private clsBeVW_stock_res cvStockItem;
    private clsBeProducto cvProd = new clsBeProducto();
    private clsBeVW_stock_resList stockResList = new clsBeVW_stock_resList();

    private clsBeProducto_estadoList productoEstadoDestinoList = new clsBeProducto_estadoList();
    private clsBeVW_stock_resList presentacionList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList lotesList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList venceList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList productoEstadoOrigenList = new clsBeVW_stock_resList();
    private clsBeVW_stock_res vStockRes = new clsBeVW_stock_res();

    private clsBeTrans_movimientos gMovimientoDet;

    private ArrayList<String> cmbEstadoDestinoList = new ArrayList<String>();
    private ArrayList<String> cmbPresentacionList = new ArrayList<String>();
    private ArrayList<String> cmbLoteList = new ArrayList<String>();
    private ArrayList<String> cmbVenceList = new ArrayList<String>();
    private ArrayList<String> cmbEstadoOrigenList = new ArrayList<String>();
    private String cvLote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm__packing);
        super.InitBase();

        txtUbicOr = (EditText)findViewById(R.id.txtUbicOr);
        txtPrd = (EditText)findViewById(R.id.txtPrd);
        txtNuevoLp = (EditText)findViewById(R.id.txtNuevoLp);
        txtCantidad = (EditText)findViewById(R.id.txtCantidad);
        txtLpAnt = (EditText)findViewById(R.id.txtLpAnt);
        txtLic_Plate = findViewById(R.id.txtLic_Plate);

        lblUbicOrigen = (TextView) findViewById(R.id.lblUbicOrigen);
        lblDesProducto = (TextView) findViewById(R.id.lblDesProducto);
        lblLpAnt = (TextView)findViewById(R.id.textView64);
        lblIdStock = (TextView)findViewById(R.id.textView65);
        lblCCant = (TextView)findViewById(R.id.textView66);
        lblLote= (TextView)findViewById(R.id.textView59);
        lblVence= (TextView)findViewById(R.id.textView60);
        lblNomDestino = (TextView)findViewById(R.id.lblNomDestino);
        txtUbicDestino = findViewById(R.id.txtUbicDestino);

        cmbPresentacion = (Spinner) findViewById(R.id.cmbPresentacion);
        cmbLote = (Spinner) findViewById(R.id.cmbLote);
        cmbVence = (Spinner) findViewById(R.id.cmbVence);
        cmbEstado = (Spinner) findViewById(R.id.cmbEstado);

        btnGuardarDirigida = (Button)findViewById(R.id.btnGuardarDirigida);
        cmdImprimir =(ImageView) findViewById(R.id.cmdImprimir2);

        ws = new frm_Packing.WebServiceHandler(frm_Packing.this, gl.wsurl);
        xobj = new XMLObject(ws);

        ProgressDialog("Cargando forma");

        setHandles();

        execws(1);

    }

    private void setHandles(){

        try{

            txtLic_Plate.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        Procesa_Lp();
                    }

                    return false;
                }
            });

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

                            if (stockResList.items!=null){
                                gLoteOrigen = stockResList.items.get(position).Lote;
                            }

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

                            fechaVenceU = stockResList.items.get(position).getFecha_Vence();

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

            cmbEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        TextView spinlabel = (TextView) parentView.getChildAt(0);

                        if (spinlabel!=null){
                            spinlabel.setTextColor(Color.BLACK);
                            spinlabel.setPadding(5, 0, 0, 0);
                            spinlabel.setTextSize(18);
                            spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                            if (productoEstadoOrigenList.items!=null){
                                cvEstEst = productoEstadoOrigenList.items.get(position).getIdProductoEstado();
                            }
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

            txtUbicOr.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        Scan_Ubic_Origen();
                    }

                    return false;
                }
            });

            txtPrd.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (!txtPrd.getText().equals("")){
                            Busca_Producto();
                        }
                    }

                    return false;
                }
            });

            txtNuevoLp.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (!txtPrd.getText().equals("")){
                            AplicaPacking();
                        }
                    }

                    return false;
                }
            });

            cmdImprimir.setOnClickListener(v -> {
                imprimirDesdeBoton = true;
                Imprimir(v);
            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Procesa_Lp() {

        try {

            progress.setMessage("Cargando datos del producto");
            progress.show();

            String pbarra;

            pbarra = txtPrd.getText().toString();

            cvLote = "";
            cvPresID = 0;
            cvEstOrigen = 0;
            cvProdID = 0;
            cvVence = "01/01/1900";

            //Es una barra de pallet válida por tamaño
            int vLengthBarra = txtLic_Plate.getText().toString().length();

            Escaneo_Pallet = true;

            pLicensePlate = txtLic_Plate.getText().toString().replace("$", "");

            //Llama al método del WS Existe_LP
            execws(9);

            progress.cancel();

        } catch (Exception ex) {
            progress.cancel();
            msgbox("Error " + ex.getMessage());
        }
        progress.cancel();
    }

    private void Busca_Producto(){

        try{

            gIdProductoOrigen= 0;

            String vStarWithParameter = "$";

            if (txtPrd.getText().toString().startsWith("$") ||
                    txtPrd.getText().toString().startsWith("(01)") ||
                    txtPrd.getText().toString().startsWith(vStarWithParameter)) {

                int vLengthBarra = txtPrd.getText().toString().length();

                if (vLengthBarra > 0) {

                    Escaneo_Pallet = true;

                    pLicensePlate = txtPrd.getText().toString().replace("$", "");

                    //Llama al método del WS Get_Stock_By_Lic_Plate
                    execws(3);
                }

            }else{

                Escaneo_Pallet=false;

                if (txtNuevoLp.getText().toString().isEmpty()){
                    int cyear,cmonth,cday,ch,cm;
                    final Calendar c = Calendar.getInstance();
                    cyear = c.get(Calendar.YEAR);
                    cmonth = c.get(Calendar.MONTH)+1;
                    cday = c.get(Calendar.DAY_OF_MONTH);
                    ch= c.get(Calendar.HOUR_OF_DAY);
                    cm= c.get(Calendar.MINUTE);

                    String vCodigoBodega =  FormatBodega(gl.CodigoBodega);
                    String vCodigoProducto = FormatProducto(txtPrd.getText().toString());
                    String vCorrelativo = (cyear-2000) +
                            ("00"+cmonth).substring(("00"+cmonth).length()-2) +
                            ("00"+cday).substring(("00"+cday).length()-2)  +
                            ("00"+ch).substring(("00"+ch).length()-2)  +
                            ("00"+cm).substring(("00"+cm).length()-2) ;

                    txtNuevoLp.setText(vCodigoBodega + "" + vCodigoProducto + "W"+vCorrelativo); // & vCorrelativo
                }

                //Get_BeProducto_By_Codigo_For_HH
                if (!txtLic_Plate.getText().toString().isEmpty() && !txtLic_Plate.getText().toString().equals("")) {
                    Escaneo_Pallet = true;
                    //Llama al método del WS Get_Stock_By_Lic_Plate_And_Codigo
                    execws(12);
                }else{
                    //Llama al método Get_BeProducto_By_Codigo_For_HH
                    execws(6);
                }

            }

        }catch (Exception e){
            mu.msgbox("Busca_Producto:"+e.getMessage());
        }
    }

    private String FormatBodega(String pCodigo){

        String cdBodega="";

        try{

            int Lengthindex = pCodigo.length();

            if (Lengthindex>=2){
                cdBodega = pCodigo;
            }else{
                cdBodega = ("00"+pCodigo);
                cdBodega = cdBodega.substring(cdBodega.length()-2);
            }

        }catch (Exception e){
            mu.msgbox("FormatBodega:"+e.getMessage());
        }

        return  cdBodega;
    }

    private String FormatProducto(String pCodigo){

        String cdBodega="";

        try{

            int Lengthindex = pCodigo.length();

            if (Lengthindex>=4){
                cdBodega = pCodigo;
            }else{
                cdBodega = ("0000"+pCodigo);
                cdBodega = cdBodega.substring(cdBodega.length()-4);
            }

        }catch (Exception e){
            mu.msgbox("FormatProducto:"+e.getMessage());
        }

        return  cdBodega;
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void Inicializa_Valores(){

        try{

            idle=false;
            cmbPresentacion.setAdapter(null);
            cmbEstado.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbLote.setAdapter(null);
            gIdProductoOrigen = 0;
            cvPresID = 0;
            gLoteOrigen = "";
            gIdEstadoProductoOrigen = 0;
            cvUbicDestID = 0;
            cvCant = 0;
            cvCantMax = 0;
            cvEstEst = 0;

            if (BorrarUbicOrigen){
                lblUbicOrigen.setText("");
            }

            lblCCant.setText("");

            txtNuevoLp.setText("");
            txtNuevoLp.setEnabled(false);

            txtLpAnt.setText("");
            txtLpAnt.setVisibility(View.INVISIBLE);

            lblLpAnt.setVisibility(View.INVISIBLE);

            lblIdStock.setText("");

            txtCantidad.setText("");
            txtCantidad.setEnabled(false);
            txtPrd.setText("");
            txtUbicDestino.setText("");
            lblNomDestino.setText("");

        }catch (Exception e){

        }
    }

    private void Limpiar_Valores(){

        try{

            idle=false;
            cmbPresentacion.setAdapter(null);
            cmbEstado.setAdapter(null);
            cmbVence.setAdapter(null);
            cmbLote.setAdapter(null);
            gIdProductoOrigen = 0;
            cvPresID = 0;
            gLoteOrigen = "";
            gIdEstadoProductoOrigen = 0;
            cvUbicDestID = 0;
            cvCant = 0;
            cvCantMax = 0;
            cvEstEst = 0;

            if (BorrarUbicOrigen){
                lblUbicOrigen.setText("");
                txtUbicOr.setText("");
            }

            txtLic_Plate.setText("");

            lblCCant.setText("");

            txtNuevoLp.setText("");
            txtNuevoLp.setEnabled(false);

            txtLpAnt.setText("");
            txtLpAnt.setVisibility(View.INVISIBLE);

            lblLpAnt.setVisibility(View.INVISIBLE);

            lblIdStock.setText("");

            txtCantidad.setText("");
            txtCantidad.setEnabled(false);
            txtPrd.setText("");

            txtLic_Plate.setText("");
            txtUbicOr.setText("2570");
            txtLic_Plate.requestFocus();

        }catch (Exception e){

        }
    }

    private void Scan_Ubic_Origen(){

        try{

            int CantPorUbic= 0;
            cvUbicOrigID = 0;

            txtPrd.setText("");
            lblDesProducto.setText("");

            if (txtUbicOr.getText().toString().equals("")){
                txtUbicOr.requestFocus();
                return;
            }

            Inicializa_Valores();

            execws(2);

        }catch (Exception e){
            mu.msgbox("Scan_Ubic_Origen:"+e.getMessage());
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
            cmbEstado.setAdapter(dataAdapter);

            if (cmbEstadoDestinoList.size() > 0) cmbEstado.setSelection(0);

            if (Escaneo_Pallet && ListBeStockPallet != null) {
                //LLama este procedimiento del WS Get_Productos_By_IdUbicacion_And_LicPlate
                execws(5);
            } else {
                //LLama este procedimiento del WS Get_Productos_By_IdUbicacion
                execws(7);
            }

        } catch (Exception e) {
            mu.msgbox(e.getMessage());
        }
    }

    private void LlenaPresentaciones() {

        String valor;

        try {

            cmbPresentacionList.clear();

            //#EJC20210729: agregué and IdPresentación <> 0
            List AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == gIdProductoOrigen && c.IdPresentacion != 0)
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

                if (Escaneo_Pallet && ListBeStockPallet !=null ){
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

            gLoteOrigen = "";

            cmbLote.setVisibility(BeProductoUbicacionOrigen.Control_lote ? View.VISIBLE : View.GONE);
            lblLote.setVisibility(BeProductoUbicacionOrigen.Control_lote ? View.VISIBLE : View.GONE);

            if (BeProductoUbicacionOrigen.Control_lote) {

                try {

                    List AuxList;

                    //if (escaneoPallet && productoList != null) {
                    //Quité esta validación porque en stockResList ya está filtrado por LicensePlate

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == gIdProductoOrigen)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .toList();

                    if (AuxList == null) {
                        gLoteOrigen = "";
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
                            gLoteOrigen = cmbLote.getSelectedItem().toString();
                        }

                    }
                } catch (Exception ex) {
                    gLoteOrigen = "";
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

            cmbVence.setVisibility(BeProductoUbicacionOrigen.Control_vencimiento ? View.VISIBLE : View.GONE);
            lblVence.setVisibility(BeProductoUbicacionOrigen.Control_vencimiento ? View.VISIBLE : View.GONE);

            if (BeProductoUbicacionOrigen.Control_vencimiento) {

                try {

                    Date currentTime = Calendar.getInstance().getTime();

                    if (cmbLote.getAdapter()!=null && cmbLote.getAdapter().getCount()>0){
                        gLoteOrigen = cmbLote.getSelectedItem().toString();
                    }

                    cvVence =app.strFecha(currentTime);

                    List AuxList;

                    if (BeProductoUbicacionOrigen.Control_lote) {
                        //if (escaneoPallet && productoList != null) {
                        //Quité esta validación porque en stockResList ya está filtrado por LicensePlate

                        AuxList = stream(stockResList.items)
                                .where(c -> c.IdProducto == gIdProductoOrigen)
                                .where(c -> c.getIdPresentacion() == cvPresID)
                                .where(c -> c.getLote() == gLoteOrigen)
                                .toList();

                    } else {
                        //if (escaneoPallet && productoList != null) {
                        //Quité esta validación porque en stockResList ya está filtrado por LicensePlate

                        AuxList = stream(stockResList.items)
                                .where(c -> c.IdProducto == gIdProductoOrigen)
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

            if (BeProductoUbicacionOrigen.Control_vencimiento && !cvVence.equals("01/01/1900")) {

                if (BeProductoUbicacionOrigen.Control_lote) {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == gIdProductoOrigen)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .where(c -> c.getLote() == gLoteOrigen)
                            .where(c -> (app.strFecha(c.Fecha_Vence).equals(cvVence)))
                            .toList();

                } else {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == gIdProductoOrigen)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .where(c -> (app.strFecha(c.Fecha_Vence).equals(cvVence)))
                            .toList();
                }

            }else{

                if (BeProductoUbicacionOrigen.Control_lote) {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == gIdProductoOrigen)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .where(c -> c.getLote() == gLoteOrigen)
                            .toList();

                } else {

                    AuxList = stream(stockResList.items)
                            .where(c -> c.IdProducto == gIdProductoOrigen)
                            .where(c -> c.getIdPresentacion() == cvPresID)
                            .toList();
                }

            }

            if (AuxList == null) {
                cvEstEst = 0;
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
                cmbEstado.setAdapter(dataAdapter);

                if (cmbEstadoOrigenList.size() > 0) {

                    cmbEstado.setSelection(0);
                    cvEstEst = Integer.valueOf(cmbEstado.getSelectedItem().toString().split(" - ")[0].toString());
                    muestraCantidad();

                }

            }

        } catch (Exception ex) {
            cvEstEst = 0;
            muestraCantidad();

            addlog(new Object() {}.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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

            if (Escaneo_Pallet && ListBeStockPallet != null) {
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

        vCantidadAUbicar =0;
        vCantidadDisponible =0;
        lblCCant.setText("Max : " + mu.frmdecimal(vCantidadDisponible, 6));

        if (gl.gCProdAnterior.equals(txtPrd.getText())  && gl.gCUbicAnterior.equals(txtUbicOr.getText().toString()))
        {
            try{
                int IndexAux;

                if (gl.gCEstadoAnterior != -1 && cmbEstado.getAdapter()!=null  && cmbEstado.getAdapter().getCount()>0) {
                    IndexAux = cmbEstadoOrigenList.indexOf(gl.gCEstadoAnterior+ " - " + gl.gCNomEstadoAnterior);
                    cmbEstado.setSelection(IndexAux);
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
            //#CKFK 20210728 Agregué la condición del LP
            AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == gIdProductoOrigen)
                    .where(c -> c.IdPresentacion == cvPresID)
                    .where(c -> c.Lote.equals(gLoteOrigen))
                    .where(c -> c.Atributo_variante_1 == (cvAtrib == null ? "" : cvAtrib))
                    .where(c -> (cvEstEst > 0 ? c.IdProductoEstado == cvEstEst : c.IdProductoEstado >= 0))
                    .where(c -> (BeProductoUbicacionOrigen.Control_vencimiento?app.strFecha(c.Fecha_Vence).equals(cvVence):1==1))
                    .where(c -> c.Lic_plate.equals(txtLic_Plate.getText().toString()))
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
                Lic_Plate_Ant = (tmpStockResList.items.get(0).getLic_plate()==""?"":tmpStockResList.items.get(0).getLic_plate());
            }else{
                vCantidadAUbicar = 0;
                cvStockID =0;
            }

            if (Lic_Plate_Ant!=""){
                lblLpAnt.setVisibility(View.VISIBLE);
                txtLpAnt.setText(Lic_Plate_Ant);
                txtLpAnt.setVisibility(View.VISIBLE);
            }else{
                lblLpAnt.setVisibility(View.GONE);
                txtLpAnt.setVisibility(View.GONE);
            }

            if( Escaneo_Pallet && ListBeStockPallet != null) {

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
                Inicializa_Valores();
                return;
            }else{
                lblCCant.setText(mu.frmdecimal(vCantidadDisponible, gl.gCantDecDespliegue));
                txtCantidad.setText(mu.frmdecimal(vCantidadAUbicar, gl.gCantDecDespliegue));
                txtCantidad.selectAll();
            }

            txtNuevoLp.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtCantidad.requestFocus();

            fechaVenceU = app.strFechaXMLCombo(cvVence);

        }catch(Exception ex){
            msgbox("Llena cantidad " + ex.getMessage());
        }finally {
            progress.cancel();
        }

    }

    private boolean ValidaDatos(){

        boolean datosCorrectos=true;

        try{

            vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString().replace(",",""));
            vCantidadDisponible = Double.parseDouble(lblCCant.getText().toString().replace(",",""));

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


        }catch (Exception e){

        }
        return datosCorrectos;
    }

    private void aplicarCambio() {
        int vUbicacionProducto;

        try{

            imprimirDesdeBoton=false;

            progress.setMessage("Aplicando el cambio");
            progress.show();

            if (!ValidaDatos())return;

            vUbicacionProducto = BeStockPallet.IdUbicacion;

            if (pUbicacionLP!=0){
                if (vUbicacionProducto!=pUbicacionLP){
                    progress.cancel();
                    msgbox("La ubicación del License Plate seleccionado no coincide con la del License Plate destino, no se puede realizar el packing");
                    return;
                }
            }

            NuevoLp = txtNuevoLp.getText().toString();

            if (!Crear_Movimiento_Ubicacion_ND(true)){
                return;
            }

            vStockRes.IdProductoBodega = cvProd.IdProductoBodega;
            vStockRes.IdUbicacion = cvUbicOrigID;

            if( BeProductoUbicacionOrigen.Control_lote){
                vStockRes.Lote = cmbLote.getSelectedItem().toString();
            }else{
                vStockRes.Lote = "";
            }

            if( BeProductoUbicacionOrigen.Control_vencimiento){
                vStockRes.Fecha_Vence = app.strFechaXMLCombo(cmbVence.getSelectedItem().toString());
            }else{
                vStockRes.Fecha_Vence = "01/01/1900";
            }

            vStockRes.CantidadUmBas = vCantidadAUbicar;
            vStockRes.Peso = cvStockItem.Peso;
            vStockRes.IdPresentacion =cvPresID;
            vStockRes.IdProductoEstado = cvEstEst;
            vStockRes.Fecha_ingreso = app.strFechaXML(du.getFechaActual());
            vStockRes.ValorFecha = app.strFechaXML(du.getFechaActual());
            vStockRes.Lic_plate =NuevoLp;
            vStockRes.Lic_plate_Anterior = Lic_Plate_Ant;

            if( Escaneo_Pallet && ListBeStockPallet != null){

                vStockRes.Lic_plate = NuevoLp;//BeStockPallet.Lic_plate;

                if( BeStockPallet.Factor > 0){
                    vStockRes.CantidadUmBas = vCantidadAUbicar * BeStockPallet.Factor;
                }

            }else if ( cvPresID != 0){
                vStockRes.CantidadUmBas = vCantidadAUbicar * vFactorPres;
            }

            //Set_LP_Stock(gMovimientoDet, vStockRes);
            execws(8);

        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
            btnGuardarDirigida.setVisibility(View.VISIBLE);
        }
    }

    private boolean Crear_Movimiento_Ubicacion_ND(boolean EsCambioEstado) {

        try{

            progress.setMessage("Creando el movimiento");

            // The preferred idiom for iterating over collections and arrays
            for (clsBeVW_stock_res st : stockResList.items) {

                if(Escaneo_Pallet &&  ListBeStockPallet != null ) {
                    if (st.IdStock == cvStockID && st.Lic_plate.equals(BeStockPallet.Lic_plate)) {
                        cvStockItem = st;
                        break;}
                }else{
                    if(BeProductoUbicacionOrigen.Control_lote && BeProductoUbicacionOrigen.Control_vencimiento ) {
                        if (st.IdStock == cvStockID && st.Lote.equals(gLoteOrigen) && app.strFecha(st.Fecha_Vence).equals(cvVence) &&
                                st.IdPresentacion == cvPresID && st.IdProductoEstado == cvEstEst && st.IdUnidadMedida == cvUMBID) {
                            cvStockItem = st;
                            break;
                        }
                    }else if( !BeProductoUbicacionOrigen.Control_lote && BeProductoUbicacionOrigen.Control_vencimiento ) {
                        if (st.IdStock == cvStockID && app.strFecha(st.Fecha_Vence).equals(cvVence) &&
                                st.IdPresentacion == cvPresID && st.IdProductoEstado == cvEstEst) {
                            cvStockItem = st;
                            break;
                        }
                    }else if(BeProductoUbicacionOrigen.Control_lote &&  !BeProductoUbicacionOrigen.Control_vencimiento ) {
                        if (st.IdStock == cvStockID && st.Lote.equals(gLoteOrigen) && st.IdPresentacion == cvPresID &&
                                st.IdProductoEstado == cvEstEst && st.IdUnidadMedida == cvUMBID) {
                            cvStockItem = st;
                            break;
                        }
                    }else if(st.IdStock == cvStockID && st.IdPresentacion == cvPresID &&
                            st.IdProductoEstado == cvEstEst && st.IdUnidadMedida == cvUMBID ){
                        cvStockItem = st;
                        break;}
                }
            }

        }catch(Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox( ex.getMessage());
            btnGuardarDirigida.setVisibility(View.VISIBLE);
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
            if(cmbEstado.getAdapter()!=null && cmbEstado.getAdapter().getCount()>0){
                gMovimientoDet.IdEstadoOrigen = Integer.valueOf( cmbEstado.getSelectedItem().toString().split(" - ")[0].toString());
            }else{
                gMovimientoDet.IdEstadoOrigen = 0;
            }

            gMovimientoDet.IdEstadoDestino = cvEstEst;

            if(gl.modo_cambio == 1 ){
                gMovimientoDet.IdEstadoDestino = gMovimientoDet.IdEstadoOrigen;}

            gMovimientoDet.IdUnidadMedida = cvStockItem.IdUnidadMedida;


            gMovimientoDet.IdTipoTarea = 12;

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

            bprod = BeProductoUbicacionOrigen;

            if(bprod.Control_vencimiento ){
                if(cmbVence.getAdapter()!=null && cmbVence.getAdapter().getCount()>0){
                    gMovimientoDet.Fecha_vence = app.strFechaXMLCombo(cmbVence.getSelectedItem().toString());
                }else{
                    gMovimientoDet.Fecha_vence = app.strFechaXMLCombo("01/01/1900");
                }

            }

            gMovimientoDet.Fecha = app.strFechaXML(du.getFechaActual());

            if(Escaneo_Pallet &&  ListBeStockPallet != null ) {
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
            btnGuardarDirigida.setVisibility(View.VISIBLE);
            return false;
        }

    }

    public void Asociar(View view){

        try{

            AplicaPacking();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }finally {
            progress.cancel();
        }
    }

    private void AplicaPacking(){
        try{

            progress.setMessage("Aplicando cambio de ubicacion");
            progress.show();

            btnGuardarDirigida.setVisibility(View.INVISIBLE);

            msgAskAsociar("Asociar producto al número de pallet: "+txtNuevoLp.getText().toString());

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }finally {
            progress.cancel();
        }
    }

    private void msgAskAsociar(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.packing1);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    execws(11);
                    //aplicarCambio(); Primero voy a buscar ubicación del LP
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    btnGuardarDirigida.setVisibility(View.VISIBLE);
                    progress.cancel();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void SalirForm(View view){
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
                    return;
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

            gl.gCEstadoAnterior = -1;
            gl.gCNomEstadoAnterior = "";

            gl.gCFechaAnterior="01/01/1900";

            gl.gCLoteAnterior = "";

            gl.gCPresAnterior = -1;
            gl.gCNomPresAnterior = "";

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
                        callMethod("Get_IdUbic_Ciega_Recepcion_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicOr.getText().toString(),"pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_Stock_By_Lic_Plate","pLicensePlate",pLicensePlate,
                                "pIdBodega", gl.IdBodega);
                        break;
                    case 4:
                        callMethod("Get_Estados_By_IdPropietario",
                                "pIdPropietario",BeProductoUbicacionOrigen.getIdPropietario());
                        break;
                    case 5:
                        callMethod("Get_Productos_By_IdUbicacion_And_LicPlate",
                                "pIdUbicacion", txtUbicOr.getText().toString(),
                                "pIdProductoBodega",BeProductoUbicacionOrigen.IdProductoBodega,
                                "pLicPlate",BeStockPallet.Lic_plate);

                        break;
                    case 6:
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtPrd.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;
                    case 7:
                        callMethod("Get_Productos_By_IdUbicacion",
                                "pIdUbicacion", txtUbicOr.getText().toString(),
                                "pIdProductoBodega",BeProductoUbicacionOrigen.IdProductoBodega);
                        break;
                    case 8:
                        callMethod("Set_LP_Stock",
                                "pMovimiento", gMovimientoDet,
                                "pStockRes",vStockRes,
                                "pIdResolucionLp", gl.IdResolucionLpOperador);
                        break;
                    case 9:
                        callMethod("Existe_Lp_In_Stock","pLic_Plate",pLicensePlate);
                        break;
                    case 10:
                        //#CKFK 20210617 Agregué el llamado a esta función para obtener el LP para el Packing
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador",gl.IdOperador,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 11:
                        //#CKFK 20210617 Agregué el llamado a esta función para obtener la ubicación del LP
                        callMethod("Get_Ubicacion_LP",
                                   "pLic_Plate",txtNuevoLp.getText().toString(),
                                   "pIdBodega",gl.IdBodega,
                                   "nombre_ubicacion",pNombreUbicacionLP);
                        break;
                    case 12:
                        callMethod("Get_Stock_By_Lic_Plate_And_Codigo",
                                "pLicensePlate",txtLic_Plate.getText().toString(),
                                "pCodigo",txtPrd.getText().toString(),
                                "pIdBodega",gl.IdBodega);
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
                    processUbicRecepcion();
                    break;
                case 2:
                    processUbicacionBarra();
                    break;
                case 3:
                    processStockLP();
                    break;
                case 4:
                    processEstadosProp();
                    break;
                case 5:
                    processProductoUbicLP();
                    break;
                case 6:
                    processProducto();
                    break;
                case 7:
                    processProductoUbic();
                    break;
                case 8:
                    processSetStockLp();
                    break;
                case 9:
                    processExisteLp();
                    break;
                case 10:
                    processNuevoLPA();
                    //#EJC20210504: Refactor por resolución LP
                    //processNuevoLP();
                    break;
                case 11:
                    processUbicacionLP();
                    break;
                case 12:
                    processStockLP_AndCodigo();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    public static clsBeProducto gBeProducto = new clsBeProducto();

    private void processPresentacionesProducto(){

        try {

            progress.setMessage("Obteniendo presentaciones de producto");

            gBeProducto.Presentaciones = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProductoBodega");

            Listar_Producto_Presentaciones();

        }catch (Exception e){
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private ArrayList<String> PresList = new ArrayList<String>();
    private void Listar_Producto_Presentaciones() {

        try {
            if (gBeProducto.Presentaciones != null) {

                progress.setMessage("Listando presentaciones de producto");

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

    private void processExisteLp() {

        try{

            Existe_Lp = xobj.getresult(Boolean.class,"Existe_Lp_In_Stock");

            if (Existe_Lp){

                progress.cancel();
                txtPrd.requestFocus();

                //Get_Stock_By_Lic_Plate
                execws(3);

            }else{
                progress.cancel();
                mu.msgbox("Lp no existe");
                txtLic_Plate.selectAll();
                txtLic_Plate.requestFocus();
            }


        }catch (Exception e){
            mu.msgbox("processExisteLp:"+e.getMessage());
        }

    }

    private void processUbicRecepcion(){

        try{

            idUbicRecep = xobj.getresult(Integer.class,"Get_IdUbic_Ciega_Recepcion_By_IdBodega");

            if (idUbicRecep>0){
                txtUbicOr.setText(idUbicRecep+"");
                cvUbicOrigID = idUbicRecep;
                Scan_Ubic_Origen();
            }

        }catch (Exception e){
            mu.msgbox("ProcessUbicRecepcion:"+e.getMessage());
        }
    }

    private void processUbicacionBarra(){

        try{

            cUbicOrig = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (cUbicOrig!=null){

                if (cUbicOrig.IdUbicacion==0){
                    lblUbicOrigen.setText("Ubicación no válida");
                    return;
                }

                cvUbicOrigID = cUbicOrig.IdUbicacion;
                lblUbicOrigen.setText(cUbicOrig.Descripcion);
                cvUbicDestID = cvUbicOrigID;

                txtLic_Plate.setSelectAllOnFocus(true);
                txtLic_Plate.requestFocus();

                idle = true;

            }

            execws(10);

        }catch (Exception e){
            mu.msgbox("processUbicacionBarra:"+e.getMessage());
        }
    }

    private void processStockLP(){

        try{

            ListBeStockPallet = xobj.getresult(clsBeProductoList.class,"Get_Stock_By_Lic_Plate");

            if (Escaneo_Pallet && ListBeStockPallet == null) {
                lblDesProducto.setTextColor(Color.RED);
                gIdProductoOrigen = 0;
                lblDesProducto.setText("Código de LP no válido");
                Limpiar_Valores();
            }else{

                if (Escaneo_Pallet && ListBeStockPallet != null){

                    List AuxList = stream(ListBeStockPallet.items)
                            .where(c->c.Stock.IdUbicacion==cvUbicOrigID)
                            .toList();

                    if (AuxList.size() == 0) {
                        msgbox("El pallet no se encuentra en la ubicación: " + cvUbicOrigID);
                        lblDesProducto.setTextColor(Color.RED);
                        gIdProductoOrigen = 0;
                        lblDesProducto.setText("LP N.E.E.U");
                    }else{

                        ListBeStockPallet = new clsBeProductoList();
                        ListBeStockPallet.items = AuxList;

                        if (AuxList.size() == 1){

                            txtPrd.setText(ListBeStockPallet.items.get(0).Codigo);
                            BeProductoUbicacionOrigen = ListBeStockPallet.items.get(0);
                            BeStockPallet = ListBeStockPallet.items.get(0).Stock;

                            lblDesProducto.setTextColor(Color.BLUE);
                            lblDesProducto.setText(BeProductoUbicacionOrigen.getNombre());

                            cvProd = BeProductoUbicacionOrigen;
                            gIdProductoOrigen = BeProductoUbicacionOrigen.getIdProducto();
                            cvPropID = BeProductoUbicacionOrigen.getIdPropietario();
                            cvUMBID = BeProductoUbicacionOrigen.getIdUnidadMedidaBasica();

                            gLoteOrigen = BeStockPallet.Lote;
                            cvPresID = BeStockPallet.IdPresentacion;
                            gIdEstadoProductoOrigen = BeStockPallet.IdProductoEstado;
                            cvVence = app.strFecha(BeStockPallet.Fecha_Vence);

                            //Llama al método del WS Get_Estados_By_IdPropietario
                            execws(4);

                        }else{
                            progress.cancel();
                            msgbox("Escanee el producto que a ubicar");
                            txtPrd.requestFocus();
                        }

                    }

                }else{
                    //Llama a este método del WS Get_BeProducto_By_Codigo_For_HH
                    execws(5);
                }

            }

        }catch (Exception e){
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

                if (Escaneo_Pallet && ListBeStockPallet != null) {
                    //LLama este procedimiento del WS Get_Productos_By_IdUbicacion_And_LicPlate
                    execws(5);
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

    private void processProductoUbicLP(){

        try {

            progress.setMessage("Cargando stock de producto con License Plate");
            progress.show();

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion_And_LicPlate");

            if (stockResList != null){
                LlenaPresentaciones();
            }else{
                msgbox("No hay existencias disponibles de este producto en esta ubicación o las existentes están reservadas");
                Inicializa_Valores();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProducto(){

        try {

            progress.setMessage("Cargando datos del producto");
            progress.show();

            BeProductoUbicacionOrigen = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProductoUbicacionOrigen != null){
                gIdProductoOrigen=BeProductoUbicacionOrigen.getIdProducto();

                lblDesProducto.setTextColor(Color.BLUE);
                cvProd = BeProductoUbicacionOrigen;
                gIdProductoOrigen = BeProductoUbicacionOrigen.IdProducto;
                lblDesProducto.setText (BeProductoUbicacionOrigen.Nombre);
                cvPropID = BeProductoUbicacionOrigen.IdPropietario;
                cvUMBID = BeProductoUbicacionOrigen.IdUnidadMedidaBasica;

                //Llama al método del WS Get_Estados_By_IdPropietario
                execws(4);

            }else{
                lblDesProducto.setTextColor(Color.RED);
                gIdProductoOrigen = 0;
                lblDesProducto.setText ("Código no válido");
                mu.msgbox("Producto no existe");
                Limpiar_Valores();
                progress.cancel();
                txtPrd.requestFocus();
                txtPrd.selectAll();
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
                msgbox("El producto no existe en la ubicación origen");
                progress.cancel();
                txtPrd.requestFocus();
                txtPrd.selectAll();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processSetStockLp(){

        try{

            String pResultado= "";

            pResultado =xobj.getresult(String.class,"Set_LP_Stock");

            if (pResultado!=""){
                mu.msgbox("Cambio Aplicado");

                txtPrd.setText(gl.gCProdAnterior);
                txtUbicOr.setText(gl.gCUbicAnterior);

                if (cmbEstado.getAdapter()!=null  && cmbEstado.getAdapter().getCount()>0){
                    gl.gCEstadoAnterior = Integer.valueOf( cmbEstado.getSelectedItem().toString().split(" - ")[0]);
                    gl.gCNomEstadoAnterior = cmbEstado.getSelectedItem().toString().split(" - ")[1];
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
                    if (cmbPresentacion.getSelectedItem().toString().split(" - ").length>1){
                        gl.gCNomPresAnterior = cmbPresentacion.getSelectedItem().toString().split(" - ")[1];
                    }
                }else{
                    gl.gCPresAnterior = -1;
                    gl.gCNomPresAnterior = "";
                }

                lblDesProducto.setText("");

                Imprime_Barra_Despues_Guardar();

                //Get_Ubicacion_By_Codigo_Barra_And_IdBodega
                execws(1);


            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processNuevoLPA(){

        try {

            clsBeResolucion_lp_operador resolucionActivaLpByBodega = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");

            if (resolucionActivaLpByBodega !=null){

                gl.IdResolucionLpOperador = resolucionActivaLpByBodega.IdResolucionlp;

                float pLpSiguiente = resolucionActivaLpByBodega.Correlativo_Actual +1;
                float largoMaximo = String.valueOf(resolucionActivaLpByBodega.Correlativo_Final).length();

                int intLPSig = (int) pLpSiguiente;
                int MaxL = (int) largoMaximo;

                String str = String.valueOf(intLPSig);
                StringBuilder sb = new StringBuilder();

                for (int toPrepend= MaxL-str.length(); toPrepend>0; toPrepend--) {
                    sb.append('0');
                }

                sb.append(str);
                String result = sb.toString();

                txtNuevoLp.setText(resolucionActivaLpByBodega.Serie + result);

            }else{
                gl.IdResolucionLpOperador =0;
            }

        }catch (Exception e){
            mu.msgbox("processNuevoLP: "+e.getMessage());
        }

    }

    private void processUbicacionLP(){


        try{

            pUbicacionLP = xobj.getresult(Integer.class,"Get_Ubicacion_LP");
            pNombreUbicacionLP = xobj.getresultSingle(String.class,"nombre_ubicacion");

            if (pUbicacionLP>0){
                txtUbicDestino.setText(pUbicacionLP+"");
                lblNomDestino.setText(pNombreUbicacionLP);
            }

            aplicarCambio();

        }catch (Exception e){
            mu.msgbox("processUbicacionLP:"+e.getMessage());
        }
    }

    private void processStockLP_AndCodigo(){

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            ListBeStockPallet = xobj.getresult(clsBeProductoList.class,"Get_Stock_By_Lic_Plate_And_Codigo");

            if (Escaneo_Pallet && ListBeStockPallet == null) {
                lblDesProducto.setTextColor(Color.RED);
                gIdProductoOrigen = 0;
                lblDesProducto.setText("Código de LP no válido");
                Limpiar_Valores();
            }else{

                if (Escaneo_Pallet && ListBeStockPallet != null){

                    List AuxList = stream(ListBeStockPallet.items)
                            .where(c->c.Stock.IdUbicacion==cvUbicOrigID)
                            .toList();

                    if (AuxList.size() == 0) {
                        msgbox("El pallet no se encuentra en la ubicación: " + cvUbicOrigID);
                        lblDesProducto.setTextColor(Color.RED);
                        gIdProductoOrigen = 0;
                        lblDesProducto.setText("LP N.E.E.U");
                    }else{

                        ListBeStockPallet = new clsBeProductoList();
                        ListBeStockPallet.items = AuxList;

                        if (AuxList.size() == 1){

                            txtPrd.setText(ListBeStockPallet.items.get(0).Codigo);
                            BeProductoUbicacionOrigen = ListBeStockPallet.items.get(0);
                            BeStockPallet = ListBeStockPallet.items.get(0).Stock;

                            lblDesProducto.setTextColor(Color.BLUE);
                            lblDesProducto.setText(BeProductoUbicacionOrigen.getNombre());

                            cvProd = BeProductoUbicacionOrigen;
                            gIdProductoOrigen = BeProductoUbicacionOrigen.getIdProducto();
                            cvPropID = BeProductoUbicacionOrigen.getIdPropietario();
                            cvUMBID = BeProductoUbicacionOrigen.getIdUnidadMedidaBasica();

                            gLoteOrigen = BeStockPallet.Lote;
                            cvPresID = BeStockPallet.IdPresentacion;
                            gIdEstadoProductoOrigen = BeStockPallet.IdProductoEstado;
                            cvVence = app.strFecha(BeStockPallet.Fecha_Vence);

                            //Llama al método del WS Get_Estados_By_IdPropietario
                            execws(4);

                        }else{
                            progress.cancel();
                            msgbox("Escanee el producto que a ubicar");
                            txtPrd.requestFocus();
                        }

                    }

                }else{
                    //Llama a este método del WS Get_BeProducto_By_Codigo_For_HH
                    execws(5);
                }

            }


        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    public void Imprimir(View view){
        msgAskImprimir("Imprimir Licencia de Packing");
    }

    //#CKFK 20210617: Agregué funcionalidad de impresion
    private void msgAskImprimir(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Licencia de Packing", (dialog12, which) -> {
                progress.setMessage("Imprimiendo licencia de packing");
                progress.show();
                Imprimir_Licencia();
            });

            dialog.setNegativeButton("Salir", (dialog13, which) -> {
                if (!imprimirDesdeBoton){
                    progress.setMessage("Imprimiendo licencia");
                    progress.show();
                }else{
                    progress.cancel();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object()
            {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
            progress.cancel();
        }finally {
            progress.cancel();
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
                String zpl="";

                if (BeProductoUbicacionOrigen.IdTipoEtiqueta==1){
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
                                    "^FT670,367^A0I,25,24^FH^FDTOMWMS License Number^FS \n" +
                                    "^FO2,340^GB670,0,14^FS \n" +
                                    "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                    "^FD%4$s^FS \n" +
                                    "^PQ1,0,1,Y " +
                                    "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                            BeProductoUbicacionOrigen.Codigo+" - "+BeProductoUbicacionOrigen.Nombre,
                            "$"+NuevoLp);
                }else if (BeProductoUbicacionOrigen.IdTipoEtiqueta==2){
                    zpl = String.format("^XA\n" +
                                    "^MMT\n" +
                                    "^PW609\n" +
                                    "^LL0406\n" +
                                    "^LS0\n" +
                                    "^FT221,61^A0I,28,30^FH^FD%1$s^FS\n" +
                                    "^FT480,61^A0I,28,30^FH^FD%2$s^FS\n" +
                                    "^FT600,400^A0I,35,40^FH^FD%3$s^FS\n" +
                                    "^FT322,61^A0I,26,30^FH^FDBodega:^FS\n" +
                                    "^FT600,61^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                    "^FT600,500^A0I,25,24^FH^FDTOMWMS License Number^FS\n" +
                                    "^FO2,450^GB670,14,14^FS\n" +
                                    "^BY3,3,160^FT550,180^BCI,,Y,N\n" +
                                    "^FD%1$s^FS\n" +
                                    "^PQ1,0,1,Y \n" +
                                    "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                            BeProductoUbicacionOrigen.Codigo+" - "+BeProductoUbicacionOrigen.Nombre,
                            "$"+NuevoLp);
                }

                zPrinterIns.sendCommand(zpl);

                Thread.sleep(500);

                // Close the connection to release resources.
                printerIns.close();

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

            if (!imprimirDesdeBoton){
                msgAskImprimir("Imprimir licencia de packing");
            }

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir el código de barra del packing. No existe conexión a la impresora: "+ gl.MacPrinter);
                if (!imprimirDesdeBoton){
                    msgAskImprimir("Imprimir licencia de packing");
                }
            }else{
                mu.msgbox("Imprimir_barra de packing: "+e.getMessage());
            }
        }finally {
            progress.cancel();
        }
    }

    private void Imprime_Barra_Despues_Guardar(){

        try{

            progress.show();
            progress.setMessage("Validando imprimir barra");

            if (gl.IdImpresora>0){

                progress.cancel();
                imprimirDesdeBoton=false;

                msgAskImprimir("Imprimir Licencia de Packing");

            }

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
                if (!imprimirDesdeBoton){
                    msgAskImprimir("Imprimir licencia de packing");
                }
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }


}
