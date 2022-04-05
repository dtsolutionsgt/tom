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

import java.util.ArrayList;

public class list_adapt_detalle_tareas_verificacion extends BaseAdapter {

    private ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_detalle_tareas_verificacion(Context context, ArrayList<clsBeDetallePedidoAVerificar> results) {
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
                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_tareas_verificacion, null);
                holder = new ViewHolder();

                holder.lblPedidoEnc = (TextView) convertView.findViewById(R.id.lblPedEnc);
                holder.lblPedidoDet = (TextView) convertView.findViewById(R.id.lblPedDet);
                holder.lblCodigo = (TextView) convertView.findViewById(R.id.lblCodigo);
                holder.lblProducto = (TextView) convertView.findViewById(R.id.lblProducto);
                holder.lblLote = (TextView) convertView.findViewById(R.id.lblLote);
                holder.lblVence = (TextView) convertView.findViewById(R.id.lblVence);
                holder.lblLicPlate = (TextView) convertView.findViewById(R.id.lblLicPlate);
                holder.lblUmBas = (TextView) convertView.findViewById(R.id.lblUmBas);
                holder.lblPresentacion = (TextView) convertView.findViewById(R.id.lblPresentacion);
                holder.lblSolicitado = (TextView) convertView.findViewById(R.id.lblSolicitado);
                holder.lblPickeado = (TextView) convertView.findViewById(R.id.lblPickeado);
                holder.lblVerificado = (TextView) convertView.findViewById(R.id.lblVerificado);
                holder.lblEstado = (TextView) convertView.findViewById(R.id.lblEstado);
                holder.lblIdPresentacion = (TextView) convertView.findViewById(R.id.lblIdPresentacion);
                holder.lblIdProductoBodega = (TextView) convertView.findViewById(R.id.lblIdProductoBodega);
                holder.lblNDias = (TextView) convertView.findViewById(R.id.lblNDias);
                holder.lblArea = (TextView) convertView.findViewById(R.id.lblArea);
                holder.lblClasificacion = (TextView) convertView.findViewById(R.id.lblClasificacion);

                holder.lblEPedidoEnc = (TextView) convertView.findViewById(R.id.lblEPedEnc);
                holder.lblEPedidoDet = (TextView) convertView.findViewById(R.id.lblEPedDet);
                holder.lblEIdPresentacion = (TextView) convertView.findViewById(R.id.lblEIdPresentacion);
                holder.lblEIdProductoBodega = (TextView) convertView.findViewById(R.id.lblEIdProductoBodega);
                holder.lblENDias = (TextView) convertView.findViewById(R.id.lblENDias);

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

            if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }catch (Exception ex){

        }
        return convertView;
    }

    static class ViewHolder {
        TextView lblPedidoEnc,lblPedidoDet,lblCodigo,lblProducto,lblLote,lblVence,lblLicPlate,lblUmBas,lblPresentacion,
                lblSolicitado,lblPickeado, lblVerificado, lblEstado, lblIdPresentacion, lblIdProductoBodega, lblNDias,
                lblEPedidoEnc,lblEPedidoDet, lblEIdPresentacion, lblEIdProductoBodega, lblENDias, lblArea, lblClasificacion;
    }

}
