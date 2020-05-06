package com.dts.classes.Transacciones.Inventario;


import com.dts.classes.Mantenimientos.Bodega.clsBeBodega;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Propietario.Propietario.clsBePropietarios;
import com.dts.classes.Transacciones.Inventario.TipoConteo.clsBeTipoConteo;
import com.dts.classes.Transacciones.Inventario.TipoInventario.clsBeTipoInventario;

import org.simpleframework.xml.Element;

public class clsBeTrans_inv_enc {

    @Element(required=false) public int Idinventarioenc=0;
    @Element(required=false) public int Idpropietario=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdTipoInventario=0;
    @Element(required=false) public int Tipo_Conteo_Producto=0;
    @Element(required=false) public boolean Doble_verificacion=false;
    @Element(required=false) public String Fecha="1900-01-01T00:00:01";
    @Element(required=false) public String Fecha_Ultimo_Inventario="1900-01-01T00:00:01";
    @Element(required=false) public String Estado="";
    @Element(required=false) public boolean Inicial=false;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Regularizado=false;
    @Element(required=false) public String Hora_ini="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_fin="1900-01-01T00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean EsSistema=false;
    @Element(required=false) public boolean Cambia_Ubicacion=false;
    @Element(required=false) public boolean Mostrar_Cantidad_Teorica_hh=false;
    @Element(required=false) public int IdProductoFamilia=0;
    @Element(required=false) public int IdBodegaVirtual=0;
    @Element(required=false) public boolean Ajuste_Por_Inventario=false;
    @Element(required=false) public boolean Capturar_no_existente=false;
    @Element(required=false) public clsBePropietarios Propietario=new clsBePropietarios();
    @Element(required=false) public clsBeBodega Bodega=new clsBeBodega();
    @Element(required=false) public clsBeTipoConteo TipoConteo=new clsBeTipoConteo();
    @Element(required=false) public clsBeTipoInventario TipoInv=new clsBeTipoInventario();
    @Element(required=false) public int IdProducto=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdInventarioDet=0;
    @Element(required=false) public int IdInventarioRes=0;
    @Element(required=false) public int IdTramo=0;
    @Element(required=false) public clsBeBodega_ubicacion Ubicacion=new clsBeBodega_ubicacion();
    @Element(required=false) public String UbicacionCompleta="";
    @Element(required=false) public String Tramo="";
    @Element(required=false) public String Producto="";
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Presentacion="";
    @Element(required=false) public double Detalle=0;
    @Element(required=false) public double Resumen=0;
    @Element(required=false) public double Stock=0;
    @Element(required=false) public double Peso=0;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public String EstadoResumen="";
    @Element(required=false) public String EstadoDetalle="";
    @Element(required=false) public String OperadorConteo="";
    @Element(required=false) public String OperadorVerifica="";
    @Element(required=false) public String FechaConteo="1900-01-01T00:00:01";
    @Element(required=false) public String FechaVence="1900-01-01T00:00:01";
    @Element(required=false) public String FechaVerifica="1900-01-01T00:00:01";
    @Element(required=false) public String Lote="";
    @Element(required=false) public String UMBas="";
    @Element(required=false) public String Transcurrido="";


    public clsBeTrans_inv_enc() {
    }

    public clsBeTrans_inv_enc(int Idinventarioenc,int Idpropietario,int IdBodega,int IdTipoInventario,
                              int Tipo_Conteo_Producto,boolean Doble_verificacion,String Fecha,String Fecha_Ultimo_Inventario,
                              String Estado,boolean Inicial,boolean Activo,boolean Regularizado,
                              String Hora_ini,String Hora_fin,String User_agr,String Fec_agr,
                              String User_mod,String Fec_mod,boolean EsSistema,boolean Cambia_Ubicacion,
                              boolean Mostrar_Cantidad_Teorica_hh,int IdProductoFamilia,int IdBodegaVirtual,boolean Ajuste_Por_Inventario,
                              boolean Capturar_no_existente,clsBePropietarios Propietario,clsBeBodega Bodega,clsBeTipoConteo TipoConteo,
                              clsBeTipoInventario TipoInv,int IdProducto,int IdPresentacion,int IdInventarioDet,
                              int IdInventarioRes,int IdTramo,clsBeBodega_ubicacion Ubicacion,String UbicacionCompleta,
                              String Tramo,String Producto,String Codigo,String Presentacion,
                              double Detalle,double Resumen,double Stock,double Peso,
                              boolean IsNew,String EstadoResumen,String EstadoDetalle,String OperadorConteo,
                              String OperadorVerifica,String FechaConteo,String FechaVence,String FechaVerifica,
                              String Lote,String UMBas) {

        this.Idinventarioenc=Idinventarioenc;
        this.Idpropietario=Idpropietario;
        this.IdBodega=IdBodega;
        this.IdTipoInventario=IdTipoInventario;
        this.Tipo_Conteo_Producto=Tipo_Conteo_Producto;
        this.Doble_verificacion=Doble_verificacion;
        this.Fecha=Fecha;
        this.Fecha_Ultimo_Inventario=Fecha_Ultimo_Inventario;
        this.Estado=Estado;
        this.Inicial=Inicial;
        this.Activo=Activo;
        this.Regularizado=Regularizado;
        this.Hora_ini=Hora_ini;
        this.Hora_fin=Hora_fin;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.EsSistema=EsSistema;
        this.Cambia_Ubicacion=Cambia_Ubicacion;
        this.Mostrar_Cantidad_Teorica_hh=Mostrar_Cantidad_Teorica_hh;
        this.IdProductoFamilia=IdProductoFamilia;
        this.IdBodegaVirtual=IdBodegaVirtual;
        this.Ajuste_Por_Inventario=Ajuste_Por_Inventario;
        this.Capturar_no_existente=Capturar_no_existente;
        this.Propietario=Propietario;
        this.Bodega=Bodega;
        this.TipoConteo=TipoConteo;
        this.TipoInv=TipoInv;
        this.IdProducto=IdProducto;
        this.IdPresentacion=IdPresentacion;
        this.IdInventarioDet=IdInventarioDet;
        this.IdInventarioRes=IdInventarioRes;
        this.IdTramo=IdTramo;
        this.Ubicacion=Ubicacion;
        this.UbicacionCompleta=UbicacionCompleta;
        this.Tramo=Tramo;
        this.Producto=Producto;
        this.Codigo=Codigo;
        this.Presentacion=Presentacion;
        this.Detalle=Detalle;
        this.Resumen=Resumen;
        this.Stock=Stock;
        this.Peso=Peso;
        this.IsNew=IsNew;
        this.EstadoResumen=EstadoResumen;
        this.EstadoDetalle=EstadoDetalle;
        this.OperadorConteo=OperadorConteo;
        this.OperadorVerifica=OperadorVerifica;
        this.FechaConteo=FechaConteo;
        this.FechaVence=FechaVence;
        this.FechaVerifica=FechaVerifica;
        this.Lote=Lote;
        this.UMBas=UMBas;

    }


    public int getIdinventarioenc() {
        return Idinventarioenc;
    }
    public void setIdinventarioenc(int value) {
        Idinventarioenc=value;
    }
    public int getIdpropietario() {
        return Idpropietario;
    }
    public void setIdpropietario(int value) {
        Idpropietario=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdTipoInventario() {
        return IdTipoInventario;
    }
    public void setIdTipoInventario(int value) {
        IdTipoInventario=value;
    }
    public int getTipo_Conteo_Producto() {
        return Tipo_Conteo_Producto;
    }
    public void setTipo_Conteo_Producto(int value) {
        Tipo_Conteo_Producto=value;
    }
    public boolean getDoble_verificacion() {
        return Doble_verificacion;
    }
    public void setDoble_verificacion(boolean value) {
        Doble_verificacion=value;
    }
    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String value) {
        Fecha=value;
    }
    public String getFecha_Ultimo_Inventario() {
        return Fecha_Ultimo_Inventario;
    }
    public void setFecha_Ultimo_Inventario(String value) {
        Fecha_Ultimo_Inventario=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public boolean getInicial() {
        return Inicial;
    }
    public void setInicial(boolean value) {
        Inicial=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getRegularizado() {
        return Regularizado;
    }
    public void setRegularizado(boolean value) {
        Regularizado=value;
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
    public boolean getEsSistema() {
        return EsSistema;
    }
    public void setEsSistema(boolean value) {
        EsSistema=value;
    }
    public boolean getCambia_Ubicacion() {
        return Cambia_Ubicacion;
    }
    public void setCambia_Ubicacion(boolean value) {
        Cambia_Ubicacion=value;
    }
    public boolean getMostrar_Cantidad_Teorica_hh() {
        return Mostrar_Cantidad_Teorica_hh;
    }
    public void setMostrar_Cantidad_Teorica_hh(boolean value) {
        Mostrar_Cantidad_Teorica_hh=value;
    }
    public int getIdProductoFamilia() {
        return IdProductoFamilia;
    }
    public void setIdProductoFamilia(int value) {
        IdProductoFamilia=value;
    }
    public int getIdBodegaVirtual() {
        return IdBodegaVirtual;
    }
    public void setIdBodegaVirtual(int value) {
        IdBodegaVirtual=value;
    }
    public boolean getAjuste_Por_Inventario() {
        return Ajuste_Por_Inventario;
    }
    public void setAjuste_Por_Inventario(boolean value) {
        Ajuste_Por_Inventario=value;
    }
    public boolean getCapturar_no_existente() {
        return Capturar_no_existente;
    }
    public void setCapturar_no_existente(boolean value) {
        Capturar_no_existente=value;
    }
    public clsBePropietarios getPropietario() {
        return Propietario;
    }
    public void setPropietario(clsBePropietarios value) {
        Propietario=value;
    }
    public clsBeBodega getBodega() {
        return Bodega;
    }
    public void setBodega(clsBeBodega value) {
        Bodega=value;
    }
    public clsBeTipoConteo getTipoConteo() {
        return TipoConteo;
    }
    public void setTipoConteo(clsBeTipoConteo value) {
        TipoConteo=value;
    }
    public clsBeTipoInventario getTipoInv() {
        return TipoInv;
    }
    public void setTipoInv(clsBeTipoInventario value) {
        TipoInv=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdInventarioDet() {
        return IdInventarioDet;
    }
    public void setIdInventarioDet(int value) {
        IdInventarioDet=value;
    }
    public int getIdInventarioRes() {
        return IdInventarioRes;
    }
    public void setIdInventarioRes(int value) {
        IdInventarioRes=value;
    }
    public int getIdTramo() {
        return IdTramo;
    }
    public void setIdTramo(int value) {
        IdTramo=value;
    }
    public clsBeBodega_ubicacion getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(clsBeBodega_ubicacion value) {
        Ubicacion=value;
    }
    public String getUbicacionCompleta() {
        return UbicacionCompleta;
    }
    public void setUbicacionCompleta(String value) {
        UbicacionCompleta=value;
    }
    public String getTramo() {
        return Tramo;
    }
    public void setTramo(String value) {
        Tramo=value;
    }
    public String getProducto() {
        return Producto;
    }
    public void setProducto(String value) {
        Producto=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(String value) {
        Presentacion=value;
    }
    public double getDetalle() {
        return Detalle;
    }
    public void setDetalle(double value) {
        Detalle=value;
    }
    public double getResumen() {
        return Resumen;
    }
    public void setResumen(double value) {
        Resumen=value;
    }
    public double getStock() {
        return Stock;
    }
    public void setStock(double value) {
        Stock=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getEstadoResumen() {
        return EstadoResumen;
    }
    public void setEstadoResumen(String value) {
        EstadoResumen=value;
    }
    public String getEstadoDetalle() {
        return EstadoDetalle;
    }
    public void setEstadoDetalle(String value) {
        EstadoDetalle=value;
    }
    public String getOperadorConteo() {
        return OperadorConteo;
    }
    public void setOperadorConteo(String value) {
        OperadorConteo=value;
    }
    public String getOperadorVerifica() {
        return OperadorVerifica;
    }
    public void setOperadorVerifica(String value) {
        OperadorVerifica=value;
    }
    public String getFechaConteo() {
        return FechaConteo;
    }
    public void setFechaConteo(String value) {
        FechaConteo=value;
    }
    public String getFechaVence() {
        return FechaVence;
    }
    public void setFechaVence(String value) {
        FechaVence=value;
    }
    public String getFechaVerifica() {
        return FechaVerifica;
    }
    public void setFechaVerifica(String value) {
        FechaVerifica=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getUMBas() {
        return UMBas;
    }
    public void setUMBas(String value) {
        UMBas=value;
    }

}

