package com.dts.classes.Mantenimientos.Producto;


import com.dts.classes.Mantenimientos.Arancel.clsBeArancel;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Producto.Producto_clasificacion.clsBeProducto_clasificacion;
import com.dts.classes.Mantenimientos.Producto.Producto_codigos_barra.clsBeProducto_codigos_barraList;
import com.dts.classes.Mantenimientos.Producto.Producto_familia.clsBeProducto_familia;
import com.dts.classes.Mantenimientos.Producto.Producto_marca.clsBeProducto_marca;
import com.dts.classes.Mantenimientos.Producto.Producto_parametros.clsBeProducto_parametrosList;
import com.dts.classes.Mantenimientos.Producto.Producto_tipo.clsBeProducto_tipo;
import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;

import org.simpleframework.xml.Element;

public class clsBeProducto {

  @Element(required=false) public int IdProducto=0;
  @Element(required=false) public int IdPropietario=0;
  @Element(required=false) public int IdClasificacion=0;
  @Element(required=false) public int IdFamilia=0;
  @Element(required=false) public int IdMarca=0;
  @Element(required=false) public int IdTipoProducto=0;
  @Element(required=false) public int IdUnidadMedidaBasica=0;
  @Element(required=false) public int IdCamara=0;
  @Element(required=false) public int IdTipoRotacion=0;
  @Element(required=false) public int IdPerfilSerializado=0;
  @Element(required=false) public int IdIndiceRotacion=0;
  @Element(required=false) public int IdSimbologia=0;
  @Element(required=false) public int IdArancel=0;
  @Element(required=false) public String Codigo="";
  @Element(required=false) public String Nombre="";
  @Element(required=false) public String Codigo_barra="";
  @Element(required=false) public double Precio=0;
  @Element(required=false) public double Existencia_min=0;
  @Element(required=false) public double Existencia_max=0;
  @Element(required=false) public double Costo=0;
  @Element(required=false) public double Peso_referencia=0;
  @Element(required=false) public double Peso_tolerancia=0;
  @Element(required=false) public double Temperatura_referencia=0;
  @Element(required=false) public double Temperatura_tolerancia=0;
  @Element(required=false) public boolean Activo=true;
  @Element(required=false) public boolean Serializado=false;
  @Element(required=false) public boolean Genera_lote=false ;
  @Element(required=false) public boolean Genera_lp= false;
  @Element(required=false) public boolean Control_vencimiento= false;
  @Element(required=false) public boolean Control_lote = false;
  @Element(required=false) public boolean Peso_recepcion = false;
  @Element(required=false) public boolean Peso_despacho=false;
  @Element(required=false) public boolean Temperatura_recepcion =false;
  @Element(required=false) public boolean Temperatura_despacho = false;
  @Element(required=false) public boolean Materia_prima=false;
  @Element(required=false) public boolean Kit=false;
  @Element(required=false) public int Tolerancia=0;
  @Element(required=false) public int Ciclo_vida=0;
  @Element(required=false) public String UseClienteRapidor_agr="";
  @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
  @Element(required=false) public String User_mod="";
  @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
  @Element(required=false) public String Imagen="";
  @Element(required=false) public String Noserie="";
  @Element(required=false) public String Noparte="";
  @Element(required=false) public boolean Fechamanufactura=false;
  @Element(required=false) public boolean Capturar_aniada=false;
  @Element(required=false) public boolean Control_peso =false;
  @Element(required=false) public boolean Captura_arancel=false;
  @Element(required=false) public boolean Es_hardware = false;
  @Element(required=false) public double Largo=0;
  @Element(required=false) public double Alto=0;
  @Element(required=false) public double Ancho=0;
  @Element(required=false) public int IdProductoBodega;
  @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
  @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
  @Element(required=false) public clsBeProducto_clasificacion Clasificacion=new clsBeProducto_clasificacion();
  @Element(required=false) public clsBeProducto_familia Familia=new clsBeProducto_familia();
  @Element(required=false) public clsBeProducto_marca Marca=new clsBeProducto_marca();
  @Element(required=false) public clsBeProducto_tipo TipoProducto=new clsBeProducto_tipo();
  @Element(required=false) public clsBeUnidad_medida UnidadMedida=new clsBeUnidad_medida();
  @Element(required=false) public clsBeArancel Arancel=new clsBeArancel();
  @Element(required=false) public clsBeProducto_PresentacionList Presentaciones=new clsBeProducto_PresentacionList();
  @Element(required=false) public clsBeProducto_codigos_barraList Codigos_Barra=new clsBeProducto_codigos_barraList();
  @Element(required=false) public clsBeProducto_parametrosList Parametros=new clsBeProducto_parametrosList();
  @Element(required=false) public clsBeVW_stock_res Stock=new clsBeVW_stock_res();
  @Element(required=false) public boolean IsNew=false;
  @Element(required=false) public Object Tag=new Object();
  @Element(required=false) public int IdPresentacionOrigen=0;
  @Element(required=false) public int IdPresentacionDestino=0;
  @Element(required=false) public double Factor=0;
  @Element(required=false) public double ExistenciaUMBas;
  @Element(required=false) public String Lote="";
  @Element(required=false) public String FechaVence="1900-01-01T00:00:01";
  @Element(required=false) public double Cantidad=0;
  @Element(required=false) public int IdUnidadMedidaCobro=0;
  @Element(required=false) public int IdTipoEtiqueta=0;
  @Element(required=false) public String User_agr="";
//EJC

  public clsBeProducto()
  {
    this.IdProducto=IdProducto;
    this.IdPropietario=IdPropietario;
    this.IdClasificacion=IdClasificacion;
    this.IdFamilia=IdFamilia;
    this.IdMarca=IdMarca;
    this.IdTipoProducto=IdTipoProducto;
    this.IdUnidadMedidaBasica=IdUnidadMedidaBasica;
    this.IdCamara=IdCamara;
    this.IdTipoRotacion=IdTipoRotacion;
    this.IdPerfilSerializado=IdPerfilSerializado;
    this.IdIndiceRotacion=IdIndiceRotacion;
    this.IdSimbologia=IdSimbologia;
    this.IdArancel=IdArancel;
    this.Codigo=Codigo;
    this.Nombre=Nombre;
    this.Codigo_barra=Codigo_barra;
    this.Precio=Precio;
    this.Existencia_min=Existencia_min;
    this.Existencia_max=Existencia_max;
    this.Costo=Costo;
    this.Peso_referencia=Peso_referencia;
    this.Peso_tolerancia=Peso_tolerancia;
    this.Temperatura_referencia=Temperatura_referencia;
    this.Temperatura_tolerancia=Temperatura_tolerancia;
    this.Activo=Activo;
    this.Serializado=Serializado;
    this.Genera_lote=Genera_lote;
    this.Genera_lp=Genera_lp;
    this.Control_vencimiento=Control_vencimiento;
    this.Control_lote=Control_lote;
    this.Peso_recepcion=Peso_recepcion;
    this.Peso_despacho=Peso_despacho;
    this.Temperatura_recepcion=Temperatura_recepcion;
    this.Temperatura_despacho=Temperatura_despacho;
    this.Materia_prima=Materia_prima;
    this.Kit=Kit;
    this.Tolerancia=Tolerancia;
    this.Ciclo_vida=Ciclo_vida;
    this.User_agr=User_agr;
    this.Fec_agr=Fec_agr;
    this.User_mod=User_mod;
    this.Fec_mod=Fec_mod;
    this.Imagen=Imagen;
    this.Noserie=Noserie;
    this.Noparte=Noparte;
    this.Fechamanufactura=Fechamanufactura;
    this.Capturar_aniada=Capturar_aniada;
    this.Control_peso=Control_peso;
    this.Captura_arancel=Captura_arancel;
    this.Es_hardware=Es_hardware;
    this.Largo=Largo;
    this.Alto=Alto;
    this.Ancho=Ancho;
    this.IdProductoBodega=IdProductoBodega;
    this.Propietario=Propietario;
    this.Presentacion=Presentacion;
    this.Clasificacion=Clasificacion;
    this.Familia=Familia;
    this.Marca=Marca;
    this.TipoProducto=TipoProducto;
    this.UnidadMedida=UnidadMedida;
    this.Arancel=Arancel;
    this.Presentaciones=Presentaciones;
    this.Codigos_Barra=Codigos_Barra;
    this.Parametros=Parametros;
    this.Stock=Stock;
    this.IsNew=IsNew;
    this.Tag=Tag;
    this.IdPresentacionOrigen=IdPresentacionOrigen;
    this.IdPresentacionDestino=IdPresentacionDestino;
    this.Factor=Factor;
    this.ExistenciaUMBas=ExistenciaUMBas;
    this.Lote=Lote;
    this.FechaVence=FechaVence;
    this.Cantidad=Cantidad;
    this.IdUnidadMedidaCobro = IdUnidadMedidaCobro;
    this.IdTipoEtiqueta = IdTipoEtiqueta;
  }

  public clsBeProducto(int IdProducto,int IdPropietario,int IdClasificacion,int IdFamilia,
                       int IdMarca,int IdTipoProducto,int IdUnidadMedidaBasica,int IdCamara,
                       int IdTipoRotacion,int IdPerfilSerializado,int IdIndiceRotacion,int IdSimbologia,
                       int IdArancel,String Codigo,String Nombre,String Codigo_barra,
                       double Precio,double Existencia_min,double Existencia_max,double Costo,
                       double Peso_referencia,double Peso_tolerancia,double Temperatura_referencia,double Temperatura_tolerancia,
                       boolean Activo,boolean Serializado,boolean Genera_lote,boolean Genera_lp,
                       boolean Control_vencimiento,boolean Control_lote,boolean Peso_recepcion,boolean Peso_despacho,
                       boolean Temperatura_recepcion,boolean Temperatura_despacho,boolean Materia_prima,boolean Kit,
                       int Tolerancia,int Ciclo_vida,String User_agr,String Fec_agr,
                       String User_mod,String Fec_mod,String Imagen,String Noserie,
                       String Noparte,boolean Fechamanufactura,boolean Capturar_aniada,boolean Control_peso,
                       boolean Captura_arancel,boolean Es_hardware,double Largo,double Alto,
                       double Ancho,int IdProductoBodega,clsBePropietarios Propietario,clsBeProducto_Presentacion Presentacion,
                       clsBeProducto_clasificacion Clasificacion,clsBeProducto_familia Familia,clsBeProducto_marca Marca,clsBeProducto_tipo TipoProducto,
                       clsBeUnidad_medida UnidadMedida,clsBeArancel Arancel,clsBeProducto_PresentacionList Presentaciones,clsBeProducto_codigos_barraList Codigos_Barra,
                       clsBeProducto_parametrosList Parametros,clsBeVW_stock_res Stock,boolean IsNew,Object Tag,
                       int IdPresentacionOrigen,int IdPresentacionDestino,double Factor,double ExistenciaUMBas,
                       String Lote,String FechaVence,double Cantidad, int IdUnidadMedidaCobro, int IdTipoEtiqueta) {

    this.IdProducto=IdProducto;
    this.IdPropietario=IdPropietario;
    this.IdClasificacion=IdClasificacion;
    this.IdFamilia=IdFamilia;
    this.IdMarca=IdMarca;
    this.IdTipoProducto=IdTipoProducto;
    this.IdUnidadMedidaBasica=IdUnidadMedidaBasica;
    this.IdCamara=IdCamara;
    this.IdTipoRotacion=IdTipoRotacion;
    this.IdPerfilSerializado=IdPerfilSerializado;
    this.IdIndiceRotacion=IdIndiceRotacion;
    this.IdSimbologia=IdSimbologia;
    this.IdArancel=IdArancel;
    this.Codigo=Codigo;
    this.Nombre=Nombre;
    this.Codigo_barra=Codigo_barra;
    this.Precio=Precio;
    this.Existencia_min=Existencia_min;
    this.Existencia_max=Existencia_max;
    this.Costo=Costo;
    this.Peso_referencia=Peso_referencia;
    this.Peso_tolerancia=Peso_tolerancia;
    this.Temperatura_referencia=Temperatura_referencia;
    this.Temperatura_tolerancia=Temperatura_tolerancia;
    this.Activo=Activo;
    this.Serializado=Serializado;
    this.Genera_lote=Genera_lote;
    this.Genera_lp=Genera_lp;
    this.Control_vencimiento=Control_vencimiento;
    this.Control_lote=Control_lote;
    this.Peso_recepcion=Peso_recepcion;
    this.Peso_despacho=Peso_despacho;
    this.Temperatura_recepcion=Temperatura_recepcion;
    this.Temperatura_despacho=Temperatura_despacho;
    this.Materia_prima=Materia_prima;
    this.Kit=Kit;
    this.Tolerancia=Tolerancia;
    this.Ciclo_vida=Ciclo_vida;
    this.User_agr=User_agr;
    this.Fec_agr=Fec_agr;
    this.User_mod=User_mod;
    this.Fec_mod=Fec_mod;
    this.Imagen=Imagen;
    this.Noserie=Noserie;
    this.Noparte=Noparte;
    this.Fechamanufactura=Fechamanufactura;
    this.Capturar_aniada=Capturar_aniada;
    this.Control_peso=Control_peso;
    this.Captura_arancel=Captura_arancel;
    this.Es_hardware=Es_hardware;
    this.Largo=Largo;
    this.Alto=Alto;
    this.Ancho=Ancho;
    this.IdProductoBodega=IdProductoBodega;
    this.Propietario=Propietario;
    this.Presentacion=Presentacion;
    this.Clasificacion=Clasificacion;
    this.Familia=Familia;
    this.Marca=Marca;
    this.TipoProducto=TipoProducto;
    this.UnidadMedida=UnidadMedida;
    this.Arancel=Arancel;
    this.Presentaciones=Presentaciones;
    this.Codigos_Barra=Codigos_Barra;
    this.Parametros=Parametros;
    this.Stock=Stock;
    this.IsNew=IsNew;
    this.Tag=Tag;
    this.IdPresentacionOrigen=IdPresentacionOrigen;
    this.IdPresentacionDestino=IdPresentacionDestino;
    this.Factor=Factor;
    this.ExistenciaUMBas=ExistenciaUMBas;
    this.Lote=Lote;
    this.FechaVence=FechaVence;
    this.Cantidad=Cantidad;
    this.IdUnidadMedidaCobro = IdUnidadMedidaCobro;
    this.IdTipoEtiqueta = IdTipoEtiqueta;
  }


  public int getIdProducto() {
    return IdProducto;
  }
  public void setIdProducto(int value) {
    IdProducto=value;
  }
  public int getIdPropietario() {
    return IdPropietario;
  }
  public void setIdPropietario(int value) {
    IdPropietario=value;
  }
  public int getIdClasificacion() {
    return IdClasificacion;
  }
  public void setIdClasificacion(int value) {
    IdClasificacion=value;
  }
  public int getIdFamilia() {
    return IdFamilia;
  }
  public void setIdFamilia(int value) {
    IdFamilia=value;
  }
  public int getIdMarca() {
    return IdMarca;
  }
  public void setIdMarca(int value) {
    IdMarca=value;
  }
  public int getIdTipoProducto() {
    return IdTipoProducto;
  }
  public void setIdTipoProducto(int value) {
    IdTipoProducto=value;
  }
  public int getIdUnidadMedidaBasica() {
    return IdUnidadMedidaBasica;
  }
  public void setIdUnidadMedidaBasica(int value) {
    IdUnidadMedidaBasica=value;
  }
  public int getIdCamara() {
    return IdCamara;
  }
  public void setIdCamara(int value) {
    IdCamara=value;
  }
  public int getIdTipoRotacion() {
    return IdTipoRotacion;
  }
  public void setIdTipoRotacion(int value) {
    IdTipoRotacion=value;
  }
  public int getIdPerfilSerializado() {
    return IdPerfilSerializado;
  }
  public void setIdPerfilSerializado(int value) {
    IdPerfilSerializado=value;
  }
  public int getIdIndiceRotacion() {
    return IdIndiceRotacion;
  }
  public void setIdIndiceRotacion(int value) {
    IdIndiceRotacion=value;
  }
  public int getIdSimbologia() {
    return IdSimbologia;
  }
  public void setIdSimbologia(int value) {
    IdSimbologia=value;
  }
  public int getIdArancel() {
    return IdArancel;
  }
  public void setIdArancel(int value) {
    IdArancel=value;
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
  public String getCodigo_barra() {
    return Codigo_barra;
  }
  public void setCodigo_barra(String value) {
    Codigo_barra=value;
  }
  public double getPrecio() {
    return Precio;
  }
  public void setPrecio(double value) {
    Precio=value;
  }
  public double getExistencia_min() {
    return Existencia_min;
  }
  public void setExistencia_min(double value) {
    Existencia_min=value;
  }
  public double getExistencia_max() {
    return Existencia_max;
  }
  public void setExistencia_max(double value) {
    Existencia_max=value;
  }
  public double getCosto() {
    return Costo;
  }
  public void setCosto(double value) {
    Costo=value;
  }
  public double getPeso_referencia() {
    return Peso_referencia;
  }
  public void setPeso_referencia(double value) {
    Peso_referencia=value;
  }
  public double getPeso_tolerancia() {
    return Peso_tolerancia;
  }
  public void setPeso_tolerancia(double value) {
    Peso_tolerancia=value;
  }
  public double getTemperatura_referencia() {
    return Temperatura_referencia;
  }
  public void setTemperatura_referencia(double value) {
    Temperatura_referencia=value;
  }
  public double getTemperatura_tolerancia() {
    return Temperatura_tolerancia;
  }
  public void setTemperatura_tolerancia(double value) {
    Temperatura_tolerancia=value;
  }
  public boolean getActivo() {
    return Activo;
  }
  public void setActivo(boolean value) {
    Activo=value;
  }
  public boolean getSerializado() {
    return Serializado;
  }
  public void setSerializado(boolean value) {
    Serializado=value;
  }
  public boolean getGenera_lote() {
    return Genera_lote;
  }
  public void setGenera_lote(boolean value) {
    Genera_lote=value;
  }
  public boolean getGenera_lp() {
    return Genera_lp;
  }
  public void setGenera_lp(boolean value) {
    Genera_lp=value;
  }
  public boolean getControl_vencimiento() {
    return Control_vencimiento;
  }
  public void setControl_vencimiento(boolean value) {
    Control_vencimiento=value;
  }
  public boolean getControl_lote() {
    return Control_lote;
  }
  public void setControl_lote(boolean value) {
    Control_lote=value;
  }
  public boolean getPeso_recepcion() {
    return Peso_recepcion;
  }
  public void setPeso_recepcion(boolean value) {
    Peso_recepcion=value;
  }
  public boolean getPeso_despacho() {
    return Peso_despacho;
  }
  public void setPeso_despacho(boolean value) {
    Peso_despacho=value;
  }
  public boolean getTemperatura_recepcion() {
    return Temperatura_recepcion;
  }
  public void setTemperatura_recepcion(boolean value) {
    Temperatura_recepcion=value;
  }
  public boolean getTemperatura_despacho() {
    return Temperatura_despacho;
  }
  public void setTemperatura_despacho(boolean value) {
    Temperatura_despacho=value;
  }
  public boolean getMateria_prima() {
    return Materia_prima;
  }
  public void setMateria_prima(boolean value) {
    Materia_prima=value;
  }
  public boolean getKit() {
    return Kit;
  }
  public void setKit(boolean value) {
    Kit=value;
  }
  public int getTolerancia() {
    return Tolerancia;
  }
  public void setTolerancia(int value) {
    Tolerancia=value;
  }
  public int getCiclo_vida() {
    return Ciclo_vida;
  }
  public void setCiclo_vida(int value) {
    Ciclo_vida=value;
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
  public String getImagen() {
    return Imagen;
  }
  public void setImagen(String value) {
    Imagen=value;
  }
  public String getNoserie() {
    return Noserie;
  }
  public void setNoserie(String value) {
    Noserie=value;
  }
  public String getNoparte() {
    return Noparte;
  }
  public void setNoparte(String value) {
    Noparte=value;
  }
  public boolean getFechamanufactura() {
    return Fechamanufactura;
  }
  public void setFechamanufactura(boolean value) {
    Fechamanufactura=value;
  }
  public boolean getCapturar_aniada() {
    return Capturar_aniada;
  }
  public void setCapturar_aniada(boolean value) {
    Capturar_aniada=value;
  }
  public boolean getControl_peso() {
    return Control_peso;
  }
  public void setControl_peso(boolean value) {
    Control_peso=value;
  }
  public boolean getCaptura_arancel() {
    return Captura_arancel;
  }
  public void setCaptura_arancel(boolean value) {
    Captura_arancel=value;
  }
  public boolean getEs_hardware() {
    return Es_hardware;
  }
  public void setEs_hardware(boolean value) {
    Es_hardware=value;
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
  public double getAncho() {
    return Ancho;
  }
  public void setAncho(double value) {
    Ancho=value;
  }
  public int getIdProductoBodega() {
    return IdProductoBodega;
  }
  public void setIdProductoBodega(int value) {
    IdProductoBodega=value;
  }
  public clsBePropietarios getPropietario() {
    return Propietario;
  }
  public void setPropietario(clsBePropietarios value) {
    Propietario=value;
  }
  public clsBeProducto_Presentacion getPresentacion() {
    return Presentacion;
  }
  public void setPresentacion(clsBeProducto_Presentacion value) {
    Presentacion=value;
  }
  public clsBeProducto_clasificacion getClasificacion() {
    return Clasificacion;
  }
  public void setClasificacion(clsBeProducto_clasificacion value) {
    Clasificacion=value;
  }
  public clsBeProducto_familia getFamilia() {
    return Familia;
  }
  public void setFamilia(clsBeProducto_familia value) {
    Familia=value;
  }
  public clsBeProducto_marca getMarca() {
    return Marca;
  }
  public void setMarca(clsBeProducto_marca value) {
    Marca=value;
  }
  public clsBeProducto_tipo getTipoProducto() {
    return TipoProducto;
  }
  public void setTipoProducto(clsBeProducto_tipo value) {
    TipoProducto=value;
  }
  public clsBeUnidad_medida getUnidadMedida() {
    return UnidadMedida;
  }
  public void setUnidadMedida(clsBeUnidad_medida value) {
    UnidadMedida=value;
  }
  public clsBeArancel getArancel() {
    return Arancel;
  }
  public void setArancel(clsBeArancel value) {
    Arancel=value;
  }
  public clsBeProducto_PresentacionList getPresentaciones() {
    return Presentaciones;
  }
  public void setPresentaciones(clsBeProducto_PresentacionList value) {
    Presentaciones=value;
  }
  public clsBeProducto_codigos_barraList getCodigos_Barra() {
    return Codigos_Barra;
  }
  public void setCodigos_Barra(clsBeProducto_codigos_barraList value) {
    Codigos_Barra=value;
  }
  public clsBeProducto_parametrosList getParametros() {
    return Parametros;
  }
  public void setParametros(clsBeProducto_parametrosList value) {
    Parametros=value;
  }
  public clsBeVW_stock_res getStock() {
    return Stock;
  }
  public void setStock(clsBeVW_stock_res value) {
    Stock=value;
  }
  public boolean getIsNew() {
    return IsNew;
  }
  public void setIsNew(boolean value) {
    IsNew=value;
  }
  public Object getTag() {
    return Tag;
  }
  public void setTag(Object value) {
    Tag=value;
  }
  public int getIdPresentacionOrigen() {
    return IdPresentacionOrigen;
  }
  public void setIdPresentacionOrigen(int value) {
    IdPresentacionOrigen=value;
  }
  public int getIdPresentacionDestino() {
    return IdPresentacionDestino;
  }
  public void setIdPresentacionDestino(int value) {
    IdPresentacionDestino=value;
  }
  public double getFactor() {
    return Factor;
  }
  public void setFactor(double value) {
    Factor=value;
  }
  public double getExistenciaUMBas() {
    return ExistenciaUMBas;
  }
  public void setExistenciaUMBas(double value) {
    ExistenciaUMBas=value;
  }
  public String getLote() {
    return Lote;
  }
  public void setLote(String value) {
    Lote=value;
  }
  public String getFechaVence() {
    return FechaVence;
  }
  public void setFechaVence(String value) {
    FechaVence=value;
  }
  public double getCantidad() {
    return Cantidad;
  }
  public void setCantidad(double value) {
    Cantidad=value;
  }
  public int getIdUnidadMedidaCobro() {
    return IdUnidadMedidaCobro;
  }
  public void setIdUnidadMedidaCobro(int value) {
    IdUnidadMedidaCobro=value;
  }
  public int getIdTipoEtiqueta() {
    return IdTipoEtiqueta;
  }
  public void setIdTipoEtiqueta(int value) {
    IdTipoEtiqueta=value;
  }

}