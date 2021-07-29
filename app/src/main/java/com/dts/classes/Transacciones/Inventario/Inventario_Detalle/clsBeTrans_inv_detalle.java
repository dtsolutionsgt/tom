package com.dts.classes.Transacciones.Inventario.Inventario_Detalle;


import org.simpleframework.xml.Element;

public class clsBeTrans_inv_detalle {

    @Element(required=false) public int Idinventariodet=0;
    @Element(required=false) public int Idinventarioenc=0;
    @Element(required=false) public int Idtramo=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public int Idoperador=0;
    @Element(required=false) public int Idproducto=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int Idunidadmedida=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Fecha_vence="1900-01-01T00:00:01";
    @Element(required=false) public String Serie="";
    @Element(required=false) public String Idproductoestado="";
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public String Fecha_captura="1900-01-01T00:00:01";
    @Element(required=false) public String Host="";
    @Element(required=false) public String Nom_producto="";
    @Element(required=false) public String Nom_operador="";
    @Element(required=false) public int Carga=0;
    @Element(required=false) public double Peso=0;


    public clsBeTrans_inv_detalle() {
    }

    public clsBeTrans_inv_detalle(int Idinventariodet,int Idinventarioenc,int Idtramo,int IdUbicacion,
                                  int Idoperador,int Idproducto,int IdPresentacion,int Idunidadmedida,
                                  String Lote,String Fecha_vence,String Serie,String Idproductoestado,
                                  double Cantidad,String Fecha_captura,String Host,String Nom_producto,
                                  String Nom_operador,int Carga,double Peso) {

        this.Idinventariodet=Idinventariodet;
        this.Idinventarioenc=Idinventarioenc;
        this.Idtramo=Idtramo;
        this.IdUbicacion=IdUbicacion;
        this.Idoperador=Idoperador;
        this.Idproducto=Idproducto;
        this.IdPresentacion=IdPresentacion;
        this.Idunidadmedida=Idunidadmedida;
        this.Lote=Lote;
        this.Fecha_vence=Fecha_vence;
        this.Serie=Serie;
        this.Idproductoestado=Idproductoestado;
        this.Cantidad=Cantidad;
        this.Fecha_captura=Fecha_captura;
        this.Host=Host;
        this.Nom_producto=Nom_producto;
        this.Nom_operador=Nom_operador;
        this.Carga=Carga;
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
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public int getIdoperador() {
        return Idoperador;
    }
    public void setIdoperador(int value) {
        Idoperador=value;
    }
    public int getIdproducto() {
        return Idproducto;
    }
    public void setIdproducto(int value) {
        Idproducto=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
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
    public String getSerie() {
        return Serie;
    }
    public void setSerie(String value) {
        Serie=value;
    }
    public String getIdproductoestado() {
        return Idproductoestado;
    }
    public void setIdproductoestado(String value) {
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
    public int getCarga() {
        return Carga;
    }
    public void setCarga(int value) {
        Carga=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }

}

