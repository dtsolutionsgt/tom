package com.dts.classes.Transacciones.Recepcion.Reglas_recepcion;


import org.simpleframework.xml.Element;

public class clsBeReglas_recepcion {

    @Element(required=false) public int IdReglaRecepcion;
    @Element(required=false) public String Codigo;
    @Element(required=false) public String Nombre;
    @Element(required=false) public String Descripcion;
    @Element(required=false) public boolean Rechazar;
    @Element(required=false) public boolean StockNoDisponible;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean Activo;


    public clsBeReglas_recepcion() {
    }

    public clsBeReglas_recepcion(int IdReglaRecepcion,String Codigo,String Nombre,String Descripcion,
                                 boolean Rechazar,boolean StockNoDisponible,String User_agr,String Fec_agr,
                                 String User_mod,String Fec_mod,boolean Activo) {

        this.IdReglaRecepcion=IdReglaRecepcion;
        this.Codigo=Codigo;
        this.Nombre=Nombre;
        this.Descripcion=Descripcion;
        this.Rechazar=Rechazar;
        this.StockNoDisponible=StockNoDisponible;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;

    }


    public int getIdReglaRecepcion() {
        return IdReglaRecepcion;
    }
    public void setIdReglaRecepcion(int value) {
        IdReglaRecepcion=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public boolean getRechazar() {
        return Rechazar;
    }
    public void setRechazar(boolean value) {
        Rechazar=value;
    }
    public boolean getStockNoDisponible() {
        return StockNoDisponible;
    }
    public void setStockNoDisponible(boolean value) {
        StockNoDisponible=value;
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

