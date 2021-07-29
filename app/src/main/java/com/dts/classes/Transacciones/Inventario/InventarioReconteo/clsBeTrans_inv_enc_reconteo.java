package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.Element;

public class clsBeTrans_inv_enc_reconteo {

    @Element(required=false) public int Idinvencreconteo=0;
    @Element(required=false) public int Idinventarioenc=0;
    @Element(required=false) public int Reconteo=0;
    @Element(required=false) public String Estado="";
    @Element(required=false) public String Hora_ini ="00:00:01";
    @Element(required=false) public String Hora_fin="00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public String Ubicacion="";
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Producto="";
    @Element(required=false) public String Presentacion="";
    @Element(required=false) public String EstadoProd="";
    @Element(required=false) public int IdStock=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public Double cantidadAnterior=0.0;
    @Element(required=false) public Double Cantidad=0.0;
    @Element(required=false) public Double pesoAnterior=0.0;
    @Element(required=false) public Double Peso=0.0;
    @Element(required=false) public String Operador="";
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:01";
    @Element(required=false) public int IdInvReconteo=0;


    public clsBeTrans_inv_enc_reconteo() {
    }

    public clsBeTrans_inv_enc_reconteo(int Idinvencreconteo, int Idinventarioenc, int Reconteo,
                                       String Estado,String Hora_ini, String Hora_fin,String User_agr,
                                       String Fec_agr,String User_mod,String Fec_mod,String Ubicacion,
                                       String Codigo,String Producto,String Presentacion,String EstadoProd,
                                       int IdStock,String Lote,Double cantidadAnterior,Double Cantidad,
                                       Double pesoAnterior,Double Peso,String Operador,String Fecha_Vence,int IdInvReconteo
                                       ) {

        this.Idinvencreconteo = Idinvencreconteo;
        this.Idinventarioenc = Idinventarioenc;
        this.Reconteo = Reconteo;
        this.Estado = Estado;
        this.Hora_ini = Hora_ini;
        this.Hora_fin = Hora_fin;
        this.User_agr = User_agr;
        this.Fec_agr = Fec_agr;
        this.User_mod = User_mod;
        this.Fec_mod = Fec_mod;
        this.Ubicacion = Ubicacion;
        this.Codigo = Codigo;
        this.Producto = Producto;
        this.Presentacion = Presentacion;
        this.EstadoProd = EstadoProd;
        this.IdStock = IdStock;
        this.Lote = Lote;
        this.cantidadAnterior = cantidadAnterior;
        this.Cantidad = Cantidad;
        this.pesoAnterior = pesoAnterior;
        this.Peso = Peso;
        this.Operador = Operador;
        this.Fecha_Vence = Fecha_Vence;
        this.IdInvReconteo = IdInvReconteo;

       // this.Idinvencreconteo = Idinvencreconteo;

    }

    public int getidinvencreconteoField() {
        return Idinvencreconteo;
    }
    public void setidinvencreconteoField(int value) {
        Idinvencreconteo=value;
    }

    public int getIdinventarioenc() {
        return Idinventarioenc;
    }
    public void setIdinventarioenc(int value) {
        Idinventarioenc=value;
    }

    public int getreconteoField() {
        return Reconteo;
    }
    public void setreconteoField(int value) {
        Reconteo=value;
    }

    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }

    public String getHora_ini() {
        return Hora_ini;
    }
    public void setHora_ini(String value) {
        Hora_ini=value;
    }

    public String getHora_fin() {
        return Hora_fin;
    }
    public void setHora_fin(String value) {
        Hora_fin=value;
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

    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }

    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }

    public String getProducto() {
        return Producto;
    }
    public void setProducto(String value) {
        Producto=value;
    }

    public String getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(String value) {
        Presentacion=value;
    }

    public String getestadoProdField() {
        return EstadoProd;
    }
    public void setestadoProdField(String value) {
        EstadoProd=value;
    }

    public int getIdStock() {
        return IdStock;
    }
    public void setIdStock(int value) {
        IdStock=value;
    }

    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }

    public double getcantidadAnterior() {
        return cantidadAnterior;
    }
    public void setcantidadAnterior(double value) {
        cantidadAnterior=value;
    }

    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }

    public double getpesoAnterior() {
        return pesoAnterior;
    }
    public void setpesoAnterior(double value) {
        pesoAnterior=value;
    }

    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }

    public String getOperador() {
        return Operador;
    }
    public void setOperador(String value) {
        Operador=value;
    }

    public String getFecha_Vence() {
        return Fecha_Vence;
    }
    public void setFecha_Vence(String value) {
        Fecha_Vence=value;
    }

    public int getIdInvReconteo() {
        return IdInvReconteo;
    }
    public void setIdInvReconteo(int value) {
        IdInvReconteo=value;
    }



    public int getIdinvencreconteo() {
        return Idinvencreconteo;
    }
    public void setIdinvencreconteo(int value) {
        Idinvencreconteo=value;
    }


}
