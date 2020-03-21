package com.dts.classes.Mantenimientos.Producto.Producto_parametros;


import com.dts.classes.Mantenimientos.Producto.P_parametro.clsBeP_parametro;

import org.simpleframework.xml.Element;

public class clsBeProducto_parametros {

    @Element(required=false) public int IdProductoParametro;
    @Element(required=false) public int IdParametro;
    @Element(required=false) public int IdProducto;
    @Element(required=false) public String Valor_texto;
    @Element(required=false) public double Valor_numerico;
    @Element(required=false) public String Valor_fecha;
    @Element(required=false) public boolean Valor_logico;
    @Element(required=false) public boolean Capturar_siempre;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public clsBeP_parametro TipoParametro=new clsBeP_parametro();
    @Element(required=false) public String Valor_Unico;


    public clsBeProducto_parametros() {
    }

    public clsBeProducto_parametros(int IdProductoParametro,int IdParametro,int IdProducto,String Valor_texto,
                                    double Valor_numerico,String Valor_fecha,boolean Valor_logico,boolean Capturar_siempre,
    String User_agr,String Fec_agr,String User_mod,String Fec_mod,
    boolean Activo,boolean IsNew,clsBeP_parametro TipoParametro,String Valor_Unico
            ) {

        this.IdProductoParametro=IdProductoParametro;
        this.IdParametro=IdParametro;
        this.IdProducto=IdProducto;
        this.Valor_texto=Valor_texto;
        this.Valor_numerico=Valor_numerico;
        this.Valor_fecha=Valor_fecha;
        this.Valor_logico=Valor_logico;
        this.Capturar_siempre=Capturar_siempre;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.TipoParametro=TipoParametro;
        this.Valor_Unico=Valor_Unico;

    }


    public int getIdProductoParametro() {
        return IdProductoParametro;
    }
    public void setIdProductoParametro(int value) {
        IdProductoParametro=value;
    }
    public int getIdParametro() {
        return IdParametro;
    }
    public void setIdParametro(int value) {
        IdParametro=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
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
    public boolean getValor_logico() {
        return Valor_logico;
    }
    public void setValor_logico(boolean value) {
        Valor_logico=value;
    }
    public boolean getCapturar_siempre() {
        return Capturar_siempre;
    }
    public void setCapturar_siempre(boolean value) {
        Capturar_siempre=value;
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
    public clsBeP_parametro getTipoParametro() {
        return TipoParametro;
    }
    public void setTipoParametro(clsBeP_parametro value) {
        TipoParametro=value;
    }
    public String getValor_Unico() {
        return Valor_Unico;
    }
    public void setValor_Unico(String value) {
        Valor_Unico=value;
    }

}

