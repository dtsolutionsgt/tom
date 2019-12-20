package com.dts.ws;

import com.dts.classes.clsBeOperador;
import com.dts.classes.clsBeOperador_bodega;
import com.dts.tom.PBase;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetOperadoresByBodegaForHH extends WebServiceBase {

    public ArrayList<clsBeOperador_bodega> items = new ArrayList<clsBeOperador_bodega>();

    public GetOperadoresByBodegaForHH(PBase Parent, String Url) {
        super(Parent,Url);
    }

    @Override
    public void dataCallback() {
        clsBeOperador_bodega item;
        int rc;

        items.clear();
        rc=response.getPropertyCount();

        for (int i = 0; i < rc; i++) {

            Object property = response.getProperty(i);
            if (property instanceof SoapObject) {

                SoapObject xmlitem = (SoapObject) property;

                //Nombres de campos deber respetar Mayusculas
                item=new clsBeOperador_bodega();
                item.idoperador= Integer.parseInt(xmlitem.getProperty("IdOperador").toString());
                item.idbodega= Integer.parseInt(xmlitem.getProperty("IdBodega").toString());
                items.add(item);

            }
        }

        //Collections.sort(items, new Sorter());

    }

    /*
    public class Sorter implements Comparator<clsBeOperador> {
        public int compare(clsBeOperador_bodega left, clsBeOperador_bodega right) {
            return left.idoperador.compareTo(right.idoperador);
        }
    }
    */
}
