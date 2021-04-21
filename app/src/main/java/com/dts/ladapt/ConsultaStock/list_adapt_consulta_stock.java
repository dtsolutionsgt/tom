package com.dts.ladapt.ConsultaStock;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
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
                        holder.lblIngreso = convertView.findViewById(R.id.lblIngreso);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder) convertView.getTag();

            }

            if (position==0) {

                holder.lblCodigo.setText("Código");
                holder.lblNombre.setText("Nombre");
                holder.lblUM.setText("UM");
                holder.lblExistUMBAs.setText("Exist UMB");
                holder.lblPres.setText("Pres");
                holder.lblExistPres.setText("ExistPres");
                holder.lblReservadoUMBAs.setText("Res. UMB");
                holder.lblDisponibleUMBas.setText("Dispo UMB");
                holder.lblLote.setText("Lote");
                holder.lblVence.setText("Vence");
                holder.lblEstado.setText("Estado");
                holder.lblUbic.setText("Ubic");
                holder.lblidUbic.setText("idUbic");
                holder.lblPedido.setText("Pedido");
                holder.lblPick.setText("Pick");
                holder.lbLicPlate.setText("LicPlate");
                holder.lblIdProductoBodega.setText("IdProdBod");
                holder.lblIngreso.setText("Ingreso");

                holder.lblCodigo.setTextColor(Color.WHITE);
                holder.lblNombre.setTextColor(Color.WHITE);
                holder.lblUM.setTextColor(Color.WHITE);
                holder.lblExistUMBAs.setTextColor(Color.WHITE);
                holder.lblPres.setTextColor(Color.WHITE);
                holder.lblExistPres.setTextColor(Color.WHITE);
                holder.lblReservadoUMBAs.setTextColor(Color.WHITE);
                holder.lblDisponibleUMBas.setTextColor(Color.WHITE);
                holder.lblLote.setTextColor(Color.WHITE);
                holder.lblVence.setTextColor(Color.WHITE);
                holder.lblEstado.setTextColor(Color.WHITE);
                holder.lblUbic.setTextColor(Color.WHITE);
                holder.lblidUbic.setTextColor(Color.WHITE);
                holder.lblPedido.setTextColor(Color.WHITE);
                holder.lblPick.setTextColor(Color.WHITE);
                holder.lbLicPlate.setTextColor(Color.WHITE);
                holder.lblIdProductoBodega.setTextColor(Color.WHITE);
                holder.lblIngreso.setTextColor(Color.WHITE);

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
                holder.lblIngreso.setText(BeListStock.get(position).ingreso  +"");

                holder.lblCodigo.setTextColor(Color.BLACK);
                holder.lblNombre.setTextColor(Color.BLACK);
                holder.lblUM.setTextColor(Color.BLACK);
                holder.lblExistUMBAs.setTextColor(Color.BLACK);
                holder.lblPres.setTextColor(Color.BLACK);
                holder.lblExistPres.setTextColor(Color.BLACK);
                holder.lblReservadoUMBAs.setTextColor(Color.BLACK);
                holder.lblDisponibleUMBas.setTextColor(Color.BLACK);
                holder.lblLote.setTextColor(Color.BLACK);
                holder.lblVence.setTextColor(Color.BLACK);
                holder.lblEstado.setTextColor(Color.BLACK);
                holder.lblUbic.setTextColor(Color.BLACK);
                holder.lblidUbic.setTextColor(Color.BLACK);
                holder.lblPedido.setTextColor(Color.BLACK);
                holder.lblPick.setTextColor(Color.BLACK);
                holder.lbLicPlate.setTextColor(Color.BLACK);
                holder.lblIdProductoBodega.setTextColor(Color.BLACK);
                holder.lblIngreso.setTextColor(Color.BLACK);
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
        TextView lblCodigo,lblNombre,lblUM,lblExistUMBAs,
                lblPres,lblExistPres,lblReservadoUMBAs,lblDisponibleUMBas,
                lblLote,lblVence,lblEstado,lblUbic,lblidUbic,lblPedido,
                lblPick,lbLicPlate,lblIdProductoBodega,lblIngreso;
    }
}
