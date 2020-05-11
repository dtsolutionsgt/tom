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

    private ArrayList<clsBeTrans_pe_enc> BeListTareasHH;
    private Context cCont;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

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

                holder.lblPedEnc = (TextView) convertView.findViewById(R.id.lblPedEnc);
                holder.lblReferencia = (TextView) convertView.findViewById(R.id.lblReferencia);
                holder.lblMuelle = (TextView) convertView.findViewById(R.id.lblMuelle);
                holder.lblIdCliente = (TextView) convertView.findViewById(R.id.lblIdCliente);
                holder.lblCliente = (TextView) convertView.findViewById(R.id.lblCliente);
                holder.lblEstado = (TextView) convertView.findViewById(R.id.lblEstado);
                holder.lblIdPickingEnc = (TextView) convertView.findViewById(R.id.lblIdPickingEnc);

                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.lblPedEnc.setText(""+BeListTareasHH.get(position).IdPedidoEnc);
            holder.lblReferencia.setText(""+BeListTareasHH.get(position).Referencia);
            holder.lblMuelle.setText(""+BeListTareasHH.get(position).IdMuelle);
            holder.lblIdCliente.setText(""+BeListTareasHH.get(position).IdCliente);
            holder.lblCliente.setText(""+BeListTareasHH.get(position).Cliente.Nombre_comercial);
            holder.lblEstado.setText(""+BeListTareasHH.get(position).Estado);
            holder.lblIdPickingEnc.setText(""+BeListTareasHH.get(position).IdPickingEnc);

            LinearLayout encabezado = (LinearLayout) convertView.findViewById(R.id.linearEncCB);

            if (position>0){
                encabezado.setVisibility(View.GONE);
            }else{
                encabezado.setVisibility(View.VISIBLE);
            }

            if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
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
        TextView lblPedEnc,lblReferencia,lblMuelle,lblIdCliente,lblCliente,lblEstado,lblIdPickingEnc;
    }
}
