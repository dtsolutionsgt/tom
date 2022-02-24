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

public class list_adapt_consulta_stock2 extends BaseAdapter {

    private ArrayList<clsBeVW_stock_res_CI> BeListStock;
    private Context cCont;
    private int selectedIndex;
    private LayoutInflater l_Inflater;

    public list_adapt_consulta_stock2(Context context, ArrayList<clsBeVW_stock_res_CI> results){
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

                convertView = l_Inflater.inflate(R.layout.activity_list_adapt_consulta_stock2, null);
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
                        holder.lblIdTipoEtiqueta = convertView.findViewById(R.id.lblIdTipoEtiqueta);
                        holder.lblResPres = convertView.findViewById(R.id.lblResPres);
                        holder.lblDispPres = convertView.findViewById(R.id.lblDispPres);
                        holder.lblNombreArea = convertView.findViewById(R.id.lblNombreArea);
                convertView.setTag(holder);

            }else {

                holder = (ViewHolder) convertView.getTag();

            }

            //AT 20211221 Ya no se crea el encabezado unicamente se agrega la información
            holder.lblCodigo.setText(BeListStock.get(position).Codigo  +" ");
            holder.lblNombre.setText(BeListStock.get(position).Nombre  +" ");
            holder.lblUM.setText(BeListStock.get(position).UM  +"");
            holder.lblExistUMBAs.setText(BeListStock.get(position).ExistUMBAs  +"");
            holder.lblPres.setText(BeListStock.get(position).Pres  +"");
            holder.lblExistPres.setText(BeListStock.get(position).ExistPres  +"");
            holder.lblReservadoUMBAs.setText(BeListStock.get(position).ReservadoUMBAs  +"");
            holder.lblDisponibleUMBas.setText(BeListStock.get(position).DisponibleUMBas  +"");
            holder.lblResPres.setText(BeListStock.get(position).ResPres+"");
            holder.lblDispPres.setText(BeListStock.get(position).DispPres+"");
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
            holder.lblIdTipoEtiqueta.setText(BeListStock.get(position).IdTipoEtiqueta  +"");
            holder.lblNombreArea.setText(BeListStock.get(position).NombreArea  +"");

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
            holder.lblIdTipoEtiqueta.setTextColor(Color.BLACK);
            holder.lblNombreArea.setTextColor(Color.BLACK);

            if(selectedIndex!= -1 && position == selectedIndex) {
                convertView.setBackgroundColor(Color.rgb(0, 128, 0));
            } else {

                //AT 20211221 se quitó la valicación para darle color al encabezado
               /* if (position==0){
                    convertView.setBackgroundResource(R.drawable.color_medium);
               // }else{
                    convertView.setBackgroundColor(Color.TRANSPARENT);
                }*/
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
        TextView lblCodigo,lblNombre,lblUM,lblExistUMBAs,
                lblPres,lblExistPres,lblReservadoUMBAs,lblDisponibleUMBas,
                lblLote,lblVence,lblEstado,lblUbic,lblidUbic,lblPedido,
                lblPick,lbLicPlate,lblIdProductoBodega,lblIngreso, lblIdTipoEtiqueta,
                lblResPres, lblDispPres,lblNombreArea;
    }
}
