package com.dts.classes.Mantenimientos.Bodega;

import org.simpleframework.xml.Element;

public class clsBeBodegaBase
{
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdPais=0;
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Nombre="";
    @Element(required=false) public boolean bloquear_lp_hh= false;

    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
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

    public boolean getbloquear_lp_hh() {
        return bloquear_lp_hh;
    }
    public void setbloquear_lp_hh(boolean value) {
        bloquear_lp_hh=value;
    }

}
