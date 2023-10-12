package com.dts.classes.Transacciones.Picking;


import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;

import org.simpleframework.xml.Element;

public class clsBeTrans_picking_ubic {

    @Element(required=false) public int IdPickingEnc=0;
    @Element(required=false) public int IdPickingUbic=0;
    @Element(required=false) public int IdPickingDet=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_minima="1900-01-01T00:00:00";
    @Element(required=false) public String Serial="";
    @Element(required=false) public String Lic_plate="";
    @Element(required=false) public boolean Acepto=false;
    @Element(required=false) public double Peso_solicitado=0;
    @Element(required=false) public double Peso_recibido=0.00;
    @Element(required=false) public double Peso_verificado=0;
    @Element(required=false) public double Peso_despachado=0;
    @Element(required=false) public double Cantidad_Solicitada=0;
    @Element(required=false) public double Cantidad_Recibida=0;
    @Element(required=false) public double Cantidad_Verificada=0;
    @Element(required=false) public boolean Encontrado=false;
    @Element(required=false) public boolean Danado_verificacion =false;
    @Element(required=false) public String Fecha_real_vence="1900-01-01T00:00:00";
    @Element(required=false) public String No_packing="";
    @Element(required=false) public String Fecha_picking="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_verificado="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_packing="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_despachado="1900-01-01T00:00:00";
    @Element(required=false) public double Cantidad_despachada=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Danado_picking =false;
    @Element(required=false) public int IdProducto=0;
    @Element(required=false) public int IdOperadorBodega_Pickeo=0;
    @Element(required=false) public int IdOperadorBodega_Verifico=0;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public String NombreUbicacion="";
    @Element(required=false) public int IdPedidoDet=0;
    @Element(required=false) public int IdStockRes=0;
    @Element(required=false) public int IdStock=0;
    @Element(required=false) public String CodigoProducto="";
    @Element(required=false) public String NombreProducto="";
    @Element(required=false) public String ProductoPresentacion="";
    @Element(required=false) public String ProductoUnidadMedida="";
    @Element(required=false) public String ProductoEstado="";
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdProductoEstado=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public clsBeBodega_ubicacion Ubicacion=new clsBeBodega_ubicacion();
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdUbicacionAnterior=0;
    @Element(required=false) public int IdRecepcion=0;
    @Element(required=false) public double CantidadDanada =0;
    @Element(required=false) public String Lic_plate_Reemplazo="";
    @Element(required=false) public int IdUbicacion_reemplazo=0;
    @Element(required=false) public int IdStock_reemplazo=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public double Tarima =0;
    @Element(required=false) public  boolean No_encontrado = false;
    @Element(required=false) public String NombreArea = "";
    @Element(required=false) public String NombreClasificacion="";
    @Element(required=false) public int IdUbicacionTemporal=0;
    @Element(required=false) public String NombreUbicacionTemporal="";
    @Element(required=false) public int IdOperadorBodega_Asignado=0;

    public clsBeTrans_picking_ubic() {
    }

    public clsBeTrans_picking_ubic(int IdPickingEnc, String NombreArea,int IdPickingUbic, int IdPickingDet, int IdUbicacion,
                                   String Lote, String Fecha_Vence, String Fecha_minima, String Serial,
                                   String Lic_plate, boolean Acepto, double Peso_solicitado, double Peso_recibido,
                                   double Peso_verificado, double Peso_despachado, double Cantidad_Solicitada, double Cantidad_Recibida,
                                   double Cantidad_Verificada, boolean Encontrado, boolean Danado_verificacion, String Fecha_real_vence,
                                   String No_packing, String Fecha_picking, String Fecha_verificado, String Fecha_packing,
                                   String Fecha_despachado, double Cantidad_despachada, String User_agr, String Fec_agr,
                                   String User_mod, String Fec_mod, boolean Activo, boolean Danado_picking,
                                   int IdProducto, int IdOperadorBodega_Pickeo, int IdOperadorBodega_Verifico, boolean IsNew,
                                   String NombreUbicacion, int IdPedidoDet, int IdStockRes, int IdStock,
                                   String CodigoProducto, String NombreProducto, String ProductoPresentacion, String ProductoUnidadMedida,
                                   String ProductoEstado, int IdProductoBodega, int IdProductoEstado, int IdPresentacion,
                                   int IdUnidadMedida, int IdPedidoEnc, clsBeBodega_ubicacion Ubicacion, int IdPropietarioBodega,
                                   int IdUbicacionAnterior, int IdRecepcion, double CantidadDanada, String Lic_plate_Reemplazo,
                                   int IdUbicacion_reemplazo, int IdStock_reemplazo, int IdBodega, double Tarima,
                                   boolean No_encontrado, String NombreClasificacion, int IdUbicacionTemporal, String NombreUbicacionTemporal,
                                   int IdOperadorBodega_Asignado ) {

        this.IdPickingEnc=IdPickingEnc;
        this.NombreArea = NombreArea;
        this.IdPickingUbic=IdPickingUbic;
        this.IdPickingDet=IdPickingDet;
        this.IdUbicacion=IdUbicacion;
        this.Lote=Lote;
        this.Fecha_Vence=Fecha_Vence;
        this.Fecha_minima=Fecha_minima;
        this.Serial=Serial;
        this.Lic_plate=Lic_plate;
        this.Acepto=Acepto;
        this.Peso_solicitado=Peso_solicitado;
        this.Peso_recibido=Peso_recibido;
        this.Peso_verificado=Peso_verificado;
        this.Peso_despachado=Peso_despachado;
        this.Cantidad_Solicitada=Cantidad_Solicitada;
        this.Cantidad_Recibida=Cantidad_Recibida;
        this.Cantidad_Verificada=Cantidad_Verificada;
        this.Encontrado=Encontrado;
        this.Danado_verificacion = Danado_verificacion;
        this.Fecha_real_vence=Fecha_real_vence;
        this.No_packing=No_packing;
        this.Fecha_picking=Fecha_picking;
        this.Fecha_verificado=Fecha_verificado;
        this.Fecha_packing=Fecha_packing;
        this.Fecha_despachado=Fecha_despachado;
        this.Cantidad_despachada=Cantidad_despachada;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Danado_picking = Danado_picking;
        this.IdProducto=IdProducto;
        this.IdOperadorBodega_Pickeo=IdOperadorBodega_Pickeo;
        this.IdOperadorBodega_Verifico=IdOperadorBodega_Verifico;
        this.IsNew=IsNew;
        this.NombreUbicacion=NombreUbicacion;
        this.IdPedidoDet=IdPedidoDet;
        this.IdStockRes=IdStockRes;
        this.IdStock=IdStock;
        this.CodigoProducto=CodigoProducto;
        this.NombreProducto=NombreProducto;
        this.ProductoPresentacion=ProductoPresentacion;
        this.ProductoUnidadMedida=ProductoUnidadMedida;
        this.ProductoEstado=ProductoEstado;
        this.IdProductoBodega=IdProductoBodega;
        this.IdProductoEstado=IdProductoEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdPedidoEnc=IdPedidoEnc;
        this.Ubicacion=Ubicacion;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdUbicacionAnterior=IdUbicacionAnterior;
        this.IdRecepcion=IdRecepcion;
        this.CantidadDanada = CantidadDanada;
        this.Lic_plate_Reemplazo=Lic_plate_Reemplazo;
        this.IdUbicacion_reemplazo=IdUbicacion_reemplazo;
        this.IdStock_reemplazo=IdStock_reemplazo;
        this.IdBodega=IdBodega;
        this.Tarima=Tarima;
        this.No_encontrado = No_encontrado;
        this.NombreClasificacion = NombreClasificacion;
        this.IdUbicacionTemporal = IdUbicacionTemporal;
        this.NombreUbicacionTemporal = NombreUbicacionTemporal;
        this.IdOperadorBodega_Asignado = IdOperadorBodega_Asignado;
    }


    public int getIdPickingEnc() {
        return IdPickingEnc;
    }
    public void setIdPickingEnc(int value) {
        IdPickingEnc=value;
    }
    public int getIdPickingUbic() {
        return IdPickingUbic;
    }
    public void setIdPickingUbic(int value) {
        IdPickingUbic=value;
    }
    public int getIdPickingDet() {
        return IdPickingDet;
    }
    public void setIdPickingDet(int value) {
        IdPickingDet=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getFecha_Vence() {
        return Fecha_Vence;
    }
    public void setFecha_Vence(String value) {
        Fecha_Vence=value;
    }
    public String getFecha_minima() {
        return Fecha_minima;
    }
    public void setFecha_minima(String value) {
        Fecha_minima=value;
    }
    public String getSerial() {
        return Serial;
    }
    public void setSerial(String value) {
        Serial=value;
    }
    public String getLic_plate() {
        return Lic_plate;
    }
    public void setLic_plate(String value) {
        Lic_plate=value;
    }
    public boolean getAcepto() {
        return Acepto;
    }
    public void setAcepto(boolean value) {
        Acepto=value;
    }
    public double getPeso_solicitado() {
        return Peso_solicitado;
    }
    public void setPeso_solicitado(double value) {
        Peso_solicitado=value;
    }
    public double getPeso_recibido() {
        return Peso_recibido;
    }
    public void setPeso_recibido(double value) {
        Peso_recibido=value;
    }
    public double getPeso_verificado() {
        return Peso_verificado;
    }
    public void setPeso_verificado(double value) {
        Peso_verificado=value;
    }
    public double getPeso_despachado() {
        return Peso_despachado;
    }
    public void setPeso_despachado(double value) {
        Peso_despachado=value;
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
    public boolean getEncontrado() {
        return Encontrado;
    }
    public void setEncontrado(boolean value) {
        Encontrado=value;
    }
    public boolean getDanado_verificacion() {
        return Danado_verificacion;
    }
    public void setDanado_verificacion(boolean value) {
        Danado_verificacion =value;
    }
    public String getFecha_real_vence() {
        return Fecha_real_vence;
    }
    public void setFecha_real_vence(String value) {
        Fecha_real_vence=value;
    }
    public String getNo_packing() {
        return No_packing;
    }
    public void setNo_packing(String value) {
        No_packing=value;
    }
    public String getFecha_picking() {
        return Fecha_picking;
    }
    public void setFecha_picking(String value) {
        Fecha_picking=value;
    }
    public String getFecha_verificado() {
        return Fecha_verificado;
    }
    public void setFecha_verificado(String value) {
        Fecha_verificado=value;
    }
    public String getFecha_packing() {
        return Fecha_packing;
    }
    public void setFecha_packing(String value) {
        Fecha_packing=value;
    }
    public String getFecha_despachado() {
        return Fecha_despachado;
    }
    public void setFecha_despachado(String value) {
        Fecha_despachado=value;
    }
    public double getCantidad_despachada() {
        return Cantidad_despachada;
    }
    public void setCantidad_despachada(double value) {
        Cantidad_despachada=value;
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
    public boolean getDanado_picking() {
        return Danado_picking;
    }
    public void setDanado_picking(boolean value) {
        Danado_picking =value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }
    public int getIdOperadorBodega_Pickeo() {
        return IdOperadorBodega_Pickeo;
    }
    public void setIdOperadorBodega_Pickeo(int value) {
        IdOperadorBodega_Pickeo=value;
    }
    public int getIdOperadorBodega_Verifico() {
        return IdOperadorBodega_Verifico;
    }
    public void setIdOperadorBodega_Verifico(int value) {
        IdOperadorBodega_Verifico=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getNombreUbicacion() {
        return NombreUbicacion;
    }
    public void setNombreUbicacion(String value) {
        NombreUbicacion=value;
    }
    public int getIdPedidoDet() {
        return IdPedidoDet;
    }
    public void setIdPedidoDet(int value) {
        IdPedidoDet=value;
    }
    public int getIdStockRes() {
        return IdStockRes;
    }
    public void setIdStockRes(int value) {
        IdStockRes=value;
    }
    public int getIdStock() {
        return IdStock;
    }
    public void setIdStock(int value) {
        IdStock=value;
    }
    public String getCodigoProducto() {
        return CodigoProducto;
    }
    public void setCodigoProducto(String value) {
        CodigoProducto=value;
    }
    public String getNombreProducto() {
        return NombreProducto;
    }
    public void setNombreProducto(String value) {
        NombreProducto=value;
    }
    public String getProductoPresentacion() {
        return ProductoPresentacion;
    }
    public void setProductoPresentacion(String value) {
        ProductoPresentacion=value;
    }
    public String getProductoUnidadMedida() {
        return ProductoUnidadMedida;
    }
    public void setProductoUnidadMedida(String value) {
        ProductoUnidadMedida=value;
    }
    public String getProductoEstado() {
        return ProductoEstado;
    }
    public void setProductoEstado(String value) {
        ProductoEstado=value;
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
    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public clsBeBodega_ubicacion getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(clsBeBodega_ubicacion value) {
        Ubicacion=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdUbicacionAnterior() {
        return IdUbicacionAnterior;
    }
    public void setIdUbicacionAnterior(int value) {
        IdUbicacionAnterior=value;
    }
    public int getIdRecepcion() {
        return IdRecepcion;
    }
    public void setIdRecepcion(int value) {
        IdRecepcion=value;
    }
    public double getCantidadDanada() {
        return CantidadDanada;
    }
    public void setCantidadDanada(double value) {
        CantidadDanada =value;
    }
    public String getLic_plate_Reemplazo() {
        return Lic_plate_Reemplazo;
    }
    public void setLic_plate_Reemplazo(String value) {
        Lic_plate_Reemplazo=value;
    }
    public int getIdUbicacion_reemplazo() {
        return IdUbicacion_reemplazo;
    }
    public void setIdUbicacion_reemplazo(int value) {
        IdUbicacion_reemplazo=value;
    }
    public int getIdStock_reemplazo() {
        return IdStock_reemplazo;
    }
    public void setIdStock_reemplazo(int value) {
        IdStock_reemplazo=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public double getTarima() {
        return Tarima;
    }
    public void setIdBodega(double value) {
        Tarima=value;
    }
    public boolean getNoEncontrado() {
        return No_encontrado;
    }
    public void setNoEncontrado(boolean value) {
        No_encontrado=value;
    }
    public String getNombreArea() {
        return NombreArea;
    }
    public void setNombreArea(String value) {
        NombreArea=value;
    }
    public String getNombreClasificacion() {
        return NombreClasificacion;
    }
    public void setNombreClasificacion(String value) {
        NombreClasificacion=value;
    }

    public int getIdUbicacionTemporal() {
        return IdUbicacionTemporal;
    }
    public void setIdUbicacionTemporal(int value) {
        IdUbicacionTemporal=value;
    }

    public String getNombreUbicacionTemporal() {
        return NombreUbicacionTemporal;
    }
    public void setNombreUbicacionTemporal(String value) {
        NombreUbicacionTemporal=value;
    }

    public int getIdOperadorBodega_Asignado() {
        return IdOperadorBodega_Asignado;
    }
    public void setIdOperadorBodega_Asignado(int value) {
        IdOperadorBodega_Asignado=value;
    }


}

