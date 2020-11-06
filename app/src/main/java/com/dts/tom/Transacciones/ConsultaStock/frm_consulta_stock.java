package com.dts.tom.Transacciones.ConsultaStock;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStockList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_consulta_stock extends PBase {

    private frm_consulta_stock.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;


    private ListView listView;
    private Button btnBack;
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
    private TextView lbldescripcion;
    private Boolean idle = false;
    private Integer selest  = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock);

        super.InitBase();

        ws = new WebServiceHandler(frm_consulta_stock.this, gl.wsurl);
        xobj = new XMLObject(ws);
        Escaneo_Pallet = false;
        idle = false;

        listView = (ListView) findViewById(R.id.listInventario);
        btnBack = (Button) findViewById(R.id.btnBack);
        txtCodigo = (EditText) findViewById(R.id.txtCodigo1);
        txtUbic = (EditText) findViewById(R.id.txtUbic1);
        lbldescripcion = findViewById(R.id.lblDescripcion);

        setHandlers();
    }

    private void setHandlers() {
        try{
            txtUbic.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                //lbldescripcion.setTextColor(Color.WHITE);
                                lbldescripcion.setText("");

                                if (txtUbic.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()
                                       ){
                                    toast("Debe definir una ubicacion o producto");
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
                                lbldescripcion.setText("");

                                if (txtUbic.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()
                                ) {
                                    toast("Debe definir una ubicacion o producto");
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

    public void BacKList(View view) {
        super.finish();
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

                    lbldescripcion.setTextColor(Color.BLUE);
                    lbldescripcion.setText(cUbic.getDescripcion());

                    //toast("ubicación encontrada " +  cUbic.getDescripcion());
                }else{
                    idubic = 0;
                    lbldescripcion.setTextColor(Color.RED);
                    lbldescripcion.setText("Ubicación no existe en la bodega" + gl.IdBodega);

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

    private void processUbicacion3(){
        idubic = 0;
        try {
            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null){
                idprod = BeProducto.IdProducto;
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
            //pListStock = xobj.getresult(clsBeVW_stock_res.class,"Get_Stock_Por_Producto_Ubicacion");
            pListStock2= xobj.getresult(clsBeVW_stock_resList.class,"Get_Stock_Por_Producto_Ubicacion");
            //toast("stock por producto/ubicacion");

            int conteo = pListStock2.items.size();

            if(conteo == 0 || pListStock2.items.isEmpty()){

                lbldescripcion.setText("U.S.P");
                idle = true;
            }
            else{

               // lbldescripcion.setText("");
            }

            /*If (pListStock.Count = 0) Or (pListStock Is Nothing) Then
            If pListStock.Count = 0 Then
            lblMensaje.ForeColor = Color.Firebrick
            lblMensaje.Text = "U.S.P"
            End If
            DefCur() : idle = True : Return
                    Else
            lblMensaje.ForeColor = Color.SteelBlue
            lblMensaje.Text = ""
            End If*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listaStock2() {
        try {
            //pListStock = xobj.getresult(clsBeVW_stock_res.class,"Get_Stock_Por_Pallet");
            pListStock2 = xobj.getresult(clsBeVW_stock_resList.class,"Get_Stock_Por_Pallet");
            //toast("stock por pallet");

            int conteo = pListStock2.items.size();

            if(conteo == 0 || pListStock2.items.isEmpty()){

                lbldescripcion.setText("U.S.P");
                idle = true;
            }
            else{

                //lbldescripcion.setText("");
            }

            List AuxList;

            if(selest > 0){
                AuxList = stream(pListStock2.items)
                        .where(c ->c.IdProductoEstado == selest)
                        .orderBy(c ->c.Nombre_Producto)
                        .groupBy(c ->c.Codigo_Producto)
                        .toList();
            }
            else {
                AuxList = stream(pListStock2.items)
                        .where(c ->c.IdProductoEstado > 0)
                        .orderBy(c ->c.Nombre_Producto)
                        .groupBy(c ->c.Codigo_Producto)
                        .toList();
            }




/*            AuxList = stream(stockResList.items)
                    .where(c -> c.IdProducto == cvProdID)
                    .where(c -> c.getIdPresentacion() == cvPresID)
                    .toList();*/



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
            if (ws.callback==3) processUbicacion3();
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

}
