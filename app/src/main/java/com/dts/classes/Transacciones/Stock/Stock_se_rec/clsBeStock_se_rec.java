package com.dts.classes.Transacciones.Stock.Stock_se_rec;


import org.simpleframework.xml.Element;

public class clsBeStock_se_rec {

    @Element(required=false) public int IdStockSeRec=0;
    @Element(required=false) public int IdStockRec=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public String NoSerie="";
    @Element(required=false) public String NoSerieInicial="";
    @Element(required=false) public String NoSerieFinal="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Regularizado=false;
    @Element(required=false) public String Fecha_regularizacion="1900-01-01T00:00:01";
    @Element(required=false) public boolean IsNew=false;


    public clsBeStock_se_rec() {
    }

    public clsBeStock_se_rec(int IdStockSeRec,int IdStockRec,int IdProductoBodega,String NoSerie,
                             String NoSerieInicial,String NoSerieFinal,String User_agr,String Fec_agr,
                             String User_mod,String Fec_mod,boolean Activo,boolean Regularizado,
                             String Fecha_regularizacion,boolean IsNew) {

        this.IdStockSeRec=IdStockSeRec;
        this.IdStockRec=IdStockRec;
        this.IdProductoBodega=IdProductoBodega;
        this.NoSerie=NoSerie;
        this.NoSerieInicial=NoSerieInicial;
        this.NoSerieFinal=NoSerieFinal;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Regularizado=Regularizado;
        this.Fecha_regularizacion=Fecha_regularizacion;
        this.IsNew=IsNew;

    }


    public int getIdStockSeRec() {
        return IdStockSeRec;
    }
    public void setIdStockSeRec(int value) {
        IdStockSeRec=value;
    }
    public int getIdStockRec() {
        return IdStockRec;
    }
    public void setIdStockRec(int value) {
        IdStockRec=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public String getNoSerie() {
        return NoSerie;
    }
    public void setNoSerie(String value) {
        NoSerie=value;
    }
    public String getNoSerieInicial() {
        return NoSerieInicial;
    }
    public void setNoSerieInicial(String value) {
        NoSerieInicial=value;
    }
    public String getNoSerieFinal() {
        return NoSerieFinal;
    }
    public void setNoSerieFinal(String value) {
        NoSerieFinal=value;
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
    public boolean getRegularizado() {
        return Regularizado;
    }
    public void setRegularizado(boolean value) {
        Regularizado=value;
    }
    public String getFecha_regularizacion() {
        return Fecha_regularizacion;
    }
    public void setFecha_regularizacion(String value) {
        Fecha_regularizacion=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

