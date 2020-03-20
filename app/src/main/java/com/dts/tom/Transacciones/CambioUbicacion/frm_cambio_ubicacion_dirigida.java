package com.dts.tom.Transacciones.CambioUbicacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dts.base.AppMethods;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class frm_cambio_ubicacion_dirigida extends PBase {

    private TextView lblTituloForma,lbCant,lblCambioEstado,lblUbicDestino;
    private EditText txtUbicOrigen,txtCodigoPrd,txtPresentacion,txtPropietario,txtLote,txtVence,txtEstado,txtCantidad,txtUbicDestino,txtEstadoDestino;
    private double vCantidadAUbicar;
    private boolean compl;

    private clsBeTrans_movimientos gMovimientoDet;
    private clsBeBodega_ubicacion bodega_ubicacion;
    private clsBeProducto_estado gProdEstado;

    private frm_cambio_ubicacion_dirigida.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private Date fecha_ini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cambio_ubicacion_dirigida);

        super.InitBase();

        ws = new WebServiceHandler(frm_cambio_ubicacion_dirigida.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lbCant = (TextView) findViewById(R.id.lbCant);
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

        lblUbicDestino = (TextView)findViewById(R.id.lblDestino);

        lblTituloForma.setText( String.format("Cambio de %s dirigido",(gl.modo_cambio==1?"ubicación":"estado")));

        lblCambioEstado.setVisibility((gl.modo_cambio==2?View.VISIBLE:View.INVISIBLE));
        txtEstadoDestino.setVisibility((gl.modo_cambio==2?View.VISIBLE:View.INVISIBLE));

        app.readOnly(txtCodigoPrd);
        app.readOnly(txtPresentacion);
        app.readOnly(txtPropietario);
        app.readOnly(txtLote);
        app.readOnly(txtVence);
        app.readOnly(txtEstado);

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
            lblUbicDestino.setText(gl.tareadet.UbicacionDestino.NombreCompleto);

            gl.gCantDisponible = gl.tareadet.Cantidad - gl.tareadet.Recibido;
            gl.tareadet.Estado = "En proceso";
            gl.tareadet.HoraInicio = app.strFechaHora(fecha_ini);

            txtUbicDestino.setText("");

            if (gl.modo_cambio==2){
                txtEstadoDestino.setText("");
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

            cantStock = gl.gCantDisponible;

            vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString());

            if (vCantidadAUbicar<0) {
                mu.msgbox("La cantidad no puede ser negativa");
                txtCantidad.requestFocus();
                return;
            }

            if (vCantidadAUbicar >cantStock){
                mu.msgbox("Cantidad mayor que stock disponible: "+ cantStock);
                txtCantidad.requestFocus();
                return;
            }

            if ((vCantidadAUbicar-cantStock)==0){
                compl=true;
            }

            msgAsk("¿Aplicar cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void msgAsk(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
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

            Date currentTime = Calendar.getInstance().getTime();

            if (vCantidadAUbicar>0){
                gl.tareadet.Recibido = gl.tareadet.Recibido + vCantidadAUbicar;
            }else {
                gl.tareadet.Recibido = 0;
            }

            if (gl.tareadet.Recibido ==0) {
                gl.tareadet.HoraInicio = app.strFechaHora(fecha_ini);
            }

            gl.tareadet.HoraFin = app.strFechaHora(currentTime);
            gl.tareadet.Estado = "Pendiente";
            gl.tareadet.Recibido = gl.tareadet.Cantidad;
            gl.tareadet.Realizado = true;

            if (Crear_movimiento()){
                //Llama al WS para realizar la actualización de los registros.
                execws(3);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private boolean Crear_movimiento(){

        try{

            gMovimientoDet = new clsBeTrans_movimientos();

            gMovimientoDet.IdMovimiento = 0;
            gMovimientoDet.IdEmpresa = gl.IdEmpresa;
            gMovimientoDet.IdBodegaOrigen = gl.IdBodega;
            gMovimientoDet.IdTransaccion = gl.tareaenc.IdTareaUbicacionEnc;
            gMovimientoDet.IdPropietarioBodega = gl.tareadet.Producto.IdPropietario;
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
            gMovimientoDet.Fecha_vence =app.strFechaSQL(gl.tareadet.Stock.Fecha_vence.toString());
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
            gMovimientoDet.Usuario_agr = String.valueOf(gl.tareadet.IdOperador);
            gMovimientoDet.Cantidad_hist = gMovimientoDet.Cantidad;
            gMovimientoDet.Peso_hist = gMovimientoDet.Peso;
            gMovimientoDet.IsNew = true;

            return true;
        }catch (Exception e){
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicOrigen.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicDestino.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Actualizar_Trans_Ubic_HH_Det","oBeTrans_ubic_hh_det", gl.tareadet,
                                "pMovimiento",gMovimientoDet);
                    case 4:
                        callMethod("Get_Single_By_IdEstado","pIdEstado",gl.tareadet.IdEstadoDestino);
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
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processUbicOrigen(){

        try {

            progress.setMessage("Validando ubicación");

            bodega_ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (bodega_ubicacion == null){
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                throw new Exception("Ubicación origen incorrecta");
            }

            if (bodega_ubicacion.IdUbicacion != gl.IdOrigen){
                mu.msgbox("La ubicación origen no coincide");
                txtUbicOrigen.selectAll();
                txtUbicOrigen.requestFocus();
                return;
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
                mu.msgbox("La ubicación destino no coincide");
                txtUbicDestino.selectAll();
                txtUbicDestino.requestFocus();
                return;
            }

            txtCantidad.requestFocus();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processCambio(){

        try {

            progress.setMessage("Validando ubicación");

            boolean actualizar = (Boolean) xobj.getSingle("Actualizar_Trans_Ubic_HH_Det",boolean.class);

            if (actualizar){

                Date currentTime = Calendar.getInstance().getTime();

                gl.tareaenc.Estado = "Pendiente";

                if (gl.tareaenc.Estado.equals("NUEVO")) {
                    gl.tareaenc.HoraInicio =  app.strFecha(currentTime);
                    gl.tareaenc.FechaInicio = app.strFecha(currentTime);
                }

                if (gl.modo_cambio==2){
                    gl.tareaenc.Cambio_estado = true;
                }else{
                    gl.tareaenc.Cambio_estado = false;
                }

                if (compl) {
                    gl.tareaenc.Estado = "Finalizado";
                    gl.tareaenc.HoraFin = app.strFecha(currentTime);
                    gl.tareaenc.FechaFin = app.strFecha(currentTime);
                }

                if(gl.modo_cambio==2){
                    mu.msgbox("Cambio de estado aplicado");
                }else{
                    mu.msgbox("Cambio de ubicación aplicado");
                }

                //Actualiza_Tarea_Lista();
            }

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

    public void Regresar(View view){
        msgAskExit(String.format("Está seguro de salir del cambio de %s",(gl.modo_cambio==1?"ubicación":"estado")));
    }

    public void AplicarCambio(View view){
        cambioUbicEst();
    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_cambio_ubicacion_dirigida.super.finish();
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

    @Override
    public void onBackPressed() {

        try{

            msgAskExit(String.format("Está seguro de salir del cambio de %s",(gl.modo_cambio==1?"ubicación":"estado")));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}
