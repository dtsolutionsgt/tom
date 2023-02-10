package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Picking.clsBeStockReemplazo;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_detalle_reemplazo_picking extends BaseAdapter {

    private static ArrayList<clsBeStockReemplazo> BeListTareasHH;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_detalle_reemplazo_picking(){}

    public list_adapt_detalle_reemplazo_picking(Context context, ArrayList<clsBeStockReemplazo> results) {
        BeListTareasHH = results;
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_reemplazo_picking, null);
            holder = new ViewHolder();

            holder.lblCodigoRe = convertView.findViewById(R.id.lblCodigoRe);
            holder.lblProductoRe = convertView.findViewById(R.id.lblProductoRe);
            holder.lblPresRe = convertView.findViewById(R.id.lblPresRe);
            holder.lblUmbasRe = convertView.findViewById(R.id.lblUmbasRe);
            holder.lblCantRe = convertView.findViewById(R.id.lblCantRe);
            holder.lblUbicRe = convertView.findViewById(R.id.lblUbicRe);
            holder.lblVenceRe = convertView.findViewById(R.id.lblVenceRe);
            holder.lblLpRe = convertView.findViewById(R.id.lblLpRe);
            holder.lblLoteRe = convertView.findViewById(R.id.lblLoteRe);
            holder.lblCodPrRe = convertView.findViewById(R.id.lblCodPrRe);
            holder.lblPesoRe = convertView.findViewById(R.id.lblPesoRe);
            holder.lblEstadoRe = convertView.findViewById(R.id.lblEstadoRe);
            holder.lblStock = convertView.findViewById(R.id.lblStock);
            holder.lblDespachar = convertView.findViewById(R.id.lblDespachar);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

            holder.lblCodigoRe.setText("0");
            holder.lblProductoRe.setText("--");
            holder.lblPresRe.setText("--");
            holder.lblUmbasRe.setText("--");
            holder.lblCantRe.setText("0");
            holder.lblUbicRe.setText("0");
            holder.lblVenceRe.setText("--");
            holder.lblLpRe.setText("--");
            holder.lblLoteRe.setText("--");
            holder.lblCodPrRe.setText("--");
            holder.lblPesoRe.setText("0");
            holder.lblEstadoRe.setText("--");
            holder.lblStock.setText("0");
            holder.lblDespachar.setText("No");

            if (!BeListTareasHH.get(position).Codigo.isEmpty()){
                holder.lblCodigoRe.setText(BeListTareasHH.get(position).Codigo);
            }

            if (!BeListTareasHH.get(position).Producto.isEmpty()){
                holder.lblProductoRe.setText(BeListTareasHH.get(position).Producto);
            }

            if (!BeListTareasHH.get(position).Presentacion.isEmpty()){
                holder.lblPresRe.setText(""+BeListTareasHH.get(position).Presentacion);
            }

            if (!BeListTareasHH.get(position).UMBas.isEmpty()){
                holder.lblUmbasRe.setText(""+BeListTareasHH.get(position).UMBas);
            }

            if (BeListTareasHH.get(position).Cant!=0){
                holder.lblCantRe.setText(""+BeListTareasHH.get(position).Cant);
            }

            if (BeListTareasHH.get(position).IdUbicacion!=0){
                 holder.lblUbicRe.setText(""+BeListTareasHH.get(position).NombreUbicacion);
            }

            if (!BeListTareasHH.get(position).FechaVence.isEmpty()){
                holder.lblVenceRe.setText(""+BeListTareasHH.get(position).FechaVence);
            }

            if (!BeListTareasHH.get(position).LicPlate.isEmpty()){
                holder.lblLpRe.setText(""+BeListTareasHH.get(position).LicPlate);
            }

            if (!BeListTareasHH.get(position).Lote.isEmpty()){
                holder.lblLoteRe.setText(""+BeListTareasHH.get(position).Lote);
            }

            if (!BeListTareasHH.get(position).CodigoProducto.isEmpty()){
                holder.lblCodPrRe.setText(""+BeListTareasHH.get(position).CodigoProducto);
            }

            if (BeListTareasHH.get(position).Peso!=0){
                holder.lblPesoRe.setText(""+BeListTareasHH.get(position).Peso);
            }

            if (!BeListTareasHH.get(position).Estado.isEmpty()){
                holder.lblEstadoRe.setText(""+BeListTareasHH.get(position).Estado);
            }

            if (BeListTareasHH.get(position).IdStock!=0){
                holder.lblStock.setText(""+BeListTareasHH.get(position).IdStock);
            }

            if (!BeListTareasHH.get(position).Despachar.isEmpty()){
                holder.lblDespachar.setText(""+BeListTareasHH.get(position).Despachar);
            }

       // }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblCodigoRe,lblProductoRe,lblPresRe,lblUmbasRe,lblCantRe,lblUbicRe,lblVenceRe,lblLpRe,lblLoteRe,lblCodPrRe,lblPesoRe
                ,lblEstadoRe,lblStock,lblDespachar;
    }

}
