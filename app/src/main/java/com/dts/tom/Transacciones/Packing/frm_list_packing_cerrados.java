package com.dts.tom.Transacciones.Packing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_encList;
import com.dts.ladapt.ClickListener;
import com.dts.ladapt.list_adapt_licenciaspacking;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.base.WebService;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.util.ArrayList;

public class frm_list_packing_cerrados extends PBase {
    private WebServiceHandler ws;
    private XMLObject xobj;

    private EditText txtFiltro;
    private RecyclerView listaRv;
    private ImageView btnBuscar;

    private list_adapt_licenciaspacking adapter;

    private clsBeTrans_packing_encList LicenciasPacking;
    private ArrayList<clsBeTrans_packing_enc> auxPacking = new ArrayList<>();
    private String Licencia = "";
    private String bodDestino= "";
    private Boolean procesando = false;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_packing_cerrados);
        super.InitBase();

        ws = new WebServiceHandler(frm_list_packing_cerrados.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtFiltro = findViewById(R.id.txtFiltro);
        btnBuscar = findViewById(R.id.btnBuscar);
        listaRv = findViewById(R.id.rvLista);
        listaRv.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listaRv.getContext(), DividerItemDecoration.VERTICAL);
        listaRv.addItemDecoration(dividerItemDecoration);

        setHandlers();
    }

    private void setHandlers() {
        try {
            txtFiltro.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    ProgressDialog();
                    progress.setMessage("Buscando...");

                    if (event.getRepeatCount() >  0) {
                        Log.e("EnterKeyPress", "Ignorando repetición de tecla.");
                        return true;
                    }

                    if (!txtFiltro.getText().toString().isEmpty()) {
                        execws(1);
                    } else {
                        progress.cancel();
                        msgbox("Debe ingresar un pedido o referencia.");
                    }

                }
                return false;
            });

            btnBuscar.setOnClickListener(view -> {
                ProgressDialog();
                progress.setMessage("Buscando...");

                if (procesando) {
                    return;
                }

                procesando = true;

                new Handler().postDelayed(() -> {
                    if (!txtFiltro.getText().toString().isEmpty()) {
                        execws(1);
                        procesando = false;
                    } else {
                        progress.cancel();
                        msgbox("Debe ingresar un pedido o referencia.");
                    }
                }, 200); // Cambia el tiempo según la duración de tu proceso

            });
        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processLicenciasPackingCerrado() {
        try {

            progress.cancel();

            LicenciasPacking = xobj.getresult(clsBeTrans_packing_encList.class,"Get_LicenciasPacking_Cerrado");
            auxPacking.clear();
            if (LicenciasPacking != null) {

                for (clsBeTrans_packing_enc obj: LicenciasPacking.items) {
                    auxPacking.add(obj);
                }

                adapter = new list_adapt_licenciaspacking(this, auxPacking);
                onClickRv();
                listaRv.setAdapter(adapter);
            }else{
                msgbox("No se encontraron licencias de packing asociadas al parámetro de busqueda.");
            }
        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void msgImprimir(String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg + "\n\nImpresora: " + gl.MacPrinter);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setCancelable(false);

            dialog.setPositiveButton("Si", (dialog1, which) -> {
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                    Imprimir_Licencia();

                }, 300);


            });

            dialog.setNegativeButton("No", (dialog12, which) -> {});
            dialog.show();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
        }

    private void Imprimir_Licencia(){
        try{

            if (!Licencia.isEmpty()){
                Licencia = Licencia.replace("$","");
            }

            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);

            if (!printerIns.isConnected()){
                printerIns.open();
            }

            if (printerIns.isConnected()){

                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);

                String zpl="";

                if (!Licencia.isEmpty()) {

                    if (gl.pBeBodega.IdTipoEtiquetaLicencia == 1) {

                        zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW700 \n" +
                                        "^LL0406 \n" +
                                        "^LS0 \n" +
                                        "^FT450,21^A0I,20,14^FH^FD%4$s^FS \n" +
                                        "^FO2,40^GB670,0,5^FS \n" +
                                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                        "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                        "^FT670,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS \n" +
                                        "^FO2,340^GB670,0,14^FS \n" +
                                        "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                        "^FD%3$s^FS \n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT130,210^A0I,100,40^FH^FDCol %5$s^FS \n" +
                                        "^FT130,110^A0I,100,40^FH^FD%6$s^FS \n" +
                                        "^XZ",
                                        gl.CodigoBodega + " - " + gl.gNomBodega,
                                        gl.gNomEmpresa,
                                        "$" + Licencia,
                                        gl.IdOperador + " " + gl.gNomOperador + " / " + du.Fecha_Completa(),
                                        bodDestino,
                                        txtFiltro.getText());

                    } else if (gl.pBeBodega.IdTipoEtiquetaLicencia == 2) {
                        zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW600\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT450,80^A0I,20,14^FH^FD%5$s^FS\\n" +
                                        "^FO2,110^GB670,0,5^FS \n" +
                                        "^FT440,130^A0I,28,30^FH^FD%1$s^FS\n" +
                                        "^FT560,130^A0I,26,30^FH^FDBodega:^FS\n" +
                                        "^FT440,165^A0I,28,30^FH^FD%2$s^FS\n" +
                                        "^FT560,165^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                        "^FT560,220^A0I,70,70^FH^FD%3$s^FS\n" +
                                        "^BY3,3,160^FT550,300^BCI,,N,N\n" +
                                        "^FD%3$s^FS\n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT560,480^A0I,35,30^FH^FD%4$s^FS\n" +
                                        "^FO2,520^GB670,14,14^FS\n" +
                                        "^FT560,540^A0I,25,24^FH^FDTOMWMS  No. Licencia^FS\n" +
                                        "^XZ", gl.CodigoBodega + "-" + gl.gNomBodega,
                                gl.gNomEmpresa,
                                "$" + Licencia,
                                "",
                                "");

                    } else if (gl.pBeBodega.IdTipoEtiquetaLicencia == 4) {
                        zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW812 \n" +
                                        "^LL0630 \n" +
                                        "^LS0 \n" +
                                        "^FT450,21^A0I,20,14^FH^FD%5$s^FS \n" +
                                        "^FO2,40^GB670,0,5^FS \n" +
                                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                        "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                        "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                        "^FT670,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS \n" +
                                        "^FO2,340^GB670,0,14^FS \n" +
                                        "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                        "^FD%4$s^FS \n" +
                                        "^PQ1,0,1,Y " +
                                        "^XZ", gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                "",
                                "$" + Licencia,
                                "");

                    }else if (gl.pBeBodega.IdTipoEtiquetaLicencia == 5) {

                        zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW700\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT450,21^A0I,20,14^FH^FD%5$s^FS\n" +
                                        "^FO2,40^GB700,5,5^FS\n" +
                                        "^FT270,61^A0I,30,24^FH^FD%1$s^FS\n" +
                                        "^FT550,61^A0I,30,24^FH^FD%2$s^FS\n" +
                                        "^FT700,306^A0I,30,24^FH^FD%3$s^FS\n" +
                                        "^FT290,135^A0I,85,54^FH^FDV.%7$s^FS\n" +
                                        "^FT290,225^A0I,85,49^FH^FDL.%6$s^FS\n" +
                                        "^FT360,61^A0I,30,24^FH^FDBodega:^FS\n" +
                                        "^FT700,61^A0I,30,24^FH^FDEmpresa:^FS\n" +
                                        "^FT700,367^A0I,25,24^FH^FDTOMWMS No. Licencia^FS\n" +
                                        "^FO2,340^GB700,14,14^FS\n" +
                                        "^BY3,3,160^FT700,131^BCI,,Y,N\n" +
                                        "^FD%4$s^FS\n" +
                                        "^PQ1,0,1,Y\n" +
                                        "^XZ", gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                "",
                                "$" + Licencia,
                                "",
                                "");

                    }

                    if (!zpl.isEmpty()) {
                        zPrinterIns.sendCommand(zpl);
                    } else {
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido (LP)");
                    }
                }

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }
        } catch (Exception e) {
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir la licencia del producto. No existe conexión a la impresora: "+ gl.MacPrinter);
            }else{
                mu.msgbox("Imprimir_licencia: "+e.getMessage());
            }
        }
    }

    public void onClickRv() {
        try {

            adapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Object lvObj = adapter.getItem(position);
                    clsBeTrans_packing_enc sitem = (clsBeTrans_packing_enc) lvObj;
                    selidx = position;
                    adapter.setSelectedIndex(position);
                    Licencia = sitem.No_linea;
                    bodDestino = sitem.Referencia;

                    msgImprimir("¿Imprimir Licencia "+ Licencia +"?");
                }

                @Override
                public void onItemLongClick(int position, View v) {

                }

            });
        } catch (Exception e) {
            addlog(new Object() {}.getClass().getEnclosingMethod().getName(), e.getMessage(), sql);
            mu.msgbox(e.getMessage());
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
                        callMethod("Get_LicenciasPacking_Cerrado",
                                "pIdPedidoEnc", Integer.valueOf(txtFiltro.getText().toString()));
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
                    processLicenciasPackingCerrado();
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

    public void botonAtras(View view){
        finish();
    }

    public void ProgressDialog(){
        progress=new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

}
