package com.dts.classes.Transacciones.Stock.Revision;

import org.simpleframework.xml.Element;

public class clsBeRevision {

    @Element(required=false) public String Producto;
    @Element(required=false) public String Presentacion;
    @Element(required=false) public String Estado;
    @Element(required=false) public String Ubicacion;
    @Element(required=false) public double Minimo;
    @Element(required=false) public double Maximo;
    @Element(required=false) public double Disponible;
    @Element(required=false) public int IdPropietarioBodega;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdProductoEstado;
    @Element(required=false) public int IdUnidadMedida;
    @Element(required=false) public int IdUbicacion;
    @Element(required=false) public int IdPropietario;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public double Factor;


    public clsBeRevision() {
    }

    public clsBeRevision(String Producto,String Presentacion,String Estado,String Ubicacion,
                    double Minimo,double Maximo,double Disponible,int IdPropietarioBodega,
                    int IdProductoBodega,int IdPresentacion,int IdProductoEstado,int IdUnidadMedida,
                    int IdUbicacion,int IdPropietario,int IdBodega,double Factor
    ) {

        this.Producto=Producto;
        this.Presentacion=Presentacion;
        this.Estado=Estado;
        this.Ubicacion=Ubicacion;
        this.Minimo=Minimo;
        this.Maximo=Maximo;
        this.Disponible=Disponible;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProductoBodega=IdProductoBodega;
        this.IdPresentacion=IdPresentacion;
        this.IdProductoEstado=IdProductoEstado;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdUbicacion=IdUbicacion;
        this.IdPropietario=IdPropietario;
        this.IdBodega=IdBodega;
        this.Factor=Factor;

    }


    public String getProducto() {
        return Producto;
    }
    public void setProducto(String value) {
        Producto=value;
    }
    public String getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(String value) {
        Presentacion=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }
    public double getMinimo() {
        return Minimo;
    }
    public void setMinimo(double value) {
        Minimo=value;
    }
    public double getMaximo() {
        return Maximo;
    }
    public void setMaximo(double value) {
        Maximo=value;
    }
    public double getDisponible() {
        return Disponible;
    }
    public void setDisponible(double value) {
        Disponible=value;
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
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdProductoEstado() {
        return IdProductoEstado;
    }
    public void setIdProductoEstado(int value) {
        IdProductoEstado=value;
    }
    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public double getFactor() {
        return Factor;
    }
    public void setFactor(double value) {
        Factor=value;
    }

}


