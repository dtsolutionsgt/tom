package com.dts.tom.Transacciones.CambioUbicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dts.classes.clsBebodega_ubicacion;
import com.dts.classes.clsBeproducto_estado;
import com.dts.classes.clsBetrans_movimientos;
import com.dts.classes.clsBetrans_ubic_hh_det;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class frm_cambio_ubicacion_dirigida extends PBase {

    private TextView lblTituloForma,lbCant,lblCambioEstado;
    private EditText txtUbicOrigen,txtCodigoPrd,txtPresentacion,txtLote,txtVence,txtEstado,txtCantidad,txtUbicDestino,txtEstadoDestino;
    private double vCantidadAUbicar;
    private clsBetrans_movimientos gMovimientoDet;
    private boolean compl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cambio_ubicacion_dirigida);

        super.InitBase();

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

        if (gl.modo_cambio==2){
            lblTituloForma.setText("Cambio de estado dirigido");
            lblCambioEstado.setVisibility(View.VISIBLE);
            txtEstadoDestino.setVisibility(View.VISIBLE);
        }else{
            lblCambioEstado.setVisibility(View.INVISIBLE);
            txtEstadoDestino.setVisibility(View.INVISIBLE);
        }

        setHandlers();
        Inicia_Tarea_Detalle();

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
                                 txtUbicDestino.requestFocus();
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
                                txtCantidad.requestFocus();
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
                                Cambio_Ubicacion();
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


            gl.IdOrigen = gl.tareadet.UbicacionOrigen.IdUbicacion;
            gl.IdDestino = gl.tareadet.UbicacionDestino.IdUbicacion;

            txtCodigoPrd.setText(gl.tareadet.Producto.nombre);
            txtPresentacion.setText(gl.tareadet.ProductoPresentacion.nombre);
            //txtProp.setText(gl.tareadet.Producto.Propietario.Nombre_comercial);
            txtLote.setText(gl.tareadet.Stock.lote);
            txtVence.setText(String.valueOf(gl.tareadet.Stock.fecha_vence));
            txtEstado.setText(gl.tareadet.Stock.ProductoEstado.nombre);
            txtCantidad.setText(String.valueOf(gl.tareadet.cantidad - gl.tareadet.recibido));
            gl.gCantDisponible = gl.tareadet.cantidad - gl.tareadet.recibido;
            gl.tareadet.estado = "En proceso";

            if (gl.modo_cambio==2){
                txtEstadoDestino.setText("");
            }

            txtUbicDestino.setText("");

            //Llama al WS para actualizar trans_ubic_hh_det
            //frmInicio.m_proxy.Actualizar_Trans_Ubic_HH_Det(tareadet, Nothing)

            if (gl.modo_cambio==2){

                clsBeproducto_estado prest;

                prest = null;//frmInicio.m_proxy.Get_Single_By_IdEstado(gl.tareadet.IdEstadoDestino)

                if(prest!=null){
                    txtUbicDestino.setText(prest.nombre);
                }


            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private boolean validaOrigen(){
        clsBebodega_ubicacion BeUbicacion;

        try{

            if (txtUbicOrigen.getText().toString() !=""){


                BeUbicacion =null;//Get_Ubicacion_By_Codigo_Barra_And_IdBodega(Val(txtUbicOrigen.getText().toString()), gl.IdBodega)

                if (BeUbicacion == null)return false;

                if (BeUbicacion.IdUbicacion != gl.IdOrigen){
                    mu.msgbox("La ubicación origen no coincide");
                }


            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

        return  true;

    }

    private  boolean validaDestino(){
        clsBebodega_ubicacion BeUbicacion;

        try{

            if (txtUbicDestino.getText().toString() !=""){


                BeUbicacion =null;//Get_Ubicacion_By_Codigo_Barra_And_IdBodega(txtUbicDestino.getText().toString(), gl.IdBodega)


                if (BeUbicacion == null)return false;

                if (BeUbicacion.IdUbicacion != gl.IdDestino){
                    mu.msgbox("La ubicación destino no coincide");
                }

            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

        return true;

    }

    private void Cambio_Ubicacion(){

        try{

            vCantidadAUbicar=0;
            compl = false;
            double cantStock;
            String tipoOp="ubicación";

            cantStock = gl.gCantDisponible;

            vCantidadAUbicar = Double.parseDouble(txtCantidad.getText().toString());

            if (vCantidadAUbicar<0) mu.msgbox("La cantidad no puede ser negativa");

            if (vCantidadAUbicar >cantStock)mu.msgbox("Cantidad mayor que stock disponible: "+ cantStock); txtCantidad.requestFocus();

            iif ((gl.modo_cambio==2),tipoOp="estado",tipoOp="ubicación");

            if ((vCantidadAUbicar-cantStock)==0){
                compl=true;
            }

            msgAsk("¿Aplicar cambio de "+ tipoOp);

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
                gl.tareadet.recibido = gl.tareadet.recibido + vCantidadAUbicar;
            }else {
                gl.tareadet.recibido = 0;
            }

            if (gl.tareadet.recibido ==0){
                gl.tareadet.HoraInicio =currentTime;
                gl.tareadet.HoraFin = currentTime;
                gl.tareadet.estado = "Pendiente";
                gl.tareadet.recibido = gl.tareadet.cantidad;
                gl.tareadet.Realizado = true;
            }

            Crear_movimiento();

            //Llama al WS para realizar la actualización de los registros.
            //if Actualizar_Trans_Ubic_HH_Det(gl.tareadet, gMovimientoDet){

            gl.tareaenc.estado = "Pendiente";

            if (gl.tareaenc.estado.equals("NUEVO")) {
                gl.tareaenc.HoraInicio =  currentTime;
                gl.tareaenc.FechaInicio = currentTime;
            }


            if (gl.modo_cambio==2){
                gl.tareaenc.cambio_estado = true;
            }else{
                gl.tareaenc.cambio_estado = false;
            }

            if (compl) {
                gl.tareaenc.estado = "Finalizado";
                gl.tareaenc.HoraFin = currentTime;
                gl.tareaenc.FechaFin = currentTime;
            }

            if(gl.modo_cambio==2){
                mu.msgbox("Cambio de estado aplicado");
            }else{
                mu.msgbox("Cambio de ubicación aplicado");
            }

            //Actualiza_Tarea_Lista();


            //}


        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void Crear_movimiento(){

        try{

            gMovimientoDet = new clsBetrans_movimientos();

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
            gMovimientoDet.cantidad = vCantidadAUbicar;
            gMovimientoDet.serie = gl.tareadet.Stock.serial;
            gMovimientoDet.peso = gl.tareadet.ProductoPresentacion.peso * vCantidadAUbicar;
            gMovimientoDet.lote = gl.tareadet.Stock.lote;
            gMovimientoDet.fecha_vence = gl.tareadet.Stock.fecha_vence;
            gMovimientoDet.fecha = gl.tareadet.HoraFin;

            if (gl.Escaneo_Pallet){
                if (gl.BeStockPallet!=null) {
                    gMovimientoDet.barra_pallet = gl.BeStockPallet.codigo_barra;
                }else{
                    gMovimientoDet.barra_pallet = "";
                }

            }else{
                gMovimientoDet.barra_pallet = "";
            }

            gMovimientoDet.hora_ini = gl.tareadet.HoraInicio;
            gMovimientoDet.hora_fin = gl.tareadet.HoraFin;
            gMovimientoDet.fecha_agr = gl.tareadet.HoraFin;
            gMovimientoDet.usuario_agr = String.valueOf(gl.tareadet.IdOperador);
            gMovimientoDet.cantidad_hist = gMovimientoDet.cantidad;
            gMovimientoDet.peso_hist = gMovimientoDet.peso;
            gMovimientoDet.IsNew = true;


        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }


}
