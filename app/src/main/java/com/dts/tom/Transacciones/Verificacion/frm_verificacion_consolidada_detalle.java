package com.dts.tom.Transacciones.Verificacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion2;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion3;
import com.dts.ladapt.Verificacion.list_adapt_detalle_tareas_verificacion4;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.google.android.material.button.MaterialButton;

import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.plistPickingUbi;

import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.AuxCantReemplazar;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.pSubListPickingU;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.BePedidoDetVerif;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.CantReemplazar;
import static br.com.zbra.androidlinq.Linq.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class frm_verificacion_consolidada_detalle extends PBase {

    private ListView ListVeri;
    private TextView txtDetalleProd;
    private Button btnRegs;
    private TextView txtMensaje;
    private MaterialButton btnAceptar, btnCambiar;
    private ImageView icono;

    private frm_verificacion_consolidada_detalle.WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private final ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();
    private clsBeDetallePedidoAVerificarList pListaPedidoDet = new clsBeDetallePedidoAVerificarList();
    private list_adapt_detalle_tareas_verificacion adapter;
    private clsBeDetallePedidoAVerificar selItem = new clsBeDetallePedidoAVerificar();
    public static boolean unico = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_verificacion_consolidada_detalle);

        super.InitBase();
        ws = new frm_verificacion_consolidada_detalle.WebServiceHandler(frm_verificacion_consolidada_detalle.this, gl.wsurl);
        xobj = new XMLObject(ws);

        ListVeri = findViewById(R.id.ListDetVeri);
        btnRegs = findViewById(R.id.btnRegs);
        txtDetalleProd = findViewById(R.id.txtDetalleProd);

        txtDetalleProd.setText(BePedidoDetVerif.Codigo+" - "+BePedidoDetVerif.Nombre_Producto+
                "\n Cant. Reemplazar: "+CantReemplazar+" "+BePedidoDetVerif.Nom_Unid_Med);

        ProgressDialog("Cargando datos...");
        Load();
        setHandlers();
    }

    private void Load() {
        try {

            pSubListPickingU.items = stream(pSubListPickingU.items)
                    .where(c-> c.IdProductoBodega == BePedidoDetVerif.IdProductoBodega)
                    .where(c-> c.CodigoProducto.equals(BePedidoDetVerif.Codigo))
                    .where(c->c.IdPresentacion == BePedidoDetVerif.IdPresentacion)
                    .where(c->c.Cantidad_Recibida > 0)
                    .toList();

            if (pSubListPickingU.items.size() == 1 ){
                unico = true;
                Procesa_Registro();
            } else {
                execws(1);
            }
        } catch (Exception e) {
            mu.msgbox("Error Load: "+e.getMessage());
        }
    }

    private void Procesa_Registro(clsBeDetallePedidoAVerificar TareaDet){
        //List AuxList;
        try{
            gl.gBePedidoDetVerif = BePedidoDetVerif;
            BePedidoDetVerif = TareaDet;

            /*if (TareaDet.LicPlate.equals("")) {
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

            pSubListPickingU.items = AuxList;*/

            browse = 1;
            startActivity(new Intent(this, frm_danado_verificacion.class));
            super.finish();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void ValidaCantidad() {
        double cantidad = 0;
        try {
            if (CantReemplazar > selItem.Cantidad_Recibida) {
                if (selItem.Cantidad_Recibida > selItem.Cantidad_Verificada) {
                    cantidad = selItem.Cantidad_Recibida - selItem.Cantidad_Verificada;
                }

                mgsConfirmarCantidad("La cantidad selecionada ("+cantidad+") es menor a la cantidad a reemplazar ("+CantReemplazar+"). ¿Desea continuar?", cantidad);
            } else {
                Procesa_Registro(selItem);
            }
        } catch (Exception e) {
            msgbox(new Object() {} .getClass().getEnclosingMethod().getName() +" - "+ e.getMessage());
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

            BePedidoDetVerif = vItem;

            browse = 1;
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
            pListBeTareasVerificacionHH.clear();

            try{

                if (pListaPedidoDet!= null) {
                    if (pListaPedidoDet.items != null) {

                        for (int i = 0; i <= pListaPedidoDet.items.size() - 1; i++) {

                            vItem = new clsBeDetallePedidoAVerificar();

                            vItem.IdPedidoEnc = pListaPedidoDet.items.get(i).getIdPedidoEnc();
                            vItem.IdPedidoDet = pListaPedidoDet.items.get(i).getIdPedidoDet();
                            vItem.IdProductoBodega = pListaPedidoDet.items.get(i).getIdProductoBodega();
                            vItem.Codigo = pListaPedidoDet.items.get(i).getCodigo();
                            vItem.Nombre_Producto = pListaPedidoDet.items.get(i).getNombre_Producto();
                            vItem.Lote = pListaPedidoDet.items.get(i).getLote();
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
                            vItem.IdProductoEstado = pListaPedidoDet.items.get(i).getIdProductoEstado();
                            vItem.NombreArea = pListaPedidoDet.items.get(i).getNombreArea();
                            vItem.NombreClasificacion = pListaPedidoDet.items.get(i).getNombreClasificacion();

                            pListBeTareasVerificacionHH.add(vItem);

                        }

                        btnRegs.setText("Registros: " + pListaPedidoDet.items.size());
                        adapter = new list_adapt_detalle_tareas_verificacion(this, pListBeTareasVerificacionHH);
                        ListVeri.setAdapter(adapter);

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
                    selItem = (clsBeDetallePedidoAVerificar) lvObj;

                    int index = position;
                    ValidaCantidad();
                    //Procesa_Registro(vItem);

                }
            });

        } catch (Exception e) {
            mu.msgbox("setHandlers: "+ e.getMessage());
        }
    }

    private void processGetDetalleVerificacion() {
        try {
            progress.setMessage("Obteniendo detalle consolidado...");

            pListaPedidoDet = xobj.getresult(clsBeDetallePedidoAVerificarList.class,"Get_Detalle_Verificacion_Consolidada_LFV");

            if (pListaPedidoDet!=null){
                if(pListaPedidoDet.items!=null) {
                    Lista_Detalle_Pedido();
                } else {
                    toast("No se encontraron registros.");
                }
            }
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
                        callMethod("Get_Detalle_Verificacion_Consolidada_LFV",
                                "pIdPedidoEnc",gl.pIdPedidoEnc,
                                "pIdProductoBodega", BePedidoDetVerif.IdProductoBodega,
                                "pIdPresentacion", BePedidoDetVerif.IdPresentacion);
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

    private void mgsConfirmarCantidad(String msg, double cantidad) {

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
            LayoutInflater inflater = getLayoutInflater();
            View vistaDialog = inflater.inflate(R.layout.frm_fecha_vencimiento, null, false);


            txtMensaje = vistaDialog.findViewById(R.id.txtMensaje);
            btnAceptar = vistaDialog.findViewById(R.id.btnAceptar);
            btnCambiar = vistaDialog.findViewById(R.id.btnCambiar);
            icono = vistaDialog.findViewById(R.id.imageView21);
            dialog.setView(vistaDialog);

            dialog.setCancelable(false);
            dialog.setTitle(R.string.app_name);
            dialog.setIcon(R.drawable.ic_quest);

            icono.setImageResource( R.drawable.alert);

            //region botones
            btnCambiar.setText("CANCELAR");
            btnCambiar.setIconResource(R.drawable.cancel_btn);
            btnCambiar.setTextSize(13);
            ViewCompat.setBackgroundTintList(btnCambiar, ColorStateList.valueOf(Color.parseColor("#FFCC0000")));

            btnAceptar.setTextSize(13);
            ViewCompat.setBackgroundTintList(btnAceptar, ColorStateList.valueOf(Color.parseColor("#FF669900")));
            //endregion

            //region mensaje
            txtMensaje.setTextSize(25);
            txtMensaje.setText(msg);
            //endregion

            AlertDialog alert = dialog.create();

            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CantReemplazar = cantidad;
                    Procesa_Registro(selItem);
                    alert.cancel();
                }
            });

            btnCambiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.cancel();
                }
            });


            alert.show();

        }catch (Exception e){
            addlog(Objects.requireNonNull(new Object()
            {
            }.getClass().getEnclosingMethod()).getName(),e.getMessage(),"");
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void Regresar(View view) {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }

}