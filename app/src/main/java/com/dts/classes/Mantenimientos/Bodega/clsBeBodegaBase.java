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
    @Element(required=false) public boolean captura_estiba_ingreso= false;
    @Element(required=false) public boolean captura_pallet_no_estandar= false;
    @Element(required=false) public boolean priorizar_ubicrec_sobre_ubicest =false;
    @Element(required=false) public String ubic_merma="";

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

    public boolean getCaptura_pallet_no_estandar() {
        return captura_pallet_no_estandar;
    }
    public void setCaptura_pallet_no_estandar(boolean value) {
        captura_pallet_no_estandar=value;
    }

    public boolean getCaptura_estiba_ingreso() {
        return captura_estiba_ingreso;
    }
    public void setCaptura_estiba_ingreso(boolean value) {
        captura_estiba_ingreso=value;
    }

    public boolean getpriorizar_ubicrec_sobre_ubicest() {return priorizar_ubicrec_sobre_ubicest;}
    public void setpriorizar_ubicrec_sobre_ubicest(boolean value) {priorizar_ubicrec_sobre_ubicest=value;}

    public String getUbic_merma() {
        return ubic_merma;
    }
    public void setUbic_merma(String value) {
        ubic_merma=value;
    }
}
