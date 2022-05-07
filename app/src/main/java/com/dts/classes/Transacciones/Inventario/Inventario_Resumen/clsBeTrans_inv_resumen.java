package com.dts.classes.Transacciones.Inventario.Inventario_Resumen;


import org.simpleframework.xml.Element;

public class clsBeTrans_inv_resumen {

    @Element(required=false) public int Idinventariores=0;
    @Element(required=false) public int Idinventarioenct=0;
    @Element(required=false) public int Idtramo=0;
    @Element(required=false) public int Idproducto=0;
    @Element(required=false) public int Idoperador=0;
    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public int Idpresentacion=0;
    @Element(required=false) public int Idproductoestado=0;
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public String Fecha_captura="1900-01-01T00:00:01";
    @Element(required=false) public String Host="";
    @Element(required=false) public String Nom_producto="";
    @Element(required=false) public String Nom_operador="";
    @Element(required=false) public int IdUbicacion=0;


    public clsBeTrans_inv_resumen() {
    }

    public clsBeTrans_inv_resumen(int Idinventariores,int Idinventarioenct,int Idtramo,int Idproducto,
                                  int Idoperador,int IdUnidadMedida,int Idpresentacion,int Idproductoestado,
                                  double Cantidad,String Fecha_captura,String Host,String Nom_producto,
                                  String Nom_operador, int IdUbicacion) {

        this.Idinventariores=Idinventariores;
        this.Idinventarioenct=Idinventarioenct;
        this.Idtramo=Idtramo;
        this.Idproducto=Idproducto;
        this.Idoperador=Idoperador;
        this.IdUnidadMedida=IdUnidadMedida;
        this.Idpresentacion=Idpresentacion;
        this.Idproductoestado=Idproductoestado;
        this.Cantidad=Cantidad;
        this.Fecha_captura=Fecha_captura;
        this.Host=Host;
        this.Nom_producto=Nom_producto;
        this.Nom_operador=Nom_operador;
        this.IdUbicacion=IdUbicacion;

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
    public int getIdoperador() {
        return Idoperador;
    }
    public void setIdoperador(int value) {
        Idoperador=value;
    }
    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
    }
    public int getIdpresentacion() {
        return Idpresentacion;
    }
    public void setIdpresentacion(int value) {
        Idpresentacion=value;
    }
    public int getIdproductoestado() {
        return Idproductoestado;
    }
    public void setIdproductoestado(int value) {
        Idproductoestado=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public String getFecha_captura() {
        return Fecha_captura;
    }
    public void setFecha_captura(String value) {
        Fecha_captura=value;
    }
    public String getHost() {
        return Host;
    }
    public void setHost(String value) {
        Host=value;
    }
    public String getNom_producto() {
        return Nom_producto;
    }
    public void setNom_producto(String value) {
        Nom_producto=value;
    }
    public String getNom_operador() {
        return Nom_operador;
    }
    public void setNom_operador(String value) {
        Nom_operador=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }

}


