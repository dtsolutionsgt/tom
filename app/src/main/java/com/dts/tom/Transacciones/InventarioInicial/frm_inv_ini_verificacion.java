package com.dts.tom.Transacciones.InventarioInicial;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalleList;
import com.dts.classes.Transacciones.Inventario.Inventario_Resumen.clsBeTrans_inv_resumen;
import com.dts.classes.Transacciones.Inventario.Productos_Sugeridos.Inv_Stock_Prod.clsBeTrans_inv_stock_prod_sug;
import com.dts.classes.Transacciones.Inventario.Productos_Sugeridos.Inv_Stock_Prod.clsBeTrans_inv_stock_prod_sugList;
import com.dts.classes.Transacciones.Inventario.clsBeTrans_inv_enc;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeUbic;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.IngUbic;

public class frm_inv_ini_verificacion extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private EditText txtUbicVer, txtUmbasVeri, txtCantVer, txtLicencia;
    private AutoCompleteTextView txtBarraVer;
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
    public clsBeTrans_inv_stock_prod_sugList listProductosSugeridos;
    private clsBeTrans_inv_detalle InvDetalle = new clsBeTrans_inv_detalle();
    private clsBeTrans_inv_detalleList listInvDet = new clsBeTrans_inv_detalleList();

    private ArrayList<String> EstadoList = new ArrayList<String>();
    private ArrayList<String> PresList = new ArrayList<String>();

    private int pIdTramo=0;
    private double CantidadVer = 0;
    private clsBeTrans_inv_stock_prod InvItem = new clsBeTrans_inv_stock_prod();
    private clsBeTrans_inv_resumen BeInvResumen = new clsBeTrans_inv_resumen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_verificacion);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_verificacion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicVer = findViewById(R.id.txtUbicVer);
        txtBarraVer = findViewById(R.id.txtBarraVer);
        txtUmbasVeri = findViewById(R.id.txtUmbasVeri);
        txtCantVer = findViewById(R.id.txtCantVer);
        txtLicencia = findViewById(R.id.txtLicencia);

        cmbPresVeri = findViewById(R.id.cmbPresVeri);
        cmbEstadoVeri = findViewById(R.id.cmbEstadoVeri);

        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblDescVer = findViewById(R.id.lblDescVer);
        lblUbicDes = findViewById(R.id.lblUbicDes);

        emptyPres = false;

        Limpia_Valores();

        ProgressDialog();

        setHandles();

        Load();

    }

    private void setHandles() {

        try {

            txtUbicVer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { }
            });

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

            txtBarraVer.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!txtBarraVer.getText().toString().isEmpty()) {
                        txtLicencia.setText(txtBarraVer.getText());
                        execws(13);
                    }
                }

                return false;
            });

            txtCantVer.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ProcesaRegistro();
                }

                return false;
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
                        //#CKFK Modifiqué la forma en que se determina el  IdPresentación del producto
                        //porque el procedimiento anterior ocasionaba error
                        if (cmbPresVeri.getSelectedItem().equals("Sin Presentación")){
                            IdPresSelect=0;
                        }else{

                            if (BeListPres!=null){
                                if(BeListPres.items!=null){

                                    if ( BeListPres.items.size()==1){

                                        IdPresSelect = BeListPres.items.get(0).IdPresentacion;

                                    }else{

                                        for( clsBeProducto_Presentacion pres : BeListPres.items){
                                            if(pres.Nombre.equals(cmbPresVeri.getSelectedItem())){
                                                IdPresSelect=pres.IdPresentacion;
                                            }
                                        }

                                    }
                                }
                            }

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

            txtLicencia.setOnKeyListener((v, keyCode, event) -> {

                if (!txtLicencia.getText().toString().isEmpty()) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        execws(13);

                    }
                }

                return false;
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

            //#CKFK20240724 Es mejor no conservar la ubicación anterior
            // porque pueden generarse inconsistencias
            /*if (BeUbic.IdUbicacion!=0){
                txtUbicVer.setText(BeUbic.IdUbicacion+"");
                lblUbicDes.setText(BeUbic.Descripcion);
            }*/

            if (!IngUbic){
                pIdTramo=BeInvTramo.Idtramo;
            }else{
                pIdTramo=BeUbic.IdTramo;
            }

            execws(1);

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }

    }

    private boolean validaUbicacion() {
        boolean esValida = false;

        try {
            if (BeUbic.Tramo.IdTramo != 0) {
                esValida = BeInvTramo.Idtramo == BeUbic.Tramo.IdTramo;
            }

        } catch (Exception e) {
            mu.msgbox("validaUbicacion: "+ e.getMessage());
        }
        return  esValida;
    }

    private void Procesa_Ubicacion() {

        try {

            if (!validaUbicacion()) {
                txtUbicVer.requestFocus();
                txtUbicVer.setSelectAllOnFocus(true);
                txtBarraVer.setText("");
                txtBarraVer.setEnabled(false);

                mu.msgbox("La ubicación no partenece al tramo: " + BeInvTramo.Nombre_Tramo);
            } else {
                txtBarraVer.setEnabled(true);
                txtBarraVer.requestFocus();
                txtBarraVer.setSelectAllOnFocus(true);
            }

            lblUbicDes.setText("" + BeUbic.Descripcion);

        } catch (Exception e) {
            mu.msgbox("Procesa_Ubicacion");
        }
    }

    private void Carga_Datos_Producto(){

        try{

            Carga_Det_Producto();

            //txtCantVer.setText("");
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

            //#CKFK 20211127 Tuvimos que poner esto en comentario porque no se cargaban las presentaciones ni los estados
            //Hacer pruebas para ver si así como quedó esto funciona para ByB
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

            if (PresList.size() > 0){
                if (PresList.get(0).contains("Sin Presentación") && PresList.size()>1){
                    cmbPresVeri.setSelection(1);
                }else{
                    cmbPresVeri.setSelection(0);
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
                EstadoList.add(BeEstado.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoVeri.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoVeri.setSelection(0);

            txtCantVer.requestFocus();

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
        progress.setMessage("Guardando verificación");
        try{
            ProcesaRegistro();
        }catch (Exception e){
        }finally {
            progress.cancel();
        }
    }

    private void ProcesaRegistro() {
        try {
            if (validaUbicacion()) {
                execws(11);
            } else {
                progress.cancel();
                mu.msgbox("La ubicación no pertenece al tramo: " + BeInvTramo.Nombre_Tramo);
            }
        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("ProcesaRegistro: "+ e.getMessage());
        }
    }

    private void Guardar_Verificacion(){

        try{
            //#CKFK20240724 Agregué validación para saber si hay verificaciones anteriores
            execws(15);

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("Guardar_Verificacion:"+e.getMessage());
        }
    }

    private void Guarda_Verificacion_Confirmada(){
        try{

            Valida_Verif();

            creaVerifItem();

            execws(6);

        }catch (Exception e){

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
            vitem.IdUbicacion = Integer.valueOf(txtUbicVer.getText().toString());

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
            txtLicencia.setText("");
            //lblTituloForma.setText("");
            txtUmbasVeri.setText("");

            BeProducto = new clsBeProducto();
            BeListPres = new clsBeProducto_PresentacionList();
            BeListEstado = new clsBeProducto_estadoList();

            vitem = new clsBeTrans_inv_resumen();

            EstadoList = new ArrayList<>();
            PresList = new ArrayList<>();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresVeri.setAdapter(dataAdapter);

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoVeri.setAdapter(dataAdapter1);

            IdPresSelect = 0;
            IdEstadoSelect = 0;

            IngUbic = false;

            if(BeInvTramo.Nombre_Tramo!=null){
                //#REFRESH TITULO FORMA
                lblTituloForma.setText("TRAMO :" + BeInvTramo.Nombre_Tramo);
                pIdTramo=BeUbic.IdTramo;
            }else{
                mu.msgbox("El tramo se ha perdido.");
            }

        }catch (Exception e){
            mu.msgbox("Error: " + e.getMessage());
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

                    case 10:
                        callMethod("Get_All_Codigos_By_IdInventario_And_IdBodega",
                                         "pIdInventario",BeInvEnc.Idinventarioenc,
                                               "pIdBodega",BeInvEnc.IdBodega);
                    case 11:
                        callMethod("Get_CantidadInvConteo_By_Producto",
                                         "pIdUbicacion",BeUbic.IdUbicacion,
                                               "pIdProducto",BeProducto.IdProducto,
                                               "pIdBodega", gl.IdBodega,
                                               "pIdPresentacion", IdPresSelect);
                        break;
                    case 12:
                        callMethod("Existe_Conteo",
                                "pIdUbicacion",BeUbic.IdUbicacion,
                                      "pIdBodega", gl.IdBodega,
                                      "pIdProducto", BeProducto.IdProducto);
                        break;
                    case 13:
                        callMethod("Get_Inventario_Teorico_By_Codigo_O_Licencia",
                                "pIdInventario", BeInvEnc.Idinventarioenc,
                                "pCodigo", txtBarraVer.getText().toString().replace("$",""),
                                "pIdBodega", gl.IdBodega);

                        /*callMethod("Get_InventarioItem_By_Licencia",
                                "pLicencia", txtLicencia.getText().toString().replace("$", ""),
                                "pIdBodega", gl.IdBodega,
                                "pUbicacion", Integer.valueOf(txtUbicVer.getText().toString()));*/
                        break;
                    case 14:
                    case 15:
                        callMethod("Get_CantidadInvVer_By_Producto",
                                "pIdUbicacion",BeUbic.IdUbicacion,
                                "pIdProducto", BeProducto.IdProducto,
                                "pIdBodega", gl.IdBodega,
                                "pIdPresentacion", IdPresSelect);
                        break;
                }

                progress.cancel();

            } catch (Exception e) {
                progress.cancel();
                error=e.getMessage();errorflag =true;msgbox(error);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                    msgbox("Verificación guardada correctamente");
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
                case 10:
                    processCodigosSugeridos();
                    break;
                case 11:
                    processValidaCantidadConteo();
                    break;
                case 12:
                    processExisteConteo();
                    break;
                case 13:
                    processInventarioLicencia();
                    break;
                case 14:
                    processValidaCantidadVerificacion();
                    break;
                case 15:
                    processVerificacionAnterior();
                    break;
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processTramosInv(){

        try{

            utramo = xobj.getresult(clsBeTrans_inv_tramo.class, "Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo");

            BeInvTramo = utramo;

            txtUbicVer.setSelectAllOnFocus(true);
            txtUbicVer.requestFocus();
            lblTituloForma.setText("TRAMO :" + BeInvTramo.Nombre_Tramo);

            //#EJC20220506: Obtener listado de códigos sugeridos del inv.
            execws(10);

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
                    txtCantVer.requestFocus();
                    return;
                }
            }else{
                mu.msgbox("Los estados no existen");
                txtCantVer.requestFocus();
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

    private final List<String> CodigosSugeridos = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processCodigosSugeridos(){

        try {

            listProductosSugeridos = xobj.getresult(clsBeTrans_inv_stock_prod_sugList.class,"Get_All_Codigos_By_IdInventario_And_IdBodega");

            if (listProductosSugeridos != null) {

                if (listProductosSugeridos.items != null) {

                    CodigosSugeridos.clear();

                    for (clsBeTrans_inv_stock_prod_sug psg: listProductosSugeridos.items){
                        CodigosSugeridos.add(psg.Codigo);
                    }

                    ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, CodigosSugeridos);
                    txtBarraVer.setAdapter(arrayAdapter);
                    txtBarraVer.setThreshold(1);
                }
            }

        }catch (Exception e){
            mu.msgbox("processInvTeorico:"+e.getMessage());
        }
    }

    private void processValidaCantidadConteo() {
        try {
            InvDetalle = xobj.getresult(clsBeTrans_inv_detalle.class, "Get_CantidadInvConteo_By_Producto");

            execws(12);

        } catch (Exception e) {
            progress.cancel();
             mu.msgbox("processValidaCantidadConteo: "+ e.getMessage());
        }
    }
    private void processExisteConteo() {
        try {
            listInvDet = xobj.getresult(clsBeTrans_inv_detalleList.class, "Existe_Conteo");

            if (InvDetalle != null) {
                //#AT20240729 Si la cantidad es 0 continua el proceso de guardar
                //se agregó esta validacion porque cuando no encuentra resultados el metodo Get_CantidadInvConteo_By_Producto
                //está devolviendo un objeto inicializado que no es igual a nulo
                if (InvDetalle.Cantidad > 0) {
                    CantidadVer = Double.valueOf(txtCantVer.getText().toString());
                    if (CantidadVer != InvDetalle.Cantidad) {
                        //#CKFK20240723 Agregué esta validación por si hay otras verificaciones
                        // del mismo producto en la misma ubicación
                        execws(14);
                        //msgValidaCantidadConteo();
                    } else {
                        Guardar_Verificacion();
                    }
                } else {
                    Guardar_Verificacion();
                }
            } else {
                if (listInvDet != null) {
                    if (listInvDet.items != null) {
                        if (listInvDet.items.size() > 0) {
                            msgAgregarProducto();
                        }
                    } else {
                        Guardar_Verificacion();
                    }
                } else {
                    Guardar_Verificacion();
                }
            }
        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("processExisteConteo: "+e.getMessage());
        }
    }

    private void processInventarioLicencia() {

        try {

            InvTeorico = xobj.getresult(clsBeTrans_inv_stock_prodList.class,"Get_Inventario_Teorico_By_Codigo_O_Licencia");

            if(InvTeorico!=null){

                BeProducto= InvTeorico.items.get(0).BeProducto;
                txtLicencia.setText(InvTeorico.items.get(0).getLicense_plate());
                txtBarraVer.setText(BeProducto.Codigo);


                PresList.clear();
                PresList.add("Sin Presentación");

                Carga_Datos_Producto();

            } else {
                mu.msgbox("No se puede agregar productos nuevos");
            }

            /*InvItem = xobj.getresult(clsBeTrans_inv_stock_prod.class, "Get_InventarioItem_By_Licencia");

            if (InvItem != null) {
                txtBarraVer.setText(InvItem.Codigo);
                //txtCantVer.setText(""+InvItem.Cant);
                execws(3);
            }*/
        } catch (Exception e) {
            mu.msgbox("processInventarioLicencia: "+ e.getMessage());
        }
    }
    private void msgValidaCantidadConteo() {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("La cantidad contada ("+ InvDetalle.Cantidad +") no coincide con la cantidad  ("+ CantidadVer +") de la verificación. ¿La cantidad es correcta?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Procede a guardar
                Guardar_Verificacion();
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
            dialog.setMessage("El código de producto no coincide, ¿Agregar verificación de todas formas?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Procede a guardar
                Guardar_Verificacion();
            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgAgregarProducto"+e.getMessage());
        }
    }

    private void msgExisteVerificacionYConteo(double pContada, double pVerificada) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("La ubicación ya reporta un conteo de " + pContada + " y una verificación de " + pVerificada + ", ¿Quiere verificar producto nuevamente en esta ubicación?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Contar de nuevo
                Guardar_Verificacion();
            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExisteVerificacionYConteo"+e.getMessage());
        }
    }

    private void msgExisteVerificacionAnterior(double pVerificada) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("La ubicación ya reporta una verificación de " + pVerificada + ", ¿Quiere verificar producto nuevamente en esta ubicación?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog12, which) -> {
                //Contar de nuevo
                //#CKFK20240724 Modifiqué el guardar para que se valide primero si hay verificaciones anteriores
                //Guardar_Verificacion();
                Guarda_Verificacion_Confirmada();
            });

            dialog.setNegativeButton("No", (dialog1, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgExisteVerificacionYConteo"+e.getMessage());
        }
    }

    private void processValidaCantidadVerificacion() {
        try {
            BeInvResumen = xobj.getresult(clsBeTrans_inv_resumen.class, "Get_CantidadInvVer_By_Producto");

            if (BeInvResumen != null) {
                if (BeInvResumen.Cantidad > 0) {

                    double vContada = InvDetalle.Cantidad;
                    double vVerificada = BeInvResumen.Cantidad;

                    msgExisteVerificacionYConteo(vContada, vVerificada);
                }
            } else {
                progress.cancel();
                msgValidaCantidadConteo();
            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("processValidaCantidadVerificacion: "+e.getMessage());
        }finally {
            progress.cancel();
        }
    }

    private void processVerificacionAnterior() {
        try {
            BeInvResumen = xobj.getresult(clsBeTrans_inv_resumen.class, "Get_CantidadInvVer_By_Producto");

            if (BeInvResumen != null) {
                if (BeInvResumen.Cantidad > 0) {

                    double vVerificada = BeInvResumen.Cantidad;

                    msgExisteVerificacionAnterior(vVerificada);
                } else {
                    //#AT20240729 Cuando devuelve 0 es un objecto inicializado desde BOF del metodo Get_CantidadInvVer_By_Producto
                    Guarda_Verificacion_Confirmada();
                }
            }else{
                Guarda_Verificacion_Confirmada();
            }

        } catch (Exception e) {
            mu.msgbox("processValidaCantidadVerificacion: "+e.getMessage());
        }
    }

    public void ProgressDialog(){
        progress=new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }
}