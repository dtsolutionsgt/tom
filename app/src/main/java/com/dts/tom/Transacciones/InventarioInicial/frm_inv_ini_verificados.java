package com.dts.tom.Transacciones.InventarioInicial;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen;
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen_grid;
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen_gridList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.list_adapt_verificados;

import java.util.ArrayList;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;

public class frm_inv_ini_verificados extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private EditText txtCodProdVeri,txtCantRes, txtUbic;
    private TextView lblPrdContVeri,lblUniRes;
    private ListView ListView;
    private Button btnRegs,btnGuardarRes,btnBack;
    private Dialog dialog;
    private Spinner cmbPresRes,cmbEstadoRes;

    private clsBeTrans_inv_resumen_grid selItem = new clsBeTrans_inv_resumen_grid();
    private final ArrayList<clsBeTrans_inv_resumen_grid> BeListVerificados = new ArrayList<clsBeTrans_inv_resumen_grid>();
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto BBeProducto = new clsBeProducto();
    private clsBeTrans_inv_resumen_gridList BeList = new clsBeTrans_inv_resumen_gridList();
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();
    private clsBeProducto_estadoList BeListEstado = new clsBeProducto_estadoList();
    private clsBeTrans_inv_resumen vditem = new clsBeTrans_inv_resumen();

    private int IdPresSelect,IdEstadoSelect;

    private final ArrayList<String> EstadoList= new ArrayList<String>();
    private final ArrayList<String> PresList= new ArrayList<String>();

    private list_adapt_verificados adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_verificados);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_verificados.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtCodProdVeri = findViewById(R.id.txtCodProdVeri);
        lblPrdContVeri = findViewById(R.id.lblPrdContVeri);
        txtUbic = findViewById(R.id.txtUbic);
        ListView = findViewById(R.id.listContVeri);
        btnRegs = findViewById(R.id.btnRegs);

        setHandles();

    }

    private void setHandles(){

        try{

            ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        Object lvObj = ListView.getItemAtPosition(position);
                        clsBeTrans_inv_resumen_grid sitem = (clsBeTrans_inv_resumen_grid) lvObj;
                        selItem = new clsBeTrans_inv_resumen_grid();
                        selItem = BeListVerificados.get(position);

                        selid = sitem.Idinventariores;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        if (selItem.Idinventariores>0){
                            Llamar_Editar();
                        }

                    }

                }

            });

            txtCodProdVeri.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtCodProdVeri.getText().toString().isEmpty()) {
                            execws(1);
                        }
                    }

                    return false;
                }
            });

            txtUbic.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbic.getText().toString().isEmpty()) {
                            execws(2);

                            if (txtCodProdVeri.getText().toString().isEmpty()) {
                                lblPrdContVeri.setText("");
                            }
                        }
                    }
                    return false;
                }
            });


        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Listar_Verificados(){
        clsBeTrans_inv_resumen_grid vItem;
        BeListVerificados.clear();

        try{

            vItem = new clsBeTrans_inv_resumen_grid();

            BeListVerificados.add(vItem);

            if (BeList!=null){
                if (BeList.items!=null){

                    for (clsBeTrans_inv_resumen_grid Obj: BeList.items) {
                        vItem = new clsBeTrans_inv_resumen_grid();

                        vItem = Obj;

                        BeListVerificados.add(vItem);

                    }

                    int Count = BeListVerificados.size()-1;

                    btnRegs.setText("Regs: "+Count);

                }else{
                    btnRegs.setText("Regs: 0");
                }
            }else{
                btnRegs.setText("Regs: 0");
            }

            adapter=new list_adapt_verificados(this,BeListVerificados);
            ListView.setAdapter(adapter);

        }catch (Exception e){
            mu.msgbox("Listar_Verificados:"+e.getMessage());
        }
    }

    private void Llamar_Editar(){
        Mostrar_Pantalla(this);
    }

    private void Mostrar_Pantalla(Activity activity){

        try{

            dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.frm_inv_edita_resumen);

            cmbPresRes = dialog.findViewById(R.id.cmbPresRes);
            cmbEstadoRes = dialog.findViewById(R.id.cmbEstadoRes);

            txtCantRes = dialog.findViewById(R.id.txtCantRes);

            lblUniRes = dialog.findViewById(R.id.lblUniRes);

            btnGuardarRes = dialog.findViewById(R.id.btnGuardarRes);
            btnBack = dialog.findViewById(R.id.btnBack);

            cmbPresRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

            cmbEstadoRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

            btnGuardarRes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    Guardar_Res_Edit();
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            execws(3);

            dialog.show();

        }catch (Exception e){
            mu.msgbox("Mostrar_Pantalla:"+e.getMessage());
        }
    }

    private void Listar_Presentaciones(){

        try{

            PresList.clear();

            if (BeListPres!=null){
                if (BeListPres.items!=null){
                    for (clsBeProducto_Presentacion BePres: BeListPres.items){
                        PresList.add(BePres.Nombre);
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresRes.setAdapter(dataAdapter);

            if (PresList.size()>0) cmbPresRes.setSelection(0);

            if (vditem.Idpresentacion>0){

                List AuxPres = stream(BeListPres.items).select(c->c.IdPresentacion).toList();
                int indx=AuxPres.indexOf(vditem.Idpresentacion);

                cmbPresRes.setSelection(indx);
            }

        }catch (Exception e){
            mu.msgbox("Listar_Presentaciones:"+e.getMessage());
        }
    }

    private void Listar_Estados(){

        try{

            EstadoList.clear();

            if (BeListEstado!=null){
                if (BeListEstado.items!=null){
                    for (clsBeProducto_estado BeEstado: BeListEstado.items){
                        EstadoList.add(BeEstado.Nombre);
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoRes.setAdapter(dataAdapter);

            if (EstadoList.size()>0) cmbEstadoRes.setSelection(0);

            if (vditem.Idproductoestado>0){

                List AuxEstado = stream(BeListEstado.items).select(c->c.IdEstado).toList();
                int indx=AuxEstado.indexOf(vditem.Idproductoestado);

                cmbEstadoRes.setSelection(indx);
            }

        }catch (Exception e){
            mu.msgbox("Listar_Estados:"+e.getMessage());
        }
    }

    private void Continua_Llenando_Registros(){

        try{

            lblUniRes.setText(BBeProducto.UnidadMedida.Nombre);
            txtCantRes.setText(mu.frmdecimal(vditem.Cantidad,gl.gCantDecDespliegue));
            txtCantRes.requestFocus();
            txtCantRes.selectAll();

        }catch (Exception e){
            mu.msgbox("Continua_Llenando_Registros:"+e.getMessage());
        }
    }

    private void Guardar_Res_Edit(){

        try{

            vditem.Idpresentacion = IdPresSelect;
            vditem.Idproductoestado = IdEstadoSelect;

            if (txtCantRes.getText().toString().isEmpty()||txtCantRes.getText().equals("")||txtCantRes.getText().equals("0")){
                msValidar("La cantidad no puede ser vacía o 0");
            }

            vditem.Cantidad =Double.parseDouble(txtCantRes.getText().toString());

            execws(7);

        }catch (Exception e){
            mu.msgbox("Guardar_Res_Edit:"+e.getMessage());
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

    public void BotonExit(View view){
        txtCodProdVeri.setText("");
        super.finish();
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
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodProdVeri.getText().toString(),"IdBodega",gl.IdBodega);
                        break;
                    case 2:
                        int ubic = 0;
                        if (!txtUbic.getText().toString().isEmpty()) {
                            ubic = Integer.valueOf(txtUbic.getText().toString());
                        }
                        callMethod("Get_All_Inventario_Inicial_By_IdInventario_Enc_And_Idtramo_And_IdProducto",
                                "pIdinventarioenct",BeInvEnc.Idinventarioenc,"pIdtramo",BeInvTramo.Idtramo,"pIdProducto",BeProducto.IdProducto, "pIdUbicacion", ubic, "pIdBodega", gl.IdBodega);
                        break;
                    case 3:
                        callMethod("InventarioInicialVerGet","pIdinventariores",selItem.Idinventariores);
                        break;
                    case 4:
                        callMethod("Get_BeProducto_By_IdProducto","pIdProducto",vditem.Idproducto);
                        break;
                    case 5:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",BBeProducto.IdProducto,"pActivo",true);
                        break;
                    case 6:
                        callMethod("Get_Estados_By_IdPropietario","pIdPropietario",BBeProducto.IdPropietario);
                        break;
                    case 7:
                        callMethod("InventarioInicialVerActualizar","pItem",vditem);
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
                    processBeProducto();
                    break;
                case 2:
                    processBeList();
                    break;
                case 3:
                    processResDet();
                    break;
                case 4:
                    processBeProductoRes();
                    break;
                case 5:
                    processPresentaciones();
                    break;
                case 6:
                    processEstados();
                    break;
                case 7:
                    execws(2);
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void  processBeProducto(){

        try{

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto!=null){
                lblPrdContVeri.setText(BeProducto.Nombre);
                execws(2);
            }else{
                mu.msgbox("El producto no existe en el maestro de articulos verificados");
            }

        }catch (Exception e){
            mu.msgbox("processBeProducto:"+e.getMessage());
        }
    }

    private void processBeList(){

        try{

            BeList = xobj.getresult(clsBeTrans_inv_resumen_gridList.class,"Get_All_Inventario_Inicial_By_IdInventario_Enc_And_Idtramo_And_IdProducto");

            Listar_Verificados();

        }catch (Exception e){
            mu.msgbox("processBeList:"+e.getMessage());
        }
    }

    private void processResDet(){

        try{

            vditem = xobj.getresult(clsBeTrans_inv_resumen.class,"InventarioInicialVerGet");

            execws(4);

        }catch (Exception e){
            mu.msgbox("processResDet:"+e.getMessage());
        }
    }

    private void processBeProductoRes(){

        try{

            BBeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_IdProducto");

            execws(5);

        }catch (Exception e){
            mu.msgbox("processBeProductoRes:"+e.getMessage());
        }
    }

    private void processPresentaciones(){

        try{

            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            Listar_Presentaciones();

            execws(6);

        }catch (Exception e){
            mu.msgbox("processPresentaciones:"+e.getMessage());
        }
    }

    private void processEstados(){

        try{

            BeListEstado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario");

            Listar_Estados();

            Continua_Llenando_Registros();

        }catch (Exception e){
            mu.msgbox("processEstados:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}