package com.dts.classes.Transacciones.Recepcion.Trans_re_oc;


import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;

import org.simpleframework.xml.Element;

public class clsBeTrans_re_oc {

    @Element(required=false) public int IdRecepcionOc=0;
    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdOrdenCompraEnc=0;
    @Element(required=false) public boolean Recepcion_ciega=false;
    @Element(required=false) public boolean Recepcion_manual=false;
    @Element(required=false) public String No_docto="";
    @Element(required=false) public String Hora_ini_hh="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_fin_hh="1900-01-01T00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public Byte Firma_operador;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeTrans_oc_enc OC=new clsBeTrans_oc_enc();


    public clsBeTrans_re_oc() {
    }

    public clsBeTrans_re_oc(int IdRecepcionOc,int IdRecepcionEnc,int IdOrdenCompraEnc,boolean Recepcion_ciega,
                            boolean Recepcion_manual,String No_docto,String Hora_ini_hh,String Hora_fin_hh,
                            String User_agr,String Fec_agr,Byte Firma_operador,boolean IsNew,
                            clsBeTrans_oc_enc OC) {

        this.IdRecepcionOc=IdRecepcionOc;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.Recepcion_ciega=Recepcion_ciega;
        this.Recepcion_manual=Recepcion_manual;
        this.No_docto=No_docto;
        this.Hora_ini_hh=Hora_ini_hh;
        this.Hora_fin_hh=Hora_fin_hh;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.Firma_operador=Firma_operador;
        this.IsNew=IsNew;
        this.OC=OC;

    }


    public int getIdRecepcionOc() {
        return IdRecepcionOc;
    }
    public void setIdRecepcionOc(int value) {
        IdRecepcionOc=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdOrdenCompraEnc() {
        return IdOrdenCompraEnc;
    }
    public void setIdOrdenCompraEnc(int value) {
        IdOrdenCompraEnc=value;
    }
    public boolean getRecepcion_ciega() {
        return Recepcion_ciega;
    }
    public void setRecepcion_ciega(boolean value) {
        Recepcion_ciega=value;
    }
    public boolean getRecepcion_manual() {
        return Recepcion_manual;
    }
    public void setRecepcion_manual(boolean value) {
        Recepcion_manual=value;
    }
    public String getNo_docto() {
        return No_docto;
    }
    public void setNo_docto(String value) {
        No_docto=value;
    }
    public String getHora_ini_hh() {
        return Hora_ini_hh;
    }
    public void setHora_ini_hh(String value) {
        Hora_ini_hh=value;
    }
    public String getHora_fin_hh() {
        return Hora_fin_hh;
    }
    public void setHora_fin_hh(String value) {
        Hora_fin_hh=value;
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
    public Byte getFirma_operador() {
        return Firma_operador;
    }
    public void setFirma_operador(Byte value) {
        Firma_operador=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public clsBeTrans_oc_enc getOC() {
        return OC;
    }
    public void setOC(clsBeTrans_oc_enc value) {
        OC=value;
    }

}

