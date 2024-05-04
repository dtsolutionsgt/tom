package com.dts.classes.Mantenimientos.Propietario.Propietario;


import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresa;

import org.simpleframework.xml.Element;

public class clsBePropietarios {

    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public int IdTipoActualizacionCosto=0;
    @Element(required=false) public String Contacto="";
    @Element(required=false) public String Nombre_comercial="";
    @Element(required=false) public String Imagen="";
    @Element(required=false) public String Telefono="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public String Email="";
    @Element(required=false) public boolean Actualiza_costo_oc=false;
    @Element(required=false) public int Color=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public boolean Sistema=false;
    @Element(required=false) public String NIT="";
    @Element(required=false) public String codigo_acceso="";
    @Element(required=false) public String clave_acceso="";
    @Element(required=false) public clsBeEmpresa Empresa=new clsBeEmpresa();
    @Element(required=false) public boolean Es_Consolidador = false;


    public clsBePropietarios() {
    }

    public clsBePropietarios(int IdPropietario,int IdEmpresa,int IdTipoActualizacionCosto,String Contacto,
                             String Nombre_comercial,String Imagen,String Telefono,String Direccion,
                             boolean Activo,String User_agr,String Fec_agr,String User_mod,
                             String Fec_mod,String Email,boolean Actualiza_costo_oc,int Color,
                             String Codigo,boolean Sistema,String NIT,String codigo_acceso,
                             String clave_acceso,clsBeEmpresa Empresa, boolean Es_Consolidador) {

        this.IdPropietario=IdPropietario;
        this.IdEmpresa=IdEmpresa;
        this.IdTipoActualizacionCosto=IdTipoActualizacionCosto;
        this.Contacto=Contacto;
        this.Nombre_comercial=Nombre_comercial;
        this.Imagen=Imagen;
        this.Telefono=Telefono;
        this.Direccion=Direccion;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Email=Email;
        this.Actualiza_costo_oc=Actualiza_costo_oc;
        this.Color=Color;
        this.Codigo=Codigo;
        this.Sistema=Sistema;
        this.NIT = NIT;
        this.Empresa=Empresa;
        this.codigo_acceso=codigo_acceso;
        this.clave_acceso=clave_acceso;
        this.Es_Consolidador=Es_Consolidador;
    }


    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public int getIdTipoActualizacionCosto() {
        return IdTipoActualizacionCosto;
    }
    public void setIdTipoActualizacionCosto(int value) {
        IdTipoActualizacionCosto=value;
    }
    public String getContacto() {
        return Contacto;
    }
    public void setContacto(String value) {
        Contacto=value;
    }
    public String getNombre_comercial() {
        return Nombre_comercial;
    }
    public void setNombre_comercial(String value) {
        Nombre_comercial=value;
    }
    public String getImagen() {
        return Imagen;
    }
    public void setImagen(String value) {
        Imagen=value;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String value) {
        Telefono=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
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
    public String getEmail() {
        return Email;
    }
    public void setEmail(String value) {
        Email=value;
    }
    public boolean getActualiza_costo_oc() {
        return Actualiza_costo_oc;
    }
    public void setActualiza_costo_oc(boolean value) {
        Actualiza_costo_oc=value;
    }
    public int getColor() {
        return Color;
    }
    public void setColor(int value) {
        Color=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public String getNIT() {
        return NIT;
    }
    public void setNIT(String value) {
        NIT=value;
    }
    public clsBeEmpresa getEmpresa() {
        return Empresa;
    }
    public void setEmpresa(clsBeEmpresa value) {
        Empresa=value;
    }
    public String getCodigo_acceso() {
        return codigo_acceso;
    }
    public void setCodigo_acceso(String value) {
        codigo_acceso=value;
    }
    public String getClave_acceso() {
        return clave_acceso;
    }
    public void setClave_acceso(String value) {
        clave_acceso=value;
    }
    public boolean getEs_Consolidador() {
        return Es_Consolidador;
    }
    public void setEs_Consolidador(boolean value) {
        Es_Consolidador=value;
    }
}

