package com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion;

import org.simpleframework.xml.Element;

public class clsBeMotivo_ubicacion {

    @Element(required=false) public int IdEmpresa;
    @Element(required=false) public int IdMotivoUbicacion;
    @Element(required=false) public String Nombre;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;


    public clsBeMotivo_ubicacion() {
    }

    public clsBeMotivo_ubicacion(int IdEmpresa,int IdMotivoUbicacion,String Nombre,String User_agr,
                                 String Fec_agr,String User_mod,String Fec_mod,boolean Activo
    ) {

        this.IdEmpresa=IdEmpresa;
        this.IdMotivoUbicacion=IdMotivoUbicacion;
        this.Nombre=Nombre;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;

    }


    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public int getIdMotivoUbicacion() {
        return IdMotivoUbicacion;
    }
    public void setIdMotivoUbicacion(int value) {
        IdMotivoUbicacion=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }

}

