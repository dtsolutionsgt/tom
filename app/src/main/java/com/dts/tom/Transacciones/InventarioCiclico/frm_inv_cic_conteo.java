package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.MaskFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.base.clsClasses;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod.clsBeTrans_inv_stock_prodList;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBeTrans_inv_enc_reconteo;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBeTrans_inv_enc_reconteoList;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_dataList;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramoList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.ladapt.InventarioCiclico.list_adapt_consulta_ciclico;
import com.dts.tom.PBase;
import com.dts.tom.R;
import org.simpleframework.xml.Element;
import java.util.ArrayList;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

import com.dts.base.DateUtils;
import com.dts.tom.frm_lista_tareas_principal;


public class frm_inv_cic_conteo extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;
    private list_adapt_consulta_ciclico adapter_ciclico;

    private EditText txtBuscFiltro;
    private ListView listCiclico;
    private ProgressDialog progress;
    private Integer idreconteo,tareapos, idtarea, idubic, idprod, nidubic, nidprod, vIdProductoBodega;
    private boolean esconteo, lic_plate, noubicflag, nostockflag, ProductosMultiples;
    private String lplate, LoteSel,FechaVencSel,PresSel,ProdSel;
    private Integer ubicid, nubicid,IdUbicacionSel;
    private TextView cmdList;
    CheckBox checkbox;
    boolean chkPendientes;
    private Integer Idx;
    private Cursor DT;

    Existe_producto existeProducto = new Existe_producto();
    private clsBe_inv_reconteo_data data_rec = new clsBe_inv_reconteo_data();
    private ArrayList<clsBe_inv_reconteo_data> data_list = new ArrayList<clsBe_inv_reconteo_data>();

    private clsBeProducto prod = new clsBeProducto();
    private clsBeProducto nprod = new clsBeProducto();
    private clsBeProducto npprod = new clsBeProducto();
    private clsBeProducto pBeProductoNuevo = new clsBeProducto();

    private clsBeProducto tProd = new clsBeProducto();
    private clsBeTrans_inv_tramoList Listtramos = new clsBeTrans_inv_tramoList();
    private clsBeTrans_inv_enc_reconteo reconteo = new clsBeTrans_inv_enc_reconteo();
    private clsBeTrans_inv_enc_reconteoList reconteos = new clsBeTrans_inv_enc_reconteoList();

    private clsBeTrans_inv_stock_prodList InvTeorico = new clsBeTrans_inv_stock_prodList();
    private clsBeTrans_inv_stock_prodList InvTeoricoPorProducto = new clsBeTrans_inv_stock_prodList();

    private Object item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_conteo);

        super.InitBase();

        txtBuscFiltro = findViewById(R.id.txtBuscFiltro);
        listCiclico = findViewById(R.id.listCiclico);
        checkbox = findViewById(R.id.chkPendientes);
        cmdList = findViewById(R.id.cmdList);
        idreconteo =0;
        tareapos= 0;
        esconteo = true;
        ProductosMultiples = false;
        LoteSel="";
        FechaVencSel = "19000101";
        PresSel="";
        IdUbicacionSel=0;
        ProdSel="";
        Idx = 0;
        chkPendientes = true;
        checkbox.setChecked(true);
        checkbox.setText("Pendientes");

        ws = new WebServiceHandler(frm_inv_cic_conteo.this,gl.wsurl);
        xobj = new XMLObject(ws);


        gl.pprod.IdProducto = 0;
        gl.pprod.Control_lote= false;
        gl.pprod.Control_vencimiento = false;

        ProgressDialog("Cargando conteo ciclico.");

        setHandles();

       execws(1);

    }

    private void setHandles() {

        try{

            txtBuscFiltro.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                if (txtBuscFiltro.getText().toString().isEmpty())
                                {
                                    toast("No ingreso una Ubicación!");

                                }else{

                                    if(chkPendientes){

                                        BuscarFiltro();

                                    }else {

                                        BuscarFiltro2();

                                    }

                                }
                        }
                    }
                    return false;
                }
            });

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    tareapos = 0;

                    if (isChecked) {
                        //toastcent("verdadero");
                        chkPendientes = true;
                        ListaTareas();
                    } else {
                        //toastcent("falso");
                        chkPendientes = false;
                        ListaTareas();
                    }
                }
            });

            listCiclico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(position);

                        execws(4);

                        startActivity(new Intent(getApplicationContext(),frm_inv_cic_add.class));

                    }
                }

            });

        }catch(Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void BuscarFiltro2() {




    }

    private void BuscarFiltro() {

        Integer registros = 0;
        String evaluar = txtBuscFiltro.getText().toString().trim();

        for (int i = 0; i < data_list.size(); i++) {

            String ubicacion = String.valueOf(data_list.get(i).NoUbic);

            if (ubicacion.equals(evaluar) && data_list.get(i).cantidad.equals(0))
                registros = registros+1;
        }

        if(registros>1){

            msgbox("La úbicación contiene más codigos de producto, escanee ahora el código de producto.");
        }
        else if(registros == 0){

            for (int i = 0; i < data_list.size(); i++) {

                String codigo_producto = data_list.get(i).Codigo;

                if (codigo_producto.equals(evaluar) && data_list.get(i).cantidad.equals(0.0))

                    registros = registros+1;
            }
            if(registros >= 1){

                msgbox("cargar 1er registro de la lista.");
            }
            else if(registros ==0){
                msgbox("código de ubicación no existe en UBICACIONES asignadas de inventario.");
            }

        }

    }

    private void processReConteos() {
        try{

            reconteos = xobj.getresult(clsBeTrans_inv_enc_reconteoList.class,"Inventario_Ciclico_ReConteos");

            if (reconteos != null){

                if(reconteos.items != null){
                    if(reconteos.items.size()>0){

                        idreconteo = reconteos.items.size();

                    }else{

                        idreconteo = 0;
                    }

                }
            }

            esconteo = false;
            idreconteo = 0;

            ListaTareas();

        }
        catch (Exception e){
            mu.msgbox("processReConteos:"+e.getMessage());
        }
    }

    private void Existe_Producto() {

        try{

            existeProducto = xobj.getresult(Existe_producto.class,"Existe_Producto");

        }
        catch (Exception e){
            mu.msgbox("Existe_Producto:"+e.getMessage());
        }

    }

    private void processCiclico_Listar_Conteo() {

        clsBe_inv_reconteo_data rec;
        try{
            DT = xobj.filldt();
            data_list.clear();

            if(!txtBuscFiltro.equals("")){

                if(DT !=null){
                    if(DT.getCount()>0){

                        rec = new clsBe_inv_reconteo_data();
                        data_list.add(rec);

                        DT.moveToFirst();

                        while (!DT.isAfterLast()) {

                            data_rec = new clsBe_inv_reconteo_data();
                            data_rec.NoUbic = Integer.parseInt(DT.getString(4));
                            data_rec.IdProductoBodega = Integer.parseInt(DT.getString(1));
                            data_rec.IdPresentacion = Integer.parseInt(DT.getString(3));
                            data_rec.Codigo = DT.getString(30);
                            data_rec.Producto_nombre = DT.getString(20);
                            data_rec.Pres = DT.getString(22);
                            data_rec.UMBas = DT.getString(23);
                            data_rec.cantidad = Double.valueOf(DT.getString(11));

                            if(DT.getString(7)!=null){
                                data_rec.Lote = DT.getString(7);
                            }else{
                                data_rec.Lote = "";
                            }

                            if (DT.getString(9)!=null){
                                //vItem.FechaVence = du.convierteFechaMostar(DT.getString(19));
                                data_rec.Fecha_Vence =  du.convierteFechaMostar(DT.getString(9));
                            }else{
                                data_rec.Fecha_Vence = "";
                            }

                            data_rec.control_peso = Boolean.valueOf(DT.getString(24));
                            data_rec.Conteo = Integer.parseInt(DT.getString(11));
                            data_rec.Ubic_nombre = DT.getString(21);
                            data_rec.Estado = DT.getString(19);
                            data_rec.Factor = Double.valueOf(DT.getString(35));

                            data_list.add(data_rec);
                            DT.moveToNext();

                        }

                        int count =data_list.size()-1;
                        //cmdList.setText("No.Reg: "+count);
                        cmdList.setText( count+ "/" + count);

                        if (DT!=null) DT.close();

                        adapter_ciclico= new list_adapt_consulta_ciclico(getApplicationContext(),data_list);
                        listCiclico.setAdapter(adapter_ciclico);


                   /*     for (int i = 0; i < data_list.size(); i++) {

                            data_list2.items.get(i).NoUbic = data_list.get(i).NoUbic;

                        }*/


                    }else{
                        rec = new clsBe_inv_reconteo_data();
                        data_list.add(rec);

                        adapter_ciclico= new list_adapt_consulta_ciclico(getApplicationContext(),data_list);
                        listCiclico.setAdapter(adapter_ciclico);
                        cmdList.setText( "0/0");
                    }
                }
            }

            progress.cancel();

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processReConteos:"+e.getMessage());
        }
    }

    private void ListaTareas() {

        if(BeInvEnc.Idinventarioenc != 0){

            Integer idprodbod = 0;
            ubicid = 0;
            lic_plate = false;
            lplate = "";

            if(txtBuscFiltro.getText().toString() !=""){
                SubTarea1();
            }else{
                SubTarea2();
            }


        }

    }

    private void SubTarea1(){

        clsBeProducto tProd;

        try{

                if(checkbox.isChecked()){
                    //filtrar data de items
                    // item = items.AsEnumerable.Where(Function(x) (x.Item("IdUbicacion") = txtBuscFiltro.Text.Trim OrElse x.Item("Codigo_Producto") = txtBuscFiltro.Text.Trim) AndAlso x.Item("Cantidad") = 0).FirstOrDefault()


                    item = 0;

                }else{
                    //filtrar data de items
                    //item = items.AsEnumerable.Where(Function(x) (x.Item("IdUbicacion") = txtBuscFiltro.Text.Trim OrElse x.Item("Codigo_Producto") = txtBuscFiltro.Text.Trim) AndAlso x.Item("Cantidad") > 0).FirstOrDefault()
                    item = 1;
                }

                if(item != null){

                    tareapos = Idx;
                    txtBuscFiltro.setText("");
                    //Tarea_Conteo()
                    //Valida_Ubicacion_Orig();
                }
                else{

                    if(BeInvEnc.Capturar_no_existente){

                        execws(2);

                        if(!(existeProducto.respuesta)){

                            msgCompletar("El producto no existe en el maestro,¿Desea insertarlo?");

                        }else{

                            Toast.makeText(getApplicationContext(),"Código de ubicación no existe en ubicaciones asignadas de inventario",Toast.LENGTH_SHORT);
                        }

                    }else{

                        Toast.makeText(getApplicationContext(),"Código de ubicación no existe en ubicaciones asignadas de inventario",Toast.LENGTH_SHORT);
                    }
                }

            //items = m_proxy.Inventario_Ciclico_Listar_Conteo(tarea.Idinventarioenc, _ gOperador, _ Not chkPend.Checked)
            SubTarea3();

        }
        catch (Exception e){
            lic_plate = false;
            lplate = "";
            mu.msgbox("processReConteos:"+e.getMessage());
        }
    }

    private void SubTarea2(){

        execws(3);

        if(LoteSel !="" && FechaVencSel !="" && ProdSel != "0" && IdUbicacionSel != 0){
            if(checkbox.isChecked()){

                            /*item = items.AsEnumerable.Where(Function(x) x.Item("IdUbicacion") = IdUbicacionSel AndAlso x.Item("Codigo_Producto") = ProdSel _
                                    AndAlso x.Item("Cantidad") = 0 AndAlso x.Item("Lote_stock") = LoteSel _
                                    AndAlso Format(x.Item("Fecha_vence_stock"), "yyyyMMdd") = FechaVencSel _
                                    AndAlso x.Item("pres_nombre") = PresSel).FirstOrDefault()*/

            }else{
                            /*item = items.AsEnumerable.Where(Function(x) x.Item("IdUbicacion") = IdUbicacionSel AndAlso x.Item("Codigo_Producto") = ProdSel _
                                    AndAlso x.Item("Cantidad") > 0 AndAlso x.Item("Lote_stock") = LoteSel _
                                    AndAlso Format(x.Item("Fecha_vence_stock"), "yyyyMMdd") = FechaVencSel _
                                    AndAlso x.Item("pres_nombre") = PresSel).FirstOrDefault()*/
            }

            FechaVencSel = "";
            LoteSel = "";
            PresSel = "";
            IdUbicacionSel = 0;
            ProdSel = "";

            if(item != null ){
                tareapos = Idx;
                txtBuscFiltro.setText("");
                //Tarea_Conteo();
                //txtUbic.Text = item.Item("IdUbicacion");
                //Valida_Ubicacion_Orig();
                //txtProd.Focus();
            }
            else{
                mu.msgbox("Código de ubicación no existe en ubicaciones asignadas de inventario");
            }

        }
        else if(LoteSel != "" && FechaVencSel != "" && ProdSel != "" && IdUbicacionSel != 0){

            if(checkbox.isChecked()){

                            /*item = items.AsEnumerable.Where(Function(x) x.Item("IdUbicacion") = IdUbicacionSel AndAlso x.Item("Codigo_Producto") = ProdSel _
                                    AndAlso x.Item("Cantidad") = 0 _
                                    AndAlso Format(x.Item("Fecha_vence_stock"), "yyyyMMdd") = FechaVencSel _
                                    AndAlso x.Item("pres_nombre") = PresSel).FirstOrDefault()*/

            }else{

                           /* item = items.AsEnumerable.Where(Function(x) x.Item("IdUbicacion") = IdUbicacionSel AndAlso x.Item("Codigo_Producto") = ProdSel _
                                    AndAlso x.Item("Cantidad") > 0 _
                                    AndAlso Format(x.Item("Fecha_vence_stock"), "yyyyMMdd") = FechaVencSel _
                                    AndAlso x.Item("pres_nombre") = PresSel).FirstOrDefault()*/
            }

            FechaVencSel = "";
            LoteSel = "";
            PresSel = "";
            IdUbicacionSel = 0;
            ProdSel = "";

            if(item != null ){
                tareapos = Idx;
                txtBuscFiltro.setText("");
                //Tarea_Conteo();
                //txtUbic.Text = item.Item("IdUbicacion");
                //Valida_Ubicacion_Orig();
                //txtProd.Focus();
            }
            else{
                mu.msgbox("Código de ubicación no existe en ubicaciones asignadas de inventario");
            }
        }

        SubTarea3();

    }

    private void SubTarea3() {

        cmdList.setText("0/0");

        if(!lic_plate){

            execws(3);

            //Toast.makeText(getApplicationContext(),"datatable " + DT.getCount() ,Toast.LENGTH_SHORT);

        }

    }

    private void Tarea_Conteo() {

        try{

            gl.pprod.IdProducto = Integer.valueOf( xobj.getresultSingle(String.class,"IdProducto"));
            gl.pprod.Control_lote= xobj.getresultSingle(Boolean.class,"Control_lote");
            gl.pprod.Control_vencimiento = xobj.getresultSingle(Boolean.class,"Control_vencimiento");

        }
        catch (Exception e){
            mu.msgbox("Existe_Producto:"+e.getMessage());
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
                        callMethod("Inventario_Ciclico_ReConteos","pIdenc",BeInvEnc.Idinventarioenc);
                        break;
                    case 2:
                        callMethod("Existe_Producto","pCodigo",txtBuscFiltro.getText().toString().trim());
                        break;
                    case 3:
                        callMethod("Inventario_Ciclico_Listar_Conteo","pIdInventarioEnc",BeInvEnc.Idinventarioenc,
                                "pIdOperador",gl.IdOperador,"pPendientes",chkPendientes);
                        break;

                    case 4:
                        callMethod("Get_Control_Lote_And_Vencimiento_By_IdProductoBodega","IdProductoBodega",gl.inv_ciclico.IdProductoBodega,
                                "IdProducto",gl.pprod.IdProducto,"Control_Lote",gl.pprod.Control_lote,"Control_Vencimiento",gl.pprod.Control_vencimiento);
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
                    processReConteos();
                    break;
                case 2:
                    Existe_Producto();
                    break;
                case 3:
                    processCiclico_Listar_Conteo();
                    break;
                case 4:
                    Tarea_Conteo();
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

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir?");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Exit(View view) {
        msgAskExit("Está seguro de salir");
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

                    /*BeInvTramo = new clsBeTrans_inv_tramo();
                    BeUbic = new clsBeBodega_ubicacion();
                    Listtramos = new clsBeTrans_inv_tramoList();
                    BeListTramos = new ArrayList<clsBeTrans_inv_tramo>();
                    TipoConteo = 0;*/

                    frm_inv_cic_conteo.super.finish();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
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
                    //Tarea_Producto_Nuevo();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            mu.msgbox("msgTarea_Producto_Nuevo"+e.getMessage());
        }
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    public static class Existe_producto{
        @Element(required=false) public boolean respuesta=false;

        public Existe_producto() {
        }

        public Existe_producto(boolean respuesta){
            this.respuesta=respuesta;
        }

        public boolean getrespuesta() {
            return respuesta;
        }
        public void setrespuesta(boolean value) {
            respuesta=value;
        }
    }

}