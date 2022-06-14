package com.dts.tom.Transacciones.Verificacion;

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
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.BePedidoDetVerif;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.CantReemplazar;
import static com.dts.tom.Transacciones.Verificacion.frm_verificacion_datos.gBeProducto;

public class frm_danado_verificacion extends PBase {

    private frm_danado_verificacion.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private boolean aplicarReemplazo = false;

    private Spinner cmbEstadoDanadoVeri;
    private TextView lblProdDanadoVeri,lblUbicDestVeri,lblNomUbicVeri, lblTituloForma;
    private EditText txtUbicDestVeri;
    private Button btnGuardarDanadoVeri,btnBackVeri;

    private clsBeProducto_estadoList LProductoEstadoDanado = new clsBeProducto_estadoList();
    private clsBeBodega_ubicacion BeUbicDestino = new clsBeBodega_ubicacion();

    private ArrayList<String> EstadoList = new ArrayList<String>();

    public static int IdUbicacionDestino=0;
    public static int IdEstadoDanado = 0;
    public static String vNomUbicDestino="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_danado_verificacion);

        super.InitBase();

        cmbEstadoDanadoVeri = (Spinner)findViewById(R.id.cmbEstadoDanadoVeri);
        lblProdDanadoVeri = (TextView)findViewById(R.id.lblProdDanadoVeri);
        lblUbicDestVeri = (TextView)findViewById(R.id.lblUbicDestVeri);
        lblNomUbicVeri = (TextView) findViewById(R.id.lblNomUbicVeri);
        txtUbicDestVeri = (EditText) findViewById(R.id.txtUbicDestVeri);
        btnGuardarDanadoVeri = (Button)findViewById(R.id.btnGuardarDanadoVeri);
        btnBackVeri = (Button)findViewById(R.id.btnBackVeri);
        lblTituloForma = (TextView) findViewById(R.id.lblTituloForma);

        ws = new WebServiceHandler(frm_danado_verificacion.this, gl.wsurl);
        xobj = new XMLObject(ws);

        setHandlers();

        ProgressDialog("Cargando forma...");

        Load();

    }

    private void Load() {

        try {

            //Llama a método del WS Get_Estados_By_IdPropietario_And_IdBodega
            execws(1);

        } catch (Exception ex) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), ex.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + ex.getMessage());
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

    private void setHandlers(){

        try{

            cmbEstadoDanadoVeri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    IdEstadoDanado=LProductoEstadoDanado.items.get(position).IdEstado;
                    txtUbicDestVeri.setText(LProductoEstadoDanado.items.get(position).IdUbicacionBodegaDefecto+"");

                    aplicarReemplazo= false;

                    getNombreUbicacion(txtUbicDestVeri.getText().toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    return;
                }

            });

            txtUbicDestVeri.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        aplicarReemplazo = true;

                        getNombreUbicacion(txtUbicDestVeri.getText().toString());

                    }

                    return false;
                }
            });

            lblTituloForma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Load();
                }
            });

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }

    }

    private void reemplazaProducto(){

        try{

            browse=1;
            startActivity(new Intent(this, frm_list_prod_reemplazo_verif.class));

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            mu.msgbox( e.getMessage());
        }
    }

    private void getNombreUbicacion(String idUbicacion){
        try{

            if (!idUbicacion.isEmpty()){

                BeUbicDestino = new clsBeBodega_ubicacion();
                BeUbicDestino.IdUbicacion = Integer.parseInt(txtUbicDestVeri.getText().toString().trim());

                IdUbicacionDestino = BeUbicDestino.IdUbicacion;

                //Llama al método del WS Ubicacion_Valida_By_IdUbicacion_And_IdEstado
                execws(2);

            }

        }catch (Exception ex){

        }
    }

    public void Reemplazar(View view){
        try{

            aplicarReemplazo = true;

            getNombreUbicacion(txtUbicDestVeri.getText().toString());

        }catch (Exception ex){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
            mu.msgbox(new Object(){}.getClass().getEnclosingMethod().getName() + " " + ex.getMessage());
        }
    }

    private void Listar_Producto_Estado() {

        try {

            EstadoList.clear();

            LProductoEstadoDanado.items = stream(LProductoEstadoDanado.items).where(c->c.Danado==true).toList();

            for (int i = 0; i < LProductoEstadoDanado.items.size(); i++) {
                EstadoList.add(LProductoEstadoDanado.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EstadoList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cmbEstadoDanadoVeri.setAdapter(dataAdapter);

            if (EstadoList.size() > 0) cmbEstadoDanadoVeri.setSelection(0);


        } catch (Exception e) {
            mu.msgbox("Listar_Producto_Estado:" + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public class WebServiceHandler extends WebService {

        public WebServiceHandler(PBase Parent, String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try {
                switch (ws.callback) {
                    case 1:
                        callMethod("Get_Estados_By_IdPropietario_And_IdBodega",
                                   "pIdPropietario",gBeProducto.Propietario.IdPropietario,
                                   "pIdBod0.ega",gl.IdBodega);
                        break;
                    case 2:
                        callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado",
                                   "IdUbicacion",BeUbicDestino.IdUbicacion,
                                   "IdEstado",IdEstadoDanado,
                                   "IdBodega",gl.IdBodega,
                                   "pNombreUbicacion",BeUbicDestino.NombreCompleto);
                        break;
                    case 3:
                        callMethod("Ubicacion_Valida_By_IdUbicacion_And_IdEstado",
                                   "IdUbicacion",Integer.parseInt(txtUbicDestVeri.getText().toString()),
                                   "IdEstado",IdEstadoDanado,
                                   "IdBodega",gl.IdBodega,
                                   "pNombreUbicacion",vNomUbicDestino);
                        break;
                }

            }catch (Exception e){
                addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                error=e.getMessage();errorflag =true;
                msgbox(error);
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
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processEstadoProducto(){

        try{
            boolean existe = false;

            LProductoEstadoDanado = xobj.getresult(clsBeProducto_estadoList.class,"Get_Estados_By_IdPropietario_And_IdBodega");

            lblProdDanadoVeri.setText(BePedidoDetVerif.getCodigo()+" "+BePedidoDetVerif.getNombre_Producto()+
                    "\n Cad: "+BePedidoDetVerif.Fecha_Vence+
                    "\n Lote: "+BePedidoDetVerif.Lote+
                    "\n Reemplazar: "+CantReemplazar+" "+BePedidoDetVerif.getNom_Unid_Med());

            if (LProductoEstadoDanado != null) {
                if (LProductoEstadoDanado.items != null) {
                    Listar_Producto_Estado();
                    existe = true;
                }
            }

            if (!existe) {
                msgProdEstado("No se ha encontrado estados disponibles para el producto: "+
                        "\n"+BePedidoDetVerif.getCodigo()+" - "+BePedidoDetVerif.getNombre_Producto());
            }

        }catch (Exception e){
            mu.msgbox("processEstadoProducto:"+e.getMessage());
        }
    }

    private void processUbicacion(){

        try{

            progress.setMessage("Procesando ubicación destino...");

            BeUbicDestino.NombreCompleto = xobj.getresultSingle(String.class,"pNombreUbicacion");

            lblNomUbicVeri.setText(BeUbicDestino.NombreCompleto);

            if (IdEstadoDanado==0){
                mu.msgbox("Seleccione un estado de producto válido");
                cmbEstadoDanadoVeri.setFocusable(true);
                return;
            }

            if (aplicarReemplazo){
                //Llama al método del WS Ubicacion_Valida_By_IdUbicacion_And_IdEstado
                execws(3);
            }else{

                progress.cancel();

                txtUbicDestVeri.setSelectAllOnFocus(true);
                txtUbicDestVeri.requestFocus();
                txtUbicDestVeri.selectAll();

            }

        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processUbicacion:"+e.getMessage());
        }
    }

    private void processUbicacionValida(){

        try {

            progress.setMessage("Procesa ubicación destino válida...");
            progress.show();

            Boolean Valida=false;

            Valida = xobj.getresult(Boolean.class,"Ubicacion_Valida_By_IdUbicacion_And_IdEstado");

            progress.cancel();

            if (!Valida){
                mu.msgbox("La ubicación no es válida para el estado del producto!");
                txtUbicDestVeri.setSelectAllOnFocus(true);
                txtUbicDestVeri.requestFocus();
                return;
            }

            msgMover("Producto: "+gBeProducto.Nombre
                    + "\n Destino: "+lblNomUbicVeri.getText().toString()
                    + "\n Estado: "+ stream(LProductoEstadoDanado.items).where(c->c.IdEstado == IdEstadoDanado).select(c->c.Nombre).first()
                    + "\n ¿Mover?");


        }catch (Exception e){
            progress.cancel();
            mu.msgbox("processUbicacionValida:"+e.getMessage());
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
                    reemplazaProducto();
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

    private void msgAskExit(String msg) {
        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.cambioubic);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_danado_verificacion.super.finish();
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

    private void msgProdEstado(String msg) {
        try{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage(msg);

            dialog.setCancelable(false);

            dialog.setIcon(R.drawable.back);

            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    frm_danado_verificacion.super.finish();
                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Regresar(View view){
        msgAskExit("Está seguro de salir, no se guardará el producto para el reemplazo");
    }

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir, no se guardará el producto para el reemplazo");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    @Override
    protected void onResume() {

        try{

            super.onResume();

            if (browse==1){
                super.finish();
            }

        }catch (Exception e){
            mu.msgbox("OnResume"+e.getMessage());
        }

    }

}
