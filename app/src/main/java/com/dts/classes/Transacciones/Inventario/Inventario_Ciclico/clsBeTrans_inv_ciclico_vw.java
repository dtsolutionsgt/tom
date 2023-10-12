package com.dts.classes.Transacciones.Inventario.Inventario_Ciclico;

import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacion;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_ubicacionList;

import org.simpleframework.xml.Element;

public class clsBeTrans_inv_ciclico_vw {

    @Element(required=false) public int Idinvciclico  = 0;
    @Element(required=false) public int Idinventarioenc  = 0;
    @Element(required=false) public int IdStock  = 0;
    @Element(required=false) public int IdProductoBodega  = 0;
    @Element(required=false) public int IdProductoEstado  = 0;
    @Element(required=false) public int IdPresentacion  = 0;
    @Element(required=false) public int IdUbicacion  = 0;
    @Element(required=false) public boolean EsNuevo  = false;
    @Element(required=false) public String Lote_stock  = "";
    @Element(required=false) public String Lote  = "";
    @Element(required=false) public String Fecha_vence_stock  = "1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_vence  = "1900-01-01T00:00:00";
    @Element(required=false) public double Cant_stock  = 0.0;
    @Element(required=false) public double Cantidad  = 0.0;
    @Element(required=false) public double Cant_reconteo  = 0.0;
    @Element(required=false) public double Peso_stock  = 0.0;
    @Element(required=false) public double Peso  = 0.0;
    @Element(required=false) public double Peso_reconteo  = 0.0;
    @Element(required=false) public int Idoperador  = 0;
    @Element(required=false) public String User_agr  = "";
    @Element(required=false) public String Fec_agr  = "1900-01-01T00:00:00";
    @Element(required=false) public int IdTramo  = 0;
    @Element(required=false) public String Estado_nombre  = "";
    @Element(required=false) public String Producto_nombre  = "";
    @Element(required=false) public String Ubic_nombre  = "";
    @Element(required=false) public String Pres_nombre  = "";
    @Element(required=false) public String Unid_nombre  = "";
    @Element(required=false) public boolean Control_peso  = false;
    @Element(required=false) public boolean Genera_lote  = false;
    @Element(required=false) public boolean Control_vencimiento  = false;
    @Element(required=false) public int IdProductoEst_nuevo  = 0;
    @Element(required=false) public int idPresentacion_nuevo  = 0;
    @Element(required=false) public int idreconteo  = 0;
    @Element(required=false) public String Codigo_Producto  = "";
    @Element(required=false) public int Columna  = 0;
    @Element(required=false) public int Nivel  = 0;
    @Element(required=false) public String Posicion  = "";
    @Element(required=false) public int Total  = 0;
    @Element(required=false) public double Factor  = 0.00;
    @Element(required=false) public int IdBodega  = 0;
    @Element(required=false) public clsBeBodega_ubicacion Ubicacion=new clsBeBodega_ubicacion();

    public clsBeTrans_inv_ciclico_vw(){}

    public clsBeTrans_inv_ciclico_vw(int Idinvciclico,int Idinventarioenc,int IdStock,int IdProductoBodega,
                                     int IdProductoEstado,int IdPresentacion,int IdUbicacion,boolean EsNuevo,
                                     String Lote_stock,String Lote,String Fecha_vence_stock,String Fecha_vence,
                                     double Cant_stock,double Cantidad,double Cant_reconteo,double Peso_stock,
                                     double Peso,double Peso_reconteo,int Idoperador,String User_agr,String Fec_agr,
                                     int IdTramo,String Estado_nombre,String Producto_nombre,String Ubic_nombre,
                                     String Pres_nombre,String Unid_nombre,boolean Control_peso,boolean Genera_lote,
                                     boolean Control_vencimiento,int IdProductoEst_nuevo,int idPresentacion_nuevo,
                                     int idreconteo,String Codigo_Producto,int Columna,int Nivel,String Posicion,
                                     int Total,double Factor,int IdBodega, clsBeBodega_ubicacion Ubicacion){


        this.Idinvciclico=Idinvciclico;
        this.Idinventarioenc=Idinventarioenc;
        this.IdStock=IdStock;
        this.IdProductoBodega=IdProductoBodega;
        this.IdProductoEstado=IdProductoEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUbicacion=IdUbicacion;
        this.EsNuevo=EsNuevo;
        this.Lote_stock=Lote_stock;
        this.Lote=Lote;
        this.Fecha_vence_stock=Fecha_vence_stock;
        this.Fecha_vence=Fecha_vence;
        this.Cant_stock=Cant_stock;
        this.Cantidad=Cantidad;
        this.Cant_reconteo=Cant_reconteo;
        this.Peso_stock=Peso_stock;
        this.Peso=Peso;
        this.Peso_reconteo=Peso_reconteo;
        this.Idoperador=Idoperador;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.IdTramo=IdTramo;
        this.Estado_nombre=Estado_nombre;
        this.Producto_nombre=Producto_nombre;
        this.Ubic_nombre=Ubic_nombre;

        this.Pres_nombre=Pres_nombre;
        this.Unid_nombre=Unid_nombre;
        this.Control_peso=Control_peso;
        this.Genera_lote=Genera_lote;
        this.Control_vencimiento=Control_vencimiento;
        this.IdProductoEst_nuevo=IdProductoEst_nuevo;
        this.idPresentacion_nuevo=idPresentacion_nuevo;
        this.idreconteo=idreconteo;
        this.Codigo_Producto=Codigo_Producto;
        this.Columna=Columna;
        this.Nivel=Nivel;
        this.Posicion=Posicion;
        this.Total=Total;
        this.Factor=Factor;
        this.IdBodega=IdBodega;
        this.Ubicacion=Ubicacion;

    }

    public int getIdinvciclico() {
        return Idinvciclico;
    }
    public void setIdinvciclico(int value) {
        Idinvciclico=value;
    }

    public int getidinventarioenc() {
        return Idinventarioenc;
    }
    public void setidinventarioenc(int value) {
        Idinventarioenc=value;
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

    public String getLote_stock() {
        return Lote_stock;
    }
    public void setLote_stock(String value) {
        Lote_stock=value;
    }

    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }

    public String getfecha_vence_stock() {
        return Fecha_vence_stock;
    }
    public void setfecha_vence_stock(String value) {
        Fecha_vence_stock=value;
    }

    public String getfecha_vence() {
        return Fecha_vence;
    }
    public void setfecha_vence(String value) {
        Fecha_vence=value;
    }

    public double getcant_stock() {
        return Cant_stock;
    }
    public void setcant_stock(double value) {
        Cant_stock=value;
    }

    public double getcantidad() {
        return Cantidad;
    }
    public void setcantidad(double value) {
        Cantidad=value;
    }

    public double getcant_reconteo() {
        return Cant_reconteo;
    }
    public void setcant_reconteo(double value) {
        Cant_reconteo=value;
    }

    public double getpeso_stock() {
        return Peso_stock;
    }
    public void setpeso_stock(double value) {
        Peso_stock=value;
    }

    public double getpeso() {
        return Peso;
    }
    public void setpeso(double value) {
        Peso=value;
    }

    public double getpeso_reconteo() {
        return Peso_reconteo;
    }
    public void setpeso_reconteo(double value) {
        Peso_reconteo=value;
    }

    public int getidoperador() {
        return Idoperador;
    }
    public void setidoperador(int value) {
        Idoperador=value;
    }

    public String getuser_agr() {
        return User_agr;
    }
    public void setuser_agr(String value) {
        User_agr=value;
    }

    public String getfec_agr() {
        return Fec_agr;
    }
    public void setfec_agr(String value) {
        Fec_agr=value;
    }

    public int getIdTramo() {
        return IdTramo;
    }
    public void setIdTramo(int value) {
        IdTramo=value;
    }

    public String getEstado_nombre() {
        return Estado_nombre;
    }
    public void setEstado_nombre(String value) {
        Estado_nombre=value;
    }

    public String getProducto_nombre() {
        return Producto_nombre;
    }
    public void setProducto_nombre(String value) {
        Producto_nombre=value;
    }

    public String getUbic_nombre() {
        return Ubic_nombre;
    }
    public void setUbic_nombre(String value) {
        Ubic_nombre=value;
    }

    public String getPres_nombre() {
        return Pres_nombre;
    }
    public void setPres_nombre(String value) {
        Pres_nombre=value;
    }

    public String getUnid_nombre() {
        return Unid_nombre;
    }
    public void setUnid_nombre(String value) {
        Unid_nombre=value;
    }

    public boolean getControl_peso() {
        return Control_peso;
    }
    public void setControl_peso(boolean value) {
        Control_peso=value;
    }

    public boolean getGenera_lote() {
        return Genera_lote;
    }
    public void setGenera_lote(boolean value) {
        Genera_lote=value;
    }

    public boolean getControl_vencimiento() {
        return Control_vencimiento;
    }
    public void setControl_vencimiento(boolean value) {
        Control_vencimiento=value;
    }

    public int getIdProductoEst_nuevo() {
        return IdProductoEst_nuevo;
    }
    public void setIdProductoEst_nuevo(int value) {
        IdProductoEst_nuevo=value;
    }

    public int getidPresentacion_nuevo() {
        return idPresentacion_nuevo;
    }
    public void setidPresentacion_nuevo(int value) {
        idPresentacion_nuevo=value;
    }

    public int getidreconteo() {
        return idreconteo;
    }
    public void setidreconteo(int value) {
        idreconteo=value;
    }

    public String getCodigo_Producto() {
        return Codigo_Producto;
    }
    public void setCodigo_Producto(String value) {
        Codigo_Producto=value;
    }

    public int getColumna() {
        return Columna;
    }
    public void setColumna(int value) {
        Columna=value;
    }

    public int getNivel() {
        return Nivel;
    }
    public void setNivel(int value) {
        Nivel=value;
    }

    public String getPosicion() {
        return Posicion;
    }
    public void setPosicion(String value) {
        Posicion=value;
    }

    public int getTotal() {
        return Total;
    }
    public void setTotal(int value) {
        Total=value;
    }

    public double getFactor() {
        return Factor;
    }
    public void setFactor(double value) {
        Factor=value;
    }

    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }

    public clsBeBodega_ubicacion getUbicaciones() {
        return Ubicacion;
    }
    public void setUbicaciones(clsBeBodega_ubicacion value) {
        Ubicacion=value;
    }

}
