package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.Element;

public class clsBeTrans_inv_enc_reconteo {

    @Element(required=false) public int idinvencreconteoField=0;
    @Element(required=false) public int idinventarioencField=0;
    @Element(required=false) public int reconteoField=0;
    @Element(required=false) public String estadoField="";
    @Element(required=false) public String hora_iniField ="00:00:01";
    @Element(required=false) public String hora_finField="00:00:01";
    @Element(required=false) public String user_agrField="";
    @Element(required=false) public String fec_agrField="1900-01-01T00:00:01";
    @Element(required=false) public String user_modField="";
    @Element(required=false) public String fec_modField="1900-01-01T00:00:01";
    @Element(required=false) public String ubicacionField="";
    @Element(required=false) public String codigoField="";
    @Element(required=false) public String productoField="";
    @Element(required=false) public String presentacionField="";
    @Element(required=false) public String estadoProdField="";
    @Element(required=false) public int idStockField=0;
    @Element(required=false) public String loteField="";
    @Element(required=false) public Double cantidadAnteriorField=0.0;
    @Element(required=false) public Double cantidadField=0.0;
    @Element(required=false) public Double pesoAnteriorField=0.0;
    @Element(required=false) public Double pesoField=0.0;
    @Element(required=false) public String operadorField="";
    @Element(required=false) public String fecha_VenceField="1900-01-01T00:00:01";
    @Element(required=false) public int idInvReconteoField=0;


    public clsBeTrans_inv_enc_reconteo() {
    }

    public clsBeTrans_inv_enc_reconteo(int idinvencreconteoField, int idinventarioencField, int reconteoField,
                                       String estadoField,String hora_iniField, String hora_finField,String user_agrField,
                                       String fec_agrField,String user_modField,String fec_modField,String ubicacionField,
                                       String codigoField,String productoField,String presentacionField,String estadoProdField,
                                       int idStockField,String loteField,Double cantidadAnteriorField,Double cantidadField,
                                       Double pesoAnteriorField,Double pesoField,String operadorField,String fecha_VenceField,int idInvReconteoField) {

        this.idinvencreconteoField = idinvencreconteoField;
        this.idinventarioencField = idinventarioencField;
        this.reconteoField = reconteoField;
        this.estadoField = estadoField;
        this.hora_iniField = hora_iniField;
        this.hora_finField = hora_finField;
        this.user_agrField = user_agrField;
        this.fec_agrField = fec_agrField;
        this.user_modField = user_modField;
        this.fec_modField = fec_modField;
        this.ubicacionField = ubicacionField;
        this.codigoField = codigoField;
        this.productoField = productoField;
        this.presentacionField = presentacionField;
        this.estadoProdField = estadoProdField;
        this.idStockField = idStockField;
        this.loteField = loteField;
        this.cantidadAnteriorField = cantidadAnteriorField;
        this.cantidadField = cantidadField;
        this.pesoAnteriorField = pesoAnteriorField;
        this.pesoField = pesoField;
        this.operadorField = operadorField;
        this.fecha_VenceField = fecha_VenceField;
        this.idInvReconteoField = idInvReconteoField;

    }

    public int getidinvencreconteoField() {
        return idinvencreconteoField;
    }
    public void setidinvencreconteoField(int value) {
        idinvencreconteoField=value;
    }

    public int getidinventarioencField() {
        return idinventarioencField;
    }
    public void setidinventarioencField(int value) {
        idinventarioencField=value;
    }

    public int getreconteoField() {
        return reconteoField;
    }
    public void setreconteoField(int value) {
        reconteoField=value;
    }

    public String getestadoField() {
        return estadoField;
    }
    public void setestadoField(String value) {
        estadoField=value;
    }

    public String gethora_iniField() {
        return hora_iniField;
    }
    public void sethora_iniField(String value) {
        hora_iniField=value;
    }

    public String gethora_finField() {
        return hora_finField;
    }
    public void sethora_finField(String value) {
        hora_finField=value;
    }

    public String getuser_agrField() {
        return user_agrField;
    }
    public void setuser_agrField(String value) {
        user_agrField=value;
    }

    public String getfec_agrField() {
        return fec_agrField;
    }
    public void setfec_agrField(String value) {
        fec_agrField=value;
    }

    public String getuser_modField() {
        return user_modField;
    }
    public void setuser_modField(String value) {
        user_modField=value;
    }

    public String getfec_modField() {
        return fec_modField;
    }
    public void setfec_modField(String value) {
        fec_modField=value;
    }

    public String getubicacionField() {
        return ubicacionField;
    }
    public void setubicacionField(String value) {
        ubicacionField=value;
    }

    public String getcodigoField() {
        return codigoField;
    }
    public void setcodigoField(String value) {
        codigoField=value;
    }

    public String getproductoField() {
        return productoField;
    }
    public void setproductoField(String value) {
        productoField=value;
    }

    public String getpresentacionField() {
        return presentacionField;
    }
    public void setpresentacionField(String value) {
        presentacionField=value;
    }

    public String getestadoProdField() {
        return estadoProdField;
    }
    public void setestadoProdField(String value) {
        estadoProdField=value;
    }

    public int getidStockField() {
        return idStockField;
    }
    public void setidStockField(int value) {
        idStockField=value;
    }

    public String getloteField() {
        return loteField;
    }
    public void setloteField(String value) {
        loteField=value;
    }

    public double getcantidadAnteriorField() {
        return cantidadAnteriorField;
    }
    public void setcantidadAnteriorField(double value) {
        cantidadAnteriorField=value;
    }

    public double getcantidadField() {
        return cantidadField;
    }
    public void setcantidadField(double value) {
        cantidadField=value;
    }

    public double getpesoAnteriorField() {
        return pesoAnteriorField;
    }
    public void setpesoAnteriorField(double value) {
        pesoAnteriorField=value;
    }

    public double getpesoField() {
        return pesoField;
    }
    public void setpesoField(double value) {
        pesoField=value;
    }

    public String getoperadorField() {
        return operadorField;
    }
    public void setoperadorField(String value) {
        operadorField=value;
    }

    public String getfecha_VenceField() {
        return fecha_VenceField;
    }
    public void setfecha_VenceField(String value) {
        fecha_VenceField=value;
    }

    public int getidInvReconteoField() {
        return idInvReconteoField;
    }
    public void setidInvReconteoField(int value) {
        idInvReconteoField=value;
    }
}
