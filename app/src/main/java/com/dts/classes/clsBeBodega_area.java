package com.dts.classes;

import java.util.Date;

import org.simpleframework.xml.Element;

public class clsBeBodega_area {

    @Element(required=false) public int IdArea;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public String Descripcion;
    @Element(required=false) public boolean Sistema;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public String Codigo;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public double Alto;
    @Element(required=false) public double Largo;
    @Element(required=false) public double Ancho;
    @Element(required=false) public double Margen_izquierdo;
    @Element(required=false) public double Margen_derecho;
    @Element(required=false) public double Margen_superior;
    @Element(required=false) public double Margen_inferior;
    @Element(required=false) public boolean Is;


    public clsBeBodega_area() {
    }

    public clsBeBodega_area(int IdArea,int IdBodega,String Descripcion,boolean Sistema,
                            String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                            String Codigo,boolean Activo,double Alto,double Largo,
                            double Ancho,double Margen_izquierdo,double Margen_derecho,double Margen_superior,
                            double Margen_inferior,boolean Is) {

        this.IdArea=IdArea;
        this.IdBodega=IdBodega;
        this.Descripcion=Descripcion;
        this.Sistema=Sistema;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Codigo=Codigo;
        this.Activo=Activo;
        this.Alto=Alto;
        this.Largo=Largo;
        this.Ancho=Ancho;
        this.Margen_izquierdo=Margen_izquierdo;
        this.Margen_derecho=Margen_derecho;
        this.Margen_superior=Margen_superior;
        this.Margen_inferior=Margen_inferior;
        this.Is=Is;

    }


    public int getIdArea() {
        return IdArea;
    }
    public void setIdArea(int value) {
        IdArea=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
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
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public double getAlto() {
        return Alto;
    }
    public void setAlto(double value) {
        Alto=value;
    }
    public double getLargo() {
        return Largo;
    }
    public void setLargo(double value) {
        Largo=value;
    }
    public double getAncho() {
        return Ancho;
    }
    public void setAncho(double value) {
        Ancho=value;
    }
    public double getMargen_izquierdo() {
        return Margen_izquierdo;
    }
    public void setMargen_izquierdo(double value) {
        Margen_izquierdo=value;
    }
    public double getMargen_derecho() {
        return Margen_derecho;
    }
    public void setMargen_derecho(double value) {
        Margen_derecho=value;
    }
    public double getMargen_superior() {
        return Margen_superior;
    }
    public void setMargen_superior(double value) {
        Margen_superior=value;
    }
    public double getMargen_inferior() {
        return Margen_inferior;
    }
    public void setMargen_inferior(double value) {
        Margen_inferior=value;
    }
    public boolean getIs() {
        return Is;
    }
    public void setIs(boolean value) {
        Is=value;
    }

}