package com.dts.classes.Transacciones.Stock.Stock_res;


import org.simpleframework.xml.Element;

public class clsBeStock_res {

    @Element(required=false) public int IdStockRes=0;
    @Element(required=false) public int IdTransaccion=0;
    @Element(required=false) public String Indicador="";
    @Element(required=false) public int IdPedidoDet=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdStock=0;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public int IdProductoEstado=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Lic_plate="";
    @Element(required=false) public String Serial="";
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public double Peso=0;
    @Element(required=false) public String Estado="";
    @Element(required=false) public String Fecha_ingreso="";
    @Element(required=false) public String Fecha_vence="";
    @Element(required=false) public double Uds_lic_plate=0;
    @Element(required=false) public String Ubicacion_ant="";
    @Element(required=false) public int No_bulto=0;
    @Element(required=false) public int IdRecepcion=0;
    @Element(required=false) public int IdPicking=0;
    @Element(required=false) public int IdPedido=0;
    @Element(required=false) public int IdDespacho=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="";
    @Element(required=false) public String Host="";
    @Element(required=false) public int anada=0;
    @Element(required=false) public String Fecha_manufactura="";
    @Element(required=false) public String Atributo_Variante_1="";
    @Element(required=false) public boolean Control_Ultimo_Lote=false;
    @Element(required=false) public String Ultimo_Lote="";
    @Element(required=false) public Boolean Pallet_no_estandar=false;
    @Element(required=false) public String Codigo_Producto="";
    @Element(required=false) public String No_Pedido="";

    public clsBeStock_res() {
    }

    public clsBeStock_res(int IdStockRes, int IdTransaccion, String Indicador, int IdPedidoDet,
                          int IdBodega, int IdStock, int IdPropietarioBodega, int IdProductoBodega,
                          int IdUbicacion, int IdProductoEstado, int IdPresentacion, int IdUnidadMedida,
                          String Lote, String Lic_plate, String Serial, double Cantidad,
                          double Peso, String Estado, String Fecha_ingreso, String Fecha_vence,
                          double Uds_lic_plate, String Ubicacion_ant, int No_bulto, int IdRecepcion,
                          int IdPicking, int IdPedido, int IdDespacho, String User_agr,
                          String Fec_agr, String User_mod, String Fec_mod, String Host,
                          int anada, String Fecha_manufactura, String Atributo_Variante_1, boolean Control_Ultimo_Lote,
                          String Ultimo_Lote, Boolean Pallet_no_estandar, String Codigo_Producto, String No_Pedido) {

        this.IdStockRes=IdStockRes;
        this.IdTransaccion=IdTransaccion;
        this.Indicador=Indicador;
        this.IdPedidoDet=IdPedidoDet;
        this.IdBodega=IdBodega;
        this.IdStock=IdStock;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProductoBodega=IdProductoBodega;
        this.IdUbicacion=IdUbicacion;
        this.IdProductoEstado=IdProductoEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedida=IdUnidadMedida;
        this.Lote=Lote;
        this.Lic_plate=Lic_plate;
        this.Serial=Serial;
        this.Cantidad=Cantidad;
        this.Peso=Peso;
        this.Estado=Estado;
        this.Fecha_ingreso=Fecha_ingreso;
        this.Fecha_vence=Fecha_vence;
        this.Uds_lic_plate=Uds_lic_plate;
        this.Ubicacion_ant=Ubicacion_ant;
        this.No_bulto=No_bulto;
        this.IdRecepcion=IdRecepcion;
        this.IdPicking=IdPicking;
        this.IdPedido=IdPedido;
        this.IdDespacho=IdDespacho;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Host=Host;
        this.anada = anada;
        this.Fecha_manufactura=Fecha_manufactura;
        this.Atributo_Variante_1=Atributo_Variante_1;
        this.Control_Ultimo_Lote=Control_Ultimo_Lote;
        this.Ultimo_Lote=Ultimo_Lote;
        this.Pallet_no_estandar=Pallet_no_estandar;
        this.Codigo_Producto=Codigo_Producto;
        this.No_Pedido=No_Pedido;
    }


    public int getIdStockRes() {
        return IdStockRes;
    }
    public void setIdStockRes(int value) {
        IdStockRes=value;
    }
    public int getIdTransaccion() {
        return IdTransaccion;
    }
    public void setIdTransaccion(int value) {
        IdTransaccion=value;
    }
    public String getIndicador() {
        return Indicador;
    }
    public void setIndicador(String value) {
        Indicador=value;
    }
    public int getIdPedidoDet() {
        return IdPedidoDet;
    }
    public void setIdPedidoDet(int value) {
        IdPedidoDet=value;
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
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
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
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public String getFecha_ingreso() {
        return Fecha_ingreso;
    }
    public void setFecha_ingreso(String value) {
        Fecha_ingreso=value;
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
    public String getUbicacion_ant() {
        return Ubicacion_ant;
    }
    public void setUbicacion_ant(String value) {
        Ubicacion_ant=value;
    }
    public int getNo_bulto() {
        return No_bulto;
    }
    public void setNo_bulto(int value) {
        No_bulto=value;
    }
    public int getIdRecepcion() {
        return IdRecepcion;
    }
    public void setIdRecepcion(int value) {
        IdRecepcion=value;
    }
    public int getIdPicking() {
        return IdPicking;
    }
    public void setIdPicking(int value) {
        IdPicking=value;
    }
    public int getIdPedido() {
        return IdPedido;
    }
    public void setIdPedido(int value) {
        IdPedido=value;
    }
    public int getIdDespacho() {
        return IdDespacho;
    }
    public void setIdDespacho(int value) {
        IdDespacho=value;
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
    public String getHost() {
        return Host;
    }
    public void setHost(String value) {
        Host=value;
    }
    public int getañada() {
        return anada;
    }
    public void setañada(int value) {
        anada =value;
    }
    public String getFecha_manufactura() {
        return Fecha_manufactura;
    }
    public void setFecha_manufactura(String value) {
        Fecha_manufactura=value;
    }
    public String getAtributo_Variante_1() {
        return Atributo_Variante_1;
    }
    public void setAtributo_Variante_1(String value) {
        Atributo_Variante_1=value;
    }
    public boolean getControl_Ultimo_Lote() {
        return Control_Ultimo_Lote;
    }
    public void setControl_Ultimo_Lote(boolean value) {
        Control_Ultimo_Lote=value;
    }
    public String getUltimo_Lote() {
        return Ultimo_Lote;
    }
    public void setUltimo_Lote(String value) {
        Ultimo_Lote=value;
    }
    public boolean getPallet_No_Estandar() {
        return Pallet_no_estandar;
    }
    public void setPallet_No_Estandar(boolean value) {
        Pallet_no_estandar=value;
    }
    public String getCodigo_Producto() {
        return Codigo_Producto;
    }
    public void setCodigo_Producto(String value) {
        Codigo_Producto=value;
    }
    public String getNo_Pedido() {
        return No_Pedido;
    }
    public void setNo_Pedido(String value) {
        No_Pedido=value;
    }
}


