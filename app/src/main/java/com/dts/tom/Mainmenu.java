package com.dts.tom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dts.base.XMLObject;
import com.dts.base.clsClasses;
import com.dts.tom.Transacciones.CambioUbicacion.frm_tareas_cambio_ubicacion;

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

                case 1:

                    gl.tipoIngreso = "HCOC00";
                    gl.tipoTarea = idmenu;

                    startActivity(new Intent(this,frm_lista_tareas_principal.class));

                    break;

                case 2://Cambio de Ubicación

                    gl.modo_cambio = 1;
                    startActivity(new Intent(this, frm_tareas_cambio_ubicacion.class));

                    break;

                case 3://Cambio de Estado

                    gl.modo_cambio = 2;
                    startActivity(new Intent(this, frm_tareas_cambio_ubicacion.class));

                    break;

                case 4:

                case 5:

                case 6:

                case 7:

                case 8:

                case 9:

            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    //endregion

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion

}
