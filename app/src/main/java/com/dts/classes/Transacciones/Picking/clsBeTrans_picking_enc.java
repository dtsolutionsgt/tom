package com.dts.classes.Transacciones.Picking;


import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;

import org.simpleframework.xml.Element;

public class clsBeTrans_picking_enc {

    @Element(required=false) public int IdPickingEnc=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdUbicacionPicking=0;
    @Element(required=false) public String Fecha_picking="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_ini="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_fin="1900-01-01T00:00:01";
    @Element(required=false) public String Estado="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Detalle_operador=false;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean verifica_auto=false;
    @Element(required=false) public boolean procesado_bof=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public String NombreUbicacionPicking="";
    @Element(required=false) public clsBeBodega_ubicacion UbicacionPicking=new clsBeBodega_ubicacion();
    @Element(required=false) public clsBeTrans_picking_detList ListaPickingDet=new clsBeTrans_picking_detList();
    @Element(required=false) public clsBeTrans_picking_ubicList ListaPickingUbic=new clsBeTrans_picking_ubicList();
    @Element(required=false) public String NombreBodega="";
    @Element(required=false) public String NombrePropietarioPicking="";
    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public boolean Requiere_Preparacion=false;
    @Element(required=false) public String Tipo_Preparacion="";



    public clsBeTrans_picking_enc() {
    }

    public clsBeTrans_picking_enc(int IdPickingEnc, int IdBodega, int IdPropietarioBodega, int IdUbicacionPicking,
                                  String Fecha_picking, String Hora_ini, String Hora_fin, String Estado,
                                  String User_agr, String Fec_agr, String User_mod, String Fec_mod,
                                  boolean Detalle_operador, boolean Activo,
                                  boolean verifica_auto,boolean procesado_bof, boolean Requiere_Preparacion,String Tipo_Preparacion,
                                  boolean IsNew, String NombreUbicacionPicking, clsBeBodega_ubicacion UbicacionPicking, clsBeTrans_picking_detList ListaPickingDet,
                                  clsBeTrans_picking_ubicList ListaPickingUbic, String NombreBodega, String NombrePropietarioPicking, int IdPedidoEnc

    ) {

        this.IdPickingEnc=IdPickingEnc;
        this.IdBodega=IdBodega;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdUbicacionPicking=IdUbicacionPicking;
        this.Fecha_picking=Fecha_picking;
        this.Hora_ini=Hora_ini;
        this.Hora_fin=Hora_fin;
        this.Estado=Estado;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Detalle_operador=Detalle_operador;
        this.Activo=Activo;
        this.verifica_auto=verifica_auto;
        this.procesado_bof=procesado_bof;
        this.IsNew=IsNew;
        this.NombreUbicacionPicking=NombreUbicacionPicking;
        this.UbicacionPicking=UbicacionPicking;
        this.ListaPickingDet=ListaPickingDet;
        this.ListaPickingUbic=ListaPickingUbic;
        this.NombreBodega=NombreBodega;
        this.NombrePropietarioPicking=NombrePropietarioPicking;
        this.IdPedidoEnc=IdPedidoEnc;
        this.Requiere_Preparacion=Requiere_Preparacion;
        this.Tipo_Preparacion=Tipo_Preparacion;
    }


    public int getIdPickingEnc() {
        return IdPickingEnc;
    }
    public void setIdPickingEnc(int value) {
        IdPickingEnc=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdUbicacionPicking() {
        return IdUbicacionPicking;
    }
    public void setIdUbicacionPicking(int value) {
        IdUbicacionPicking=value;
    }
    public String getFecha_picking() {
        return Fecha_picking;
    }
    public void setFecha_picking(String value) {
        Fecha_picking=value;
    }
    public String getHora_ini() {
        return Hora_ini;
    }
    public void setHora_ini(String value) {
        Hora_ini=value;
    }
    public String getHora_fin() {
        return Hora_fin;
    }
    public void setHora_fin(String value) {
        Hora_fin=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
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
    public boolean getDetalle_operador() {
        return Detalle_operador;
    }
    public void setDetalle_operador(boolean value) {
        Detalle_operador=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getverifica_auto() {
        return verifica_auto;
    }
    public void setverifica_auto(boolean value) {
        verifica_auto=value;
    }
    public boolean getprocesado_bof() {
        return procesado_bof;
    }
    public void setprocesado_bof(boolean value) {
        procesado_bof=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getNombreUbicacionPicking() {
        return NombreUbicacionPicking;
    }
    public void setNombreUbicacionPicking(String value) {
        NombreUbicacionPicking=value;
    }
    public clsBeBodega_ubicacion getUbicacionPicking() {
        return UbicacionPicking;
    }
    public void setUbicacionPicking(clsBeBodega_ubicacion value) {
        UbicacionPicking=value;
    }
    public clsBeTrans_picking_detList getListaPickingDet() {
        return ListaPickingDet;
    }
    public void setListaPickingDet(clsBeTrans_picking_detList value) {
        ListaPickingDet=value;
    }
    public clsBeTrans_picking_ubicList getListaPickingUbic() {
        return ListaPickingUbic;
    }
    public void setListaPickingUbic(clsBeTrans_picking_ubicList value) {
        ListaPickingUbic=value;
    }
    public String getNombreBodega() {
        return NombreBodega;
    }
    public void setNombreBodega(String value) {
        NombreBodega=value;
    }
    public String getNombrePropietarioPicking() {
        return NombrePropietarioPicking;
    }
    public void setNombrePropietarioPicking(String value) {
        NombrePropietarioPicking=value;
    }
    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public boolean getRequiere_Preparacion() {
        return Requiere_Preparacion;
    }
    public void setRequiere_Preparacion(boolean value) {
        Requiere_Preparacion=value;
    }

    public String getTipo_Preparacion() {
        return Tipo_Preparacion;
    }
    public void setTipo_Preparacion(String value) {
        Tipo_Preparacion=value;
    }

}


