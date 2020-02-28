package com.dts.classes.Mantenimientos.Barra_pallet;


import org.simpleframework.xml.Element;

public class clsBeI_nav_barras_pallet {

    @Element(required=false) public int IdPallet;
    @Element(required=false) public String Codigo;
    @Element(required=false) public String Nombre;
    @Element(required=false) public int Camas_Por_Tarima;
    @Element(required=false) public int Cajas_Por_Cama;
    @Element(required=false) public double Cantidad_Presentacion;
    @Element(required=false) public double Cantidad_UMP;
    @Element(required=false) public String UM_Producto;
    @Element(required=false) public String Lote;
    @Element(required=false) public int Lote_Numerico;
    @Element(required=false) public String Fecha_Agregado;
    @Element(required=false) public String Fecha_Ingreso;
    @Element(required=false) public String Fecha_Vence;
    @Element(required=false) public String Fecha_Produccion;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean Recibido;
    @Element(required=false) public int IdRecepcion;
    @Element(required=false) public String Bodega_Origen;
    @Element(required=false) public String Bodega_Destino;
    @Element(required=false) public String Codigo_barra;


    public clsBeI_nav_barras_pallet() {
    }

    public clsBeI_nav_barras_pallet(int IdPallet,String Codigo,String Nombre,int Camas_Por_Tarima,
                                    int Cajas_Por_Cama,double Cantidad_Presentacion,double Cantidad_UMP,String UM_Producto,
                                    String Lote,int Lote_Numerico,String Fecha_Agregado,String Fecha_Ingreso,
                                    String Fecha_Vence,String Fecha_Produccion,boolean Activo,boolean Recibido,
                                    int IdRecepcion,String Bodega_Origen,String Bodega_Destino,String Codigo_barra
    ) {

        this.IdPallet=IdPallet;
        this.Codigo=Codigo;
        this.Nombre=Nombre;
        this.Camas_Por_Tarima=Camas_Por_Tarima;
        this.Cajas_Por_Cama=Cajas_Por_Cama;
        this.Cantidad_Presentacion=Cantidad_Presentacion;
        this.Cantidad_UMP=Cantidad_UMP;
        this.UM_Producto=UM_Producto;
        this.Lote=Lote;
        this.Lote_Numerico=Lote_Numerico;
        this.Fecha_Agregado=Fecha_Agregado;
        this.Fecha_Ingreso=Fecha_Ingreso;
        this.Fecha_Vence=Fecha_Vence;
        this.Fecha_Produccion=Fecha_Produccion;
        this.Activo=Activo;
        this.Recibido=Recibido;
        this.IdRecepcion=IdRecepcion;
        this.Bodega_Origen=Bodega_Origen;
        this.Bodega_Destino=Bodega_Destino;
        this.Codigo_barra=Codigo_barra;

    }


    public int getIdPallet() {
        return IdPallet;
    }
    public void setIdPallet(int value) {
        IdPallet=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public int getCamas_Por_Tarima() {
        return Camas_Por_Tarima;
    }
    public void setCamas_Por_Tarima(int value) {
        Camas_Por_Tarima=value;
    }
    public int getCajas_Por_Cama() {
        return Cajas_Por_Cama;
    }
    public void setCajas_Por_Cama(int value) {
        Cajas_Por_Cama=value;
    }
    public double getCantidad_Presentacion() {
        return Cantidad_Presentacion;
    }
    public void setCantidad_Presentacion(double value) {
        Cantidad_Presentacion=value;
    }
    public double getCantidad_UMP() {
        return Cantidad_UMP;
    }
    public void setCantidad_UMP(double value) {
        Cantidad_UMP=value;
    }
    public String getUM_Producto() {
        return UM_Producto;
    }
    public void setUM_Producto(String value) {
        UM_Producto=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public int getLote_Numerico() {
        return Lote_Numerico;
    }
    public void setLote_Numerico(int value) {
        Lote_Numerico=value;
    }
    public String getFecha_Agregado() {
        return Fecha_Agregado;
    }
    public void setFecha_Agregado(String value) {
        Fecha_Agregado=value;
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
    public String getFecha_Produccion() {
        return Fecha_Produccion;
    }
    public void setFecha_Produccion(String value) {
        Fecha_Produccion=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getRecibido() {
        return Recibido;
    }
    public void setRecibido(boolean value) {
        Recibido=value;
    }
    public int getIdRecepcion() {
        return IdRecepcion;
    }
    public void setIdRecepcion(int value) {
        IdRecepcion=value;
    }
    public String getBodega_Origen() {
        return Bodega_Origen;
    }
    public void setBodega_Origen(String value) {
        Bodega_Origen=value;
    }
    public String getBodega_Destino() {
        return Bodega_Destino;
    }
    public void setBodega_Destino(String value) {
        Bodega_Destino=value;
    }
    public String getCodigo_barra() {
        return Codigo_barra;
    }
    public void setCodigo_barra(String value) {
        Codigo_barra=value;
    }

}

