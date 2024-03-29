package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBeTrans_inv_enc_reconteoList;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.ladapt.InventarioCiclico.list_adapt_consulta_ciclico;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;


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
    private TextView cmdList, lblConteo;
    CheckBox checkbox;
    boolean chkPendientes;
    private Integer Idx;
    private Cursor DT;
    private boolean Busqueda;

    //Existe_producto existeProducto = new Existe_producto();
    private clsBe_inv_reconteo_data data_rec = new clsBe_inv_reconteo_data();
    private ArrayList<clsBe_inv_reconteo_data> lista_filtro = new ArrayList<clsBe_inv_reconteo_data>();
    private ArrayList<clsBe_inv_reconteo_data> data_list = new ArrayList<clsBe_inv_reconteo_data>();
    private clsBeTrans_inv_enc_reconteoList reconteos = new clsBeTrans_inv_enc_reconteoList();
    private clsBeTrans_inv_enc_reconteoList registro_ciclico = new clsBeTrans_inv_enc_reconteoList();
    private Object item;
    private clsBeProducto BeProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_conteo);

        super.InitBase();

        lblConteo = findViewById(R.id.lblConteo);
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
        Busqueda = false;

        ws = new WebServiceHandler(frm_inv_cic_conteo.this,gl.wsurl);
        xobj = new XMLObject(ws);


        gl.pprod.IdProducto = 0;
        gl.pprod.Control_lote= false;
        gl.pprod.Control_vencimiento = false;

        ProgressDialog("Cargando conteo ciclico.");

        Lista_Tareas();

        setHandles();

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

                                        ListaFiltrada();

                                    }else {

                                        ListaFiltrada2();

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

                        chkPendientes = true;
                        checkbox.setText("Pendientes");
                        ProgressDialog("Cargando pendientes.");
                        execws(1);

                    } else {

                        chkPendientes = false;
                        checkbox.setText("Contados");
                        ProgressDialog("Cargando contados.");
                        execws(1);

                    }
                }
            });

            listCiclico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    gl.IndexCiclico = 0;

                    if (position > 0) {

                        gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(position);
                        gl.inv_ciclico.index = position;
                        gl.IndexCiclico = gl.inv_ciclico.index;

                        execws(4);

                    }
                }

            });

        }catch(Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }


    private void Lista_Tareas(){

        if(BeInvEnc.Idinventarioenc != 0){

            cmdList.setText(" 0/0 ");
            ubicid = 0;
            lic_plate = false;
            lplate = "";

            execws(1);
            //execws(3);

        } else{
            mu.msgbox("validar: No hay Tarea registrada.");
        }
    }

    private void processCiclico_Listar_Conteo() {

        clsBe_inv_reconteo_data rec;

        try{

            DT = xobj.filldt();
            data_list.clear();
            gl.reconteo_list.clear();
            int index = 0;

            if(!txtBuscFiltro.equals("")){

                if(DT !=null){
                    if(DT.getCount()>0){

                        rec = new clsBe_inv_reconteo_data();
                        data_list.add(rec);

                        DT.moveToFirst();

                        while (!DT.isAfterLast()) {

                            data_rec = new clsBe_inv_reconteo_data();


                            data_rec.index = index;
                            data_rec.idinventarioenc = Integer.parseInt(DT.getString(0));
                            data_rec.idinvreconteo = Integer.parseInt(DT.getString(1));
                            data_rec.IdProductoBodega = Integer.parseInt(DT.getString(2));
                            data_rec.IdProductoEstado = Integer.parseInt(DT.getString(3));

                            if(DT.getString(4) != null){
                                data_rec.IdPresentacion = Integer.parseInt(DT.getString(4));
                            }else{
                                data_rec.IdPresentacion = 0;
                            }

                            data_rec.NoUbic = Integer.parseInt(DT.getString(5));

                            if(DT.getString(7)!=null){
                                data_rec.Lote_stock = DT.getString(7);
                            }else{
                                data_rec.Lote_stock = "";
                            }

                            if(DT.getString(8)!=null){
                                data_rec.Lote = DT.getString(8);
                            }else{
                                data_rec.Lote = "";
                            }

                            //fecha_vence_stock = index 9, fecha_vence = index 10
                            if (DT.getString(9)!=null){
                                data_rec.Fecha_Vence =  du.convierteFechaMostrar(DT.getString(9));
                            }else{
                                data_rec.Fecha_Vence = "";
                            }

                            if(BeInvEnc.Mostrar_Cantidad_Teorica_hh){
                                data_rec.Cant_Stock = Double.valueOf(DT.getString(11));
                            }

                            data_rec.cantidad = Double.valueOf(DT.getString(12));
                            data_rec.Conteo = Integer.parseInt(DT.getString(13));

                            data_rec.Peso = Double.valueOf(DT.getString(15));

                            //GT01122021:se agrega licence plate  y los index se corren 1 posición del 20 en adelante
                            data_rec.Licence_plate = DT.getString(18);


                            data_rec.Estado = DT.getString(21);
                            data_rec.Producto_nombre = DT.getString(22);
                            data_rec.Ubic_nombre = DT.getString(23);
                            data_rec.Pres = DT.getString(24);
                            data_rec.UMBas = DT.getString(25);
                            data_rec.control_peso = Boolean.valueOf(DT.getString(26));
                            data_rec.idPresentacion_nuevo = Integer.parseInt(DT.getString(29));
                            data_rec.IdProductoEst_nuevo = Integer.parseInt(DT.getString(31));
                            data_rec.Codigo = DT.getString(32);
                            data_rec.Factor = Double.valueOf(DT.getString(37));

                            data_list.add(data_rec);

                            //GT 30122020 lista global para ser accedida desde validaciones cuando se agrega nuevo_producto
                            gl.reconteo_list.add(data_rec);

                            index = index+1;

                            DT.moveToNext();
                        }

                        //Se resta un registro, porque el primero es un registro para los encabezados del grid
                        int count =data_list.size()-1;
                        cmdList.setText( count+ "/" + count);

                        if (DT!=null) DT.close();

                        adapter_ciclico= new list_adapt_consulta_ciclico(getApplicationContext(),data_list);
                        listCiclico.setAdapter(adapter_ciclico);


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
            mu.msgbox("Carga_Conteos:"+e.getMessage());
        }
    }

    private void iniciaTareas(){

    try{

        reconteos = xobj.getresult(clsBeTrans_inv_enc_reconteoList.class,"Inventario_Ciclico_ReConteos");

        if (reconteos != null){

            if(reconteos.items != null){
                if(reconteos.items.size()>0  && reconteos.items.get(0).Reconteo > 0){

                    idreconteo = reconteos.items.get(0).Reconteo;

                }else{

                    idreconteo = 0;
                }
            }
        }

        esconteo = (idreconteo == 0?true:false);

        if(esconteo){
            lblConteo.setText("Conteo");
        }else{
            lblConteo.setText("Reconteo #" + idreconteo);
        }

        execws(3);

    }
    catch (Exception e){
        mu.msgbox("processReConteos:"+e.getMessage());
    }

}

    private void ListaFiltrada() {

        Integer registros = 0;

        String evaluar = txtBuscFiltro.getText().toString().trim();

        //GT 01122020 inicia busqueda en lista por Ubicación
        for (int i = 0; i < data_list.size(); i++) {

            String ubicacion = String.valueOf(data_list.get(i).NoUbic);

            if (ubicacion.equals(evaluar) && data_list.get(i).cantidad.equals(0.0)){

                registros = registros+1;

                if (registros==1){
                    gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(i);
                }
            }
        }

        if(registros>1){

            //carga la lista con el Filtro Ubicación
            FiltroxUbicacion(evaluar);

            gl.inv_ciclico = new clsBe_inv_reconteo_data();

            msgbox("La úbicación contiene más codigos de producto, seleccione ahora el código de producto.");

            txtBuscFiltro.setText("");

            Busqueda = false;

        }else if(registros==1){

            Busqueda = true;

            execws(4);
            //startActivity(new Intent(getApplicationContext(),frm_inv_cic_add.class));

        } else if(registros == 0){

            for (int i = 0; i < data_list.size(); i++) {

                String codigo_producto = data_list.get(i).Codigo;

                if (codigo_producto.equals(evaluar) && data_list.get(i).cantidad.equals(0.0)){

                    registros = registros+1;

                    if (registros==1){
                        gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(i);
                    }
                }
            }
            if(registros > 1){


                //carga la lista con el Filtro Código
                FiltroxCodigo(evaluar);

                gl.inv_ciclico = new clsBe_inv_reconteo_data();
                msgbox("El código contiene varias ubicaciones, seleccione ahora el código de producto.");
                txtBuscFiltro.setText("");
                Busqueda= false;

            } else if (registros ==1){

                Busqueda = true;
                execws(4);

            }
            else if(registros ==0){

                //GT 18012020 Busqueda no resulta por ubicación/producto, se inicia por lote
                for (int i = 0; i < data_list.size(); i++) {

                    String lote_stock = data_list.get(i).Lote_stock;

                    if (lote_stock.equals(evaluar) && data_list.get(i).cantidad.equals(0.0)){

                        registros = registros+1;

                        if (registros==1){
                            gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(i);
                        }
                    }
                }
                if(registros > 1){

                    //GT 18012020 carga la lista con el Filtro Lote
                    FiltroxLote(evaluar);

                    gl.inv_ciclico = new clsBe_inv_reconteo_data();
                    msgbox("La busqueda contiene varios productos, seleccione ahora el código de producto.");
                    txtBuscFiltro.setText("");
                    Busqueda= false;

                } else if (registros ==1){

                    Busqueda = true;
                    execws(4);
                }


                //GT 18012020 se omite validación de capturar_noexiste porque no se insertará código si existe en la bd, pero no en la lista del conteo
                /*if(BeInvEnc.Capturar_no_existente){

                    execws(2);

                }else{

                    Toast.makeText(getApplicationContext(),"Código de ubicación no existe en ubicaciones asignadas de inventario",Toast.LENGTH_SHORT);
                }*/
            }
        }
    }

    private void FiltroxLote(String evaluar) {

        clsBe_inv_reconteo_data rec;

        lista_filtro.clear();

        rec = new clsBe_inv_reconteo_data();
        lista_filtro.add(rec);

        for (int i = 0; i < gl.reconteo_list.size(); i++) {

            String ubicacion_lista = String.valueOf(gl.reconteo_list.get(i).Lote);

            if (ubicacion_lista.equals(evaluar)){

                data_rec = new clsBe_inv_reconteo_data();

                data_rec.index = gl.reconteo_list.get(i).index;
                data_rec.idinventarioenc = gl.reconteo_list.get(i).idinventarioenc;
                data_rec.idinvreconteo = gl.reconteo_list.get(i).idinvreconteo;
                data_rec.NoUbic = gl.reconteo_list.get(i).NoUbic;
                data_rec.IdProductoBodega = gl.reconteo_list.get(i).IdProductoBodega;
                data_rec.IdProductoEstado = gl.reconteo_list.get(i).IdProductoEstado;
                data_rec.IdPresentacion = gl.reconteo_list.get(i).IdPresentacion;
                data_rec.Codigo = gl.reconteo_list.get(i).Codigo;
                data_rec.Producto_nombre = gl.reconteo_list.get(i).Producto_nombre;
                data_rec.Pres = gl.reconteo_list.get(i).Pres;
                data_rec.UMBas = gl.reconteo_list.get(i).UMBas;
                data_rec.cantidad = gl.reconteo_list.get(i).cantidad;
                data_rec.Lote = gl.reconteo_list.get(i).Lote;
                data_rec.Lote_stock = gl.reconteo_list.get(i).Lote_stock;
                data_rec.Peso = gl.reconteo_list.get(i).Peso;
                data_rec.Fecha_Vence =  gl.reconteo_list.get(i).Fecha_Vence;
                data_rec.control_peso = gl.reconteo_list.get(i).control_peso;
                data_rec.Conteo = gl.reconteo_list.get(i).Conteo;
                data_rec.Ubic_nombre = gl.reconteo_list.get(i).Ubic_nombre;
                data_rec.Estado = gl.reconteo_list.get(i).Estado;
                data_rec.Factor = gl.reconteo_list.get(i).Factor;
                data_rec.idPresentacion_nuevo = gl.reconteo_list.get(i).idPresentacion_nuevo;
                data_rec.IdProductoEst_nuevo = gl.reconteo_list.get(i).IdProductoEst_nuevo;
                lista_filtro.add(data_rec);
            }
        }

        adapter_ciclico= new list_adapt_consulta_ciclico(getApplicationContext(),lista_filtro);
        listCiclico.setAdapter(adapter_ciclico);

        int count =data_list.size()-1;
        cmdList.setText( count+ "/" + count);

    }

    private void FiltroxCodigo(String evaluar) {

        clsBe_inv_reconteo_data rec;

        lista_filtro.clear();

        rec = new clsBe_inv_reconteo_data();
        lista_filtro.add(rec);

        for (int i = 0; i < gl.reconteo_list.size(); i++) {

            String ubicacion_lista = String.valueOf(gl.reconteo_list.get(i).Codigo);

            if (ubicacion_lista.equals(evaluar)){

                data_rec = new clsBe_inv_reconteo_data();

                data_rec.index = gl.reconteo_list.get(i).index;

                data_rec.idinventarioenc = gl.reconteo_list.get(i).idinventarioenc;
                data_rec.idinvreconteo = gl.reconteo_list.get(i).idinvreconteo;

                data_rec.NoUbic = gl.reconteo_list.get(i).NoUbic;
                data_rec.IdProductoBodega = gl.reconteo_list.get(i).IdProductoBodega;
                data_rec.IdProductoEstado = gl.reconteo_list.get(i).IdProductoEstado;
                data_rec.IdPresentacion = gl.reconteo_list.get(i).IdPresentacion;
                data_rec.Codigo = gl.reconteo_list.get(i).Codigo;
                data_rec.Producto_nombre = gl.reconteo_list.get(i).Producto_nombre;
                data_rec.Pres = gl.reconteo_list.get(i).Pres;
                data_rec.UMBas = gl.reconteo_list.get(i).UMBas;
                data_rec.cantidad = gl.reconteo_list.get(i).cantidad;
                data_rec.Lote = gl.reconteo_list.get(i).Lote;
                data_rec.Lote_stock = gl.reconteo_list.get(i).Lote_stock;
                data_rec.Peso = gl.reconteo_list.get(i).Peso;
                data_rec.Fecha_Vence =  gl.reconteo_list.get(i).Fecha_Vence;
                data_rec.control_peso = gl.reconteo_list.get(i).control_peso;
                data_rec.Conteo = gl.reconteo_list.get(i).Conteo;
                data_rec.Ubic_nombre = gl.reconteo_list.get(i).Ubic_nombre;
                data_rec.Estado = gl.reconteo_list.get(i).Estado;
                data_rec.Factor = gl.reconteo_list.get(i).Factor;
                data_rec.idPresentacion_nuevo = gl.reconteo_list.get(i).idPresentacion_nuevo;
                data_rec.IdProductoEst_nuevo = gl.reconteo_list.get(i).IdProductoEst_nuevo;
                lista_filtro.add(data_rec);
            }
        }

        adapter_ciclico= new list_adapt_consulta_ciclico(getApplicationContext(),lista_filtro);
        listCiclico.setAdapter(adapter_ciclico);

        int count =data_list.size()-1;
        cmdList.setText( count+ "/" + count);
    }

    private void FiltroxUbicacion(String evaluar) {

        clsBe_inv_reconteo_data rec;

        lista_filtro.clear();

        rec = new clsBe_inv_reconteo_data();
        lista_filtro.add(rec);

        for (int i = 0; i < gl.reconteo_list.size(); i++) {

            String ubicacion_lista = String.valueOf(gl.reconteo_list.get(i).NoUbic);

            if (ubicacion_lista.equals(evaluar)){

                data_rec = new clsBe_inv_reconteo_data();

                data_rec.index = gl.reconteo_list.get(i).index;

                data_rec.idinventarioenc = gl.reconteo_list.get(i).idinventarioenc;
                data_rec.idinvreconteo = gl.reconteo_list.get(i).idinvreconteo;

                data_rec.NoUbic = gl.reconteo_list.get(i).NoUbic;
                data_rec.IdProductoBodega = gl.reconteo_list.get(i).IdProductoBodega;
                data_rec.IdProductoEstado = gl.reconteo_list.get(i).IdProductoEstado;
                data_rec.IdPresentacion = gl.reconteo_list.get(i).IdPresentacion;
                data_rec.Codigo = gl.reconteo_list.get(i).Codigo;
                data_rec.Producto_nombre = gl.reconteo_list.get(i).Producto_nombre;
                data_rec.Pres = gl.reconteo_list.get(i).Pres;
                data_rec.UMBas = gl.reconteo_list.get(i).UMBas;
                data_rec.cantidad = gl.reconteo_list.get(i).cantidad;
                data_rec.Lote = gl.reconteo_list.get(i).Lote;
                data_rec.Lote_stock = gl.reconteo_list.get(i).Lote_stock;
                data_rec.Peso = gl.reconteo_list.get(i).Peso;
                data_rec.Fecha_Vence =  gl.reconteo_list.get(i).Fecha_Vence;
                data_rec.control_peso = gl.reconteo_list.get(i).control_peso;
                data_rec.Conteo = gl.reconteo_list.get(i).Conteo;
                data_rec.Ubic_nombre = gl.reconteo_list.get(i).Ubic_nombre;
                data_rec.Estado = gl.reconteo_list.get(i).Estado;
                data_rec.Factor = gl.reconteo_list.get(i).Factor;
                data_rec.idPresentacion_nuevo = gl.reconteo_list.get(i).idPresentacion_nuevo;
                data_rec.IdProductoEst_nuevo = gl.reconteo_list.get(i).IdProductoEst_nuevo;
                lista_filtro.add(data_rec);
            }
        }

        adapter_ciclico= new list_adapt_consulta_ciclico(getApplicationContext(),lista_filtro);
        listCiclico.setAdapter(adapter_ciclico);

        int count =data_list.size()-1;
        cmdList.setText( count+ "/" + count);
    }

    private void ListaFiltrada2() {

        Integer registros = 0;

        String evaluar = txtBuscFiltro.getText().toString().trim();

        //GT 01122020 inicia busqueda en lista por Ubicación
        for (int i = 0; i < data_list.size(); i++) {

            String ubicacion = String.valueOf(data_list.get(i).NoUbic);

            if (ubicacion.equals(evaluar) && data_list.get(i).cantidad > 0 ){

                registros = registros+1;

                if (registros==1){
                    gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(i);
                }
            }
        }

        if(registros>1){

            //carga la lista con el Filtro Ubicación
            FiltroxUbicacion(evaluar);

            gl.inv_ciclico = new clsBe_inv_reconteo_data();
            msgbox("La úbicación contiene más codigos de producto, escanee ahora el código de producto.");
            txtBuscFiltro.setText("");
            Busqueda = false;

        }else if(registros==1){

            Busqueda = true;

            execws(4);

        } else if(registros == 0){

            for (int i = 0; i < data_list.size(); i++) {

                String codigo_producto = data_list.get(i).Codigo;

                if (codigo_producto.equals(evaluar) && data_list.get(i).cantidad > 0){

                    registros = registros+1;

                    if (registros==1){
                        gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(i);
                    }
                }
            }
            if(registros > 1){

                //carga la lista con el Filtro Código
                FiltroxCodigo(evaluar);

                gl.inv_ciclico = new clsBe_inv_reconteo_data();
                msgbox("La úbicación contiene más codigos de producto, escanee ahora el código de producto.");
                txtBuscFiltro.setText("");
                Busqueda= false;

            } else if (registros ==1){

                //Se encontró una coincidencia en la busqueda y la clase global se ha llenado, solo se carga el activity con la data
                Busqueda = true;

                execws(4);

            }
            else if(registros ==0){

                //GT 18012020 Busqueda no resulta por ubicación/producto, se inicia por lote
                for (int i = 0; i < data_list.size(); i++) {

                    String lote_stock = data_list.get(i).Lote_stock;

                    if (lote_stock.equals(evaluar) && data_list.get(i).cantidad.equals(0.0)){

                        registros = registros+1;

                        if (registros==1){
                            gl.inv_ciclico = (clsBe_inv_reconteo_data) listCiclico.getItemAtPosition(i);
                        }
                    }
                }
                if(registros > 1){

                    //GT 18012020 carga la lista con el Filtro Lote
                    FiltroxLote(evaluar);

                    gl.inv_ciclico = new clsBe_inv_reconteo_data();
                    msgbox("La busqueda contiene varios productos, seleccione ahora el código de producto.");
                    txtBuscFiltro.setText("");
                    Busqueda= false;

                } else if (registros ==1){

                    Busqueda = true;
                    execws(4);
                }


                //GT 18012020 se omite validación de capturar_noexiste porque no se insertará código si existe en la bd, pero no en la lista del conteo
           /*     if(BeInvEnc.Capturar_no_existente){

                    execws(2);

                }else{

                    msgbox("Código de ubicación no existe en ubicaciones asignadas de inventario");
                }*/
            }
        }

    }

    private void Existe_Producto() {

        try{

            boolean respuesta = false;

            //existeProducto = xobj.getresult(Existe_producto.class,"Existe_Producto");
            respuesta = xobj.getresult(Boolean.class,"Existe_Producto");

            if(! respuesta){

                msgNuevoRegistro("El producto no existe en el maestro,¿Desea insertarlo?");

            }else{
                msgbox("Código de ubicación no existe en ubicaciones asignadas de inventario");
            }

        }
        catch (Exception e){
            mu.msgbox("Existe_Producto:"+e.getMessage());
        }

    }

    private void Tarea_Conteo() {

        try{

            gl.pprod.IdProducto = Integer.valueOf( xobj.getresultSingle(String.class,"IdProducto"));
            gl.pprod.Control_lote= xobj.getresultSingle(Boolean.class,"Control_lote");
            gl.pprod.Control_vencimiento = xobj.getresultSingle(Boolean.class,"Control_vencimiento");

            if (BeInvEnc.Idpropietario >0){
                execws(5);
            }
            else if (BeInvEnc.multi_propietario){
                gl.multipropietario = BeInvEnc.multi_propietario;
                execws(6);
            }else{
                msgbox("El inventario no tiene asignado un propietario.");
            }

        }
        catch (Exception e){
            mu.msgbox("Existe_Producto:"+e.getMessage());
        }

    }

    private void Llena_Estado() {

        try {

            gl.lista_estados = xobj.getresult(clsBeProducto_estadoList.class, "Get_Estados_By_IdPropietario");

            if(gl.lista_estados !=null){

                txtBuscFiltro.setText("");
                startActivity(new Intent(getApplicationContext(),frm_inv_cic_add.class));
            }else{
                msgbox("No hay estados para asignar al producto");
            }


        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    public void limpiar(View view) {
        txtBuscFiltro.setText("");
        txtBuscFiltro.requestFocus();
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

                    case 5:
                        callMethod("Get_Estados_By_IdPropietario","pIdPropietario",BeInvEnc.Idpropietario);
                        break;

                    case 6:
                        callMethod("Get_BeProducto_By_IdProducto","pIdProducto",gl.pprod.IdProducto);
                        break;
                }

                //progress.cancel();

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
                    //processReConteos();
                    iniciaTareas();
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
                case 5:
                    Llena_Estado();
                    break;

                case 6:
                    Llena_Producto();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Llena_Producto() {

        try{

            BeProducto = new clsBeProducto();


            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_IdProducto");

            if (BeProducto != null){

                BeInvEnc.Idpropietario =  BeProducto.IdPropietario;
                execws(5);
            }

        }catch (Exception e){
            mu.msgbox("processBeProductoRes:"+e.getMessage());
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

                    //gl.reconteo_list.clear();
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

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void msgNuevoRegistro(String msg) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    gl.nuevo_producto_cic = txtBuscFiltro.getText().toString().trim();
                    txtBuscFiltro.setText("");
                    gl.cerrarActividad2=false;
                    startActivity(new Intent(getApplicationContext(), frm_inv_cic_nuevo.class));
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

    @Override
    public void onRestart()
    {
        super.onRestart();
        Lista_Tareas();

    }

}