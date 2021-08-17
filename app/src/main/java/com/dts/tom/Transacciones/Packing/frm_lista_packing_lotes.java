package com.dts.tom.Transacciones.Packing;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_lista_packing_lotes extends PBase {

    private ListView listView;
    private EditText txtTarea,txtCant,txtBulto,txtCamas;
    private TextView lblNombre;

    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_packing_lotes);

        super.InitBase();

        txtTarea = findViewById(R.id.editText8);
        txtCant = findViewById(R.id.editTextNumber2);
        txtBulto = findViewById(R.id.editTextTextPersonName);
        txtCamas = findViewById(R.id.editTextNumber3);
        lblNombre = findViewById(R.id.lblTituloForma);lblNombre.setText(gl.paNombre);

        codigo=gl.paCodigo;
    }

    //region Events


    //endregion

    //region Main


    //endregion

    //region Aux


    //endregion

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion

}