package com.dts.tom.Transacciones.Recepcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.dts.tom.DrawingView;
import com.dts.tom.PBase;
import com.dts.tom.R;

public class frmFirma extends PBase {

    private DrawingView txtFirma;
    public String firmaRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_firma);
        txtFirma = new DrawingView(this, null);
    }
}
