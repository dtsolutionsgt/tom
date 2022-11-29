package com.dts.ladapt.ControlCalidad;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.classes.Transacciones.ControlCalidad.clsBeCalidad;
import com.dts.classes.Transacciones.ControlCalidad.clsBeCalidadList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_control_calidad extends BaseAdapter {

    private ArrayList<clsBeCalidad> ControlCalidad;
    private Context cCont;
    private int selectedIndex, actual;
    private boolean nuevo;
    private LayoutInflater l_Inflater;

    public list_adapt_control_calidad(Context context, ArrayList<clsBeCalidad> results, int val){
        ControlCalidad = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
        cCont = context;
        actual = val;
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

        return ControlCalidad.size();
    }

    @Override
    public Object getItem(int position) {
        return ControlCalidad.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            ViewHolder holder;

            if (convertView == null) {

                convertView = l_Inflater.inflate(R.layout.list_control_calidad, null);
                holder = new ViewHolder();

                holder.lblTexto = convertView.findViewById(R.id.lblTexto);
                holder.imgEstado = convertView.findViewById(R.id.imgEstado);


                convertView.setTag(holder);

            }else {

                holder = (ViewHolder) convertView.getTag();

            }

            if (position <= actual) {
                convertView.setBackgroundColor(Color.parseColor("#E8F5E9"));
            }

            holder.lblTexto.setText(ControlCalidad.get(position).Texto);


            if (ControlCalidad.get(position).Estado) {
                holder.imgEstado.setImageResource(R.drawable.accept);
            } else {
                holder.imgEstado.setImageResource(R.drawable.close);
            }


        } catch (Exception ex){
            toast(ex.getMessage());
        }
        return convertView;
    }

    public void toast(String msg) {
        Toast.makeText(cCont, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isEnabled(int position) {

        if (position > 0) {
            return false;
        } else {
            return true;
        }
    }

    static class ViewHolder {
        TextView lblTexto;
        ImageView imgEstado;
    }
}
