package com.dts.tom.Transacciones.Packing;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.ladapt.list_adapt_packing_lp;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class frm_lista_packing_lp extends PBase {

    private ListView listView;
    private EditText txtFiltro;
    private TextView lblFiltro, lblPackingLicencia;

    private list_adapt_packing_lp adapter;

    public ArrayList<clsBeTrans_packing_lotes> items = new ArrayList<clsBeTrans_packing_lotes>();
    private clsBeTrans_packing_lotes sitem = new clsBeTrans_packing_lotes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_packing_lp);

        super.InitBase();

        listView = findViewById(R.id.listView1);
        txtFiltro = findViewById(R.id.editTextTextPersonName);
        lblFiltro = findViewById(R.id.lblTituloForma);
        lblPackingLicencia = findViewById(R.id.lblPackingLicencia);

        lblPackingLicencia.setText("Licencia Packing: "+ gl.LicenciaPacking);

        if (gl.filtroprod.isEmpty()) {
            lblFiltro.setVisibility(View.GONE);
        } else {
            lblFiltro.setVisibility(View.VISIBLE);
            lblFiltro.setText("Filtro : "+gl.filtroprod);
            txtFiltro.setText(gl.filtroprod);
        }

        gl.paBulto="";
        gl.auxPacking = null;
        setHandlers();
        listItems();
    }

    //region Events

    public void doFilter(View view) {
        listItems();
    }

    public void salirTareas(View view) {
        finish();
    }

    private void setHandlers() {

        try {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object lvObj = listView.getItemAtPosition(position);

                    sitem = (clsBeTrans_packing_lotes) lvObj;
                    //gl.paBulto=sitem.lote;
                    //gl.paEstado=sitem.estado;

                    /*if (sitem.lote.isEmpty()) {
                        msgbox("No se pueden adicionar productos sin licencia al empaque por tarima");
                    } else {
                        finish();
                    }*/

                    showFormDialog();
                }
            });

            txtFiltro.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        listItems();
                    }
                    return false;
                }
            });

        } catch (Exception e){
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }


    //endregion

    //region Main

    private void listItems() {
        clsBeTrans_packing_lotes item;
        boolean flag;
        int pp;
        String ft=txtFiltro.getText().toString().toUpperCase();

        try {
            items.clear();

            for (int i = 0; i <gl.packlotes.size(); i++) {
                item=gl.packlotes.get(i);
                flag=false;
                if (ft.isEmpty()) {
                    flag=true;
                } else {
                    if (item.producto.toUpperCase().indexOf(ft)>=0 |
                        item.licencia.toUpperCase().indexOf(ft)>=0) flag=true;
                }
                if (flag) items.add(item);
            }

            if (items.size()>0) {
                if (gl.filtroprod.isEmpty()) {
                    Collections.sort(items,new OrdenarPorProducto());
                } else {
                    Collections.sort(items,new OrdenarPorLote());
                }
            }

            adapter=new list_adapt_packing_lp(this,items);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            mu.msgbox("listItems : "+e.getMessage());
        }

        txtFiltro.selectAll();
        focusFiltro();
    }
    //endregion

    //region Aux

    public class OrdenarPorProducto implements Comparator<clsBeTrans_packing_lotes> {
        public int compare(clsBeTrans_packing_lotes left,clsBeTrans_packing_lotes rigth){
            return left.presentacion.compareTo(rigth.presentacion);
        }
    }

    public class OrdenarPorLote implements Comparator<clsBeTrans_packing_lotes> {
        public int compare(clsBeTrans_packing_lotes left,clsBeTrans_packing_lotes rigth){
            return left.lote.compareTo(rigth.lote);
        }
    }

    private void focusFiltro() {
        Handler mtimer = new Handler();
        Runnable mrunner=new Runnable() {
            @Override
            public void run() {
                txtFiltro.requestFocus();
            }
        };
        mtimer.postDelayed(mrunner,200);
    }

    //endregion

    private void showFormDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_form, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView txtProducto = dialogView.findViewById(R.id.txtProducto);
        TextView txtLicencia = dialogView.findViewById(R.id.txtLicencia);
        EditText txtCantidad = dialogView.findViewById(R.id.txtCantidad);

        txtProducto.setText(sitem.codigo + " - " + sitem.producto);
        txtLicencia.setText(sitem.licencia);
        txtCantidad.setText("" + sitem.disp);

        txtCantidad.setSelectAllOnFocus(true);

        builder.setView(dialogView)
                .setPositiveButton("Aceptar", null)
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        txtCantidad.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                procesarCantidad(dialogView, dialog);
                return true;
            }
            return false;
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> procesarCantidad(dialogView, dialog));
    }

    private void procesarCantidad(View dialogView, AlertDialog dialog) {
        EditText txtCantidad = dialogView.findViewById(R.id.txtCantidad);
        double cantidad = 0;

        try {
            cantidad = Double.parseDouble(txtCantidad.getText().toString());
        } catch (NumberFormatException e) {
            toast("Por favor ingrese una cantidad válida.");
            return;
        }

        if (cantidad > 0) {
            if (cantidad > sitem.disp) {
                toast("La cantidad no debe ser mayor a la pickeada.");
            } else {
                gl.auxPacking = sitem;
                gl.auxPacking.cant = cantidad;
                dialog.dismiss();  // Cierra el cuadro de diálogo correctamente
                finish();
            }
        } else {
            toast("La cantidad debe ser mayor a 0.");
        }
    }
}