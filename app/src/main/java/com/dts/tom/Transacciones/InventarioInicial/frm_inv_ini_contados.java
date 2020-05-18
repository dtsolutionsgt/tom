package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.Inventario_Detalle.clsBeTrans_inv_detalle_gridList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;

public class frm_inv_ini_contados extends PBase {

    private EditText txtCodProd,txtUbicConts;
    private TextView lblPrdCont;
    private ListView listView;
    private Button btnRegs;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeTrans_inv_detalle_gridList BeListDet = new clsBeTrans_inv_detalle_gridList();
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeBodega_ubicacion BeUbica = new  clsBeBodega_ubicacion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_contados);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_contados.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView)findViewById(R.id.listConts);

        txtCodProd = (EditText)findViewById(R.id.txtCodProd);
        txtUbicConts = (EditText)findViewById(R.id.txtUbicConts);

        lblPrdCont = (TextView)findViewById(R.id.lblPrdCont);

    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent, Url);
        }

        @Override
        public void wsExecute() {

            try {

                switch (ws.callback) {

                    case 1:
                        callMethod("Get_Lista_Conteo_Inventario_Inicial_By_IdInventarioEnc",
                                "pIdInventarioEnc", BeInvEnc.Idinventarioenc, "pIdtramo", BeInvTramo.Idtramo,
                                "pIdProducto",BeProducto.IdProducto,"pIdUbic",BeUbica.IdUbicacion);
                        break;
                }

            } catch (Exception e) {
                mu.msgbox(e.getClass() + "WebServiceHandler:" + e.getMessage());
            }

        }

    }

    @Override
    public void wsCallBack(Boolean throwing, String errmsg, int errlevel) {

        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {

                case 1:
                    //processBeTramo();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
