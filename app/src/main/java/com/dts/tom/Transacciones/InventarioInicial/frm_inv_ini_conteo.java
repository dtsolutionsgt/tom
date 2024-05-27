package com.dts.tom.Transacciones.InventarioInicial;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prod;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prodList;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalleList;
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen;
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumenList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeUbic;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.IngUbic;
//import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.Listtramos;

public class frm_inv_ini_conteo extends PBase {

    private EditText txtUbicInv, txtCodBarra, txtLoteInvIni, txtVenceInvIni, txtCantInvIni, txtPesoInvIni;
    private TextView lblUbicDesc, lblDescProd, lblUnidadInv, lblLote,lblLotes, lblPeso, lblTituloForma, lblVence, lblVenceList,
            txtLote, txtFechaVen;
    private Button btnGuardarConteo, btnCompletar, btnBack;
    private Spinner cmbPresInvIni, cmbEstadoInvIni, cmbLotes,cmbFechasVence;
    private DatePicker dpResult;
    private ImageView imgDate, btnEditarLote, btnLoteCancel, btnEditarFecha, btnFechaCancel;
    private RelativeLayout relFechaItems;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private boolean verlote, vervence, emptyPres;
    private int codestmalo = 0;
    private int IdEstadoSelect=0;
    private int IdPresSelect=0;
    public static String CodBarra="";

    private boolean tiene_lotes,tiene_fechas;
    private String LoteSelect,FechaSelect;
    private boolean editarFecha, editarLote, bloqueaConteo;
    private double CantidadConteo = 0;

    // date
    private int year;
    private int month;
    private int day;

    private clsBeTrans_inv_tramo utramo = new clsBeTrans_inv_tramo();
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();
    private clsBeProducto_estadoList BeListEstado = new clsBeProducto_estadoList();
    private clsBeTrans_inv_stock_prodList InvTeorico = new clsBeTrans_inv_stock_prodList();
    private clsBeTrans_inv_stock_prodList InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();
    private clsBeTrans_inv_detalle  ditem = new clsBeTrans_inv_detalle();
    private final clsBeTrans_inv_stock_prod BeLotes = new clsBeTrans_inv_stock_prod();
    private clsBeTrans_inv_detalleList listInvDet = new clsBeTrans_inv_detalleList();
    private clsBeTrans_inv_resumen BeInvResumen = new clsBeTrans_inv_resumen();
    private clsBeTrans_inv_resumenList listInvRes= new clsBeTrans_inv_resumenList();

    private ArrayList<String> EstadoList = new ArrayList<String>();
    private ArrayList<String> PresList = new ArrayList<String>();
    private final ArrayList<String> LoteList = new ArrayList<String>();
    private final ArrayList<String> FechasVenceList = new ArrayList<String>();

    static final int DATE_DIALOG_ID = 999;

    private int pIdTramo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_conteo);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_conteo.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicInv = findViewById(R.id.txtUbicInv);
        txtCodBarra = findViewById(R.id.txtCodBarra);
        txtLoteInvIni = findViewById(R.id.txtLoteInvIni);
        txtVenceInvIni = findViewById(R.id.txtVenceInvIni);
        txtCantInvIni = findViewById(R.id.txtCantInvIni);
        txtPesoInvIni = findViewById(R.id.txtPesoInvIni);
        txtLote = findViewById(R.id.txtLote);
        txtFechaVen = findViewById(R.id.txtFechaVen);

        lblUbicDesc = findViewById(R.id.lblUbicDesc);
        lblDescProd = findViewById(R.id.lblDescProd);
        lblUnidadInv = findViewById(R.id.lblUnidadInv);
        lblLote = findViewById(R.id.textView35);
        lblPeso = findViewById(R.id.textView38);
        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblVence = findViewById(R.id.textView36);

        cmbPresInvIni = findViewById(R.id.cmbPresInvIni);
        cmbEstadoInvIni = findViewById(R.id.cmbEstadoInvIni);

        btnGuardarConteo = findViewById(R.id.btnGuardarConteo);
        //btnCompletar = (Button) findViewById(R.id.btnCompletar);
        btnBack = findViewById(R.id.btnBack);
        btnEditarLote = findViewById(R.id.btnEditarLote);
        btnLoteCancel = findViewById(R.id.btnLoteCancel);
        btnEditarFecha = findViewById(R.id.btnEditarFecha);
        btnFechaCancel = findViewById(R.id.btnFechaCancel);

        relFechaItems = findViewById(R.id.relFechaItems);

        dpResult = findViewById(R.id.datePicker3);
        imgDate = findViewById(R.id.imgDate2);

        cmbLotes = findViewById(R.id.cmbLotes);
        lblLotes = findViewById(R.id.lblLotes);
        lblVenceList = findViewById(R.id.lblVenceList);
        cmbFechasVence = findViewById(R.id.cmbFechasVence);
        tiene_fechas = false;
        tiene_lotes = false;
        LoteSelect = "";
        FechaSelect = "";
        emptyPres = false;
        editarFecha = false;
        editarLote = false;
        bloqueaConteo = true;

        setCurrentDateOnView();

        setHandles();

        Load();

    }

    private void setHandles() {

        try {

            txtUbicInv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { }
            });

            txtUbicInv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicInv.getText().toString().isEmpty()) {
                            execws(2);
                        }
                    }

                    return false;
                }
            });

            txtCodBarra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { }
            });

            txtCodBarra.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtCodBarra.getText().toString().isEmpty()) {
                            emptyPres = false;
                            execws(3);
                        }
                    }

                    return false;
                }
            });

            txtCantInvIni.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (txtPesoInvIni.getVisibility()==View.VISIBLE){
                            txtPesoInvIni.setSelectAllOnFocus(true);
                            txtPesoInvIni.requestFocus();
                        }else{
                            execws(14);
                        }
                    }

                    return false;
                }
            });

            cmbEstadoInvIni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoSelect=BeListEstado.items.get(position).IdEstado;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbPresInvIni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    //IdPresSelect=BeListPres.items.get(position).IdPresentacion;

                    //GT 19112021 si agregamos un registro vacio a la lista presentacion, la posicion 0 es la vacia
                    if (emptyPres && position ==0 ) {
                        IdPresSelect = 0;

                    }else if(emptyPres && position> 0){
                        IdPresSelect=BeListPres.items.get(position-1).IdPresentacion;
                    }else{
                        IdPresSelect=BeListPres.items.get(position).IdPresentacion;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbLotes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    LoteSelect=InvTeorico.items.get(position).Lote;
                    cmbFechasVence.setSelection(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbFechasVence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    FechaSelect=InvTeorico.items.get(position).Fecha_vence;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            btnEditarLote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editarLote = true;

                    btnLoteCancel.setVisibility(View.VISIBLE);
                    txtLote.setVisibility(View.VISIBLE);
                    txtLote.requestFocus();

                    btnEditarLote.setVisibility(View.GONE);
                    cmbLotes.setVisibility(View.GONE);
                }
            });

            btnLoteCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editarLote = false;
                    btnEditarLote.setVisibility(View.VISIBLE);
                    cmbLotes.setVisibility(View.VISIBLE);

                    btnLoteCancel.setVisibility(View.GONE);
                    txtLote.setVisibility(View.GONE);
                    txtLote.setText("");
                }
            });

            btnEditarFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editarFecha = true;
                    relFechaItems.setVisibility(View.VISIBLE);
                    btnFechaCancel.setVisibility(View.VISIBLE);
                    txtFechaVen.requestFocus();
                    txtFechaVen.requestFocus();

                    cmbFechasVence.setVisibility(View.GONE);
                    btnEditarFecha.setVisibility(View.GONE);

                }
            });

            btnFechaCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editarFecha = false;
                    cmbFechasVence.setVisibility(View.VISIBLE);
                    btnEditarFecha.setVisibility(View.VISIBLE);

                    relFechaItems.setVisibility(View.GONE);
                    btnFechaCancel.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandles:" + e.getMessage());
        }
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        StringBuilder tmpFecha =  new StringBuilder().append(day)
                .append("-").append(month).append("-").append(year)
                .append(" ");

        // #AT 20211126 Se da formato a la fecha
        setFecha(tmpFecha);

        // set current date into datepicker
        dpResult.init(year, month, day, null);

        txtUbicInv.setFocusable(true);
        txtUbicInv.requestFocus();

    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            // set selected date into textview
            StringBuilder tmpFecha =  new StringBuilder().append(day)
                    .append("-").append(month).append("-").append(year)
                    .append(" ");

            // #AT 20211126 Se da formato a la fecha
            setFecha(tmpFecha);

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

    public void ChangeDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    // #AT 20211126 Se da formato a la fecha
    public void setFecha(StringBuilder tmpFecha) {
        String fecha = du.convierteFechaSinHora(String.valueOf(tmpFecha));

        if (editarFecha) {
            txtFechaVen.setText(fecha);
        } else {
            txtVenceInvIni.setText(fecha);
        }
    }

    private void Load() {

        try {

            txtUbicInv.setText("");
            txtCodBarra.setText("");
            txtLoteInvIni.setText("");
            txtVenceInvIni.setText("");
            txtCantInvIni.setText("");
            txtPesoInvIni.setText("");

            lblUbicDesc.setText("");
            lblDescProd.setText("");
            lblUnidadInv.setText("");

            txtLoteInvIni.setVisibility(View.GONE);
            lblLote.setVisibility(View.GONE);

            lblLotes.setVisibility(View.GONE);
            cmbLotes.setVisibility(View.GONE);

            lblVenceList.setVisibility(View.GONE);
            cmbFechasVence.setVisibility(View.GONE);

            lblPeso.setVisibility(View.GONE);
            txtPesoInvIni.setVisibility(View.GONE);

            relFechaItems.setVisibility(View.GONE);
            btnEditarFecha.setVisibility(View.GONE);
            btnEditarLote.setVisibility(View.GONE);

            // #AT 20211126 Se da formato a la fecha
            String tmpFecha = du.convierteFechaMostrar(du.getFechaActual());
            String fecha = du.convierteFechaSinHora(tmpFecha);
            txtVenceInvIni.setText(fecha);

            pIdTramo=0;

            if (BeUbic.IdUbicacion > 0) {
                txtUbicInv.setText("" + BeUbic.IdUbicacion);
                lblUbicDesc.setText(BeUbic.Descripcion);
                txtCodBarra.requestFocus();
            }

            if (IngUbic){
               pIdTramo = BeUbic.IdTramo;
            }else{
                pIdTramo = BeInvTramo.Idtramo;
            }

            txtCantInvIni.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoInvIni.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);

            execws(1);

        } catch (Exception e) {
            mu.msgbox("Load:" + e.getMessage());
        }
    }

    private boolean validaUbicacion() {
        boolean esValida = false;
        try {
            if (BeUbic != null) {
                if (BeUbic.Tramo.IdTramo != 0) {
                    esValida = BeInvTramo.Idtramo == BeUbic.Tramo.IdTramo;
                }
            }
        } catch (Exception e) {
            mu.msgbox("validaUbicacion: "+ e.getMessage());
        }

        return esValida;
    }

    private void Procesa_Ubicacion() {

        try {

            if (!validaUbicacion()) {
                txtUbicInv.requestFocus();
                txtUbicInv.setSelectAllOnFocus(true);
                txtCodBarra.setText("");
                txtCodBarra.setEnabled(false);

                mu.msgbox("La ubicación "+ txtUbicInv.getText().toString() +" no pertenece al tramo: " + BeInvTramo.Nombre_Tramo);
            } else {
                txtCodBarra.setEnabled(true);
                txtCodBarra.requestFocus();
                txtCodBarra.setSelectAllOnFocus(true);
            }

            /*if (BeUbic.Tramo.IdTramo != 0) {
                if (BeInvTramo.Idtramo != BeUbic.Tramo.IdTramo) {

                    txtUbicInv.requestFocus();
                    txtUbicInv.setSelectAllOnFocus(true);
                    txtCodBarra.setText("");
                    txtCodBarra.setEnabled(false);

                    mu.msgbox("La ubicación no partenece al tramo: " + BeInvTramo.Nombre_Tramo);
                } else {
                    txtCodBarra.setEnabled(true);
                    txtCodBarra.requestFocus();
                    txtCodBarra.setSelectAllOnFocus(true);
                }
            }

            if (BeUbic.Nivel > 1) {

            }*/

            lblUbicDesc.setText("" + BeUbic.Descripcion);


        } catch (Exception e) {
            mu.msgbox("Procesa_Ubicacion");
        }
    }

    private void Carga_Det_Producto() {

        try {

            lblDescProd.setText(BeProducto.Nombre);

            lblUbicDesc.setText(BeProducto.UnidadMedida.Nombre);

            //GT 19112021 aqui primero valida que prod no tenga pres para agregar esa opcion al combo
            //execws(4);
            execws(6);


        } catch (Exception e) {
            mu.msgbox("Carga_Det_Producto:" + e.getMessage());
        }
    }

    private void Llena_Det_Presentacion_Producto() {

        try {

            //PresList.clear();

            for (clsBeProducto_Presentacion BePres : BeListPres.items) {
                PresList.add(BePres.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresInvIni.setAdapter(dataAdapter);

            if (PresList.size() > 0){
                if (emptyPres) {
                    cmbPresInvIni.setSelection(1);
                } else {
                    cmbPresInvIni.setSelection(0);
                }
            }

        } catch (Exception e) {
            mu.msgbox("llenaDetPresentacionProducto:" + e.getMessage());
        }
    }

    private void Llena_Det_Estados_Producto() {

        try {

            EstadoList.clear();

            for (clsBeProducto_estado BeEstado : BeListEstado.items) {
                if (!BeEstado.Utilizable) {
                    codestmalo = BeEstado.IdEstado;
                }
                EstadoList.add(BeEstado.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoInvIni.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoInvIni.setSelection(0);

        } catch (Exception e) {
            mu.msgbox("Llena_Det_Estados_Producto:" + e.getMessage());
        }
    }

    private void Llena_Lotes() {

        try {

            LoteList.clear();

            //#AT20240527 Distinct por lote
            List<clsBeTrans_inv_stock_prod> auxLista = InvTeorico.items
                                                       .stream()
                                                       .filter(mu.distinctByKey(clsBeTrans_inv_stock_prod::getLote))
                                                       .collect(Collectors.toList());

            for (clsBeTrans_inv_stock_prod BeLotes : auxLista) {

                LoteList.add(BeLotes.Lote);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LoteList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbLotes.setAdapter(dataAdapter);

            if (LoteList.size() > 0) {

                tiene_lotes = true;
                btnEditarLote.setVisibility(View.VISIBLE);
                lblLotes.setVisibility(View.VISIBLE);
                cmbLotes.setVisibility(View.VISIBLE);
                cmbLotes.setSelection(0);

                lblLote.setVisibility(View.GONE);
                txtLoteInvIni.setVisibility(View.GONE);

                Llena_FechasVence();

            }else{
                tiene_lotes = false;
                lblLotes.setVisibility(View.GONE);
                cmbLotes.setVisibility(View.GONE);

            }


        } catch (Exception e) {
            mu.msgbox("Llena_Lotes:" + e.getMessage());
        }
    }

    private void Llena_FechasVence() {

        try {

            FechasVenceList.clear();

            //#AT20240527 Distinct por fecha vencimiento
            List<clsBeTrans_inv_stock_prod> auxLista = InvTeorico.items
                                                       .stream()
                                                       .filter(mu.distinctByKey(clsBeTrans_inv_stock_prod::getFecha_vence))
                                                       .collect(Collectors.toList());

            for (clsBeTrans_inv_stock_prod BeLotes : auxLista) {

                //Date date = du.convierteFechaMostrar(BeLotes.Fecha_vence);
                String date = du.convierteFechaMostrar(BeLotes.Fecha_vence);

                FechasVenceList.add(date);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FechasVenceList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbFechasVence.setAdapter(dataAdapter);

            if (FechasVenceList.size() > 0) {

                tiene_fechas = true;
                btnEditarFecha.setVisibility(View.VISIBLE);
                lblVenceList.setVisibility(View.VISIBLE);
                cmbFechasVence.setVisibility(View.VISIBLE);
                cmbFechasVence.setSelection(0);

                lblVence.setVisibility(View.GONE);
                txtVenceInvIni.setVisibility(View.GONE);
                imgDate.setVisibility(View.GONE);

                cmbFechasVence.requestFocus();
                cmbFechasVence.setFocusable(true);

            }else{

                tiene_fechas = false;
                txtLoteInvIni.requestFocus();
                txtLoteInvIni.setFocusable(true);

                lblVenceList.setVisibility(View.GONE);
                cmbFechasVence.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            mu.msgbox("Llena_FechasVence:" + e.getMessage());
        }
    }


    private void Carga_Datos_Producto(){

        try{

            mostrarlote(BeProducto.Control_lote);

            mostrarvence(BeProducto.Control_vencimiento);

            txtCantInvIni.setText("");

            lblUnidadInv.setText(BeProducto.UnidadMedida.Nombre);

            muestra_peso(BeProducto.Control_peso);

            txtPesoInvIni.setText("");

            Carga_Det_Producto();



            //GT 17112021: se llama a inv teorico porque, el objeto InvTeorico esta vacio y nunca entrara a la validación
            //execws(6);

//            if (InvTeorico != null) {
//                if (InvTeorico.items != null) {
//
//                    InvTeoricoPorProducto.items = new ArrayList<>();
//                    InvTeoricoPorProducto.items = stream(InvTeorico.items).where(c -> c.Idinventario == BeInvEnc.Idinventarioenc &&
//                            c.IdProducto == BeProducto.IdProducto).toList();
//
//                    if (InvTeoricoPorProducto != null) {
//                        if (InvTeoricoPorProducto.items != null) {
//                            execws(6);
//                        }
//                    }
//                }
//            }

        }catch (Exception e){
            mu.msgbox("Carga_Datos_Producto:"+e.getMessage());
        }
    }

    private void mostrarvence(boolean Control_vence){

        try{

            if (Control_vence){
                lblVence.setVisibility(View.VISIBLE);
                txtVenceInvIni.setVisibility(View.VISIBLE);
                imgDate.setVisibility(View.VISIBLE);
            }else{
                lblVence.setVisibility(View.GONE);
                txtVenceInvIni.setVisibility(View.GONE);
                imgDate.setVisibility(View.GONE);
            }


        }catch (Exception e){
            mu.msgbox("mostrarvence:"+e.getMessage());
        }
    }

    private void muestra_peso(boolean Contro_peso){

        try{

            if (Contro_peso){
                lblPeso.setVisibility(View.VISIBLE);
                txtPesoInvIni.setVisibility(View.VISIBLE);
            }else{
                lblPeso.setVisibility(View.GONE);
                txtPesoInvIni.setVisibility(View.GONE);
            }

        }catch (Exception e){
            mu.msgbox("muestra_peso:"+e.getMessage());
        }
    }

    private void mostrarlote(boolean Control_lote) {

        try {

            if (Control_lote) {
                lblLote.setVisibility(View.VISIBLE);
                txtLoteInvIni.setVisibility(View.VISIBLE);
            } else {
                lblLote.setVisibility(View.GONE);
                txtLoteInvIni.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            mu.msgbox("mostrarlote:" + e.getMessage());
        }
    }

    private void Valida_Inventario_Teorico(){

        try{

            //Falta agregar un combo de lote y llenarlo con el inventario teorico
            /* InvTeoricoPorProducto.items = new ArrayList<>();
                    InvTeoricoPorProducto.items = stream(InvTeorico.items).where(c -> c.Idinventario == BeInvEnc.Idinventarioenc &&
                            c.IdProducto == BeProducto.IdProducto).toList();*/

            PresList.clear();

            emptyPres = true;

            if (InvTeorico != null) {

                if (InvTeorico.items != null) {

                    //GT 19112021: si hay un prod sin presentacion, se carga primero op vacia y luego las existentes

                    for (clsBeTrans_inv_stock_prod BeLotes : InvTeorico.items) {

                        if (BeLotes.IdPresentacion ==0) {
                            emptyPres = true;
                        }
                    }

                    if (emptyPres) {
                        PresList.add("Sin Presentación");
                    }

                    if(BeProducto.Control_lote){
                        Llena_Lotes();
                    } else if(InvTeorico.items.size() == 1 && InvTeorico.items.get(0).Lote.isEmpty()) {
                        lblLotes.setVisibility(View.GONE);
                        cmbLotes.setVisibility(View.GONE);

                        lblVenceList.setVisibility(View.GONE);
                        cmbFechasVence.setVisibility(View.GONE);

                        String tmpFecha = du.convierteFechaMostrar(du.getFechaActual());
                        String fecha = du.convierteFechaSinHora(tmpFecha);
                        txtVenceInvIni.setText(fecha);

                        lblVence.setVisibility(View.VISIBLE);
                        txtVenceInvIni.setVisibility(View.VISIBLE);
                        imgDate.setVisibility(View.VISIBLE);
                        btnEditarLote.setVisibility(View.GONE);
                        btnEditarFecha.setVisibility(View.GONE);
                    }
                }
            }

            //GT 19112021: ya validamos si hay prod sin presentación, ahora se carga las presentaciones, lotes y demas
            execws(4);

        }catch (Exception e){
            mu.msgbox("Valida_Inventario_Teorico:"+e.getMessage());
        }
    }

    private void Agrega_Producto_Nuevo(){

        try{

            browse=1;
            CodBarra = txtCodBarra.getText().toString();
            txtCodBarra.setText("");
            startActivity(new Intent(this, frm_inv_agrega_prd.class));

        }catch (Exception e){
            mu.msgbox("Agrega_Producto_Nuevo:"+e.getMessage());
        }
    }

    private void Guardar_Inventario_Conteo(){

        try{

            boolean aceptamalo=false;
            int vest=0;

            if (validaUbicacion()) {
                if (IdEstadoSelect != 0) {
                    if (IdEstadoSelect == codestmalo) {
                        execws(8);
                    } else {
                        Guardar_Item();
                    }
                } else {
                    mu.msgbox("El estado no es válido");
                }
            } else {
                mu.msgbox("La ubicación no partenece al tramo: " + BeInvTramo.Nombre_Tramo);
                txtUbicInv.setText("");
                lblUbicDesc.setText("");
            }

        }catch (Exception e){
            mu.msgbox("Guardar_Inventario_Conteo:"+e.getMessage());
        }
    }

    private void ExisteConteo() {
        //clsBeTrans_inv_tramo BeTramo = new clsBeTrans_inv_tramo();

        try {
            Crea_Item();

            /*if (Listtramos != null) {
                if (Listtramos.items != null) {
                    BeTramo = stream(Listtramos.items).where(c-> c.Idtramo == BeUbic.Tramo.IdTramo).first();
                }
            }*/

            if (BeInvTramo != null) {
                if (BeInvTramo.Es_Rack) {
                    execws(13);
                } else {
                    if (ditem.IdPresentacion > 0) {
                        execws(9);
                    } else {
                        Continua_Guardando_Item();
                    }
                }
            } else {
                mu.msgbox("El tramo no es válido");
            }

        } catch (Exception e) {
            mu.msgbox("ExisteConteo: "+e.getMessage());
        }
    }

    private void Guardar_Item(){

        try{

            if (!Validar_Detalle()){
                return;
            }

            ExisteConteo();

            /*if (ditem.IdPresentacion>0){
                execws(9);
            }else{
                Continua_Guardando_Item();
            }*/

        }catch (Exception e){
            mu.msgbox("Guardar_Item:"+e.getMessage());
        }
    }

    private void Continua_Guardando_Item(){

        try{

            if (ditem.Idunidadmedida==0){
                mu.msgbox("Unidad de medida básica es 0");
                return;
            }else{
                execws(10);
            }

        }catch (Exception e){
            mu.msgbox("Continua_Guardando_Item:"+e.getMessage());
        }
    }

    private void Crea_Item(){

        try{

            ditem = new clsBeTrans_inv_detalle();

            ditem.Idinventariodet=0;
            ditem.Idinventarioenc =BeInvEnc.Idinventarioenc;
            ditem.Idtramo = BeUbic.Tramo.IdTramo;
            ditem.IdUbicacion = BeUbic.IdUbicacion;
            ditem.Idoperador = gl.IdOperador;
            ditem.Idproducto = BeProducto.IdProducto;
            ditem.IdPresentacion = IdPresSelect;
            ditem.Idunidadmedida = BeProducto.IdUnidadMedidaBasica;
            if (ditem.Idunidadmedida==0){
                ditem.Idunidadmedida = -1;
            }
            if (BeProducto.Control_lote){
                //GT 17112021: si existe un lote al producto
                if(tiene_lotes){
                    //#AT20220506 Opción para editar lote obtiene el valor de txtLote
                    if (editarLote) {
                        ditem.Lote = txtLote.getText().toString();
                    } else {
                        ditem.Lote = LoteSelect;
                    }
                }else{
                    ditem.Lote = txtLoteInvIni.getText().toString();
                }

            }else{
                ditem.Lote="";
            }

            if (BeProducto.Control_vencimiento){

                //GT 17112021: si existe una fecha vencimiento
                if(tiene_fechas){
                    //#AT20220506 Opción para editar fecha obtiene el valor de txtFechaVen
                    if (editarFecha) {
                        ditem.Fecha_vence = du.convierteFecha(txtFechaVen.getText().toString());
                    } else {
                        ditem.Fecha_vence = FechaSelect;
                    }
                }else{
                    ditem.Fecha_vence = du.convierteFecha(txtVenceInvIni.getText().toString());
                }

            }else{
                ditem.Fecha_vence="1900-01-01T00:00:00";
            }

            ditem.Serie="";
            ditem.Idproductoestado = ""+IdEstadoSelect;
            ditem.Cantidad = Double.parseDouble(txtCantInvIni.getText().toString());
            ditem.Fecha_captura = du.Fecha_CompletaT();
            ditem.Host = "1";
            ditem.Nom_producto = BeProducto.Nombre;
            ditem.Nom_operador = gl.OperadorBodega.Operador.Nombres;
            ditem.Carga = 0;
            ditem.Peso = Double.parseDouble(txtPesoInvIni.getText().toString());
            //#AT20220504 Se agrega IdPropietarioBodega e IdBodega para ser guardados
            ditem.IdPropietarioBodega = InvTeorico.items.get(0).IdPropietarioBodega;
            ditem.IdBodega = gl.IdBodega;

            //Valida si existe un conteo con el  producto y ubicacion
            //execws(13);

        }catch (Exception e){
            mu.msgbox("Crea_Item:"+e.getMessage());
        }
    }

    private boolean Validar_Detalle(){

        try{

            if (BeUbic.Tramo.IdTramo==0){
                mu.msgbox("Tramo Incorrecto");
                txtUbicInv.setFocusable(true);
                return false;
            }

            if (BeProducto.IdProducto==0){
                mu.msgbox("Producto incorrecto");
                txtCodBarra.setFocusable(true);
                return false;
            }

            if (IdEstadoSelect<=0){
                mu.msgbox("Estado incorrecto");
                return  false;
            }

            if (txtLoteInvIni.getVisibility()==View.VISIBLE){
                if(txtLoteInvIni.getText().toString().isEmpty()){
                    mu.msgbox("Lote Incorrecto");
                    txtLoteInvIni.setFocusable(true);
                    return  false;
                }
            }

            if (txtCantInvIni.getText().toString().equals("0")||
                    txtCantInvIni.getText().toString().isEmpty()||
                    txtCantInvIni.getText().toString().equals("")){
                mu.msgbox("Cantidad incorrecta");
                txtCantInvIni.setFocusable(true);
                return false;
            }

            if (txtPesoInvIni.getVisibility()==View.VISIBLE){
                if (txtPesoInvIni.getText().toString().equals("0")||
                        txtPesoInvIni.getText().toString().isEmpty()||
                        txtPesoInvIni.getText().toString().equals("")){
                mu.msgbox("Peso Incorrecto");
                txtPesoInvIni.setFocusable(true);
                return false;
                }
            }else{
                txtPesoInvIni.setText("0");
            }

            if (InvTeorico == null) {
                mu.msgbox("El producto no es válido: "+txtCodBarra.getText().toString());
                return false;
            }

        }catch (Exception e){
            mu.msgbox("Validar_Detalle:"+e.getMessage());
        }

        return  true;
    }

    private void Limpiar_Campos(){

        try{

            relFechaItems.setVisibility(View.GONE);
            btnEditarFecha.setVisibility(View.GONE);
            btnEditarLote.setVisibility(View.GONE);
            btnLoteCancel.setVisibility(View.GONE);
            btnFechaCancel.setVisibility(View.GONE);
            txtLote.setVisibility(View.GONE);

            txtUbicInv.setText("");
            lblUbicDesc.setText("");
            txtCodBarra.setText("");
            lblDescProd.setText("");
            txtCantInvIni.setText("");
            lblUbicDesc.setText("");
            txtVenceInvIni.setText("");
            txtLoteInvIni.setText("");
            txtPesoInvIni.setText("");

            txtLote.setText("");
            txtFechaVen.setText("");
            //#AT20220505 setVisibility(View.GONE) lblPeso y txtPeso luego de guardar el conteo de inventario
            lblPeso.setVisibility(View.GONE);
            txtPesoInvIni.setVisibility(View.GONE);

            lblVenceList.setVisibility(View.GONE);
            lblLotes.setVisibility(View.GONE);
            cmbLotes.setVisibility(View.GONE);
            cmbFechasVence.setVisibility(View.GONE);

           BeProducto = new clsBeProducto();
           BeListPres = new clsBeProducto_PresentacionList();
           BeListEstado = new clsBeProducto_estadoList();
           InvTeorico = new clsBeTrans_inv_stock_prodList();
           InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();
           ditem = new clsBeTrans_inv_detalle();

            EstadoList = new ArrayList<String>();
            PresList = new ArrayList<String>();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresInvIni.setAdapter(dataAdapter);

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoInvIni.setAdapter(dataAdapter1);

            codestmalo = 0;
            IdPresSelect = 0;
            IdEstadoSelect = 0;
            //GT 17112021 variables usadas cuando hay varios lotes y fechas vencimiento
            FechaSelect="";
            LoteSelect="";
            tiene_fechas = false;
            tiene_lotes = false;
            editarFecha = false;
            editarLote = false;
            bloqueaConteo = false;

            setCurrentDateOnView();

            IngUbic = false;


        }catch (Exception e){
            mu.msgbox("Limpiar_Campos:"+e.getMessage());
        }
    }

    public void BotonCompletar(View view){

        try{

            msgCompletar("¿Completar conteo de tramo  ?");

        }catch (Exception e){
            mu.msgbox("BotonCompletar:"+e.getMessage());
        }
    }

    public void BotonDetalle(View view){

        try{

            browse=1;
            startActivity(new Intent(this, frm_inv_ini_contados.class));

        }catch (Exception e){
            mu.msgbox("BotonDetalle"+e.getMessage());
        }
    }

    public void BotonExit(View view){
        Limpiar_Campos();
        super.finish();
    }

    public void BotonGuardar(View view){
        try {
            execws(14);
        } catch (Exception e) {
            mu.msgbox("BotonGuardar: "+e.getMessage());
        }
    }

    private void Completar_tramo(){

        try{

            utramo.Det_estado = "Finalizado";
            utramo.Det_fin = du.getFechaActual();
            utramo.Det_idoperador = gl.OperadorBodega.IdOperador;
            utramo.IdBodega = gl.IdBodega;

            execws(12);

        }catch (Exception e){
            mu.msgbox("Completar_tramo:"+e.getMessage());
        }
    }

    private void msgCompletar(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Completar_tramo();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaProductoPallet"+e.getMessage());
        }
    }

    private void msgNoExistente(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Agrega_Producto_Nuevo();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgRegConteo(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Guardar_Item();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgRegPallet(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Continua_Guardando_Item();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent, Url);
        }

        @Override
        public void wsExecute() {

            try {

                switch (ws.callback) {

                    case 1:
                        callMethod("Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo",
                                "pidinventario", BeInvEnc.Idinventarioenc, "pidtramo", pIdTramo);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega", "pBarra", txtUbicInv.getText().toString(),
                                "pIdBodega", gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_Inventario_Teorico_By_Codigo_O_Licencia",
                                   "pIdInventario", BeInvEnc.Idinventarioenc,
                                   "pCodigo", txtCodBarra.getText().toString().replace("$",""),
                                   "pIdBodega", gl.IdBodega);
                        break;

                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto_IdBodega", "pIdProducto", BeProducto.IdProducto, "pIdBodega",gl.IdBodega, "pActivo", true);
                        break;
                    case 5:
                        callMethod("Get_Estados_By_IdPropietario", "pIdPropietario", BeProducto.IdPropietario);
                        break;
                    case 6:
                        callMethod("Get_Inventario_Teorico_By_Codigo","IdInventarioEnc",BeInvEnc.Idinventarioenc,
                                "IdProducto",BeProducto.IdProducto);
                        break;
                    case 7:
                        callMethod("Existe_Producto","pCodigo",txtCodBarra.getText().toString());
                        break;
                    case 8:
                        callMethod("Inventario_Inicial_Acepta_Mal_Estado_By_IdUbicacion","pIdUbicacion",BeUbic.IdUbicacion,"pEstMalo",codestmalo);
                        break;
                    case 9:
                        callMethod("validaUbicacionPalet","pidinvenc",ditem.Idinventarioenc,"pidubicacion",BeUbic.IdUbicacion,
                                "pidpresentacion",IdPresSelect);
                        break;
                    case 10:
                        callMethod("Agregar_Inventario_Inicial","pItem",ditem);
                        break;
                    case 11:
                        callMethod("Actualizar_Inventario_Inicial_By_BeTransInvTramo","pTramo",utramo);
                        break;
                    case 12:
                        callMethod("Actualizar_Inventario_Inicial_By_BeTransInvTramo","pTramo",utramo);
                        break;
                    case 13:
                        callMethod("Existe_Conteo",
                                        "pIdUbicacion",BeUbic.IdUbicacion,
                                              "pIdBodega", gl.IdBodega,
                                              "pIdProducto", BeProducto.IdProducto);
                        break;
                    case 14:
                        callMethod("Get_CantidadInvVer_By_Producto",
                                        "pIdUbicacion",BeUbic.IdUbicacion,
                                              "pIdProducto", BeProducto.IdProducto,
                                              "pIdBodega", gl.IdBodega,
                                              "pIdPresentacion", IdPresSelect);
                        break;
                    case 15:
                        callMethod("Existe_Verificacion",
                                "pIdUbicacion",BeUbic.IdUbicacion,
                                "pIdBodega", gl.IdBodega,
                                "pIdProducto", BeProducto.IdProducto);
                        break;

                }

            } catch (Exception e) {
                mu.msgbox(e.getClass() + "WebServiceHandler:" + e.getMessage());
            }

        }

    }

    @Override
    public void wsCallBack(Boolean throwing, String errmsg, int errlevel) {

        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {

                case 1:
                    processBeTramo();
                    break;
                case 2:
                    processUbic();
                    break;
                case 3:
                    //·EJC20220502
                    processInvTeorico();
                    break;
                case 4:
                    processPresentacion();
                    break;
                case 5:
                    processEstados();
                    break;
                case 6:
                    processInvTeorico();
                    break;
                case 7:
                    processExisteProducto();
                    break;
                case 8:
                    processAceptaMalo();
                    break;
                case 9:
                    processValidaUbicPallet();
                    break;
                case 10:
                    processAgInventario();
                    break;
                case 11:
                    Limpiar_Campos();
                    txtUbicInv.requestFocus();
                    txtUbicInv.selectAll();
                    break;
                case 12:
                    Limpiar_Campos();
                    super.finish();
                    break;
                case 13:
                    processExisteConteo();
                    break;
                case 14:
                    processValidaCantidadVerificacion();
                    break;
                case 15:
                    processExisteVerificacion();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processBeTramo() {

        try {

            utramo = xobj.getresult(clsBeTrans_inv_tramo.class, "Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo");

            BeInvTramo = utramo;

            lblTituloForma.setText("TRAMO :" + BeInvTramo.Nombre_Tramo);

            if (txtUbicInv.getText().toString().isEmpty()) {
                txtUbicInv.setSelectAllOnFocus(true);
                txtUbicInv.requestFocus();
            }

        } catch (Exception e) {
            mu.msgbox("processBeTramo:" + e.getMessage());
        }
    }

    private void processUbic() {

        try {

            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class, "Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                Procesa_Ubicacion();
            } else {
                mu.msgbox("La ubicación "+ txtUbicInv.getText().toString()+" no existe.");
                txtUbicInv.setText("");
                lblUbicDesc.setText("");
            }

        } catch (Exception e) {
            mu.msgbox("processUbic:" + e.getMessage());
        }
    }

    private void processProducto() {

        try {

            //·EJC20220502: Obsoleto, obtener inv teòrico a partir del escaneo de producto o licencia.
            //BeProducto = xobj.getresult(clsBeProducto.class, "Get_Inventario_Teorico_By_Codigo_O_Licencia");

            if (BeProducto != null) {

                Carga_Datos_Producto();

            } else {

                if (BeInvEnc.Capturar_no_existente) {
                    execws(7);
                }else{
                    mu.msgbox("No se puede agregar productos nuevos");
                }

            }

        } catch (Exception e) {
            mu.msgbox("processProducto:" + e.getMessage());
        }
    }

    private void processPresentacion(){

        try{

            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto_IdBodega");

            if (BeListPres!=null){
                if (BeListPres.items!=null){
                    Llena_Det_Presentacion_Producto();
                }
            } else {
                //#AT20220505 Si no se tiene presentación del producto
                if (emptyPres) {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPresInvIni.setAdapter(dataAdapter);

                    cmbPresInvIni.setSelection(0);
                } else {
                    cmbPresInvIni.setVisibility(View.GONE);
                }
            }

            execws(5);

        }catch (Exception e){
            mu.msgbox("processPresentacion:"+e.getMessage());
        }
    }

    private void processEstados(){

        try{

            BeListEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario");

            if (BeListEstado!=null){
                if (BeListEstado.items!=null){
                    Llena_Det_Estados_Producto();
                }else{
                    mu.msgbox("Los estados no existen");
                    return;
                }
            }else{
                mu.msgbox("Los estados no existen");
                return;
            }

        }catch (Exception e){
            mu.msgbox("");
        }
    }

    private void processInvTeorico(){

        try {

            InvTeorico = xobj.getresult(clsBeTrans_inv_stock_prodList.class,"Get_Inventario_Teorico_By_Codigo_O_Licencia");

            if(InvTeorico!=null){

                BeProducto= InvTeorico.items.get(0).BeProducto;
                lblDescProd.setText(BeProducto.Codigo+" - "+BeProducto.Nombre);
                lblUnidadInv.setText(BeProducto.UnidadMedida.Nombre);

                //#AT20220505 Si control_peso = true se muestran lblPeso y txtPeso
                muestra_peso(BeProducto.Control_peso);

                Valida_Inventario_Teorico();

            } else {
                if (BeInvEnc.Capturar_no_existente) {
                    execws(7);
                }else{
                    mu.msgbox("No se puede agregar productos nuevos");
                }
            }

        }catch (Exception e){
            mu.msgbox("processInvTeorico:"+e.getMessage());
        }
    }

    private void processExisteProducto(){

        try{

            boolean Existe=false;

            Existe = xobj.getresult(Boolean.class,"Existe_Producto");

            if (!Existe){
                msgNoExistente("El producto no existe en el maestro,¿Desea insertarlo?");
            }else{
                mu.msgbox("No existe ningún producto con ese código de barra en esta bodega");
                return;
            }



        }catch (Exception e){
            mu.msgbox("processExisteProducto: "+e.getMessage());
        }
    }

    private  void processAceptaMalo(){

        try{

            boolean aceptaMalo= xobj.getresult(Boolean.class,"Inventario_Inicial_Acepta_Mal_Estado_By_IdUbicacion");

            if (aceptaMalo){
                Guardar_Item();
            }else{
                msgRegConteo("La ubicacion no está configurada para producto dañado."
                        +"\n¿Registrar conteo en ésta ubicación de todas formas?");
            }

        }catch (Exception e){
            mu.msgbox("processAceptaMalo:"+e.getMessage());
        }
    }

    private void processValidaUbicPallet(){

        try{

            int ubPallet=0;

            ubPallet = xobj.getresult(Integer.class,"validaUbicacionPalet");

            if (ubPallet==1){
                msgRegPallet("Ya se contó un pallet en esta ubicación."
                        +"\n¿Registrar el pallet en esta ubicación de todas formas?");
            }else{
                Continua_Guardando_Item();
            }

        }catch (Exception e){
            mu.msgbox("processValidaUbicPallet:"+e.getMessage());
        }
    }

    private void processAgInventario(){

        try{

            utramo.Det_estado="En proceso";
            utramo.Det_inicio = du.getFechaActual();
            utramo.Det_idoperador = gl.IdOperador;
            utramo.IdBodega = gl.IdBodega;

            BeInvEnc.Estado = "En proceso";
            BeInvEnc.Fec_mod = du.getFechaActual();
            BeInvEnc.User_mod = gl.OperadorBodega.Operador.Nombres;

            execws(11);

        }catch (Exception e){
            mu.msgbox("processAgInventario:"+e.getMessage());
        }
    }

    private void processExisteConteo() {
        try {

            listInvDet = xobj.getresult(clsBeTrans_inv_detalleList.class, "Existe_Conteo");

            if (listInvDet != null) {
                if (listInvDet.items.size() > 0) {
                    msgExisteConteo();
                }
            } else {
                if (ditem.IdPresentacion>0){
                    execws(9);
                }else{
                    Continua_Guardando_Item();
                }
            }

        } catch (Exception e) {
            mu.msgbox("processExisteConteo: "+e.getMessage());
        }
    }

    private void processValidaCantidadVerificacion() {
        try {
            BeInvResumen = xobj.getresult(clsBeTrans_inv_resumen.class, "Get_CantidadInvVer_By_Producto");

            execws(15);



        } catch (Exception e) {
            mu.msgbox("processValidaCantidadVerificacion: "+e.getMessage());
        }
    }

    private void processExisteVerificacion() {
        try {

            listInvRes = xobj.getresult(clsBeTrans_inv_resumenList.class, "Existe_Verificacion");

            if (BeInvResumen != null) {
                CantidadConteo = Double.valueOf(txtCantInvIni.getText().toString());

                if (CantidadConteo != BeInvResumen.Cantidad) {
                    msgValidaCantidadVer();
                } else {
                    Guardar_Inventario_Conteo();
                }
            } else {
                if (listInvRes != null) {
                    if (listInvRes.items != null) {
                        if (listInvRes.items.size() > 0) {
                            msgAgregarProducto();
                        }
                    } else {
                        Guardar_Inventario_Conteo();
                    }
                } else {
                    Guardar_Inventario_Conteo();
                }
            }
        } catch (Exception e) {
            mu.msgbox("processExisteVerificacion: "+ e.getMessage());
        }
    }

    private void doExit(){
        BeProducto = new clsBeProducto();
        BeListPres = new clsBeProducto_PresentacionList();
        BeListEstado = new clsBeProducto_estadoList();
        InvTeorico = new clsBeTrans_inv_stock_prodList();
        InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();
        ditem = new clsBeTrans_inv_detalle();
        CodBarra = "";
        super.finish();
    }

    private void msgExisteConteo() {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("La ubicación ya reporta un conteo, ¿Quiere contar producto nuevamente en esta ubicación?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Contar de nuevo
                if (ditem.IdPresentacion>0){
                    execws(9);
                }else{
                    Continua_Guardando_Item();
                }

            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgValidaCantidadVer() {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("La cantidad verificada ("+ BeInvResumen.Cantidad +") no coincide con la cantidad ("+ CantidadConteo +") del conteo,  ¿La cantidad es correcta?.");
            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Procede a guardar
                Guardar_Inventario_Conteo();
            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExcedeCantidad"+e.getMessage());
        }
    }

    private void msgAgregarProducto() {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("El código de producto no coincide, ¿Agregar conteo de todas formas?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Procede a guardar
                Guardar_Inventario_Conteo();
            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgAgregarProducto"+e.getMessage());
        }
    }


    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                doExit();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
