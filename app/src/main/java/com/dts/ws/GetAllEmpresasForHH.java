package com.dts.ws;

import com.dts.classes.clsBeEmpresa;
import com.dts.tom.PBase;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;

public class GetAllEmpresasForHH extends WebServiceBase
{

    public ArrayList<clsBeEmpresa> items = new ArrayList<clsBeEmpresa>();

    public GetAllEmpresasForHH(PBase Parent, String Url)
    {
        super(Parent,Url);
    }

    @Override
    public void dataCallback() {
        clsBeEmpresa item;
        int rc;

        try {
            items.clear();
            rc=response.getPropertyCount();

        for (int i = 0; i < rc; i++) {

            Object property = response.getProperty(i);
            if (property instanceof SoapObject) {

                    SoapObject xmlitem = (SoapObject) property;

                    //Nombres de campos deber respetar Mayusculas
                    item=new clsBeEmpresa();

                    item.idempresa= Integer.parseInt(xmlitem.getProperty("IdEmpresa").toString());
                    item.nombre= xmlitem.getProperty("Nombre").toString();
                    items.add(item);

                }
            }


        } catch (Exception e) {
            errorlevel=3;
            error="Error a procesar datos : \n"+this.getClass().getSimpleName()+"\n"+e.getMessage();
            throw new Exception(error);
        }

    }

}
