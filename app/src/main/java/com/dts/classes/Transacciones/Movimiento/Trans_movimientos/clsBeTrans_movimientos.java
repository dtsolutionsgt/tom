package com.dts.classes.Transacciones.Movimiento.Trans_movimientos;

import org.simpleframework.xml.Element;

public class clsBeTrans_movimientos {

    @Element(required=false) public int IdMovimiento;
    @Element(required=false) public int IdEmpresa;
    @Element(required=false) public int IdBodegaOrigen;
    @Element(required=false) public int IdTransaccion;
    @Element(required=false) public int IdPropietarioBodega;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int IdUbicacionOrigen;
    @Element(required=false) public int IdUbicacionDestino;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdEstadoOrigen;
    @Element(required=false) public int IdEstadoDestino;
    @Element(required=false) public int IdUnidadMedida;
    @Element(required=false) public int IdTipoTarea;
    @Element(required=false) public int IdBodegaDestino;
    @Element(required=false) public int IdRecepcion;
    @Element(required=false) public double Cantidad;
    @Element(required=false) public String Serie;
    @Element(required=false) public double Peso;
    @Element(required=false) public String Lote;
    @Element(required=false) public String Fecha_vence;
    @Element(required=false) public String Fecha;
    @Element(required=false) public String Barra_pallet;
    @Element(required=false) public String Hora_ini;
    @Element(required=false) public String Hora_fin;
    @Element(required=false) public String Fecha_agr;
    @Element(required=false) public String Usuario_agr;
    @Element(required=false) public double Cantidad_hist;
    @Element(required=false) public double Peso_hist;
    @Element(required=false) public boolean IsNew;


    public clsBeTrans_movimientos() {
    }

    public clsBeTrans_movimientos(int IdMovimiento,int IdEmpresa,int IdBodegaOrigen,int IdTransaccion,
                                  int IdPropietarioBodega,int IdProductoBodega,int IdUbicacionOrigen,int IdUbicacionDestino,
                                  int IdPresentacion,int IdEstadoOrigen,int IdEstadoDestino,int IdUnidadMedida,
                                  int IdTipoTarea,int IdBodegaDestino,int IdRecepcion,double Cantidad,
                                  String Serie,double Peso,String Lote,String Fecha_vence,
                                  String Fecha,String Barra_pallet,String Hora_ini,String Hora_fin,
                                  String Fecha_agr,String Usuario_agr,double Cantidad_hist,double Peso_hist,
                                  boolean IsNew) {

        this.IdMovimiento=IdMovimiento;
        this.IdEmpresa=IdEmpresa;
        this.IdBodegaOrigen=IdBodegaOrigen;
        this.IdTransaccion=IdTransaccion;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProductoBodega=IdProductoBodega;
        this.IdUbicacionOrigen=IdUbicacionOrigen;
        this.IdUbicacionDestino=IdUbicacionDestino;
        this.IdPresentacion=IdPresentacion;
        this.IdEstadoOrigen=IdEstadoOrigen;
        this.IdEstadoDestino=IdEstadoDestino;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdTipoTarea=IdTipoTarea;
        this.IdBodegaDestino=IdBodegaDestino;
        this.IdRecepcion=IdRecepcion;
        this.Cantidad=Cantidad;
        this.Serie=Serie;
        this.Peso=Peso;
        this.Lote=Lote;
        this.Fecha_vence=Fecha_vence;
        this.Fecha=Fecha;
        this.Barra_pallet=Barra_pallet;
        this.Hora_ini=Hora_ini;
        this.Hora_fin=Hora_fin;
        this.Fecha_agr=Fecha_agr;
        this.Usuario_agr=Usuario_agr;
        this.Cantidad_hist=Cantidad_hist;
        this.Peso_hist=Peso_hist;
        this.IsNew=IsNew;

    }


    public int getIdMovimiento() {
        return IdMovimiento;
    }
    public void setIdMovimiento(int value) {
        IdMovimiento=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public int getIdBodegaOrigen() {
        return IdBodegaOrigen;
    }
    public void setIdBodegaOrigen(int value) {
        IdBodegaOrigen=value;
    }
    public int getIdTransaccion() {
        return IdTransaccion;
    }
    public void setIdTransaccion(int value) {
        IdTransaccion=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getIdUbicacionOrigen() {
        return IdUbicacionOrigen;
    }
    public void setIdUbicacionOrigen(int value) {
        IdUbicacionOrigen=value;
    }
    public int getIdUbicacionDestino() {
        return IdUbicacionDestino;
    }
    public void setIdUbicacionDestino(int value) {
        IdUbicacionDestino=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdEstadoOrigen() {
        return IdEstadoOrigen;
    }
    public void setIdEstadoOrigen(int value) {
        IdEstadoOrigen=value;
    }
    public int getIdEstadoDestino() {
        return IdEstadoDestino;
    }
    public void setIdEstadoDestino(int value) {
        IdEstadoDestino=value;
    }
    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
    }
    public int getIdTipoTarea() {
        return IdTipoTarea;
    }
    public void setIdTipoTarea(int value) {
        IdTipoTarea=value;
    }
    public int getIdBodegaDestino() {
        return IdBodegaDestino;
    }
    public void setIdBodegaDestino(int value) {
        IdBodegaDestino=value;
    }
    public int getIdRecepcion() {
        return IdRecepcion;
    }
    public void setIdRecepcion(int value) {
        IdRecepcion=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public String getSerie() {
        return Serie;
    }
    public void setSerie(String value) {
        Serie=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
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
    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String value) {
        Fecha=value;
    }
    public String getBarra_pallet() {
        return Barra_pallet;
    }
    public void setBarra_pallet(String value) {
        Barra_pallet=value;
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
    public String getFecha_agr() {
        return Fecha_agr;
    }
    public void setFecha_agr(String value) {
        Fecha_agr=value;
    }
    public String getUsuario_agr() {
        return Usuario_agr;
    }
    public void setUsuario_agr(String value) {
        Usuario_agr=value;
    }
    public double getCantidad_hist() {
        return Cantidad_hist;
    }
    public void setCantidad_hist(double value) {
        Cantidad_hist=value;
    }
    public double getPeso_hist() {
        return Peso_hist;
    }
    public void setPeso_hist(double value) {
        Peso_hist=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

