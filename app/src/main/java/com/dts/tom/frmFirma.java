package com.dts.tom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

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
