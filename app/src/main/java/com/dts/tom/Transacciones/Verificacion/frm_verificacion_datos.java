package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_imagen.clsBeProducto_imagen;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Picking.Trans_Picking_Img.clsBeTrans_picking_imgList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_img.clsBeTrans_re_imgList;
import com.dts.classes.clsBeImagen;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.ProcesaImagen.frm_imagenes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.zbra.androidlinq.delegate.SelectorDouble;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Verificacion.frm_list_prod_reemplazo_verif.reemplazoCorrecto;

import androidx.core.content.FileProvider;

public class frm_verificacion_datos extends PBase {

    private frm_verificacion_datos.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtVenceVeri,txtCantVeri,txtPesoVeri, txtUmbasVeri, txtLoteVeri, txtCajas, txtUnidades, txtPreSol, txtPresRec, txtUnidadSol, txtUnidadRec;
    private TextView lblTituloForma,lblLicPlate2,lblVenceVeri,lblCantVeri,lblPesoVeri, lblUmbasVeri, lblLoteVeri, lblPresentacion,lblPresRec, lblPresSol, lblUnidadSol, lblUnidadRec;
    private Button btMarcarReemplazoVeri,btnConfirmarV,btnBack;
    private Spinner cmbPresVeri;
    private LinearLayout llFechaVence,llLote, llPresentacion, llCantidad, llPeso, llUMBas, llReemplazo,llBono;
    private RelativeLayout relDesglose, relPick, relReemplazo;
    private EditText txtPresPick, txtUnidadPick;
    private TextView lblPresPick, lblUnidadPick;
    private FloatingActionButton btnTareas;
    private CheckBox chkBono;
    private clsBeTrans_picking_ubicList BePickingUbicList = new clsBeTrans_picking_ubicList();

    public static clsBeProducto gBeProducto = new clsBeProducto();
    public static clsBeProducto_PresentacionList tmpBePresentacion = new clsBeProducto_PresentacionList();
    public static clsBeDetallePedidoAVerificar BePedidoDetVerif = new clsBeDetallePedidoAVerificar();
    public static clsBeTrans_picking_ubicList pSubListPickingU = new clsBeTrans_picking_ubicList();
    private clsBeProducto_Presentacion auxPres = new clsBeProducto_Presentacion();

    private int IdProductoBodega;
    private String Lp;

    public static double CantReemplazar=0;
    public static double AuxCantReemplazar=0;

    private final ArrayList<String> PresList = new ArrayList<String>();

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
    private int pTipo = 0;
    //#GT10062022: variable para guardar el peso del picking...luego se modifique el editext, no hay contra que validar el peso original
    private double peso_picking = 0;
    // Fecha
    private int anno;
    private int mes;
    private int dia;
    static final int DATE_DIALOG_ID = 999;
    private DatePicker dpResult;
    private ImageView imgDate;

    //Imagen
    private String encoded="";
    private clsBeImagen BeImagen;
    private clsBeTrans_picking_imgList BeListTransPickingImagen =  new clsBeTrans_picking_imgList();

    private RelativeLayout relOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_verificacion_datos);

        super.InitBase();

        ws = new frm_verificacion_datos.WebServiceHandler(frm_verificacion_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtVenceVeri = findViewById(R.id.txtVenceVeri);
        txtCantVeri = findViewById(R.id.txtCantVeri);
        txtPesoVeri = findViewById(R.id.txtPesoVeri);
        txtUmbasVeri = findViewById(R.id.txtUmbasVeri);
        txtLoteVeri = findViewById(R.id.txtLoteVeri);
        lblVenceVeri = findViewById(R.id.lblVenceVeri);
        lblCantVeri = findViewById(R.id.lblCantVeri);
        lblPesoVeri = findViewById(R.id.lblPesoVeri);
        lblUmbasVeri = findViewById(R.id.lblUmbasVeri);
        lblLoteVeri = findViewById(R.id.lblLoteVeri);
        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblLicPlate2 = findViewById(R.id.lblLicPlate2);
        btnBack = findViewById(R.id.btnBack);
        cmbPresVeri = findViewById(R.id.cmbPresVeri);
        llFechaVence = findViewById(R.id.llFechaVence);
        llLote = findViewById(R.id.llLote);
        llPresentacion = findViewById(R.id.llPresentacion);
        llCantidad = findViewById(R.id.llCantidad);
        llPeso = findViewById(R.id.llPeso);
        llUMBas = findViewById(R.id.llUMBas);
        dpResult = findViewById(R.id.datePicker2);
        imgDate = findViewById(R.id.imgDate3);
        txtCajas = findViewById(R.id.txtCajas);
        txtUnidades = findViewById(R.id.txtUnidades);
        lblPresentacion = findViewById(R.id.lblPresentacion);
        relDesglose = findViewById(R.id.relDesglose);
        txtPreSol = findViewById(R.id.txtPresSol);
        txtUnidadSol = findViewById(R.id.txtUnidadSol);
        txtUnidadRec = findViewById(R.id.txtUnidadRec);
        txtPresRec = findViewById(R.id.txtPresRec);
        lblPresRec = findViewById(R.id.lblPresRec);
        lblPresSol = findViewById(R.id.lblPresSol);
        lblUnidadSol = findViewById(R.id.lblUnidadSol);
        lblUnidadRec = findViewById(R.id.lblUnidadRec);
        txtPresPick = findViewById(R.id.txtPresPick);
        txtUnidadPick = findViewById(R.id.txtUnidadPick);
        lblPresPick = findViewById(R.id.lblPresPick);
        lblUnidadPick = findViewById(R.id.lblUnidadPick);
        btnTareas = findViewById(R.id.btnTareas);
        relPick = findViewById(R.id.relPick);
        relReemplazo = findViewById(R.id.relReemplazo);
        relOpciones = findViewById(R.id.relOpciones);
        llBono = findViewById(R.id.llBono);
        chkBono = findViewById(R.id.chkBono);

        BePickingUbicList = gl.gBePickingUbicList;
        pTipo = 0;

        setCurrentDateOnView();

        setHandlers();

        ProgressDialog("Cargando forma...");

        Load();

    }

    private void Load() {

        try {
            //#AT20230214 Se muestra el boton si Permitir_Reemplazo_Verificacion = verdadero
            if (gl.Permitir_Reemplazo_Verificacion) {
                relReemplazo.setVisibility(View.VISIBLE);
            }

            BePedidoDetVerif = gl.gBePedidoDetVerif;
            //gl.gBePedidoDetVerif = new clsBeDetallePedidoAVerificar();

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
                                    //#GT10062022: porque usar un metodo que valida cantidad, para validar peso,
                                    // dejando las mismas variables??
                                    //valida_Valor(txtPesoVeri);
                                    valida_Valor_Peso(txtPesoVeri);
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

            if (gl.VerificacionSinLoteFechaVen) {
                lblTituloForma.setText(String.format("Prod: %s-%s",
                        Codigo, Nombre));
            } else {
                lblTituloForma.setText(String.format("Prod: %s-%s Expira: %s Lote: %s",
                        Codigo, Nombre, Expira, Lote));
            }

            if (gBeProducto == null){
                throw new Exception("El producto no está definido");
            }else{

                if (BePickingUbicList !=null){

                    if (BePickingUbicList.items !=null){
                        if  (gl.VerificacionConsolidada) {
                            AuxList = stream(BePickingUbicList.items)
                                    .where (z -> z.CodigoProducto.equals(Codigo))
                                    .toList();
                        } else {
                            if (Lp.equals("")) {
                                AuxList = stream(BePickingUbicList.items)
                                        .where(z -> z.CodigoProducto.equals(Codigo))
                                        .where(z -> z.Lote.equals(Lote))
                                        .where(z -> app.strFecha(z.Fecha_Vence).equals(Expira))
                                        .toList();
                            } else {
                                AuxList = stream(BePickingUbicList.items)
                                        .where(z -> z.CodigoProducto.equals(Codigo))
                                        .where(z -> z.Lote.equals(Lote))
                                        .where(z -> z.Lic_plate.equals(Lp))
                                        .where(z -> app.strFecha(z.Fecha_Vence).equals(Expira))
                                        .toList();
                            }
                        }

                        pSubListPickingU.items = AuxList;
                    }

                }

            }

            if (gl.gFotografiaVerificacion){
                relOpciones.setVisibility(View.VISIBLE);
            }else{
                relOpciones.setVisibility(View.INVISIBLE);
            }

            if (BePedidoDetVerif.Fecha_Vence.equals("01/01/1900")) {
                llFechaVence.setVisibility(View.GONE);
            }

            txtVenceVeri.setText(Expira);
            txtCantVeri.setText(String.valueOf(Rec-Ver));

            //txtUmbasVeri.setText(UM);
            if (BePedidoDetVerif.Lote.isEmpty()) {
                llLote.setVisibility(View.GONE);
            }
            txtLoteVeri.setText(BePedidoDetVerif.getLote());

            chkBono.setChecked(BePedidoDetVerif.Bono.equals("Bono"));

            int sel = PresList.indexOf(BePedidoDetVerif.getIdPresentacion()+ " - " +
                                       BePedidoDetVerif.getNom_Presentacion());

            //#CKFK20220326 Agregué esta validación para que si el producto no tiene presentación
            //no se muestre el combo de la presentación
            //if (sel>-1){
                //llPresentacion.setVisibility(View.VISIBLE);
                //cmbPresVeri.setSelection(sel);

            //#AT20220419 Se muestra Cajas y Unidades en cantidad solicitada y recibida
            if (BePedidoDetVerif.getIdPresentacion() > 0){
                lblCantVeri.setText("Cantidad ("+BePedidoDetVerif.getNom_Presentacion()+"): ");

                if (gBeProducto.Presentaciones!=null){
                    if (gBeProducto.Presentaciones.items!=null){

                        auxPres = stream(gBeProducto.Presentaciones.items).where(c-> c.IdPresentacion == BePedidoDetVerif.IdPresentacion).first();
                        factor = auxPres.Factor;

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

                                lblPresRec.setText(auxPres.Nombre+":");
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

                            lblPresRec.setText(auxPres.Nombre+":");
                            txtPresRec.setText("0.00");
                            txtUnidadRec.setText("0.00");
                        }

                        //#AT20220525 Cantidad Pickeada
                        double CantidadPick = Rec;
                        double CantidadDecimalPick = CantidadPick % 1;
                        double CantidadPresentacionPick = 0;
                        CantidadPresentacionPick = CantidadPick - CantidadDecimalPick;
                        double CantidadUMBasPick = CantidadDecimalPick * factor;

                        if (Rec >  0) {
                            btnTareas.setVisibility(View.VISIBLE);
                            txtCantVeri.setEnabled(true);

                            if (CantidadPresentacionPick > 0) {

                                if ((CantidadPick % 1) > 0 || (CantidadPick >= factor)) { }

                                if (CantidadUMBasPick == 0) {
                                    txtUnidadPick.setVisibility(View.GONE);
                                    lblUnidadPick.setVisibility(View.GONE);
                                }

                                lblPresPick.setText(auxPres.Nombre+":");
                                txtPresPick.setText(String.valueOf(mu.frmdec(CantidadPresentacionPick)));
                                txtUnidadPick.setText(String.valueOf(mu.frmdec(CantidadUMBasPick)));

                            }else if ((CantidadPick % 1) > 0 || (factor > 0)) {
                                lblPresPick.setVisibility(View.GONE);
                                txtPresPick.setVisibility(View.GONE);

                                txtUnidadPick.setText(String.valueOf(mu.frmdec(CantidadUMBasPick)));
                            }
                        } else {
                            btnTareas.setVisibility(View.GONE);
                            txtCantVeri.setEnabled(false);
                            if (CantidadUMBas == 0) {
                                lblUnidadPick.setVisibility(View.GONE);
                                txtUnidadPick.setVisibility(View.GONE);
                            }

                            if (CantidadPresentacion == 0) {
                                lblPresPick.setVisibility(View.GONE);
                                txtPresPick.setVisibility(View.GONE);
                            }

                            lblPresPick.setText(auxPres.Nombre+":");
                            txtPresPick.setText(""+Rec);
                        }
                    }
                }
            } else {

                lblCantVeri.setText("Cantidad ("+UM+"): ");

                if (gBeProducto.Presentaciones != null) {
                    factor = gBeProducto.Presentaciones.items.get(0).Factor;
                }else{
                    factor = 0;
                }

                double CantSol = Sol;
                double CantRec = Ver;
                double CantPick = Rec;

                if (CantSol > factor && factor > 0) {

                    //#AT Cantidad Solicitada
                    double cantidadPresentacion = CantSol / factor;
                    double decimal = cantidadPresentacion % 1;
                    double cajas = cantidadPresentacion - decimal;
                    double unidades = decimal * factor;

                    if (gBeProducto.Presentaciones != null) {
                        lblPresSol.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");
                    }

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

                    //#AT Cantidad Pickeada
                    double cantidadPresentacionPick = CantPick / factor;
                    double decimalPick = cantidadPresentacionPick % 1;
                    double cajasPick = cantidadPresentacionPick - decimalPick;
                    double unidadesPick = decimalPick * factor;

                    lblPresPick.setText(gBeProducto.Presentaciones.items.get(0).Nombre+":");

                    txtUnidadPick.setVisibility(unidadesPick > 0 ? View.VISIBLE: View.GONE);
                    lblUnidadPick.setVisibility(unidadesPick > 0 ? View.VISIBLE: View.GONE);

                    txtPresPick.setText(String.valueOf(mu.frmdec(cajasPick)));
                    txtUnidadPick.setText(String.valueOf(mu.frmdec(unidadesPick)));

                } else {
                    lblPresSol.setVisibility(View.GONE);
                    txtPreSol.setVisibility(View.GONE);
                    lblPresRec.setVisibility(View.GONE);
                    txtPresRec.setVisibility(View.GONE);
                    lblPresPick.setVisibility(View.GONE);
                    txtPresPick.setVisibility(View.GONE);

                    txtUnidadSol.setText(""+mu.frmdec(Sol));
                    txtUnidadRec.setText(""+mu.frmdec(Ver));
                    txtUnidadPick.setText(""+mu.frmdec(Rec));
                }
                //llPresentacion.setVisibility(View.GONE);

                execws(4);
            }

            if (Lp != ""){
                lblLicPlate2.setVisibility(View.VISIBLE);
                lblLicPlate2.setText(String.format("Licencia: %s", Lp));
            }

            txtPesoVeri.setText(mu.frmdecimal(0, gl.gCantDecDespliegue));

            if (pSubListPickingU !=null) {
                if (pSubListPickingU.items != null) {
                    suma = stream(pSubListPickingU.items)
                            .select(clsBeTrans_picking_ubic::getPeso_recibido)
                            .sum((SelectorDouble<Double>) z -> z);

                    txtPesoVeri.setText(mu.frmdecimal(suma, gl.gCantDecDespliegue));

                    if (suma>0){
                        peso_picking = suma;
                        llPeso.setVisibility(View.VISIBLE);
                    }

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

            lblPresSol.setText(auxPres.Nombre+":");
            txtPreSol.setText(String.valueOf(CantPresentacion));
            txtUnidadSol.setText(String.valueOf(mu.frmdec(CantidadUMBas)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private final boolean HicimosUnaFumada=false;

    private void calculaUnidades(double CantidadUMBas) {

        try {

            lblPresSol.setText(auxPres.Nombre+":");
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
                                gBeProducto.Presentaciones.items.get(i).getNombre();

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

    //#GT10062022: esto valida si la cantidad coincide con lo pickeado, pero no se debe usar para validar el peso del producto
    private boolean valida_Valor(EditText txt){

        boolean result = false;

        try{
            Double cantPendiente = Rec - Ver;


            //#GT10062022: antes de castear el valor, se le debe remover la "," porque no forma parte del valor númerico
            //double valor =Double.valueOf(txt.getText().toString());
            double valor =Double.valueOf(txt.getText().toString().replace(",",""));

            //#AT20220525 Validación de cantidad antes de verificar
            if (valor > 0) {
                if (valor > BePedidoDetVerif.Cantidad_Recibida) {
                    mu.msgbox("La cantidad ingresada es mayor a la pickeada");
                    result = false;
                } else if (valor > cantPendiente) {
                    mu.msgbox("La cantidad ingresada es mayor a la pendiente de verificar");
                    result = false;
                } else {
                    result = true;
                }

            }else {
                mu.msgbox("El valor es incorrecto, ingréselo nuevamente");
                result = false;
            }

        }catch (Exception ex){
            result = false;
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName() + " " + ex.getMessage());
            txt.selectAll();
            txt.requestFocus();
            //showkeyb();
        }

        return result;

    }

    //#GT10062022: esto valida si el peso coincide con el peso pickeado. Es una copia del proceso valida_valor, porque lo usaban para lo mismo
    private boolean valida_Valor_Peso(EditText txt){

        boolean result = false;

        try{

            //Double cantPendiente = Rec - Ver;

            //#GT10062022: antes de castear el valor, se le debe remover la "," porque no forma parte del valor númerico
            //double valor =Double.valueOf(txt.getText().toString());
            double valor =Double.valueOf(txt.getText().toString().replace(",",""));

            Double peso_conversion = Double.valueOf(mu.frmdecimal(peso_picking, gl.gCantDecDespliegue));

            peso_picking = peso_conversion;

            if(Objects.equals(valor, peso_picking)){
                result = true;
            }else{
                result = true;
                //#CKFK2023 Puse esto en comentario porque cuando sea fiscal
                // no se va a poder modificar el peso
               /* if (valor > 0) {
                    if (valor > peso_picking) {
                        mu.msgbox("El peso ingresado es mayor al pickeado");
                        result = false;
                    } else if(valor< peso_picking) {
                        mu.msgbox("El peso ingresado es menor al pickeado");
                        result = false;
                    }
                }*/
            }


//                if (valor > 0) {
//                    if (valor > peso_picking) {
//                        mu.msgbox("El peso ingresado es mayor al pickeado");
//                        result = false;
//                    } else if(valor< peso_picking) {
//                        mu.msgbox("El peso ingresado es menor al pickeado");
//                        result = false;
//                    }
//
//                }else {
//                    mu.msgbox("El valor del peso es incorrecto, ingréselo nuevamente");
//                    result = false;
//                }


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

            if (gBeProducto.Control_peso){
                //#GT10062022: para validar el peso, no usar mismo metodo que valida cantidad
                //if (!valida_Valor(txtPesoVeri)){
                if (!valida_Valor_Peso(txtPesoVeri)){
                    return result;
                }
            }

            pCantidad = Double.parseDouble(txtCantVeri.getText().toString());

            String vPeso = txtPesoVeri.getText().toString();

            if(!vPeso.isEmpty()){
                if(vPeso.contains(",")){
                    vPeso = vPeso.replace(",","");
                }
            }

            pPeso =  Double.parseDouble(vPeso);

            pSubListPickingU = new clsBeTrans_picking_ubicList();

            if (BePickingUbicList!=null){

                if (gl.VerificacionSinLoteFechaVen) {
                    if (Lote.equals("") && Expira.equals("01/01/1900")) {
                        int tmpPresentacion = BePedidoDetVerif.IdPresentacion > 0 ? BePedidoDetVerif.IdPresentacion: 0;

                        AuxList = stream(BePickingUbicList.items)
                                .where(c -> c.CodigoProducto.equals(Codigo))
                                .where(c -> c.IdPresentacion == tmpPresentacion)
                                .where(c -> c.getCantidad_Recibida() - c.getCantidad_Verificada() != 0)
                                .toList();

                        pSubListPickingU.items = AuxList;
                    }
                } else {
                    if (Lp.equals("")) {
                        AuxList = stream(BePickingUbicList.items)
                                .where(z -> z.CodigoProducto.equals(BePedidoDetVerif.Codigo))
                                .where(z -> z.Lote.equals(BePedidoDetVerif.Lote))
                                .where(z -> app.strFecha(z.Fecha_Vence).equals(BePedidoDetVerif.Fecha_Vence))
                                .where(c -> c.getCantidad_Recibida() - c.getCantidad_Verificada() != 0)
                                .toList();
                    } else {
                        AuxList = stream(BePickingUbicList.items)
                                .where(c -> c.CodigoProducto.equals(Codigo))
                                .where(c -> c.Lote.equals(Lote))
                                .where(c -> (app.strFecha(c.Fecha_Vence).equals(Expira)))
                                .where(c -> c.Lic_plate.equals(Lp))
                                .where(c -> c.getCantidad_Recibida() - c.getCantidad_Verificada() != 0)
                                .toList();

                    }

                    pSubListPickingU.items = AuxList;
                }

                //pSubListPickingU.items = AuxList;
            }else{
                throw new Exception("#ERR_20220612: La lista de verificación es nula.");
            }

            //Llama al método del WS Actualiza_Cant_Peso_Verificacion
            if (pSubListPickingU!=null){
                if(pSubListPickingU.items!=null){
                    if (pSubListPickingU.items.size()!=0){
                        if (gl.VerificacionSinLoteFechaVen) {
                            pTipo = 1;
                        }
                        execws(3);
                    }else{
                        throw new Exception("#ERR_20220406: No se obtuvo la lista de verificación.");
                    }
                }else{
                    throw new Exception("#ERR_20220612: No se obtuvo la lista de verificación.");
                }
            }else{
                throw new Exception("#ERR_20220612: No se obtuvo la lista de verificación.");
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
                                   "pPeso",pPeso,
                                   "pTipo", pTipo);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",gBeProducto.getIdProducto(),"pActivo",true);
                        break;

                    case 5:
                        callMethod("Guardar_Fotos_Verificacion",
                                "pIdPedidoDet",BePedidoDetVerif.IdPedidoDet,
                                "Foto",encoded);
                        break;
                    case 6:
                        callMethod("Get_All_Imagen_Verificacion",
                                "pIdPedidoDet", BePedidoDetVerif.IdPedidoDet);
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
                case 5:
                    processEnviarFoto();
                    break;
                case 6:
                    processGetFotosVerificacion();
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
            app.readOnly(txtPesoVeri,gl.es_bodega_fiscal);

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

            //Listar_Producto_Presentaciones();
            Inicia_Tarea_Detalle();

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

                    if (gl.VerificacionConsolidada) {
                        if (pSubListPickingU != null) {
                            if (pSubListPickingU.items != null) {
                                if (pSubListPickingU.items.size() > 0) {
                                    startActivity(new Intent(frm_verificacion_datos.this, frm_verificacion_consolidada_detalle.class));
                                    browse = 1;
                                }
                            }
                        }
                    } else {
                        Reemplazar();
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

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

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

            return "0" + c;
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
            AuxCantReemplazar = valor;
            msgAskReemplazar("¿Marcar producto para reemplazo?");

        }catch (Exception e){
            mu.msgbox("BotonReemplazo:"+e.getMessage());
        }
    }

    //region

    public void CapturarVerificacion(View view) {
        abrirCamara();
    }

    public void verImagenesVerificacion(View view) {
        progress.setMessage("Cargando imágenes verificación...");
        progress.show();
        execws(6);
    }

    private void abrirCamara() {
        try{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.dts.tom.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }catch (Exception ee){
            mu.msgbox(ee.getMessage());
        }

    }

    String currentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private File  createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void processEnviarFoto() {
        boolean exito;

        try {
            progress.setMessage("Guardando imagen...");
            progress.show();

            exito = xobj.getresult(Boolean.class,"Guardar_Fotos_Verificacion");

            if (exito) {
                progress.cancel();
                toastlong("Foto guardada con éxito");
            }
        } catch (Exception e) {
            progress.cancel();
            msgbox("processEnviarFoto: " + e.getMessage());
        }

    }

    private void processGetFotosVerificacion() {

        try {

            progress.setMessage("Cargando imágenes...");
            BeListTransPickingImagen = xobj.getresult(clsBeTrans_picking_imgList.class,"Get_All_Imagen_Verificacion");

            gl.ListImagen.clear();

            if (BeListTransPickingImagen != null) {
                if (BeListTransPickingImagen.items != null) {

                    for (int i=0; i < BeListTransPickingImagen.items.size(); i++) {
                        BeImagen = new clsBeImagen();
                        BeImagen.Descripcion = "Imagen " + BeListTransPickingImagen.items.get(i).IdImagen;
                        BeImagen.Imagen = BeListTransPickingImagen.items.get(i).Imagen;
                        gl.ListImagen.add(BeImagen);
                    }
                } else {
                    progress.cancel();
                    return;

                }
            } else {
                progress.cancel();
                return;

            }
            startActivity(new Intent(this, frm_imagenes.class));
            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            msgbox("processGetFotosVerificacion: "+ e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            //aquí la convierto a base 64
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            execws(5);
        }
    }

    //end region

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
                reemplazoCorrecto = false;
                browse=0;
                super.finish();
            }
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
