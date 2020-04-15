package com.dts.classes.Mantenimientos.Operador;


import com.dts.classes.Mantenimientos.Rol.clsBeRol_operador;

import org.simpleframework.xml.Element;

public class clsBeOperador {

    @Element(required=false) public int IdOperador=0;
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public int IdRolOperador=0;
    @Element(required=false) public int IdJornada=0;
    @Element(required=false) public String Nombres="";
    @Element(required=false) public String Apellidos="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public String Telefono="";
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Clave="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public double Costo_hora=0;
    @Element(required=false) public boolean Usa_hh=false;
    @Element(required=false) public boolean Recibe=false;
    @Element(required=false) public boolean Ubica=false;
    @Element(required=false) public boolean Transporta=false;
    @Element(required=false) public boolean Pickea=false;
    @Element(required=false) public boolean Verifica=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeRol_operador RolOperador=new clsBeRol_operador();


    public clsBeOperador() {
    }

    public clsBeOperador(int IdOperador, int IdEmpresa, int IdRolOperador, int IdJornada,
                         String Nombres, String Apellidos, String Direccion, String Telefono,
                         String Codigo, String Clave, boolean Activo, String User_agr,
                         String Fec_agr, String User_mod, String Fec_mod, double Costo_hora,
                         boolean Usa_hh,boolean Recibe,boolean Ubica,
                         boolean Transporta,boolean Pickea,boolean Verifica, boolean IsNew, clsBeRol_operador RolOperador)
    {

        this.IdOperador=IdOperador;
        this.IdEmpresa=IdEmpresa;
        this.IdRolOperador=IdRolOperador;
        this.IdJornada=IdJornada;
        this.Nombres=Nombres;
        this.Apellidos=Apellidos;
        this.Direccion=Direccion;
        this.Telefono=Telefono;
        this.Codigo=Codigo;
        this.Clave=Clave;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Costo_hora=Costo_hora;
        this.Usa_hh=Usa_hh;
        this.Recibe = Recibe;
        this.Ubica = Ubica;
        this.Transporta = Transporta;
        this.Pickea = Pickea;
        this.Verifica = Verifica;
        this.IsNew = IsNew;
        this.RolOperador=RolOperador;

    }


    public int getIdOperador() {
        return IdOperador;
    }
    public void setIdOperador(int value) {
        IdOperador=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public int getIdRolOperador() {
        return IdRolOperador;
    }
    public void setIdRolOperador(int value) {
        IdRolOperador=value;
    }
    public int getIdJornada() {
        return IdJornada;
    }
    public void setIdJornada(int value) {
        IdJornada=value;
    }
    public String getNombres() {
        return Nombres;
    }
    public void setNombres(String value) {
        Nombres=value;
    }
    public String getApellidos() {
        return Apellidos;
    }
    public void setApellidos(String value) {
        Apellidos=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String value) {
        Telefono=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getClave() {
        return Clave;
    }
    public void setClave(String value) {
        Clave=value;
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
    public double getCosto_hora() {
        return Costo_hora;
    }
    public void setCosto_hora(double value) {
        Costo_hora=value;
    }
    public boolean getUsa_hh() {
        return Usa_hh;
    }
    public void setUsa_hh(boolean value) {
        Usa_hh=value;
    }
    public boolean getRecibe() {
        return Recibe;
    }
    public void setRecibe(boolean value) {
        Recibe=value;
    }
    public boolean getUbica() {
        return Ubica;
    }
    public void setUbica(boolean value) {
        Ubica=value;
    }
    public boolean getTransporta() {
        return Transporta;
    }
    public void setTransporta(boolean value) {
        Transporta=value;
    }
    public boolean getPickea() {
        return Pickea;
    }
    public void setPickea(boolean value) {
        Pickea=value;
    }
    public boolean getVerifica() {
        return Verifica;
    }
    public void setVerifica(boolean value) {
        Verifica=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNe(boolean value) {
        IsNew =value;
    }
    public clsBeRol_operador getRolOperador() {
        return RolOperador;
    }
    public void setRolOperador(clsBeRol_operador value) {
        RolOperador=value;
    }

}

