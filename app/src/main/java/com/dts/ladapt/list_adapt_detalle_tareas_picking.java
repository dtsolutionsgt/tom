package com.dts.ladapt;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.tom.R;

import java.util.ArrayList;

public class list_adapt_detalle_tareas_picking extends BaseAdapter {

    private static ArrayList<clsBeTrans_picking_ubic> BeListTareasHH;

    private int selectedIndex;

    private LayoutInflater l_Inflater;

    public list_adapt_detalle_tareas_picking(Context context, ArrayList<clsBeTrans_picking_ubic> results) {
        BeListTareasHH = results;
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
        return BeListTareasHH.size();
    }

    public Object getItem(int position) {
        return BeListTareasHH.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_tareas_picking, null);
            holder = new ViewHolder();

            holder.lblUbicDetPick = (TextView) convertView.findViewById(R.id.lblUbicDetPick);
            holder.lblNomUbicDet = (TextView) convertView.findViewById(R.id.lblNomUbicDet);
            holder.lblCodigoDet = (TextView) convertView.findViewById(R.id.lblCodigoDet);
            holder.lblProductoDet = (TextView) convertView.findViewById(R.id.lblProductoDet);
            holder.lblUmbasDet = (TextView) convertView.findViewById(R.id.lblUmbasDet);
            holder.lblPresDet = (TextView) convertView.findViewById(R.id.lblPresDet);
            holder.lblSol = (TextView) convertView.findViewById(R.id.lblSol);
            holder.lblTarima = (TextView) convertView.findViewById(R.id.lblTarima);
            holder.lblRec = (TextView) convertView.findViewById(R.id.lblRec);
            holder.lblLoteDet = (TextView) convertView.findViewById(R.id.lblLoteDet);
            holder.lblLpDet = (TextView) convertView.findViewById(R.id.lblLpDet);
            holder.lblVenceDet = (TextView) convertView.findViewById(R.id.lblVenceDet);
            holder.lblNomEstadoDet = (TextView) convertView.findViewById(R.id.lblNomEstadoDet);
            holder.lblPesoDet = (TextView) convertView.findViewById(R.id.lblPesoDet);
            holder.lblIdPresDet = (TextView) convertView.findViewById(R.id.lblIdPresDet);
            holder.lblIdEstadoDet = (TextView) convertView.findViewById(R.id.lblIdEstadoDet);
            holder.lblIdPedidoEnc = (TextView) convertView.findViewById(R.id.lblIdPedidoEnc);
            holder.lblPedidoDet = (TextView) convertView.findViewById(R.id.lblPedidoDet);
            holder.lblIdPickingDet = (TextView) convertView.findViewById(R.id.lblIdPickingDet);
            holder.lblProductoBodegaDet = (TextView) convertView.findViewById(R.id.lblProductoBodegaDet);
            holder.lblPickingUbic = (TextView) convertView.findViewById(R.id.lblPickingUbic);
            holder.lblStock = (TextView) convertView.findViewById(R.id.lblStock);
            holder.lblStockRes = (TextView) convertView.findViewById(R.id.lblStockRes);
            holder.lblNombreArea = (TextView) convertView.findViewById(R.id.lblNombreArea);
            holder.lblNombreClasificacion = (TextView) convertView.findViewById(R.id.lblNombreClasificacion);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblUbicDetPick.setText("0");
        holder.lblNomUbicDet.setText("--");
        holder.lblCodigoDet.setText("--");
        holder.lblProductoDet.setText("--");
        holder.lblUmbasDet.setText("--");
        holder.lblPresDet.setText("--");
        holder.lblSol.setText("0");
        holder.lblTarima.setText("0");
        holder.lblRec.setText("0");
        holder.lblLoteDet.setText("--");
        holder.lblLpDet.setText("--");
        holder.lblVenceDet.setText("--");
        holder.lblNomEstadoDet.setText("--");
        holder.lblPesoDet.setText("0");
        holder.lblIdPresDet.setText("0");
        holder.lblIdEstadoDet.setText("0");
        holder.lblIdPedidoEnc.setText("0");
        holder.lblPedidoDet.setText("0");
        holder.lblIdPickingDet.setText("0");
        holder.lblProductoBodegaDet.setText("0");
        holder.lblPickingUbic.setText("0");
        holder.lblStock.setText("0");
        holder.lblStockRes.setText("0");

        if (BeListTareasHH.get(position).IdUbicacion>0){
            holder.lblUbicDetPick.setText(""+BeListTareasHH.get(position).IdUbicacion);
        }

        if (!BeListTareasHH.get(position).NombreUbicacion.isEmpty()){
            holder.lblNomUbicDet.setText(BeListTareasHH.get(position).NombreUbicacion);
        }

        if (!BeListTareasHH.get(position).CodigoProducto.isEmpty()){
            holder.lblCodigoDet.setText(BeListTareasHH.get(position).CodigoProducto);
        }

        if (!BeListTareasHH.get(position).NombreProducto.isEmpty()){
            holder.lblProductoDet.setText(BeListTareasHH.get(position).NombreProducto);
        }

        if (!BeListTareasHH.get(position).NombreProducto.isEmpty()){
            holder.lblProductoDet.setText(BeListTareasHH.get(position).NombreProducto);
        }

        if (!BeListTareasHH.get(position).ProductoUnidadMedida.isEmpty()){
            holder.lblUmbasDet.setText(BeListTareasHH.get(position).ProductoUnidadMedida);
        }

        if (!BeListTareasHH.get(position).ProductoPresentacion.isEmpty()){
            holder.lblPresDet.setText(BeListTareasHH.get(position).ProductoPresentacion);
        }

        if (BeListTareasHH.get(position).Cantidad_Solicitada>0){
            holder.lblSol.setText(""+BeListTareasHH.get(position).Cantidad_Solicitada);
        }

        if (BeListTareasHH.get(position).Tarima > 0){
            holder.lblTarima.setText(""+BeListTareasHH.get(position).Tarima);
        }

        if (BeListTareasHH.get(position).Cantidad_Recibida>0){
            holder.lblRec.setText(""+BeListTareasHH.get(position).Cantidad_Recibida);
        }

        if (!BeListTareasHH.get(position).Lote.isEmpty()){
            holder.lblLoteDet.setText(BeListTareasHH.get(position).Lote);
        }

        if (!BeListTareasHH.get(position).Lic_plate.isEmpty()){
            holder.lblLpDet.setText(BeListTareasHH.get(position).Lic_plate);
        }

        if (!BeListTareasHH.get(position).Fecha_Vence.isEmpty()){
            holder.lblVenceDet.setText(BeListTareasHH.get(position).Fecha_Vence);
        }

        if (!BeListTareasHH.get(position).ProductoEstado.isEmpty()){
            holder.lblNomEstadoDet.setText(BeListTareasHH.get(position).ProductoEstado);
        }

        if (BeListTareasHH.get(position).Peso_solicitado>0){
            holder.lblPesoDet.setText(""+BeListTareasHH.get(position).Peso_solicitado);
        }

        if (BeListTareasHH.get(position).IdPresentacion>0){
            holder.lblIdPresDet.setText(""+BeListTareasHH.get(position).IdPresentacion);
        }

        if (BeListTareasHH.get(position).IdProductoEstado>0){
            holder.lblIdEstadoDet.setText(""+BeListTareasHH.get(position).IdProductoEstado);
        }
        if (BeListTareasHH.get(position).IdPedidoEnc>0){
            holder.lblIdPedidoEnc.setText(""+BeListTareasHH.get(position).IdPedidoEnc);
        }
        if (BeListTareasHH.get(position).IdPedidoDet>0){
            holder.lblPedidoDet.setText(""+BeListTareasHH.get(position).IdPedidoDet);
        }
        if (BeListTareasHH.get(position).IdPickingDet>0){
            holder.lblIdPickingDet.setText(""+BeListTareasHH.get(position).IdPickingDet);
        }
        if (BeListTareasHH.get(position).IdProductoBodega>0){
            holder.lblProductoBodegaDet.setText(""+BeListTareasHH.get(position).IdProductoBodega);
        }
        if (BeListTareasHH.get(position).IdPickingUbic>0){
            holder.lblPickingUbic.setText(""+BeListTareasHH.get(position).IdPickingUbic);
        }
        if (BeListTareasHH.get(position).IdStock>0){
            holder.lblStock.setText(""+BeListTareasHH.get(position).IdStock);
        }
        if (BeListTareasHH.get(position).IdStockRes>0){
            holder.lblStockRes.setText(""+BeListTareasHH.get(position).IdStockRes);
        }
        if (!BeListTareasHH.get(position).NombreArea.isEmpty()){
            holder.lblNombreArea.setText(""+BeListTareasHH.get(position).NombreArea);
        }else{
            holder.lblNombreArea.setText("ND");
        }

        if (!BeListTareasHH.get(position).NombreClasificacion.isEmpty()){
            holder.lblNombreClasificacion.setText(""+BeListTareasHH.get(position).NombreClasificacion);
        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            
            convertView.setBackgroundColor(Color.TRANSPARENT);

        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblUbicDetPick,lblNomUbicDet,lblCodigoDet,lblProductoDet,lblUmbasDet,lblPresDet,lblSol,lblRec,
                lblLoteDet,lblLpDet,lblVenceDet,lblNomEstadoDet,lblPesoDet,lblIdPresDet,lblIdEstadoDet,
                lblIdPedidoEnc,lblPedidoDet,lblIdPickingDet,lblProductoBodegaDet,lblPickingUbic,lblStock,
                lblStockRes, lblTarima,lblNombreArea, lblNombreClasificacion;
    }

}
