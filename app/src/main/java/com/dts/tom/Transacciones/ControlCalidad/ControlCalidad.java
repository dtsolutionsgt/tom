package com.dts.tom.Transacciones.ControlCalidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.Transacciones.ControlCalidad.clsBeCalidad;
import com.dts.classes.Transacciones.ControlCalidad.clsBeCalidadList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.ladapt.ControlCalidad.list_adapt_control_calidad;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

public class ControlCalidad extends PBase {

    private TextView lblDescProd, lblEstadoDesc;
    private EditText txtCodigo;
    private ListView listCalidad;

    private list_adapt_control_calidad AdapterControlCalidad;
    private clsBeCalidad item = new clsBeCalidad();
    private ArrayList<clsBeCalidad> ListaCalidad = new ArrayList<>() ;
    public static clsBeCalidad AuxItem = new clsBeCalidad();
    private int browse = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_calidad);

        super.InitBase();

        lblDescProd = findViewById(R.id.lblDescProd);
        lblEstadoDesc = findViewById(R.id.lblEstadoDesc);
        listCalidad = findViewById(R.id.listCalidad);
        txtCodigo = findViewById(R.id.txtCodigo);

        gl.actual = 0;
        txtCodigo.requestFocus();

        setHandlers();
        CargaLista();
    }

    private void CargaLista() {
        if (ListaCalidad != null) ListaCalidad.clear();

        item = new clsBeCalidad();
        item.id = 1;
        item.Texto = "T-LLANTAS Y RUEDAS";
        item.Estado = false;
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

        SetLista();
    }

    private void setHandlers() {

        txtCodigo.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!txtCodigo.getText().toString().isEmpty()) {
                        lblEstadoDesc.setText("Nuevo");
                        lblDescProd.setText("Moto CC150 / Negra");
                    }
                }

                return false;
            }
        });

        listCalidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (!lblDescProd.getText().toString().isEmpty()) {
                    AuxItem = (clsBeCalidad) listCalidad.getItemAtPosition(position);
                    browse = 2;
                    startActivity(new Intent(ControlCalidad.this, frm_encuesta_calidad.class));
                } else {
                    toast("Ingrese código");
                }
            }
        });
    }

    private void SetLista() {

        AdapterControlCalidad = new list_adapt_control_calidad(getApplicationContext(),ListaCalidad,  gl.actual);
        listCalidad.setAdapter(AdapterControlCalidad);
    }

    public void Exit(View view) {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (browse == 2) {
            if (gl.actual < 1) {
                gl.actual +=1;
            }

            if (gl.Completo) {
                ListaCalidad.get(0).Estado = true;

            }

            SetLista();
        }
    }
}