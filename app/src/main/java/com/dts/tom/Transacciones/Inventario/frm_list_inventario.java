package com.dts.tom.Transacciones.Inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Inventario.clsBeTrans_inv_enc;
import com.dts.classes.Transacciones.Inventario.clsBeTrans_inv_encList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_tareas_inventario;

import java.util.ArrayList;

public class frm_list_inventario extends PBase {

    private clsBeTrans_inv_encList pListTareas = new clsBeTrans_inv_encList();
    private clsBeTrans_inv_enc Tarea;

    private int idtarea=0;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private ListView listView;
    private EditText txtIdTareaInv;
    private Button btnRegs;

    private ArrayList<clsBeTrans_inv_enc> BeListInv = new ArrayList<clsBeTrans_inv_enc>();
    private list_adapt_tareas_inventario adapter;
    public static clsBeTrans_inv_enc selitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_inventario);

        super.InitBase();

        ws = new WebServiceHandler(frm_list_inventario.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView) findViewById(R.id.listInventario);
        txtIdTareaInv = (EditText) findViewById(R.id.txtIdTareaInv);
        btnRegs = (Button)findViewById(R.id.btnRegs);

        ProgressDialog("Cargando datos de inventario...");

        setHandlers();
        Load();

    }

    private void setHandlers(){

        try{


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_inv_enc sitem = (clsBeTrans_inv_enc) lvObj;
                        selitem = new clsBeTrans_inv_enc();
                        selitem = BeListInv.get(position);


                        selid = sitem.Idinventarioenc;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                       // procesar_registro();

                    }

                }

            });

        }catch (Exception e){
            mu.msgbox("setHandlers:"+e.getMessage());
        }
    }

    private void Load(){

        try{

            if (txtIdTareaInv.getText().toString().isEmpty()){
                idtarea = 0;
            }else{
                idtarea = Integer.parseInt(txtIdTareaInv.getText().toString());
            }

            execws(1);

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Llena_Lista_Tareas(){
        clsBeTrans_inv_enc vItem;

        try{

            vItem = new clsBeTrans_inv_enc();
            BeListInv.add(vItem);

            for (clsBeTrans_inv_enc BeInv: pListTareas.items){

                vItem = new clsBeTrans_inv_enc();

                vItem= BeInv;
                vItem.Hora_ini = du.convierteHoraMostar(vItem.Hora_ini);
                vItem.Transcurrido = String.valueOf(du.DateDiff(BeInv.Hora_ini));

                BeListInv.add(vItem);

            }

            int count = BeListInv.size()-1;
            btnRegs.setText("Regs:"+count);


        }catch (Exception e){
            mu.msgbox("Llena_Lista_Tareas:"+e.getMessage());
        }
    }

    public void BotonActualizar(View view){
        execws(1);
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
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
                        callMethod("Get_All_Inventario_By_IdBodega_And_IdOperador","pIdBodega",gl.IdBodega,
                                "pIdOperador",gl.OperadorBodega.IdOperador,"pIdTarea",idtarea);
                        break;
                }

                progress.cancel();

            } catch (Exception e) {
                progress.cancel();
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
                    processListTareas();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }


    private void processListTareas(){

        try {

            pListTareas = xobj.getresult(clsBeTrans_inv_encList.class,"Get_All_Inventario_By_IdBodega_And_IdOperador");

            if (pListTareas!=null){
                if (pListTareas.items!=null){
                    Llena_Lista_Tareas();
                }else{
                    mu.msgbox("No hay tareas de inventario disponibles");
                }
            }else{
                mu.msgbox("No hay tareas de inventario disponibles");
            }

        }catch (Exception e){
            mu.msgbox("processListTareas:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}