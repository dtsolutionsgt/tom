package com.dts.classes.Mantenimientos.Producto.Producto_codigos_barra;


import com.dts.classes.Mantenimientos.Proveedor.clsBeProveedor;

import org.simpleframework.xml.Element;

public class clsBeProducto_codigos_barra {

    @Element(required=false) public int IdProductoCodigoBarra=0;
    @Element(required=false) public int IdProducto=0;
    @Element(required=false) public int IdProveedor=0;
    @Element(required=false) public String Codigo_barra="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean IsNew=true;
    @Element(required=false) public clsBeProveedor Proveedor=new clsBeProveedor();


    public clsBeProducto_codigos_barra() {
    }

    public clsBeProducto_codigos_barra(int IdProductoCodigoBarra,int IdProducto,int IdProveedor,String Codigo_barra,
                                       String Fec_agr,String User_mod,String Fec_mod,String User_agr,
                                       boolean Activo,boolean IsNew,clsBeProveedor Proveedor) {

        this.IdProductoCodigoBarra=IdProductoCodigoBarra;
        this.IdProducto=IdProducto;
        this.IdProveedor=IdProveedor;
        this.Codigo_barra=Codigo_barra;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.User_agr=User_agr;
        this.Activo=Activo;
        this.IsNew=IsNew;
        this.Proveedor=Proveedor;

    }


    public int getIdProductoCodigoBarra() {
        return IdProductoCodigoBarra;
    }
    public void setIdProductoCodigoBarra(int value) {
        IdProductoCodigoBarra=value;
    }
    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int value) {
        IdProducto=value;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setIdProveedor(int value) {
        IdProveedor=value;
    }
    public String getCodigo_barra() {
        return Codigo_barra;
    }
    public void setCodigo_barra(String value) {
        Codigo_barra=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public clsBeProveedor getProveedor() {
        return Proveedor;
    }
    public void setProveedor(clsBeProveedor value) {
        Proveedor=value;
    }

}

