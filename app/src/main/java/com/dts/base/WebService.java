package com.dts.base;

import android.os.AsyncTask;
import android.util.Log;

import com.dts.tom.PBase;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WebService {

    public String xmlresult;
    public String error;
    public int callback=-1;
    public Boolean errorflag;

    private final PBase parent;

    private java.net.URL mUrl;
    private String mMethodName,mResult,argstr;

    public WebService(PBase Parent,String Url) {
        parent=Parent;
        try {
            mUrl = new URL(Url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //region Public methods

    public void execute() {
        errorflag =false;error="";
        AsyncCallWS wstask = new AsyncCallWS();
        wstask.execute();
    }

    public void wsExecute(){ }

    public void wsFinished() {
        try
        {
            parent.wsCallBack(errorflag,error,0);
        } catch (Exception e) {
            //error = e.getMessage(); errorflag = true;
        }
    }

    public void callMethodJsonGet(String methodName, Object... args) throws Exception {


        mResult = "";xmlresult="";
        error="";errorflag=false;
        String line="";

        try{

            String vUrl = mUrl.toString() + "/" + methodName + "?";
            vUrl += buildArgValue3(args);
            URL url = new URL(vUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(6000);
            conn.setConnectTimeout(6000);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("mArch", "Andr");
            conn.connect();

            int responsecode = conn.getResponseCode();
            String responsemsg = conn.getResponseMessage();

            if (responsecode!=299 && responsecode!=404 && responsecode!=500) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) mResult += line;
                rd.close();rd.close();

                mResult=mResult.replace("ñ","n");
                xmlresult=mResult;

                if(xmlresult.isEmpty()){
                    Log.i("vacio","no creo");
                }

            } if (responsecode==299) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) mResult += line;
                rd.close();rd.close();

                mResult=mResult.replace("ñ","n");
                xmlresult=mResult;

                errorflag=true;error=parseError();
                throw new Exception("Error al procesar la solicitud :\n " + parseError());

            } if (responsecode==404) {
                errorflag=true;error="Error 404: No se obtuvo acceso a: \n" + mUrl.toURI() + "\n" + "Verifique que el WS Existe y es accesible desde el explorador.";
                throw new Exception(error);
            }if (responsecode==500) {

                errorflag=true;error=parseError();
                throw new Exception("Error al procesar la solicitud :\n " + methodName + " Code: 500");

            }

        } catch (Exception e)  {
            errorflag=true;error=e.getMessage();
            throw new Exception(e.getMessage());
        }
    }

    public void callMethod(String methodName, Object... args) throws Exception {

        URLConnection conn = mUrl.openConnection();
        String ss = "",line="";
        int TIMEOUT = 6000;
        int READTIMEOUT = 0;

        mMethodName = methodName; mResult = "";xmlresult="";
        error="";errorflag=false;

        try{

           conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
           conn.addRequestProperty("SOAPAction", "http://tempuri.org/" + methodName);
           conn.setReadTimeout(READTIMEOUT);
           conn.setConnectTimeout(TIMEOUT);
           conn.setDoInput(true);
           conn.setDoOutput(true);
           conn.setRequestProperty("mArch", "Andr");

            OutputStream ostream = null;

            try {
                ostream = conn.getOutputStream();
            } catch (IOException e) {

                mResult=mResult.replace("ñ","n");
                xmlresult=mResult;

                errorflag=true;error=e.getMessage();
                throw new Exception("Error al conectar con el webservice:\n " + error);

            }

            OutputStreamWriter wr = new OutputStreamWriter(ostream);

           String body = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
                   "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:" +
                   "xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:" +
                   "soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                   "<soap:Header>" +
                   "<clsArchHeader xmlns=\"http://tempuri.org/\">" +
                   "<Tipo>Andr</Tipo>" +
                   "</clsArchHeader>" +
                   "</soap:Header>" +
                   "<soap:Body>" +
                   "<" + methodName + " xmlns=\"http://tempuri.org/\">";

           body += buildArgValue2(args);
           body += "</" + methodName + ">" +
                   "</soap:Body>" +
                   "</soap:Envelope>";
           wr.write(body);
           wr.flush();

           int responsecode = ((HttpURLConnection) conn).getResponseCode();
           String responsemsg = ((HttpURLConnection) conn).getResponseMessage();

           if (responsecode!=299 && responsecode!=404 && responsecode!=500) {

               BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               while ((line = rd.readLine()) != null) mResult += line;
               rd.close();rd.close();

               mResult=mResult.replace("ñ","n");
               xmlresult=mResult;

               if(xmlresult.isEmpty()){
                   Log.i("vacio","no creo");
               }

           } if (responsecode==299) {

               BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               while ((line = rd.readLine()) != null) mResult += line;
               rd.close();rd.close();

               mResult=mResult.replace("ñ","n");
               xmlresult=mResult;

               errorflag=true;error=parseError();
               throw new Exception("Error al procesar la solicitud :\n " + parseError());

           } if (responsecode==404) {
               errorflag=true;error="Error 404: No se obtuvo acceso a: \n" + mUrl.toURI() + "\n" + "Verifique que el WS Existe y es accesible desde el explorador.";
               throw new Exception(error);
           }if (responsecode==500) {

                errorflag=true;error=parseError();
                throw new Exception("Error al procesar la solicitud :\n " + methodName + " Code: 500");

            }

       } catch (Exception e)  {
           errorflag=true;error=e.getMessage();
           throw new Exception(e.getMessage());
       }
    }


    //endregion

    //region Arguments

    private String buildArgValue2(Object... obj) throws IllegalArgumentException, IllegalAccessException{

        String result = "";
        String argName = "";

        try{

            for (int i = 0; i < obj.length; i++)
            {
                Class<?> cl = obj[i].getClass();
                if (i % 2 == 0)
                {
                    argName = obj[i].toString();
                } else
                {
                    if(cl.isPrimitive() || (cl.getName().contains("java.lang.")) || (cl.getName().contains("java.int")))
                    {

                        if (obj!=null)
                        {
                            result += "<" + argName + ">";
                            argstr = result;
                            result += buildArgValue(obj[i].toString(),argName);
                            argstr = result;
                            result += "</" + argName + ">";
                            argstr = result;
                        }else
                        {
                            result += "<" + argName + ">";
                            result += "</" + argName + ">";
                            argstr = result;
                        }

                    }
                    if(cl.getName().equals("java.util.Date"))
                    {
                        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                        result += "<" + argName + ">";
                        argstr = result;
                        result += dfm.format((Date)obj[i]);
                        argstr = result;
                        result += "</" + argName + ">";
                        argstr = result;
                        //return argstr;
                    }else
                    {//Is a strong type class
                        String vResultObjects =buildArgs(obj);
                        return vResultObjects;
                    }
                }
            }

        }catch (Exception ex)
        {
            try
            {
                throw new Exception(" WebService callMethod : "+ ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private String buildArgs(Object... args) throws IllegalArgumentException, IllegalAccessException {

        String result = "";
        String argName = "";

        try
        {

            for (int i = 0; i < args.length; i++)
            {
                if (i % 2 == 0) {
                    argName = args[i].toString();
                } else
                {
                    result += "<" + argName + ">";
                    argstr = result;
                    result += buildArgValue(args[i],argName);
                    argstr = result;
                    result += "</" + argName + ">";
                    argstr = result;
                }
            }
        }
        catch (Exception ex)
        {
            try
            {
                throw new Exception(" WebService callMethod : "+ ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private String buildArgValue(Object obj, String pFieldName) throws IllegalArgumentException, IllegalAccessException {
        //Class<?> cl = obj.getClass();

        Class<?> cl = null;

        try
        {
            cl = obj.getClass();
        } catch (Exception e)
        {
            return "";
        }

        String result = "";

        if(result.contains("elementData"))
        {
            Log.e("Pausa", "Debug");
        }

        try
        {
            if(cl.isPrimitive() || (cl.getName().contains("java.lang.")) || (cl.getName().contains("java.int")))
            {
                if (obj!=null)
                {
                    return obj.toString();
                }else
                {
                    Log.e("#EJC_Null","#EJC20200320_NullPointer Esto no debería suceder...");
                    return null;
                }

            }
            if (cl.getName().equals("java.util.Date"))
            {

                DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date convertedDate = new Date();

                try
                {

                    if (obj!=null)
                    {
                        convertedDate = dfm.parse(obj.toString());
                        result += buildArgValue(convertedDate,pFieldName);
                        return obj.toString();
                    }else
                    {
                        return "1900-01-01T00:00:00";
                    }

                } catch (ParseException e)
                {
                    Log.e("#EJC_Invalid_Format","ParamName: " + pFieldName + " Se aplicó valor x Defecto REF#1");
                    result += buildArgValue("1900-01-01T00:00:00",pFieldName);
                }
            }

            if (cl.isArray())
            {
                String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
                xmlName = xmlName.replace(";", "");
                Object[] arr = (Object[]) obj;

                for (int i = 0; i < arr.length; i++) {
                    result += "<" + xmlName + ">";
                    result += buildArgValue(arr[i],xmlName);
                    result += "</" + xmlName + ">";
                }
                return result;
            }

            if(result.contains("elementData"))
            {
                Log.e("Pausa", "Debug");
            }

            Field[] fields = cl.getDeclaredFields();

            String fieldname ="";

            String ClassName = cl.getName();

            for (int i = 0; i <= fields.length-1; i++)
            {

                fieldname =fields[i].getName();

                if(fieldname == "Tag" || fieldname == "Fec_agr" || fieldname.startsWith("Fecha"))
                {
                    Log.d("tag",fieldname);
                }

                result += "<" + fieldname  + ">";

                if(result.contains("elementData"))
                {
                    Log.e("Pausa", "Debug");
                }

                if(fieldname!= "Tag")
                {
                    if ((fieldname.startsWith("Fec_") || fieldname.startsWith("Fech") || fieldname.startsWith("Hora")) &&
                            (!fieldname.contains("especifica")) )
                    {
                        if (obj!=null)
                        {

                            Object oFecha =fields[i].get(obj);
                            String vFecha="1900-01-01T00:00:00";

                            if (oFecha!=null)
                            {
                                vFecha=oFecha.toString();
                            }

                            if(vFecha.contains("T"))
                            {
                                try
                                {
                                    result += buildArgValue(vFecha,fieldname);
                                } catch (Exception e)
                                {
                                    Log.e("#EJC_Invalid_Format","ParamName: " + fieldname + " Se aplicó valor x Defecto REF#2");
                                    result += buildArgValue("1900-01-01T00:00:00",fieldname);
                                }
                            }else
                            {

                                DateFormat dfm = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                                Date convertedDate = new Date();

                                try
                                {
                                    convertedDate = dfm.parse(vFecha);
                                    DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                                    // format the date into another format
                                    String dateStr = destDf.format(convertedDate);
                                    result += buildArgValue(dateStr,fieldname);
                                } catch (ParseException e)
                                {
                                    Log.e("#EJC_Invalid_Format","ParamName: " + fieldname + " Se aplicó valor x Defecto REF#3");
                                    if (fieldname=="Fechamanufactura")
                                    {
                                        result += buildArgValue(false,fieldname);
                                    }else
                                    {
                                        result += buildArgValue("1900-01-01T00:00:00",fieldname);
                                    }
                                }
                            }

                        }else
                        {
                            Object vobj = fields[i].get(obj);
                            if (vobj!=null)
                            {
                                result += buildArgValue(vobj,fieldname);
                            }
                        }

                    }else
                    {
                        Object vobj =null;

                        if (obj instanceof ArrayList){
                            vobj =obj;
                        }else
                        {
                            vobj = fields[i].get(obj);
                        }

                        if (vobj!=null)
                        {
                            if (vobj instanceof ArrayList)
                            {
                                Log.i("islist","vojb");

                                try{
                                    result="";
                                    for (Object vItem: ((ArrayList) vobj).toArray())
                                    {
                                        System.out.println(vItem);

                                        Class<?> clchild = null;

                                        try
                                        {
                                            //#EJC20200325: No colocar inicio, hasta no definir el objeto completo
                                            //result=""; #CKFK 20200512 tuve que poner esto en comentario para poder enviar la lista completa
                                            String ClassNamechild = vItem.getClass().getSimpleName();
                                            System.out.println(ClassNamechild);
                                            result+=buildArgs(ClassNamechild,vItem);

                                        } catch (Exception e)
                                        {
                                            return "";
                                        }

                                    }

                                }catch (Exception ex)
                                {
                                   Log.e("Nosepudo",ex.getMessage());
                                }

                                return result;

                            }else
                            {
                                result += buildArgValue(vobj,fieldname);
                            }

                        }
                    }

                }else
                {
                    Log.e("#EJC_Invalid_Format","ParamName: " + fieldname + " Se aplicó valor x Defecto REF#4");
                    //Si el valor es obj.java en campo tag....
                    result += buildArgValue("",fieldname);
                }

                result += "</" + fields[i].getName() + ">";
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private Date stringToDate(String aDate)
    {
        String aFormat = "yyyy-MM-dd HH:mm:ss Z";
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }

    //endregion

    //region WebService Async Call Class

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                wsExecute();
            } catch (Exception e) {
                //error = e.getMessage(); errorflag = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                wsFinished();
            } catch (Exception e) {
                //error = e.getMessage(); errorflag = true;
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }

    //endregion

    //region Aux

    public String parseError() {
        try {
            int p1=xmlresult.indexOf("<Error>")+7;
            int p2=xmlresult.indexOf("</Error>");
            return xmlresult.substring(p1,p2);
        } catch (Exception e) {
            return "";
        }
    }

    public void callMethodJsonPost(String methodName, Object... args) throws Exception {


        mResult = "";xmlresult="";
        error="";errorflag=false;
        String line="";

        try{

            String vUrl = mUrl.toString() + "/" + methodName;
            URL url = new URL(vUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("mArch", "Andr");

            OutputStream ostream = null;

            String str =buildArgValue3(args);
            byte[] outputInBytes = str.getBytes(StandardCharsets.UTF_8);
            ostream = conn.getOutputStream();
            ostream.write( outputInBytes );
            ostream.close();

            try {
                ostream = conn.getOutputStream();
            } catch (IOException e) {

                mResult=mResult.replace("ñ","n");
                xmlresult=mResult;

                errorflag=true;error=e.getMessage();
                throw new Exception("Error al conectar con el webservice:\n " + error);
            }

            conn.connect();

            int responsecode = conn.getResponseCode();
            String responsemsg = conn.getResponseMessage();

            if (responsecode!=299 && responsecode!=404 && responsecode!=500) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) mResult += line;
                rd.close();rd.close();

                mResult=mResult.replace("ñ","n");
                xmlresult=mResult;

                if(xmlresult.isEmpty()){
                    Log.i("vacio","no creo");
                }

            } if (responsecode==299) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) mResult += line;
                rd.close();rd.close();

                mResult=mResult.replace("ñ","n");
                xmlresult=mResult;

                errorflag=true;error=parseError();
                throw new Exception("Error al procesar la solicitud :\n " + parseError());

            } if (responsecode==404) {
                errorflag=true;error="Error 404: No se obtuvo acceso a: \n" + mUrl.toURI() + "\n" + "Verifique que el WS Existe y es accesible desde el explorador.";
                throw new Exception(error);
            }if (responsecode==500) {

                errorflag=true;error=parseError();
                throw new Exception("Error al procesar la solicitud :\n " + methodName + " Code: 500");

            }

        } catch (Exception e)  {
            errorflag=true;error=e.getMessage();
            throw new Exception(e.getMessage());
        }
    }

    private String buildArgValue3(Object... obj) throws IllegalArgumentException, IllegalAccessException{

        String result = "";
        String argName = "";
        char vComilla = ((char) 34);

        try{

            for (int i = 0; i < obj.length; i++)
            {
                Class<?> cl = obj[i].getClass();
                if (i % 2 == 0)
                {
                    argName = obj[i].toString();
                } else
                {
                    if(cl.isPrimitive() || (cl.getName().contains("java.lang.")) || (cl.getName().contains("java.int")))
                    {

                        if (obj!=null)
                        {
                            result += argName;
                            argstr = result;

                            Object value_argument = obj[i].toString();

                            if (value_argument.toString().isEmpty()){
                                result +="="; //+ "" + vComilla + "" + vComilla;
                            }else{
                                result +="=" + value_argument;
                            }

                            argstr = result;

                        }else
                        {
                            result += argName;
                            result +=  argName;
                            argstr = result;
                        }

                    }
                    if(cl.getName().equals("java.util.Date"))
                    {
                        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                        result += "<" + argName + ">";
                        argstr = result;
                        result += dfm.format((Date)obj[i]);
                        argstr = result;
                        result += "</" + argName + ">";
                        argstr = result;
                        //return argstr;
                    }else
                    {//Is a strong type class
                        String vResultObjects =buildArgsJson(obj);
                        return vResultObjects;
                    }
                }
            }

        }catch (Exception ex)
        {
            try
            {
                throw new Exception(" WebService callMethod : "+ ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private String buildArgsJson(Object... args) throws IllegalArgumentException, IllegalAccessException {

        String result = "";
        String argName = "";
        char vComilla = ((char) 34);

        try{

            for (int i = 0; i < args.length; i++)
            {
                if (i % 2 == 0) {
                    argName = args[i].toString();
                } else
                {
                    result += argName + "=";
                    argstr = result;

                    Object value_argument = args[i].toString();

                    if (value_argument.toString().isEmpty()){
                        result +="";// + vComilla + "" + vComilla;
                        if (i!=args.length -1){
                            result += "&";
                        }
                    }else{

                        result += value_argument;

                        if (i!=args.length -1){
                            result += "&";
                        }
                        //result +="=" + buildArgValue3(args[i],argName);
                    }
                    argstr = result;
                }
            }
        }
        catch (Exception ex)
        {
            try
            {
                throw new Exception(" WebService callMethod : "+ ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    //endregion
}
