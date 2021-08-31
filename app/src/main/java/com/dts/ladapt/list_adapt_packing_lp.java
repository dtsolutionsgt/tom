package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_packing_lp extends BaseAdapter {

    private static ArrayList<clsBeTrans_packing_lotes> items;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_packing_lp(Context context, ArrayList<clsBeTrans_packing_lotes> results) {
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

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_packing_lp, null);
            holder = new ViewHolder();

            holder.lblProd = (TextView) convertView.findViewById(R.id.lblIdTramo);
            holder.lblLP = (TextView) convertView.findViewById(R.id.textView102);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblProd.setText(items.get(position).presentacion);
        holder.lblLP.setText("LP : "+items.get(position).lote);

        if (selectedIndex != -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblProd, lblLP;
    }

}
