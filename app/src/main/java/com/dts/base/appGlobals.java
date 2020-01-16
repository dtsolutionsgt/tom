package com.dts.base;

import android.app.Application;

import com.dts.classes.clsBeOperador_bodega;

public class appGlobals extends Application {

    public String wsurl;
    public clsBeOperador_bodega seloper=new clsBeOperador_bodega();


    //Variable para mostrar cambio de estado o cambio de ubicación
    //1 cambio de ubicación
    //2 cambio de estado
    public int modo_cambio;


}
