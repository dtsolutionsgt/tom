package com.dts.tom.Transacciones.Recepcion;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_det;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_detalle_rec_prod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.zbra.androidlinq.Linq.stream;


public class frm_list_rec_prod_detalle extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private Button btnRegs;
    private ListView listView;

    private clsBeTrans_oc_det BeOcDet;
    private clsBeProducto BeProducto = new clsBeProducto();
    private clsBeTrans_re_detList pListTransRecDet = new clsBeTrans_re_detList();
    private clsBeTrans_re_det selitem;

    private int mdet=1;

    private list_adapt_detalle_rec_prod adapter;
    private static ArrayList<clsBeTrans_re_det> BeListDetalleRec= new ArrayList<clsBeTrans_re_det>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_rec_prod_detalle);

        super.InitBase();

        ws = new frm_list_rec_prod_detalle.WebServiceHandler(frm_list_rec_prod_detalle.this, gl.wsurl);
        xobj = new XMLObject(ws);

        btnRegs = (Button) findViewById(R.id.btnRegs);
        listView = (ListView)findViewById(R.id.listRecDet);

        if (gl.gselitem != null) {
            BeOcDet=gl.gselitem;
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setHandles();

        execws(1);

    }

    private void setHandles(){

        try{

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    if (position>0){

                            Object lvObj = listView.getItemAtPosition(position);
                            selitem = pListTransRecDet.items.get(position-1);

                            selid =selitem.IdRecepcionDet ;
                            selidx = position;
                            adapter.setSelectedIndex(position);

                            Procesar_registro();

                    }

                }

            });


        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }

    }

    public void BacKList(View view) {
    doExit();
    }

    private void Procesar_registro(){
        gl.mode=2;
        gl.gListTransRecDet.items = stream(pListTransRecDet.items).where(c->c.IdRecepcionDet == selid).toList();
        startActivity(new Intent(this, frm_recepcion_datos.class));
    }

    private void Lista_Detalle_Rec(){
        clsBeTrans_re_det vItem;
        BeListDetalleRec.clear();

        try{

            vItem = new clsBeTrans_re_det();
            BeListDetalleRec.add(vItem);

            if (mdet==1){

                Map<String, Map<String, Map<String, Map<Double, List<clsBeTrans_re_det>>>>> employessGroup=
                        null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    employessGroup = pListTransRecDet.items.stream().collect(Collectors.groupingBy(clsBeTrans_re_det::getNombre_presentacion,
                            Collectors.groupingBy(clsBeTrans_re_det::getNombre_producto_estado,
                                    Collectors.groupingBy(clsBeTrans_re_det::getNombre_producto,
                                            Collectors.groupingBy(clsBeTrans_re_det::getcantidad_recibida)))));
                }

                employessGroup.size();


            }else{

            }

            if (pListTransRecDet!=null){
                if (pListTransRecDet.items!=null){

                   for (clsBeTrans_re_det obj:pListTransRecDet.items){

                       vItem = new clsBeTrans_re_det();

                       vItem.Nombre_presentacion = obj.Nombre_presentacion;
                       vItem.Codigo_Producto = obj.Codigo_Producto;
                       vItem.Nombre_unidad_medida = obj.Nombre_unidad_medida;
                       vItem.cantidad_recibida = obj.cantidad_recibida;
                       vItem.IdProductoBodega = obj.IdProductoBodega;
                       vItem.Nombre_producto_estado = obj.Nombre_producto_estado;
                       vItem.Fecha_vence = du.convierteFechaMostar(obj.Fecha_vence);
                       vItem.Lic_plate = obj.Lic_plate;

                       BeListDetalleRec.add(vItem);

                   }
                    int count =BeListDetalleRec.size()-1;
                    btnRegs.setText("Regs: "+count);
                }
            }

            Collections.sort(BeListDetalleRec,new OrdenarItems());

            adapter=new list_adapt_detalle_rec_prod(this,BeListDetalleRec);
            listView.setAdapter(adapter);

        }catch (Exception e){
            mu.msgbox("Lista_Detalle_Rec:"+e.getMessage());
        }
    }

    public class OrdenarItems implements Comparator<clsBeTrans_re_det> {

        public int compare(clsBeTrans_re_det left,clsBeTrans_re_det rigth){
            return left.IdRecepcionDet-rigth.IdRecepcionDet;
            //return left.Nombre.compareTo(rigth.Nombre);
        }

    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){

            try{

                switch (ws.callback) {

                    case 1:
                        callMethod("Get_Producto_By_IdProductoBodega","IdProductoBodega",BeOcDet.IdProductoBodega);
                        break;
                    case 2:
                        callMethod("Get_Detalle_By_IdRecepcionDet_HH","pIdRecepcionEnc",gl.gIdRecepcionEnc,
                                "pIdProductoBodega",BeOcDet.IdProductoBodega,"pNoLinea",BeOcDet.No_Linea);
                        break;
                }

            }catch (Exception e){
                mu.msgbox(e.getClass()+"WebServiceHandler:"+e.getMessage());
            }

        }

    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {

        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {

                case 1:
                    processBeProducto();
                    break;
                case 2:
                    processDetRec();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "wsCallBack: " + e.getMessage());
        }

    }

    private void processBeProducto(){

        try {


            BeProducto = xobj.getresult(clsBeProducto.class,"Get_Producto_By_IdProductoBodega");

            execws(2);
            //Load();

        } catch (Exception e) {
            msgbox(" processBeProducto: " + e.getMessage());
        }

    }

    private void processDetRec(){

        try{

            pListTransRecDet = xobj.getresult(clsBeTrans_re_detList.class,"Get_Detalle_By_IdRecepcionDet_HH");

            if (pListTransRecDet!=null){
                if (pListTransRecDet.items!=null){
                    Lista_Detalle_Rec();
                }else{
                    doExit();
                }
            }

        }catch (Exception e){
            mu.msgbox("processDetRec:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void doExit(){
        try{

            //LimpiaValores();
            super.finish();
            gl.Carga_Producto_x_Pallet=false;
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    public void onBackPressed() {
        try{
            doExit();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

}