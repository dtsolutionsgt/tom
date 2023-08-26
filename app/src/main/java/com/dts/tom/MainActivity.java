package com.dts.tom;

import static br.com.zbra.androidlinq.Linq.stream;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.dts.base.ExDialog;
import com.dts.base.NetWorkInfoUtility;
import com.dts.base.WebService;
import com.dts.base.XMLObject;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaBase;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodegaList;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresaAndList;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresaBase;
import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodega;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodegaList;
import com.dts.classes.Mantenimientos.Resolucion_LP.clsBeResolucion_lp_operador;
import com.dts.classes.Mantenimientos.Version.clsBeVersion_wms_hh_andList;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends PBase implements ForceUpdateChecker.OnUpdateNeededListener {

    private Spinner spinemp,spinbod,spinprint;
    //#GT26062023: se debe digitar en lugar de seleccionar,spinuser;
    private EditText txtpass;
    private EditText txtUser;
    private TextView lblver,lbldate,lblurl, lblVersion, txtMensajeDialog, lblManufacturadorEquipo, lblModeloEquipo;
    //private ProgressDialog progress;
    Dialog progress;
    private ImageView imgIngresar;
    private ImageView imgEmpresaLogin;

    private WebServiceHandler ws;
    private XMLObject xobj;

    private clsBeEmpresaAndList empresas = new clsBeEmpresaAndList();
    private clsBeBodegaList bodegas = new clsBeBodegaList();
    private final ArrayList<clsBeImpresora> impres = new ArrayList<clsBeImpresora>();
    private clsBeOperador_bodegaList users = new clsBeOperador_bodegaList();
    private final clsBeVersion_wms_hh_andList versiones = new clsBeVersion_wms_hh_andList();
    private clsBeResolucion_lp_operador ResolucionLpByBodega = new clsBeResolucion_lp_operador();
    private clsBeOperador_bodega seloper=new clsBeOperador_bodega();

    private final ArrayList<String> emplist= new ArrayList<String>();
    private final ArrayList<String> bodlist= new ArrayList<String>();
    private final ArrayList<String> prnlist= new ArrayList<String>();
    private final ArrayList<String> userlist= new ArrayList<String>();

    private int idemp=0,idbodega=0,idimpres=0,iduser=-1,ii;
    private String NomOperador, NomBodega;
    private boolean idle=false;

    private FirebaseAnalytics mFirebaseAnalytics;

    private String mensaje_progress ="";

    NetWorkInfoUtility netWorkInfoUtility = new NetWorkInfoUtility();

    private final boolean IsNetWorkAvailable =false;

    private String Modelo_Equipo ="";
    private String Manufacturador_Equipo = "";

    private RelativeLayout relbot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ProgressDialog("Inicializando...");

            //#CKFK20220215 Agregué esta variable de control para evitar que entre al Load dos veces, la inicializo al entrar
            browse = 1;

            grantPermissions();

            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

            gl.PathDataDir = this.getApplicationContext().getDataDir().getPath();

            Boolean Equipo_Valido = false;

            for (String element:gl.listValidDevices) {
                if (element.contains(Modelo_Equipo)){
                    Equipo_Valido=true;
                }
            }

            lblModeloEquipo.setText(Modelo_Equipo);
            lblManufacturadorEquipo.setText(Manufacturador_Equipo);

            if(!Equipo_Valido){
                progress.cancel();
                msgAskDispositivoValido("El equipo:" + Modelo_Equipo + " en el que está ejecutando el WMS, no es un equipo certificado para su uso.");
            }else{
                lblModeloEquipo.setTextColor(Color.parseColor("#1B5E20"));
                lblManufacturadorEquipo.setTextColor(Color.parseColor("#1B5E20"));
                Log.d("Modelo Equipo: ", Modelo_Equipo + "es válido para ejecución de WMS.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //#EJC20220110: To log especific events.
//    private void LogFireBase(){
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
//    }

    private void startApplication() {

        try {

            super.InitBase();

            spinemp = findViewById(R.id.spinner);
            spinbod = findViewById(R.id.spinner2);
            spinprint = findViewById(R.id.spinner3);
            // = findViewById(R.id.spinner4);
            txtpass = findViewById(R.id.editText3);
            //#GT26062023: se obtiene el usuario digitado
            txtUser = findViewById(R.id.userText);
            lblver= findViewById(R.id.txtNoVersion);
            lbldate= findViewById(R.id.txtFechaVersion);
            lblurl= findViewById(R.id.txtURLWS);lblurl.setText("");
            lblVersion = findViewById(R.id.lblVersion);
            imgIngresar = findViewById(R.id.imageView11);
            imgEmpresaLogin = findViewById(R.id.imgEmpresaLogin);
            lblModeloEquipo = findViewById(R.id.lblModeloEquipo);
            lblManufacturadorEquipo = findViewById(R.id.lblManufacturadorEquipo);
            relbot = findViewById(R.id.relbot);

            lblver.setText("Versión: " +  gl.version);
            //lblVersion.setText("V. "+ gl.version);

            getURL();

            if (!gl.wsurl.isEmpty()){
                ws= new WebServiceHandler(MainActivity.this, gl.wsurl);
                xobj= new XMLObject(ws);
                setHandlers();
            }

            //'#EJC2023020610120: Buscar siempre el ID Marca y Modelo.
            gl.deviceId =androidid();
            gl.devicename = getDeviceName();//getLocalBluetoothName();

            try {

                String orddir=gl.PathDataDir + "/tomtask";
                File directory = new File(orddir);
                directory.mkdirs();

            } catch (Exception e) {

            }

            //#GT25072023: validamos el input de usuario y limitados los digitos
            //txtUser.filters = arrayOf<InputFilter>(MinMaxFilter(1, 100))

            Load();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }

    }



    public String getDeviceName() {
        Manufacturador_Equipo = Build.MANUFACTURER;
        Modelo_Equipo= Build.MODEL;
        if (Modelo_Equipo.startsWith(Manufacturador_Equipo)) {
            //return capitalize(Modelo_Equipo);
        }
        return capitalize(Manufacturador_Equipo) + " " + Modelo_Equipo;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    private void Load(){

        try{

            progress_setMessage("Cargando empresas...");
            progress.show();

            //#CKFK20220215 Agregué esta variable de control para evitar que entre al Load dos veces, aquí le cambio el valor
            browse = 2;

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
            txtUser.setText("");
            txtpass.setText("");
            spinemp.setAdapter(null);
            spinbod.setAdapter(null);
            spinprint.setAdapter(null);
            //#GT26062023: ya no se llena visualmente el combo.
            //spinuser.setAdapter(null);


        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    private void setURL(){

        String url="";

        try{

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Conexión");
            alert.setMessage("Ingrese la URL:");

            alert.setIcon(R.drawable.ic_quest);

            final EditText input = new EditText(this);
            input.setText(url);
            input.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            if (!lblurl.getText().toString().isEmpty()){
                input.setText(lblurl.getText());
            }else{
                //input.setText("http://192.168.0.13/WCFTOM4/TOMHHWS.asmx");
                input.setText("http://10.10.20.181/WCFTOM4/tomhhws.asmx");
            }
            alert.setView(input);
            input.requestFocus();

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    msgbox("");
                }
            });

            alert.setPositiveButton("Guardar", (dialog, whichButton) -> {

                gl.wsurl=input.getText().toString();

                if(!gl.wsurl.isEmpty()){
                    guardaDatosConexion();
                }else{
                    toast("Debe ingresar la URL");
                    setURL();
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

            String fname = gl.PathDataDir+"/tomws.txt";
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
                    startApplication();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                //#CKFK20220215 Quité el que se vuelva a iniciar la aplicación por esta opción probé varias veces desinstalando
                //y no me dio problemas
               // startApplication();
            } else
            {
                super.finish();
            }
        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    public void setUrlOntxtURLWSClic(View view){

        try {
            setURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doLogin(View view){

        try{

            imgIngresar.setVisibility(View.INVISIBLE);

            progress.show();
            progress_setMessage("Ingresando....");

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

    public int contador=1;
    public void HabilitarWSLabel(View view) {

        if(contador<5){
            toast("Está a "  + (5 -contador) + " pasos de habilitar la URL");
            contador++;
        } else if (contador==5) {

            if (!lblurl.isShown()){
                toast("URL habilitada");
                lblurl.setVisibility(View.VISIBLE);
            }else{
                lblurl.setVisibility(View.GONE);
            }
            contador=0;
        }
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
                    gl.buscar_actualizacion_hh = empresas.items.get(position).buscar_actualizacion_hh;
                    idbodega=0;

                    execws(2);//Lista las bodegas

                    //#GT valida si busca o no update
                    if (gl.buscar_actualizacion_hh){

                        if (idle) validaVersion();
                    }


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
                    gl.gPriorizar_UbicRec_Sobre_UbicEst = bodegas.items.get(position).priorizar_ubicrec_sobre_ubicest;
                    gl.gUbicMerma = bodegas.items.get(position).ubic_merma;
                    gl.gUbicProdNe = bodegas.items.get(position).ubic_producto_ne;
                    gl.IdProductoEstadoNE = bodegas.items.get(position).IdProductoEstadoNE;
                    gl.Mostrar_Area_En_HH = bodegas.items.get(position).Mostrar_Area_En_HH;
                    gl.confirmar_codigo_en_picking = bodegas.items.get(position).confirmar_codigo_en_picking;
                    gl.inferir_origen_en_cambio_ubic = bodegas.items.get(position).inferir_origen_en_cambio_ubic;
                    gl.operador_picking_realiza_verificacion= bodegas.items.get(position).Operador_Picking_Realiza_Verificacion;
                    gl.Permitir_Cambio_Ubic_Producto_Picking = bodegas.items.get(position).Permitir_Cambio_Ubic_Producto_Picking;
                    gl.Notificacion_Voz = bodegas.items.get(position).Notificacion_Voz;
                    gl.TipoPantallaPicking = bodegas.items.get(position).tipo_pantalla_picking;
                    gl.VerificacionConsolidada = bodegas.items.get(position).Verificacion_Consolidada;
                    gl.TipoPantallaRecepcion = bodegas.items.get(position).tipo_pantalla_recepcion;
                    gl.TipoPantallaVerificacion = bodegas.items.get(position).tipo_pantalla_verificacion;
                    gl.Permitir_Buen_Estado_En_Reemplazo = bodegas.items.get(position).Permitir_Buen_Estado_En_Reemplazo;
                    gl.Permitir_Decimales = bodegas.items.get(position).Permitir_Decimales;
                    gl.Dias_Maximo_Vencimiento_Reemplazo = bodegas.items.get(position).Dias_Maximo_Vencimiento_Reemplazo;
                    gl.Permitir_Repeticiones_En_Ingreso = bodegas.items.get(position).Permitir_Repeticiones_En_Ingreso;
                    gl.Calcular_Ubicacion_Sugerida_ML = bodegas.items.get(position).Calcular_Ubicacion_Sugerida_ML;
                    gl.validar_disponibilidad_ubicaicon_destino = bodegas.items.get(position).validar_disponibilidad_ubicaicon_destino;
                    gl.Permitir_Reemplazo_Picking = bodegas.items.get(position).Permitir_Reemplazo_Picking;
                    gl.Permitir_No_Encontrado_Picking = bodegas.items.get(position).Permitir_No_Encontrado_Picking;
                    gl.Permitir_Reemplazo_Verificacion = bodegas.items.get(position).Permitir_Reemplazo_Verificacion;

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

                    txtUser.requestFocus();

                } catch (Exception e) {
                    msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

    /*    spinuser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
                    gl.beOperador = users.items.get(position).Operador;
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

        });*/

        txtpass.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
            {

                imgIngresar.setVisibility(View.INVISIBLE);

                Valida_Ingreso();

                imgIngresar.setVisibility(View.VISIBLE);

            }

            return false;
        });

        txtpass.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                showkeyb();
            }else{
                hidekeyb();
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
                    progress_setMessage("Cargando empresas");
                    processEmpresas();break;
                case 2:
                    progress_setMessage("Cargando bodegas");
                    processBodegas();break;
                case 3:
                    progress_setMessage("Cargando impresoras");
                    processImpresoras();
                    //#GT26062023: ya no se utiliza iduser, se obtiene directo del objeto
                    //iduser=0;
                    //execws(4); // Llama lista de usuarios
                    //#GT26062023: se omite la carga de usuarios, y pasamos a Parametros A
                    execws(5);
                    break;
                case 4:
                    progress_setMessage("Cargando operadores");
                    //processUsers();
                    //Llama al método del WS Get_cantidad_decimales_calculo
                    //execws(5);
                    break;
                case 5:
                    progress_setMessage("Obteniendo Parámetros A");
                    processGetDecimalesCalculo();
                    execws(8);
                    break;
                case 6:
                    progress_setMessage("Obteniendo Parámetros B");
                    processGetDecimalesDespliegue();
                    progress.cancel(); //#EJC20220118: Terminó el proceso de carga de combos login
                    break;
                case 7:
                    Intent i = new Intent(this, Mainmenu.class);
                    i.putExtra("version", gl.version);
                    startActivity(i);
                    progress.cancel();
                    //startActivity(new Intent(this,Mainmenu.class));
                    break;
                case 8:
                    progress_setMessage("Validando versión");
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
                case 12:
                    processLoginOperador();
                    break;
            }

        } catch (Exception e)  {
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }finally {
            //progress.cancel();
        }
    }

    private void processLoginOperador() {

        try{

            clsBeOperador_bodega operador_bodega = new clsBeOperador_bodega();

            operador_bodega = xobj.getresult(clsBeOperador_bodega.class,"Login_Operador_By_Codigo_By_IdBodega");

            List<clsBeBodega> BeBodega =
                    stream(bodegas.items)
                            .where(c -> c.IdBodega  == gl.IdBodega)
                            .toList();

            gl.CodigoBodega = BeBodega.get(0).Codigo;

            //#EJC20220129_1430: Set validar_disponibilidad_ubicaicon_destino
            gl.validar_disponibilidad_ubicaicon_destino = BeBodega.get(0).validar_disponibilidad_ubicaicon_destino;

            if (operador_bodega != null) {

                //gl.gOperadorBodega = operador_bodega;
                String nombre_completo =operador_bodega.Operador.Nombres +" "+ operador_bodega.Operador.Apellidos;
                gl.OperadorBodega = operador_bodega;
                gl.OperadorBodega.Nombre_Completo = nombre_completo;
                //#GT26062023: estos campos se llenaban al tomar valor del combo
                gl.beOperador = operador_bodega.Operador;
                gl.IdOperador = operador_bodega.IdOperador;
                gl.gNomOperador = nombre_completo;

                List<clsBeImpresora> BeImpresora =
                        stream(impres)
                                .where(c-> c.IdBodega == gl.IdBodega).toList();

                if (BeImpresora.size()>0) {
                    gl.gImpresora = BeImpresora;
                    if (gl.gImpresora.get(0).Direccion_Ip =="") {
                        //progress.cancel();
                        mu.msgbox("La impresora no está configurada correctamente (Expec: MAC/IP)");
                    } else {
                        //#CKFK 20201021 Agregué este else para agregar_marcaje
                        //execws(7);
                        //#EJC20210504> Validar resolucion LP antes de ingresar.
                        execws(9);
                        //#CKFK 20220506 Validar licencia antes de ingresar método loginHH
                        //se dejó en comentario por solicitud de EJC
                        //execws(10);
                    }
                } else  {
                    //progress.cancel();
                    //CKFK 20201021 Cambié mensaje para que sea un si o no
                    msgAsk_continuar_sin_impresora("La impresora no está definida, ¿Continuar sin impresora?");
                }
            } else {
                progress.cancel();
                mu.msgbox("Código o contraseña incorrecta, verifique datos.");
            }
        }
        catch (Exception e){
            progress.cancel();
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }

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
                    //if (gl.IdOperador>0) {
                    if (!TextUtils.isEmpty(txtUser.getText())   || txtUser.length() > 0) {
                        if (!txtpass.getText().toString().isEmpty())  {
                            //#GT26062023: toda la codificación de aqui, se paso al processLoginOperador
                            execws(12);

                        } else {
                            progress.cancel();
                            mu.msgbox("Ingrese clave");
                        }
                    } else  {
                        progress.cancel();
                        //mu.msgbox("No se ha seleccionado un operador válido");
                        mu.msgbox("No se ha ingresado un operador válido");
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
            dialog.setMessage(msg);
            dialog.setIcon(R.drawable.printicon);
            dialog.setPositiveButton("Si", (dialog1, which) -> {
                //#CKFK20220506 Cambie el execws(9) por el execws(10)
                execws(10);
            });
            dialog.setNegativeButton("No", (dialog12, which) -> execws(10));
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
            dialog.setPositiveButton("Si", (dialog1, which) -> execws(7));
            dialog.setNegativeButton("No", (dialog12, which) -> execws(7));
            dialog.show();

        }catch (Exception e){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
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

            String jsonArray = ws.xmlresult;
            List<clsBeEmpresaBase> vempresas = gl.getList(jsonArray, clsBeEmpresaBase.class);

            if(vempresas != null){
                empresas = new clsBeEmpresaAndList();
                empresas.items = vempresas;
            }

            if(empresas != null){

                if(empresas.items != null){

                    class EmpresaSort implements Comparator<clsBeEmpresaBase>                 {
                        public int compare(clsBeEmpresaBase left, clsBeEmpresaBase right)                    {
                            return left.Nombre.compareTo(right.Nombre);
                        }
                    }

                    Collections.sort(empresas.items, new EmpresaSort());

                    fillSpinemp();

                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
            Log.e("Error al listar empresas", e.getMessage());
        }
    }

    private void processBodegas(){

        try  {

            bodegas=xobj.getresult(clsBeBodegaList.class,"Android_Get_Bodegas_By_IdEmpresa");

            if(bodegas != null){

                if(bodegas.items != null){

                    class BodegaSort implements Comparator<clsBeBodegaBase>   {
                        public int compare(clsBeBodegaBase left, clsBeBodegaBase right) {
                            return left.IdBodega - right.IdBodega;
                        }
                    }

                    Collections.sort(bodegas.items, new BodegaSort());
                    fillSpinBod();

                }

            }else{
                msgbox("No se obtuvieron bodegas");
                progress.cancel();
            }

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
            Log.e("Error al listar empresas", e.getMessage());
        }
    }

    private void processUsers() {

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
                            users.items.get(i).Nombre_Completo=users.items.get(i).Operador.Nombres+" "+users.items.get(i).Operador.Apellidos;

                        } else
                        {
                            users.items.remove(i);
                        }
                    }

                    //fillSpinUser();
                    txtUser.requestFocus();
                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processGetDecimalesDespliegue() {
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

        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    private void processVersiones() {

        try {

            //#GT23052022: se valida nuevamente si busca o no update de la versión, según la empresa en BOF
            if (gl.buscar_actualizacion_hh){
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
                gl.TieneResoluciones = true;
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

            progress.cancel();
            servidor =(String) xobj.getSingle("nombreServidorLicenciasResult",String.class);
            msgbox("El dispositivo: " + gl.devicename + " ha enviado una solicitud de licencia al servidor de licencias: " + servidor);

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    //endregion

    //region Spinners

    private void fillSpinemp()  {

        String filePathImg = null;

        try{

            emplist.clear();

            String dirLogosEmpresa = getApplicationContext().getFilesDir() + "/LogosEmpresa/";
            int vCodigoEmpresa;

            File dir = new File(getApplicationContext().getFilesDir(), "LogosEmpresa");
            if(!dir.exists()){
                dir.mkdir();
            }

            for (int i = 0; i <empresas.items.size(); i++){

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

            if (emplist.size()>0){
                spinemp.setSelection(0);
            }

            //#EJC20210223: Set las image get from empresa, por ahora solo funcionará para una empresa, en el futuro lo debo mejorar para que funcione para N
            if (filePathImg != null) {
                Bitmap bmImg = BitmapFactory.decodeFile(filePathImg);
                imgEmpresaLogin.setImageBitmap(bmImg);
            }


        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
            Log.e("Error en fillspin", e.getMessage());
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

        try{

            userlist.clear();

            for (int i = 0; i <users.items.size(); i++)
            {
                userlist.add(users.items.get(i).Nombre_Completo);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spinuser.setAdapter(dataAdapter);

           /* if (userlist.size()>0) {
                spinuser.setSelection(0);
                seloper =users.items.get(0);

                txtpass.requestFocus();
                //showkeyb();
            }*/

        } catch (Exception e)
        {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }

    }

    private void getURL() {

        gl.wsurl="";

        try {

            //#EJC20220118: reemplazo, por Android 11, context datadir.
            if (gl.PathDataDir.isEmpty()){
                  gl.PathDataDir = this.getApplicationContext().getDataDir().getPath();
            }

            String pathText = gl.PathDataDir + "/tomws.txt";
            File file1 = new File(pathText);

            if (file1.exists())
            {
                FileInputStream fIn = new FileInputStream(file1);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                gl.wsurl = myReader.readLine();
                myReader.close();
            }else{
                progress.cancel();
                setURL();
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

    private final Runnable mUpdate = new Runnable() {

        public void run() {

            txtMensajeDialog.setText(mensaje_progress);
            txtMensajeDialog.postDelayed(this, 1000);

        }
    };

    public void progress_setMessage(String mensaje){
        try {
            if(progress!=null){
                runOnUiThread(() -> {
                    txtMensajeDialog = progress.findViewById(R.id.txtMensajeDialog);
                    if(txtMensajeDialog!=null){
                        txtMensajeDialog.setText(mensaje);
                        mensaje_progress = mensaje;
                        txtMensajeDialog.postDelayed(mUpdate,0);
                    }
                });
            }else{
                Log.println(Log.DEBUG,"Progress","Isnull");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProgressDialog(String mensaje){

        progress= new Dialog(this);
        progress.setContentView(R.layout.dialog_loading);
        progress.setCancelable(false);
        Window window=progress.getWindow();

       /* if(window!=null){
            window.setBackgroundDrawable(new ColorDrawable(0));
        }*/

        runOnUiThread(() -> {
            txtMensajeDialog= progress.findViewById(R.id.txtMensajeDialog);
            if(txtMensajeDialog!=null){
                txtMensajeDialog.setText(mensaje);
            }
        });

        progress.show();

    }

    private void execws(int callbackvalue) {
        ws.callback=callbackvalue;
        ws.execute();
    }

    protected void onResume() {
        try  {
            //#CKFK20220215 Agregué esta variable de control para evitar que entre al Load dos veces
            if (browse==1){
                Load();
            }

            txtUser.setText("");
            txtpass.setText("");
            super.onResume();
        } catch (Exception e) {
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
        }
    }

    @Override
    public void onUpdateNeeded(String updateUrl) {

        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        updateUrl = remoteConfig.getString("KEY_UPDATE_URL");
        redirectStore(updateUrl);

    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
                        callMethodJsonPost("Android_Get_All_Empresas_Json");
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
                    case 12:
                        //#GT21072023: hago login y en el ws se inserta el intento de sesión
                        //#GT21072023: intento de commit y push
                        callMethod("Login_Operador_By_Codigo_By_IdBodega",
                                "codigo",txtUser.getText().toString().trim(),
                                "clave",txtpass.getText().toString().trim(),
                                "host",gl.Ip_Device,
                                "IdBodega",idbodega);
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

        try {

            if (empresas.items.size()==0) throw new Exception("No se obtuvieron Empresas");

            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            String VersionRemoteConfigFireBase = remoteConfig.getString("KEY_CURRENT_VERSION");
            String Nueva_Version_FireBaseConPuntos = VersionRemoteConfigFireBase;
            String Nueva_Version_FireBase = Nueva_Version_FireBaseConPuntos.replace(".","");
            String versionActual = gl.version.replace(".","");

            Log.d("version_firebase",VersionRemoteConfigFireBase);

            if (!Nueva_Version_FireBase.isEmpty()){

                long vNuevaVersionFireBase = Long.parseLong(Nueva_Version_FireBase);
                long vVersionActualHH = Long.parseLong(versionActual);

                if(vNuevaVersionFireBase > vVersionActualHH)
                {
                    msgAskActualizarVersion("La versión actual es: "  + gl.version + " ¿Actualizar a versión: " + Nueva_Version_FireBaseConPuntos + "?");
                    return;
                }
            }

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void msgAskActualizarVersion(String msg) {

        ExDialog dialog = new ExDialog(this);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Si", (dialog1, which) -> {
            try {
                actualizaVersion();
            } catch (Exception e) {
                msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
            }
        });

        dialog.setNegativeButton("No", (dialog12, which) -> {});
        dialog.show();

    }

    private void actualizaVersion() {
        try {

            //force_update
            ForceUpdateChecker.with(this).onUpdateNeeded(this).do_update();

//#EJC20220422
//            Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.dts.mposupd");
//            intent.putExtra("filename","tom.apk");
//            this.startActivity(intent);

        } catch (Exception e) {
            msgbox("No está instalada aplicación para actualización de versiónes, por favor informe soporte.");
        }
    }

    //endregion

    private void msgAskDispositivoValido(String msg) {

        ExDialog dialog = new ExDialog(this);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", (dialog1, which) -> {
            try {
                //actualizaVersion();
            } catch (Exception e) {
                msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
            }
        });
        dialog.show();
    }


}
