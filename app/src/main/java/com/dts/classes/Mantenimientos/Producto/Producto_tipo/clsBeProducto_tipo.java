package com.dts.classes.Mantenimientos.Producto.Producto_tipo;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeProducto_tipo {

    @Element(required=false) public int IdTipoProducto=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public String NombreTipoProducto="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Seleccion=false;
    @Element(required=false) public int idTipoProducto=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String NombrePropietario="";


    public clsBeProducto_tipo() {
    }

    public clsBeProducto_tipo(int IdTipoProducto,int IdPropietario,clsBePropietarios Propietario,String NombreTipoProducto,
                              boolean Activo,String User_agr,String Fec_agr,String User_mod,
                              String Fec_mod,boolean Seleccion,int idTipoProducto,String Nombre,
                              String NombrePropietario) {

        this.IdTipoProducto=IdTipoProducto;
        this.IdPropietario=IdPropietario;
        this.Propietario=Propietario;
        this.NombreTipoProducto=NombreTipoProducto;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Seleccion=Seleccion;
        this.idTipoProducto=idTipoProducto;
        this.Nombre=Nombre;
        this.NombrePropietario=NombrePropietario;

    }


    public int getIdTipoProducto() {
        return IdTipoProducto;
    }
    public void setIdTipoProducto(int value) {
        IdTipoProducto=value;
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
    public String getNombreTipoProducto() {
        return NombreTipoProducto;
    }
    public void setNombreTipoProducto(String value) {
        NombreTipoProducto=value;
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
    public boolean getSeleccion() {
        return Seleccion;
    }
    public void setSeleccion(boolean value) {
        Seleccion=value;
    }
    public int getidTipoProducto() {
        return idTipoProducto;
    }
    public void setidTipoProducto(int value) {
        idTipoProducto=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getNombrePropietario() {
        return NombrePropietario;
    }
    public void setNombrePropietario(String value) {
        NombrePropietario=value;
    }

}

