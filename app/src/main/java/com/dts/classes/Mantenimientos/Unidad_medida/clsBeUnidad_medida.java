package com.dts.classes.Mantenimientos.Unidad_medida;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeUnidad_medida {

    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Nombre="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public boolean IsNew=false;


    public clsBeUnidad_medida() {
    }

    public clsBeUnidad_medida(int IdUnidadMedida,int IdPropietario,clsBePropietarios Propietario,String Codigo,
                              String Nombre,boolean Activo,String Fec_agr,String User_mod,
                              String Fec_mod,String User_agr,boolean IsNew) {

        this.IdUnidadMedida=IdUnidadMedida;
        this.IdPropietario=IdPropietario;
        this.Propietario=Propietario;
        this.Codigo=Codigo;
        this.Nombre=Nombre;
        this.Activo=Activo;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.User_agr=User_agr;
        this.IsNew=IsNew;

    }


    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
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
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
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
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

