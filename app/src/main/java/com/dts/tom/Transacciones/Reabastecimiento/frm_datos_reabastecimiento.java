package com.dts.tom.Transacciones.Reabastecimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
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
import com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res;

import static com.dts.tom.Transacciones.Reabastecimiento.frm_reabastecimiento_manual.selitem;

public class frm_datos_reabastecimiento extends PBase {

    private  frm_datos_reabastecimiento.WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtUbicOrigen, txtLipPlate, txtCodigoPrd, txtLote, txtVence,
                     txtEstado, txtCantidad, txtUbicDestino;
    private TextView lblUbicCompleta, lblDescProducto, lblUbicCompDestino;
    private TableRow trLicPlate;

    private clsBeBodega_ubicacion BeUbic = new clsBeBodega_ubicacion();

    private int IdUbicacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_datos_reabastecimiento);

        super.InitBase();

        ws = new frm_datos_reabastecimiento.WebServiceHandler(frm_datos_reabastecimiento.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicOrigen = findViewById(R.id.txtUbicOrigen);
        txtLipPlate = findViewById(R.id.txtLipPlate);
        txtCodigoPrd = findViewById(R.id.txtCodigoPrd);
        txtLote = findViewById(R.id.txtLote);
        txtVence = findViewById(R.id.txtVence);
        txtEstado = findViewById(R.id.txtEstado);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtUbicDestino = findViewById(R.id.txtUbicDestino);

        lblUbicCompleta = findViewById(R.id.lblUbicCompleta);
        lblDescProducto = findViewById(R.id.lblDescProducto);
        lblUbicCompDestino = findViewById(R.id.lblUbicCompDestino);

        trLicPlate = findViewById(R.id.trLicPlate);

        Load();
        setHandlers();

    }

    private void setHandlers() {
        try {
            txtLipPlate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtCodigoPrd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtCantidad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtLipPlate.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtLipPlate.getText().toString().isEmpty()) {
                            if (selitem.Lic_plate.equals(txtLipPlate.getText().toString())) {
                                CargaDatos();
                            } else {
                                mu.msgbox("La licencia no coincide.");
                            }
                        } else {
                            mu.msgbox("Ingrese licencia válida.");
                        }
                    }

                    return false;
                }
            });

            if (trLicPlate.getVisibility() == View.GONE) {
                txtCodigoPrd.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            if (!txtCodigoPrd.getText().toString().isEmpty()) {
                                if (selitem.Codigo_Producto.equals(txtCodigoPrd.getText().toString())) {
                                    CargaDatos();
                                } else {
                                    mu.msgbox("El código producto no coincide.");
                                }
                            } else {
                                mu.msgbox("Ingrese código válido.");
                            }
                        }

                        return false;
                    }
                });
            }

            txtUbicDestino.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicDestino.getText().toString().isEmpty()) {
                            execws(3);
                        } else {
                            mu.msgbox("Ubicación no válida.");
                        }
                    }

                    return false;
                }
            });

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void CargaDatos() {
        try {

            if (trLicPlate.getVisibility() != View.GONE) {
                txtCodigoPrd.setText(selitem.Codigo_Producto);
            }

            lblDescProducto.setText( selitem.Codigo_Producto+" - "+selitem.Nombre_Producto);
            txtLote.setText(selitem.Lote);
            txtVence.setText(selitem.Fecha_Vence);
            txtEstado.setText(selitem.NomEstado);

            if (selitem.IdPresentacion > 0) {
                txtCantidad.setText(""+selitem.CantidadPresentacion);
            } else {
                txtCantidad.setText(""+selitem.CantidadUmBas);
            }

            txtCantidad. requestFocus();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Load() {
        try {
            txtUbicOrigen.setText(""+selitem.IdUbicacion);
            lblUbicCompleta.setText(""+selitem.NombreUbicacion);

            if (!selitem.Lic_plate.isEmpty()) {
                txtLipPlate.requestFocus();
            } else {
                trLicPlate.setVisibility(View.GONE);
                txtCodigoPrd.setFocusable(true);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processValidaUbicacion() {
        try {
            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                if ((BeUbic.Nivel == 1 || BeUbic.Nivel == 0 || BeUbic.Ubicacion_picking)
                        && BeUbic.IdUbicacion != selitem.IdUbicacion){

                    lblUbicCompDestino.setText(BeUbic.Descripcion);
                    txtCantidad.requestFocus();
                } else {
                    lblUbicCompDestino.setText("");
                    toast("Ubicación no válida");
                }
            } else {
                lblUbicCompDestino.setText("");
                toast("Ubicación no válida");
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
                        break;
                    case 2:
                        break;
                    case 3:
                        IdUbicacion = Integer.valueOf(txtUbicDestino.getText().toString());

                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra", IdUbicacion,
                                "pIdBodega", gl.IdBodega);
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
                    break;
                case 2:
                    break;
                case 3:
                    processValidaUbicacion();
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