package com.dts.tom.Transacciones.ConsultaStock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_detalle_consulta_stock_CI extends PBase {

    clsBeVW_stock_res_CI DataSelected;
    private TextView lblcodigo,lbldescripcion,lblexUnidad,lblexPres,lblestado,lblpedido,lblpicking,lblvence,lbllote,lblubic,lblnomUbic;

protected  void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.frm_detalle_consulta_stock_ci);

    if(getIntent().getExtras() != null) {
        DataSelected = (clsBeVW_stock_res_CI) getIntent().getParcelableExtra("ItemSelected");
    }


    super.InitBase();

    lblcodigo = findViewById(R.id.lblcodigo);
    lbldescripcion = findViewById(R.id.lbldescripcion);
    lblexUnidad = findViewById(R.id.lblexUnidad);
    lblexPres = findViewById(R.id.lblexPres);
    lblestado = findViewById(R.id.lblestado);
    lblpedido = findViewById(R.id.lblpedido);
    lblpicking = findViewById(R.id.lblpicking);
    lblvence = findViewById(R.id.lblvence);
    lbllote = findViewById(R.id.lbllote);
    lblubic = findViewById(R.id.lblubic);
    lblnomUbic = findViewById(R.id.lblnomUbic);

    asignarDatos();

}

    private void asignarDatos() {

        lblcodigo.setText(DataSelected.Codigo);
        lbldescripcion.setText(""+0);
        lblexUnidad.setText(""+0);
        lblexPres.setText("");
        lblestado.setText(DataSelected.Estado);
        lblpedido.setText(DataSelected.Pedido);
        lblpicking.setText(DataSelected.Pick);
        lblvence.setText(DataSelected.Vence);
        lbllote.setText(DataSelected.Lote);
        lblubic.setText(DataSelected.idUbic);
        lblnomUbic.setText(DataSelected.Ubic);

    }

}
