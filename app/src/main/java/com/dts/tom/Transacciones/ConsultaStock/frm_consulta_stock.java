package com.dts.tom.Transacciones.ConsultaStock;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.base.appGlobals;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Producto.clsBeProductoList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI_List;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock;
import com.dts.ladapt.ConsultaStock.list_adapt_consulta_stock2;
import com.dts.tom.PBase;
import com.dts.tom.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

import static br.com.zbra.androidlinq.Linq.stream;


public class frm_consulta_stock extends PBase {

    private frm_consulta_stock.WebServiceHandler ws;
    private XMLObject xobj;
    private ProgressDialog progress;

    private ListView listView;
    private ImageView btnFiltros;
    private Button btnBack, registros,btnBuscar;
    private RelativeLayout relFiltros;
    private int pIdTarea=0;
    private EditText txtCodigo, txtUbic, txtNombre;
    private CheckBox chkDetalle;
    private int idubic, idprod, conteo;
    private clsBeBodega_ubicacion cUbic;
    private clsBeProductoList ListBeStockPallet;
    private String pLicensePlate, codProducto, nomProducto;
    private clsBeProducto BeProducto;
    private boolean Escaneo_Pallet;
    private TextView lblNombreUbicacion;
    private TextView lblNombreProducto;
    private Boolean idle = false;
    private Integer selest;
    private clsBeVW_stock_res_CI_List pListStock2;
    private list_adapt_consulta_stock adapter_stock;
    private list_adapt_consulta_stock2 adapter_stock2;
    private ArrayList<clsBeVW_stock_res_CI> items_stock = new ArrayList<clsBeVW_stock_res_CI>();
    private ArrayList<clsBeVW_stock_res_CI> items_stock2 = new ArrayList<clsBeVW_stock_res_CI>();
    //clsBeVW_stock_res_CI  ItemSelected;
    private TextView lblTituloForma;
    private Spinner cmbEstadoExist, spOrdenar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.InitBase();

        if (gl.Mostrar_Area_En_HH) {
            setContentView(R.layout.activity_frm_consulta_stock2);
        } else {
            setContentView(R.layout.activity_frm_consulta_stock);
        }

        ws = new WebServiceHandler(frm_consulta_stock.this, gl.wsurl);
        xobj = new XMLObject(ws);
        Escaneo_Pallet = false;
        idle = false;
        selest = 0;

        listView = (ListView) findViewById(R.id.listExist);
        btnBack = (Button) findViewById(R.id.btnBack);
        //btnBuscar = (Button) findViewById(R.id.btnBuscar);
        registros = findViewById(R.id.btnRegs2);
        txtCodigo = (EditText) findViewById(R.id.txtCodigo1);
        txtUbic = (EditText) findViewById(R.id.txtUbic1);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        lblNombreUbicacion = findViewById(R.id.lblNombreUbicacion);
        lblNombreProducto = findViewById(R.id.lblNombreProducto);
        cmbEstadoExist = findViewById(R.id.cmbEstadoExist);
        spOrdenar= (Spinner)findViewById(R.id.spOrdenar) ;
        chkDetalle=(CheckBox)findViewById(R.id.chkDetalle);
        btnFiltros = findViewById(R.id.btnFiltros);
        relFiltros = findViewById(R.id.relFiltros);
        lblTituloForma = findViewById(R.id.lblTituloForma);

        gl.mostar_filtros = false;
        setHandlers();
        lblTituloForma.setText(gl.CodigoBodega + "-" + gl.gNomBodega + "\n Consulta de Existencias ");
        txtUbic.requestFocus();
    }

    private void setHandlers() {
        try{

            txtUbic.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            switch (keyCode) {
                                case KeyEvent.KEYCODE_ENTER:
                                    //Get_Ubicacion_By_Codigo_Barra_And_IdBodega
                                    execws(1);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtCodigo.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    try {

                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:

                                    lblNombreProducto.setText("");
                                    lblNombreProducto.setVisibility(View.GONE);

                                    busca_stock();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            txtNombre.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent event) {
                    try {

                        if (event.getAction() == KeyEvent.ACTION_DOWN) {

                            switch (keyCode) {

                                case KeyEvent.KEYCODE_ENTER:
                                    busca_stock();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            cmbEstadoExist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                    try{
                        TextView spinlabel = (TextView) parentView.getChildAt(0);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                        String estado = cmbEstadoExist.getSelectedItem().toString();

                        if(estado.isEmpty() && selest ==0){

                        }
                        else {
                            Spinner(estado);
                        }

                    }catch (Exception e){
                        msgbox("Error " + e.getMessage());
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }

            });

            spOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    if (pListStock2 != null){

                        if (pListStock2.items != null){

                            if (pListStock2.items.size()>0){

                                String orden = spOrdenar.getSelectedItem().toString();

                                switch (orden) {
                                    case  "Código":
                                        Collections.sort(pListStock2.items, new CodigoSort());
                                        break;
                                    case  "Ubicación":
                                        Collections.sort(pListStock2.items, new UbicacionSort());
                                        break;
                                    case  "Estado":
                                        Collections.sort(pListStock2.items, new EstadoSort());
                                        break;
                                    case  "Fecha vencimiento":
                                        Collections.sort(pListStock2.items, new FechaVencSort());
                                        break;
                                    case  "Fecha ingreso":
                                        Collections.sort(pListStock2.items, new FechaIngSort());
                                        break;
                                }

                                lista_inventario_ordenado();

                            }
                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    toast("No hay orden seleccionado");
                }

            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selid = 0;

                    // AT 20211221 lo hace sin importar que la posición sea  = a 0
                    //if (position > 0) {
                    gl.existencia = (clsBeVW_stock_res_CI) listView.getItemAtPosition(position);

                    try {
                        if (gl.existencia.IdProductoBodega!=-1) {
                            Intent intent = new Intent(getApplicationContext(), frm_consulta_stock_detalleCI.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        msgbox("Error al intentar cargar detalle del producto");
                        addlog(new Object() {
                        }.getClass().getEnclosingMethod().getName(), e.getMessage(), "");
                    }
                    //}
                }

            });

            chkDetalle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    busca_stock();
                }
            });

            btnFiltros.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gl.mostar_filtros = !gl.mostar_filtros;
                    relFiltros.setVisibility(gl.mostar_filtros ? View.VISIBLE : View.GONE);
                }
            });

        }
        catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    class CodigoSort implements Comparator<clsBeVW_stock_res_CI>   {
        public int compare(clsBeVW_stock_res_CI left, clsBeVW_stock_res_CI right)
        {
            //return left.Codigo  - right.Codigo;
            return left.Codigo.compareTo(right.Codigo);
        }
    }

    class UbicacionSort implements Comparator<clsBeVW_stock_res_CI>    {
        public int compare(clsBeVW_stock_res_CI left, clsBeVW_stock_res_CI right)
        {
            return left.idUbic.compareTo(right.idUbic);
        }
    }

    class EstadoSort implements Comparator<clsBeVW_stock_res_CI>    {
        public int compare(clsBeVW_stock_res_CI left, clsBeVW_stock_res_CI right)
        {
            return left.getIdProductoEstado().compareTo(right.getIdProductoEstado());
        }
    }

    class FechaVencSort implements Comparator<clsBeVW_stock_res_CI>    {
        public int compare(clsBeVW_stock_res_CI left, clsBeVW_stock_res_CI right)
        {
            return left.Vence.compareTo(right.Vence);
        }
    }

    class FechaIngSort implements Comparator<clsBeVW_stock_res_CI>    {
        public int compare(clsBeVW_stock_res_CI left, clsBeVW_stock_res_CI right)
        {
            return left.ingreso.compareTo(right.ingreso);
        }
    }

    private void processUbicacion() {
        try {

            cUbic =xobj.getresult(clsBeBodega_ubicacion.class,"Get_Ubicacion_By_Codigo_Barra_And_IdBodega");

            lblNombreUbicacion.setVisibility(View.VISIBLE);
            if (cUbic != null){
                idubic = cUbic.IdUbicacion;

                lblNombreUbicacion.setTextColor(Color.BLUE);
                lblNombreUbicacion.setText(cUbic.getDescripcion());

            }else{
                idubic = 0;
                lblNombreUbicacion.setTextColor(Color.RED);
                lblNombreUbicacion.setText("Ubicación no existe en la bodega " + gl.IdBodega);
            }

            //Listar_Existencias();
            busca_stock();

        } catch (Exception e) {
            //progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processScanProducto(){

        idubic = 0;
        idprod = 0;
        codProducto = "";

        try {

            BeProducto = xobj.getresult(clsBeProducto.class,"Get_BeProducto_By_Codigo_For_HH");

            if (BeProducto != null){
                idprod = BeProducto.IdProducto;
                lblNombreProducto.setVisibility(View.VISIBLE);
                lblNombreProducto.setText(BeProducto.getNombre());
                codProducto=BeProducto.Codigo;
                //toast("ubicación encontrada por HH");
            }else{
                throw new Exception("El producto no existe en la bodega: " + gl.IdBodega);
            }

           busca_stock();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void listaStock() {
        String lpid,cod,lname="";
        double uni,tuni,tExistUmbas, tResUmbas, tDispUmbas, tResPres, tDisPres;
        double existUmbas, resUmbas, dispUmbas,resPres, disPres;
        int lcnt;

        clsBeVW_stock_res_CI vItem;
        clsBeVW_stock_res_CI item;

        try {
            items_stock.clear();

            switch (ws.callback) {
                case 2:
                    pListStock2= xobj.getresult(clsBeVW_stock_res_CI_List.class,"Get_Stock_Por_Pallet_By_IdUbicacion_CI");
                    break;
                case 4:
                    pListStock2= xobj.getresult(clsBeVW_stock_res_CI_List.class,"Get_Stock_Por_Producto_Ubicacion_CI");
                    break;
            }

            if (pListStock2 != null) {
                conteo = pListStock2.items.size();

                if(conteo == 0 || pListStock2.items.isEmpty()){
                    lblNombreUbicacion.setVisibility(View.VISIBLE);
                    lblNombreUbicacion.setText("U.S.P");
                    idle = true;
                } else {

                    // lbldescripcion.setText("");

                    //AT 20211221 ya no se agrega un item vacío
                    //vItem = new clsBeVW_stock_res_CI();
                    //items_stock.add(vItem);

                    registros.setText("REGISTROS: "+ conteo);

                    if (pListStock2.items.size()>0) lpid=pListStock2.items.get(0).Codigo;else lpid=" ";
                    tuni=0;lcnt=0;tExistUmbas=0; tDispUmbas = 0; tResUmbas=0; tResPres = 0; tDisPres = 0;

                    for (int i = 0; i < pListStock2.items.size(); i++) {

                        cod=pListStock2.items.get(i).Codigo;

                        if (!cod.equals(lpid)) {
                            if (lcnt>1) {

                                item = new clsBeVW_stock_res_CI();

                                item.Codigo = "Total:";
                                item.Nombre = lname;
                                item.UM = "";
                                item.ExistUMBAs = mu.frmdec(tExistUmbas);
                                item.Pres = "";
                                item.ExistPres =mu.frmdec(tuni);
                                item.ReservadoUMBAs = mu.frmdec(tResUmbas);
                                item.DisponibleUMBas = mu.frmdec(tDispUmbas);
                                item.Lote = "";
                                item.Vence = "";
                                item.Estado = "";
                                item.Ubic = "";
                                item.idUbic = "";
                                item.Pedido = "";
                                item.Pick =  "";
                                item.LicPlate =  "";
                                item.IdProductoBodega =  -1;
                                item.factor = 0;
                                item.ingreso=  "";
                                item.IdTipoEtiqueta= 0;
                                item.ResPres =  mu.frmdec(tResPres);
                                item.DispPres =  mu.frmdec(tDisPres);
                                item.NombreArea =  "";
                                item.Clasificacion =  "";

                                items_stock.add(item);

                            }

                            lpid= cod;tuni=0;lcnt=0;
                            tDisPres = 0;
                            tResPres = 0;
                            tDispUmbas = 0;
                            tResUmbas = 0;
                            tExistUmbas = 0;

                        }

                        item = new clsBeVW_stock_res_CI();

                        item.Codigo = pListStock2.items.get(i).Codigo;
                        item.Nombre = pListStock2.items.get(i).Nombre;lname=item.Nombre;
                        item.UM = pListStock2.items.get(i).UM;
                        item.ExistUMBAs = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ExistUMBAs));
                        item.Pres = pListStock2.items.get(i).Pres;
                        item.ExistPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ExistPres));
                        item.ReservadoUMBAs = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ReservadoUMBAs));
                        item.DisponibleUMBas = mu.frmdec(Double.valueOf(pListStock2.items.get(i).DisponibleUMBas));
                        item.Lote = pListStock2.items.get(i).Lote;
                        item.Vence = pListStock2.items.get(i).Vence;
                        item.Estado = pListStock2.items.get(i).Estado;
                        item.Ubic = pListStock2.items.get(i).Ubic;
                        item.idUbic = pListStock2.items.get(i).idUbic;
                        item.Pedido = pListStock2.items.get(i).Pedido;
                        item.Pick = pListStock2.items.get(i).Pick;
                        item.LicPlate = pListStock2.items.get(i).LicPlate;
                        item.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;
                        item.factor = pListStock2.items.get(i).factor;
                        item.ingreso= pListStock2.items.get(i).ingreso;
                        item.IdTipoEtiqueta=pListStock2.items.get(i).IdTipoEtiqueta;
                        item.ResPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ResPres));
                        item.DispPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).DispPres));
                        item.NombreArea = pListStock2.items.get(i).NombreArea;
                        item.Clasificacion = pListStock2.items.get(i).Clasificacion;

                        items_stock.add(item);

                        if (i==0){
                            if (!txtCodigo.getText().toString().isEmpty()){
                                if (!txtCodigo.getText().toString().equals("0")){
                                    lblNombreProducto.setVisibility(View.VISIBLE);
                                    lblNombreProducto.setText(item.Codigo + " " + item.Nombre);
                                }
                            }
                        }

                        try {
                            uni=Double.parseDouble(pListStock2.items.get(i).ExistPres);
                            existUmbas = Double.parseDouble(pListStock2.items.get(i).ExistUMBAs);
                            dispUmbas = Double.parseDouble(pListStock2.items.get(i).DisponibleUMBas);
                            resUmbas = Double.parseDouble(pListStock2.items.get(i).ReservadoUMBAs);

                            resPres = Double.parseDouble(pListStock2.items.get(i).ResPres);
                            disPres = Double.parseDouble(pListStock2.items.get(i).DispPres);

                        } catch (NumberFormatException e) {
                            uni=0;
                            existUmbas = 0;
                            dispUmbas = 0;
                            resUmbas = 0;
                            resPres = 0;
                            disPres = 0;

                        }

                        tuni+=uni;lcnt++;
                        tExistUmbas += existUmbas;
                        tDispUmbas += dispUmbas;
                        tResUmbas += resUmbas;
                        tDisPres += disPres;
                        tResPres += resPres;
                    }

                    if (lcnt>1) {

                        item = new clsBeVW_stock_res_CI();

                        item.Codigo = "Total:";
                        item.Nombre = lname;
                        item.UM = "";
                        item.ExistUMBAs = mu.frmdec(tExistUmbas);
                        item.Pres = "";
                        item.ExistPres =mu.frmdec(tuni);
                        item.ReservadoUMBAs = mu.frmdec(tResUmbas);
                        item.DisponibleUMBas = mu.frmdec(tDispUmbas);
                        item.Lote = "";
                        item.Vence = "";
                        item.Estado = "";
                        item.Ubic = "";
                        item.idUbic = "";
                        item.Pedido = "";
                        item.Pick =  "";
                        item.LicPlate =  "";
                        item.IdProductoBodega =  -1;
                        item.factor = 0;
                        item.ingreso=  "";
                        item.IdTipoEtiqueta= 0;
                        item.ResPres =  mu.frmdec(tResPres);
                        item.DispPres =  mu.frmdec(tDisPres);
                        item.NombreArea =  "";
                        item.Clasificacion =  "";

                        items_stock.add(item);

                    }


                    if (gl.Mostrar_Area_En_HH) {
                        adapter_stock2 = new list_adapt_consulta_stock2(getApplicationContext(),items_stock);
                        listView.setAdapter(adapter_stock2);
                    } else {
                        adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                        listView.setAdapter(adapter_stock);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        Map<String, List<clsBeVW_stock_res_CI>> ListaEstados = pListStock2.items.stream()
                                .collect(groupingBy(clsBeVW_stock_res_CI::getEstado));

                        List<String> categories = new ArrayList<String>();
                        categories.add("");

                        ListaEstados.forEach((k, v) -> {
                            //System.out.printf("%s : %d%n", k, v);
                            categories.add(k);
                        });

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cmbEstadoExist.setAdapter(dataAdapter);

                        if(categories.size() <= 1){
                            selest = 0;
                        } else {
                            selest = 1;
                        }
                    }
                }
            } else {
                //limpiar el grid.
                if (gl.Mostrar_Area_En_HH) {
                    adapter_stock2 = new list_adapt_consulta_stock2(getApplicationContext(),items_stock);
                    listView.setAdapter(adapter_stock2);
                } else {
                    adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                    listView.setAdapter(adapter_stock);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lista_inventario_ordenado(){

        clsBeVW_stock_res_CI vItem;
        clsBeVW_stock_res_CI item;

        try{

            items_stock.clear();

            //AT 20211221 ya no se agrega un item vacío
            //vItem = new clsBeVW_stock_res_CI();
            //items_stock.add(vItem);

            registros.setText("REGISTROS: "+ conteo);

            for (int i = 0; i < pListStock2.items.size(); i++) {

                item = new clsBeVW_stock_res_CI();
                item.Codigo = pListStock2.items.get(i).Codigo;
                item.Nombre = pListStock2.items.get(i).Nombre;
                item.UM = pListStock2.items.get(i).UM;
                item.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                item.Pres = pListStock2.items.get(i).Pres;
                item.ExistPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ExistPres));
                item.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                item.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                item.Lote = pListStock2.items.get(i).Lote;
                item.Vence = pListStock2.items.get(i).Vence;
                item.Estado = pListStock2.items.get(i).Estado;
                item.Ubic = pListStock2.items.get(i).Ubic;
                item.idUbic = pListStock2.items.get(i).idUbic;
                item.Pedido = pListStock2.items.get(i).Pedido;
                item.Pick = pListStock2.items.get(i).Pick;
                item.LicPlate = pListStock2.items.get(i).LicPlate;
                item.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;
                item.factor = pListStock2.items.get(i).factor;
                item.ingreso= pListStock2.items.get(i).ingreso;
                item.IdTipoEtiqueta= pListStock2.items.get(i).IdTipoEtiqueta;//#CKFK 20210716 1846 Agregué el campo IdTipoEtiqueta
                item.ResPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ResPres));
                item.DispPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).DispPres));
                item.NombreArea = pListStock2.items.get(i).NombreArea;
                item.Clasificacion = pListStock2.items.get(i).Clasificacion;
                items_stock.add(item);
            }

            if (gl.Mostrar_Area_En_HH) {
                adapter_stock2 = new list_adapt_consulta_stock2(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock2);
            } else {
                adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock);
            }

        }catch (Exception ex){

        }
    }

    private void Spinner(String estado){

        FiltroSpinner(estado);

    }

    private void FiltroSpinner(String estado){

        items_stock2.clear();
        clsBeVW_stock_res_CI items;
        clsBeVW_stock_res_CI vItem;

        if(estado == "" && selest>0){

            //AT 20211221 ya no se agrega un item vacío
            //vItem = new clsBeVW_stock_res_CI();
            //items_stock2.add(vItem);

            for (int i = 0; i < pListStock2.items.size(); i++) {

                items = new clsBeVW_stock_res_CI();
                items.Codigo = pListStock2.items.get(i).Codigo;
                items.Nombre = pListStock2.items.get(i).Nombre;
                items.UM = pListStock2.items.get(i).UM;
                items.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                items.Pres = pListStock2.items.get(i).Pres;
                items.ExistPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ExistPres));
                items.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                items.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                items.Lote = pListStock2.items.get(i).Lote;
                items.Vence = pListStock2.items.get(i).Vence;
                items.Estado = pListStock2.items.get(i).Estado;
                items.Ubic = pListStock2.items.get(i).Ubic;
                items.idUbic = pListStock2.items.get(i).idUbic;
                items.Pedido = pListStock2.items.get(i).Pedido;
                items.Pick = pListStock2.items.get(i).Pick;
                items.LicPlate = pListStock2.items.get(i).LicPlate;
                items.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;
                items.factor = pListStock2.items.get(i).factor;
                items.ingreso = pListStock2.items.get(i).ingreso;
                items.IdTipoEtiqueta = pListStock2.items.get(i).IdTipoEtiqueta;
                items.ResPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ResPres));
                items.DispPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).DispPres));
                items.NombreArea = pListStock2.items.get(i).NombreArea;
                items.Clasificacion = pListStock2.items.get(i).Clasificacion;
                items_stock2.add(items);

            }
            if (gl.Mostrar_Area_En_HH) {
                adapter_stock2 = new list_adapt_consulta_stock2(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock2);
            } else {
                adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock);
            }

            // AT20211221 ya no se resta -1
            conteo = items_stock2.size();
            registros.setText("REGISTROS: "+ conteo);

        } else {
            //AT 20211221 ya no se agrega un item vacío
            //vItem = new clsBeVW_stock_res_CI();
            //items_stock2.add(vItem);

            for (int i = 0; i < pListStock2.items.size(); i++) {

                String estado_ = pListStock2.items.get(i).Estado;

                if (estado_.equals(estado)) {
                    items = new clsBeVW_stock_res_CI();
                    items.Codigo = pListStock2.items.get(i).Codigo;
                    items.Nombre = pListStock2.items.get(i).Nombre;
                    items.UM = pListStock2.items.get(i).UM;
                    items.ExistUMBAs = pListStock2.items.get(i).ExistUMBAs;
                    items.Pres = pListStock2.items.get(i).Pres;
                    items.ExistPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ExistPres));
                    items.ReservadoUMBAs = pListStock2.items.get(i).ReservadoUMBAs;
                    items.DisponibleUMBas = pListStock2.items.get(i).DisponibleUMBas;
                    items.Lote = pListStock2.items.get(i).Lote;
                    items.Vence = pListStock2.items.get(i).Vence;
                    items.Estado = pListStock2.items.get(i).Estado;
                    items.Ubic = pListStock2.items.get(i).Ubic;
                    items.idUbic = pListStock2.items.get(i).idUbic;
                    items.Pedido = pListStock2.items.get(i).Pedido;
                    items.Pick = pListStock2.items.get(i).Pick;
                    items.LicPlate = pListStock2.items.get(i).LicPlate;
                    items.IdProductoBodega = pListStock2.items.get(i).IdProductoBodega;
                    items.ingreso = pListStock2.items.get(i).ingreso;
                    items.ResPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).ResPres));
                    items.DispPres = mu.frmdec(Double.valueOf(pListStock2.items.get(i).DispPres));
                    items.NombreArea = pListStock2.items.get(i).NombreArea;
                    items.Clasificacion = pListStock2.items.get(i).Clasificacion;
                    items_stock2.add(items);
                }
            }
            if (gl.Mostrar_Area_En_HH) {
                adapter_stock2 = new list_adapt_consulta_stock2(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock2);
            } else {
                adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                listView.setAdapter(adapter_stock);
            }

            // AT20211221 ya no se resta 1
            conteo = items_stock2.size();
            registros.setText("REGISTROS: "+ conteo);

        }

    }

    public void ProgressDialog(String mensaje) {
        try {
            progress = new ProgressDialog(this);
            progress.setMessage(mensaje);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarStock(View view){

        try{

            busca_stock();

        }catch (Exception ex){

        }
    }

    private void busca_stock(){

        try{

            lblNombreProducto.setText("");
            lblNombreProducto.setVisibility(View.GONE);

            if ((txtCodigo.getText().toString().isEmpty() && txtCodigo.getText().toString().isEmpty()) &&
                    (txtUbic.getText().toString().isEmpty() && txtUbic.getText().toString().isEmpty()) &&
                    (txtNombre.getText().toString().isEmpty() && txtNombre.getText().toString().isEmpty())
            ) {
                items_stock.clear();

                if (gl.Mostrar_Area_En_HH) {
                    adapter_stock2 = new list_adapt_consulta_stock2(getApplicationContext(),items_stock);
                    listView.setAdapter(adapter_stock2);
                } else {
                    adapter_stock = new list_adapt_consulta_stock(getApplicationContext(),items_stock);
                    listView.setAdapter(adapter_stock);
                }

                toast("Ingrese código de producto y/o ubicación");
            } else {

                if(txtCodigo.getText().toString().isEmpty()){
                    idprod = 0;
                    codProducto = "";
                }else{
                    try {
                        codProducto = txtCodigo.getText().toString();
                        idprod = Integer.valueOf(txtCodigo.getText().toString());
                    }catch (Exception e){
                        idprod = -1;
                    }
                }
                if(txtUbic.getText().toString().isEmpty()){
                    idubic = 0;
                }else{
                    idubic = Integer.valueOf(txtUbic.getText().toString());
                }

                if (txtNombre.getText().toString().isEmpty()) {
                    nomProducto = "";
                } else {
                    nomProducto = txtNombre.getText().toString();
                }

                String vStarWithParameter = "$";
                //Comentario: La barra de pallet puede comenzar con $ y no con (01)
                if (txtCodigo.getText().toString().startsWith("$") ||
                        txtCodigo.getText().toString().startsWith("(01)") ||
                        txtCodigo.getText().toString().startsWith(vStarWithParameter)) {
                    //Es una barra de pallet válida por tamaño
                    int vLengthBarra = txtCodigo.getText().toString().length();
                    if (vLengthBarra >= 0) {
                        pLicensePlate = txtCodigo.getText().toString().replace("$", "");

                        ProgressDialog("Cargando existencias");
                        //Get_Stock_Por_Pallet_By_IdUbicacion_CI
                        execws(2);
                    }
                } else {

                    ProgressDialog("Cargando existencias");
                    //Get_Stock_Por_Producto_Ubicacion_CI
                    execws(4);
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
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
                        callMethod("Get_Ubicacion_By_Codigo_Barra_And_IdBodega",
                                "pBarra",txtUbic.getText().toString(),
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 2:
                        //ByVal pLicensePlate As String,ByVal pIdBodega As Integer
                        callMethod("Get_Stock_Por_Pallet_By_IdUbicacion_CI",
                                "pLicPlate",(pLicensePlate==null?0:pLicensePlate),
                                "pIdBodega",gl.IdBodega,
                                "pIdUbicacion",idubic,
                                "pNombre", nomProducto,
                                "pDetallado", chkDetalle.isChecked());
                        break;
                    case 3:
                        //ByVal pCodigo As String, ByVal IdBodega As Integer
                        callMethod("Get_BeProducto_By_Codigo_For_HH",
                                "pCodigo",txtCodigo.getText().toString(),
                                "IdBodega",gl.IdBodega);
                        break;

                    case 4:
                        callMethod("Get_Stock_Por_Producto_Ubicacion_CI","" +
                                               "pidProducto",codProducto,
                                               "pIdUbicacion",idubic,
                                               "pIdBodega",gl.IdBodega,
                                               "pNombre", nomProducto,
                                               "pDetallado", chkDetalle.isChecked());
                        break;

                }

                if (progress !=null){
                    progress.cancel();
                }

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
                    processUbicacion();
                    break;
                case 2:
                    listaStock();
                    break;
                case 3:
                    processScanProducto();
                    break;
                case 4:
                    listaStock();
                    break;

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
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
                    //frm_verificacion_datos.super.finish();
                    frm_consulta_stock.super.finish();
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

    @Override
    public void onBackPressed() {

        try{

            msgAskExit("Está seguro de salir de existencia iventarios");

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public void Exit(View view) {
        msgAskExit("Está seguro de salir");
    }


}
