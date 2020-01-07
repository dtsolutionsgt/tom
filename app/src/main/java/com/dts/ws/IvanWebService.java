package com.dts.ws;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IvanWebService {

        private URL mUrl;
        public String mResult;
        public String argstr;
        private String mMethodName;

        public IvanWebService(String url){
            try {
                mUrl = new URL(url);
            } catch (MalformedURLException e) {
                 e.printStackTrace();
            }
        }

        public void call(String methodName,Object... args) throws IOException,IllegalArgumentException, IllegalAccessException{
            String ss="";

            mMethodName = methodName;

            //try {
                URLConnection conn = mUrl.openConnection();

                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.addRequestProperty("SOAPAction", "http://tempuri.org/" + methodName);
                conn.setDoOutput(true);

                OutputStream ostream=conn.getOutputStream();

                OutputStreamWriter wr = new OutputStreamWriter(ostream);

                String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"  +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:" +
                        "xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:" +
                        "soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "<" + methodName + " xmlns=\"http://tempuri.org/\">";


                argstr="<clsBePaises>";
                argstr+="<IdPais>1</IdPais>";
                argstr+="<ISONUM>0</ISONUM>";
                argstr+="<ISO2/>";
                argstr+="<ISO3/>";
                argstr+="<NOMBRE/>";
                argstr+="<Activo>false</Activo>";
                argstr+="</clsBePaises>";
                body+=argstr;

                //body += buildArgs(args);


                body += "</" + methodName + ">" +
                        "</soap:Body>" +
                        "</soap:Envelope>";
                wr.write(body);
                wr.flush();

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                mResult = "";
                String line;
                while ((line = rd.readLine()) != null) {
                    mResult += line;
                }

                wr.close();
                rd.close();

             /*
            } catch (IOException e) {
                ss=e.getMessage();
            } catch (IllegalArgumentException e) {
                ss=e.getMessage();
            } catch (IllegalAccessException e) {
                ss=e.getMessage();
            } catch (Exception e) {
                ss=e.getMessage();
            }

            String sst=ss;

              */

        }

        private String buildArgs(Object... args) throws IllegalArgumentException, IllegalAccessException{
            String result = "";
            String argName = "";
            for(int i=0;i<args.length;i++) {
                if(i % 2 == 0) {
                    argName = args[i].toString();
                } else{
                    result += "<" + argName + ">";argstr=result;
                    result += buildArgValue(args[i]);argstr=result;
                    result += "</" + argName + ">";argstr=result;
                }
            }
            return result;
        }

        private String buildArgValue(Object obj) throws IllegalArgumentException, IllegalAccessException {

            Class<?> cl = obj.getClass();
            String result = "";
            if(cl.isPrimitive()) return obj.toString();
            if(cl.getName().contains("java.lang.")) return obj.toString();
            if(cl.getName().equals("java.util.Date")){
                DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                return dfm.format((Date)obj);
            }

            if (cl.isArray()) {
                String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
                xmlName = xmlName.replace(";", "");
                Object[] arr = (Object[])obj;
                for(int i=0; i< arr.length; i++) {
                    result += "<" + xmlName + ">";
                    result += buildArgValue(arr[i]);
                    result += "</" + xmlName + ">";
                }
                return result;
            }

            Field[] fields = cl.getDeclaredFields();

            for(int i=0;i<fields.length;i++) {
                result += "<" + fields[i].getName() + ">";
                result += buildArgValue(fields[i].get(obj));
                result += "</" + fields[i].getName() + ">";
            }

            return result;
        }

        public String getResult(){
            return mResult;
        }

        public Object getReturnValue(Class<?> cl) throws IllegalAccessException,InstantiationException, ParseException {
            return getVariableValue(mResult, mMethodName + "Result", cl);
        }

        public Object getVariableValue(String name, Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException {
            return getVariableValue(mResult, name, cl);
        }

        private Object getVariableValue(String body, String name, Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException  {
            int start = body.indexOf("<" + name + ">");
            start += name.length() + 2; //with < and > char
            int end = body.indexOf("</" + name + ">");
            if(end == -1)
                body = "";
            else
                body = body.substring(start, end);
            if(cl.getName().toLowerCase().contains("string")) return body;
            if(cl.getName().toLowerCase().contains("double")) return Double.parseDouble(body);
            if(cl.getName().toLowerCase().contains("date")){
                DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dfm.parse(body.replace("T"," "));
            }
            if(cl.getName().toLowerCase().contains("boolean")) return Boolean.parseBoolean(body);
            if(cl.isArray()) {
                if(body == "") return Array.newInstance(cl.getComponentType(), 0); //return empty array
                String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
                xmlName = xmlName.replace(";", "");
                String[] items = body.split("</" + xmlName + ">");
                Object arr = Array.newInstance(cl.getComponentType(), items.length);
                for(int i=0;i<items.length;i++) {
                    items[i] += "</" + xmlName + ">";
                    Array.set(arr, i, getVariableValue(items[i], xmlName, cl.getComponentType()));
                }
                return arr;
            }
            Object result = cl.newInstance();
            Field[] fields = cl.getDeclaredFields();
            for(int i=0;i<fields.length;i++) {
                fields[i].set(result, getVariableValue(body, fields[i].getName(), fields[i].getType()));
            }
            return result;
        }

}
