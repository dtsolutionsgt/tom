package com.dts.classes;


import org.simpleframework.xml.Element;

public class clsBeImpresora {

    @Element(required=false) public int IdImpresora;
    @Element(required=false) public int IdEmpresa;
    @Element(required=false) public String Nombre;
    @Element(required=false) public String Direccion_Ip;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String mac_adress;
    @Element(required=false) public int IdBodega;


    public clsBeImpresora() {
    }

    public clsBeImpresora(int IdImpresora,int IdEmpresa,String Nombre,String Direccion_Ip,
                          String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                          boolean Activo,String mac_adress,int IdBodega) {

        this.IdImpresora=IdImpresora;
        this.IdEmpresa=IdEmpresa;
        this.Nombre=Nombre;
        this.Direccion_Ip=Direccion_Ip;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.mac_adress=mac_adress;
        this.IdBodega=IdBodega;

    }


    public int getIdImpresora() {
        return IdImpresora;
    }
    public void setIdImpresora(int value) {
        IdImpresora=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getDireccion_Ip() {
        return Direccion_Ip;
    }
    public void setDireccion_Ip(String value) {
        Direccion_Ip=value;
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
    public String getmac_adress() {
        return mac_adress;
    }
    public void setmac_adress(String value) {
        mac_adress=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }

}

