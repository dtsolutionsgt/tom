package com.dts.servicios;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class srvCantTareas extends srvBaseJob {

    private wsCantTareas ws;
    private Runnable rnListaTareas;

    private int idbodega, idoperador, nuevas;
    private String params, ntext, listatareas="";
    private String fname = Environment.getExternalStorageDirectory().getPath()  + "/tom_tareas.txt";
    private String taskdir= Environment.getExternalStorageDirectory().getPath() + "/tomtask";


    @Override
    public void execute() {

        rnListaTareas = new Runnable() {
            public void run() {
                listatareas =ws.retval;
                procesaTareas();
                startCantTareas.startService(getApplicationContext(),params);
            }
        };

        ws=new wsCantTareas(URL,idbodega,idoperador);
        ws.execute(rnListaTareas);

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

    private void procesaTareas() {
        String[] sp;
        String lista,ss;

        nuevas=0;ntext="";

        try {
            sp=listatareas.split(";");
            lista=sp[0];

            procesaLista(lista);

            for (int i = 1; i <sp.length; i++) {
                if (esnuevaTarea(sp[i])) nuevas++;
            }
        } catch (Exception e) {

        }

        if (nuevas>0) notifynew(ntext);
    }

    private boolean esnuevaTarea(String tname) {
        FileWriter wfile=null;
        BufferedWriter writer=null;
        File file;
        String fname,nx="",px;

        try {

            fname = taskdir + "/" + tname + ".txt";
            file = new File(fname);
            if (file.exists()) return false;

            try {
                px=tname.substring(0,1);
                if (px.equalsIgnoreCase("R")) {
                    nx=tname.replace("R","Recepción #");
                } else if (px.equalsIgnoreCase("P")) {
                    nx=tname.replace("P","Picking #");
                } else if (px.equalsIgnoreCase("V")) {
                    nx=tname.replace("V","Verificación #");
                } else if (px.equalsIgnoreCase("E")) {
                    nx=tname.replace("E","Cambio Estado #");
                } else if (px.equalsIgnoreCase("U")) {
                    nx=tname.replace("U","Cambio Ubicación #");
                }
                ntext+=nx+ "\n";

                wfile = new FileWriter(fname, false);
                writer = new BufferedWriter(wfile);
                writer.write(tname);writer.write("\r\n");
                writer.close();writer = null;
            } catch (IOException e) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void procesaLista(String lista) {
        FileWriter wfile=null;
        BufferedWriter writer=null;

        try {
            wfile = new FileWriter(fname, false);
            writer = new BufferedWriter(wfile);
            writer.write(lista);writer.write("\r\n");
            writer.close();writer = null;wfile = null;
        } catch (Exception e) {
            //notification("TOM IMS error : "+e.getMessage());
        }
    }

}
