package com.dts.classes.Transacciones.Inventario.Inventario_Resumen;


import org.simpleframework.xml.Element;

public class clsBeTrans_inv_resumen_grid {

    @Element(required=false) public int Idinventariores=0;
    @Element(required=false) public int Idinventarioenct=0;
    @Element(required=false) public int Idtramo=0;
    @Element(required=false) public int Idproducto=0;
    @Element(required=false) public String productoestado="";
    @Element(required=false) public String presentacion="";
    @Element(required=false) public String UnidadMedida="";
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public String Nom_Ubicacion="";



    public clsBeTrans_inv_resumen_grid() {
    }

    public clsBeTrans_inv_resumen_grid(int Idinventariores,int Idinventarioenct,int Idtramo,int Idproducto,
                                       String productoestado,String presentacion,String UnidadMedida,double Cantidad,
                                       int IdUbicacion, String Nom_Ubicacion, int IdBodega
    ) {

        this.Idinventariores=Idinventariores;
        this.Idinventarioenct=Idinventarioenct;
        this.Idtramo=Idtramo;
        this.Idproducto=Idproducto;
        this.productoestado=productoestado;
        this.presentacion=presentacion;
        this.UnidadMedida=UnidadMedida;
        this.Cantidad=Cantidad;
        this.IdUbicacion = IdUbicacion;
        this.Nom_Ubicacion = Nom_Ubicacion;
        this.IdBodega = IdBodega;

    }


    public int getIdinventariores() {
        return Idinventariores;
    }
    public void setIdinventariores(int value) {
        Idinventariores=value;
    }
    public int getIdinventarioenct() {
        return Idinventarioenct;
    }
    public void setIdinventarioenct(int value) {
        Idinventarioenct=value;
    }
    public int getIdtramo() {
        return Idtramo;
    }
    public void setIdtramo(int value) {
        Idtramo=value;
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

}

