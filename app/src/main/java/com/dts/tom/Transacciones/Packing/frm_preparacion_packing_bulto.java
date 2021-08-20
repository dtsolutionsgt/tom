package com.dts.tom.Transacciones.Packing;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_encList;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc_bulto;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.ladapt.list_adapt_lista_packing;
import com.dts.ladapt.list_adapt_lista_packing_bulto;
import com.dts.tom.PBase;
import com.dts.tom.R;

import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_preparacion_packing_bulto extends PBase {

    private ListView listView;
    private EditText txtLP,txtLinea;
    private TextView lblProc,lblPend;
    private ProgressBar pbar;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ArrayList<clsBeTrans_packing_enc> items = new ArrayList<>();
    private ArrayList<clsBeTrans_packing_enc_bulto> item_list = new ArrayList<>();
    private clsBeTrans_picking_ubicList pick = new clsBeTrans_picking_ubicList();
    private clsBeTrans_packing_encList itemList ;

    private list_adapt_lista_packing_bulto adapter;
    private clsBeTrans_packing_enc_bulto selitem;
    private clsBeTrans_picking_ubic selpick;

    private ObjectAnimator anim;
    private ProgressDialog progress;

    private int idPickingEnc,selidx,pendientes,newid,itot,proc;
    private boolean idle=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_preparacion_packing_bulto);
        super.InitBase();

        listView = findViewById(R.id.listTareas);
        txtLP = findViewById(R.id.editText8);
        txtLinea = findViewById(R.id.editTextNumber);txtLinea.setText("1");
        lblProc = findViewById(R.id.btnRegsList);
        lblPend = findViewById(R.id.btnRegsList2);
        pbar =  findViewById(R.id.pgrtareas2);

        idPickingEnc=gl.gIdPickingEnc;

        anim = ObjectAnimator.ofInt(pbar,"progress",0,100);
        ProgressDialog("Cargando forma");

        ws = new WebServiceHandler(frm_preparacion_packing_bulto.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandlers();

        Load();
        items.clear();
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
        //if (pendientes==0) {
            msgAskSave("Finalizar la tarea");
        //} else {
        //    msgbox("La tarea no se puede finalizar, quedan articulos pendientes");
        //}
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
                        clsBeTrans_packing_enc_bulto sitem = (clsBeTrans_packing_enc_bulto) lvObj;
                        selitem = sitem;
                        selid = sitem.Idpackingenc;
                        selidx = position;
                        adapter.setSelectedIndex(position);
                    }
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selid = 0;
                    if (position > 0){
                        Object lvObj = listView.getItemAtPosition(position);
                        clsBeTrans_packing_enc_bulto sitem = (clsBeTrans_packing_enc_bulto) lvObj;
                        selid = sitem.Idpackingenc;
                        adapter.setSelectedIndex(position);

                        if (sitem.bandera==0) msgAskBorrar("Eliminar registro");
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
        clsBeTrans_packing_enc_bulto item,itemt;
        clsBeTrans_packing_enc enc;
        int bult=0,linea=0;

        try {
            item_list.clear();proc=0;

            Collections.sort(items,new OrdenarPorLinea());

            item=new clsBeTrans_packing_enc_bulto();
            item_list.add(item);

            for (int i = 0; i <items.size(); i++) {

                item=new clsBeTrans_packing_enc_bulto();
                enc=items.get(i);

                if (i==0) linea=enc.No_linea;

                item.Idpackingenc=enc.Idpackingenc;
                item.Idbodega=enc.Idbodega;
                item.Idpickingenc=enc.Idpickingenc;
                item.Iddespachoenc=enc.Iddespachoenc;
                item.Idproductobodega=enc.Idproductobodega;
                item.Idproductoestado=enc.Idproductoestado;
                item.Idpresentacion=enc.Idpresentacion;
                item.Idunidadmedida=enc.Idunidadmedida;
                item.Lote=enc.Lote;
                item.Fecha_vence=enc.Fecha_vence;
                item.Lic_plate=enc.Lic_plate;
                item.No_linea=enc.No_linea;
                item.Cantidad_bultos_packing=enc.Cantidad_bultos_packing;
                item.Cantidad_camas_packing=enc.Cantidad_camas_packing;
                item.Idoperadorbodega=enc.Idoperadorbodega;
                item.Idempresaservicio=enc.Idempresaservicio;
                item.Referencia=enc.Referencia;
                item.Fecha_packing=enc.Fecha_packing;

                item.nom_prod=enc.nom_prod;
                item.CodigoProducto=enc.CodigoProducto;
                item.ProductoPresentacion=enc.ProductoPresentacion;
                item.ProductoUnidadMedida=enc.ProductoUnidadMedida;

                item.bandera=0;

                if (linea==enc.No_linea) {
                    bult+=(int) enc.Cantidad_bultos_packing;
                } else {
                    itemt=new clsBeTrans_packing_enc_bulto();
                    itemt.No_linea=linea;
                    itemt.Cantidad_bultos_packing=bult;
                    itemt.bandera=1;
                    item_list.add(itemt);

                    linea=enc.No_linea;
                    bult=(int) enc.Cantidad_bultos_packing;
                }

                item_list.add(item);

                proc+=item.Cantidad_bultos_packing;
            }

            if (bult>0) {
                itemt=new clsBeTrans_packing_enc_bulto();
                itemt.No_linea=linea;
                itemt.Cantidad_bultos_packing=bult;
                itemt.bandera=1;
                item_list.add(itemt);
            }

            adapter=new list_adapt_lista_packing_bulto(this,item_list);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            mu.msgbox("listItems : "+e.getMessage());
        }

        progress.cancel();

        lblProc.setText("Procesado : "+proc);
        pendientes=itot-proc;
        lblPend.setText("Pendiente : "+pendientes);

        txtLP.setText("");txtLP.requestFocus();
    }

    private void processListUbic(){
        try {
            progress.setMessage("Obteniendo lista de tareas");

            pick =xobj.getresult(clsBeTrans_picking_ubicList.class,"Get_All_PickingUbic_By_PickingEnc");

            itot=0;
            for (int i = 0; i <pick.items.size(); i++) {
                itot+=pick.items.get(i).Cantidad_Solicitada;
            }

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

    private void newItem() {
        if (gl.paPickUbicId==0) return;

        try {
            selpick=cargaPickUbic();
            newid=getNewID()+1;

            addItem(selpick);
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void addItem(clsBeTrans_picking_ubic p) {
        clsBeTrans_packing_enc item;
        int lin;

        lin=Integer.parseInt(txtLinea.getText().toString());

        try {
            item=new clsBeTrans_packing_enc();

            item.Idpackingenc=newid;
            item.Idbodega=gl.IdBodega;
            item.Idpickingenc=p.IdPickingEnc;
            item.Iddespachoenc=gl.paPickUbicId;
            item.Idproductobodega=p.IdProductoBodega;
            item.Idproductoestado=0;
            item.Idpresentacion=p.IdPresentacion;
            item.Idunidadmedida=p.IdUnidadMedida;
            item.Lote=p.Lote;
            item.Fecha_vence=p.Fecha_Vence;
            item.Lic_plate="";
            item.No_linea=lin;
            item.Cantidad_bultos_packing=gl.paCant;
            item.Cantidad_camas_packing=gl.paCamas;
            item.Idoperadorbodega=gl.OperadorBodega.IdOperadorBodega;
            item.Idempresaservicio=gl.IdEmpresa;
            item.Referencia=gl.paBulto;
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
            if (items.get(i).Idpackingenc==selid) {
                items.remove(i);
                listItems();return;
            }
        }
    }

    private void GetFila() {
        String ss;
        int lin,pos=-1;

        try {
            ss=txtLP.getText().toString();
            if (ss.isEmpty()) return;

            try {
                lin=Integer.parseInt(txtLinea.getText().toString());
                if (lin<1 | lin>99) {
                    msgbox("Número de linea incorrecto");return;
                }
            } catch (Exception e) {
                lin=1;txtLinea.setText("1");
            }

            creaListaLotes(ss);
            if (gl.packlotes.size()==0) {
                toast("No existe ninguno lote disponible");return;
            }

            browse=1;
            startActivity(new Intent(this,frm_lista_packing_lotes.class));

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void creaListaLotes(String prid) {
        clsBeTrans_packing_lotes item;
        int cantu;

        try {
            gl.packlotes.clear();

            if (pick.items.size()==0) return;

            for (int i = 0; i <pick.items.size(); i++) {

                if (pick.items.get(i).CodigoProducto.equalsIgnoreCase(prid)) {

                    gl.paNombre=pick.items.get(i).NombreProducto;

                    cantu=cantIdPackUbic(pick.items.get(i).IdPickingUbic);

                    item = new clsBeTrans_packing_lotes();

                    item.id = pick.items.get(i).IdPickingUbic;
                    item.lote = pick.items.get(i).Lote;
                    item.fecha = pick.items.get(i).Fecha_Vence;
                    item.presentacion = pick.items.get(i).ProductoPresentacion;
                    item.disp = (int) pick.items.get(i).Cantidad_Solicitada-cantu;
                    item.cant = 0;

                    if (item.disp>0) gl.packlotes.add(item);
                }
            }

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
                    toast("Tarea finalizada : "+regs+" registros");finish();
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

    //region Aux Lista

    private clsBeTrans_picking_ubic cargaPickUbic() {
        for (int i = 0; i <pick.items.size(); i++) {
            if (pick.items.get(i).IdPickingUbic==gl.paPickUbicId) return pick.items.get(i);
        }

        return null;
    }

    private int getNewID() {
        int val,maxx=-1;

        if (items.size()==0) return 0;
        for (int i = 0; i <items.size(); i++) {
            val=items.get(i).Idpackingenc;
            if (val>maxx) maxx=val;
        }

        return maxx;
    }

    private int cantIdPackUbic(int IdPickingUbic) {
        int val=0;

        if (items.size()==0) return 0;
        for (int i = 0; i <items.size(); i++) {
            if (items.get(i).Iddespachoenc==IdPickingUbic) val+=items.get(i).Cantidad_bultos_packing;
        }

        return val;
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

            if (browse==1) {
                browse=0;
                newItem();
                return;
            }

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