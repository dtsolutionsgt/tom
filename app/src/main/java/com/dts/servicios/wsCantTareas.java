package com.dts.servicios;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class wsCantTareas extends wsBase {

    public String retval;
    private final int idbod;
    private final int idoper;

    public wsCantTareas(String Url, int codigo_bodega, int codigo_operador) {
        super(Url);
        idbod = codigo_bodega;
        idoper = codigo_operador;
    }

    @Override
    protected void wsExecute() {
        pedidosImport();
    }

    private void pedidosImport() {
        String METHOD_NAME = "Get_Count_Tareas_Servicio";

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            PropertyInfo param1 = new PropertyInfo();
            param1.setType(Integer.class);
            param1.setName("pIdBodega");param1.setValue(idbod);
            request.addProperty(param1);

            PropertyInfo param2 = new PropertyInfo();
            param2.setType(Integer.class);
            param2.setName("pIdOperadorBodega");param2.setValue(idoper);
            request.addProperty(param2);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(NAMESPACE+METHOD_NAME, envelope);

            SoapObject result = (SoapObject) envelope.bodyIn;

            retval=result.getPropertyAsString(0);

        } catch (Exception e) {
            errflag=true;error=e.getMessage();
            retval="#"+error;
        }
    }

}
