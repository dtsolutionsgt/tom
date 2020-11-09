package com.dts.tom.Transacciones.ConsultaStock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.tom.PBase;
import com.dts.tom.R;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collections;


import static br.com.zbra.androidlinq.Linq.stream;
import static java.util.stream.Collectors.toSet;

public class frm_consulta_stock extends PBase {

    private frm_consulta_stock.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;


    private ListView listView;
    private Button btnBack, registros;
    private int pIdTarea=0;
    private EditText txtCodigo, txtUbic;
    private int idubic, idprod;
    private clsBeBodega_ubicacion cUbic;
    private clsBeProductoList ListBeStockPallet;
    private String pLicensePlate;
    private clsBeProducto BeProducto;
    private boolean Escaneo_Pallet;
    private clsBeVW_stock_res pListStock;
    private clsBeVW_stock_resList pListStock2;
    private TextView lblNombreUbicacion;
    private TextView lblNombreProducto;
    private Boolean idle = false;
    private Integer selest  = 0;
    private list_adapt_consulta_stock adapter_stock;
    private ArrayList<clsBeVW_stock_res> items_stock = new ArrayList<clsBeVW_stock_res>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock);

        super.InitBase();

        ws = new WebServiceHandler(frm_consulta_stock.this, gl.wsurl);
        xobj = new XMLObject(ws);
        Escaneo_Pallet = false;
        idle = false;

        listView = (ListView) findViewById(R.id.listExist);
        btnBack = (Button) findViewById(R.id.btnBack);
        registros = findViewById(R.id.btnRegs2);
        txtCodigo = (EditText) findViewById(R.id.txtCodigo1);
        txtUbic = (EditText) findViewById(R.id.txtUbic1);
        lblNombreUbicacion = findViewById(R.id.lblNombreUbicacion);
        lblNombreProducto = findViewById(R.id.lblNombreProducto);
        setHandlers();
    }

    private void setHandlers() {
        try{

            txtUbic.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lblNombreUbicacion.setText("");
                }
            });

            txtCodigo.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {}
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lblNombreProducto.setText("");
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

                                //lbldescripcion.setTextColor(Color.WHITE);
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
                                                execws(2);
                                            }
                                        } else {
                                            execws(3);
                                        }
                                    }
                                }
                        }
                    }
                    return false;
                }
            });

        }
        catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

//    public void BacKList(View view) {
//        finish();
//    }

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
                toast("Pallet si existe en la bodega");
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
                toast("ubicación encontrada por HH");
            }else{
                throw new Exception("El producto no existe en la bodega: " + gl.IdBodega);
            }

            Listar_Existencias();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }


    private void listaStock() {
        try {

            items_stock.clear();
            clsBeVW_stock_res item;

            //pListStock = xobj.getresult(clsBeVW_stock_res.class,"Get_Stock_Por_Producto_Ubicacion");
            pListStock2= xobj.getresult(clsBeVW_stock_resList.class,"Get_Stock_Por_Producto_Ubicacion");
            int conteo = pListStock2.items.size();

            if(conteo == 0 || pListStock2.items.isEmpty()){

                lblNombreUbicacion.setText("U.S.P");
                idle = true;
            }
            else{

               // lbldescripcion.setText("");
            }

            //List AuxList;

            if(selest > 0){
                        /* AuxList = stream(pListStock2.items)
                        .where(c ->c.IdProductoEstado == selest)
                        .orderBy(c ->c.Nombre_Producto)
                        .toList();*/

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                {
                    Map<String, Map<String, Map<String, Map<String, Map<String, Map<String,Map<String, Map<Integer,Map<Integer, Map<Integer, Map<String, Map<Integer, List<clsBeVW_stock_res>>>>>>>>>>>>> employessGroup=
                            null;

                    employessGroup = pListStock2.items.stream().filter(c ->c.IdProductoEstado == selest).collect(Collectors.groupingBy(clsBeVW_stock_res::getCodigo_Producto,
                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Producto,
                                    Collectors.groupingBy(clsBeVW_stock_res::getUMBas,
                                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Presentacion,
                                                    Collectors.groupingBy(clsBeVW_stock_res::getLote,
                                                            Collectors.groupingBy(clsBeVW_stock_res::getFecha_Vence,
                                                                    Collectors.groupingBy(clsBeVW_stock_res::getNomEstado,
                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdUbicacion,
                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getIdPedido,
                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdPicking,
                                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getLic_plate,
                                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdProductoBodega
                                                                                                            )))))))))))));

                    if (employessGroup != null){

                        int count =employessGroup.size();
                        registros.setText("Regs: "+count);
                    }
                    else{
                        registros.setText("Regs: "+ 0);
                    }
                }

            }
            else {
                        /* AuxList = stream(pListStock2.items)
                        .where(c ->c.IdProductoEstado > 0)
                        .orderBy(c ->c.Nombre_Producto)
                        .toList();*/

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                {
                    Map<String, Map<String, Map<String, Map<String, Map<String, Map<String,Map<String, Map<Integer,Map<Integer, Map<Integer, Map<String, Map<Integer, List<clsBeVW_stock_res>>>>>>>>>>>>> employessGroup=
                            null;

                    employessGroup = pListStock2.items.stream().filter(c ->c.IdProductoEstado>0).collect(Collectors.groupingBy(clsBeVW_stock_res::getCodigo_Producto,
                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Producto,
                                    Collectors.groupingBy(clsBeVW_stock_res::getUMBas,
                                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Presentacion,
                                                    Collectors.groupingBy(clsBeVW_stock_res::getLote,
                                                            Collectors.groupingBy(clsBeVW_stock_res::getFecha_Vence,
                                                                    Collectors.groupingBy(clsBeVW_stock_res::getNomEstado,
                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdUbicacion,
                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getIdPedido,
                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdPicking,
                                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getLic_plate,
                                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdProductoBodega
                                                                                                            )))))))))))));

                    if (employessGroup != null){

                        item = new clsBeVW_stock_res();

                        //item.IdProductoEstado = employessGroup.values("codigo");
                        //item.Nombre_Producto = employessGroup.values("key");

                        int count =employessGroup.size();
                        registros.setText("Regs: "+count);
                    }
                    else{
                        registros.setText("Regs: "+ 0);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listaStock2() {
        try {
            //pListStock = xobj.getresult(clsBeVW_stock_res.class,"Get_Stock_Por_Pallet");
            pListStock2 = xobj.getresult(clsBeVW_stock_resList.class,"Get_Stock_Por_Pallet");
            int conteo = pListStock2.items.size();

            if(conteo == 0 || pListStock2.items.isEmpty()){

                lblNombreUbicacion.setText("U.S.P");
                idle = true;
            }
            else{

                //lbldescripcion.setText("");
            }

            List AuxList;

            if(selest > 0){
                /*AuxList = stream(pListStock2.items)
                        .where(c ->c.IdProductoEstado == selest)
                        .orderBy(c ->c.Nombre_Producto)
                        .toList();*/

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                {
                    Map<String, Map<String, Map<String, Map<String, Map<String, Map<String,Map<String, Map<Integer,Map<Integer, Map<Integer, Map<String, Map<Integer, List<clsBeVW_stock_res>>>>>>>>>>>>> employessGroup=
                            null;

                    employessGroup = pListStock2.items.stream().filter(c ->c.IdProductoEstado == selest).collect(Collectors.groupingBy(clsBeVW_stock_res::getCodigo_Producto,
                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Producto,
                                    Collectors.groupingBy(clsBeVW_stock_res::getUMBas,
                                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Presentacion,
                                                    Collectors.groupingBy(clsBeVW_stock_res::getLote,
                                                            Collectors.groupingBy(clsBeVW_stock_res::getFecha_Vence,
                                                                    Collectors.groupingBy(clsBeVW_stock_res::getNomEstado,
                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdUbicacion,
                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getIdPedido,
                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdPicking,
                                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getLic_plate,
                                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdProductoBodega
                                                                                                            )))))))))))));

                    if (employessGroup != null){

                        int count =employessGroup.size();
                        registros.setText("Regs: "+count);
                    }
                    else{
                        registros.setText("Regs: "+ 0);
                    }
                }


            }
            else {
                /*AuxList = stream(pListStock2.items)
                        .where(c ->c.IdProductoEstado > 0)
                        .orderBy(c ->c.Nombre_Producto)
                        .toList();*/

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                {
                    Map<String, Map<String, Map<String, Map<String, Map<String, Map<String,Map<String, Map<Integer,Map<Integer, Map<Integer, Map<String, Map<Integer, List<clsBeVW_stock_res>>>>>>>>>>>>> employessGroup=
                            null;

                    employessGroup = pListStock2.items.stream().filter(c ->c.IdProductoEstado>0).collect(Collectors.groupingBy(clsBeVW_stock_res::getCodigo_Producto,
                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Producto,
                                    Collectors.groupingBy(clsBeVW_stock_res::getUMBas,
                                            Collectors.groupingBy(clsBeVW_stock_res::getNombre_Presentacion,
                                                    Collectors.groupingBy(clsBeVW_stock_res::getLote,
                                                            Collectors.groupingBy(clsBeVW_stock_res::getFecha_Vence,
                                                                    Collectors.groupingBy(clsBeVW_stock_res::getNomEstado,
                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdUbicacion,
                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getIdPedido,
                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdPicking,
                                                                                                    Collectors.groupingBy(clsBeVW_stock_res::getLic_plate,
                                                                                                            Collectors.groupingBy(clsBeVW_stock_res::getIdProductoBodega
                                                                                                            )))))))))))));

                    if (employessGroup != null){

                        int count =employessGroup.size()-1;
                        registros.setText("Regs: "+count);
                    }
                    else{
                        registros.setText("Regs: "+ 0);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
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
                        callMethod("Get_Stock_Por_Producto_Ubicacion","pidProducto",idprod,
                                "pIdUbicacion",idubic, "pIdBodega",gl.IdBodega);
                        break;

                    case 5:
                        callMethod("Get_Stock_Por_Pallet","pLicPlate",pLicensePlate, "pIdBodega",gl.IdBodega);
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
            if (ws.callback==1) processUbicacion();
            if (ws.callback==2) processUbicacion2();
            if (ws.callback==3) processScanProducto();
            if (ws.callback==4) listaStock();
            if (ws.callback==5) listaStock2();

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

}
