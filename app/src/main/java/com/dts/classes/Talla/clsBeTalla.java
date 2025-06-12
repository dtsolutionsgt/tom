package com.dts.classes.Talla;

import org.simpleframework.xml.Element;

public class clsBeTalla {
    @Element(required = false) public int IdTalla = 0;
    @Element(required = false) public String Nombre = "";
    @Element(required = false) public String Descripcion = "";
    @Element(required = false) public int IdPropietario = 0;
    @Element(required = false) public String Fec_agr = "1900-01-01T00:00:00";
    @Element(required = false) public String User_agr = "";
    @Element(required = false) public String Fec_mod = "1900-01-01T00:00:00";
    @Element(required = false) public String User_mod = "";
    @Element(required = false) public boolean Activo = false;
    @Element(required = false) public String Codigo = "";
    @Element(required = false) public boolean IsNew = false;

    public clsBeTalla() {}

    public clsBeTalla(int IdTalla, String Nombre, String Descripcion, int IdPropietario,
                      String Fec_agr, String User_agr, String Fec_mod, String User_mod,
                      boolean Activo, String Codigo, boolean IsNew) {

        this.IdTalla = IdTalla;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.IdPropietario = IdPropietario;
        this.Fec_agr = Fec_agr;
        this.User_agr = User_agr;
        this.Fec_mod = Fec_mod;
        this.User_mod = User_mod;
        this.Activo = Activo;
        this.Codigo = Codigo;
        this.IsNew = IsNew;
    }

    // Getters and Setters
    public int getIdTalla() {
        return IdTalla;
    }

    public void setIdTalla(int idTalla) {
        IdTalla = idTalla;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getIdPropietario() {
        return IdPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        IdPropietario = idPropietario;
    }

    public String getFec_agr() {
        return Fec_agr;
    }

    public void setFec_agr(String fec_agr) {
        Fec_agr = fec_agr;
    }

    public String getUser_agr() {
        return User_agr;
    }

    public void setUser_agr(String user_agr) {
        User_agr = user_agr;
    }

    public String getFec_mod() {
        return Fec_mod;
    }

    public void setFec_mod(String fec_mod) {
        Fec_mod = fec_mod;
    }

    public String getUser_mod() {
        return User_mod;
    }

    public void setUser_mod(String user_mod) {
        User_mod = user_mod;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public boolean isIsNew() {
        return IsNew;
    }

    public void setIsNew(boolean isNew) {
        IsNew = isNew;
    }
}
