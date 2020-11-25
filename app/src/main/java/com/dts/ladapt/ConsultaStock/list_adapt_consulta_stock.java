package com.dts.ladapt.ConsultaStock;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.ladapt.Verificacion.list_adapt_tareas_verificacion;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_consulta_stock extends BaseAdapter {

    private ArrayList<clsBeVW_stock_res_CI> BeListStock;
    private Context cCont;
    private int selectedIndex;
    private LayoutInflater l_Inflater;

    public list_adapt_consulta_stock(Context context, ArrayList<clsBeVW_stock_res_CI> results){
        BeListStock = results;
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

        return BeListStock.size();
    }

    @Override
    public Object getItem(int position) {
        return BeListStock.get(position);
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

                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_consulta_stock, null);
                holder = new ViewHolder();

                        holder.lblCodigo = convertView.findViewById(R.id.lblCodigo);
                        holder.lblNombre = convertView.findViewById(R.id.lblNombre);
                        holder.lblUM = convertView.findViewById(R.id.lblUM);
                        holder.lblExistUMBAs = convertView.findViewById(R.id.lblExistUMBAs);
                        holder.lblPres = convertView.findViewById(R.id.lblPres);
                        holder.lblExistPres = convertView.findViewById(R.id.lblExistPres);
                        holder.lblReservadoUMBAs = convertView.findViewById(R.id.lblReservadoUMBAs);
                        holder.lblDisponibleUMBas = convertView.findViewById(R.id.lblDisponibleUMBas);
                        holder.lblLote = convertView.findViewById(R.id.lblLote);
                        holder.lblVence = convertView.findViewById(R.id.lblVence);
                        holder.lblEstado = convertView.findViewById(R.id.lblEstado);
                        holder.lblUbic = convertView.findViewById(R.id.lblUbic);
                        holder.lblidUbic = convertView.findViewById(R.id.lblidUbic);
                        holder.lblPedido = convertView.findViewById(R.id.lblPedido);
                        holder.lblPick = convertView.findViewById(R.id.lblPick);
                        holder.lbLicPlate = convertView.findViewById(R.id.lblLicPlate_ci);
                        holder.lblIdProductoBodega = convertView.findViewById(R.id.lblIdProductoBodega);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder) convertView.getTag();

            }

            if (position==0) {

                holder.lblCodigo.setText("Código");
                holder.lblNombre.setText("Nombre");
                holder.lblUM.setText("UM");
                holder.lblExistUMBAs.setText("ExistUMBAs");
                holder.lblPres.setText("Pres");
                holder.lblExistPres.setText("ExistPres");
                holder.lblReservadoUMBAs.setText("ReservadoUMBAs");
                holder.lblDisponibleUMBas.setText("DisponibleUMBas");
                holder.lblLote.setText("Lote");
                holder.lblVence.setText("Vence");
                holder.lblEstado.setText("Estado");
                holder.lblUbic.setText("Ubic");
                holder.lblidUbic.setText("idUbic");
                holder.lblPedido.setText("Pedido");
                holder.lblPick.setText("Pick");
                holder.lbLicPlate.setText("LicPlate");
                holder.lblIdProductoBodega.setText("IdProdBodega");

            }else{
                holder.lblCodigo.setText(BeListStock.get(position).Codigo  +"");
                holder.lblNombre.setText(BeListStock.get(position).Nombre  +"");
                holder.lblUM.setText(BeListStock.get(position).UM  +"");
                holder.lblExistUMBAs.setText(BeListStock.get(position).ExistUMBAs  +"");
                holder.lblPres.setText(BeListStock.get(position).Pres  +"");
                holder.lblExistPres.setText(BeListStock.get(position).ExistPres  +"");
                holder.lblReservadoUMBAs.setText(BeListStock.get(position).ReservadoUMBAs  +"");
                holder.lblDisponibleUMBas.setText(BeListStock.get(position).DisponibleUMBas  +"");
                holder.lblLote.setText(BeListStock.get(position).Lote  +"");
                holder.lblVence.setText(BeListStock.get(position).Vence  +"");
                holder.lblEstado.setText(BeListStock.get(position).Estado  +"");
                holder.lblUbic.setText(BeListStock.get(position).Ubic  +"");
                holder.lblidUbic.setText(BeListStock.get(position).idUbic  +"");
                holder.lblPedido.setText(BeListStock.get(position).Pedido  +"");
                holder.lblPick.setText(BeListStock.get(position).Pick  +"");
                holder.lbLicPlate.setText(BeListStock.get(position).LicPlate  +"");
                holder.lblIdProductoBodega.setText(BeListStock.get(position).IdProductoBodega  +"");
            }
        }
        catch (Exception ex){
            toast(ex.getMessage());
        }
        return convertView;
    }

    public void toast(String msg) {
        Toast.makeText(cCont, msg, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        TextView lblCodigo,lblNombre,lblUM,lblExistUMBAs,
                lblPres,lblExistPres,lblReservadoUMBAs,lblDisponibleUMBas,
                lblLote,lblVence,lblEstado,lblUbic,lblidUbic,lblPedido,
                lblPick,lbLicPlate,lblIdProductoBodega;
    }
}
