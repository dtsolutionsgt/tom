package com.dts.ladapt.InventarioCiclico;

import android.graphics.Color;
import android.widget.BaseAdapter;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.tom.R;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class list_adapt_consulta_ciclico extends BaseAdapter {

    ArrayList<clsBe_inv_reconteo_data> data_list;
    private final Context cCont;
    private int selectedIndex;
    private final LayoutInflater l_Inflater;

    public list_adapt_consulta_ciclico(Context context,ArrayList<clsBe_inv_reconteo_data> results ){
        data_list = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
        cCont = context;
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
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{
            ViewHolder holder;

            if(convertView == null){
                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_consulta_ciclico, null);
                holder = new ViewHolder();

                holder.lblNoUbic = convertView.findViewById(R.id.lblNoUbic);
                holder.lblCodigo = convertView.findViewById(R.id.lblCodigo);
                holder.lblProducto_nombre = convertView.findViewById(R.id.lblProducto_nombre);
                holder.lblUMBas = convertView.findViewById(R.id.lblUMBas);
                holder.lblPres = convertView.findViewById(R.id.lblPres);
                holder.lblCant_Conteo = convertView.findViewById(R.id.lblCant_Conteo);
                holder.lblPeso_Conteo = convertView.findViewById(R.id.lblPeso_Conteo);
                holder.lblCant_Stock = convertView.findViewById(R.id.lblCant_Stock);
                holder.lblPeso_Stock = convertView.findViewById(R.id.lblPeso_Stock);
                holder.lblLote = convertView.findViewById(R.id.lblLote);
                holder.lblFecha_Vence_Stock = convertView.findViewById(R.id.lblFecha_Vence_Stock);
                holder.lblFecha_Vence = convertView.findViewById(R.id.lblFecha_Vence);
                holder.lblConteo = convertView.findViewById(R.id.lblConteo);
                holder.lblUbic_nombre = convertView.findViewById(R.id.lblUbic_nombre);
                holder.lblEstadoStock = convertView.findViewById(R.id.lblEstado);
                holder.lblIdProductoBodega = convertView.findViewById(R.id.lblIdProductoBodega);
                holder.lblTramo = convertView.findViewById(R.id.lblTramo);
                holder.lblIndiceX = convertView.findViewById(R.id.lblIndiceX);
                holder.lblNivel = convertView.findViewById(R.id.lblNivel);
                holder.lblPos = convertView.findViewById(R.id.lblPos);
                holder.lblFactor = convertView.findViewById(R.id.lblFactor);
                holder.lblidinvreconteo = convertView.findViewById(R.id.lblidinvreconteo);
                holder.lbllicplate = convertView.findViewById(R.id.lblicplate);
                holder.lblLoteStock = convertView.findViewById(R.id.lblLoteStock);
                holder.lblEstado = convertView.findViewById(R.id.lblEstado);
                holder.lblStock = convertView.findViewById(R.id.lblStock);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.lblNoUbic.setText(data_list.get(position).NoUbic  +"");
            holder.lblCodigo.setText(data_list.get(position).Codigo  +"");
            holder.lblProducto_nombre.setText(data_list.get(position).Producto_nombre  +"");
            holder.lblUMBas.setText(data_list.get(position).UMBas  +"");
            holder.lblPres.setText(data_list.get(position).Pres  +"");
            holder.lblCant_Conteo.setText(data_list.get(position).Cant_Conteo  +"");
            holder.lblPeso_Conteo.setText(data_list.get(position).Peso_Conteo  +"");
            holder.lblCant_Stock.setText(data_list.get(position).Cant_Stock  +"");
            holder.lblPeso_Stock.setText(data_list.get(position).Peso_Stock  +"");
            holder.lblLote.setText(data_list.get(position).Lote  +"");
            holder.lblFecha_Vence_Stock.setText(data_list.get(position).Fecha_Vence_Stock  +"");
            holder.lblFecha_Vence.setText(data_list.get(position).Fecha_Vence  +"");
            holder.lblConteo.setText(data_list.get(position).cantidad  +"");
            holder.lblUbic_nombre.setText(data_list.get(position).Ubic_nombre  +"");
            holder.lblEstadoStock.setText(data_list.get(position).Estado  +"");
            holder.lblIdProductoBodega.setText(data_list.get(position).IdProductoBodega  +"");
            holder.lblTramo.setText(data_list.get(position).Tramo  +"");
            holder.lblIndiceX.setText(data_list.get(position).IndiceX  +"");
            holder.lblNivel.setText(data_list.get(position).Nivel  +"");
            holder.lblPos.setText(data_list.get(position).Pos  +"");
            holder.lblFactor.setText(data_list.get(position).Factor  +"");
            holder.lblidinvreconteo.setText(data_list.get(position).idinvreconteo +"");
            holder.lbllicplate.setText(data_list.get(position).Licence_plate +"");
            holder.lblLoteStock.setText(data_list.get(position).Lote_stock);
            holder.lblEstado.setText(data_list.get(position).Nuevo_Estado);
            holder.lblStock.setText(data_list.get(position).IdStock+"");

            boolean esOriginal = false;

            esOriginal = data_list.get(position).IdProductoEstado == data_list.get(position).IdProductoEst_nuevo &&
                    data_list.get(position).Fecha_Vence.equals(data_list.get(position).Fecha_Vence_Stock) &&
                    data_list.get(position).Lote.equals(data_list.get(position).Lote_stock) &&
                    data_list.get(position).IdUbicacion_nuevo == 0;

            if (esOriginal) {
            //if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.parseColor("#D5F5E3"));
                /*if (esOriginal) {

                } else {
                    convertView.setBackgroundColor(Color.rgb(0, 128, 0));
                }*/
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

        }catch (Exception ex){
            toast(ex.getMessage());
        }
        return convertView;
    }

    public void toast(String msg) {
        Toast.makeText(cCont, msg, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        TextView lblNoUbic,lblCodigo,lblProducto_nombre,lblUMBas,lblPres,lblCant_Conteo,lblPeso_Conteo,lblCant_Stock,
                lblPeso_Stock,lblLote,lblFecha_Vence_Stock, lblFecha_Vence,lblConteo,lblUbic_nombre,lblEstado,lblIdProductoBodega,lblTramo,
                lblIndiceX,lblNivel,lblPos,lblFactor,lblidinvreconteo,lbllicplate, lblLoteStock, lblEstadoStock, lblStock;
    }
}
