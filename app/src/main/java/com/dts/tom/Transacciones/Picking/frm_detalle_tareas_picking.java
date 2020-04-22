package com.dts.tom.Transacciones.Picking;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubic;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_detalle_tareas_picking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.zbra.androidlinq.Stream;

import static br.com.zbra.androidlinq.Linq.stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

public class frm_detalle_tareas_picking extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private ProgressDialog progress;
    private ListView listView;
    private Spinner cmbOrdenadorPor;
    private Button btnPendientes;

    private clsBeTrans_picking_enc gBePicking;
    private clsBeTrans_picking_ubicList plistPickingUbi= new clsBeTrans_picking_ubicList();

    private ArrayList<clsBeTrans_picking_ubic> BeListPickingUbic = new ArrayList<clsBeTrans_picking_ubic>();
    private list_adapt_detalle_tareas_picking adapter;

    private List TipoOrden = new ArrayList();

    private int TipoLista=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_detalle_tareas_picking);
        super.InitBase();

        ws = new WebServiceHandler(frm_detalle_tareas_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        listView = (ListView) findViewById(R.id.listTareasPicking);
        cmbOrdenadorPor = (Spinner)findViewById(R.id.cmbOrdenadorPor);
        btnPendientes = (Button)findViewById(R.id.btnPendientes);

        ProgressDialog("Cargando forma...");

        setHandlers();

        Load();


    }

    private void setHandlers() {

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position > 0) {

                    }

                }

            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
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


    private void Load(){

        try {

            TipoOrden.add("Ubicacion");
            TipoOrden.add("Codigo");
            TipoOrden.add("Vence");
            TipoOrden.add("Estado");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, TipoOrden);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbOrdenadorPor.setAdapter(dataAdapter);

            if (TipoOrden.size()>0) cmbOrdenadorPor.setSelection(0);

            if (gl.gIdPickingEnc>0){
                execws(1);
            }

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Lista_Detalle_Picking(){
        clsBeTrans_picking_ubic vItem;
        BeListPickingUbic.clear();

        try{

            progress.setMessage("Listando detalle de picking");

            if (plistPickingUbi!=null){
                if (plistPickingUbi.items!=null){

                if (TipoLista==1){//Resumido

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        Function<clsBeTrans_picking_ubic, List<Object>> compositKey = std ->
                                Arrays.asList(std.IdUbicacion,std.CodigoProducto,std.NombreUbicacion,std.NombreProducto,std.ProductoUnidadMedida,
                                            std.ProductoPresentacion,std.Lote,std.Lic_plate,std.Fecha_Vence,std.ProductoEstado,std.IdPresentacion,
                                            std.IdProductoEstado,std.IdProductoBodega);

                         plistPickingUbi.items.stream().collect(Collectors.groupingBy(compositKey, Collectors.toList()));


                    }


                }else if (TipoLista==2){//Detallado

                }

                    vItem = new  clsBeTrans_picking_ubic();

                    BeListPickingUbic.add(vItem);

                for (clsBeTrans_picking_ubic obj:plistPickingUbi.items){

                    vItem = new  clsBeTrans_picking_ubic();
                    obj.Fecha_Vence = du.convierteFechaMostar(obj.Fecha_Vence);
                    vItem = obj;

                    BeListPickingUbic.add(vItem);

                }

                    int count =BeListPickingUbic.size()-1;
                    btnPendientes.setText("Regs: "+count);

                }
            }

            Collections.sort(BeListPickingUbic,new OrdenarItems());

            adapter=new list_adapt_detalle_tareas_picking(this,BeListPickingUbic);
            listView.setAdapter(adapter);


        }catch (Exception e){
            mu.msgbox("Lista_Detalle_Picking:"+e.getMessage());
        }
    }

    public class OrdenarItems implements Comparator<clsBeTrans_picking_ubic> {

        public int compare(clsBeTrans_picking_ubic left,clsBeTrans_picking_ubic rigth){
            //return left.CodigoProducto-rigth.IdRecepcionDet;
            return left.CodigoProducto.compareTo(rigth.CodigoProducto);
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
                        callMethod("Get_Picking_By_IdPickingEnc","pIdPickingEnc",gl.gIdPickingEnc);
                        break;
                    case 2:
                        callMethod("Actualizar_Estado_Picking","oBeTrans_picking_enc",gBePicking);
                        break;
                    case 3:
                        break;
                    case 4:
                        //callMethod("Get_Operadores_By_IdBodega_For_HH","IdBodega",idbodega);
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
                    processGetPicking();
                    break;
                case 2:
                    procesActualizarEstadoPicking();
                    break;
                case 3:

                    break;
                case 4:
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processGetPicking(){

        try{

            progress.setMessage("Obteniendo valores de picking");

            gBePicking =xobj.getresult(clsBeTrans_picking_enc.class,"Get_Picking_By_IdPickingEnc");

            if (gBePicking!=null){

                progress.setMessage("Actualizando picking a estado pendiente...");

                if (gBePicking.Estado.equals("Nuevo")){
                    gBePicking.Estado = "Pendiente";
                    execws(2);
                }else{
                    procesActualizarEstadoPicking();
                }

            }else{
                progress.cancel();
            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processGetPicking:"+e.getMessage());
        }
    }

    private void procesActualizarEstadoPicking(){

        try{

            progress.setMessage("Cargando detalle del picking...");

            plistPickingUbi = gBePicking.ListaPickingUbic;

            Lista_Detalle_Picking();

        }catch (Exception e){
            mu.msgbox("procesActualizarEstadoPicking:"+e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
