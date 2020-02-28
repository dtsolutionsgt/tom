package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_imagen;


import org.simpleframework.xml.Element;

public class clsBeTrans_oc_imagen {

    @Element(required=false) public int IdOrdenCompraImg;
    @Element(required=false) public int IdOrdenCompraEnc;
    @Element(required=false) public int Orden;
    @Element(required=false) public Byte Imagen;
    @Element(required=false) public String Descripcion;


    public clsBeTrans_oc_imagen() {
    }

    public clsBeTrans_oc_imagen(int IdOrdenCompraImg,int IdOrdenCompraEnc,int Orden,Byte Imagen,
                                String Descripcion) {

        this.IdOrdenCompraImg=IdOrdenCompraImg;
        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.Orden=Orden;
        this.Imagen=Imagen;
        this.Descripcion=Descripcion;

    }


    public int getIdOrdenCompraImg() {
        return IdOrdenCompraImg;
    }
    public void setIdOrdenCompraImg(int value) {
        IdOrdenCompraImg=value;
    }
    public int getIdOrdenCompraEnc() {
        return IdOrdenCompraEnc;
    }
    public void setIdOrdenCompraEnc(int value) {
        IdOrdenCompraEnc=value;
    }
    public int getOrden() {
        return Orden;
    }
    public void setOrden(int value) {
        Orden=value;
    }
    public Byte getImagen() {
        return Imagen;
    }
    public void setImagen(Byte value) {
        Imagen=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }

}

