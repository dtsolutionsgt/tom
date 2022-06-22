package com.dts.tom.Transacciones.Reabastecimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dts.tom.PBase;
import com.dts.tom.R;

public class frm_reabastecimiento_manual extends PBase {
    private TextView txtCodigoPrd, txtLicencia, txtUbicOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_reabastecimiento_manual);

        super.InitBase();

        txtCodigoPrd = findViewById(R.id.txtCodigoPrd);
        txtLicencia = findViewById(R.id.txtLicencia);
        txtUbicOrigen = findViewById(R.id.txtUbicOrigen);

    }

    public void Regresar(View view) {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}