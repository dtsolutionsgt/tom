package com.dts.classes.Mantenimientos.Motivo_devolucion;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresa;

import org.simpleframework.xml.Element;

public class clsBeMotivo_devolucion {

    @Element(required=false) public int IdMotivoDevolucion=0;
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Es_detalle=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeEmpresa Empresa=new clsBeEmpresa();
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();


    public clsBeMotivo_devolucion() {
    }

    public clsBeMotivo_devolucion(int IdMotivoDevolucion,int IdEmpresa,int IdPropietario,String Nombre,
                                  String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                  boolean Activo,boolean Es_detalle,boolean IsNew,clsBeEmpresa Empresa,
                                  clsBePropietarios Propietario) {

        this.IdMotivoDevolucion=IdMotivoDevolucion;
        this.IdEmpresa=IdEmpresa;
        this.IdPropietario=IdPropietario;
        this.Nombre=Nombre;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Es_detalle=Es_detalle;
        this.IsNew=IsNew;
        this.Empresa=Empresa;
        this.Propietario=Propietario;

    }


    public int getIdMotivoDevolucion() {
        return IdMotivoDevolucion;
    }
    public void setIdMotivoDevolucion(int value) {
        IdMotivoDevolucion=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
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
    public boolean getEs_detalle() {
        return Es_detalle;
    }
    public void setEs_detalle(boolean value) {
        Es_detalle=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public clsBeEmpresa getEmpresa() {
        return Empresa;
    }
    public void setEmpresa(clsBeEmpresa value) {
        Empresa=value;
    }
    public clsBePropietarios getPropietario() {
        return Propietario;
    }
    public void setPropietario(clsBePropietarios value) {
        Propietario=value;
    }

}

