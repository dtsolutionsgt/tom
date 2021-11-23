package com.dts.tom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.base.clsClasses;
import com.dts.ladapt.list_view_menu2;
import com.dts.servicios.startCantTareas;
import com.dts.tom.Transacciones.CambioUbicacion.frm_cambio_ubicacion_ciega;
import com.dts.tom.Transacciones.CambioUbicacion.frm_tareas_cambio_ubicacion;
import com.dts.tom.Transacciones.ConsultaStock.frm_consulta_stock;
import com.dts.tom.Transacciones.Inventario.frm_list_inventario;
import com.dts.tom.Transacciones.Packing.frm_Packing;
import com.dts.tom.Transacciones.Packing.frm_lista_packing;
import com.dts.tom.Transacciones.Recepcion.frm_lista_tareas_recepcion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Mainmenu extends PBase {

    private GridView gridView;
    private TextView lblBodega,lblUsuario;

    private Mainmenu.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private ArrayList<clsClasses.clsMenu> items= new ArrayList<clsClasses.clsMenu>();

    private list_view_menu2 adaptergrid;

    private boolean started = false;
    private Handler handler = new Handler();

    private int selId,selIdx,menuid,iicon;
    private String sdoc;
    private boolean horizpos;

    private boolean listo=true;

    private int tiempo_actualizacion=25 * 1000;
    private int cantRecep = 0,cantPicking = 0,cantVerif = 0,cantCambioEst = 0, cantCambioUbic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        super.InitBase();

        ws = new Mainmenu.WebServiceHandler(Mainmenu.this, gl.wsurl);
        xobj = new XMLObject(ws);

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

        setHandlers();

        String params=gl.wsurl+"#"+gl.IdBodega+"#"+gl.OperadorBodega.IdOperadorBodega;
        startCantTareas.startService(this,params);

        ProgressDialog("Cargando forma");
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
        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    //endregion

    //region Main

    public void listItems() {
        try{
            clsClasses.clsMenu item;

            items.clear();selIdx=-1;

            try {

                item = clsCls.new clsMenu();
                item.ID=1;item.Icon=1;item.Name="Recepción\n";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=2;item.Icon=2;item.Name="Cambio de ubicación";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=3;item.Icon=3;item.Name="Cambio de estado";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=4;item.Icon=4;item.Name="Implosión";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=5;item.Icon=5;item.Name="Picking\n";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=6;item.Icon=6;item.Name="Verificación\n";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=11;item.Icon=11;item.Name="Lista Empaque";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=7;item.Icon=7;item.Name="Inventario";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=8;item.Icon=8;item.Name="Existencias";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=10;item.Icon=10;item.Name="Utilerias";item.cant=-1;
                items.add(item);

                item = clsCls.new clsMenu();
                item.ID=9;item.Icon=9;item.Name="Cambio usuario";item.cant=-1;
                items.add(item);

                progress.cancel();

            } catch (Exception e) {
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            }

            adaptergrid=new list_view_menu2(this, items);
            gridView.setAdapter(adaptergrid);
            adaptergrid.setSelectedIndex(selIdx);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void updateList() {
        try {
            items.get(0).cant=cantRecep;
            items.get(1).cant=cantCambioUbic;
            items.get(2).cant=cantCambioEst;
            items.get(4).cant=cantPicking;
            items.get(5).cant=cantVerif;

            adaptergrid.notifyDataSetChanged();
        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void Load(){
        try {
            lblBodega.setText("Bodega: "+ gl.CodigoBodega);

            try {
                lblUsuario.setText("Usuario: "+ gl.gOperadorBodega.get(0).Operador.Nombres + " "+ gl.gOperadorBodega.get(0).Operador.Apellidos );
            } catch (Exception e) {}

            listItems();

            execws(1);
        } catch (Exception e){
            mu.msgbox(e.getMessage());
        }
    }

    private void finishLoad() {
        try {
            updateList();
            starttimer();
        } catch (Exception e){
            mu.msgbox(e.getMessage());
        }
    }

    //endregion

    //region Web Service

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Count_Recepciones_For_HH_By_IdBodega",
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                    case 2:
                        callMethod("Get_Count_Picking_For_HH_By_IdBodega",
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                    case 3:
                        callMethod("Get_Count_Verificaciones_For_HH_By_IdBodega",
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                    case 4:
                        callMethod("Get_Count_Cambio_Est_Ubic_For_HH",
                                "pCambioEstado",0,
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                    case 5:
                        callMethod("Get_Count_Cambio_Est_Ubic_For_HH",
                                "pCambioEstado",1,
                                "pIdBodega",gl.IdBodega,
                                "pIdOperadorBodega",gl.OperadorBodega.IdOperadorBodega);
                        break;
                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                error=e.getMessage();errorflag =true;msgbox(error);
            }
        }
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {

                case 1:
                    process_get_count_recepciones();break;
                case 2:
                    process_get_count_picking();break;
                case 3:
                    process_get_count_verificaciones();break;
                case 4:
                    process_get_count_cambio_ubic();break;
                case 5:
                    process_get_count_cambio_estado();break;
            }

        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            //msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void process_get_count_recepciones(){

        try {

            progress.setMessage("Obteniendo cantidad  de recepciones");

            cantRecep = (Integer) xobj.getSingle("Get_Count_Recepciones_For_HH_By_IdBodegaResult",Integer.class);

            execws(2);

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void process_get_count_picking(){

        try {

            progress.setMessage("Obteniendo cantidad  de recepciones");

            cantPicking = (Integer) xobj.getSingle("Get_Count_Picking_For_HH_By_IdBodegaResult",Integer.class);

            execws(3);

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void process_get_count_verificaciones(){

        try {

            progress.setMessage("Obteniendo cantidad  de recepciones");

            cantVerif = (Integer) xobj.getSingle("Get_Count_Verificaciones_For_HH_By_IdBodegaResult",Integer.class);

            execws(4);

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void process_get_count_cambio_ubic(){

        try {

            progress.setMessage("Obteniendo cantidad  de recepciones");

            cantCambioUbic = (Integer) xobj.getSingle("Get_Count_Cambio_Est_Ubic_For_HHResult",Integer.class);

            execws(5);

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void process_get_count_cambio_estado(){

        try {

            progress.setMessage("Obteniendo cantidad  de recepciones");

            cantCambioEst = (Integer) xobj.getSingle("Get_Count_Cambio_Est_Ubic_For_HHResult",Integer.class);

            finishLoad();

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    //endregion

    //region Timer

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            cargaDatosServicio();
            updateList();
            if(started) starttimer();
        }
    };

    public void stoptimer() {
        started = false;
        handler.removeCallbacks(timer);
    }

    public void starttimer() {
        started = true;
        handler.postDelayed(timer, tiempo_actualizacion);
    }

    //endregion

    //region Aux

    private void Procesa_Opcion_Menu(int idmenu){

        try{

            //#CKFK 20200813
            if (!listo) return;

            listo=false;
            Handler mtimer = new Handler();
            Runnable mrunner=new Runnable() {
                @Override
                public void run() {
                    listo=true;
                }
            };
            mtimer.postDelayed(mrunner,1000);

            switch (idmenu) {

                case 1://Recepción

                    gl.tipoIngreso = "HCOC00";
                    gl.tipoTarea = idmenu;

                    startActivity(new Intent(this, frm_lista_tareas_recepcion.class));

                    break;

                case 2://Cambio de Ubicación

                    gl.IdTareaUbicEnc =0;
                    gl.modo_cambio = 1;
                    msgAskUbicNoDirigida("Ubicación dirigida");

                    break;

                case 3://Cambio de Estado

                    gl.modo_cambio = 2;
                    msgAskUbicNoDirigida("Cambio de estado dirigido");

                    break;

                case 4://Packing
                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_Packing.class));

                    break;
                case 5://Picking

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_lista_tareas_recepcion.class));

                    break;

                case 6://Verificacion

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_lista_tareas_recepcion.class));

                    break;

                case 7://Inventario

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_list_inventario.class));

                    break;

                case 8://Existencias

                    gl.tipoTarea = idmenu;
                    startActivity(new Intent(this, frm_consulta_stock.class));

                    break;

                case 9://Cambio de usuario
                    Mainmenu.super.finish(); break;

                case 10:// Utilerias
                    menuUtilerias(); break;

                case 11:// Packing
                    startActivity(new Intent(this, frm_lista_packing.class)); break;
            }

        } catch (Exception e){
            mu.msgbox(e.getMessage());
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    public void ProgressDialog(String mensaje){
        progress=new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private void menuUtilerias() {
        final AlertDialog Dialog;
        final String[] selitems = {"Actualizar versión"};

        AlertDialog.Builder menudlg = new AlertDialog.Builder(this);
        menudlg.setTitle("Utilerias ");

        menudlg.setItems(selitems , new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        msgAskUpdate("Actualizar versión");break;

                }

                dialog.cancel();
            }
        });

        menudlg.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Dialog = menudlg.create();
        Dialog.show();
    }

    private void actualizaVersion() {
        try {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.dts.mposupd");
            intent.putExtra("filename","tom.apk");
            this.startActivity(intent);
        } catch (Exception e) {
            msgbox("No está instalada aplicación para actualización de versiónes, por favor informe soporte.");
        }
    }

    private void cargaDatosServicio() {
        String vs,ss;
        String[] sp;

        cantRecep=-1;cantPicking=-1;cantVerif=-1;cantCambioEst=-1;cantCambioUbic=-1;
        //toast("timer");

        try {

            vs= readServiceFile();
            sp=vs.split(",");

            try {
                ss= sp[0];ss=ss.substring(1);
                cantRecep=Integer.parseInt(ss);
            } catch (Exception e) {
                cantRecep=-1;
            }

            try {
                ss= sp[1];ss=ss.substring(1);
                cantPicking=Integer.parseInt(ss);
            } catch (Exception e) {
                cantPicking=-1;
            }

            try {
                ss= sp[2];ss=ss.substring(1);
                cantVerif=Integer.parseInt(ss);
            } catch (Exception e) {
                cantVerif=-1;
            }

            try {
                ss= sp[3];ss=ss.substring(1);
                cantCambioUbic=Integer.parseInt(ss);
            } catch (Exception e) {
                cantCambioUbic=-1;
            }

            try {
                ss= sp[4];ss=ss.substring(1);
                cantCambioEst=Integer.parseInt(ss);
            } catch (Exception e) {
                cantCambioEst=-1;
            }

        } catch (Exception e) {
            toast(e.getMessage());
        }
    }

    public static String readServiceFile() throws Exception {
        String fname = Environment.getExternalStorageDirectory().getPath()+"/tom_tareas.txt";
        String aBuffer = "";

        File myFile = new File(fname);
        FileInputStream fIn = new FileInputStream(myFile);
        BufferedReader txtReader = new BufferedReader(new InputStreamReader(fIn));
        String aDataRow = "";
        while ((aDataRow = txtReader.readLine()) != null) aBuffer += aDataRow;

        txtReader.close();

        return aBuffer;
    }

    //endregion

    //region Dialogs

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

    private void msgAskUpdate(String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Tom WMS");
        dialog.setMessage("¿" + msg + "?");

        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                actualizaVersion();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });

        dialog.show();

    }

    //endregion

    //region Activity Events

    protected void onResume() {
        try{
            Load();
            super.onResume();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stoptimer();
    }

    @Override
    public void onBackPressed() {
        stoptimer();
        super.onBackPressed();
    }


    //endregion

}
