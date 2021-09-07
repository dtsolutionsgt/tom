package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc_bulto;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_lista_packing_bulto extends BaseAdapter {

    private static ArrayList<clsBeTrans_packing_enc_bulto> items;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_lista_packing_bulto(Context context, ArrayList<clsBeTrans_packing_enc_bulto> results) {
        items = results;
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
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String ss;
        int pp;
        boolean regular=true;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_lista_packing_bulto, null);
            holder = new ViewHolder();

            holder.lbl1 = convertView.findViewById(R.id.lbl1);
            holder.lbl2 = convertView.findViewById(R.id.lbl2);
            holder.lbl3 = convertView.findViewById(R.id.lbl3);
            holder.lbl4 = convertView.findViewById(R.id.lbl4);
            holder.lbl5 = convertView.findViewById(R.id.lbl5);
            holder.lbl6 = convertView.findViewById(R.id.lbl6);
            holder.lbl7 = convertView.findViewById(R.id.lbl7);
            holder.lbl8 = convertView.findViewById(R.id.lbl8);
            holder.lbl9 = convertView.findViewById(R.id.lbl9);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position>0) {

            regular=items.get(position).bandera==0;

            holder.lbl1.setText(" ");
            holder.lbl2.setText(" ");
            holder.lbl3.setText(" ");
            holder.lbl4.setText(" ");
            holder.lbl5.setText(" ");
            holder.lbl6.setText(" ");
            holder.lbl7.setText(" ");
            holder.lbl8.setText(" ");
            holder.lbl9.setText(" ");

            if (regular) {
                //ss=items.get(position).Fecha_vence;pp=ss.indexOf("T");ss=ss.substring(0,pp);
                try {
                    ss=items.get(position).Fecha_vence;
                    pp=ss.indexOf("T");
                    if (pp>=0) {
                        ss=ss.substring(0,pp);
                    } else {
                        ss=items.get(position).Fecha_vence;
                    }
                } catch (Exception e) {
                    ss=items.get(position).Fecha_vence;
                }

                holder.lbl1.setText(""+ items.get(position).No_linea);
                holder.lbl7.setText(""+items.get(position).Referencia);
                holder.lbl2.setText(""+items.get(position).CodigoProducto);
                holder.lbl3.setText(""+items.get(position).nom_prod);
                holder.lbl4.setText(""+items.get(position).Cantidad_bultos_packing);
                holder.lbl5.setText(""+ items.get(position).ProductoPresentacion);
                holder.lbl6.setText(""+ items.get(position).ProductoUnidadMedida);
                holder.lbl8.setText(""+ ss);
                holder.lbl9.setText(""+ items.get(position).Lote);
            } else {
                holder.lbl1.setText("");
                holder.lbl3.setText("Total linea "+ items.get(position).No_linea+ " : ");
                holder.lbl4.setText(""+items.get(position).Cantidad_bultos_packing);
            }
        }

        if (selectedIndex!= -1 && position == selectedIndex) {
            if (regular)
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            else
                convertView.setBackgroundColor(Color.rgb(96, 96, 96));
        } else {
            if (regular) {
                if (position==0){
                    convertView.setBackgroundResource(R.drawable.color_medium);
                    holder.lbl1.setTextColor(R.style.titlestyle);
                } else {
                    convertView.setBackgroundColor(Color.TRANSPARENT);
                }
            } else {
                convertView.setBackgroundColor(Color.rgb(192, 192, 192));
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lbl1,lbl2,lbl3,lbl4, lbl5,lbl6,lbl7,lbl8,lbl9;
    }

}
