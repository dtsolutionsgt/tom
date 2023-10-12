package com.dts.classes.Transacciones.Movimiento.clsBeVW_Movimientos;

import org.simpleframework.xml.Element;

public class clsBeVW_Movimientos {

    @Element(required=false) public pTipoTarea TipoTarea;
    @Element(required=false) public String TTarea;
    @Element(required=false) public int IdProducto;
    @Element(required=false) public int IdRecepcion;
    @Element(required=false) public int IdRecepcionOC;
    @Element(required=false) public int IdBodegaOrigen;
    @Element(required=false) public int IdUbicacionOrigen;
    @Element(required=false) public int IdUbicacionDestino;
    @Element(required=false) public String Codigo;
    @Element(required=false) public String CodigoBarra;
    @Element(required=false) public String Producto;
    @Element(required=false) public String Propietario;
    @Element(required=false) public String Presentacion;
    @Element(required=false) public String EstadoOrigen;
    @Element(required=false) public String EstadoDestino;
    @Element(required=false) public String Motivo;
    @Element(required=false) public String UMBas;
    @Element(required=false) public String UbicOrigen;
    @Element(required=false) public String UbicDestino;
    @Element(required=false) public double Cantidad;
    @Element(required=false) public double Peso;
    @Element(required=false) public String Lote;
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha;
    @Element(required=false) public int IdTipoTarea;
    @Element(required=false) public String No_Doc_Ingreso;
    @Element(required=false) public String No_Ref_Ingreso;
    @Element(required=false) public String No_Doc_Salida;
    @Element(required=false) public String No_Ref_Salida;
    @Element(required=false) public int IdUnidadMedida;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdEstadoOrigen;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public double Ingresos;
    @Element(required=false) public double Salidas;
    @Element(required=false) public double Ajustes_Positivos;
    @Element(required=false) public double Ajustes_Negativos;
    @Element(required=false) public String Lic_Plate;
    @Element(required=false) public double Cantidad_Presentacion;
    @Element(required=false) public double Factor;

    public enum pTipoTarea
    {
        RECE ,UBIC ,CEST,TRAS,DESP,INVE,AJUS,PIK,DEVP,PICK,VERI,PACK,
        AJCANTP,AJPESO,AJVENC,AJLOTE,AJCANTN,AJCANTNI, AJCANTPI
    }

    public clsBeVW_Movimientos() {
    }

    public clsBeVW_Movimientos(pTipoTarea TipoTarea,String TTarea,int IdProducto,int IdRecepcion,
                               int IdRecepcionOC,int IdBodegaOrigen,int IdUbicacionOrigen,int IdUbicacionDestino,
                               String Codigo,String CodigoBarra,String Producto,String Propietario,
                               String Presentacion,String EstadoOrigen,String EstadoDestino,String Motivo,
                               String UMBas,String UbicOrigen,String UbicDestino,double Cantidad,
                               double Peso,String Lote,String Fecha_Vence,String Fecha,
                               int IdTipoTarea,String No_Doc_Ingreso,String No_Ref_Ingreso,String No_Doc_Salida,
                               String No_Ref_Salida,int IdUnidadMedida,int IdPresentacion,int IdEstadoOrigen,
                               int IdProductoBodega,double Ingresos,double Salidas,double Ajustes_Positivos,
                               double Ajustes_Negativos,String Lic_Plate,double Cantidad_Presentacion,double Factor
    ) {

        this.TipoTarea=TipoTarea;
        this.TTarea=TTarea;
        this.IdProducto=IdProducto;
        this.IdRecepcion=IdRecepcion;
        this.IdRecepcionOC=IdRecepcionOC;
        this.IdBodegaOrigen=IdBodegaOrigen;
        this.IdUbicacionOrigen=IdUbicacionOrigen;
        this.IdUbicacionDestino=IdUbicacionDestino;
        this.Codigo=Codigo;
        this.CodigoBarra=CodigoBarra;
        this.Producto=Producto;
        this.Propietario=Propietario;
        this.Presentacion=Presentacion;
        this.EstadoOrigen=EstadoOrigen;
        this.EstadoDestino=EstadoDestino;
        this.Motivo=Motivo;
        this.UMBas=UMBas;
        this.UbicOrigen=UbicOrigen;
        this.UbicDestino=UbicDestino;
        this.Cantidad=Cantidad;
        this.Peso=Peso;
        this.Lote=Lote;
        this.Fecha_Vence=Fecha_Vence;
        this.Fecha=Fecha;
        this.IdTipoTarea=IdTipoTarea;
        this.No_Doc_Ingreso=No_Doc_Ingreso;
        this.No_Ref_Ingreso=No_Ref_Ingreso;
        this.No_Doc_Salida=No_Doc_Salida;
        this.No_Ref_Salida=No_Ref_Salida;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdPresentacion=IdPresentacion;
        this.IdEstadoOrigen=IdEstadoOrigen;
        this.IdProductoBodega=IdProductoBodega;
        this.Ingresos=Ingresos;
        this.Salidas=Salidas;
        this.Ajustes_Positivos=Ajustes_Positivos;
        this.Ajustes_Negativos=Ajustes_Negativos;
        this.Lic_Plate=Lic_Plate;
        this.Cantidad_Presentacion=Cantidad_Presentacion;
        this.Factor=Factor;

    }


    public pTipoTarea getTipoTarea() {
        return TipoTarea;
    }
    public void setTipoTarea(pTipoTarea value) {
        TipoTarea=value;
    }
    public String getTTarea() {
        return TTarea;
    }
    public void setTTarea(String value) {
        TTarea=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }
    public int getIdRecepcion() {
        return IdRecepcion;
    }
    public void setIdRecepcion(int value) {
        IdRecepcion=value;
    }
    public int getIdRecepcionOC() {
        return IdRecepcionOC;
    }
    public void setIdRecepcionOC(int value) {
        IdRecepcionOC=value;
    }
    public int getIdBodegaOrigen() {
        return IdBodegaOrigen;
    }
    public void setIdBodegaOrigen(int value) {
        IdBodegaOrigen=value;
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
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getCodigoBarra() {
        return CodigoBarra;
    }
    public void setCodigoBarra(String value) {
        CodigoBarra=value;
    }
    public String getProducto() {
        return Producto;
    }
    public void setProducto(String value) {
        Producto=value;
    }
    public String getPropietario() {
        return Propietario;
    }
    public void setPropietario(String value) {
        Propietario=value;
    }
    public String getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(String value) {
        Presentacion=value;
    }
    public String getEstadoOrigen() {
        return EstadoOrigen;
    }
    public void setEstadoOrigen(String value) {
        EstadoOrigen=value;
    }
    public String getEstadoDestino() {
        return EstadoDestino;
    }
    public void setEstadoDestino(String value) {
        EstadoDestino=value;
    }
    public String getMotivo() {
        return Motivo;
    }
    public void setMotivo(String value) {
        Motivo=value;
    }
    public String getUMBas() {
        return UMBas;
    }
    public void setUMBas(String value) {
        UMBas=value;
    }
    public String getUbicOrigen() {
        return UbicOrigen;
    }
    public void setUbicOrigen(String value) {
        UbicOrigen=value;
    }
    public String getUbicDestino() {
        return UbicDestino;
    }
    public void setUbicDestino(String value) {
        UbicDestino=value;
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
    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String value) {
        Fecha=value;
    }
    public int getIdTipoTarea() {
        return IdTipoTarea;
    }
    public void setIdTipoTarea(int value) {
        IdTipoTarea=value;
    }
    public String getNo_Doc_Ingreso() {
        return No_Doc_Ingreso;
    }
    public void setNo_Doc_Ingreso(String value) {
        No_Doc_Ingreso=value;
    }
    public String getNo_Ref_Ingreso() {
        return No_Ref_Ingreso;
    }
    public void setNo_Ref_Ingreso(String value) {
        No_Ref_Ingreso=value;
    }
    public String getNo_Doc_Salida() {
        return No_Doc_Salida;
    }
    public void setNo_Doc_Salida(String value) {
        No_Doc_Salida=value;
    }
    public String getNo_Ref_Salida() {
        return No_Ref_Salida;
    }
    public void setNo_Ref_Salida(String value) {
        No_Ref_Salida=value;
    }
    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdEstadoOrigen() {
        return IdEstadoOrigen;
    }
    public void setIdEstadoOrigen(int value) {
        IdEstadoOrigen=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public double getIngresos() {
        return Ingresos;
    }
    public void setIngresos(double value) {
        Ingresos=value;
    }
    public double getSalidas() {
        return Salidas;
    }
    public void setSalidas(double value) {
        Salidas=value;
    }
    public double getAjustes_Positivos() {
        return Ajustes_Positivos;
    }
    public void setAjustes_Positivos(double value) {
        Ajustes_Positivos=value;
    }
    public double getAjustes_Negativos() {
        return Ajustes_Negativos;
    }
    public void setAjustes_Negativos(double value) {
        Ajustes_Negativos=value;
    }
    public String getLic_Plate() {
        return Lic_Plate;
    }
    public void setLic_Plate(String value) {
        Lic_Plate=value;
    }
    public double getCantidad_Presentacion() {
        return Cantidad_Presentacion;
    }
    public void setCantidad_Presentacion(double value) {
        Cantidad_Presentacion=value;
    }
    public double getFactor() {
        return Factor;
    }
    public void setFactor(double value) {
        Factor=value;
    }

}

