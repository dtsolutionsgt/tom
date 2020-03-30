package com.dts.tom.Transacciones.CambioUbicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodegaList;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
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
import java.util.Date;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_cambio_ubicacion_ciega extends PBase {

    private frm_cambio_ubicacion_ciega.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtUbicOrigen,txtCodigoPrd,txtCantidad,txtUbicDestino;
    private TextView lblUbicCompleta,lblDescProducto;
    private Spinner cmbPresentacion,cmbLote,cmbVence,cmbEstadoDanado,cmbEstadoUbicacion;

    private clsBeMotivo_ubicacionList pListBeMotivoUbicacion = new clsBeMotivo_ubicacionList();

    private clsBeProducto_Presentacion presentacion = new clsBeProducto_Presentacion();
    private ArrayList<clsBeProducto_Presentacion> presentacionArrayList = new ArrayList<clsBeProducto_Presentacion>();
    private clsBeProducto_PresentacionList presentacionList = new clsBeProducto_PresentacionList();

    private clsBeProducto producto_ubicacion = new clsBeProducto();
    private ArrayList<clsBeProducto> productoArrayList = new ArrayList<clsBeProducto>();
    private clsBeProductoList productoList = new clsBeProductoList();

    private clsBeVW_stock_res stock_res = new clsBeVW_stock_res();
    private clsBeVW_stock_resList stockResList = new clsBeVW_stock_resList();

    private ArrayList<clsBeVW_stock_res> fechaVenceArrayList = new ArrayList<clsBeVW_stock_res>();
    private ArrayList<clsBeVW_stock_res> loteArrayList = new ArrayList<clsBeVW_stock_res>();

    private clsBeProducto_estado producto_estado_danado = new clsBeProducto_estado();
    private ArrayList<clsBeProducto_estado> productoEstadoDanadoArrayList = new ArrayList<clsBeProducto_estado>();
    private clsBeProducto_estadoList productoEstadoDanadoList = new clsBeProducto_estadoList();

    private clsBeProducto_estado producto_estado_ubicacion = new clsBeProducto_estado();
    private ArrayList<clsBeProducto_estado> productoEstadoUbicacionArrayList = new ArrayList<clsBeProducto_estado>();
    private clsBeProducto_estadoList productoEstadoUbicacionList = new clsBeProducto_estadoList();

    private ArrayList<String> cmbPresentacionList= new ArrayList<String>();
    private ArrayList<String> cmbLoteList= new ArrayList<String>();
    private ArrayList<String> cmbVenceList= new ArrayList<String>();
    private ArrayList<String> cmbEstadoDanadoList= new ArrayList<String>();
    private ArrayList<String> cmbEstadoUbicacionList= new ArrayList<String>();

    private int idPresentacion=0,idEstadoDanado=0,idEstadoUbicacion=0;
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

    private double vCantidadAUbicar;
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
           lblDescProducto = (TextView) findViewById(R.id.lblDescProd);

           cmbPresentacion = (Spinner) findViewById(R.id.cmbPresentacion);
           cmbLote = (Spinner) findViewById(R.id.cmbLote);
           cmbVence = (Spinner) findViewById(R.id.cmbVence);
           cmbEstadoDanado = (Spinner) findViewById(R.id.cmbEstadoDanado);
           cmbEstadoUbicacion = (Spinner) findViewById(R.id.cmbEstadoDestino);

           ProgressDialog("Cargando forma");

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

        cmbEstadoDanado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idEstadoDanado=productoEstadoDanadoList.items.get(position).IdEstado;

                } catch (Exception e) { }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        cmbEstadoUbicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idEstadoUbicacion=productoEstadoUbicacionList.items.get(position).IdEstado;

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
            cmbPresentacionList.clear();

            for (int i = 0; i <presentacionList.items.size(); i++) {
                cmbPresentacionList.add(presentacionList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbPresentacionList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresentacion.setAdapter(dataAdapter);

            if (cmbPresentacionList.size()>0) cmbPresentacion.setSelection(0);
        } catch (Exception ex) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaLotes() {
        try {
            cmbPresentacionList.clear();

            for (int i = 0; i <presentacionList.items.size(); i++) {
                cmbPresentacionList.add(presentacionList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbPresentacionList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresentacion.setAdapter(dataAdapter);

            if (cmbPresentacionList.size()>0) cmbPresentacion.setSelection(0);
        } catch (Exception ex) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void LlenaFechaVence() {
        try {
            cmbPresentacionList.clear();

            for (int i = 0; i <presentacionList.items.size(); i++) {
                cmbPresentacionList.add(presentacionList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbPresentacionList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresentacion.setAdapter(dataAdapter);

            if (cmbPresentacionList.size()>0) cmbPresentacion.setSelection(0);
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void LlenaEstadoDanado() {
        try {
            cmbEstadoDanadoList.clear();

            for (int i = 0; i <productoEstadoDanadoList.items.size(); i++) {
                cmbEstadoDanadoList.add(productoEstadoDanadoList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbEstadoDanadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDanado.setAdapter(dataAdapter);

            if (cmbEstadoDanadoList.size()>0) cmbEstadoDanado.setSelection(0);
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void LlenaEstadoUbicacion() {
        try {
            cmbEstadoUbicacionList.clear();

            for (int i = 0; i <productoEstadoUbicacionList.items.size(); i++) {
                cmbEstadoUbicacionList.add(productoEstadoUbicacionList.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cmbEstadoUbicacionList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoUbicacion.setAdapter(dataAdapter);

            if (cmbEstadoUbicacionList.size()>0) cmbEstadoUbicacion.setSelection(0);
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void llenaDatosProducto(){
        try{
            // Data As Object = Nothing
            //cbotable, ctable As New DataTable
            String pLP = "";
            String pbarra;

            int cvProdID = 0;

            pbarra = txtCodigoPrd.getText().toString();

            cvLote = "";
            cvPresID = 0;
            cvEstadoID = 0;
            cvVence = new Date(19000101);

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

                    //Obtener el stock donde exista ese License Plate
                    execws(5);

                }

            }else{
                escaneoPallet = false;
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
                    processUbicOrigen();break;
                case 12:
                    processUbicDestino();break;
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
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 4://Obtiene el estado del producto
                        callMethod("Get_Single_By_IdEstado","pIdEstado",gl.tareadet.IdEstadoDestino);
                        break;
                    case 5://Obtiene el stock con License Plate en una bodega
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
                    case 33:
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

            cvUbicOrigID = (Integer) xobj.getSingle("Get_IdUbic_Ciega_Recepcion_By_IdBodega",int.class);

            if (cvUbicOrigID > 0){
                if (gl.modo_cambio == 1) {
                    txtUbicOrigen.setText(cvUbicOrigID);
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
                throw new Exception("Ubicación origen incorrecta");
            }

            if (bodega_ubicacion.IdUbicacion != gl.IdOrigen)
            {
                mu.msgbox("La ubicación origen no coincide");
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                return;
            }

            if (!continua){
                txtUbicDestino.requestFocus();
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

    private void processProducto(){

        try {

            progress.setMessage("Validando ubicación");

            clsBeProducto bProd = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (bProd != null){
                IdProductoUbicacion=bProd.getIdProducto();
            }else{
                throw new Exception("Producto no existe");
            }

            txtCantidad.selectAll();
            txtCantidad.requestFocus();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processStockLP(){

        try {

            progress.setMessage("Validando ubicación");

           /* // stockResList = xobj.getresult(clsBeVW_stock_resList.class,"Get_Stock_By_Lic_Plate");

            if (escaneoPallet && stockResList == null) {
*//*                lblCProd.ForeColor = Color.Red
                cvProdID = 0 :DefCur()
                lblDescProducto.Text = "Código de LP no válido"*//*
                return;
            }else{ if (escaneoPallet && stockResList != null){

                // int Idx = stockResList.findIndex(Function(x)x.Stock.IdUbicacion = cvUbicOrigID)

                *//*if (Idx = -1){
                    MsgBox("El pallet no se encuentra en la ubicación: " & cvUbicOrigID, MsgBoxStyle.Exclamation, Text)
                    lblDescProducto.ForeColor = Color.Red
                    cvProdID = 0
                    DefCur()
                    lblDescProducto.setText ("LP N.E.E.U");
                    return;
                }else{
                    BeProductoUbicacion = stockResList.items.get(Idx);
                    BeStockPallet = stockResList.items.get(Idx).Stock;

                    cvLote = BeStockPallet.Lote
                    cvPresID = BeStockPallet.IdPresentacion
                    cvEstadoID = BeStockPallet.IdProductoEstado
                    cvVence = BeStockPallet.Fecha_Vence

                }*//*
            }else{

            }
            *//*Else

                    BeProductoUbicacion = frmInicio.m_proxy.Get_BeProducto_By_Codigo_For_HH(txtCProd.Text, gIdBodega)

            If BeProductoUbicacion Is Nothing Then
            lblCProd.ForeColor = Color.Red
            cvProdID = 0 : DefCur()
            lblCProd.Text = "Código no válido"
            Exit Sub
            End If

            End If*//*

                txtCantidad.selectAll();
                txtCantidad.requestFocus();*/

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProductoUbicLP(){

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

    private void processProductoUbic(){

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

                execws(1);

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

                execws(2);
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
    /*

    private boolean datosOk(){

        boolean continua=true;

        execws(11)
        If cvUbicOrigID = 0 Then
            MsgBox("Ubicación origen no válida", MsgBoxStyle.Exclamation, Text) : txtCOrig.Focus() : Return False
        End If

        If cvProdID = 0 Then
            MsgBox("Producto no válido", MsgBoxStyle.Exclamation, Text) : txtCProd.Focus() : Return False
        End If

        'If cvCantMax = 0 Or cvStockID = 0 Then
        If cvCantMax = 0 Then
            MsgBox("Cantidad disponible es 0, no se puede realizar el movimiento", MsgBoxStyle.Exclamation, Text) : txtCProd.Focus() : Return False
        End If

        If Modo = pModo.Cambio_Estado Then
            Try
                cvEstEst = cboEstEst.SelectedValue
            Catch ex As Exception
                cvEstEst = 0
            End Try
            If cvEstEst = 0 Then
                MsgBox("Estado destino incorrecto", MsgBoxStyle.Exclamation, Text) : cboEstEst.Focus() : Return False
            End If
        End If

        Try
            txtCCant.Text = FormatNumber(txtCCant.Text, 6)
            cvCant = txtCCant.Text
            If cvCant <= 0 Then Throw New Exception
        Catch ex As Exception
            MsgBox("Cantidad incorrecta", MsgBoxStyle.Exclamation, Text) : txtCCant.Focus() : Return False
        End Try

        Try

            If (cvCant - cvCantMax) >= 0.0001 Then
                MsgBox("Cantidad incorrecta", MsgBoxStyle.Exclamation, Text) : txtCCant.Focus() : Return False
            End If

        Catch ex As Exception
            MsgBox("Cantidad incorrecta", MsgBoxStyle.Exclamation, Text) : txtCCant.Focus() : Return False
        End Try

        Try

            '#CKFK 20181007 1156PM Agregué el IdBodega
            cUbicDest = frmInicio.m_proxy.Get_Ubicacion_By_Codigo_Barra_And_IdBodega(txtCDest.Text, gIdBodega)

            If cUbicDest Is Nothing Then Throw New Exception

            cvUbicDestID = cUbicDest.IdUbicacion

            If (cvUbicOrigID = cvUbicDestID) And (Modo = pModo.Cambio_Ubicacion) Then
                MsgBox("La ubicación de destino coincide con la de origen", MsgBoxStyle.Exclamation, Text) : txtCDest.Focus() : Return False
            End If

        Catch ex As Exception
            MsgBox("Ubicación destino incorrecta", MsgBoxStyle.Exclamation, Text) : txtCDest.Focus() : Return False
        End Try

        Return True

    End Function


     */

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
