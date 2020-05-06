package com.dts.classes.Transacciones.Inventario.TipoConteo;


import org.simpleframework.xml.Element;

public class clsBeTipoConteo {

    @Element(required=false) public int IdTipoConteo=0;
    @Element(required=false) public String Descripcion="";


    public clsBeTipoConteo() {
    }

    public clsBeTipoConteo(int IdTipoConteo,String Descripcion) {

        this.IdTipoConteo=IdTipoConteo;
        this.Descripcion=Descripcion;

    }


    public int getIdTipoConteo() {
        return IdTipoConteo;
    }
    public void setIdTipoConteo(int value) {
        IdTipoConteo=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }

}

