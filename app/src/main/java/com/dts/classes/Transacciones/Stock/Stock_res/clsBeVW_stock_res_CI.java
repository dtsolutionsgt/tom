package com.dts.classes.Transacciones.Stock.Stock_res;


import org.simpleframework.xml.Element;

public class clsBeVW_stock_res_CI {

  @Element(required=false) public String Codigo="";
  @Element(required=false) public String Nombre="";
  @Element(required=false) public String UM="";
  @Element(required=false) public String ExistUMBAs="";
  @Element(required=false) public String Pres="";
  @Element(required=false) public String ExistPres="";
  @Element(required=false) public String ReservadoUMBAs="";
  @Element(required=false) public String DisponibleUMBas="";
  @Element(required=false) public String Lote="";
  @Element(required=false) public String Vence="1900-01-01T00:00:01";
  @Element(required=false) public String Estado="";
  @Element(required=false) public String Ubic="";
  @Element(required=false) public String idUbic="";
  @Element(required=false) public String Pedido="";
  @Element(required=false) public String Pick="";
  @Element(required=false) public String LicPlate="";
  @Element(required=false) public String IdProductoEstado="";
  @Element(required=false) public int IdProductoBodega=0;
  @Element(required=false) public int factor=0;
  @Element(required=false) public String ingreso="1900-01-01T00:00:01";
  @Element(required=false) public int IdTipoEtiqueta=0;//#CKFK 20210716 1846 Agregué el campo IdTipoEtiqueta a la clase clsBeVW_stock_res_CI
  @Element(required=false) public String DispPres = "";
  @Element(required=false) public String ResPres = "";
  @Element(required=false) public String NombreArea = "";
  @Element(required=false) public String Clasificacion = "";

  public clsBeVW_stock_res_CI() {
  }

  public clsBeVW_stock_res_CI(String Codigo, String Nombre, String UM, String ExistUMBAs,String Pres,
                              String ExistPres,String ReservadoUMBAs,String DisponibleUMBas,String Lote,
                              String Vence,String Estado,String Ubic,String idUbic,String Pedido,String Pick,
                              String LicPlate,String IdProductoEstado,int IdProductoBodega, int factor,
                              String ingreso, int IdTipoEtiqueta,String DispPres,String ResPres, String NombreArea, String Clasificacion) {

    this.Codigo=Codigo;
    this.Nombre=Nombre;
    this.UM=UM;
    this.ExistUMBAs=ExistUMBAs;
    this.Pres=Pres;
    this.ExistPres=ExistPres;
    this.ReservadoUMBAs=ReservadoUMBAs;
    this.DisponibleUMBas=DisponibleUMBas;
    this.Lote=Lote;
    this.Vence=Vence;
    this.Estado=Estado;
    this.Ubic=Ubic;
    this.idUbic=idUbic;
    this.Pedido=Pedido;
    this.Pick=Pick;
    this.LicPlate=LicPlate;
    this.IdProductoEstado= IdProductoEstado;
    this.IdProductoBodega=IdProductoBodega;
    this.factor = factor;
    this.ingreso = ingreso;
    this.IdTipoEtiqueta=IdTipoEtiqueta;
    this.DispPres = DispPres;
    this.ResPres = ResPres;
    this.NombreArea = NombreArea;
    this.Clasificacion = Clasificacion;
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
  public String getUM() {
    return UM;
  }
  public void setUM(String value) {
    UM=value;
  }
  public String getExistUMBAs() {
    return ExistUMBAs;
  }
  public void setExistUMBAs(String value) {
    ExistUMBAs=value;
  }
  public String getPres() {
    return Pres;
  }
  public void setPres(String value) {
    Pres=value;
  }
  public String getExistPres() {
    return ExistPres;
  }
  public void setExistPres(String value) {
    ExistPres=value;
  }
  public String getReservadoUMBAs() {
    return ReservadoUMBAs;
  }
  public void setReservadoUMBAs(String value) {
    ReservadoUMBAs=value;
  }
  public String getDisponibleUMBas() {
    return DisponibleUMBas;
  }
  public void setDisponibleUMBas(String value) {
    DisponibleUMBas=value;
  }
  public String getLote() {
    return Lote;
  }
  public void setLote(String value) {
    Lote=value;
  }
  public String getVence() {
    return Vence;
  }
  public void setVence(String value) {
    Vence=value;
  }
  public String getEstado() {
    return Estado;
  }
  public void setEstado(String value) {
    Estado=value;
  }
  public String getIdPropietario() {
    return Ubic;
  }
  public void setIdPropietario(String value) {
    Ubic=value;
  }
  public String getidUbic() {
    return idUbic;
  }
  public void setIdUbic(String value){ idUbic=value; }
  public String getIdUbic() {
    return idUbic;
  }
  public void setPedido(String value){ Pedido=value; }
  public String getPedido() {
    return Pedido;
  }
  public void setPick(String value){ Pick=value; }
  public String getPick() {
    return Pick;
  }
  public void setLicPlate(String value){ LicPlate=value; }
  public String getLicPlate() {
    return LicPlate;
  }
  public void setProductoEstado(String value){ IdProductoEstado=value; }
  public String getIdProductoEstado() {
    return IdProductoEstado;
  }
  public void setIdProductoBodega(int value){ IdProductoBodega=value; }
  public int getIdProductoBodega() {
    return IdProductoBodega;
  }
  public void setfactor(int value){ factor =value; }
  public int getfactor() {
    return factor;
  }
  public String getingreso() {
    return ingreso;
  }
  public void setingreso(String value) {
    ingreso=value;
  }
  public void setIdTipoEtiqueta(int value){ IdTipoEtiqueta=value; }
  public int getIdTipoEtiqueta() {
    return IdTipoEtiqueta;
  }
  public String getDispPres() {
    return DispPres;
  }
  public void setDispPres(String value) {
    DispPres=value;
  }
  public String getResPres() {
    return ResPres;
  }
  public void setResPres(String value) {
    ResPres=value;
  }
  public String getNombreArea() {
    return NombreArea;
  }
  public void setNombreArea(String value) {
    NombreArea=value;
  }
  public String getClasificacion() {
    return Clasificacion;
  }
  public void setClasificacion(String value) { Clasificacion=value; }
}