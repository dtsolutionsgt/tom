package com.dts.classes.Transacciones.Picking;


import org.simpleframework.xml.Element;

public class clsBeTrans_picking_det_parametros {

    @Element(required=false) public int IdParametroPicking=0;
    @Element(required=false) public int IdPickingDet=0;
    @Element(required=false) public int IdProductoParametro=0;
    @Element(required=false) public String Valor_texto="";
    @Element(required=false) public double Valor_numerico=0;
    @Element(required=false) public String Valor_fecha="";
    @Element(required=false) public boolean Valor_logico=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public boolean IsNew=false;


    public clsBeTrans_picking_det_parametros() {
    }

    public clsBeTrans_picking_det_parametros(int IdParametroPicking,int IdPickingDet,int IdProductoParametro,String Valor_texto,
                                             double Valor_numerico,String Valor_fecha,boolean Valor_logico,String User_agr,
                                             String Fec_agr,boolean IsNew) {

        this.IdParametroPicking=IdParametroPicking;
        this.IdPickingDet=IdPickingDet;
        this.IdProductoParametro=IdProductoParametro;
        this.Valor_texto=Valor_texto;
        this.Valor_numerico=Valor_numerico;
        this.Valor_fecha=Valor_fecha;
        this.Valor_logico=Valor_logico;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.IsNew=IsNew;

    }


    public int getIdParametroPicking() {
        return IdParametroPicking;
    }
    public void setIdParametroPicking(int value) {
        IdParametroPicking=value;
    }
    public int getIdPickingDet() {
        return IdPickingDet;
    }
    public void setIdPickingDet(int value) {
        IdPickingDet=value;
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

}

