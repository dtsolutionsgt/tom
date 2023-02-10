package com.dts.ladapt.Recepcion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.tom.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class list_adapt_detalle_recepcion3 extends BaseAdapter {

    private static ArrayList<clsBeTrans_oc_det> BeDetalleOC;
    private int selectedIndex;
    private final LayoutInflater l_Inflater;
    private boolean Es_Poliza_Consolidada=false;
    private final int decimales_redondeo;


    public list_adapt_detalle_recepcion3(Context context, ArrayList<clsBeTrans_oc_det> results,
                                         boolean pEs_Poliza_Consolidada, int pDecimales_redondeo) {
        BeDetalleOC = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
        Es_Poliza_Consolidada = pEs_Poliza_Consolidada;
        decimales_redondeo = pDecimales_redondeo;

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
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_recepcion3, null);
            holder = new ViewHolder();

            holder.txtNoLinea = convertView.findViewById(R.id.txtNoLinea);
            holder.txtProdDoc = convertView.findViewById(R.id.txtProdDoc);
            holder.txtClasificacion = convertView.findViewById(R.id.txtClasificacion);
            holder.lblCantidad = convertView.findViewById(R.id.lblCantidad);
            holder.txtCantidad = convertView.findViewById(R.id.txtCantidad);
            holder.txtCosto = convertView.findViewById(R.id.txtCosto);
            holder.txtFactor = convertView.findViewById(R.id.txtFactor);
            holder.txtEmbarcador = convertView.findViewById(R.id.txtEmbarcador);
            holder.relEmbarcador = convertView.findViewById(R.id.relEmbarcador);
            holder.relFactor = convertView.findViewById(R.id.relFactor);
            holder.relPropietario = convertView.findViewById(R.id.relPropietario);
            holder.txtPropietario = convertView.findViewById(R.id.txtPropietario);
            holder.txtPres = convertView.findViewById(R.id.txtPres);
            holder.txtCantidadRec = convertView.findViewById(R.id.txtCantidadRec);
            holder.txtCantidadDif = convertView.findViewById(R.id.txtCantidadDif);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(BeDetalleOC.get(position).No_Linea>0) {
            holder.txtNoLinea.setText("" + BeDetalleOC.get(position).No_Linea);
        }

        holder.txtProdDoc.setText(BeDetalleOC.get(position).Producto.Codigo +" - "+ BeDetalleOC.get(position).Producto.Nombre);

        holder.txtClasificacion.setText(""+ BeDetalleOC.get(position).Nombre_Clasificacion);

        String Pres = BeDetalleOC.get(position).Presentacion.Nombre!=null ? BeDetalleOC.get(position).Presentacion.Nombre: BeDetalleOC.get(position).UnidadMedida.Nombre;
        holder.txtPres.setText(Pres);

        /*double diferencia = 0;
        diferencia =  BeDetalleOC.get(position).Cantidad_recibida - BeDetalleOC.get(position).Cantidad;
        holder.txtCantidad.setText("Rec: "+BeDetalleOC.get(position).Cantidad_recibida+"      Dif: "+diferencia);*/

        holder.txtCosto.setText(""+BeDetalleOC.get(position).Costo);

        if(BeDetalleOC.get(position).FactorPresentacion>0){
            holder.txtFactor.setText(""+BeDetalleOC.get(position).FactorPresentacion);
        } else {
            holder.relFactor.setVisibility(View.GONE);
        }

        BigDecimal cantidad = new BigDecimal(BeDetalleOC.get(position).Cantidad).setScale(decimales_redondeo, RoundingMode.HALF_UP);
        holder.txtCantidad.setText(""+cantidad);

        BigDecimal cantRec = new BigDecimal(BeDetalleOC.get(position).Cantidad_recibida).setScale(decimales_redondeo, RoundingMode.HALF_UP);
        holder.txtCantidadRec.setText(""+cantRec);

        double input_valor = BeDetalleOC.get(position).Cantidad_recibida-BeDetalleOC.get(position).Cantidad;
        BigDecimal bd = new BigDecimal(input_valor).setScale(decimales_redondeo, RoundingMode.HALF_UP);
        holder.txtCantidadDif.setText(""+bd.doubleValue());

        if(!BeDetalleOC.get(position).Nombre_Embarcador.isEmpty()){
            holder.txtEmbarcador.setText(""+BeDetalleOC.get(position).Nombre_Embarcador);
        } else {
            holder.relEmbarcador.setVisibility(View.GONE);
        }

        if (!Es_Poliza_Consolidada) {
            holder.relPropietario.setVisibility(View.GONE);
        } else {
            holder.txtPropietario.setText(BeDetalleOC.get(position).IdPropietarioBodega +" - "+ BeDetalleOC.get(position).Nombre_Propietario);
        }

        //#CKFK 20210525 Aquí ponemos la fila de color verde claro para identificar que ya fue recepcionado
        if (BeDetalleOC.get(position).Cantidad_recibida == BeDetalleOC.get(position).Cantidad) {
            convertView.setBackgroundColor(Color.parseColor("#c9ffc0"));
        }else if (BeDetalleOC.get(position).Cantidad_recibida < BeDetalleOC.get(position).Cantidad  &&
                  BeDetalleOC.get(position).Cantidad_recibida >0) {
            convertView.setBackgroundColor(Color.parseColor("#f5ffae"));
        }else if (BeDetalleOC.get(position).Cantidad_recibida < BeDetalleOC.get(position).Cantidad  &&
                BeDetalleOC.get(position).Cantidad_recibida == 0) {
            convertView.setBackgroundColor(Color.parseColor("#ff8080"));
        }else if (BeDetalleOC.get(position).Cantidad_recibida > BeDetalleOC.get(position).Cantidad) {
            convertView.setBackgroundColor(Color.parseColor("#80c3ff"));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView txtNoLinea, txtProdDoc, txtClasificacion, lblCantidad, txtCantidad, txtCosto, txtFactor, txtEmbarcador, txtPropietario, txtPres, txtCantidadRec, txtCantidadDif;
        RelativeLayout relEmbarcador, relFactor, relPropietario;
    }

}
