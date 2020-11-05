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

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_enc;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHHList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock);

        super.InitBase();

        ws = new WebServiceHandler(frm_consulta_stock.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView) findViewById(R.id.listInventario);
        btnBack = (Button) findViewById(R.id.btnBack);
        txtCodigo = (EditText) findViewById(R.id.txtCodigo1);
        txtUbic = (EditText) findViewById(R.id.txtUbic1);

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

                                if (txtUbic.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()
                                       ){
                                    toast("Debe definir una ubicacion o producto");
                                } else{

                                        execws(1);
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
                                if (txtUbic.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()
                                ) {
                                    toast("Debe definir una ubicacion o producto");
                                } else {
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

        if(txtUbic.getText().toString().equals("") && txtCodigo.getText().toString().equals("")){
            toast("Debe definir una ubicacion o producto");
        }
        else{
            if(txtUbic.getText().toString().equals("")){
                idubic = 0;
            }
            else {
                try {
                    progress.setMessage("Obteniendo existencias");

                    cUbic =xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

                    if (cUbic != null){
                        idubic = cUbic.IdUbicacion;

                    }else{

                        throw new Exception("La ubicación no existe en la bodega: " + cUbic.getIdBodega());
                    }

                } catch (Exception e) {
                    progress.cancel();
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
                }
            }
        }
    }

    private void processUbicacion() {

        try {
                cUbic =xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

                if (cUbic != null){
                    idubic = cUbic.IdUbicacion;
                    toast("ubicación encontrada");
                }else{
                    idubic = 0;
                    throw new Exception("La ubicación no existe en la bodega: " + gl.IdBodega);
                }

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
                idubic = cUbic.IdUbicacion;
                toast("bodega encontrada por LIC_PLATE");
            }else {
                idubic = 0;
                throw new Exception("Pallet no existe en la bodega: " + gl.IdBodega);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

        } /*else {
            //escaneoPallet = false;
            //Llama al método del WS Get_BeProducto_By_Codigo_For_HH
           // execws(3);
            toast("metodo 3");
            execws(3);
        }*/

    private void processUbicacion3(){
        try {
            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null){
                idprod = BeProducto.IdProducto;
                toast("ubicación encontrada por HH");
            }else{
                idubic = 0;
                throw new Exception("La ubicación no existe en la bodega: " + gl.IdBodega);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
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

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
