package com.dts.tom;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;

import java.util.ArrayList;

public class list_adapt_detalle_recepcion extends BaseAdapter {

    private static ArrayList<clsBeTrans_oc_det> BeDetalleOC;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_detalle_recepcion(Context context, ArrayList<clsBeTrans_oc_det> results) {
        BeDetalleOC = results;
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
        return BeDetalleOC.size();
    }

    public Object getItem(int position) {
        return BeDetalleOC.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_recepcion, null);
            holder = new ViewHolder();

            holder.lblNoLinea = (TextView) convertView.findViewById(R.id.lblNoLinea);
            holder.lblCodigo = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.lblProducto = (TextView) convertView.findViewById(R.id.lblProducto);
            holder.lblPres = (TextView) convertView.findViewById(R.id.lblPres);
            holder.lblUmbas = (TextView) convertView.findViewById(R.id.lblUmbas);
            holder.lblCantidad = (TextView) convertView.findViewById(R.id.lblCantidad);
            holder.lblCantRec = (TextView) convertView.findViewById(R.id.lblCantRec);
            holder.lblCosto = (TextView) convertView.findViewById(R.id.lblCosto);
            holder.lblFactor = (TextView) convertView.findViewById(R.id.lblFactor);
            holder.lblIdOcDet = (TextView) convertView.findViewById(R.id.lblIdOcDet);
            holder.lblIdOcEnc = (TextView) convertView.findViewById(R.id.lblIdOcEnc);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){

            holder.lblNoLinea.setText("NoLinea");
            holder.lblCodigo.setText("Código");
            holder.lblCodigo.setTextColor(R.style.titlestyle);
            holder.lblProducto.setText("Producto");
            holder.lblProducto.setTextColor(R.style.titlestyle);
            holder.lblPres.setText("Presentación");
            holder.lblPres.setTextColor(R.style.titlestyle);
            holder.lblUmbas.setText("UmBas");
            holder.lblUmbas.setTextColor(R.style.titlestyle);
            holder.lblCantidad.setText("Cantidad");
            holder.lblCantidad.setTextColor(R.style.titlestyle);
            holder.lblCantRec.setText("CantidadRec");
            holder.lblCantRec.setTextColor(R.style.titlestyle);
            holder.lblCosto.setText("Costo");
            holder.lblCantRec.setTextColor(R.style.titlestyle);
            holder.lblFactor.setText("Factor");
            holder.lblFactor.setTextColor(R.style.titlestyle);
            holder.lblIdOcDet.setText("IdOcDet");
            holder.lblIdOcDet.setTextColor(R.style.titlestyle);
            holder.lblIdOcEnc.setText("IdOcEnc");
            holder.lblIdOcEnc.setTextColor(R.style.titlestyle);

        }else{

            holder.lblNoLinea.setText("0");
            holder.lblCodigo.setText("--");
            holder.lblProducto.setText("--");
            holder.lblPres.setText("--");
            holder.lblUmbas.setText("--");
            holder.lblCantidad.setText("0");
            holder.lblCantRec.setText("0");
            holder.lblCosto.setText("0");
            holder.lblFactor.setText("0");
            holder.lblIdOcDet.setText("0");
            holder.lblIdOcEnc.setText("0");


            if(BeDetalleOC.get(position).No_Linea>0){
                holder.lblNoLinea.setText(""+BeDetalleOC.get(position).No_Linea);
            }
            if(BeDetalleOC.get(position).Producto.Codigo!=null){
                holder.lblCodigo.setText(BeDetalleOC.get(position).Producto.Codigo);
            }

            if(BeDetalleOC.get(position).Producto.Nombre!=null){
                holder.lblProducto.setText(BeDetalleOC.get(position).Producto.Nombre);
            }

            if(BeDetalleOC.get(position).Presentacion.Nombre!=null){
                holder.lblPres.setText(BeDetalleOC.get(position).Presentacion.Nombre);
            }

            if(BeDetalleOC.get(position).UnidadMedida.Nombre!=null){
                holder.lblUmbas.setText(BeDetalleOC.get(position).UnidadMedida.Nombre);
            }

            if(BeDetalleOC.get(position).Cantidad>0){
                holder.lblCantidad.setText(""+BeDetalleOC.get(position).Cantidad);
            }

            if(BeDetalleOC.get(position).Cantidad_recibida>0){
                holder.lblCantRec.setText(""+BeDetalleOC.get(position).Cantidad_recibida);
            }

            if(BeDetalleOC.get(position).Costo>0){
                holder.lblCosto.setText(""+BeDetalleOC.get(position).Costo);
            }

            if(BeDetalleOC.get(position).FactorPresentacion>0){
                holder.lblFactor.setText(""+BeDetalleOC.get(position).FactorPresentacion);
            }

            if(BeDetalleOC.get(position).IdOrdenCompraDet>0){
                holder.lblIdOcDet.setText(""+BeDetalleOC.get(position).IdOrdenCompraDet);
            }

            if(BeDetalleOC.get(position).IdOrdenCompraEnc>0){
                holder.lblIdOcEnc.setText(""+BeDetalleOC.get(position).IdOrdenCompraEnc);
            }

        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblNoLinea.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblNoLinea,lblCodigo,lblProducto,lblPres,lblUmbas,lblCantidad,lblCantRec,lblCosto,lblFactor,lblIdOcDet,lblIdOcEnc;
    }

}