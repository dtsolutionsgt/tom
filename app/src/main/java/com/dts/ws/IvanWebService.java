package com.dts.ws;


import com.dts.classes.XmlUtils;
import com.dts.classes.clsBeOperador_bodega;
import com.dts.tom.MainActivity;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class IvanWebService {

    private URL mUrl;

    public String mResult;
    public String argstr;
    private String mMethodName;

    public IvanWebService(String url) {
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void call(String methodName, Object... args) throws IOException, IllegalArgumentException, IllegalAccessException {
        String ss = "";

        mMethodName = methodName;

        //try {

        URLConnection conn = mUrl.openConnection();
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.addRequestProperty("SOAPAction", "http://tempuri.org/" + methodName);
        conn.setDoOutput(true);

        OutputStream ostream = conn.getOutputStream();

        OutputStreamWriter wr = new OutputStreamWriter(ostream);

        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:" +
                "xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:" +
                "soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<" + methodName + " xmlns=\"http://tempuri.org/\">";

                /*
            XmlUtils xml=new XmlUtils();
            try{

                String xms=xml.serializeXml(args[1],"oBePais");
                body+=xms;
             } catch (Exception e){
               String ee=e.getMessage();
            }
                /*
                argstr="<oBePais>";
                argstr+="<IdPais>1</IdPais>";
                argstr+="<ISONUM>0</ISONUM>";
                argstr+="<ISO2/>";
                argstr+="<ISO3/>";
                argstr+="<NOMBRE/>";
                argstr+="<Activo>false</Activo>";
                argstr+="</oBePais>";
                */


        body += buildArgs(args);
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

    //region Arguments

    private String buildArgs(Object... args) throws IllegalArgumentException, IllegalAccessException {
        String result = "";
        String argName = "";
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                argName = args[i].toString();
            } else {
                result += "<" + argName + ">";
                argstr = result;
                result += buildArgValue(args[i]);
                argstr = result;
                result += "</" + argName + ">";
                argstr = result;
            }
        }
        return result;
    }

    private String buildArgValue(Object obj) throws IllegalArgumentException, IllegalAccessException {
        //Class<?> cl = obj.getClass();

        Class<?> cl = null;
        try {
            cl = obj.getClass();
        } catch (Exception e) {
            return "";
        }

        String result = "";

        if (cl.isPrimitive()) return obj.toString();
        if (cl.getName().contains("java.lang.")) return obj.toString();
        if (cl.getName().equals("java.util.Date")) {
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            return dfm.format((Date) obj);
        }

        if (cl.isArray()) {
            String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
            xmlName = xmlName.replace(";", "");
            Object[] arr = (Object[]) obj;

            for (int i = 0; i < arr.length; i++) {
                result += "<" + xmlName + ">";
                result += buildArgValue(arr[i]);
                result += "</" + xmlName + ">";
            }
            return result;
        }

        Field[] fields = cl.getDeclaredFields();

        for (int i = 0; i < fields.length - 1; i++) {
            result += "<" + fields[i].getName() + ">";
            result += buildArgValue(fields[i].get(obj));
            result += "</" + fields[i].getName() + ">";
        }

        return result;
    }

    //endregion

    //region Scalar Values

    public String getResult() {
        return mResult;
    }

    public Object getReturnValue(Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException {
        return getVariableValue(mResult, mMethodName + "Result", cl);
    }

    public Object getVariableValue(String name, Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException {
        return getVariableValue(mResult, name, cl);
    }

    private Object getVariableValue(String body, String name, Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException {

        int start = body.indexOf("<" + name + ">");
        if (start>-1)  start += name.length() + 2;else start=0;//with < and > char
        int end = body.indexOf("</" + name + ">");
        if (end == -1) body = "";else body = body.substring(start, end);

        String gname = cl.getName();

        if (cl.getName().toLowerCase().contains("string")) {
            if (end <start)return "";else return body;
        }
        if (cl.getName().toLowerCase().contains("double")) {
            if (body.isEmpty()) return 0; else return Double.parseDouble(body);
        }
        if (cl.getName().toLowerCase().contains("int")) {
            if (body.isEmpty()) return 0; else return Integer.parseInt(body);
        }

        if (cl.getName().toLowerCase().contains("date")) {
            String ss = body;
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dfm.parse(body.replace("T", " "));
        }

        if (cl.getName().toLowerCase().contains("boolean")) {
            return Boolean.parseBoolean(body);
        }

        if (cl.isArray()) {
            if (body == "") return Array.newInstance(cl.getComponentType(), 0); //return empty array
            String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
            xmlName = xmlName.replace(";", "");
            String[] items = body.split("</" + xmlName + ">");
            Object arr = Array.newInstance(cl.getComponentType(), items.length);

            for (int i = 0; i < items.length; i++) {
                items[i] += "</" + xmlName + ">";
                Object retobj = getVariableValue(items[i], xmlName, cl.getComponentType());
                String rstt = retobj.toString();
                Array.set(arr, i, retobj);
            }

            return arr;
        }

        Object result = cl.newInstance();

//        try {
//            result = cl.newInstance();
//        } catch (Exception e) {
//           String ee = e.getMessage();
//        }

        Field[] fields = cl.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].set(result, getVariableValue(body, fields[i].getName(), fields[i].getType()));
        }

        return result;
    }

    private Object ddgetVariableValue(String body, String name, Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException {
        int start = body.indexOf("<" + name + ">");
        start += name.length() + 2; //with < and > char
        int end = body.indexOf("</" + name + ">");

        if (end == -1) body = ""; else body = body.substring(start, end);

        String gname = cl.getName();

        if (cl.getName().toLowerCase().contains("string")) {
            return body;
        }
        if (cl.getName().toLowerCase().contains("double")) {
            return Double.parseDouble(body);
        }
        if (cl.getName().toLowerCase().contains("date")) {
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dfm.parse(body.replace("T", " "));
        }

        if (cl.getName().toLowerCase().contains("boolean")) {
            return Boolean.parseBoolean(body);
        }

        return body.toString();
    }

    //endregion

    //region Class Values

    /*
    public Object getClassValue(String name, Class<?> cl,Object obj) throws IllegalAccessException, InstantiationException, ParseException {
        return getClassValue(mResult, name, cl);
    }


*/

    private Object xxgetClassValue(String body, String name, Class<?> cl,Object obj) throws IllegalAccessException, InstantiationException, ParseException {
        //int start = body.indexOf("<" + name + ">");
        //start += name.length() + 2; //with < and > char
        //int end = body.indexOf("</" + name + ">");

        int start = body.indexOf("<" + name + ">");
        int end = body.indexOf("</" + name + ">")+name.length() + 3;

        if (end == -1) body = "";else body = body.substring(start, end);


/*
        if (cl.isArray()) {

            if (body == "") return Array.newInstance(cl.getComponentType(), 0); //return empty array
            String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
            xmlName = xmlName.replace(";", "");
            String[] items = body.split("</" + xmlName + ">");
            Object arr = Array.newInstance(cl.getComponentType(), items.length);

            for (int i = 0; i < items.length; i++) {
                String fname=items[i];
                items[i] += "</" + xmlName + ">";



                Object retobj = getVariableValue(items[i], xmlName, cl.getComponentType());
                String rstt = retobj.toString();
                Array.set(arr, i, retobj);
            }
            return arr;
        }

        Object result = null;
        try {
            result = cl.getClass().newInstance();
        } catch (Exception e) {
            String ee = e.getMessage();
        }


*/
        Object result = null;


        Field[] fields = cl.getDeclaredFields();

        for (int i = 0; i < fields.length - 1; i++) {
            String fnm=fields[i].getName();
            Object vobj=getVariableValue(body, fields[i].getName(), fields[i].getType());
            String svobj=vobj.toString();
            //fields[i].set(result, );
        }


        return result;
    }

    private  Document convertStringIntoXML(String xml)  {
        Document doc = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try  {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            System.out.println("XML parse error: " + e.getMessage());
            return null;
        } catch (SAXException e) {
            System.out.println("Wrong XML structure: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("I/O exeption: " + e.getMessage());
            return null;
        }

        return doc;
    }

    //endregion

    //region Respaldo

    private Object OLD_getVariableValue(String body, String name, Class<?> cl) throws IllegalAccessException, InstantiationException, ParseException {
        int start = body.indexOf("<" + name + ">");
        start += name.length() + 2; //with < and > char
        int end = body.indexOf("</" + name + ">");

        if (end == -1) body = "";
        else body = body.substring(start, end);

        String gname = cl.getName();

        if (cl.getName().toLowerCase().contains("string")) {
            return body;
        }
        if (cl.getName().toLowerCase().contains("double")) {
            return Double.parseDouble(body);
        }
        if (cl.getName().toLowerCase().contains("date")) {
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dfm.parse(body.replace("T", " "));
        }

        if (cl.getName().toLowerCase().contains("boolean")) {
            return Boolean.parseBoolean(body);
        }

        if (cl.isArray()) {
            if (body == "") return Array.newInstance(cl.getComponentType(), 0); //return empty array
            String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
            xmlName = xmlName.replace(";", "");
            String[] items = body.split("</" + xmlName + ">");
            Object arr = Array.newInstance(cl.getComponentType(), items.length);
            for (int i = 0; i < items.length; i++) {
                items[i] += "</" + xmlName + ">";
                Object retobj = getVariableValue(items[i], xmlName, cl.getComponentType());
                String rstt = retobj.toString();
                Array.set(arr, i, retobj);
            }
            return arr;
        }

        Object result = null;
        try {
            result = cl.getClass().newInstance();
        } catch (Exception e) {
            String ee = e.getMessage();
        }

        Field[] fields = cl.getDeclaredFields();
        for (int i = 0; i < fields.length - 1; i++) {
                /*
                String ss=fields[i].getName();

                //fields[i].get(result);
                //String s1=result.toString();

                result=getVariableValue(body, fields[i].getName(), fields[i].getType());

                fields[i].set(cl,result);
                fields[i].get(result);
                String s2=result.toString();

                */

            fields[i].set(result, getVariableValue(body, fields[i].getName(), fields[i].getType()));
        }
        return result;
    }

    //endregion

}
