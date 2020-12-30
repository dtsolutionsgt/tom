package com.dts.tom.Transacciones.InventarioCiclico;

import androidx.appcompat.app.AppCompatActivity;
import com.dts.base.WebService;
import com.dts.base.XMLObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_ciclico_spinner;
import com.dts.tom.PBase;
import com.dts.tom.R;

import org.apache.commons.lang.ObjectUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.dts.tom.Transacciones.Inventario.frm_list_inventario.BeInvEnc;

public class frm_inv_cic_nuevo extends PBase {

    private WebServiceHandler ws;
    private XMLObject xobj;
    private Cursor ctableFamilia,ctableClasi,ctableMarca,ctableTipo,ctableUMB;
    //private clsBeProducto pBeProductoNuevo;

    private clsBe_inv_ciclico_spinner item_spinner;
    private ArrayList<clsBe_inv_ciclico_spinner>  list_spinner = new ArrayList<clsBe_inv_ciclico_spinner>();
    private ArrayList<clsBe_inv_ciclico_spinner>  list_spinner2 = new ArrayList<clsBe_inv_ciclico_spinner>();
    private ArrayList<clsBe_inv_ciclico_spinner>  list_spinner3 = new ArrayList<clsBe_inv_ciclico_spinner>();
    private ArrayList<clsBe_inv_ciclico_spinner>  list_spinner4 = new ArrayList<clsBe_inv_ciclico_spinner>();
    private ArrayList<clsBe_inv_ciclico_spinner>  list_spinner5 = new ArrayList<clsBe_inv_ciclico_spinner>();

    private ArrayList<String> bodlist= new ArrayList<String>();
    private ArrayList<String> bodlist2= new ArrayList<String>();
    private ArrayList<String> bodlist3= new ArrayList<String>();
    private ArrayList<String> bodlist4= new ArrayList<String>();
    private ArrayList<String> bodlist5= new ArrayList<String>();

    private ProgressDialog progress;

    private EditText etcodigo,etdescripcion;
    private Spinner cmbFamilia_nv,cmbClasificacion_nv,cmbMarca_nv,cmbTipo_nv,cmbUmbas_nv;
    private CheckBox chkControlVence,chkControlLote;
    private Button cmdAddProd;
    boolean chkcvence, chkclote;
    private String fecha_reg;

    Integer IdClasificacion,IdFamilia,IdMarca,IdTipo,IdUmBas;


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

        setHandlers();

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


    private void setHandlers() {
        try{

            cmbFamilia_nv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    IdFamilia = position +1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }
            });

            cmbClasificacion_nv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    IdClasificacion = position +1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }
            });

            cmbMarca_nv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    IdMarca = position +1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }
            });

            cmbTipo_nv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    IdTipo = position +1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }
            });

            cmbUmbas_nv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    IdUmBas = position +1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                    toast("No hay estado seleccionado");
                }
            });

            chkControlVence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        chkcvence = true;
                        //execws(1);

                    } else {

                        chkcvence = false;
                        //execws(1);

                    }
                }
            });

            chkControlLote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        chkclote = true;

                    } else {

                        chkclote = false;
                    }
                }
            });

        }
        catch (Exception e){
            mu.msgbox(e.getClass()+" "+e.getMessage());
        }
    }

    public void GuardarNuevo(View view) {

        try{

            gl.pBeProductoNuevo =  new clsBeProducto();

            try {
                fecha_reg = String.valueOf(du.getFechaActual());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            gl.pBeProductoNuevo.IdPropietario = BeInvEnc.Idpropietario;
            gl.pBeProductoNuevo.IdClasificacion = IdClasificacion;
            gl.pBeProductoNuevo.IdFamilia = IdFamilia;
            gl.pBeProductoNuevo.IdMarca = IdMarca;
            gl.pBeProductoNuevo.IdTipoProducto = IdTipo;
            gl.pBeProductoNuevo.IdUnidadMedidaBasica = IdUmBas;
            //gl.pBeProductoNuevo.Presentacion=
            gl.pBeProductoNuevo.Codigo = etcodigo.getText().toString().trim();
            gl.pBeProductoNuevo.Nombre = etdescripcion.getText().toString().trim();
            gl.pBeProductoNuevo.Codigo_barra = etcodigo.getText().toString().trim();
            gl.pBeProductoNuevo.Activo = true;
            gl.pBeProductoNuevo.User_agr = BeInvEnc.User_agr;
            gl.pBeProductoNuevo.Fec_agr = fecha_reg;
            gl.pBeProductoNuevo.User_mod = BeInvEnc.User_agr;
            gl.pBeProductoNuevo.Fec_mod = fecha_reg;
            gl.pBeProductoNuevo.Control_vencimiento = chkcvence;
            gl.pBeProductoNuevo.Control_lote = chkclote;

            if(gl.pBeProductoNuevo != null){

                startActivity(new Intent(getApplicationContext(),frm_inv_cic_guardar.class));

            }

        }catch (Exception e) {
            mu.msgbox(e.getMessage());
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
                        callMethod("Get_Datos_Maestros_Inv_Fam","pIdenc",BeInvEnc.Idpropietario,
                                "pListFamilia",ctableFamilia);
                        break;
                    case 2:
                        callMethod("Get_Datos_Maestros_Inv_Clas","pIdenc",BeInvEnc.Idpropietario,
                                "pListClasificacion",ctableClasi);
                        break;
                    case 3:
                        callMethod("Get_Datos_Maestros_Inv_Marc","pIdenc",BeInvEnc.Idpropietario,
                                "pListMarca",ctableMarca);
                        break;
                    case 4:
                        callMethod("Get_Datos_Maestros_Inv_Tip","pIdenc",BeInvEnc.Idpropietario,
                                "pListTipo",ctableTipo);
                        break;
                    case 5:
                        callMethod("Get_Datos_Maestros_Inv_UmBas","pIdenc",BeInvEnc.Idpropietario,
                                "pListUMBas",ctableUMB);
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
                    Llena_Combos_Familia();
                    break;
                case 2:
                    Llena_Combos_Clase();
                    break;
                case 3:
                    Llena_Combos_Marca();
                    break;
                case 4:
                    Llena_Combos_Tipo();
                    break;
                case 5:
                    Llena_Combos_UmBas();
                    break;
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void Llena_Combos_UmBas() {

        try {

            list_spinner5.clear();
            ctableUMB = xobj.filldt();

            if(ctableUMB !=null) {

                if (ctableUMB.getCount() > 0) {

                    ctableUMB.moveToFirst();
                    while (!ctableUMB.isAfterLast()) {

                        item_spinner = new clsBe_inv_ciclico_spinner();
                        item_spinner.Id = Integer.parseInt(ctableUMB.getString(0));
                        item_spinner.Descripcion = ctableUMB.getString(1);
                        list_spinner5.add(item_spinner);
                        ctableUMB.moveToNext();
                    }

                    if (ctableUMB!=null) ctableUMB.close();

                    bodlist5.clear();
                    for (int i = 0; i <list_spinner5.size(); i++)
                    {
                        bodlist5.add(list_spinner5.get(i).Id + " - " + list_spinner5.get(i).Descripcion);
                    }

                    ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist5);
                    EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbUmbas_nv.setAdapter(EstadosAdapter);

                }
            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("inv_cic_marca:"+e.getMessage());
        }

    }

    private void Llena_Combos_Tipo() {

        try {

            list_spinner4.clear();
            ctableTipo = xobj.filldt();

            if(ctableTipo !=null) {

                if (ctableTipo.getCount() > 0) {

                    ctableTipo.moveToFirst();
                    while (!ctableTipo.isAfterLast()) {

                        item_spinner = new clsBe_inv_ciclico_spinner();
                        item_spinner.Id = Integer.parseInt(ctableTipo.getString(0));
                        item_spinner.Descripcion = ctableTipo.getString(1);
                        list_spinner4.add(item_spinner);
                        ctableTipo.moveToNext();
                    }

                    if (ctableTipo!=null) ctableTipo.close();

                    bodlist4.clear();
                    for (int i = 0; i <list_spinner4.size(); i++)
                    {
                        bodlist4.add(list_spinner4.get(i).Id + " - " + list_spinner4.get(i).Descripcion);
                    }

                    ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist4);
                    EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbTipo_nv.setAdapter(EstadosAdapter);

                    execws(5);

                }
            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("inv_cic_marca:"+e.getMessage());
        }

    }

    private void Llena_Combos_Marca() {

        try {

            list_spinner3.clear();
            ctableMarca = xobj.filldt();

            if(ctableMarca !=null) {

                if (ctableMarca.getCount() > 0) {

                    ctableMarca.moveToFirst();
                    while (!ctableMarca.isAfterLast()) {

                        item_spinner = new clsBe_inv_ciclico_spinner();
                        item_spinner.Id = Integer.parseInt(ctableMarca.getString(0));
                        item_spinner.Descripcion = ctableMarca.getString(1);
                        list_spinner3.add(item_spinner);
                        ctableMarca.moveToNext();
                    }

                    if (ctableMarca!=null) ctableMarca.close();

                    bodlist3.clear();
                    for (int i = 0; i <list_spinner3.size(); i++)
                    {
                        bodlist3.add(list_spinner3.get(i).Id + " - " + list_spinner3.get(i).Descripcion);
                    }

                    ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist3);
                    EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbMarca_nv.setAdapter(EstadosAdapter);

                    execws(4);
                }
            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("inv_cic_marca:"+e.getMessage());
        }

    }

    private void Llena_Combos_Clase() {

        try {

            list_spinner2.clear();

            ctableClasi = xobj.filldt();

            if(ctableClasi !=null) {

                if (ctableClasi.getCount() > 0) {

                    ctableClasi.moveToFirst();
                    while (!ctableClasi.isAfterLast()) {

                        item_spinner = new clsBe_inv_ciclico_spinner();
                        item_spinner.Id = Integer.parseInt(ctableClasi.getString(0));
                        item_spinner.Descripcion = ctableClasi.getString(1);
                        list_spinner2.add(item_spinner);
                        ctableClasi.moveToNext();
                    }

                    if (ctableClasi!=null) ctableClasi.close();

                    bodlist2.clear();
                    for (int i = 0; i <list_spinner2.size(); i++)
                    {
                        bodlist2.add(list_spinner2.get(i).Id + " - " + list_spinner2.get(i).Descripcion);
                    }

                    ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist2);
                    EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbClasificacion_nv.setAdapter(EstadosAdapter);

                    execws(3);
                }
            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("inv_cic_clase:"+e.getMessage());
        }
    }

    private void Llena_Combos_Familia() {

        try {

            list_spinner.clear();

            ctableFamilia = xobj.filldt();

            if(ctableFamilia !=null) {

                if (ctableFamilia.getCount() > 0) {

                    ctableFamilia.moveToFirst();
                    while (!ctableFamilia.isAfterLast()) {

                        item_spinner = new clsBe_inv_ciclico_spinner();
                        item_spinner.Id = Integer.parseInt(ctableFamilia.getString(0));
                        item_spinner.Descripcion = ctableFamilia.getString(1);
                        list_spinner.add(item_spinner);
                        ctableFamilia.moveToNext();
                    }

                    if (ctableFamilia!=null) ctableFamilia.close();

                    bodlist.clear();
                    for (int i = 0; i <list_spinner.size(); i++)
                    {
                        bodlist.add(list_spinner.get(i).Id + " - " + list_spinner.get(i).Descripcion);
                    }

                    ArrayAdapter<String> EstadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist);
                    EstadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbFamilia_nv.setAdapter(EstadosAdapter);

                    execws(2);
                }
            }

        } catch (Exception e) {
            progress.cancel();
            mu.msgbox("inv_cic_familia:"+e.getMessage());
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