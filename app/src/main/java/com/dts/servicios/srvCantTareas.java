package com.dts.servicios;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class srvCantTareas extends srvBaseJob {

    private wsCantTareas ws;
    private Runnable rnListaTareas;

    private int idbodega, idoperador;
    private String params, listatareas="";
    private String fname = Environment.getExternalStorageDirectory().getPath()+"/tom_tareas.txt";

    @Override
    public void execute() {

        rnListaTareas = new Runnable() {
            public void run() {
                listatareas =ws.retval;
                procesaLista();
                startCantTareas.startService(getApplicationContext(),params);
            }
        };

        ws=new wsCantTareas(URL,idbodega,idoperador);
        ws.execute(rnListaTareas);

        //notification("TomIMS service");
    }

    @Override
    public boolean loadParams(String paramstr) {
        params=paramstr;

        try {
            String[] sp = params.split("#");

            URL=sp[0];
            idbodega =Integer.parseInt(sp[1]);
            idoperador =Integer.parseInt(sp[2]);

            return true;
        } catch (Exception e) {
            URL="";
            idbodega =0;idoperador =0;
            error=e.getMessage();return false;
        }
    }

    private void procesaLista() {
        FileWriter wfile=null;
        BufferedWriter writer=null;

        try {
            wfile = new FileWriter(fname, false);
            writer = new BufferedWriter(wfile);
            writer.write(listatareas);writer.write("\r\n");
            writer.close();writer = null;wfile = null;
        } catch (Exception e) {
            //notification("TOM IMS error : "+e.getMessage());
        }
    }

}
