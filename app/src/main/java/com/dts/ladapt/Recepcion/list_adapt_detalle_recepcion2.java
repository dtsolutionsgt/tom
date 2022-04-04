package com.dts.ladapt.Recepcion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.tom.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class list_adapt_detalle_recepcion2 extends BaseAdapter {

    private static ArrayList<clsBeTrans_oc_det> BeDetalleOC;
    private int selectedIndex;
    private LayoutInflater l_Inflater;
    private boolean Es_Poliza_Consolidada=false;
    private int decimales_redondeo;


    public list_adapt_detalle_recepcion2(Context context, ArrayList<clsBeTrans_oc_det> results,
                                         boolean pEs_Poliza_Consolidada, int pDecimales_redondeo) {
        BeDetalleOC = results;
        l_Inflater = LayoutInflater.from(context);
        selectedIndex = -1;
        Es_Poliza_Consolidada = pEs_Poliza_Consolidada;
        decimales_redondeo = pDecimales_redondeo;

    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public void refreshItems() {
        notifyDataSetChanged();
    }

    public int getCount() {
        return BeDetalleOC.size();
    }

    public Object getItem(int position) {
        return BeDetalleOC.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.activity_list_adapt_detalle_recepcion2, null);
            holder = new ViewHolder();

            holder.lblNoLinea = (TextView) convertView.findViewById(R.id.lblNoLinea);
            holder.lblCodigo = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.lblProducto = (TextView) convertView.findViewById(R.id.lblProducto);
            holder.lblPres = (TextView) convertView.findViewById(R.id.lblPres);
            holder.lblUmbas = (TextView) convertView.findViewById(R.id.lblUmbas);
            holder.lblCantidad = (TextView) convertView.findViewById(R.id.lblCantidad);
            holder.lblCantRec = (TextView) convertView.findViewById(R.id.lblCantRec);
            holder.lblDiferencia = (TextView) convertView.findViewById(R.id.lblDiferencia);
            holder.lblCosto = (TextView) convertView.findViewById(R.id.lblCosto);
            holder.lblFactor = (TextView) convertView.findViewById(R.id.lblFactor);
            holder.lblIdOcDet = (TextView) convertView.findViewById(R.id.lblIdOcDet);
            holder.lblIdOcEnc = (TextView) convertView.findViewById(R.id.lblIdOcEnc);
            holder.lblIdOcEnc = (TextView) convertView.findViewById(R.id.lblIdOcEnc);
            holder.lblIdPropietarioBodega = (TextView) convertView.findViewById(R.id.lblIdPropietarioBodega);
            holder.lblNombrePropietario =  (TextView) convertView.findViewById(R.id.lblNombrePropietario);
            holder.lblShipper = (TextView) convertView.findViewById(R.id.lblShipper);
            holder.lblClasificacion = (TextView) convertView.findViewById(R.id.lblClasificacion);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position==0){


            holder.lblNoLinea.setText("NoLinea");
            holder.lblCodigo.setText("Código");
            holder.lblProducto.setText("Producto");
            holder.lblShipper.setText("Embarcador");
            holder.lblClasificacion.setText("Clasificacion");
            holder.lblPres.setText("Presentación");
            holder.lblUmbas.setText("UmBas");
            holder.lblCantidad.setText("Cantidad");
            holder.lblCantRec.setText("CantidadRec");
            holder.lblDiferencia.setText("Diferencia");
            holder.lblCosto.setText("Costo");
            holder.lblFactor.setText("Factor");
            holder.lblIdOcDet.setText("IdDocDet");
            holder.lblIdOcDet.setVisibility(View.GONE);
            holder.lblIdOcEnc.setText("IdDocEnc");
            holder.lblIdOcEnc.setVisibility(View.GONE);
            holder.lblIdPropietarioBodega.setText("IdPropietarioBodega");
            holder.lblNombrePropietario.setText("Propietario");
            if (!Es_Poliza_Consolidada) {
                holder.lblIdPropietarioBodega.setVisibility(View.GONE);
                holder.lblNombrePropietario.setVisibility(View.GONE);
            }

        }else{


            holder.lblNoLinea.setText("0");
            holder.lblCodigo.setText("--");
            holder.lblProducto.setText("--");
            holder.lblPres.setText("--");
            holder.lblUmbas.setText("--");
            holder.lblCantidad.setText("0");
            holder.lblCantRec.setText("0");
            holder.lblDiferencia.setText("0");
            holder.lblCosto.setText("0");
            holder.lblFactor.setText("0");
            holder.lblIdOcDet.setText("0");
            holder.lblIdOcDet.setVisibility(View.GONE);
            holder.lblIdOcEnc.setText("0");
            holder.lblIdOcEnc.setVisibility(View.GONE);
            holder.lblIdPropietarioBodega.setText("0");
            holder.lblNombrePropietario.setText("--");
            if (!Es_Poliza_Consolidada) {
                holder.lblIdPropietarioBodega.setVisibility(View.GONE);
                holder.lblNombrePropietario.setVisibility(View.GONE);
            }

            if(BeDetalleOC.get(position).No_Linea>0){
                holder.lblNoLinea.setText(""+BeDetalleOC.get(position).No_Linea);
            }
            if(BeDetalleOC.get(position).Producto.Codigo!=null){
                holder.lblCodigo.setText(BeDetalleOC.get(position).Producto.Codigo);
            }

            if(BeDetalleOC.get(position).Producto.Nombre!=null){
                holder.lblProducto.setText(BeDetalleOC.get(position).Producto.Nombre);
            }

            if(BeDetalleOC.get(position).Presentacion.Nombre!=null){
                holder.lblPres.setText(BeDetalleOC.get(position).Presentacion.Nombre);
            }

            if(BeDetalleOC.get(position).UnidadMedida.Nombre!=null){
                holder.lblUmbas.setText(BeDetalleOC.get(position).UnidadMedida.Nombre);
            }

            if(BeDetalleOC.get(position).Cantidad>0){
                holder.lblCantidad.setText(""+BeDetalleOC.get(position).Cantidad);
            }

            if(BeDetalleOC.get(position).Cantidad_recibida>0){

                double input_valor = BeDetalleOC.get(position).Cantidad_recibida;
                BigDecimal bd = new BigDecimal(input_valor).setScale(decimales_redondeo, RoundingMode.HALF_UP);
                //holder.lblCantRec.setText(""+BeDetalleOC.get(position).Cantidad_recibida);
                holder.lblCantRec.setText(""+bd.doubleValue());
            }

            double input_valor = BeDetalleOC.get(position).Cantidad_recibida-BeDetalleOC.get(position).Cantidad;
            BigDecimal bd = new BigDecimal(input_valor).setScale(decimales_redondeo, RoundingMode.HALF_UP);

            //holder.lblDiferencia.setText(""+(BeDetalleOC.get(position).Cantidad_recibida-BeDetalleOC.get(position).Cantidad));
                holder.lblDiferencia.setText(""+bd.doubleValue());


            if(BeDetalleOC.get(position).Costo>0){
                holder.lblCosto.setText(""+BeDetalleOC.get(position).Costo);
            }

            if(BeDetalleOC.get(position).FactorPresentacion>0){
                holder.lblFactor.setText(""+BeDetalleOC.get(position).FactorPresentacion);
            }

            if(BeDetalleOC.get(position).IdOrdenCompraDet>0){
                holder.lblIdOcDet.setText(""+BeDetalleOC.get(position).IdOrdenCompraDet);
            }

            if(BeDetalleOC.get(position).IdOrdenCompraEnc>0){
                holder.lblIdOcEnc.setText(""+BeDetalleOC.get(position).IdOrdenCompraEnc);
            }

            if(BeDetalleOC.get(position).IdPropietarioBodega>0){
                holder.lblIdPropietarioBodega.setText(""+BeDetalleOC.get(position).IdPropietarioBodega);
            }

            if(!BeDetalleOC.get(position).Nombre_Propietario.isEmpty()){
                holder.lblNombrePropietario.setText(""+BeDetalleOC.get(position).Nombre_Propietario);
            }

            holder.lblShipper.setText(""+ BeDetalleOC.get(position).Nombre_Embarcador);
            holder.lblClasificacion.setText(""+ BeDetalleOC.get(position).Nombre_Clasificacion);

        }

        if(selectedIndex!= -1 && position == selectedIndex) {
            convertView.setBackgroundColor(Color.rgb(0, 128, 0));
        } else {
            if (position==0){
                convertView.setBackgroundResource(R.drawable.color_medium);
            }else{
                //#CKFK 20210525 Aquí ponemos la fila de color verde claro para identificar que ya fue recepcionado
                if (BeDetalleOC.get(position).Cantidad_recibida == BeDetalleOC.get(position).Cantidad) {
                    convertView.setBackgroundColor(Color.parseColor("#c9ffc0"));
                }else if (BeDetalleOC.get(position).Cantidad_recibida < BeDetalleOC.get(position).Cantidad  &&
                          BeDetalleOC.get(position).Cantidad_recibida >0) {
                    convertView.setBackgroundColor(Color.parseColor("#f5ffae"));
                }else if (BeDetalleOC.get(position).Cantidad_recibida < BeDetalleOC.get(position).Cantidad  &&
                        BeDetalleOC.get(position).Cantidad_recibida == 0) {
                    convertView.setBackgroundColor(Color.parseColor("#ff8080"));
                }else if (BeDetalleOC.get(position).Cantidad_recibida > BeDetalleOC.get(position).Cantidad) {
                    convertView.setBackgroundColor(Color.parseColor("#80c3ff"));
                }
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lblNoLinea,
                 lblCodigo,
                 lblProducto,
                 lblPres,
                 lblUmbas,
                 lblCantidad,
                 lblCantRec,
                 lblDiferencia,
                 lblCosto,
                 lblFactor,
                 lblIdOcDet,
                 lblIdOcEnc,
                 lblIdPropietarioBodega,
                 lblNombrePropietario,
                 lblShipper,
                 lblClasificacion;
    }

}
