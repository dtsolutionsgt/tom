package com.dts.tom.Transacciones.Recepcion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;

import androidx.core.content.FileProvider;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Configuracion_barra_pallet.clsBeConfiguracion_barra_pallet;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_oc.clsBeTrans_re_oc;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_recList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_ingresos extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;
    private boolean Finalizada = false, Anulada = false;

    private clsBeTrans_re_enc gBeRecepcion = new clsBeTrans_re_enc();
    private clsBeTrans_re_detList pListTransRecDet;
    private  clsBeTrans_re_oc gBeReOC = new clsBeTrans_re_oc();
    private clsBeTrans_oc_detList pListDetalleOC = new clsBeTrans_oc_detList();
    private clsBeTrans_oc_enc gBeOrdenCompra = new clsBeTrans_oc_enc();
    public static clsBeConfiguracion_barra_pallet gBeConfiguracionBarraPallet =  new clsBeConfiguracion_barra_pallet();
    private clsBeStock_recList pListBeStockRecPI = new clsBeStock_recList();
    private clsBeStock_rec gBeStockRec = new clsBeStock_rec();

    private Spinner cmbPropietario,cmbProveedor,cmbTipoDoc,cmbFechaRec;
    private EditText txtOc,txtRef,txtMarch,txtGuia,txtEstadoRec,txtMuelleRec, txtNumOrden, txtNumPoliza;
    private TableRow tblNumOrden, tblNumPoliza;
    private ProgressBar pbar;
    private ProgressDialog progress;
    private ImageView btnCamara;

    String encoded="";

    private ArrayList<String> proplist= new ArrayList<String>();
    private ArrayList<String> provlist= new ArrayList<String>();
    private ArrayList<String> tipooclist= new ArrayList<String>();
    private ArrayList<String> vencelist= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_detalle_ingresos);

        super.InitBase();

        ws = new WebServiceHandler(frm_detalle_ingresos.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtOc = (EditText)findViewById(R.id.txtOc);
        txtRef = (EditText)findViewById(R.id.txtRef);
        txtMarch = (EditText)findViewById(R.id.txtMarch);
        txtGuia = (EditText)findViewById(R.id.txtGuia);
        txtEstadoRec = (EditText)findViewById(R.id.txtEstadoRec);
        txtMuelleRec = (EditText)findViewById(R.id.txtMuelleRec);
        txtNumOrden = (EditText)findViewById(R.id.txtNumOrden);
        txtNumPoliza = (EditText)findViewById(R.id.txtNumPoliza);

        tblNumOrden = (TableRow) findViewById(R.id.tblNumOrden);
        tblNumPoliza = (TableRow) findViewById(R.id.tblNumPoliza);

        cmbPropietario = (Spinner) findViewById(R.id.cmbPropietario);
        cmbProveedor = (Spinner) findViewById(R.id.cmbProveedor);
        cmbTipoDoc = (Spinner) findViewById(R.id.cmbTipoDoc);
        cmbFechaRec = (Spinner) findViewById(R.id.cmbFechaRec);

        btnCamara = (ImageView)findViewById(R.id.imageView12);

        ProgressDialog("Cargando forma");

        Load();

    }

    private void Load(){

        try{

            if (gl.gIdRecepcionEnc>0){
                execws(1);
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    //CM_20201201: Guarda fotos tomadas con la camara del dispositivo en la bd.
    /*public void abrirCamara (){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/
    //COMENTARIO PARA COMMIT

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            //aquí la convierto a base 64
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            msgAsGuardaFotos("Desea asignar esta foto a la recepción "+gl.gIdRecepcionEnc);
            //imageView.setImageBitmap(imageBitmap);
        }
    }

    //CM_20201201: Esta es la forma en la que estaba intentando guardar la imagen en el dispositivo, pero al agregar los varloes siguientes en el
    //-manifest me da un error y no he logrado ver en dónde está mi error porque la aplicación solo se cierra.
    /*
*/

   private void abrirCamara() {
       try{
           Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           // Ensure that there's a camera activity to handle the intent
           if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
               // Create the File where the photo should go
               File photoFile = null;
               try {
                   photoFile = createImageFile();
               } catch (IOException ex) {
               }
               // Continue only if the File was successfully created
               if (photoFile != null) {
                   Uri photoURI = FileProvider.getUriForFile(this,
                           "com.dts.tom.fileprovider",
                           photoFile);
                   takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                   startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
               }
           }
       }catch (Exception ee){
           mu.msgbox(ee.getMessage());
       }

    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    String currentPhotoPath;

    private File  createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void cCamara(View view){
        abrirCamara();
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
                        callMethod("Get_Banderas_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"pFinalizada",Finalizada,"pAnulada",Anulada);
                        break;
                    case 2:
                        callMethod("Get_IdProductoBuenEstado_Por_Defecto_By_IdBodega_And_IdEmpresa","pIdBodega",gl.IdBodega,"pIdEmpresa",gl.IdEmpresa);
                        break;
                    case 3:
                        callMethod("GetSingleRec","pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 4:
                        callMethod("Get_Configuracion_Barra_Pallet_By_IdConfiguracion","pIdConfiguracionBarraPallet",gBeOrdenCompra.ProveedorBodega.Proveedor.IdConfiguracionBarraPallet);
                        break;
                    case 5:
                        callMethod("Get_Stock_Rec_By_IdRecepcionEnc","pIdRecepcionEnc",gl.gIdRecepcionEnc);
                        break;
                    case 6:
                        //aquí mando la imagen ya convertida, así como se hizo con la firma.
                        callMethod("Guardar_Fotos_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"Foto",encoded);
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
                    processBanderasRecep();break;
                case 2:
                    processIdProductoBuenEstadoPorDefecto();break;
                case 3:
                    processgBeRecepcion();break;
                case 4:
                    processConfiguracionPallet();break;
                case 5:
                    processStockRec();
                    break;
                case 6:
                    encoded="";
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processStockRec(){

        try{

            progress.setMessage("Obteniendo stock rec");

            pListBeStockRecPI = xobj.getresult(clsBeStock_recList.class,"Get_Stock_Rec_By_IdRecepcionEnc");

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processConfiguracionPallet(){

        try{

            progress.setMessage("Buscando configuración de barras pallet");

            gBeConfiguracionBarraPallet = xobj.getresult(clsBeConfiguracion_barra_pallet.class,"Get_Configuracion_Barra_Pallet_By_IdConfiguracion");

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processBanderasRecep(){

        try {

            progress.setMessage("Obteniendo banderas de recepción");

            Finalizada =(Boolean) xobj.getSingle("pFinalizada",Boolean.class);
            Anulada =(Boolean) xobj.getSingle("pAnulada",Boolean.class);

            if (Finalizada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " ya fue finalizada");
                progress.cancel();
                doExit();
            }

            if (Anulada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " fue anulada");
                progress.cancel();
                doExit();
            }

            if (Finalizada & Anulada){
                super.finish();
            }else{
                execws(2);
            }



        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processIdProductoBuenEstadoPorDefecto(){

        try {

            progress.setMessage("Obteniendo estado por defecto");

            gl.gIdProductoBuenEstadoPorDefecto = xobj.getresult(Integer.class,"Get_IdProductoBuenEstado_Por_Defecto_By_IdBodega_And_IdEmpresa");

            execws(3);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void processgBeRecepcion(){

        try {

            progress.setMessage("Cargando recepción");

            gBeRecepcion = xobj.getresult(clsBeTrans_re_enc.class,"GetSingleRec");
            gl.mode =1;
            if (gBeRecepcion!=null)
            {
                Llenar_Campos();
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void Llenar_Campos(){

        try{

            txtGuia.setText("");
            txtMuelleRec.setText("");
            txtOc.setText("");
            txtRef.setText("");
            txtMarch.setText("");
            txtEstadoRec.setText("");
            txtNumOrden.setText("");
            txtNumPoliza.setText("");

            progress.setMessage("Listando propietarios");

            Listar_Propietarios_Bodega();

            progress.setMessage("Listando tipo de documento");

            Listar_Tipo_Documentos();

            progress.setMessage("Llenando guía");

            if (gBeRecepcion.NoGuia!=null){
                txtGuia.setText(gBeRecepcion.NoGuia);
            }

            progress.setMessage("Llenando muelle");

            if (gBeRecepcion.Muelle!=null){
                txtMuelleRec.setText(gBeRecepcion.Muelle.Nombre);
            }

            if (gl.TipoOpcion==1) {

                 progress.setMessage("Obteniendo detalle de recepción");

                pListTransRecDet = gBeRecepcion.Detalle;

                gBeOrdenCompra = new clsBeTrans_oc_enc();
                gBeReOC = new clsBeTrans_re_oc();
                pListDetalleOC =new clsBeTrans_oc_detList();

                progress.setMessage("Obteniendo documento de ingreso");

               gBeOrdenCompra = gBeRecepcion.OrdenCompraRec.OC;

                progress.setMessage("Obteniendo re oc");

                gBeReOC = gBeRecepcion.OrdenCompraRec;

                progress.setMessage("Obteniendo detalle de ingreso");

                pListDetalleOC = gBeRecepcion.OrdenCompraRec.OC.DetalleOC;

                if (pListDetalleOC!=null){
                    gl.gListDetalleOC = pListDetalleOC;
                }

                if (gBeRecepcion!=null){
                    gl.gBeRecepcion = gBeRecepcion;
                }

                progress.setMessage("Listando proveedor bodega");

                Listar_Proveedores_Bodega();

                progress.setMessage("Listando fecha de recepción");

                Listar_Fecha_Rec();

                progress.setMessage("Obteniendo estado de oc");

                gBeOrdenCompra.EstadoOC.IdEstadoOC = gBeOrdenCompra.IdEstadoOC;

                gBeOrdenCompra.EstadoOC = gBeRecepcion.OrdenCompraRec.OC.EstadoOC;

                if (gBeOrdenCompra.ProveedorBodega.Proveedor.IdConfiguracionBarraPallet!=0){
                    execws(4);
                }

                progress.setMessage("Llenando número de documento");

                if (gBeOrdenCompra.No_Documento!=null){
                    txtOc.setText(gBeOrdenCompra.No_Documento);
                }

                progress.setMessage("Llenando número de orden");


                if (gBeOrdenCompra.getObjPoliza().numero_orden!=null){
                    txtNumPoliza.setText(gBeOrdenCompra.getObjPoliza().numero_orden);
                }else{
                    tblNumOrden.setVisibility(View.GONE);
                }

                progress.setMessage("Llenando número de póliza");

                if (gBeOrdenCompra.getObjPoliza().codigo_poliza!=null){
                    txtNumPoliza.setText(gBeOrdenCompra.getObjPoliza().codigo_poliza);
                }else{
                    tblNumPoliza.setVisibility(View.GONE);
                }

                progress.setMessage("Llenando referencia");

                if (gBeOrdenCompra.Referencia!=null){
                    txtRef.setText(gBeOrdenCompra.Referencia);
                }

                progress.setMessage("Llenando número de marchamo");

                if (gl.gBeRecepcion.No_Marchamo!=null){
                    txtMarch.setText(gl.gBeRecepcion.No_Marchamo);
                }

                progress.setMessage("Llenando estado de oc");

                if (gBeOrdenCompra.EstadoOC.Nombre!=null){
                    txtEstadoRec.setText(gBeOrdenCompra.EstadoOC.Nombre);
                }

                if (gBeRecepcion.IdTipoTransaccion.equals("PICH000")){
                    execws(5);
                }else{

                    if (pListBeStockRecPI.items!= null){
                        if (pListBeStockRecPI.items.size()>0){
                            List<clsBeStock_rec> BeStockRec =
                                    stream(pListBeStockRecPI.items)
                                            .where(c -> c.Lic_plate.equals(""))
                                            .toList();

                            if (BeStockRec!=null){
                                gBeStockRec = BeStockRec.get(0);
                            }
                        }
                    }
                }

                BloquearControles();

            }

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Listar_Fecha_Rec(){

        try{

            vencelist.clear();

            vencelist.add(gBeRecepcion.Fecha_recepcion);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, vencelist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbFechaRec.setAdapter(dataAdapter);

            if (vencelist.size()>0) cmbFechaRec.setSelection(0);


        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Listar_Propietarios_Bodega(){

        try{

            proplist.clear();

            proplist.add(gBeRecepcion.PropietarioBodega.Propietario.Nombre_comercial);

            gl.IdPropietario = gBeRecepcion.PropietarioBodega.Propietario.IdPropietario;

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, proplist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbPropietario.setAdapter(dataAdapter);

            if (proplist.size()>0) cmbPropietario.setSelection(0);

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }

    }

    private void Listar_Proveedores_Bodega(){

        try{

            provlist.clear();

            provlist.add(gBeRecepcion.OrdenCompraRec.OC.ProveedorBodega.Proveedor.Nombre);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, provlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbProveedor.setAdapter(dataAdapter);

            if (provlist.size()>0) cmbProveedor.setSelection(0);

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void Listar_Tipo_Documentos(){

        try{

            tipooclist.clear();

            if (gBeRecepcion.OrdenCompraRec.OC.TipoIngreso != null){

                tipooclist.add(gBeRecepcion.OrdenCompraRec.OC.TipoIngreso.Nombre);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tipooclist);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbTipoDoc.setAdapter(dataAdapter);

                if (tipooclist.size()>0) cmbTipoDoc.setSelection(0);

            }

        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void BloquearControles(){

        try{

            txtGuia.setFocusable(false);
            txtGuia.setFocusableInTouchMode(false);
            txtGuia.setClickable(false);
            cmbPropietario.setFocusable(false);
            cmbPropietario.setFocusableInTouchMode(false);
            cmbPropietario.setClickable(false);
            cmbProveedor.setFocusable(false);
            cmbProveedor.setFocusableInTouchMode(false);
            cmbProveedor.setClickable(false);
            cmbTipoDoc.setFocusable(false);
            cmbTipoDoc.setFocusableInTouchMode(false);
            cmbTipoDoc.setClickable(false);
            cmbFechaRec.setFocusable(false);
            cmbFechaRec.setFocusableInTouchMode(false);
            cmbFechaRec.setClickable(false);
            txtEstadoRec.setFocusable(false);
            txtEstadoRec.setFocusableInTouchMode(false);
            txtEstadoRec.setClickable(false);
            txtMarch.setFocusable(false);
            txtMarch.setFocusableInTouchMode(false);
            txtMarch.setClickable(false);
            txtRef.setFocusable(false);
            txtRef.setFocusableInTouchMode(false);
            txtRef.setClickable(false);
            txtOc.setFocusable(false);
            txtOc.setFocusableInTouchMode(false);
            txtOc.setClickable(false);
            txtMuelleRec.setFocusable(false);
            txtMuelleRec.setFocusableInTouchMode(false);
            txtMuelleRec.setClickable(false);

        }catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    public void Atender_rece(View view){
        browse=1;
        startActivity(new Intent(this, frm_list_rec_prod.class));
    }

    public void Regresar(View view){
        msgAskExit("Está seguro de salir");
    }

    public void LimpiaValores(){

        gBeRecepcion = new clsBeTrans_re_enc();
        gl.gBeRecepcion = new clsBeTrans_re_enc();
        pListTransRecDet = new clsBeTrans_re_detList();
        gBeReOC = new clsBeTrans_re_oc();
        pListDetalleOC = new clsBeTrans_oc_detList();
        gBeOrdenCompra = new clsBeTrans_oc_enc();
        gBeConfiguracionBarraPallet =  new clsBeConfiguracion_barra_pallet();
        pListBeStockRecPI = new clsBeStock_recList();
        gBeStockRec = new clsBeStock_rec();
        gl.CodigoRecepcion ="";
        gl.Carga_Producto_x_Pallet = false;
        gl.gFechaVenceAnterior = "";
        gl.gLoteAnterior ="";
        gl.Escaneo_Pallet=false;

    }

    private void doExit(){
        try{

            LimpiaValores();
            super.finish();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setCancelable(false);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    doExit();
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

    private void msgAsGuardaFotos(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setCancelable(false);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  execws(6);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    encoded="";
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }


    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){

                doExit();
            }


        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}

