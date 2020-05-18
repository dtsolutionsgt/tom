package com.dts.classes.Transacciones.Inventario.Inventario_Detalle;


import org.simpleframework.xml.Element;

public class clsBeTrans_inv_detalle_grid {

    @Element(required=false) public int Idinventariodet=0;
    @Element(required=false) public int Idinventarioenc=0;
    @Element(required=false) public int Idtramo=0;
    @Element(required=false) public int IdUbic=0;
    @Element(required=false) public String Ubic="";
    @Element(required=false) public int Idproducto=0;
    @Element(required=false) public String productoestado="";
    @Element(required=false) public String presentacion="";
    @Element(required=false) public String UnidadMedida="";
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public double Peso=0;


    public clsBeTrans_inv_detalle_grid() {
    }

    public clsBeTrans_inv_detalle_grid(int Idinventariodet,int Idinventarioenc,int Idtramo,int IdUbic,
                                       String Ubic,int Idproducto,String productoestado,String presentacion,
                                       String UnidadMedida,double Cantidad,double Peso) {

        this.Idinventariodet=Idinventariodet;
        this.Idinventarioenc=Idinventarioenc;
        this.Idtramo=Idtramo;
        this.IdUbic=IdUbic;
        this.Ubic=Ubic;
        this.Idproducto=Idproducto;
        this.productoestado=productoestado;
        this.presentacion=presentacion;
        this.UnidadMedida=UnidadMedida;
        this.Cantidad=Cantidad;
        this.Peso=Peso;

    }


    public int getIdinventariodet() {
        return Idinventariodet;
    }
    public void setIdinventariodet(int value) {
        Idinventariodet=value;
    }
    public int getIdinventarioenc() {
        return Idinventarioenc;
    }
    public void setIdinventarioenc(int value) {
        Idinventarioenc=value;
    }
    public int getIdtramo() {
        return Idtramo;
    }
    public void setIdtramo(int value) {
        Idtramo=value;
    }
    public int getIdUbic() {
        return IdUbic;
    }
    public void setIdUbic(int value) {
        IdUbic=value;
    }
    public String getUbic() {
        return Ubic;
    }
    public void setUbic(String value) {
        Ubic=value;
    }
    public int getIdproducto() {
        return Idproducto;
    }
    public void setIdproducto(int value) {
        Idproducto=value;
    }
    public String getproductoestado() {
        return productoestado;
    }
    public void setproductoestado(String value) {
        productoestado=value;
    }
    public String getpresentacion() {
        return presentacion;
    }
    public void setpresentacion(String value) {
        presentacion=value;
    }
    public String getUnidadMedida() {
        return UnidadMedida;
    }
    public void setUnidadMedida(String value) {
        UnidadMedida=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }

}

