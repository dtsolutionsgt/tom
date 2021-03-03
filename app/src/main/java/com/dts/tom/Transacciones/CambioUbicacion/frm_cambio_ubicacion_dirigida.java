package com.dts.tom.Transacciones.CambioUbicacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.Calendar;
import java.util.Date;

public class frm_cambio_ubicacion_dirigida extends PBase {

    private TextView lblTituloForma,lblCant,lblCambioEstado,lblUbicDestino;
    private EditText txtUbicOrigen,txtCodigoPrd,txtPresentacion,txtPropietario,txtLote,txtVence,txtEstado,
            txtCantidad,txtUbicDestino,txtEstadoDestino, txtPosiciones;

    private double vCantidadAUbicar;
    private boolean compl;

    private clsBeTrans_movimientos gMovimientoDet;
    private clsBeBodega_ubicacion bodega_ubicacion;
    private clsBeProducto_estado gProdEstado;
    private clsBeStock pStock;

    private frm_cambio_ubicacion_dirigida.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private Date fecha_ini;

    private boolean EsPalletNoEstandar=false;
    private boolean TienePosiciones=false;

    private int vPosiciones=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cambio_ubicacion_dirigida);

        super.InitBase();

        ws = new WebServiceHandler(frm_cambio_ubicacion_dirigida.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblCant = (TextView) findViewById(R.id.lbCant);
        lblCambioEstado = (TextView) findViewById(R.id.lblCambioEstado);
        txtUbicOrigen = (EditText) findViewById(R.id.txtUbicOrigen);
        txtCodigoPrd = (EditText) findViewById(R.id.txtCodigoPrd);
        txtPresentacion = (EditText) findViewById(R.id.txtPresentacion);
        txtLote = (EditText) findViewById(R.id.txtLote);
        txtVence = (EditText) findViewById(R.id.txtVence);
        txtEstado = (EditText) findViewById(R.id.txtEstado);
        txtCantidad = (EditText) findViewById(R.id.txtCantidad);
        txtUbicDestino = (EditText) findViewById(R.id.txtUbicDestino);
        txtEstadoDestino = (EditText) findViewById(R.id.txtEstadoDestino);
        txtPropietario = (EditText) findViewById(R.id.txtPropietario);

        txtPosiciones = new EditText(this,null);
        txtPosiciones.setInputType(InputType.TYPE_CLASS_NUMBER);

        lblUbicDestino = (TextView)findViewById(R.id.lblDestino);

        lblTituloForma.setText( String.format("Cambio de %s dirigido",(gl.modo_cambio==1?"ubicación":"estado")));

        lblCambioEstado.setVisibility((gl.modo_cambio==2?View.VISIBLE:View.INVISIBLE));
        txtEstadoDestino.setVisibility((gl.modo_cambio==2?View.VISIBLE:View.INVISIBLE));

        txtCodigoPrd.setEnabled(true);
        txtPresentacion.setEnabled(true);
        txtPropietario.setEnabled(true);
        txtLote.setEnabled(true);
        txtVence.setEnabled(true);
        txtEstado.setEnabled(true);
        txtEstadoDestino.setEnabled(true);

        setHandlers();

        ProgressDialog("Cargando forma");

        Inicia_Tarea_Detalle();

        progress.cancel();

    }

    private void setHandlers(){

        try{

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

            txtUbicOrigen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        if (txtUbicOrigen.getText().toString().equals("") ||
                                txtUbicOrigen.getText().toString().isEmpty() ||
                                txtUbicOrigen.getText().toString()==null){
                            mu.msgbox("Debe ingresar la ubicación origen");
                        }
                    }
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

            txtUbicDestino.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        if (txtUbicDestino.getText().toString().equals("") ||
                                txtUbicDestino.getText().toString().isEmpty() ||
                                txtUbicDestino.getText().toString()==null){
                            mu.msgbox("Debe ingresar la ubicación destino");
                        }
                    }
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

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    public void Inicia_Tarea_Detalle(){

        try{

            fecha_ini = Calendar.getInstance().getTime();

            gl.IdOrigen = gl.tareadet.UbicacionOrigen.IdUbicacion;
            gl.IdDestino = gl.tareadet.UbicacionDestino.IdUbicacion;

            lblTituloForma.setText(gl.tareadet.UbicacionOrigen.NombreCompleto);

            txtCodigoPrd.setText(gl.tareadet.Producto.Nombre);
            txtPresentacion.setText(gl.tareadet.ProductoPresentacion.Nombre);
            txtPropietario.setText(gl.tareadet.Producto.Propietario.Nombre_comercial);
            txtLote.setText(gl.tareadet.Stock.Lote);

            txtVence.setText(app.strFecha(gl.tareadet.Stock.Fecha_vence));

            txtEstado.setText(gl.tareadet.Stock.ProductoEstado.Nombre);
            txtCantidad.setText(String.valueOf(gl.tareadet.Cantidad - gl.tareadet.Recibido));

            if (!gl.tareadet.UbicacionDestino.NombreCompleto.contains("#")){
                lblUbicDestino.setText("Destino: " + gl.tareadet.UbicacionDestino.NombreCompleto + " - #" + gl.IdDestino);
            }else{
                lblUbicDestino.setText("Destino: " + gl.tareadet.UbicacionDestino.NombreCompleto);
            }

            gl.gCantDisponible = gl.tareadet.Cantidad - gl.tareadet.Recibido;
            gl.tareadet.Estado = "En proceso";
            gl.tareadet.HoraInicio = app.strFechaHoraXML(fecha_ini);

            lblCant.setText(gl.gCantDisponible+"");
            txtUbicDestino.setText("");

            if (gl.modo_cambio==2){
                txtEstadoDestino.setText("");
                //Llama al método del WS Get_Single_By_IdEstado
                execws(4);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void validaOrigen(){

        try{

            if (txtUbicOrigen.getText().toString() !=""){

                bodega_ubicacion = new clsBeBodega_ubicacion();

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar la ubicación origen
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

                //Llama al método del WS Get_Ubicacion_By_Codigo_Barra_And_IdBodega para validar la ubicación destino
                execws(2);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void cambioUbicEst(){

        try{

            progress.setMessage("Aplicando el cambio");
            progress.show();

            vCantidadAUbicar=0;
            compl = false;
            double cantStock;

            vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString());

            if (txtUbicOrigen.getText().toString().isEmpty()) {
                mu.msgbox("Debe ingresar la ubicación origen");
                txtUbicOrigen.requestFocus();
                return;
            }

            if (txtUbicDestino.getText().toString().isEmpty()) {
                mu.msgbox("Debe ingresar la ubicación destino");
                txtUbicDestino.requestFocus();
                return;
            }

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

            progress.cancel();

            //#CKFK 20210106 Moví esto para después de ingresar las posiciones en caso de que aplique
             // msgAsk("Aplicar cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));

            pStock = new clsBeStock();
            pStock.IdUbicacion = Integer.valueOf(txtUbicOrigen.getText().toString());
            pStock.IdProductoBodega = gl.tareadet.getProducto().IdProductoBodega;
            pStock.IdProductoEstado = gl.tareadet.Stock.ProductoEstado.getIdEstado();
            pStock.IdPresentacion = gl.tareadet.ProductoPresentacion.getIdPresentacion();
            pStock.IdUnidadMedida = gl.tareadet.UnidadMedida.IdUnidadMedida;
            pStock.Fecha_vence = du.convierteFechaDiagonal(txtVence.getText().toString());
            pStock.Lote = gl.tareadet.Stock.Lote;

            execws(5);

        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }finally {
        }

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
                    //Llamar método
                    Aplicar();
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

    private void Aplicar(){

        try{

            progress.setMessage("Aplicando el cambio");
            progress.show();

            Date currentTime = Calendar.getInstance().getTime();

            if (vCantidadAUbicar>0){
                gl.tareadet.Recibido = gl.tareadet.Recibido + vCantidadAUbicar;
            }else {
                gl.tareadet.Recibido = 0;
            }

            if (gl.tareadet.Recibido ==0) {
                gl.tareadet.HoraInicio = app.strFechaHoraXML(fecha_ini);
                gl.tareaenc.FechaInicio = app.strFechaHoraXML(fecha_ini);
            }

            if(gl.tareadet.getCantidad()==gl.tareadet.getRecibido()){
                gl.tareadet.Realizado = true;
            }

            gl.tareaenc.Cambio_estado = (gl.modo_cambio==2?true:false);

            if (compl) {
                gl.tareaenc.Estado = "Finalizado";
                gl.tareaenc.HoraFin = app.strFecha(currentTime);
                gl.tareaenc.FechaFin = app.strFecha(currentTime);
            }else{
                gl.tareadet.Estado = "Pendiente";
            }

            if (Crear_movimiento()){
                //Llama al WS para realizar la actualización de los registros. Actualizar_Trans_Ubic_HH_Det
                execws(3);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            progress.cancel();
        }

    }

    private boolean Crear_movimiento(){

        try{

            progress.setMessage("Creando el movimiento");

            gMovimientoDet = new clsBeTrans_movimientos();

            gMovimientoDet.IdMovimiento = 0;
            gMovimientoDet.IdEmpresa = gl.IdEmpresa;
            gMovimientoDet.IdBodegaOrigen = gl.IdBodega;
            gMovimientoDet.IdTransaccion = gl.tareaenc.IdTareaUbicacionEnc;
            gMovimientoDet.IdPropietarioBodega = gl.IdPropietarioBodega;
            gMovimientoDet.IdProductoBodega = gl.tareadet.Producto.IdProductoBodega;
            gMovimientoDet.IdUbicacionOrigen = gl.tareadet.UbicacionOrigen.IdUbicacion;
            gMovimientoDet.IdUbicacionDestino = gl.tareadet.UbicacionDestino.IdUbicacion;
            gMovimientoDet.IdPresentacion = gl.tareadet.ProductoPresentacion.IdPresentacion;
            gMovimientoDet.IdEstadoOrigen = gl.tareadet.Stock.ProductoEstado.IdEstado;

            if (gl.modo_cambio==2){
                gMovimientoDet.IdEstadoDestino = gl.tareadet.IdEstadoDestino;
            }else{
                gMovimientoDet.IdEstadoDestino = gl.tareadet.Stock.ProductoEstado.IdEstado;
            }

            gMovimientoDet.IdUnidadMedida = gl.tareadet.UnidadMedida.IdUnidadMedida;
            gMovimientoDet.IdTipoTarea = 2;
            gMovimientoDet.IdBodegaDestino = gl.IdBodega;
            gMovimientoDet.IdRecepcion = gl.tareadet.Stock.IdRecepcionEnc;
            gMovimientoDet.Cantidad = vCantidadAUbicar;
            gMovimientoDet.Serie = gl.tareadet.Stock.Serial;
            gMovimientoDet.Peso = gl.tareadet.ProductoPresentacion.Peso * vCantidadAUbicar;
            gMovimientoDet.Lote = gl.tareadet.Stock.Lote;
            gMovimientoDet.Fecha_vence =app.strFechaXML(gl.tareadet.Stock.Fecha_vence.toString());
            gMovimientoDet.Fecha = gl.tareadet.HoraFin;

            if (gl.Escaneo_Pallet){
                if (gl.BeStockPallet!=null) {
                    gMovimientoDet.Barra_pallet = gl.BeStockPallet.Codigo_Barra;
                }else{
                    gMovimientoDet.Barra_pallet = "";
                }

            }else{
                gMovimientoDet.Barra_pallet = "";
            }

            gMovimientoDet.Hora_ini = gl.tareadet.HoraInicio;
            gMovimientoDet.Hora_fin = gl.tareadet.HoraFin;
            gMovimientoDet.Fecha_agr = gl.tareadet.HoraFin;
            gMovimientoDet.Usuario_agr = String.valueOf(gl.tareadet.IdOperadorBodega);
            gMovimientoDet.Cantidad_hist = gMovimientoDet.Cantidad;
            gMovimientoDet.Peso_hist = gMovimientoDet.Peso;
            gMovimientoDet.IsNew = true;

            return true;
        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            return false;
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

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                   "pBarra",txtUbicOrigen.getText().toString(),
                                   "pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                   "pBarra",txtUbicDestino.getText().toString(),
                                   "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Actualizar_Trans_Ubic_HH_Det",
                                   "oBeTrans_ubic_hh_det", gl.tareadet,
                                   "pMovimiento",gMovimientoDet,
                                   "pPosiciones",vPosiciones);
                        break;
                    case 4:
                        callMethod("Get_Single_By_IdEstado",
                                   "pIdEstado",gl.tareadet.IdEstadoDestino);
                        break;
                    case 5:
                        callMethod("Es_Pallet_No_Estandar",
                                   "pStock",pStock);
                        break;
                    case 6:
                        callMethod("Tiene_Posiciones",
                                   "pStock",pStock);
                        break;

                }

            } catch (Exception e) {
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
                    processUbicOrigen();break;
                case 2:
                    processUbicDestino();break;
                case 3:
                    processCambio();
                    break;
                case 4:
                    processProdEstado();
                    break;
                case 5:
                    processPalletNoEstandar();
                    break;
                case 6:
                    processTienePosiciones();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processUbicOrigen(){

        try {

            progress.setMessage("Validando ubicación  origen");

            bodega_ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion == null)
            {
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                throw new Exception("Ubicación origen incorrecta");
            }

            if (bodega_ubicacion.IdUbicacion != gl.IdOrigen)
            {
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                throw new Exception("La ubicación origen no coincide");
            }

            txtUbicDestino.requestFocus();

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
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                throw new Exception("La ubicación destino no coincide");
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

            progress.cancel();

            if (actualizar){
                msgAskExit(String.format("Cambio de %s aplicado",(gl.modo_cambio==1?"ubicación":"estado")));
            }else{
                msgbox("Ocurrió un error al procesar el cambio de ubicación");
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processProdEstado(){

        try {

            progress.setMessage("Obteniendo el estado del producto");

            gProdEstado = xobj.getresult(clsBeProducto_estado.class,"Get_Single_By_IdEstado");

            if (gProdEstado != null){
                txtEstadoDestino.setText(gProdEstado.Nombre);
            }

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    public void Regresar(View view){
       finish();
    }

    public void AplicarCambio(View view){
        cambioUbicEst();
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
                    frm_cambio_ubicacion_dirigida.super.finish();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void processPalletNoEstandar(){

        try{

            EsPalletNoEstandar = xobj.getresult(Boolean.class,"Es_Pallet_No_Estandar");

            if (EsPalletNoEstandar){
                execws(6);
            }else{

                msgAsk("Aplicar cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));

            }

        }catch (Exception e){
            mu.msgbox("processPalletNoEstandar:"+e.getMessage());
        }
    }

    private void processTienePosiciones(){

        try{

            TienePosiciones = xobj.getresult(Boolean.class,"Tiene_Posiciones");

            if (!TienePosiciones){
                msgAskIngresePosiciones();
            }else{
                msgAsk("Aplicar cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));
            }

        }catch (Exception e){
            mu.msgbox("processPalletNoEstandar:"+e.getMessage());
        }
    }

    private void msgAskIngresePosiciones() {

        try{

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Ingrese número de posiciones");

            vPosiciones = 0;

            final LinearLayout layout   = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            if(txtPosiciones.getParent()!= null){
                ((ViewGroup) txtPosiciones.getParent()).removeView(txtPosiciones);
            }

            txtPosiciones.setText("");
            txtPosiciones.requestFocus();

            layout.addView(txtPosiciones);

            alert.setView(layout);

            showkeyb();
            alert.setCancelable(false);
            alert.create();

            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    layout.removeAllViews();

                    vPosiciones=Integer.valueOf(txtPosiciones.getText().toString());

                    if (vPosiciones==0){
                        layout.removeAllViews();
                        msgAskIngresePosiciones();
                    }else{
                        msgAsk("Aplicar cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));
                    }

                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    layout.removeAllViews();
                }
            });

            alert.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}
