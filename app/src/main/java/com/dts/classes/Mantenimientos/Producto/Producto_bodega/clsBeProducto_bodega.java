package com.dts.classes.Mantenimientos.Producto.Producto_bodega;


import com.dts.classes.Mantenimientos.Producto.clsBeProducto;

import org.simpleframework.xml.Element;

public class clsBeProducto_bodega {

    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int IdProducto;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean Sistema;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();


    public clsBeProducto_bodega() {
    }

    public clsBeProducto_bodega(int IdProductoBodega,int IdProducto,int IdBodega,boolean Activo,
                                boolean Sistema,String User_agr,String Fec_agr,String User_mod,
                                String Fec_mod,clsBeProducto Producto) {

        this.IdProductoBodega=IdProductoBodega;
        this.IdProducto=IdProducto;
        this.IdBodega=IdBodega;
        this.Activo=Activo;
        this.Sistema=Sistema;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Producto=Producto;

    }


    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
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
    public clsBeProducto getProducto() {
        return Producto;
    }
    public void setProducto(clsBeProducto value) {
        Producto=value;
    }

}

