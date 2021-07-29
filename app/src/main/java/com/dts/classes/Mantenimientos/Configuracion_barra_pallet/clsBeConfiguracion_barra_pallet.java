package com.dts.classes.Mantenimientos.Configuracion_barra_pallet;


import org.simpleframework.xml.Element;

public class clsBeConfiguracion_barra_pallet {

    @Element(required=false) public int IdConfiguracionPallet=0;
    @Element(required=false) public int LongCodBodegaOrigen=0;
    @Element(required=false) public int LongCodProducto=0;
    @Element(required=false) public int LongLP=0;
    @Element(required=false) public boolean CodigoNumerico=false;
    @Element(required=false) public String IdentificadorInicio="";


    public clsBeConfiguracion_barra_pallet() {
    }

    public clsBeConfiguracion_barra_pallet(int IdConfiguracionPallet,int LongCodBodegaOrigen,int LongCodProducto,int LongLP,
                                           boolean CodigoNumerico,String IdentificadorInicio) {

        this.IdConfiguracionPallet=IdConfiguracionPallet;
        this.LongCodBodegaOrigen=LongCodBodegaOrigen;
        this.LongCodProducto=LongCodProducto;
        this.LongLP=LongLP;
        this.CodigoNumerico=CodigoNumerico;
        this.IdentificadorInicio=IdentificadorInicio;

    }


    public int getIdConfiguracionPallet() {
        return IdConfiguracionPallet;
    }
    public void setIdConfiguracionPallet(int value) {
        IdConfiguracionPallet=value;
    }
    public int getLongCodBodegaOrigen() {
        return LongCodBodegaOrigen;
    }
    public void setLongCodBodegaOrigen(int value) {
        LongCodBodegaOrigen=value;
    }
    public int getLongCodProducto() {
        return LongCodProducto;
    }
    public void setLongCodProducto(int value) {
        LongCodProducto=value;
    }
    public int getLongLP() {
        return LongLP;
    }
    public void setLongLP(int value) {
        LongLP=value;
    }
    public boolean getCodigoNumerico() {
        return CodigoNumerico;
    }
    public void setCodigoNumerico(boolean value) {
        CodigoNumerico=value;
    }
    public String getIdentificadorInicio() {
        return IdentificadorInicio;
    }
    public void setIdentificadorInicio(String value) {
        IdentificadorInicio=value;
    }

}

