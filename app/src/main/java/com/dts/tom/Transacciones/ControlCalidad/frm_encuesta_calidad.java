package com.dts.tom.Transacciones.ControlCalidad;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_encuesta_calidad extends PBase {

    private CheckBox chk1, chk2, chk3, chk4;
    private RadioButton chkFrontal, chkTrasera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_encuesta_calidad);

        super.InitBase();

        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);
        chk3 = findViewById(R.id.chk3);
        chk4 = findViewById(R.id.chk4);
        chkFrontal = findViewById(R.id.chkFrontal);
        chkTrasera = findViewById(R.id.chkTrasera);

    }

    public void Exit(View view) {
        super.finish();
    }
}