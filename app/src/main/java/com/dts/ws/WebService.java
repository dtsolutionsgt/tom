package com.dts.ws;

import android.database.Cursor;
import android.os.AsyncTask;

import com.dts.tom.PBase;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;

public class WebService {

    public Cursor openDTCursor;

    public String  error="";
    public String  rawdata="";
    public Boolean status;

    // private

    private PBase parent;

    private ArrayList<String> results=new ArrayList<String>();

    private String URL,sql;
    private boolean errflag;

    // OpenDT
    private int odt_rows,odt_cols;

    private final String NAMESPACE ="http://tempuri.org/";
    private String METHOD_NAME;

    public WebService(PBase Parent,String Url) {
        parent=Parent;
        URL=Url;
    }

    public void openDT(String SQL) {
        sql=SQL;
        execute();
    }

    //region OpenDT

    private void processOpenDT() {
        String str,vv,val1,val2,val3,val4,val5;
        int rc;

        final String METHOD_NAME = "Get_All_Filter";
        final String NAMESPACE = "http://tempuri.org/";
        final String URL = "http://192.168.1.94/tomimswcf/Cliente/Cliente/ServiceCliente.svc";
        final String SOAP_ACTION = "http://tempuri.org/IServiceCliente/Get_All_Filter";

        StringBuilder sb;
        XmlSerializer writer;

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("pActivo",true);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive result = (SoapPrimitive)envelope.getResponse();

            //to get the data
            String resultData = result.toString();
            // 0 is the first object of data

        } catch (Exception e) {
            errflag = true;
            error = e.getMessage();
            rawdata = "Error : " + error;
        }
    }

    private void processOpenDT_old() {
        String str,vv,val1,val2,val3,val4,val5;
        int rc;

        METHOD_NAME = "getClsType1";

        results.clear();errflag = false;rawdata="";

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            PropertyInfo param = new PropertyInfo();
            param.setType(String.class);
            param.setName("SQL");param.setValue(sql);

            request.addProperty(param);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(NAMESPACE+METHOD_NAME, envelope);

            SoapObject response =(SoapObject) envelope.getResponse();
            SoapObject result = (SoapObject) envelope.bodyIn;

            rc=response.getPropertyCount();

            for (int i = 0; i < rc; i++) {
                Object property = response.getProperty(i);

                if (property instanceof SoapObject) {

                    SoapObject item = (SoapObject) property;

                    val1= item.getProperty("code").toString();
                    val2= item.getProperty("cant").toString();
                    val3= item.getProperty("total").toString();

                    //SoapObject sitem = (SoapObject) item.getProperty("item");
                    //val4= sitem.getProperty("code").toString();

                    str = val1+" , "+val2+" , "+val3+"\n";
                    rawdata+=str+"\n";
                }
            }

        } catch (Exception e) {
            errflag=true;
            error=e.getMessage();
            rawdata="Error : "+error;
        }

    }


    //endregion

    //region WebService Core

    private void execute() {
        AsyncCallWS wstask = new AsyncCallWS();
        wstask.execute();
    }

    public void wsExecute(){
        errflag=false;status=false;error="";

        try {
            processOpenDT();
            status=errflag;
        } catch (Exception e) {
            error=e.getMessage();
            status=false;
        }
    }

    public void wsFinished()  {
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
