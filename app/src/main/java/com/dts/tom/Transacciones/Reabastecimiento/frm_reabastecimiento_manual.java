package com.dts.tom.Transacciones.Reabastecimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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

    private TextView txtCodigoPrd, txtLicencia, txtUbicOrigen;
    private ListView listExist;

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
                        if (!txtCodigoPrd.getText().toString().isEmpty()) {
                            execws(1);
                        } else {
                            execws(2);
                        }
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
            startActivity(new Intent(this, frm_datos_reabastecimiento.class));
        } catch (Exception e) {
            mu.msgbox("ProcesarRegistro: "+e.getMessage());
        }
    }

    private void GetProductos() {
        try {
            execws(2);
        } catch (Exception e) {
            mu.msgbox("GetProductos: "+e.getMessage());
        }
    }

    private void processGetProducto() {
        try {
            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null) {
                if (BeProducto.IdProducto > 0) {
                    execws(2);
                }
            } else {
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
                    ListStock.clear();

                    for (clsBeVW_stock_res  obj: BeStockList.items) {

                        if (obj.Fecha_Vence.contains("T")) {
                            obj.Fecha_Vence = du.convierteFechaMostrar(obj.Fecha_Vence);
                        }

                        ListStock.add(obj);
                    }

                    adapter_stock = new list_adapt_reabast_stock_res(getApplicationContext(),ListStock);
                    listExist.setAdapter(adapter_stock);
                }
            }
        } catch (Exception e) {
            mu.msgbox("processGetStock: "+ e.getMessage());
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

                        if (!txtUbicOrigen.getText().toString().isEmpty()) {
                            IdUbicacion = Integer.valueOf(txtUbicOrigen.getText().toString());
                        }

                        callMethod("Get_All_Products_For_Reabastecimiento",
                                         "pIdUbicacion", IdUbicacion,
                                               "pIdProducto", BeProducto.IdProducto,
                                               "pIdBodega", gl.IdBodega);
                        break;
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
                    processGetProducto();
                    break;
                case 2:
                    processGetStock();
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

    public void Regresar(View view) {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}