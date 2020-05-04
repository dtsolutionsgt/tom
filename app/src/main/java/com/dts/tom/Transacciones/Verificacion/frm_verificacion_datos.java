package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.zbra.androidlinq.delegate.Selector;
import br.com.zbra.androidlinq.delegate.SelectorDouble;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_verificacion_datos extends PBase {

    private frm_verificacion_datos.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtVenceVeri,txtCantVeri,txtPesoVeri, txtUmbasVeri, txtLoteVeri;
    private TextView lblTituloForma,lblLicPlate2,lblVenceVeri,lblCantVeri,lblPesoVeri, lblUmbasVeri, lblLoteVeri;
    private Button btMarcarReemplazoVeri,btnConfirmarV,btnBack;
    private Spinner cmbPresVeri;

    private clsBeProducto gBeProducto = new clsBeProducto();
    private clsBeStock_res BeStockRes = new clsBeStock_res();
    private clsBeTrans_picking_ubic BePickingUbic = new clsBeTrans_picking_ubic();
    private clsBeTrans_picking_ubicList BePickingUbicList = new clsBeTrans_picking_ubicList();

    private int IdProductoBodega;
    private String Lp;

    private ArrayList<String> PresList = new ArrayList<String>();

    //Datos del producto a verificar
    private String Codigo = "";
    private String Nombre = "";
    private String Expira = "";
    private String Lote = "";
    private double Sol = 0;
    private double Rec = 0;
    private double Ver = 0;
    private String UM = "";

    // Fecha
    private int anno;
    private int mes;
    private int dia;
    static final int DATE_DIALOG_ID = 999;
    private DatePicker dpResult;
    private ImageView imgDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_verificacion_datos);

        super.InitBase();

        ws = new frm_verificacion_datos.WebServiceHandler(frm_verificacion_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtVenceVeri = (EditText) findViewById(R.id.txtVenceVeri);
        txtCantVeri = (EditText) findViewById(R.id.txtCantVeri);
        txtPesoVeri = (EditText) findViewById(R.id.txtPesoVeri);
        txtUmbasVeri = (EditText) findViewById(R.id.txtUmbasVeri);
        txtLoteVeri = (EditText) findViewById(R.id.txtLoteVeri);
        lblVenceVeri = (TextView) findViewById(R.id.lblVenceVeri);
        lblCantVeri = (TextView) findViewById(R.id.lblCantVeri);
        lblPesoVeri = (TextView) findViewById(R.id.lblPesoVeri);
        lblUmbasVeri = (TextView) findViewById(R.id.lblUmbasVeri);
        lblLoteVeri = (TextView) findViewById(R.id.lblLoteVeri);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblLicPlate2 = (TextView) findViewById(R.id.lblLicPlate2);
        btMarcarReemplazoVeri = (Button) findViewById(R.id.btMarcarReemplazoVeri);
        btnConfirmarV = (Button) findViewById(R.id.btnConfirmarV);
        btnBack = (Button) findViewById(R.id.btnBack);
        cmbPresVeri = (Spinner) findViewById(R.id.cmbPresVeri);

        dpResult = (DatePicker) findViewById(R.id.datePicker2);

        imgDate = (ImageView)findViewById(R.id.imgDate3);

        BePickingUbicList = gl.gBePickingUbicList;

        setCurrentDateOnView();

        setHandlers();

        ProgressDialog("Cargando forma...");

        Load();

    }

    private void Load() {

        try {

            IdProductoBodega = gl.gBePedidoDetVerif.getIdProductoBodega();

            //Llama a método del WS Get_Producto_By_IdProductoBodega
            execws(1);

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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

    private void setHandlers(){

        try{

            txtVenceVeri.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                if (!txtVenceVeri.getText().equals(gl.gBePedidoDetVerif.getFecha_Vence())){
                                    msgAskCambioFecha("Está seguro de modificar la fecha de vencimiento del producto");
                                }

                                return true;
                        }
                    }
                    return false;
                }
            });

            txtCantVeri.setOnKeyListener(new View.OnKeyListener(){

                double vDif = 0;

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                if (valida_Valor(txtCantVeri)){

                                    vDif = gl.gBePedidoDetVerif.Cantidad_Recibida - gl.gBePedidoDetVerif.Cantidad_Verificada + Double.valueOf(txtCantVeri.getText().toString());

                                    if (vDif < 0 && Math.abs(vDif) > 0.000000001) {
                                        msgbox("La cantidad recibida es mayor a la cantidad solicitada, no se puede ingresar esa cantidad");
                                        txtCantVeri.selectAll();
                                        txtCantVeri.requestFocus();
                                        //showkeyb();
                                    }else {
                                        Guardar_Verificacion();
                                    }
                                }

                                return true;
                        }
                    }
                    return false;
                }
            });

            txtPesoVeri.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                if (gBeProducto.getControl_peso()){
                                    valida_Valor(txtPesoVeri);
                                }

                                return true;
                        }
                    }
                    return false;
                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    public void Inicia_Tarea_Detalle(){

        double suma=0;
        clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();
        List AuxList;

        try{

            Codigo = gl.gBePedidoDetVerif.getCodigo();
            Nombre = gl.gBePedidoDetVerif.getNombre_Producto();
            Expira = gl.gBePedidoDetVerif.getFecha_Vence();
            Lote = gl.gBePedidoDetVerif.getLote();
            Sol = gl.gBePedidoDetVerif.getCantidad_Solicitada();
            Rec = gl.gBePedidoDetVerif.getCantidad_Recibida();
            Ver = gl.gBePedidoDetVerif.getCantidad_Verificada();
            UM = gl.gBePedidoDetVerif.getNom_Unid_Med();

            int IdPedidoDet = gl.gBePedidoDetVerif.getIdPedidoDet();
            int IdPresentacion = gl.gBePedidoDetVerif.getIdPresentacion();

            Lp = gl.gBePedidoDetVerif.getLicPlate();

            lblTituloForma.setText(String.format("Prod: %s-%s Expira: %s Lote: %s Sol: %s Pick: %s Veri: %s",
                    Codigo, Nombre, Expira, Lote,String.valueOf(Sol), String.valueOf(Rec), String.valueOf(Ver)));

            if (gBeProducto == null){
                throw new Exception("El producto no está definido");
            }else{

                AuxList = stream(BePickingUbicList.items)
                        .where (z -> z.CodigoProducto.equals(Codigo))
                        .where(z -> z.Lote.equals(Lote))
                        .where(z -> app.strFecha(z.Fecha_Vence).equals(Expira))
                        .toList();

                pSubListPickingU.items = AuxList;
            }

            txtVenceVeri.setText(Expira);
            txtCantVeri.setText(String.valueOf(Rec-Ver));

            txtUmbasVeri.setText(UM);
            txtLoteVeri.setText(gl.gBePedidoDetVerif.getLote());

            int sel = PresList.indexOf(gl.gBePedidoDetVerif.getIdPresentacion()+ " - " +
                                       gl.gBePedidoDetVerif.getNom_Presentacion());
            cmbPresVeri.setSelection(sel);

            if (Lp != ""){
                lblLicPlate2.setVisibility(View.VISIBLE);
                lblLicPlate2.setText(String.format("LP: %s", Lp));
            }

            txtPesoVeri.setText(mu.frmdecimal(0, gl.gCantDecDespliegue));

            if (pSubListPickingU !=null) {
                if (pSubListPickingU.items != null) {
                    suma = stream(pSubListPickingU.items)
                            .select(clsBeTrans_picking_ubic::getPeso_recibido)
                            .sum((SelectorDouble<Double>) z -> z);
                    txtPesoVeri.setText(mu.frmdecimal(suma, gl.gCantDecDespliegue));
                }
            }

            Bloquea_Controles();

            txtCantVeri.selectAll();
            txtCantVeri.requestFocus();
            //showkeyb();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void Listar_Producto_Presentaciones() {

        String valor;

        try {

            if (gBeProducto.Presentaciones != null) {

                progress.setMessage("Listando presentaciones de producto...");

                if (gBeProducto.Presentaciones.items != null) {

                    PresList.clear();

                    for (int i = 0; i < gBeProducto.Presentaciones.items.size(); i++) {

                        valor = gBeProducto.Presentaciones.items.get(i).getIdPresentacion() + " - " +
                                gBeProducto.Presentaciones.items.get(i).getNombre().toString();

                        if (PresList.indexOf(valor)==-1){
                            PresList.add(valor);
                        }
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresVeri.setAdapter(dataAdapter);

                    if (PresList.size() > 0) cmbPresVeri.setSelection(0);

                }

            }

            Inicia_Tarea_Detalle();

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Listar_Producto_Presentaciones:" + e.getMessage());
        }
    }

    private boolean valida_Valor(EditText txt){

        boolean result = false;

        try{

            double valor =Double.valueOf(txt.getText().toString());

            if(valor<=0) throw  new Exception("El valor es incorrecto, ingréselo nuevamente");

            result = true;

        }catch (Exception ex){
            result = false;
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName() + " " + ex.getMessage());
            txt.selectAll();
            txt.requestFocus();
            //showkeyb();
        }

        return result;

    }

    private boolean Guardar_Verificacion() {

        clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();
        double CantPendiente  = 0;
        double PesoPendiente  = 0;
        double Cantidad  = 0;
        double Peso  = 0;
        String resultado = "";
        boolean result = false;
        List AuxList;

        try{

            if (!valida_Valor(txtCantVeri)) {
                return result;
            }

            if (gBeProducto.getControl_peso()){
                if (!valida_Valor(txtPesoVeri)){
                    return result;
                }
            }

            Cantidad = Double.parseDouble(txtCantVeri.getText().toString());
            Peso =  Double.parseDouble(txtPesoVeri.getText().toString());

            if (Lp.equals("")){
                AuxList = stream(BePickingUbicList.items)
                        .where (z -> z.CodigoProducto.equals(gl.gBePedidoDetVerif.Codigo))
                        .where(z -> z.Lote.equals(gl.gBePedidoDetVerif.Lote))
                        .where(z -> app.strFecha(z.Fecha_Vence).equals(gl.gBePedidoDetVerif.Fecha_Vence))
                        .toList();
            }else{
                AuxList = stream(BePickingUbicList.items)
                        .where (c -> c.CodigoProducto.equals(Codigo))
                        .where(c -> c.Lote.equals(Lote))
                        .where(c -> (app.strFecha(c.Fecha_Vence).equals(Expira)))
                        .where(c -> c.Lic_plate.equals(Lp))
                        .toList();

            }

            pSubListPickingU.items = AuxList;

            for (clsBeTrans_picking_ubic vBePickingUbic:pSubListPickingU.items ) {

                if ((vBePickingUbic.Cantidad_Verificada + Cantidad) > vBePickingUbic.Cantidad_Recibida) {
                    CantPendiente = vBePickingUbic.Cantidad_Recibida - vBePickingUbic.Cantidad_Verificada;
                } else {
                    CantPendiente = Cantidad;
                }

                if ((vBePickingUbic.Peso_verificado + Peso) > vBePickingUbic.Peso_recibido) {
                    PesoPendiente = vBePickingUbic.Peso_recibido - vBePickingUbic.Peso_verificado;
                } else {
                    PesoPendiente = Peso;
                }

                vBePickingUbic.Cantidad_Verificada += CantPendiente;
                vBePickingUbic.Peso_verificado += PesoPendiente;
                vBePickingUbic.IdOperadorBodega_Verifico = gl.IdOperador;
                vBePickingUbic.Fecha_verificado = du.getFechaActual();

                BeStockRes.IdStockRes = vBePickingUbic.IdStockRes;
                BePickingUbic = vBePickingUbic;

                //Llama al método del WS Get_Single_StockRes
                final Handler cbhandler = new Handler();
                cbhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        execws(3);
                    }
                }, 1000);

                if (Cantidad - CantPendiente == 0){
                    break;
                }else {
                    Cantidad -= CantPendiente;
                }

            }

            result = true;

        }catch (Exception ex){
            result = false;
        }

        return  result;
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Producto_By_IdProductoBodega",
                                "IdProductoBodega",IdProductoBodega);
                        break;
                    case 2:
                        callMethod("Get_All_Presentaciones_By_IdProducto",
                                   "pIdProducto",gBeProducto.IdProducto,
                                   "pActivo",true);
                        break;
                    case 3:
                        callMethod("Get_Single_StockRes",
                                   "pBeStock_res", BeStockRes);
                        break;
                    case 4:
                        callMethod("Actualizar_PickingUbic_Por_Verificacion",
                                "oBeTrans_picking_ubic",BePickingUbic,
                                "BeStockRes", BeStockRes);
                        break;
                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                error=e.getMessage();errorflag =true;
                msgbox(error);
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
                    processPresentacionesProducto();
                    break;
                case 3:
                    processStockRes();
                    break;
                case 4:
                    processActualizarPicking();
                    break;

            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Bloquea_Controles() {

        try {

            if (!gBeProducto.Control_lote && !gBeProducto.Control_vencimiento){
                lblVenceVeri.setVisibility(View.GONE);
                txtVenceVeri.setVisibility(View.GONE);
                lblLoteVeri.setVisibility(View.GONE);
                txtLoteVeri.setVisibility(View.GONE);
            }else if (!gBeProducto.Control_lote){
                lblLoteVeri.setVisibility(View.GONE);
                txtLoteVeri.setVisibility(View.GONE);
            }else if (!gBeProducto.Control_vencimiento){
                txtVenceVeri.setVisibility(View.GONE);
                lblVenceVeri.setVisibility(View.GONE);
            }else{
                txtVenceVeri.setVisibility(View.VISIBLE);
                lblVenceVeri.setVisibility(View.VISIBLE);
                lblLoteVeri.setVisibility(View.VISIBLE);
                txtLoteVeri.setVisibility(View.VISIBLE);
            }

            app.readOnly(txtVenceVeri, true);
            cmbPresVeri.setEnabled(false);
            cmbPresVeri.setBackgroundColor(Color.WHITE);
            app.readOnly(txtLoteVeri,true);
            app.readOnly(txtUmbasVeri,true);
            app.readOnly(txtCantVeri,false);
            app.readOnly(txtPesoVeri,true);

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Bloquea_Controles:" + e.getMessage());
        }
    }

    private void processProducto(){

        try {

            progress.setMessage("Cargando datos del producto");
            progress.show();

            gBeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            if (gBeProducto == null){
                throw new Exception("El producto no está definido");
            }else{

                gBeProducto.IdProductoBodega = IdProductoBodega;

                //Llama al método del WS Get_All_Presentaciones_By_IdProducto
                execws(2);
            }
        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processPresentacionesProducto(){

        try {

            progress.setMessage("Obteniendo presentaciones de producto");

            gBeProducto.Presentaciones = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            Listar_Producto_Presentaciones();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private void processStockRes(){

        try {

            progress.setMessage("Obteniendo el Stock Reservado");
            progress.show();

            Boolean resultado = (Boolean) xobj.getSingle("Get_Single_StockResResult",Boolean.class);
            BeStockRes =  xobj.get(clsBeStock_res.class,"pBeStock_res");

            BeStockRes.Estado = "VERIFICADO";
            BeStockRes.User_mod = String.valueOf(gl.IdOperador);
            BeStockRes.Fec_mod = du.getFechaActual();

            if (resultado){
                //Llama al método del WS Actualizar_PickingUbic_Por_Verificacion
                execws(4);
            }


        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private void processActualizarPicking(){

        try {

            progress.setMessage("Actualizando picking por verificación...");

            String resultado = (String)xobj.getSingle("Actualizar_PickingUbic_Por_VerificacionResult",String.class);

            progress.cancel();

            frm_verificacion_datos.super.finish();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_verificacion_datos.super.finish();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskCambioFecha(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    txtCantVeri.requestFocus();
                    txtCantVeri.selectAll();
                    //showkeyb();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                   txtVenceVeri.setText(gl.gBePedidoDetVerif.getFecha_Vence());
                    txtCantVeri.requestFocus();
                    txtCantVeri.selectAll();
                    //showkeyb();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }


    public void ChangeDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        anno, mes,dia);
        }
        return null;
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        anno = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH)+1;
        dia = c.get(Calendar.DAY_OF_MONTH);

        txtVenceVeri.setText(new StringBuilder()
                .append(padding_str(dia))
                .append("/").append(padding_str(mes))
                .append("/").append(anno)
                .append(" "));

        dpResult.init(anno, mes, dia, null);

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            anno = selectedYear;
            mes = selectedMonth+1;
            dia = selectedDay;

            txtVenceVeri.setText(new StringBuilder()
                    .append(padding_str(dia))
                    .append("/").append(padding_str(mes))
                    .append("/").append(anno)
                    .append(" "));

            dpResult.init(anno, mes, dia, null);

        }
    };

    private static String padding_str(int c) {

        if (c >= 10)
            return String.valueOf(c);

        else

            return "0" + String.valueOf(c);
    }

    public void Actualizar(View view){
        Guardar_Verificacion();
    }

    public void Regresar(View view){
        msgAskExit("Está seguro de salir");
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir de las tareas de verificación");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    protected void onResume() {
        try{
            super.onResume();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
