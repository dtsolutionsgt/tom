package com.dts.classes.Mantenimientos.Producto.Producto_rellenado;


import org.simpleframework.xml.Element;

public class clsBeProducto_rellenado {

    @Element(required=false) public int IdRellenado;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdProductoEstado;
    @Element(required=false) public int IdUbicacion;
    @Element(required=false) public int IdTipoAccion;
    @Element(required=false) public double Minimo;
    @Element(required=false) public double Maximo;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public String Estado;
    @Element(required=false) public String Presentacion;
    @Element(required=false) public String Ubicacion;


    public clsBeProducto_rellenado() {
    }

    public clsBeProducto_rellenado(int IdRellenado,int IdPresentacion,int IdProductoEstado,int IdUbicacion,
                                   int IdTipoAccion,double Minimo,double Maximo,String User_agr,
                                   String Fec_agr,String User_mod,String Fec_mod,boolean Activo,
                                   boolean IsNew,String Estado,String Presentacion,String Ubicacion
    ) {

        this.IdRellenado=IdRellenado;
        this.IdPresentacion=IdPresentacion;
        this.IdProductoEstado=IdProductoEstado;
        this.IdUbicacion=IdUbicacion;
        this.IdTipoAccion=IdTipoAccion;
        this.Minimo=Minimo;
        this.Maximo=Maximo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.Estado=Estado;
        this.Presentacion=Presentacion;
        this.Ubicacion=Ubicacion;

    }


    public int getIdRellenado() {
        return IdRellenado;
    }
    public void setIdRellenado(int value) {
        IdRellenado=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdProductoEstado() {
        return IdProductoEstado;
    }
    public void setIdProductoEstado(int value) {
        IdProductoEstado=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public int getIdTipoAccion() {
        return IdTipoAccion;
    }
    public void setIdTipoAccion(int value) {
        IdTipoAccion=value;
    }
    public double getMinimo() {
        return Minimo;
    }
    public void setMinimo(double value) {
        Minimo=value;
    }
    public double getMaximo() {
        return Maximo;
    }
    public void setMaximo(double value) {
        Maximo=value;
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
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public String getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(String value) {
        Presentacion=value;
    }
    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }

}

