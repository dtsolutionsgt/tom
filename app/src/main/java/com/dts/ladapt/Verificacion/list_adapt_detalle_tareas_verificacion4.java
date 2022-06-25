package com.dts.ladapt.Verificacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.tom.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class list_adapt_detalle_tareas_verificacion4 extends BaseAdapter {

    private ArrayList<clsBeDetallePedidoAVerificar> pListBeTareasVerificacionHH= new ArrayList<clsBeDetallePedidoAVerificar>();

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_detalle_tareas_verificacion4(Context context, ArrayList<clsBeDetallePedidoAVerificar> results) {
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
    public View getView(int position, View convertView, ViewGroup pListBeTareasVerificacionHH) {

        try{
            ViewHolder holder;

            if (convertView == null) {
                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_tareas_verificacion4, null);
                holder = new ViewHolder();

                holder.txtArea = (TextView) convertView.findViewById(R.id.txtArea);
                holder.txtUbicacion = (TextView) convertView.findViewById(R.id.txtUbic);
                holder.txtLicencia = (TextView) convertView.findViewById(R.id.lblLicencia);
                holder.txtProdDoc = (TextView) convertView.findViewById(R.id.txtProdDoc);
                holder.txtClasificacion = (TextView) convertView.findViewById(R.id.txtClasificacion);
                holder.txtLote = (TextView) convertView.findViewById(R.id.txtLote);
                holder.txtVence = (TextView) convertView.findViewById(R.id.txtVence);
                holder.txtUm =(TextView) convertView.findViewById(R.id.txtUm);
                holder.relLicencia = (RelativeLayout) convertView.findViewById(R.id.relLicencia);
                holder.relTarima = (RelativeLayout) convertView.findViewById(R.id.relTarima);
                holder.relLote  = (RelativeLayout) convertView.findViewById(R.id.relLote);
                holder.relVence = (RelativeLayout) convertView.findViewById(R.id.relVence);
                holder.txtTarima = (TextView) convertView.findViewById(R.id.txtTarima);
                holder.txtEstado = (TextView) convertView.findViewById(R.id.txtEstado);
                holder.txtPeso = (TextView) convertView.findViewById(R.id.txtPeso);
                holder.txtPendiente  = (TextView) convertView.findViewById(R.id.txtPendiente);
                holder.txtVerificado = (TextView) convertView.findViewById(R.id.txtVerificado);
                holder.txtSolicitado = (TextView) convertView.findViewById(R.id.txtSolicitado);

                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtArea.setText(this.pListBeTareasVerificacionHH.get(position).NombreArea);
            if (!this.pListBeTareasVerificacionHH.get(position).LicPlate.isEmpty()) {
                holder.txtLicencia.setText(this.pListBeTareasVerificacionHH.get(position).LicPlate);
            } else {
                holder.relLicencia.setVisibility(View.GONE);
            }

            if (!this.pListBeTareasVerificacionHH.get(position).Lote.isEmpty()) {
                holder.txtLote.setText(this.pListBeTareasVerificacionHH.get(0).Lote);
            } else {
                holder.relLote.setVisibility(View.GONE);
            }

            if (this.pListBeTareasVerificacionHH.get(position).Fecha_Vence.equals("01/01/1900")) {
                holder.relVence.setVisibility(View.GONE);
            } else {
                holder.txtVence.setText(this.pListBeTareasVerificacionHH.get(0).Fecha_Vence);
            }

            holder.txtProdDoc.setText(this.pListBeTareasVerificacionHH.get(position).Codigo+" - "+this.pListBeTareasVerificacionHH.get(position).Nombre_Producto);

            String presentacion = this.pListBeTareasVerificacionHH.get(position).IdPresentacion > 0 ? this.pListBeTareasVerificacionHH.get(position).Nom_Presentacion:this.pListBeTareasVerificacionHH.get(position).Nom_Unid_Med;
            holder.txtUm.setText(presentacion);

            holder.txtSolicitado.setText(""+this.pListBeTareasVerificacionHH.get(position).Cantidad_Solicitada);
            holder.txtVerificado.setText(""+this.pListBeTareasVerificacionHH.get(position).Cantidad_Verificada);

            double pendiente = this.pListBeTareasVerificacionHH.get(position).Cantidad_Recibida - this.pListBeTareasVerificacionHH.get(position).Cantidad_Verificada;
            holder.txtPendiente.setText(""+pendiente);

            holder.txtEstado.setText(this.pListBeTareasVerificacionHH.get(position).Nom_Estado);
            holder.txtClasificacion.setText(this.pListBeTareasVerificacionHH.get(position).NombreClasificacion);

            /*if (this.pListBeTareasVerificacionHH.get(position).Cantidad_Verificada > 0) {
                convertView.setBackgroundColor(Color.parseColor("#FFF9C4"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#FFCDD2"));
            }*/

            //#AT20220531 Colores y estados según la cantidad solicitada, pickeada y verificada.
            double Pick  = this.pListBeTareasVerificacionHH.get(position).Cantidad_Recibida;
            double Sol = this.pListBeTareasVerificacionHH.get(position).Cantidad_Solicitada;
            double Ver = this.pListBeTareasVerificacionHH.get(position).Cantidad_Verificada;

            if (Pick == 0 && Ver == 0) {
                //Rojo - No Pickeado
                convertView.setBackgroundColor(Color.parseColor("#EF5350"));
            } else if(Pick == Sol && Ver < Pick && Ver != 0) {
                //Celeste - Faltante en verificación
                convertView.setBackgroundColor(Color.parseColor("#81D4FA"));
            } else if ((Pick < Sol && Ver == Pick) || (Pick < Sol && Ver < Pick && Ver != 0)) {
                //Naranja - Faltante en Picking
                convertView.setBackgroundColor(Color.parseColor("#FFCA28"));
            }else if((Pick > 0 && Ver == 0) || (Pick > 0 && Ver < Pick)) {
                //Amarillo - Pendiente de Verificar
                convertView.setBackgroundColor(Color.parseColor("#F5FFAE"));
            } else if (Pick == Sol && Ver == Sol) {
                //Verde - Verificación completa
                convertView.setBackgroundColor(Color.parseColor("#00E676"));
            }


        }catch (Exception ex){

        }
        return convertView;
    }

    static class ViewHolder {
        TextView txtArea, txtUbicacion, txtLicencia, txtProdDoc, txtClasificacion, txtLote, txtVence, txtCantidad, lblCantidad,
                txtPeso, txtTarima, txtEstado, txtUm, txtPendiente, txtSolicitado, txtVerificado;
        RelativeLayout relLicencia, relTarima, relLote, relVence;
    }

}
