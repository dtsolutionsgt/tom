package com.dts.classes;


import org.simpleframework.xml.Element;

public class clsBeBodega_ubicacion {

    @Element(required=false) public int IdUbicacion;
    @Element(required=false) public int IdTramo;
    @Element(required=false) public String Descripcion;
    @Element(required=false) public double Ancho;
    @Element(required=false) public double Largo;
    @Element(required=false) public double Alto;
    @Element(required=false) public int Nivel;
    @Element(required=false) public int Indice_x;
    @Element(required=false) public int IdIndiceRotacion;
    @Element(required=false) public int IdTipoRotacion;
    @Element(required=false) public boolean Sistema;
    @Element(required=false) public String Codigo_barra;
    @Element(required=false) public String Codigo_barra2;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public boolean Dañado;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean Bloqueada;
    @Element(required=false) public boolean Acepta_pallet;
    @Element(required=false) public boolean Ubicacion_picking;
    @Element(required=false) public boolean Ubicacion_recepcion;
    @Element(required=false) public boolean Ubicacion_despacho;
    @Element(required=false) public boolean Ubicacion_merma;
    @Element(required=false) public double Margen_izquierdo;
    @Element(required=false) public double Margen_derecho;
    @Element(required=false) public double Margen_superior;
    @Element(required=false) public double Margen_inferior;
    @Element(required=false) public String Orientacion_pos;


    public clsBeBodega_ubicacion() {
    }

    public clsBeBodega_ubicacion(int IdUbicacion,int IdTramo,String Descripcion,double Ancho,
                                 double Largo,double Alto,int Nivel,int Indice_x,
                                 int IdIndiceRotacion,int IdTipoRotacion,boolean Sistema,String Codigo_barra,
                                 String Codigo_barra2,String User_agr,String Fec_agr,String User_mod,
                                 String Fec_mod,boolean Dañado,boolean Activo,boolean Bloqueada,
                                 boolean Acepta_pallet,boolean Ubicacion_picking,boolean Ubicacion_recepcion,boolean Ubicacion_despacho,
                                 boolean Ubicacion_merma,double Margen_izquierdo,double Margen_derecho,double Margen_superior,
                                 double Margen_inferior,String Orientacion_pos) {

        this.IdUbicacion=IdUbicacion;
        this.IdTramo=IdTramo;
        this.Descripcion=Descripcion;
        this.Ancho=Ancho;
        this.Largo=Largo;
        this.Alto=Alto;
        this.Nivel=Nivel;
        this.Indice_x=Indice_x;
        this.IdIndiceRotacion=IdIndiceRotacion;
        this.IdTipoRotacion=IdTipoRotacion;
        this.Sistema=Sistema;
        this.Codigo_barra=Codigo_barra;
        this.Codigo_barra2=Codigo_barra2;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Dañado=Dañado;
        this.Activo=Activo;
        this.Bloqueada=Bloqueada;
        this.Acepta_pallet=Acepta_pallet;
        this.Ubicacion_picking=Ubicacion_picking;
        this.Ubicacion_recepcion=Ubicacion_recepcion;
        this.Ubicacion_despacho=Ubicacion_despacho;
        this.Ubicacion_merma=Ubicacion_merma;
        this.Margen_izquierdo=Margen_izquierdo;
        this.Margen_derecho=Margen_derecho;
        this.Margen_superior=Margen_superior;
        this.Margen_inferior=Margen_inferior;
        this.Orientacion_pos=Orientacion_pos;

    }


    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public int getIdTramo() {
        return IdTramo;
    }
    public void setIdTramo(int value) {
        IdTramo=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public double getAncho() {
        return Ancho;
    }
    public void setAncho(double value) {
        Ancho=value;
    }
    public double getLargo() {
        return Largo;
    }
    public void setLargo(double value) {
        Largo=value;
    }
    public double getAlto() {
        return Alto;
    }
    public void setAlto(double value) {
        Alto=value;
    }
    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int value) {
        Nivel=value;
    }
    public int getIndice_x() {
        return Indice_x;
    }
    public void setIndice_x(int value) {
        Indice_x=value;
    }
    public int getIdIndiceRotacion() {
        return IdIndiceRotacion;
    }
    public void setIdIndiceRotacion(int value) {
        IdIndiceRotacion=value;
    }
    public int getIdTipoRotacion() {
        return IdTipoRotacion;
    }
    public void setIdTipoRotacion(int value) {
        IdTipoRotacion=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public String getCodigo_barra() {
        return Codigo_barra;
    }
    public void setCodigo_barra(String value) {
        Codigo_barra=value;
    }
    public String getCodigo_barra2() {
        return Codigo_barra2;
    }
    public void setCodigo_barra2(String value) {
        Codigo_barra2=value;
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
    public boolean getDañado() {
        return Dañado;
    }
    public void setDañado(boolean value) {
        Dañado=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getBloqueada() {
        return Bloqueada;
    }
    public void setBloqueada(boolean value) {
        Bloqueada=value;
    }
    public boolean getAcepta_pallet() {
        return Acepta_pallet;
    }
    public void setAcepta_pallet(boolean value) {
        Acepta_pallet=value;
    }
    public boolean getUbicacion_picking() {
        return Ubicacion_picking;
    }
    public void setUbicacion_picking(boolean value) {
        Ubicacion_picking=value;
    }
    public boolean getUbicacion_recepcion() {
        return Ubicacion_recepcion;
    }
    public void setUbicacion_recepcion(boolean value) {
        Ubicacion_recepcion=value;
    }
    public boolean getUbicacion_despacho() {
        return Ubicacion_despacho;
    }
    public void setUbicacion_despacho(boolean value) {
        Ubicacion_despacho=value;
    }
    public boolean getUbicacion_merma() {
        return Ubicacion_merma;
    }
    public void setUbicacion_merma(boolean value) {
        Ubicacion_merma=value;
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
    public String getOrientacion_pos() {
        return Orientacion_pos;
    }
    public void setOrientacion_pos(String value) {
        Orientacion_pos=value;
    }

}

