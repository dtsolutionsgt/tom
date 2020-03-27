package com.dts.classes.Mantenimientos.Producto.Producto_presentacion_tarima;


import org.simpleframework.xml.Element;

public class clsBeProducto_presentacion_tarima {

    @Element(required=false) public int IdPresentacionTarima=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdTipoTarima=0;
    @Element(required=false) public double CantidadPorCama=0;
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public String Presentacion="";
    @Element(required=false) public String TipoTarima="";


    public clsBeProducto_presentacion_tarima() {
    }

    public clsBeProducto_presentacion_tarima(int IdPresentacionTarima,int IdPresentacion,int IdTipoTarima,double CantidadPorCama,
                                             double Cantidad,String User_agr,String Fec_agr,String User_mod,
                                             String Fec_mod,boolean Activo,boolean IsNew,String Presentacion,
                                             String TipoTarima) {

        this.IdPresentacionTarima=IdPresentacionTarima;
        this.IdPresentacion=IdPresentacion;
        this.IdTipoTarima=IdTipoTarima;
        this.CantidadPorCama=CantidadPorCama;
        this.Cantidad=Cantidad;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.Presentacion=Presentacion;
        this.TipoTarima=TipoTarima;

    }


    public int getIdPresentacionTarima() {
        return IdPresentacionTarima;
    }
    public void setIdPresentacionTarima(int value) {
        IdPresentacionTarima=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdTipoTarima() {
        return IdTipoTarima;
    }
    public void setIdTipoTarima(int value) {
        IdTipoTarima=value;
    }
    public double getCantidadPorCama() {
        return CantidadPorCama;
    }
    public void setCantidadPorCama(double value) {
        CantidadPorCama=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
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
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(String value) {
        Presentacion=value;
    }
    public String getTipoTarima() {
        return TipoTarima;
    }
    public void setTipoTarima(String value) {
        TipoTarima=value;
    }

}

