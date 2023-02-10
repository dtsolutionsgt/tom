package com.dts.ladapt.CambioUbicacion;

import android.graphics.Color;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_det;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

public class list_adapter_detalle_cambio_ubic extends BaseAdapter {

    private ArrayList<clsBeTrans_ubic_hh_det> pBeTransUbicHhDetListArray;
    private int selectedIndex;
    private LayoutInflater l_Inflater;
    private Filter filter;

    public list_adapter_detalle_cambio_ubic(){}

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

        try{
            if (convertView == null) {

                convertView = l_Inflater.inflate(R.layout.activity_list_adapter_detalle_cambio_ubic, null);
                holder = new ViewHolder();

                holder.lblTareaDet  = convertView.findViewById(R.id.lblTareaDet);
                holder.lblIdStock = convertView.findViewById(R.id.lblIdStock);
                holder.lblCodigo  = convertView.findViewById(R.id.lblCodigo);
                holder.lblProducto = convertView.findViewById(R.id.lblProducto);
                holder.lblPresentacion = convertView.findViewById(R.id.lblPresentacion);
                holder.lblOrigen = convertView.findViewById(R.id.lblOrigen);
                holder.lblDestino = convertView.findViewById(R.id.lblDestino);
                holder.lblCantidad = convertView.findViewById(R.id.lblCantidad);
                holder.lblRecibido = convertView.findViewById(R.id.lblRecibido);
                holder.lblOperador = convertView.findViewById(R.id.lblOperador);
                holder.lblLicencia = convertView.findViewById(R.id.lblLicencia);

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

            holder.lblLicencia.setText("--");
            if (pBeTransUbicHhDetListArray.get(position).Stock.Lic_plate!=null){
                if (!pBeTransUbicHhDetListArray.get(position).Stock.Lic_plate.isEmpty()){
                    holder.lblLicencia.setText(pBeTransUbicHhDetListArray.get(position).Stock.Lic_plate);
                }
            }

            LinearLayout encabezado = convertView.findViewById(R.id.linearEncCB);

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

        }catch (Exception e){

        }
        return convertView;
    }

    static class ViewHolder {
        TextView lblTareaDet,lblIdStock,lblCodigo,lblProducto,lblPresentacion,lblOrigen,lblDestino,lblCantidad,lblRecibido,lblOperador, lblLicencia;
    }




}
