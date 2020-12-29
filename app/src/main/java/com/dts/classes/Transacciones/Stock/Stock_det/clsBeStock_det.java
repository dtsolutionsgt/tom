package com.dts.classes.Transacciones.Stock.Stock_det;

import org.simpleframework.xml.Element;

public class clsBeStock_det {

    @Element(required=false) public int IdStock=0;
    @Element(required=false) public int Posiciones=0;


    public clsBeStock_det() {
    }

    public clsBeStock_det(int IdStock,int Posiciones) {

        this.IdStock=IdStock;
        this.Posiciones=Posiciones;

    }


    public int getIdStock() {
        return IdStock;
    }
    public void setIdStock(int value) {
        IdStock=value;
    }
    public int getPosiciones() {
        return Posiciones;
    }
    public void setPosiciones(int value) {
        Posiciones=value;
    }

}

