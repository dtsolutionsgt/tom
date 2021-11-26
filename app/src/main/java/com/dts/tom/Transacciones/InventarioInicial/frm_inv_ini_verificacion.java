package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeUbic;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.IngUbic;

public class frm_inv_ini_verificacion extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private EditText txtUbicVer, txtBarraVer, txtUmbasVeri, txtCantVer;
    private Spinner cmbPresVeri, cmbEstadoVeri;
    private TextView lblDescVer, lblTituloForma, lblUbicDes;
    private Button btnDetVeri;

    private int IdPresSelect, IdEstadoSelect;
    private boolean emptyPres;

    private clsBeTrans_inv_tramo utramo = new clsBeTrans_inv_tramo();
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();
    private clsBeProducto_estadoList BeListEstado = new clsBeProducto_estadoList();
    private clsBeTrans_inv_resumen vitem = new clsBeTrans_inv_resumen();
    private clsBeTrans_inv_stock_prodList InvTeorico = new clsBeTrans_inv_stock_prodList();

    private ArrayList<String> EstadoList = new ArrayList<String>();
    private ArrayList<String> PresList = new ArrayList<String>();

    private int pIdTramo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_verificacion);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_verificacion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicVer = (EditText)findViewById(R.id.txtUbicVer);
        txtBarraVer = (EditText)findViewById(R.id.txtBarraVer);
        txtUmbasVeri = (EditText)findViewById(R.id.txtUmbasVeri);
        txtCantVer = (EditText)findViewById(R.id.txtCantVer);

        cmbPresVeri = (Spinner)findViewById(R.id.cmbPresVeri);
        cmbEstadoVeri = (Spinner)findViewById(R.id.cmbEstadoVeri);

        lblTituloForma = (TextView)findViewById(R.id.lblTituloForma);
        lblDescVer = (TextView)findViewById(R.id.lblDescVer);
        lblUbicDes = (TextView)findViewById(R.id.lblUbicDes);

        emptyPres = false;

        setHandles();

        Load();

    }

    private void setHandles() {

        try {

            txtUbicVer.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicVer.getText().toString().isEmpty()) {
                            execws(2);
                        }
                    }

                    return false;
                }
            });

            txtBarraVer.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtBarraVer.getText().toString().isEmpty()) {
                            execws(3);
                        }
                    }

                    return false;
                }
            });

            txtCantVer.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            Guardar_Verificacion();
                    }

                    return false;
                }
            });

            cmbEstadoVeri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

            cmbPresVeri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    try {
                        //GT 18112021 si agregamos un registro vacio a la lista presentacion, la posicion 0 es la vacia
                        if (emptyPres && position ==0 ) {
                            IdPresSelect = 0;


                        }else if(emptyPres && position> 0){

                            int Registros =IdPresSelect=BeListPres.items.size();

                            if(Registros > position){
                                IdPresSelect=BeListPres.items.get(position).IdPresentacion;
                            }else{
                                IdPresSelect=BeListPres.items.get(position - 1).IdPresentacion;
                            }

                        }else{

                            int Registros =IdPresSelect=BeListPres.items.size();

                            if(Registros > position){
                                IdPresSelect=BeListPres.items.get(position).IdPresentacion;
                            }else{
                                IdPresSelect=BeListPres.items.get(position -1).IdPresentacion;
                            }

                            //IdPresSelect=BeListPres.items.get(position).IdPresentacion;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

        } catch (Exception e) {
            mu.msgbox("setHandles:" + e.getMessage());
        }
    }

    private void Load(){

        try{


            txtUbicVer.setText("");
            lblDescVer.setText("");
            lblUbicDes.setText("");

            txtCantVer.setText("");

            pIdTramo=0;

            if (BeUbic.IdUbicacion!=0){
                txtUbicVer.setText(BeUbic.IdUbicacion+"");
                lblUbicDes.setText(BeUbic.Descripcion);
            }

            if (!IngUbic){
                pIdTramo=BeInvTramo.Idtramo;
            }else{
                pIdTramo=BeUbic.IdTramo;
            }

            execws(1);

            lblTituloForma.setText("TRAMO :" + BeInvTramo.Nombre_Tramo);

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }

    }

    private void Procesa_Ubicacion() {

        try {

            if (BeUbic.Tramo.IdTramo != 0) {
                if (BeInvTramo.Idtramo != BeUbic.Tramo.IdTramo) {
                    mu.msgbox("La ubicación no partenece al tramo: " + BeUbic.Tramo.Descripcion);
                }
            }

            if (BeUbic.Nivel > 1) {

            }

            lblUbicDes.setText("" + BeUbic.Descripcion);

           /* txtBarraVer.setSelectAllOnFocus(true);
            txtBarraVer.requestFocus();
            txtBarraVer.selectAll();*/

        } catch (Exception e) {
            mu.msgbox("Procesa_Ubicacion");
        }
    }

    private void Carga_Datos_Producto(){

        try{

            Carga_Det_Producto();

            txtCantVer.setText("");

            txtUmbasVeri.setText(BeProducto.UnidadMedida.Nombre);

        }catch (Exception e){
            mu.msgbox("Carga_Datos_Producto:"+e.getMessage());
        }
    }

    private void Carga_Det_Producto() {

        try {

            lblDescVer.setText(BeProducto.Nombre);

            txtUmbasVeri.setText(BeProducto.UnidadMedida.Nombre);

            //txtUmbasVeri.setFocusable(false);
            //txtCantVer.requestFocus();

            //GT 18112021 primero validamos prod guardado sin presentacion
            //execws(9);
            execws(4);

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
            cmbPresVeri.setAdapter(dataAdapter);

            if (PresList.size() > 0) cmbPresVeri.setSelection(0);

        } catch (Exception e) {
            mu.msgbox("llenaDetPresentacionProducto:" + e.getMessage());
        }
    }

    private void Llena_Det_Estados_Producto() {

        try {

            EstadoList.clear();

            for (clsBeProducto_estado BeEstado : BeListEstado.items) {
                EstadoList.add(BeEstado.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoVeri.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoVeri.setSelection(0);

        } catch (Exception e) {
            mu.msgbox("Llena_Det_Estados_Producto:" + e.getMessage());
        }
    }


    private void processInvTeorico(){

        try {

            InvTeorico = xobj.getresult(clsBeTrans_inv_stock_prodList.class,"Get_Inventario_Teorico_By_Codigo");

            if (InvTeorico != null) {
                if (InvTeorico.items != null) {
                    Valida_presentaciones();
                }
            }

        }catch (Exception e){
            mu.msgbox("processInvTeorico:"+e.getMessage());
        }
    }


    private void Valida_presentaciones() {

        try {

            PresList.clear();

            //GT 18112021: si hay un prod sin presentacion, se carga primero vacia y luego las existentes
            for (clsBeTrans_inv_stock_prod BeLotes : InvTeorico.items) {

                if (BeLotes.IdPresentacion ==0) {
                    PresList.add("Sin Presentación");
                    emptyPres = true;
                }
            }

            execws(4);

        } catch (Exception e) {
            mu.msgbox("Llena_Lotes:" + e.getMessage());
        }
    }


    public void BotonGuardarVerificacion(View view){
        Guardar_Verificacion();
    }

    private void Guardar_Verificacion(){

        try{

            Valida_Verif();

            creaVerifItem();

            execws(6);

        }catch (Exception e){
            mu.msgbox("Guardar_Verificacion:"+e.getMessage());
        }
    }

    private void Valida_Verif(){

        try{

            if (BeInvTramo.Idtramo==0){
                mu.msgbox("Tramo incorrecto");
                return;
            }

            if (BeProducto.IdProducto==0){
                mu.msgbox("Producto incorrecto");
                return;
            }

            if (IdEstadoSelect<=0){
                mu.msgbox("Estado incorrecto");
                return;
            }

            if (txtCantVer.getText().toString().isEmpty()||txtCantVer.getText().toString().equals("")||txtCantVer.getText().toString().equals("0")){
                mu.msgbox("Cantidad no puede ser vacía o 0");
                return;
            }

        }catch (Exception e){
            mu.msgbox("Valida_Verif:"+e.getMessage()    );
        }
    }

    private void creaVerifItem(){

        try{

            vitem = new clsBeTrans_inv_resumen();
            vitem.Idinventariores = 0;
            vitem.Idinventarioenct = BeInvEnc.Idinventarioenc;
            vitem.Idtramo = BeUbic.IdTramo;
            vitem.Idproducto = BeProducto.IdProducto;
            vitem.Idoperador = gl.OperadorBodega.IdOperador;
            vitem.IdUnidadMedida = BeProducto.IdUnidadMedidaBasica;
            vitem.Idpresentacion = IdPresSelect;
            vitem.Idproductoestado = IdEstadoSelect;
            vitem.Cantidad = Double.parseDouble(txtCantVer.getText().toString());
            vitem.Fecha_captura = du.getFechaActual();
            vitem.Host = "1";
            vitem.Nom_producto = BeProducto.Nombre;
            vitem.Nom_operador = gl.OperadorBodega.Operador.Nombres;



        }catch (Exception e){
            mu.msgbox("creaVerifItem:"+e.getMessage());
        }
    }

    private void Limpia_Valores(){

        try{

            txtUbicVer.setText("");
            lblDescVer.setText("");
            txtBarraVer.setText("");
            lblUbicDes.setText("");
            txtCantVer.setText("");
            lblTituloForma.setText("");
            txtUmbasVeri.setText("");

            BeProducto = new clsBeProducto();
            BeListPres = new clsBeProducto_PresentacionList();
            BeListEstado = new clsBeProducto_estadoList();

            vitem = new clsBeTrans_inv_resumen();

            EstadoList = new ArrayList<String>();
            PresList = new ArrayList<String>();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresVeri.setAdapter(dataAdapter);

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoVeri.setAdapter(dataAdapter1);

            IdPresSelect = 0;
            IdEstadoSelect = 0;

            IngUbic = false;

        }catch (Exception e){
            mu.msgbox("");
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
        browse=1;
        startActivity(new Intent(this, frm_inv_ini_verificados.class));
    }

    public void BotonExit(View view){
        Limpia_Valores();
        super.finish();
    }

    private void Completar_tramo(){

        try{

            //utramo.Res_inicio = "Finalizado";
            utramo.Res_estado="Finalizado";
            utramo.Res_fin = du.getFechaActual();
            utramo.Res_idoperador = gl.OperadorBodega.IdOperador;
            utramo.IdBodega = gl.IdBodega;

            execws(8);

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

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo","pidinventario",BeInvEnc.Idinventarioenc,
                                "pidtramo",pIdTramo);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega", "pBarra", txtUbicVer.getText().toString(),
                                "pIdBodega", gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_BeProducto_By_Codigo_For_HH", "pCodigo", txtBarraVer.getText().toString(),
                                "IdBodega", gl.IdBodega);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto", "pIdProducto", BeProducto.IdProducto, "pActivo", true);
                        break;
                    case 5:
                        callMethod("Get_Estados_By_IdPropietario", "pIdPropietario", BeProducto.IdPropietario);
                        break;
                    case 6:
                        callMethod("InventarioInicialVerAgrega", "pItem", vitem);
                        break;
                    case 7:
                        callMethod("Actualizar_Inventario_Inicial_By_BeTransInvTramo","pTramo",utramo);
                        break;
                    case 8:
                        callMethod("Actualizar_Inventario_Inicial_By_BeTransInvTramo","pTramo",utramo);

                    case 9:
                        callMethod("Get_Inventario_Teorico_By_Codigo","IdInventarioEnc",BeInvEnc.Idinventarioenc,
                                "IdProducto",BeProducto.IdProducto);
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
                   processTramosInv();
                    break;
                case 2:
                    processUbic();
                    break;
                case 3:
                    processBeProducto();
                    break;
                case 4:
                    processPresentacion();
                    break;
                case 5:
                    processEstados();
                    break;
                case 6:
                    processInventarioVerificacion();
                    break;
                case 7:
                    Limpia_Valores();
                    txtUbicVer.requestFocus();
                    txtUbicVer.selectAll();
                    break;
                case 8:
                    Limpia_Valores();
                    super.finish();
                case 9:
                    processInvTeorico();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processTramosInv(){

        try{

            utramo = xobj.getresult(clsBeTrans_inv_tramo.class, "Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo");

            BeInvTramo = utramo;

            txtUbicVer.setSelectAllOnFocus(true);
            txtUbicVer.requestFocus();

        }catch (Exception e){
            mu.msgbox("processTramosInv:"+e.getMessage());
        }
    }

    private void processUbic(){

        try{

            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class, "Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                Procesa_Ubicacion();
            } else {
                mu.msgbox("La ubicación no existe");
            }


        }catch (Exception e){
            mu.msgbox("processUbic:"+e.getMessage());
        }
    }

    private void processBeProducto(){

        try{

            BeProducto = xobj.getresult(clsBeProducto.class, "Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null) {

                PresList.clear();
                PresList.add("Sin Presentación");

                Carga_Datos_Producto();

            } else {

                    mu.msgbox("No se puede agregar productos nuevos");
            }

        }catch (Exception e){
            mu.msgbox("processBeProducto:"+e.getMessage());
        }
    }

    private void processPresentacion(){

        try{

            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (BeListPres!=null){
                if (BeListPres.items!=null){
                    Llena_Det_Presentacion_Producto();
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

    private void  processInventarioVerificacion(){

        try{

            utramo.Res_estado = "En proceso";
            utramo.Res_inicio = du.getFechaActual();
            utramo.IdBodega = gl.IdBodega;

            execws(7);

        }catch (Exception e){
            mu.msgbox(" processInventarioVerificacion():"+e.getMessage());
        }
    }

    public void BotonSalir(View view){
        Limpia_Valores();
        super.finish();
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
