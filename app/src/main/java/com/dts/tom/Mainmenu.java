package com.dts.tom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dts.base.XMLObject;
import com.dts.base.clsClasses;
import com.dts.tom.Transacciones.CambioUbicacion.frm_cambio_ubicacion_ciega;
import com.dts.tom.Transacciones.CambioUbicacion.frm_tareas_cambio_ubicacion;
import com.dts.tom.Transacciones.Inventario.frm_list_inventario;

import java.util.ArrayList;

public class Mainmenu extends PBase {

    private GridView gridView;
    private TextView lblBodega,lblUsuario;

    private MainActivity.WebServiceHandler ws;
    private XMLObject xobj;

    private ArrayList<clsClasses.clsMenu> items= new ArrayList<clsClasses.clsMenu>();

    private list_view_menu adaptergrid;

    private int selId,selIdx,menuid,iicon;
    private String sdoc;
    private boolean horizpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        super.InitBase();

        gridView = (GridView) findViewById(R.id.gridView1);
        lblBodega = (TextView) findViewById(R.id.lblBodegaName);
        lblUsuario = (TextView) findViewById(R.id.lblUsuarioName);

        int ori=this.getResources().getConfiguration().orientation; // 1 - portrait , 2 - landscape
        horizpos=ori==2;

        if (horizpos) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }


        Load();

        setHandlers();

    }

    //region Events


    public void setHandlers(){
        try{
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    clsClasses.clsMenu vItem = (clsClasses.clsMenu) adaptergrid.getItem(position);
                    menuid=vItem.ID;

                    adaptergrid.setSelectedIndex(position);

                    Procesa_Opcion_Menu(menuid);

                }
            });
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }


    }

    //endregion

    //region Main


    //endregion

    //region Aux

    private void Load(){

        try{

            listItems();

            lblBodega.setText("Bodega: "+ gl.CodigoBodega);
            lblUsuario.setText("Usuario: "+ gl.gOperadorBodega.get(0).Operador.Nombres + " "+ gl.gOperadorBodega.get(0).Operador.Apellidos );

        }catch (Exception e){
            mu.msgbox(e.getMessage());
        }

    }

    public void listItems() {
        try{
            clsClasses.clsMenu item;

            items.clear();selIdx=-1;

            try {

                item = clsCls.new clsMenu();
                item.ID=1;item.Name="Recepción";item.Icon=1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=2;item.Name="Cambio de ubicación";item.Icon=2;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=3;item.Name="Cambio de estado";item.Icon=3;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=4;item.Name="Packing";item.Icon=4;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=5;item.Name="Picking";item.Icon=5;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=6;item.Name="Verificación";item.Icon=6;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=7;item.Name="Inventario";item.Icon=7;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=8;item.Name="Existencias";item.Icon=8;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=9;item.Name="Cambio usuario";item.Icon=9;
                items.add(item);

            } catch (Exception e) {
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            }

            adaptergrid=new list_view_menu(this, items);
            gridView.setAdapter(adaptergrid);
            adaptergrid.setSelectedIndex(selIdx);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }


    }

    private void Procesa_Opcion_Menu(int idmenu){

        try{

            switch (idmenu) {

                case 1://Recepción

                    gl.tipoIngreso = "HCOC00";
                    gl.tipoTarea = idmenu;

                    startActivity(new Intent(this,frm_lista_tareas_principal.class));

                    break;

                case 2://Cambio de Ubicación

                    gl.IdTareaUbicEnc =0;
                    gl.modo_cambio = 1;
                    msgAskUbicNoDirigida("Ubicación no dirigida");

                    break;

                case 3://Cambio de Estado

                    gl.modo_cambio = 2;
                    msgAskUbicNoDirigida("Cambio de estado no dirigido");

                    break;

                case 4://Packing

                case 5://Picking

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this,frm_lista_tareas_principal.class));
                    
                    break;

                case 6://Verificacion

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_lista_tareas_principal.class));

                    break;

                case 7://Inventario

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_list_inventario.class));

                    break;

                case 8://Existencias

                case 9://Cambio de usuario
                    Mainmenu.super.finish();
            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskUbicNoDirigida(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setCancelable(false);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Mainmenu.this, frm_cambio_ubicacion_ciega.class);
                    startActivity(intent);

                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Mainmenu.this, frm_tareas_cambio_ubicacion.class));
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    //endregion

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion

}
