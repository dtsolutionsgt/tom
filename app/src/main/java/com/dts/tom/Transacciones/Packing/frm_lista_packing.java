package com.dts.tom.Transacciones.Packing;

import static br.com.zbra.androidlinq.Linq.stream;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_encList;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;
import com.dts.ladapt.list_adapt_tareashh_picking;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class frm_lista_packing extends PBase {

    private EditText txtTarea;
    private TextView lblTitulo;
    private Button lblRegs,btnNueva;
    private ListView listView;
    private ProgressBar pbar;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeTrans_picking_encList pListBeTareasPickingHH = new clsBeTrans_picking_encList();
    private ArrayList<clsBeTareasIngresoHH> BeListTareas = new ArrayList<clsBeTareasIngresoHH>();
    private ArrayList<clsBeTrans_picking_enc> BeListTareasPicking = new ArrayList<clsBeTrans_picking_enc>();
    private list_adapt_tareashh_picking adapterPicking;

    private ObjectAnimator anim;
    private ProgressDialog progress;

    private boolean idle=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_packing);
        super.InitBase();

        lblTitulo = (TextView) findViewById(R.id.lblTituloForma);
        txtTarea = (EditText) findViewById(R.id.editText8);
        listView = (ListView) findViewById(R.id.listTareas);
        lblRegs = (Button) findViewById(R.id.btnRegsList);
        btnNueva = (Button)findViewById(R.id.btnNuevaTarea);
        pbar = (ProgressBar) findViewById(R.id.pgrtareas);

        anim = ObjectAnimator.ofInt(pbar, "progress", 0, 100);
        ProgressDialog("Cargando forma");

        try {

            setHandlers();

            ws = new WebServiceHandler(frm_lista_packing.this, gl.wsurl);
            xobj = new XMLObject(ws);

            Load();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //region Events

    public void SalirTareas(View view){
        msgAskExit("Está seguro de salir");
    }

    public void doLoad(View view){
        Load();
    }

    private void setHandlers() {

        try {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0){
                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_picking_enc sitem = (clsBeTrans_picking_enc) lvObj;

                        selid = sitem.IdPickingEnc;
                        selidx = position;
                        adapterPicking.setSelectedIndex(position);

                        procesar_registro();
                    }
               }
            });

            txtTarea.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                        GetFila();
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

    private void listItems(){

        clsBeTrans_picking_enc vItem;
        BeListTareasPicking.clear();
        int count;

        try{

            progress.setMessage("Listando tareas");

            if (pListBeTareasPickingHH!=null){

                if (pListBeTareasPickingHH.items!=null){

                    vItem = new clsBeTrans_picking_enc();

                    BeListTareasPicking.add(vItem);

                    for (clsBeTrans_picking_enc BePicking:pListBeTareasPickingHH.items ){

                        vItem = new clsBeTrans_picking_enc();

                        vItem.IdPickingEnc=BePicking.IdPickingEnc;
                        vItem.NombreBodega=BePicking.NombreBodega;
                        vItem.NombrePropietarioPicking=BePicking.NombrePropietarioPicking;
                        vItem.NombreUbicacionPicking=BePicking.NombreUbicacionPicking;
                        vItem.Estado=BePicking.Estado;
                        vItem.Fecha_picking=du.convierteFechaMostaryy(BePicking.Fecha_picking);
//                        vItem.Detalle_operador=BePicking.Detalle_operador;
//                        vItem.Hora_ini=du.convierteHoraMostarhm(BePicking.Hora_ini);
//                        vItem.Hora_fin=du.convierteHoraMostarhm(BePicking.Hora_fin);
                        vItem.Tipo_Preparacion=BePicking.Tipo_Preparacion;

                        BeListTareasPicking.add(vItem);

                    }

                    count = BeListTareasPicking.size()-1;
                    lblRegs.setText("Regs: "+ count);
                }
            }

            Collections.sort(BeListTareasPicking,new OrdenarItemsPicking());

            adapterPicking=new list_adapt_tareashh_picking(this,BeListTareasPicking);
            listView.setAdapter(adapterPicking);

            progress.cancel();

        } catch (Exception e) {
            mu.msgbox("listItems : "+e.getMessage());
        }
    }

    private void processListRecep(){
        try {
            progress.setMessage("Obteniendo lista de tareas");

            pListBeTareasPickingHH=xobj.getresult(clsBeTrans_picking_encList.class,"Get_All_Picking_Para_Empaque_By_IdBodega");

            listItems();
            idle=true;
        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Load(){

        if (!idle) return;

        try {

            idle=false;
            progress.setMessage("Cargando tareas ...");
            progress.show();

            execws(1);
         } catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    //endregion

    //region Web Service

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_All_Picking_Para_Empaque_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Set_Estado_Pendiente_Packing","pIdPickingEnc",gl.gIdPickingEnc);
                        break;
                }

                anim.cancel();

            } catch (Exception e) {
                anim.cancel();
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
                    processListRecep();break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    //endregion

    //region Aux

    private void procesar_registro() {

        String tipoprep;

        try {

            gl.gIdPickingEnc = selid; gl.gVerifCascade =false;

            for (int i = 0; i <BeListTareasPicking.size(); i++) {

                if (BeListTareasPicking.get(i).IdPickingEnc==gl.gIdPickingEnc) {

                    tipoprep=BeListTareasPicking.get(i).Tipo_Preparacion;

                    browse=1;

                    execws(2);

                    if (tipoprep.equalsIgnoreCase("Tarima")) {
                        startActivity(new Intent(this, frm_preparacion_packing.class));
                    } else if (tipoprep.equalsIgnoreCase("Granel")) {
                        startActivity(new Intent(this, frm_preparacion_packing_bulto.class));
                    }

                    break;

                }
            }

       } catch (Exception e){
            mu.msgbox(e.getMessage());
       }
    }

    private void GetFila() {

        int vIdTarea=0;

        try {

            if (txtTarea.getText().toString().isEmpty()) return;

            selid = Integer.parseInt(txtTarea.getText().toString());
            vIdTarea = stream(pListBeTareasPickingHH.items).where(c->c.IdPickingEnc == selid).select(c->c.IdPickingEnc).first();

            if (vIdTarea>0) {
                procesar_registro();
                return;
            }

        } catch (Exception e){

        }

        mu.msgbox("No existe la tarea "+selid);
        txtTarea.selectAll();txtTarea.requestFocus();
    }

    public class OrdenarItemsPicking implements Comparator<clsBeTrans_picking_enc> {

        public int compare(clsBeTrans_picking_enc left,clsBeTrans_picking_enc rigth){
            return left.IdPickingEnc-rigth.IdPickingEnc;
            //return left.Nombre.compareTo(rigth.Nombre);
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
                    ;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

     //endregion

    //region Activity Events

    protected void onResume() {

        try {

            super.onResume();

            txtTarea.requestFocus();

            if (browse==1){
                browse=0;
                Load();
            }

        } catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }
    }

    //endregion

}