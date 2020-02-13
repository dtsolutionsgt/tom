package com.dts.tom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.dts.classes.clsBeBodega;
import com.dts.classes.clsBeMenu_sistema;
import com.dts.classes.clsBeOperador_bodega;
import com.dts.classes.clsBePaises;
import com.dts.tom.Transacciones.CambioUbicacion.frm_tareas_cambio_ubicacion;
import com.dts.ws.GetAllEmpresasForHH;
import com.dts.ws.GetAllImpresoraByEmpresa;
import com.dts.ws.GetBodegasByEmpresaForHH;
import com.dts.ws.GetOperadoresByBodegaForHH;
import com.dts.ws.IvanWebService;
import com.dts.ws.OperadorValidoForHH;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends PBase {

    private Spinner spinemp,spinbod,spinprint,spinuser;
    private EditText txtpass;
    private TextView lblver,lbldate,lblurl;
    private ProgressBar pbar;

    private GetAllEmpresasForHH wsemp;
    private GetBodegasByEmpresaForHH wsbod;
    private GetOperadoresByBodegaForHH wsuser;
    private GetAllImpresoraByEmpresa wsprn;
    private OperadorValidoForHH wsoper;

    private ArrayList<String> emplist= new ArrayList<String>();
    private ArrayList<String> bodlist= new ArrayList<String>();
    private ArrayList<String> prnlist= new ArrayList<String>();
    private ArrayList<String> userlist= new ArrayList<String>();

    private clsBeOperador_bodega seloper=new clsBeOperador_bodega();

    private int callhandle,idemp,idbodega,idimpres,iduser=-1;
    private String xmlstr;

    private String wserror;
    private boolean errflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

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
            lblver=(TextView)  findViewById(R.id.textView4);
            lbldate=(TextView)  findViewById(R.id.textView3);
            lblurl=(TextView)  findViewById(R.id.textView9);lblurl.setText("");
            pbar=(ProgressBar)  findViewById(R.id.progressBar);

            getURL();

            setHandlers();

            wsemp = new GetAllEmpresasForHH(MainActivity.this, gl.wsurl);
            wsbod = new GetBodegasByEmpresaForHH(MainActivity.this, gl.wsurl);
            wsuser = new GetOperadoresByBodegaForHH(MainActivity.this, gl.wsurl);
            wsprn = new GetAllImpresoraByEmpresa(MainActivity.this, gl.wsurl);
            wsoper = new OperadorValidoForHH(MainActivity.this, gl.wsurl);

            fillLogin();

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + "." + e.getMessage());
        }
    }

    //region Grant permissions

    private void grantPermissions() {

        try {
            if (Build.VERSION.SDK_INT >= 20) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    startApplication();
                } else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }

        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startApplication();
            } else {
                super.finish();
            }
        } catch (Exception e) {
            msgbox(new Object() {}.getClass().getEnclosingMethod().getName() + " . " + e.getMessage());
        }
    }

    //endregion

    //region Events

    public void doLogin(View view) {
        //checkLogin();
        doTest();
     }

    public void doMenu(View view) {
        startActivity(new Intent(this,Mainmenu.class));
    }

    private void setHandlers() {

        spinemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idemp=wsemp.items.get(position).idempresa;
                    fillBodega();
                    gl.IdEmpresa = idemp;

                } catch (Exception e) { }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinbod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idbodega=wsbod.items.get(position).IdBodega;
                    gl.IdBodega = idbodega;
                    fillUserImpres();

                } catch (Exception e) { }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinprint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    idimpres=wsprn.items.get(position).idimpresora;
                    gl.IdImpresora = idimpres;

                } catch (Exception e) { }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinuser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    TextView spinlabel = (TextView) parentView.getChildAt(0);
                    spinlabel.setTextColor(Color.BLACK);
                    spinlabel.setPadding(5,0,0,0);spinlabel.setTextSize(18);
                    spinlabel.setTypeface(spinlabel.getTypeface(), Typeface.BOLD);

                    seloper =wsuser.items.get(position);
                    iduser=wsuser.items.get(position).idoperador;
                    gl.IdOperador = iduser;

                } catch (Exception e) { }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

    }

    //endregion

    //region Main

    private void checkLogin() {
        String psw=txtpass.getText().toString();

        if (iduser==-1) {
            msgbox("¡Falta seleccionar un operador!");return;
        }

        if (psw.isEmpty()) {
            msgbox("¡La contraseña es requerida!");return;
        }

        if (!psw.equalsIgnoreCase(seloper.operador.clave)) {
            msgbox("¡Contraseña incorrecta!");return;
        }

        //msgbox();
        //writeXml()
        checkOperadorHH();

        //startActivity(new Intent(this,Mainmenu.class));
    }

    private void checkOperadorHH() {

        try{
            idemp=0;
            callhandle=4;

            xmlstr=wsoper.writeXml();
            wsoper.addParam("oBeOperador",xmlstr);

            wsoper.callMethod("OperadorValidoForHHXml");
        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    @Override
    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) {
        try {
            if (throwing) throw new Exception(errmsg);

            switch (callhandle) {
                case 1:
                    fillSpinemp();break;
                case 2:
                    fillSpinbod();break;
                case 3:
                    fillSpinuser();
                    fillSpinimpres();
                    pbar.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    toast("Scalar result "+wsoper.scalar);
                    break;

            }
        } catch (Exception e) {
            msgbox(errmsg);pbar.setVisibility(View.INVISIBLE);
        }
    }


    //endregion

    //region Logins

    private void fillLogin() {
        try{
            idemp=0;
            callhandle=1;
            wsemp.callMethod("Get_All_Empresas_For_HH");
        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    private void fillBodega() {
        try{
            idbodega=0;
            callhandle=2;

            wsbod.addParam("IdEmpresa",""+idemp);
            wsbod.callMethod("Get_Bodegas_By_IdEmpresa_For_HH");

            //wsprn.addParam("IdEmpresa",""+idemp);
            //wsprn.callMethod("GetAllImpresoraByEmpresa");

        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    private void fillUserImpres() {
        try{
            iduser=-1;idimpres=0;
            callhandle=3;
            wsuser.addParam("IdBodega",""+idbodega);
            wsuser.callMethod("Get_Operadores_By_IdBodega_For_HH");
        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    //endregion

    //region Spinners

    private void fillSpinemp() {
        try {
            emplist.clear();

            for (int i = 0; i <wsemp.items.size(); i++) {
                emplist.add(wsemp.items.get(i).nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, emplist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinemp.setAdapter(dataAdapter);

            if (emplist.size()>0) spinemp.setSelection(0);
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void fillSpinbod() {
        try {
            bodlist.clear();

            for (int i = 0; i <wsbod.items.size(); i++) {
                bodlist.add(wsbod.items.get(i).Nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bodlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinbod.setAdapter(dataAdapter);

            if (bodlist.size()>0) spinbod.setSelection(0);
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void fillSpinuser() {
        try {
            userlist.clear();

            for (int i = 0; i <wsuser.items.size(); i++) {
                userlist.add(wsuser.items.get(i).operador.apellidos+" "+wsuser.items.get(i).operador.nombres);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinuser.setAdapter(dataAdapter);

            if (userlist.size()>0) {
                spinuser.setSelection(0);
                seloper =wsuser.items.get(0);
            }
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    private void fillSpinimpres() {
        try {
            prnlist.clear();

            for (int i = 0; i < wsprn.items.size(); i++) {
                prnlist.add(wsprn.items.get(i).nombre);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, prnlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinprint.setAdapter(dataAdapter);

            if (prnlist.size()>0) spinprint.setSelection(0);
        } catch (Exception e) {
            mu.msgbox( e.getMessage());
        }
    }

    //endregion

    //region Aux

    private void getURL() {
        gl.wsurl = "http://192.168.1.94/WSTOMHH_QA/TOMHHWS.asmx";
        gl.wsurl="";

        try {
            File file1 = new File(Environment.getExternalStorageDirectory(), "/tomws.txt");
            if (file1.exists()) {
                FileInputStream fIn = new FileInputStream(file1);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                gl.wsurl = myReader.readLine();
                myReader.close();
            }

        } catch (Exception e) {
            gl.wsurl ="";
        }

        if (!gl.wsurl.isEmpty()) lblurl.setText(gl.wsurl);else lblurl.setText("Falta archivo con URL");
    }

    //endregion

    //region Dialogs


    //endregion

    //region TEST WS

//    public class clsBePaises {
//        public int  IdPais;
//        public int  ISONUM;
//        public String ISO2;
//        public String ISO3;
//        public String NOMBRE;
//        public boolean Activo;
//
//        public clsBePaises(){
//            IdPais=0;
//            ISONUM=0;
//            ISO2="";
//            ISO3="";
//            NOMBRE="";
//            Activo=true;
//        }
//
//        /*
//        public clsBePaises(int _IdPais,int _ISONUM,String _ISO2,String _ISO3,String _NOMBRE,boolean _Activo){
//            IdPais=_IdPais;
//            ISONUM=_ISONUM;
//            ISO2=_ISO2;
//            ISO3=_ISO3;
//            NOMBRE=_NOMBRE;
//            Activo=_Activo;
//        }
//        */
//
//
//    }

    private void doTest() {
        gl.seloper= seloper;
        startActivity(new Intent(this, frm_tareas_cambio_ubicacion.class));

        //wsexecute();
    }


    //endregion

    //region WebService Thread Core

    private void wsexecute() {
        wserror="";
        AsyncCallWS wstask = new AsyncCallWS();
        wstask.execute();
    }

    private void wsExecute() {
        IvanWebService iws;
        String mResult;

        errflag=false;wserror="";
        iws = new IvanWebService("http://192.168.1.114/WSTOMHH_QA/TOMHHWS.asmx");

        try {

            clsBePaises[] pais=new clsBePaises[10];
            clsBePaises[] retpais = new clsBePaises[1];
            pais[0] = new clsBePaises();
            pais[0].IdPais = 2;


            clsBeMenu_sistema[] menu=new clsBeMenu_sistema[1];
            clsBeMenu_sistema[] retmenu=new clsBeMenu_sistema[1];
            menu[0]=new clsBeMenu_sistema();
            menu[0].IdMenu="1";

            clsBeBodega[] bod=new clsBeBodega[1];


            //iws.call("ws_WSTest", "oBePais", pais);
            //iws.call("ws_WSTest", "oBePais", pais, "errmsg" , "--");
            //iws.call("ws_WSTest", "oBePais", menu, "errmsg" , "--");

            idemp=1;
            iws.call("Get_Bodegas_By_IdEmpresa_For_HH", "IdEmpresa", idemp);

            //Boolean ret = false;
            //String rmsg="",rval="",mr=iws.mResult;

            bod = (clsBeBodega[]) iws.getReturnValue(bod.getClass());

            //ret = (Boolean) iws.getReturnValue(ret.getClass());

            pais = (clsBePaises[]) iws.getVariableValue("BePais", retpais.getClass());

            //retpais = (clsBePaises[]) iws.getVariableValue("oBePais", retpais.getClass());
            //retmenu = (clsBeMenu_sistema[]) iws.getVariableValue("oBePais", retmenu.getClass());

            String ss="";
            //retpais = (clsBePaises) iws.getClassValue("oBePais", retpais.getClass(),retpais);

        } catch (Exception e)   {
            errflag=true;
            wserror=e.getMessage();
        }

        String ss=iws.mResult;
        String ast=iws.argstr;
    }

    private void wsFinished()  {
        try {
            if (errflag) msgbox(wserror);else toast("OK");
        } catch (Exception e) {
        }
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                wsExecute();
            } catch (Exception e) {}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                wsFinished();
            } catch (Exception e) {
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }

    //endregion


    //region Activity Events


    //endregion

}
