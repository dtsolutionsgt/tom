package com.dts.classes.Mantenimientos.Operador;

import org.simpleframework.xml.Element;

public class clsBeOperador_bodega {

    @Element(required=false) public int IdOperadorBodega=0;
    @Element(required=false) public int IdOperador=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeOperador Operador=new clsBeOperador();
    @Element(required=false) public String Nombre_Completo="";

    public clsBeOperador_bodega() {
    }

    public clsBeOperador_bodega(int IdOperadorBodega,int IdOperador,int IdBodega,boolean Activo,
                                String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                boolean IsNew,clsBeOperador Operador) {

        this.IdOperadorBodega=IdOperadorBodega;
        this.IdOperador=IdOperador;
        this.IdBodega=IdBodega;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.IsNew=IsNew;
        this.Operador=Operador;

    }


    public int getIdOperadorBodega() {
        return IdOperadorBodega;
    }
    public void setIdOperadorBodega(int value) {
        IdOperadorBodega=value;
    }
    public int getIdOperador() {
        return IdOperador;
    }
    public void setIdOperador(int value) {
        IdOperador=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
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
    public clsBeOperador getOperador() {
        return Operador;
    }
    public void setOperador(clsBeOperador value) {
        Operador=value;
    }

    public String getNombre_Completo()
    {
        return Nombre_Completo;
    }
    public void setNombre_Completo(String value)
    {
        Nombre_Completo=value;
    }

}

