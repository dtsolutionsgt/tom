package com.dts.tom.Transacciones.Verificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_enc;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.CambioUbicacion.frm_tareas_cambio_ubicacion;
import com.dts.tom.list_adapt_detalle_tareas_verificacion;

import java.util.ArrayList;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_tareas_verificacion extends PBase {

    private frm_detalle_tareas_verificacion.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private int indiceDetallePedido = -1;
    private int index = 0;

    private list_adapt_detalle_tareas_verificacion adapter;

    private ListView listDetVeri;
    private EditText txtCodProd;
    private Button btnFinalizarTareaPicking,btnNoVerificado,btnRegs,btnConsultaDa,btnBack;

    private clsBeTrans_picking_ubicList plistPickingUbiList = new clsBeTrans_picking_ubicList();
    private clsBeProducto gBeProducto = new clsBeProducto();
    private clsBeDetallePedidoAVerificarList pListaPedidoDet = new clsBeDetallePedidoAVerificarList();

    private ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();

    private clsBeProducto_estadoList lProductoEstadoDañado = new clsBeProducto_estadoList();

    private double cantReemplazar = 0;

    //private DT_Completo As New DataTable

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frm_detalle_tareas_verificacion);

            super.InitBase();

            ws = new frm_detalle_tareas_verificacion.WebServiceHandler(frm_detalle_tareas_verificacion.this, gl.wsurl);
            xobj = new XMLObject(ws);

            listDetVeri = (ListView)findViewById(R.id.ListDetVeri);
            txtCodProd = (EditText) findViewById(R.id.txtCodProd);
            btnFinalizarTareaPicking = (Button) findViewById(R.id.btnFinalizarTareaPicking);
            btnNoVerificado = (Button) findViewById(R.id.btnNoVerificado);
            btnRegs = (Button) findViewById(R.id.btnRegs);
            btnConsultaDa = (Button) findViewById(R.id.btnConsultaDa);
            btnBack = (Button) findViewById(R.id.btnBack);

            setHandlers();

            ProgressDialog("Cargando forma");

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void Load(){

        try{

            listDetVeri.setAdapter(null);

            if (gl.IdTareaUbicEnc>0){
                progress.setMessage("Cargando detalle de tarea de verificación");
                progress.show();
                //Llama al método del WS Get_Detalle_By_IdPedidoEnc
                execws(1);
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
                    clsBeTrans_picking_ubic vItem = (clsBeTrans_picking_ubic) lvObj;

                    adapter.setSelectedIndex(position);

                    int index = position;

                    //Procesa_Registro(vItem.IdPickingEnc);

                    adapter.refreshItems();

                }
            });

            txtCodProd.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:

                                //Metodo para filtrar la lista en base a un código de producto o llamar al WS
                                if (!txtCodProd.getText().toString().isEmpty()){
                                    progress.setMessage("Validando existencia del producto");
                                    progress.show();
                                    //Llama al método del WS Get_BeProducto_By_Codigo_For_HH
                                    execws(3);
                                }else{
                                    //Load();
                                }
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
                        callMethod("Get_Detalle_By_IdPedidoEnc","pIdPedidoEnc",gl.IdBodega);
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
                    processTareasVerificacion();break;
            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processTareasVerificacion(){

        try {

            progress.setMessage("Obteniendo Tareas de verificación en HH");

            pListaPedidoDet = xobj.getresult(clsBeDetallePedidoAVerificarList.class,"Get_Detalle_By_IdPedidoEnc");

            listDetVeri.setAdapter(null);

            if(pListaPedidoDet!=null){
                if(pListaPedidoDet.items!=null){
                    Lista_Detalle_Pedido();
                }
            }

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Lista_Detalle_Pedido(){

        try {

            progress.setMessage("Listando Tareas de verificación en HH");

            List AuxList = stream(pListaPedidoDet.items)
                    .toList();

            clsBeDetallePedidoAVerificar vItem;
            pListBeTareasVerificacionHH.clear();

            try{

                progress.setMessage("Cargando tareas de cambio de ubicación");

                if(pListaPedidoDet != null) {

                    if(pListaPedidoDet.items!=null ){

                        for (int i = 0; i<=pListaPedidoDet.items.size()-1; i++) {

                            vItem = new clsBeDetallePedidoAVerificar();

                           vItem.IdPedidoEnc = pListaPedidoDet.items.get(i).getIdPedidoEnc();
                            vItem.IdPedidoDet = pListaPedidoDet.items.get(i).getIdPedidoDet();
                            vItem.Codigo = pListaPedidoDet.items.get(i).getCodigo();
                            vItem.Nombre_Producto = pListaPedidoDet.items.get(i).getNombre_Producto();
                            vItem.Lote =  pListaPedidoDet.items.get(i).getLote();
                            vItem.Fecha_Vence = pListaPedidoDet.items.get(i).getFecha_Vence();
                            vItem.LicPlate = pListaPedidoDet.items.get(i).getLicPlate();
                            vItem.Nom_Unid_Med = pListaPedidoDet.items.get(i).getNom_Unid_Med();
                            vItem.Nom_Presentacion = pListaPedidoDet.items.get(i).getNom_Presentacion();
                            vItem.Cantidad_Solicitada = pListaPedidoDet.items.get(i).getCantidad_Solicitada();
                            vItem.Cantidad_Recibida = pListaPedidoDet.items.get(i).getCantidad_Recibida();
                            vItem.Cantidad_Verificada = pListaPedidoDet.items.get(i).getCantidad_Verificada();
                            vItem.Nom_Estado = pListaPedidoDet.items.get(i).getNom_Estado();
                            vItem.IdPresentacion = pListaPedidoDet.items.get(i).getIdPresentacion();
                            vItem.IdProductoBodega = pListaPedidoDet.items.get(i).getIdProductoBodega();
                            vItem.NDias = pListaPedidoDet.items.get(i).getNDias();

                            pListBeTareasVerificacionHH.add(vItem);

                        }

                        //lblRegs.setText("Regs: "+pListaPedidoDet.items.size());

                        adapter=new list_adapt_detalle_tareas_verificacion(this,pListBeTareasVerificacionHH);

                        listDetVeri.setAdapter(adapter);

                        if (pListaPedidoDet.items.size()>1){

                            adapter.setSelectedIndex(-1);
                            index = -1;
                        }

                    }

                }else{
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

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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
           // Load();
            super.onResume();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

}
