package com.dts.tom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.dts.ws.GetAllEmpresasForHH;
import com.dts.ws.GetAllImpresoraByEmpresa;
import com.dts.ws.GetBodegasByEmpresaForHH;
import com.dts.ws.GetOperadoresByBodegaForHH;

import java.util.ArrayList;


public class MainActivity extends PBase {

    private Spinner spinemp,spinbod,spinprint,spinuser;
    private EditText txtpass;

    private GetAllEmpresasForHH wsemp;
    private GetBodegasByEmpresaForHH wsbod;
    private GetOperadoresByBodegaForHH wsuser;
    private GetAllImpresoraByEmpresa wsprn;

    private ArrayList<String> emplist= new ArrayList<String>();
    private ArrayList<String> bodlist= new ArrayList<String>();
    private ArrayList<String> prnlist= new ArrayList<String>();
    private ArrayList<String> userlist= new ArrayList<String>();

    private int callhandle,idemp,idbodega,idimpres,iduser;

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

            setHandlers();

            gl.wsurl = "http://192.168.1.94/WSTOMHH_QA/TOMHHWS.asmx";

            wsemp = new GetAllEmpresasForHH(MainActivity.this, gl.wsurl);
            wsbod = new GetBodegasByEmpresaForHH(MainActivity.this, gl.wsurl);
            wsuser = new GetOperadoresByBodegaForHH(MainActivity.this, gl.wsurl);
            wsprn = new GetAllImpresoraByEmpresa(MainActivity.this, gl.wsurl);

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
        checkLogin();
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

                } catch (Exception e) {
                    //addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                    //mu.msgbox(e.getMessage());
                }

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

                    idbodega=wsbod.items.get(position).idbodega;
                    fillUserImpres();

                } catch (Exception e) {
                    //addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                    //mu.msgbox(e.getMessage());
                }

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

                    //idimpres=wsprn.items.get(position).idimpresora;

                } catch (Exception e) {
                    //addlog(new Object(){}.getClass().getEnclosingMethod().getName(),e.getMessage(),"");
                    //mu.msgbox(e.getMessage());
                }

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
        startActivity(new Intent(this,Mainmenu.class));
    }

    //endregion

    //region Login

    @Override
    public void wsCallBack(Boolean throwing,String errmsg) {
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
                    break;
            }
        } catch (Exception e) {
            msgbox(e.getMessage()+"-\n"+errmsg);
         }
    }


    //endregion

    //region Logins

    private void fillLogin() {
        try{
            idemp=0;
            callhandle=1;
            wsemp.callMethod("GetAllEmpresasForHH");
        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    private void fillBodega() {
        try{
            idbodega=0;
            callhandle=2;

            wsbod.addParam("IdEmpresa",""+idemp);
            wsbod.callMethod("GetBodegasByEmpresaForHH");

            wsprn.addParam("IdEmpresa",""+idemp);
            wsprn.callMethod("GetAllImpresoraByEmpresa");

        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    private void fillUserImpres() {
        try{
            iduser=0;idimpres=0;
            callhandle=3;
            wsuser.addParam("IdBodega",""+idbodega);
            wsuser.callMethod("GetOperadoresByBodegaForHH");
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
                bodlist.add(wsbod.items.get(i).nombre);
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
                userlist.add(""+wsuser.items.get(i).idoperador);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userlist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinuser.setAdapter(dataAdapter);

            if (userlist.size()>0) spinuser.setSelection(0);
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

    //endregion

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion

}
