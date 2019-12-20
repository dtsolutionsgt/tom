package com.dts.ws;

import com.dts.classes.clsBeImpresora;
import com.dts.tom.PBase;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetAllImpresoraByEmpresa extends WebServiceBase {

    public ArrayList<clsBeImpresora> items = new ArrayList<clsBeImpresora>();

    public GetAllImpresoraByEmpresa(PBase Parent, String Url) {
        super(Parent,Url);
    }

    @Override
    public void dataCallback() {
        clsBeImpresora item;
        int rc;

        items.clear();
        rc=response.getPropertyCount();

        for (int i = 0; i < rc; i++) {

            Object property = response.getProperty(i);
            if (property instanceof SoapObject) {

                SoapObject xmlitem = (SoapObject) property;

                //Nombres de campos deber respetar Mayusculas
                item=new clsBeImpresora();
                item.idempresa= Integer.parseInt(xmlitem.getProperty("IdImpresora").toString());
                item.nombre= xmlitem.getProperty("Nombre").toString();
                items.add(item);

            }
        }

        Collections.sort(items, new Sorter());

    }

    public class Sorter implements Comparator<clsBeImpresora> {
        public int compare(clsBeImpresora left, clsBeImpresora right) {
            return left.nombre.compareTo(right.nombre);
        }
    }

}
