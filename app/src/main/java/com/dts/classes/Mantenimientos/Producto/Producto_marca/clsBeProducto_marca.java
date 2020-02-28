package com.dts.classes.Mantenimientos.Producto.Producto_marca;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeProducto_marca {

    @Element(required=false) public int IdMarca;
    @Element(required=false) public int IdPropietario;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public String Nombre;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean IsNew;


    public clsBeProducto_marca() {
    }

    public clsBeProducto_marca(int IdMarca,int IdPropietario,clsBePropietarios Propietario,String Nombre,
                               boolean Activo,String User_agr,String Fec_agr,String User_mod,
                               String Fec_mod,boolean IsNew) {

        this.IdMarca=IdMarca;
        this.IdPropietario=IdPropietario;
        this.Propietario=Propietario;
        this.Nombre=Nombre;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.IsNew=IsNew;

    }


    public int getIdMarca() {
        return IdMarca;
    }
    public void setIdMarca(int value) {
        IdMarca=value;
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
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

