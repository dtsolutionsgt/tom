package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_verificacion_datos extends PBase {

    private frm_verificacion_datos.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private EditText txtVenceVeri,txtCantVeri,txtPesoVeri, txtUmbasVeri, txtLote;
    private Button btMarcarReemplazoVeri,btnConfirmarV,btnBack;
    private Spinner cmbPresVeri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_verificacion_datos);

        super.InitBase();

        ws = new frm_verificacion_datos.WebServiceHandler(frm_verificacion_datos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtVenceVeri = (EditText) findViewById(R.id.txtVenceVeri);
        txtCantVeri = (EditText) findViewById(R.id.txtCantVeri);
        txtPesoVeri = (EditText) findViewById(R.id.txtPesoVeri);
        txtUmbasVeri = (EditText) findViewById(R.id.txtUmbasVeri);
        txtLote = (EditText) findViewById(R.id.txtLote);
        btMarcarReemplazoVeri = (Button) findViewById(R.id.btMarcarReemplazoVeri);
        btnConfirmarV = (Button) findViewById(R.id.btnConfirmarV);
        btnBack = (Button) findViewById(R.id.btnBack);
        cmbPresVeri = (Spinner) findViewById(R.id.cmbPresVeri);

        setHandlers();

        ProgressDialog("Cargando forma...");

    }


    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void setHandlers(){

        try{

            txtVenceVeri.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                return true;
                        }
                    }
                    return false;
                }
            });


            txtCantVeri.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                return true;
                        }
                    }
                    return false;
                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Detalle_By_IdPedidoEnc",
                                "pIdPedidoEnc",gl.pIdPedidoEnc);
                        break;
                    case 2:
                        callMethod("Get_Single_By_IdPedidoEnc",
                                "pIdPedidoEnc",gl.pIdPedidoEnc);
                        break;
                    case 3:
                        callMethod("Get_All_PickingUbic_By_IdPickingEnc",
                                "pIdPickingEnc",gl.gIdPickingEnc,
                                "pDetalleOperador", false,
                                "pIdOperadorBodega",0);
                        break;
                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                error=e.getMessage();errorflag =true;
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
                    //processTareasVerificacion();
                    break;
                case 2:

                    break;
                case 3:

                    break;

            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_verificacion_datos.super.finish();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Regresar(View view){
        msgAskExit("Está seguro de salir");
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir de las tareas de verificación");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    protected void onResume() {
        try{
            super.onResume();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
