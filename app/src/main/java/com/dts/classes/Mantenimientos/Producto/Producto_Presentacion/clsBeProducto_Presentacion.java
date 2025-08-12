package com.dts.classes.Mantenimientos.Producto.Producto_Presentacion;

import com.dts.classes.Mantenimientos.Producto.Producto_presentacion_tarima.clsBeProducto_presentacion_tarima;
import com.dts.classes.Mantenimientos.Producto.Producto_presentacion_tarima.clsBeProducto_presentacion_tarimaList;
import com.dts.classes.Mantenimientos.Producto.Producto_rellenado.clsBeProducto_rellenadoList;
import com.google.gson.annotations.SerializedName;

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

    // Lista dual para XML y JSON
    @Element(required=false) public clsBeProducto_presentacion_tarimaList MedidasPorTarimaXml = new clsBeProducto_presentacion_tarimaList();
    @SerializedName("MedidasPorTarima")
    private clsBeProducto_presentacion_tarimaList MedidasPorTarimaJson = new clsBeProducto_presentacion_tarimaList();

    @Element(required=false) public clsBeProducto_rellenadoList RellenadoPorUbicacionDePicking = new clsBeProducto_rellenadoList();
    @Element(required=false) public String Codigo="";

    public clsBeProducto_Presentacion() {}

    public int getIdPresentacion() {
        return IdPresentacion;
    }

    public void setIdPresentacion(int value) {
        IdPresentacion = value;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int value) {
        IdProducto = value;
    }

    public String getCodigo_barra() {
        return Codigo_barra;
    }

    public void setCodigo_barra(String value) {
        Codigo_barra = value;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String value) {
        Nombre = value;
    }

    public boolean getImprime_barra() {
        return Imprime_barra;
    }

    public void setImprime_barra(boolean value) {
        Imprime_barra = value;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double value) {
        Peso = value;
    }

    public double getAlto() {
        return Alto;
    }

    public void setAlto(double value) {
        Alto = value;
    }

    public double getLargo() {
        return Largo;
    }

    public void setLargo(double value) {
        Largo = value;
    }

    public double getAncho() {
        return Ancho;
    }

    public void setAncho(double value) {
        Ancho = value;
    }

    public double getFactor() {
        return Factor;
    }

    public void setFactor(double value) {
        Factor = value;
    }

    public clsBeProducto_presentacion_tarimaList getMedidasPorTarima() {
        return MedidasPorTarimaXml != null && !MedidasPorTarimaXml.items.isEmpty()
                ? MedidasPorTarimaXml
                : MedidasPorTarimaJson;
    }

    public void setMedidasPorTarima(clsBeProducto_presentacion_tarimaList value) {
        MedidasPorTarimaXml = value;
        MedidasPorTarimaJson = value;
    }

    public void syncMedidasPorTarimaToXml() {
        if ((MedidasPorTarimaXml == null || MedidasPorTarimaXml.items == null || MedidasPorTarimaXml.items.isEmpty())
                && MedidasPorTarimaJson != null) {
            MedidasPorTarimaXml = MedidasPorTarimaJson;
        }
    }

    public clsBeProducto_rellenadoList getRellenadoPorUbicacionDePicking() {
        return RellenadoPorUbicacionDePicking;
    }

    public void setRellenadoPorUbicacionDePicking(clsBeProducto_rellenadoList value) {
        RellenadoPorUbicacionDePicking = value;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String value) {
        Codigo = value;
    }

    public boolean getEsPallet() {
        return EsPallet;
    }

    public void setEsPallet(boolean value) {
        EsPallet = value;
    }

    public String toString() {
        return Nombre;
    }
}