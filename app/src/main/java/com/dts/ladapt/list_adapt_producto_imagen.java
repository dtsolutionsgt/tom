package com.dts.ladapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dts.classes.clsBeImagen;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_producto_imagen extends BaseAdapter {
    private static ArrayList<clsBeImagen> items;
    private final Context cont;

    private int selectedIndex;

    private final LayoutInflater l_Inflater;

    public list_adapt_producto_imagen(Context context, ArrayList<clsBeImagen> results) {
        items = results;
        cont = context;
        l_Inflater = LayoutInflater.from(cont);
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

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.imagen, null);
            holder = new ViewHolder();

            holder.imgProducto = convertView.findViewById(R.id.imgProducto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)
            convertView.getTag();
        }

        /*Glide.with(cont)
                .load(items.get(position).bmImg)
                .thumbnail()
                .into(holder.imgProducto);*/
        holder.imgProducto.setImageBitmap(items.get(position).bmImg);


        return convertView;
    }

    static class ViewHolder {
        ImageView imgProducto;
    }
}
