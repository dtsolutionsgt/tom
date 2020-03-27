package com.dts.classes.Mantenimientos.Bodega;


import com.dts.classes.Mantenimientos.Empresa.clsBeFont_Enc;

import org.simpleframework.xml.Element;

public class clsBeBodega_tramo {

    @Element(required=false) public int IdTramo=0;
    @Element(required=false) public int IdSector=0;
    @Element(required=false) public int IdArea=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public boolean Sistema=false;
    @Element(required=false) public String Descripcion="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public double Alto=0;
    @Element(required=false) public double Largo=0;
    @Element(required=false) public double Ancho=0;
    @Element(required=false) public double Margen_izquierdo=0;
    @Element(required=false) public double Margen_derecho=0;
    @Element(required=false) public double Margen_superior=0;
    @Element(required=false) public double Margen_inferior=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public int Indice_x=0;
    @Element(required=false) public int Orientacion=0;
    @Element(required=false) public int IdTipoProductoDefault=0;
    @Element(required=false) public int IdFontEnc=0;
    @Element(required=false) public int IdTipoRack=0;
    @Element(required=false) public boolean Es_Rack=false;
    @Element(required=false) public boolean Horizontal=false;
    @Element(required=false) public double VolumenUtilizado=0;
    @Element(required=false) public double CantidadUtilizada=0;
    @Element(required=false) public Object Tag=new Object();
    @Element(required=false) public clsBeFont_Enc pFont=new clsBeFont_Enc();
    @Element(required=false) public int Nivel=0;


    public clsBeBodega_tramo() {
    }

    public clsBeBodega_tramo(int IdTramo,int IdSector,int IdArea,int IdBodega,
                             boolean Sistema,String Descripcion,String User_agr,String Fec_agr,
                             String User_mod,String Fec_mod,boolean Activo,double Alto,
                             double Largo,double Ancho,double Margen_izquierdo,double Margen_derecho,
                             double Margen_superior,double Margen_inferior,String Codigo,int Indice_x,
                             int Orientacion,int IdTipoProductoDefault,int IdFontEnc,int IdTipoRack,
                             boolean Es_Rack,boolean Horizontal,double VolumenUtilizado,double CantidadUtilizada,
                             Object Tag,clsBeFont_Enc pFont,int Nivel) {

        this.IdTramo=IdTramo;
        this.IdSector=IdSector;
        this.IdArea=IdArea;
        this.IdBodega=IdBodega;
        this.Sistema=Sistema;
        this.Descripcion=Descripcion;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Alto=Alto;
        this.Largo=Largo;
        this.Ancho=Ancho;
        this.Margen_izquierdo=Margen_izquierdo;
        this.Margen_derecho=Margen_derecho;
        this.Margen_superior=Margen_superior;
        this.Margen_inferior=Margen_inferior;
        this.Codigo=Codigo;
        this.Indice_x=Indice_x;
        this.Orientacion=Orientacion;
        this.IdTipoProductoDefault=IdTipoProductoDefault;
        this.IdFontEnc=IdFontEnc;
        this.IdTipoRack=IdTipoRack;
        this.Es_Rack=Es_Rack;
        this.Horizontal=Horizontal;
        this.VolumenUtilizado=VolumenUtilizado;
        this.CantidadUtilizada=CantidadUtilizada;
        this.Tag=Tag;
        this.pFont=pFont;
        this.Nivel=Nivel;

    }


    public int getIdTramo() {
        return IdTramo;
    }
    public void setIdTramo(int value) {
        IdTramo=value;
    }
    public int getIdSector() {
        return IdSector;
    }
    public void setIdSector(int value) {
        IdSector=value;
    }
    public int getIdArea() {
        return IdArea;
    }
    public void setIdArea(int value) {
        IdArea=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
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
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public double getAlto() {
        return Alto;
    }
    public void setAlto(double value) {
        Alto=value;
    }
    public double getLargo() {
        return Largo;
    }
    public void setLargo(double value) {
        Largo=value;
    }
    public double getAncho() {
        return Ancho;
    }
    public void setAncho(double value) {
        Ancho=value;
    }
    public double getMargen_izquierdo() {
        return Margen_izquierdo;
    }
    public void setMargen_izquierdo(double value) {
        Margen_izquierdo=value;
    }
    public double getMargen_derecho() {
        return Margen_derecho;
    }
    public void setMargen_derecho(double value) {
        Margen_derecho=value;
    }
    public double getMargen_superior() {
        return Margen_superior;
    }
    public void setMargen_superior(double value) {
        Margen_superior=value;
    }
    public double getMargen_inferior() {
        return Margen_inferior;
    }
    public void setMargen_inferior(double value) {
        Margen_inferior=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public int getIndice_x() {
        return Indice_x;
    }
    public void setIndice_x(int value) {
        Indice_x=value;
    }
    public int getOrientacion() {
        return Orientacion;
    }
    public void setOrientacion(int value) {
        Orientacion=value;
    }
    public int getIdTipoProductoDefault() {
        return IdTipoProductoDefault;
    }
    public void setIdTipoProductoDefault(int value) {
        IdTipoProductoDefault=value;
    }
    public int getIdFontEnc() {
        return IdFontEnc;
    }
    public void setIdFontEnc(int value) {
        IdFontEnc=value;
    }
    public int getIdTipoRack() {
        return IdTipoRack;
    }
    public void setIdTipoRack(int value) {
        IdTipoRack=value;
    }
    public boolean getEs_Rack() {
        return Es_Rack;
    }
    public void setEs_Rack(boolean value) {
        Es_Rack=value;
    }
    public boolean getHorizontal() {
        return Horizontal;
    }
    public void setHorizontal(boolean value) {
        Horizontal=value;
    }
    public double getVolumenUtilizado() {
        return VolumenUtilizado;
    }
    public void setVolumenUtilizado(double value) {
        VolumenUtilizado=value;
    }
    public double getCantidadUtilizada() {
        return CantidadUtilizada;
    }
    public void setCantidadUtilizada(double value) {
        CantidadUtilizada=value;
    }
    public Object getTag() {
        return Tag;
    }
    public void setTag(Object value) {
        Tag=value;
    }
    public clsBeFont_Enc getpFont() {
        return pFont;
    }
    public void setpFont(clsBeFont_Enc value) {
        pFont=value;
    }
    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int value) {
        Nivel=value;
    }

}

