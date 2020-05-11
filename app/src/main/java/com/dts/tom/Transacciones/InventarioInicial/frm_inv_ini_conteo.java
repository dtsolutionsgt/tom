package com.dts.tom.Transacciones.InventarioInicial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeInvTramo;
import static com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos.BeUbic;

public class frm_inv_ini_conteo extends PBase {

    private EditText txtUbicInv,txtCodBarra,txtLoteInvIni,txtVenceInvIni,txtCantInvIni,txtPesoInvIni;
    private TextView lblUbicDesc,lblDescProd,lblUnidadInv,lblLote,lblPeso,lblTituloForma;
    private Button btnGuardarConteo,btnCompletar,btnBack;
    private Spinner cmbPresInvIni,cmbEstadoInvIni;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private  boolean verlote,vervence;

    private clsBeTrans_inv_tramo utramo = new clsBeTrans_inv_tramo();
    private clsBeProducto BeProducto= new clsBeProducto();
    private clsBeProducto_PresentacionList BeListPres = new clsBeProducto_PresentacionList();

    private ArrayList<String> EstadoList= new ArrayList<String>();
    private ArrayList<String> PresList= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_ini_conteo);
        super.InitBase();

        ws = new WebServiceHandler(frm_inv_ini_conteo.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtUbicInv = (EditText)findViewById(R.id.txtUbicInv);
        txtCodBarra = (EditText)findViewById(R.id.txtCodBarra);
        txtLoteInvIni = (EditText)findViewById(R.id.txtLoteInvIni);
        txtVenceInvIni = (EditText)findViewById(R.id.txtVenceInvIni);
        txtCantInvIni = (EditText)findViewById(R.id.txtCantInvIni);
        txtPesoInvIni = (EditText)findViewById(R.id.txtPesoInvIni);

        lblUbicDesc = (TextView)findViewById(R.id.lblUbicDesc);
        lblDescProd = (TextView)findViewById(R.id.lblDescProd);
        lblUnidadInv = (TextView)findViewById(R.id.lblUnidadInv);
        lblLote = (TextView)findViewById(R.id.textView35);
        lblPeso = (TextView)findViewById(R.id.textView38);
        lblTituloForma = (TextView)findViewById(R.id.lblTituloForma);

        cmbPresInvIni = (Spinner)findViewById(R.id.cmbPresInvIni);
        cmbEstadoInvIni = (Spinner)findViewById(R.id.cmbEstadoInvIni);

        btnGuardarConteo = (Button)findViewById(R.id.btnGuardarConteo);
        btnCompletar = (Button)findViewById(R.id.btnCompletar);
        btnBack = (Button)findViewById(R.id.btnBack);

        setHandles();

        Load();

    }

    private void setHandles(){

        try{

            txtUbicInv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        if (!txtUbicInv.getText().toString().isEmpty()){
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

    private void Load(){

        try{

            txtUbicInv.setText("");
            txtCodBarra.setText("");
            txtLoteInvIni.setText("");
            txtVenceInvIni.setText("");
            txtCantInvIni.setText("");
            txtPesoInvIni.setText("");

            lblUbicDesc.setText("");
            lblDescProd.setText("");
            lblUnidadInv.setText("");

            txtLoteInvIni.setVisibility(View.GONE);
            lblLote.setVisibility(View.GONE);

            lblPeso.setVisibility(View.GONE);
            txtPesoInvIni.setVisibility(View.GONE);

            txtVenceInvIni.setText(du.convierteFechaMostar(du.getFechaActual()));

            if (BeUbic.IdUbicacion>0){
                txtUbicInv.setText(""+BeUbic.IdTramo);
                lblUbicDesc.setText(BeUbic.NombreCompleto);
            }

            lblTituloForma.setText(BeUbic.Tramo.Descripcion);

            execws(1);

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Procesa_Ubicacion(){

        try{

            if (BeUbic.Tramo.IdTramo!=0){
                if (BeInvTramo.Idtramo==BeUbic.Tramo.IdTramo){
                    mu.msgbox("La ubicación no partenece al tramo: "+BeUbic.Tramo.Descripcion);
                }
            }

            if (BeUbic.Nivel>1){

            }

            lblUbicDesc.setText(""+BeUbic.NombreCompleto);

            txtCodBarra.setSelectAllOnFocus(true);
            txtCodBarra.requestFocus();
            txtCodBarra.selectAll();

        }catch (Exception e){
            mu.msgbox("Procesa_Ubicacion");
        }
    }

    private void Carga_Det_Producto(){

        try{

            lblDescProd.setText(BeProducto.Nombre);

            lblUbicDesc.setText(BeProducto.UnidadMedida.Nombre);

            execws(4);

        }catch (Exception e){
            mu.msgbox("Carga_Det_Producto:"+e.getMessage());
        }
    }

    private void Llena_Det_Presentacion_Producto(){

        try{

            PresList.clear();

            for (clsBeProducto_Presentacion BePres: BeListPres.items){
                PresList.add(BePres.Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PresList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPresInvIni.setAdapter(dataAdapter);

            if (PresList.size()>0) cmbPresInvIni.setSelection(0);

        }catch (Exception e){
            mu.msgbox("llenaDetPresentacionProducto:"+e.getMessage());
        }
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){

            try{

                switch (ws.callback) {

                    case 1:
                       callMethod("Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo",
                               "pidinventario",BeInvEnc.Idinventarioenc,"pidtramo",BeInvTramo.Idtramo);
                        break;
                    case 2:
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra",txtUbicInv.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodBarra.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;
                    case 4:
                        callMethod("Get_All_Presentaciones_By_IdProducto","pIdProducto",BeProducto.IdProducto,"pActivo",true);
                        break;
                    case 5:
                        callMethod("");
                        break;
                }

            }catch (Exception e){
                mu.msgbox(e.getClass()+"WebServiceHandler:"+e.getMessage());
            }

        }

    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {

        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {

                case 1:
                    processBeTramo();
                    break;
                case 2:
                    processUbic();
                    break;
                case 3:
                    processProducto();
                    break;
                case 4:
                    processPresentacion();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processBeTramo(){

        try{

            utramo = xobj.getresult(clsBeTrans_inv_tramo.class,"Get_Inventario_Inicial_By_IdInventarioEnc_And_IdTramo");

            txtUbicInv.setSelectAllOnFocus(true);
            txtUbicInv.requestFocus();

        }catch (Exception e){
            mu.msgbox("processBeTramo:"+e.getMessage());
        }
    }

    private void processUbic(){

        try{

            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic !=null){
                Procesa_Ubicacion();
            }else{
                mu.msgbox("La ubicación no existe");
            }

        }catch (Exception e){
            mu.msgbox("processUbic:"+e.getMessage());
        }
    }

    private void processProducto(){

        try{

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto!=null){

                Carga_Det_Producto();

            }else{

            }

        }catch (Exception e){
            mu.msgbox("processProducto:"+e.getMessage());
        }
    }

    private void processPresentacion(){

        try{

            BeListPres = xobj.getresult(clsBeProducto_PresentacionList.class,"Get_All_Presentaciones_By_IdProducto");

            if (BeListPres!=null){
                if (BeListPres.items!=null){
                    Llena_Det_Presentacion_Producto();
                }
            }

            execws(5);

        }catch (Exception e){
            mu.msgbox("processPresentacion:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
