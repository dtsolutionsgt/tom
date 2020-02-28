package com.dts.classes;

import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;

import java.util.Date;

public class clsBetrans_ubic_hh_det {

  public int IdTareaUbicacionEnc;
  public int IdTareaUbicacionDet;
  public int IdStock;
  public int IdUbicacionOrigen;
  public int IdUbicacionDestino;
  public int IdEstadoOrigen;
  public int IdEstadoDestino;
  public int IdOperador;
  public Date HoraInicio;
  public Date HoraFin;
  public boolean Realizado;
  public double cantidad;
  public boolean activo;
  public double recibido;
  public String estado;
  public String atributo_variante_1;
  public clsBeProducto Producto;
  public clsBeBodega_ubicacion UbicacionOrigen;
  public clsBeBodega_ubicacion UbicacionDestino;
  public clsBeProducto_Presentacion ProductoPresentacion;
  public clsBestock Stock;
  public clsBeUnidad_medida UnidadMedida;



}