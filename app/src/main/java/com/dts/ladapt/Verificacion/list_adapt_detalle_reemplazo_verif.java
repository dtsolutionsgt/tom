package com.dts.ladapt.Verificacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.Picking.clsBeStockReemplazo;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_detalle_reemplazo_verif extends BaseAdapter {

    private static ArrayList<clsBeStockReemplazo> BeListTareasHH;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_detalle_reemplazo_verif(Context context, ArrayList<clsBeStockReemplazo> results) {
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
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_reemplazo_verif, null);
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

        holder.lblCodigoRe.setText(BeListTareasHH.get(position).Codigo);
        holder.lblProductoRe.setText(BeListTareasHH.get(position).Producto);
        holder.lblPresRe.setText(""+BeListTareasHH.get(position).Presentacion);
        holder.lblUmbasRe.setText(""+BeListTareasHH.get(position).UMBas);
        holder.lblCantRe.setText(""+BeListTareasHH.get(position).Cant);
        holder.lblUbicRe.setText(""+BeListTareasHH.get(position).NombreUbicacion);
        holder.lblVenceRe.setText(""+BeListTareasHH.get(position).FechaVence);
        holder.lblLpRe.setText(""+BeListTareasHH.get(position).LicPlate);
        holder.lblLoteRe.setText(""+BeListTareasHH.get(position).Lote);
        holder.lblCodPrRe.setText(""+BeListTareasHH.get(position).CodigoProducto);
        holder.lblPesoRe.setText(""+BeListTareasHH.get(position).Peso);
        holder.lblEstadoRe.setText(""+BeListTareasHH.get(position).Estado);
        holder.lblStock.setText(""+BeListTareasHH.get(position).IdStock);
        holder.lblDespachar.setText(""+BeListTareasHH.get(position).Despachar);

        //LinearLayout encabezado = (LinearLayout) convertView.findViewById(R.id.linearEncCB);

       /* if (position>0){
            encabezado.setVisibility(View.GONE);
        }else{
            encabezado.setVisibility(View.VISIBLE);
        }*/

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        }else{
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblCodigoRe,lblProductoRe,lblPresRe,lblUmbasRe,lblCantRe,lblUbicRe,lblVenceRe,lblLpRe,lblLoteRe,lblCodPrRe,lblPesoRe
                ,lblEstadoRe,lblStock,lblDespachar;
    }

}

