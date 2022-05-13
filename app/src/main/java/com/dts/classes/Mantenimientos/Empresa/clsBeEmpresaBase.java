package com.dts.classes.Mantenimientos.Empresa;

import android.graphics.Bitmap;

import org.simpleframework.xml.Element;

public class clsBeEmpresaBase
{
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String Imagen;
    @Element(required=false) public boolean buscar_actualizacion_hh = false;

    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getImagen() {
        return Imagen;
    }
    public void setImagen(String value) {
        Imagen=value;
    }

    public boolean getbuscar_actualizacion_hh() {
        return buscar_actualizacion_hh;
    }
    public void setbuscar_actualizacion_hh(boolean value) {
        buscar_actualizacion_hh=value;
    }




    public clsBeEmpresaBase()
    {
        //Constructor...
    }
}
