package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.Element;

public class clsBe_inv_reconteo_data {

    @Element(required=false) public int NoUbic=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Producto_nombre="";
    @Element(required=false) public String UMBas="";
    @Element(required=false) public String Pres ="";
    @Element(required=false) public Double Cant_Conteo=0.0;
    @Element(required=false) public Double Peso_Conteo=0.0;
    @Element(required=false) public Double Cant_Stock=0.0;
    @Element(required=false) public Double Peso_Stock=0.0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:01";
    @Element(required=false) public int Conteo=0;
    @Element(required=false) public String Ubic_nombre="";
    @Element(required=false) public String Estado="";
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public String Tramo="";
    @Element(required=false) public int IndiceX=0;
    @Element(required=false) public int Nivel=0;
    @Element(required=false) public String Pos="";
    @Element(required=false) public Double Factor=0.0;


    public clsBe_inv_reconteo_data(){}

    public clsBe_inv_reconteo_data(int NoUbic,String Codigo,String Producto_nombre,String UMBas,
                                   String Pres,Double Cant_Conteo,Double Peso_Conteo,Double Cant_Stock,
                                   Double Peso_Stock,String Lote,String Fecha_Vence,int Conteo,
                                   String Ubic_nombre,String Estado,int IdProductoBodega,String Tramo,int IndiceX,
                                   int Nivel,String Pos,Double Factor){

        this.NoUbic = NoUbic;
        this.Codigo = Codigo;
        this.Producto_nombre = Producto_nombre;
        this.UMBas = UMBas;
        this.Pres = Pres;
        this.Cant_Conteo = Cant_Conteo;
        this.Peso_Conteo = Peso_Conteo;
        this.Cant_Stock = Cant_Stock;
        this.Peso_Stock = Peso_Stock;
        this.Lote = Lote;
        this.Fecha_Vence = Fecha_Vence;
        this.Conteo = Conteo;
        this.Ubic_nombre = Ubic_nombre;
        this.Estado = Estado;
        this.IdProductoBodega = IdProductoBodega;
        this.Tramo = Tramo;
        this.IndiceX = IndiceX;
        this.Nivel = Nivel;
        this.Pos = Pos;
        this.Factor = Factor;
    }
}
