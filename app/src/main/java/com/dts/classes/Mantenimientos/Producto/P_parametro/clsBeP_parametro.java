package com.dts.classes.Mantenimientos.Producto.P_parametro;


import org.simpleframework.xml.Element;

public class clsBeP_parametro {

    @Element(required=false) public int IdParametro=0;
    @Element(required=false) public String Tipo="";
    @Element(required=false) public String Descripcion="";
    @Element(required=false) public String Valor_texto="";
    @Element(required=false) public double Valor_numerico=0;
    @Element(required=false) public String Valor_fecha="1900-01-01T00:00:01";
    @Element(required=false) public int Valor_logico=0;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";


    public clsBeP_parametro() {
    }

    public clsBeP_parametro(int IdParametro,String Tipo,String Descripcion,String Valor_texto,
                            double Valor_numerico,String Valor_fecha,int Valor_logico,boolean Activo,
                            String User_agr,String Fec_agr,String User_mod,String Fec_mod
    ) {

        this.IdParametro=IdParametro;
        this.Tipo=Tipo;
        this.Descripcion=Descripcion;
        this.Valor_texto=Valor_texto;
        this.Valor_numerico=Valor_numerico;
        this.Valor_fecha=Valor_fecha;
        this.Valor_logico=Valor_logico;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;

    }


    public int getIdParametro() {
        return IdParametro;
    }
    public void setIdParametro(int value) {
        IdParametro=value;
    }
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String value) {
        Tipo=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public String getValor_texto() {
        return Valor_texto;
    }
    public void setValor_texto(String value) {
        Valor_texto=value;
    }
    public double getValor_numerico() {
        return Valor_numerico;
    }
    public void setValor_numerico(double value) {
        Valor_numerico=value;
    }
    public String getValor_fecha() {
        return Valor_fecha;
    }
    public void setValor_fecha(String value) {
        Valor_fecha=value;
    }
    public int getValor_logico() {
        return Valor_logico;
    }
    public void setValor_logico(int value) {
        Valor_logico=value;
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

}

