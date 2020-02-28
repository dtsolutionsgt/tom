package com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros;


import com.dts.classes.Mantenimientos.Producto.P_parametro.clsBeP_parametro;
import com.dts.classes.Mantenimientos.Producto.Producto_parametros.clsBeProducto_parametros;

import org.simpleframework.xml.Element;

public class clsBeTrans_re_det_parametros {

    @Element(required=false) public int IdParametroDet;
    @Element(required=false) public int IdRecepcionDet;
    @Element(required=false) public int IdRecepcionEnc;
    @Element(required=false) public int IdProductoParametro;
    @Element(required=false) public String Valor_texto;
    @Element(required=false) public double Valor_numerico;
    @Element(required=false) public String Valor_fecha;
    @Element(required=false) public boolean Valor_logico;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public String Valor_Unico;
    @Element(required=false) public clsBeP_parametro TipoParametro=new clsBeP_parametro();
    @Element(required=false) public clsBeProducto_parametros ProductoParametro=new clsBeProducto_parametros();


    public clsBeTrans_re_det_parametros() {
    }

    public clsBeTrans_re_det_parametros(int IdParametroDet, int IdRecepcionDet, int IdRecepcionEnc, int IdProductoParametro,
                                        String Valor_texto,double Valor_numerico,String Valor_fecha,boolean Valor_logico,
    String User_agr,String Fec_agr,boolean IsNew,String Valor_Unico,
    clsBeP_parametro TipoParametro,clsBeProducto_parametros ProductoParametro) {

        this.IdParametroDet=IdParametroDet;
        this.IdRecepcionDet=IdRecepcionDet;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdProductoParametro=IdProductoParametro;
        this.Valor_texto=Valor_texto;
        this.Valor_numerico=Valor_numerico;
        this.Valor_fecha=Valor_fecha;
        this.Valor_logico=Valor_logico;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.IsNew=IsNew;
        this.Valor_Unico=Valor_Unico;
        this.TipoParametro=TipoParametro;
        this.ProductoParametro=ProductoParametro;

    }


    public int getIdParametroDet() {
        return IdParametroDet;
    }
    public void setIdParametroDet(int value) {
        IdParametroDet=value;
    }
    public int getIdRecepcionDet() {
        return IdRecepcionDet;
    }
    public void setIdRecepcionDet(int value) {
        IdRecepcionDet=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdProductoParametro() {
        return IdProductoParametro;
    }
    public void setIdProductoParametro(int value) {
        IdProductoParametro=value;
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
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getValor_Unico() {
        return Valor_Unico;
    }
    public void setValor_Unico(String value) {
        Valor_Unico=value;
    }
    public clsBeP_parametro getTipoParametro() {
        return TipoParametro;
    }
    public void setTipoParametro(clsBeP_parametro value) {
        TipoParametro=value;
    }
    public clsBeProducto_parametros getProductoParametro() {
        return ProductoParametro;
    }
    public void setProductoParametro(clsBeProducto_parametros value) {
        ProductoParametro=value;
    }

}

