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

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_producto_imagen(Context context, ArrayList<clsBeImagen> results) {
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

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.imagen, null);
            holder = new ViewHolder();

            holder.imgProducto = (ImageView) convertView.findViewById(R.id.imgProducto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)
            convertView.getTag();
        }


        //holder.imgProducto.setRotation((float) 90.0);
        holder.imgProducto.setImageBitmap(items.get(position).bmImg);

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProducto;
    }
}
