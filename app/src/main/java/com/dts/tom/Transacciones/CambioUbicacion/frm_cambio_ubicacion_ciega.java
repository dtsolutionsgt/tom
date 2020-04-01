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
import java.util.Date;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_cambio_ubicacion_ciega extends PBase {

    private frm_cambio_ubicacion_ciega.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtUbicOrigen,txtCodigoPrd,txtCantidad,txtUbicDestino;
    private TextView lblUbicCompleta,lblDescProducto,lblLote, lblVence,lblEstadoDestino,lblCant;
    private Spinner cmbPresentacion,cmbLote,cmbVence,cmbEstadoOrigen,cmbEstadoDestino;

    private clsBeMotivo_ubicacionList pListBeMotivoUbicacion = new clsBeMotivo_ubicacionList();

    private clsBeProducto_Presentacion presentacion = new clsBeProducto_Presentacion();
    private ArrayList<clsBeProducto_Presentacion> presentacionArrayList = new ArrayList<clsBeProducto_Presentacion>();

    private clsBeProducto cvProd = new clsBeProducto();
    private clsBeProducto producto_ubicacion = new clsBeProducto();
    private ArrayList<clsBeProducto> productoArrayList = new ArrayList<clsBeProducto>();
    private clsBeProductoList productoList = new clsBeProductoList();

    private clsBeVW_stock_res stock_res = new clsBeVW_stock_res();
    private clsBeVW_stock_resList stockResList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList lotesList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList venceList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList presentacionList = new clsBeVW_stock_resList();
    private clsBeVW_stock_resList productoEstadoOrigenList = new clsBeVW_stock_resList();

    private ArrayList<clsBeVW_stock_res> fechaVenceArrayList = new ArrayList<clsBeVW_stock_res>();
    private ArrayList<clsBeVW_stock_res> loteArrayList = new ArrayList<clsBeVW_stock_res>();

    private clsBeProducto_estado producto_estado_origen = new clsBeProducto_estado();
    private ArrayList<clsBeProducto_estado> productoEstadoOrigenArrayList = new ArrayList<clsBeProducto_estado>();
    private clsBeProducto_estadoList productoEstadoDestinoList = new clsBeProducto_estadoList();

    private clsBeProducto_estado producto_estado_destino = new clsBeProducto_estado();
    private ArrayList<clsBeProducto_estado> productoEstadoDestinoArrayList = new ArrayList<clsBeProducto_estado>();

    private ArrayList<String> cmbPresentacionList= new ArrayList<String>();
    private ArrayList<String> cmbLoteList= new ArrayList<String>();
    private ArrayList<String> cmbVenceList= new ArrayList<String>();
    private ArrayList<String> cmbEstadoOrigenList= new ArrayList<String>();
    private ArrayList<String> cmbEstadoDestinoList= new ArrayList<String>();

    private int idPresentacion=0,idEstadoOrigen=0,idEstadoDestino=0;
    String lote="", fechaVence="";

    private int cvUbicOrigID;
    private int cvProdID;
    private int cvPresID;
    private String cvLote;
    private Date cvVence;
    private int cvEstadoID;
    private int cvEstEst;
    private int cvUbicDestID;
    private double cvCant, cvCantMax;
    private int cvStockID;
    private String cvAtrib;
    private int cvPropID;
    private int cvUMBID;

    private boolean escaneoPallet;

    private clsBeTrans_movimientos gMovimientoDet;
    private clsBeBodega_ubicacion bodega_ubicacion;
    private clsBeProducto_estado gProdEstado;

    private clsBeProducto BeProductoUbicacion;
    private int IdProductoUbicacion;

    private clsBeVW_stock_res BeStockPallet;

    private String vNuevoPalletId;
    private int vIdStockNuevo=0;
    private int vIdMovimientoNuevo=0;

    private double vCantidadAUbicar, vCantidadDisponible;
    private boolean compl;

    private boolean continua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       try{
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

           cmbEstadoDestino.setVisibility(gl.modo_cambio==1?View.GONE:View.VISIBLE);
           lblEstadoDestino.setVisibility(gl.modo_cambio==1?View.GONE:View.VISIBLE);

           ProgressDialog("Cargando forma");

           setHandlers();

           Load();

       }catch (Exception ex){
           addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
           msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
       }
    }

    private void Load(){

        try{
           execws(1);
        }catch (Exception ex){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }

    }

    private void setHandlers() {

        cmbPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idPresentacion=presentacionList.items.get(position).IdPresentacion;
                    execws(2);
                } catch (Exception ex) {
                    addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    lote=stockResList.items.get(position).Lote;

                    execws(3);

                } catch (Exception ex) {
                    addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    fechaVence=stockResList.items.get(position).getFecha_Vence();

                } catch (Exception e) { }

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
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idEstadoOrigen=productoEstadoOrigenList.items.get(position).getIdProductoEstado();

                } catch (Exception e) { }

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
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idEstadoDestino=productoEstadoDestinoList.items.get(position).IdEstado;

                } catch (Exception e) { }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        txtCodigoPrd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                   llenaDatosProducto();
                }

                return false;
            }
        });

        txtUbicOrigen.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            continua=false;
                            validaOrigen();
                    }
                }
                return false;
            }
        });

        txtUbicDestino.setOnKeyListener(new View.OnKeyListener(){

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

        txtCantidad.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            cambioUbicEst();
                    }
                }
                return false;
            }
        });


    }

    private void LlenaPresentaciones() {
        try {

            List AuxList = stream(stockResList.items)
                    .where(c->c.IdProducto==cvProdID)
                    .select(c -> c.getNombre_Presentacion())
                    .distinct().toList();

            presentacionList.items=AuxList;

            cmbPresentacionList.clear();

            for (int i = 0; i <presentacionList.items.size(); i++) {
                cmbPresentacionList.add(presentacionList.items.get(i).Nombre_Presentacion);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbPresentacionList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresentacion.setAdapter(dataAdapter);

            if (cmbPresentacionList.size()>0) cmbPresentacion.setSelection(0);

            cvPresID = presentacionList.items.get(0).getIdPresentacion();

            LlenaLotes();

        } catch (Exception ex) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaLotes() {
        try {
            cmbLoteList.clear();

            try{

                cvLote="";

                cmbLote.setVisibility(BeProductoUbicacion.Control_lote?View.VISIBLE:View.GONE);
                lblLote.setVisibility(BeProductoUbicacion.Control_lote?View.VISIBLE:View.GONE);

                if (BeProductoUbicacion.Control_lote){

                    try{

                        List AuxList;

                        if (escaneoPallet && productoList != null ){

                             AuxList = stream(stockResList.items)
                                    .where(c->c.IdProducto==cvProdID)
                                    .where(c->c.getIdPresentacion() == cvPresID)
                                    .where(c->c.getLic_plate() == BeStockPallet.Lic_plate)
                                    .select(c -> c.getLote())
                                    .distinct()
                                    .toList();

                        }else{

                             AuxList = stream(stockResList.items)
                                    .where(c->c.IdProducto==cvProdID)
                                    .where(c->c.getIdPresentacion() == cvPresID)
                                    .select(c -> c.getLote())
                                    .distinct()
                                    .toList();
                        }

                        if (AuxList == null ){
                            cvLote = "";
                            LlenaFechaVence();
                            return;
                        }else{

                            lotesList.items=AuxList;

                            for (int i = 0; i <lotesList.items.size(); i++) {
                                cmbLoteList.add(lotesList.items.get(i).Lote);
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbLoteList);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cmbLote.setAdapter(dataAdapter);

                            if (cmbLoteList.size()>0) cmbLote.setSelection(0);

                            cvLote = lotesList.items.get(0).getLote();

                            LlenaFechaVence();
                        }
                    }catch (Exception ex){
                        cvLote = "";
                        msgbox("Llena lote : " + ex.getMessage());
                        LlenaFechaVence();
                    }

                }else{
                    LlenaFechaVence();
                }

            }catch (Exception ex){
                msgbox("Error al llenar el lote: " + ex.getMessage());
            }
        } catch (Exception ex) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaFechaVence() {
        try {
            cmbVenceList.clear();

            try{

                cvVence=app.dateFechaStr("1900-01-01T00:00:01");

                cmbVence.setVisibility(BeProductoUbicacion.Control_vencimiento?View.VISIBLE:View.GONE);
                lblVence.setVisibility(BeProductoUbicacion.Control_vencimiento?View.VISIBLE:View.GONE);

                if (BeProductoUbicacion.Control_vencimiento){

                    try{

                        Date currentTime = Calendar.getInstance().getTime();

                        cvLote = cmbLote.getSelectedItem().toString();
                        cvVence = currentTime;

                        List AuxList;

                        if (BeProductoUbicacion.Control_lote){
                            if (escaneoPallet && productoList != null ){

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote()==cvLote)
                                        .where(c->c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .select(c -> c.getFecha_Vence())
                                        .distinct()
                                        .toList();

                            }else{

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote()==cvLote)
                                        .select(c -> c.getFecha_Vence())
                                        .distinct()
                                        .toList();
                            }
                        }else{
                            if (escaneoPallet && productoList != null ){

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .where(c->c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .distinct()
                                        .toList();

                            }else{

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .distinct()
                                        .toList();
                            }
                        }

                        if (AuxList == null ){
                            cvVence = app.dateFechaStr("1900-01-01T00:00:01");;
                            LlenaEstadoOrigen();
                            return;
                        }else{

                            venceList.items=AuxList;

                            for (int i = 0; i <venceList.items.size(); i++) {
                                cmbVenceList.add(app.strFecha(venceList.items.get(i).Fecha_Vence));
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbVenceList);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cmbVence.setAdapter(dataAdapter);

                            if (cmbVenceList.size()>0) cmbVence.setSelection(0);

                            cvVence =app.dateFechaStr(venceList.items.get(0).getFecha_Vence());

                            LlenaEstadoOrigen();
                        }
                    }catch (Exception ex){
                        cvVence = app.dateFechaStr("1900-01-01T00:00:01");
                        msgbox("Llena vence : " + ex.getMessage());
                        LlenaEstadoOrigen();
                        return;
                    }


                }else{
                    LlenaFechaVence();
                }

            }catch (Exception ex){
                msgbox("Error al llenar el lote: " + ex.getMessage());
            }
        } catch (Exception ex) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaEstadoOrigen() {

        try {

            cmbEstadoOrigenList.clear();

            try{

                if (BeProductoUbicacion.Control_vencimiento){

                    try{

                        Date currentTime = Calendar.getInstance().getTime();

                        cvLote = cmbLote.getSelectedItem().toString();
                        cvVence =app.dateFechaStr(cmbLote.getSelectedItem().toString());

                        List AuxList;

                        if (BeProductoUbicacion.Control_lote){
                            if (escaneoPallet && productoList != null ){

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote()==cvLote)
                                        .where(c->c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .select(c -> c.getNomEstado())
                                        .distinct()
                                        .toList();

                            }else{

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .where(c -> c.getLote()==cvLote)
                                        .select(c -> c.getNomEstado())
                                        .distinct()
                                        .toList();
                            }
                        }else{
                            if (escaneoPallet && productoList != null ){

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .where(c->c.getLic_plate() == BeStockPallet.Lic_plate)
                                        .distinct()
                                        .toList();

                            }else{

                                AuxList = stream(stockResList.items)
                                        .where(c->c.IdProducto==cvProdID)
                                        .where(c->c.getIdPresentacion() == cvPresID)
                                        .distinct()
                                        .toList();
                            }
                        }

                        if (AuxList == null ){
                            cvVence = app.dateFechaStr("1900-01-01T00:00:01");
                            LlenaEstadoOrigen();
                            return;
                        }else{

                            productoEstadoOrigenList.items=AuxList;

                            for (int i = 0; i <productoEstadoOrigenList.items.size(); i++) {
                                cmbEstadoOrigenList.add(productoEstadoOrigenList.items.get(i).getNomEstado());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbEstadoOrigenList);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cmbEstadoOrigen.setAdapter(dataAdapter);

                            if (cmbEstadoOrigenList.size()>0) cmbEstadoOrigen.setSelection(0);

                        }
                    }catch (Exception ex){
                        cvVence = app.dateFechaStr("1900-01-01T00:00:01");
                        msgbox("Llena vence : " + ex.getMessage());
                        LlenaEstadoOrigen();
                        return;
                    }


                }else{
                    LlenaFechaVence();
                }

            }catch (Exception ex){
                msgbox("Error al llenar el lote: " + ex.getMessage());
            }
        } catch (Exception ex) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaEstadoDestino(int idPropietario) {
        try {
            cmbEstadoDestinoList.clear();

            for (int i = 0; i <productoEstadoDestinoList.items.size(); i++) {
                cmbEstadoDestinoList.add(productoEstadoDestinoList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbEstadoDestinoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDestino.setAdapter(dataAdapter);

            if (cmbEstadoDestinoList.size()>0) cmbEstadoDestino.setSelection(0);

            if (escaneoPallet && productoList != null) {
                //LLama este procedimiento del WS Get_Productos_By_IdUbicacion_And_LicPlate
                execws(6);
            }else{
                //LLama este procedimiento del WS Get_Productos_By_IdUbicacion
                execws(7);
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void llenaDatosProducto(){
        try{


            String pLP = "";
            String pbarra;

            int cvProdID = 0;

            pbarra = txtCodigoPrd.getText().toString();

            cvLote = "";
            cvPresID = 0;
            cvEstadoID = 0;
            cvVence = app.dateFechaStr("1900-01-01T00:00:01");

            String vStarWithParameter = "$";

            //Comentario: La barra de pallet puede comenzar con $ y no con (01)
            if (txtCodigoPrd.getText().toString().startsWith("$")  ||
                    txtCodigoPrd.getText().toString().startsWith("(01)")  ||
                    txtCodigoPrd.getText().toString().startsWith(vStarWithParameter)) {

                //Es una barra de pallet válida por tamaño
                int vLengthBarra = txtCodigoPrd.getText().toString().length();

                if (vLengthBarra >= 16) {

                    escaneoPallet = true;

                    pLP = txtCodigoPrd.getText().toString().replace("$", "");

                    //Llama al método del WS Get_Stock_By_Lic_Plate
                    execws(5);

                }

            }else{
                escaneoPallet = false;
                //Llama al método del WS Get_BeProducto_By_Codigo_For_HH
                execws(3);
            }

        }catch (Exception ex){
            msgbox("Error " + ex.getMessage());
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
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicRecepcion(){

        try {

            progress.setMessage("Obteniendo la ubicación por defecto de la recepción");

            cvUbicOrigID = (Integer) xobj.getSingle("Get_IdUbic_Ciega_Recepcion_By_IdBodegaResult",int.class);

            if (cvUbicOrigID > 0){
                if (gl.modo_cambio == 1) {
                    txtUbicOrigen.setText(String.valueOf(cvUbicOrigID));
                    lblUbicCompleta.setText("");
                    txtCodigoPrd.requestFocus();
                }else{
                    txtUbicOrigen.setText("");
                }
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

            bodega_ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion == null)
            {
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                throw new Exception("Ubicación no válida");
            }else{
                cvUbicOrigID=bodega_ubicacion.getIdUbicacion();
                lblUbicCompleta.setText(bodega_ubicacion.getNombreCompleto());
            }

            if (!continua){
                txtCodigoPrd.requestFocus();
            }else{
                execws(12);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processUbicDestino(){

        try {

            progress.setMessage("Validando ubicación");

            bodega_ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion == null){
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("Ubicación destino incorrecta");
            }else{
                cvUbicDestID=bodega_ubicacion.getIdUbicacion();
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

            progress.setMessage("Validando ubicación");

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
                        cvEstadoID = BeStockPallet.IdProductoEstado;
                        cvVence = app.dateFechaStr(BeStockPallet.Fecha_Vence);
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

            progress.setMessage("Validando ubicación");

            progress.setMessage("Cargando stock de producto con License Plate");

            stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Productos_By_IdUbicacion");

            if (stockResList != null){
                LlenaPresentaciones();
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processNuevoCorrelativoLP(){

        try {

            progress.setMessage("Validando ubicación");

            bodega_ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion == null){
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("Ubicación destino incorrecta");
            }

            if (bodega_ubicacion.IdUbicacion != gl.IdDestino){
                mu.msgbox("La ubicación destino no coincide");
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                return;
            }

            txtCantidad.selectAll();
            txtCantidad.requestFocus();

        } catch (Exception e) {
        }

    }

    private void processIdNuevoPallet(){

        try {

            progress.setMessage("Validando ubicación");

            bodega_ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion == null){
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("Ubicación destino incorrecta");
            }

            if (bodega_ubicacion.IdUbicacion != gl.IdDestino){
                mu.msgbox("La ubicación destino no coincide");
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                return;
            }

            txtCantidad.selectAll();
            txtCantidad.requestFocus();

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

    private void validaOrigen(){

        try{

            if (txtUbicOrigen.getText().toString() !=""){

                bodega_ubicacion = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega
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

                bodega_ubicacion = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega
                execws(12);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void cambioUbicEst(){

        try{

            vCantidadAUbicar=0;
            compl = false;
            double cantStock;

            vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString());

            if (vCantidadAUbicar<0) {
                mu.msgbox("La cantidad no puede ser negativa");
                txtCantidad.requestFocus();
                return;
            }

            if (vCantidadAUbicar==0) {
                mu.msgbox("La cantidad debe ser mayor que 0");
                txtCantidad.requestFocus();
                return;
            }

            cantStock = gl.gCantDisponible;

            if (vCantidadAUbicar >cantStock){
                mu.msgbox("Cantidad mayor que stock disponible: "+ cantStock);
                txtCantidad.requestFocus();
                return;
            }

            if ((vCantidadAUbicar-cantStock)==0){
                compl=true;
            }

            msgAsk("Aplicar cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private boolean datosOk(){

        boolean continua=true;

        execws(11);

        if (cvUbicOrigID == 0) {
            msgbox("Ubicación origen no válida");
            txtUbicOrigen.requestFocus();
            return false;
        }

        if (cvProdID == 0) {
            msgbox("Producto no válido");
            txtCodigoPrd.requestFocus();
            return false;
        }

        if (cvCantMax == 0) {
            msgbox("Cantidad disponible es 0, no se puede realizar el movimiento");
            txtCodigoPrd.requestFocus();
            return false;
        }

        if (gl.modo_cambio==2) {
           // cvEstEst = cmbEstadoDestino.SelectedValue;
        }

        if (cvEstEst == 0){
            msgbox("Estado destino incorrecto");
            cmbEstadoDestino.requestFocus();
            return false;
        }

        vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString());
        vCantidadDisponible = Double.parseDouble(lblCant.getText().toString());

        if (vCantidadAUbicar<0) {
            mu.msgbox("La cantidad no puede ser negativa");
            txtCantidad.requestFocus();
            return false;
        }

        if (vCantidadAUbicar==0) {
            msgbox("La cantidad debe ser mayor que 0");
            txtCantidad.requestFocus();
            return false;
        }

        if (vCantidadAUbicar> vCantidadDisponible) {
            msgbox("Cantidad incorrecta") ;
            txtCantidad.requestFocus();
            return false;
        }

        if ((cvUbicOrigID == cvUbicDestID) && (gl.modo_cambio ==1)){
            msgbox("La ubicación de destino coincide con la de origen");
            txtUbicDestino.requestFocus();
            return false;
        }

        return true;
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
                    //Llamar método para aplicar el cambio de estado

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

    public void Regresar(View view){
        finish();
    }

    @Override
    public void onBackPressed()
    {
    }

}
