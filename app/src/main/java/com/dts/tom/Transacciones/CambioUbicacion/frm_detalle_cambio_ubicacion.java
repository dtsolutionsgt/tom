package com.dts.tom.Transacciones.CambioUbicacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_det;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_detList;
import com.dts.ladapt.CambioUbicacion.list_adapter_detalle_cambio_ubic;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_cambio_ubicacion extends PBase {

    private TextView lblTituloForma,lblRegs,lblTotal;
    private EditText txtCodigo;
    private ListView listView;
    private CheckBox chkUbicados,chkTodos;
    private Button btnBack;

    private frm_detalle_cambio_ubicacion.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    boolean Modo=false;

    private list_adapter_detalle_cambio_ubic adapter;

    private clsBeTrans_ubic_hh_det pBeTransUbicHhDet = new clsBeTrans_ubic_hh_det();
    private clsBeTrans_ubic_hh_detList pBeTransUbicHhDetList = new clsBeTrans_ubic_hh_detList();

    private ArrayList<clsBeTrans_ubic_hh_det> pBeTransUbicHhDetListArray= new ArrayList<clsBeTrans_ubic_hh_det>();

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_detalle_cambio_ubicacion);

        super.InitBase();

        ws = new WebServiceHandler(frm_detalle_cambio_ubicacion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        listView = (ListView) findViewById(R.id.listDetalle);
        chkUbicados = (CheckBox) findViewById(R.id.chkUbicados);
        chkTodos = (CheckBox) findViewById(R.id.chkTodos);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblRegs = (TextView) findViewById(R.id.lblRegs);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        btnBack = (Button) findViewById(R.id.btnBack);

        Modo = (gl.modo_cambio==1?true:false);

        lblTituloForma.setText( String.format("Listado de tareas de cambios de %s",(Modo==true?"ubicación":"estado")));

        setHandlers();

        clearAll();

        ProgressDialog("Cargando forma");

        Load();

    }

    private void Load(){

        try{
            if (gl.IdTareaUbicEnc>0){
                execws(1);
            }
        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }

    }

    private void setHandlers(){

        try{

            txtCodigo.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_ENTER:
                                //Metodo para filtrar la lista o llamar al WS

                                if (!txtCodigo.getText().toString().isEmpty()){
                                    //Sirve para filtrar los registros por un producto especifico.

                                    if (pBeTransUbicHhDetList!=null){
                                        if(pBeTransUbicHhDetList.items != null){
                                            List<clsBeTrans_ubic_hh_det> BeTransUbicHhDetTmp =
                                                    stream(pBeTransUbicHhDetList.items)
                                                            .where(c -> c.getProducto().getCodigo().equalsIgnoreCase(txtCodigo.getText().toString()))
                                                            .toList();

                                            pBeTransUbicHhDetList.items=BeTransUbicHhDetTmp;
                                            Llena_Tarea_Detalle_Ubicacion();
                                            txtCodigo.setText("");
                                        }
                                    }
                                }else{
                                    Load();
                                }
                                return true;
                        }
                    }
                    return false;
                }
            });

            chkUbicados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkUbicados.isChecked()==true){
                        //Acción cuando esté marcado en true el chkUbicados que sirve para filtrar todos los registros ya procesados.

                        if (pBeTransUbicHhDetList!=null){
                            if(pBeTransUbicHhDetList.items != null){
                                List<clsBeTrans_ubic_hh_det> BeTransUbicHhDetTmp =
                                        stream(pBeTransUbicHhDetList.items)
                                                .where(c -> c.Cantidad == c.Recibido)
                                                .toList();

                                pBeTransUbicHhDetList.items=BeTransUbicHhDetTmp;
                                Llena_Tarea_Detalle_Ubicacion();
                            }
                        }
                    }else{
                        Load();
                    }
                }
            });

            chkTodos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (chkTodos.isChecked()==true){
                        //Acción cuando esté marcado en true el chkUbicados que sirve para filtrar todos los registros ya procesados.

                        if (pBeTransUbicHhDetList!=null){
                            if(pBeTransUbicHhDetList.items != null){
                                List<clsBeTrans_ubic_hh_det> BeTransUbicHhDetTmp =
                                        stream(pBeTransUbicHhDetList.items)
                                                .where(c -> c.IdOperador > -1)
                                                .toList();

                                pBeTransUbicHhDetList.items=BeTransUbicHhDetTmp;
                                Llena_Tarea_Detalle_Ubicacion();
                            }
                        }
                    }else{
                        Load();
                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_ubic_hh_det vItem = (clsBeTrans_ubic_hh_det) lvObj;

                    adapter.setSelectedIndex(position);

                    index = position;

                    Procesa_Registro(vItem.IdTareaUbicacionDet);

                    adapter.refreshItems();

                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
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

    private void clearAll() {
        try{
            for (int i = 0; i < pBeTransUbicHhDetListArray.size(); i++ ) {
                pBeTransUbicHhDetListArray.get(i);
            }
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void Procesa_Registro(int IdTareaDet){

        try{

            gl.IdTareaUbicDet = IdTareaDet;
            gl.tareadet = pBeTransUbicHhDetList.items.get(index);

            Intent intent = new Intent(this,frm_cambio_ubicacion_dirigida.class);
            startActivity(intent);

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void Llena_Tarea_Detalle_Ubicacion(){

        clsBeTrans_ubic_hh_det vItem;
        pBeTransUbicHhDetListArray.clear();

        try{

            progress.setMessage("Cargando detalle de tarea de cambio de ubicación");

            if(!pBeTransUbicHhDetList.items.isEmpty() && pBeTransUbicHhDetList.items!=null ){

                for (int i = pBeTransUbicHhDetList.items.size()-1; i>=0; i--) {

                    vItem = new clsBeTrans_ubic_hh_det();

                    vItem.IdTareaUbicacionDet = pBeTransUbicHhDetList.items.get(i).getIdTareaUbicacionDet();
                    vItem.IdStock = pBeTransUbicHhDetList.items.get(i).getIdStock();
                    vItem.Producto.Codigo=pBeTransUbicHhDetList.items.get(i).getProducto().getCodigo();
                    vItem.Producto.Nombre = pBeTransUbicHhDetList.items.get(i).getProducto().getNombre();
                    vItem.ProductoPresentacion.Nombre = pBeTransUbicHhDetList.items.get(i).ProductoPresentacion.getNombre();
                    vItem.UbicacionOrigen.NombreCompleto = pBeTransUbicHhDetList.items.get(i).UbicacionOrigen.getNombreCompleto();
                    vItem.UbicacionDestino.NombreCompleto = pBeTransUbicHhDetList.items.get(i).UbicacionDestino.getNombreCompleto();
                    vItem.Cantidad = pBeTransUbicHhDetList.items.get(i).getCantidad();
                    vItem.Recibido = pBeTransUbicHhDetList.items.get(i).getRecibido();
                    vItem.Operador.Nombres = pBeTransUbicHhDetList.items.get(i).Operador.getNombres();
                    vItem.IdTareaUbicacionEnc = pBeTransUbicHhDetList.items.get(i).getIdTareaUbicacionEnc();

                    pBeTransUbicHhDetListArray.add(vItem);

                }

                lblTotal.setText("Regs: " + pBeTransUbicHhDetList.items.size());
            }

            adapter=new list_adapter_detalle_cambio_ubic(this,pBeTransUbicHhDetListArray);
            listView.setAdapter(adapter);

            progress.cancel();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processTareaDetalleCambioUbicacion();break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processTareaDetalleCambioUbicacion(){

        try {

            progress.setMessage("Obteniendo detalle de la tareas de cambio de ubicación");

            pBeTransUbicHhDetList = xobj.getresult(clsBeTrans_ubic_hh_detList.class,"Get_All_By_IdTransUbicEnc_And_IdOperador");

            if(pBeTransUbicHhDetList!=null){
                if(pBeTransUbicHhDetList.items!=null){
                    Llena_Tarea_Detalle_Ubicacion();
                }
            }

            progress.cancel();

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        } catch (Exception e) {
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
                        callMethod("Get_All_By_IdTransUbicEnc_And_IdOperador","pIdTransUbicHhEnc",gl.IdTareaUbicEnc,
                                "pIdOperador",gl.IdOperador);
                        break;
                }

            } catch (Exception e) {
                error=e.getMessage();errorflag =true;msgbox(error);
            }
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void Regresar(View view){
       msgAskExit(String.format("Está seguro de salir del detalle de la tarea de cambio de %s",(Modo==true?"ubicación":"estado")));
    }

    private void msgAskExit(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    gl.tareadet=new clsBeTrans_ubic_hh_det();
                    gl.IdTareaUbicDet = 0;
                    frm_detalle_cambio_ubicacion.super.finish();
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

    @Override
    public void onBackPressed() {

        try{

            msgAskExit(String.format("Está seguro de salir del detalle de la tarea de cambio de %s",(Modo==true?"ubicación":"estado")));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}
