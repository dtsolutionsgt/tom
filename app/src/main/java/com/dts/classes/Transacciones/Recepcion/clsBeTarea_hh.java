package com.dts.classes.Transacciones.Recepcion;


import org.simpleframework.xml.Element;

public class clsBeTarea_hh {

  @Element(required=false) public int IdTareahh;
  @Element(required=false) public int IdPropietario;
  @Element(required=false) public int IdBodega;
  @Element(required=false) public int IdMuelle;
  @Element(required=false) public int IdEstado;
  @Element(required=false) public int IdPrioridad;
  @Element(required=false) public int IdTipoTarea;
  @Element(required=false) public int IdTransaccion;
  @Element(required=false) public int Tipo;
  @Element(required=false) public String FechaInicio;
  @Element(required=false) public String FechaFin;
  @Element(required=false) public boolean DiaCompleto;
  @Element(required=false) public String Asunto;
  @Element(required=false) public String Ubicacion;
  @Element(required=false) public String Descripcion;
  @Element(required=false) public String Recordatorio;
  @Element(required=false) public boolean CreaTarea;
  @Element(required=false) public boolean IsNew;


  public clsBeTarea_hh() {
  }

  public clsBeTarea_hh(int IdTareahh,int IdPropietario,int IdBodega,int IdMuelle,
                       int IdEstado,int IdPrioridad,int IdTipoTarea,int IdTransaccion,
                       int Tipo,String FechaInicio,String FechaFin,boolean DiaCompleto,
                       String Asunto,String Ubicacion,String Descripcion,String Recordatorio,
                       boolean CreaTarea,boolean IsNew) {

    this.IdTareahh=IdTareahh;
    this.IdPropietario=IdPropietario;
    this.IdBodega=IdBodega;
    this.IdMuelle=IdMuelle;
    this.IdEstado=IdEstado;
    this.IdPrioridad=IdPrioridad;
    this.IdTipoTarea=IdTipoTarea;
    this.IdTransaccion=IdTransaccion;
    this.Tipo=Tipo;
    this.FechaInicio=FechaInicio;
    this.FechaFin=FechaFin;
    this.DiaCompleto=DiaCompleto;
    this.Asunto=Asunto;
    this.Ubicacion=Ubicacion;
    this.Descripcion=Descripcion;
    this.Recordatorio=Recordatorio;
    this.CreaTarea=CreaTarea;
    this.IsNew=IsNew;

  }


  public int getIdTareahh() {
    return IdTareahh;
  }
  public void setIdTareahh(int value) {
    IdTareahh=value;
  }
  public int getIdPropietario() {
    return IdPropietario;
  }
  public void setIdPropietario(int value) {
    IdPropietario=value;
  }
  public int getIdBodega() {
    return IdBodega;
  }
  public void setIdBodega(int value) {
    IdBodega=value;
  }
  public int getIdMuelle() {
    return IdMuelle;
  }
  public void setIdMuelle(int value) {
    IdMuelle=value;
  }
  public int getIdEstado() {
    return IdEstado;
  }
  public void setIdEstado(int value) {
    IdEstado=value;
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
  public int getIdTransaccion() {
    return IdTransaccion;
  }
  public void setIdTransaccion(int value) {
    IdTransaccion=value;
  }
  public int getTipo() {
    return Tipo;
  }
  public void setTipo(int value) {
    Tipo=value;
  }
  public String getFechaInicio() {
    return FechaInicio;
  }
  public void setFechaInicio(String value) {
    FechaInicio=value;
  }
  public String getFechaFin() {
    return FechaFin;
  }
  public void setFechaFin(String value) {
    FechaFin=value;
  }
  public boolean getDiaCompleto() {
    return DiaCompleto;
  }
  public void setDiaCompleto(boolean value) {
    DiaCompleto=value;
  }
  public String getAsunto() {
    return Asunto;
  }
  public void setAsunto(String value) {
    Asunto=value;
  }
  public String getUbicacion() {
    return Ubicacion;
  }
  public void setUbicacion(String value) {
    Ubicacion=value;
  }
  public String getDescripcion() {
    return Descripcion;
  }
  public void setDescripcion(String value) {
    Descripcion=value;
  }
  public String getRecordatorio() {
    return Recordatorio;
  }
  public void setRecordatorio(String value) {
    Recordatorio=value;
  }
  public boolean getCreaTarea() {
    return CreaTarea;
  }
  public void setCreaTarea(boolean value) {
    CreaTarea=value;
  }
  public boolean getIsNew() {
    return IsNew;
  }
  public void setIsNew(boolean value) {
    IsNew=value;
  }

}

