package com.dts.ws;

import com.dts.classes.clsTest1;
import com.dts.classes.clsTest2;
import com.dts.tom.PBase;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class wsTest extends WebServiceBase {

    public ArrayList<clsTest1> items = new ArrayList<clsTest1>();

    private clsTest1 ct;
    private clsTest2 cts;

    public wsTest(PBase Parent, String Url) {
        super(Parent,Url);
    }

    @Override
    public void dataCallback() {
        String str,vv,val1,val2,val3,val4,val5;
        int rc;

        items.clear();
        rc=response.getPropertyCount();

        for (int i = 0; i < rc; i++) {

            Object property = response.getProperty(i);

            if (property instanceof SoapObject) {

                SoapObject item = (SoapObject) property;

                val1= item.getProperty("code").toString();
                val2= item.getProperty("cant").toString();
                val3= item.getProperty("total").toString();

                ct=new clsTest1();

                ct.code=val1;
                try {ct.cant=Integer.parseInt(val2);} catch (Exception e) {ct.cant=0;}
                try {ct.total=Double.parseDouble(val3);} catch (Exception e) {ct.total=0;}

                try {  // encapsulated structure
                    SoapObject sitem = (SoapObject) item.getProperty("item");

                    cts=new clsTest2();
                    val4= sitem.getProperty("code").toString();
                    val5= sitem.getProperty("total").toString();

                    cts.code=val4;
                    try {cts.total=Double.parseDouble(val5);} catch (Exception e) {cts.total=0;}

                } catch (Exception e) {
                    cts.code="";cts.total=0;
                }

                ct.item=cts;

                items.add(ct);

                str = val1+" , "+val2+" , "+val3+"\n";
                rawdata+=str+"\n";
            }

        }

    }

}
