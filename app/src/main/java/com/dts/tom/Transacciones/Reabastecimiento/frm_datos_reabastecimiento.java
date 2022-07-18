package com.dts.tom.Transacciones.Reabastecimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Transacciones.Movimiento.Trans_movimientos.clsBeTrans_movimientos;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res;

import static com.dts.tom.Transacciones.Reabastecimiento.frm_reabastecimiento_manual.selitem;

public class frm_datos_reabastecimiento extends PBase {

    private  frm_datos_reabastecimiento.WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtUbicOrigen, txtLipPlate, txtCodigoPrd, txtLote, txtVence,
                     txtEstado, txtCantidad, txtUbicDestino;
    private TextView lblUbicCompleta, lblDescProducto, lblUbicCompDestino, lblCantidad, lblCant;
    private TableRow trLicPlate;
    private CheckBox chkExplosionar;


    private clsBeTrans_movimientos gMovimientoDet = new clsBeTrans_movimientos();
    private clsBeVW_stock_res vStockRes = new clsBeVW_stock_res();

    private int vIdStockNuevo = 0;
    private int vIdMovimientoNuevo = 0;
    private int IdUbicacion = 0;

    private String vNuevoPalletId = "";

    private boolean explosionar = false;

    private clsBeBodega_ubicacion BeUbic = new clsBeBodega_ubicacion();

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
        lblCantidad = findViewById(R.id.lblCantidad);
        lblCant = findViewById(R.id.lblCant);

        chkExplosionar = findViewById(R.id.chkExplosionar);

        trLicPlate = findViewById(R.id.trLicPlate);
        txtUbicOrigen.setEnabled(false);

        Load();
        setHandlers();

    }

    private void setHandlers() {
        try {
            txtCodigoPrd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtUbicDestino.setOnClickListener(new View.OnClickListener() {
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

            txtUbicOrigen.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicOrigen.getText().toString().isEmpty()) {
                            if (Integer.valueOf(txtUbicOrigen.getText().toString()) == selitem.IdUbicacion) {
                                lblUbicCompleta.setVisibility(View.VISIBLE);
                                lblUbicCompleta.setText(selitem.Ubicacion_Nombre);

                                if (trLicPlate.getVisibility() != View.GONE) {
                                    txtLipPlate.requestFocus();
                                } else {
                                    txtCodigoPrd.requestFocus();
                                }

                            } else {
                                mu.msgbox("La ubicación no coincide.");
                            }
                        } else {
                            mu.msgbox("Ingrese la ubicación.");
                        }
                    }

                    return false;
                }
            });

            chkExplosionar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        explosionar = true;
                    } else {
                        explosionar = false;
                    }
                }
            });
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void CargaDatos() {
        try {

            txtCantidad.setEnabled(false);

            if (trLicPlate.getVisibility() != View.GONE) {
                txtCodigoPrd.setText(selitem.Codigo_Producto);
            }

            lblDescProducto.setVisibility(View.VISIBLE);
            lblDescProducto.setText( selitem.Codigo_Producto+" - "+selitem.Nombre_Producto);
            txtLote.setText(selitem.Lote);
            txtVence.setText(selitem.Fecha_Vence);
            txtEstado.setText(selitem.NomEstado);

            if (selitem.IdPresentacion > 0) {
                lblCantidad.setText("Cantidad ("+selitem.Nombre_Presentacion+"): ");

                selitem.CantidadPresentacion = selitem.CantidadReservadaUMBas / selitem.Factor;
                txtCantidad.setText(""+selitem.CantidadPresentacion);
                lblCant.setText(""+selitem.CantidadPresentacion);
            } else {
                lblCantidad.setText("Cantidad ("+selitem.UMBas+"): ");
                txtCantidad.setText(""+selitem.CantidadReservadaUMBas);
                lblCant.setText(""+selitem.CantidadReservadaUMBas);
            }

            txtUbicDestino.requestFocus();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Load() {
        try {
            lblDescProducto.setVisibility(View.GONE);
            lblUbicCompleta.setVisibility(View.GONE);

            txtUbicOrigen.setHint(""+selitem.IdUbicacion);
            txtUbicOrigen.setEnabled(true);

            if (!selitem.Lic_plate.isEmpty()) {
                txtLipPlate.setHint(selitem.Lic_plate);
            } else {
                trLicPlate.setVisibility(View.GONE);
            }

            txtCodigoPrd.setHint(selitem.Codigo_Producto);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void CreaMovimiento() {
        try {

            gMovimientoDet = new clsBeTrans_movimientos();

            gMovimientoDet.IdMovimiento = 0;
            gMovimientoDet.IdEmpresa = gl.IdEmpresa;
            gMovimientoDet.IdBodegaOrigen = gl.IdBodega;
            gMovimientoDet.IdTransaccion = 1;
            gMovimientoDet.IdPropietarioBodega = selitem.IdPropietarioBodega;
            gMovimientoDet.IdProductoBodega = selitem.IdProductoBodega;
            gMovimientoDet.IdUbicacionOrigen = selitem.IdUbicacion;
            gMovimientoDet.IdUbicacionDestino = Integer.valueOf(txtUbicDestino.getText().toString());

            if (selitem.IdPresentacion > 0) {
                gMovimientoDet.IdPresentacion = selitem.IdPresentacion;
            } else {
                gMovimientoDet.IdPresentacion = 0;
            }

            if(selitem.IdProductoEstado > 0){
                gMovimientoDet.IdEstadoOrigen = selitem.IdProductoEstado;
            }else{
                gMovimientoDet.IdEstadoOrigen = 0;
            }

            gMovimientoDet.IdEstadoDestino = selitem.IdProductoEstado;
            gMovimientoDet.IdUnidadMedida = selitem.IdUnidadMedida;
            gMovimientoDet.IdTipoTarea = 23;
            gMovimientoDet.IdBodegaDestino = gl.IdBodega;
            gMovimientoDet.IdRecepcion = selitem.IdRecepcionEnc;

            if (selitem.IdPresentacion > 0) {
                gMovimientoDet.Cantidad = selitem.CantidadPresentacion;
            } else {
                gMovimientoDet.Cantidad = selitem.CantidadUmBas;
            }

            gMovimientoDet.Serie = selitem.Serial;
            gMovimientoDet.Peso = selitem.Peso;
            gMovimientoDet.Lote = selitem.Lote;
            gMovimientoDet.Fecha_vence = selitem.Fecha_Vence;
            gMovimientoDet.Fecha = app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Barra_pallet = "";
            gMovimientoDet.Hora_ini =  app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Hora_fin =  app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Fecha_agr =  app.strFechaXML(du.getFechaActual());
            gMovimientoDet.Usuario_agr = String.valueOf(gl.IdOperador);
            gMovimientoDet.Cantidad_hist = gMovimientoDet.Cantidad;
            gMovimientoDet.Peso_hist = gMovimientoDet.Peso;
            gMovimientoDet.setIsNew(true);

            AplicarCambio();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() +" . " + e.getMessage());
        }
    }

    private void AplicarCambio() {
        try {
            vStockRes.IdProductoBodega = selitem.IdProductoBodega;
            vStockRes.IdUbicacion = selitem.IdUbicacion;
            vStockRes.Lote = selitem.Lote;
            vStockRes.Fecha_Vence = du.convierteFecha(selitem.Fecha_Vence);

            if (selitem.IdPresentacion > 0) {
                Double Cant = Double.valueOf(txtCantidad.getText().toString().replace(",",""));
                vStockRes.CantidadUmBas =  Cant * selitem.Factor;
            } else {
                vStockRes.CantidadUmBas = selitem.CantidadUmBas;
            }

            vStockRes.Peso = selitem.Peso;
            vStockRes.IdPresentacion = selitem.IdPresentacion;
            vStockRes.IdProductoEstado = selitem.IdProductoEstado;
            vStockRes.Fecha_ingreso = app.strFechaXML(du.getFechaActual());
            vStockRes.ValorFecha = app.strFechaXML(du.getFechaActual());
            vStockRes.Pallet_No_Estandar = selitem.Pallet_No_Estandar;
            vStockRes.Lic_plate = selitem.Lic_plate;

            execws(1);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processValidaUbicacion() {
        try {
            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                if ((BeUbic.Nivel < selitem.Ubicacion_Nivel || BeUbic.Ubicacion_picking)
                        && BeUbic.IdUbicacion != selitem.IdUbicacion){

                    lblUbicCompDestino.setText(BeUbic.Descripcion);
                    txtCantidad.requestFocus();

                    CreaMovimiento();
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

    private void processNuevoLPA(){

        try {

            vNuevoPalletId = "";

            clsBeResolucion_lp_operador resolucionActivaLpByBodega = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");

            if (resolucionActivaLpByBodega !=null){

                gl.IdResolucionLpOperador = resolucionActivaLpByBodega.IdResolucionlp;

                float pLpSiguiente = resolucionActivaLpByBodega.Correlativo_Actual +1;
                float largoMaximo = String.valueOf(resolucionActivaLpByBodega.Correlativo_Final).length();

                int intLPSig = (int) pLpSiguiente;
                int MaxL = (int) largoMaximo;

                //#CKFK20220410 Reemplacé el código de arriba por esta línea
                String result = String.format("%0"+ MaxL + "d",intLPSig);

                vNuevoPalletId= resolucionActivaLpByBodega.Serie + result;

            }else{
                gl.IdResolucionLpOperador =0;
                toast("Este usuario no tiene resoluciones configuradas");
            }

            if (!vNuevoPalletId.isEmpty()){
                //Set_Nuevo_Pallet_Id
                execws(4);
            }else{
                msgbox("Error al obtener correlativo de licencia!");
            }

        }catch (Exception e){
            mu.msgbox("processNuevoLP_packing: "+e.getMessage());
        }

    }

    private void processIdNuevoPallet(){

        try {

            boolean result = (Boolean) xobj.getSingle("Set_Nuevo_Pallet_IdResult",boolean.class);

            if (result){
                toast("Nuevo id pallet creado.");
                startActivity(new Intent(this, frm_reabastecimiento_manual.class));
            }else{
                msgbox("Ocurrió un error creando el Id del nuevo pallet");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processCompletaReabastecimiento() {
        try {
            boolean resultado = false;

            /*progress.setMessage("Procesando cambio");
            progress.show();*/

            resultado = (Boolean) xobj.getSingle("Aplica_Cambio_Estado_Ubic_HHResult",boolean.class);
            vIdStockNuevo = (Integer) xobj.getSingle("pIdStockNuevo",int.class);
            vIdMovimientoNuevo= (Integer) xobj.getSingle("pIdMovimientoNuevo",int.class);

            if( resultado){
                msgAsk("Reabastecimiento aplicado");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void msgAsk(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);
            dialog.setIcon(R.drawable.ic_quest);

            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    if (explosionar) {
                        msgAskExplosionar("¿Generar nuevo palletId y continuar?");
                    } else {
                        dialog.cancel();
                        startActivity(new Intent(frm_datos_reabastecimiento.this, frm_reabastecimiento_manual.class));
                    }

                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskExplosionar(String msg){

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(2);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
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
                        callMethod("Aplica_Cambio_Estado_Ubic_HH",
                                         "pMovimiento",gMovimientoDet,
                                               "pStockRes",vStockRes,
                                               "pIdStockNuevo",vIdStockNuevo,
                                               "pIdMovimientoNuevo",vIdMovimientoNuevo);
                        break;
                    case 2:
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                         "pIdOperador", gl.IdOperador,
                                               "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        IdUbicacion = Integer.valueOf(txtUbicDestino.getText().toString());

                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                         "pBarra", IdUbicacion,
                                               "pIdBodega", gl.IdBodega);
                        break;
                    case 4:
                        callMethod("Set_Nuevo_Pallet_Id",
                                         "pIdBodega",gl.IdBodega,
                                               "pIdUsuario",gl.OperadorBodega.getIdOperadorBodega(),
                                               "pLicPlateAnt",selitem.getLic_plate(),
                                               "pLicPlateNuevo", vNuevoPalletId,
                                               "pIdStockNuevo", vIdStockNuevo,
                                               "pIdMovimientoNuevo",vIdMovimientoNuevo);
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
                    processCompletaReabastecimiento();
                    break;
                case 2:
                    processNuevoLPA();
                    break;
                case 3:
                    processValidaUbicacion();
                    break;
                case 4:
                    processIdNuevoPallet();
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