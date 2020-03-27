package com.dts.classes.Transacciones.Recepcion.Trans_re_fact;


import org.simpleframework.xml.Element;

public class clsBeTrans_re_fact {

    @Element(required=false) public int IdFacturaRecepcion=0;
    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int Orden=0;
    @Element(required=false) public String NoFactura="";
    @Element(required=false) public String Observacion="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public boolean Completa=false;
    @Element(required=false) public boolean IsNew=false;


    public clsBeTrans_re_fact() {
    }

    public clsBeTrans_re_fact(int IdFacturaRecepcion,int IdRecepcionEnc,int Orden,String NoFactura,
                              String Observacion,String Fec_agr,String User_agr,String Fec_mod,
                              String User_mod,boolean Completa,boolean IsNew) {

        this.IdFacturaRecepcion=IdFacturaRecepcion;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.Orden=Orden;
        this.NoFactura=NoFactura;
        this.Observacion=Observacion;
        this.Fec_agr=Fec_agr;
        this.User_agr=User_agr;
        this.Fec_mod=Fec_mod;
        this.User_mod=User_mod;
        this.Completa=Completa;
        this.IsNew=IsNew;

    }


    public int getIdFacturaRecepcion() {
        return IdFacturaRecepcion;
    }
    public void setIdFacturaRecepcion(int value) {
        IdFacturaRecepcion=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getOrden() {
        return Orden;
    }
    public void setOrden(int value) {
        Orden=value;
    }
    public String getNoFactura() {
        return NoFactura;
    }
    public void setNoFactura(String value) {
        NoFactura=value;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public boolean getCompleta() {
        return Completa;
    }
    public void setCompleta(boolean value) {
        Completa=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

