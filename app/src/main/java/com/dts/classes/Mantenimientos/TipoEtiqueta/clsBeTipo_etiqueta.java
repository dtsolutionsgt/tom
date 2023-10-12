package com.dts.classes.Mantenimientos.TipoEtiqueta;

import org.simpleframework.xml.Element;

public class clsBeTipo_etiqueta {

    @Element(required=false) public int IdTipoEtiqueta=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public double Alto=0;
    @Element(required=false) public double Ancho=0;
    @Element(required=false) public double MargenIzq=0;
    @Element(required=false) public double MagenDer=0;
    @Element(required=false) public double MargenSup=0;
    @Element(required=false) public double MargenInf=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public int dpi=0;
    @Element(required=false) public String codigo_zpl="";

    @Element(required=false) public int Idclasificacion_etiqueta = 0;//#CKFK20231008 Campo faltante

    public clsBeTipo_etiqueta() {
    }

    public clsBeTipo_etiqueta(int IdTipoEtiqueta,String Nombre,double Alto,double Ancho,
                              double MargenIzq,double MagenDer,double MargenSup,double MargenInf,
                              String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                              boolean Activo,int dpi,String codigo_zpl, int Idclasificacion_etiqueta) {

        this.IdTipoEtiqueta=IdTipoEtiqueta;
        this.Nombre=Nombre;
        this.Alto=Alto;
        this.Ancho=Ancho;
        this.MargenIzq=MargenIzq;
        this.MagenDer=MagenDer;
        this.MargenSup=MargenSup;
        this.MargenInf=MargenInf;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.dpi=dpi;
        this.codigo_zpl=codigo_zpl;
        this.Idclasificacion_etiqueta = Idclasificacion_etiqueta;
    }


    public int getIdTipoEtiqueta() {
        return IdTipoEtiqueta;
    }
    public void setIdTipoEtiqueta(int value) {
        IdTipoEtiqueta=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public double getAlto() {
        return Alto;
    }
    public void setAlto(double value) {
        Alto=value;
    }
    public double getAncho() {
        return Ancho;
    }
    public void setAncho(double value) {
        Ancho=value;
    }
    public double getMargenIzq() {
        return MargenIzq;
    }
    public void setMargenIzq(double value) {
        MargenIzq=value;
    }
    public double getMagenDer() {
        return MagenDer;
    }
    public void setMagenDer(double value) {
        MagenDer=value;
    }
    public double getMargenSup() {
        return MargenSup;
    }
    public void setMargenSup(double value) {
        MargenSup=value;
    }
    public double getMargenInf() {
        return MargenInf;
    }
    public void setMargenInf(double value) {
        MargenInf=value;
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
    public int getDPI() {
        return dpi;
    }
    public void setDPI(int value) {
        dpi=value;
    }
    public String getCodigo_ZPL() {
        return codigo_zpl;
    }
    public void setCodigo_ZPL(String value) {
        codigo_zpl=value;
    }

    public int getIdclasificacion_etiqueta() {
        return Idclasificacion_etiqueta;
    }
    public void setIdclasificacion_etiqueta(int value) {
        Idclasificacion_etiqueta=value;
    }

}

