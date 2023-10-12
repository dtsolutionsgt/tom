package com.dts.classes.Mantenimientos.Producto.Producto_imagen;

import org.simpleframework.xml.Element;

public class clsBeProducto_imagen {
    @Element(required=false) public int IdProductoImagen=0;
    @Element(required=false) public int IdProducto=0;
    @Element(required=false) public String Etiqueta="";
    @Element(required=false) public String Imagen="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public boolean IsNew=false;


    public clsBeProducto_imagen() {
    }

    public clsBeProducto_imagen(int IdProductoImagen, int IdProducto, String Etiqueta,
                                String Imagen, String User_agr,String Fec_agr,boolean IsNew) {

        this.IdProductoImagen=IdProductoImagen;
        this.IdProducto=IdProducto;
        this.Etiqueta=Etiqueta;
        this.Imagen=Imagen;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.IsNew = IsNew;

    }


    public int getIdProductoImagen() {
        return IdProductoImagen;
    }
    public void setIdProductoImagen(int value) {
        IdProductoImagen=value;
    }

    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }

    public String getEtiqueta() {
        return Etiqueta;
    }
    public void setEtiqueta(String value) {
        Etiqueta=value;
    }

    public String getImagen() {
        return Imagen;
    }
    public void setImagen(String value) {
        Imagen=value;
    }

    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }

    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
}
