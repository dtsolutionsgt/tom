package com.dts.ws;

import android.os.AsyncTask;

import com.dts.tom.PBase;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class WebServiceBase
{

    public String  error="",rawdata="", methodname="";
    public Boolean status;

    protected SoapObject request;
    protected SoapObject response;
    protected SoapObject result;

    private PBase parent;

    private String URL,sql;
    private boolean errflag;

    private ArrayList<clsWSParam> params = new ArrayList<clsWSParam>();

    private class clsWSParam { public String name,value; }


    public WebServiceBase(PBase Parent,String Url) {
        parent=Parent;
        URL=Url;
    }

    public void callMethod(String methodname) {
        this.methodname = methodname;
        execute();
    }

    public void addParam(String name,String value) {
        addParameter(name,value);
    }

    //region Method Call

    protected void processMethod() {

        try {

            request = new SoapObject("http://tempuri.org/", methodname);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            processParameters();

            envelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call("http://tempuri.org/"+ methodname, envelope);

            response =(SoapObject) envelope.getResponse();
            result = (SoapObject) envelope.bodyIn;

            dataCallback();

        } catch (Exception e) {
            errflag=true;error=e.getMessage();rawdata="";
        }

    }

    public void dataCallback() {}

    //endregion

    //region  Method Core

    private void addParameter(String name,String value) {
        clsWSParam item;

        item=new clsWSParam();

        item.name=name;
        item.value=value;

        params.add(item);
    }

    private void processParameters() {
        PropertyInfo param;

        if (params.size()==0) return;

        for (int i = 0; i <params.size(); i++) {
            param = new PropertyInfo();

            param.setType(String.class);
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
            parent.wsCallBack(errflag,error);
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


}
