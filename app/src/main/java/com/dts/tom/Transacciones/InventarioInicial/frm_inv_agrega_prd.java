package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_clasificacion.clsBeProducto_clasificacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_familia.clsBeProducto_familia;
import com.dts.classes.Mantenimientos.Producto.Producto_marca.clsBeProducto_marca;
import com.dts.classes.Mantenimientos.Producto.Producto_tipo.clsBeProducto_tipo;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_conteo.CodBarra;

public class frm_inv_agrega_prd extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private EditText txtCodPrdNuevo,txtDesPrd;
    private Spinner cmbFamilia,cmbClasificacion,cmbMarca,cmbTipo,cmbUmbas;
    private CheckBox chkCtrlVence,chkCtrlLote;
    private Button btnGuardarPrd;

    private Cursor ctableFamilia,ctableClasi,ctableMarca,ctableTipo,ctableUMB;

    private ArrayList<String> FamiliaList = new ArrayList<String>();
    private ArrayList<String> ClasiList = new ArrayList<String>();
    private ArrayList<String> MarcaList = new ArrayList<String>();
    private ArrayList<String> TipoList = new ArrayList<String>();
    private ArrayList<String> UMBasList = new ArrayList<String>();

    private ArrayList<clsBeProducto_familia> BeListFam = new ArrayList<clsBeProducto_familia>();
    private ArrayList<clsBeProducto_clasificacion> BeListClasi = new ArrayList<clsBeProducto_clasificacion>();
    private ArrayList<clsBeProducto_marca> BeListMarca = new ArrayList<clsBeProducto_marca>();
    private ArrayList<clsBeProducto_tipo> BeListTipo = new ArrayList<clsBeProducto_tipo>();
    private ArrayList<clsBeUnidad_medida> BeListUmBas = new ArrayList<clsBeUnidad_medida>();
    public static clsBeProducto pBeProductoNuevo = new clsBeProducto();

    private int IdFamilia=0;
    private int IdClasificacion=0;
    private int IdMarca=0;
    private int IdTipo=0;
    private int IdUmBas=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_agrega_prd);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_agrega_prd.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtCodPrdNuevo = (EditText)findViewById(R.id.txtCodPrdNuevo);
        txtDesPrd = (EditText)findViewById(R.id.txtDesPrd);

        cmbFamilia = (Spinner)findViewById(R.id.cmbFamilia);
        cmbClasificacion = (Spinner)findViewById(R.id.cmbClasificacion);
        cmbMarca = (Spinner)findViewById(R.id.cmbMarca);
        cmbTipo = (Spinner)findViewById(R.id.cmbTipo);
        cmbUmbas = (Spinner)findViewById(R.id.cmbUmbas);

        chkCtrlVence = (CheckBox)findViewById(R.id.chkCtrlVence);
        chkCtrlLote = (CheckBox)findViewById(R.id.chkCtrlLote);

        btnGuardarPrd = (Button)findViewById(R.id.btnGuardarPrd);

        setHandles();

        Load();

    }

    private void setHandles(){

        try {

            cmbFamilia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdFamilia=BeListFam.get(position).IdFamilia;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbClasificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    IdClasificacion=BeListClasi.get(position).IdClasificacion;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    IdMarca=BeListMarca.get(position).IdMarca;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    //ctableTipo.move(position);
                    IdTipo=BeListTipo.get(position).IdTipoProducto;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            cmbUmbas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    //ctableUMB.move(position);
                    IdUmBas=BeListUmBas.get(position).IdUnidadMedida;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Load(){

        try{

            txtCodPrdNuevo.setText(CodBarra);
            execws(1);

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Llena_Combo_Familia(){

        clsBeProducto_familia vItem;

        try{

            FamiliaList.clear();
            BeListFam.clear();

            ctableFamilia.moveToFirst();

            while (!ctableFamilia.isAfterLast()) {

                vItem = new clsBeProducto_familia();
                vItem.IdFamilia = ctableFamilia.getInt(0);
                vItem.Nombre = ctableFamilia.getString(1);

                BeListFam.add(vItem);
                FamiliaList.add(ctableFamilia.getString(1));

                ctableFamilia.moveToNext();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FamiliaList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbFamilia.setAdapter(dataAdapter);

            if (FamiliaList.size() > 0) cmbFamilia.setSelection(0);

        }catch (Exception e){
            mu.msgbox("Llena_Combo_Familia:"+e.getMessage());
        }
    }

    private void Llena_Combo_Clasificacion(){

        clsBeProducto_clasificacion vItem;

        try{

            ClasiList.clear();
            BeListClasi.clear();

            ctableClasi.moveToFirst();

            while (!ctableClasi.isAfterLast()) {

                vItem = new clsBeProducto_clasificacion();
                vItem.IdClasificacion = ctableClasi.getInt(0);
                vItem.Nombre = ctableClasi.getString(1);

                BeListClasi.add(vItem);
                ClasiList.add(ctableClasi.getString(1));

                ctableClasi.moveToNext();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ClasiList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbClasificacion.setAdapter(dataAdapter);

            if (ClasiList.size() > 0) cmbClasificacion.setSelection(0);

        }catch (Exception e){
            mu.msgbox("Llena_Combo_Clasificacion:"+e.getMessage());
        }
    }

    private void Llena_Combo_Marca(){
        clsBeProducto_marca vItem;

        try{

            MarcaList.clear();
            BeListMarca.clear();

            ctableMarca.moveToFirst();

            while (!ctableMarca.isAfterLast()) {

                vItem = new clsBeProducto_marca();

                vItem.IdMarca = ctableMarca.getInt(0);
                vItem.Nombre = ctableMarca.getString(1);

                BeListMarca.add(vItem);
                MarcaList.add(ctableMarca.getString(1));

                ctableMarca.moveToNext();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MarcaList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbMarca.setAdapter(dataAdapter);

            if (MarcaList.size() > 0) cmbMarca.setSelection(0);

        }catch (Exception e){
            mu.msgbox("Llena_Combo_Marca:"+e.getMessage());
        }
    }

    private void Llena_Combo_Tipo(){
        clsBeProducto_tipo vItem;

        try{

            TipoList.clear();
            BeListTipo.clear();

            ctableTipo.moveToFirst();

            while (!ctableTipo.isAfterLast()) {

                vItem = new clsBeProducto_tipo();

                vItem.IdTipoProducto = ctableTipo.getInt(0);
                vItem.Nombre = ctableTipo.getString(1);

                BeListTipo.add(vItem);
                TipoList.add(ctableTipo.getString(1));

                ctableTipo.moveToNext();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TipoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbTipo.setAdapter(dataAdapter);

            if (TipoList.size() > 0) cmbTipo.setSelection(0);

        }catch (Exception e){
            mu.msgbox("Llena_Combo_Tipo:"+e.getMessage());
        }
    }

    private void Llena_Combo_UMBas(){
        clsBeUnidad_medida vItem;

        try{

            UMBasList.clear();
            BeListUmBas.clear();

            ctableUMB.moveToFirst();

            while (!ctableUMB.isAfterLast()) {

                vItem = new clsBeUnidad_medida();

                vItem.IdUnidadMedida = ctableUMB.getInt(0);
                vItem.Nombre = ctableUMB.getString(1);

                BeListUmBas.add(vItem);
                UMBasList.add(ctableUMB.getString(1));

                ctableUMB.moveToNext();
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UMBasList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbUmbas.setAdapter(dataAdapter);

            if (UMBasList.size() > 0) cmbUmbas.setSelection(0);

        }catch (Exception e){
            mu.msgbox("Llena_Combo_UMBas:"+e.getMessage());
        }
    }

    private void Guardar_Producto_Nuevo(){

        try{

            pBeProductoNuevo = new clsBeProducto();

            pBeProductoNuevo.IdPropietario = BeInvEnc.Idpropietario;
            pBeProductoNuevo.IdClasificacion = IdClasificacion;
            pBeProductoNuevo.IdFamilia = IdFamilia;
            pBeProductoNuevo.IdMarca = IdMarca;
            pBeProductoNuevo.IdTipoProducto = IdTipo;
            pBeProductoNuevo.IdUnidadMedidaBasica = IdUmBas;
            pBeProductoNuevo.Codigo = txtCodPrdNuevo.getText().toString();
            pBeProductoNuevo.Nombre = txtDesPrd.getText().toString();
            pBeProductoNuevo.Codigo_barra = txtCodPrdNuevo.getText().toString();
            pBeProductoNuevo.Activo = true;
            pBeProductoNuevo.User_agr = BeInvEnc.User_agr;
            pBeProductoNuevo.Fec_agr = du.getFechaActual();
            pBeProductoNuevo.User_mod = BeInvEnc.User_agr;
            pBeProductoNuevo.Fec_mod = du.getFechaActual();
            if (chkCtrlLote.isChecked()){
                pBeProductoNuevo.Control_lote =true;
            }else{
                pBeProductoNuevo.Control_lote =false;
            }

            if (chkCtrlVence.isChecked()){
                pBeProductoNuevo.Control_vencimiento =true;
            }else{
                pBeProductoNuevo.Control_vencimiento =false;
            }

            browse=1;
            startActivity(new Intent(this, frm_inv_nuevo_reg.class));


        }catch (Exception e){
            mu.msgbox("Guardar_Producto_Nuevo:"+e.getMessage());
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
                        callMethod("Get_Familia_Inv","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                    case 2:
                        callMethod("Get_Clasificacion_Inv","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                    case 3:
                        callMethod("Get_Marca_Inv","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                    case 4:
                        callMethod("Get_Tipo_Inv","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                    case 5:
                        callMethod("Get_UMbas_Inv","pIdPropietario",BeInvEnc.Idpropietario);
                        break;
                }

                progress.cancel();

            } catch (Exception e) {
                progress.cancel();
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
                    processFamilia();
                    break;
                case 2:
                    processClasificacion();
                    break;
                case 3:
                    processMarca();
                    break;
                case 4:
                    processTipo();
                    break;
                case 5:
                    processUMbas();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processFamilia(){

        try{

            ctableFamilia = xobj.filldt();

            if (ctableFamilia.getCount()>0) {
                Llena_Combo_Familia();
            }

           execws(2);

        }catch (Exception e){
            mu.msgbox("processFamilia:"+e.getMessage());
        }
    }

    private void processClasificacion(){

        try{

            ctableClasi = xobj.filldt();

            if (ctableClasi.getCount()>0) {
                Llena_Combo_Clasificacion();
            }

            execws(3);

        }catch (Exception e){
            mu.msgbox("processClasificacion:"+e.getMessage());
        }
    }

    private void processMarca(){

        try {

            ctableMarca = xobj.filldt();

            if (ctableMarca.getCount()>0) {
                Llena_Combo_Marca();
            }

            execws(4);

        }catch (Exception e){
            mu.msgbox("processMarca:"+e.getMessage());
        }
    }

    private void processTipo(){

        try{

            ctableTipo = xobj.filldt();

            if (ctableTipo.getCount()>0) {
                Llena_Combo_Tipo();
            }

            execws(5);

        }catch (Exception e){
            mu.msgbox("processTipo:"+e.getMessage());
        }
    }

    private void processUMbas(){

        try{

            ctableUMB = xobj.filldt();

            if (ctableUMB.getCount()>0) {
                Llena_Combo_UMBas();
            }


        }catch (Exception e){
            mu.msgbox("processUMbas:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void doExit(){
        super.finish();
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

}
