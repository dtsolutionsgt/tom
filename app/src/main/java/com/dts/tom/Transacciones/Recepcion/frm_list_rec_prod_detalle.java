package com.dts.tom.Transacciones.Recepcion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_det;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.list_adapt_detalle_rec_prod;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.zbra.androidlinq.Linq.stream;


public class frm_list_rec_prod_detalle extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private Button btnRegs, btnCompletaRec2;
    private ListView listView;

    private clsBeTrans_oc_det BeOcDet;
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeTrans_re_detList pListTransRecDet = new clsBeTrans_re_detList();
    private clsBeTrans_re_det selitem;

    private int mdet=1;
    private String pNumeroLP="";

    private ImageView imgImprimir;

    private list_adapt_detalle_rec_prod adapter;
    private static ArrayList<clsBeTrans_re_det> BeListDetalleRec= new ArrayList<clsBeTrans_re_det>() ;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_rec_prod_detalle);

        super.InitBase();

        ws = new frm_list_rec_prod_detalle.WebServiceHandler(frm_list_rec_prod_detalle.this, gl.wsurl);
        xobj = new XMLObject(ws);

        btnRegs = (Button) findViewById(R.id.btnRegs);
        btnCompletaRec2 = (Button) findViewById(R.id.btnCompletaRec2);
        imgImprimir = (ImageView)findViewById(R.id.imgImprimir);

        listView = (ListView)findViewById(R.id.listRecDet);

        if (gl.gselitem != null) {
            BeOcDet=gl.gselitem;

            valida_producto_completo();
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ProgressDialog();

        progress.setMessage("Cargando detalle");

        setHandles();

        //Llama a processBeProducto
        execws(1);

    }

    public void ProgressDialog(){
        progress=new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void setHandles(){

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position>0){

                            Object lvObj = listView.getItemAtPosition(position);
                            selitem = pListTransRecDet.items.get(position-1);

                            selid =selitem.IdRecepcionDet ;
                            selidx = position;
                            adapter.setSelectedIndex(position);
                            pNumeroLP = selitem.Lic_plate;

                            imgImprimir.setVisibility(View.VISIBLE);

                    }else{
                        imgImprimir.setVisibility(View.INVISIBLE);
                    }

                }

            });


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position>0){

                        Object lvObj = listView.getItemAtPosition(position);
                        selitem = pListTransRecDet.items.get(position-1);

                        selid =selitem.IdRecepcionDet ;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        if (!gl.gBeRecepcion.Habilitar_Stock) {
                            Procesar_registro();
                        }else{
                            msgbox("El detalle no se puede modificar porque ya el Stock está habilitado");
                        }
                    }

                    return true;
                }

            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }

    }

    public void BacKList(View view) {
        doExit();
    }

    private void Procesar_registro(){
        gl.mode=2;
        gl.gListTransRecDet.items = stream(pListTransRecDet.items).where(c->c.IdRecepcionDet == selid).toList();
        browse=1;
        startActivity(new Intent(this, frm_recepcion_datos.class));
    }

    private void Lista_Detalle_Rec(){
        clsBeTrans_re_det vItem;
        BeListDetalleRec.clear();

        try{

            vItem = new clsBeTrans_re_det();
            BeListDetalleRec.add(vItem);

            if (mdet==1){
//Version de Android 7
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                {
                Map<String, Map<String, Map<String, Map<Double, List<clsBeTrans_re_det>>>>> employessGroup=
                        null;
                    employessGroup = pListTransRecDet.items.stream().collect(Collectors.groupingBy(clsBeTrans_re_det::getNombre_presentacion,
                            Collectors.groupingBy(clsBeTrans_re_det::getNombre_producto_estado,
                                    Collectors.groupingBy(clsBeTrans_re_det::getNombre_producto,
                                            Collectors.groupingBy(clsBeTrans_re_det::getcantidad_recibida)))));
                    employessGroup.size();
                }

            }else{

            }

            if (pListTransRecDet!=null){

                if (pListTransRecDet.items!=null){

                   for (clsBeTrans_re_det obj:pListTransRecDet.items){

                       vItem = new clsBeTrans_re_det();

                       vItem.Nombre_presentacion = obj.Nombre_presentacion;
                       vItem.Codigo_Producto = obj.Codigo_Producto;
                       vItem.Nombre_unidad_medida = obj.Nombre_unidad_medida;
                       vItem.cantidad_recibida = obj.cantidad_recibida;
                       vItem.IdProductoBodega = obj.IdProductoBodega;
                       vItem.Nombre_producto_estado = obj.Nombre_producto_estado;
                       vItem.Fecha_vence = du.convierteFechaMostar(obj.Fecha_vence);
                       vItem.Lic_plate = obj.Lic_plate;

                       BeListDetalleRec.add(vItem);

                   }
                    int count =BeListDetalleRec.size()-1;
                    btnRegs.setText("Regs: "+count);
                }
            }

            Collections.sort(BeListDetalleRec,new OrdenarItems());

            adapter=new list_adapt_detalle_rec_prod(this,BeListDetalleRec);
            listView.setAdapter(adapter);

        }catch (Exception e){
            mu.msgbox("Lista_Detalle_Rec:"+e.getMessage());
        }
    }
    void valida_producto_completo(){

        double CantRec = 0;
        double CantOC  = 0;

        try{

           CantOC = BeOcDet.getCantidad();
           CantRec = BeOcDet.getCantidad_recibida();

            if (CantRec >= CantOC){

                if (CantRec == CantOC){
                    btnCompletaRec2.setText("COMPLETA");
                    btnCompletaRec2.setBackgroundColor(Color.parseColor("#FF99CC00"));
                }else if (CantRec > CantOC) {
                    btnCompletaRec2.setText(" DIF - (POS)");
                    btnCompletaRec2.setBackgroundColor(Color.parseColor("#FF0399D5"));
                }
            }else{
                btnCompletaRec2.setText("DIF - (NEG)");
                btnCompletaRec2.setBackgroundColor(Color.parseColor("#FFCC0000"));
            }
        }catch (Exception ex){
            msgbox(ex.getMessage());
        }
    }


    /*Private Sub Valida_Producto_Completo(ByVal CantRec As Double, ByVal CantOC As Double)

    Try

    If CantRec >= CantOC Then
    cmdCompletaProd.Text = "COMPLETA"
    cmdCompletaProd.BackColor = Color.Green
            Else
    cmdCompletaProd.Text = "INCOMPLETA"
    cmdCompletaProd.BackColor = Color.Firebrick
    End If

    Catch ex As Exception
    MsgBox(ex.Message, MsgBoxStyle.Critical, Me.Text)
    End Try

    End Sub*/

    public class OrdenarItems implements Comparator<clsBeTrans_re_det> {

        public int compare(clsBeTrans_re_det left,clsBeTrans_re_det rigth){
            return left.IdRecepcionDet-rigth.IdRecepcionDet;
            //return left.Nombre.compareTo(rigth.Nombre);
        }

    }

    public void Imprimir(View view){
        msgAskImprimir("Imprimir Licencia");
    }

    private void msgAskImprimir(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage( msg);

            dialog.setCancelable(true);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Código de Producto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Barra();
                }
            });

            dialog.setNegativeButton("Licencia de Producto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Imprimir_Licencia();
                }
            });

            dialog.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void Imprimir_Licencia(){
        try{

            //CM_20210112: Impresión de barras.
            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);
            printerIns.open();

            if (printerIns.isConnected()){
                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);
                //zPrinterIns.sendCommand("! U1 setvar \"device.languages\" \"zpl\"\r\n");


                String zpl = String.format("^XA \n" +
                                "^MMT \n" +
                                "^PW700 \n" +
                                "^LL0406 \n" +
                                "^LS0 \n" +
                                "^FT171,61^A0I,25,14^FH^FD%1$s^FS \n" +
                                "^FT550,61^A0I,25,14^FH^FD%2$s^FS \n" +
                                "^FT670,306^A0I,25,14^FH^FD%3$s^FS \n" +
                                "^FT292,61^A0I,25,24^FH^FDBodega:^FS \n" +
                                "^FT670,61^A0I,25,24^FH^FDEmpresa:^FS \n" +
                                "^FT670,367^A0I,25,24^FH^FDTOMIMS, WMS.  Product Barcode^FS \n" +
                                "^FO2,340^GB670,0,14^FS \n" +
                                "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                "^FD%4$s^FS \n" +
                                "^PQ1,0,1,Y " +
                                "^XZ",gl.CodigoBodega, gl.gNomEmpresa,
                        BeProducto.Codigo+" - "+BeProducto.Nombre,
                        "$"+pNumeroLP);

                zPrinterIns.sendCommand(zpl);


                Thread.sleep(500);

                // Close the connection to release resources.
                printerIns.close();

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

           // Actualiza_Valores_Despues_Imprimir();

        }catch (Exception e){
            //#EJC20210126
            if (e.getMessage().contains("Could not connect to device:")){
                mu.msgbox("Error al imprimir el código de barra. No existe conexión a la impresora: "+ gl.MacPrinter);
               // Actualiza_Valores_Despues_Imprimir();
            }else{
                mu.msgbox("Imprimir_barra: "+e.getMessage());
            }


        }
    }

    private void Imprimir_Barra(){

        try{

            //CM_20210112: Impresión de barras.
            BluetoothConnection printerIns= new BluetoothConnection(gl.MacPrinter);
            printerIns.open();

            if (printerIns.isConnected()){
                ZebraPrinter zPrinterIns = ZebraPrinterFactory.getInstance(printerIns);
                //zPrinterIns.sendCommand("! U1 setvar \"device.languages\" \"zpl\"\r\n");

                String zpl = String.format("^XA \n" +
                                "^MMT \n" +
                                "^PW700 \n" +
                                "^LL0406 \n" +
                                "^LS0 \n" +
                                "^FT171,61^A0I,25,14^FH^FD%1$s^FS \n" +
                                "^FT550,61^A0I,25,14^FH^FD%2$s^FS \n" +
                                "^FT670,306^A0I,25,14^FH^FD%3$s^FS \n" +
                                "^FT292,61^A0I,25,24^FH^FDBodega:^FS \n" +
                                "^FT670,61^A0I,25,24^FH^FDEmpresa:^FS \n" +
                                "^FT670,367^A0I,25,24^FH^FDTOMIMS, WMS.  Product Barcode^FS \n" +
                                "^FO2,340^GB670,0,14^FS \n" +
                                "^BY3,3,160^FT670,131^BCI,,Y,N \n" +
                                "^FD%4$s^FS \n" +
                                "^PQ1,0,1,Y " +
                                "^XZ",gl.CodigoBodega, gl.gNomEmpresa,
                        BeProducto.Codigo+" - "+BeProducto.Nombre,
                        (pNumeroLP!="")?"$"+pNumeroLP:BeProducto.Codigo);

                zPrinterIns.sendCommand(zpl);


                Thread.sleep(500);

                // Close the connection to release resources.
                printerIns.close();

            }else{
                mu.msgbox("No se pudo obtener conexión con la impresora");
            }

            //Actualiza_Valores_Despues_Imprimir();

        }catch (Exception e){
            mu.msgbox("Imprimir_barra: "+e.getMessage());
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
                        callMethod("Get_Producto_By_IdProductoBodega","IdProductoBodega",BeOcDet.IdProductoBodega);
                        break;
                    case 2:
                        callMethod("Get_Detalle_By_IdRecepcionDet_HH","pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "pIdProductoBodega",BeOcDet.IdProductoBodega,"pNoLinea",BeOcDet.No_Linea);
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
                    processBeProducto();
                    break;
                case 2:
                    processDetRec();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processBeProducto(){

        try {


            BeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            //Llama a processDetRec
            execws(2);
            //Load();

        } catch (Exception e) {
            msgbox(" processBeProducto: " + e.getMessage());
        }

    }

    private void processDetRec(){

        try{

            progress.setMessage("Obteniendo detalle");

            pListTransRecDet = xobj.getresult(clsBeTrans_re_detList.class,"Get_Detalle_By_IdRecepcionDet_HH");

            if (pListTransRecDet!=null){
                if (pListTransRecDet.items!=null){
                    Lista_Detalle_Rec();

                    progress.cancel();

                }else{
                    doExit();
                }
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processDetRec:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void doExit(){
        try{

            //LimpiaValores();
            super.finish();
            gl.Carga_Producto_x_Pallet=false;
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                //Llama a processDetRec
                execws(2);
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        try{
            doExit();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}
