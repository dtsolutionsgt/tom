package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_det;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_detalle_rec_prod extends BaseAdapter {

    private static ArrayList<clsBeTrans_re_det> BeDetalleRec;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_detalle_rec_prod(Context context, ArrayList<clsBeTrans_re_det> results) {
        BeDetalleRec = results;
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
        return BeDetalleRec.size();
    }

    public Object getItem(int position) {
        return BeDetalleRec.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_rec_prod, null);
            holder = new ViewHolder();

            holder.lblCodigo = convertView.findViewById(R.id.lblCodigo);
            holder.lblPres = convertView.findViewById(R.id.lblPres);
            holder.lblUmbas = convertView.findViewById(R.id.lblUmbas);
            holder.lblCantidad = convertView.findViewById(R.id.lblCantidad);
            holder.lblProdBod = convertView.findViewById(R.id.lblProdBod);
            holder.lblEstado = convertView.findViewById(R.id.lblEstado);
            holder.lblVence = convertView.findViewById(R.id.lblVence);
            holder.lblLote = convertView.findViewById(R.id.lblLote);
            holder.lblLp = convertView.findViewById(R.id.lblLp);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {

            holder.lblCodigo.setText("Código");
            holder.lblPres.setText("Presentación");
            holder.lblUmbas.setText("UmBas");
            holder.lblCantidad.setText("Cantidad");
            holder.lblProdBod.setText("ProdBod");
            holder.lblEstado.setText("Estado");
            holder.lblVence.setText("Vence");
            holder.lblLote.setText("Lote");
            holder.lblLp.setText("Licencia");

        } else {

            holder.lblCodigo.setText("--");
            holder.lblPres.setText("--");
            holder.lblUmbas.setText("--");
            holder.lblCantidad.setText("0");
            holder.lblProdBod.setText("0");
            holder.lblEstado.setText("--");
            holder.lblVence.setText("--");
            holder.lblLote.setText("--");
            holder.lblLp.setText("--");

            if (BeDetalleRec.get(position).Codigo_Producto != null) {
                holder.lblCodigo.setText(BeDetalleRec.get(position).Codigo_Producto);
            }

            if (BeDetalleRec.get(position).Nombre_presentacion != null) {
                holder.lblPres.setText(BeDetalleRec.get(position).Nombre_presentacion);
            }

            if (BeDetalleRec.get(position).Nombre_unidad_medida != null) {
                holder.lblUmbas.setText(BeDetalleRec.get(position).Nombre_unidad_medida);
            }

            if (BeDetalleRec.get(position).cantidad_recibida > 0) {
                holder.lblCantidad.setText("" + BeDetalleRec.get(position).cantidad_recibida);
            }

            if (BeDetalleRec.get(position).IdProductoBodega > 0) {
                holder.lblProdBod.setText("" + BeDetalleRec.get(position).IdProductoBodega);
            }

            if (!BeDetalleRec.get(position).Nombre_producto_estado.isEmpty()) {
                holder.lblEstado.setText(BeDetalleRec.get(position).Nombre_producto_estado);
            }

            if (!BeDetalleRec.get(position).Fecha_vence.isEmpty()) {
                holder.lblVence.setText("" + BeDetalleRec.get(position).Fecha_vence);
            }

            if (!BeDetalleRec.get(position).Lote.isEmpty()) {
                holder.lblLote.setText("" + BeDetalleRec.get(position).Lote);
            }

            if (!BeDetalleRec.get(position).Lic_plate.isEmpty()) {
                holder.lblLp.setText(BeDetalleRec.get(position).Lic_plate);
            }


        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
            }else{
                //#CKFK 20210525 Aquí ponemos la fila de color verde claro para identificar que ya fue recepcionado
                if (BeDetalleRec.get(position).cantidad_recibida == 0) {
                    convertView.setBackgroundColor(Color.parseColor("#c9ffc0"));
                }else{
                    convertView.setBackgroundColor(Color.parseColor("#f5ffae"));
                }
            }
        }


    /*    if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
                holder.lblCodigo.setTextColor(R.style.titlestyle);
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }*/

        return convertView;
    }

    static class ViewHolder {
        TextView lblCodigo,lblPres,lblUmbas,lblCantidad,lblProdBod,lblEstado,
                 lblVence, lblLote, lblLp, lblDiferencia;
    }

}
