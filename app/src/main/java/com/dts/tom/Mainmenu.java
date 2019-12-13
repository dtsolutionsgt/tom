package com.dts.tom;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.dts.ws.WebService;
import com.dts.ws.WebServiceBase;
import com.dts.ws.wsTest;

public class Mainmenu extends PBase {

    private TextView lbl1;

    //private WebService ws;

    private wsTest wsb;

    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        super.InitBase();

        lbl1=(TextView) findViewById(R.id.textView8);

        URL="http://192.168.1.94/XMLTest/webservice1.asmx";

        //ws=new WebService(Mainmenu.this,URL);
        wsb=new wsTest(Mainmenu.this,URL);
    }

    //region Events

    public void doTest(View view) {
        try{
            wsb.addParam("SQL","select * from P_LINEA");
            wsb.callMethod("getClsType1");
        } catch (Exception e){
            msgbox(e.getMessage());
        }
    }

    //endregion

    //region Main

    @Override
    public void wsCallBack(Boolean throwing,String errmsg) {
        try {
            if (throwing) throw new Exception(errmsg);
            lbl1.setText(wsb.rawdata);
        } catch (Exception e) {
            msgbox(e.getMessage()+"-\n"+errmsg);
            lbl1.setText(errmsg);
        }
    }

    //endregion

    //region Aux


    //endregion

    //region Dialogs


    //endregion

    //region Activity Events


    //endregion


}
