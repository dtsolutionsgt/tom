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
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_det;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_detList;
import com.dts.ladapt.CambioUbicacion.list_adapter_detalle_cambio_ubic;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SplittableRandom;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_detalle_cambio_ubicacion extends PBase {

    private TextView lblTituloForma,lblRegs,lblTotal;
    private EditText txtCodigo;
    private ListView listView;
    private CheckBox chkTodos;
    private Button btnBack;

    private frm_detalle_cambio_ubicacion.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private boolean Modo=false;
    private String eststr="";
    private int BeBuscaProducto;

    private list_adapter_detalle_cambio_ubic adapter;

    private clsBeTrans_ubic_hh_det pBeTransUbicHhDet = new clsBeTrans_ubic_hh_det();
    private clsBeTrans_ubic_hh_detList pBeTransUbicHhDetList = new clsBeTrans_ubic_hh_detList();

    private ArrayList<clsBeTrans_ubic_hh_det> pBeTransUbicHhDetListArray= new ArrayList<clsBeTrans_ubic_hh_det>();

    private int index, listCompTot,listTot, listUser, listCompUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_detalle_cambio_ubicacion);

        super.InitBase();

        ws = new WebServiceHandler(frm_detalle_cambio_ubicacion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        listView = (ListView) findViewById(R.id.listDetalle);
        chkTodos = (CheckBox) findViewById(R.id.chkTodos);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);
        lblRegs = (TextView) findViewById(R.id.lblRegs);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        btnBack = (Button) findViewById(R.id.btnBack);

        Modo = (gl.modo_cambio==1?true:false);

        lblTituloForma.setText( String.format("Lista de cambios de %s",(Modo==true?"ubicación":"estado")));

        listCompTot = 0;
        listTot = 0;
        listUser = 0;
        listCompUser = 0;

        lblTotal.setText(" - ");

        setHandlers();

        clearAll();

        ProgressDialog("Cargando forma");

        Load();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void Load(){

        try{

            listView.setAdapter(null);

            if (gl.IdTareaUbicEnc>0){
                progress.setMessage("Cargando detalle de tarea de cambio de ubicación");
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

                                    progress.setMessage("Validando existencia del producto");

                                    execws(3);
                                }else{
                                    Load();
                                }
                                return true;
                        }
                    }
                    return false;
                }
            });

            chkTodos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Load();
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

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    processTareaDetalleCambioUbicacion();
                    break;
                case 2:
                    processCambioEstado();
                    break;
                case 3:
                    processProducto();
                    break;
            }

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
                                "pIdOperador",(chkTodos.isChecked()?0: gl.IdOperador));
                        break;
                    case 2:
                        callMethod("Actualizar_Estado_Cambio","pIdTransUbicHHEnc", gl.tareaenc,"finalizar", true);
                         break;
                    case 3:
                        callMethod("Get_BeProducto_By_Codigo_For_HH","pCodigo",txtCodigo.getText().toString(),
                                "pIdBodega",gl.IdBodega);
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

        boolean soperador,ubicados;

        try{

            clsBeTrans_ubic_hh_det vItem;
            pBeTransUbicHhDetListArray.clear();

            listTot=0;
            listUser=0;
            listCompUser=0;
            listCompTot=0;

            if (pBeTransUbicHhDetList!=null) {

                if( pBeTransUbicHhDetList.items!=null ){

                    if (chkTodos.isChecked()) {
                        soperador = true;
                    } else
                    {soperador = false;}

                    ubicados=false;

                    List<clsBeTrans_ubic_hh_det> BeTransUbicHhDetTmp =
                            stream(pBeTransUbicHhDetList.items)
                                    .where(c -> (soperador?c.IdOperador > -1:c.IdOperador == gl.IdOperador))
                                    .where(c -> (BeBuscaProducto==0? c.getProducto().IdProducto > -1: c.getProducto().IdProducto == BeBuscaProducto))
                                    .where (c -> (ubicados?c.getCantidad() == c.getRecibido(): c.getCantidad() - c.getRecibido() >0))
                                    .where(c -> c.getCantidad()>0)
                                    .toList();

                    pBeTransUbicHhDetList.items=BeTransUbicHhDetTmp;

                    for (int i = 0; i<=pBeTransUbicHhDetList.items.size()-1; i++) {

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

                        if (pBeTransUbicHhDetList.items.get(i).getIdOperador()==gl.IdOperador){
                            listUser += 1;
                        }

                        if (!pBeTransUbicHhDetList.items.get(i).getRealizado()){
                            if (pBeTransUbicHhDetList.items.get(i).getIdOperador()==gl.IdOperador){
                                listCompUser += 1;
                            }

                            listCompTot += 1;
                        }

                        listTot += 1;
                    }

                    lblRegs.setText(""+listUser);
                    lblTotal.setText(listCompTot + "/" + listTot);

                    if (pBeTransUbicHhDetList!=null){
                        if (pBeTransUbicHhDetList.items!=null){

                            adapter=new list_adapter_detalle_cambio_ubic(this,pBeTransUbicHhDetListArray);
                            listView.setAdapter(adapter);

                            if (pBeTransUbicHhDetList.items.size()==1){

                                if (pBeTransUbicHhDetList.items.get(0).getCantidad()-pBeTransUbicHhDetList.items.get(0).getRecibido()>0){
                                    msgAskContinuar("Ya solo le queda este registro pendiente ¿Quiere continuar ingresando lo pendiente?");
                                }else{
                                    msgAskFinalizar(String.format("Ya no hay productos pendientes de %s. ¿Quiere finalizar la tarea?",
                                            (gl.modo_cambio==1? "ubicar": "cambiar de estado")));
                                }

                            }else{
                                adapter.setSelectedIndex(-1);
                                index = -1;
                            }

                            if (pBeTransUbicHhDetList.items.size()==0){

                                msgAskFinalizar(String.format("Ya no hay productos pendientes de %s. ¿Quiere finalizar la tarea?",
                                        (gl.modo_cambio==1? "ubicar": "cambiar de estado")));

                            }
                            adapter.refreshItems();
                        }
                    }
                }
            }

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
        progress.cancel();
    }

    private void processCambioEstado(){

        try {

            int resultado = (int) xobj.getSingle("Actualizar_Estado_CambioResult",int.class);

            if(resultado==0){
                msgSalir("La tarea ha sido finalizada");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

        progress.cancel();

    }

    private void processTareaDetalleCambioUbicacion(){

        try {

            pBeTransUbicHhDetList = xobj.getresult(clsBeTrans_ubic_hh_detList.class,"Get_All_By_IdTransUbicEnc_And_IdOperador");

            if(pBeTransUbicHhDetList!=null){
                if(pBeTransUbicHhDetList.items!=null){

                    Llena_Tarea_Detalle_Ubicacion();

                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

        progress.cancel();

    }

    private void processProducto(){

        try {

            clsBeProducto bProd = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (bProd != null){
                BeBuscaProducto=bProd.getIdProducto();

                progress.setMessage("Obteniendo detalle de la tarea de cambio de ubicación");

                execws(1);
            }else{
                BeBuscaProducto=0;
                throw new Exception("El producto no existe");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
        progress.cancel();
    }

    public void validacion_finalizar_tarea(View view){

        try {

            if (listCompUser != listUser){
                msgAskFinalizarIncompleto("No ha " + (gl.modo_cambio==1?"ubicado":"cambiado de estado") +  " todos los productos.\\¿Desea finalizar la tarea?");
                return;
            }

            if (listCompTot != listTot) {
                msgAskFinalizarIncompleto("No ha " + (gl.modo_cambio==1?"ubicado":"cambiado de estado") +  " todos los productos.\\¿Desea finalizar la tarea?");
                return;
            }

            msgAskFinalizar("Finalizar la tarea");

        }catch(Exception ex){

        }
    }

    private void finalizar_tarea(){

        try {

            Date currentTime = Calendar.getInstance().getTime();

            gl.tareaenc.setEstado(eststr);
            gl.tareaenc.setHoraFin( app.strFecha(currentTime));
            gl.tareaenc.setFechaFin(app.strFecha(currentTime));

            progress.setMessage("Finalizando la tarea de cambio de "+ (gl.modo_cambio==1?"ubicación":"estado"));

            execws(2);

        }catch(Exception ex){

        }
    }

    private void msgAskFinalizarIncompleto(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    eststr="Incompleto";
                    msgAskFinalizarSeguro("Está seguro");
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

    private void msgAskFinalizar(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    msgAskFinalizarSeguro("Está seguro");
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

    private void msgAskFinalizarSeguro(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    eststr = "Finalizado";
                    finalizar_tarea();
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

    private void msgAskContinuar(String msg){

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Object lvObj = listView.getItemAtPosition(0);
                    clsBeTrans_ubic_hh_det vItemTmp = (clsBeTrans_ubic_hh_det) lvObj;

                    adapter.setSelectedIndex(0);

                    index = 0;

                    Procesa_Registro(vItemTmp.IdTareaUbicacionDet);
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

    private void msgSalir(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_detalle_cambio_ubicacion.super.finish();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Regresar(View view){
      finish();
    }
//
//    @Override
//    public void onBackPressed() {}

    @Override
    protected void onResume(){
        Llena_Tarea_Detalle_Ubicacion();
        super.onResume();
    }

}
