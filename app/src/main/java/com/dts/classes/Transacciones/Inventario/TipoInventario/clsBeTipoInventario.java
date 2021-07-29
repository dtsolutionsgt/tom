package com.dts.classes.Transacciones.Inventario.TipoInventario;


import org.simpleframework.xml.Element;

public class clsBeTipoInventario {

    @Element(required=false) public int IdTipoInv=0;
    @Element(required=false) public String Descripcion="";


    public clsBeTipoInventario() {
    }

    public clsBeTipoInventario(int IdTipoInv,String Descripcion) {

        this.IdTipoInv=IdTipoInv;
        this.Descripcion=Descripcion;

    }


    public int getIdTipoInv() {
        return IdTipoInv;
    }
    public void setIdTipoInv(int value) {
        IdTipoInv=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }

}

