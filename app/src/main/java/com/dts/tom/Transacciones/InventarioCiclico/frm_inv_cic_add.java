package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico;
import com.dts.classes.Transacciones.Inventario.Inventario_Ciclico.clsBeTrans_inv_ciclico_vw;
import com.dts.tom.Mainmenu;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.google.common.collect.Table;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioCiclico.frm_inv_cic_conteo.NuevoConteo;

public class frm_inv_cic_add extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private Button btnBack_cic,btAdelante,btAtras, btGuardar;
    private ImageView imgDate, btnSetLicencia;
    private EditText txtUbic,txtProd,txtLote1,txtCantContada,txtPesoContado,dtpVence, txtLicencia, txtUbicNueva;
    private Spinner cboEstado,cboPres;
    private TextView txtlote_cic,lblCantStock,lblUM,lblUbic1,lblProd,txtFecha_cic,txtpeso_cic,lbltitulo_cic, lblCantidadContada, lblUbicNueva;
    private TableRow tblote_cic, tblVence, tblNuevaUbic;
    private int idPresentacion;
    private int year;
    private int month;
    private int day;
    private int IdProductoBodega,idubic, idstock, tam_lista;
    private double vFactor;
    private String Resultado;
    private int Index;
    //private int adelante,atras;
    private String codigo_producto;

    private final ArrayList<String> bodlist= new ArrayList<String>();
    private final ArrayList<String> PresList= new ArrayList<String>();
    private final ArrayList<Integer> IndexPresList= new ArrayList<Integer>();

    private  clsBeTrans_inv_ciclico pitem;
    private clsBeTrans_inv_ciclico BeTrans_inv_ciclico;
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();

    private boolean nuevoRegistro;

    //variables para obtener id de los combobox
    private int IdEstadoselected, IdPresentacionselected;

    //obtiene el id máximo de inventario ciclico
    private int IDInventarioCiclico;

    private final boolean noubicflag = false;

    //Nueva cantidad a enviar cuando el registro no es pendiente, sino ya contado
    private double Nueva_Cantidad;

    //respuesta de validación
    boolean respuesta_producto;
    private boolean existeConteo = false, esOriginal = false;

    private clsBeBodega_ubicacion ubicacion = new clsBeBodega_ubicacion();
    private clsBeProducto BeProductoUbicacion = new clsBeProducto();
    private clsBeProducto_estadoList listaEstados = new clsBeProducto_estadoList();
    private double CantidadContada = 0;
    private clsBeTrans_inv_ciclico pItemBase = new clsBeTrans_inv_ciclico();
    private clsBe_inv_reconteo_data auxInvCiclico = new clsBe_inv_reconteo_data();
    private int IdUbicacion = 0;
    private boolean esCambioUbicacion = false;
    private clsBeTrans_inv_ciclico invCongelado = new clsBeTrans_inv_ciclico();
    private clsBeProducto gBeProducto = new clsBeProducto();
    private int IdStock = 0;
    private boolean esInvCongelado = false;
    private String NomPresentacion = "";
    private int pIdUbicacion = 0;
    private clsBeResolucion_lp_operador nBeResolucion = null;
    private String pNumeroLP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_add);
        super.InitBase();

        btGuardar = findViewById(R.id.btnGuardar);
        btAdelante = findViewById(R.id.btAdelante);
        btAtras = findViewById(R.id.btAtras);
        btnBack_cic = findViewById(R.id.btnBack_cic);
        txtUbic = findViewById(R.id.txtUbic);
        txtProd = findViewById(R.id.txtProd);
        lblUbic1 = findViewById(R.id.lblUbic1);
        lblProd = findViewById(R.id.lblProd);

        cboEstado = findViewById(R.id.cboEstado);
        cboPres = findViewById(R.id.cboPres);
        txtLote1 = findViewById(R.id.txtLote1);
        dtpVence = findViewById(R.id.dtpVence);

        lblCantStock = findViewById(R.id.lblCantStock);
        txtCantContada = findViewById(R.id.txtCantContada);
        txtPesoContado = findViewById(R.id.txtPesoContado);
        lblUM = findViewById(R.id.lblUM);

        txtlote_cic = findViewById(R.id.lblNLote);
        txtFecha_cic = findViewById(R.id.lblNVence);
        imgDate = findViewById(R.id.imgDate);
        txtpeso_cic = findViewById(R.id.txtpeso_cic);
        lbltitulo_cic = findViewById(R.id.lbltitulo_cic);

        tblote_cic = findViewById(R.id.tblote_cic);
        tblVence = findViewById(R.id.tblVence);
        txtLicencia = findViewById(R.id.txtLicencia);
        lblCantidadContada = findViewById(R.id.lblCantidadContada);

        tblNuevaUbic = findViewById(R.id.tblNuevaUbic);
        txtUbicNueva = findViewById(R.id.txtUbicNueva);
        lblUbicNueva = findViewById(R.id.lblUbicNueva);
        btnSetLicencia = findViewById(R.id.btnSetLicencia);

        idPresentacion =0;
        vFactor = 0.00;
        IdProductoBodega = 0;
        idubic = 0;
        IDInventarioCiclico = 0;

        cboPres.setEnabled(false);

        Index = 0;
        tam_lista = 0;

        ws = new WebServiceHandler(frm_inv_cic_add.this,gl.wsurl);
        xobj = new XMLObject(ws);

        btGuardar.setEnabled(false);

        respuesta_producto = false;

        ValidaBotones();

        if (!NuevoConteo) {
            Load();
        } else {
            IdStock = 0;
            lblProd.setText("");
            lblUbic1.setText("");
            txtUbic.requestFocus();
            btnSetLicencia.setVisibility(View.VISIBLE);
            txtLicencia.setEnabled(true);

            btAdelante.setVisibility(View.GONE);
            btAtras.setVisibility(View.GONE);
            lblCantidadContada.setVisibility(View.GONE);

            if (gl.ubicacionInv != 0) {
                txtUbic.setText(gl.ubicacionInv+"");
                pIdUbicacion = gl.ubicacionInv;
                execws(6);
            }
        }

        setHandlers();
    }



    private void setHandlers() {
        try{

            txtUbicNueva.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!txtUbicNueva.getText().toString().isEmpty()) {

                        if (!txtUbic.getText().toString().equals(txtUbicNueva.getText().toString())) {
                            pIdUbicacion = Integer.valueOf(txtUbicNueva.getText().toString());
                            execws(6);
                        } else {
                            msgbox("La ubicación deber ser diferente a la de origen.");
                        }
                    } else {
                        lblUbicNueva.setText("");
                        tblNuevaUbic.setVisibility(View.GONE);
                    }
                }
                return false;
            });

            txtProd.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (NuevoConteo) {
                        if (!txtProd.getText().toString().isEmpty()) {
                            execws(7);
                        } else {
                            toast("Ingrese código de producto.");
                        }
                    } else {
                        codigo_producto = txtProd.getText().toString().trim();
                        IdUbicacion = gl.inv_ciclico.NoUbic;

                        //GT03120202: la busqueda por LP esta anidada dentro de scan_codigo_producto
                        if (Scan_Codigo_Producto()) {
                            btGuardar.setEnabled(true);
                            respuesta_producto = false;
                        } else {
                            respuesta_producto = false;
                        }
                    }
                }

                return respuesta_producto;
            });

            txtCantContada.setOnKeyListener(((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnGuardar(v);
                }

                return false;
            }));

            cboEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        TextView spinlabel = (TextView) parentView.getChildAt(0);

                        if (spinlabel != null){
                            spinlabel.setTextColor(Color.BLACK);
                            spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                            spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                        }

                        /*if (!NuevoConteo) {
                            IdEstadoselected = gl.lista_estados.items.get(position).IdEstado;

                        } else {
                            if (NuevoConteo || existeConteo) {
                                clsBeProducto_estado aux = (clsBeProducto_estado) parentView.getItemAtPosition(position);
                                IdEstadoselected = aux.IdEstado;
                            }
                        }*/

                        clsBeProducto_estado aux = (clsBeProducto_estado) parentView.getItemAtPosition(position);
                        IdEstadoselected = aux.IdEstado;
                        gl.inv_ciclico.IdProductoEst_nuevo = IdEstadoselected;

                    } catch (Exception e) {
                        msgbox("setOnItemSelectedListener - " + e.getMessage());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cboPres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                {
                    try
                    {
                        TextView spinlabel = (TextView) parentView.getChildAt(0);

                        if(spinlabel != null){
                            spinlabel.setTextColor(Color.BLACK);
                            spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                            spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                        }

                        clsBeProducto_Presentacion aux = (clsBeProducto_Presentacion) parentView.getItemAtPosition(position);
                        IdPresentacionselected = aux.IdPresentacion;
                        vFactor = aux.Factor;
                        NomPresentacion = aux.Nombre;

                        /*if (!NuevoConteo) {
                            IdPresentacionselected = IndexPresList.get(position);
                        } else {
                            if (NuevoConteo || existeConteo) {

                            }
                        }*/

                    } catch (Exception e)
                    {
                        msgbox(e.getMessage());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            txtLote1.setOnKeyListener((v, keyCode, event) -> {

                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                       if(txtLote1.getText().toString().trim().isEmpty()){

                           mu.msgbox("Lote no puede estar vacio!");

                       }else {

                           btGuardar.setEnabled(true);
                           imgDate.requestFocus();

                       }
                }

                return false;
            });

            txtUbic.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!txtUbic.getText().toString().isEmpty()) {
                        pIdUbicacion = Integer.valueOf(txtUbic.getText().toString());
                        execws(6);
                    } else {
                        toast("Ingrese ubicación");
                        lblUbic1.setText("");
                        lbltitulo_cic.setText("");
                        txtUbic.requestFocus();
                    }
                }

                return false;
            });

            btnSetLicencia.setOnClickListener(view -> {
                btnSetLicencia.setEnabled(false);
                txtLicencia.setText("");
                execws(13);
            });

        }
        catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }
    }

    private void Load() {
        try {
            if (gl.inv_ciclico != null) {

                //#AT20241205 Validar si es el registro es original//#AT20241205 Validar si es el registro es original
                esOriginal = gl.inv_ciclico.IdProductoEstado == gl.inv_ciclico.IdProductoEst_nuevo &&
                        gl.inv_ciclico.Fecha_Vence.equals(gl.inv_ciclico.Fecha_Vence_Stock) &&
                        gl.inv_ciclico.Lote.equals(gl.inv_ciclico.Lote_stock) &&
                        gl.inv_ciclico.IdUbicacion_nuevo == 0;

                //Index para determinar el registro seleccionado de la lista para avanzar o retroceder y tam_list para saber minimo y maximo a recorrer
                Index = gl.IndexCiclico;
                tam_lista = gl.reconteo_list.size() - 1;

                //index para el combobox estados
                int index = 0;

                if (gl.inv_ciclico.Factor.toString().isEmpty() || gl.inv_ciclico.Factor == 0) {
                    vFactor = 0;
                } else {
                    vFactor = gl.inv_ciclico.Factor;
                }

                idPresentacion = gl.inv_ciclico.IdPresentacion;

                //validaciones para obtener lista de estados por idPropietario
                if (gl.lista_estados != null) {

                    if (gl.lista_estados.items != null) {

                        ArrayAdapter<clsBeProducto_estado> EstadosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gl.lista_estados.items);
                        EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboEstado.setAdapter(EstadosAdapter);

                        int estado = (gl.inv_ciclico.cantidad > 0 && gl.inv_ciclico.IdProductoEst_nuevo != 0) ? gl.inv_ciclico.IdProductoEst_nuevo : gl.inv_ciclico.IdProductoEstado;

                        int indice = gl.lista_estados.items.stream()
                                .filter(obj -> obj.getIdEstado() == estado)
                                .map(gl.lista_estados.items::indexOf)
                                .findFirst()
                                .orElse(-1);

                        if (indice != -1) {
                            cboEstado.setSelection(indice);
                        } else {
                            cboEstado.setSelection(0);
                        }
                    }
                }

                //llama a ws para cargar spinner con presentaciones del producto
                execws(4);

                txtUbic.setText(gl.inv_ciclico.NoUbic + "");
                idubic = gl.inv_ciclico.NoUbic;
                lblUbic1.setTypeface(null, Typeface.BOLD);
                lblUbic1.setText(gl.inv_ciclico.Ubic_nombre + "");
                lblProd.setTypeface(null, Typeface.BOLD);
                lblProd.setText(gl.inv_ciclico.Codigo + " - " + gl.inv_ciclico.Producto_nombre);

                String lote = gl.inv_ciclico.cantidad > 0 ? gl.inv_ciclico.Lote : gl.inv_ciclico.Lote_stock;
                txtLote1.setText(lote);

                String fecha = gl.inv_ciclico.cantidad > 0 ? gl.inv_ciclico.Fecha_Vence : gl.inv_ciclico.Fecha_Vence_Stock;
                dtpVence.setText(fecha);

                if (gl.inv_ciclico.IdPresentacion == 0) {
                    lblUM.setText(gl.inv_ciclico.UMBas);
                } else {

                    String stringDecimal = String.format("%.6f", gl.inv_ciclico.Factor);
                    lblUM.setText(gl.inv_ciclico.Pres + "->" + stringDecimal);
                }

                if (gl.pprod.Control_lote) {
                    txtlote_cic.setVisibility(TextView.VISIBLE);
                    txtLote1.setVisibility(TextView.VISIBLE);
                    //txtLote1.setEnabled(false);
                } else {
                    txtlote_cic.setVisibility(TextView.INVISIBLE);
                    txtLote1.setVisibility(TextView.INVISIBLE);
                }

                if (gl.pprod.Control_vencimiento) {
                    txtFecha_cic.setVisibility(TextView.VISIBLE);
                    imgDate.setVisibility(TextView.VISIBLE);
                    dtpVence.setVisibility(TextView.VISIBLE);
                } else {
                    txtFecha_cic.setVisibility(TextView.INVISIBLE);
                    imgDate.setVisibility(TextView.INVISIBLE);
                    dtpVence.setVisibility(TextView.INVISIBLE);
                }

                if (gl.inv_ciclico.control_peso) {

                    txtpeso_cic.setVisibility(TextView.VISIBLE);

                    txtPesoContado.setVisibility(TextView.VISIBLE);

                } else {
                    txtpeso_cic.setVisibility(TextView.INVISIBLE);

                    txtPesoContado.setVisibility(TextView.INVISIBLE);
                }


                if (BeInvEnc.Mostrar_Cantidad_Teorica_hh) {

                    if (!gl.inv_ciclico.cantidad.equals(0.00)) {

                        if (idPresentacion == 0) {

                            lblCantStock.setVisibility(TextView.VISIBLE);
                            lblCantStock.setText(gl.inv_ciclico.Cant_Stock + "");

                        } else {

                            double resultado_ = gl.inv_ciclico.Cant_Stock / vFactor;
                            String stringDecimal = String.format("%.6f", resultado_);
                            lblCantStock.setText(stringDecimal);
                        }
                    } else {

                        if (idPresentacion == 0) {

                            lblCantStock.setVisibility(TextView.VISIBLE);
                            lblCantStock.setText(gl.inv_ciclico.Cant_Stock + "");

                        } else {

                            double resultado_ = gl.inv_ciclico.Cant_Stock / vFactor;
                            String stringDecimal = String.format("%.6f", resultado_);
                            lblCantStock.setText(stringDecimal);
                        }
                    }

                } else {

                    lblCantStock.setVisibility(TextView.INVISIBLE);
                }

                lbltitulo_cic.setText("Ubic # " + gl.inv_ciclico.NoUbic);

                if (!gl.inv_ciclico.cantidad.equals(0.00)) {

                    if (idPresentacion == 0) {

                        String stringDecimal = String.format("%.6f", gl.inv_ciclico.cantidad);
                        txtCantContada.setText(stringDecimal);

                    } else {

                        double resultado_ = gl.inv_ciclico.cantidad / vFactor;
                        String stringDecimal = String.format("%.6f", resultado_);
                        txtCantContada.setText(stringDecimal);
                    }
                }

                txtLicencia.setText(gl.inv_ciclico.getLicence_plate());
                txtLicencia.setEnabled(false);

                if (BeInvEnc.Cambia_Ubicacion) {
                    if (esCambioUbicacion) {
                        txtUbicNueva.setText(""+IdUbicacion);
                    } else {
                        tblNuevaUbic.setVisibility(View.VISIBLE);
                        txtUbicNueva.setText("" + gl.inv_ciclico.IdUbicacion_nuevo);
                    }
                } else {
                    tblNuevaUbic.setVisibility(View.GONE);
                }

                txtProd.requestFocus();
            } else {

                mu.msgbox("El registro seleccionado no es válido.");
            }
        } catch (Exception e) {
            mu.msgbox(e.getClass()+" Load: "+e.getMessage());
        }
    }

    private void LoadExisteConteo() {
        try {
            if (gl.inv_ciclico != null) {
                idPresentacion = gl.inv_ciclico.IdPresentacion;

                if (gl.inv_ciclico.Factor.toString().isEmpty() || gl.inv_ciclico.Factor == 0) {
                    vFactor = 0;
                } else {
                    vFactor = gl.inv_ciclico.Factor;
                }

                txtUbic.setText(gl.inv_ciclico.NoUbic + "");
                idubic = gl.inv_ciclico.NoUbic;
                lblUbic1.setTypeface(null, Typeface.BOLD);
                lblUbic1.setText(gl.inv_ciclico.Ubic_nombre + "");
                lblProd.setTypeface(null, Typeface.BOLD);
                lblProd.setText(gl.inv_ciclico.Codigo + " - " + gl.inv_ciclico.Producto_nombre);
                txtLote1.setText(gl.inv_ciclico.Lote + "");
                dtpVence.setText(gl.inv_ciclico.Fecha_Vence);
                txtLicencia.setText(gl.inv_ciclico.getLicence_plate());

                if (gl.inv_ciclico.IdPresentacion == 0) {
                    lblUM.setText(gl.inv_ciclico.UMBas);
                } else {

                    String stringDecimal = String.format("%.6f", gl.inv_ciclico.Factor);
                    lblUM.setText(gl.inv_ciclico.Pres + "->" + stringDecimal);
                }

                if (gl.pprod.Control_lote) {
                    txtlote_cic.setVisibility(TextView.VISIBLE);
                    txtLote1.setVisibility(TextView.VISIBLE);
                    //txtLote1.setEnabled(false);
                } else {
                    txtlote_cic.setVisibility(TextView.INVISIBLE);
                    txtLote1.setVisibility(TextView.INVISIBLE);
                }

                if (gl.pprod.Control_vencimiento) {
                    txtFecha_cic.setVisibility(TextView.VISIBLE);
                    imgDate.setVisibility(TextView.VISIBLE);
                    dtpVence.setVisibility(TextView.VISIBLE);
                } else {
                    txtFecha_cic.setVisibility(TextView.INVISIBLE);
                    imgDate.setVisibility(TextView.INVISIBLE);
                    dtpVence.setVisibility(TextView.INVISIBLE);
                }

                if (gl.pprod.Control_peso) {

                    txtpeso_cic.setVisibility(TextView.VISIBLE);

                    txtPesoContado.setVisibility(TextView.VISIBLE);

                } else {
                    txtpeso_cic.setVisibility(TextView.INVISIBLE);

                    txtPesoContado.setVisibility(TextView.INVISIBLE);
                }


                if (BeInvEnc.Mostrar_Cantidad_Teorica_hh) {

                    if (!gl.inv_ciclico.cantidad.equals(0.00)) {

                        if (idPresentacion == 0) {

                            lblCantStock.setVisibility(TextView.VISIBLE);
                            lblCantStock.setText(gl.inv_ciclico.Cant_Stock + "");

                        } else {

                            double resultado_ = gl.inv_ciclico.Cant_Stock / vFactor;
                            String stringDecimal = String.format("%.6f", resultado_);
                            lblCantStock.setText(stringDecimal);
                        }
                    } else {

                        if (idPresentacion == 0) {

                            lblCantStock.setVisibility(TextView.VISIBLE);
                            lblCantStock.setText(gl.inv_ciclico.Cant_Stock + "");

                        } else {

                            double resultado_ = gl.inv_ciclico.Cant_Stock / vFactor;
                            String stringDecimal = String.format("%.6f", resultado_);
                            lblCantStock.setText(stringDecimal);
                        }
                    }

                } else {

                    lblCantStock.setVisibility(TextView.INVISIBLE);
                }

                lbltitulo_cic.setText("Ubic # " + gl.inv_ciclico.NoUbic);

                if (!gl.inv_ciclico.cantidad.equals(0.00)) {

                    if (idPresentacion == 0) {

                        String stringDecimal = String.format("%.6f", gl.inv_ciclico.cantidad);
                        txtCantContada.setText(stringDecimal);

                    } else {

                        double resultado_ = gl.inv_ciclico.cantidad / vFactor;
                        String stringDecimal = String.format("%.6f", resultado_);
                        txtCantContada.setText(stringDecimal);
                    }
                }

                txtCantContada.requestFocus();
            } else {
                mu.msgbox("El registro seleccionado no es válido.");
            }
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void LoadInvCongelado() {
        try {
            if (invCongelado != null) {
                idPresentacion = invCongelado.IdPresentacion;

                //#CKFK20241217 Cambié la ubicación nueva por la del inventario cíclico que estaba cargado
                txtUbicNueva.setText(gl.inv_ciclico.NoUbic + "");
                lblUbicNueva.setTypeface(null, Typeface.BOLD);
                lblUbicNueva.setText(gl.inv_ciclico.Ubic_nombre + "");

                lblProd.setTypeface(null, Typeface.BOLD);
                lblProd.setText(gBeProducto.Codigo + " - " + gBeProducto.Nombre);

                txtLote1.setText(invCongelado + "");
                dtpVence.setText(du.convierteFechaMostrar(invCongelado.Fecha_vence));
                txtLicencia.setText(invCongelado.lic_plate);

                txtProd.setText(gBeProducto.Codigo);
                txtLote1.setText(invCongelado.Lote);
                btGuardar.setEnabled(true);

                if (invCongelado.IdPresentacion == 0) {
                    lblUM.setText(gBeProducto.UnidadMedida.Nombre);
                } else {
                    String stringDecimal = String.format("%.6f", vFactor);
                    lblUM.setText(NomPresentacion + "->" + stringDecimal);
                }

                if (BeInvEnc.Mostrar_Cantidad_Teorica_hh) {
                    if (invCongelado.IdPresentacion == 0) {
                        lblCantStock.setVisibility(TextView.VISIBLE);
                        lblCantStock.setText(invCongelado.Cant_stock + "");
                    } else {
                        double resultado_ = invCongelado.Cant_stock / vFactor;
                        String stringDecimal = String.format("%.6f", resultado_);
                        lblCantStock.setText(stringDecimal);
                    }
                } else {
                    lblCantStock.setVisibility(TextView.INVISIBLE);
                }

                new Handler(Looper.getMainLooper()).post(() -> txtCantContada.requestFocus());
            }
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private boolean Scan_Codigo_Producto(){
        boolean respuesta = false;

        try {
            if(!codigo_producto.isEmpty()){
                if(gl.inv_ciclico.Codigo.equals(codigo_producto)){
                    cboEstado.requestFocus();
                    respuesta = true;
                } else {
                    IdProductoBodega = gl.inv_ciclico.IdProductoBodega;

                    //el codigo ingresado no tiene match con el registro seleccionado, se procede a buscar en la lista
                    if (Buscar_producto(codigo_producto)){
                        respuesta = true;
                        new Handler(Looper.getMainLooper()).post(() -> txtCantContada.requestFocus());
                    } else {
                        /*//GT03122021: Al no encontrar match por cod_producto, se busca como LP
                        if(Scan_por_LP()){
                            respuesta = true;
                            txtProd.setText(gl.inv_ciclico.Codigo);
                            new Handler(Looper.getMainLooper()).post(() -> txtCantContada.requestFocus());
                        }else{
                            //#AT20241210 Busca en inventario congelado
                            execws(11);
                        }*/
                        //#AT20241210 Busca en inventario congelado
                        execws(11);
                    }
                }
            } else {
                mu.msgbox("No ha ingresado un código.");
            }
        }
        catch (Exception e){
            respuesta = false;
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
        return respuesta;
    }

    private boolean Buscar_producto(String codigo_producto){

       /* boolean respuesta = false;

        for (int i = 0; i < gl.reconteo_list.size() ; i++) {

            String codigo = gl.reconteo_list.get(i).Codigo;
            String licencia = gl.reconteo_list.get(i).Licence_plate;

            //if (codigo.equals(codigo_producto) && gl.reconteo_list.get(i).cantidad.equals(0.0) ) {
            if (codigo.equals(codigo_producto) || ) {

                gl.inv_ciclico = gl.reconteo_list.get(i);
                esCambioUbicacion = IdUbicacion != gl.inv_ciclico.NoUbic;
                Load();

                respuesta = true;
                break;

            }
        }

        return respuesta;*/

        Optional<clsBe_inv_reconteo_data> resultado = gl.reconteo_list.stream()
                .filter(item -> codigo_producto.equals(item.Codigo) || codigo_producto.equals(item.Licence_plate))
                .findFirst();

        if (resultado.isPresent()) {
            gl.inv_ciclico = resultado.get();
            esCambioUbicacion = IdUbicacion != gl.inv_ciclico.NoUbic;
            Load();
            return true;
        }

        return false;
    }

    private boolean Buscar_lp(String licence_plate){
        boolean respuesta = false;

        for (int i = 0; i < gl.reconteo_list.size() ; i++) {

            String license_p = gl.reconteo_list.get(i).Licence_plate;

            if (license_p.equals(licence_plate) ) {

                gl.inv_ciclico = gl.reconteo_list.get(i);
                txtProd.setText(gl.inv_ciclico.codigo_producto);
                txtLicencia.setText(gl.inv_ciclico.Licence_plate);
                esCambioUbicacion = IdUbicacion != gl.inv_ciclico.NoUbic;
                Load();

                respuesta = true;
                break;
            }
        }
        return respuesta;
    }


    private boolean Scan_por_LP(){

        boolean respuesta = false;

        //#CKFK20220218 Agregué este replace para cuando la barra tiene el símbolo de dólar
        codigo_producto = codigo_producto.replace("$","");

        try{
                if(gl.inv_ciclico.Licence_plate.equals(codigo_producto)){

                    cboEstado.requestFocus();
                    respuesta = true;
                    txtProd.setText(gl.inv_ciclico.Licence_plate);

                }else{

                    IdProductoBodega = gl.inv_ciclico.IdProductoBodega;

                    //la LP ingresada no tiene match con el registro seleccionado, se procede a buscar en la lista
                    if(Buscar_lp(codigo_producto)){

                        respuesta = true;

                    }else{

                        respuesta = false;
                        txtProd.setText("");
                        //mu.msgbox("Licence plate no asignado para conteo. Intente con otra!");
                    }
                }
        }
        catch (Exception e){
            respuesta = false;
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
        return respuesta;

    }



/*    private void Scan_Codigo_Producto1() {

        if(gl.inv_ciclico.codigo_producto == null){
            toast("¡Producto no existe!");

        }else{

            if(!gl.inv_ciclico.Codigo.equals(txtProd.getText().toString().trim())){

                toast("El código de producto no es válido");

                txtProd.requestFocus();


            }else{

                IdProductoBodega = gl.inv_ciclico.IdProductoBodega;

                if(IdProductoBodega != gl.inv_ciclico.IdProductoBodega){

                    if(!buscaproducto(IdProductoBodega, txtProd.getText().toString().trim())){

                        toast("¿Producto no pertence a esta ubicación, Registrar de todas formas?");

                        *//*******************************************************************************//*
                        *//****** FALTA CREAR TOAST PARA CONFIRMAR Y ENVIAR A FORM_CIC_NUEVO.JAVA *******//*
                    } else{



                    }
                }

                btGuardar.setEnabled(true);
              //  if(gl.pprod.Control_lote && txtLote1.toString().isEmpty()){
                if(gl.pprod.Control_lote){

                    txtLote1.requestFocus();

                }else{
                    txtCantContada.requestFocus();
                }
            }
        }
    }

    private boolean buscaproducto(int idprod, String prodtxt) {

        boolean respuesta = false;
        int ii, idu, idp;

        for (ii = 0; ii < gl.reconteo_list.size() - 1; ii++) {

            if (gl.reconteo_list.get(ii).IdUbicacion == idubic && gl.reconteo_list.get(ii).IdProductoBodega == idprod) {

                txtUbic.setText(idubic + "");
                respuesta = true;
                break;
            }
        }

        return respuesta;
    }*/

    public void ChangeDate(View view) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dtpVence.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void Exit(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {

        try{

            gl.inv_ciclico = new clsBe_inv_reconteo_data();
            pitem = new clsBeTrans_inv_ciclico();
            BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
            txtUbic.setText("");
            txtProd.setText("");
            txtCantContada.setText("");
            txtPesoContado.setText("");
            gl.pprod = new clsBeProducto();

            frm_inv_cic_add.super.finish();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void backward(View view) {

        if(Index > 0){

            Index = Index -1;
            gl.inv_ciclico=  gl.reconteo_list.get(Index);

            ValidaBotones();

            Load();
            setHandlers();


        }
    }

    public void forward(View view) {

        if (Index < tam_lista){

            Index = Index+1;
            gl.inv_ciclico=  gl.reconteo_list.get(Index);

            ValidaBotones();

            Load();
            setHandlers();

        }

    }

    public void ValidaBotones(){

        if(Index == tam_lista){
            btAdelante.setEnabled(false);
        }
        if(Index == 0){

            btAtras.setEnabled(false);

        }

        if(Index > 0 && Index < tam_lista){
            btAdelante.setEnabled(true);
            btAtras.setEnabled(true);
        }
    }

    public void btnGuardar(View view) {
        try {
            if (txtUbic.getText().toString().trim().isEmpty()) {
                msgbox("¡Ubicacion vacia!");
                txtUbic.requestFocus();

            } else if (txtProd.getText().toString().trim().isEmpty()) {
                toast("¡Producto vacio!");
                txtProd.requestFocus();

            } else if (txtCantContada.getText().toString().trim().isEmpty()) {
                toast("¡Cantidad incorrecta!");
                txtCantContada.requestFocus();

            } else if (gl.pprod.Control_lote && txtLote1.getText().toString().trim().isEmpty()) {
                toast("¡Lote incorrecto!");
                txtLote1.requestFocus();

            } else if (gl.inv_ciclico.control_peso && txtPesoContado.getText().toString().trim().isEmpty()) {
                toast("¡Peso incorrecto!");
                txtPesoContado.requestFocus();

            } else if (NuevoConteo && !existeConteo && !esInvCongelado && txtLicencia.getText().toString().trim().isEmpty()) {
                toast("¡Debe ingresar una licencia!");
                txtLicencia.requestFocus();

            } else if( txtLicencia.getText().toString().trim().isEmpty()) {
                toast("¡Debe ingresar una licencia!!");
                txtLicencia.requestFocus();
            } else {
                btGuardar.setEnabled(true);

                pitem = new clsBeTrans_inv_ciclico();
                String Ubic = txtUbicNueva.getText().toString();
                int UbicNueva =  Ubic.isEmpty() ? 0 : Integer.valueOf(Ubic);
                pitem.IdUbicacion_nuevo = UbicNueva;

                if (NuevoConteo && !existeConteo && !esInvCongelado) {

                    pitem.Idinventarioenc = BeInvEnc.Idinventarioenc;
                    pitem.IdStock = 0;
                    pitem.IdProductoBodega = BeProductoUbicacion.IdProductoBodega;
                    pitem.IdUnidadMedida = BeProductoUbicacion.IdUnidadMedidaBasica;
                    pitem.IdPresentacion = IdPresentacionselected;
                    pitem.IdPresentacion_nuevo = IdPresentacionselected;
                    pitem.IdProductoEstado = IdEstadoselected;
                    pitem.IdProductoEst_nuevo = IdEstadoselected;
                    pitem.IdUbicacion = Integer.valueOf(txtUbic.getText().toString());
                    pitem.IdUbicacion_nuevo = UbicNueva;

                    if (BeProductoUbicacion.Control_lote) {
                        String lote = txtLote1.getText().toString();

                        pitem.Lote = lote;
                        pitem.Lote_stock = lote;
                    } else {
                        pitem.Lote = "";
                        pitem.Lote_stock = "";
                    }

                    if (BeProductoUbicacion.Control_vencimiento) {
                        String fecha = app.strFechaXML2(dtpVence.getText().toString());

                        pitem.Fecha_vence = fecha;
                        pitem.Fecha_vence_stock = fecha;
                    } else {
                        pitem.Fecha_vence = "1900-01-01T00:00:00";
                        pitem.Fecha_vence_stock = "1900-01-01T00:00:00";
                    }

                    if (BeProductoUbicacion.Control_peso) {
                        pitem.Peso = Double.valueOf(txtPesoContado.getText().toString().trim());
                    } else {
                        pitem.Peso = 0;
                    }

                    pitem.Cantidad = Double.valueOf(txtCantContada.getText().toString().trim());

                    if (pitem.IdPresentacion_nuevo > 0) {
                        pitem.Cantidad = pitem.Cantidad * vFactor;
                    }

                    pitem.lic_plate = txtLicencia.getText().toString().replace("$","");
                    pitem.Fec_agr = du.Fecha_CompletaT();
                    pitem.Fec_Mod = du.Fecha_CompletaT();
                    pitem.Idoperador = gl.IdOperador;
                    pitem.User_agr = gl.OperadorBodega.Nombre_Completo;
                    pitem.IdBodega = gl.IdBodega;

                    if (pitem.Cantidad==0){
                       msgAskCantidadCero("Guardar la cantidad en 0");
                    }else{
                        execws(9);
                    }
                } else if (esInvCongelado) {
                    pitem.Idinventarioenc = BeInvEnc.Idinventarioenc;
                    pitem.IdStock = invCongelado.IdStock;

                    pitem.IdProductoBodega = gBeProducto.IdProductoBodega;
                    pitem.IdUnidadMedida = gBeProducto.IdUnidadMedidaBasica;
                    pitem.IdPresentacion = invCongelado.IdPresentacion;
                    pitem.IdPresentacion_nuevo = IdPresentacionselected;
                    pitem.IdProductoEstado = invCongelado.IdProductoEstado;
                    pitem.IdProductoEst_nuevo = IdEstadoselected;
                    pitem.IdUbicacion = Integer.valueOf(txtUbic.getText().toString());
                    pitem.IdUbicacion_nuevo = UbicNueva;

                    if (gBeProducto.Control_lote) {
                        String lote = txtLote1.getText().toString();

                        pitem.Lote = lote;
                        pitem.Lote_stock = invCongelado.Lote_stock;
                    } else {
                        pitem.Lote = "";
                        pitem.Lote_stock = "";
                    }

                    if (gBeProducto.Control_vencimiento) {
                        String fecha = app.strFechaXML2(dtpVence.getText().toString());

                        pitem.Fecha_vence = fecha;
                        pitem.Fecha_vence_stock = invCongelado.Fecha_vence_stock;
                    } else {
                        pitem.Fecha_vence = "1900-01-01T00:00:00";
                        pitem.Fecha_vence_stock = "1900-01-01T00:00:00";
                    }

                    if (gBeProducto.Control_peso) {
                        pitem.Peso = Double.valueOf(txtPesoContado.getText().toString().trim());
                    } else {
                        pitem.Peso = 0;
                    }

                    pitem.Cantidad = Double.valueOf(txtCantContada.getText().toString().trim());
                    pitem.Cant_stock = invCongelado.Cant_stock;

                    if (pitem.Cant_stock==0) {
                        throw new Exception("La cantidad del inventario congelado no puede ser 0");
                    }

                    if (pitem.IdPresentacion_nuevo > 0) {
                        pitem.Cantidad = pitem.Cantidad * vFactor;
                    }

                    pitem.lic_plate = txtLicencia.getText().toString().replace("$","");
                    pitem.Fec_agr = du.Fecha_CompletaT();
                    pitem.Idoperador = gl.IdOperador;
                    pitem.User_agr = gl.OperadorBodega.Nombre_Completo;
                    pitem.IdBodega = gl.IdBodega;
                    pitem.EsNuevo = true;

                    if (pitem.Cantidad==0){
                        msgAskCantidadCero("Guardar la cantidad en 0");
                    }else{
                        execws(9);
                    }
                } else {
                    Guardar();
                }
            }
        } catch (Exception e) {
            mu.msgbox("btnGuardar: " + e.getMessage());
        }
    }

    private void  Guardar(){
        try {

            if (IdPresentacionselected < 0) {

                gl.inv_ciclico.idPresentacion_nuevo = 0;

            } else {
                gl.inv_ciclico.idPresentacion_nuevo = IdPresentacionselected;
            }

            gl.inv_ciclico.cantidad = Double.valueOf(txtCantContada.getText().toString().trim());


            if (gl.inv_ciclico.control_peso) {
                gl.inv_ciclico.Peso = Double.valueOf(txtPesoContado.getText().toString().trim());
            }

            //----------------------------------------------------------------------------------------------
            //#GT26072022-1150: Se valida si es tarea de conteo o reconteo
            if (gl.Es_Reconteo) {
                EnviarReconteo();
            } else {
                //GT 18012021 set para la clase que se envia como conteo.
                pitem = new clsBeTrans_inv_ciclico();

                String Lote = txtLote1.getText().toString().trim();
                String FechaVence = du.convierteFecha(dtpVence.getText().toString().trim());

                pitem.Idinventarioenc = gl.inv_ciclico.idinventarioenc;
                pitem.IdStock = 0;
                pitem.IdProductoBodega = gl.inv_ciclico.IdProductoBodega;
                pitem.IdUbicacion = gl.inv_ciclico.NoUbic;
                pitem.Lote_stock = gl.inv_ciclico.Lote_stock;
                pitem.Fecha_vence_stock = app.strFechaXML2(gl.inv_ciclico.Fecha_Vence);
                pitem.Lote = Lote;
                pitem.Fecha_vence = FechaVence;

                pitem.Cantidad = gl.inv_ciclico.cantidad;
                pitem.Peso = gl.inv_ciclico.Peso;
                pitem.IdPresentacion = gl.inv_ciclico.IdPresentacion;
                pitem.IdPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
                pitem.IdProductoEst_nuevo = gl.inv_ciclico.IdProductoEst_nuevo;
                pitem.IdProductoEstado = gl.inv_ciclico.IdProductoEstado;
                pitem.lic_plate = gl.inv_ciclico.Licence_plate;
                pitem.Idoperador = gl.IdOperador;
                pitem.IdInvCiclico = gl.inv_ciclico.IdInventarioCiclico;

                if (pitem.IdPresentacion > 0) {

                    pitem.Cantidad = pitem.Cantidad * vFactor;
                }

                //ejecutar proceso actualización Inventario_Ciclico_Actualiza_Conteo
                if (pitem.Cantidad==0){
                    msgAskCantidadCeroActualiza("Guardar la cantidad en 0");
                }else{
                    execws(1);
                }
            }
        } catch (Exception e) {
            mu.msgbox("Guardar: "+e.getMessage());
        }

    }

    private void EnviarReconteo() {

        if(gl.inv_ciclico.Factor !=0){

            Nueva_Cantidad = Double.parseDouble(txtCantContada.getText().toString().trim()) * gl.inv_ciclico.Factor;
        }else{

            Nueva_Cantidad = Double.parseDouble(txtCantContada.getText().toString().trim());
        }

        execws(5);

    }

    private boolean AgregaNuevoRegistro(int IdStock){

        try{

            if(IdStock > 0){

                idstock = IdStock;
                //obtener el MaxIDInventarioCiclico
                execws(2);

            }else {


                BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
                BeTrans_inv_ciclico.IdInvCiclico = 0;
                BeTrans_inv_ciclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
                BeTrans_inv_ciclico.IdStock = IDInventarioCiclico;
                BeTrans_inv_ciclico.IdProductoBodega =  gl.inv_ciclico.IdProductoBodega;
                BeTrans_inv_ciclico.IdProductoEstado =  gl.inv_ciclico.IdProductoEstado;
                BeTrans_inv_ciclico.IdProductoEst_nuevo =  gl.inv_ciclico.IdProductoEst_nuevo;
                BeTrans_inv_ciclico.IdPresentacion = gl.inv_ciclico.IdPresentacion;
                BeTrans_inv_ciclico.IdPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
                BeTrans_inv_ciclico.IdUbicacion = idubic;
                BeTrans_inv_ciclico.IdUbicacion_nuevo = idubic;
                BeTrans_inv_ciclico.EsNuevo = true;

                if( gl.pprod.Control_lote){
                    BeTrans_inv_ciclico.Lote = gl.inv_ciclico.Lote;
                    if(idstock > 0){
                        BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote_stock;
                    }else {
                        BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote;
                    }
                }

                if(gl.pprod.Control_vencimiento){
                    BeTrans_inv_ciclico.Fecha_vence = gl.inv_ciclico.Fecha_Vence;
                    BeTrans_inv_ciclico.Fecha_vence_stock = gl.inv_ciclico.Fecha_Vence;
                }else {
                    BeTrans_inv_ciclico.Fecha_vence = du.convierteFecha(du.AddYearsToDate(du.getFecha(), 10));
                    BeTrans_inv_ciclico.Fecha_vence_stock =  du.convierteFecha(du.AddYearsToDate(du.getFecha(), 10));
                }

               // m_proxy.Inventario_Agregar_Conteo(BeTrans_inv_ciclico)
                execws(3);

                //nuevoRegistro = true;

            }

            return  nuevoRegistro;
        }
        catch (Exception e){
            mu.msgbox("inv_cic_AgregarNuevoRegistro:"+e.getMessage());
            return  false;
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
                        if (BeInvEnc.Cambia_Ubicacion) {
                            String NuevaUbicacion = txtUbicNueva.getText().toString();

                            if (!NuevaUbicacion.isEmpty()) {
                                pitem.IdUbicacion_nuevo = Integer.valueOf(NuevaUbicacion);
                            }
                        }

                        callMethod("Inventario_Ciclico_Act_Conteo_Andr",
                                "pitem",pitem,
                                "pReconteo",0,
                                "esOriginal", esOriginal,
                                "Resultado",Resultado);
                        break;
                    case 2:
                        callMethod("MaxIDInventarioCiclico");
                        break;
                    case 3:
                        callMethod("Inventario_Agregar_Conteo", "pBeTransInvCiclico", BeTrans_inv_ciclico);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto", "pIdProducto", gl.pprod.IdProducto, "pActivo", true);
                        break;

                    case 5:
                        callMethod("Inventario_Ciclico_Actualiza_Reconteo", "idinvreconteo", gl.inv_ciclico.idinvreconteo, "pCantidad_Reconteo", Nueva_Cantidad );
                        break;
                    case 6:

                        if (pIdUbicacion == 0) return;

                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra", pIdUbicacion,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 7:
                        callMethod("Get_BeProducto_By_Codigo_For_HH",
                                "pCodigo",txtProd.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;
                    case 8:
                        callMethod("Get_Estados_By_IdPropietario","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                    case 9:
                        int idresolucion = 0;

                        if (nBeResolucion != null) {
                            idresolucion = nBeResolucion.IdResolucionlp;
                        }

                        callMethod("Inventario_Agregar_Conteo", "pBeTransInvCiclico", pitem, "pIdResolucion", idresolucion);
                        break;
                    case 10:
                        clsBeTrans_inv_ciclico item = new clsBeTrans_inv_ciclico();
                        item.Idinventarioenc = BeInvEnc.Idinventarioenc;

                        if (esInvCongelado) {
                            item.Lote_stock = invCongelado.Lote_stock;
                            item.Fecha_vence_stock = invCongelado.Fecha_vence_stock;
                            item.IdUbicacion =invCongelado.IdUbicacion;
                            item.IdProductoBodega = invCongelado.IdProductoBodega;
                            item.IdPresentacion = invCongelado.IdPresentacion;
                        } else {
                            item.Lote_stock = gl.inv_ciclico.Lote_stock;
                            item.Fecha_vence_stock = app.strFechaXML2(gl.inv_ciclico.Fecha_Vence_Stock);
                            item.IdUbicacion = gl.inv_ciclico.NoUbic;
                            item.IdProductoBodega = gl.inv_ciclico.IdProductoBodega;
                            item.IdPresentacion = gl.inv_ciclico.IdPresentacion;
                        }

                        callMethod("Get_Conteo_Inv_Ciclico", "pInvCiclico", item);
                        break;
                    case 11:
                        clsBeTrans_inv_ciclico invCiclico = new clsBeTrans_inv_ciclico();
                        invCiclico.lic_plate = codigo_producto;
                        invCiclico.IdUbicacion = Integer.valueOf(txtUbic.getText().toString());
                        invCiclico.IdBodega = gl.IdBodega;
                        invCiclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
                        
                        callMethod("Get_Stock_Congelado", "pInvCiclico", invCiclico);
                        break;
                    case 12:
                        callMethod("Get_Producto_By_IdProductoBodega","IdProductoBodega", invCongelado.IdProductoBodega);
                        break;
                    case 13:
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador",gl.IdOperador,
                                "pIdBodega",gl.IdBodega);
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
                    Inv_Ciclico_Actualiza_Conteo();
                    break;
                case 2:
                    MaxIDInventarioCiclico_();
                    break;
                case 3:
                    Inventario_Agregar_Conteo_();
                    break;
                case 4:
                    processPresentacion();
                    break;
                case 5:
                    Inventario_Ciclico_Actualiza_Reconteo();
                    break;
                case 6:
                    processUbic();
                    break;
                case 7:
                    processProducto();
                    break;
                case 8:
                    processEstados();
                    break;
                case 9:
                    processAgregarConteo();
                    break;
                case 10:
                    processGetCantidadContada();
                    break;
                case 11:
                    processGetStockCongelado();
                    break;
                case 12:
                    processProductoUbic();
                    break;
                case 13:
                    processLicenciaInv();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processLicenciaInv() {
        try {

            if (nBeResolucion == null){
                nBeResolucion = new clsBeResolucion_lp_operador();
                if (xobj!=null){
                    nBeResolucion = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");
                }else{
                    toast("El objeto SI es nulo");
                }
            }

            if (nBeResolucion !=null){
                gl.IdResolucionLpOperador = nBeResolucion.IdResolucionlp;

                long pLpSiguiente = nBeResolucion.Correlativo_Actual +1;
                int largoMaximo = String.valueOf(nBeResolucion.Correlativo_Final).length();

                long intLPSig = pLpSiguiente;
                int MaxL = largoMaximo;

                String result = String.format("%0"+ MaxL + "d",intLPSig);

                pNumeroLP= nBeResolucion.Serie + result;
                txtLicencia.setText(pNumeroLP);
                btnSetLicencia.setEnabled(true);
            } else {
                gl.IdResolucionLpOperador =0;
                return;
            }
        }catch (Exception e){
            mu.msgbox("processNuevoLP_RE: "+e.getMessage());
        }
    }

    private void processPresentacion() {

        try {
            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (BeListPres!=null){

                if (BeListPres.items!=null){

                    Set<Integer> presentaciones = new HashSet<>();
                    BeListPres.items = BeListPres.items.stream()
                            .filter(obj -> presentaciones.add(obj.getIdPresentacion()))
                            .collect(Collectors.toList());

                    clsBeProducto_Presentacion pres = new clsBeProducto_Presentacion();
                    pres.IdPresentacion = 0;
                    pres.Nombre = "Sin Presentación";

                    BeListPres.items.add(0, pres);

                    ArrayAdapter<clsBeProducto_Presentacion> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, BeListPres.items);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cboPres.setAdapter(dataAdapter);

                    int presentacion = 0;
                    if (esInvCongelado) {
                        presentacion = invCongelado.IdPresentacion;
                    } else {
                        presentacion = gl.inv_ciclico.IdPresentacion;
                    }

                    int Presentacion = presentacion;
                    int indice = BeListPres.items.stream()
                            .filter(obj -> obj.getIdPresentacion() == Presentacion)
                            .map(BeListPres.items::indexOf)
                            .findFirst()
                            .orElse(-1);

                    if (indice != -1) {
                        cboPres.setSelection(indice);
                    } else {
                        cboPres.setSelection(0);
                    }
                }
            }

            execws(10);
        } catch (Exception e) {
            mu.msgbox("processPresentacion:"+e.getMessage());
        }

    }

    private void Listar_Presentaciones() {

        try{

            //crea listas con descripcion item no repetidos del ws
            PresList.clear();
            for (clsBeProducto_Presentacion BePres: BeListPres.items){

                if(PresList.contains(BePres.Nombre)){

                }else{

                    PresList.add(BePres.Nombre);

                }
            }

            //crea lista con id item no repetido del ws
            IndexPresList.clear();
            for (clsBeProducto_Presentacion BePres: BeListPres.items){

                if(IndexPresList.contains(BePres.IdPresentacion)){

                }else{

                    IndexPresList.add(BePres.IdPresentacion);

                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cboPres.setAdapter(dataAdapter);

            if ( gl.inv_ciclico.IdPresentacion > 0){

                List AuxPres = stream(BeListPres.items).select(c->c.IdPresentacion).toList();
                int indx=AuxPres.indexOf(gl.inv_ciclico.IdPresentacion);
                cboPres.setSelection(indx);
            }else {

                cboPres.setSelection(0);
            }

        }catch (Exception e){
            mu.msgbox("cic_add_cboPres:"+e.getMessage());
        }

    }

    private void Inv_Ciclico_Actualiza_Conteo() {

        try {
            int respuesta = xobj.getresult(Integer.class,"Inventario_Ciclico_Act_Conteo_Andr");

            if(respuesta !=0){

                toast("¡Todo bien, guardado!");
                frm_inv_cic_add.super.finish();
            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void MaxIDInventarioCiclico_() {

        try {

            IDInventarioCiclico = xobj.getresult(Integer.class,"MaxIDInventarioCiclico");

            BeTrans_inv_ciclico = new clsBeTrans_inv_ciclico();
            BeTrans_inv_ciclico.IdInvCiclico = 0;
            BeTrans_inv_ciclico.Idinventarioenc = BeInvEnc.Idinventarioenc;
            BeTrans_inv_ciclico.IdStock = IDInventarioCiclico;
            BeTrans_inv_ciclico.IdProductoBodega =  gl.inv_ciclico.IdProductoBodega;
            BeTrans_inv_ciclico.IdProductoEstado =  gl.inv_ciclico.IdProductoEstado;
            BeTrans_inv_ciclico.IdProductoEst_nuevo =  gl.inv_ciclico.IdProductoEst_nuevo;
            BeTrans_inv_ciclico.IdPresentacion = gl.inv_ciclico.IdPresentacion;
            BeTrans_inv_ciclico.IdPresentacion_nuevo = gl.inv_ciclico.idPresentacion_nuevo;
            BeTrans_inv_ciclico.IdUbicacion = idubic;
            BeTrans_inv_ciclico.IdUbicacion_nuevo = idubic;
            BeTrans_inv_ciclico.EsNuevo = true;

            if( gl.pprod.Control_lote){
                BeTrans_inv_ciclico.Lote = gl.inv_ciclico.Lote;
                if(idstock > 0){
                    BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote_stock;
                }else {
                    BeTrans_inv_ciclico.Lote_stock = gl.inv_ciclico.Lote;
                }
            }

            if(gl.pprod.Control_vencimiento){
                BeTrans_inv_ciclico.Fecha_vence = gl.inv_ciclico.Fecha_Vence;
                BeTrans_inv_ciclico.Fecha_vence_stock = gl.inv_ciclico.Fecha_Vence;
            }else {
                BeTrans_inv_ciclico.Fecha_vence = du.convierteFecha(du.AddYearsToDate(du.getFecha(), 10));
                BeTrans_inv_ciclico.Fecha_vence_stock =  du.convierteFecha(du.AddYearsToDate(du.getFecha(), 10));
            }

            BeTrans_inv_ciclico.Cantidad = Double.parseDouble(txtCantContada.getText().toString().trim());
            BeTrans_inv_ciclico.Cant_stock = gl.inv_ciclico.Cant_Stock;

            if (BeTrans_inv_ciclico.Cant_stock==0) {
                throw new Exception("La cantidad del inventario no puede ser 0");
            }

            BeTrans_inv_ciclico.Cant_reconteo = 0;

            if(gl.inv_ciclico.control_peso){

                BeTrans_inv_ciclico.Peso = Double.parseDouble(txtPesoContado.getText().toString().trim());
                BeTrans_inv_ciclico.Peso_stock =  gl.inv_ciclico.Peso_Stock;
                BeTrans_inv_ciclico.Peso_reconteo = 0;
            }

            BeTrans_inv_ciclico.Idoperador =  gl.IdOperador;
            BeTrans_inv_ciclico.User_agr = gl.gNomOperador;

            String fecha_vence = du.getFechaActual();
            BeTrans_inv_ciclico.Fec_agr = fecha_vence;

            //m_proxy.Inventario_Agregar_Conteo(BeTrans_inv_ciclico)
            execws(3);

        } catch (Exception e) {
            mu.msgbox("Inventario_Agregar_Conteo: "+e.getMessage());
        }
    }

    private void Inventario_Agregar_Conteo_() {

        try {

            int getrespuesta = xobj.getresult(Integer.class,"Inventario_Agregar_Conteo");

            nuevoRegistro = getrespuesta == 1;

        } catch (Exception e) {
            mu.msgbox("Inventario_Agregar_Conteo getResult: "+e.getMessage());
        }

    }

    private void Inventario_Ciclico_Actualiza_Reconteo() {

        try {
            int respuesta  = xobj.getresult(Integer.class,"Inventario_Ciclico_Actualiza_Reconteo");

            if(respuesta ==1){
                toast("Reconteo registrado!");
                super.finish();

            }else if(respuesta > 1) {
                toast("Se actualizó más de un registro!");
            }else if (respuesta == 0){
                toast("Error al actualizar recongeo");
            }

        } catch (Exception e) {
            mu.msgbox("actualiza_ciclico_reconteo_: "+e.getMessage());
        }
    }

    private void processUbic() {
        try {
            ubicacion = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (ubicacion == null) {

                if (NuevoConteo) {
                    txtUbic.selectAll();
                    txtUbic.requestFocus();
                    lblUbic1.setText("");
                } else {
                    txtUbicNueva.selectAll();
                    txtUbicNueva.requestFocus();
                    lblUbicNueva.setText("");
                }

                lbltitulo_cic.setText("Ubic");
                throw new Exception("Ubicación no válida");
            } else {

                if (NuevoConteo) {
                    lblUbic1.setText(ubicacion.getDescripcion());
                    lbltitulo_cic.setText("Ubic # " + ubicacion.IdUbicacion);
                    txtProd.requestFocus();
                } else {
                    lblUbicNueva.setVisibility(View.VISIBLE);
                    lblUbicNueva.setText(ubicacion.getDescripcion());
                    lbltitulo_cic.setText("Ubic # " + ubicacion.IdUbicacion);
                    txtProd.requestFocus();
                }
            }
        } catch (Exception e) {
            mu.msgbox("processUbic: "+e.getMessage());
        }
    }

    private void processProducto(){
        try {
            existeConteo = false;
            BeProductoUbicacion = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProductoUbicacion != null){
                lblProd.setText(BeProductoUbicacion.Nombre);
                lblUM.setText(BeProductoUbicacion.UnidadMedida.Nombre);

                if (!BeProductoUbicacion.Control_lote) {
                    tblote_cic.setVisibility(View.GONE);
                } else {
                    tblote_cic.setVisibility(View.VISIBLE);
                }

                if (!BeProductoUbicacion.Control_vencimiento) {
                    tblVence.setVisibility(View.GONE);
                } else {
                    tblVence.setVisibility(View.VISIBLE);
                }

                if (!BeProductoUbicacion.Control_peso) {
                    txtpeso_cic.setVisibility(View.INVISIBLE);
                    txtPesoContado.setVisibility(View.INVISIBLE);
                } else {
                    txtpeso_cic.setVisibility(View.VISIBLE);
                    txtPesoContado.setVisibility(View.VISIBLE);
                }

                codigo_producto = txtProd.getText().toString().trim();

                Optional<clsBe_inv_reconteo_data> existe = gl.reconteo_list.stream()
                        .filter(obj -> obj.Codigo.equals(txtProd.getText().toString())
                                && obj.getNoUbic() == Integer.valueOf(txtUbic.getText().toString()))
                        .findFirst();

                if (existe.isPresent()) {
                    existeConteo = true;

                    gl.inv_ciclico = existe.get();
                    gl.inv_ciclico.index = gl.reconteo_list.indexOf(gl.inv_ciclico);
                    gl.IndexCiclico = gl.inv_ciclico.index;

                    gl.pprod.IdProducto = BeProductoUbicacion.IdProducto;
                    gl.pprod.Control_vencimiento = BeProductoUbicacion.Control_vencimiento;
                    gl.pprod.Control_lote = BeProductoUbicacion.Control_lote;
                    gl.pprod.Control_peso = BeProductoUbicacion.Control_peso;

                    LoadExisteConteo();
                    toastlong("Ya existe un conteo del producto en inventario en la ubicación " + txtUbic.getText().toString());
                } else {
                    btnSetLicencia.setVisibility(View.VISIBLE);
                    txtLicencia.setEnabled(true);
                }

                execws(8);

                btGuardar.setEnabled(true);
            } else {
                lblProd.setText ("Código no válido");
                txtProd.requestFocus();
                txtProd.selectAll();
                toast("Producto no existe");

                if (NuevoConteo || invCongelado == null) {
                    gl.IdUbicInvCic = Integer.valueOf(txtUbic.getText().toString());
                    gl.nuevo_producto_cic = txtProd.getText().toString();
                    startActivity(new Intent(this, frm_inv_cic_nuevo.class));
                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . processProducto " + e.getMessage());
        }
    }

    private void processEstados() {
        try {
            bodlist.clear();
            listaEstados = xobj.getresult(clsBeProducto_estadoList.class, "Get_Estados_By_IdPropietario");

            if (listaEstados != null){

                ArrayAdapter<clsBeProducto_estado> EstadosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEstados.items);
                EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cboEstado.setAdapter(EstadosAdapter);

                if (existeConteo) {
                    int indice = listaEstados.items.stream()
                            .filter(obj -> obj.getIdEstado() == gl.inv_ciclico.IdProductoEstado)
                            .map(listaEstados.items::indexOf)
                            .findFirst()
                            .orElse(-1);

                    if (indice != -1) {
                        cboEstado.setSelection(indice);
                    }
                } else {
                    cboEstado.setSelection(0);
                }

                LLenaPresentacion();
            }else{
                msgbox("No hay estados para asignar al producto");
            }


        } catch (Exception e) {
            mu.msgbox("processEstados " + e.getMessage());
        }
    }

    private void processAgregarConteo() {
        try {
            int getrespuesta = xobj.getresult(Integer.class, "Inventario_Agregar_Conteo");

            if (getrespuesta > 0) {
                toastlong("Conteo agregado con éxito.");

                if (NuevoConteo) NuevoConteo = false;
                nBeResolucion = null;
                finish();
            }

        } catch (Exception e) {
            mu.msgbox("processAgregarConteo: " + e.getMessage());
        }
    }

    private void processGetCantidadContada() {
        try {
            CantidadContada = xobj.getresultSingle(Double.class, "Get_Conteo_Inv_CiclicoResult");

            if (CantidadContada > 0) {
                lblCantidadContada.setVisibility(View.VISIBLE);
                lblCantidadContada.setText("Conteo total: "+CantidadContada);
            } else {
                lblCantidadContada.setVisibility(View.GONE);
                lblCantidadContada.setText("");
            }

            if (BeInvEnc.Cambia_Ubicacion) {
                if (gl.inv_ciclico.IdUbicacion_nuevo != 0) {
                    pIdUbicacion = gl.inv_ciclico.IdUbicacion_nuevo;
                } else if (esCambioUbicacion && !txtUbicNueva.getText().toString().isEmpty() && !txtUbicNueva.getText().toString().equals("0")) {
                    pIdUbicacion = Integer.parseInt(txtUbicNueva.getText().toString());
                }
                if (pIdUbicacion > 0) execws(6);
            }

            if (esInvCongelado) LoadInvCongelado();
        } catch (Exception e) {
            mu.msgbox("processAgregarConteo: " + e.getMessage());
        }
    }

    private void processGetStockCongelado() {
        try {
            invCongelado = xobj.getresult(clsBeTrans_inv_ciclico.class,"Get_Stock_Congelado");

            NuevoConteo = true;
            if (invCongelado != null) {
                esInvCongelado = true;
                esCambioUbicacion = IdUbicacion != invCongelado.IdUbicacion;
                txtUbicNueva.setText(""+IdUbicacion);
                txtUbic.setText(""+invCongelado.IdUbicacion);
                IdUbicacion = invCongelado.IdUbicacion;

                execws(12);
            } else {
                toastlong("Producto o licencia no asignado para conteo.");
                txtLicencia.setText("");
                execws(7);
            }
        } catch (Exception e) {
            mu.msgbox("processGetStockCongelado: " + e.getMessage());
        }
    }

    private void processProductoUbic(){
        try {
            gBeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            if (gBeProducto != null) {
                lblProd.setText(gBeProducto.Nombre);
                lblUM.setText(gBeProducto.UnidadMedida.Nombre);

                if (!gBeProducto.Control_lote) {
                    tblote_cic.setVisibility(View.GONE);
                } else {
                    tblote_cic.setVisibility(View.VISIBLE);
                }

                if (!gBeProducto.Control_vencimiento) {
                    tblVence.setVisibility(View.GONE);
                } else {
                    tblVence.setVisibility(View.VISIBLE);
                }

                if (!gBeProducto.Control_peso) {
                    txtpeso_cic.setVisibility(View.INVISIBLE);
                    txtPesoContado.setVisibility(View.INVISIBLE);
                } else {
                    txtpeso_cic.setVisibility(View.VISIBLE);
                    txtPesoContado.setVisibility(View.VISIBLE);
                }

                gl.pprod.IdProducto = gBeProducto.IdProducto;
                execws(4);
            }
        } catch (Exception e) {
            mu.msgbox("processProducto:"+e.getMessage());
        }
    }

    private void LLenaPresentacion() {
        try {
            PresList.clear();

            if (BeProductoUbicacion != null){
                if (BeProductoUbicacion.Presentaciones.items != null) {

                    if (!existeConteo) {
                        clsBeProducto_Presentacion pres = new clsBeProducto_Presentacion();
                        pres.IdPresentacion = 0;
                        pres.Nombre = "Sin Presentación";

                        BeProductoUbicacion.Presentaciones.items.add(0, pres);
                    }

                    ArrayAdapter<clsBeProducto_Presentacion> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, BeProductoUbicacion.Presentaciones.items);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cboPres.setAdapter(dataAdapter);

                    if (existeConteo) {
                        int indice = BeProductoUbicacion.Presentaciones.items.stream()
                                .filter(obj -> obj.getIdPresentacion() == gl.inv_ciclico.IdPresentacion)
                                .map(BeProductoUbicacion.Presentaciones.items::indexOf)
                                .findFirst()
                                .orElse(-1);

                        if (indice != -1) {
                            cboPres.setSelection(indice);
                        } else {
                            cboPres.setSelection(0);
                        }
                    } else {
                        cboPres.setSelection(0);
                    }
                }
            }
        } catch (Exception e) {
            mu.msgbox("LLenaPresentacion " + e.getMessage());
        }
    }

    private void msgAskCantidadCero(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(9);
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

    private void msgAskCantidadCeroActualiza(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(1);
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

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gl.cerrarActividad2) {
            gl.cerrarActividad2 = false;
            finish();
        }
    }
}