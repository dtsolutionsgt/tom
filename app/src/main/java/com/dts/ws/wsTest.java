package com.dts.ws;

import com.dts.tom.PBase;

import org.ksoap2.serialization.SoapObject;

public class wsTest extends WebServiceBase{

    public wsTest(PBase Parent, String Url) {
        super(Parent,Url);
    }

    @Override
    public void dataCallback() {
        String str,vv,val1,val2,val3,val4,val5;
        int rc;

        rc=response.getPropertyCount();

        for (int i = 0; i < rc; i++) {
            Object property = response.getProperty(i);

            if (property instanceof SoapObject) {

                SoapObject item = (SoapObject) property;

                val1= item.getProperty("code").toString();
                val2= item.getProperty("cant").toString();
                val3= item.getProperty("total").toString();

                //SoapObject sitem = (SoapObject) item.getProperty("item");
                //val4= sitem.getProperty("code").toString();

                str = val1+" , "+val2+" , "+val3+"\n";
                rawdata+=str+"\n";
            }
        }


    }

}
