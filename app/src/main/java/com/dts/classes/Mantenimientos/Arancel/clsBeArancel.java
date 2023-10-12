package com.dts.classes.Mantenimientos.Arancel;


import org.simpleframework.xml.Element;

public class clsBeArancel {

    @Element(required=false) public int IdArancel;
    @Element(required=false) public String Nombre;
    @Element(required=false) public double Porcentaje;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod;
    @Element(required=false) public boolean Activo;

    public clsBeArancel() {
    }

    public clsBeArancel(int IdArancel,String Nombre,double Porcentaje,String Fec_agr,
                        String User_agr,String Fec_mod,String User_mod,boolean Activo
    ) {

        this.IdArancel=IdArancel;
        this.Nombre=Nombre;
        this.Porcentaje=Porcentaje;
        this.Fec_agr=Fec_agr;
        this.User_agr=User_agr;
        this.Fec_mod=Fec_mod;
        this.User_mod=User_mod;
        this.Activo=Activo;

    }


    public int getIdArancel() {
        return IdArancel;
    }
    public void setIdArancel(int value) {
        IdArancel=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public double getPorcentaje() {
        return Porcentaje;
    }
    public void setPorcentaje(double value) {
        Porcentaje=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }

}

