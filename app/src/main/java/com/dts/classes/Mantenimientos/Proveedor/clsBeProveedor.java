package com.dts.classes.Mantenimientos.Proveedor;


import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;
import com.dts.classes.Mantenimientos.Empresa.clsBeEmpresa;

import org.simpleframework.xml.Element;

public class clsBeProveedor {

    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public clsBeEmpresa Empresa=new clsBeEmpresa();
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public int IdProveedor=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String Telefono="";
    @Element(required=false) public String Nit="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public String Email="";
    @Element(required=false) public String Contacto="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Muestra_precio=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Actualiza_costo_oc=false;
    @Element(required=false) public int IdUbicacionVirtual=0;
    @Element(required=false) public boolean Es_Bodega_Recepcion=false;
    @Element(required=false) public boolean Es_Bodega_Traslado=false;
    @Element(required=false) public String Referencia="";
    @Element(required=false) public boolean Sistema=false;
    @Element(required=false) public int IdConfiguracionBarraPallet=0;


    public clsBeProveedor() {
    }

    public clsBeProveedor(int IdEmpresa,clsBeEmpresa Empresa,int IdPropietario,clsBePropietarios Propietario,
                          int IdProveedor,String Codigo,String Nombre,String Telefono,
                          String Nit,String Direccion,String Email,String Contacto,
                          boolean Activo,boolean Muestra_precio,String User_agr,String Fec_agr,
                          String User_mod,String Fec_mod,boolean Actualiza_costo_oc,int IdUbicacionVirtual,
                          boolean Es_Bodega_Recepcion,boolean Es_Bodega_Traslado,String Referencia,boolean Sistema,
                          int IdConfiguracionBarraPallet) {

        this.IdEmpresa=IdEmpresa;
        this.Empresa=Empresa;
        this.IdPropietario=IdPropietario;
        this.Propietario=Propietario;
        this.IdProveedor=IdProveedor;
        this.Codigo=Codigo;
        this.Nombre=Nombre;
        this.Telefono=Telefono;
        this.Nit=Nit;
        this.Direccion=Direccion;
        this.Email=Email;
        this.Contacto=Contacto;
        this.Activo=Activo;
        this.Muestra_precio=Muestra_precio;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Actualiza_costo_oc=Actualiza_costo_oc;
        this.IdUbicacionVirtual=IdUbicacionVirtual;
        this.Es_Bodega_Recepcion=Es_Bodega_Recepcion;
        this.Es_Bodega_Traslado=Es_Bodega_Traslado;
        this.Referencia=Referencia;
        this.Sistema=Sistema;
        this.IdConfiguracionBarraPallet=IdConfiguracionBarraPallet;

    }


    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public clsBeEmpresa getEmpresa() {
        return Empresa;
    }
    public void setEmpresa(clsBeEmpresa value) {
        Empresa=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public clsBePropietarios getPropietario() {
        return Propietario;
    }
    public void setPropietario(clsBePropietarios value) {
        Propietario=value;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setIdProveedor(int value) {
        IdProveedor=value;
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
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String value) {
        Telefono=value;
    }
    public String getNit() {
        return Nit;
    }
    public void setNit(String value) {
        Nit=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String value) {
        Email=value;
    }
    public String getContacto() {
        return Contacto;
    }
    public void setContacto(String value) {
        Contacto=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getMuestra_precio() {
        return Muestra_precio;
    }
    public void setMuestra_precio(boolean value) {
        Muestra_precio=value;
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
    public boolean getActualiza_costo_oc() {
        return Actualiza_costo_oc;
    }
    public void setActualiza_costo_oc(boolean value) {
        Actualiza_costo_oc=value;
    }
    public int getIdUbicacionVirtual() {
        return IdUbicacionVirtual;
    }
    public void setIdUbicacionVirtual(int value) {
        IdUbicacionVirtual=value;
    }
    public boolean getEs_Bodega_Recepcion() {
        return Es_Bodega_Recepcion;
    }
    public void setEs_Bodega_Recepcion(boolean value) {
        Es_Bodega_Recepcion=value;
    }
    public boolean getEs_Bodega_Traslado() {
        return Es_Bodega_Traslado;
    }
    public void setEs_Bodega_Traslado(boolean value) {
        Es_Bodega_Traslado=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public int getIdConfiguracionBarraPallet() {
        return IdConfiguracionBarraPallet;
    }
    public void setIdConfiguracionBarraPallet(int value) {
        IdConfiguracionBarraPallet=value;
    }

}

