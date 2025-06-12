package com.dts.classes.Mantenimientos.Producto.Producto_talla_color;

import org.simpleframework.xml.Element;

public class clsBeProducto_talla_color {
    @Element(required = false) public int IdProductoTallaColor = 0;
    @Element(required = false) public int IdProducto = 0;
    @Element(required = false) public int IdTalla = 0;
    @Element(required = false) public int IdColor = 0;
    @Element(required = false) public String CodigoSKU = "";
    @Element(required = false) public int IdCampana = 0;
    @Element(required = false) public String Fec_agr = "1900-01-01T00:00:00";
    @Element(required = false) public String User_agr = "";
    @Element(required = false) public String Fec_mod = "1900-01-01T00:00:00";
    @Element(required = false) public String User_mod = "";
    @Element(required = false) public boolean Activo = false;

    public clsBeProducto_talla_color() {}

    public clsBeProducto_talla_color(int IdProductoTallaColor, int IdProducto, int IdTalla, int IdColor,
                                   String CodigoSKU, int IdCampana, String Fec_agr, String User_agr,
                                   String Fec_mod, String User_mod, boolean Activo) {
        this.IdProductoTallaColor = IdProductoTallaColor;
        this.IdProducto = IdProducto;
        this.IdTalla = IdTalla;
        this.IdColor = IdColor;
        this.CodigoSKU = CodigoSKU;
        this.IdCampana = IdCampana;
        this.Fec_agr = Fec_agr;
        this.User_agr = User_agr;
        this.Fec_mod = Fec_mod;
        this.User_mod = User_mod;
        this.Activo = Activo;
    }

    public int getIdProductoTallaColor() {
        return IdProductoTallaColor;
    }

    public void setIdProductoTallaColor(int IdProductoTallaColor) {
        this.IdProductoTallaColor = IdProductoTallaColor;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int IdProducto) {
        this.IdProducto = IdProducto;
    }

    public int getIdTalla() {
        return IdTalla;
    }

    public void setIdTalla(int IdTalla) {
        this.IdTalla = IdTalla;
    }

    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int IdColor) {
        this.IdColor = IdColor;
    }

    public String getCodigoSKU() {
        return CodigoSKU;
    }

    public void setCodigoSKU(String CodigoSKU) {
        this.CodigoSKU = CodigoSKU;
    }

    public int getIdCampana() {
        return IdCampana;
    }

    public void setIdCampana(int IdCampana) {
        this.IdCampana = IdCampana;
    }

    public String getFec_agr() {
        return Fec_agr;
    }

    public void setFec_agr(String Fec_agr) {
        this.Fec_agr = Fec_agr;
    }

    public String getUser_agr() {
        return User_agr;
    }

    public void setUser_agr(String User_agr) {
        this.User_agr = User_agr;
    }

    public String getFec_mod() {
        return Fec_mod;
    }

    public void setFec_mod(String Fec_mod) {
        this.Fec_mod = Fec_mod;
    }

    public String getUser_mod() {
        return User_mod;
    }

    public void setUser_mod(String User_mod) {
        this.User_mod = User_mod;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }
}
