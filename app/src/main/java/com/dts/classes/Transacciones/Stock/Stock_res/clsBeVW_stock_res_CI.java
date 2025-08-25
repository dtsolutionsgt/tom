package com.dts.classes.Transacciones.Stock.Stock_res;

import com.google.gson.annotations.SerializedName;
import org.simpleframework.xml.Element;

public class clsBeVW_stock_res_CI {

  @Element(required = false) @SerializedName("Codigo")
  public String Codigo = "";

  @Element(required = false) @SerializedName("Nombre")
  public String Nombre = "";

  @Element(required = false) @SerializedName("UM")
  public String UM = "";

  @Element(required = false) @SerializedName("ExistUMBAs")
  public String ExistUMBAs = "";

  @Element(required = false) @SerializedName("Pres")
  public String Pres = "";

  @Element(required = false) @SerializedName("ExistPres")
  public String ExistPres = "";

  @Element(required = false) @SerializedName("ReservadoUMBAs")
  public String ReservadoUMBAs = "";

  @Element(required = false) @SerializedName("DisponibleUMBas")
  public String DisponibleUMBas = "";

  @Element(required = false) @SerializedName("Lote")
  public String Lote = "";

  @Element(required = false) @SerializedName("Fecha_Vence")
  public String Fecha_Vence = "1900-01-01T00:00:00";

  @Element(required = false) @SerializedName("Estado")
  public String Estado = "";

  @Element(required = false) @SerializedName("Ubic")
  public String Ubic = "";

  @Element(required = false) @SerializedName("idUbic")
  public String idUbic = "";

  @Element(required = false) @SerializedName("Pedido")
  public String Pedido = "";

  @Element(required = false) @SerializedName("Pick")
  public String Pick = "";

  @Element(required = false) @SerializedName("LicPlate")
  public String LicPlate = "";

  @Element(required = false) @SerializedName("IdProductoEstado")
  public String IdProductoEstado = "";

  @Element(required = false) @SerializedName("IdProductoBodega")
  public int IdProductoBodega = 0;

  @Element(required = false) @SerializedName("factor")
  public int factor = 0;

  @Element(required = false) @SerializedName("ingreso")
  public String ingreso = "1900-01-01T00:00:00";

  @Element(required = false) @SerializedName("IdTipoEtiqueta")
  public int IdTipoEtiqueta = 0;

  @Element(required = false) @SerializedName("DispPres")
  public String DispPres = "";

  @Element(required = false) @SerializedName("ResPres")
  public String ResPres = "";

  @Element(required = false) @SerializedName("NombreArea")
  public String NombreArea = "";

  @Element(required = false) @SerializedName("Clasificacion")
  public String Clasificacion = "";

  @Element(required = false) @SerializedName("IdPresentacion")
  public int IdPresentacion = 0;

  @Element(required = false) @SerializedName("IdArea")
  public int IdArea = 0;

  @Element(required = false) @SerializedName("IdStock")
  public int IdStock = 0;

  @Element(required = false) @SerializedName("Nombre_Talla")
  public String Nombre_Talla = "";

  @Element(required = false) @SerializedName("Codigo_Talla")
  public String Codigo_Talla = "";

  @Element(required = false) @SerializedName("Nombre_Color")
  public String Nombre_Color = "";

  @Element(required = false) @SerializedName("Codigo_Color")
  public String Codigo_Color = "";

  @Element(required = false) @SerializedName("CodigoSKU")
  public String CodigoSKU = "";

  @Element(required = false) @SerializedName("IdUbicacion_anterior")
  public int IdUbicacion_anterior = 0;

  // Constructor vacío
  public clsBeVW_stock_res_CI() {}

  // Constructor con parámetros principales
  public clsBeVW_stock_res_CI(String Codigo, String Nombre, String UM, String ExistUMBAs, String Pres,
                              String ExistPres, String ReservadoUMBAs, String DisponibleUMBas, String Lote,
                              String Fecha_Vence, String Estado, String Ubic, String idUbic, String Pedido, String Pick,
                              String LicPlate, String IdProductoEstado, int IdProductoBodega, int factor,
                              String ingreso, int IdTipoEtiqueta, String DispPres, String ResPres, String NombreArea,
                              String Clasificacion, int IdPresentacion, String Nombre_Talla, String Codigo_Talla,
                              String Nombre_Color, String Codigo_Color, String CodigoSKU) {

    this.Codigo = Codigo;
    this.Nombre = Nombre;
    this.UM = UM;
    this.ExistUMBAs = ExistUMBAs;
    this.Pres = Pres;
    this.ExistPres = ExistPres;
    this.ReservadoUMBAs = ReservadoUMBAs;
    this.DisponibleUMBas = DisponibleUMBas;
    this.Lote = Lote;
    this.Fecha_Vence = Fecha_Vence;
    this.Estado = Estado;
    this.Ubic = Ubic;
    this.idUbic = idUbic;
    this.Pedido = Pedido;
    this.Pick = Pick;
    this.LicPlate = LicPlate;
    this.IdProductoEstado = IdProductoEstado;
    this.IdProductoBodega = IdProductoBodega;
    this.factor = factor;
    this.ingreso = ingreso;
    this.IdTipoEtiqueta = IdTipoEtiqueta;
    this.DispPres = DispPres;
    this.ResPres = ResPres;
    this.NombreArea = NombreArea;
    this.Clasificacion = Clasificacion;
    this.IdPresentacion = IdPresentacion;
    this.Nombre_Talla = Nombre_Talla;
    this.Codigo_Talla = Codigo_Talla;
    this.Nombre_Color = Nombre_Color;
    this.Codigo_Color = Codigo_Color;
    this.CodigoSKU = CodigoSKU;
  }

  // Getters y setters
  public String getCodigo() { return Codigo; }
  public void setCodigo(String value) { Codigo = value; }

  public String getNombre() { return Nombre; }
  public void setNombre(String value) { Nombre = value; }

  public String getUM() { return UM; }
  public void setUM(String value) { UM = value; }

  public String getExistUMBAs() { return ExistUMBAs; }
  public void setExistUMBAs(String value) { ExistUMBAs = value; }

  public String getPres() { return Pres; }
  public void setPres(String value) { Pres = value; }

  public String getExistPres() { return ExistPres; }
  public void setExistPres(String value) { ExistPres = value; }

  public String getReservadoUMBAs() { return ReservadoUMBAs; }
  public void setReservadoUMBAs(String value) { ReservadoUMBAs = value; }

  public String getDisponibleUMBas() { return DisponibleUMBas; }
  public void setDisponibleUMBas(String value) { DisponibleUMBas = value; }

  public String getLote() { return Lote; }
  public void setLote(String value) { Lote = value; }

  public String getFecha_Vence() { return Fecha_Vence; }
  public void setFecha_Vence(String value) { Fecha_Vence = value; }

  public String getEstado() { return Estado; }
  public void setEstado(String value) { Estado = value; }

  public String getUbic() { return Ubic; }
  public void setUbic(String value) { Ubic = value; }

  public String getIdUbic() { return idUbic; }
  public void setIdUbic(String value) { idUbic = value; }

  public String getPedido() { return Pedido; }
  public void setPedido(String value) { Pedido = value; }

  public String getPick() { return Pick; }
  public void setPick(String value) { Pick = value; }

  public String getLicPlate() { return LicPlate; }
  public void setLicPlate(String value) { LicPlate = value; }

  public String getIdProductoEstado() { return IdProductoEstado; }
  public void setIdProductoEstado(String value) { IdProductoEstado = value; }

  public int getIdProductoBodega() { return IdProductoBodega; }
  public void setIdProductoBodega(int value) { IdProductoBodega = value; }

  public int getFactor() { return factor; }
  public void setFactor(int value) { factor = value; }

  public String getIngreso() { return ingreso; }
  public void setIngreso(String value) { ingreso = value; }

  public int getIdTipoEtiqueta() { return IdTipoEtiqueta; }
  public void setIdTipoEtiqueta(int value) { IdTipoEtiqueta = value; }

  public String getDispPres() { return DispPres; }
  public void setDispPres(String value) { DispPres = value; }

  public String getResPres() { return ResPres; }
  public void setResPres(String value) { ResPres = value; }

  public String getNombreArea() { return NombreArea; }
  public void setNombreArea(String value) { NombreArea = value; }

  public String getClasificacion() { return Clasificacion; }
  public void setClasificacion(String value) { Clasificacion = value; }
}