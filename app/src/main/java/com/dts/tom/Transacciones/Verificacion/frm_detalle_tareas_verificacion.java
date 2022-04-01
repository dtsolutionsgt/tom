package com.dts.tom.Transacciones.Verificacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion2;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_tareas_verificacion extends PBase {

    private frm_detalle_tareas_verificacion.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private int indiceDetallePedido = -1;
    private int index = 0;

    private list_adapt_detalle_tareas_verificacion adapter;
    private list_adapt_detalle_tareas_verificacion2 adapter2;

    private ListView listDetVeri;
    private EditText txtCodProd;
    private TextView lblNoDocumento;
    private Button btnFinalizarTareaVerificacion,btnNoVerificado,btnRegs,btnConsultaDa,btnBack;

    private clsBeTrans_picking_ubicList plistPickingUbic = new clsBeTrans_picking_ubicList();
    private clsBeDetallePedidoAVerificarList pListaPedidoDet = new clsBeDetallePedidoAVerificarList();
    private clsBeTrans_picking_enc gBePickingEnc = new clsBeTrans_picking_enc();

    private ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();

    private clsBeProducto_estadoList lProductoEstadoDañado = new clsBeProducto_estadoList();

    private clsBeTrans_pe_enc gBePedido = new clsBeTrans_pe_enc();
    private clsBeDetallePedidoAVerificar selitem;

    private double cantReemplazar = 0;
    private boolean preguntoPorDiferencia = false, mostrar_area;
    private boolean finalizar = true;
    private int selidx = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frm_detalle_tareas_verificacion);

            super.InitBase();

            mostrar_area = gl.Mostrar_Area_En_HH;

            ws = new frm_detalle_tareas_verificacion.WebServiceHandler(frm_detalle_tareas_verificacion.this, gl.wsurl);
            xobj = new XMLObject(ws);

            listDetVeri = (ListView)findViewById(R.id.ListDetVeri);
            txtCodProd = (EditText) findViewById(R.id.txtCodProd);
            btnFinalizarTareaVerificacion = (Button) findViewById(R.id.btnFinalizarTareaPicking);
            btnNoVerificado = (Button) findViewById(R.id.btnNoVerificado);
            btnRegs = (Button) findViewById(R.id.btnRegs);
            btnConsultaDa = (Button) findViewById(R.id.btnConsultaDa);
            btnBack = (Button) findViewById(R.id.btnBack);
            lblNoDocumento= (TextView) findViewById(R.id.lblNoDoumento);

            setHandlers();

            ProgressDialog("Cargando forma...");

            Load();

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void Load(){

        try {
            progress.setMessage("Actualizado detalle de tareas de verificación...");
            progress.show();

            listDetVeri.setAdapter(null);

            if (gl.pIdPedidoEnc>0){
                //Llama al método del WS Get_Single_By_IdPedidoEnc
                execws(2);
            }else{
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox(e.getClass()+e.getMessage());
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

    private void setHandlers(){

        try{

            listDetVeri.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object lvObj = listDetVeri.getItemAtPosition(position);
                    clsBeDetallePedidoAVerificar vItem = (clsBeDetallePedidoAVerificar) lvObj;

                    if (mostrar_area) {
                        adapter2.setSelectedIndex(position);
                    } else {
                        adapter.setSelectedIndex(position);
                    }
                    int index = position;
                    Procesa_Registro(vItem);

                    if (mostrar_area) {
                        adapter2.refreshItems();
                    } else {
                        adapter.refreshItems();
                    }

                }
            });

            txtCodProd.setOnKeyListener(new View.OnKeyListener(){
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                GetFila();
                                return true;
                        }
                    }
                    return false;
                }
            });

            btnConsultaDa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrar_Reemplazados();
                }
            });

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void GetFila(){

        try{

            if (!txtCodProd.getText().toString().isEmpty()) {

                String selProd = txtCodProd.getText().toString();

                if (selProd.startsWith("$")) {
                    selProd = selProd.replace("$", "");
                }

                String finalSelProd = selProd;
                clsBeDetallePedidoAVerificar vPedidoVerif  = stream(pListaPedidoDet.items)
                                                        .where(c -> c.getCodigo().equals(finalSelProd) || c.getLicPlate().equals(finalSelProd))
                                                        .first();

                if (vPedidoVerif != null) {
                    Procesa_Registro(vPedidoVerif);
                } else {
                    mu.msgbox(String.format("No existe el producto %s en esta Verificación",selProd));
                }
            }

        }catch (Exception e){
            if (e.getMessage() !=null){
                mu.msgbox("Error obteniendo el producto a verificar : "+e.getMessage());
            }else{
                mu.msgbox("El código de producto ingresado no es válido");
            }
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

                        int vIdOperadorBodega =0;

                        //#EJC20220330_CEALSA: Si true, se envía en la HH el IdOperadorBodega para filtrar las tareas de verificación
                        if (gl.operador_picking_realiza_verificacion){
                            vIdOperadorBodega=gl.OperadorBodega.IdOperadorBodega;
                        }
                        //#EJC20201008: Lllamada a método específico para verificación!
                        callMethod("Get_All_PickingUbic_By_IdPickingEnc_For_Verificacion",
                                   "pIdPickingEnc",gl.gIdPickingEnc,
                                   "pDetalleOperador", false,
                                   "pIdOperadorBodega",vIdOperadorBodega);
                        break;
                    case 4:
                        callMethod("Get_All_PickingUbic_By_IdPickingEnc_And_IdPedidoEnc",
                                   "pIdPickingEnc", gl.gIdPickingEnc,
                                   "pIdPedidoEnc", gl.pIdPedidoEnc);
                        break;
                    case 5:
                        callMethod("Set_Estado_Pedido_Verificado",
                                   "oBeTrans_pe_enc", gBePedido);
                        break;
                    case 6:
                        callMethod("Tiene_Pedidos_Sin_Verificar_By_IdPickingEnc",
                                   "pIdPickingEnc", gl.gIdPickingEnc);
                        break;
                    case 7:
                        callMethod("Get_Picking_By_IdPickingEnc",
                                   "pIdPickingEnc", gl.gIdPickingEnc);
                        break;
                    case 8:
                        callMethod("Actualizar_PickingEnc_Verificado",
                                   "oBeTrans_picking_enc", gBePickingEnc);
                        break;
                    case 9:
                        callMethod("Get_Reemplazo_Producto_By_IdPedidoEnc",
                                   "pIdPedidoEnc", gl.pIdPedidoEnc);
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
                    processTareasVerificacion();
                    break;
                case 2:
                    processEncabezadoPedido();
                    break;
                case 3:
                    processPickingUbic();
                    break;
                case 4:
                    processPickingUbic2();
                    break;
                case 5:
                    processSetEstado();
                    break;
                case 6:
                    processPedidosSinVerificar();
                    break;
                case 7:
                    processGetPicking();
                    break;
                case 8:
                    processActualizarPickingVerificado();
                    break;
            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Procesa_Registro(clsBeDetallePedidoAVerificar TareaDet){

        try{

            gl.pIdPedidoDet =TareaDet.IdPedidoDet;
            gl.gBePedidoDetVerif=TareaDet;
            gl.gBePickingUbicList=plistPickingUbic;

            browse = 1;

            Intent intent = new Intent(this, frm_verificacion_datos.class);
            startActivity(intent);

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void mostrar_Reemplazados(){

        try{

            Intent intent = new Intent(this, frm_list_prod_reemplazados.class);
            startActivity(intent);

        }catch (Exception ex){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox( ex.getMessage());
        }

    }

    private void  processEncabezadoPedido(){
        try{
            progress.setMessage("Cargando datos del encabezado del pedido...");
            progress.show();

            gBePedido =  xobj.getresult(clsBeTrans_pe_enc.class,"Get_Single_By_IdPedidoEnc");

            gl.gIdPickingEnc = gBePedido.IdPickingEnc;

            lblNoDocumento.setText(String.format("IdPedido:%s Referencia:%s \n Cliente: %s", String.valueOf(gl.pIdPedidoEnc),
                    gBePedido.Referencia, gBePedido.Cliente.Nombre_comercial));

            progress.setMessage("Cargando detalle del Picking Ubic");

            if (gl.gIdPickingEnc>0){
                //Llama a método del WS Get_All_PickingUbic_By_IdPickingEnc
                execws(3);
            }else{
                progress.cancel();
            }

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processTareasVerificacion(){

        try {

            progress.setMessage("Obteniendo Tareas de verificación en HH...");

            pListaPedidoDet = xobj.getresult(clsBeDetallePedidoAVerificarList.class,"Get_Detalle_By_IdPedidoEnc");

            listDetVeri.setAdapter(null);

            if (pListaPedidoDet!=null){
                if(pListaPedidoDet.items!=null) Lista_Detalle_Pedido();
            } else {
                btnNoVerificado.setText("Verificado");
                btnNoVerificado.setBackgroundColor(Color.GREEN);
                progress.cancel();
                toast("Este pedido ya no tiene productos pendientes de verificar");
            }

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processPickingUbic(){

        try{

            progress.setMessage("Obteniendo listado de Picking Ubic...");

            plistPickingUbic = xobj.getresult(clsBeTrans_picking_ubicList.class,"Get_All_PickingUbic_By_IdPickingEnc_For_Verificacion");

            progress.setMessage("Cargando detalle de tarea de verificación");

            if (gl.pIdPedidoEnc>0){
                //Llama al método del WS Get_Detalle_By_IdPedidoEnc
                execws(1);
            }else{
                progress.cancel();
            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processPickingUbic2(){

        try{

            progress.setMessage("Obteniendo listado de Picking Ubic para finalizar tarea...");

            preguntoPorDiferencia = false;
            finalizar = true;

            plistPickingUbic = xobj.getresult(clsBeTrans_picking_ubicList.class,"Get_All_PickingUbic_By_IdPickingEnc_And_IdPedidoEnc");

            if (plistPickingUbic != null){
                if (plistPickingUbic.items != null){
                    for (clsBeTrans_picking_ubic ubi:plistPickingUbic.items){

                        if (ubi.Cantidad_Recibida != ubi.Cantidad_Verificada) {

                            preguntoPorDiferencia = true;

                            msgAskIncompleta("La verificación está incompleta, está seguro(a) de finalizarla?");

                        }
                    }

                    progress.cancel();
                    if (finalizar){
                        if (!preguntoPorDiferencia){
                            msgAskFinalizar("Finalizar la tarea de verificación");
                        }
                    }

                }else{
                    progress.cancel();
                }
            }else{
                progress.cancel();
                if (finalizar){
                    if (!preguntoPorDiferencia){
                        msgAskFinalizar("Finalizar la tarea de verificación");
                    }
                }
            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processSetEstado(){

        try{

            progress.setMessage("Actualizando estado de la verificacion...");
            progress.show();
            int actualizados = (Integer) xobj.getSingle("Set_Estado_Pedido_VerificadoResult",Integer.class);

            //Llama a método del WS Tiene_Pedidos_Sin_Verificar_By_IdPickingEnc
            execws(6);

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processPedidosSinVerificar(){

        try{

            progress.setMessage("Obteniendo pedidos sin verificar...");

            boolean tienePedidosSV = (Boolean) xobj.getSingle("Tiene_Pedidos_Sin_Verificar_By_IdPickingEncResult",Boolean.class);

            if (! tienePedidosSV) {
                //Llama a método del WS Get_Picking_By_IdPickingEnc
                execws(7);
            } else {
                progress.cancel();
            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processGetPicking(){

        try{

            progress.setMessage("Obteniendo picking...");

            gBePickingEnc = xobj.getresult(clsBeTrans_picking_enc.class,"Get_Picking_By_IdPickingEnc");

            Date currentTime = Calendar.getInstance().getTime();

            if (gBePickingEnc != null){
                gBePickingEnc.Estado = "Verificado";
                gBePickingEnc.Fec_mod = app.strFecha(currentTime);
                gBePickingEnc.User_mod = String.valueOf(gl.IdOperador);
                gBePickingEnc.Hora_fin = app.strFecha(currentTime);

                //Llama a método del WS Actualizar_PickingEnc_Verificado
                execws(8);
            }

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void processActualizarPickingVerificado(){

        try{

            progress.setMessage("Actualizando picking verificado...");

            int actualizados = (Integer) xobj.getSingle("Actualizar_PickingEnc_VerificadoResult",Integer.class);

            progress.cancel();

            frm_detalle_tareas_verificacion.super.finish();

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void Lista_Detalle_Pedido(){
        try {

            progress.setMessage("Listando detalle de pedido para verificación en HH...");

            clsBeDetallePedidoAVerificar vItem;
            pListBeTareasVerificacionHH.clear();selidx = -1;

            try{

                progress.setMessage("Cargando tareas de verificación");

                if (pListaPedidoDet!= null) {

                    if (pListaPedidoDet.items!=null ){

                        for (int i = 0; i<=pListaPedidoDet.items.size()-1; i++) {

                            vItem = new clsBeDetallePedidoAVerificar();

                            vItem.IdPedidoEnc = pListaPedidoDet.items.get(i).getIdPedidoEnc();
                            vItem.IdPedidoDet = pListaPedidoDet.items.get(i).getIdPedidoDet();
                            vItem.IdProductoBodega = pListaPedidoDet.items.get(i).getIdProductoBodega();
                            vItem.Codigo = pListaPedidoDet.items.get(i).getCodigo();
                            vItem.Nombre_Producto = pListaPedidoDet.items.get(i).getNombre_Producto();
                            vItem.Lote =  pListaPedidoDet.items.get(i).getLote();
                            vItem.Fecha_Vence = app.strFecha(pListaPedidoDet.items.get(i).getFecha_Vence());
                            vItem.LicPlate = pListaPedidoDet.items.get(i).getLicPlate();
                            vItem.Nom_Unid_Med = pListaPedidoDet.items.get(i).getNom_Unid_Med();
                            vItem.Nom_Presentacion = pListaPedidoDet.items.get(i).getNom_Presentacion();
                            vItem.Cantidad_Solicitada = pListaPedidoDet.items.get(i).getCantidad_Solicitada();
                            vItem.Cantidad_Recibida = pListaPedidoDet.items.get(i).getCantidad_Recibida();
                            vItem.Cantidad_Verificada = pListaPedidoDet.items.get(i).getCantidad_Verificada();
                            vItem.Nom_Estado = pListaPedidoDet.items.get(i).getNom_Estado();
                            vItem.IdPresentacion = pListaPedidoDet.items.get(i).getIdPresentacion();
                            vItem.IdUnidadMedidaBasica = pListaPedidoDet.items.get(i).getIdUnidadMedidaBasica();
                            vItem.NDias = pListaPedidoDet.items.get(i).getNDias();
                            vItem.IdProductoEstado =  pListaPedidoDet.items.get(i).getIdProductoEstado();
                            vItem.NombreArea = pListaPedidoDet.items.get(i).getNombreArea();
                            vItem.NombreClasificacion = pListaPedidoDet.items.get(i).getNombreClasificacion();

                            pListBeTareasVerificacionHH.add(vItem);

                            if (vItem.LicPlate.equalsIgnoreCase(gl.gLP)) {
                                selitem=vItem;
                                selidx = i;
                            }
                        }

                        btnRegs.setText("Regs: "+pListaPedidoDet.items.size());

                        if (mostrar_area) {
                            adapter2 = new list_adapt_detalle_tareas_verificacion2(this, pListBeTareasVerificacionHH);
                            listDetVeri.setAdapter(adapter2);
                        } else {
                            adapter = new list_adapt_detalle_tareas_verificacion(this, pListBeTareasVerificacionHH);
                            listDetVeri.setAdapter(adapter);
                        }

                        if (pListaPedidoDet.items.size()>0){
                            if (mostrar_area) {
                                adapter2.setSelectedIndex(-1);
                            } else {
                                adapter.setSelectedIndex(-1);
                            }

                            index = -1;
                            btnNoVerificado.setText("No Verificado");
                            btnNoVerificado.setBackgroundColor(Color.RED);
                        } else {
                            btnNoVerificado.setText("Verificado");
                            btnNoVerificado.setBackgroundColor(Color.GREEN);
                        }

                        if ( gl.gVerifCascade && selidx>-1) {
                            try{
                                gl.pIdPedidoDet =selitem.IdPedidoDet;
                                gl.gBePedidoDetVerif=selitem;
                                gl.gBePickingUbicList=plistPickingUbic;

                                browse = 1;
                                Intent intent = new Intent(this, frm_verificacion_datos.class);
                                startActivity(intent);
                            } catch (Exception e){
                                mu.msgbox( e.getMessage());
                            }
                        }

                    }

                } else {
                    listDetVeri.setAdapter(null);
                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                mu.msgbox( e.getMessage());
            }

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    public void finalizarTareaVerificacion(View view){

        try{

            progress.setMessage("Listando tareas de verificación para finalizarla...");
            progress.show();

            //Llama a método del WS Get_All_PickingUbic_By_IdPickingEnc_And_IdPedidoEnc
            execws(4);

        }catch (Exception ex){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
        }
    }

    private void msgAskIncompleta(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (preguntoPorDiferencia){
                        msgAskFinalizar("Finalizar tarea de verificación");
                    }

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finalizar = false;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskFinalizar(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Llamo a método del WS Set_Estado_Pedido_Verificado
                    progress.setMessage("Finalizando la tarea de verificación...");
                    progress.show();
                    execws(5);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
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
                    gl.gVerifCascade=false;
                    frm_detalle_tareas_verificacion.super.finish();
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
        msgAskExit("Está seguro de salir de las tareas de verificación");
    }

    public void Actualizar(View view){
        Load();
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

            if (browse==1) {
                browse = 0;

                txtCodProd.setText("");

                progress.setMessage("Actualizando detalle verificación...");
                progress.show();

                //Llama al método del WS Get_Detalle_By_IdPedidoEnc
                execws(1);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
