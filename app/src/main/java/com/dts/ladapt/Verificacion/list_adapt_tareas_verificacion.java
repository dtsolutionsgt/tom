package com.dts.ladapt.Verificacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc.clsBeTrans_pe_enc;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_tareas_verificacion extends BaseAdapter {

    private final ArrayList<clsBeTrans_pe_enc> BeListTareasHH;
    private final Context cCont;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_tareas_verificacion(Context context, ArrayList<clsBeTrans_pe_enc> results) {
        BeListTareasHH = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
        cCont = context;
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public void refreshItems() {
        notifyDataSetChanged();
    }

    public int getCount() {
        return BeListTareasHH.size();
    }

    public Object getItem(int position) {
        return BeListTareasHH.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{

            ViewHolder holder;

            if (convertView == null) {

                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_tareas_verificacion, null);
                holder = new ViewHolder();

                holder.lblPedEnc = convertView.findViewById(R.id.lblPedEnc);
                holder.lblReferencia = convertView.findViewById(R.id.lblReferencia);
                holder.lblMuelle = convertView.findViewById(R.id.lblMuelle);
                holder.lblIdCliente = convertView.findViewById(R.id.lblIdCliente);
                holder.lblCliente = convertView.findViewById(R.id.lblCliente);
                holder.lblEstado = convertView.findViewById(R.id.lblEstado);
                holder.lblIdPickingEnc = convertView.findViewById(R.id.lblIdPickingEnc);
                holder.lblFechaPedido = convertView.findViewById(R.id.lblFechaPedido);
                holder.lblRutaDespacho = convertView.findViewById(R.id.lblRutaDespacho);
                holder.lblObservaciones = convertView.findViewById(R.id.lblObservaciones);
                holder.lblRoadDirEntrega = convertView.findViewById(R.id.lblRoadDirEntrega);
                holder.lblRequiereTarima = convertView.findViewById(R.id.lblRequiereTarima);

                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.lblPedEnc.setText(""+BeListTareasHH.get(position).IdPedidoEnc);
            holder.lblFechaPedido.setText(""+BeListTareasHH.get(position).Fecha_Pedido);
            holder.lblReferencia.setText(""+BeListTareasHH.get(position).Referencia);
            holder.lblMuelle.setText(""+BeListTareasHH.get(position).IdMuelle);

            if (!BeListTareasHH.get(position).NombreRutaDespacho.isEmpty()) {
                holder.lblRutaDespacho.setText("" + BeListTareasHH.get(position).NombreRutaDespacho);
            } else {
                holder.lblRutaDespacho.setText("--");
            }

            if (!BeListTareasHH.get(position).Observacion.isEmpty()) {
                holder.lblObservaciones.setText("" + BeListTareasHH.get(position).Observacion);
            } else {
                holder.lblObservaciones.setText("--");
            }

            if (!BeListTareasHH.get(position).RoadDirEntrega.isEmpty()) {
                holder.lblRoadDirEntrega.setText("" + BeListTareasHH.get(position).RoadDirEntrega);
            } else {
                holder.lblRoadDirEntrega.setText("--");
            }

            if (BeListTareasHH.get(position).Requiere_Tarimas) {
                holder.lblRequiereTarima.setText("Si");
            } else {
                holder.lblRequiereTarima.setText("No");
            }

            holder.lblIdCliente.setText(""+BeListTareasHH.get(position).IdCliente);
            holder.lblCliente.setText(""+BeListTareasHH.get(position).Cliente.Nombre_comercial);
            holder.lblEstado.setText(""+BeListTareasHH.get(position).Estado);
            holder.lblIdPickingEnc.setText(""+BeListTareasHH.get(position).IdPickingEnc);

            if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

            //GT15012024: se asignan los colores de acuerdo al estado del pickeo para facilitar la verificación
            String estado = BeListTareasHH.get(position).Estado;

            if (estado.equals("Pendiente") ) {
                //Rojo - Pendiente
                convertView.setBackgroundColor(Color.parseColor("#F5FFAE"));
            } else if(estado.equals("Pickeado")) {
                //Celeste - Faltante en verificación
                convertView.setBackgroundColor(Color.parseColor("#00E676"));
            }

        }catch (Exception ex){
            toast(ex.getMessage());
        }
        return convertView;
    }

    public void toast(String msg) {
        Toast.makeText(cCont, msg, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        TextView lblPedEnc,lblFechaPedido,lblReferencia,lblMuelle, lblRutaDespacho,lblIdCliente,
                lblCliente,lblEstado,lblIdPickingEnc, lblObservaciones, lblRequiereTarima, lblRoadDirEntrega;
    }
}
