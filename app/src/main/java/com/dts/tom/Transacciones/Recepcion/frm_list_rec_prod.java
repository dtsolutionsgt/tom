package com.dts.tom.Transacciones.Recepcion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Configuracion_barra_pallet.clsBeConfiguracion_barra_pallet;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_oc.clsBeTrans_re_oc;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_rec;
import com.dts.classes.Transacciones.Stock.Stock_rec.clsBeStock_recList;
import com.dts.tom.PBase;
import com.dts.tom.R;
import com.dts.tom.list_adapt_detalle_recepcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class frm_list_rec_prod extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;

    private TextView lblNoDocumento;
    private Button btnRegs,btnCompletaRec;
    private ListView listView;
    private EditText txtCodigoProductoRecepcion;

    private clsBeTrans_oc_enc gBeOrdenCompra = new clsBeTrans_oc_enc();
    private clsBeTrans_re_detList pListTransRecDet;
    private  clsBeTrans_re_oc gBeReOC = new clsBeTrans_re_oc();
    private clsBeTrans_oc_detList pListDetalleOC = new clsBeTrans_oc_detList();
    private clsBeConfiguracion_barra_pallet gBeConfiguracionBarraPallet =  new clsBeConfiguracion_barra_pallet();
    private clsBeStock_recList pListBeStockRecPI = new clsBeStock_recList();
    private clsBeStock_rec gBeStockRec = new clsBeStock_rec();
    private static ArrayList<clsBeTrans_oc_det> BeListDetalleOC= new ArrayList<clsBeTrans_oc_det>() ;

    private boolean Escaneo_Pallet;
    private boolean Finalizada = false, Anulada = false;
    private double Cant_Recibida_Anterior;

    private clsBeTrans_oc_det selitem;

    private list_adapt_detalle_recepcion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_rec_prod);

        super.InitBase();

        ws = new WebServiceHandler(frm_list_rec_prod.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblNoDocumento = (TextView) findViewById(R.id.lblNoDocumento);
        btnRegs = (Button) findViewById(R.id.btnRegs);
        btnCompletaRec = (Button)findViewById(R.id.btnCompletaRec);
        listView = (ListView)findViewById(R.id.listRec);

        setHandlers();

        Load();

    }

    private void Load(){

        try{

            lblNoDocumento.setText("");

            if (gl.tipoTarea==1){

                if (gl.gBeRecepcion!=null){

                    pListTransRecDet = gl.gBeRecepcion.Detalle;

                    gBeOrdenCompra = new clsBeTrans_oc_enc();
                    gBeReOC = new clsBeTrans_re_oc();
                    pListDetalleOC =new clsBeTrans_oc_detList();


                    gBeOrdenCompra = gl.gBeRecepcion.OrdenCompraRec.OC;
                    gl.gBeOrdenCompra = gBeOrdenCompra;

                    gBeReOC = gl.gBeRecepcion.OrdenCompraRec;

                    pListDetalleOC = gl.gBeRecepcion.OrdenCompraRec.OC.DetalleOC;

                    gl.gpListDetalleOC = pListDetalleOC;

                    if(gBeOrdenCompra.No_Documento!=null & gBeOrdenCompra.Referencia!=null){
                        lblNoDocumento.setText("No. Documento:"+ gBeOrdenCompra.No_Documento+ " - "+gBeOrdenCompra.Referencia);
                    }

                    if(gBeOrdenCompra.IdEstadoOC != 3){

                        gBeOrdenCompra.EstadoOC.IdEstadoOC = 3;
                        gBeOrdenCompra.IdEstadoOC = 3;
                        gBeOrdenCompra.Fecha_Recepcion = du.getActDate()+"";
                        gBeOrdenCompra.Hora_Inicio_Recepcion =  du.getActDate()+"";
                        gBeOrdenCompra.User_Mod = gl.IdOperador+"";
                        gBeOrdenCompra.Fec_Mod =  du.getActDate()+"";


                    }

                    execws(1);
                    execws(2);
                    execws(3);

                    Lista_Detalle_OC();

                    Recepcion_Completa();

                }

            }else{
                mu.msgbox("Falta programar");
            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }

    }


    private void setHandlers() {

        try{


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    Object lvObj = listView.getItemAtPosition(position);
                    clsBeTrans_oc_det sitem = (clsBeTrans_oc_det) lvObj;
                    selitem = sitem;

                    selid = sitem.No_Linea;
                    selidx = position;
                    adapter.setSelectedIndex(position);

                    procesar_registro();

                }

            });


        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private void doExit(){
        try{

            //LimpiaValores();
            super.finish();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void procesar_registro(){

        try {


            if (Finalizada & Anulada){
                doExit();
            }else{

                gl.gEscaneo_Pallet = Escaneo_Pallet;
                gl.gselitem = selitem;

                gl.CodigoRecepcion = selitem.Producto.Codigo_barra;

                startActivity(new Intent(this, frm_recepcion_datos.class));

            }



        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

    }

    private boolean Recepcion_Completa(){

        double vTipoDiferencia;
        double Cantidad_recibida,Cantidad;

        try{

            if (gBeOrdenCompra.DetalleOC.items!=null){

                for (int i = gBeOrdenCompra.DetalleOC.items.size()-1; i>=0; i--) {

                    Cantidad_recibida = gBeOrdenCompra.DetalleOC.items.get(i).Cantidad_recibida;
                    Cantidad = gBeOrdenCompra.DetalleOC.items.get(i).Cantidad;

                    if (Cantidad_recibida>0){

                        vTipoDiferencia = mu.round(Cantidad_recibida-Cantidad,7);

                        if (vTipoDiferencia>0){

                            btnCompletaRec.setText("DIF - (NEG)");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#ffcc0000"));

                            return false;

                        }else if(vTipoDiferencia<0){

                            btnCompletaRec.setText(" DIF - (POS)");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FF0399D5"));

                            return false;

                        }else if (vTipoDiferencia==0){

                            btnCompletaRec.setText("COMPLETA");
                            btnCompletaRec.setBackgroundColor(Color.parseColor("#FF99CC00"));

                            return true;
                        }

                    }else{

                        btnCompletaRec.setText("DIF - (NEG)");
                        btnCompletaRec.setBackgroundColor(Color.parseColor("#ffcc0000"));

                    }

                }

            }

        }catch (Exception e){
            mu.msgbox(e.getClass()+" "+ e.getMessage());
        }

        return  true;

    }

    private void Lista_Detalle_OC(){

        clsBeTrans_oc_det vItem;
        BeListDetalleOC.clear();

        try{

            if(pListDetalleOC.items!=null){

                vItem = new clsBeTrans_oc_det();

                BeListDetalleOC.add(vItem);

                for (int i = pListDetalleOC.items.size()-1; i>=0; i--) {

                    vItem = new clsBeTrans_oc_det();

                    vItem.No_Linea = pListDetalleOC.items.get(i).No_Linea;
                    vItem.Producto.Codigo = pListDetalleOC.items.get(i).Producto.Codigo;
                    vItem.Producto.Nombre = pListDetalleOC.items.get(i).Producto.Nombre;
                    vItem.Presentacion.Nombre = pListDetalleOC.items.get(i).Presentacion.Nombre;
                    vItem.UnidadMedida.Nombre = pListDetalleOC.items.get(i).UnidadMedida.Nombre;
                    vItem.Cantidad = pListDetalleOC.items.get(i).Cantidad;
                    vItem.Cantidad_recibida = pListDetalleOC.items.get(i).Cantidad_recibida;
                    vItem.Costo = pListDetalleOC.items.get(i).Costo;
                    vItem.FactorPresentacion = pListDetalleOC.items.get(i).FactorPresentacion;
                    vItem.IdOrdenCompraDet = pListDetalleOC.items.get(i).IdOrdenCompraDet;
                    vItem.IdOrdenCompraEnc = pListDetalleOC.items.get(i).IdOrdenCompraEnc;

                    BeListDetalleOC.add(vItem);

                }

                btnRegs.setText("Regs: "+pListDetalleOC.items.size());
            }

            Collections.sort(BeListDetalleOC,new OrdenarItems());

            adapter=new list_adapt_detalle_recepcion(this,BeListDetalleOC);
            listView.setAdapter(adapter);

        }catch (Exception e){
            mu.msgbox(e.getClass()+e.getMessage());
        }

    }

    public class OrdenarItems implements Comparator<clsBeTrans_oc_det> {

        public int compare(clsBeTrans_oc_det left,clsBeTrans_oc_det rigth){
            return left.No_Linea-rigth.No_Linea;
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
                        callMethod("Iniciar_Recepcion_OC","oBeTrans_oc_enc",gBeOrdenCompra);
                        break;
                    case  2:
                        callMethod("Actualizar_Estado_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"Estado","Pendiente");
                        break;
                    case 3:
                        callMethod("Get_Single_BeTrans_OC_Estado","pBeTrans_oc_estado",gBeOrdenCompra.EstadoOC);
                        break;
                    case 4:
                        callMethod("Get_Banderas_Recepcion","pIdRecepcionEnc",gl.gIdRecepcionEnc,"pFinalizada",Finalizada,"pAnulada",Anulada);
                        break;

                }

            }catch (Exception e){
                mu.msgbox(e.getClass()+e.getMessage());
            }

        }

    }

        @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {

            try {
                if (throwing) throw new Exception(errmsg);

                switch (ws.callback) {
                    case 4:
                        processBanderasRecep();
                        break;
                }

            } catch (Exception e) {
                msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
            }

    }

    private void processBanderasRecep(){

        try {

            Finalizada =(Boolean) xobj.getSingle("pFinalizada",Boolean.class);
            Anulada =(Boolean) xobj.getSingle("pAnulada",Boolean.class);

            if (Finalizada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " ya fue finalizada");
            }

            if (Anulada){
                mu.msgbox("La recepción "+ gl.gIdRecepcionEnc + " fue anulada");
            }

            if (Finalizada & Anulada){
                super.finish();
            }



        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
