package com.dts.classes.Transacciones.Inventario.Inventario_Ciclico;

import org.simpleframework.xml.Element;

import java.util.Date;

public class clsBeTrans_inv_ciclico {

    @Element(required=false) public int IdInvCiclico = 0;
    @Element(required=false) public int Idinventarioenc = 0;
    @Element(required=false) public int IdStock=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdProductoEstado=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUbicacion =0;
    @Element(required=false) public boolean EsNuevo = false;
    @Element(required=false) public String Lote_stock ="";
    @Element(required=false) public String Lote= "";
    @Element(required=false) public String Fecha_vence_stock="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_vence="1900-01-01T00:00:00";
    @Element(required=false) public double Cant_stock = 0.00;
    @Element(required=false) public double Cantidad = 0.00;
    @Element(required=false) public double Cant_reconteo = 0.00;
    @Element(required=false) public double Peso_stock =0.00;
    @Element(required=false) public double Peso =0.00;
    @Element(required=false) public double Peso_reconteo = 0.00;
    @Element(required=false) public int Idoperador=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public int IdProductoEst_nuevo=0;
    @Element(required=false) public int IdPresentacion_nuevo =0;
    @Element(required=false) public int IdUbicacion_nuevo =0;
    @Element(required=false) public boolean EsPallet= false;
    @Element(required=false) public String lic_plate="";

    @Element(required=false) public String Fec_Mod ="1900-01-01T00:00:00";
    @Element(required=false) public String Ubicacion ="";
    @Element(required=false) public  String Tramo ="";
    @Element(required=false) public String Estado ="";
    @Element(required=false) public String Codigo ="";
    @Element(required=false) public String Presentacion ="";
    @Element(required=false) public String UnidadMedida ="";
    @Element(required=false) public String Producto ="";
    @Element(required=false) public String Operador ="";
    @Element(required=false) public int IdPropietario = 0;
    @Element(required=false) public int IdClasificacion = 0;
    @Element(required=false) public int IdFamilia = 0;
    @Element(required=false) public int IdUnidadMedida = 0;
    @Element(required=false) public int IdTramo = 0;
    @Element(required=false) public double Recepciones =0.00;
    @Element(required=false) public double Despachos = 0.00;
    @Element(required=false) public int IdProducto =0;
    @Element(required=false) public double EntradasSalidas =0.00;
    @Element(required=false) public String TipoProducto ="";
    @Element(required=false) public double Factor =0.00;

    public clsBeTrans_inv_ciclico(){}

    public clsBeTrans_inv_ciclico(int IdInvCiclico,int Idinventarioenc,int IdStock,int IdProductoBodega,int IdProductoEstado,int IdPresentacion,
    int IdUbicacion,boolean EsNuevo,String Lote_stock,String Lote,String Fecha_vence_stock,String Fecha_vence,double Cant_stock,double Cantidad,
                                  double Cant_reconteo,double Peso_stock,double Peso,double Peso_reconteo,int Idoperador,String User_agr,String Fec_agr,
                                  int IdProductoEst_nuevo,int IdPresentacion_nuevo,int IdUbicacion_nuevo,boolean EsPallet,String lic_plate,
                                  String Fec_Mod, String Ubicacion, String Tramo, String Estado, String Codigo, String Presentacion,
                                  String UnidadMedida, String Producto, String Operador, int IdPropietario, int IdClasificacion, int IdFamilia,
                                  int IdUnidadMedida, int IdTramo, double Recepciones, double Despachos, int IdProducto, double EntradasSalidas,
                                  String TipoProducto, double Factor
        ){

        this.IdInvCiclico=IdInvCiclico;
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
        this.User_agr=User_agr;
        this.Idoperador=Idoperador;
        this.Fec_agr=Fec_agr;
        this.IdProductoEst_nuevo=IdProductoEst_nuevo;
        this.IdPresentacion_nuevo=IdPresentacion_nuevo;
        this.IdUbicacion_nuevo=IdUbicacion_nuevo;
        this.EsPallet=EsPallet;
        this.lic_plate=lic_plate;

        this.Fec_Mod = Fec_Mod;
        this.Ubicacion = Ubicacion;
        this.Tramo = Tramo;
        this.Estado = Estado;
        this.Codigo = Codigo;
        this.Presentacion = Presentacion;
        this.UnidadMedida = UnidadMedida;
        this.Producto = Producto;
        this.Operador = Operador;
        this.IdPropietario = IdPropietario;
        this.IdClasificacion = IdClasificacion;
        this.IdFamilia = IdFamilia;
        this.IdUnidadMedida =IdUnidadMedida;
        this.IdTramo = IdTramo;
        this.Recepciones = Recepciones;
        this.Despachos = Despachos;
        this.IdProducto = IdProducto;
        this.EntradasSalidas = EntradasSalidas;
        this.TipoProducto = TipoProducto;
        this.Factor = Factor;


    }

    public int getidinvciclico() {
        return IdInvCiclico;
    }
    public void setidinvciclico(int value) {
        IdInvCiclico=value;
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
