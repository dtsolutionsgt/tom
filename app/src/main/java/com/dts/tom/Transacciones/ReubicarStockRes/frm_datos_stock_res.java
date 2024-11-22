package com.dts.tom.Transacciones.ReubicarStockRes;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.dts.base.ExDialog;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.ladapt.list_adapt_lista_productos_recubic;
import com.dts.tom.PBase;
import com.dts.tom.R;

import static com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res.selitem;
import static com.dts.tom.Transacciones.ReubicarStockRes.frm_lista_stock_res.lista;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class frm_datos_stock_res extends PBase {

    private frm_datos_stock_res.WebServiceHandler ws;
    private XMLObject xobj;

    private TextView lblTituloForma, lblNomDestino, lbCant, lblCantidad,
                     txtUnidadMedida, txtPresentacion, txtPropietario, txtLote, txtVence, txtEstado, txtDescProd;
    private EditText txtUbicOrigen, txtLicensePlate, txtCodigoPrd, txtCantidad, txtUbicDestino;
    private TableRow trDestino, trPresentacion, trLote, trVence, trProducto, trCodigo, trEstado, trCantidad, tblLicenciaMixta;
    private ListView listProductos;
    private RecyclerView recyclerView;

    private int IdUbicacion = 0;
    private final int IdUbicacionDest = 0;
    private int CambioUbicRealizado = 0;
    private boolean UbicValida = false;
    private list_adapt_lista_productos_recubic adapter;
    private ArrayList<clsBeVW_stock_res> filtrada = new ArrayList<>();

    private clsBeBodega_ubicacion BeUbic = new clsBeBodega_ubicacion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_datos_stock_res);

        super.InitBase();

        ws = new frm_datos_stock_res.WebServiceHandler(frm_datos_stock_res.this, gl.wsurl);
        xobj = new XMLObject(ws);

        lblTituloForma = findViewById(R.id.lblTituloForma);
        lblNomDestino = findViewById(R.id.lblNomDestino);
        lbCant = findViewById(R.id.lbCant);
        lblCantidad = findViewById(R.id.lblCantidad);

        txtUbicOrigen  = findViewById(R.id.txtUbicOrigen);
        txtLicensePlate = findViewById(R.id.txtLicensePlate);
        txtCodigoPrd = findViewById(R.id.txtCodigoPrd);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtUbicDestino = findViewById(R.id.txtUbicDestino);
        txtUnidadMedida = findViewById(R.id.txtUnidadMedida);
        txtPresentacion = findViewById(R.id.txtPresentacion);
        txtPropietario = findViewById(R.id.txtPropietario);
        txtLote = findViewById(R.id.txtLote);
        txtVence = findViewById(R.id.txtVence);
        txtEstado = findViewById(R.id.txtEstado);
        txtDescProd = findViewById(R.id.txtDescProd);

        trDestino = findViewById(R.id.trDestino);
        trPresentacion = findViewById(R.id.trPresentacion);
        trLote = findViewById(R.id.trLote);
        trVence = findViewById(R.id.trVence);
        trProducto = findViewById(R.id.trProducto);
        trCodigo = findViewById(R.id.trCodigo);
        trEstado = findViewById(R.id.trEstado);
        trCantidad = findViewById(R.id.trCantidad);
        tblLicenciaMixta = findViewById(R.id.tblLicenciaMixta);

        recyclerView = findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UbicValida = false;

        if (gl.pBeBodega.Control_Pallet_Mixto) {
            LoadMixto();
        } else {
            Load();
        }

        setHandlers();
    }

    private void setHandlers() {
        try {
            txtUbicDestino.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    execws(1);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtUbicOrigen.setOnKeyListener((v, keyCode, event) -> {
                try {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {

                        switch (keyCode) {

                            case KeyEvent.KEYCODE_ENTER:
                                ValidaUbicacionOrigen();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });

            txtLicensePlate.setOnKeyListener((v, keyCode, event) -> {
                try {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        ValidaLicencia();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });

            txtCodigoPrd.setOnKeyListener((v, keyCode, event) -> {
                try {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {

                        switch (keyCode) {

                            case KeyEvent.KEYCODE_ENTER:
                                ValidaCodigoPrd();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });

            txtUbicDestino.setOnClickListener(v -> { });
            txtCantidad.setOnClickListener(v -> { });

        } catch (Exception e) {
            mu.msgbox("setHandlers: "+e.getMessage());
        }
    }

    private void Load() {
        try {
            lblTituloForma.setText(selitem.Ubicacion_Nombre);
            txtUbicOrigen.setHint(""+selitem.IdUbicacion);
            txtLicensePlate.setHint(selitem.Lic_plate);
            txtCodigoPrd.setHint(selitem.Codigo_Producto);
            tblLicenciaMixta.setVisibility(View.GONE);

            if (selitem.IdPresentacion > 0) {
                lblCantidad.setText("Cantidad ("+selitem.Nombre_Presentacion+"): ");
                txtCantidad.setText(""+selitem.CantidadPresentacion);
                lbCant.setText(""+selitem.CantidadPresentacion);
            } else {
                lblCantidad.setText("Cantidad ("+selitem.UMBas+"): ");
                txtCantidad.setText(""+selitem.CantidadReservadaUMBas);
                lbCant.setText(""+selitem.CantidadReservadaUMBas);
            }

            if (selitem.Nombre_Presentacion.isEmpty()) {
                trPresentacion.setVisibility(View.GONE);
            } else {
                txtPresentacion.setText(selitem.Nombre_Presentacion);
            }

            txtPropietario.setText(selitem.Propietario);

            if (selitem.Lote.isEmpty()) {
                trLote.setVisibility(View.GONE);
            } else {
                txtLote.setText(selitem.Lote);
            }

            txtEstado.setText(selitem.NomEstado);

            if (selitem.Fecha_Vence.equals("01-01-1900")) {
                trVence.setVisibility(View.GONE);
            } else {
                txtVence.setText(selitem.Fecha_Vence);
            }

            trProducto.setVisibility(View.GONE);

        } catch (Exception e) {
            mu.msgbox("Load: "+e.getMessage());
        }
    }

    private void LoadMixto() {
        try {
            trCodigo.setVisibility(View.GONE);
            trLote.setVisibility(View.GONE);
            trVence.setVisibility(View.GONE);
            trEstado.setVisibility(View.GONE);
            trCantidad.setVisibility(View.GONE);
            trProducto.setVisibility(View.GONE);

            lblTituloForma.setText(selitem.Ubicacion_Nombre);
            txtUbicOrigen.setHint(""+selitem.IdUbicacion);
            txtLicensePlate.setHint(selitem.Lic_plate);
            txtPropietario.setText(selitem.Propietario);

            filtrada = lista.items.stream()
                    .filter(item -> selitem.Lic_plate.equals(item.Lic_plate) && item.IdUbicacion == selitem.IdUbicacion) // Filtra por Lic_plate e IdUbicacion
                    .collect(Collectors.toCollection(ArrayList::new));

            adapter = new list_adapt_lista_productos_recubic(getApplicationContext(), filtrada);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            mu.msgbox("LoadMixto: "+e.getMessage());
        }
    }

    private void ValidaUbicacionOrigen() {
        int UbicOrigen = 0;
        try {

            if (!txtUbicOrigen.getText().toString().isEmpty()) {
                UbicOrigen = Integer.valueOf(txtUbicOrigen.getText().toString());

                if (UbicOrigen != selitem.IdUbicacion) {
                    mu.msgbox("La ubicación ingresada " + UbicOrigen + " no coincide con: " + selitem.IdUbicacion);
                    txtUbicOrigen.requestFocus();
                    txtUbicOrigen.selectAll();
                    txtUbicOrigen.setText("");
                    UbicOrigen = 0;
                } else {
                    //txtLicensePlate.requestFocus();
                }
            } else {
                txtUbicOrigen.requestFocus();
                txtUbicOrigen.selectAll();
                txtUbicOrigen.setText("");
                UbicOrigen = 0;
                mu.msgbox("Ingrese ubicación de origen.");
            }
        } catch (Exception e) {
            mu.msgbox("ValidaUbicacionOrigen: "+e.getMessage());
        }
    }

    //#AT20221125 Si Mostrar_Area_En_HH = true se carga el codigo del producto
    private void setCodigoProd() {
        try {
            if (!gl.Mostrar_Area_En_HH) {
                txtCodigoPrd.setText(selitem.Codigo_Producto);
                trProducto.setVisibility(View.VISIBLE);
                txtDescProd.setText(selitem.Codigo_Producto+" - "+selitem.Nombre_Producto);
                txtUbicDestino.requestFocus();
            } else {
                txtCodigoPrd.requestFocus();
            }
        } catch (Exception e) {
            mu.msgbox(new Object() {} .getClass().getEnclosingMethod().getName() +" - "+ e.getMessage());
        }
    }

    private void ValidaLicencia() {
        String Lic = "";
        try {

            if (!txtLicensePlate.getText().toString().isEmpty()) {
                //#CKFK20220923 Agregué este replace para cuando escanen la barra
                Lic = txtLicensePlate.getText().toString().replace("$","");

                if (!Lic.equals(selitem.Lic_plate)) {
                    txtLicensePlate.requestFocus();
                    txtLicensePlate.selectAll();
                    txtLicensePlate.setText("");
                    mu.msgbox("La licencia ingresada " + Lic + " no coincide con: " + selitem.Lic_plate);
                    Lic= "";
                } else {
                    if (gl.pBeBodega.Control_Pallet_Mixto) {
                        toast("Licencia válida");
                        txtUbicDestino.requestFocus();
                    } else {
                        setCodigoProd();
                    }
                }
            } else {
                txtLicensePlate.requestFocus();
                txtLicensePlate.selectAll();
                txtLicensePlate.setText("");
                Lic= "";
                mu.msgbox("Ingrese licencia.");
            }
        } catch (Exception e) {
            mu.msgbox("ValidaLicencia: "+e.getMessage());
        }
    }

    private void ValidaCodigoPrd() {
        String Codigo = "";
        try {

            if (!txtCodigoPrd.getText().toString().isEmpty()) {
                Codigo = txtCodigoPrd.getText().toString();

                if (!Codigo.equals(selitem.Codigo_Producto)) {
                    mu.msgbox("El código ingresado " + Codigo + " no coincide con: " + selitem.Codigo_Producto);
                    txtCodigoPrd.requestFocus();
                    txtCodigoPrd.selectAll();
                } else {
                    trProducto.setVisibility(View.VISIBLE);
                    txtDescProd.setText(selitem.Codigo_Producto+" - "+selitem.Nombre_Producto);
                    txtUbicDestino.requestFocus();
                }
            } else {
                txtCodigoPrd.requestFocus();
                mu.msgbox("Ingrese licencia.");
            }
        } catch (Exception e) {
            mu.msgbox("ValidaLicencia: "+e.getMessage());
        }
    }

    private void processValidaUbic() {
        try {
            BeUbic = xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            if (BeUbic != null) {
                if ((BeUbic.Nivel <= selitem.Ubicacion_Nivel || BeUbic.Ubicacion_picking)
                        && BeUbic.IdUbicacion != selitem.IdUbicacion){

                    trDestino.setVisibility(View.VISIBLE);
                    lblNomDestino.setText(BeUbic.Descripcion);
                    txtCantidad.requestFocus();
                    txtCantidad.setSelectAllOnFocus(true);
                    UbicValida = true;

                    ActualizaUbicacion();

                } else {
                    txtUbicDestino.requestFocus();
                    txtUbicDestino.selectAll();
                    lblNomDestino.setText("");
                    toast("Ubicación no válida");
                }
            } else {
                txtUbicDestino.requestFocus();
                txtUbicDestino.selectAll();
                lblNomDestino.setText("");
                toast("Ubicación no válida");
            }
        } catch (Exception e) {
            mu.msgbox("processValidaUbic: "+ e.getMessage());
        }
    }

    private void processActualizaUbic() {
        try {

            CambioUbicRealizado = (Integer) xobj.getSingle("Actualizar_Ubicaciones_Reservadas_By_IdStockResult",int.class);

            if (CambioUbicRealizado > 0) {
                msgExito("Cambio aplicado correctamente.");
            } else {
                msgbox("No se pudo completar el cambio.");
            }

        } catch (Exception e) {
            mu.msgbox("processActualizaUbic: "+e.getMessage());
        }
    }

    private void processActualizaUbicPalletMixto() {
        try {

            CambioUbicRealizado = (Integer) xobj.getSingle("Actualizar_Ubicaciones_Reservadas_Pallet_MixtoResult",int.class);

            if (CambioUbicRealizado > 0) {
                msgExito("Cambio aplicado correctamente.");
            } else {
                msgbox("No se pudo completar el cambio.");
            }

        } catch (Exception e) {
            mu.msgbox("processActualizaUbic: "+e.getMessage());
        }
    }

    private boolean ValidaDatos() {
        boolean permite = true;

        try {
            if (txtUbicOrigen.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese ubicación de origen.");
                return false;
            } else if (Integer.valueOf(txtUbicOrigen.getText().toString()) != selitem.IdUbicacion) {
                mu.msgbox("La ubicación origen no coincide.");
                return false;
            }

            if (txtLicensePlate.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese licencia.");
                return false;
            } else if (!txtLicensePlate.getText().toString().replace("$","").equals(selitem.Lic_plate)) {
                mu.msgbox("La licencia no coincide.");
                return false;
            }

            if (txtCodigoPrd.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese código.");
                return false;
            } else if (!txtCodigoPrd.getText().toString().equals(selitem.Codigo_Producto)) {
                mu.msgbox("El código no coincide.");
                return false;
            }

            if (txtUbicDestino.getText().toString().isEmpty() ) {
                mu.msgbox("Ingrese ubicación destino.");
                return false;
            }

            if (txtCantidad.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese cantidad.");
                return false;
            }

        } catch (Exception e) {
            mu.msgbox("ValidaDatos: "+e.getMessage());
            return false;
        }

        return permite;
    }

    private boolean ValidaDatosPalletMixto() {
        boolean permite = true;

        try {
            if (txtUbicOrigen.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese ubicación de origen.");
                return false;
            } else if (Integer.valueOf(txtUbicOrigen.getText().toString()) != selitem.IdUbicacion) {
                mu.msgbox("La ubicación origen no coincide.");
                return false;
            }

            if (txtLicensePlate.getText().toString().isEmpty()) {
                mu.msgbox("Ingrese licencia.");
                return false;
            } else if (!txtLicensePlate.getText().toString().replace("$","").equals(selitem.Lic_plate)) {
                mu.msgbox("La licencia no coincide.");
                return false;
            }

            if (txtUbicDestino.getText().toString().isEmpty() ) {
                mu.msgbox("Ingrese ubicación destino.");
                return false;
            }

        } catch (Exception e) {
            mu.msgbox("ValidaDatos: "+e.getMessage());
            return false;
        }

        return permite;
    }

    public void BotonAplicaCambio(View view) {
        try {

            if (!txtUbicDestino.getText().toString().isEmpty()) {
                if (UbicValida) {
                    ActualizaUbicacion();
                } else {
                    execws(1);
                }
            } else {
                toast("Ingrese una ubicación de destino");
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() +" . " + e.getMessage());
        }
    }

    private void ActualizaUbicacion() {
        try {
            if (gl.pBeBodega.Control_Pallet_Mixto) {
                if (ValidaDatosPalletMixto()) {
                    execws(3);
                }
            } else {
                if (ValidaDatos()) {
                    execws(2);
                }
            }
        } catch (Exception e) {
            mu.msgbox("ActualizaUbicacion: "+e.getMessage());
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
                        IdUbicacion = Integer.valueOf(txtUbicDestino.getText().toString());
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                         "pBarra", IdUbicacion,
                                         "pIdBodega", gl.IdBodega);
                        break;

                    case 2:
                        callMethod("Actualizar_Ubicaciones_Reservadas_By_IdStock",
                                         "pIdStock", selitem.IdStock,
                                         "pIdBodega", gl.IdBodega,
                                         "pIdUbicacion",IdUbicacion,
                                         "pIdOperador", gl.IdOperador);
                        break;

                    case 3:
                        callMethod("Actualizar_Ubicaciones_Reservadas_Pallet_Mixto",
                                "pLista", filtrada,
                                "pIdBodega", gl.IdBodega,
                                "pIdUbicacion",IdUbicacion,
                                "pIdOperador", gl.IdOperador);
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
                    processValidaUbic();
                    break;
                case 2:
                    processActualizaUbic();
                    break;
                case 3:
                    processActualizaUbicPalletMixto();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void msgExito(String msg) {
        ExDialog dialog = new ExDialog(this);
        dialog.setMessage(msg);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();

    }


    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    public void Regresar(View view) {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}