package com.dts.classes.Transacciones.Movimiento.USUbicSingle;

import org.simpleframework.xml.Element;

public class USUbicSingle {

    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public String Orientacion="";
    @Element(required=false) public boolean Bloqueada=false;
    @Element(required=false) public boolean Acepta_Pallet=false;
    @Element(required=false) public boolean Ubicacion_Merma=false;
    @Element(required=false) public boolean Danado=false;


    public USUbicSingle() {
    }

    public USUbicSingle(int IdUbicacion,String Orientacion,boolean Bloqueada,boolean Acepta_Pallet,
                        boolean Ubicacion_Merma,boolean Danado) {

        this.IdUbicacion=IdUbicacion;
        this.Orientacion=Orientacion;
        this.Bloqueada=Bloqueada;
        this.Acepta_Pallet=Acepta_Pallet;
        this.Ubicacion_Merma=Ubicacion_Merma;
        this.Danado=Danado;

    }


    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public String getOrientacion() {
        return Orientacion;
    }
    public void setOrientacion(String value) {
        Orientacion=value;
    }
    public boolean getBloqueada() {
        return Bloqueada;
    }
    public void setBloqueada(boolean value) {
        Bloqueada=value;
    }
    public boolean getAcepta_Pallet() {
        return Acepta_Pallet;
    }
    public void setAcepta_Pallet(boolean value) {
        Acepta_Pallet=value;
    }
    public boolean getUbicacion_Merma() {
        return Ubicacion_Merma;
    }
    public void setUbicacion_Merma(boolean value) {
        Ubicacion_Merma=value;
    }
    public boolean getDanado() {
        return Danado;
    }
    public void setDanado(boolean value) {
        Danado=value;
    }

}