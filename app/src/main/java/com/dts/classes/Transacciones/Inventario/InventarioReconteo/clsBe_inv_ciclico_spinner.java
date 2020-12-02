package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.Element;

public class clsBe_inv_ciclico_spinner {

    @Element(required=false) public Integer Id= 0;
    @Element(required=false) public String Descripcion = "";

    public clsBe_inv_ciclico_spinner(){}

    public clsBe_inv_ciclico_spinner(Integer Id, String Descripcion ){

        this.Id = Id;
        this.Descripcion = Descripcion;
    }


    public Integer getId() {
        return Id;
    }
    public void setId(Integer value) {
        Id=value;
    }

    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }


}
