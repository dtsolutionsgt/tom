package com.dts.tom.Transacciones.Packing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.ladapt.list_adapt_packing_lotes;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.Collections;
import java.util.Comparator;

public class frm_lista_packing_lotes extends PBase {

    private ListView listView;
    private EditText txtCant,txtBulto,txtCamas,txtLinea;
    private TextView lblNombre;

    private list_adapt_packing_lotes adapter;

    private String codigo;
    private int cantm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_packing_lotes);

        super.InitBase();

        listView = findViewById(R.id.listView1);
        txtCant = findViewById(R.id.editTextNumber2);
        txtBulto = findViewById(R.id.editTextTextPersonName);
        txtCamas = findViewById(R.id.editTextNumber3);txtCamas.setText("1");
        txtLinea = findViewById(R.id.editTextNumber4);txtLinea.setText(""+gl.paLinea);
        lblNombre = findViewById(R.id.lblTituloForma);lblNombre.setText(gl.paNombre);

        codigo=gl.paCodigo;
        gl.paPickUbicId=-1;gl.paCant=0;gl.paCamas=0;gl.paBulto="";

        setHandlers();

        listItems();
    }

    //region Events

    public void SalirTareas(View view) {
        //msgAskExit("Está seguro de salir");
        finish();
    }

    public void doSave(View view) {
        save();
    }

    private void setHandlers() {

        try {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selid = 0;
                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_packing_lotes sitem = (clsBeTrans_packing_lotes) lvObj;
                    gl.paPickUbicId=sitem.id;
                    cantm=sitem.disp;
                    selidx = position;

                    adapter.setSelectedIndex(position);
                    focusCant();
                }
            });

            txtCant.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        try {
                            int val=Integer.parseInt(txtCant.getText().toString());
                            if (val<1) throw new Exception();
                            txtBulto.requestFocus();
                        } catch (Exception e) {
                            toast("Cantidad incorrecta");
                        }

                    }
                    return false;
                }
            });

            txtBulto.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        txtCamas.requestFocus();
                    }
                    return false;
                }
            });

            txtCamas.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        try {
                            int val=Integer.parseInt(txtCant.getText().toString());
                            if (val<1) throw new Exception();
                            save();
                        } catch (Exception e) {
                            toast("Cantidad camas incorrecta");
                        }

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

        try {
            Collections.sort(gl.packlotes,new OrdenarPorLote());

            adapter=new list_adapt_packing_lotes(this,gl.packlotes);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            mu.msgbox("listItems : "+e.getMessage());
        }

        selid=-1;
        txtCant.setText("");txtBulto.setText("");txtCamas.setText("1");

        focusCant();
    }

    private void save() {
        if (validaValores()) finish();
    }

    //endregion

    //region Aux

    private boolean validaValores() {
        int val,lin;

        if (gl.paPickUbicId==-1) {
            toast("Falta seleccionar un lote");return false;
        }

        try {
            lin=Integer.parseInt(txtLinea.getText().toString());
            if (lin<1 | lin>99) throw new Exception();
            gl.paLinea=lin;
        } catch (Exception e) {
            msgbox("Número de linea incorrecto");
            txtLinea.selectAll();txtLinea.requestFocus();
            return false;
        }

        try {
            val=Integer.parseInt(txtCant.getText().toString());
            if (val<1) throw new Exception();

            if (val>cantm) {
                toast("Cantidad mayor que disponible : "+cantm);
                txtCant.requestFocus();txtCant.selectAll();
                return false;
            }

            gl.paCant=val;

        } catch (Exception e) {
            txtCant.requestFocus();txtCant.selectAll();
            toast("Cantidad incorrecta");return false;
        }

        try {
            val=Integer.parseInt(txtCamas.getText().toString());
            if (val<1) throw new Exception();
            gl.paCamas=val;
        } catch (Exception e) {
            txtCamas.requestFocus();txtCamas.selectAll();
            toast("Cantidad camas incorrecta");return false;
        }

        gl.paBulto=txtBulto.getText().toString();

        return true;
    }

    public class OrdenarPorLote implements Comparator<clsBeTrans_packing_lotes> {
        public int compare(clsBeTrans_packing_lotes left,clsBeTrans_packing_lotes rigth){
            return left.lote.compareTo(rigth.lote);
        }
    }

    private void focusCant() {
        Handler mtimer = new Handler();
        Runnable mrunner=new Runnable() {
            @Override
            public void run() {
                txtCant.requestFocus();
            }
        };
        mtimer.postDelayed(mrunner,200);
    }

    //endregion

    //region Dialogs

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    //endregion

    //region Activity Events


    //endregion

}