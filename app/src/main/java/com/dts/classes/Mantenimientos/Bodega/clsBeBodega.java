package com.dts.classes.Mantenimientos.Bodega;


import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresa;

import org.simpleframework.xml.Element;

public class clsBeBodega extends clsBeBodegaBase {

    @Element(required=false) public String Codigo_barra="";
    @Element(required=false) public String Nombre_comercial="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public String Telefono="";
    @Element(required=false) public String Email="";
    @Element(required=false) public String Encargado="";
    @Element(required=false) public String Ubic_recepcion="";
    @Element(required=false) public String Ubic_picking="";
    @Element(required=false) public String Ubic_despacho="";
    @Element(required=false) public String Ubic_merma="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String Coordenada_x="";
    @Element(required=false) public String Coordenada_y="";
    @Element(required=false) public double Largo=0;
    @Element(required=false) public double Ancho=0;
    @Element(required=false) public double Alto=0;
    @Element(required=false) public boolean Reservar_stocks_por_linea=false;
    @Element(required=false) public boolean Rechazar_pedido_por_stock=false;
    @Element(required=false) public String IdTipoTransaccion="";
    @Element(required=false) public double Zoom=0;
    @Element(required=false) public int IdMotivoUbicacionDanadoPicking=0;
    @Element(required=false) public boolean cambio_ubicacion_auto=false;
    @Element(required=false) public String codigo_bodega_erp="";
    @Element(required=false) public int ubic_producto_ne=0;
    @Element(required=false) public int IdProductoEstadoNE=0;
    @Element(required=false) public clsBeEmpresa Empresa=new clsBeEmpresa();
    @Element(required=false) public clsBeBodega_areaList Areas=new clsBeBodega_areaList();
    @Element(required=false) public clsBeBodega_sectorList Sectores=new clsBeBodega_sectorList();
    @Element(required=false) public clsBeBodega_tramoList Tramos=new clsBeBodega_tramoList();
    @Element(required=false) public clsBeBodega_ubicacionList Ubicaciones=new clsBeBodega_ubicacionList();
    @Element(required=false) public clsBeBodega_muellesList Muelles=new clsBeBodega_muellesList();

    @Element(required=false) public String Cuenta_Ingreso_Mercancias="0";
    @Element(required=false) public String Cuenta_Egreso_Mercancias="0";
    @Element(required=false) public boolean Notificacion_Voz=false;


    public clsBeBodega() {
    }

    public clsBeBodega(int IdBodega,int IdPais,int IdEmpresa,String Codigo,
                       String Codigo_barra,String Nombre,String Nombre_comercial,String Direccion,
                       String Telefono,String Email,String Encargado,String Ubic_recepcion,
                       String Ubic_picking,String Ubic_despacho,String Ubic_merma,String User_agr,
                       String Fec_agr,String User_mod,String Fec_mod,boolean Activo,
                       String Coordenada_x,String Coordenada_y,double Largo,double Ancho,
                       double Alto,boolean Reservar_stocks_por_linea,boolean Rechazar_pedido_por_stock,String IdTipoTransaccion,
                       double Zoom,int IdMotivoUbicacionDanadoPicking,boolean cambio_ubicacion_auto,String codigo_bodega_erp,
                       int ubic_producto_ne,int IdProductoEstadoNE,clsBeEmpresa Empresa,clsBeBodega_areaList Areas,
                       clsBeBodega_sectorList Sectores,clsBeBodega_tramoList Tramos,clsBeBodega_ubicacionList Ubicaciones,clsBeBodega_muellesList Muelles,
            String Cuenta_Ingreso_Mercancias,String Cuenta_Egreso_Mercancias, boolean Notificacion_Voz
    ) {

        this.IdBodega=IdBodega;
        this.IdPais=IdPais;
        this.IdEmpresa=IdEmpresa;
        this.Codigo=Codigo;
        this.Codigo_barra=Codigo_barra;
        this.Nombre=Nombre;
        this.Nombre_comercial=Nombre_comercial;
        this.Direccion=Direccion;
        this.Telefono=Telefono;
        this.Email=Email;
        this.Encargado=Encargado;
        this.Ubic_recepcion=Ubic_recepcion;
        this.Ubic_picking=Ubic_picking;
        this.Ubic_despacho=Ubic_despacho;
        this.Ubic_merma=Ubic_merma;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Coordenada_x=Coordenada_x;
        this.Coordenada_y=Coordenada_y;
        this.Largo=Largo;
        this.Ancho=Ancho;
        this.Alto=Alto;
        this.Reservar_stocks_por_linea=Reservar_stocks_por_linea;
        this.Rechazar_pedido_por_stock=Rechazar_pedido_por_stock;
        this.IdTipoTransaccion=IdTipoTransaccion;
        this.Zoom=Zoom;
        this.IdMotivoUbicacionDanadoPicking=IdMotivoUbicacionDanadoPicking;
        this.cambio_ubicacion_auto=cambio_ubicacion_auto;
        this.codigo_bodega_erp=codigo_bodega_erp;
        this.ubic_producto_ne=ubic_producto_ne;
        this.IdProductoEstadoNE=IdProductoEstadoNE;
        this.Empresa=Empresa;
        this.Areas=Areas;
        this.Sectores=Sectores;
        this.Tramos=Tramos;
        this.Ubicaciones=Ubicaciones;
        this.Muelles=Muelles;

        this.Cuenta_Ingreso_Mercancias= Cuenta_Ingreso_Mercancias;
        this.Cuenta_Egreso_Mercancias = Cuenta_Egreso_Mercancias;

        this.Notificacion_Voz = Notificacion_Voz;

    }

    public int getIdPais() {
        return IdPais;
    }
    public void setIdPais(int value) {
        IdPais=value;
    }

    public String getCodigo_barra() {
        return Codigo_barra;
    }
    public void setCodigo_barra(String value) {
        Codigo_barra=value;
    }
    public String getNombre_comercial() {
        return Nombre_comercial;
    }
    public void setNombre_comercial(String value) {
        Nombre_comercial=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String value) {
        Telefono=value;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String value) {
        Email=value;
    }
    public String getEncargado() {
        return Encargado;
    }
    public void setEncargado(String value) {
        Encargado=value;
    }
    public String getUbic_recepcion() {
        return Ubic_recepcion;
    }
    public void setUbic_recepcion(String value) {
        Ubic_recepcion=value;
    }
    public String getUbic_picking() {
        return Ubic_picking;
    }
    public void setUbic_picking(String value) {
        Ubic_picking=value;
    }
    public String getUbic_despacho() {
        return Ubic_despacho;
    }
    public void setUbic_despacho(String value) {
        Ubic_despacho=value;
    }
    public String getUbic_merma() {
        return Ubic_merma;
    }
    public void setUbic_merma(String value) {
        Ubic_merma=value;
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
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public String getCoordenada_x() {
        return Coordenada_x;
    }
    public void setCoordenada_x(String value) {
        Coordenada_x=value;
    }
    public String getCoordenada_y() {
        return Coordenada_y;
    }
    public void setCoordenada_y(String value) {
        Coordenada_y=value;
    }
    public double getLargo() {
        return Largo;
    }
    public void setLargo(double value) {
        Largo=value;
    }
    public double getAncho() {
        return Ancho;
    }
    public void setAncho(double value) {
        Ancho=value;
    }
    public double getAlto() {
        return Alto;
    }
    public void setAlto(double value) {
        Alto=value;
    }
    public boolean getReservar_stocks_por_linea() {
        return Reservar_stocks_por_linea;
    }
    public void setReservar_stocks_por_linea(boolean value) {
        Reservar_stocks_por_linea=value;
    }
    public boolean getRechazar_pedido_por_stock() {
        return Rechazar_pedido_por_stock;
    }
    public void setRechazar_pedido_por_stock(boolean value) {
        Rechazar_pedido_por_stock=value;
    }
    public String getIdTipoTransaccion() {
        return IdTipoTransaccion;
    }
    public void setIdTipoTransaccion(String value) {
        IdTipoTransaccion=value;
    }
    public double getZoom() {
        return Zoom;
    }
    public void setZoom(double value) {
        Zoom=value;
    }
    public int getIdMotivoUbicacionDanadoPicking() {
        return IdMotivoUbicacionDanadoPicking;
    }
    public void setIdMotivoUbicacionDanadoPicking(int value) {
        IdMotivoUbicacionDanadoPicking=value;
    }
    public boolean getcambio_ubicacion_auto() {
        return cambio_ubicacion_auto;
    }
    public void setcambio_ubicacion_auto(boolean value) {
        cambio_ubicacion_auto=value;
    }
    public String getcodigo_bodega_erp() {
        return codigo_bodega_erp;
    }
    public void setcodigo_bodega_erp(String value) {
        codigo_bodega_erp=value;
    }
    public int getubic_producto_ne() {
        return ubic_producto_ne;
    }
    public void setubic_producto_ne(int value) {
        ubic_producto_ne=value;
    }
    public int getIdProductoEstadoNE() {
        return IdProductoEstadoNE;
    }
    public void setIdProductoEstadoNE(int value) {
        IdProductoEstadoNE=value;
    }
    public clsBeEmpresa getEmpresa() {
        return Empresa;
    }
    public void setEmpresa(clsBeEmpresa value) {
        Empresa=value;
    }
    public clsBeBodega_areaList getAreas() {
        return Areas;
    }
    public void setAreas(clsBeBodega_areaList value) {
        Areas=value;
    }
    public clsBeBodega_sectorList getSectores() {
        return Sectores;
    }
    public void setSectores(clsBeBodega_sectorList value) {
        Sectores=value;
    }
    public clsBeBodega_tramoList getTramos() {
        return Tramos;
    }
    public void setTramos(clsBeBodega_tramoList value) {
        Tramos=value;
    }
    public clsBeBodega_ubicacionList getUbicaciones() {
        return Ubicaciones;
    }
    public void setUbicaciones(clsBeBodega_ubicacionList value) {
        Ubicaciones=value;
    }
    public clsBeBodega_muellesList getMuelles() {
        return Muelles;
    }
    public void setMuelles(clsBeBodega_muellesList value) {
        Muelles=value;
    }

    public String getCuenta_Ingreso_Mercancias() {
        return Cuenta_Ingreso_Mercancias;
    }
    public void setCuenta_Ingreso_Mercancias(String value) {
        Cuenta_Ingreso_Mercancias=value;
    }

    public String getCuenta_Egreso_Mercancias() {
        return Cuenta_Egreso_Mercancias;
    }
    public void setCuenta_Egreso_Mercancias(String value) {
        Cuenta_Egreso_Mercancias=value;
    }

    public boolean getNotificacion_Voz() {
        return Notificacion_Voz;
    }
    public void setNotificacion_Voz(boolean value) {
        Notificacion_Voz=value;
    }

}

