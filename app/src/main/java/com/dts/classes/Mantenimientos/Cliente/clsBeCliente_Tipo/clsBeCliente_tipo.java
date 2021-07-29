package com.dts.classes.Mantenimientos.Cliente.clsBeCliente_Tipo;

import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeCliente_tipo {

    @Element(required=false) public int IdTipoCliente=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public String NombreTipoCliente="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="";


    public clsBeCliente_tipo() {
    }

    public clsBeCliente_tipo(int IdTipoCliente,int IdPropietario,clsBePropietarios Propietario,String NombreTipoCliente,
                             boolean Activo,String User_agr,String Fec_agr,String User_mod,
                             String Fec_mod) {

        this.IdTipoCliente=IdTipoCliente;
        this.IdPropietario=IdPropietario;
        this.Propietario=Propietario;
        this.NombreTipoCliente=NombreTipoCliente;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;

    }


    public int getIdTipoCliente() {
        return IdTipoCliente;
    }
    public void setIdTipoCliente(int value) {
        IdTipoCliente=value;
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
    public String getNombreTipoCliente() {
        return NombreTipoCliente;
    }
    public void setNombreTipoCliente(String value) {
        NombreTipoCliente=value;
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

}

