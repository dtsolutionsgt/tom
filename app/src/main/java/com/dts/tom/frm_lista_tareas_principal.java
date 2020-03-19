package com.dts.tom;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHHList;
import com.dts.tom.Transacciones.Recepcion.frm_detalle_ingresos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class frm_lista_tareas_principal extends PBase {

    private EditText txtTarea;
    private TextView lblTitulo,lblRegs;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeTareasIngresoHHList pListBeTareasIngresoHH = new clsBeTareasIngresoHHList();

    private ArrayList<clsBeTareasIngresoHH> BeListTareas = new ArrayList<clsBeTareasIngresoHH>();
    private list_adapter_tareashh adapter;
    private ListView listView;
    private clsBeTareasIngresoHH selitem;
    private ProgressBar pbar;
    private ObjectAnimator anim;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_lista_tareas_principal);

        super.InitBase();

        lblTitulo = (TextView) findViewById(R.id.lblTituloForma);
        txtTarea = (EditText) findViewById(R.id.editText8);
        listView = (ListView) findViewById(R.id.listTareas);
        lblRegs = (TextView) findViewById(R.id.btnRegsList);

        pbar=(ProgressBar)  findViewById(R.id.pgrtareas);

        anim = ObjectAnimator.ofInt(pbar, "progress", 0, 100);

        ProgressDialog("Cargando forma");

        ws= new WebServiceHandler(frm_lista_tareas_principal.this, gl.wsurl);
        xobj= new XMLObject(ws);

        setHandlers();

        Load();

    }

    private void Load(){

        try{

            switch (gl.tipoTarea) {

                case 1:

                    switch (gl.tipoIngreso){

                        case "HCOC00":
                            gl.TipoOpcion =1;
                            execws(1);

                            break;

                        case "HSOC00":
                            gl.TipoOpcion =2;
                            execws(2);

                            break;

                    }

                    lblTitulo.setText("Lista de tareas de recepción");

                case 2:


                case 3:

            }

        }catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

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
                        callMethod("Get_All_Recepciones_For_HH_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Get_All_Rec_Ciegas_For_HH_By_IdBodega","pIdBodega",gl.IdBodega);
                        break;
                    case 3:
                        //callMethod("Get_All_Impresora_By_IdEmpresa_And_IdBodega_Dt",
                          //      "IdEmpresa",idemp,"IdBodega",idbodega);
                        break;
                    case 4:
                        //callMethod("Get_Operadores_By_IdBodega_For_HH","IdBodega",idbodega);
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
                case 2:
                    processListRecepCiega();break;
                case 3:
                    //processImpresoras();

                    //iduser=0; execws(4); // Llama lista de usuarios
                    break;
                case 4:
                    //processUsers();break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void processListRecep(){

        try {

            progress.setMessage("Obteniendo lista de tareas");

            pListBeTareasIngresoHH=xobj.getresult(clsBeTareasIngresoHHList.class,"Get_All_Recepciones_For_HH_By_IdBodega");

            listItems();

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }


    private void processListRecepCiega(){

        try {

            pListBeTareasIngresoHH=xobj.getresult(clsBeTareasIngresoHHList.class,"Get_All_Rec_Ciegas_For_HH_By_IdBodega");

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void listItems(){
        clsBeTareasIngresoHH vItem;
        BeListTareas.clear();
        int count;

        try{

            progress.setMessage("Listando tareas");

            if (pListBeTareasIngresoHH.items!=null){

                vItem = new clsBeTareasIngresoHH();

                BeListTareas.add(vItem);

                for (int i = pListBeTareasIngresoHH.items.size()-1; i>=0; i--) {

                    progress.setMessage("Listando tarea #: "+pListBeTareasIngresoHH.items.get(i).IdRecepcionEnc);

                    vItem = new clsBeTareasIngresoHH();

                    vItem.IdOrderCompraEnc=pListBeTareasIngresoHH.items.get(i).IdOrderCompraEnc;
                    vItem.IdRecepcionEnc=pListBeTareasIngresoHH.items.get(i).IdRecepcionEnc;
                    vItem.NoReferenciaOC=pListBeTareasIngresoHH.items.get(i).NoReferenciaOC;
                    vItem.NoDocumentoOc=pListBeTareasIngresoHH.items.get(i).NoDocumentoOc;
                    vItem.NombreProveedor=pListBeTareasIngresoHH.items.get(i).NombreProveedor;
                    vItem.NombreTipoIngresoOC=pListBeTareasIngresoHH.items.get(i).NombreTipoIngresoOC;
                    vItem.NombreTipoRecepcion=pListBeTareasIngresoHH.items.get(i).NombreTipoRecepcion;

                    BeListTareas.add(vItem);

                }
                count = BeListTareas.size()-1;
                lblRegs.setText("Regs: "+ count);

            }

            Collections.sort(BeListTareas,new OrdenarItems());

            adapter=new list_adapter_tareashh(this,BeListTareas);
            listView.setAdapter(adapter);

            progress.cancel();

        } catch (Exception e) {
            progress.cancel();
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox(e.getMessage());
        }
    }

    public class OrdenarItems implements Comparator<clsBeTareasIngresoHH> {

        public int compare(clsBeTareasIngresoHH left,clsBeTareasIngresoHH rigth){
            return left.IdOrderCompraEnc-rigth.IdOrderCompraEnc;
            //return left.Nombre.compareTo(rigth.Nombre);
        }

    }

    private void procesar_registro(){

        try{

            gl.gIdRecepcionEnc=0;

            switch (gl.tipoTarea) {

                case 1:
                    gl.gIdRecepcionEnc = selid;
                    startActivity(new Intent(this, frm_detalle_ingresos.class));
                break;
            }

        }catch (Exception e){
            mu.msgbox(e.getMessage());
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


    private void setHandlers() {

        try{


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position>0){

                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTareasIngresoHH sitem = (clsBeTareasIngresoHH) lvObj;
                        selitem = sitem;

                        selid = sitem.IdRecepcionEnc;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        procesar_registro();

                    }

                }

            });


            }catch (Exception e){

            }

    }

    @Override
    public void onBackPressed() {
        try{
            //msgAskExit("Salir sin guardar datos");
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }


}
