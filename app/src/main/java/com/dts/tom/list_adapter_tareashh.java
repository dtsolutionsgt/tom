package com.dts.tom;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;

import java.util.ArrayList;

public class list_adapter_tareashh extends BaseAdapter {

    private static ArrayList<clsBeTareasIngresoHH> BeListTareasHH;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapter_tareashh(Context context, ArrayList<clsBeTareasIngresoHH> results) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapter_tareashh, null);
            holder = new ViewHolder();

            holder.lblIdOrderCompraEnc = (TextView) convertView.findViewById(R.id.lblIdOC);
            holder.lblIdRecepcionEnc = (TextView) convertView.findViewById(R.id.lblRecepcionEnc);
            holder.lblNoReferenciaOC = (TextView) convertView.findViewById(R.id.lblNoRefOC);
            holder.lblNoDocumentoOc = (TextView) convertView.findViewById(R.id.lblNDocOc);
            holder.lblProveedor = (TextView) convertView.findViewById(R.id.lblProvee);
            holder.lblTipoIngresoOC = (TextView) convertView.findViewById(R.id.lblTipoIng);
            holder.lblTipoRecepcion = (TextView) convertView.findViewById(R.id.lblTipoRece);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){
            holder.lblIdOrderCompraEnc.setText("OC");
            holder.lblIdRecepcionEnc.setText("Rec");
            holder.lblIdRecepcionEnc.setTextColor(R.style.titlestyle);
            holder.lblNoReferenciaOC.setText("NoRefOC");
            holder.lblNoReferenciaOC.setTextColor(R.style.titlestyle);
            holder.lblNoDocumentoOc.setText("NoDocOc");
            holder.lblNoDocumentoOc.setTextColor(R.style.titlestyle);
            holder.lblProveedor.setText("Proveedor");
            holder.lblProveedor.setTextColor(R.style.titlestyle);
            holder.lblTipoIngresoOC.setText("TipoIngresoOC");
            holder.lblTipoIngresoOC.setTextColor(R.style.titlestyle);
            holder.lblTipoRecepcion.setText("TipoRecepcion");
            holder.lblTipoRecepcion.setTextColor(R.style.titlestyle);
        }else{

            holder.lblIdOrderCompraEnc.setText("0");
            holder.lblIdRecepcionEnc.setText("0");
            holder.lblNoReferenciaOC.setText("--");
            holder.lblNoDocumentoOc.setText("--");
            holder.lblProveedor.setText("--");
            holder.lblTipoIngresoOC.setText("--");
            holder.lblTipoRecepcion.setText("--");


            if(BeListTareasHH.get(position).IdOrderCompraEnc>0){
                holder.lblIdOrderCompraEnc.setText(""+BeListTareasHH.get(position).IdOrderCompraEnc);
            }
            if(BeListTareasHH.get(position).IdRecepcionEnc>0){
                holder.lblIdRecepcionEnc.setText(""+BeListTareasHH.get(position).IdRecepcionEnc);
            }

            if(BeListTareasHH.get(position).NoReferenciaOC!=null){
                holder.lblNoReferenciaOC.setText(BeListTareasHH.get(position).NoReferenciaOC);
            }

            if(BeListTareasHH.get(position).NoDocumentoOc!=null){
                holder.lblNoDocumentoOc.setText(BeListTareasHH.get(position).NoDocumentoOc);
            }

            if(BeListTareasHH.get(position).NombreProveedor!=null){
                holder.lblProveedor.setText(BeListTareasHH.get(position).NombreProveedor);
            }

            if(BeListTareasHH.get(position).NombreTipoIngresoOC!=null){
                holder.lblTipoIngresoOC.setText(BeListTareasHH.get(position).NombreTipoIngresoOC);
            }

            if(BeListTareasHH.get(position).NombreTipoRecepcion!=null){
                holder.lblTipoRecepcion.setText(BeListTareasHH.get(position).NombreTipoRecepcion);
            }
        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
           if (position==0){
               convertView.setBackgroundResource(R.drawable.color_medium);
               holder.lblIdOrderCompraEnc.setTextColor(R.style.titlestyle);
           }else{
               convertView.setBackgroundColor(Color.TRANSPARENT);
           }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblIdOrderCompraEnc,lblIdRecepcionEnc,lblNoReferenciaOC,lblNoDocumentoOc,lblProveedor,lblTipoIngresoOC,lblTipoRecepcion;
    }

}
