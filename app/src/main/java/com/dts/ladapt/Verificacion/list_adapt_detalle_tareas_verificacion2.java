package com.dts.ladapt.Verificacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.tom.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class list_adapt_detalle_tareas_verificacion2 extends BaseAdapter {

    private ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_detalle_tareas_verificacion2(Context context, ArrayList<clsBeDetallePedidoAVerificar> results) {
        pListBeTareasVerificacionHH = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public void refreshItems() {
        notifyDataSetChanged();
    }

    public int getCount() {
        return pListBeTareasVerificacionHH.size();
    }

    public Object getItem(int position) {
        return pListBeTareasVerificacionHH.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{
            ViewHolder holder;

            if (convertView == null) {
                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_tareas_verificacion2, null);
                holder = new ViewHolder();

                holder.lblPedidoEnc = convertView.findViewById(R.id.lblPedEnc);
                holder.lblPedidoDet = convertView.findViewById(R.id.lblPedDet);
                holder.lblCodigo = convertView.findViewById(R.id.lblCodigo);
                holder.lblProducto = convertView.findViewById(R.id.lblProducto);
                holder.lblLote = convertView.findViewById(R.id.lblLote);
                holder.lblVence = convertView.findViewById(R.id.lblVence);
                holder.lblLicPlate = convertView.findViewById(R.id.lblLicPlate);
                holder.lblUmBas = convertView.findViewById(R.id.lblUmBas);
                holder.lblPresentacion = convertView.findViewById(R.id.lblPresentacion);
                holder.lblSolicitado = convertView.findViewById(R.id.lblSolicitado);
                holder.lblPickeado = convertView.findViewById(R.id.lblPickeado);
                holder.lblVerificado = convertView.findViewById(R.id.lblVerificado);
                holder.lblEstado = convertView.findViewById(R.id.lblEstado);
                holder.lblIdPresentacion = convertView.findViewById(R.id.lblIdPresentacion);
                holder.lblIdProductoBodega = convertView.findViewById(R.id.lblIdProductoBodega);
                holder.lblNDias = convertView.findViewById(R.id.lblNDias);
                holder.lblArea = convertView.findViewById(R.id.lblArea);
                holder.lblClasificacion = convertView.findViewById(R.id.lblClasificacion);
                holder.lblEstadoVer = convertView.findViewById(R.id.lblEstadoVer);

                holder.lblEPedidoEnc = convertView.findViewById(R.id.lblEPedEnc);
                holder.lblEPedidoDet = convertView.findViewById(R.id.lblEPedDet);
                holder.lblEIdPresentacion = convertView.findViewById(R.id.lblEIdPresentacion);
                holder.lblEIdProductoBodega = convertView.findViewById(R.id.lblEIdProductoBodega);
                holder.lblENDias = convertView.findViewById(R.id.lblENDias);

                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.lblPedidoEnc.setText(""+pListBeTareasVerificacionHH.get(position).IdPedidoEnc);
            holder.lblPedidoDet.setText(""+pListBeTareasVerificacionHH.get(position).IdPedidoDet);
            holder.lblCodigo.setText(""+pListBeTareasVerificacionHH.get(position).Codigo);
            holder.lblProducto.setText(""+pListBeTareasVerificacionHH.get(position).Nombre_Producto);
            holder.lblLote.setText(""+pListBeTareasVerificacionHH.get(position).Lote);
            holder.lblVence.setText(""+pListBeTareasVerificacionHH.get(position).Fecha_Vence);
            holder.lblLicPlate.setText(""+pListBeTareasVerificacionHH.get(position).LicPlate);
            holder.lblUmBas.setText(""+pListBeTareasVerificacionHH.get(position).Nom_Unid_Med);
            holder.lblPresentacion.setText(""+pListBeTareasVerificacionHH.get(position).Nom_Presentacion);
            holder.lblSolicitado.setText(""+pListBeTareasVerificacionHH.get(position).Cantidad_Solicitada);
            holder.lblPickeado.setText(""+pListBeTareasVerificacionHH.get(position).Cantidad_Recibida);
            holder.lblVerificado.setText(""+pListBeTareasVerificacionHH.get(position).Cantidad_Verificada);
            holder.lblEstado.setText(""+pListBeTareasVerificacionHH.get(position).Nom_Estado);
            holder.lblIdPresentacion.setText(""+pListBeTareasVerificacionHH.get(position).IdPresentacion);
            holder.lblIdProductoBodega.setText(""+pListBeTareasVerificacionHH.get(position).IdProductoBodega);
            holder.lblNDias.setText(""+pListBeTareasVerificacionHH.get(position).NDias);
            holder.lblArea.setText(""+pListBeTareasVerificacionHH.get(position).NombreArea);
            holder.lblClasificacion.setText(""+pListBeTareasVerificacionHH.get(position).NombreClasificacion);

            holder.lblPedidoEnc.setVisibility(View.GONE);
            holder.lblPedidoDet.setVisibility(View.GONE);
            holder.lblIdProductoBodega.setVisibility(View.GONE);
            holder.lblIdPresentacion.setVisibility(View.GONE);
            holder.lblNDias.setVisibility(View.GONE);

            /*if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }*/

            //#AT20220531 Colores y estados según la cantidad solicitada, pickeada y verificada.
            double Pick  = pListBeTareasVerificacionHH.get(position).Cantidad_Recibida;
            double Sol = pListBeTareasVerificacionHH.get(position).Cantidad_Solicitada;
            double Ver = pListBeTareasVerificacionHH.get(position).Cantidad_Verificada;

            if (Pick == 0 && Ver == 0) {
                //Rojo - No Pickeado
                convertView.setBackgroundColor(Color.parseColor("#EF5350"));
                holder.lblEstadoVer.setText("No pickeado");
            } else if(Pick == Sol && Ver < Pick && Ver != 0) {
                //Celeste - Faltante en verificación
                convertView.setBackgroundColor(Color.parseColor("#81D4FA"));
                holder.lblEstadoVer.setText("Faltante en verificación");
            } else if ((Pick < Sol && Ver == Pick) || (Pick < Sol && Ver < Pick && Ver != 0)) {
                //Naranja - Faltante en Picking
                convertView.setBackgroundColor(Color.parseColor("#FFCA28"));
                holder.lblEstadoVer.setText("Faltante en picking");
            }else if((Pick > 0 && Ver == 0) || (Pick > 0 && Ver < Pick)) {
                //Amarillo - Pendiente de Verificar
                convertView.setBackgroundColor(Color.parseColor("#F5FFAE"));
                holder.lblEstadoVer.setText("Pendiente de verificar");
            } else if (Pick == Sol && Ver == Sol) {
                //Verde - Verificación completa
                convertView.setBackgroundColor(Color.parseColor("#00E676"));
                holder.lblEstadoVer.setText("Verificación completa");
            }


        }catch (Exception ex){

        }
        return convertView;
    }

    static class ViewHolder {
        TextView lblPedidoEnc,lblPedidoDet,lblCodigo,lblProducto,lblLote,lblVence,lblLicPlate,lblUmBas,lblPresentacion,
                lblSolicitado,lblPickeado, lblVerificado, lblEstado, lblIdPresentacion, lblIdProductoBodega, lblNDias,
                lblEPedidoEnc,lblEPedidoDet, lblEIdPresentacion, lblEIdProductoBodega, lblENDias, lblArea, lblClasificacion,
                lblEstadoVer;
    }

}
