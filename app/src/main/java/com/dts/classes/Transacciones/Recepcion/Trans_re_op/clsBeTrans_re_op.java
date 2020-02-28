package com.dts.classes.Transacciones.Recepcion.Trans_re_op;


import org.simpleframework.xml.Element;

public class clsBeTrans_re_op {

    @Element(required=false) public int IdOperadorRec;
    @Element(required=false) public int IdRecepcionEnc;
    @Element(required=false) public int IdOperadorBodega;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public boolean UsaHH;


    public clsBeTrans_re_op() {
    }

    public clsBeTrans_re_op(int IdOperadorRec,int IdRecepcionEnc,int IdOperadorBodega,String User_agr,
                            String Fec_agr,String User_mod,String Fec_mod,boolean IsNew,
                            boolean UsaHH) {

        this.IdOperadorRec=IdOperadorRec;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdOperadorBodega=IdOperadorBodega;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.IsNew=IsNew;
        this.UsaHH=UsaHH;

    }


    public int getIdOperadorRec() {
        return IdOperadorRec;
    }
    public void setIdOperadorRec(int value) {
        IdOperadorRec=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdOperadorBodega() {
        return IdOperadorBodega;
    }
    public void setIdOperadorBodega(int value) {
        IdOperadorBodega=value;
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
    public boolean getUsaHH() {
        return UsaHH;
    }
    public void setUsaHH(boolean value) {
        UsaHH=value;
    }

}

