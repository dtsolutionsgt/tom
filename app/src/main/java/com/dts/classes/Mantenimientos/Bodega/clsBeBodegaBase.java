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
    @Element(required=false) public int ubic_producto_ne=0;
    @Element(required=false) public int IdProductoEstadoNE=0;
    @Element(required=false) public boolean validar_disponibilidad_ubicaicon_destino =false;
    @Element(required=false) public boolean Mostrar_Area_En_HH=false;
    @Element(required=false) public boolean confirmar_codigo_en_picking=false;

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

    public int getUbic_producto_ne() {
        return ubic_producto_ne;
    }
    public void setUbic_producto_ne(int value) {
        ubic_producto_ne=value;
    }

    public int getIdProductoEstadoNE() {
        return IdProductoEstadoNE;
    }
    public void setIdProductoEstadoNE(int value) {
        IdProductoEstadoNE=value;
    }

    public boolean getvalidar_disponibilidad_ubicaicon_destino() {
        return validar_disponibilidad_ubicaicon_destino;
    }
    public void setvalidar_disponibilidad_ubicaicon_destino(boolean value) {
        validar_disponibilidad_ubicaicon_destino=value;
    }

    public boolean getMostrar_Area_En_HH() {
        return Mostrar_Area_En_HH;
    }
    public void setMostrar_Area_En_HH(boolean value) {
        Mostrar_Area_En_HH=value;
    }

    public boolean getconfirmar_codigo_en_picking() {
        return confirmar_codigo_en_picking;
    }
    public void setconfirmar_codigo_en_picking(boolean value) {
        confirmar_codigo_en_picking=value;
    }
}