package com.dts.classes.Mantenimientos.Producto.Producto_estado_ubic;


import org.simpleframework.xml.Element;

public class clsBeProducto_estado_ubic {

    @Element(required=false) public int IdProductoEstadUbic=0;
    @Element(required=false) public int IdEstado=0;
    @Element(required=false) public int IdUbicacionDefecto=0;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public String Estado="";
    @Element(required=false) public String Ubicacion="";
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public String Bodega="";


    public clsBeProducto_estado_ubic() {
    }

    public clsBeProducto_estado_ubic(int IdProductoEstadUbic,int IdEstado,int IdUbicacionDefecto,String Fec_agr,
                                     String User_agr,String Fec_mod,String User_mod,boolean Activo,
                                     boolean IsNew,String Estado,String Ubicacion,int IdBodega,
                                     String Bodega) {

        this.IdProductoEstadUbic=IdProductoEstadUbic;
        this.IdEstado=IdEstado;
        this.IdUbicacionDefecto=IdUbicacionDefecto;
        this.Fec_agr=Fec_agr;
        this.User_agr=User_agr;
        this.Fec_mod=Fec_mod;
        this.User_mod=User_mod;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.Estado=Estado;
        this.Ubicacion=Ubicacion;
        this.IdBodega=IdBodega;
        this.Bodega=Bodega;

    }


    public int getIdProductoEstadUbic() {
        return IdProductoEstadUbic;
    }
    public void setIdProductoEstadUbic(int value) {
        IdProductoEstadUbic=value;
    }
    public int getIdEstado() {
        return IdEstado;
    }
    public void setIdEstado(int value) {
        IdEstado=value;
    }
    public int getIdUbicacionDefecto() {
        return IdUbicacionDefecto;
    }
    public void setIdUbicacionDefecto(int value) {
        IdUbicacionDefecto=value;
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
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public String getBodega() {
        return Bodega;
    }
    public void setBodega(String value) {
        Bodega=value;
    }

}


