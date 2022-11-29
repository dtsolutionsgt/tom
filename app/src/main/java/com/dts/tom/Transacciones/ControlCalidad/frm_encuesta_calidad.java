package com.dts.tom.Transacciones.ControlCalidad;

import android.os.Bundle;
import android.view.View;

import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_encuesta_calidad extends PBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_encuesta_calidad);
    }

    public void Exit(View view) {
        super.finish();
    }
}