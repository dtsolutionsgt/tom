package com.dts.ws;

import com.dts.classes.clsBeOperador;
import com.dts.classes.clsBeOperador_bodega;
import com.dts.tom.PBase;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetOperadoresByBodegaForHH extends WebServiceBase {

    private ArrayList<clsBeOperador> oitems = new ArrayList<clsBeOperador>();
    public ArrayList<clsBeOperador_bodega> items = new ArrayList<clsBeOperador_bodega>();

    public GetOperadoresByBodegaForHH(PBase Parent, String Url) {
        super(Parent,Url);
    }

    @Override
    public void dataCallback()  throws Exception {
        clsBeOperador oitem;
        clsBeOperador_bodega item;
        int rc;

        try {

            items.clear();
            oitems.clear();
            rc = response.getPropertyCount();

            for (int i = 0; i < rc; i++) {

                Object property = response.getProperty(i);
                if (property instanceof SoapObject) {

                    SoapObject xmlitem = (SoapObject) property;

                    item=new clsBeOperador_bodega();

                    item.idoperadorbodega=Integer.parseInt(xmlitem.getProperty("IdOperadorBodega").toString());
                    item.idoperador=Integer.parseInt(xmlitem.getProperty("IdOperador").toString());
                    item.idbodega=Integer.parseInt(xmlitem.getProperty("IdBodega").toString());
                    item.activo=xmlitem.getProperty("Activo").toString().equalsIgnoreCase("true");
                    item.user_agr="";
                    item.fec_agr=0;
                    item.user_mod="";
                    item.fec_mod=0;

                    SoapObject sitem = (SoapObject) xmlitem.getProperty("Operador");

                    oitem=new clsBeOperador();

                    oitem.idoperador= Integer.parseInt(sitem.getProperty("IdOperador").toString());
                    oitem.idempresa= Integer.parseInt(sitem.getProperty("IdEmpresa").toString());
                    oitem.idroloperador= Integer.parseInt(sitem.getProperty("IdRolOperador").toString());
                    oitem.idjornada= Integer.parseInt(sitem.getProperty("IdJornada").toString());
                    oitem.nombres=sitem.getProperty("Nombres").toString();
                    oitem.apellidos=sitem.getProperty("Apellidos").toString();
                    oitem.direccion="";
                    oitem.telefono="";
                    oitem.codigo=sitem.getProperty("Codigo").toString();
                    oitem.clave=sitem.getProperty("Clave").toString();
                    oitem.activo=sitem.getProperty("Activo").toString().equalsIgnoreCase("true")?1:0;
                    oitem.user_agr="";
                    oitem.fec_agr=0;
                    oitem.user_mod="";
                    oitem.fec_mod=0;
                    oitem.costo_hora=0;
                    oitem.usa_hh=0;

                    oitems.add(oitem);

                    item.operador=oitem;

                    items.add(item);
                }
            }

            Collections.sort(items, new Sorter());

        } catch (Exception e) {
            errorlevel = 3;
            error = "Error a procesar datos : \n" + this.getClass().getSimpleName() + "\n" + e.getMessage();
            throw new Exception(error);
        }
    }

    public class Sorter implements Comparator<clsBeOperador_bodega> {
        public int compare(clsBeOperador_bodega left, clsBeOperador_bodega right) {
            String sleft=left.operador.apellidos+" "+left.operador.nombres;
            String sright=right.operador.apellidos+" "+right.operador.nombres;

            return sleft.compareTo(sright);
        }
    }

}
