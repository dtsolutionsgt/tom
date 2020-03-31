package com.dts.classes.Mantenimientos.Empresa;

import org.simpleframework.xml.Element;

public class clsBeEmpresaBase
{
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public String Nombre="";

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

    public clsBeEmpresaBase()
    {
        //Constructor...
    }
}
