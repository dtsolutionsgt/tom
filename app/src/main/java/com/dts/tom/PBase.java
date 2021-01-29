package com.dts.tom;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dts.base.AppMethods;
import com.dts.base.DateUtils;
import com.dts.base.MiscUtils;
import com.dts.base.appGlobals;
import com.dts.base.clsClasses;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class PBase extends Activity {

    protected String sql;

    public appGlobals gl;
    public MiscUtils mu;
    public DateUtils du;
    public AppMethods app;
    protected Application vApp;
    protected clsClasses clsCls = new clsClasses();

    protected int itemid,browse,mode;
    protected int selid,selidx,deposito;
    protected long fecha;
    protected String s,ss ;

    protected InputMethodManager keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbase);
    }

    public void InitBase(){

        vApp=this.getApplication();
        gl=((appGlobals) this.getApplication());

        mu=new MiscUtils(this);
        du=new DateUtils();
        app=new AppMethods(this,gl);

        keyboard = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);

        browse=0;
    }

    // Web service call back

    public void wsCallBack(Boolean throwing,String errmsg,int errlevel) throws Exception {

    }

    // Aux

    protected void msgbox(String msg){
        try{
            mu.msgbox(msg);
        }catch (Exception ex){
            addlog(new Object(){}.getClass().getEnclosingMethod().getName(),ex.getMessage(),"");
        }
    }

    protected void toast(String msg) {
        toastcent(msg);
    }

    protected void toast(double val) {
        toastcent(""+val);
    }

    protected void toastlong(String msg) {
        Toast toast= Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void toastcent(String msg) {

        if (mu.emptystr(msg)) return;

        Toast toast= Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void addlog(final String methodname, String msg, String info) {

        final String vmethodname = methodname;
        final String vmsg = msg;
        final String vinfo = info;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAddlog(vmethodname,vmsg, vinfo);
            }
        }, 500);

    }

    protected void setAddlog(String methodname,String msg,String info) {

        BufferedWriter writer = null;
        FileWriter wfile;

        try {

            String fname = Environment.getExternalStorageDirectory()+"/roadlog.txt";
            wfile=new FileWriter(fname,true);
            writer = new BufferedWriter(wfile);

            writer.write("Método: " + methodname + " Mensaje: " +msg + " Info: "+ info );
            writer.write("\r\n");

            writer.close();

        } catch (Exception e) {
            msgbox("Error " + e.getMessage());
        }
    }

    protected String iif(boolean condition,String valtrue,String valfalse) {
        if (condition) return valtrue;else return valfalse;
    }

    protected double iif(boolean condition,double valtrue,double valfalse) {
        if (condition) return valtrue;else return valfalse;
    }

    protected double iif(boolean condition,int valtrue,int valfalse) {
        if (condition) return valtrue;else return valfalse;
    }

    protected void showkeyb(){
        if (keyboard != null) {
            keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    protected void hidekeyb() {
        keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    // Activity Events

    @Override
    protected void onResume() {
         super.onResume();
    }

    @Override
    protected void onPause() {
         super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
