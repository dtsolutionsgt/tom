package com.dts.classes.Transacciones.Movimiento.Trans_movimiento_pallet;

import org.simpleframework.xml.Element;

public class clsBeTrans_movimiento_pallet{

    @Element(required=false) public int Idmovimientopallet;
    @Element(required=false) public int IdBodega;
    @Element(required=false) public String Lp_origen;
    @Element(required=false) public String Lp_destino;
    @Element(required=false) public String Orientacion;
    @Element(required=false) public String Fecha;
    @Element(required=false) public int Idusuario;

    public clsBeTrans_movimiento_pallet() {
    }

    public clsBeTrans_movimiento_pallet(int Idmovimientopallet,int IdBodega,String Lp_origen,String Lp_destino,
                                        String Orientacion,String Fecha,int Idusuario) {

        this.Idmovimientopallet=Idmovimientopallet;
        this.IdBodega=IdBodega;
        this.Lp_origen=Lp_origen;
        this.Lp_destino=Lp_destino;
        this.Orientacion=Orientacion;
        this.Fecha=Fecha;
        this.Idusuario=Idusuario;

    }


    public int getIdmovimientopallet() {
        return Idmovimientopallet;
    }
    public void setIdmovimientopallet(int value) {
        Idmovimientopallet=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public String getLp_origen() {
        return Lp_origen;
    }
    public void setLp_origen(String value) {
        Lp_origen=value;
    }
    public String getLp_destino() {
        return Lp_destino;
    }
    public void setLp_destino(String value) {
        Lp_destino=value;
    }
    public String getOrientacion() {
        return Orientacion;
    }
    public void setOrientacion(String value) {
        Orientacion=value;
    }
    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String value) {
        Fecha=value;
    }
    public int getIdusuario() {
        return Idusuario;
    }
    public void setIdusuario(int value) {
        Idusuario=value;
    }

}



