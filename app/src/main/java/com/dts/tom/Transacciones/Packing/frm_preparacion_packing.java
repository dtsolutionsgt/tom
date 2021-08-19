package com.dts.tom.Transacciones.Packing;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_encList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.ladapt.list_adapt_lista_packing;
import com.dts.tom.PBase;
import com.dts.tom.R;

import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_detalle_tareas_picking.plistPickingUbi;

public class frm_preparacion_packing extends PBase {

    private ListView listView;
    private EditText txtLP,txtLinea;
    private TextView lblProc,lblPend;
    private ProgressBar pbar;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ArrayList<clsBeTrans_packing_enc> items = new ArrayList<>();
    private ArrayList<clsBeTrans_packing_enc> item_list = new ArrayList<>();
    private clsBeTrans_picking_ubicList pick = new clsBeTrans_picking_ubicList();
    private clsBeTrans_packing_encList itemList ;

    private list_adapt_lista_packing adapter;
    private clsBeTrans_packing_enc selitem;

    private ObjectAnimator anim;
    private ProgressDialog progress;

    private int idPickingEnc,selidx,pendientes;
    private String sellp;
    private boolean idle=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_preparacion_packing);
        super.InitBase();

        listView = (ListView) findViewById(R.id.listTareas);
        txtLP = findViewById(R.id.editText8);
        txtLinea = findViewById(R.id.editTextNumber);txtLinea.setText("1");
        lblProc = findViewById(R.id.btnRegsList);
        lblPend = findViewById(R.id.btnRegsList2);
        pbar = (ProgressBar) findViewById(R.id.pgrtareas2);

        idPickingEnc=gl.gIdPickingEnc;

        anim = ObjectAnimator.ofInt(pbar, "progress", 0, 100);
        ProgressDialog("Cargando forma");

        ws = new WebServiceHandler(frm_preparacion_packing.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandlers();

        Load();
        focusLP();
    }

    //region Events

    public void doAdd(View view){
        int nl=0;
        try {
            nl=Integer.parseInt(txtLinea.getText().toString());
            if (nl>0) nl=nl+1;else nl=1;
       } catch (Exception e) {
            msgbox("Número de linea incorrecto");
            nl=1;
       }

       txtLinea.setText(""+nl);
    }

    public void doLP(View view){
        GetFila();
    }

    public void doLoad(View view){
        Load();
    }

    public void guardarTarea(View view) {
        if (pendientes==0) {
            msgAskSave("Finalizar la tarea");
        } else {
            msgbox("La tarea no se puede finalizar, quedan articulos pendientes");
        }
    }

    public void SalirTareas(View view){
        msgAskExit("Está seguro de salir");
    }

    private void setHandlers() {

        try {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selid = 0;
                    if (position > 0){
                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_packing_enc sitem = (clsBeTrans_packing_enc) lvObj;
                        selitem = sitem;
                        selid = sitem.Idpackingenc;
                        selidx = position;
                        adapter.setSelectedIndex(position);

                        //procesar_registro();
                    }
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selid = 0;
                    if (position > 0){
                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_packing_enc sitem = (clsBeTrans_packing_enc) lvObj;
                        selitem = sitem;
                        sellp = selitem.Lic_plate;
                        adapter.setSelectedIndex(position);

                        msgAskBorrar("Eliminar registro");
                    }
                    return true;
                }
            });

            txtLP.setOnKeyListener(new View.OnKeyListener() {
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

    private void listItems() {
        clsBeTrans_packing_enc item;

        try {
            item_list.clear();

            item=new clsBeTrans_packing_enc();
            item_list.add(item);

            for (int i = 0; i <items.size(); i++) {
                item_list.add(items.get(i));
            }

            Collections.sort(item_list,new OrdenarPorLinea());

            adapter=new list_adapt_lista_packing(this,item_list);
            listView.setAdapter(adapter);

            if (item_list.size()>1) {
                for (int i = 1; i <item_list.size(); i++) {
                    if (item_list.get(i).Lic_plate.equalsIgnoreCase(sellp)) {
                        adapter.setSelectedIndex(i);
                        listView.smoothScrollToPosition(i);
                    }
                }
            }

        } catch (Exception e) {
            mu.msgbox("listItems : "+e.getMessage());
        }

        progress.cancel();

        lblProc.setText("Procesado : "+(items.size()));
        pendientes=pick.items.size()-items.size();
        lblPend.setText("Pendiente : "+pendientes);

        txtLP.setText("");txtLP.requestFocus();
    }

    private void processListUbic(){
        try {
            progress.setMessage("Obteniendo lista de tareas");

            pick =xobj.getresult(clsBeTrans_picking_ubicList.class,"Get_All_PickingUbic_By_PickingEnc");

            listItems();
            idle=true;
        } catch (Exception e) {
            progress.cancel();
            String ss=e.getMessage();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Load(){

        if (!idle) return;

        try {
            idle=false;
            progress.setMessage("Cargando tarea ...");
            progress.show();

            execws(1);
        } catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void addItem() {
        clsBeTrans_packing_enc item;
        clsBeTrans_picking_ubic p;
        int lin;

        try {
            lin=Integer.parseInt(txtLinea.getText().toString());
            if (lin<1 | lin>99) {
                msgbox("Número de linea incorrecto");return;
            }
        } catch (Exception e) {
            lin=1;txtLinea.setText("1");
        }

        if (selidx<0) return;

        try {

            item=new clsBeTrans_packing_enc();
            p=pick.items.get(selidx);

            item.Idpackingenc=items.size()+1;
            item.Idbodega=gl.IdBodega;
            item.Idpickingenc=p.IdPickingEnc;
            item.Iddespachoenc=0;
            item.Idproductobodega=p.IdProductoBodega;
            item.Idproductoestado=0;
            item.Idpresentacion=p.IdPresentacion;
            item.Idunidadmedida=p.IdUnidadMedida;
            item.Lote=p.Lote;
            item.Fecha_vence=p.Fecha_Vence;
            item.Lic_plate=p.Lic_plate;sellp=p.Lic_plate;
            item.No_linea=lin;
            item.Cantidad_bultos_packing=p.Cantidad_Recibida;
            item.Cantidad_camas_packing=1;
            item.Idoperadorbodega=gl.OperadorBodega.IdOperadorBodega;
            item.Idempresaservicio=gl.IdEmpresa;
            item.Referencia="";
            item.Fecha_packing=du.univfecha(du.getActDateTime());

            item.nom_prod=p.NombreProducto;
            item.CodigoProducto=p.CodigoProducto;
            item.ProductoPresentacion=p.ProductoPresentacion;
            item.ProductoUnidadMedida=p.ProductoUnidadMedida;

            items.add(item);

            listItems();
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void delItem() {
        for (int i = 0; i <items.size(); i++) {
            if (items.get(i).Lic_plate.equalsIgnoreCase(sellp)) {
                items.remove(i);
                listItems();return;
            }
        }
    }

    private void GetFila() {
        String ss;
        int pos=-1;

        try {
            ss=txtLP.getText().toString();
            if (ss.isEmpty()) return;

            for (int i = 0; i <pick.items.size(); i++) {
               if (pick.items.get(i).Lic_plate.contains(ss)) {
                   pos=i;break;
               }
            }

            if (pos==-1) {
                msgbox("La licencia no existe");
               txtLP.selectAll();focusLP();return;
            }

            selidx=-1;
            for (int i = 0; i <items.size(); i++) {
                if (items.get(i).Lic_plate.equalsIgnoreCase(ss)) {
                    selidx=i;break;
                }
            }

            if (selidx==-1) {
                selidx=pos;
                addItem();
            } else {
                if (selidx>-1) {
                    sellp=ss;toast("Registro agregado anteriormente");
                    listItems();
                }
            }

            focusLP();
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void guardar() {

        if (items.size()==0) {
             msgbox("La lista de empaque está vacía, no se puede finalizar");return;
        }

        try {
            itemList = new clsBeTrans_packing_encList();
            itemList.items = stream(items).toList();

            progress.setMessage("Finalizando tarea ...");
            progress.show();
            idle=false;
            execws(2);

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void cantidadRegGuardados(){
        int regs;

        try {

            idle=true; progress.cancel();

            if (!ws.errorflag) {
                regs = xobj.getresult(Integer.class,"Inserta_Packing");
                if (regs>0) {
                    toast("Tarea finalizada : "+regs);finish();
                } else {
                    mu.msgbox("No se logro finalizar la tarea, por favor repite el proceso.");
                }
            } else {
                mu.msgbox("processUbicacionMerma : "+ws.error);
            }
        } catch (Exception e){
            mu.msgbox("processUbicacionMerma : "+e.getMessage());
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
                        callMethod("Get_All_PickingUbic_By_PickingEnc","pIdPickingEnc",idPickingEnc);
                        break;
                    case 2:
                        callMethod("Inserta_Packing","pTrans_packing_enc",itemList.items);
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
                    processListUbic();break;
                case 2:
                    cantidadRegGuardados();break;
            }

        } catch (Exception e) {
            try {
                progress.cancel();
            } catch (Exception ee) {}
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    //endregion

    //region Aux

    public class OrdenarPorLinea implements Comparator<clsBeTrans_packing_enc> {
        public int compare(clsBeTrans_packing_enc left,clsBeTrans_packing_enc rigth){
            return left.No_linea-rigth.No_linea;
        }
    }

    public class OrdenarPorLP implements Comparator<clsBeTrans_packing_enc> {
        public int compare(clsBeTrans_packing_enc left,clsBeTrans_packing_enc rigth){
             return left.Lic_plate.compareTo(rigth.Lic_plate);
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

    private void focusLP() {
        Handler mtimer = new Handler();
        Runnable mrunner=new Runnable() {
            @Override
            public void run() {
                txtLP.requestFocus();
            }
        };
        mtimer.postDelayed(mrunner,200);
    }

    //endregion

    //region Dialogs

    private void msgAskExit(String msg) {
        if (!idle) return;

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

    private void msgAskSave(String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    guardar();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });

            dialog.show();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAskBorrar(String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                   delItem();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });

            dialog.show();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    //endregion

    //region Activity Events

    @Override
    protected void onResume() {
        try {
            super.onResume();
            txtLP.requestFocus();
        } catch (Exception e) {
            mu.msgbox("OnResume "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        try{
            msgAskExit("Está seguro de salir");
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    //endregion

}