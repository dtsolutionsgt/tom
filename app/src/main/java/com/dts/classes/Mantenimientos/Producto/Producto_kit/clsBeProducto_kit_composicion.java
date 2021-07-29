package com.dts.classes.Mantenimientos.Producto.Producto_kit;


import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;
import com.dts.classes.Transacciones.Stock.Stock.clsBeStock;

import org.simpleframework.xml.Element;

public class clsBeProducto_kit_composicion {

    @Element(required=false) public int IdProductoKitComposicion =0;
    @Element(required=false) public int IdProductoPadre =0;
    @Element(required=false) public int IdProductoHijo =0;
    @Element(required=false) public int IdUnidadMedidaBasicaPadre =0;
    @Element(required=false) public int IdUnidadMedidaBasicaHijo =0;
    @Element(required=false) public double Cantidad =0;
    @Element(required=false) public double CantidadDisp =0;
    @Element(required=false) public double CantidadRes =0;
    @Element(required=false) public int No_Linea =0;
    @Element(required=false) public int IdPedidoDetPadre =0;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public int IdBodega =0;
    @Element(required=false) public clsBeProducto Propietario=new clsBeProducto();
    @Element(required=false) public String Nombre="";
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeStock BeStock=new clsBeStock();


    public clsBeProducto_kit_composicion() {
    }

    public clsBeProducto_kit_composicion(int IdProductoKitComposicion, int IdProductoPadre, clsBePropietarios Propietario, String Nombre,
                                         boolean Activo, String User_agr, String Fec_agr, String User_mod,
                                         String Fec_mod, boolean IsNew) {

        this.IdProductoKitComposicion = IdProductoKitComposicion;
        this.IdProductoPadre = IdProductoPadre;
        this.Nombre=Nombre;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.IsNew=IsNew;

    }


    public int getIdProductoKitComposicion() {
        return IdProductoKitComposicion;
    }
    public void setIdProductoKitComposicion(int value) {
        IdProductoKitComposicion =value;
    }
    public int getIdProductoPadre() {
        return IdProductoPadre;
    }
    public void setIdProductoPadre(int value) {
        IdProductoPadre =value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
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
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

    public int getIdProductoHijo() {
        return IdProductoHijo;
    }
    public void setIdProductoHijo(int value) {
        IdProductoHijo =value;
    }

    public int getIdUnidadMedidaBasicaPadre() {
        return IdUnidadMedidaBasicaPadre;
    }
    public void setIdUnidadMedidaBasicaPadre(int value) {
        IdUnidadMedidaBasicaPadre =value;
    }

    public int getIdUnidadMedidaBasicaHijo() {
        return IdUnidadMedidaBasicaHijo;
    }
    public void setIdUnidadMedidaBasicaHijo(int value) {
        IdUnidadMedidaBasicaHijo =value;
    }

    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad =value;
    }

    public double getCantidadDisp() {
        return CantidadDisp;
    }
    public void setCantidadDisp(double value) {
        CantidadDisp=value;
    }

    public double getCantidadRes() {
        return CantidadRes;
    }
    public void setCantidadRes(double value) {
        CantidadRes=value;
    }

    public int getNo_Linea() {
        return No_Linea;
    }
    public void setNo_Linea(int value) {
        No_Linea =value;
    }

    public int getIdPedidoDetPadre() {
        return IdPedidoDetPadre;
    }
    public void setIdPedidoDetPadre(int value) {
        IdPedidoDetPadre =value;
    }

    //setIdProductoPadre

}

