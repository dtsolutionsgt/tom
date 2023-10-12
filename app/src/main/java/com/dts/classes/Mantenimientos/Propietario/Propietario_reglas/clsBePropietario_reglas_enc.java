package com.dts.classes.Mantenimientos.Propietario.Propietario_reglas;


import com.dts.classes.Transacciones.Recepcion.Reglas_recepcion.clsBeReglas_recepcion;

import org.simpleframework.xml.Element;

public class clsBePropietario_reglas_enc {

    @Element(required=false) public int IdReglaPropietarioEnc=0;
    @Element(required=false) public int IdReglaRecepcion=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public int IdMensajeRegla=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeReglas_recepcion Regla=new clsBeReglas_recepcion();
    @Element(required=false) public String Propietario="";
    @Element(required=false) public String Mensaje="";
    @Element(required=false) public clsBePropietario_reglas_detList ReglasDet=new clsBePropietario_reglas_detList();


    public clsBePropietario_reglas_enc() {
    }

    public clsBePropietario_reglas_enc(int IdReglaPropietarioEnc,int IdReglaRecepcion,int IdPropietario,int IdMensajeRegla,
                                       String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                       boolean Activo,boolean IsNew,clsBeReglas_recepcion Regla,String Propietario,
                                       String Mensaje,clsBePropietario_reglas_detList ReglasDet) {

        this.IdReglaPropietarioEnc=IdReglaPropietarioEnc;
        this.IdReglaRecepcion=IdReglaRecepcion;
        this.IdPropietario=IdPropietario;
        this.IdMensajeRegla=IdMensajeRegla;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.Regla=Regla;
        this.Propietario=Propietario;
        this.Mensaje=Mensaje;
        this.ReglasDet=ReglasDet;

    }


    public int getIdReglaPropietarioEnc() {
        return IdReglaPropietarioEnc;
    }
    public void setIdReglaPropietarioEnc(int value) {
        IdReglaPropietarioEnc=value;
    }
    public int getIdReglaRecepcion() {
        return IdReglaRecepcion;
    }
    public void setIdReglaRecepcion(int value) {
        IdReglaRecepcion=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public int getIdMensajeRegla() {
        return IdMensajeRegla;
    }
    public void setIdMensajeRegla(int value) {
        IdMensajeRegla=value;
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
    public clsBeReglas_recepcion getRegla() {
        return Regla;
    }
    public void setRegla(clsBeReglas_recepcion value) {
        Regla=value;
    }
    public String getPropietario() {
        return Propietario;
    }
    public void setPropietario(String value) {
        Propietario=value;
    }
    public String getMensaje() {
        return Mensaje;
    }
    public void setMensaje(String value) {
        Mensaje=value;
    }
    public clsBePropietario_reglas_detList getReglasDet() {
        return ReglasDet;
    }
    public void setReglasDet(clsBePropietario_reglas_detList value) {
        ReglasDet=value;
    }

}

