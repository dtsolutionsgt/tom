package com.dts.tom.Transacciones.Picking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;

import static br.com.zbra.androidlinq.Linq.stream;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBePickingUbic;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.gBeProducto;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.IdUbicacionPicking;
import static com.dts.tom.Transacciones.Picking.frm_picking_datos.NombreUbicacionPicking;

public class frm_danado_picking extends PBase {

    private Spinner cmbEstadoDanado;
    private TextView lblProdDanado,lblNomUbic;
    private EditText txtUbicDest;
    private Button btnGuardarDanado,btnBack;
    private ProgressDialog progress;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeProducto_estadoList LProductoEstadoDanado = new clsBeProducto_estadoList();
    private clsBeBodega_ubicacion BeUbicDestino = new clsBeBodega_ubicacion();

    public static int IdUbicacionDestino=0;

    private final ArrayList<String> EstadoList = new ArrayList<String>();

    public static int IdEstadoDanadoSelect = 0;
    public static String vNomUbicDestino="";
    public static boolean existe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_danado_picking);
        super.InitBase();

        cmbEstadoDanado = findViewById(R.id.cmbEstadoDanado);
        lblProdDanado = findViewById(R.id.lblProdDanado);
        lblNomUbic = findViewById(R.id.lblNomUbic);
        txtUbicDest = findViewById(R.id.txtUbicDest);
        btnGuardarDanado = findViewById(R.id.btnGuardarDanado);
        btnBack = findViewById(R.id.btnBack);

        ws = new WebServiceHandler(frm_danado_picking.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandles();

        execws(1);
    }

    private void setHandles(){

        try{

            cmbEstadoDanado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoDanadoSelect=LProductoEstadoDanado.items.get(position).IdEstado;
                    txtUbicDest.setText(LProductoEstadoDanado.items.get(position).IdUbicacionBodegaDefecto+"");

                    if (txtUbicDest.getText().toString().isEmpty() || txtUbicDest.getText().toString().equals("0")) {
                        txtUbicDest.setText(IdUbicacionPicking+"");
                        lblNomUbic.setText(NombreUbicacionPicking);
                    }

                    /*BeUbicDestino = new clsBeBodega_ubicacion();
                    BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDest.getText().toString().trim());
                    IdUbicacionDestino = BeUbicDestino.IdUbicacion;
                    execws(2);*/

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            txtUbicDest.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!txtUbicDest.getText().toString().isEmpty()){

                            validaUbicacion();

                            BeUbicDestino = new clsBeBodega_ubicacion();
                            BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDest.getText().toString().trim());
                            IdUbicacionDestino = BeUbicDestino.IdUbicacion;

                            if (existe) {
                                execws(2);
                            } else {
                                 msgMover("Producto: "+gBeProducto.Nombre
                                    + "\n Destino: "+txtUbicDest.getText().toString()
                                    + "\n Estado: "+ stream(LProductoEstadoDanado.items).where(c->c.IdEstado == IdEstadoDanadoSelect).select(c->c.Nombre).first()
                                    + "\n ¿Mover?");
                            }

                        }
                    }

                    return false;
                }
            });

        }catch (Exception e){
            mu.msgbox("setHandles:"+e.getMessage());
        }
    }

    public void validaUbicacion() {
        existe = false;
        for(int i = 0; i < LProductoEstadoDanado.items.size(); i++) {
            if (LProductoEstadoDanado.items.get(i).IdUbicacionBodegaDefecto == Integer.parseInt(txtUbicDest.getText().toString().trim())) {
                existe = true;
                break;
            }
        }
    }

    public void BotonGuardarDanado(View view){

        if (!txtUbicDest.getText().toString().isEmpty()){
            /*BeUbicDestino = new clsBeBodega_ubicacion();
            BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDest.getText().toString().trim());
            IdUbicacionDestino = BeUbicDestino.IdUbicacion;
            execws(2);*/

            validaUbicacion();

            String destino = "";
            BeUbicDestino = new clsBeBodega_ubicacion();

            if (existe) {
                destino = lblNomUbic.getText().toString();
                BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDest.getText().toString().trim());
                IdUbicacionDestino = BeUbicDestino.IdUbicacion;

                execws(2);

            } else {
                destino = NombreUbicacionPicking;//txtUbicDest.getText().toString();
                BeUbicDestino.IdUbicacion = IdUbicacionPicking;
                IdUbicacionDestino = BeUbicDestino.IdUbicacion;

                msgMover("Producto: "+gBeProducto.Nombre
                        + "\n Destino: "+ BeUbicDestino.NombreCompleto
                        + "\n Estado: "+ stream(LProductoEstadoDanado.items).where(c->c.IdEstado == IdEstadoDanadoSelect).select(c->c.Nombre).first()
                        + "\n ¿Mover?");
            }
        }
    }

    public void botonAtras(View view){
        regresar();
    }

    private void Listar_Producto_Estado() {

        try {

            EstadoList.clear();

            //#EJC20220701: Por defecto la lista trae todos los estado y aquí solo se dejan los dañados
            //Si el paramtro permitir buen estado está en true, entonces no se filtrarán unicamente los que están dañados.
            if(!gl.Permitir_Buen_Estado_En_Reemplazo){
                LProductoEstadoDanado.items = stream(LProductoEstadoDanado.items).where(c->c.Danado==true).toList();
            }

            for (int i = 0; i < LProductoEstadoDanado.items.size(); i++) {
                EstadoList.add(LProductoEstadoDanado.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDanado.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoDanado.setSelection(0);

        } catch (Exception e) {
            mu.msgbox("Listar_Producto_Estado:" + e.getMessage());
        }
    }

    private void Valida_ubicacion(){

        try{

            browse=1;
            startActivity(new Intent(this, frm_list_prod_reemplazo_picking.class));

        }catch (Exception e){
            mu.msgbox("Guardar_dañado");
        }
    }

    private void msgMover(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.ic_quest);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Valida_ubicacion();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgProdEstado(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.back);

            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    regresar();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
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
                        /*callMethod("Get_Estados_By_IdPropietario_And_IdBodega",
                                "pIdPropietario",gBeProducto.Propietario.IdPropietario,
                                "pIdBodega",gl.IdBodega);*/
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodegaHH",
                                "pIdPropietario",gBeProducto.Propietario.IdPropietario,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado",
                                        "IdUbicacion",BeUbicDestino.IdUbicacion,
                                              "IdEstado",IdEstadoDanadoSelect,
                                              "IdBodega",gl.IdBodega,"pNombreUbicacion",BeUbicDestino.NombreCompleto);
                        break;
                    case 3:
                        callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado",
                                "IdUbicacion",Integer.parseInt(txtUbicDest.getText().toString()),
                                "IdEstado",IdEstadoDanadoSelect,
                                "IdBodega",gl.IdBodega,
                                "pNombreUbicacion",vNomUbicDestino);
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
                    processEstadoProducto();
                    break;
                case 2:
                    processUbicacion();
                    break;
                case 3:
                    processUbicacionValida();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processEstadoProducto(){

        try{
            boolean existe = false;

            LProductoEstadoDanado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodegaHH");

            lblProdDanado.setText(gBePickingUbic.CodigoProducto+" "+gBePickingUbic.NombreProducto+
                    "\n Cad: "+gBePickingUbic.Fecha_Vence+
                    "\n Lote: "+gBePickingUbic.Lote+
                    "\n Reemplazar: "+CantReemplazar+" "+gBePickingUbic.ProductoUnidadMedida);

            txtUbicDest.setSelectAllOnFocus(true);
            txtUbicDest.requestFocus();
            txtUbicDest.selectAll();

            //#AT 20220126 Se valida  que LProductoEstadoDanado no sea nulo de ser asi mostrará un mensaje
            if (LProductoEstadoDanado != null) {
                if (LProductoEstadoDanado.items != null) {
                    Listar_Producto_Estado();
                    existe = true;
                }
            }

            if (!existe) {
                msgProdEstado("No se ha encontrado estados disponibles para el producto: "+
                        "\n"+gBePickingUbic.CodigoProducto+" - "+gBePickingUbic.NombreProducto);
            }

        }catch (Exception e){
            mu.msgbox("processEstadoProducto:"+e.getMessage());
        }
    }

    private void processUbicacion(){

        try{

            BeUbicDestino.NombreCompleto = xobj.getresultSingle(String.class,"pNombreUbicacion");

            lblNomUbic.setText(BeUbicDestino.NombreCompleto);

            if (IdEstadoDanadoSelect==0){
                mu.msgbox("Seleccione un estado de producto válido");
                cmbEstadoDanado.setFocusable(true);
                return;
            }

            execws(3);

        }catch (Exception e){
            mu.msgbox("processUbicacion:"+e.getMessage());
        }
    }

    private void processUbicacionValida(){

        try {

            Boolean Valida=false;

            Valida = xobj.getresult(Boolean.class,"Ubicacion_Valida_By_IdUbicacion_And_IdEstado");

            if (!Valida){
                mu.msgbox("La ubicación no es válida para el estado del producto!");
                txtUbicDest.setSelectAllOnFocus(true);
                txtUbicDest.requestFocus();
                return;
            }

            BeUbicDestino = new clsBeBodega_ubicacion();
            BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDest.getText().toString().trim());
            IdUbicacionDestino = BeUbicDestino.IdUbicacion;

            msgMover("Producto: "+gBeProducto.Nombre
                    + "\n Destino: "+ BeUbicDestino.NombreCompleto
                    + "\n Estado: "+ stream(LProductoEstadoDanado.items).where(c->c.IdEstado == IdEstadoDanadoSelect).select(c->c.Nombre).first()
                    + "\n ¿Mover?");

        }catch (Exception e){
            mu.msgbox("processUbicacionValida:"+e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    private void regresar() {
        super.finish();
        startActivity(new Intent(this, frm_picking_datos.class));
    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                browse=0;
                super.finish();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        regresar();
    }
}
