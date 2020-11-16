package com.dts.classes.Transacciones.Stock.Stock_res;


import android.os.Parcel;
import android.os.Parcelable;

import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;

import org.simpleframework.xml.Element;

public class clsBeVW_stock_res_CI {

  @Element(required=false) public int Codigo=0;
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

  /*@Element(required=false) public clsBeProducto_Presentacion BePresentacionProductoEnStock=new clsBeProducto_Presentacion();
  @Element(required=false) public clsBeBodega_ubicacion UbicacionActual=new clsBeBodega_ubicacion();*/



  public clsBeVW_stock_res_CI(int Codigo, String Nombre, String UM, String ExistUMBAs,String Pres,
                              String ExistPres,String ReservadoUMBAs,String DisponibleUMBas,String Lote,
                              String Vence,String Estado,String Ubic,int idUbic,String Pedido,String Pick,String LicPlate,
                              String IdProductoEstado,int IdProductoBodega) {
                              }

  public clsBeVW_stock_res_CI() {

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
  }

  public int getCodigo() {
    return Codigo;
  }
  public void setCodigo(int value) {
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



/*  public clsBeProducto_Presentacion getBePresentacionProductoEnStock() {
    return BePresentacionProductoEnStock;
  }
  public void setBePresentacionProductoEnStock(clsBeProducto_Presentacion value) {
    BePresentacionProductoEnStock=value;
  }*/



}

