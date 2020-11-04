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
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
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
    private String pLicensePlate;

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

                                if (txtUbic.getText().toString().isEmpty()
                                       ){
                                    toast("Debe definir una ubicacion o producto");
                                } else{

                                    processUbicacion();
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

                               // Listar_Existencias();

                               /* if (txtCodigo.getText().toString().equals("")){
                                    toast("Debe definir un producto");
                                } else{
                                    String vStarWithParameter = "$";
                                    //Comentario: La barra de pallet puede comenzar con $ y no con (01)
                                    if (txtCodigo.getText().toString().startsWith("$") ||
                                            txtCodigo.getText().toString().startsWith("(01)") ||
                                            txtCodigo.getText().toString().startsWith(vStarWithParameter)) {

                                        //Es una barra de pallet válida por tamaño
                                        int vLengthBarra = txtCodigo.getText().toString().length();

                                        if (vLengthBarra >= 16) {

                                            pLicensePlate = txtCodigo.getText().toString().replace("$", "");

                                            //Llama al método del WS Get_Stock_By_Lic_Plate
                                            execws(2);

                                        }

                                    } else {
                                        //escaneoPallet = false;

                                        //Llama al método del WS Get_BeProducto_By_Codigo_For_HH
                                        execws(3);
                                    }

                                    processUbicacion();
                                }*/
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

            //progress.setMessage("Obteniendo existencias");

            cUbic =xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (cUbic != null){
                idubic = cUbic.IdUbicacion;

            }else{

                //progress.cancel();
                throw new Exception("La ubicación no existe en la bodega: " + cUbic.getIdBodega());
            }

        } catch (Exception e) {
            //progress.cancel();
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbic.getText(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        //ByVal pLicensePlate As String,ByVal pIdBodega As Integer
                        callMethod("Get_Stock_By_Lic_Plate","pLicensePlate",txtUbic,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        //ByVal pCodigo As String, ByVal IdBodega As Integer
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodigo.getText(),
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

            switch (ws.callback) {
                case 1:
                    processUbicacion();
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

}
