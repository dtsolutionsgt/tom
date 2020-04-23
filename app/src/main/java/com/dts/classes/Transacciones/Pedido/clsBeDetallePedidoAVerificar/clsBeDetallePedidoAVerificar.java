package com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar;

import org.simpleframework.xml.Element;

public class clsBeDetallePedidoAVerificar {

    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public int IdPedidoDet=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String LicPlate="";
    @Element(required=false) public String Fecha_Vence="";
    @Element(required=false) public String Nom_Presentacion="";
    @Element(required=false) public String Nom_Unid_Med="";
    @Element(required=false) public String Nombre_Producto="";
    @Element(required=false) public String Nom_Estado="";
    @Element(required=false) public double Cantidad_Solicitada=0;
    @Element(required=false) public double Cantidad_Recibida=0;
    @Element(required=false) public double Cantidad_Verificada=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUnidadMedidaBasica=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public int NDias=0;


    public clsBeDetallePedidoAVerificar() {
    }

    public clsBeDetallePedidoAVerificar(int IdPedidoEnc,int IdPedidoDet,int IdProductoBodega,String Lote,
                                        String LicPlate,String Fecha_Vence,String Nom_Presentacion,String Nom_Unid_Med,
                                        String Nombre_Producto,String Nom_Estado,double Cantidad_Solicitada,double Cantidad_Recibida,
                                        double Cantidad_Verificada,int IdPresentacion,int IdUnidadMedidaBasica,String Codigo,
                                        int NDias) {

        this.IdPedidoEnc=IdPedidoEnc;
        this.IdPedidoDet=IdPedidoDet;
        this.IdProductoBodega=IdProductoBodega;
        this.Lote=Lote;
        this.LicPlate=LicPlate;
        this.Fecha_Vence=Fecha_Vence;
        this.Nom_Presentacion=Nom_Presentacion;
        this.Nom_Unid_Med=Nom_Unid_Med;
        this.Nombre_Producto=Nombre_Producto;
        this.Nom_Estado=Nom_Estado;
        this.Cantidad_Solicitada=Cantidad_Solicitada;
        this.Cantidad_Recibida=Cantidad_Recibida;
        this.Cantidad_Verificada=Cantidad_Verificada;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedidaBasica=IdUnidadMedidaBasica;
        this.Codigo=Codigo;
        this.NDias=NDias;

    }


    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public int getIdPedidoDet() {
        return IdPedidoDet;
    }
    public void setIdPedidoDet(int value) {
        IdPedidoDet=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getLicPlate() {
        return LicPlate;
    }
    public void setLicPlate(String value) {
        LicPlate=value;
    }
    public String getFecha_Vence() {
        return Fecha_Vence;
    }
    public void setFecha_Vence(String value) {
        Fecha_Vence=value;
    }
    public String getNom_Presentacion() {
        return Nom_Presentacion;
    }
    public void setNom_Presentacion(String value) {
        Nom_Presentacion=value;
    }
    public String getNom_Unid_Med() {
        return Nom_Unid_Med;
    }
    public void setNom_Unid_Med(String value) {
        Nom_Unid_Med=value;
    }
    public String getNombre_Producto() {
        return Nombre_Producto;
    }
    public void setNombre_Producto(String value) {
        Nombre_Producto=value;
    }
    public String getNom_Estado() {
        return Nom_Estado;
    }
    public void setNom_Estado(String value) {
        Nom_Estado=value;
    }
    public double getCantidad_Solicitada() {
        return Cantidad_Solicitada;
    }
    public void setCantidad_Solicitada(double value) {
        Cantidad_Solicitada=value;
    }
    public double getCantidad_Recibida() {
        return Cantidad_Recibida;
    }
    public void setCantidad_Recibida(double value) {
        Cantidad_Recibida=value;
    }
    public double getCantidad_Verificada() {
        return Cantidad_Verificada;
    }
    public void setCantidad_Verificada(double value) {
        Cantidad_Verificada=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdUnidadMedidaBasica() {
        return IdUnidadMedidaBasica;
    }
    public void setIdUnidadMedidaBasica(int value) {
        IdUnidadMedidaBasica=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public int getNDias() {
        return NDias;
    }
    public void setNDias(int value) {
        NDias=value;
    }

}


