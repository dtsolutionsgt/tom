package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage1;


import org.simpleframework.xml.Element;

public class clsUbic {

    @Element(required=false) public int ubic=0;

    public clsUbic() {
    }

    public clsUbic(int ubic
    ) {
        this.ubic=ubic;
    }

    public int getUbic() {
        return ubic;
    }
    public void setUbic(int value) {
        ubic=value;
    }
}

