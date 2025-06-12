package com.dts.classes.Mantenimientos.Color;

import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;

import org.simpleframework.xml.Element;

public class clsBeColor {
    @Element(required = false) public int IdColor = 0;
    @Element(required = false) public String Nombre = "";
    @Element(required = false) public String CodigoHex = "";
    @Element(required = false) public int IdPropietario = 0;
    @Element(required = false) public String Fec_agr = "1900-01-01T00:00:00";
    @Element(required = false) public String User_agr = "";
    @Element(required = false) public String Fec_mod = "1900-01-01T00:00:00";
    @Element(required = false) public String User_mod = "";
    @Element(required = false) public boolean Activo = false;
    @Element(required = false) public boolean IsNew = false;
    @Element(required = false) public String Codigo = "";
    @Element(required = false) public clsBePropietarios Propietario = new clsBePropietarios();

    public clsBeColor() {}

    public clsBeColor(int IdColor, String Nombre, String CodigoHex, int IdPropietario, String Fec_agr, String User_agr,
                      String Fec_mod, String User_mod, boolean Activo, boolean IsNew, String Codigo,
                      clsBePropietarios Propietario) {

        this.IdColor = IdColor;
        this.Nombre = Nombre;
        this.CodigoHex = CodigoHex;
        this.IdPropietario = IdPropietario;
        this.Fec_agr = Fec_agr;
        this.User_agr = User_agr;
        this.Fec_mod = Fec_mod;
        this.User_mod = User_mod;
        this.Activo = Activo;
        this.IsNew = IsNew;
        this.Codigo = Codigo;
        this.Propietario = Propietario;
    }

    // Getters and setters
    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int idColor) {
        this.IdColor = idColor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoHex() {
        return CodigoHex;
    }

    public void setCodigoHex(String CodigoHex) {
        this.CodigoHex = CodigoHex;
    }

    public int getIdPropietario() {
        return IdPropietario;
    }

    public void setIdPropietario(int IdPropietario) {
        this.IdPropietario = IdPropietario;
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

    public boolean isIsNew() {
        return IsNew;
    }

    public void setIsNew(boolean IsNew) {
        this.IsNew = IsNew;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public clsBePropietarios getPropietario() {
        return Propietario;
    }

    public void setPropietario(clsBePropietarios Propietario) {
        this.Propietario = Propietario;
    }
}
