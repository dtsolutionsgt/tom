package com.dts.classes.Transacciones.Stock.Stock;


import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Stock.Parametros.clsBeStock_parametroList;
import com.dts.classes.Transacciones.Stock.Revision.clsBeRevision;

import org.simpleframework.xml.Element;

public class clsBeStock {

    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdStock=0;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdProductoEstado=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public int IdUbicacion_anterior=0;
    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdRecepcionDet=0;
    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public int IdPickingEnc=0;
    @Element(required=false) public int IdDespachoEnc=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Lic_plate;
    @Element(required=false) public String Serial="";
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public String Fecha_Ingreso="1900-01-01T00:00:01";
    @Element(required=false) public String Fecha_vence="1900-01-01T00:00:01";
    @Element(required=false) public double Uds_lic_plate=0;
    @Element(required=false) public int No_bulto=0;
    @Element(required=false) public String Fecha_Manufactura="1900-01-01T00:00:01";
    @Element(required=false) public int Anada=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public double Peso=0;
    @Element(required=false) public double Temperatura=0;
    @Element(required=false) public String Atributo_Variante_1;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public boolean ProductoValidado=false;
    @Element(required=false) public String UbicacionAnterior="";
    @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeProducto_estado ProductoEstado=new clsBeProducto_estado();
    @Element(required=false) public clsBeStock_parametroList Parametros=new clsBeStock_parametroList();
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();
    @Element(required=false) public int IdStockOrigen=0;
    @Element(required=false) public clsBeRevision Revision=new clsBeRevision();

    public clsBeStock()
    {
        this.IdBodega=IdBodega;
        this.IdStock=IdStock;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProductoBodega=IdProductoBodega;
        this.IdProductoEstado=IdProductoEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdUbicacion=IdUbicacion;
        this.IdUbicacion_anterior=IdUbicacion_anterior;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdRecepcionDet=IdRecepcionDet;
        this.IdPedidoEnc=IdPedidoEnc;
        this.IdPickingEnc=IdPickingEnc;
        this.IdDespachoEnc=IdDespachoEnc;
        this.Lote=Lote;
        this.Lic_plate=Lic_plate;
        this.Serial=Serial;
        this.Cantidad=Cantidad;
        this.Fecha_Ingreso=Fecha_Ingreso;
        this.Fecha_vence=Fecha_vence;
        this.Uds_lic_plate=Uds_lic_plate;
        this.No_bulto=No_bulto;
        this.Fecha_Manufactura=Fecha_Manufactura;
        this.Anada=Anada;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Peso=Peso;
        this.Temperatura=Temperatura;
        this.Atributo_Variante_1=Atributo_Variante_1;
        this.IsNew=IsNew;
        this.ProductoValidado=ProductoValidado;
        this.UbicacionAnterior=UbicacionAnterior;
        this.Presentacion=Presentacion;
        this.ProductoEstado=ProductoEstado;
        this.Parametros=Parametros;
        this.Producto=Producto;
        this.IdStockOrigen=IdStockOrigen;
        this.Revision=Revision;
    }

    public clsBeStock(int IdBodega,int IdStock,int IdPropietarioBodega,int IdProductoBodega,
                      int IdProductoEstado,int IdPresentacion,int IdUnidadMedida,int IdUbicacion,
                      int IdUbicacion_anterior,int IdRecepcionEnc,int IdRecepcionDet,int IdPedidoEnc,
                      int IdPickingEnc,int IdDespachoEnc,String Lote,String Lic_plate,
                      String Serial,double Cantidad,String Fecha_Ingreso,String Fecha_vence,
                      double Uds_lic_plate,int No_bulto,String Fecha_Manufactura,int Anada,
                      String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                      boolean Activo,double Peso,double Temperatura,String Atributo_Variante_1,
                      boolean IsNew,boolean ProductoValidado,String UbicacionAnterior,clsBeProducto_Presentacion Presentacion,
                      clsBeProducto_estado ProductoEstado,clsBeStock_parametroList Parametros,clsBeProducto Producto,
                      int IdStockOrigen,clsBeRevision Revision
    ) {

        this.IdBodega=IdBodega;
        this.IdStock=IdStock;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProductoBodega=IdProductoBodega;
        this.IdProductoEstado=IdProductoEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdUbicacion=IdUbicacion;
        this.IdUbicacion_anterior=IdUbicacion_anterior;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdRecepcionDet=IdRecepcionDet;
        this.IdPedidoEnc=IdPedidoEnc;
        this.IdPickingEnc=IdPickingEnc;
        this.IdDespachoEnc=IdDespachoEnc;
        this.Lote=Lote;
        this.Lic_plate=Lic_plate;
        this.Serial=Serial;
        this.Cantidad=Cantidad;
        this.Fecha_Ingreso=Fecha_Ingreso;
        this.Fecha_vence=Fecha_vence;
        this.Uds_lic_plate=Uds_lic_plate;
        this.No_bulto=No_bulto;
        this.Fecha_Manufactura=Fecha_Manufactura;
        this.Anada=Anada;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Peso=Peso;
        this.Temperatura=Temperatura;
        this.Atributo_Variante_1=Atributo_Variante_1;
        this.IsNew=IsNew;
        this.ProductoValidado=ProductoValidado;
        this.UbicacionAnterior=UbicacionAnterior;
        this.Presentacion=Presentacion;
        this.ProductoEstado=ProductoEstado;
        this.Parametros=Parametros;
        this.Producto=Producto;
        this.IdStockOrigen=IdStockOrigen;
        this.Revision=Revision;
    }


    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdStock() {
        return IdStock;
    }
    public void setIdStock(int value) {
        IdStock=value;
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
    public int getIdProductoEstado() {
        return IdProductoEstado;
    }
    public void setIdProductoEstado(int value) {
        IdProductoEstado=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public int getIdUbicacion_anterior() {
        return IdUbicacion_anterior;
    }
    public void setIdUbicacion_anterior(int value) {
        IdUbicacion_anterior=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdRecepcionDet() {
        return IdRecepcionDet;
    }
    public void setIdRecepcionDet(int value) {
        IdRecepcionDet=value;
    }
    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public int getIdPickingEnc() {
        return IdPickingEnc;
    }
    public void setIdPickingEnc(int value) {
        IdPickingEnc=value;
    }
    public int getIdDespachoEnc() {
        return IdDespachoEnc;
    }
    public void setIdDespachoEnc(int value) {
        IdDespachoEnc=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getLic_plate() {
        return Lic_plate;
    }
    public void setLic_plate(String value) {
        Lic_plate=value;
    }
    public String getSerial() {
        return Serial;
    }
    public void setSerial(String value) {
        Serial=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public String getFecha_Ingreso() {
        return Fecha_Ingreso;
    }
    public void setFecha_Ingreso(String value) {
        Fecha_Ingreso=value;
    }
    public String getFecha_vence() {
        return Fecha_vence;
    }
    public void setFecha_vence(String value) {
        Fecha_vence=value;
    }
    public double getUds_lic_plate() {
        return Uds_lic_plate;
    }
    public void setUds_lic_plate(double value) {
        Uds_lic_plate=value;
    }
    public int getNo_bulto() {
        return No_bulto;
    }
    public void setNo_bulto(int value) {
        No_bulto=value;
    }
    public String getFecha_Manufactura() {
        return Fecha_Manufactura;
    }
    public void setFecha_Manufactura(String value) {
        Fecha_Manufactura=value;
    }
    public int getAnada() {
        return Anada;
    }
    public void setAnada(int value) {
        Anada=value;
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
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }
    public double getTemperatura() {
        return Temperatura;
    }
    public void setTemperatura(double value) {
        Temperatura=value;
    }
    public String getAtributo_Variante_1() {
        return Atributo_Variante_1;
    }
    public void setAtributo_Variante_1(String value) {
        Atributo_Variante_1=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public boolean getProductoValidado() {
        return ProductoValidado;
    }
    public void setProductoValidado(boolean value) {
        ProductoValidado=value;
    }
    public String getUbicacionAnterior() {
        return UbicacionAnterior;
    }
    public void setUbicacionAnterior(String value) {
        UbicacionAnterior=value;
    }
    public clsBeProducto_Presentacion getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(clsBeProducto_Presentacion value) {
        Presentacion=value;
    }
    public clsBeProducto_estado getProductoEstado() {
        return ProductoEstado;
    }
    public void setProductoEstado(clsBeProducto_estado value) {
        ProductoEstado=value;
    }
    public clsBeStock_parametroList getParametros() {
        return Parametros;
    }
    public void setParametros(clsBeStock_parametroList value) {
        Parametros=value;
    }
    public clsBeProducto getProducto() {
        return Producto;
    }
    public void setProducto(clsBeProducto value) {
        Producto=value;
    }
    public int getIdStockOrigen() {
        return IdStockOrigen;
    }
    public void setIdStockOrigen(int value) {
        IdStockOrigen=value;
    }
    public clsBeRevision getRevision() {
        return Revision;
    }
    public void setRevision(clsBeRevision value) {
        Revision=value;
    }
}


