package com.dts.classes;

import java.util.Date;

public class clsBestock {

  public int IdBodega;
  public int IdStock;
  public int IdPropietarioBodega;
  public int IdProductoBodega;
  public int IdProductoEstado;
  public int IdPresentacion;
  public int IdUnidadMedida;
  public int IdUbicacion;
  public int IdUbicacion_anterior;
  public int IdRecepcionEnc;
  public int IdRecepcionDet;
  public int IdPedidoEnc;
  public int IdPickingEnc;
  public int IdDespachoEnc;
  public String lote;
  public String lic_plate;
  public String serial;
  public double cantidad;
  public Date fecha_ingreso;
  public Date fecha_vence;
  public double uds_lic_plate;
  public int no_bulto;
  public Date fecha_manufactura;
  public int añada;
  public String user_agr;
  public Date fec_agr;
  public String user_mod;
  public Date fec_mod;
  public boolean activo;
  public double peso;
  public double temperatura;
  public String atributo_variante_1;

  public clsBeproducto_estado ProductoEstado;
  public clsBeproducto_presentacion Presentacion;
  public clsBeproducto Producto;

}