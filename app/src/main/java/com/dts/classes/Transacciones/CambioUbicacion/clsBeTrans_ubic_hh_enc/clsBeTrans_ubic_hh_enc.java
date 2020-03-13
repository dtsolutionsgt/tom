package com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc;

import org.simpleframework.xml.Element;

public class clsBeTrans_ubic_hh_enc {

    @Element(required=false) public int IdTareaUbicacionEnc;
    @Element(required=false) public int IdPropietarioBodega;
    @Element(required=false) public int IdMotivoUbicacion;
    @Element(required=false) public String FechaInicio;
    @Element(required=false) public String HoraInicio;
    @Element(required=false) public String FechaFin;
    @Element(required=false) public String HoraFin;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public String Observacion;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean Operador_por_linea;
    @Element(required=false) public boolean Ubicacion_con_hh;
    @Element(required=false) public String Estado;
    @Element(required=false) public boolean Cambio_estado;
    @Element(required=false) public int IdPrioridad;
    @Element(required=false) public int IdTipoTarea;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public String Asunto;
    @Element(required=false) public String DescripcionMotivo;
    @Element(required=false) public boolean IsNew;


    public clsBeTrans_ubic_hh_enc() {
    }

    public clsBeTrans_ubic_hh_enc(int IdTareaUbicacionEnc,int IdPropietarioBodega,int IdMotivoUbicacion,String FechaInicio,
                                  String HoraInicio,String FechaFin,String HoraFin,String User_agr,
                                  String Fec_agr,String User_mod,String Fec_mod,String Observacion,
                                  boolean Activo,boolean Operador_por_linea,boolean Ubicacion_con_hh,String Estado,
                                  boolean Cambio_estado,int IdPrioridad,int IdTipoTarea,int IdBodega,
                                  String Asunto,String DescripcionMotivo,boolean IsNew) {

        this.IdTareaUbicacionEnc=IdTareaUbicacionEnc;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdMotivoUbicacion=IdMotivoUbicacion;
        this.FechaInicio=FechaInicio;
        this.HoraInicio=HoraInicio;
        this.FechaFin=FechaFin;
        this.HoraFin=HoraFin;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Observacion=Observacion;
        this.Activo=Activo;
        this.Operador_por_linea=Operador_por_linea;
        this.Ubicacion_con_hh=Ubicacion_con_hh;
        this.Estado=Estado;
        this.Cambio_estado=Cambio_estado;
        this.IdPrioridad=IdPrioridad;
        this.IdTipoTarea=IdTipoTarea;
        this.IdBodega=IdBodega;
        this.Asunto=Asunto;
        this.DescripcionMotivo=DescripcionMotivo;
        this.IsNew=IsNew;

    }


    public int getIdTareaUbicacionEnc() {
        return IdTareaUbicacionEnc;
    }
    public void setIdTareaUbicacionEnc(int value) {
        IdTareaUbicacionEnc=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdMotivoUbicacion() {
        return IdMotivoUbicacion;
    }
    public void setIdMotivoUbicacion(int value) {
        IdMotivoUbicacion=value;
    }
    public String getFechaInicio() {
        return FechaInicio;
    }
    public void setFechaInicio(String value) {
        FechaInicio=value;
    }
    public String getHoraInicio() {
        return HoraInicio;
    }
    public void setHoraInicio(String value) {
        HoraInicio=value;
    }
    public String getFechaFin() {
        return FechaFin;
    }
    public void setFechaFin(String value) {
        FechaFin=value;
    }
    public String getHoraFin() {
        return HoraFin;
    }
    public void setHoraFin(String value) {
        HoraFin=value;
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
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getOperador_por_linea() {
        return Operador_por_linea;
    }
    public void setOperador_por_linea(boolean value) {
        Operador_por_linea=value;
    }
    public boolean getUbicacion_con_hh() {
        return Ubicacion_con_hh;
    }
    public void setUbicacion_con_hh(boolean value) {
        Ubicacion_con_hh=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public boolean getCambio_estado() {
        return Cambio_estado;
    }
    public void setCambio_estado(boolean value) {
        Cambio_estado=value;
    }
    public int getIdPrioridad() {
        return IdPrioridad;
    }
    public void setIdPrioridad(int value) {
        IdPrioridad=value;
    }
    public int getIdTipoTarea() {
        return IdTipoTarea;
    }
    public void setIdTipoTarea(int value) {
        IdTipoTarea=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public String getAsunto() {
        return Asunto;
    }
    public void setAsunto(String value) {
        Asunto=value;
    }
    public String getDescripcionMotivo() {
        return DescripcionMotivo;
    }
    public void setDescripcionMotivo(String value) {
        DescripcionMotivo=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

