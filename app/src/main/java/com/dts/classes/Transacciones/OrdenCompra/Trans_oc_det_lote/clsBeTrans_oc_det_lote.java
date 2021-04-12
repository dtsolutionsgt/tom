package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote;


import org.simpleframework.xml.Element;

public class clsBeTrans_oc_det_lote {

    @Element(required=false) public int IdOrdenCompraEnc;
    @Element(required=false) public int IdOrdenCompraDet;
    @Element(required=false) public int IdOrdenCompraDetLote;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int No_linea;
    @Element(required=false) public String Codigo_producto;
    @Element(required=false) public double Cantidad;
    @Element(required=false) public double Cantidad_recibida;
    @Element(required=false) public String Lote;
    @Element(required=false) public String Fecha_vence ="1900-01-01T00:00:01";
    @Element(required=false) public String Lic_Plate;
    @Element(required=false) public String Ubicacion;
    @Element(required=false) public Boolean IsNew;

    public clsBeTrans_oc_det_lote() {
    }

    public clsBeTrans_oc_det_lote(int IdOrdenCompraEnc,int IdOrdenCompraDet,int IdOrdenCompraDetLote,int IdProductoBodega,
                                  int No_linea,String Codigo_producto,double Cantidad,double Cantidad_recibida,
                                  String Lote,String Fecha_vence, String Lic_Plate, String Ubicacion) {

        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.IdOrdenCompraDet=IdOrdenCompraDet;
        this.IdOrdenCompraDetLote=IdOrdenCompraDetLote;
        this.IdProductoBodega=IdProductoBodega;
        this.No_linea=No_linea;
        this.Codigo_producto=Codigo_producto;
        this.Cantidad=Cantidad;
        this.Cantidad_recibida=Cantidad_recibida;
        this.Lote=Lote;
        this.Fecha_vence =Fecha_vence;
        this.Lic_Plate = Lic_Plate;
        this.Ubicacion = Ubicacion;
    }


    public int getIdOrdenCompraEnc() {
        return IdOrdenCompraEnc;
    }
    public void setIdOrdenCompraEnc(int value) {
        IdOrdenCompraEnc=value;
    }
    public int getIdOrdenCompraDet() {
        return IdOrdenCompraDet;
    }
    public void setIdOrdenCompraDet(int value) {
        IdOrdenCompraDet=value;
    }
    public int getIdOrdenCompraDetLote() {
        return IdOrdenCompraDetLote;
    }
    public void setIdOrdenCompraDetLote(int value) {
        IdOrdenCompraDetLote=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getNo_linea() {
        return No_linea;
    }
    public void setNo_linea(int value) {
        No_linea=value;
    }
    public String getCodigo_producto() {
        return Codigo_producto;
    }
    public void setCodigo_producto(String value) {
        Codigo_producto=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public double getCantidad_recibida() {
        return Cantidad_recibida;
    }
    public void setCantidad_recibida(double value) {
        Cantidad_recibida=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getFecha_vence() {
        return Fecha_vence;
    }
    public void setFecha_vence(String value) {
        Fecha_vence =value;
    }

    public String getLic_Plate() {
        return Lic_Plate;
    }
    public void setLic_Plate(String value) {
        Lic_Plate=value;
    }

    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }

    public Boolean getIsNew(){
        return IsNew;
    }

    public void setIsNew(boolean value){
        IsNew=value;
    }


}


