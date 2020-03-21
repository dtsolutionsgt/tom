package com.dts.classes.Mantenimientos.Proveedor.Proveedor_bodega;


import com.dts.classes.Mantenimientos.Proveedor.clsBeProveedor;

import org.simpleframework.xml.Element;

public class clsBeProveedor_bodega {

    @Element(required=false) public int IdAsignacion;
    @Element(required=false) public int IdProveedor;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public clsBeProveedor Proveedor=new clsBeProveedor();


    public clsBeProveedor_bodega() {
    }

    public clsBeProveedor_bodega(int IdAsignacion,int IdProveedor,int IdBodega,boolean Activo,
                                 String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                 clsBeProveedor Proveedor) {

        this.IdAsignacion=IdAsignacion;
        this.IdProveedor=IdProveedor;
        this.IdBodega=IdBodega;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Proveedor=Proveedor;

    }


    public int getIdAsignacion() {
        return IdAsignacion;
    }
    public void setI(int  value) {
        IdAsignacion=value;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setIdProveedor(int value) {
        IdProveedor=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
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
    public clsBeProveedor getProveedor() {
        return Proveedor;
    }
    public void setProveedor(clsBeProveedor value) {
        Proveedor=value;
    }

}

