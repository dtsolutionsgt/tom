package com.dts.ladapt.ConsultaStock;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.ladapt.Verificacion.list_adapt_tareas_verificacion;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_consulta_stock extends BaseAdapter {

    private ArrayList<clsBeVW_stock_res> BeListStock;
    private Context cCont;
    private int selectedIndex;
    private LayoutInflater l_Inflater;

    public list_adapt_consulta_stock(Context context, ArrayList<clsBeVW_stock_res> results){
        BeListStock = results;
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

    @Override
    public int getCount() {

        return BeListStock.size();
    }

    @Override
    public Object getItem(int position) {
        return BeListStock.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            ViewHolder holder;

            if (convertView == null) {

                convertView = l_Inflater.inflate(R.layout.activity_frm_consulta_stock, null);
                holder = new ViewHolder();

                /*holder = new list_adapt_tareas_verificacion.ViewHolder();
                holder.lblPedEnc = (TextView) convertView.findViewById(R.id.lblPedEnc);
                holder.lblReferencia = (TextView) convertView.findViewById(R.id.lblReferencia);
                holder.lblMuelle = (TextView) convertView.findViewById(R.id.lblMuelle);
                holder.lblIdCliente = (TextView) convertView.findViewById(R.id.lblIdCliente);
                holder.lblCliente = (TextView) convertView.findViewById(R.id.lblCliente);
                holder.lblEstado = (TextView) convertView.findViewById(R.id.lblEstado);
                holder.lblIdPickingEnc = (TextView) convertView.findViewById(R.id.lblIdPickingEnc);*/

                convertView.setTag(holder);

            }else {
                holder = (list_adapt_consulta_stock.ViewHolder) convertView.getTag();
            }

        }
        catch (Exception ex){
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
