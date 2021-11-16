package com.dts.classes.Transacciones.Inventario.Inv_Stock_Prod;


import org.simpleframework.xml.Element;

public class clsBeTrans_inv_stock_prod {

    @Element(required=false) public int Idinventario=0;
    @Element(required=false) public int Idinvstockprod=0;
    @Element(required=false) public int IdProducto=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public double Cant=0;
    @Element(required=false) public double Peso=0;
    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Fecha_vence="1900-01-01T00:00:01";
    @Element(required=false) public String Codigo="";
    @Element(required=false) public int idbodega=0;
    @Element(required=false) public int idubicacion=0;


    public clsBeTrans_inv_stock_prod() {
    }

    public clsBeTrans_inv_stock_prod(int Idinventario,int Idinvstockprod,int IdProducto,int IdPresentacion,
                                     double Cant,double Peso,int IdUnidadMedida,String Lote,
                                     String Fecha_vence,String Codigo, int idbodega, int idubicacion) {

        this.Idinventario=Idinventario;
        this.Idinvstockprod=Idinvstockprod;
        this.IdProducto=IdProducto;
        this.IdPresentacion=IdPresentacion;
        this.Cant=Cant;
        this.Peso=Peso;
        this.IdUnidadMedida=IdUnidadMedida;
        this.Lote=Lote;
        this.Fecha_vence=Fecha_vence;
        this.Codigo=Codigo;
        this.idbodega=idbodega;
        this.idubicacion=idubicacion;

    }


    public int getIdinventario() {
        return Idinventario;
    }
    public void setIdinventario(int value) {
        Idinventario=value;
    }
    public int getIdinvstockprod() {
        return Idinvstockprod;
    }
    public void setIdinvstockprod(int value) {
        Idinvstockprod=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public double getCant() {
        return Cant;
    }
    public void setCant(double value) {
        Cant=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
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
    public String getFecha_vence() {
        return Fecha_vence;
    }
    public void setFecha_vence(String value) {
        Fecha_vence=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public int getIdBodega() {
        return idbodega;
    }
    public void setIdBodega(int value) {
        idbodega=value;
    }
    public int getIdubicacion() {
        return idubicacion;
    }
    public void setIdubicacion(int value) {
        idubicacion=value;
    }

}

