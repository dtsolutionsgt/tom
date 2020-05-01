package com.dts.tom.Transacciones.Picking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.DecimalDigitsInputFilter;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_det;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

import br.com.zbra.androidlinq.Linq;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.TipoLista;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.gBePicking;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.plistPickingUbi;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.selitem;

public class frm_picking_datos extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    public static clsBeTrans_picking_ubic gBePickingUbic;
    public static clsBeProducto gBeProducto = new clsBeProducto();
    private clsBeProducto_estadoList LProductoEstadoIngreso = new clsBeProducto_estadoList();
    private clsBeProductoList ListBeStockPalletEscaneado = new clsBeProductoList();
    private clsBeProducto BeStockPallet = new clsBeProducto();
    private clsBeTrans_picking_det BePickingDet = new clsBeTrans_picking_det();
    private clsBeStock_res BeStockRes = new clsBeStock_res();
    private ArrayList<String> EstadoList = new ArrayList<String>();
    private ArrayList<String> PresList = new ArrayList<String>();
    private clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();

    private ProgressDialog progress;
    private TextView lblTituloForma, lblLicPlate;
    private Button btnFechaVence, btnDanado, btNE,btnConfirmarPk;
    private EditText txtBarra, txtFechaCad, txtLote, txtUniBas, txtCantidadPick, txtPesoPick;
    private Spinner cmbPresentacion, cmbEstado;

    private boolean Escaneo_Pallet = false;
    private String pLP = "";
    private int gIdUbicacion=0;
    public static double CantReemplazar=0;
    public static boolean ReemplazoLP=false;
    public static int Tipo=0;

    private int DifDias = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_picking_datos);

        super.InitBase();

        ws = new WebServiceHandler(frm_picking_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtBarra = (EditText) findViewById(R.id.txtBarra);
        txtFechaCad = (EditText) findViewById(R.id.txtFechaCad);
        txtLote = (EditText) findViewById(R.id.txtLote);
        txtUniBas = (EditText) findViewById(R.id.txtUniBas);
        txtCantidadPick = (EditText) findViewById(R.id.txtCantidadPick);
        txtPesoPick = (EditText) findViewById(R.id.txtPesoPick);

        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblLicPlate = (TextView) findViewById(R.id.lblLicPlate);

        btnFechaVence = (Button) findViewById(R.id.btnFechaVence);
        btnConfirmarPk = (Button) findViewById(R.id.btnConfirmarPk);

        cmbPresentacion = (Spinner) findViewById(R.id.cmbPresentacion);
        cmbEstado = (Spinner) findViewById(R.id.cmbEstado);

        ProgressDialog("Cargando datos de producto picking");

        if (selitem != null) {
            gBePickingUbic = selitem;
        }

        setHandlers();
        Load();

    }

    private void setHandlers() {

        try {

            txtBarra.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        Procesa_Barra();
                    }

                    return false;
                }
            });

            txtCantidadPick.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        Procesar_Registro();
                    }

                    return false;
                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandlers:" + e.getMessage());
        }
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void Load() {

        try {

            if (!gBePickingUbic.Lic_plate.isEmpty()) {
                lblLicPlate.setText(gBePickingUbic.Lic_plate);
            } else {
                lblLicPlate.setText("");
                lblLicPlate.setVisibility(View.GONE);
            }

            DifDias = du.DateDiff(gBePickingUbic.Fecha_Vence);

            lblTituloForma.setText("Prod: " + gBePickingUbic.CodigoProducto + "-" + gBePickingUbic.NombreProducto
                    + " Expira: " + gBePickingUbic.Fecha_Vence + "Lote: " + gBePickingUbic.Lote
                    + " Sol: " + gBePickingUbic.Cantidad_Solicitada + " Rec: " + gBePickingUbic.Cantidad_Recibida + " "
                    + gBePickingUbic.ProductoUnidadMedida);


            txtCantidadPick.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtCantidadPick.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});
            txtPesoPick.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoPick.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(gl.gCantDecDespliegue)});

            gBeProducto = new clsBeProducto();

            execws(1);

        } catch (Exception e) {
            mu.msgbox("Load:" + e.getMessage());
        }
    }

    private void Listar_Producto_Estado() {

        try {

            progress.setMessage("Listando estados de producto");

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

    private void Bloquea_Controles() {

        try {

            txtBarra.setSelectAllOnFocus(true);
            txtBarra.requestFocus();

            txtLote.setFocusable(false);
            txtLote.setFocusableInTouchMode(false);
            txtLote.setClickable(false);

            cmbPresentacion.setFocusable(false);
            cmbPresentacion.setFocusableInTouchMode(false);
            cmbPresentacion.setClickable(false);

            txtUniBas.setFocusable(false);
            txtUniBas.setFocusableInTouchMode(false);
            txtUniBas.setClickable(false);

            cmbEstado.setFocusable(false);
            cmbEstado.setFocusableInTouchMode(false);
            cmbEstado.setClickable(false);

            txtPesoPick.setFocusable(false);
            txtPesoPick.setFocusableInTouchMode(false);
            txtPesoPick.setClickable(false);

            txtFechaCad.setFocusable(false);
            txtFechaCad.setFocusableInTouchMode(false);
            txtFechaCad.setClickable(false);

            Limpia_controles();

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Bloquea_Controles:" + e.getMessage());
        }
    }

    private void Limpia_controles() {
        txtBarra.setText("");
        txtLote.setText("");
        cmbPresentacion.setSelection(-1);
        txtUniBas.setText("");
        txtCantidadPick.setText("0.00");
        cmbEstado.setSelection(-1);
        txtPesoPick.setText("0.00");
    }

    private void Set_dias_Vence() {

        try {

            progress.setMessage("Bloqueando controles");

            txtFechaCad.setText("");

            btnFechaVence.setText("Vence en: " + DifDias + " días");

            Bloquea_Controles();

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("Llena_datos_faltantes:" + e.getMessage());
        }
    }

    private void Procesa_Barra() {

        try {

            if (!txtBarra.getText().toString().isEmpty()) {

                String vStarWithParameter = "$";

                if (txtBarra.getText().toString().startsWith("$") |
                        txtBarra.getText().toString().startsWith("(01)") |
                        txtBarra.getText().toString().startsWith(vStarWithParameter)) {

                    int vLengthBarra = txtBarra.getText().toString().length();

                    if (vLengthBarra >= 16) {

                        Escaneo_Pallet = true;

                        pLP = txtBarra.getText().toString().replace("$", "");

                        Continua_procesando_barra();

                    }


                } else {
                    Escaneo_Pallet = false;
                    Continua_procesando_barra();
                }

            }


        } catch (Exception e) {
            mu.msgbox("Procesa_Barra:" + e.getMessage());
        }
    }

    private void Continua_procesando_barra(){


        boolean vPalletValido = false;
        boolean vMarcarComoNoEncontrado =false;

        try{

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
                                                        txtBarra.setSelectAllOnFocus(true);
                                                        txtBarra.requestFocus();
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                    txtBarra.setSelectAllOnFocus(true);
                                                    txtBarra.requestFocus();
                                                    return;
                                                }

                                            }else{
                                                mu.msgbox("El lote escaneado de pallet: "+BeStockPallet.Stock.Lote+" no coincide con el lote solicitado: "+gBePickingUbic.Lote);
                                                txtBarra.setSelectAllOnFocus(true);
                                                txtBarra.requestFocus();
                                                return;
                                            }

                                        }else{
                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtBarra.setSelectAllOnFocus(true);
                                            txtBarra.requestFocus();
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
                                                        Cargar_Datos_Producto_Picking_Consolidado();
                                                        return;
                                                    }else{
                                                        mu.msgbox("El pallet escaneado : "+gBePickingUbic.Lic_plate+" pertenece al picking pero está asociado a la ubicación: "+ gBePickingUbic.IdUbicacion+" y la ubicación actual es: "+gIdUbicacion);
                                                        txtBarra.setSelectAllOnFocus(true);
                                                        txtBarra.requestFocus();
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado : "+pLP+" ya fue procesado para este picking.");
                                                    txtBarra.setSelectAllOnFocus(true);
                                                    txtBarra.requestFocus();
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
                                                                txtBarra.setSelectAllOnFocus(true);
                                                                txtBarra.requestFocus();
                                                                txtBarra.setText("");
                                                                return;
                                                            }

                                                        }else{
                                                            mu.msgbox("El pallet escaneado no contiene la cantidad solicitada: "+gBePickingUbic.Cantidad_Solicitada+
                                                                    "\nCant. Disp: "+vCantDispLP);
                                                            txtBarra.setSelectAllOnFocus(true);
                                                            txtBarra.requestFocus();
                                                            return;
                                                        }
                                                    }else{
                                                        mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                        txtBarra.setSelectAllOnFocus(true);
                                                        txtBarra.requestFocus();
                                                        return;
                                                    }
                                                }else{
                                                    mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                                    txtBarra.setSelectAllOnFocus(true);
                                                    txtBarra.requestFocus();
                                                    txtBarra.setText("");
                                                    return;
                                                }

                                            }

                                        }else{

                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtBarra.setSelectAllOnFocus(true);
                                            txtBarra.requestFocus();
                                            txtBarra.setText("");
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
                        txtBarra.setSelectAllOnFocus(true);
                        txtBarra.requestFocus();
                        txtBarra.setText("");
                        return;
                    }

                }

                if (Escaneo_Pallet && vPalletValido){
                    Cargar_Datos_Producto_Picking_Consolidado();

                }else if ((!Escaneo_Pallet) && (!gBePickingUbic.Lic_plate.isEmpty()) &&
                        (!gBePickingUbic.Lic_plate.equals("0")) && (gBePickingUbic.CodigoProducto.equals(txtBarra.getText().toString()))){

                    msgContinuarPorCodigo("Se requiere licencia de pallet para este producto y se ha ingresado el código, ¿está seguro de verificar por código de producto?");
                    return;

                }else{

                    gBeProducto = new clsBeProducto();
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
                                                        txtBarra.setSelectAllOnFocus(true);
                                                        txtBarra.requestFocus();
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                    txtBarra.setSelectAllOnFocus(true);
                                                    txtBarra.requestFocus();
                                                    return;
                                                }

                                            }else{
                                                mu.msgbox("El lote escaneado de pallet: "+BeStockPallet.Stock.Lote+" no coincide con el lote solicitado: "+gBePickingUbic.Lote);
                                                txtBarra.setSelectAllOnFocus(true);
                                                txtBarra.requestFocus();
                                                return;
                                            }

                                        }else{
                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtBarra.setSelectAllOnFocus(true);
                                            txtBarra.requestFocus();
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
                                                        Cargar_Datos_Producto_Picking();
                                                        return;
                                                    }else{
                                                        mu.msgbox("El pallet escaneado : "+gBePickingUbic.Lic_plate+" pertenece al picking pero está asociado a la ubicación: "+ gBePickingUbic.IdUbicacion+" y la ubicación actual es: "+gIdUbicacion);
                                                        txtBarra.setSelectAllOnFocus(true);
                                                        txtBarra.requestFocus();
                                                        return;
                                                    }

                                                }else{
                                                    mu.msgbox("El pallet escaneado : "+pLP+" ya fue procesado para este picking.");
                                                    txtBarra.setSelectAllOnFocus(true);
                                                    txtBarra.requestFocus();
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
                                                                txtBarra.setSelectAllOnFocus(true);
                                                                txtBarra.requestFocus();
                                                                txtBarra.setText("");
                                                                return;
                                                            }

                                                        }else{
                                                            mu.msgbox("El pallet escaneado no contiene la cantidad solicitada: "+gBePickingUbic.Cantidad_Solicitada+
                                                                    "\nCant. Disp: "+vCantDispLP);
                                                            txtBarra.setSelectAllOnFocus(true);
                                                            txtBarra.requestFocus();
                                                            return;
                                                        }
                                                    }else{
                                                        mu.msgbox("El pallet escaneado no está disponible o ya fue reservado en un pedido.");
                                                        txtBarra.setSelectAllOnFocus(true);
                                                        txtBarra.requestFocus();
                                                        return;
                                                    }
                                                }else{
                                                    mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                                    txtBarra.setSelectAllOnFocus(true);
                                                    txtBarra.requestFocus();
                                                    txtBarra.setText("");
                                                    return;
                                                }

                                            }

                                        }else{

                                            mu.msgbox("El código escaneado de pallet: "+pLP+" no coincide con el pallet de esta ubicación: "+gBePickingUbic.Lic_plate);
                                            txtBarra.setSelectAllOnFocus(true);
                                            txtBarra.requestFocus();
                                            txtBarra.setText("");
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
                        txtBarra.setSelectAllOnFocus(true);
                        txtBarra.requestFocus();
                        txtBarra.setText("");
                        return;
                    }

                }

                if (Escaneo_Pallet && vPalletValido){
                    Cargar_Datos_Producto_Picking();

                }else if ((!Escaneo_Pallet) && (!gBePickingUbic.Lic_plate.isEmpty()) &&
                        (!gBePickingUbic.Lic_plate.equals("0")) && (gBePickingUbic.CodigoProducto.equals(txtBarra.getText().toString()))){

                    msgContinuarPorCodigo("Se requiere licencia de pallet para este producto y se ha ingresado el código, ¿está seguro de verificar por código de producto?");
                    return;

                }else{

                    gBeProducto = new clsBeProducto();
                    execws(4);
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

            if (TipoLista==1){
                Cargar_Datos_Producto_Picking_Consolidado();
            }else{
                Cargar_Datos_Producto_Picking();
            }

        }catch (Exception e){
            mu.msgbox("Continua_Con_Lp_Distinto"+e.getMessage());
        }
    }

    private void Cargar_Datos_Producto_Picking(){

        double CantARec = 0;

        try{

            CantARec = gBePickingUbic.Cantidad_Solicitada - gBePickingUbic.Cantidad_Recibida;
            txtFechaCad.setText(gBePickingUbic.Fecha_Vence);
            txtLote.setText(gBePickingUbic.Lote);

            if (gBePickingUbic.IdPresentacion>0){
                List Aux = stream(gBeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                int inx= Aux.indexOf(gBePickingUbic.IdPresentacion);
                cmbPresentacion.setSelection(inx);
            }

            txtUniBas.setText(gBePickingUbic.ProductoUnidadMedida);
            if (CantARec<=0){
                txtCantidadPick.setText(""+0);
            }else{
                txtCantidadPick.setText(""+mu.frmdecimal(CantARec,gl.gCantDecDespliegue));
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

            txtCantidadPick.selectAll();
            txtCantidadPick.setSelectAllOnFocus(true);
            txtCantidadPick.requestFocus();
            btnConfirmarPk.setEnabled(true);


        }catch (Exception e){
            mu.msgbox("Cargar_Datos_Producto_Picking:"+e.getMessage());
        }

    }

    private void Cargar_Datos_Producto_Picking_Consolidado(){

        double CantARec=0;
        double CantSol=0;
        double CantRec=0;

        try{

            CantSol = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Solicitada);
            CantRec = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Recibida);

            CantARec = CantSol - CantRec;
            txtFechaCad.setText(gBePickingUbic.Fecha_Vence);
            txtLote.setText(gBePickingUbic.Lote);

            if (gBePickingUbic.IdPresentacion>0){
                List Aux = stream(gBeProducto.Presentaciones.items).select(c->c.IdPresentacion).toList();
                int inx= Aux.indexOf(gBePickingUbic.IdPresentacion);
                cmbPresentacion.setSelection(inx);
            }

            txtUniBas.setText(gBePickingUbic.ProductoUnidadMedida);
            if (CantARec<=0){
                txtCantidadPick.setText(""+0);
            }else{
                txtCantidadPick.setText(""+mu.frmdecimal(CantARec,gl.gCantDecDespliegue));
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

            txtCantidadPick.selectAll();
            txtCantidadPick.setSelectAllOnFocus(true);
            txtCantidadPick.requestFocus();
            btnConfirmarPk.setEnabled(true);

        }catch (Exception e){
            mu.msgbox("Cargar_Datos_Producto_Picking_Consolidado:"+e.getMessage());
        }
    }

    public void BotonGuardar(View view){
        Procesar_Registro();
    }

    private void Procesar_Registro(){

        try{

            if (!txtCantidadPick.getText().toString().isEmpty()||!txtCantidadPick.getText().equals("0")){

                if (TipoLista==2){

                    Double vDif = gBePickingUbic.Cantidad_Solicitada - Double.parseDouble(txtCantidadPick.getText().toString()) + gBePickingUbic.Cantidad_Recibida;

                    if (vDif<0){
                        mu.msgbox("La cantidad es mayor a la solicitada");
                        txtCantidadPick.selectAll();
                        txtCantidadPick.setSelectAllOnFocus(true);
                        txtCantidadPick.requestFocus();
                        return;
                    }else{
                        Guardar_Picking();
                    }

                }else{

                    double vCantidadRec = 0;
                    double vCantidadSol = 0;

                    vCantidadRec = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Recibida);
                    vCantidadSol = stream(plistPickingUbi.items).sum(clsBeTrans_picking_ubic::getCantidad_Solicitada);

                    if (Double.parseDouble(txtCantidadPick.getText().toString())+vCantidadRec>vCantidadSol){
                        mu.msgbox("La cantidad es mayor a la solicitada");
                        txtCantidadPick.selectAll();
                        txtCantidadPick.setSelectAllOnFocus(true);
                        txtCantidadPick.requestFocus();
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

        try{

            if (TipoLista==2){

                double vDif = gBePickingUbic.Cantidad_Solicitada - Double.parseDouble(txtCantidadPick.getText().toString()) + gBePickingUbic.Cantidad_Recibida;

                if (vDif<0){
                    mu.msgbox("La cantidad recibida es mayor a la cantidad solicitada, no se puede ingresar esa cantidad");
                    return;
                }

                gBePickingUbic.Cantidad_Recibida +=Double.parseDouble(txtCantidadPick.getText().toString());

                if (gBePicking.verifica_auto){
                    gBePickingUbic.Cantidad_Verificada = gBePickingUbic.Cantidad_Recibida;
                }

                gBePickingUbic.Peso_recibido += Double.parseDouble(txtPesoPick.getText().toString());
                gBePickingUbic.Acepto = true;
                gBePickingUbic.Encontrado=true;
                gBePickingUbic.IdOperadorBodega_Pickeo = gl.OperadorBodega.IdOperador;
                gBePickingUbic.Fecha_Vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
                gBePickingUbic.Fec_mod = du.getFechaActual();

                BePickingDet.IdPickingDet = gBePickingUbic.IdPickingDet;
                execws(5);

            }else{

                Cantidad = Double.parseDouble(txtCantidadPick.getText().toString());

                pSubListPickingU = new clsBeTrans_picking_ubicList();

                pSubListPickingU.items = stream(plistPickingUbi.items).where(c->c.IdUbicacion==gBePickingUbic.IdUbicacion).toList();

                for (clsBeTrans_picking_ubic vBePickingUbic: pSubListPickingU.items){

                    if ((vBePickingUbic.Cantidad_Recibida+Cantidad)>vBePickingUbic.Cantidad_Solicitada){

                        CantPendiente = vBePickingUbic.Cantidad_Solicitada-vBePickingUbic.Cantidad_Recibida;

                    }else {

                        CantPendiente = Cantidad;

                    }

                    vBePickingUbic.Cantidad_Recibida +=CantPendiente;
                    vBePickingUbic.Acepto = true;
                    vBePickingUbic.Encontrado = true;
                    vBePickingUbic.IdOperadorBodega_Pickeo = gl.OperadorBodega.IdOperador;
                    vBePickingUbic.Fecha_Vence = du.convierteFecha(gBePickingUbic.Fecha_Vence);
                    vBePickingUbic.Fec_mod = du.getFechaActual();

                    BePickingDet.IdPickingDet = vBePickingUbic.IdPickingDet;
                    execws(5);

                    if ((Cantidad-CantPendiente)==0){
                        break;
                    }else{
                        Cantidad-=CantPendiente;
                    }
                }

                doExit();

            }

        }catch (Exception e){
            mu.msgbox("Guardar_Picking:"+e.getMessage());
        }
    }

    public void BotonReemplazo(View view){

        try {

            if (txtBarra.getText().toString().isEmpty()){
                mu.msgbox("Ingrese código del producto");
                txtBarra.setSelectAllOnFocus(true);
                txtBarra.requestFocus();
                return;
            }

            if (txtCantidadPick.getText().toString().equals("0")||txtCantidadPick.getText().toString().isEmpty()||txtCantidadPick.getText().toString().equals("")){
                mu.msgbox("Ingrese la cantidad de producto a reemplazar");
                txtCantidadPick.setSelectAllOnFocus(true);
                txtCantidadPick.requestFocus();
                return;
            }

            CantReemplazar = Double.parseDouble(txtCantidadPick.getText().toString());

            msgReemplazo("¿Marcar producto para reemplazo?");

        }catch (Exception e){
            mu.msgbox("BotonReemplazo:"+e.getMessage());
        }
    }

    private void Continua_reemplazo(){

        try{

            Tipo = 1;

            browse=1;
            startActivity(new Intent(this, frm_danado_picking.class));

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
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodega","pIdPropietario",gBeProducto.Propietario.IdPropietario,"pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",gBeProducto.IdProducto,"pActivo",true);
                        break;
                    case 4:
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtBarra.getText().toString(),"IdBodega",gl.IdBodega);
                        break;
                    case 5:
                        callMethod("ObtenerPickingDet","oBeTrans_picking_det",BePickingDet);
                        break;
                    case 6:
                        callMethod("Get_Single_StockRes","pBeStock_res",BeStockRes);
                        break;
                    case 7:
                        callMethod("Actualizar_Picking","oBeTrans_picking_ubic",gBePickingUbic,"BeStockRes",BeStockRes,
                                "oBeTrans_picking_det",BePickingDet,"IdBodega",gl.IdBodega);
                        break;
                    case 8:
                        callMethod("Actualizar_Picking_Con_Reemplazo_De_Pallet","oBeTrans_picking_ubic",gBePickingUbic,
                                "BeStockRes",BeStockRes,"oBeTrans_picking_det",BePickingDet,"IdBodega",gl.IdBodega,"pBeStockPalletReemplazo",BeStockPallet.Stock);
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
                    if (TipoLista==2){
                        doExit();
                    }
                    break;
                case 8:
                    if (TipoLista==2){
                        doExit();
                    }
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processProducto(){

        try{

            progress.setMessage("Cargando datos de producto");

            gBeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            if (gBeProducto!=null){

                gBeProducto.IdProductoBodega = gBePickingUbic.IdProductoBodega;

                execws(2);

            }else{
                progress.cancel();
                mu.msgbox("El producto no está definido");
                super.finish();
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processProducto:"+e.getMessage());
        }
    }

    private void processEstadoProducto(){

        try{

            progress.setMessage("Obteniendo estados de producto");

            LProductoEstadoIngreso = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodega");

            if (gBeProducto.Presentaciones!=null){
                if (gBeProducto.Presentaciones.items!=null){
                    Listar_Producto_Estado();
                    Listar_Producto_Presentaciones();
                    Set_dias_Vence();
                }else{
                    execws(3);
                }
            }else{
                execws(3);
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processEstadoProducto:"+e.getMessage());
        }
    }

    private void processPresentacionesProducto(){

        try {

            progress.setMessage("Obteniendo presentaciones de producto");

            gBeProducto.Presentaciones = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            Listar_Producto_Estado();
            Listar_Producto_Presentaciones();
            Set_dias_Vence();

        }catch (Exception e){
            mu.msgbox("processPresentacionesProducto:"+e.getMessage());
        }
    }

    private void processProductoForHH(){

        try {

            gBeProducto = xobj.getresult(clsBeProducto.class, "Get_BeProducto_By_Codigo_For_HH");

            if (gBeProducto!=null){

                if (gBeProducto.Codigo.equals(gBePickingUbic.CodigoProducto)){
                    if (TipoLista==1){
                        Cargar_Datos_Producto_Picking_Consolidado();
                    }else{
                        Cargar_Datos_Producto_Picking();
                    }
                }else{
                    mu.msgbox("El código ingresado no es el válido para la línea de picking");
                    btnConfirmarPk.setEnabled(false);
                    txtBarra.setSelectAllOnFocus(true);
                    txtBarra.requestFocus();
                }

            }else{

                mu.msgbox("El código ingresado no es el válido para la línea de picking");
                btnConfirmarPk.setEnabled(false);
                txtBarra.setSelectAllOnFocus(true);
                txtBarra.requestFocus();
            }

        }catch (Exception e){
            mu.msgbox("processProductoForHH:"+e.getMessage());
        }
    }

    private void processBePickingDet(){

        try{

            BePickingDet = xobj.getresultSingle(clsBeTrans_picking_det.class,"oBeTrans_picking_det");
            BePickingDet.Cantidad_recibida+=Double.parseDouble(txtCantidadPick.getText().toString());
            BePickingDet.User_mod = du.getFechaActual();

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

}
