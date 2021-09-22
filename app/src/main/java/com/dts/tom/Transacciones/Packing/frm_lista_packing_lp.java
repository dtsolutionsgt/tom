package com.dts.tom.Transacciones.Packing;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
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
    private TextView lblFiltro;

    private list_adapt_packing_lp adapter;

    public ArrayList<clsBeTrans_packing_lotes> items = new ArrayList<clsBeTrans_packing_lotes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_packing_lp);

        super.InitBase();

        listView = findViewById(R.id.listView1);
        txtFiltro = findViewById(R.id.editTextTextPersonName);
        lblFiltro = findViewById(R.id.lblTituloForma);lblFiltro.setText("Filtro : "+gl.filtroprod);

        gl.paBulto="";
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
                    clsBeTrans_packing_lotes sitem = (clsBeTrans_packing_lotes) lvObj;
                    gl.paBulto=sitem.lote;
                    gl.paEstado=sitem.estado;

                    if (sitem.lote.isEmpty()) {
                        msgbox("No se pueden adicionar productos sin licencia al empaque por tarima");
                    } else {
                        finish();
                    }
                }
            });

            txtFiltro.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        ;
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
                    if (item.presentacion.toUpperCase().indexOf(ft)>=0 |
                        item.lote.toUpperCase().indexOf(ft)>=0) flag=true;
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

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion


}