package com.dts.tom.Transacciones.ControlCalidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.Transacciones.ControlCalidad.clsBeCalidad;
import com.dts.classes.Transacciones.ControlCalidad.clsBeCalidadList;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.ladapt.ControlCalidad.list_adapt_control_calidad;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

public class ControlCalidad extends PBase {

    private TextView lblDescProd;
    private ListView listCalidad;

    private list_adapt_control_calidad AdapterControlCalidad;
    private clsBeCalidad item = new clsBeCalidad();
    private ArrayList<clsBeCalidad> ListaCalidad = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_calidad);

        super.InitBase();

        lblDescProd = findViewById(R.id.lblDescProd);
        listCalidad = findViewById(R.id.listCalidad);

        setHandlers();
        CargaLista();
    }

    private void CargaLista() {
        if (ListaCalidad != null) ListaCalidad.clear();

        item = new clsBeCalidad();
        item.id = 1;
        item.Texto = "T-LLANTAS Y RUEDAS";
        item.Estado = true;
        ListaCalidad.add(item);

        item = new clsBeCalidad();
        item.id = 2;
        item.Texto = "C-CONTROLES";
        item.Estado = false;
        ListaCalidad.add(item);

        item = new clsBeCalidad();
        item.id = 3;
        item.Texto = "L-LUCES Y ESPEJOS";
        item.Estado = false;
        ListaCalidad.add(item);

        item = new clsBeCalidad();
        item.id = 4;
        item.Texto = "O-ACEITE Y OTROS FLUIDOS";
        item.Estado = false;
        ListaCalidad.add(item);

        item = new clsBeCalidad();
        item.id = 5;
        item.Texto = "C-CHASIS";
        item.Estado = false;
        ListaCalidad.add(item);

        item = new clsBeCalidad();
        item.id = 6;
        item.Texto = "S-SOPORTES DE PARO";
        item.Estado = false;
        ListaCalidad.add(item);

        AdapterControlCalidad = new list_adapt_control_calidad(getApplicationContext(),ListaCalidad, true);
        listCalidad.setAdapter(AdapterControlCalidad);
    }

    private void setHandlers() {

        listCalidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                startActivity(new Intent(ControlCalidad.this, frm_encuesta_calidad.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}