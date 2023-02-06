package com.dts.tom.Transacciones.Verificacion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.gBeProducto;

public class frm_list_prod_reemplazados extends PBase {

    private frm_list_prod_reemplazados.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private TextView lblTituloForma,lblCantRegs;
    private ListView listReemplazados;
    private Button btnActReemplazados,btnBack;

    private clsBeDetallePedidoAVerificarList pListaReemplazados = new clsBeDetallePedidoAVerificarList();
    private list_adapt_detalle_tareas_verificacion adapter;

    private ArrayList<clsBeDetallePedidoAVerificar> pListBeReemplazados= new ArrayList<clsBeDetallePedidoAVerificar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_prod_reemplazados);

        super.InitBase();

        ws = new frm_list_prod_reemplazados.WebServiceHandler(frm_list_prod_reemplazados.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView)findViewById(R.id.lblTituloForma);
        lblCantRegs = (TextView)findViewById(R.id.lblCantRegs);

        listReemplazados = (ListView)findViewById(R.id.listReemplazados);

        btnActReemplazados = (Button)findViewById(R.id.btnActReemplazados);
        btnBack = (Button)findViewById(R.id.btnBack);

        setHandlers();

        ProgressDialog("Listando existencias de producto:"+gBeProducto.Codigo);

        //Llama el método del WS Get_All_Stock_Especifico_By_IdPedidoDet
        execws(1);

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

            btnActReemplazados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Llama el método del WS Get_All_Stock_Especifico_By_IdPedidoDet
                    execws(1);

                }
            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
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
                    processReemplazos();
                    break;
            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }


    private void processReemplazos(){

        try {

            progress.setMessage("Obteniendo reemplazos del pedido...");

            pListaReemplazados = xobj.getresult(clsBeDetallePedidoAVerificarList.class,"Get_Reemplazo_Producto_By_IdPedidoEnc");

            listReemplazados.setAdapter(null);

            if(pListaReemplazados!=null){
                if(pListaReemplazados.items!=null){
                    Lista_Reemplazados();
                }
            }else{
                progress.cancel();
                toast("No hay productos reemplazados");
                finish();
            }

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Lista_Reemplazados(){

        try{

            progress.setMessage("Listando productos reemplazados...");

            clsBeDetallePedidoAVerificar vItem;
            pListBeReemplazados.clear();

            if(pListaReemplazados != null) {

                if(pListaReemplazados.items!=null ){

                    for (int i = 0; i<=pListaReemplazados.items.size()-1; i++) {

                        vItem = new clsBeDetallePedidoAVerificar();
                        vItem.IdPedidoEnc = pListaReemplazados.items.get(i).getIdPedidoEnc();
                        vItem.IdPedidoDet = pListaReemplazados.items.get(i).getIdPedidoDet();
                        vItem.IdProductoBodega = pListaReemplazados.items.get(i).getIdProductoBodega();
                        vItem.Codigo = pListaReemplazados.items.get(i).getCodigo();
                        vItem.Nombre_Producto = pListaReemplazados.items.get(i).getNombre_Producto();
                        vItem.Lote =  pListaReemplazados.items.get(i).getLote();
                        vItem.Fecha_Vence = app.strFecha(pListaReemplazados.items.get(i).getFecha_Vence());
                        vItem.LicPlate = pListaReemplazados.items.get(i).getLicPlate();
                        vItem.Nom_Unid_Med = pListaReemplazados.items.get(i).getNom_Unid_Med();
                        vItem.Nom_Presentacion = pListaReemplazados.items.get(i).getNom_Presentacion();
                        vItem.Cantidad_Solicitada = pListaReemplazados.items.get(i).getCantidad_Solicitada();
                        vItem.Cantidad_Recibida = pListaReemplazados.items.get(i).getCantidad_Recibida();
                        vItem.Cantidad_Verificada = pListaReemplazados.items.get(i).getCantidad_Verificada();
                        vItem.Nom_Estado = pListaReemplazados.items.get(i).getNom_Estado();
                        vItem.IdPresentacion = pListaReemplazados.items.get(i).getIdPresentacion();
                        vItem.IdUnidadMedidaBasica = pListaReemplazados.items.get(i).getIdUnidadMedidaBasica();
                        vItem.NDias = pListaReemplazados.items.get(i).getNDias();

                        pListBeReemplazados.add(vItem);

                    }

                    lblCantRegs.setText("Regs: "+pListaReemplazados.items.size());

                    adapter=new list_adapt_detalle_tareas_verificacion(this,pListBeReemplazados);

                    listReemplazados.setAdapter(adapter);

                    if (pListaReemplazados.items.size()>0){

                        adapter.setSelectedIndex(-1);
                    }
                }

            }else{
                listReemplazados.setAdapter(null);
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

        progress.cancel();

    }

    public void Regresar(View view){
        finish();
    }

}
