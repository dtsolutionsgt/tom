package com.dts.classes;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XmlUtils {

    public String xxml;
    private String xml="";
    private int level;

    public XmlUtils(){}

    public String serializeXml(Object struct,String rootname) {
        xml="";

        try {
            starttag(rootname);
            serializeStruct(struct);
            endtag(rootname);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return xml;
    }

    //region Private

    private void serializeStruct(Object struct) {
        Class<?> cls = struct.getClass();
        Field[] fields = cls.getDeclaredFields();

        String ss;

        try {
            xml+=buildArgValue(struct);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        /*
        for(int i=0; i< arr.length; i++)  {
            result += "<" + xmlName + ">";
            result += buildArgValue(arr[i]);
            result += "</" + xmlName + ">";
        }
        */

        /*
        for (int i = 0; i < fields.length; i++) {
            Class<?> ft=fields[i].getType();

            ss= ft.getName()+"  "+ft.getDeclaredFields().length;
            xml += "</" + fields[i].getName() + ">  "+ss+"  "+isArray(ft) ;
        }
*/

        /*
        if (cls.isArray()) {
            for (int i = 0; i < fields.length; i++) {

            }
        } else {
            xml += "</" + fields[i].getName() + ">" ;
        }
        */

        //tag("IdOperadorBodega","1");
    }

    private String buildArgValue(Object obj) throws IllegalArgumentException, IllegalAccessException {

        Class<?> cl = null;
        try {
            cl = obj.getClass();
        } catch (Exception e) {
            return "";
        }

        String result = "";

        if(cl.isPrimitive()) return obj.toString();
        if(cl.getName().contains("java.lang.")) return obj.toString();
        if(cl.getName().equals("java.util.Date")) {
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            return dfm.format((Date)obj);
        }

        if (cl.isArray()) {
            String xmlName = cl.getName().substring(cl.getName().lastIndexOf(".") + 1);
            xmlName = xmlName.replace(";", "");
            Object[] arr = (Object[])obj;

            for(int i=0; i< arr.length; i++)  {
                result += "<" + xmlName + ">";
                result += buildArgValue(arr[i]);
                result += "</" + xmlName + ">";
            }
            return result;
        }

        Field[] fields = cl.getDeclaredFields();

        for(int i=0;i<fields.length;i++)  {
            String sff=fields[i].getName();
            result += "<" + fields[i].getName() + ">"; xxml=result;
            result += buildArgValue(fields[i].get(obj)); xxml=result;
            result += "</" + fields[i].getName() + ">"; xxml=result;
        }

        return result;
    }

    //endregion

    //region Aux

    private void tag(String name,Object value) {
        xml+="<"+name+">";
        xml+=value.toString();
        xml+="</"+name+">";
    }

    private void starttag(String name) {
        xml+="<"+name+">";
    }

    private void endtag(String name) {
        xml+="</"+name+">";
    }

    private boolean isArray(Class<?> ft) {
        if (ft.getDeclaredFields().length<=1) return false;
        if (ft.getName().contains("java.lang.")) return false;
        return true;
    }

    /*
    private String value(Field ft) {
        String ss="";

        if(ft.getName().equals("java.util.Date")) {
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            return dfm.format( (Date) ft);
        }

        return ss;
    }
*/

    //endregion

}
