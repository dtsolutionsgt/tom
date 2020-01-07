package com.dts.ws;

import android.util.Xml;

import com.dts.classes.clsBeEmpresa;
import com.dts.tom.PBase;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;

public class OperadorValidoForHH extends WebServiceBase {


    public OperadorValidoForHH(PBase Parent, String Url) {
        super(Parent,Url);
    }

    @Override
    public void dataCallback() throws Exception {

        try {
            scalar=scalarresponse.toString();
        } catch (Exception e) {
            errorlevel=3;scalar="";
            error="Error a procesar datos : \n"+this.getClass().getSimpleName()+"\n"+e.getMessage();
            throw new Exception(error);
        }

    }

    public String writeXml(){
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            xmlSerializer.setOutput(writer);
            // Start Document
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            // Start Root
            xmlSerializer.startTag("","Root");

            //region Item 1
            xmlSerializer.startTag("","clsBeOperador_bodega");


            xmlSerializer.startTag("","IdOperadorBodega");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","IdOperadorBodega");

            xmlSerializer.startTag("","IdOperador");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","IdOperador");

            xmlSerializer.startTag("","IdBodega");
            xmlSerializer.text(""+10);xmlSerializer.endTag("","IdBodega");

            xmlSerializer.startTag("","Activo");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","Activo");


            //region subitem clsBeOperator

            xmlSerializer.startTag("","Operador");

            xmlSerializer.startTag("","IdOperador");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","IdOperador");

            xmlSerializer.startTag("","IdEmpresa");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","IdEmpresa");

            xmlSerializer.startTag("","IdRolOperador");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","IdRolOperador");

            xmlSerializer.startTag("","IdJornada");
            xmlSerializer.text(""+1);xmlSerializer.endTag("","IdJornada");

            xmlSerializer.startTag("","Nombres");
            xmlSerializer.text("Francisco");xmlSerializer.endTag("","Nombres");

            xmlSerializer.startTag("","Apellidos");
            xmlSerializer.text("Gonzalez");xmlSerializer.endTag("","Apellidos");

            xmlSerializer.startTag("","Direccion");
            xmlSerializer.text("Direccion");xmlSerializer.endTag("","Direccion");

            xmlSerializer.startTag("","Telefono");
            xmlSerializer.text("Telefono");xmlSerializer.endTag("","Telefono");

            xmlSerializer.startTag("","Codigo");
            xmlSerializer.text("1");xmlSerializer.endTag("","Codigo");

            xmlSerializer.startTag("","Clave");
            xmlSerializer.text("123");xmlSerializer.endTag("","Clave");

            xmlSerializer.startTag("","Activo");
            xmlSerializer.text("true");xmlSerializer.endTag("","Activo");

            /*
            xmlSerializer.startTag("","User_agr");
            xmlSerializer.text("1");xmlSerializer.endTag("","User_agr");

            xmlSerializer.startTag("","Fec_agr");
            xmlSerializer.text("2018-11-13T15:57:56.703");xmlSerializer.endTag("","Fec_agr");

            xmlSerializer.startTag("","User_mod");
            xmlSerializer.text("3");xmlSerializer.endTag("","User_mod");

            xmlSerializer.startTag("","Fec_mod");
            xmlSerializer.text("2018-11-13T15:57:56.703");xmlSerializer.endTag("","Fec_mod");

            xmlSerializer.startTag("","Costo_hora");
            xmlSerializer.text("0");xmlSerializer.endTag("","Costo_hora");

            xmlSerializer.startTag("","Usa_hh");
            xmlSerializer.text("false");xmlSerializer.endTag("","Usa_hh");
            */

            xmlSerializer.endTag("","Operador");
            //endregion subitem clsBeOperator

            xmlSerializer.endTag("","clsBeOperador_bodega");
            //endregion Item 1


            // End Root
            xmlSerializer.endTag("","Root");
            // End Document
            xmlSerializer.endDocument();

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
