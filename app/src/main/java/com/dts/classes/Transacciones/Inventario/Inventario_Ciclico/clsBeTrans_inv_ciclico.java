package com.dts.classes.Transacciones.Inventario.Inventario_Ciclico;

import org.simpleframework.xml.Element;

import java.util.Date;

public class clsBeTrans_inv_ciclico {

    @Element(required=false) public int idinvciclico;
    @Element(required=false) public int idinventarioenc;
    @Element(required=false) public int IdStock;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int IdProductoEstado;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdUbicacion;
    @Element(required=false) public boolean EsNuevo;
    @Element(required=false) public String lote_stock;
    @Element(required=false) public String lote;
    @Element(required=false) public String fecha_vence_stock="";
    @Element(required=false) public String fecha_vence;
    @Element(required=false) public double cant_stock;
    @Element(required=false) public double cantidad;
    @Element(required=false) public double cant_reconteo;
    @Element(required=false) public double peso_stock;
    @Element(required=false) public double peso;
    @Element(required=false) public double peso_reconteo;
    @Element(required=false) public int idoperador;
    @Element(required=false) public String user_agr;
    @Element(required=false) public String fec_agr;
    @Element(required=false) public int IdProductoEst_nuevo;
    @Element(required=false) public int IdPresentacion_nuevo;
    @Element(required=false) public int IdUbicacion_nuevo;
    @Element(required=false) public boolean EsPallet;
    @Element(required=false) public String lic_plate;

    public clsBeTrans_inv_ciclico(){}

    public clsBeTrans_inv_ciclico(int idinvciclico,int idinventarioenc,int IdStock,int IdProductoBodega,int IdProductoEstado,int IdPresentacion,
    int IdUbicacion,boolean EsNuevo,String lote_stock,String lote,String fecha_vence_stock,String fecha_vence,double cant_stock,double cantidad,
                                  double cant_reconteo,double peso_stock,double peso,double peso_reconteo,int idoperador,String user_agr,String fec_agr,
                                  int IdProductoEst_nuevo,int IdPresentacion_nuevo,int IdUbicacion_nuevo,boolean EsPallet,String lic_plate){

        this.idinvciclico=idinvciclico;
        this.idinventarioenc=idinventarioenc;
        this.IdStock=IdStock;
        this.IdProductoBodega=IdProductoBodega;
        this.IdProductoEstado=IdProductoEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUbicacion=IdUbicacion;
        this.EsNuevo=EsNuevo;
        this.lote_stock=lote_stock;
        this.lote=lote;
        this.fecha_vence_stock=fecha_vence_stock;
        this.fecha_vence=fecha_vence;
        this.cant_stock=cant_stock;
        this.cantidad=cantidad;
        this.cant_reconteo=cant_reconteo;
        this.peso_stock=peso_stock;
        this.peso=peso;
        this.peso_reconteo=peso_reconteo;
        this.user_agr=user_agr;
        this.idoperador=idoperador;
        this.fec_agr=fec_agr;
        this.IdProductoEst_nuevo=IdProductoEst_nuevo;
        this.IdPresentacion_nuevo=IdPresentacion_nuevo;
        this.IdUbicacion_nuevo=IdUbicacion_nuevo;
        this.EsPallet=EsPallet;
        this.lic_plate=lic_plate;
    }

    public int getidinvciclico() {
        return idinvciclico;
    }
    public void setidinvciclico(int value) {
        idinvciclico=value;
    }

    public int getidinventarioenc() {
        return idinventarioenc;
    }
    public void setidinventarioenc(int value) {
        idinventarioenc=value;
    }

    public int getIdStock() {
        return IdStock;
    }
    public void setIdStock(int value) {
        IdStock=value;
    }

    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }

    public int getIdProductoEstado() {
        return IdProductoEstado;
    }
    public void setIdProductoEstado(int value) {
        IdProductoEstado=value;
    }

    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }

    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }

    public boolean getEsNuevo() {
        return EsNuevo;
    }
    public void setEsNuevo(boolean value) {
        EsNuevo=value;
    }

    public String getlote_stock() {
        return lote_stock;
    }
    public void setlote_stock(String value) {
        lote_stock=value;
    }

    public String getlote() {
        return lote;
    }
    public void setlote(String value) {
        lote=value;
    }

    public String getfecha_vence_stock() {
        return fecha_vence_stock;
    }
    public void setfecha_vence_stock(String value) {
        fecha_vence_stock=value;
    }

    public String getfecha_vence() {
        return fecha_vence;
    }
    public void setfecha_vence(String value) {
        fecha_vence=value;
    }

    public double getcant_stock() {
        return cant_stock;
    }
    public void setcant_stock(double value) {
        cant_stock=value;
    }

    public double getcantidad() {
        return cantidad;
    }
    public void setcantidad(double value) {
        cantidad=value;
    }

    public double getcant_reconteo() {
        return cant_reconteo;
    }
    public void setcant_reconteo(double value) {
        cant_reconteo=value;
    }

    public double getpeso_stock() {
        return peso_stock;
    }
    public void setpeso_stock(double value) {
        peso_stock=value;
    }

    public double getpeso() {
        return peso;
    }
    public void setpeso(double value) {
        peso=value;
    }

    public double getpeso_reconteo() {
        return peso_reconteo;
    }
    public void setpeso_reconteo(double value) {
        peso_reconteo=value;
    }

    public int getidoperador() {
        return idoperador;
    }
    public void setidoperador(int value) {
        idoperador=value;
    }

    public String getuser_agr() {
        return user_agr;
    }
    public void setuser_agr(String value) {
        user_agr=value;
    }

    public String getfec_agr() {
        return fec_agr;
    }
    public void setfec_agr(String value) {
        fec_agr=value;
    }

    public int getIdProductoEst_nuevo() {
        return IdProductoEst_nuevo;
    }
    public void setIdProductoEst_nuevo(int value) {
        IdProductoEst_nuevo=value;
    }

    public int getIdPresentacion_nuevo() {
        return IdPresentacion_nuevo;
    }
    public void setIdPresentacion_nuevo(int value) {
        IdPresentacion_nuevo=value;
    }

    public int getIdUbicacion_nuevo() {
        return IdUbicacion_nuevo;
    }
    public void setIdUbicacion_nuevo(int value) {
        IdUbicacion_nuevo=value;
    }

    public boolean getEsPallet() {
        return EsPallet;
    }
    public void setEsPallet(boolean value) {
        EsPallet=value;
    }

    public String getlic_plate() {
        return lic_plate;
    }
    public void setlic_plate(String value) {
        lic_plate=value;
    }

}
