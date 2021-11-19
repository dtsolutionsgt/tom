package com.dts.tom.Transacciones.ConsultaStock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.TipoEtiqueta.clsBeTipo_etiqueta;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

public class frm_consulta_stock_detalleCI extends PBase {

    private TextView lblcodigo,lbldescripcion,lblexUnidad,lblexPres,lblestado,lblpedido,lblpicking,lblvence,lbllote,lblubic,lblnomUbic,lblLicPlate;

    private frm_consulta_stock_detalleCI.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;
    private clsBeTipo_etiqueta pBeTipo_etiqueta;

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

        ProgressDialog();

        asignarDatos();

        //Aquí llamamos al método del WS Get_Tipo_Etiqueta_By_IdTipoEtiqueta
        pBeTipo_etiqueta = new clsBeTipo_etiqueta();
        pBeTipo_etiqueta.IdTipoEtiqueta= gl.existencia.getIdTipoEtiqueta();

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

                Double existencia = Double.valueOf(gl.existencia.ExistUMBAs);
                String stringDecimal = String.format("%.6f", existencia);
                lblexUnidad.setText(gl.existencia.UM +" "+ stringDecimal);

            }else {
                lblexUnidad.setText(gl.existencia.UM +" 0.00");
            }


            if(gl.existencia.ExistUMBAs != "" && gl.existencia.factor !=0){

                Double factor= Double.valueOf(gl.existencia.ExistUMBAs)/Double.valueOf(gl.existencia.factor);
                String stringDecimal = String.format("%.6f", factor);

                lblexPres.setText( "EXIST: " + gl.existencia.Pres +": "+ stringDecimal);
            }else {
                lblexPres.setText( "EXIST: " + gl.existencia.Pres);
            }

            lblestado.setText( gl.existencia.Estado+"");
            lblpedido.setText( gl.existencia.Pedido+"");
            lblpicking.setText( gl.existencia.Pick+"");
            lblvence.setText( gl.existencia.Vence+"");
            lbllote.setText( gl.existencia.Lote+"");
            lblubic.setText( gl.existencia.idUbic+"");
            lblnomUbic.setText( gl.existencia.Ubic+"");

            if(gl.existencia.LicPlate.equals(0)){
                lblLicPlate.setText( "LP : " + "");
            }else{
                lblLicPlate.setText( "LP : " + gl.existencia.LicPlate);
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

        } catch (Exception e) {
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    public void printBarras(View view){
        msgAskImprimir("Seleccione una opción para imprimir");
    }

    private void Imprimir_Codigo_Barra_Producto(){

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
                                "^FT670,367^A0I,25,24^FH^FDTOMWMS Product Barcode^FS \n" +
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
                                "^BY3,3,160^FT550,200^BCI,,Y,N\n" +
                                "^FD%3$s^FS\n" +
                                "^PQ1,0,1,Y \n" +
                                "^FT600,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                "^FO2,440^GB670,14,14^FS\n" +
                                "^FT600,470^A0I,25,24^FH^FDTOMWMS  Product Barcode^FS\n" +
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
                            "^FT670,367^A0I,25,24^FH^FDTOMWMS License Number^FS \n" +
                            "^FO2,340^GB670,0,14^FS \n" +
                            "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                            "^FD%4$s^FS \n" +
                            "^PQ1,0,1,Y " +
                            "^XZ",gl.CodigoBodega + "-" + gl.gNomBodega,
                            gl.gNomEmpresa,
                            gl.existencia.Codigo,
                            gl.existencia.Codigo+" - "+gl.existencia.Nombre);
                    }

                    if (!zpl.isEmpty()){
                        zPrinterIns.sendCommand(zpl);
                    }else{
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                    }

                    Thread.sleep(500);

                    // Close the connection to release resources.
                    printerIns.close();

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

    private void Imprimir_Licencia(){

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
                        zpl = String.format("^XA \n" +
                                            "^MMT \n" +
                                            "^PW609 \n" +
                                            "^LL0406 \n" +
                                            "^LS0 \n" +
                                            "^FT231,61^A0I,30,24^FH^FD%1$s^FS \n" +
                                            "^FT550,61^A0I,30,24^FH^FD%2$s^FS \n" +
                                            "^FT670,306^A0I,30,24^FH^FD%3$s^FS \n" +
                                            "^FT292,61^A0I,30,24^FH^FDBodega:^FS \n" +
                                            "^FT670,61^A0I,30,24^FH^FDEmpresa:^FS \n" +
                                            "^FT670,367^A0I,25,24^FH^FDTOMWMS License Number^FS \n" +
                                            "^FO2,340^GB670,0,14^FS \n" +
                                            "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                            "^FD%4$s^FS \n" +
                                            "^PQ1,0,1,Y " +
                                            "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                            gl.existencia.Codigo+" - "+gl.existencia.Nombre,
                                            "$"+gl.existencia.getLicPlate());
                    }else if (gl.existencia.IdTipoEtiqueta==2){
                        //#CKFK 20210804 Modificación de la impresion del LP para el tipo de etiqueta 2,
                        //Dado que la descripción salía muy pequeña
                        zpl = String.format("^XA\n" +
                                            "^MMT\n" +
                                            "^PW609 \n" +
                                            "^LL0406 \n" +
                                            "^LS0\n" +
                                            "^FT440,20^A0I,28,30^FH^FD%1$s^FS\n" +
                                            "^FT560,20^A0I,26,30^FH^FDBodega:^FS\n" +
                                            "^FT440,55^A0I,28,30^FH^FD%2$s^FS\n" +
                                            "^FT560,55^A0I,26,30^FH^FDEmpresa:^FS\n" +
                                            "^FT560,100^A0I,90,100^FH^FD%3$s^FS\n" +
                                            "^BY3,3,160^FT550,200^BCI,,N,N\n" +
                                            "^FD%3$s^FS\n" +
                                            "^PQ1,0,1,Y \n" +
                                            "^FT600,400^A0I,35,40^FH^FD%4$s^FS\n" +
                                            "^FO2,440^GB670,14,14^FS\n" +
                                            "^FT600,470^A0I,25,24^FH^FDTOMWMS  Product Barcode^FS\n" +
                                            "^XZ",gl.CodigoBodega + " - " + gl.gNomBodega, gl.gNomEmpresa,
                                            gl.existencia.Codigo+" - "+gl.existencia.Nombre,
                                            "$"+gl.existencia.getLicPlate());
                    }

                    if (!zpl.isEmpty()){
                        zPrinterIns.sendCommand(zpl);
                    }else{
                        msgbox("No se pudo generar la etiqueta porque el tipo de etiqueta no está definido");
                    }

                    Thread.sleep(500);

                    // Close the connection to release resources.
                    printerIns.close();

                }else{
                    mu.msgbox("No se pudo obtener conexión con la impresora");
                }

            }

        }catch (Exception e){
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

    private void msgAskImprimir(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg );

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Código de Producto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Codigo_Barra_Producto();
                }
            });

            dialog.setNegativeButton("Licencia de Producto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Licencia();
                }
            });

            dialog.setNeutralButton("No imprimir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

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