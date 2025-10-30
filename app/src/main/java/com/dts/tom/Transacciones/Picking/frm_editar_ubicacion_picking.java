package com.dts.tom.Transacciones.Picking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.tom.Mainmenu;
import com.dts.tom.PBase;
import com.dts.tom.R;

import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.selitem;

import java.util.concurrent.ExecutionException;

public class frm_editar_ubicacion_picking extends PBase {

    private TextView lblProducto, lblUbicActual, lblUbicDest;
    private EditText txtUbicDestino;

    private int IdUbicacion = 0;
    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeBodega_ubicacion BeUbic = new clsBeBodega_ubicacion();
    private int CambioUbicRealizado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ubicacion_picking);
        super.InitBase();

        ws = new WebServiceHandler(frm_editar_ubicacion_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblProducto = findViewById(R.id.lblProducto);
        lblUbicActual = findViewById(R.id.lblUbicActual);
        lblUbicDest = findViewById(R.id.lblUbicDest);

        txtUbicDestino = findViewById(R.id.txtUbicDestino);
        lblProducto.setText(selitem.CodigoProducto+" - "+selitem.NombreProducto);
        lblUbicActual.setText(selitem.NombreUbicacion);

        CambioUbicRealizado = 0;
        setHandlers();

    }

    private void setHandlers() {
        txtUbicDestino.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!txtUbicDestino.getText().toString().isEmpty()) {
                        procesa_cambio();
                    } else {
                        toast("Debe ingresar una ubicación.");
                        txtUbicDestino.requestFocus();
                        txtUbicDestino.setSelectAllOnFocus(true);
                    }
                }

                return false;
            }
        });
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
                        IdUbicacion = Integer.valueOf(txtUbicDestino.getText().toString());
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega","pBarra", IdUbicacion, "pIdBodega", gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Actualizar_Ubicaciones_Reservadas_By_IdStock", "pIdStock", selitem.IdStock, "pIdBodega", gl.IdBodega, "pIdUbicacion",IdUbicacion);
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
                    processValidaUbic();
                    break;
                case 2:
                    processCambioUbic();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processValidaUbic() {
       try {
           BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

           if (BeUbic != null) {
               if ((BeUbic.Nivel == 1 || BeUbic.Nivel == 0 || BeUbic.Ubicacion_picking)
                    && BeUbic.IdUbicacion != selitem.IdUbicacion){

                   msgValidaCambio("Reubicar producto a: " + BeUbic.Descripcion);
                   lblUbicDest.setVisibility(View.VISIBLE);
                   lblUbicDest.setText(BeUbic.Descripcion);
               } else {
                   toast("Ubicación no válida");
               }
           } else {
               toast("Ubicación no válida");
           }
       } catch (Exception e) {
           mu.msgbox("processValidaUbic: "+ e.getMessage());
       }
    }

    private void processCambioUbic() {
        try {

            //CambioUbicRealizado = xobj.get(Integer.class, "Actualizar_Ubicaciones_Reservadas_By_IdStock");
            CambioUbicRealizado = (Integer) xobj.getSingle("Actualizar_Ubicaciones_Reservadas_By_IdStockResult",int.class);

            if (CambioUbicRealizado > 0) {
                super.finish();
            }

        } catch (Exception e) {
            mu.msgbox("processCambioUbic: "+e.getMessage());
        }
    }

    private void procesa_cambio() {
        try {
            execws(1);
        } catch (Exception e){
            mu.msgbox("procesa cambio: "+ e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        try{
            //#GT29102025: validar url antes de invocar al WS
            final String safeUrl = requireValidUrl(gl.wsurl);
            ws.callback=callbackvalue;
            ws.execute();
        } catch (Exception ex) {
            showFatalAndGoHome(ex.getMessage()); // o un Toast/Alert y return
        }
    }

    /** Valida que la URL no sea nula, vacía ni mal formada. No corrige. */
    private String requireValidUrl(String url) {

        //#GT29102025: esto causa un bug controlado
        //url="";

        if (url == null) throw new IllegalStateException("Error: la URL del WebService es nula.");
        String s = url.trim();
        if (s.isEmpty()) throw new IllegalStateException("Error: la URL del WebService está vacía.");
        if (!s.startsWith("http://") && !s.startsWith("https://")) {
            throw new IllegalArgumentException("Error: la URL debe iniciar con http:// o https://");
        }
        try {
            new java.net.URL(s); // valida sintaxis
        } catch (java.net.MalformedURLException e) {
            throw new IllegalArgumentException("Error: la URL del WebService es inválida.", e);
        }
        return s;
    }
    private void showFatalAndGoHome(String detail) {
        runOnUiThread(() -> {
            new AlertDialog.Builder(this)
                    .setTitle("Error de comunicación")
                    .setMessage("La configuración del servicio no es válida.\n" + detail + "\nLa app se reiniciará al menu principal.")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", (d, w) -> softRestartToMain())
                    .show();
        });
    }
    private void softRestartToMain() {
        // 1) Limpieza de estado volátil
        try { cleanRuntimeState(); } catch (Exception ignore) { /* no bloquear por limpieza */ }

        // 2) Volver al MainActivity con la task stack limpia
        Intent intent = new Intent(this, Mainmenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
    }

    private void cleanRuntimeState() {
        try {
            // Limpia referencias básicas
            ws = null;      // limpia el handler actual
            Log.i("App", "Estado en memoria limpio antes del reinicio.");
        } catch (Exception e) {
            Log.w("App", "Error al limpiar estado: " + e.getMessage());
        }
    }


    private void msgValidaCambio(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿"+msg+"?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(2);
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            dialog.show();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void regresar(View view) {
        finish();
    }

    public void GuardaCambioUbic(View view) {
        if (!txtUbicDestino.getText().toString().isEmpty()) {
            procesa_cambio();
        } else {
            toast("Ingrese una ubicación");
        }
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
