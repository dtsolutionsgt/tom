package com.dts.tom.Transacciones.Picking;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStockList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_resList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.List;

import static com.dts.tom.Transacciones.Picking.frm_picking_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBePickingUbic;

public class frm_list_prod_reemplazo_picking extends PBase {

    private TextView lblTituloForma,lblCantRegs;
    private ListView listDispProd;
    private Button btnActualizaPickingDet,btnBack;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeStockList vListaStock = new clsBeStockList();
    private clsBeStockList vStock = new clsBeStockList();
    private clsBeStock_resList  BeListStockRes = new clsBeStock_resList();
    private clsBeStock_res StockResC=new clsBeStock_res();


    private double vCant=0;

    private Cursor DT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_prod_reemplazo_picking);
        super.InitBase();

        ws = new WebServiceHandler(frm_list_prod_reemplazo_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = (TextView)findViewById(R.id.lblTituloForma);
        lblCantRegs = (TextView)findViewById(R.id.lblCantRegs);

        listDispProd = (ListView)findViewById(R.id.listDispProd);

        btnActualizaPickingDet = (Button)findViewById(R.id.btnActualizaPickingDet);
        btnBack = (Button)findViewById(R.id.btnBack);

        execws(1);

    }

    private void Load(){

        try{

            lblTituloForma.setText("Picking List No: "+ gBePickingUbic.IdPickingEnc);

            for (clsBeStock_res StockRes:BeListStockRes.items){

                StockResC=new clsBeStock_res();

                StockResC = StockRes;

                execws(2);

            }

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
        }
    }

    private void Lista_Inventario_Disponible(){

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
                        callMethod("Get_All_Stock_Res_By_IdPedidoDet","pIdPedidoDet",gBePickingUbic.IdPedidoDet);
                        break;
                    case 2:
                        callMethod("Get_All_Stock_Especifico_HH","IdBodega",gl.IdBodega,"IdPedidoEnc",gBePickingUbic.IdPedidoEnc,
                                "pStockRes",StockResC);
                        return;
                    case 3:
                        //callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado","IdUbicacion",Integer.parseInt(txtUbicDest.getText().toString()),
                          //      "IdEstado",IdEstadoDanadoSelect,"IdBodega",gl.IdBodega,"pNombreUbicacion",vNomUbicDestino);
                        break;
                }

            } catch (Exception e) {
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
                    processListStockRes();
                    break;
                case 2:
                    processGetAllStock();
                    break;
                case 3:
                    //processUbicacionValida();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void  processListStockRes(){

        try{

            BeListStockRes = xobj.getresult(clsBeStock_resList.class,"Get_All_Stock_Res_By_IdPedidoDet");

            if (BeListStockRes!=null){
                if (BeListStockRes.items!=null){
                    Load();
                }
            }

        }catch (Exception e){
            mu.msgbox("processListStockRes:"+e.getMessage());
        }
    }

    private void processGetAllStock(){

        try{

            DT = xobj.filldt();

            if (DT.getCount()>0) {

                DT.moveToFirst();

                while (!DT.isAfterLast()) {

                    vCant += DT.getDouble(22);

                    if ( vCant >=CantReemplazar && vCant>0){
                        Lista_Inventario_Disponible();
                        break;
                    }

                    DT.moveToNext();
                }
            }

        }catch (Exception e){
            mu.msgbox("processGetAllStock:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

}
