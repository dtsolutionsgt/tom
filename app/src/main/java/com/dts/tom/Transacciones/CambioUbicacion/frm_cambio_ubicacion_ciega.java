package com.dts.tom.Transacciones.CambioUbicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodegaList;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacionList;
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

           progress.cancel();

       }catch (Exception ex){
           addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
           msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
       }
    }

    private void Load(){

        try{
           execws(3);
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

        txtCantidad.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

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
                    processTareaDetalleCambioUbicacion();break;
                case 2:
                    processMotivosUbiHH();break;
                case 3:

                case 4:


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
                    case 1:
                        callMethod("Get_All_By_IdTransUbicEnc_And_IdOperador","pIdTransUbicHhEnc",gl.IdTareaUbicEnc,
                                "pIdOperador",gl.IdOperador);
                        break;
                    case 2:
                        callMethod("Get_Motivos_Ubicacion_For_HH");
                        break;

                }

            } catch (Exception e) {
                error=e.getMessage();errorflag =true;msgbox(error);
            }
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
            //cvVence = "01/01/1900";

            // try{

            String vStarWithParameter = "$";

            //Comentario:
            //Se escaneó un código de pallet -> #EJC20180315
            //#CKFK 20190123 Se modificó la barra de pallet ahora va a comenzar con $ y no con (01)
            if (txtCodigoPrd.getText().toString().startsWith("$") &&
                    txtCodigoPrd.getText().toString().startsWith("(01)") &&
                    txtCodigoPrd.getText().toString().startsWith(vStarWithParameter)) {

                //Es una barra de pallet válida por tamaño
                int vLengthBarra = txtCodigoPrd.getText().toString().length();

                if (vLengthBarra >= 16) {

                    escaneoPallet = true;

                    pLP = txtCodigoPrd.getText().toString().replace("$", "");

                    //////#EJC20190328: Valida que la barra de pallet exista y que no esté recibida.
                    // ListBeStockPallet = frmInicio.m_proxy.Get_Stock_By_Lic_Plate(pLP, gIdBodega).ToList();

                }

            }else{
                escaneoPallet = false;
            }

        }catch (Exception ex){
            msgbox("Error " + ex.getMessage());
        }
    }

    private void processTareaDetalleCambioUbicacion(){

        try {

            progress.setMessage("Obteniendo detalle de la tareas de cambio de ubicación");
//
//            pBeTransUbicHhDetList = xobj.getresult(clsBeTrans_ubic_hh_detList.class,"Get_All_By_IdTransUbicEnc_And_IdOperador");
//
//            if(pBeTransUbicHhDetList!=null){
//                if(pBeTransUbicHhDetList.items!=null){
//                    Llena_Tarea_Detalle_Ubicacion();
//                }
//            }

            progress.cancel();

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
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

}
