package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.zbra.androidlinq.delegate.SelectorDouble;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Verificacion.frm_list_prod_reemplazo_verif.reemplazoCorrecto;

public class frm_verificacion_datos extends PBase {

    private frm_verificacion_datos.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtVenceVeri,txtCantVeri,txtPesoVeri, txtUmbasVeri, txtLoteVeri, txtCajas, txtUnidades, txtPreSol, txtPresRec, txtUnidadSol, txtUnidadRec;
    private TextView lblTituloForma,lblLicPlate2,lblVenceVeri,lblCantVeri,lblPesoVeri, lblUmbasVeri, lblLoteVeri, lblPresentacion,lblPresRec, lblPresSol, lblUnidadSol, lblUnidadRec;
    private Button btMarcarReemplazoVeri,btnConfirmarV,btnBack;
    private Spinner cmbPresVeri;
    private LinearLayout llFechaVence,llLote, llPresentacion, llCantidad, llPeso, llUMBas, llReemplazo;
    private RelativeLayout relDesglose, relConDesglose;

    private clsBeTrans_picking_ubicList BePickingUbicList = new clsBeTrans_picking_ubicList();

    public static clsBeProducto gBeProducto = new clsBeProducto();
    public static clsBeProducto_PresentacionList tmpBePresentacion = new clsBeProducto_PresentacionList();
    public static clsBeDetallePedidoAVerificar BePedidoDetVerif = new clsBeDetallePedidoAVerificar();
    public static clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();

    private int IdProductoBodega;
    private String Lp;

    public static double CantReemplazar=0;

    private ArrayList<String> PresList = new ArrayList<String>();

    //Datos del producto a verificar
    private String Codigo = "";
    private String Nombre = "";
    private String Expira = "";
    private String Lote = "";
    private double Sol = 0;
    private double Rec = 0;
    private double Ver = 0;
    private double factor = 0;
    private String UM = "";
    private double pPeso;
    private double pCantidad;

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
        btnConfirmarV = (Button) findViewById(R.id.btnConfirmarV);
        btnBack = (Button) findViewById(R.id.btnBack);
        cmbPresVeri = (Spinner) findViewById(R.id.cmbPresVeri);
        llFechaVence = (LinearLayout) findViewById(R.id.llFechaVence);
        llLote = (LinearLayout) findViewById(R.id.llLote);
        llPresentacion = (LinearLayout) findViewById(R.id.llPresentacion);
        llCantidad = (LinearLayout) findViewById(R.id.llCantidad);
        llPeso = (LinearLayout) findViewById(R.id.llPeso);
        llUMBas = (LinearLayout) findViewById(R.id.llUMBas);
        dpResult = (DatePicker) findViewById(R.id.datePicker2);
        imgDate = (ImageView)findViewById(R.id.imgDate3);
        txtCajas = (EditText) findViewById(R.id.txtCajas);
        txtUnidades = (EditText) findViewById(R.id.txtUnidades);
        lblPresentacion = (TextView) findViewById(R.id.lblPresentacion);
        relDesglose = findViewById(R.id.relDesglose);
        txtPreSol = findViewById(R.id.txtPresSol);
        txtUnidadSol = findViewById(R.id.txtUnidadSol);
        txtUnidadRec = findViewById(R.id.txtUnidadRec);
        txtPresRec = findViewById(R.id.txtPresRec);
        lblPresRec = findViewById(R.id.lblPresRec);
        lblPresSol = findViewById(R.id.lblPresSol);
        lblUnidadSol = findViewById(R.id.lblUnidadSol);
        lblUnidadRec = findViewById(R.id.lblUnidadRec);

        BePickingUbicList = gl.gBePickingUbicList;

        setCurrentDateOnView();

        setHandlers();

        ProgressDialog("Cargando forma...");

        Load();

    }

    private void Load() {

        try {

            BePedidoDetVerif = gl.gBePedidoDetVerif;
            gl.gBePedidoDetVerif = new clsBeDetallePedidoAVerificar();

            IdProductoBodega = BePedidoDetVerif.getIdProductoBodega();

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

                                if (!txtVenceVeri.getText().equals(BePedidoDetVerif.getFecha_Vence())){
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

                                    vDif = BePedidoDetVerif.Cantidad_Recibida - BePedidoDetVerif.Cantidad_Verificada + Double.valueOf(txtCantVeri.getText().toString());

                                    if (vDif < 0 && Math.abs(vDif) > 0.000000001) {
                                        msgbox("La cantidad recibida es mayor a la cantidad solicitada, no se puede ingresar esa cantidad");
                                        txtCantVeri.selectAll();
                                        txtCantVeri.requestFocus();
                                        showkeyb();
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

            lblTituloForma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Llama a método del WS Get_Producto_By_IdProductoBodega
                    execws(1);
                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void procesaCajasUnidades() {
        double cantidadPresentacion = 0;
        double CantVeri = 0;

        CantVeri = Double.valueOf(txtCantVeri.getText().toString());
        if (CantVeri > factor) {
            relDesglose.setVisibility(View.VISIBLE);
            app.readOnly(txtCajas,true);
            app.readOnly(txtUnidades,true);

            cantidadPresentacion = CantVeri / factor;
            double decimal = cantidadPresentacion % 1;
            double cajas = cantidadPresentacion - decimal;
            double unidades = decimal * factor;

            txtCajas.setText(String.valueOf(cajas));
            txtUnidades.setText(String.valueOf(mu.frmdec(unidades)));

        } else {
            relDesglose.setVisibility(View.GONE);
        }
    }

    public void Inicia_Tarea_Detalle(){

        double suma=0;
        List AuxList;

        try{

            Codigo = BePedidoDetVerif.getCodigo();
            Nombre = BePedidoDetVerif.getNombre_Producto();
            Expira = BePedidoDetVerif.getFecha_Vence();
            Lote = BePedidoDetVerif.getLote();
            Sol = BePedidoDetVerif.getCantidad_Solicitada();
            Rec = BePedidoDetVerif.getCantidad_Recibida();
            Ver = BePedidoDetVerif.getCantidad_Verificada();
            UM = BePedidoDetVerif.getNom_Unid_Med();

            int IdPedidoDet = BePedidoDetVerif.getIdPedidoDet();
            int IdPresentacion = BePedidoDetVerif.getIdPresentacion();

            Lp = BePedidoDetVerif.getLicPlate();

            lblTituloForma.setText(String.format("Prod: %s-%s Expira: %s Lote: %s",
                    Codigo, Nombre, Expira, Lote));

            if (gBeProducto == null){
                throw new Exception("El producto no está definido");
            }else{

                if (BePickingUbicList !=null){

                    AuxList = stream(BePickingUbicList.items)
                            .where (z -> z.CodigoProducto.equals(Codigo))
                            .where(z -> z.Lote.equals(Lote))
                            .where(z -> app.strFecha(z.Fecha_Vence).equals(Expira))
                            .toList();

                    pSubListPickingU.items = AuxList;
                }

            }

            txtVenceVeri.setText(Expira);
            txtCantVeri.setText(String.valueOf(Rec-Ver));

            //txtUmbasVeri.setText(UM);
            txtLoteVeri.setText(BePedidoDetVerif.getLote());

            int sel = PresList.indexOf(BePedidoDetVerif.getIdPresentacion()+ " - " +
                                       BePedidoDetVerif.getNom_Presentacion());

            //#CKFK20220326 Agregué esta validación para que si el producto no tiene presentación
            //no se muestre el combo de la presentación
            if (sel>-1){
                lblCantVeri.setText("Cantidad ("+BePedidoDetVerif.getNom_Presentacion()+"): ");
                //llPresentacion.setVisibility(View.VISIBLE);
                //cmbPresVeri.setSelection(sel);

                //#AT20220419 Se muestra Cajas y Unidades en cantidad solicitada y recibida
                if (BePedidoDetVerif.getIdPresentacion() > 0){
                    if (gBeProducto.Presentaciones!=null){
                        if (gBeProducto.Presentaciones.items!=null){


                            factor = gBeProducto.Presentaciones.items.get(0).Factor;

                            double cantidad_solicitada = Sol;

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

                            double CantidadRec = Ver;
                            double CantidadDecimalRec = CantidadRec % 1;
                            double CantidadPresentacionRec = 0;
                            CantidadPresentacionRec = CantidadRec - CantidadDecimalRec;
                            double CantidadUMBasRec = CantidadDecimalRec * factor;

                            if (Ver > 0) {

                                if (CantidadPresentacionRec > 0) {

                                    if ((CantidadRec % 1) > 0 || (CantidadRec >= factor)) { }

                                    if (CantidadUMBasRec == 0) {
                                        txtUnidadRec.setVisibility(View.GONE);
                                        lblUnidadRec.setVisibility(View.GONE);
                                    }

                                    lblPresRec.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");
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

                                lblPresRec.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");
                                txtPresRec.setText("0.00");
                                txtUnidadRec.setText("0.00");
                            }
                        }
                    }
                } else {


                }
            }else{
                lblCantVeri.setText("Cantidad ("+UM+"): ");
                factor = gBeProducto.Presentaciones.items.get(0).Factor;
                double CantSol = Sol;
                double CantRec = Ver;

                if (CantSol > factor) {
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

                    txtUnidadSol.setText(""+mu.frmdec(Sol));
                    txtUnidadRec.setText(""+mu.frmdec(Ver));
                }
                //llPresentacion.setVisibility(View.GONE);

                execws(4);
            }

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
            showkeyb();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void calculaCajaUnidades(double CantPresentacion, double CantidadUMBas) {

        try {

            lblPresSol.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");
            txtPreSol.setText(String.valueOf(CantPresentacion));
            txtUnidadSol.setText(String.valueOf(mu.frmdec(CantidadUMBas)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private boolean HicimosUnaFumada=false;

    private void calculaUnidades(double CantidadUMBas) {

        try {

            lblPresSol.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");
            txtPreSol.setText("0");
            txtUnidadSol.setText(String.valueOf(mu.frmdec(CantidadUMBas)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void Listar_Producto_Presentaciones() {

        String valor;

        try {

            if (gBeProducto.Presentaciones != null) {

                progress.setMessage("Listando presentaciones de producto...");

                if (gBeProducto.Presentaciones.items != null) {

                    factor = gBeProducto.Presentaciones.items.get(0).Factor;
                    lblPresentacion.setText(gBeProducto.Presentaciones.items.get(0).Nombre+": ");

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

                }else{
                    llPresentacion.setVisibility(View.GONE);
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

            pCantidad = Double.parseDouble(txtCantVeri.getText().toString());
            pPeso =  Double.parseDouble(txtPesoVeri.getText().toString());

            pSubListPickingU = new clsBeTrans_picking_ubicList();

            if (BePickingUbicList!=null){

                if (Lp.equals("")){
                    AuxList = stream(BePickingUbicList.items)
                            .where (z -> z.CodigoProducto.equals(BePedidoDetVerif.Codigo))
                            .where(z -> z.Lote.equals(BePedidoDetVerif.Lote))
                            .where(z -> app.strFecha(z.Fecha_Vence).equals(BePedidoDetVerif.Fecha_Vence))
                            .where(c -> c.getCantidad_Recibida()-c.getCantidad_Verificada()!=0)
                            .toList();
                }else{
                    AuxList = stream(BePickingUbicList.items)
                            .where (c -> c.CodigoProducto.equals(Codigo))
                            .where(c -> c.Lote.equals(Lote))
                            .where(c -> (app.strFecha(c.Fecha_Vence).equals(Expira)))
                            .where(c -> c.Lic_plate.equals(Lp))
                            .where(c -> c.getCantidad_Recibida()-c.getCantidad_Verificada()!=0)
                            .toList();

                }

                pSubListPickingU.items = AuxList;

            }

            //Llama al método del WS Actualiza_Cant_Peso_Verificacion
            if (pSubListPickingU.items.size()!=0){
                execws(3);
            }else{
                throw new Exception("#ERR_20220406: No se obtuvo la lista de verificación.");
            }

        }catch (Exception ex){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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
                        callMethod("Actualiza_Cant_Peso_Verificacion",
                                   "pBePickingUbicList",pSubListPickingU.items,
                                   "pIdOperador",gl.OperadorBodega.IdOperadorBodega,
                                   "pCantidad",pCantidad,
                                   "pPeso",pPeso);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",gBeProducto.getIdProducto(),"pActivo",true);
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
                    processActualizaCantPesoVerif();
                    break;
                case 4:
                    processGetPresentacion();
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
                llFechaVence.setVisibility(View.GONE);
                llLote.setVisibility(View.GONE);
            }else if (!gBeProducto.Control_lote){
                llLote.setVisibility(View.GONE);
            }else if (!gBeProducto.Control_vencimiento){
                llFechaVence.setVisibility(View.GONE);
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

                if (!gBeProducto.getControl_peso()) {
                    llPeso.setVisibility(View.GONE);
                }

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

    private void processActualizaCantPesoVerif(){

        try {

            progress.setMessage("Actualizando cantidad y peso de la verificación...");

            Boolean resultado = (Boolean)xobj.getSingle("Actualiza_Cant_Peso_VerificacionResult",Boolean.class);

            progress.cancel();

            frm_verificacion_datos.super.finish();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private void processGetPresentacion() {
        try {

            progress.setMessage("Actualizando cantidad y peso de la verificación...");

            tmpBePresentacion = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (tmpBePresentacion != null) {
                if (tmpBePresentacion.items != null) {
                    factor = tmpBePresentacion.items.get(0).Factor;
                    lblPresentacion.setText(tmpBePresentacion.items.get(0).Nombre+": ");

                    //procesaCajasUnidades();
                }
            }
            progress.cancel();

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
                    gl.gVerifCascade=false;
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
                   txtVenceVeri.setText(BePedidoDetVerif.getFecha_Vence());
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

    private void Reemplazar(){

        try {

            browse = 1;
            startActivity(new Intent(this, frm_danado_verificacion.class));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName() + " " + e.getMessage());
        }
    }

    private void msgAskReemplazar(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Reemplazar();
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

    public void MarcarReemplazo(View view){

        try {

            Double vDif;
            boolean permite = true;

            Double valor = Double.valueOf(txtCantVeri.getText().toString());
            vDif = BePedidoDetVerif.Cantidad_Recibida - (BePedidoDetVerif.Cantidad_Verificada + valor);

            if (valor == 0){
                mu.msgbox("Ingrese la cantidad de producto a reemplazar");
                permite = false;
            } else {
                if (vDif < 0) {
                    mu.msgbox("La cantidad ingresada es mayor a la recibida");
                    permite = false;
                }
            }

            if (!permite) {
                txtCantVeri.setSelectAllOnFocus(true);
                txtCantVeri.requestFocus();
                return;
            }

            CantReemplazar = valor;
            msgAskReemplazar("¿Marcar producto para reemplazo?");

        }catch (Exception e){
            mu.msgbox("BotonReemplazo:"+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir de las tareas de verificación");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    protected void onResume() {
        try{

            super.onResume();

            if (browse==1 && reemplazoCorrecto){
                browse=0;
                super.finish();
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
