package com.dts.tom.Transacciones.Picking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.CantReemplazo;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBePickingUbic;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBeProducto;

public class frm_danado_picking extends PBase {

    private Spinner cmbEstadoDanado;
    private TextView lblProdDanado,lblNomUbic;
    private EditText txtUbicDest;
    private Button btnGuardarDanado,btnBack;
    private ProgressDialog progress;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeProducto_estadoList LProductoEstadoDanado = new clsBeProducto_estadoList();
    private clsBeBodega_ubicacion BeUbicDestino = new clsBeBodega_ubicacion();

    private ArrayList<String> EstadoList = new ArrayList<String>();

    public static int IdEstadoDanadoSelect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_danado_picking);
        super.InitBase();

        cmbEstadoDanado = (Spinner)findViewById(R.id.cmbEstadoDanado);
        lblProdDanado = (TextView)findViewById(R.id.lblProdDanado);
        lblNomUbic = (TextView)findViewById(R.id.lblNomUbic);
        txtUbicDest = (EditText)findViewById(R.id.txtUbicDest);
        btnGuardarDanado = (Button)findViewById(R.id.btnGuardarDanado);
        btnBack = (Button)findViewById(R.id.btnBack);

        ws = new WebServiceHandler(frm_danado_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandles();

        execws(1);

    }

    private void setHandles(){

        try{

            cmbEstadoDanado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoDanadoSelect=LProductoEstadoDanado.items.get(position).IdEstado;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            txtUbicDest.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicDest.getText().toString().isEmpty()){
                            BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDest.getText().toString().trim());
                            execws(2);
                        }
                    }

                    return false;
                }
            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    private void Procesa_Ubicacion(){

        try {



        }catch (Exception e){
            mu.msgbox("Procesa_Ubicacion:"+e.getMessage());
        }

    }

    private void Listar_Producto_Estado() {

        try {

            EstadoList.clear();

            LProductoEstadoDanado.items = stream(LProductoEstadoDanado.items).where(c->c.Danado==true).toList();

            for (int i = 0; i < LProductoEstadoDanado.items.size(); i++) {
                EstadoList.add(LProductoEstadoDanado.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDanado.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoDanado.setSelection(0);


        } catch (Exception e) {
            mu.msgbox("Listar_Producto_Estado:" + e.getMessage());
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
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodega","pIdPropietario",gBeProducto.Propietario.IdPropietario,"pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado","IdUbicacion",BeUbicDestino.IdUbicacion,"IdEstado",IdEstadoDanadoSelect,
                                "IdBodega",gl.IdBodega,"pNombreUbicacion",BeUbicDestino.NombreCompleto);
                        return;
                }

            } catch (Exception e) {
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
                    processEstadoProducto();
                    break;
                case 2:
                    processUbicacion();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processEstadoProducto(){

        try{

            LProductoEstadoDanado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodega");

            Listar_Producto_Estado();

            lblProdDanado.setText(gBePickingUbic.CodigoProducto+" "+gBePickingUbic.NombreProducto+
                    "\n Cad: "+gBePickingUbic.Fecha_Vence+
                    "\n Lote: "+gBePickingUbic.Lote+
                    "\n Reemplazar: "+CantReemplazo+" "+gBePickingUbic.ProductoUnidadMedida);

            txtUbicDest.setSelectAllOnFocus(true);
            txtUbicDest.requestFocus();

        }catch (Exception e){
            mu.msgbox("processEstadoProducto:"+e.getMessage());
        }
    }

    private void processUbicacion(){

        try{

            BeUbicDestino.NombreCompleto = xobj.getresultSingle(String.class,"pNombreUbicacion");

        }catch (Exception e){
            mu.msgbox("processUbicacion:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
