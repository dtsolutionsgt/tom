package com.dts.classes.Transacciones.Packing;


import org.simpleframework.xml.Element;

public class clsBeTrans_packing_enc {

    @Element(required=false) public int Idpackingenc;
    @Element(required=false) public int Idbodega;
    @Element(required=false) public int Idpickingenc;
    @Element(required=false) public int IdDespachoEnc;;
    @Element(required=false) public int Idproductobodega;
    @Element(required=false) public int Idproductoestado;
    @Element(required=false) public int Idpresentacion;
    @Element(required=false) public int Idunidadmedida;
    @Element(required=false) public String Lote;
    @Element(required=false) public String Fecha_vence;
    @Element(required=false) public String Lic_plate;
    @Element(required=false) public int No_linea;
    @Element(required=false) public double Cantidad_bultos_packing;
    @Element(required=false) public double Cantidad_camas_packing;
    @Element(required=false) public int Idoperadorbodega;
    @Element(required=false) public int Idempresaservicio;
    @Element(required=false) public String Referencia;
    @Element(required=false) public String Fecha_packing;

    @Element(required=false) public String nom_prod;
    @Element(required=false) public String CodigoProducto;
    @Element(required=false) public String ProductoPresentacion="";
    @Element(required=false) public String ProductoUnidadMedida="";

    public clsBeTrans_packing_enc() {
    }

    public clsBeTrans_packing_enc(int Idpackingenc,int Idbodega,int Idpickingenc,int Iddespachoenc,
                                  int Idproductobodega,int Idproductoestado,int Idpresentacion,int Idunidadmedida,
                                  String Lote,String Fecha_vence,String Lic_plate,int No_linea,
                                  double Cantidad_bultos_packing,double Cantidad_camas_packing,int Idoperadorbodega,int Idempresaservicio,
                                  String Referencia,String Fecha_packing) {

        this.Idpackingenc=Idpackingenc;
        this.Idbodega=Idbodega;
        this.Idpickingenc=Idpickingenc;
        this.IdDespachoEnc=Iddespachoenc;
        this.Idproductobodega=Idproductobodega;
        this.Idproductoestado=Idproductoestado;
        this.Idpresentacion=Idpresentacion;
        this.Idunidadmedida=Idunidadmedida;
        this.Lote=Lote;
        this.Fecha_vence=Fecha_vence;
        this.Lic_plate=Lic_plate;
        this.No_linea=No_linea;
        this.Cantidad_bultos_packing=Cantidad_bultos_packing;
        this.Cantidad_camas_packing=Cantidad_camas_packing;
        this.Idoperadorbodega=Idoperadorbodega;
        this.Idempresaservicio=Idempresaservicio;
        this.Referencia=Referencia;
        this.Fecha_packing=Fecha_packing;

    }


    public int getIdpackingenc() {
        return Idpackingenc;
    }
    public void setIdpackingenc(int value) {
        Idpackingenc=value;
    }
    public int getIdbodega() {
        return Idbodega;
    }
    public void setIdbodega(int value) {
        Idbodega=value;
    }
    public int getIdpickingenc() {
        return Idpickingenc;
    }
    public void setIdpickingenc(int value) {
        Idpickingenc=value;
    }
    public int getIddespachoenc() { return  IdDespachoEnc; }
    public void setIddespachoenc(int value) {IdDespachoEnc=value;}
    public int getIdproductobodega() {
        return Idproductobodega;
    }
    public void setIdproductobodega(int value) {
        Idproductobodega=value;
    }
    public int getIdproductoestado() {
        return Idproductoestado;
    }
    public void setIdproductoestado(int value) {
        Idproductoestado=value;
    }
    public int getIdpresentacion() {
        return Idpresentacion;
    }
    public void setIdpresentacion(int value) {
        Idpresentacion=value;
    }
    public int getIdunidadmedida() {
        return Idunidadmedida;
    }
    public void setIdunidadmedida(int value) {
        Idunidadmedida=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getFecha_vence() {
        return Fecha_vence;
    }
    public void setFecha_vence(String value) {
        Fecha_vence=value;
    }
    public String getLic_plate() {
        return Lic_plate;
    }
    public void setLic_plate(String value) {
        Lic_plate=value;
    }
    public int getNo_linea() {
        return No_linea;
    }
    public void setNo_linea(int value) {
        No_linea=value;
    }
    public double getCantidad_bultos_packing() {
        return Cantidad_bultos_packing;
    }
    public void setCantidad_bultos_packing(double value) {
        Cantidad_bultos_packing=value;
    }
    public double getCantidad_camas_packing() {
        return Cantidad_camas_packing;
    }
    public void setCantidad_camas_packing(double value) {
        Cantidad_camas_packing=value;
    }
    public int getIdoperadorbodega() {
        return Idoperadorbodega;
    }
    public void setIdoperadorbodega(int value) {
        Idoperadorbodega=value;
    }
    public int getIdempresaservicio() {
        return Idempresaservicio;
    }
    public void setIdempresaservicio(int value) {
        Idempresaservicio=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public String getFecha_packing() {
        return Fecha_packing;
    }
    public void setFecha_packing(String value) {
        Fecha_packing=value;
    }

}

