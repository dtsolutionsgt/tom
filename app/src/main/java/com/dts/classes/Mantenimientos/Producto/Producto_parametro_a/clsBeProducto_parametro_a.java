package com.dts.classes.Mantenimientos.Producto.Producto_parametro_a;

import org.simpleframework.xml.Element;

public class clsBeProducto_parametro_a {

    @Element(required=false) public int IdProductoParametroA=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public boolean Activo=false;


    public clsBeProducto_parametro_a() {
    }

    public clsBeProducto_parametro_a(int IdProductoParametroA,String Codigo,String Nombre,String User_agr,
                                     String Fec_agr,String Fec_mod,String User_mod,boolean Activo
    ) {

        this.IdProductoParametroA=IdProductoParametroA;
        this.Codigo=Codigo;
        this.Nombre=Nombre;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.Fec_mod=Fec_mod;
        this.User_mod=User_mod;
        this.Activo=Activo;

    }


    public int getIdProductoParametroA() {
        return IdProductoParametroA;
    }
    public void setIdProductoParametroA(int value) {
        IdProductoParametroA=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
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
    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }
    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }

}


