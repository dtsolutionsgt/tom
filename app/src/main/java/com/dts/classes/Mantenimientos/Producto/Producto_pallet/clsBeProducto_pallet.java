package com.dts.classes.Mantenimientos.Producto.Producto_pallet;


import org.simpleframework.xml.Element;

public class clsBeProducto_pallet {

    @Element(required=false) public int IdPallet=0;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdOperadorBodega=0;
    @Element(required=false) public int IdImpresora=0;
    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdRecepcionDet=0;
    @Element(required=false) public String Codigo_Barra="";
    @Element(required=false) public String Codigo_Producto="";
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public boolean Impreso=false;
    @Element(required=false) public int Reimpresiones=0;
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:01";
    @Element(required=false) public String Fecha_ingreso="1900-01-01T00:00:01";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean IsNew=false;

    public clsBeProducto_pallet() {
    }

    public clsBeProducto_pallet(int IdPallet,int IdPropietarioBodega,int IdProductoBodega,int IdPresentacion,
                                int IdOperadorBodega,int IdImpresora,int IdRecepcionEnc,int IdRecepcionDet,
                                String Codigo_Barra,String Codigo_Producto,double Cantidad,String Lote,
                                boolean Impreso,int Reimpresiones,String Fecha_vence,String Fecha_ingreso,
                                String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                boolean Activo,boolean IsNew) {

        this.IdPallet=IdPallet;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProductoBodega=IdProductoBodega;
        this.IdPresentacion=IdPresentacion;
        this.IdOperadorBodega=IdOperadorBodega;
        this.IdImpresora=IdImpresora;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdRecepcionDet=IdRecepcionDet;
        this.Codigo_Barra=Codigo_Barra;
        this.Codigo_Producto=Codigo_Producto;
        this.Cantidad=Cantidad;
        this.Lote=Lote;
        this.Impreso=Impreso;
        this.Reimpresiones=Reimpresiones;
        this.Fecha_Vence=Fecha_vence;
        this.Fecha_ingreso=Fecha_ingreso;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.IsNew = IsNew;

    }


    public int getIdPallet() {
        return IdPallet;
    }
    public void setIdPallet(int value) {
        IdPallet=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdOperadorBodega() {
        return IdOperadorBodega;
    }
    public void setIdOperadorBodega(int value) {
        IdOperadorBodega=value;
    }
    public int getIdImpresora() {
        return IdImpresora;
    }
    public void setIdImpresora(int value) {
        IdImpresora=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdRecepcionDet() {
        return IdRecepcionDet;
    }
    public void setIdRecepcionDet(int value) {
        IdRecepcionDet=value;
    }
    public String getCodigo_Barra() {
        return Codigo_Barra;
    }
    public void setCodigo_Barra(String value) {
        Codigo_Barra=value;
    }
    public String getCodigo_Producto() {
        return Codigo_Producto;
    }
    public void setCodigo_Producto(String value) {
        Codigo_Producto=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public boolean getImpreso() {
        return Impreso;
    }
    public void setImpreso(boolean value) {
        Impreso=value;
    }
    public int getReimpresiones() {
        return Reimpresiones;
    }
    public void setReimpresiones(int value) {
        Reimpresiones=value;
    }
    public String getFecha_vence() {
        return Fecha_Vence;
    }
    public void setFecha_vence(String value) {
        Fecha_Vence=value;
    }
    public String getFecha_ingreso() {
        return Fecha_ingreso;
    }
    public void setFecha_ingreso(String value) {
        Fecha_ingreso=value;
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
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }

}

