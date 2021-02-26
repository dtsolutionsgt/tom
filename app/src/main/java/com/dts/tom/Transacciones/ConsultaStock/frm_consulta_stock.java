package com.dts.tom.Transacciones.ConsultaStock;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_consulta_stock extends PBase {

    private frm_consulta_stock.WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private ListView listView;
    private Button btnBack;
    private EditText txtUbicacion, txtProducto;
    private Spinner cmbEstadoExist;

    private clsBeBodega_ubicacion cUbic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock);

        super.InitBase();

        ws = new WebServiceHandler(frm_consulta_stock.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView) findViewById(R.id.listInventario);
        btnBack = (Button) findViewById(R.id.btnBack);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtProducto = (EditText) findViewById(R.id.txtProducto);
        cmbEstadoExist = (Spinner) findViewById(R.id.cmbEstadoExist);

    }

    public void BacKList(View view) {
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra",gl.OperadorBodega.IdOperador,
                                "pIdBodega",gl.IdBodega);
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
                    processUbicOrigen();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processUbicOrigen(){
        int idUbic;
        int idProd;

        try {

            progress.setMessage("Validando ubicación");
            progress.show();

            if (txtUbicacion.getText().toString().isEmpty()){
                idUbic = 0;
            }else{

                cUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

                if (cUbic == null) {
                    msgbox("La ubicación no existe en la bodega: " + gl.CodigoBodega);
                    return;
                }else{
                    idUbic = cUbic.IdUbicacion;
                }
            }

            if (txtProducto.getText().toString().isEmpty()){
                idProd = 0;
            }else{

                String vStarWithParameter = "$";
                if (txtProducto.getText().toString().startsWith("$") |
                    txtProducto.getText().toString().startsWith("(01)") |
                    txtProducto.getText().toString().startsWith(vStarWithParameter)){

                }
                cUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

                if (cUbic == null) {
                    msgbox("La ubicación no existe en la bodega: " + gl.CodigoBodega);
                    return;
                }else{
                    idUbic = cUbic.IdUbicacion;
                }
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }


}
