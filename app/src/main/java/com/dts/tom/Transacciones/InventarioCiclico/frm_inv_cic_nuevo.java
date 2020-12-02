package com.dts.tom.Transacciones.InventarioCiclico;

import androidx.appcompat.app.AppCompatActivity;
import com.dts.base.WebService;
import com.dts.base.XMLObject;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.dts.base.XMLObject;
import com.dts.tom.PBase;
import com.dts.tom.R;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_nuevo extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;
    private Cursor DT;
    private Cursor ctableFamilia,ctableClasi,ctableMarca,ctableTipo,ctableUMB;

    private ProgressDialog progress;

    private EditText etcodigo,etdescripcion;
    private Spinner cmbFamilia_nv,cmbClasificacion_nv,cmbMarca_nv,cmbTipo_nv,cmbUmbas_nv;
    private CheckBox chkControlVence,chkControlLote;
    private Button cmdAddProd;
    boolean chkcvence, chkclote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_inv_cic_nuevo);
        super.InitBase();

        etcodigo = findViewById(R.id.etcodigo);
        etdescripcion = findViewById(R.id.etdescripcion);
        cmbFamilia_nv = findViewById(R.id.cmbFamilia_nv);
        cmbClasificacion_nv = findViewById(R.id.cmbClasificacion_nv);
        cmbMarca_nv = findViewById(R.id.cmbMarca_nv);
        cmbTipo_nv = findViewById(R.id.cmbTipo_nv);
        cmbUmbas_nv = findViewById(R.id.cmbUmbas_nv);
        chkControlVence = findViewById(R.id.chkControlVence);
        chkControlLote = findViewById(R.id.chkControlLote);
        cmdAddProd = findViewById(R.id.cmdAddProd);

        chkcvence = false;
        chkclote = false;
        chkControlVence.setChecked(false);
        chkControlLote.setChecked(false);
        etdescripcion.requestFocus();

        ws = new WebServiceHandler(frm_inv_cic_nuevo.this,gl.wsurl);
        xobj = new XMLObject(ws);

        ProgressDialog("Cargando parametros.");

        Load();

    }

    private void Load() {

        try{

            etcodigo.setText(gl.nuevo_producto_cic + "");
            etcodigo.setEnabled(false);

            execws(1);

        }catch (Exception e){
            mu.msgbox("Load:"+e.getMessage());
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
                        callMethod("Get_Datos_Maestros_Inv","pIdenc",BeInvEnc.Idpropietario,
                                "pListFamilia",ctableFamilia,"pListClasificacion",ctableClasi,"pListMarca",ctableMarca,"pListTipo",ctableTipo,"pListUMBas",ctableUMB);
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
                    Llena_Combos_Producto_Nuevo();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Llena_Combos_Producto_Nuevo() {

        try {

            //String respuesta = String.valueOf(xobj.getSingle("Get_Datos_Maestros_Inv",String.class));



           /* ctableClasi = xobj.filldt(Boolean.class,"ctableClasi");
            ctableMarca = xobj.filldt(Boolean.class,"ctableMarca");
            ctableTipo= xobj.filldt(Boolean.class,"ctableTipo");
            ctableUMB = xobj.filldt(Boolean.class,"ctableUMB");*/


            DT = xobj.filldt();

            if(DT !=null) {

                if (DT.getCount() > 0) {

                }

            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("inv_cic_nuevo:"+e.getMessage());
        }

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void ProgressDialog(String mensaje) {
        progress = new ProgressDialog(this);
        progress.setMessage(mensaje);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

}