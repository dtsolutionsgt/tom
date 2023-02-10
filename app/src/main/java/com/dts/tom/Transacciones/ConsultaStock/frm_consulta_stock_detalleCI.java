package com.dts.tom.Transacciones.ConsultaStock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.TipoEtiqueta.clsBeTipo_etiqueta;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.CambioUbicacion.frm_cambio_ubicacion_ciega;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

public class frm_consulta_stock_detalleCI extends PBase {

    private TextView lblcodigo,lbldescripcion,lblexUnidad,lblexPres,lblestado,
            lblpedido,lblpicking,lblvence,lbllote,lblubic,lblnomUbic,lblLicPlate,
            lblPresentacion, lblUnidad;
    private TableRow trPresentacion;
    private Spinner cmbCantidad;
    private frm_consulta_stock_detalleCI.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;
    private clsBeTipo_etiqueta pBeTipo_etiqueta;
    private Integer CantCopias =1;
    public static boolean CambioUbicExistencia = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock_detalle_c_i);
        super.InitBase();

        ws = new frm_consulta_stock_detalleCI.WebServiceHandler(frm_consulta_stock_detalleCI.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblcodigo = findViewById(R.id.lblcodigoCI);
        lbldescripcion = findViewById(R.id.lbldescripcionCI);
        lblexUnidad = findViewById(R.id.lblexUnidadCI);
        lblexPres = findViewById(R.id.lblexPresCI);
        lblestado = findViewById(R.id.lblestadoCI);
        lblpedido = findViewById(R.id.lblpedidoCI);
        lblpicking = findViewById(R.id.lblpickingCI);
        lblvence = findViewById(R.id.lblvenceCI);
        lbllote = findViewById(R.id.lblloteCI);
        lblubic = findViewById(R.id.lblubicCI);
        lblnomUbic = findViewById(R.id.lblnomUbicCI);
        lblLicPlate= findViewById(R.id.lblLicPlate);
        lblPresentacion = findViewById(R.id.lblPresentacion);
        lblUnidad = findViewById(R.id.lblUnidad);
        trPresentacion = findViewById(R.id.trPresentacion);

        ProgressDialog();

        asignarDatos();

        //Aquí llamamos al método del WS Get_Tipo_Etiqueta_By_IdTipoEtiqueta
        pBeTipo_etiqueta = new clsBeTipo_etiqueta();
        pBeTipo_etiqueta.IdTipoEtiqueta= gl.existencia.getIdTipoEtiqueta();

        CambioUbicExistencia = false;

        execws(1);

    }

    public void ProgressDialog(){
        progress=new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void asignarDatos() {


        if(gl.existencia !=null){

            lblcodigo.setText( gl.existencia.Codigo +"");
            lbldescripcion.setText(gl.existencia.Nombre + "");
            //lblexUnidad.setText(""+0);

            if(gl.existencia.ExistUMBAs !="0" || !gl.existencia.ExistUMBAs.isEmpty()){

                Double existencia = Double.valueOf(gl.existencia.ExistUMBAs.replace(",",""));
                String stringDecimal = String.format("%.2f", existencia);
                lblUnidad.setText(gl.existencia.UM+":");
                lblexUnidad.setText(stringDecimal);

            }else {
                lblexUnidad.setText("0.00");
            }


            if(gl.existencia.ExistUMBAs != "" && gl.existencia.factor !=0){

                Double factor= Double.valueOf(gl.existencia.ExistUMBAs.replace(",",""))/Double.valueOf(gl.existencia.factor);
                String stringDecimal = String.format("%.2f", factor);

                lblPresentacion.setText(gl.existencia.Pres+":");
                lblexPres.setText(stringDecimal);
            }else {
                trPresentacion.setVisibility(View.GONE);
                lblexPres.setText(gl.existencia.Pres);
            }

            lblestado.setText( gl.existencia.Estado+"");
            lblpedido.setText( gl.existencia.Pedido+"");
            lblpicking.setText( gl.existencia.Pick+"");
            lblvence.setText( gl.existencia.Vence+"");
            lbllote.setText( gl.existencia.Lote+"");
            lblubic.setText( gl.existencia.idUbic+"");
            lblnomUbic.setText( gl.existencia.Ubic+"");

            if(gl.existencia.LicPlate.equals(0)){
                lblLicPlate.setText("");
            }else{
                lblLicPlate.setText(gl.existencia.LicPlate);
            }



        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processTipoEtiqueta();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
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
                    case 1://Obtiene el Tipo de Etiqueta del producto
                        callMethod("Get_Tipo_Etiqueta_By_IdTipoEtiqueta","pBeTipo_etiqueta",pBeTipo_etiqueta);
                        break;
                }

            } catch (Exception e) {
                error=e.getMessage();errorflag =true;msgbox(error);
            }
        }
    }

    private void processTipoEtiqueta(){

        try {

            progress.setMessage("Obteniendo tipo de etiqueta del producto");

            pBeTipo_etiqueta = xobj.getresultSingle(clsBeTipo_etiqueta.class,"pBeTipo_etiqueta");

            progress.cancel();

            lblcodigo.requestFocus();

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    public void printBarras(View view){
        msgAskImprimir("Imprimir");
    }

    private void Imprimir_Codigo_Barra_Producto(int Copias){

        try{

            if (gl.existencia.Codigo!= "0"){
                //EJC20210112: Impresión de barras.
                BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);

                if (!printerIns.isConnected()){
                    printerIns.open();
                }

                if (printerIns.isConnected()){

                    ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);

                    String zpl="";

                    if (gl.existencia.IdTipoEtiqueta==1){

                                zpl = String.format("^XA \n" +
                                "^MMT \n" +
                                "^PW700 \n" +
                                "^LL406 \n" +
                                "^LS0 \n" +
                                "^FT231,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                                "^FT292,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                "^FT670,367^A0I,25,24^FH^FDTOMWMS Codigo de Producto^FS \n" +
                                "^FO2,340^GB670,0,14^FS \n" +
                                "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                "^FD%4$s^FS \n" +
                                "^PQ1,0,1,Y " +
                                "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                                gl.gNomEmpresa,
                                gl.existencia.Codigo,
                                gl.existencia.Codigo+" - "+gl.existencia.Nombre);

                    }else if (gl.existencia.IdTipoEtiqueta==2){
                                zpl = String.format("^XA\n" +
                                "^MMT\n" +
                                "^LS0\n" +
                                "^FT440,90^A0I,28,30^FH^FD%1$s^FS\n" +
                                "^FT560,90^A0I,26,30^FH^FDBodega:^FS\n" +
                                "^FT440,125^A0I,28,30^FH^FD%2$s^FS\n" +
                                "^FT560,125^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                "^BY2,3,160^FT550,200^BCI,,Y,N\n" +
                                "^FD%3$s^FS\n" +
                                "^PQ1,0,1,Y \n" +
                                "^FT560,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                "^FO2,440^GB670,14,14^FS\n" +
                                "^FT560,470^A0I,25,24^FH^FDTOMWMS  Codigo de Producto^FS\n" +
                                "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                                gl.gNomEmpresa,
                                gl.existencia.Codigo,
                                gl.existencia.Codigo+" - "+gl.existencia.Nombre);
                    }else if (gl.existencia.IdTipoEtiqueta==4){
                            zpl = String.format("^XA \n" +
                            "^MMT \n" +
                            "^PW812 \n" +
                            "^LL0630 \n" +
                            "^LS0 \n" +
                            "^FT270,61^A0I,30,24^FH^FD%1$s^FS \n" +
                            "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                            "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                            "^FT360,61^A0I,30,24^FH^FDBodega:^FS \n" +
                            "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                            "^FT670,367^A0I,25,24^FH^FDTOMWMS Codigo de Producto^FS \n" +
                            "^FO2,340^GB670,0,14^FS \n" +
                            "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                            "^FD%4$s^FS \n" +
                            "^PQ1,0,1,Y " +
                            "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                            gl.gNomEmpresa,
                            gl.existencia.Codigo,
                            gl.existencia.Codigo+" - "+gl.existencia.Nombre);
                    }else if (gl.existencia.IdTipoEtiqueta==5) {
                        zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW400\n" +
                                        "^LL400\n" +
                                        "^LS0\n" +
                                        "^FT380,5^A0I,15,12^FH^FD%8$s^FS\n" +
                                        "^FT380,25^A0I,15,12^FH^FDEmpresa: %7$s - Bodega: %6$s^FS\n" +
                                        "^FO2,40^GB390,0,5^FS\n" +
                                        "^FT380,95^A0I,15,12^FH^FDMarca: %5$s^FS\n" +
                                        "^FT380,75^A0I,15,12^FH^FDLinea: %4$s^FS\n" +
                                        "^FT380,55^A0I,15,12^FH^FDModelo: %3$s^FS\n" +
                                        "^BY3,3,140^FT380,150^BCI,,Y,N\n" +
                                        "^FD%2$s^FS\n" +
                                        "^PQ1,0,1,Y\n" +
                                        "^FT380,320^A0I,25,20^FH^FD%1$s^FS\n" +
                                        "^FO2,350^GB390,0,5^FS\n" +
                                        "^FT380,370^A0I,25,24^FH^FDTOMWMS Codigo de Producto^FS\n" +
                                        "^XZ", gl.existencia.Codigo + " - " + gl.existencia.Nombre,
                                        gl.existencia.Codigo,
                                        gl.existencia.Clasificacion,
                                        "",
                                        "",
                                        gl.CodigoBodega + "-" + gl.gNomBodega,
                                        gl.gNomEmpresa,
                                        gl.beOperador.Nombres + " " + gl.beOperador.Apellidos + " / " + du.getFechaActual());
                    }

                    if (!zpl.isEmpty()){
                        if (Copias > 0){
                            for(int i=0; i < Copias; i++){
                                zPrinterIns.sendCommand(zpl);
                            }
                        }

                    }else{
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                    }

                    //#EJC20220309: Don't close, dont wait!
                    //Thread.sleep(500);
                    // Close the connection to release resources.
                    //printerIns.close();

                }else{
                    mu.msgbox("No se pudo obtener conexión con la impresora");
                }

            }

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }
        }finally {
            progress.cancel();
        }
    }

    private void Imprimir_Licencia(int Copias){

        try{

            if (gl.existencia.Codigo!="0"){
                //EJC20210112: Impresión de barras.
                BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);
                
                if (!printerIns.isConnected()){
                    printerIns.open();
                }

                if (printerIns.isConnected()){

                    ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);

                    String zpl="";

                    if (gl.existencia.IdTipoEtiqueta==1){

                        //#EJC20220307: Tomado de recepción.
                        zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW700 \n" +
                                        "^LL0406 \n" +
                                        "^LS0 \n" +
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
                                        "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                gl.existencia.Codigo+" - "+gl.existencia.Nombre,
                                "$"+gl.existencia.LicPlate);

                    }else if (gl.existencia.IdTipoEtiqueta==2){
                        //#CKFK 20210804 Modificación de la impresion del LP para el tipo de etiqueta 2,
                        //Dado que la descripción salía muy pequeña

                        /*zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW600\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT440,20^A0I,28,30^FH^FD%1$s^FS\n" +
                                        "^FT560,20^A0I,26,30^FH^FDBodega:^FS\n" +
                                        "^FT440,55^A0I,28,30^FH^FD%2$s^FS\n" +
                                        "^FT560,55^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                        "^FT560,100^A0I,90,100^FH^FD%3$s^FS\n" +
                                        "^BY3,3,160^FT550,200^BCI,,N,N\n" +
                                        "^FD%3$s^FS\n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT560,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                        "^FO2,440^GB670,14,14^FS\n" +
                                        "^FT560,470^A0I,25,24^FH^FDTOMWMS  No. Licencia^FS\n" +
                                        "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                                gl.gNomEmpresa,
                                "$"+gl.existencia.getLicPlate(),
                                gl.existencia.Codigo+" - "+gl.existencia.Nombre);*/


                        zpl = String.format("^XA\n" +
                                        "^MMT\n" +
                                        "^PW600\n" +
                                        "^LL0406\n" +
                                        "^LS0\n" +
                                        "^FT440,120^A0I,28,30^FH^FD%1$s^FS\n" +
                                        "^FT560,120^A0I,26,30^FH^FDBodega:^FS\n" +
                                        "^FT440,150^A0I,28,30^FH^FD%2$s^FS\n" +
                                        "^FT560,150^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                        "^FT560,200^A0I,90,70^FH^FD%3$s^FS\n" +
                                        "^BY3,3,150^FT550,280^BCI,,N,N\n" +
                                        "^FD%3$s^FS\n" +
                                        "^PQ1,0,1,Y \n" +
                                        "^FT560,450^A0I,35,35^FH^FD%4$s^FS\n" +
                                        "^FO2,500^GB670,14,14^FS\n" +
                                        "^FT560,520^A0I,25,24^FH^FDTOMWMS  No. Licencia^FS\n" +
                                        "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                                gl.gNomEmpresa,
                                "$"+gl.existencia.getLicPlate(),
                                gl.existencia.Codigo+" - "+gl.existencia.Nombre);


                    }else if (gl.existencia.IdTipoEtiqueta==4){

                        //#EJC20220307: Tomado de recepción.
                        zpl = String.format("^XA \n" +
                                        "^MMT \n" +
                                        "^PW812 \n" +
                                        "^LL0630 \n" +
                                        "^LS0 \n" +
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
                                        "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                gl.existencia.Codigo+" - "+gl.existencia.Nombre,
                                "$"+gl.existencia.LicPlate);
                    }

                    if (!zpl.isEmpty()){
                        if (Copias > 0) {
                            for (int i=0; i < Copias; i++) {
                                zPrinterIns.sendCommand(zpl);
                            }
                        }

                    }else{
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                    }

                    //#EJC20220309: Don't close, dont wait!
                    //Thread.sleep(500);
                    // Close the connection to release resources.
                    //printerIns.close();

                }else{
                    mu.msgbox("No se pudo obtener conexión con la impresora");
                }
            }

        }catch (Exception e){
            progress.cancel();
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.toast("Error al imprimir la licencia del producto. No existe conexión a la impresora: "+ gl.MacPrinter);
            }else{
                mu.msgbox("Imprimir_licencia: "+e.getMessage());
            }
        }finally {
            progress.cancel();
        }
    }

    public void CambiarUbicacion(View view) {
        try {

            if (gl.existencia.DisponibleUMBas.equals("0.00") && gl.existencia.DispPres.equals("0.00") &&
                    (gl.existencia.LicPlate.equals("") || gl.existencia.LicPlate.equals("0"))){
                msgbox("Todo el producto está reservado y no tiene una licencia asociada");
                return;
            }

            gl.modo_cambio = 1;
            CambioUbicExistencia = true;

            startActivity(new Intent(this, frm_cambio_ubicacion_ciega.class));
            super.finish();

        } catch (Exception e) {
            msgbox(new Object() .getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
        }
    }

    private void msgAskImprimir(String msg) {

        try{
            LayoutInflater inflater = getLayoutInflater();
            View vistaDialog = inflater.inflate(R.layout.impresion_cantidad, null, false);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            cmbCantidad = vistaDialog.findViewById(R.id.cmbCantidad);
            setHandlersImpresion();
            dialog.setView(vistaDialog);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg + "\n\nImpresora: " + gl.MacPrinter);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Código", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Codigo_Barra_Producto(CantCopias);
                }
            });

            dialog.setNegativeButton("Licencia", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Licencia(CantCopias);
                }
            });

            dialog.setNeutralButton("Salir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void setHandlersImpresion() {
        Integer[] cantidad = {1,2,4,6,8,10};
        cmbCantidad.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,cantidad));

        cmbCantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CantCopias = Integer.valueOf(cmbCantidad.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    public void Backto(View view) {
        frm_consulta_stock_detalleCI.super.finish();
    }
}