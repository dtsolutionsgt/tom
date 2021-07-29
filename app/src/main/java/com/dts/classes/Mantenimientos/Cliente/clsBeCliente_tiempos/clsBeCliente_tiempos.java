package com.dts.classes.Mantenimientos.Cliente.clsBeCliente_tiempos;

import com.dts.classes.Mantenimientos.Producto.Producto_clasificacion.clsBeProducto_clasificacion;
import com.dts.classes.Mantenimientos.Producto.Producto_familia.clsBeProducto_familia;

import org.simpleframework.xml.Element;

public class clsBeCliente_tiempos {

    @Element(required=false) public int IdTiempoCliente=0;
    @Element(required=false) public int IdCliente=0;
    @Element(required=false) public int IdFamilia=0;
    @Element(required=false) public int IdClasificacion=0;
    @Element(required=false) public clsBeProducto_familia Familia=new clsBeProducto_familia();
    @Element(required=false) public clsBeProducto_clasificacion Clasificacion=new clsBeProducto_clasificacion();
    @Element(required=false) public int Dias_Local=0;
    @Element(required=false) public int Dias_Exterior=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="";
    @Element(required=false) public boolean Activo=false;

    public clsBeCliente_tiempos() {
    }
    public clsBeCliente_tiempos(int IdTiempoCliente,int IdCliente,int IdFamilia,int IdClasificacion,
                                clsBeProducto_familia Familia,clsBeProducto_clasificacion Clasificacion,int Dias_Local,int Dias_Exterior,
                                String User_agr,String Fec_agr,String User_mod,String Fec_mod,
                                boolean Activo) {

        this.IdTiempoCliente=IdTiempoCliente;
        this.IdCliente=IdCliente;
        this.IdFamilia=IdFamilia;
        this.IdClasificacion=IdClasificacion;
        this.Familia=Familia;
        this.Clasificacion=Clasificacion;
        this.Dias_Local=Dias_Local;
        this.Dias_Exterior=Dias_Exterior;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;

    }


    public int getIdTiempoCliente() {
        return IdTiempoCliente;
    }
    public void setIdTiempoCliente(int value) {
        IdTiempoCliente=value;
    }
    public int getIdCliente() {
        return IdCliente;
    }
    public void setIdCliente(int value) {
        IdCliente=value;
    }
    public int getIdFamilia() {
        return IdFamilia;
    }
    public void setIdFamilia(int value) {
        IdFamilia=value;
    }
    public int getIdClasificacion() {
        return IdClasificacion;
    }
    public void setIdClasificacion(int value) {
        IdClasificacion=value;
    }
    public clsBeProducto_familia getFamilia() {
        return Familia;
    }
    public void setFamilia(clsBeProducto_familia value) {
        Familia=value;
    }
    public clsBeProducto_clasificacion getClasificacion() {
        return Clasificacion;
    }
    public void setClasificacion(clsBeProducto_clasificacion value) {
        Clasificacion=value;
    }
    public int getDias_Local() {
        return Dias_Local;
    }
    public void setDias_Local(int value) {
        Dias_Local=value;
    }
    public int getDias_Exterior() {
        return Dias_Exterior;
    }
    public void setDias_Exterior(int value) {
        Dias_Exterior=value;
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
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }

}

