package com.dts.classes.Transacciones.Inventario.InventarioTramo;


import org.simpleframework.xml.Element;

public class clsBeTrans_inv_tramo {

    @Element(required=false) public int Idinventario=0;
    @Element(required=false) public int Idtramo=0;
    @Element(required=false) public int Det_idoperador=0;
    @Element(required=false) public String Det_estado="";
    @Element(required=false) public String Det_inicio="1900-01-01T00:00:01";
    @Element(required=false) public String Det_fin="1900-01-01T00:00:01";
    @Element(required=false) public int Res_idoperador=0;
    @Element(required=false) public String Res_estado="";
    @Element(required=false) public String Res_inicio="1900-01-01T00:00:01";
    @Element(required=false) public String Res_fin="1900-01-01T00:00:01";
    @Element(required=false) public boolean Aplicado=false;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public String Nombre_Tramo="";
    @Element(required=false) public boolean Es_Rack=false;

    public clsBeTrans_inv_tramo() {
    }

    public clsBeTrans_inv_tramo(int Idinventario,int Idtramo,int Det_idoperador,String Det_estado,
                                String Det_inicio,String Det_fin,int Res_idoperador,String Res_estado,
                                String Res_inicio,String Res_fin,boolean Aplicado,int IdBodega,
                                String Nombre_Tramo, boolean Es_Rack) {

        this.Idinventario=Idinventario;
        this.Idtramo=Idtramo;
        this.Det_idoperador=Det_idoperador;
        this.Det_estado=Det_estado;
        this.Det_inicio=Det_inicio;
        this.Det_fin=Det_fin;
        this.Res_idoperador=Res_idoperador;
        this.Res_estado=Res_estado;
        this.Res_inicio=Res_inicio;
        this.Res_fin=Res_fin;
        this.Aplicado=Aplicado;
        this.IdBodega=IdBodega;
        this.Nombre_Tramo=Nombre_Tramo;
        this.Es_Rack = Es_Rack;

    }


    public int getIdinventario() {
        return Idinventario;
    }
    public void setIdinventario(int value) {
        Idinventario=value;
    }
    public int getIdtramo() {
        return Idtramo;
    }
    public void setIdtramo(int value) {
        Idtramo=value;
    }
    public int getDet_idoperador() {
        return Det_idoperador;
    }
    public void setDet_idoperador(int value) {
        Det_idoperador=value;
    }
    public String getDet_estado() {
        return Det_estado;
    }
    public void setDet_estado(String value) {
        Det_estado=value;
    }
    public String getDet_inicio() {
        return Det_inicio;
    }
    public void setDet_inicio(String value) {
        Det_inicio=value;
    }
    public String getDet_fin() {
        return Det_fin;
    }
    public void setDet_fin(String value) {
        Det_fin=value;
    }
    public int getRes_idoperador() {
        return Res_idoperador;
    }
    public void setRes_idoperador(int value) {
        Res_idoperador=value;
    }
    public String getRes_estado() {
        return Res_estado;
    }
    public void setRes_estado(String value) {
        Res_estado=value;
    }
    public String getRes_inicio() {
        return Res_inicio;
    }
    public void setRes_inicio(String value) {
        Res_inicio=value;
    }
    public String getRes_fin() {
        return Res_fin;
    }
    public void setRes_fin(String value) {
        Res_fin=value;
    }
    public boolean getAplicado() {
        return Aplicado;
    }
    public void setAplicado(boolean value) {
        Aplicado=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public String getNombre_Tramo() {
        return Nombre_Tramo;
    }
    public void setNombre_Tramo(String value) {
        Nombre_Tramo=value;
    }

    public boolean getEs_Rack() {
        return Es_Rack;
    }
    public void setEs_Rack(Boolean value) {
        Es_Rack=value;
    }

}


