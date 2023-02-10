package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.tom.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class list_adapt_detalle_tareas_picking3 extends BaseAdapter {

    private static ArrayList<clsBeTrans_picking_ubic> BeListTareasHH;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_detalle_tareas_picking3(Context context, ArrayList<clsBeTrans_picking_ubic> results) {
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
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_tareas_picking3, null);
            holder = new ViewHolder();

            holder.txtArea = convertView.findViewById(R.id.txtArea);
            holder.txtUbicacion = convertView.findViewById(R.id.txtUbic);
            holder.txtLicencia = convertView.findViewById(R.id.lblLicencia);
            holder.txtProdDoc = convertView.findViewById(R.id.txtProdDoc);
            holder.txtClasificacion = convertView.findViewById(R.id.txtClasificacion);
            holder.txtLote = convertView.findViewById(R.id.txtLote);
            holder.txtVence = convertView.findViewById(R.id.txtVence);
            holder.txtUm = convertView.findViewById(R.id.txtUm);
            holder.lblCantidad = convertView.findViewById(R.id.lblCantidad);
            holder.relLicencia = convertView.findViewById(R.id.relLicencia);
            holder.relTarima = convertView.findViewById(R.id.relTarima);
            holder.txtTarima = convertView.findViewById(R.id.txtTarima);
            holder.txtEstado = convertView.findViewById(R.id.txtEstado);
            holder.txtPeso = convertView.findViewById(R.id.txtPeso);
            holder.txtSolicitado = convertView.findViewById(R.id.txtSolicitado);
            holder.txtRecibido = convertView.findViewById(R.id.txtRecibido);
            holder.txtPendiente = convertView.findViewById(R.id.txtPendiente);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!BeListTareasHH.get(position).NombreArea.isEmpty()){
            holder.txtArea.setText(BeListTareasHH.get(position).NombreArea);
        }

        holder.txtUbicacion.setText(""+BeListTareasHH.get(position).IdUbicacion + " - " + BeListTareasHH.get(position).NombreUbicacion+"");

        if (!BeListTareasHH.get(position).Lic_plate.isEmpty()) {
            holder.relLicencia.setVisibility(View.VISIBLE);
            holder.txtLicencia.setText(BeListTareasHH.get(position).Lic_plate);
        } else {
            holder.relLicencia.setVisibility(View.GONE);
        }

        holder.txtProdDoc.setText(BeListTareasHH.get(position).CodigoProducto+" - "+BeListTareasHH.get(position).NombreProducto);
        holder.txtClasificacion.setText(BeListTareasHH.get(position).NombreClasificacion);
        holder.txtLote.setText(BeListTareasHH.get(position).Lote);
        holder.txtVence.setText(BeListTareasHH.get(position).Fecha_Vence);

        String um = BeListTareasHH.get(position).IdPresentacion > 0 ? BeListTareasHH.get(position).ProductoPresentacion : BeListTareasHH.get(position).ProductoUnidadMedida;
        holder.txtUm.setText(um);

        holder.txtSolicitado.setText(""+BeListTareasHH.get(position).Cantidad_Solicitada);

        holder.txtRecibido.setText(""+BeListTareasHH.get(position).Cantidad_Recibida);

        double pendiente = BeListTareasHH.get(position).Cantidad_Solicitada - BeListTareasHH.get(position).Cantidad_Recibida;
        holder.txtPendiente.setText(""+pendiente);

        if (BeListTareasHH.get(position).Tarima > 0) {
            holder.txtTarima.setText(BeListTareasHH.get(position).Tarima+"");
        } else {
            holder.relTarima.setVisibility(View.GONE);
        }

        holder.txtEstado.setText(BeListTareasHH.get(position).ProductoEstado);
        holder.txtPeso.setText(""+BeListTareasHH.get(position).Peso_solicitado);

        if (BeListTareasHH.get(position).Cantidad_Recibida > 0) {
            convertView.setBackgroundColor(Color.parseColor("#F5FFAE"));
        } else {
           convertView.setBackgroundColor(Color.parseColor("#EF5350"));
        }

        return convertView;
    }

    static class ViewHolder {

        TextView txtArea, txtUbicacion, txtLicencia, txtProdDoc, txtClasificacion, txtLote, txtVence, txtCantidad, lblCantidad,
        txtPeso, txtTarima, txtEstado, txtUm, txtSolicitado, txtRecibido, txtPendiente;
        RelativeLayout relLicencia, relTarima;
    }

}
