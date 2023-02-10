package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dts.base.clsClasses;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_view_menu2 extends BaseAdapter {
    private static ArrayList<clsClasses.clsMenu> items;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_view_menu2(Context context, ArrayList<clsClasses.clsMenu> results) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String sc;
        int iconid,cant;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_view_menu, null);
            holder = new ViewHolder();

            holder.imgEst = convertView.findViewById(R.id.imgNext);
            holder.lblName = convertView.findViewById(R.id.lblTrat);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        cant=items.get(position).cant;
        sc="";if (cant>-1) sc=" ("+cant+")";
        holder.lblName.setText(items.get(position).Name+sc);
        holder.imgEst.setImageResource(R.drawable.ok48);

        if (items.get(position).Icon==1) holder.imgEst.setImageResource(R.drawable.ingreso1);
        if (items.get(position).Icon==2) holder.imgEst.setImageResource(R.drawable.camubic);
        if (items.get(position).Icon==3) holder.imgEst.setImageResource(R.drawable.camestado);
        if (items.get(position).Icon==4) holder.imgEst.setImageResource(R.drawable.packing1);
        if (items.get(position).Icon==5) holder.imgEst.setImageResource(R.drawable.picking2);
        if (items.get(position).Icon==6) holder.imgEst.setImageResource(R.drawable.verifi1);
        if (items.get(position).Icon==7) holder.imgEst.setImageResource(R.drawable.existencias);
        if (items.get(position).Icon==8) holder.imgEst.setImageResource(R.drawable.rastreo);
        if (items.get(position).Icon==9) holder.imgEst.setImageResource(R.drawable.operador);
        if (items.get(position).Icon==10) holder.imgEst.setImageResource(R.drawable.tools);
        if (items.get(position).Icon==11) holder.imgEst.setImageResource(R.drawable.camion);
        if (items.get(position).Icon==12) holder.imgEst.setImageResource(R.drawable.reabastecimiento);
        if (items.get(position).Icon==13) holder.imgEst.setImageResource(R.drawable.paquete);

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }


        return convertView;
    }

    static class ViewHolder {
        ImageView imgEst;
        TextView  lblName;
    }
}
