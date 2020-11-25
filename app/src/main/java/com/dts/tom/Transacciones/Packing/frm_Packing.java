package com.dts.tom.Transacciones.Packing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_Packing extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtUbicOr,txtPrd,txtNuevoLp,txtCantidad,txtLpAnt;
    private TextView lblUbicOrigen,lblDesProducto,lblLpAnt,lblIdStock,lblCCant,lblVence,lblLote;
    private Spinner cmbPres,cmbLote,cmbVence,cmbEstado;
    private Button btnGuardarDirigida,btnBack;

    private ProgressDialog progress;
    private Dialog dialog;

    private int idUbicRecep = 0;
    private int cvUbicOrigID=0;
    private int gIdProductoOrigen=0,cvPresID=0,gIdEstadoProductoOrigen=0,cvUbicDestID=0,cvEstEst=0;
    private int cvStockID;
    private int cvPropID=0,cvUMBID=0;
    private boolean idle=false,BorrarUbicOrigen=false;
    private String gLoteOrigen="",cvVence="";
    private double cvCant=0,cvCantMax=0;
    private boolean Escaneo_Pallet=false;
    private String pLP="";
    private double vCantidadAUbicar, vCantidadDisponible;
    private String cvAtrib;
    private double vFactorPres;
    private String  fechaVenceU;


    public static clsBeBodega_ubicacion cUbicOrig = new clsBeBodega_ubicacion();
    public static clsBeBodega_ubicacion cUbicDest = new clsBeBodega_ubicacion();
    private clsBeProductoList ListBeStockPallet = new clsBeProductoList();
    private clsBeProducto BeProductoUbicacionOrigen;
    private clsBeVW_stock_res BeStockPallet;
    private clsBeProducto cvProd = new clsBeProducto();
    private clsBeVW_stock_resList stockResList = new clsBeVW_stock_resList();

    private clsBeProducto_estadoList productoEstadoDestinoList = new clsBeProducto_estadoList();
    private clsBeVW_stock_resList presentacionList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList lotesList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList venceList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList productoEstadoOrigenList = new clsBeVW_stock_resList();

    private ArrayList<String> cmbEstadoDestinoList = new ArrayList<String>();
    private ArrayList<String> cmbPresentacionList = new ArrayList<String>();
    private ArrayList<String> cmbLoteList = new ArrayList<String>();
    private ArrayList<String> cmbVenceList = new ArrayList<String>();
    private ArrayList<String> cmbEstadoOrigenList = new ArrayList<String>();


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

        lblUbicOrigen = (TextView) findViewById(R.id.lblUbicOrigen);
        lblDesProducto = (TextView) findViewById(R.id.lblDesProducto);
        lblLpAnt = (TextView)findViewById(R.id.textView64);
        lblIdStock = (TextView)findViewById(R.id.textView65);
        lblCCant = (TextView)findViewById(R.id.textView66);
        lblLote= (TextView)findViewById(R.id.textView59);
        lblVence= (TextView)findViewById(R.id.textView60);

        cmbPres = (Spinner) findViewById(R.id.cmbPres);
        cmbLote = (Spinner) findViewById(R.id.cmbLote);
        cmbVence = (Spinner) findViewById(R.id.cmbVence);
        cmbEstado = (Spinner) findViewById(R.id.cmbEstado);


        ws = new frm_Packing.WebServiceHandler(frm_Packing.this, gl.wsurl);
        xobj = new XMLObject(ws);

        ProgressDialog("Cargando forma");

        setHandles();

        execws(1);

    }

    private void setHandles(){

        try{

            cmbPres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {

                        TextView spinlabel = (TextView) parentView.getChildAt(0);

                        if (spinlabel!=null){
                            spinlabel.setTextColor(Color.BLACK);
                            spinlabel.setPadding(5, 0, 0, 0);
                            spinlabel.setTextSize(18);
                            spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                            cvPresID = Integer.valueOf( cmbPres.getSelectedItem().toString().split(" - ")[0].toString());
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

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Busca_Producto(){

        try{

            gIdProductoOrigen= 0;

            String vStarWithParameter = "$";

            if (txtPrd.getText().toString().startsWith("$") ||
                    txtPrd.getText().toString().startsWith("(01)") ||
                    txtPrd.getText().toString().startsWith(vStarWithParameter)) {

                int vLengthBarra = txtPrd.getText().toString().length();

                if (vLengthBarra >= 16) {

                    Escaneo_Pallet = true;

                    pLP = txtPrd.getText().toString().replace("$", "");

                    //Llama al método del WS Get_Stock_By_Lic_Plate
                    execws(3);
                }

            }else{
                Escaneo_Pallet=false;
                execws(6);
            }

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
                String vCorrelativo = (cyear-2000) + String.format("%2d",cmonth) + String.format("%2d",cday) + String.format("%2d",ch) + String.format("%2d",cm);

                txtNuevoLp.setText(vCodigoBodega + vCodigoProducto + "W"+vCorrelativo); // & vCorrelativo
            }

        }catch (Exception e){
            mu.msgbox("Busca_Producto:"+e.getMessage());
        }
    }

    private String FormatBodega(String pCodigo){

        String cdBodega="";

        try{

            int Lengthindex = pCodigo.length()-1;

            if (Lengthindex==2){
                cdBodega = pCodigo;
            }else if (Lengthindex==1){
                cdBodega = "0"+pCodigo;
            }else if (Lengthindex==0){
                cdBodega = "00"+pCodigo;
            }else{
                mu.msgbox("Formato de bodega incorrecto");
            }

        }catch (Exception e){
            mu.msgbox("FormatBodega:"+e.getMessage());
        }

        return  cdBodega;
    }

    private String FormatProducto(String pCodigo){

        String cdBodega="";

        try{

            int Lengthindex = pCodigo.length()-1;

            if (Lengthindex==4){
                cdBodega = pCodigo;
            }else if (Lengthindex==3){
                cdBodega = "0"+pCodigo;
            }else if (Lengthindex==2){
                cdBodega = "00"+pCodigo;
            }else if (Lengthindex==1){
                cdBodega = "000"+pCodigo;
            }else if (Lengthindex==0){
                cdBodega = "0000"+pCodigo;
            }else{
                mu.msgbox("Formato de producto incorrecto");
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
            cmbPres.setSelection(0);
            cmbEstado.setSelection(0);
            cmbVence.setSelection(0);
            cmbLote.setSelection(0);
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
            txtPrd.setEnabled(false);

        }catch (Exception e){

        }
    }

    private void Scan_Ubic_Origen(){

        try{

            int CantPorUbic= 0;
            cvUbicOrigID = 0;

            txtPrd.setText("");
            txtPrd.setSelectAllOnFocus(true);
            txtPrd.requestFocus();
            lblDesProducto.setText("");

            if (txtUbicOr.getText().equals("")){
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

            List AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == gIdProductoOrigen)
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
            cmbPres.setAdapter(dataAdapter);

            if (cmbPresentacionList.size() > 0) {

                if (Escaneo_Pallet && ListBeStockPallet !=null ){
                    int sel = cmbPresentacionList.indexOf(BeStockPallet.getIdPresentacion() + " - " +
                            BeStockPallet.getNombre_Presentacion());
                    cmbPres.setSelection(sel);
                    cmbPres.setEnabled(false);
                }else{
                    cmbPres.setSelection(0);
                    if (cmbPresentacionList.size() == 1){
                        cmbPres.setEnabled(false);
                    }else{
                        cmbPres.setEnabled(true);
                    }
                }

                cvPresID =Integer.valueOf( cmbPres.getSelectedItem().toString().split(" - ")[0].toString());

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

                if (gl.gCPresAnterior != -1 && cmbPres.getAdapter()!=null && cmbPres.getAdapter().getCount()>0) {
                    IndexAux = cmbPresentacionList.indexOf( gl.gCPresAnterior + " - " + gl.gCNomPresAnterior);
                    cmbPres.setSelection(IndexAux);
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
                    .where(c -> c.IdProducto == gIdProductoOrigen)
                    .where(c -> c.IdPresentacion == cvPresID)
                    .where(c -> c.Lote.equals(gLoteOrigen))
                    .where(c -> c.Atributo_variante_1 == (cvAtrib == null ? "" : cvAtrib))
                    .where(c -> (cvEstEst > 0 ? c.IdProductoEstado == cvEstEst : c.IdProductoEstado >= 0))
                    .where(c -> (BeProductoUbicacionOrigen.Control_vencimiento?app.strFecha(c.Fecha_Vence).equals(cvVence):1==1))
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

            txtCantidad.setEnabled(true);

            txtCantidad.requestFocus();

            fechaVenceU = app.strFechaXMLCombo(cvVence);

        }catch(Exception ex){
            msgbox("Llena cantidad " + ex.getMessage());
        }finally {
            progress.cancel();
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
                        callMethod("Get_Stock_By_Lic_Plate","pLicensePlate",pLP,
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

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processUbicRecepcion(){

        try{

            idUbicRecep = xobj.getresult(Integer.class,"Get_IdUbic_Ciega_Recepcion_By_IdBodega");

            if (idUbicRecep>0){
                txtUbicOr.setText(idUbicRecep);
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

            if (cUbicOrig.IdUbicacion==0){
                lblUbicOrigen.setText("Ubicación no válida");
                return;
            }

            cvUbicOrigID = cUbicOrig.IdUbicacion;
            lblUbicOrigen.setText(cUbicOrig.Descripcion);

            txtPrd.setEnabled(true);
            txtPrd.setSelectAllOnFocus(true);
            txtPrd.requestFocus();

            idle = true;

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
                    }

                }else{
                    //Llama a este método del WS Get_BeProducto_By_Codigo_For_HH
                    execws(5);
                }

            }

        }catch (Exception e){

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
                Inicializa_Valores();
                lblDesProducto.setTextColor(Color.RED);
                gIdProductoOrigen = 0;
                lblDesProducto.setText ("Código no válido");
                throw new Exception("Producto no existe");
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
                txtPrd.requestFocus();
                txtPrd.selectAll();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }


}
