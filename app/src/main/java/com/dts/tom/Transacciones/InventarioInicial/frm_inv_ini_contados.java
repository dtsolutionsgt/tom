package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
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
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle_grid;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle_gridList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_contados;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;

public class frm_inv_ini_contados extends PBase {

    private EditText txtCodProd,txtUbicConts,txtLoteDetalle,txtVenceDetalle,txtCantDetalle,txtPesoDetalle;
    private TextView lblPrdCont,lblPresDetalle,lblLoteDetalle,lblVenceDetalle,lblUniDetalle,lblPesoDetalle;
    private ListView listView;
    private Button btnRegs,btnGuardarDetalle,btnBack;
    private Spinner cmbPresDetalle,cmbEstadoDetalle;
    private DatePicker dpResult;
    private ImageView imgDate;

    private Dialog dialog;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeTrans_inv_detalle_gridList BeListDet = new clsBeTrans_inv_detalle_gridList();
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto BBeProducto = new clsBeProducto();
    private clsBeBodega_ubicacion BeUbica = new  clsBeBodega_ubicacion();
    private ArrayList<clsBeTrans_inv_detalle_grid> BeListContados = new ArrayList<clsBeTrans_inv_detalle_grid>();
    private list_adapt_contados adapter;
    private clsBeTrans_inv_detalle_grid selItem;
    private clsBeTrans_inv_detalle dditem = new clsBeTrans_inv_detalle();
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();
    private clsBeProducto_estadoList BeListEstado = new clsBeProducto_estadoList();

    private ArrayList<String> EstadoList= new ArrayList<String>();
    private ArrayList<String> PresList= new ArrayList<String>();

    // date
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    private int IdEstadoSelect=0;
    private int IdPresSelect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_contados);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_contados.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView)findViewById(R.id.listConts);

        txtCodProd = (EditText)findViewById(R.id.txtCodProd);
        txtUbicConts = (EditText)findViewById(R.id.txtUbicConts);

        lblPrdCont = (TextView)findViewById(R.id.lblPrdCont);

        btnRegs = (Button)findViewById(R.id.btnRegs);

        setHandles();

    }

    private void setHandles(){

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_inv_detalle_grid sitem = (clsBeTrans_inv_detalle_grid) lvObj;
                        selItem = new clsBeTrans_inv_detalle_grid();
                        selItem = BeListContados.get(position);

                        selid = sitem.Idinventariodet;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        if (selItem.Idinventariodet>0){
                            Llamar_Editar();
                        }

                    }

                }

            });

            txtCodProd.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtCodProd.getText().toString().isEmpty()) {
                            execws(1);
                        }
                    }

                    return false;
                }
            });

            txtUbicConts.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicConts.getText().toString().isEmpty()) {
                            execws(2);
                        }
                    }

                    return false;
                }
            });


        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Llamar_Editar(){
        Mostrar_Pantalla(this);
    }

    private void Mostrar_Pantalla(Activity activity){

        try{

            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frm_inv_edita_detalle);

            lblPresDetalle = (TextView)dialog.findViewById(R.id.lblPresDetalle);
            lblLoteDetalle = (TextView)dialog.findViewById(R.id.lblLoteDetalle);
            lblVenceDetalle= (TextView)dialog.findViewById(R.id.lblVenceDetalle);
            lblUniDetalle = (TextView)dialog.findViewById(R.id.lblUniDetalle);
            lblPesoDetalle = (TextView)dialog.findViewById(R.id.textView106);

            cmbPresDetalle = (Spinner)dialog.findViewById(R.id.cmbPresDetalle);
            cmbEstadoDetalle = (Spinner)dialog.findViewById(R.id.cmbEstadoDetalle);

            txtLoteDetalle = (EditText)dialog.findViewById(R.id.txtLoteDetalle);
            txtVenceDetalle = (EditText)dialog.findViewById(R.id.txtVenceDetalle);
            txtCantDetalle = (EditText)dialog.findViewById(R.id.txtCantDetalle);
            txtPesoDetalle = (EditText)dialog.findViewById(R.id.txtPesoDetalle);

            imgDate = (ImageView)dialog.findViewById(R.id.imgDate4);
            dpResult = (DatePicker)dialog.findViewById(R.id.datePicker5);

            btnGuardarDetalle = (Button)dialog.findViewById(R.id.btnGuardarDetalle);
            btnBack = (Button)dialog.findViewById(R.id.btnBack);

            txtCantDetalle.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            txtPesoDetalle.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            cmbPresDetalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdPresSelect=BeListPres.items.get(position).IdPresentacion;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }


            });

            cmbEstadoDetalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoSelect=BeListEstado.items.get(position).IdEstado;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }


            });

            btnGuardarDetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    Guardar_Detalle_Edit();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            imgDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeDate();
                }
            });

            setCurrentDateOnView();

            execws(4);

            dialog.show();

        }catch (Exception e){
            mu.msgbox("Procesar_registro:"+e.getMessage());
        }
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        txtVenceDetalle.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            // set selected date into textview
            txtVenceDetalle.setText(new StringBuilder().append(day)
                    .append("-").append(month).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

    public void ChangeDate(){
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

    private void Lista_Conteos(){
        clsBeTrans_inv_detalle_grid vItem;
        BeListContados.clear();

        try{

            vItem = new clsBeTrans_inv_detalle_grid();

            BeListContados.add(vItem);

            if (BeListDet!=null){
                if (BeListDet.items!=null){

                    for (clsBeTrans_inv_detalle_grid Obj: BeListDet.items){

                        vItem = new clsBeTrans_inv_detalle_grid();

                        vItem = Obj;

                        BeListContados.add(vItem);

                    }

                    int Count = BeListContados.size()-1;

                    btnRegs.setText("Regs: "+Count);

                }else{
                    btnRegs.setText("Regs: 0");
                }
            }else{
                btnRegs.setText("Regs: 0");
            }

            adapter=new list_adapt_contados(this,BeListContados);
            listView.setAdapter(adapter);

        }catch (Exception e){
            mu.msgbox("Lista_Conteos:"+e.getMessage());
        }
    }

    private void Listar_Presentaciones(){

        try{

            PresList.clear();

            for (clsBeProducto_Presentacion BePres: BeListPres.items){
                PresList.add(BePres.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresDetalle.setAdapter(dataAdapter);

            if (PresList.size()>0) cmbPresDetalle.setSelection(0);

            if (dditem.IdPresentacion>0){

                List AuxPres = stream(BeListPres.items).select(c->c.IdPresentacion).toList();
                int indx=AuxPres.indexOf(dditem.IdPresentacion);

                cmbPresDetalle.setSelection(indx);
            }

        }catch (Exception e){
            mu.msgbox("Listar_Presentaciones:"+e.getMessage());
        }
    }

    private void Listar_Estados(){

        try{

            EstadoList.clear();

            for (clsBeProducto_estado BeEstado: BeListEstado.items){
                EstadoList.add(BeEstado.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDetalle.setAdapter(dataAdapter);

            if (EstadoList.size()>0) cmbEstadoDetalle.setSelection(0);

            if (!dditem.Idproductoestado.isEmpty()){

                List AuxEstado = stream(BeListEstado.items).select(c->c.IdEstado).toList();
                int indx=AuxEstado.indexOf(dditem.Idproductoestado);

                cmbEstadoDetalle.setSelection(indx);
            }

        }catch (Exception e){
            mu.msgbox("Listar_Estados:"+e.getMessage());
        }
    }

    private void Carga_Datos_Faltantes(){

        try{

            lblUniDetalle.setText(BBeProducto.UnidadMedida.Nombre);
            txtCantDetalle.setText(mu.frmdecimal(dditem.Cantidad,gl.gCantDecDespliegue));

            txtCantDetalle.requestFocus();
            txtCantDetalle.selectAll();

            if (BBeProducto.Control_peso){
                lblPesoDetalle.setVisibility(View.VISIBLE);
                txtPesoDetalle.setVisibility(View.VISIBLE);
                txtPesoDetalle.setText(mu.frmdecimal(dditem.Peso,gl.gCantDecDespliegue));
            }else{
                lblPesoDetalle.setVisibility(View.GONE);
                txtPesoDetalle.setVisibility(View.GONE);
                txtPesoDetalle.setText("0");
            }

            if (BBeProducto.Control_lote){
                txtLoteDetalle.setVisibility(View.VISIBLE);
                lblLoteDetalle.setVisibility(View.VISIBLE);
                txtLoteDetalle.setText(dditem.Lote);
            }else {
                txtLoteDetalle.setVisibility(View.GONE);
                lblLoteDetalle.setVisibility(View.GONE);
                txtLoteDetalle.setText("");
            }

            if (BBeProducto.Control_vencimiento){
                txtVenceDetalle.setVisibility(View.VISIBLE);
                lblVenceDetalle.setVisibility(View.VISIBLE);
                txtVenceDetalle.setText(du.convierteFechaMostar(dditem.Fecha_vence));
            }else{
                txtVenceDetalle.setVisibility(View.GONE);
                lblVenceDetalle.setVisibility(View.VISIBLE);
                txtVenceDetalle.setText("");
            }

        }catch (Exception e){
            mu.msgbox("Carga_Datos_Faltantes:"+e.getMessage());
        }
    }

    private void Guardar_Detalle_Edit(){

        try{

            dditem.IdPresentacion = IdPresSelect;
            dditem.Idproductoestado = IdEstadoSelect+"";

            if (txtCantDetalle.getText().toString().isEmpty()||txtCantDetalle.getText().equals("")||txtCantDetalle.getText().equals("0")){
                msValidar("La cantidad no puede ser vacía o 0");
            }

            dditem.Cantidad =Double.parseDouble(txtCantDetalle.getText().toString());

            if (BBeProducto.Control_peso){
                if (txtPesoDetalle.getText().toString().isEmpty()||txtPesoDetalle.getText().equals("")||txtPesoDetalle.getText().equals("0")){
                    msValidar("El peso no puede ser vacía o 0");
                }
            }

            dditem.Peso =Double.parseDouble(txtPesoDetalle.getText().toString());

            if (BBeProducto.Control_lote){
                dditem.Lote = txtLoteDetalle.getText().toString();
            }else{
                dditem.Lote = "";
            }

            if (BBeProducto.Control_vencimiento){
                dditem.Fecha_vence = du.convierteFecha(txtVenceDetalle.getText().toString());
            }else{
                dditem.Fecha_vence = "1900-01-01T00:00:01";
            }

            execws(8);

        }catch (Exception e){
            mu.msgbox("Guardar_Detalle_Edit:"+e.getMessage());
        }
    }

    private void msValidar(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                   return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgValidaProductoPallet"+e.getMessage());
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
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodProd.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicConts.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_Lista_Conteo_Inventario_Inicial_By_IdInventarioEnc",
                                "pIdInventarioEnc", BeInvEnc.Idinventarioenc, "pIdtramo", BeInvTramo.Idtramo,
                                "pIdProducto",BeProducto.IdProducto,"pIdUbic",BeUbica.IdUbicacion);
                        break;
                    case 4:
                        callMethod("InventarioInicialDetGet","pIdinventariodet",selItem.Idinventariodet);
                        break;
                    case 5:
                        callMethod("Get_BeProducto_By_IdProducto","pIdProducto",dditem.Idproducto);
                        break;
                    case 6:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",BBeProducto.IdProducto,"pActivo",true);
                        break;
                    case 7:
                        callMethod("Get_Estados_By_IdPropietario","pIdPropietario",BBeProducto.IdPropietario);
                        break;
                    case 8:
                        callMethod("InventarioInicialDetActualizar",dditem);
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
                    processProducto();
                    break;
                case 2:
                    processUbicacion();
                    break;
                case 3:
                    processListConteos();
                    break;
                case 4:
                    processInventarioDet();
                    break;
                case 5:
                    processBeProductoDet();
                    break;
                case 6:
                    processPresentacion();
                    break;
                case 7:
                    processEstadoProd();
                    break;
                case 8:
                    execws(3);
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processProducto(){

        try{

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto!=null){
                lblPrdCont.setText(BeProducto.Nombre);
            }

            if (!txtUbicConts.getText().toString().isEmpty()){
                execws(2);
            }

        }catch (Exception e){
            mu.msgbox("processProducto:"+e.getMessage());
        }
    }

    private void processUbicacion(){

        try{

            BeUbica = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeProducto.IdProducto>0 && BeUbica.IdUbicacion>0){
                execws(3);
            }

        }catch (Exception e){
            mu.msgbox("processUbicacion:"+e.getMessage());
        }
    }

    private void processListConteos(){

        try{

            BeListDet = xobj.getresult(clsBeTrans_inv_detalle_gridList.class,"Get_Lista_Conteo_Inventario_Inicial_By_IdInventarioEnc");

            Lista_Conteos();

        }catch (Exception e){
            mu.msgbox("processListConteos:"+e.getMessage());
        }
    }

    private void processInventarioDet(){

        try{

            dditem = new clsBeTrans_inv_detalle();

            dditem = xobj.getresult(clsBeTrans_inv_detalle.class,"InventarioInicialDetGet");

            execws(5);

        }catch (Exception e){
            mu.msgbox("processInventarioDet:"+e.getMessage());
        }
    }

    private void processBeProductoDet(){

        try{

            BBeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_IdProducto");

            if (BBeProducto.IdProducto>0){

                execws(6);

            }

        }catch (Exception e){
            mu.msgbox("processBeProductoDet:"+e.getMessage());
        }
    }

    private void processPresentacion(){

        try{

            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (BeListPres!=null){
                if (BeListPres.items!=null){
                    Listar_Presentaciones();
                    execws(7);
                }else{
                    execws(7);
                }
            }else{
                execws(7);
            }

        }catch (Exception e){
            mu.msgbox("processPresentacion:"+e.getMessage());
        }
    }

    private void processEstadoProd(){

        try{

            BeListEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario");

            if (BeListEstado!=null){
                if (BeListEstado.items!=null){
                    Listar_Estados();
                    Carga_Datos_Faltantes();
                }else{
                    Carga_Datos_Faltantes();
                }
            }else{
                Carga_Datos_Faltantes();
            }

        }catch (Exception e){
            mu.msgbox("processEstadoProd:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
