package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.tom.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class list_adapt_reabast_stock_res extends BaseAdapter {

    private static ArrayList<clsBeVW_stock_res> BeListStockRes;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

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


            holder.txtUbicacion = convertView.findViewById(R.id.txtUbicacion);
            holder.txtProducto = convertView.findViewById(R.id.txtProducto);
            holder.txtLicencia = convertView.findViewById(R.id.txtLicencia);
            holder.txtLote = convertView.findViewById(R.id.txtLote);
            holder.txtFechaVen = convertView.findViewById(R.id.txtFechaVen);
            holder.txtEstado = convertView.findViewById(R.id.txtEstado);
            holder.txtCantidad = convertView.findViewById(R.id.txtCantidad);
            holder.txtPresentacion = convertView.findViewById(R.id.txtPresentacion);

            holder.lblCantidad = convertView.findViewById(R.id.lblCantidad);

            holder.trLicencia = convertView.findViewById(R.id.trLicencia);
            holder.trLote = convertView.findViewById(R.id.trLote);
            holder.trFechaVen = convertView.findViewById(R.id.trFechaVen);

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

        if (BeListStockRes.get(position).IdPresentacion > 0) {
            BeListStockRes.get(position).CantidadPresentacion = BeListStockRes.get(position).CantidadReservadaUMBas / BeListStockRes.get(position).Factor;
            holder.lblCantidad.setText("Cantidad ("+BeListStockRes.get(position).Nombre_Presentacion+"): ");
            holder.txtCantidad.setText(""+BeListStockRes.get(position).CantidadPresentacion);
        } else {
            holder.lblCantidad.setText("Cantidad ("+BeListStockRes.get(position).UMBas+"): ");
            holder.txtCantidad.setText(""+BeListStockRes.get(position).CantidadReservadaUMBas);
        }

        if (!BeListStockRes.get(position).Nombre_Presentacion.isEmpty()) {
            holder.txtPresentacion.setText(BeListStockRes.get(position).Nombre_Presentacion);
        }

        if(position == selectedIndex) {
            convertView.setBackgroundColor(Color.parseColor("#FFEBEE"));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView txtUbicacion, txtProducto, txtLicencia, txtLote, txtFechaVen, txtEstado, txtCantidad, txtPresentacion,
                 lblCantidad;
        TableRow trLicencia, trLote, trFechaVen;
    }

}
