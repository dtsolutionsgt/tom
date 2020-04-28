package com.dts.classes.Mantenimientos.Cliente.clsBeCliente_direccion;

import org.simpleframework.xml.Element;

public class clsBeCliente_direccion {

    @Element(required=false) public int IdDireccion=0;
    @Element(required=false) public int IdCliente=0;
    @Element(required=false) public int IdMunicipio=0;
    @Element(required=false) public String Avenida="";
    @Element(required=false) public String Calle="";
    @Element(required=false) public String No_Casa="";
    @Element(required=false) public String Zona="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public int IdRegion=0;
    @Element(required=false) public String Referencia="";
    @Element(required=false) public String Coordenada_x="";
    @Element(required=false) public String Coordenada_y="";
    @Element(required=false) public boolean Local=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public int IdPais=0;
    @Element(required=false) public int IdDepartamento=0;


    public clsBeCliente_direccion() {
    }

    public clsBeCliente_direccion(int IdDireccion,int IdCliente,int IdMunicipio,String Avenida,
                                  String Calle,String No_Casa,String Zona,String Direccion,
                                  int IdRegion,String Referencia,String Coordenada_x,String Coordenada_y,
                                  boolean Local,String User_agr,String Fec_agr,String User_mod,
                                  String Fec_mod,boolean Activo,int IdPais,int IdDepartamento
    ) {

        this.IdDireccion=IdDireccion;
        this.IdCliente=IdCliente;
        this.IdMunicipio=IdMunicipio;
        this.Avenida=Avenida;
        this.Calle=Calle;
        this.No_Casa=No_Casa;
        this.Zona=Zona;
        this.Direccion=Direccion;
        this.IdRegion=IdRegion;
        this.Referencia=Referencia;
        this.Coordenada_x=Coordenada_x;
        this.Coordenada_y=Coordenada_y;
        this.Local=Local;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IdPais=IdPais;
        this.IdDepartamento=IdDepartamento;

    }


    public int getIdDireccion() {
        return IdDireccion;
    }
    public void setIdDireccion(int value) {
        IdDireccion=value;
    }
    public int getIdCliente() {
        return IdCliente;
    }
    public void setIdCliente(int value) {
        IdCliente=value;
    }
    public int getIdMunicipio() {
        return IdMunicipio;
    }
    public void setIdMunicipio(int value) {
        IdMunicipio=value;
    }
    public String getAvenida() {
        return Avenida;
    }
    public void setAvenida(String value) {
        Avenida=value;
    }
    public String getCalle() {
        return Calle;
    }
    public void setCalle(String value) {
        Calle=value;
    }
    public String getNo_Casa() {
        return No_Casa;
    }
    public void setNo_Casa(String value) {
        No_Casa=value;
    }
    public String getZona() {
        return Zona;
    }
    public void setZona(String value) {
        Zona=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public int getIdRegion() {
        return IdRegion;
    }
    public void setIdRegion(int value) {
        IdRegion=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public String getCoordenada_x() {
        return Coordenada_x;
    }
    public void setCoordenada_x(String value) {
        Coordenada_x=value;
    }
    public String getCoordenada_y() {
        return Coordenada_y;
    }
    public void setCoordenada_y(String value) {
        Coordenada_y=value;
    }
    public boolean getLocal() {
        return Local;
    }
    public void setLocal(boolean value) {
        Local=value;
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
    public int getIdPais() {
        return IdPais;
    }
    public void setIdPais(int value) {
        IdPais=value;
    }
    public int getIdDepartamento() {
        return IdDepartamento;
    }
    public void setIdDepartamento(int value) {
        IdDepartamento=value;
    }

}

