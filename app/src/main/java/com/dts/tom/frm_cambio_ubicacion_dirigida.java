package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class frm_cambio_ubicacion_dirigida extends PBase {

    private TextView lblTituloForma,lbCant,lblCambioEstado;
    private EditText txtUbicOrigen,txtCodigoPrd,txtPresentacion,txtLote,txtVence,txtEstado,txtCantidad,txtUbicDestino,txtEstadoDestino;

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

    }

    private void setHandlers(){

        try{

            txtUbicOrigen.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                txtCodigoPrd.requestFocus();
                        }
                    }
                    return false;
                }
            });

            txtCodigoPrd.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                txtPresentacion.requestFocus();
                        }
                    }
                    return false;
                }
            });

            txtPresentacion.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                txtLote.requestFocus();
                        }
                    }
                    return false;
                }
            });

            txtLote.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                txtVence.requestFocus();
                        }
                    }
                    return false;
                }
            });

            txtVence.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                txtEstado.requestFocus();
                        }
                    }
                    return false;
                }
            });

            txtEstado.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
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
                                txtUbicDestino.requestFocus();
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

    public void Llena_campos(){

        try{

            txtUbicOrigen.setText("");
            txtUbicOrigen .setText("");
            txtCodigoPrd.setText("");
            txtPresentacion.setText("");
            txtLote.setText("");
            txtVence.setText("");
            txtEstado.setText("");
            txtCantidad.setText("");
            txtUbicDestino.setText("");

            if (gl.modo_cambio==2){
                txtEstadoDestino.setText("");
            }


        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void validaOrigen(){

        try{

            if (txtUbicOrigen.getText().toString() != "") {

            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void Aplicar_Cambio_Ubicacion(){

        try{



        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

}
