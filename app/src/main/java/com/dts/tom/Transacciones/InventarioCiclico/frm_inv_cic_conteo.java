package com.dts.tom.Transacciones.InventarioCiclico;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramo;
import com.dts.classes.Transacciones.Inventario.InventarioTramo.clsBeTrans_inv_tramoList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.Transacciones.InventarioInicial.frm_inv_ini_tramos;

import java.util.ArrayList;

public class frm_inv_cic_conteo extends PBase {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_conteo);


    }

    public void Exit(View view) {
        msgAskExit("Está seguro de salir");
    }

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    /*BeInvTramo = new clsBeTrans_inv_tramo();
                    BeUbic = new clsBeBodega_ubicacion();
                    Listtramos = new clsBeTrans_inv_tramoList();
                    BeListTramos = new ArrayList<clsBeTrans_inv_tramo>();
                    TipoConteo = 0;*/

                    frm_inv_cic_conteo.super.finish();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}