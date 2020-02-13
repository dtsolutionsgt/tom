package com.dts.base;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.dts.base.WebService;

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

    public XMLObject(WebService webservice) {
        ws=webservice;
    }

    public <T> T get(Class<? extends T> type, String source) throws Exception {
        String xnode=getXMLRegion(source);
        Serializer serializer = new Persister();
        return serializer.read( type, xnode);
    }

    public <T> T getresult(Class<? extends T> type, String source) throws Exception {
        String xnode=getXMLRegion(source+"Result");
        Serializer serializer = new Persister();
        return serializer.read( type, xnode);
    }

    public Cursor filldt() throws Exception {
        if (!parseXMLArray()) createVoidCursor();
        return data;
    }

    public String getXMLRegion(String nodename) throws Exception {
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
