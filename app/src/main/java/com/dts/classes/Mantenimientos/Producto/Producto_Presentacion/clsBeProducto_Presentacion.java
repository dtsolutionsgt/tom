package com.dts.classes.Mantenimientos.Producto.Producto_Presentacion;


import com.dts.classes.Mantenimientos.Producto.Producto_presentacion_tarima.clsBeProducto_presentacion_tarimaList;
import com.dts.classes.Mantenimientos.Producto.Producto_rellenado.clsBeProducto_rellenadoList;

import org.simpleframework.xml.Element;

public class clsBeProducto_Presentacion {

    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdProducto=0;
    @Element(required=false) public String Codigo_barra="";
    @Element(required=false) public String Nombre="";
    @Element(required=false) public boolean Imprime_barra=false;
    @Element(required=false) public double Peso=0;
    @Element(required=false) public double Alto=0;
    @Element(required=false) public double Largo=0;
    @Element(required=false) public double Ancho=0;
    @Element(required=false) public double Factor=0;
    @Element(required=false) public double MinimoExistencia=0;
    @Element(required=false) public double MaximoExistencia=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean EsPallet=false;
    @Element(required=false) public double Precio=0;
    @Element(required=false) public double MinimoPeso=0;
    @Element(required=false) public double MaximoPeso=0;
    @Element(required=false) public double Costo=0;
    @Element(required=false) public double CamasPorTarima=0;
    @Element(required=false) public double CajasPorCama=0;
    @Element(required=false) public boolean Genera_lp_auto=false;
    @Element(required=false) public boolean Permitir_paletizar=false;
    @Element(required=false) public boolean Sistema=false;
    @Element(required=false) public int IdPresentacionPallet=0;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public boolean ExisteStock=false;
    @Element(required=false) public clsBeProducto_presentacion_tarimaList MedidasPorTarima=new clsBeProducto_presentacion_tarimaList();
    @Element(required=false) public clsBeProducto_rellenadoList RellenadoPorUbicacionDePicking=new clsBeProducto_rellenadoList();
    @Element(required=false) public String Codigo="";


    public clsBeProducto_Presentacion() {
    }

    public clsBeProducto_Presentacion(int IdPresentacion,int IdProducto,String Codigo_barra,String Nombre,
                                      boolean Imprime_barra,double Peso,double Alto,double Largo,
                                      double Ancho,double Factor,double MinimoExistencia,double MaximoExistencia,
                                      String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                      boolean Activo,boolean EsPallet,double Precio,double MinimoPeso,
                                      double MaximoPeso,double Costo,double CamasPorTarima,double CajasPorCama,
                                      boolean Genera_lp_auto,boolean Permitir_paletizar,boolean Sistema,int IdPresentacionPallet,
                                      boolean IsNew,boolean ExisteStock,clsBeProducto_presentacion_tarimaList MedidasPorTarima,clsBeProducto_rellenadoList RellenadoPorUbicacionDePicking,
                                      String Codigo
    ) {

        this.IdPresentacion=IdPresentacion;
        this.IdProducto=IdProducto;
        this.Codigo_barra=Codigo_barra;
        this.Nombre=Nombre;
        this.Imprime_barra=Imprime_barra;
        this.Peso=Peso;
        this.Alto=Alto;
        this.Largo=Largo;
        this.Ancho=Ancho;
        this.Factor=Factor;
        this.MinimoExistencia=MinimoExistencia;
        this.MaximoExistencia=MaximoExistencia;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.EsPallet=EsPallet;
        this.Precio=Precio;
        this.MinimoPeso=MinimoPeso;
        this.MaximoPeso=MaximoPeso;
        this.Costo=Costo;
        this.CamasPorTarima=CamasPorTarima;
        this.CajasPorCama=CajasPorCama;
        this.Genera_lp_auto=Genera_lp_auto;
        this.Permitir_paletizar=Permitir_paletizar;
        this.Sistema=Sistema;
        this.IdPresentacionPallet=IdPresentacionPallet;
        this.IsNew=IsNew;
        this.ExisteStock=ExisteStock;
        this.MedidasPorTarima=MedidasPorTarima;
        this.RellenadoPorUbicacionDePicking=RellenadoPorUbicacionDePicking;
        this.Codigo = Codigo;

    }


    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }
    public String getCodigo_barra() {
        return Codigo_barra;
    }
    public void setCodigo_barra(String value) {
        Codigo_barra=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public boolean getImprime_barra() {
        return Imprime_barra;
    }
    public void setImprime_barra(boolean value) {
        Imprime_barra=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
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
    public double getFactor() {
        return Factor;
    }
    public void setFactor(double value) {
        Factor=value;
    }
    public double getMinimoExistencia() {
        return MinimoExistencia;
    }
    public void setMinimoExistencia(double value) {
        MinimoExistencia=value;
    }
    public double getMaximoExistencia() {
        return MaximoExistencia;
    }
    public void setMaximoExistencia(double value) {
        MaximoExistencia=value;
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
    public boolean getEsPallet() {
        return EsPallet;
    }
    public void setEsPallet(boolean value) {
        EsPallet=value;
    }
    public double getPrecio() {
        return Precio;
    }
    public void setPrecio(double value) {
        Precio=value;
    }
    public double getMinimoPeso() {
        return MinimoPeso;
    }
    public void setMinimoPeso(double value) {
        MinimoPeso=value;
    }
    public double getMaximoPeso() {
        return MaximoPeso;
    }
    public void setMaximoPeso(double value) {
        MaximoPeso=value;
    }
    public double getCosto() {
        return Costo;
    }
    public void setCosto(double value) {
        Costo=value;
    }
    public double getCamasPorTarima() {
        return CamasPorTarima;
    }
    public void setCamasPorTarima(double value) {
        CamasPorTarima=value;
    }
    public double getCajasPorCama() {
        return CajasPorCama;
    }
    public void setCajasPorCama(double value) {
        CajasPorCama=value;
    }
    public boolean getGenera_lp_auto() {
        return Genera_lp_auto;
    }
    public void setGenera_lp_auto(boolean value) {
        Genera_lp_auto=value;
    }
    public boolean getPermitir_paletizar() {
        return Permitir_paletizar;
    }
    public void setPermitir_paletizar(boolean value) {
        Permitir_paletizar=value;
    }
    public boolean getSistema() {
        return Sistema;
    }
    public void setSistema(boolean value) {
        Sistema=value;
    }
    public int getIdPresentacionPallet() {
        return IdPresentacionPallet;
    }
    public void setIdPresentacionPallet(int value) {
        IdPresentacionPallet=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public boolean getExisteStock() {
        return ExisteStock;
    }
    public void setExisteStock(boolean value) {
        ExisteStock=value;
    }
    public clsBeProducto_presentacion_tarimaList getMedidasPorTarima() {
        return MedidasPorTarima;
    }
    public void setMedidasPorTarima(clsBeProducto_presentacion_tarimaList value) {
        MedidasPorTarima=value;
    }
    public clsBeProducto_rellenadoList getRellenadoPorUbicacionDePicking() {
        return RellenadoPorUbicacionDePicking;
    }
    public void setRellenadoPorUbicacionDePicking(clsBeProducto_rellenadoList value) {
        RellenadoPorUbicacionDePicking=value;
    }

    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getCodigo() {
        return Codigo;
    }
}

