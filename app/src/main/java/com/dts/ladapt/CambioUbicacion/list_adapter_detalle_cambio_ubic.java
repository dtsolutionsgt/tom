package com.dts.ladapt.CambioUbicacion;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_det;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapter_detalle_cambio_ubic extends BaseAdapter {

    private ArrayList<clsBeTrans_ubic_hh_det> pBeTransUbicHhDetListArray;

    private int selectedIndex;

    private LayoutInflater l_Inflater;


    public list_adapter_detalle_cambio_ubic(Context context, ArrayList<clsBeTrans_ubic_hh_det> results) {
        pBeTransUbicHhDetListArray = results;
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

    @Override
    public int getCount() {
        return pBeTransUbicHhDetListArray.size();
    }

    @Override
    public Object getItem(int position) {
        return pBeTransUbicHhDetListArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = l_Inflater.inflate(R.layout.activity_list_adapter_detalle_cambio_ubic, null);
            holder = new ViewHolder();

            holder.lblTareaDet  = (TextView) convertView.findViewById(R.id.lblTareaDet);
            holder.lblIdStock = (TextView) convertView.findViewById(R.id.lblIdStock);
            holder.lblCodigo  = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.lblProducto = (TextView) convertView.findViewById(R.id.lblProducto);
            holder.lblPresentacion = (TextView) convertView.findViewById(R.id.lblPresentacion);
            holder.lblOrigen = (TextView) convertView.findViewById(R.id.lblOrigen);
            holder.lblDestino = (TextView) convertView.findViewById(R.id.lblDestino);
            holder.lblCantidad = (TextView) convertView.findViewById(R.id.lblCantidad);
            holder.lblRecibido = (TextView) convertView.findViewById(R.id.lblRecibido);
            holder.lblOperador = (TextView) convertView.findViewById(R.id.lblOperador);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblTareaDet.setText(""+pBeTransUbicHhDetListArray.get(position).IdTareaUbicacionDet);
        holder.lblIdStock.setText(""+pBeTransUbicHhDetListArray.get(position).IdStock);
        holder.lblCodigo.setText(""+pBeTransUbicHhDetListArray.get(position).Producto.Codigo);
        holder.lblProducto.setText(""+pBeTransUbicHhDetListArray.get(position).Producto.Nombre);
        holder.lblPresentacion.setText(""+pBeTransUbicHhDetListArray.get(position).ProductoPresentacion.Nombre);
        holder.lblOrigen.setText(""+pBeTransUbicHhDetListArray.get(position).UbicacionOrigen.NombreCompleto);
        holder.lblDestino.setText(""+pBeTransUbicHhDetListArray.get(position).UbicacionDestino.NombreCompleto);
        holder.lblCantidad.setText(""+pBeTransUbicHhDetListArray.get(position).Cantidad);
        holder.lblRecibido.setText(""+pBeTransUbicHhDetListArray.get(position).Recibido);
        holder.lblOperador.setText(""+pBeTransUbicHhDetListArray.get(position).Operador.Nombres);

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

        return convertView;
    }

    static class ViewHolder {
        TextView lblTareaDet,lblIdStock,lblCodigo,lblProducto,lblPresentacion,lblOrigen,lblDestino,lblCantidad,lblRecibido,lblOperador;
    }

}
