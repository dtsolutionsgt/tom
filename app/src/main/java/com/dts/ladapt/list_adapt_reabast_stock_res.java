package com.dts.ladapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_reabast_stock_res extends BaseAdapter {

    private static ArrayList<clsBeVW_stock_res> BeListStockRes;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_reabast_stock_res(Context context, ArrayList<clsBeVW_stock_res> results) {
        BeListStockRes = results;
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
        return BeListStockRes.size();
    }

    public Object getItem(int position) {
        return BeListStockRes.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_reabast_stock_res, null);
            holder = new ViewHolder();


            holder.txtUbicacion = (TextView) convertView.findViewById(R.id.txtUbicacion);
            holder.txtProducto = (TextView) convertView.findViewById(R.id.txtProducto);
            holder.txtLicencia = (TextView) convertView.findViewById(R.id.txtLicencia);
            holder.txtLote = (TextView) convertView.findViewById(R.id.txtLote);
            holder.txtFechaVen = (TextView) convertView.findViewById(R.id.txtFechaVen);
            holder.txtEstado = (TextView) convertView.findViewById(R.id.txtEstado);
            holder.txtUmBas = (TextView) convertView.findViewById(R.id.txtUmBas);
            holder.txtPresentacion = (TextView) convertView.findViewById(R.id.txtPresentacion);

            holder.trLicencia = (TableRow) convertView.findViewById(R.id.trLicencia);
            holder.trLote = (TableRow) convertView.findViewById(R.id.trLote);
            holder.trFechaVen = (TableRow) convertView.findViewById(R.id.trFechaVen);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!BeListStockRes.get(position).Ubicacion_Nombre.isEmpty()) {
            holder.txtUbicacion.setText(BeListStockRes.get(position).Ubicacion_Nombre);
        }

        if (!BeListStockRes.get(position).Codigo_Producto.isEmpty()) {
            holder.txtProducto.setText(BeListStockRes.get(position).Codigo_Producto+" - "+BeListStockRes.get(position).Nombre_Producto);
        }

        if (!BeListStockRes.get(position).Lic_plate.isEmpty()) {
            holder.txtLicencia.setText(BeListStockRes.get(position).Lic_plate);
        } else {
            holder.trLicencia.setVisibility(View.GONE);
        }

        if (!BeListStockRes.get(position).Lote.isEmpty()) {
            holder.txtLote.setText(BeListStockRes.get(position).Lote);
        } else {
            holder.trLote.setVisibility(View.GONE);
        }

        if (!BeListStockRes.get(position).Fecha_Vence.isEmpty() &&  !BeListStockRes.get(position).Fecha_Vence.equals("01-01-1900")) {
            holder.txtFechaVen.setText(BeListStockRes.get(position).Fecha_Vence);
        } else {
            holder.trFechaVen.setVisibility(View.GONE);
        }

        if (!BeListStockRes.get(position).NomEstado.isEmpty()) {
            holder.txtEstado.setText(BeListStockRes.get(position).NomEstado);
        }

        if (!BeListStockRes.get(position).UMBas.isEmpty()) {
            holder.txtUmBas.setText(BeListStockRes.get(position).UMBas);
        }

        if (!BeListStockRes.get(position).Nombre_Presentacion.isEmpty()) {
            holder.txtPresentacion.setText(BeListStockRes.get(position).Nombre_Presentacion);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView txtUbicacion, txtProducto, txtLicencia, txtLote, txtFechaVen, txtEstado, txtUmBas, txtPresentacion;
        TableRow trLicencia, trLote, trFechaVen;
    }

}
