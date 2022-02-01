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
    private Context cCont;
    private int selectedIndex;
    private LayoutInflater l_Inflater;

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
                holder.lblFecha_Vence = convertView.findViewById(R.id.lblFecha_Vence);
                holder.lblConteo = convertView.findViewById(R.id.lblConteo);
                holder.lblUbic_nombre = convertView.findViewById(R.id.lblUbic_nombre);
                holder.lblEstado = convertView.findViewById(R.id.lblEstado);
                holder.lblIdProductoBodega = convertView.findViewById(R.id.lblIdProductoBodega);
                holder.lblTramo = convertView.findViewById(R.id.lblTramo);
                holder.lblIndiceX = convertView.findViewById(R.id.lblIndiceX);
                holder.lblNivel = convertView.findViewById(R.id.lblNivel);
                holder.lblPos = convertView.findViewById(R.id.lblPos);
                holder.lblFactor = convertView.findViewById(R.id.lblFactor);
                holder.lblidinvreconteo = convertView.findViewById(R.id.lblidinvreconteo);
                holder.lbllicplate = convertView.findViewById(R.id.lblicplate);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            if(position==0){

                holder.lblNoUbic.setText("NoUbic");
                holder.lblNoUbic.setTextColor(R.style.titlestyle);
                holder.lblCodigo.setText("Codigo");
                holder.lblCodigo.setTextColor(R.style.titlestyle);
                holder.lblProducto_nombre.setText("Producto_nombre");
                holder.lblProducto_nombre.setTextColor(R.style.titlestyle);
                holder.lblUMBas.setText("UMBas");
                holder.lblUMBas.setTextColor(R.style.titlestyle);
                holder.lblPres.setText("Pres");
                holder.lblPres.setTextColor(R.style.titlestyle);
                holder.lblCant_Conteo.setText("Cant_Conteo");
                holder.lblCant_Conteo.setTextColor(R.style.titlestyle);
                holder.lblPeso_Conteo.setText("Peso_Conteo");
                holder.lblPeso_Conteo.setTextColor(R.style.titlestyle);
                holder.lblCant_Stock.setText("Cant_Stock");
                holder.lblCant_Stock.setTextColor(R.style.titlestyle);
                holder.lblPeso_Stock.setText("Peso_Stock");
                holder.lblPeso_Stock.setTextColor(R.style.titlestyle);
                holder.lblLote.setText("Lote");
                holder.lblLote.setTextColor(R.style.titlestyle);
                holder.lblFecha_Vence.setText("Fecha_Vence");
                holder.lblFecha_Vence.setTextColor(R.style.titlestyle);
                holder.lblConteo.setText("Cantidad");
                holder.lblConteo.setTextColor(R.style.titlestyle);
                holder.lblUbic_nombre.setText("Ubic_nombre");
                holder.lblUbic_nombre.setTextColor(R.style.titlestyle);
                holder.lblEstado.setText("Estado");
                holder.lblEstado.setTextColor(R.style.titlestyle);
                holder.lblIdProductoBodega.setText("IdProductoBodega");
                holder.lblIdProductoBodega.setTextColor(R.style.titlestyle);
                holder.lblTramo.setText("Tramo");
                holder.lblTramo.setTextColor(R.style.titlestyle);
                holder.lblIndiceX.setText("IndiceX");
                holder.lblIndiceX.setTextColor(R.style.titlestyle);
                holder.lblNivel.setText("Nivel");
                holder.lblNivel.setTextColor(R.style.titlestyle);
                holder.lblPos.setText("Pos");
                holder.lblPos.setTextColor(R.style.titlestyle);
                holder.lblFactor.setText("Factor");
                holder.lblFactor.setTextColor(R.style.titlestyle);
                holder.lblidinvreconteo.setText("id_reconteo");
                holder.lblidinvreconteo.setTextColor(R.style.titlestyle);
                holder.lbllicplate.setTextColor(R.style.titlestyle);

            }else{

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
                holder.lblFecha_Vence.setText(data_list.get(position).Fecha_Vence  +"");
                holder.lblConteo.setText(data_list.get(position).Conteo  +"");
                holder.lblUbic_nombre.setText(data_list.get(position).Ubic_nombre  +"");
                holder.lblEstado.setText(data_list.get(position).Estado  +"");
                holder.lblIdProductoBodega.setText(data_list.get(position).IdProductoBodega  +"");
                holder.lblTramo.setText(data_list.get(position).Tramo  +"");
                holder.lblIndiceX.setText(data_list.get(position).IndiceX  +"");
                holder.lblNivel.setText(data_list.get(position).Nivel  +"");
                holder.lblPos.setText(data_list.get(position).Pos  +"");
                holder.lblFactor.setText(data_list.get(position).Factor  +"");
                holder.lblidinvreconteo.setText(data_list.get(position).idinvreconteo +"");
                holder.lbllicplate.setText(data_list.get(position).Licence_plate +"");
            }


            if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            } else {
                if (position==0){
                    convertView.setBackgroundResource(R.drawable.color_medium);

                }else{
                    convertView.setBackgroundColor(Color.TRANSPARENT);
                }
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
                lblPeso_Stock,lblLote,lblFecha_Vence,lblConteo,lblUbic_nombre,lblEstado,lblIdProductoBodega,lblTramo,
                lblIndiceX,lblNivel,lblPos,lblFactor,lblidinvreconteo,lbllicplate;
    }
}
