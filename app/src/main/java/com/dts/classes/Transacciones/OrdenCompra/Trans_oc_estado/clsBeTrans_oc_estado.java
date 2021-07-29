package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_estado;


import org.simpleframework.xml.Element;

public class clsBeTrans_oc_estado {

    @Element(required=false) public int IdEstadoOC;
    @Element(required=false) public String Nombre;


    public clsBeTrans_oc_estado() {
    }

    public clsBeTrans_oc_estado(int IdEstadoOC,String Nombre) {

        this.IdEstadoOC=IdEstadoOC;
        this.Nombre=Nombre;

    }


    public int getIdEstadoOC() {
        return IdEstadoOC;
    }
    public void setIdEstadoOC(int value) {
        IdEstadoOC=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }

}

