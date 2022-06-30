package com.dts.tom.Transacciones.ReubicarStockRes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking;

import static com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res.selitem;

public class frm_datos_stock_res extends PBase {

    private frm_datos_stock_res.WebServiceHandler ws;
    private XMLObject xobj;

    private TextView lblTituloForma, lblNomDestino, lbCant,
                     txtUnidadMedida, txtPresentacion, txtPropietario, txtLote, txtVence, txtEstado, txtDescProd;
    private EditText txtUbicOrigen, txtLicensePlate, txtCodigoPrd, txtCantidad, txtUbicDestino;
    private TableRow trDestino, trPresentacion, trLote, trVence, trProducto;

    private int IdUbicacion = 0, IdUbicacionDest = 0;
    private clsBeBodega_ubicacion BeUbic = new clsBeBodega_ubicacion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_datos_stock_res);

        super.InitBase();

        ws = new frm_datos_stock_res.WebServiceHandler(frm_datos_stock_res.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblNomDestino = findViewById(R.id.lblNomDestino);
        lbCant = findViewById(R.id.lbCant);

        txtUbicOrigen  = findViewById(R.id.txtUbicOrigen);
        txtLicensePlate = findViewById(R.id.txtLicensePlate);
        txtCodigoPrd = findViewById(R.id.txtCodigoPrd);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtUbicDestino = findViewById(R.id.txtUbicDestino);
        txtUnidadMedida = findViewById(R.id.txtUnidadMedida);
        txtPresentacion = findViewById(R.id.txtPresentacion);
        txtPropietario = findViewById(R.id.txtPropietario);
        txtLote = findViewById(R.id.txtLote);
        txtVence = findViewById(R.id.txtVence);
        txtEstado = findViewById(R.id.txtEstado);
        txtDescProd = findViewById(R.id.txtDescProd);

        trDestino = findViewById(R.id.trDestino);
        trPresentacion = findViewById(R.id.trPresentacion);
        trLote = findViewById(R.id.trLote);
        trVence = findViewById(R.id.trVence);
        trProducto = findViewById(R.id.trProducto);

        Load();
        setHandlers();
    }

    private void setHandlers() {
        try {

            txtUbicDestino.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtUbicDestino.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    execws(1);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtUbicOrigen.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    ValidaUbicacionOrigen();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtLicensePlate.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    ValidaLicencia();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtCodigoPrd.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    ValidaCodigoPrd();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtCantidad.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    ActualizaUbicacion();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandlers: "+e.getMessage());
        }
    }

    private void Load() {
        try {
            lblTituloForma.setText(selitem.Ubicacion_Nombre);
            txtUbicOrigen.setHint(""+selitem.IdUbicacion);
            lbCant.setText(""+selitem.CantidadReservadaUMBas);
            txtLicensePlate.setHint(selitem.Lic_plate);
            txtCodigoPrd.setHint(selitem.Codigo_Producto);
            txtCantidad.setText(""+selitem.CantidadReservadaUMBas);
            txtUnidadMedida.setText(""+selitem.UMBas);

            if (selitem.Nombre_Presentacion.isEmpty()) {
                trPresentacion.setVisibility(View.GONE);
            } else {
                txtPresentacion.setText(selitem.Nombre_Presentacion);
            }

            txtPropietario.setText(selitem.Propietario);

            if (selitem.Lote.isEmpty()) {
                trLote.setVisibility(View.GONE);
            } else {
                txtLote.setText(selitem.Lote);
            }

            txtEstado.setText(selitem.NomEstado);

            if (selitem.Fecha_Vence.equals("01-01-1900")) {
                trVence.setVisibility(View.GONE);
            } else {
                txtVence.setText(du.convierteFechaMostrar(selitem.Fecha_Vence));
            }

            trProducto.setVisibility(View.GONE);

        } catch (Exception e) {
            mu.msgbox("Load: "+e.getMessage());
        }
    }

    private void ValidaUbicacionOrigen() {
        int UbicOrigen = 0;
        try {

            if (!txtUbicOrigen.getText().toString().isEmpty()) {
                UbicOrigen = Integer.valueOf(txtUbicOrigen.getText().toString());

                if (UbicOrigen != selitem.IdUbicacion) {
                    mu.msgbox("La ubicación ingresada " + UbicOrigen + " no coincide con: " + selitem.IdUbicacion);
                    txtUbicOrigen.requestFocus();
                    txtUbicOrigen.selectAll();
                } else {
                    txtLicensePlate.requestFocus();
                }
            } else {
                txtUbicOrigen.requestFocus();
                mu.msgbox("Ingrese ubicación de origen.");
            }
        } catch (Exception e) {
            mu.msgbox("ValidaUbicacionOrigen: "+e.getMessage());
        }
    }

    private void ValidaLicencia() {
        String Lic = "";
        try {

            if (!txtLicensePlate.getText().toString().isEmpty()) {
                Lic = txtLicensePlate.getText().toString();

                if (!Lic.equals(selitem.Lic_plate)) {
                    mu.msgbox("La licencia ingresada " + Lic + " no coincide con: " + selitem.Lic_plate);
                    txtLicensePlate.requestFocus();
                    txtLicensePlate.selectAll();
                } else {
                    txtCodigoPrd.requestFocus();
                }
            } else {
                txtLicensePlate.requestFocus();
                mu.msgbox("Ingrese licencia.");
            }
        } catch (Exception e) {
            mu.msgbox("ValidaLicencia: "+e.getMessage());
        }
    }

    private void ValidaCodigoPrd() {
        String Codigo = "";
        try {

            if (!txtCodigoPrd.getText().toString().isEmpty()) {
                Codigo = txtCodigoPrd.getText().toString();

                if (!Codigo.equals(selitem.Codigo_Producto)) {
                    mu.msgbox("El código ingresado " + Codigo + " no coincide con: " + selitem.Codigo_Producto);
                    txtCodigoPrd.requestFocus();
                    txtCodigoPrd.selectAll();
                } else {
                    trProducto.setVisibility(View.VISIBLE);
                    txtDescProd.setText(selitem.Codigo_Producto+" - "+selitem.Nombre_Producto);
                    txtUbicDestino.requestFocus();
                }
            } else {
                txtCodigoPrd.requestFocus();
                mu.msgbox("Ingrese licencia.");
            }
        } catch (Exception e) {
            mu.msgbox("ValidaLicencia: "+e.getMessage());
        }
    }

    private void processValidaUbic() {
        try {
            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                if ((BeUbic.Nivel == 1 || BeUbic.Nivel == 0 || BeUbic.Ubicacion_picking)
                        && BeUbic.IdUbicacion != selitem.IdUbicacion){

                    trDestino.setVisibility(View.VISIBLE);
                    lblNomDestino.setText(BeUbic.Descripcion);
                    txtCantidad.requestFocus();
                } else {
                    txtUbicDestino.requestFocus();
                    txtUbicDestino.selectAll();
                    lblNomDestino.setText("");
                    toast("Ubicación no válida");
                }
            } else {
                txtUbicDestino.requestFocus();
                txtUbicDestino.selectAll();
                lblNomDestino.setText("");
                toast("Ubicación no válida");
            }
        } catch (Exception e) {
            mu.msgbox("processValidaUbic: "+ e.getMessage());
        }
    }

    private void processActualizaUbic() {
        try {

        } catch (Exception e) {
            mu.msgbox("processActualizaUbic: "+e.getMessage());
        }
    }

    private void ValidaDatos() {
        try {
            if (txtUbicOrigen.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese ubicación de origen.");
                return;
            }

            if (txtLicensePlate.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese licencia.");
                return;
            }

            if (txtCodigoPrd.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese código.");
                return;
            }

            if (txtUbicDestino.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese ubicación destino.");
                return;
            }

            if (txtCantidad.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese cantidad.");
                return;
            }

        } catch (Exception e) {
            mu.msgbox("ValidaDatos: "+e.getMessage());
        }
    }

    private void ActualizaUbicacion() {
        try {
            ValidaDatos();

            execws(2);
        } catch (Exception e) {
            mu.msgbox("ActualizaUbicacion: "+e.getMessage());
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
                        IdUbicacion = Integer.valueOf(txtUbicDestino.getText().toString());
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                         "pBarra", IdUbicacion,
                                               "pIdBodega", gl.IdBodega);
                        break;

                    case 2:
                        callMethod("Actualizar_Ubicaciones_Reservadas_By_IdStock",
                                         "pIdStock", selitem.IdStock,
                                               "pIdBodega", gl.IdBodega,
                                               "pIdUbicacion",IdUbicacion);
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
                    processValidaUbic();
                    break;
                case 2:
                    processActualizaUbic();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void Regresar(View view) {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}