package com.dts.tom.Transacciones.ConsultaStock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI_List;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.InventarioCiclico.frm_inv_cic_add;
import com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


public class frm_consulta_stock extends PBase {

    private frm_consulta_stock.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private ListView listView;
    private Button btnBack, registros;
    private int pIdTarea=0;
    private EditText txtCodigo, txtUbic;
    private int idubic, idprod, conteo;
    private clsBeBodega_ubicacion cUbic;
    private clsBeProductoList ListBeStockPallet;
    private String pLicensePlate;
    private clsBeProducto BeProducto;
    private boolean Escaneo_Pallet;
    private TextView lblNombreUbicacion;
    private TextView lblNombreProducto;
    private Boolean idle = false;
    private Integer selest;
    private clsBeVW_stock_res_CI_List pListStock2;
    private list_adapt_consulta_stock adapter_stock;
    private ArrayList<clsBeVW_stock_res_CI> items_stock = new ArrayList<clsBeVW_stock_res_CI>();
    private ArrayList<clsBeVW_stock_res_CI> items_stock2 = new ArrayList<clsBeVW_stock_res_CI>();
    //clsBeVW_stock_res_CI  ItemSelected;

    private Spinner cmbEstadoExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock);

        super.InitBase();

        ws = new WebServiceHandler(frm_consulta_stock.this, gl.wsurl);
        xobj = new XMLObject(ws);
        Escaneo_Pallet = false;
        idle = false;
        selest = 0;

        listView = (ListView) findViewById(R.id.listExist);
        btnBack = (Button) findViewById(R.id.btnBack);
        registros = findViewById(R.id.btnRegs2);
        txtCodigo = (EditText) findViewById(R.id.txtCodigo1);
        txtUbic = (EditText) findViewById(R.id.txtUbic1);
        lblNombreUbicacion = findViewById(R.id.lblNombreUbicacion);
        lblNombreProducto = findViewById(R.id.lblNombreProducto);
        cmbEstadoExist = findViewById(R.id.cmbEstadoExist);

        setHandlers();
    }

    private void setHandlers() {
        try{

            txtUbic.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lblNombreUbicacion.setText("");
                    registros.setText("REGISTROS: "+ 0);
                    selest = 0;
                    items_stock2.clear();
                }
            });

            txtCodigo.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lblNombreProducto.setText("");
                    registros.setText("REGISTROS: "+ 0);
                    selest = 0;
                    items_stock2.clear();
                }
            });

            txtUbic.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                //lbldescripcion.setTextColor(Color.WHITE);
                                lblNombreUbicacion.setText("");

                                if (txtUbic.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()
                                       ){
                                    toast("Ubicación de producto no definida!");
                                } else{

                                    if(txtUbic.getText().toString().isEmpty()){
                                        idubic = 0;
                                    }
                                    else{
                                        ProgressDialog("Cargando existencias");

                                        execws(1);
                                    }
                                }
                        }
                    }
                    return false;
                }
            });

            txtCodigo.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                lblNombreProducto.setText("");

                                if (txtCodigo.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()
                                ) {
                                    toast("Escane código de producto");
                                } else {

                                    if(txtCodigo.getText().toString().isEmpty()){
                                        idprod = 0;
                                    }
                                    else{

                                        String vStarWithParameter = "$";
                                        //Comentario: La barra de pallet puede comenzar con $ y no con (01)
                                        if (txtCodigo.getText().toString().startsWith("$") ||
                                                txtCodigo.getText().toString().startsWith("(01)") ||
                                                txtCodigo.getText().toString().startsWith(vStarWithParameter)) {
                                            //Es una barra de pallet válida por tamaño
                                            int vLengthBarra = txtCodigo.getText().toString().length();
                                            if (vLengthBarra >= 16) {
                                                pLicensePlate = txtCodigo.getText().toString().replace("$", "");

                                                ProgressDialog("Cargando existencias");
                                                execws(2);
                                            }
                                        } else {

                                            ProgressDialog("Cargando existencias");
                                            execws(3);
                                        }
                                    }
                                }
                        }
                    }
                    return false;
                }
            });

            cmbEstadoExist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    String estado = cmbEstadoExist.getSelectedItem().toString();

                    if(estado.isEmpty() && selest ==0){

                    }
                    else {

                        Spinner(estado);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }

            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        gl.existencia = (clsBeVW_stock_res_CI) listView.getItemAtPosition(position);

                        startActivity(new Intent(getApplicationContext(),frm_consulta_stock_detalleCI.class));

                    }

                }

            });

        }
        catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    public void Listar_Existencias(){
        try{
            if(!Escaneo_Pallet){

                //Get_Stock_Por_Producto_Ubicacion
                execws(4);
            }else{

                //Get_Stock_Por_Pallet
                execws(5);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void processUbicacion() {

        try {
                cUbic =xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

                if (cUbic != null){
                    idubic = cUbic.IdUbicacion;

                    lblNombreUbicacion.setTextColor(Color.BLUE);
                    lblNombreUbicacion.setText(cUbic.getDescripcion());

                    //toast("ubicación encontrada " +  cUbic.getDescripcion());
                }else{
                    idubic = 0;
                    lblNombreUbicacion.setTextColor(Color.RED);
                    lblNombreUbicacion.setText("Ubicación no existe en la bodega" + gl.IdBodega);

                    //throw new Exception("La ubicación no existe en la bodega: " + gl.IdBodega);
                }

            Listar_Existencias();

        } catch (Exception e) {
            //progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processUbicacion2(){
        //Llama al método del WS Get_Stock_By_Lic_Plate
        try {
            ListBeStockPallet = xobj.getresult(clsBeProductoList.class,"Get_Stock_By_Lic_Plate");

            if (ListBeStockPallet != null){
                Escaneo_Pallet = true;
                //toast("Pallet si existe en la bodega");
            }else {
                idubic = 0;
                throw new Exception("Pallet no existe en la bodega: " + gl.IdBodega);
            }

            Listar_Existencias();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

        }

    private void processScanProducto(){
        idubic = 0;
        try {
            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null){
                idprod = BeProducto.IdProducto;
                lblNombreProducto.setText(BeProducto.getNombre());
                //toast("ubicación encontrada por HH");
            }else{
                throw new Exception("El producto no existe en la bodega: " + gl.IdBodega);
            }

            Listar_Existencias();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void listaStock() {

        clsBeVW_stock_res_CI vItem;
        clsBeVW_stock_res_CI item;

        try {

            items_stock.clear();

            pListStock2= xobj.getresult(clsBeVW_stock_res_CI_List.class,"Get_Stock_Por_Producto_Ubicacion_CI");

            if(pListStock2 != null){

                conteo = pListStock2.items.size();

                if(conteo == 0 || pListStock2.items.isEmpty()){

                    lblNombreUbicacion.setText("U.S.P");
                    idle = true;
                }
                else{

                    // lbldescripcion.setText("");

                    vItem = new clsBeVW_stock_res_CI();
                    items_stock.add(vItem);

                    registros.setText("REGISTROS: "+ conteo);

                    for (int i = 0; i < pListStock2.items.size(); i++) {

                        item = new clsBeVW_stock_res_CI();
                        item.Codigo = pListStock2.items.get(i).Codigo;
                        item.Nombre = pListStock2.items.get(i).Nombre;
                        item.UM = pListStock2.items.get(i).UM;
                        item.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                        item.Pres = pListStock2.items.get(i).Pres;
                        item.ExistPres = pListStock2.items.get(i).ExistPres;
                        item.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                        item.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                        item.Lote = pListStock2.items.get(i).Lote;
                        item.Vence = pListStock2.items.get(i).Vence;
                        item.Estado = pListStock2.items.get(i).Estado;
                        item.Ubic = pListStock2.items.get(i).Ubic;
                        item.idUbic = pListStock2.items.get(i).idUbic;
                        item.Pedido = pListStock2.items.get(i).Pedido;
                        item.Pick = pListStock2.items.get(i).Pick;
                        item.LicPlate = pListStock2.items.get(i).LicPlate;
                        item.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;

                        items_stock.add(item);
                    }

                    adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                    listView.setAdapter(adapter_stock);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Map<String, List<clsBeVW_stock_res_CI>> ListaEstados = pListStock2.items.stream()
                                .collect(groupingBy(clsBeVW_stock_res_CI::getEstado));

                        List<String> categories = new ArrayList<String>();
                        categories.add("");

                        ListaEstados.forEach((k, v) -> {
                            //System.out.printf("%s : %d%n", k, v);
                            categories.add(k);
                        });

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cmbEstadoExist.setAdapter(dataAdapter);

                        if(categories.size() <= 1){
                            selest = 0;
                        }else{
                            selest = 1;
                        }
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listaStock2() {

        clsBeVW_stock_res_CI vItem;

        try {

            items_stock.clear();
            clsBeVW_stock_res_CI item;

            pListStock2 = xobj.getresult(clsBeVW_stock_res_CI_List.class,"Get_Stock_Por_Pallet");

            if(pListStock2 != null){

                vItem = new clsBeVW_stock_res_CI();
                items_stock.add(vItem);

                conteo = pListStock2.items.size();

                if(conteo == 0 || pListStock2.items.isEmpty()){

                    lblNombreUbicacion.setText("U.S.P");
                    idle = true;
                }
                else{

                    //lbldescripcion.setText("");

                }

                registros.setText("REGISTROS: "+ conteo);

                for (int i = 0; i < pListStock2.items.size(); i++) {

                    item = new clsBeVW_stock_res_CI();
                    item.Codigo = pListStock2.items.get(i).Codigo;
                    item.Nombre = pListStock2.items.get(i).Nombre;
                    item.UM = pListStock2.items.get(i).UM;
                    item.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                    item.Pres = pListStock2.items.get(i).Pres;
                    item.ExistPres = pListStock2.items.get(i).ExistPres;
                    item.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                    item.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                    item.Lote = pListStock2.items.get(i).Lote;
                    item.Vence = pListStock2.items.get(i).Vence;
                    item.Estado = pListStock2.items.get(i).Estado;
                    item.Ubic = pListStock2.items.get(i).Ubic;
                    item.idUbic = pListStock2.items.get(i).idUbic;
                    item.Pedido = pListStock2.items.get(i).Pedido;
                    item.Pick = pListStock2.items.get(i).Pick;
                    item.LicPlate = pListStock2.items.get(i).LicPlate;
                    item.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;

                    items_stock.add(item);
                }

                adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock);

                /***************************LLENAR COMBOBOX *******************************/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Map<String, List<clsBeVW_stock_res_CI>> ListaEstados = pListStock2.items.stream()
                            .collect(groupingBy(clsBeVW_stock_res_CI::getEstado));

                    List<String> categories = new ArrayList<String>();
                    categories.add("");

                    ListaEstados.forEach((k, v) -> {
                        //System.out.printf("%s : %d%n", k, v);
                        categories.add(k);
                    });

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbEstadoExist.setAdapter(dataAdapter);
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void Spinner(String estado){

        FiltroSpinner(estado);

    }

    private void FiltroSpinner(String estado){

        items_stock2.clear();
        clsBeVW_stock_res_CI items;
        clsBeVW_stock_res_CI vItem;

        if(estado == "" && selest>0){

            vItem = new clsBeVW_stock_res_CI();
            items_stock2.add(vItem);

            for (int i = 0; i < pListStock2.items.size(); i++) {

                items = new clsBeVW_stock_res_CI();
                items.Codigo = pListStock2.items.get(i).Codigo;
                items.Nombre = pListStock2.items.get(i).Nombre;
                items.UM = pListStock2.items.get(i).UM;
                items.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                items.Pres = pListStock2.items.get(i).Pres;
                items.ExistPres = pListStock2.items.get(i).ExistPres;
                items.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                items.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                items.Lote = pListStock2.items.get(i).Lote;
                items.Vence = pListStock2.items.get(i).Vence;
                items.Estado = pListStock2.items.get(i).Estado;
                items.Ubic = pListStock2.items.get(i).Ubic;
                items.idUbic = pListStock2.items.get(i).idUbic;
                items.Pedido = pListStock2.items.get(i).Pedido;
                items.Pick = pListStock2.items.get(i).Pick;
                items.LicPlate = pListStock2.items.get(i).LicPlate;
                items.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;
                items_stock2.add(items);

            }
            adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock2);
            listView.setAdapter(adapter_stock);

            conteo = items_stock2.size()-1;
            registros.setText("REGISTROS: "+ conteo);

        }
        else{

            vItem = new clsBeVW_stock_res_CI();
            items_stock2.add(vItem);



            for (int i = 0; i < pListStock2.items.size(); i++) {

                String estado_ = pListStock2.items.get(i).Estado;

                if (estado_.equals(estado)) {
                    items = new clsBeVW_stock_res_CI();
                    items.Codigo = pListStock2.items.get(i).Codigo;
                    items.Nombre = pListStock2.items.get(i).Nombre;
                    items.UM = pListStock2.items.get(i).UM;
                    items.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                    items.Pres = pListStock2.items.get(i).Pres;
                    items.ExistPres = pListStock2.items.get(i).ExistPres;
                    items.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                    items.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                    items.Lote = pListStock2.items.get(i).Lote;
                    items.Vence = pListStock2.items.get(i).Vence;
                    items.Estado = pListStock2.items.get(i).Estado;
                    items.Ubic = pListStock2.items.get(i).Ubic;
                    items.idUbic = pListStock2.items.get(i).idUbic;
                    items.Pedido = pListStock2.items.get(i).Pedido;
                    items.Pick = pListStock2.items.get(i).Pick;
                    items.LicPlate = pListStock2.items.get(i).LicPlate;
                    items.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;
                    items_stock2.add(items);
                }
            }
            adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock2);
            listView.setAdapter(adapter_stock);

            conteo = items_stock2.size()-1;
            registros.setText("REGISTROS: "+ conteo);

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


    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {

                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbic.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        //ByVal pLicensePlate As String,ByVal pIdBodega As Integer
                        callMethod("Get_Stock_By_Lic_Plate","pLicensePlate",pLicensePlate,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        //ByVal pCodigo As String, ByVal IdBodega As Integer
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodigo.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;

                    case 4:
                        callMethod("Get_Stock_Por_Producto_Ubicacion_CI","pidProducto",idprod,
                                "pIdUbicacion",idubic, "pIdBodega",gl.IdBodega);
                        break;

                    case 5:
                        callMethod("Get_Stock_Por_Pallet_CI","pLicPlate",pLicensePlate, "pIdBodega",gl.IdBodega);
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
                    processUbicacion();
                    break;
                case 2:
                    processUbicacion2();
                    break;
                case 3:
                    processScanProducto();
                    break;
                case 4:
                    listaStock();
                    break;
                case 5:
                    listaStock2();
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

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //frm_verificacion_datos.super.finish();
                    frm_consulta_stock.super.finish();
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

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir de existencia iventarios");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Exit(View view) {
        msgAskExit("Está seguro de salir");
    }



}
