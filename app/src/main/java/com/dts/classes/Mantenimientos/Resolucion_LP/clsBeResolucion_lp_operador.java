package com.dts.classes.Mantenimientos.Resolucion_LP;

import org.simpleframework.xml.Element;

public class clsBeResolucion_lp_operador {

    @Element(required=false) public int IdResolucionlp=0;
    @Element(required=false) public int IdOperador=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public String Serie="";
    @Element(required=false) public float Correlativo_Inicial=0;
    @Element(required=false) public float Correlativo_Final=0;
    @Element(required=false) public float Correlativo_Actual=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean IsNew=false;


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

    public int getIdResolucionlp() {
        return IdResolucionlp;
    }
    public void setIdResolucionlp(int value) {
        IdResolucionlp=value;
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

    public String getSerie() {
        return Serie;
    }
    public void setSerie(String value) {
        Serie=value;
    }

    public float getCorrelativo_Inicial() {
        return Correlativo_Inicial;
    }
    public void setCorrelativo_Inicial(float value) {
        Correlativo_Inicial=value;
    }

    public float getCorrelativo_Final() {
        return Correlativo_Final;
    }
    public void setCorrelativo_Final(float value) {
        Correlativo_Final=value;
    }

    public float getCorrelativo_Actual() {
        return Correlativo_Actual;
    }
    public void setCorrelativo_Actual(float value) {
        Correlativo_Actual=value;
    }

    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}
