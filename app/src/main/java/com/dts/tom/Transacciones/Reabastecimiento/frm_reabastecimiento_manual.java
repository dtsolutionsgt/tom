package com.dts.tom.Transacciones.Reabastecimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStockList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.ladapt.list_adapt_reabast_stock_res;
import com.dts.ladapt.list_adapt_stock_res;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res;

import java.util.ArrayList;
import java.util.List;

public class frm_reabastecimiento_manual extends PBase {

    private  frm_reabastecimiento_manual.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private TextView txtMensajeDialog;
    private String mensaje_progress ="";

    private TextView txtCodigoPrd, txtLicencia, txtUbicOrigen;
    private ListView listExist;
    private TextView lblNumReg;

    private int IdUbicacion = 0;
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeVW_stock_resList BeStockList = new clsBeVW_stock_resList();
    private static ArrayList<clsBeVW_stock_res> ListStock = new ArrayList<clsBeVW_stock_res>() ;
    private list_adapt_reabast_stock_res adapter_stock;
    public static clsBeVW_stock_res selitem = new clsBeVW_stock_res();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_reabastecimiento_manual);

        super.InitBase();

        ws = new frm_reabastecimiento_manual.WebServiceHandler(frm_reabastecimiento_manual.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtCodigoPrd = findViewById(R.id.txtCodigoPrd);
        txtLicencia = findViewById(R.id.txtLicencia);
        txtUbicOrigen = findViewById(R.id.txtUbicOrigen);
        listExist = findViewById(R.id.listExist);
        lblNumReg = findViewById(R.id.lblNumReg);

        setHandlers();
    }

    private void setHandlers(){
        try {
            txtCodigoPrd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtCodigoPrd.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        Buscar_Lista_Reabastecimiento();
                    }

                    return false;
                }
            });

            listExist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Object lvObj = listExist.getItemAtPosition(i);
                    selitem = (clsBeVW_stock_res) lvObj;
                    ProcesarRegistro();
                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandlers: "+e.getMessage());
        }
    }

    private void ProcesarRegistro() {
        try {
            txtCodigoPrd.setText("");
            browse = 1;
            startActivity(new Intent(this, frm_datos_reabastecimiento.class));
        } catch (Exception e) {
            mu.msgbox("ProcesarRegistro: "+e.getMessage());
        }
    }

    private void Buscar_Lista_Reabastecimiento(){
        try{
            if (!txtCodigoPrd.getText().toString().isEmpty()) {
                ProgressDialog("Obteniendo producto...");
                execws(1);
            } else {
                ProgressDialog("Listando detalle...");
                execws(2);
            }
        }catch (Exception ex){
            if (progress!=null) progress.cancel();
            mu.msgbox("Error Buscar_Lista_Reabastecimiento : " + ex.getMessage());
        }
    }

    private void processGetProducto() {
        try {

            if (progress!=null) progress.hide();
            ProgressDialog("Obtenido resultado del WS...");

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null) {
                if (BeProducto.IdProducto > 0) {
                    execws(2);
                }
            } else {
                if (progress!=null) progress.cancel();
                mu.msgbox("Código ingresado no válido.");
                txtCodigoPrd.requestFocus();
                txtCodigoPrd.setSelectAllOnFocus(true);
            }

        } catch (Exception e) {
            mu.msgbox("processGetProducto: "+e.getMessage());
        }
    }

    private void processGetStock() {
        try {
            BeStockList = xobj.getresult(clsBeVW_stock_resList.class, "Get_All_Products_For_Reabastecimiento");

            if (BeStockList != null) {
                if (BeStockList.items != null) {
                    ListarStock();
                }
            } else {
                if (progress!=null) progress.cancel();
                toast("No se encontró stock para reabastecer.");
            }
        } catch (Exception e) {
            if (progress!=null) progress.cancel();
            mu.msgbox("processGetStock: "+ e.getMessage());
        } finally {
            if (progress!=null) progress.cancel();
        }
    }

    private void ListarStock() {

        try {
            ListStock.clear();

            for (clsBeVW_stock_res obj : BeStockList.items) {

                if (obj.Fecha_Vence.contains("T")) {
                    obj.Fecha_Vence = du.convierteFechaMostrar(obj.Fecha_Vence);
                }

                ListStock.add(obj);
            }

            adapter_stock = new list_adapt_reabast_stock_res(getApplicationContext(), ListStock);
            listExist.setAdapter(adapter_stock);

            lblNumReg.setText("Reg: " + ListStock.size());

            if (progress!=null) progress.cancel();

        } catch (Exception e) {
            if (progress!=null) progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
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
                       callMethod("Get_BeProducto_By_Codigo_For_HH",
                                         "pCodigo", txtCodigoPrd.getText().toString(),
                                         "IdBodega", gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_All_Products_For_Reabastecimiento",
                                               "pIdProducto", BeProducto.IdProducto,
                                               "pIdBodega", gl.IdBodega);
                        break;
                }

            } catch (Exception e) {
                error=e.getMessage();errorflag =true;
                if (progress!=null) progress.cancel();
                msgbox(error);
            }
        }
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processGetProducto();
                    break;
                case 2:
                    processGetStock();
                    break;
            }

        } catch (Exception e) {
            if (progress!=null) progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void Regresar(View view) {
        super.finish();
    }

    public void Buscar(View view) {
        Buscar_Lista_Reabastecimiento();
    }

    public void ProgressDialog(String mensaje) {
        try {
            progress = new ProgressDialog(this);
            progress.setMessage(mensaje);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;

                if (!txtCodigoPrd.getText().toString().isEmpty()) {
                    ProgressDialog("Listando detalle...");
                    execws(1);
                } else {
                    ProgressDialog("Listando detalle...");
                    execws(2);
                }
            }

        }catch (Exception e) {
            if (progress!=null) progress.cancel();
            mu.msgbox("OnResume" + e.getMessage());
        }

    }

}