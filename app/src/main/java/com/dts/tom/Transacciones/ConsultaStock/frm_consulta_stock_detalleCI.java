package com.dts.tom.Transacciones.ConsultaStock;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.InventarioCiclico.frm_inv_cic_add;

public class frm_consulta_stock_detalleCI extends PBase {


    private TextView lblcodigo,lbldescripcion,lblexUnidad,lblexPres,lblestado,lblpedido,lblpicking,lblvence,lbllote,lblubic,lblnomUbic,lblLicPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_consulta_stock_detalle_c_i);
        super.InitBase();

        lblcodigo = findViewById(R.id.lblcodigoCI);
        lbldescripcion = findViewById(R.id.lbldescripcionCI);
        lblexUnidad = findViewById(R.id.lblexUnidadCI);
        lblexPres = findViewById(R.id.lblexPresCI);
        lblestado = findViewById(R.id.lblestadoCI);
        lblpedido = findViewById(R.id.lblpedidoCI);
        lblpicking = findViewById(R.id.lblpickingCI);
        lblvence = findViewById(R.id.lblvenceCI);
        lbllote = findViewById(R.id.lblloteCI);
        lblubic = findViewById(R.id.lblubicCI);
        lblnomUbic = findViewById(R.id.lblnomUbicCI);
        lblLicPlate= findViewById(R.id.lblLicPlate);

        asignarDatos();

    }

    private void asignarDatos() {


        if(gl.existencia !=null){

            lblcodigo.setText( gl.existencia.Codigo +"");
            lbldescripcion.setText(gl.existencia.Nombre + "");
            lblexUnidad.setText(""+0);
            lblexPres.setText("");
            lblestado.setText( gl.existencia.Estado+"");
            lblpedido.setText( gl.existencia.Pedido+"");
            lblpicking.setText( gl.existencia.Pick+"");
            lblvence.setText( gl.existencia.Vence+"");
            lbllote.setText( gl.existencia.Lote+"");
            lblubic.setText( gl.existencia.idUbic+"");
            lblnomUbic.setText( gl.existencia.Ubic+"");

            //lblLicPlate.Text = "LicPlate : " & sval(IIf(sList.Lic_plate = "0", "", sList.Lic_plate))

            if(gl.existencia.LicPlate.equals(0)){
                lblLicPlate.setText( "LicPlate : " + "");
            }else{
                lblLicPlate.setText( "LicPlate : " + gl.existencia.LicPlate);
            }



        }

    }

    public void Backto(View view) {
        frm_consulta_stock_detalleCI.super.finish();
    }
}