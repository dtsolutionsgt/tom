package com.dts.tom.Transacciones.Verificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion;
import com.dts.tom.PBase;
import com.dts.tom.R;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.pSubListPickingU;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.BePedidoDetVerif;
import static br.com.zbra.androidlinq.Linq.stream;

import java.util.ArrayList;
import java.util.List;


public class frm_verificacion_consolidada_detalle extends PBase {

    private ListView ListVeri;
    private Button btnRegs;

    private frm_verificacion_consolidada_detalle.WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private final ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();

    private clsBeDetallePedidoAVerificar selitem;
    private list_adapt_detalle_tareas_verificacion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_verificacion_consolidada_detalle);

        super.InitBase();
        ws = new frm_verificacion_consolidada_detalle.WebServiceHandler(frm_verificacion_consolidada_detalle.this, gl.wsurl);
        xobj = new XMLObject(ws);

        ListVeri = findViewById(R.id.ListDetVeri);
        btnRegs = findViewById(R.id.btnRegs);

        ProgressDialog("Cargando datos...");
        Load();
        setHandlers();
    }

    private void Load() {
        try {
            Lista_Detalle_Pedido();
        } catch (Exception e) {
            mu.msgbox("Error Load: "+e.getMessage());
        }
    }

    private void Procesa_Registro(clsBeDetallePedidoAVerificar TareaDet){
        List AuxList;
        try{

            gl.gBePedidoDetVerif=TareaDet;
            frm_verificacion_datos.BePedidoDetVerif = gl.gBePedidoDetVerif;

            if (TareaDet.LicPlate.equals("")) {
                AuxList = stream(frm_verificacion_datos.pSubListPickingU.items)
                        .where(z -> z.CodigoProducto.equals(TareaDet.Codigo))
                        .where(z -> z.Lote.equals(TareaDet.Lote))
                        .where(z -> app.strFecha(z.Fecha_Vence).equals(TareaDet.Fecha_Vence))
                        .toList();
            } else {
                AuxList = stream(frm_verificacion_datos.pSubListPickingU.items)
                        .where(z -> z.CodigoProducto.equals(TareaDet.Codigo))
                        .where(z -> z.Lote.equals(TareaDet.Lote))
                        .where(z -> z.Lic_plate.equals(TareaDet.LicPlate))
                        .where(z -> app.strFecha(z.Fecha_Vence).equals(TareaDet.Fecha_Vence))
                        .toList();
            }

            pSubListPickingU.items = AuxList;

            startActivity(new Intent(this, frm_danado_verificacion.class));
            super.finish();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void Procesa_Registro(){
        clsBeDetallePedidoAVerificar vItem;
        try{

            vItem = new clsBeDetallePedidoAVerificar();

            vItem.IdPedidoEnc = pSubListPickingU.items.get(0).getIdPedidoEnc();
            vItem.IdPedidoDet = pSubListPickingU.items.get(0).getIdPedidoDet();
            vItem.IdProductoBodega = pSubListPickingU.items.get(0).getIdProductoBodega();
            vItem.Codigo = pSubListPickingU.items.get(0).getCodigoProducto();
            vItem.Nombre_Producto = pSubListPickingU.items.get(0).NombreProducto;
            vItem.Lote = pSubListPickingU.items.get(0).getLote();
            vItem.Fecha_Vence = app.strFecha(pSubListPickingU.items.get(0).getFecha_Vence());
            vItem.LicPlate = pSubListPickingU.items.get(0).getLic_plate();
            vItem.Nom_Unid_Med = pSubListPickingU.items.get(0).ProductoUnidadMedida;
            vItem.Nom_Presentacion = pSubListPickingU.items.get(0).ProductoPresentacion;
            vItem.Cantidad_Solicitada = pSubListPickingU.items.get(0).getCantidad_Solicitada();
            vItem.Cantidad_Recibida = pSubListPickingU.items.get(0).getCantidad_Recibida();
            vItem.Cantidad_Verificada = pSubListPickingU.items.get(0).getCantidad_Verificada();
            vItem.Nom_Estado = pSubListPickingU.items.get(0).ProductoEstado;
            vItem.IdPresentacion = pSubListPickingU.items.get(0).getIdPresentacion();
            vItem.IdUnidadMedidaBasica = pSubListPickingU.items.get(0).IdUnidadMedida;
            vItem.NDias = 0;
            vItem.IdProductoEstado = pSubListPickingU.items.get(0).getIdProductoEstado();
            vItem.NombreArea = pSubListPickingU.items.get(0).getNombreArea();
            vItem.NombreClasificacion = pSubListPickingU.items.get(0).getNombreClasificacion();

            frm_verificacion_datos.BePedidoDetVerif = vItem;

            startActivity(new Intent(this, frm_danado_verificacion.class));
            super.finish();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void Lista_Detalle_Pedido(){
        try {

            progress.setMessage("Listando detalle de pedido para verificación en HH...");

            clsBeDetallePedidoAVerificar vItem;
            pListBeTareasVerificacionHH.clear();selidx = -1;

            try{

                progress.setMessage("Cargando tareas de verificación");

                //#AT20230112 Se filtra la lista por idpresentacion
                pSubListPickingU.items = stream(pSubListPickingU.items)
                                        .where(c-> c.IdProductoBodega == BePedidoDetVerif.IdProductoBodega)
                                        .where(c-> c.CodigoProducto.equals(BePedidoDetVerif.Codigo))
                                        .where(c->c.IdPresentacion == BePedidoDetVerif.IdPresentacion).toList();

                if (pSubListPickingU!= null) {

                    if (pSubListPickingU.items!=null ) {

                        if (pSubListPickingU.items.size() > 1) {
                            for (int i = 0; i <= pSubListPickingU.items.size() - 1; i++) {

                                vItem = new clsBeDetallePedidoAVerificar();

                                vItem.IdPedidoEnc = pSubListPickingU.items.get(i).getIdPedidoEnc();
                                vItem.IdPedidoDet = pSubListPickingU.items.get(i).getIdPedidoDet();
                                vItem.IdProductoBodega = pSubListPickingU.items.get(i).getIdProductoBodega();
                                vItem.Codigo = pSubListPickingU.items.get(i).getCodigoProducto();
                                vItem.Nombre_Producto = pSubListPickingU.items.get(i).NombreProducto;
                                vItem.Lote = pSubListPickingU.items.get(i).getLote();
                                vItem.Fecha_Vence = app.strFecha(pSubListPickingU.items.get(i).getFecha_Vence());
                                vItem.LicPlate = pSubListPickingU.items.get(i).getLic_plate();
                                vItem.Nom_Unid_Med = pSubListPickingU.items.get(i).ProductoUnidadMedida;
                                vItem.Nom_Presentacion = pSubListPickingU.items.get(i).ProductoPresentacion;
                                vItem.Cantidad_Solicitada = pSubListPickingU.items.get(i).getCantidad_Solicitada();
                                vItem.Cantidad_Recibida = pSubListPickingU.items.get(i).getCantidad_Recibida();
                                vItem.Cantidad_Verificada = pSubListPickingU.items.get(i).getCantidad_Verificada();
                                vItem.Nom_Estado = pSubListPickingU.items.get(i).ProductoEstado;
                                vItem.IdPresentacion = pSubListPickingU.items.get(i).getIdPresentacion();
                                vItem.IdUnidadMedidaBasica = pSubListPickingU.items.get(i).IdUnidadMedida;
                                vItem.NDias = 0;
                                vItem.IdProductoEstado = pSubListPickingU.items.get(i).getIdProductoEstado();
                                vItem.NombreArea = pSubListPickingU.items.get(i).getNombreArea();
                                vItem.NombreClasificacion = pSubListPickingU.items.get(i).getNombreClasificacion();

                                pListBeTareasVerificacionHH.add(vItem);

                                if (vItem.LicPlate.equalsIgnoreCase(gl.gLP)) {
                                    selitem = vItem;
                                    selidx = i;
                                }
                            }

                            btnRegs.setText("Registros: " + pSubListPickingU.items.size());

                            adapter = new list_adapt_detalle_tareas_verificacion(this, pListBeTareasVerificacionHH);
                            ListVeri.setAdapter(adapter);

                        } else {
                            Procesa_Registro();
                        }
                    }
                } else {
                    ListVeri.setAdapter(null);
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

    private void setHandlers() {
        try {

            ListVeri.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object lvObj = ListVeri.getItemAtPosition(position);
                    clsBeDetallePedidoAVerificar vItem = (clsBeDetallePedidoAVerificar) lvObj;

                    int index = position;
                    Procesa_Registro(vItem);

                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandlers: "+ e.getMessage());
        }
    }

    private void processGetDetalleVerificacion() {
        try {

        } catch (Exception e) {
            mu.msgbox("processGetDetalleVerificacion: "+ e.getMessage());
        }
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
                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                error=e.getMessage();errorflag =true;
                msgbox(error);
            }
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

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processGetDetalleVerificacion();
                    break;
            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    public void Regresar(View view) {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}