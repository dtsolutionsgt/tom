package com.dts.classes.Transacciones.Recepcion.Trans_re_det_lote_num;


import org.simpleframework.xml.Element;

public class clsBeTrans_re_det_lote_num {

    @Element(required=false) public int IdLoteNum=0;
    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Lote="";
    @Element(required=false) public int Lote_Numerico=0;
    @Element(required=false) public String FechaIngreso="1900-01-01T00:00:01";
    @Element(required=false) public double Cantidad=0;


    public clsBeTrans_re_det_lote_num() {
    }

    public clsBeTrans_re_det_lote_num(int IdLoteNum,int IdRecepcionEnc,int IdProductoBodega,String Codigo,
                                      String Lote,int Lote_Numerico,String FechaIngreso,double Cantidad
    ) {

        this.IdLoteNum=IdLoteNum;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdProductoBodega=IdProductoBodega;
        this.Codigo=Codigo;
        this.Lote=Lote;
        this.Lote_Numerico=Lote_Numerico;
        this.FechaIngreso=FechaIngreso;
        this.Cantidad=Cantidad;

    }


    public int getIdLoteNum() {
        return IdLoteNum;
    }
    public void setIdLoteNum(int value) {
        IdLoteNum=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public int getLote_Numerico() {
        return Lote_Numerico;
    }
    public void setLote_Numerico(int value) {
        Lote_Numerico=value;
    }
    public String getFechaIngreso() {
        return FechaIngreso;
    }
    public void setFechaIngreso(String value) {
        FechaIngreso=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }

}

