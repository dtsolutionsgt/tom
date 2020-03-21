package com.dts.classes.Transacciones.Stock.Stock_rec;


import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;

import org.simpleframework.xml.Element;

public class clsBeStock_rec {

    @Element(required=false) public int IdBodega;
    @Element(required=false) public int IdStockRec;
    @Element(required=false) public int IdPropietarioBodega;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int IdProductoEstado;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdUnidadMedida;
    @Element(required=false) public int IdUbicacion;
    @Element(required=false) public int IdUbicacion_anterior;
    @Element(required=false) public int IdRecepcionEnc;
    @Element(required=false) public int IdRecepcionDet;
    @Element(required=false) public int IdPedidoEnc;
    @Element(required=false) public int IdPickingEnc;
    @Element(required=false) public int IdDespachoEnc;
    @Element(required=false) public String Lote;
    @Element(required=false) public String Lic_plate;
    @Element(required=false) public String Serial;
    @Element(required=false) public double Cantidad;
    @Element(required=false) public String Fecha_Ingreso;
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:01";
    @Element(required=false) public double Uds_lic_plate;
    @Element(required=false) public int No_bulto;
    @Element(required=false) public String Fecha_Manufactura="1900-01-01T00:00:01";
    @Element(required=false) public int Anada;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public double Peso;
    @Element(required=false) public double Temperatura;
    @Element(required=false) public boolean Regularizado;
    @Element(required=false) public String Fecha_regularizacion;
    @Element(required=false) public int No_linea;
    @Element(required=false) public String Atributo_Variante_1;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public boolean ProductoValidado;
    @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeProducto_estado ProductoEstado=new clsBeProducto_estado();
    @Element(required=false) public double CantidadEnStock;
    @Element(required=false) public double PesoEnStock;
    @Element(required=false) public double Cantidad_Nav;


    public clsBeStock_rec() {
    }

    public clsBeStock_rec(int IdBodega,int IdStockRec,int IdPropietarioBodega,int IdProductoBodega,
                          int IdProductoEstado,int IdPresentacion,int IdUnidadMedida,int IdUbicacion,
                          int IdUbicacion_anterior,int IdRecepcionEnc,int IdRecepcionDet,int IdPedidoEnc,
                          int IdPickingEnc,int IdDespachoEnc,String Lote,String Lic_plate,
                          String Serial,double Cantidad,String Fecha_Ingreso,String Fecha_Vence,
                          double Uds_lic_plate,int No_bulto,String Fecha_Manufactura,int Anada,
                          String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                          boolean Activo,double Peso,double Temperatura,boolean Regularizado,
                          String Fecha_regularizacion,int No_linea,String Atributo_Variante_1,boolean IsNew,
                          boolean ProductoValidado,clsBeProducto_Presentacion Presentacion,clsBeProducto_estado ProductoEstado,double CantidadEnStock,
                          double PesoEnStock,double Cantidad_Nav) {

        this.IdBodega=IdBodega;
        this.IdStockRec=IdStockRec;
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
        this.Fecha_Vence=Fecha_Vence;
        this.Uds_lic_plate=Uds_lic_plate;
        this.No_bulto=No_bulto;
        this.Fecha_Manufactura=Fecha_Manufactura;
        this.Anada = Anada;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Peso=Peso;
        this.Temperatura=Temperatura;
        this.Regularizado=Regularizado;
        this.Fecha_regularizacion=Fecha_regularizacion;
        this.No_linea=No_linea;
        this.Atributo_Variante_1=Atributo_Variante_1;
        this.IsNew=IsNew;
        this.ProductoValidado=ProductoValidado;
        this.Presentacion=Presentacion;
        this.ProductoEstado=ProductoEstado;
        this.CantidadEnStock=CantidadEnStock;
        this.PesoEnStock=PesoEnStock;
        this.Cantidad_Nav=Cantidad_Nav;

    }


    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdStockRec() {
        return IdStockRec;
    }
    public void setIdStockRec(int value) {
        IdStockRec=value;
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
    public String getFecha_Vence() {
        return Fecha_Vence;
    }
    public void setFecha_Vence(String value) {
        Fecha_Vence=value;
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
        Anada =value;
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
    public boolean getRegularizado() {
        return Regularizado;
    }
    public void setRegularizado(boolean value) {
        Regularizado=value;
    }
    public String getFecha_regularizacion() {
        return Fecha_regularizacion;
    }
    public void setFecha_regularizacion(String value) {
        Fecha_regularizacion=value;
    }
    public int getNo_linea() {
        return No_linea;
    }
    public void setNo_linea(int value) {
        No_linea=value;
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
    public double getCantidadEnStock() {
        return CantidadEnStock;
    }
    public void setCantidadEnStock(double value) {
        CantidadEnStock=value;
    }
    public double getPesoEnStock() {
        return PesoEnStock;
    }
    public void setPesoEnStock(double value) {
        PesoEnStock=value;
    }
    public double getCantidad_Nav() {
        return Cantidad_Nav;
    }
    public void setCantidad_Nav(double value) {
        Cantidad_Nav=value;
    }

}

