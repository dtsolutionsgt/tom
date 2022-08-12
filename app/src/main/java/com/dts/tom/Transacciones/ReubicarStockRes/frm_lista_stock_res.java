package com.dts.tom.Transacciones.ReubicarStockRes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_resList;
import com.dts.ladapt.Recepcion.list_adapt_detalle_recepcion3;
import com.dts.ladapt.list_adapt_detalle_rec_prod;
import com.dts.ladapt.list_adapt_stock_res;
import com.dts.tom.PBase;
import com.dts.tom.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class frm_lista_stock_res extends PBase {

    private frm_lista_stock_res.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtCodigo, txtUbicacion;
    private TextView lblRegs;
    private ListView listStockRes;

    private clsBeVW_stock_resList detStockRes = new clsBeVW_stock_resList();
    private static ArrayList<clsBeVW_stock_res> BeListStockRes= new ArrayList<clsBeVW_stock_res>() ;
    public static clsBeVW_stock_res selitem = new clsBeVW_stock_res();
    private list_adapt_stock_res adapter;
    private String ubicacion, codigo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_stock_res);

        super.InitBase();

        ws = new frm_lista_stock_res.WebServiceHandler(frm_lista_stock_res.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtCodigo = findViewById(R.id.txtCodigo);
        txtUbicacion = findViewById(R.id.txtUbicacion);
        lblRegs = findViewById(R.id.lblRegs);
        listStockRes = findViewById(R.id.listStockRes);

        ProgressDialog("Cargando pantalla...");
        Load();
        setHandlers();

    }

    private void setHandlers() {
        try {
            txtCodigo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { }
            });

            txtCodigo.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    if (ValidaCampos()) {
                                        ProgressDialog("Listando detalle...");
                                        execws(1);
                                    } else {
                                        mu.msgbox("Ingrese ubicación o código válidos.");
                                    }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtUbicacion.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    if (ValidaCampos()) {
                                        ProgressDialog("Listando detalle...");
                                        execws(1);
                                    } else {
                                        mu.msgbox("Ingrese ubicación o código válidos.");
                                    }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            listStockRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Object lvObj = listStockRes.getItemAtPosition(i);
                    selitem = (clsBeVW_stock_res) lvObj;

                    ProcesarRegistro();
                }
            });
        } catch (Exception e) {
            mu.msgbox("setHandlers: "+e.getMessage());
        }
    }

    private void Load() {
        try {
            execws(1);
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() +" - "+ e.getMessage());
        }
    }

    private boolean ValidaCampos() {
        boolean permite = false;

        try {
            ubicacion = txtUbicacion.getText().toString();
            codigo = txtCodigo.getText().toString();

            if (!ubicacion.isEmpty() || !codigo.isEmpty()) {
                permite = true;
            } else {
                if (ubicacion.isEmpty() && codigo.isEmpty()) {
                    txtCodigo.requestFocus();
                }
            }
        } catch (Exception e) {
            mu.msgbox("ValidaCampos: "+e.getMessage());
        }

        return permite;
    }
    
    private void ProcesarRegistro() {
        try {
            browse = 1;
            startActivity(new Intent(this, frm_datos_stock_res.class));
        } catch (Exception e) {
            mu.msgbox("ProcesarRegistro: "+e.getMessage());
        }
    }
    private void processGetStockRes() {
        try {

            detStockRes = xobj.getresult(clsBeVW_stock_resList.class, "Get_Stock_Res_By_Codigo_And_IdUbicacion");

            if (detStockRes != null) {
                if (detStockRes.items != null) {

                    BeListStockRes.clear();

                    for (clsBeVW_stock_res obj : detStockRes.items) {

                        if (obj.Fecha_Vence.contains("T")) {
                            obj.Fecha_Vence = du.convierteFechaMostrar(obj.Fecha_Vence);
                        }

                        if (obj.Fecha_Preparacion.contains("T")) {
                            obj.Fecha_Preparacion = du.convierteFechaMostrar(obj.Fecha_Preparacion);
                        }

                        if (obj.Fecha_Pedido.contains("T")) {
                            obj.Fecha_Pedido = du.convierteFechaMostrar(obj.Fecha_Pedido);
                        }

                        BeListStockRes.add(obj);
                    }

                    lblRegs.setText("REGS: "+detStockRes.items.size());
                    adapter = new list_adapt_stock_res(this, BeListStockRes);
                    listStockRes.setAdapter(adapter);

                } else {
                    toast("No se encontró stock reservado");
                    listStockRes.setAdapter(null);
                    lblRegs.setText("REGS: 0");
                }
            } else {
                toast("No se encontró stock reservado");
                listStockRes.setAdapter(null);
                lblRegs.setText("REGS: 0");
            }

        } catch (Exception e) {
            mu.msgbox("processGetStockRes: "+e.getMessage());
        } finally {
            progress.cancel();
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
                        int IdUbic = 0;

                        if (!txtUbicacion.getText().toString().isEmpty()) {
                            IdUbic = Integer.valueOf(txtUbicacion.getText().toString());
                        }

                        callMethod("Get_Stock_Res_By_Codigo_And_IdUbicacion",
                                         "pIdUbicacion", IdUbic,
                                               "pCodigo", txtCodigo.getText().toString(),
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
                    processGetStockRes();
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
    protected void onResume() {
        super.onResume();

        try {
            if (browse == 1) {
                browse = 0;
                execws(1);
            }
        } catch (Exception e) {
            mu.msgbox("OnResume" + e.getMessage());
        } finally {
            progress.cancel();
        }
    }
}