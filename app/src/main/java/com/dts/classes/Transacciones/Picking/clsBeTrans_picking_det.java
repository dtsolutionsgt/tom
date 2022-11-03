package com.dts.classes.Transacciones.Picking;


import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;

import org.simpleframework.xml.Element;

public class clsBeTrans_picking_det {

    @Element(required=false) public int IdPickingDet=0;
    @Element(required=false) public int IdPickingEnc=0;
    @Element(required=false) public int IdPedidoDet=0;
    @Element(required=false) public int IdOperadorBodega=0;
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public int Cliente_dias=0;
    @Element(required=false) public double Cantidad_recibida=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String Bodega="";
    @Element(required=false) public String Cliente="";
    @Element(required=false) public String Propietario="";
    @Element(required=false) public String FechaPedido="1900-01-01T00:00:01";
    @Element(required=false) public int No_Documento=0;
    @Element(required=false) public int IdMuelle=0;
    @Element(required=false) public String Hora_Inicio="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_fin="1900-01-01T00:00:01";
    @Element(required=false) public String Estado="";
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public String UbicacionPicking="";
    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public String Referencia="";
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String NombreProducto="";
    @Element(required=false) public String Fecha_Ingreso="1900-01-01T00:00:01";
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:01";
    @Element(required=false) public String Presetacion="";
    @Element(required=false) public double Factorx=0;
    @Element(required=false) public double CantidadReservada=0;
    @Element(required=false) public double Cantidad_Pickeada=0;
    @Element(required=false) public double Cantidad_Verificada=0;
    @Element(required=false) public double Cantidad_Stock=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public String UMBas="";
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();
    @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeProducto_estado ProductoEstado=new clsBeProducto_estado();
    @Element(required=false) public clsBeUnidad_medida UnidadMedida=new clsBeUnidad_medida();
    @Element(required=false) public clsBeTrans_picking_det_parametrosList ListaDetalleParametro=new clsBeTrans_picking_det_parametrosList();
    @Element(required=false) public String Lic_Plate="";
    @Element(required=false) public String Lote="";

    public clsBeTrans_picking_det() {
    }

    public clsBeTrans_picking_det(int IdPickingDet,int IdPickingEnc,int IdPedidoDet,int IdOperadorBodega,
                                  double Cantidad,int Cliente_dias,double Cantidad_recibida,String User_agr,
                                  String Fec_agr,String User_mod,String Fec_mod,boolean Activo,
                                  String Bodega,String Cliente,String Propietario,String FechaPedido,
                                  int No_Documento,int IdMuelle,String Hora_Inicio,String Hora_fin,
                                  String Estado,boolean IsNew,String UbicacionPicking,int IdPedidoEnc,
                                  String Referencia,String Codigo,String NombreProducto,String Fecha_Ingreso,
                                  String Fecha_Vence,String Presetacion,double Factorx,double CantidadReservada,
                                  double Cantidad_Pickeada,double Cantidad_Verificada,double Cantidad_Stock,int IdUbicacion,
                                  String UMBas,clsBeProducto Producto,clsBeProducto_Presentacion Presentacion,clsBeProducto_estado ProductoEstado,
                                  clsBeUnidad_medida UnidadMedida,clsBeTrans_picking_det_parametrosList ListaDetalleParametro,clsBeTrans_picking_ubicList ListaDetalleUbicacion,
                                  String Lic_Plate, String Lote) {

        this.IdPickingDet=IdPickingDet;
        this.IdPickingEnc=IdPickingEnc;
        this.IdPedidoDet=IdPedidoDet;
        this.IdOperadorBodega=IdOperadorBodega;
        this.Cantidad=Cantidad;
        this.Cliente_dias=Cliente_dias;
        this.Cantidad_recibida=Cantidad_recibida;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Bodega=Bodega;
        this.Cliente=Cliente;
        this.Propietario=Propietario;
        this.FechaPedido=FechaPedido;
        this.No_Documento=No_Documento;
        this.IdMuelle=IdMuelle;
        this.Hora_Inicio=Hora_Inicio;
        this.Hora_fin=Hora_fin;
        this.Estado=Estado;
        this.IsNew=IsNew;
        this.UbicacionPicking=UbicacionPicking;
        this.IdPedidoEnc=IdPedidoEnc;
        this.Referencia=Referencia;
        this.Codigo=Codigo;
        this.NombreProducto=NombreProducto;
        this.Fecha_Ingreso=Fecha_Ingreso;
        this.Fecha_Vence=Fecha_Vence;
        this.Presetacion=Presetacion;
        this.Factorx=Factorx;
        this.CantidadReservada=CantidadReservada;
        this.Cantidad_Pickeada=Cantidad_Pickeada;
        this.Cantidad_Verificada=Cantidad_Verificada;
        this.Cantidad_Stock=Cantidad_Stock;
        this.IdUbicacion=IdUbicacion;
        this.UMBas=UMBas;
        this.Producto=Producto;
        this.Presentacion=Presentacion;
        this.ProductoEstado=ProductoEstado;
        this.UnidadMedida=UnidadMedida;
        this.ListaDetalleParametro=ListaDetalleParametro;
        this.Lic_Plate=Lic_Plate;
        this.Lote=Lote;

    }

    public int getIdPickingDet() {
        return IdPickingDet;
    }
    public void setIdPickingDet(int value) {
        IdPickingDet=value;
    }
    public int getIdPickingEnc() {
        return IdPickingEnc;
    }
    public void setIdPickingEnc(int value) {
        IdPickingEnc=value;
    }
    public int getIdPedidoDet() {
        return IdPedidoDet;
    }
    public void setIdPedidoDet(int value) {
        IdPedidoDet=value;
    }
    public int getIdOperadorBodega() {
        return IdOperadorBodega;
    }
    public void setIdOperadorBodega(int value) {
        IdOperadorBodega=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public int getCliente_dias() {
        return Cliente_dias;
    }
    public void setCliente_dias(int value) {
        Cliente_dias=value;
    }
    public double getCantidad_recibida() {
        return Cantidad_recibida;
    }
    public void setCantidad_recibida(double value) {
        Cantidad_recibida=value;
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
    public String getBodega() {
        return Bodega;
    }
    public void setBodega(String value) {
        Bodega=value;
    }
    public String getCliente() {
        return Cliente;
    }
    public void setCliente(String value) {
        Cliente=value;
    }
    public String getPropietario() {
        return Propietario;
    }
    public void setPropietario(String value) {
        Propietario=value;
    }
    public String getFechaPedido() {
        return FechaPedido;
    }
    public void setFechaPedido(String value) {
        FechaPedido=value;
    }
    public int getNo_Documento() {
        return No_Documento;
    }
    public void setNo_Documento(int value) {
        No_Documento=value;
    }
    public int getIdMuelle() {
        return IdMuelle;
    }
    public void setIdMuelle(int value) {
        IdMuelle=value;
    }
    public String getHora_Inicio() {
        return Hora_Inicio;
    }
    public void setHora_Inicio(String value) {
        Hora_Inicio=value;
    }
    public String getHora_fin() {
        return Hora_fin;
    }
    public void setHora_fin(String value) {
        Hora_fin=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getUbicacionPicking() {
        return UbicacionPicking;
    }
    public void setUbicacionPicking(String value) {
        UbicacionPicking=value;
    }
    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getNombreProducto() {
        return NombreProducto;
    }
    public void setNombreProducto(String value) {
        NombreProducto=value;
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
    public String getPresetacion() {
        return Presetacion;
    }
    public void setPresetacion(String value) {
        Presetacion=value;
    }
    public double getFactorx() {
        return Factorx;
    }
    public void setFactorx(double value) {
        Factorx=value;
    }
    public double getCantidadReservada() {
        return CantidadReservada;
    }
    public void setCantidadReservada(double value) {
        CantidadReservada=value;
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
    public double getCantidad_Stock() {
        return Cantidad_Stock;
    }
    public void setCantidad_Stock(double value) {
        Cantidad_Stock=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public String getUMBas() {
        return UMBas;
    }
    public void setUMBas(String value) {
        UMBas=value;
    }
    public clsBeProducto getProducto() {
        return Producto;
    }
    public void setProducto(clsBeProducto value) {
        Producto=value;
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
    public clsBeUnidad_medida getUnidadMedida() {
        return UnidadMedida;
    }
    public void setUnidadMedida(clsBeUnidad_medida value) {
        UnidadMedida=value;
    }
    public clsBeTrans_picking_det_parametrosList getListaDetalleParametro() {
        return ListaDetalleParametro;
    }
    public void setListaDetalleParametro(clsBeTrans_picking_det_parametrosList value) {
        ListaDetalleParametro=value;
    }
    public String getLic_Plate() {
        return Lic_Plate;
    }
    public void setLic_Plate(String value) {
        Lic_Plate=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
}

