package com.dts.classes.Mantenimientos.Propietario.Propietario_bodega;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;
import com.dts.classes.Mantenimientos.Propietario.Propietario_reglas.clsBePropietario_reglas_encList;

import org.simpleframework.xml.Element;

public class clsBePropietario_bodega {

    @Element(required=false) public int IdPropietarioBodega;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public int IdPropietario;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public clsBePropietario_reglas_encList ReglasEnc=new clsBePropietario_reglas_encList();


    public clsBePropietario_bodega() {
    }

    public clsBePropietario_bodega(int IdPropietarioBodega,int IdBodega,int IdPropietario,String User_agr,
                                   String Fec_agr,String User_mod,String Fec_mod,boolean Activo,
                                   clsBePropietarios Propietario,clsBePropietario_reglas_encList ReglasEnc) {

        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdBodega=IdBodega;
        this.IdPropietario=IdPropietario;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Propietario=Propietario;
        this.ReglasEnc=ReglasEnc;

    }


    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
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
    public clsBePropietarios getPropietario() {
        return Propietario;
    }
    public void setPropietario(clsBePropietarios value) {
        Propietario=value;
    }
    public clsBePropietario_reglas_encList getReglasEnc() {
        return ReglasEnc;
    }
    public void setReglasEnc(clsBePropietario_reglas_encList value) {
        ReglasEnc=value;
    }

}

