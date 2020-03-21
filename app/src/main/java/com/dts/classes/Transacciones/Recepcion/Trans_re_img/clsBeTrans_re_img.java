package com.dts.classes.Transacciones.Recepcion.Trans_re_img;


import org.simpleframework.xml.Element;

public class clsBeTrans_re_img {

    @Element(required=false) public int IdImagen;
    @Element(required=false) public int IdRecepcionEnc;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String Observacion;
    @Element(required=false) public boolean IsNew;


    public clsBeTrans_re_img() {
    }

    public clsBeTrans_re_img(int IdImagen,int IdRecepcionEnc,String User_agr,String Fec_agr,
                             String Observacion,boolean IsNew) {

        this.IdImagen=IdImagen;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.Observacion=Observacion;
        this.IsNew=IsNew;

    }


    public int getIdImagen() {
        return IdImagen;
    }
    public void setIdImagen(int value) {
        IdImagen=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
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
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

