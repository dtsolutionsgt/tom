package com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det;

import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;

import org.simpleframework.xml.Element;

public class clsBeTrans_ubic_hh_det {

    @Element(required=false) public int IdTareaUbicacionEnc=0;
    @Element(required=false) public int IdTareaUbicacionDet=0;
    @Element(required=false) public int IdStock=0;
    @Element(required=false) public int IdUbicacionOrigen=0;
    @Element(required=false) public int IdUbicacionDestino=0;
    @Element(required=false) public int IdEstadoOrigen=0;
    @Element(required=false) public int IdEstadoDestino=0;
    @Element(required=false) public int IdOperadorBodega=0;
    @Element(required=false) public String HoraInicio="";
    @Element(required=false) public String HoraFin="";
    @Element(required=false) public boolean Realizado;
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public double Recibido=0;
    @Element(required=false) public String Estado="";
    @Element(required=false) public String Atributo_variante_1="";
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdTramo=0;
    @Element(required=false) public String Tramo="";
    @Element(required=false) public int Indice_x=0;
    @Element(required=false) public int Nivel=0;
    @Element(required=false) public clsBeProducto_Presentacion ProductoPresentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeUnidad_medida UnidadMedida=new clsBeUnidad_medida();
    @Element(required=false) public clsBeProducto_estado ProductoEstado=new clsBeProducto_estado();
    @Element(required=false) public clsBeBodega_ubicacion UbicacionOrigen=new clsBeBodega_ubicacion();
    @Element(required=false) public clsBeBodega_ubicacion UbicacionDestino=new clsBeBodega_ubicacion();
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();
    @Element(required=false) public clsBeStock Stock=new clsBeStock();
    @Element(required=false) public clsBeOperador Operador=new clsBeOperador();


    public clsBeTrans_ubic_hh_det() {
    }

    public clsBeTrans_ubic_hh_det(int IdTareaUbicacionEnc,int IdTareaUbicacionDet,int IdStock,int IdUbicacionOrigen,
                                  int IdUbicacionDestino,int IdEstadoOrigen,int IdEstadoDestino,int IdOperadorBodega,
                                  String HoraInicio,String HoraFin,boolean Realizado,double Cantidad,
                                  boolean Activo,double Recibido,String Estado,String Atributo_variante_1,
                                  int IdBodega,int IdTramo,String Tramo,int Indice_x,
                                  int Nivel,clsBeProducto_Presentacion ProductoPresentacion,clsBeUnidad_medida UnidadMedida,clsBeProducto_estado ProductoEstado,
                                  clsBeBodega_ubicacion UbicacionOrigen,clsBeBodega_ubicacion UbicacionDestino,clsBeProducto Producto,clsBeStock Stock,
                                  clsBeOperador Operador) {

        this.IdTareaUbicacionEnc=IdTareaUbicacionEnc;
        this.IdTareaUbicacionDet=IdTareaUbicacionDet;
        this.IdStock=IdStock;
        this.IdUbicacionOrigen=IdUbicacionOrigen;
        this.IdUbicacionDestino=IdUbicacionDestino;
        this.IdEstadoOrigen=IdEstadoOrigen;
        this.IdEstadoDestino=IdEstadoDestino;
        this.IdOperadorBodega=IdOperadorBodega;
        this.HoraInicio=HoraInicio;
        this.HoraFin=HoraFin;
        this.Realizado=Realizado;
        this.Cantidad=Cantidad;
        this.Activo=Activo;
        this.Recibido=Recibido;
        this.Estado=Estado;
        this.Atributo_variante_1=Atributo_variante_1;
        this.IdBodega=IdBodega;
        this.IdTramo=IdTramo;
        this.Tramo=Tramo;
        this.Indice_x=Indice_x;
        this.Nivel=Nivel;
        this.ProductoPresentacion=ProductoPresentacion;
        this.UnidadMedida=UnidadMedida;
        this.ProductoEstado=ProductoEstado;
        this.UbicacionOrigen=UbicacionOrigen;
        this.UbicacionDestino=UbicacionDestino;
        this.Producto=Producto;
        this.Stock=Stock;
        this.Operador=Operador;

    }


    public int getIdTareaUbicacionEnc() {
        return IdTareaUbicacionEnc;
    }
    public void setIdTareaUbicacionEnc(int value) {
        IdTareaUbicacionEnc=value;
    }
    public int getIdTareaUbicacionDet() {
        return IdTareaUbicacionDet;
    }
    public void setIdTareaUbicacionDet(int value) {
        IdTareaUbicacionDet=value;
    }
    public int getIdStock() {
        return IdStock;
    }
    public void setIdStock(int value) {
        IdStock=value;
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
    public int getIdOperadorBodega() {
        return IdOperadorBodega;
    }
    public void setIdOperadorBodega(int value) {
        IdOperadorBodega=value;
    }
    public String getHoraInicio() {
        return HoraInicio;
    }
    public void setHoraInicio(String value) {
        HoraInicio=value;
    }
    public String getHoraFin() {
        return HoraFin;
    }
    public void setHoraFin(String value) {
        HoraFin=value;
    }
    public boolean getRealizado() {
        return Realizado;
    }
    public void setRealizado(boolean value) {
        Realizado=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public double getRecibido() {
        return Recibido;
    }
    public void setRecibido(double value) {
        Recibido=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public String getAtributo_variante_1() {
        return Atributo_variante_1;
    }
    public void setAtributo_variante_1(String value) {
        Atributo_variante_1=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdTramo() {
        return IdTramo;
    }
    public void setIdTramo(int value) {
        IdTramo=value;
    }
    public String getTramo() {
        return Tramo;
    }
    public void setTramo(String value) {
        Tramo=value;
    }
    public int getIndice_x() {
        return Indice_x;
    }
    public void setIndice_x(int value) {
        Indice_x=value;
    }
    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int value) {
        Nivel=value;
    }
    public clsBeProducto_Presentacion getProductoPresentacion() {
        return ProductoPresentacion;
    }
    public void setProductoPresentacion(clsBeProducto_Presentacion value) {
        ProductoPresentacion=value;
    }
    public clsBeUnidad_medida getUnidadMedida() {
        return UnidadMedida;
    }
    public void setUnidadMedida(clsBeUnidad_medida value) {
        UnidadMedida=value;
    }
    public clsBeProducto_estado getProductoEstado() {
        return ProductoEstado;
    }
    public void setProductoEstado(clsBeProducto_estado value) {
        ProductoEstado=value;
    }
    public clsBeBodega_ubicacion getUbicacionOrigen() {
        return UbicacionOrigen;
    }
    public void setUbicacionOrigen(clsBeBodega_ubicacion value) {
        UbicacionOrigen=value;
    }
    public clsBeBodega_ubicacion getUbicacionDestino() {
        return UbicacionDestino;
    }
    public void setUbicacionDestino(clsBeBodega_ubicacion value) {
        UbicacionDestino=value;
    }
    public clsBeProducto getProducto() {
        return Producto;
    }
    public void setProducto(clsBeProducto value) {
        Producto=value;
    }
    public clsBeStock getStock() {
        return Stock;
    }
    public void setStock(clsBeStock value) {
        Stock=value;
    }
    public clsBeOperador getOperador() {
        return Operador;
    }
    public void setOperador(clsBeOperador value) {
        Operador=value;
    }

}