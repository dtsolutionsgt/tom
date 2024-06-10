package com.dts.classes.Mantenimientos.Bodega;


import org.simpleframework.xml.Element;

public class clsBeBodega_muelles {

    @Element(required=false) public int IdMuelle=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public String Codigo_barra="";
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public int Color=0;
    @Element(required=false) public String Imagen="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Entrada=false;
    @Element(required=false) public boolean Salida=false;
    @Element(required=false) public int IdUbicacionDefecto=0;

    public clsBeBodega_muelles() {
    }

    public clsBeBodega_muelles(int IdMuelle,int IdBodega,String Codigo_barra,String Nombre,
                               String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                               int Color,String Imagen,boolean Activo,boolean Entrada,
                               boolean Salida, int IdUbicacionDefecto) {

        this.IdMuelle=IdMuelle;
        this.IdBodega=IdBodega;
        this.Codigo_barra=Codigo_barra;
        this.Nombre=Nombre;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Color=Color;
        this.Imagen=Imagen;
        this.Activo=Activo;
        this.Entrada=Entrada;
        this.Salida=Salida;
        this.IdUbicacionDefecto=IdUbicacionDefecto;
    }


    public int getIdMuelle() {
        return IdMuelle;
    }
    public void setIdMuelle(int value) {
        IdMuelle=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public String getCodigo_barra() {
        return Codigo_barra;
    }
    public void setCodigo_barra(String value) {
        Codigo_barra=value;
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
    public int getColor() {
        return Color;
    }
    public void setColor(int value) {
        Color=value;
    }
    public String getImagen() {
        return Imagen;
    }
    public void setImagen(String value) {
        Imagen=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getEntrada() {
        return Entrada;
    }
    public void setEntrada(boolean value) {
        Entrada=value;
    }
    public boolean getSalida() {
        return Salida;
    }
    public void setSalida(boolean value) {
        Salida=value;
    }
    public int getIdUbicacionDefecto() {
        return IdUbicacionDefecto;
    }
    public void setIdUbicacionDefecto(int value) {
        IdUbicacionDefecto=value;
    }
}

