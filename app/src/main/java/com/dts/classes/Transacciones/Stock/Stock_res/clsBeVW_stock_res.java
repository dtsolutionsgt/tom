package com.dts.classes.Transacciones.Stock.Stock_res;


import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;

import org.simpleframework.xml.Element;

public class clsBeVW_stock_res {

  @Element(required=false) public int IdBodega;
  @Element(required=false) public int IdPropietario;
  @Element(required=false) public int IdPropietarioBodega;
  @Element(required=false) public int IdProducto;
  @Element(required=false) public int IdProductoBodega;
  @Element(required=false) public int IdStock;
  @Element(required=false) public int IdStockRes;
  @Element(required=false) public int IdUbicacion;
  @Element(required=false) public int IdUbicacion_Anterior;
  @Element(required=false) public int IdUnidadMedida;
  @Element(required=false) public int IdProductoEstado;
  @Element(required=false) public int IdPresentacion;
  @Element(required=false) public int IdRecepcionEnc;
  @Element(required=false) public String Propietario;
  @Element(required=false) public String UMBas;
  @Element(required=false) public String Nombre_Presentacion;
  @Element(required=false) public String Codigo_Producto;
  @Element(required=false) public String Nombre_Producto;
  @Element(required=false) public String Lote;
  @Element(required=false) public String Fecha_ingreso;
  @Element(required=false) public String Serial;
  @Element(required=false) public int Anada;
  @Element(required=false) public double CantidadUmBas;
  @Element(required=false) public double Factor;
  @Element(required=false) public double CantidadPresentacion;
  @Element(required=false) public String Fecha_Vence;
  @Element(required=false) public String NomEstado;
  @Element(required=false) public boolean EstadoUtilizable;
  @Element(required=false) public boolean Danado;
  @Element(required=false) public String Lic_plate_Anterior;
  @Element(required=false) public String Lic_plate;
  @Element(required=false) public double Peso;
  @Element(required=false) public int IdIndiceRotacion;
  @Element(required=false) public double AltoUbicacion;
  @Element(required=false) public double LargoUbicacion;
  @Element(required=false) public double AnchoUbicacion;
  @Element(required=false) public double CantidadReservadaUMBas;
  @Element(required=false) public int IdTramo;
  @Element(required=false) public double Ancho_ubicacion;
  @Element(required=false) public double Largo_ubicacion;
  @Element(required=false) public double Alto_ubicacion;
  @Element(required=false) public String IndiceRotacion;
  @Element(required=false) public double Existencia_min_umbas;
  @Element(required=false) public double Existencia_max_umbas;
  @Element(required=false) public String Codigo_Barra;
  @Element(required=false) public double Costo;
  @Element(required=false) public double Tolerancia;
  @Element(required=false) public double Existencia_min_pres;
  @Element(required=false) public double Existencia_max_pres;
  @Element(required=false) public String Atributo_variante_1;
  @Element(required=false) public int Ubicacion_Nivel;
  @Element(required=false) public int Ubicacion_Indice_x;
  @Element(required=false) public String Ubicacion_Nombre;
  @Element(required=false) public String Ubicacion_Tramo;
  @Element(required=false) public int IdPedido;
  @Element(required=false) public int IdPedidoDet;
  @Element(required=false) public int IdPicking;
  @Element(required=false) public double TotalLinea;
  @Element(required=false) public clsBeProducto_Presentacion BePresentacionProductoEnStock=new clsBeProducto_Presentacion();
  @Element(required=false) public double AltoUMBas;
  @Element(required=false) public double LargoUmBas;
  @Element(required=false) public double AnchoUmBas;
  @Element(required=false) public boolean acepto;
  @Element(required=false) public double peso_pickeado;
  @Element(required=false) public double peso_verificado;
  @Element(required=false) public double Cantidad_Pickeada;
  @Element(required=false) public double Cantidad_Verificada;
  @Element(required=false) public double Cantidad_Despachada;
  @Element(required=false) public boolean encontrado;
  @Element(required=false) public clsBeBodega_ubicacion UbicacionActual=new clsBeBodega_ubicacion();
  @Element(required=false) public double Cantidad_Res;
  @Element(required=false) public String ValorTexto;
  @Element(required=false) public double ValorNumerico;
  @Element(required=false) public String ValorFecha;
  @Element(required=false) public Byte ValorLogico;
  @Element(required=false) public String No_Serie;
  @Element(required=false) public String No_Serie_Inicial;
  @Element(required=false) public String No_Serie_Final;
  @Element(required=false) public double CantidadReservada;
  @Element(required=false) public int IdFamilia;
  @Element(required=false) public int IdClasificacion;
  @Element(required=false) public int IdTipoProducto;
  @Element(required=false) public String NombreTipoProducto;


  public clsBeVW_stock_res() {
  }

  public clsBeVW_stock_res(int IdBodega,int IdPropietario,int IdPropietarioBodega,int IdProducto,
                           int IdProductoBodega,int IdStock,int IdStockRes,int IdUbicacion,
                           int IdUbicacion_Anterior,int IdUnidadMedida,int IdProductoEstado,int IdPresentacion,
                           int IdRecepcionEnc,String Propietario,String UMBas,String Nombre_Presentacion,
                           String Codigo_Producto,String Nombre_Producto,String Lote,String Fecha_ingreso,
                           String Serial,int Anada,double CantidadUmBas,double Factor,
                           double CantidadPresentacion,String Fecha_Vence,String NomEstado,boolean EstadoUtilizable,
                           boolean Danado,String Lic_plate_Anterior,String Lic_plate,double Peso,
                           int IdIndiceRotacion,double AltoUbicacion,double LargoUbicacion,double AnchoUbicacion,
                           double CantidadReservadaUMBas,int IdTramo,double Ancho_ubicacion,double Largo_ubicacion,
                           double Alto_ubicacion,String IndiceRotacion,double Existencia_min_umbas,double Existencia_max_umbas,
                           String Codigo_Barra,double Costo,double Tolerancia,double Existencia_min_pres,
                           double Existencia_max_pres,String Atributo_variante_1,int Ubicacion_Nivel,int Ubicacion_Indice_x,
                           String Ubicacion_Nombre,String Ubicacion_Tramo,int IdPedido,int IdPedidoDet,
                           int IdPicking,double TotalLinea,clsBeProducto_Presentacion BePresentacionProductoEnStock,double AltoUMBas,
                           double LargoUmBas,double AnchoUmBas,boolean acepto,double peso_pickeado,
                           double peso_verificado,double Cantidad_Pickeada,double Cantidad_Verificada,double Cantidad_Despachada,
                           boolean encontrado,clsBeBodega_ubicacion UbicacionActual,double Cantidad_Res,String ValorTexto,
                           double ValorNumerico,String ValorFecha,Byte ValorLogico,String No_Serie,
                           String No_Serie_Inicial,String No_Serie_Final,double CantidadReservada,int IdFamilia,
                           int IdClasificacion,int IdTipoProducto,String NombreTipoProducto) {

    this.IdBodega=IdBodega;
    this.IdPropietario=IdPropietario;
    this.IdPropietarioBodega=IdPropietarioBodega;
    this.IdProducto=IdProducto;
    this.IdProductoBodega=IdProductoBodega;
    this.IdStock=IdStock;
    this.IdStockRes=IdStockRes;
    this.IdUbicacion=IdUbicacion;
    this.IdUbicacion_Anterior=IdUbicacion_Anterior;
    this.IdUnidadMedida=IdUnidadMedida;
    this.IdProductoEstado=IdProductoEstado;
    this.IdPresentacion=IdPresentacion;
    this.IdRecepcionEnc=IdRecepcionEnc;
    this.Propietario=Propietario;
    this.UMBas=UMBas;
    this.Nombre_Presentacion=Nombre_Presentacion;
    this.Codigo_Producto=Codigo_Producto;
    this.Nombre_Producto=Nombre_Producto;
    this.Lote=Lote;
    this.Fecha_ingreso=Fecha_ingreso;
    this.Serial=Serial;
    this.Anada=Anada;
    this.CantidadUmBas=CantidadUmBas;
    this.Factor=Factor;
    this.CantidadPresentacion=CantidadPresentacion;
    this.Fecha_Vence=Fecha_Vence;
    this.NomEstado=NomEstado;
    this.EstadoUtilizable=EstadoUtilizable;
    this.Danado=Danado;
    this.Lic_plate_Anterior=Lic_plate_Anterior;
    this.Lic_plate=Lic_plate;
    this.Peso=Peso;
    this.IdIndiceRotacion=IdIndiceRotacion;
    this.AltoUbicacion=AltoUbicacion;
    this.LargoUbicacion=LargoUbicacion;
    this.AnchoUbicacion=AnchoUbicacion;
    this.CantidadReservadaUMBas=CantidadReservadaUMBas;
    this.IdTramo=IdTramo;
    this.Ancho_ubicacion=Ancho_ubicacion;
    this.Largo_ubicacion=Largo_ubicacion;
    this.Alto_ubicacion=Alto_ubicacion;
    this.IndiceRotacion=IndiceRotacion;
    this.Existencia_min_umbas=Existencia_min_umbas;
    this.Existencia_max_umbas=Existencia_max_umbas;
    this.Codigo_Barra=Codigo_Barra;
    this.Costo=Costo;
    this.Tolerancia=Tolerancia;
    this.Existencia_min_pres=Existencia_min_pres;
    this.Existencia_max_pres=Existencia_max_pres;
    this.Atributo_variante_1=Atributo_variante_1;
    this.Ubicacion_Nivel=Ubicacion_Nivel;
    this.Ubicacion_Indice_x=Ubicacion_Indice_x;
    this.Ubicacion_Nombre=Ubicacion_Nombre;
    this.Ubicacion_Tramo=Ubicacion_Tramo;
    this.IdPedido=IdPedido;
    this.IdPedidoDet=IdPedidoDet;
    this.IdPicking=IdPicking;
    this.TotalLinea=TotalLinea;
    this.BePresentacionProductoEnStock=BePresentacionProductoEnStock;
    this.AltoUMBas=AltoUMBas;
    this.LargoUmBas=LargoUmBas;
    this.AnchoUmBas=AnchoUmBas;
    this.acepto=acepto;
    this.peso_pickeado=peso_pickeado;
    this.peso_verificado=peso_verificado;
    this.Cantidad_Pickeada=Cantidad_Pickeada;
    this.Cantidad_Verificada=Cantidad_Verificada;
    this.Cantidad_Despachada=Cantidad_Despachada;
    this.encontrado=encontrado;
    this.UbicacionActual=UbicacionActual;
    this.Cantidad_Res=Cantidad_Res;
    this.ValorTexto=ValorTexto;
    this.ValorNumerico=ValorNumerico;
    this.ValorFecha=ValorFecha;
    this.ValorLogico=ValorLogico;
    this.No_Serie=No_Serie;
    this.No_Serie_Inicial=No_Serie_Inicial;
    this.No_Serie_Final=No_Serie_Final;
    this.CantidadReservada=CantidadReservada;
    this.IdFamilia=IdFamilia;
    this.IdClasificacion=IdClasificacion;
    this.IdTipoProducto=IdTipoProducto;
    this.NombreTipoProducto=NombreTipoProducto;

  }


  public int getIdBodega() {
    return IdBodega;
  }
  public void setIdBodega(int value) {
    IdBodega=value;
  }
  public int getIdPropietario() {
    return IdPropietario;
  }
  public void setIdPropietario(int value) {
    IdPropietario=value;
  }
  public int getIdPropietarioBodega() {
    return IdPropietarioBodega;
  }
  public void setIdPropietarioBodega(int value) {
    IdPropietarioBodega=value;
  }
  public int getIdProducto() {
    return IdProducto;
  }
  public void setIdProducto(int value) {
    IdProducto=value;
  }
  public int getIdProductoBodega() {
    return IdProductoBodega;
  }
  public void setIdProductoBodega(int value) {
    IdProductoBodega=value;
  }
  public int getIdStock() {
    return IdStock;
  }
  public void setIdStock(int value) {
    IdStock=value;
  }
  public int getIdStockRes() {
    return IdStockRes;
  }
  public void setIdStockRes(int value) {
    IdStockRes=value;
  }
  public int getIdUbicacion() {
    return IdUbicacion;
  }
  public void setIdUbicacion(int value) {
    IdUbicacion=value;
  }
  public int getIdUbicacion_Anterior() {
    return IdUbicacion_Anterior;
  }
  public void setIdUbicacion_Anterior(int value) {
    IdUbicacion_Anterior=value;
  }
  public int getIdUnidadMedida() {
    return IdUnidadMedida;
  }
  public void setIdUnidadMedida(int value) {
    IdUnidadMedida=value;
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
  public int getIdRecepcionEnc() {
    return IdRecepcionEnc;
  }
  public void setIdRecepcionEnc(int value) {
    IdRecepcionEnc=value;
  }
  public String getPropietario() {
    return Propietario;
  }
  public void setPropietario(String value) {
    Propietario=value;
  }
  public String getUMBas() {
    return UMBas;
  }
  public void setUMBas(String value) {
    UMBas=value;
  }
  public String getNombre_Presentacion() {
    return Nombre_Presentacion;
  }
  public void setNombre_Presentacion(String value) {
    Nombre_Presentacion=value;
  }
  public String getCodigo_Producto() {
    return Codigo_Producto;
  }
  public void setCodigo_Producto(String value) {
    Codigo_Producto=value;
  }
  public String getNombre_Producto() {
    return Nombre_Producto;
  }
  public void setNombre_Producto(String value) {
    Nombre_Producto=value;
  }
  public String getLote() {
    return Lote;
  }
  public void setLote(String value) {
    Lote=value;
  }
  public String getFecha_ingreso() {
    return Fecha_ingreso;
  }
  public void setFecha_ingreso(String value) {
    Fecha_ingreso=value;
  }
  public String getSerial() {
    return Serial;
  }
  public void setSerial(String value) {
    Serial=value;
  }
  public int getAnada() {
    return Anada;
  }
  public void setAnada(int value) {
    Anada=value;
  }
  public double getCantidadUmBas() {
    return CantidadUmBas;
  }
  public void setCantidadUmBas(double value) {
    CantidadUmBas=value;
  }
  public double getFactor() {
    return Factor;
  }
  public void setFactor(double value) {
    Factor=value;
  }
  public double getCantidadPresentacion() {
    return CantidadPresentacion;
  }
  public void setCantidadPresentacion(double value) {
    CantidadPresentacion=value;
  }
  public String getFecha_Vence() {
    return Fecha_Vence;
  }
  public void setFecha_Vence(String value) {
    Fecha_Vence=value;
  }
  public String getNomEstado() {
    return NomEstado;
  }
  public void setNomEstado(String value) {
    NomEstado=value;
  }
  public boolean getEstadoUtilizable() {
    return EstadoUtilizable;
  }
  public void setEstadoUtilizable(boolean value) {
    EstadoUtilizable=value;
  }
  public boolean getDanado() {
    return Danado;
  }
  public void setDanado(boolean value) {
    Danado=value;
  }
  public String getLic_plate_Anterior() {
    return Lic_plate_Anterior;
  }
  public void setLic_plate_Anterior(String value) {
    Lic_plate_Anterior=value;
  }
  public String getLic_plate() {
    return Lic_plate;
  }
  public void setLic_plate(String value) {
    Lic_plate=value;
  }
  public double getPeso() {
    return Peso;
  }
  public void setPeso(double value) {
    Peso=value;
  }
  public int getIdIndiceRotacion() {
    return IdIndiceRotacion;
  }
  public void setIdIndiceRotacion(int value) {
    IdIndiceRotacion=value;
  }
  public double getAltoUbicacion() {
    return AltoUbicacion;
  }
  public void setAltoUbicacion(double value) {
    AltoUbicacion=value;
  }
  public double getLargoUbicacion() {
    return LargoUbicacion;
  }
  public void setLargoUbicacion(double value) {
    LargoUbicacion=value;
  }
  public double getAnchoUbicacion() {
    return AnchoUbicacion;
  }
  public void setAnchoUbicacion(double value) {
    AnchoUbicacion=value;
  }
  public double getCantidadReservadaUMBas() {
    return CantidadReservadaUMBas;
  }
  public void setCantidadReservadaUMBas(double value) {
    CantidadReservadaUMBas=value;
  }
  public int getIdTramo() {
    return IdTramo;
  }
  public void setIdTramo(int value) {
    IdTramo=value;
  }
  public double getAncho_ubicacion() {
    return Ancho_ubicacion;
  }
  public void setAncho_ubicacion(double value) {
    Ancho_ubicacion=value;
  }
  public double getLargo_ubicacion() {
    return Largo_ubicacion;
  }
  public void setLargo_ubicacion(double value) {
    Largo_ubicacion=value;
  }
  public double getAlto_ubicacion() {
    return Alto_ubicacion;
  }
  public void setAlto_ubicacion(double value) {
    Alto_ubicacion=value;
  }
  public String getIndiceRotacion() {
    return IndiceRotacion;
  }
  public void setIndiceRotacion(String value) {
    IndiceRotacion=value;
  }
  public double getExistencia_min_umbas() {
    return Existencia_min_umbas;
  }
  public void setExistencia_min_umbas(double value) {
    Existencia_min_umbas=value;
  }
  public double getExistencia_max_umbas() {
    return Existencia_max_umbas;
  }
  public void setExistencia_max_umbas(double value) {
    Existencia_max_umbas=value;
  }
  public String getCodigo_Barra() {
    return Codigo_Barra;
  }
  public void setCodigo_Barra(String value) {
    Codigo_Barra=value;
  }
  public double getCosto() {
    return Costo;
  }
  public void setCosto(double value) {
    Costo=value;
  }
  public double getTolerancia() {
    return Tolerancia;
  }
  public void setTolerancia(double value) {
    Tolerancia=value;
  }
  public double getExistencia_min_pres() {
    return Existencia_min_pres;
  }
  public void setExistencia_min_pres(double value) {
    Existencia_min_pres=value;
  }
  public double getExistencia_max_pres() {
    return Existencia_max_pres;
  }
  public void setExistencia_max_pres(double value) {
    Existencia_max_pres=value;
  }
  public String getAtributo_variante_1() {
    return Atributo_variante_1;
  }
  public void setAtributo_variante_1(String value) {
    Atributo_variante_1=value;
  }
  public int getUbicacion_Nivel() {
    return Ubicacion_Nivel;
  }
  public void setUbicacion_Nivel(int value) {
    Ubicacion_Nivel=value;
  }
  public int getUbicacion_Indice_x() {
    return Ubicacion_Indice_x;
  }
  public void setUbicacion_Indice_x(int value) {
    Ubicacion_Indice_x=value;
  }
  public String getUbicacion_Nombre() {
    return Ubicacion_Nombre;
  }
  public void setUbicacion_Nombre(String value) {
    Ubicacion_Nombre=value;
  }
  public String getUbicacion_Tramo() {
    return Ubicacion_Tramo;
  }
  public void setUbicacion_Tramo(String value) {
    Ubicacion_Tramo=value;
  }
  public int getIdPedido() {
    return IdPedido;
  }
  public void setIdPedido(int value) {
    IdPedido=value;
  }
  public int getIdPedidoDet() {
    return IdPedidoDet;
  }
  public void setIdPedidoDet(int value) {
    IdPedidoDet=value;
  }
  public int getIdPicking() {
    return IdPicking;
  }
  public void setIdPicking(int value) {
    IdPicking=value;
  }
  public double getTotalLinea() {
    return TotalLinea;
  }
  public void setTotalLinea(double value) {
    TotalLinea=value;
  }
  public clsBeProducto_Presentacion getBePresentacionProductoEnStock() {
    return BePresentacionProductoEnStock;
  }
  public void setBePresentacionProductoEnStock(clsBeProducto_Presentacion value) {
    BePresentacionProductoEnStock=value;
  }
  public double getAltoUMBas() {
    return AltoUMBas;
  }
  public void setAltoUMBas(double value) {
    AltoUMBas=value;
  }
  public double getLargoUmBas() {
    return LargoUmBas;
  }
  public void setLargoUmBas(double value) {
    LargoUmBas=value;
  }
  public double getAnchoUmBas() {
    return AnchoUmBas;
  }
  public void setAnchoUmBas(double value) {
    AnchoUmBas=value;
  }
  public boolean getacepto() {
    return acepto;
  }
  public void setacepto(boolean value) {
    acepto=value;
  }
  public double getpeso_pickeado() {
    return peso_pickeado;
  }
  public void setpeso_pickeado(double value) {
    peso_pickeado=value;
  }
  public double getpeso_verificado() {
    return peso_verificado;
  }
  public void setpeso_verificado(double value) {
    peso_verificado=value;
  }
  public double getCantidad_Pickeada() {
    return Cantidad_Pickeada;
  }
  public void setCantidad_Pickeada(double value) {
    Cantidad_Pickeada=value;
  }
  public double getCantidad_Verificada() {
    return Cantidad_Verificada;
  }
  public void setCantidad_Verificada(double value) {
    Cantidad_Verificada=value;
  }
  public double getCantidad_Despachada() {
    return Cantidad_Despachada;
  }
  public void setCantidad_Despachada(double value) {
    Cantidad_Despachada=value;
  }
  public boolean getencontrado() {
    return encontrado;
  }
  public void setencontrado(boolean value) {
    encontrado=value;
  }
  public clsBeBodega_ubicacion getUbicacionActual() {
    return UbicacionActual;
  }
  public void setUbicacionActual(clsBeBodega_ubicacion value) {
    UbicacionActual=value;
  }
  public double getCantidad_Res() {
    return Cantidad_Res;
  }
  public void setCantidad_Res(double value) {
    Cantidad_Res=value;
  }
  public String getValorTexto() {
    return ValorTexto;
  }
  public void setValorTexto(String value) {
    ValorTexto=value;
  }
  public double getValorNumerico() {
    return ValorNumerico;
  }
  public void setValorNumerico(double value) {
    ValorNumerico=value;
  }
  public String getValorFecha() {
    return ValorFecha;
  }
  public void setValorFecha(String value) {
    ValorFecha=value;
  }
  public Byte getValorLogico() {
    return ValorLogico;
  }
  public void setValorLogico(Byte value) {
    ValorLogico=value;
  }
  public String getNo_Serie() {
    return No_Serie;
  }
  public void setNo_Serie(String value) {
    No_Serie=value;
  }
  public String getNo_Serie_Inicial() {
    return No_Serie_Inicial;
  }
  public void setNo_Serie_Inicial(String value) {
    No_Serie_Inicial=value;
  }
  public String getNo_Serie_Final() {
    return No_Serie_Final;
  }
  public void setNo_Serie_Final(String value) {
    No_Serie_Final=value;
  }
  public double getCantidadReservada() {
    return CantidadReservada;
  }
  public void setCantidadReservada(double value) {
    CantidadReservada=value;
  }
  public int getIdFamilia() {
    return IdFamilia;
  }
  public void setIdFamilia(int value) {
    IdFamilia=value;
  }
  public int getIdClasificacion() {
    return IdClasificacion;
  }
  public void setIdClasificacion(int value) {
    IdClasificacion=value;
  }
  public int getIdTipoProducto() {
    return IdTipoProducto;
  }
  public void setIdTipoProducto(int value) {
    IdTipoProducto=value;
  }
  public String getNombreTipoProducto() {
    return NombreTipoProducto;
  }
  public void setNombreTipoProducto(String value) {
    NombreTipoProducto=value;
  }

}

