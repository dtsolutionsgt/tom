package com.dts.classes.Mantenimientos.Producto.Producto_estado;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeProducto_estado {

    @Element(required=false) public int IdEstado;
    @Element(required=false) public int IdPropietario;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public String Nombre;
    @Element(required=false) public int IdUbicacionDefecto;
    @Element(required=false) public int IdUbicacionBodegaDefecto;
    @Element(required=false) public boolean Utilizable;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean Danado;
    @Element(required=false) public boolean Sistema;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public String Descripcion;


    public clsBeProducto_estado() {
    }

    public clsBeProducto_estado(int IdEstado,int IdPropietario,clsBePropietarios Propietario,String Nombre,
                                int IdUbicacionDefecto,int IdUbicacionBodegaDefecto,boolean Utilizable,boolean Activo,
                                String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                boolean Danado,boolean Sistema,boolean IsNew,String Descripcion
    ) {

        this.IdEstado=IdEstado;
        this.IdPropietario=IdPropietario;
        this.Propietario=Propietario;
        this.Nombre=Nombre;
        this.IdUbicacionDefecto=IdUbicacionDefecto;
        this.IdUbicacionBodegaDefecto=IdUbicacionBodegaDefecto;
        this.Utilizable=Utilizable;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Danado=Danado;
        this.Sistema=Sistema;
        this.IsNew=IsNew;
        this.Descripcion=Descripcion;

    }


    public int getIdEstado() {
        return IdEstado;
    }
    public void setIdEstado(int value) {
        IdEstado=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public clsBePropietarios getPropietario() {
        return Propietario;
    }
    public void setPropietario(clsBePropietarios value) {
        Propietario=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public int getIdUbicacionDefecto() {
        return IdUbicacionDefecto;
    }
    public void setIdUbicacionDefecto(int value) {
        IdUbicacionDefecto=value;
    }
    public int getIdUbicacionBodegaDefecto() {
        return IdUbicacionBodegaDefecto;
    }
    public void setIdUbicacionBodegaDefecto(int value) {
        IdUbicacionBodegaDefecto=value;
    }
    public boolean getUtilizable() {
        return Utilizable;
    }
    public void setUtilizable(boolean value) {
        Utilizable=value;
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
    public boolean getDanado() {
        return Danado;
    }
    public void setDanado(boolean value) {
        Danado=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }

}

