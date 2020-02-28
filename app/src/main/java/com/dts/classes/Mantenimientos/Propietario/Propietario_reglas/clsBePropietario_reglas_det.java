package com.dts.classes.Mantenimientos.Propietario.Propietario_reglas;


import org.simpleframework.xml.Element;

public class clsBePropietario_reglas_det {

    @Element(required=false) public int IdReglaPropietarioDet;
    @Element(required=false) public int IdReglaPropietarioEnc;
    @Element(required=false) public int IdDestinatarioPropietario;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public String NombreDestinatario;


    public clsBePropietario_reglas_det() {
    }

    public clsBePropietario_reglas_det(int IdReglaPropietarioDet,int IdReglaPropietarioEnc,int IdDestinatarioPropietario,String User_agr,
                                       String Fec_agr,String User_mod,String Fec_mod,boolean Activo,
                                       boolean IsNew,String NombreDestinatario) {

        this.IdReglaPropietarioDet=IdReglaPropietarioDet;
        this.IdReglaPropietarioEnc=IdReglaPropietarioEnc;
        this.IdDestinatarioPropietario=IdDestinatarioPropietario;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.NombreDestinatario=NombreDestinatario;

    }


    public int getIdReglaPropietarioDet() {
        return IdReglaPropietarioDet;
    }
    public void setIdReglaPropietarioDet(int value) {
        IdReglaPropietarioDet=value;
    }
    public int getIdReglaPropietarioEnc() {
        return IdReglaPropietarioEnc;
    }
    public void setIdReglaPropietarioEnc(int value) {
        IdReglaPropietarioEnc=value;
    }
    public int getIdDestinatarioPropietario() {
        return IdDestinatarioPropietario;
    }
    public void setIdDestinatarioPropietario(int value) {
        IdDestinatarioPropietario=value;
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
    public String getNombreDestinatario() {
        return NombreDestinatario;
    }
    public void setNombreDestinatario(String value) {
        NombreDestinatario=value;
    }

}

