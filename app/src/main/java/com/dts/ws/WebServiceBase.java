package com.dts.ws;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.dts.tom.PBase;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class WebServiceBase
{

    public String  error="",debug="",rawdata="", methodname="";
    public Boolean status;
    public String scalar;
    public int errorlevel=0; // 0 - sin error, 1-connection, 2-sql, 3- parse data

    protected SoapObject request;
    protected SoapObject response;
    protected SoapObject result;
    protected SoapPrimitive scalarresponse;

    public PBase parent;
    private Context cont;

    private String URL;
    private boolean errflag;

    private ArrayList<clsWSParam> params = new ArrayList<clsWSParam>();

    private class clsWSParam {
        public String name;
        public Object value;
        public Object type;
    }

    public WebServiceBase(PBase Parent,String Url) {
        parent=Parent;
        cont=Parent.getApplicationContext();
        URL=Url;
    }

    public void callMethod(String methodname) throws Exception {

        try {
            if (isConnected()!=1) {
                errflag=true;errorlevel=1;error="Error de conexión :\nNo hay conexión Wi-Fi";
                parent.wsCallBack(errflag,error,errorlevel);
            } else {
                this.methodname = methodname;
                execute();
            }
        } catch (Exception e) {
            errflag=true;errorlevel=1;error=e.getMessage();
            parent.wsCallBack(errflag,error,errorlevel);
        }
    }

    public void addParam(String name,String value) {
        addParameter(name,value);
    }


    //region Method Call

    protected void processMethod() {

        errorlevel=0;

        try {

            request = new SoapObject("http://tempuri.org/", methodname);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            processParameters();

            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call("http://tempuri.org/"+ methodname, envelope);

            try {
                if (envelope.getResponse() instanceof SoapObject) {
                    response = (SoapObject) envelope.getResponse();
                } else {
                    scalarresponse = (SoapPrimitive) envelope.getResponse();
                }
            } catch (Exception e) {
            }

            result = (SoapObject) envelope.bodyIn;

            dataCallback();
        } catch (Exception e) {
            errflag=true;
            error=e.getMessage();
            rawdata="";
        }

    }

    public void dataCallback() throws Exception {}

    //endregion

    //region  Method Core

    private void addParameter(String name,String value) {
        clsWSParam item;

        item=new clsWSParam();

        item.name=name;
        item.value=value;
        item.type=String.class;

        params.add(item);
    }

    private void processParameters() {
        PropertyInfo param;

        if (params.size()==0) return;

        for (int i = 0; i <params.size(); i++) {
            param = new PropertyInfo();

            //param.setType(String.class);
            param.setType(params.get(i).type);
            param.setName(params.get(i).name);
            param.setValue(params.get(i).value);

            request.addProperty(param);
        }

        params.clear();
    }

    //endregion

    //region WebService Core

    private void execute() {
        WebServiceBase.AsyncCallWS wstask = new WebServiceBase.AsyncCallWS();
        wstask.execute();
    }

    private void wsExecute(){
        errflag=false;status=false;
        error="";rawdata="";

        try {
            processMethod();
            status=errflag;
        } catch (Exception e) {
            error=e.getMessage();
            status=false;
        }
    }

    private void wsFinished()  {
        status=!errflag;
        try {
            parent.wsCallBack(errflag,error,errorlevel);
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

    //region Aux

    public int isConnected(){
        int activo=0;

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager)  cont.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()){
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) activo=1;
                //if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) activo = 2;
            }
        } catch (Exception ex){
        }

        return activo;
    }

    //endregion

}
