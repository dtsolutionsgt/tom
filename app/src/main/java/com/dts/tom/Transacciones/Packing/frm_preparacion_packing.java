package com.dts.tom.Transacciones.Packing;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_enc;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_encList;
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.ladapt.list_adapt_lista_packing;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import static br.com.zbra.androidlinq.Linq.stream;

public class frm_preparacion_packing extends PBase {

    private ListView listView;
    private EditText txtLP,txtLinea;
    private TextView lblProc,lblPend, txtLicenciaPacking;
    private ProgressBar pbar;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private final ArrayList<clsBeTrans_packing_enc> items = new ArrayList<>();
    private final ArrayList<clsBeTrans_packing_enc> item_list = new ArrayList<>();
    private clsBeTrans_picking_ubicList pick = new clsBeTrans_picking_ubicList();
    private clsBeTrans_packing_encList itemList ;
    private clsBeTrans_packing_encList savedList = new clsBeTrans_packing_encList();

    private list_adapt_lista_packing adapter;
    private clsBeTrans_packing_enc selitem;

    private ObjectAnimator anim;
    private ProgressDialog progress;

    private int idPickingEnc,selidx,pendientes;
    private String sellp=".";
    private boolean idle=true;
    private clsBeResolucion_lp_operador nBeResolucion = null;
    private String pNumeroLP = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_preparacion_packing);
        super.InitBase();

        listView = findViewById(R.id.listTareas);
        txtLP = findViewById(R.id.txtLicencia);
        txtLinea = findViewById(R.id.editTextNumber);txtLinea.setText("1");
        lblProc = findViewById(R.id.btnRegsList);
        lblPend = findViewById(R.id.btnRegsList2);

        txtLicenciaPacking = findViewById(R.id.txtLicenciaPacking);

        pbar = findViewById(R.id.pgrtareas2);

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

    public void doList(View view){
        verLista();
    }

    private void verLista() {
        try {
            gl.LicenciaPacking = txtLicenciaPacking.getText().toString();
            browse = 1;

            creaListaLotes(txtLP.getText().toString());
            startActivity(new Intent(this, frm_lista_packing_lp.class));
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" verLista . "+e.getMessage());
        }
    }

    public void guardarTarea(View view) {
        if (items.size()==0) {
            msgbox("No se puede finalizar una tarea vacia");return;
        }

        msgAskSave("¿Finalizar la tarea?");
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
                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_packing_enc sitem = (clsBeTrans_packing_enc) lvObj;
                    selitem = sitem;
                    selid = sitem.Idpackingenc;
                    selidx = position;
                    adapter.setSelectedIndex(position);

                    if (selitem.Idpackingenc == 0) {
                        nBeResolucion = null;
                        String[] licencia = selitem.nom_prod.split(":");
                        txtLicenciaPacking.setText(licencia[0].trim());
                    }

                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selid = 0;
                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_packing_enc sitem = (clsBeTrans_packing_enc) lvObj;
                    selitem = sitem;
                    sellp = selitem.Lic_plate;
                    adapter.setSelectedIndex(position);

                    msgAskBorrar("Eliminar registro");

                    return true;
                }
            });

            txtLP.setOnKeyListener((v, keyCode, event) -> {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    gl.filtroprod = txtLP.getText().toString();
                    verLista();
                }
                return false;
            });

        } catch (Exception e){
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    //endregion

    //region Main

    private void listItems() {
        String actLinea = "";
        int count = 0;
        double cantidad = 0;
        clsBeTrans_packing_enc item;

        try {

            if (items.size()>1) Collections.sort(items,new OrdenarPorLinea());

            item_list.clear();

            for (int i = 0; i <items.size(); i++) {
                item=items.get(i);
                item.CodigoProducto=codigoProducto(item.Idproductobodega);
                item.nom_prod=nombreProducto(item.Idproductobodega);
                item.ProductoPresentacion=productoPresentacion(item.Idpresentacion);
                item.ProductoUnidadMedida=productoUnidadMedida(item.Idunidadmedida);
                item.ProductoEstado=estadoProducto(item.Idproductoestado);

                if (!item.No_linea.equals(actLinea)) {
                    // Si no es la primera iteración, agregar un item que indica el número de registros
                    if (!actLinea.equals("")) {
                        clsBeTrans_packing_enc tmp = new clsBeTrans_packing_enc();
                        tmp.Lic_plate = "";
                        tmp.CodigoProducto = "";
                        tmp.Fecha_vence = "";
                        tmp.Lote = "";
                        tmp.EsConteo = true;
                        tmp.nom_prod = actLinea + ": " + count;
                        tmp.Cantidad_bultos_packing = cantidad;
                        item_list.add(tmp);
                    }

                    actLinea = item.No_linea;
                    count = 0;
                    cantidad = 0;
                }

                item_list.add(item);
                count++;
                cantidad += item.Cantidad_bultos_packing;
            }

            if (!actLinea.equals("")) {
                clsBeTrans_packing_enc tmpU = new clsBeTrans_packing_enc();
                tmpU.Lic_plate = "";
                tmpU.CodigoProducto = "";
                tmpU.Fecha_vence = "";
                tmpU.Lote = "";
                tmpU.EsConteo = true;
                tmpU.nom_prod = actLinea + ": " + count;
                tmpU.Cantidad_bultos_packing = cantidad;
                item_list.add(tmpU);
            }


            adapter=new list_adapt_lista_packing(this,item_list);
            listView.setAdapter(adapter);

            try {
                if (item_list.size()>1) {
                    for (int i = 1; i <item_list.size(); i++) {
                        if (item_list.get(i).Lic_plate.equalsIgnoreCase(sellp)) {
                            adapter.setSelectedIndex(i);
                            listView.smoothScrollToPosition(i);
                        }
                    }
                }
            } catch (Exception e) {}

        } catch (Exception e) {
            mu.msgbox("listItems : "+e.getMessage());
        }

        progress.cancel();

        lblProc.setText("Procesado : "+(items.size()));
        try {
            pendientes=pick.items.size()-items.size();
        } catch (Exception e) {
            String ss=e.getMessage();
            pendientes=0;
        }
        lblPend.setText("Pendiente : "+pendientes);
        txtLP.setText("");focusLP();
    }

    private void processListUbic(){
        try {
            progress.setMessage("Obteniendo lista de articulos");

            pick=xobj.getresult(clsBeTrans_picking_ubicList.class,"Get_All_PickingUbic_By_PickingEnc");

            execws(2);
        } catch (Exception e) {
            progress.cancel();
            String ss=e.getMessage();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processListSaved(){
        try {
            items.clear();

            progress.setMessage("Obteniendo lista de articulos guardados");

            savedList =xobj.getresult(clsBeTrans_packing_encList.class,"Get_All_Packing_By_IdPicking");

            try {
                if (savedList!=null) {
                    if (savedList.items.size()>0) cargaListaGuardados();
                }
            } catch (Exception e) {}

            listItems();

       } catch (Exception e) {
            progress.cancel();
            String ss=e.getMessage();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
       }

       idle=true;
    }

    private void cargaListaGuardados() {
        try {


            for (int i = 0; i <savedList.items.size(); i++) {

                clsBeTrans_packing_enc itm=savedList.items.get(i);
                itm.CodigoProducto=codigoProducto(itm.Idproductobodega);
                itm.nom_prod=nombreProducto(itm.Idproductobodega);
                itm.ProductoPresentacion=productoPresentacion(itm.Idpresentacion);
                itm.ProductoUnidadMedida=productoUnidadMedida(itm.Idunidadmedida);
                itm.ProductoEstado=estadoProducto(itm.Idproductoestado);

                items.add(itm);
            }

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void Load(){

        if (!idle) return;

        try {
            idle=false;
            progress.setMessage("Cargando tarea ...");
            progress.show();

            execws(4);
        } catch (Exception e){
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void addItem() {
        clsBeTrans_packing_enc item;
        clsBeTrans_picking_ubic p;
        int lin;

        if (selidx<0) return;

        try {

            if (items.size() > 0) {
                Optional<clsBeTrans_packing_enc> itemOpt = items.stream()
                .filter(o -> o.getIdproductobodega() == gl.auxPacking.IdProductoBodega)
                .filter(o -> o.getFecha_vence().equals(gl.auxPacking.fecha))
                .filter(o -> o.getLic_plate().equals(gl.auxPacking.licencia))
                .filter(o -> o.getLote().equals(gl.auxPacking.lote))
                .filter(o -> o.getNo_linea().equals(txtLicenciaPacking.getText().toString()))
                .findFirst();

                if (itemOpt.isPresent()) {
                    clsBeTrans_packing_enc tmpObj;
                    tmpObj = itemOpt.get();

                    if (tmpObj.No_linea.equals(txtLicenciaPacking.getText().toString())) {
                        tmpObj.setCantidad_bultos_packing(tmpObj.getCantidad_bultos_packing() + gl.auxPacking.cant);
                        adapter.notifyDataSetChanged();

                        itemList = new clsBeTrans_packing_encList();
                        itemList.items = new ArrayList<>();
                        itemList.items.add(tmpObj);

                        execws(3);
                        return;
                    }
                }
            }

            item=new clsBeTrans_packing_enc();

            p=pick.items.get(selidx);
            item.Idpackingenc=0;
            item.Idbodega=gl.IdBodega;
            item.Idpickingenc=p.IdPickingEnc;
            item.IdDespachoEnc=0;
            item.Idproductobodega=p.IdProductoBodega;
            item.Idproductoestado=p.IdProductoEstado;
            item.Idpresentacion=p.IdPresentacion;
            item.Idunidadmedida=p.IdUnidadMedida;
            item.Lote=p.Lote;
            item.Fecha_vence=p.Fecha_Vence;
            item.Lic_plate=""+p.Lic_plate;sellp=p.Lic_plate;
            item.No_linea=txtLicenciaPacking.getText().toString();
            item.Cantidad_bultos_packing=gl.auxPacking.cant;
            item.Cantidad_camas_packing=1;
            item.Idoperadorbodega=gl.OperadorBodega.IdOperadorBodega;
            item.Idempresaservicio=gl.IdEmpresa;
            item.Referencia="";
            item.Fecha_packing=app.strFechaXML(du.getFechaActual());
            item.nom_prod=p.NombreProducto;
            item.CodigoProducto=p.CodigoProducto;
            item.ProductoPresentacion=p.ProductoPresentacion;
            item.ProductoUnidadMedida=p.ProductoUnidadMedida;
            item.ProductoEstado=p.ProductoEstado;

            itemList = new clsBeTrans_packing_encList();
            itemList.items = new ArrayList<>();
            itemList.items.add(item);

            txtLP.setText("");

            execws(3);
            //listItems();
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void delItem() {
        try {
            execws(6);
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void GetFila() {

        clsBeTrans_packing_enc it;
        String ss;
        boolean exist,flag=false;

        if (pick==null) return;

        try {
            ss=txtLP.getText().toString();
            if (ss.isEmpty()) return;

            for (int i = 0; i <pick.items.size(); i++) {
               if (pick.items.get(i).Lic_plate.equalsIgnoreCase(ss)) {
                   exist=false;selidx=i;flag=true;

                   for (int j = 0; j <items.size(); j++) {
                       it=items.get(j);
                       if (it.Lic_plate.equalsIgnoreCase(ss) & it.Idproductoestado==pick.items.get(i).IdProductoEstado) {
                           exist=true;break;
                       }
                   }

                   if (!exist) addItem();
                }
            }

            if (!flag) {
                txtLP.selectAll();focusLP();
                msgAskLista("La licencia no existe.\n¿Mostrar lista?");focusLP();
                return;
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
            progress.setMessage("Finalizando tarea ...");
            progress.show();
            idle=false;
            execws(5);

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
                    toast("Producto agregado.");

                    nBeResolucion = null;
                    execws(2);
                } else {
                    mu.msgbox("No se logró finalizar la tarea, por favor repite el proceso.");
                }
            } else {
                mu.msgbox("cantidadRegGuardados : "+ws.error);
            }
        } catch (Exception e){
            mu.msgbox("cantidadRegGuardados : "+e.getMessage());
        }
    }

    private void processLicenciaPacking() {
        try {

            if (nBeResolucion == null){
                nBeResolucion = new clsBeResolucion_lp_operador();
                if (xobj!=null){
                    nBeResolucion = xobj.getresult(clsBeResolucion_lp_operador.class, "Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");
                }else{
                    toast("El objeto SI es nulo");
                }
            }

            if (nBeResolucion !=null){
                gl.IdResolucionLpOperador = nBeResolucion.IdResolucionlp;

                long pLpSiguiente = nBeResolucion.Correlativo_Actual +1;
                int largoMaximo = String.valueOf(nBeResolucion.Correlativo_Final).length();

                long intLPSig = pLpSiguiente;
                int MaxL = largoMaximo;

                String result = String.format("%0"+ MaxL + "d",intLPSig);

                pNumeroLP= nBeResolucion.Serie + result;
                txtLicenciaPacking.setText(pNumeroLP);
            } else {
                gl.IdResolucionLpOperador =0;
                return;
            }

            execws(1);
        }catch (Exception e){
            mu.msgbox("processNuevoLP_RE: "+e.getMessage());
        }
    }

    private void processActualizaEstado(){
        int exito;

        try {

            idle=true; progress.cancel();

            if (!ws.errorflag) {
                exito = xobj.getresult(Integer.class,"Actualizar_Estado_Packing");
                if (exito>0) {
                    toast("Tarea finalizada.");
                    finish();
                } else {
                    mu.msgbox("No se logró finalizar la tarea, por favor repite el proceso.");
                }
            } else {
                mu.msgbox("processActualizaEstado : "+ws.error);
            }
        } catch (Exception e){
            mu.msgbox("cantidadRegGuardados : "+e.getMessage());
        }
    }

    private void processEliminarLinea(){
        int exito;

        try {
            idle=true; progress.cancel();

            if (!ws.errorflag) {
                exito = xobj.getresult(Integer.class,"Eliminar_Linea_Packing");
                if (exito>0) {
                    toast("Línea eliminada con éxito.");
                    execws(2);
                } else {
                    mu.msgbox("No se logró finalizar la tarea, por favor repite el proceso.");
                }
            } else {
                mu.msgbox("processEliminarLinea : "+ws.error);
            }
        } catch (Exception e){
            mu.msgbox("processEliminarLinea : "+e.getMessage());
        }
    }

    private void creaListaLotes(String licencia) {
        clsBeTrans_packing_lotes item;
        try {
            gl.packlotes.clear();
            gl.filtroprod=licencia;

            if (pick.items.size() > 0) {
                for (clsBeTrans_picking_ubic obj: pick.items) {
                    item = new clsBeTrans_packing_lotes();

                    item.codigo = obj.getCodigoProducto();
                    item.producto = obj.getNombreProducto();
                    item.lote = obj.getLote();
                    item.licencia = obj.getLic_plate();
                    item.IdProductoBodega = obj.IdProductoBodega;
                    item.cant = obj.getCantidad_Recibida();
                    item.fecha = obj.getFecha_Vence();

                    if (items.size() > 0) {
                        double totalCantidad = 0;
                        totalCantidad = items.stream()
                        .filter(p -> p.getLote().equals(obj.Lote))
                        .filter(p -> p.getLic_plate().equals(obj.Lic_plate))
                        .filter(p -> p.getIdproductobodega() == obj.IdProductoBodega)
                        .filter(p -> p.getFecha_vence().equals(obj.getFecha_Vence()))
                        .mapToDouble(clsBeTrans_packing_enc::getCantidad_bultos_packing) // Aquí accedemos a la cantidad
                        .sum();

                        item.disp = (int) (obj.getCantidad_Recibida() - totalCantidad);

                    } else {
                        item.disp = (int) obj.getCantidad_Recibida();
                    }

                    if (item.disp > 0) {
                        gl.packlotes.add(item);
                    }
                }
            }
        } catch (Exception e) {
            mu.msgbox("creaListaLotes: "+e.getMessage());
        }
    }

    private void creaListaLotes_D(String prid) {
        clsBeTrans_packing_lotes item;
        boolean flag;

        if (pick==null) return;

        try {
            gl.packlotes.clear();
            gl.filtroprod=prid;

            if (pick.items.size()==0) return;

            for (int i = 0; i <pick.items.size(); i++) {

                if (prid.isEmpty() | pick.items.get(i).CodigoProducto.equalsIgnoreCase(prid)) {
                    item = new clsBeTrans_packing_lotes();

                    item.presentacion = pick.items.get(i).CodigoProducto+" - "+pick.items.get(i).NombreProducto;
                    item.lote = pick.items.get(i).Lic_plate;
                    item.estado=estadoProducto(pick.items.get(i).IdProductoEstado);
                    item.cant = pick.items.get(i).getCantidad_Recibida();

                    flag=false;
                    if (prid.isEmpty()) {
                        flag=true;
                    } else {
                        if (pick.items.get(i).CodigoProducto.equalsIgnoreCase(prid)) {
                            flag=true;
                        }
                    }

                    if (flag) {
                        if (items.size()>0) {
                            for (int j = 0; j <items.size(); j++) {
                                if (items.get(j).Lic_plate.equalsIgnoreCase(item.lote)
                                    & items.get(j).ProductoEstado.equalsIgnoreCase(item.estado)) {
                                    flag=false;break;
                                }
                            }
                        }

                        if (flag) gl.packlotes.add(item);
                    }
                }
            }

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void agregaLP() {
        focusLP();
        if (gl.auxPacking == null) return;

        Optional<clsBeTrans_picking_ubic> pitem = pick.items.stream()
                .filter(p -> p.getLote().equals(gl.auxPacking.lote))
                .filter(p -> p.getLic_plate().equals(gl.auxPacking.licencia))
                .filter(p -> p.getIdProductoBodega() == gl.auxPacking.IdProductoBodega)
                .filter(p -> p.getFecha_Vence().equals(gl.auxPacking.fecha))
                .findFirst();

        if (pitem.isPresent()) {
            int index = pick.items.indexOf(pitem.get());
            selidx=index;
            addItem();
        } else {
            System.out.println("Producto no encontrado");
        }
        /*
        for (int i = 0; i <pick.items.size(); i++) {
            if (pick.items.get(i).Lic_plate.equalsIgnoreCase(gl.paBulto)
            & pick.items.get(i).ProductoEstado.equalsIgnoreCase(gl.paEstado)) {
                selidx=i;
                addItem();
                return;
            }
        }*/
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
                        callMethod("Get_All_Packing_By_IdPicking","IdPicking",idPickingEnc);
                        break;
                    case 3:
                        int idresolucion = 0;

                        if (nBeResolucion != null) {
                            idresolucion = nBeResolucion.IdResolucionlp;
                        }

                        callMethod("Inserta_Packing","pTrans_packing_enc",itemList.items, "pIdResolucion", idresolucion);
                        break;
                    case 4:
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador",gl.IdOperador,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 5:
                        callMethod("Actualizar_Estado_Packing", "pIdPicking", idPickingEnc);
                        break;
                    case 6:
                        callMethod("Eliminar_Linea_Packing", "pIdPackingEnc", selitem.Idpackingenc);
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
                    processListSaved();break;
                case 3:
                    cantidadRegGuardados();break;
                case 4:
                    processLicenciaPacking();
                    break;
                case 5:
                    processActualizaEstado();
                    break;
                case 6:
                    processEliminarLinea();
                    break;
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
            return left.No_linea.compareTo(rigth.No_linea);
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

    private String codigoProducto(int id) {
        for (int j = 0; j <pick.items.size(); j++) {
            if (pick.items.get(j).IdProductoBodega==id) return pick.items.get(j).CodigoProducto;
       }
       return "";
    }

    private String nombreProducto(int id) {
        for (int j = 0; j <pick.items.size(); j++) {
            if (pick.items.get(j).IdProductoBodega==id) return pick.items.get(j).NombreProducto;
        }
        return "";
    }

    private String productoPresentacion(int id) {
        for (int j = 0; j <pick.items.size(); j++) {
            if (pick.items.get(j).IdPresentacion==id) return pick.items.get(j).ProductoPresentacion;
        }
        return "";
    }

    private String productoUnidadMedida(int id) {
        for (int j = 0; j <pick.items.size(); j++) {
            if (pick.items.get(j).IdUnidadMedida==id) return pick.items.get(j).ProductoUnidadMedida;
        }
        return "";
    }

    private String estadoProducto(int id) {
        for (int j = 0; j <pick.items.size(); j++) {
            if (pick.items.get(j).IdProductoEstado==id) {
                return pick.items.get(j).ProductoEstado;
            }
        }
        return "";
    }

    public void generarNuevaLicencia(View v) {
        try {
            msgAskConfirmar("Está seguro de generar una nueva licencia");
        } catch (Exception e) {

        }
    }
    //endregion

    //region Dialogs
    private void msgAskConfirmar(String msg) {
        if (!idle) return;

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", (dialog1, which) -> execws(4));
            dialog.setNegativeButton("No", (dialog12, which) -> {
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgAskExit(String msg) {
        if (!idle) return;

        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", (dialog1, which) -> finish());
            dialog.setNegativeButton("No", (dialog12, which) -> {
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
            dialog.setMessage( msg);
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", (dialog12, which) -> guardar());
            dialog.setNegativeButton("No", (dialog1, which) -> {});
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
            dialog.setPositiveButton("Si", (dialog12, which) -> msgAskBorrarConfirmar("Está seguro"));
            dialog.setNegativeButton("No", (dialog1, which) -> {});
            dialog.show();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgAskBorrarConfirmar(String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_quest);
            dialog.setPositiveButton("Si", (dialog12, which) -> delItem());
            dialog.setNegativeButton("No", (dialog1, which) -> {});
            dialog.show();

        } catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    private void msgAskLista(String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", (dialog1, which) -> {
                creaListaLotes("");
                browse=1;
                startActivity(new Intent(frm_preparacion_packing.this,frm_lista_packing_lp.class));
            });

            dialog.setNegativeButton("No", (dialog12, which) -> {});
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
                agregaLP();
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