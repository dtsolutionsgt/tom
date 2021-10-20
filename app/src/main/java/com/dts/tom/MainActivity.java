package com.dts.tom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaBase;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaList;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresaAndList;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresaBase;
import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodega;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodegaList;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Mantenimientos.Version.clsBeVersion_wms_hh_andList;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.com.zbra.androidlinq.Linq.stream;

public class MainActivity extends PBase {


    private Spinner spinemp,spinbod,spinprint,spinuser;
    private EditText txtpass;
    private TextView lblver,lbldate,lblurl;
    private ProgressDialog progress;
    private ImageView imgIngresar;
    private ImageView imgEmpresaLogin;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeEmpresaAndList empresas = new clsBeEmpresaAndList();
    private clsBeBodegaList bodegas = new clsBeBodegaList();
    private ArrayList<clsBeImpresora> impres = new ArrayList<clsBeImpresora>();
    private clsBeOperador_bodegaList users = new clsBeOperador_bodegaList();
    private clsBeVersion_wms_hh_andList versiones = new clsBeVersion_wms_hh_andList();
    private clsBeResolucion_lp_operador ResolucionLpByBodega = new clsBeResolucion_lp_operador();

    private clsBeOperador_bodega seloper=new clsBeOperador_bodega();

    private ArrayList<String> emplist= new ArrayList<String>();
    private ArrayList<String> bodlist= new ArrayList<String>();
    private ArrayList<String> prnlist= new ArrayList<String>();
    private ArrayList<String> userlist= new ArrayList<String>();

    private int idemp=0,idbodega=0,idimpres=0,iduser=-1;
    private String NomOperador, NomBodega;
    private boolean idle=false;

    private String rootdir = Environment.getExternalStorageDirectory() + "/WMSFotos/";
    private String version="4.5.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ProgressDialog("Inicializando...");

            grantPermissions();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startApplication() {

        try {

            super.InitBase();

            spinemp = (Spinner) findViewById(R.id.spinner);
            spinbod = (Spinner) findViewById(R.id.spinner2);
            spinprint = (Spinner) findViewById(R.id.spinner3);
            spinuser = (Spinner) findViewById(R.id.spinner4);
            txtpass = (EditText) findViewById(R.id.editText3);
            lblver=(TextView)  findViewById(R.id.txtNoVersion);
            lbldate=(TextView)  findViewById(R.id.txtFechaVersion);
            lblurl=(TextView)  findViewById(R.id.txtURLWS);lblurl.setText("");
            imgIngresar = (ImageView) findViewById(R.id.imageView11);
            imgEmpresaLogin = (ImageView) findViewById(R.id.imgEmpresaLogin);

            getURL();

            if (!gl.wsurl.isEmpty()){
                ws= new WebServiceHandler(MainActivity.this, gl.wsurl);
                xobj= new XMLObject(ws);
                setHandlers();
                gl.deviceId =androidid();
                gl.devicename = getLocalBluetoothName();
            } else {
                //msgbox("No está definida la URL de conexión al WS, configúrelo por favor");
                setURL();
            }

            try {
                String orddir=Environment.getExternalStorageDirectory().getPath() + "/tomtask";
                File directory = new File(orddir);
                directory.mkdirs();
            } catch (Exception e) {}

            //Load();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void Load(){

        try{

            progress.setMessage("Cargando empresas...");
            progress.show();

            LimpiarControles();

            // Lista de empresas
            if (!gl.wsurl.isEmpty()){
                execws(1);
            }else{
                progress.cancel();
            }

        } catch (Exception e) {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void LimpiarControles()     {

        try{
            txtpass.setText("");
            spinemp.setAdapter(null);
            spinbod.setAdapter(null);
            spinprint.setAdapter(null);
            spinuser.setAdapter(null);
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void setURL(){

        String url="http://192.168.0.98/WSTOMHH_QA/TOMHHWS.asmx";

        try{

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Conexión");
            alert.setMessage("Ingrese la URL:");

            alert.setIcon(R.drawable.ic_quest);

            final EditText input = new EditText(this);
            input.setText(url);
            input.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_FLAG_MULTI_LINE);

            alert.setView(input);

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    msgbox("");
                }
            });

            alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    gl.wsurl=input.getText().toString();

                    if(!gl.wsurl.isEmpty()){
                        guardaDatosConexion();
                    }else{
                        toast("Debe ingresar la URL");
                        setURL();
                    }
                }
            });

            final AlertDialog dialog = alert.create();
            dialog.show();

            showkeyb();
        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    protected void guardaDatosConexion() {

        BufferedWriter writer = null;
        FileWriter wfile;

        try {

            String fname = Environment.getExternalStorageDirectory()+"/tomws.txt";
            File archivo= new File(fname);

            if (archivo.exists()){
                archivo.delete();
            }

            wfile=new FileWriter(fname,true);
            writer = new BufferedWriter(wfile);

            writer.write(gl.wsurl + "\n");

            writer.close();

            getURL();

            ws = new WebServiceHandler(MainActivity.this, gl.wsurl);
            xobj = new XMLObject(ws);

            setHandlers();

            gl.deviceId =androidid();

            Load();

        } catch (Exception e) {
            msgbox("Error " + e.getMessage());
        }

    }

    public void recargar(View view){
        Load();
    }

    //region Grant permissions

    private void grantPermissions()   {
        try {

            if (Build.VERSION.SDK_INT >= 20) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        && checkCallingOrSelfPermission(Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    startApplication();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WAKE_LOCK,
                                    Manifest.permission.READ_PHONE_STATE
                            }, 1);
                }
            }

        } catch (Exception e) {
            addlog(new Object() {
            }.getClass().getEnclosingMethod().getName(), e.getMessage(), "");
            msgbox(new Object() {
            }.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                startApplication();
            } else
            {
                super.finish();
            }
        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    public void doLogin(View view)     {
        try{

            imgIngresar.setVisibility(View.INVISIBLE);

            progress.show();
            progress.setMessage("Ingresando....");

            Valida_Ingreso();
            //startActivity(new Intent(this,Mainmenu.class));

            imgIngresar.setVisibility(View.VISIBLE);

        } catch (Exception e)  {
            addlog(new Object() {}.getClass().getEnclosingMethod().getName() + "." , e.getMessage(),"");
        }
    }

    public void doMenu(View view) {
        startActivity(new Intent(this,Mainmenu.class));
    }

    private void setHandlers() {

        spinemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()         {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                try
                {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if(spinlabel != null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    }

                    idemp=empresas.items.get(position).IdEmpresa;
                    gl.IdEmpresa = idemp;
                    gl.gNomEmpresa = empresas.items.get(position).Nombre;
                    idbodega=0;

                    execws(2);//Lista las bodegas

                    if (idle) validaVersion();

                } catch (Exception e)
                {
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinbod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                try
                {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if(spinlabel != null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    }

                    idbodega=bodegas.items.get(position).IdBodega;
                    NomBodega = bodegas.items.get(position).Nombre;
                    gl.bloquear_lp_hh = bodegas.items.get(position).bloquear_lp_hh;
                    gl.IdBodega = idbodega;
                    gl.gNomBodega = NomBodega;
                    gl.gCapturaEstibaIngreso = bodegas.items.get(position).captura_estiba_ingreso;
                    gl.gCapturaPalletNoEstandar = bodegas.items.get(position).captura_pallet_no_estandar;
                    idimpres=0;
                    execws(3);

                } catch (Exception e)
                {
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinprint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                try
                {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel != null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    }

                    idimpres=impres.get(position).IdImpresora;
                    gl.MacPrinter =  impres.get(position).mac_adress;
                    gl.IdImpresora = idimpres;

                } catch (Exception e) {
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinuser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                try
                {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);

                    if (spinlabel != null){
                        spinlabel.setTextColor(Color.BLACK);
                        spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                        spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);
                    }

                    seloper =users.items.get(position);
                    iduser=users.items.get(position).IdOperador;
                    NomOperador = users.items.get(position).Nombre_Completo;
                    gl.IdOperador = iduser;
                    gl.gNomOperador = NomOperador;

                    txtpass.requestFocus();

                } catch (Exception e) {
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        txtpass.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {

                    imgIngresar.setVisibility(View.INVISIBLE);

                    Valida_Ingreso();

                    imgIngresar.setVisibility(View.VISIBLE);

                }

                return false;
            }
        });

        txtpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    showkeyb();
                }else{
                    hidekeyb();
                }
            }
        });


    }

    //endregion

    //region WebService handler

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel)  {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (ws.callback) {
                case 1:
                    progress.setMessage("Cargando empresas");
                    processEmpresas();break;
                case 2:
                    progress.setMessage("Cargando bodegas");
                    processBodegas();break;
                case 3:
                    progress.setMessage("Cargando impresoras");
                    processImpresoras();
                    iduser=0; execws(4); // Llama lista de usuarios
                    break;
                case 4:
                    progress.setMessage("Cargando usuarios");
                    processUsers();
                    //Llama al método del WS Get_cantidad_decimales_calculo
                    execws(5);
                    break;
                case 5:
                    processGetDecimalesCalculo();
                    execws(8);
                    break;
                case 6:
                    processGetDecimalesDespliegue();
                    break;
                case 7:
                    startActivity(new Intent(this,Mainmenu.class));
                    break;
                case 8:
                    processVersiones();
                    break;
                case 9:
                    processResolucionLP();
                    break;
                case 10:
                    processLicencia();
                    break;
                case 11:
                    processServidor();
                    break;

            }

            progress.cancel();

        } catch (Exception e)  {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void ejecuta(){
        startActivity(new Intent(this,Mainmenu.class));
    }

    private void Valida_Ingreso() {

        /*
        try {

            gl.IdEmpresa=1;
            gl.IdBodega=9;
            gl.IdOperador=1;
            gl.CodigoBodega="10";
            gl.OperadorBodega.IdOperadorBodega=1;

            clsBeOperador op=new clsBeOperador();
            op.setCodigo("1");
            op.setNombres("Op");
            op.setApellidos("1");
            gl.OperadorBodega.setOperador(op);

            ejecuta();
        } catch (Exception e) {
            String ss=e.getMessage();
        }

         */


        try{

            if (gl.IdEmpresa>0) {
                if (gl.IdBodega>0) {
                    if (gl.IdOperador>0) {
                        if (!txtpass.getText().toString().isEmpty())  {
                            List<clsBeBodegaBase> BeBodega =
                                    stream(bodegas.items)
                                            .where(c -> c.IdBodega  == gl.IdBodega)
                                            .toList();

                            gl.CodigoBodega = BeBodega.get(0).Codigo;

                            List<clsBeOperador_bodega> BeOperadorBodega =
                                    stream(users.items)
                                            .where(c -> c.Operador.IdOperador == gl.IdOperador & c.Operador.Clave.equals(txtpass.getText().toString()) &
                                                    c.IdBodega == gl.IdBodega)
                                            .orderBy(c-> c.Operador.IdOperador)
                                            .toList();

                            if (BeOperadorBodega.size()>0) {

                                gl.gOperadorBodega = BeOperadorBodega;
                                gl.OperadorBodega = gl.gOperadorBodega.get(0);

                                List<clsBeImpresora> BeImpresora =
                                        stream(impres)
                                                .where(c-> c.IdBodega == gl.IdBodega).toList();

                            if (BeImpresora.size()>0) {
                                gl.gImpresora = BeImpresora;
                                if (gl.gImpresora.get(0).Direccion_Ip =="") {
                                    progress.cancel();
                                    mu.msgbox("La impresora no está configurada correctamente (Expec: MAC/IP)");
                                } else {
                                    //#CKFK 20201021 Agregué este else para agregar_marcaje
                                    //execws(7);
                                    //#EJC20210504> Validar resolucion LP antes de ingresar.
                                    //execws(9);
                                    //#CKFK 20210914 Validar licencia antes de ingresar.Método loginHH
                                    execws(10);
                                }
                            } else  {
                                progress.cancel();
                                //CKFK 20201021 Cambié mensaje para que sea un si o no
                                msgAsk_continuar_sin_impresora("La impresora no está definida,¿Continuar sin impresora?");
                            }
                            } else {
                                progress.cancel();
                                mu.msgbox("Los datos ingresados para el operador no son válido, revise clave y bodega");
                            }

                        } else {
                            progress.cancel();
                            mu.msgbox("Ingrese clave");
                        }
                    } else  {
                        progress.cancel();
                        mu.msgbox("No se ha seleccionado un operador válido");
                    }
                }else {
                    progress.cancel();
                    mu.msgbox("No se ha seleccionado una bodega válida");
                }
            }else  {
                progress.cancel();
                mu.msgbox("No se ha seleccionado una empresa válida");
            }

        }catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }


    }

    private void msgAsk_continuar_sin_impresora(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.printicon);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  execws(7);
                  //ejecuta();
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    private void msgAsk_continuar_sin_resolucionLp(String msg) {

        try{

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(false);

            dialog.setTitle(R.string.app_name);
            dialog.setMessage("¿" + msg + "?");

            dialog.setIcon(R.drawable.printicon);

            dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    execws(7);
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }

    }

    public String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            return "";
        } else
        {
            return mBluetoothAdapter.getName();
        }
    }

    @SuppressLint("MissingPermission")
    private String androidid()  {
        String uniqueID="";
        try {
            uniqueID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        //    TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
          //  uniqueID = tm.getDeviceId();
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
            uniqueID="0000000000";
        }

        return uniqueID;
    }

    //endregion

    //region Data Processing

    private void processEmpresas() {
        try {
            empresas=xobj.getresult(clsBeEmpresaAndList.class,"Android_Get_All_Empresas");

            if(empresas != null){

                class EmpresaSort implements Comparator<clsBeEmpresaBase>                 {
                    public int compare(clsBeEmpresaBase left, clsBeEmpresaBase right)                    {
                        return left.Nombre.compareTo(right.Nombre);
                    }
                }

                Collections.sort(empresas.items, new EmpresaSort());

                fillSpinemp();

            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processBodegas()     {
        class BodegaSort implements Comparator<clsBeBodegaBase>   {
            public int compare(clsBeBodegaBase left, clsBeBodegaBase right) {
                return left.IdBodega - right.IdBodega;
            }
        }

        try  {
            bodegas=xobj.getresult(clsBeBodegaList.class,"Android_Get_Bodegas_By_IdEmpresa");
            Collections.sort(bodegas.items, new BodegaSort());
            fillSpinBod();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processImpresoras() {
        Cursor dt;
        clsBeImpresora imp;

        class ImprSort implements Comparator<clsBeImpresora> {
            public int compare(clsBeImpresora left, clsBeImpresora right) {
                return left.Nombre.compareTo(right.Nombre);
            }
        }

        try {
            impres.clear();

            dt=xobj.filldt();

            if (dt.getCount()>0) {
                dt.moveToFirst();
                while (!dt.isAfterLast()) {

                    imp=new clsBeImpresora();
                    imp.IdImpresora=dt.getInt(0);
                    imp.Nombre=dt.getString(2);
                    imp.mac_adress=dt.getString(9);
                    imp.Direccion_Ip=dt.getString(3);
                    imp.IdBodega = dt.getInt(10);
                    impres.add(imp);

                    dt.moveToNext();
                }
            }

            fillSpinImpres();
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processUsers() {

        String rootdir=Environment.getExternalStorageDirectory()+"/WMSFotos/";

        class UserSort implements Comparator<clsBeOperador_bodega>
        {
            public int compare(clsBeOperador_bodega left, clsBeOperador_bodega right)
            {
                return left.Nombre_Completo.compareTo(right.Nombre_Completo);
            }
        }

        try {

            users=xobj.getresult(clsBeOperador_bodegaList.class,"Get_Operadores_By_IdBodega_For_HH");

            if (users!=null)
            {
                if (users.items!=null)
                {
                    for (int i = users.items.size()-1; i>=0; i--)
                    {
                        if (users.items.get(i).Operador.Activo)
                        {
                            users.items.get(i).Nombre_Completo=users.items.get(i).Operador.Apellidos+" "+users.items.get(i).Operador.Nombres;

                        } else
                        {
                            users.items.remove(i);
                        }
                    }

                    fillSpinUser();
                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processGetDecimalesDespliegue()     {
        try
        {

            gl.gCantDecDespliegue = (Integer) xobj.getSingle("Get_Cantidad_decimales_despliegueResult",Integer.class);

        }catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processGetDecimalesCalculo(){

        try
        {

            gl.gCantDecCalculo = (Integer) xobj.getSingle("Get_cantidad_decimales_calculoResult",Integer.class);

            //Llama al metodo del WS Get_cantidad_decimales_calculo
            //GT 16082021: no lo ejecuta!! se traslado a processVersiones
            //execws(6);

        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processVersiones() {
        try {
            versiones=xobj.getresult(clsBeVersion_wms_hh_andList.class,"Android_Get_All_Versiones");
            if (versiones!=null){
                validaVersion();
            }
            idle=true;
            execws(6);
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processResolucionLP() {
        try {
            ResolucionLpByBodega =xobj.getresult(clsBeResolucion_lp_operador.class,"Get_Resoluciones_Lp_By_IdOperador_And_IdBodega");
            if (ResolucionLpByBodega !=null){
                //#EJC20210504: Registra ingreso y carga menú principal.
                execws(7);
            }else{
                msgAsk_continuar_sin_resolucionLp("El operador no tiene definida resolución de etiquetas para LP");
            }
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private boolean processLicencia() {
        boolean rslt=false;
        try {

            Integer msgLic = 0;

            msgLic =(Integer) xobj.getSingle("loginHandHeldResult",Integer.class);

            if (msgLic == 0){
                msgbox("Licencia inactiva");
                rslt= false;
            }else if (msgLic < 0){
                //Llama al método nombreServidorLicencias
               execws(11);
               rslt= false;
            } else{
                rslt= true;
                //LLama el método Get_Resoluciones_Lp_By_IdOperador_And_IdBodega
                execws(9);
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
        return rslt;
    }

    private void processServidor() {
        String servidor="";
        try {

            servidor =(String) xobj.getSingle("nombreServidorLicenciasResult",String.class);
            msgbox("El ordenador: " + gl.devicename + " ha enviado una solicitud de licencia al servidor de licencias: " + servidor);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    //endregion

    //region Spinners

    private void fillSpinemp()  {

        String filePathImg = null;

        try    {
            emplist.clear();

            String dirLogosEmpresa = getApplicationContext().getFilesDir() + "/LogosEmpresa/";
            int vCodigoEmpresa;

            File dir = new File(getApplicationContext().getFilesDir(), "LogosEmpresa");
            if(!dir.exists()){
                dir.mkdir();
            }

            for (int i = 0; i <empresas.items.size(); i++)             {
                vCodigoEmpresa = empresas.items.get(i).getIdEmpresa();
                emplist.add(empresas.items.get(i).Nombre);

                try {

                    String img = empresas.items.get(i).Imagen;

                    if (img != null) {

                        if (i==0){
                            File f = new File(dirLogosEmpresa);
                            if(!f.isDirectory()) f.mkdir();
                        }

                        filePathImg = dirLogosEmpresa + vCodigoEmpresa + ".png";
                        File file = new File(filePathImg);

                        if (!file.exists()) {
                            byte[] imgbytes = Base64.decode(img, Base64.DEFAULT);
                            int bs = imgbytes.length;
                            FileOutputStream fos = new FileOutputStream(filePathImg);
                            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
                            outputStream.write(imgbytes);
                            outputStream.close();
                        }
                    }

                } catch (Exception ee) {
                    Log.e("ImgOp", ee.getMessage());
                }

            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, emplist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinemp.setAdapter(dataAdapter);

            if (emplist.size()>0) spinemp.setSelection(0);

            //#EJC20210223: Set las image get from empresa, por ahora solo funcionará para una empresa, en el futuro lo debo mejorar para que funcione para N
            if (filePathImg != null) {
                Bitmap bmImg = BitmapFactory.decodeFile(filePathImg);
                imgEmpresaLogin.setImageBitmap(bmImg);
            }


        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void fillSpinBod() {
        String ss;

        try {

            bodlist.clear();

            for (int i = 0; i <bodegas.items.size(); i++)
            {
                bodlist.add(bodegas.items.get(i).Codigo + " - " + bodegas.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinbod.setAdapter(dataAdapter);

            if (bodlist.size()>0) spinbod.setSelection(0);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void fillSpinImpres() {
        try
        {
            prnlist.clear();

            for (int i = 0; i < impres.size(); i++)
            {
                prnlist.add(impres.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, prnlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinprint.setAdapter(dataAdapter);

            if (prnlist.size()>0) spinprint.setSelection(0);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void fillSpinUser() {
        try
        {
            userlist.clear();

            for (int i = 0; i <users.items.size(); i++)
            {
                userlist.add(users.items.get(i).Nombre_Completo);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinuser.setAdapter(dataAdapter);

            if (userlist.size()>0)
            {
                spinuser.setSelection(0);
                seloper =users.items.get(0);

                txtpass.requestFocus();
                //showkeyb();

            }

        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }

    }

    private void getURL() {

        gl.wsurl = "http://192.168.0.101/WSTOMHH_QA/TOMHHWS.asmx";

        gl.wsurl="";

        try {

            File file1 = new File(Environment.getExternalStorageDirectory(), "/tomws.txt");

            if (file1.exists())
            {
                FileInputStream fIn = new FileInputStream(file1);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                gl.wsurl = myReader.readLine();
                myReader.close();
            }

        } catch (Exception e)
        {
            gl.wsurl ="";
        }

        if (!gl.wsurl.isEmpty()) {
            lblurl.setText(gl.wsurl);
        }else {
            lblurl.setText("Falta archivo con URL");
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

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    protected void onResume() {
        try  {
            Load();
            super.onResume();
        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    public class WebServiceHandler extends WebService  {

        public WebServiceHandler(PBase Parent,String Url) {
            super(Parent,Url);
        }

        @Override
        public void wsExecute(){
            try  {
                switch (ws.callback) {
                    case 1:
                        callMethod("Android_Get_All_Empresas");
                        break;
                    case 2:
                        callMethod("Android_Get_Bodegas_By_IdEmpresa","IdEmpresa",idemp);
                        break;
                    case 3:
                        callMethod("Get_All_Impresora_By_IdEmpresa_And_IdBodega_Dt",
                                "IdEmpresa",idemp,"IdBodega",idbodega);
                        break;
                    case 4:
                        callMethod("Get_Operadores_By_IdBodega_For_HH","IdBodega",idbodega);
                        break;
                    case 5:
                        callMethod("Get_cantidad_decimales_calculo","pIdEmpresa",gl.IdEmpresa);
                        break;
                    case 6:
                        callMethod("Get_Cantidad_decimales_despliegue","pIdEmpresa",gl.IdEmpresa);
                        break;
                    case 7:
                        //#CKFK 20200416
                        //En el paramétro pIdDispositivo debe enviarse el Id del dispositivo en base al gl.deviceId
                        //EJC debe crear la tabla que contenga el Id correspondiente al gl.deviceId que actualmente no existe en el WMS
                        callMethod("Agregar_Marcaje","pIdEmpresa",gl.IdEmpresa,
                                "pIdBodega",gl.IdBodega,"pIdOperador",gl.IdOperador,"pIdDispositivo",1,"pEsSalida",false);
                        //ejecuta();
                        break;
                    case 8:
                        callMethod("Android_Get_All_Versiones");
                        break;
                    case 9:
                        callMethod("Get_Resoluciones_Lp_By_IdOperador_And_IdBodega",
                                "pIdOperador",gl.IdOperador,
                                "pIdBodega",gl.IdBodega);
                        break;
                    case 10:
                        callMethod("loginHandHeld",
                                "idHandHeld",gl.deviceId,
                                "nombreHanHeld",gl.devicename);
                        break;
                    case 11:
                        callMethod("nombreServidorLicencias");
                        break;
                }
            } catch (Exception e)  {
                error=e.getMessage();errorflag =true;
                msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
            }
        }
    }

    //endregion

    //region Control Version

    private void validaVersion() {

        int pp,idemp=0;


        if (empresas.items.size()==0) return;

        try {
            pp=spinemp.getSelectedItemPosition();
            idemp=empresas.items.get(pp).IdEmpresa;

            if (versiones!=null){
                for (int i = 0; i <versiones.items.size(); i++) {
                    if (versiones.items.get(i).IdEmpresa==idemp) {
                        if (!versiones.items.get(i).Version.equalsIgnoreCase(version)) {
                            actualizaVersion();return;
                        }
                    }
                }
            }

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }

    }

//    private void validaResolucionLP() {
//
//        try {
//
//            if (ResolucionLpByBodega !=null){
//                if (ResolucionLpByBodega.items.size() == 0){
//                    msgAsk_continuar_sin_resolucionLp("El operador no tiene definida resolucion de etiquetas para LP");
//                }else{
//                    execws(7);
//                }
//            }
//
//        } catch (Exception e) {
//            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
//        }
//
//    }

    private void actualizaVersion() {
        try {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.dts.mposupd");
            intent.putExtra("filename","tom.apk");
            this.startActivity(intent);
        } catch (Exception e) {
            msgbox("No está instalada aplicación para actualización de versiónes, por favor informe soporte.");
        }
    }

    //endregion

}
