package com.dts.base;

import android.database.Cursor;
import android.database.MatrixCursor;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLObject  {

    private WebService ws;
    private Cursor data;
    private int adimx,adimy;

    public String debg="";

    public XMLObject(WebService webservice) {
        ws=webservice;
    }

    public <T> T get(Class<? extends T> type, String source) throws Exception {

        try {

            String xnode=getXMLRegion(source);
            Serializer serializer = new Persister();
            return serializer.read( type, xnode);

        }catch (Exception e){
            throw new Exception(" XMLObject get : "+e.getMessage());
        }

    }

    public <T> T getresult(Class<? extends T> type, String source) throws Exception
    {
        String xnode="";

        try{

            xnode=getXMLRegion(source+"Result");

//            if (xnode.contains("USUbicStrucStage5")) {
//                xnode = xnode.replace("<int>","<clsUbic><ubic>");
//                xnode = xnode.replace("</int>","</ubic></clsUbic>");
//            }

            Serializer serializer = new Persister();

            if (!xnode.isEmpty())
            {
                return serializer.read(type, xnode);
            }

        }catch (Exception e)
        {
            throw new Exception(" XMLObject Error: " + e.getMessage() + " GetResult:" +ws.xmlresult);
        }
        return null;
    }

    public <baseClas> baseClas getresultSingle(Class<? extends baseClas> type, String source) throws Exception
    {
        String xnode="";

        try{

            xnode=getXMLRegion(source);

            Serializer serializer = new Persister();

            if (!xnode.isEmpty())
            {
                return serializer.read(type, xnode);
            }

        }catch (Exception e)
        {
            throw new Exception(" XMLObject Error: " + e.getMessage() + " GetResult:" +ws.xmlresult);
        }
        return null;
    }

    public Cursor filldt() throws Exception {
        if (!parseXMLArray()) createVoidCursor();
        return data;
    }

    public Object getSingle( String name, Class<?> cl)
            throws Exception {

        String body=getXMLRegionSingle(name);

        int start = body.indexOf("<" + name + ">");
        if (start>-1)  start += name.length() + 2;else start=0;//with <and > char
        int end = body.indexOf("</" + name + ">");
        if (end == -1) body = "";else body = body.substring(start, end);

        String gname = cl.getName();

        if (cl.getName().toLowerCase().contains("string")) {
            return body;
        }else if (cl.getName().toLowerCase().contains("double")) {
            if (body.isEmpty()) return 0; else return
                    Double.parseDouble(body);
        }else if (cl.getName().toLowerCase().contains("int")) {
            if (body.isEmpty()) return 0; else return
                    Integer.parseInt(body);
        }else if (cl.getName().toLowerCase().contains("boolean")) {
            return Boolean.parseBoolean(body);
        }else{

            Serializer serializer = new Persister();

            if (!body.isEmpty())
            {
                 serializer.read(cl, body);
                 return  cl;
            }
        }


        return null;
    }


    public String getXMLRegion(String nodename) throws Exception {

        String ss ="";
        String sxml="";
        Node xmlnode;
        int cVals=0;

        try {

            InputStream istream = new ByteArrayInputStream( ws.xmlresult.getBytes() );
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(istream);

            Element root=doc.getDocumentElement();

            NodeList children=root.getChildNodes();
            Node bodyroot=children.item(0);
            NodeList body=bodyroot.getChildNodes();
            Node responseroot=body.item(0);
            NodeList response=responseroot.getChildNodes();

            ss="";

            for(int i =0;i<response.getLength();i++)
            {

                ss+=response.item(i).getNodeName()+",\n";

                if (response.item(i).getNodeName().equalsIgnoreCase(nodename))
                {
                    cVals=response.item(i).getChildNodes().getLength();

                    if (cVals>0)
                    {
                        xmlnode=response.item(i);
                        sxml=nodeToString(xmlnode);
                        return sxml;
                    }
                }
           }
        } catch (Exception e)
        {
            debg = e.getMessage() + "\n "+ ws.xmlresult;
            throw new Exception(" XMLObject getXMLRegion : "+ debg);
        }
        return "";
    }

    public String getXMLRegionSingle(String nodename) throws Exception {
        String st,ss,sv,en,sxml;
        Node xmlnode;

        try {

            InputStream istream = new ByteArrayInputStream( ws.xmlresult.getBytes() );
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(istream);

            Element root=doc.getDocumentElement();

            NodeList children=root.getChildNodes();
            Node bodyroot=children.item(0);
            NodeList body=bodyroot.getChildNodes();
            Node responseroot=body.item(0);
            NodeList response=responseroot.getChildNodes();

            ss="";
            for(int i =0;i<response.getLength();i++) {
                ss+=response.item(i).getNodeName()+",\n";

                if (response.item(i).getNodeName().equalsIgnoreCase(nodename)) {
                    xmlnode=response.item(i);
                    sxml=nodeToString(xmlnode);
                    return sxml;
                }
            }
        } catch (Exception e) {
            throw new Exception(" XMLObject getXMLRegion : "+ e.getMessage());
        }
        return "";
    }

    private String nodeToString(Node node)  throws Exception {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (Exception te) {
             throw new Exception("XMLObject nodeToString : "+te.getMessage());
        }
        return sw.toString();
    }

    private boolean parseXMLArray() throws Exception {
        String sv,en;

        try {
            InputStream istream = new ByteArrayInputStream(ws.xmlresult.getBytes() );
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(istream);

            NodeList nList = null;
            try {
                nList = doc.getElementsByTagName("DocumentElement");
                NodeList ccList=nList.item(0).getChildNodes();
                adimy=ccList.getLength();
                NodeList vvList=ccList.item(0).getChildNodes();
                adimx=vvList.getLength();
            } catch (Exception e) {
                return false;
            }

            String[] crow = new String[adimx];
            MatrixCursor cursor = new MatrixCursor(crow);

            for (int i =0;i<nList.getLength();i++){

                NodeList cList=nList.item(i).getChildNodes();

                for (int ii =0;ii<cList.getLength();ii++){

                    Element elm = (Element) cList.item(ii);
                    NodeList vList=cList.item(ii).getChildNodes();

                    for (int vv =0;vv<vList.getLength();vv++){
                        en=vList.item(vv).getNodeName();
                        sv=getNodeValue(en,elm);
                        crow[vv]=sv;
                    }

                    cursor.addRow(crow);
                }
            }

            data=cursor;
            return true;
        } catch (Exception e) {
            throw new Exception("XMLObject parseXMLArray : "+e.getMessage());
        }
    }

    private void createVoidCursor() {
        String[] crow = new String[adimx];
        MatrixCursor cursor = new MatrixCursor(crow);
        data=cursor;
    }

    private String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        if (node!=null){
            if (node.hasChildNodes()){
                Node child = node.getFirstChild();
                while (child!=null){
                    if (child.getNodeType() == Node.TEXT_NODE){
                        return  child.getNodeValue();
                    }
                }
            }
        }
        return null;
    }

}
