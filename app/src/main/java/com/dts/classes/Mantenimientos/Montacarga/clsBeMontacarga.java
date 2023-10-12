package com.dts.classes.Mantenimientos.Montacarga;

import com.dts.classes.Mantenimientos.Rol.clsBeRol_operador;

import org.simpleframework.xml.Element;

public class clsBeMontacarga {


    @Element(required=false) public int IdMontacarga=0;
    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String Modelo="";
    @Element(required=false) public String Serie="";
    @Element(required=false) public Double Capacidad_basica=0.0;
    @Element(required=false) public Double Desplazamiento_motor=0.0;
    @Element(required=false) public Double Costo_Hora=0.0;
    @Element(required=false) public String Tipo_combustible="";
    @Element(required=false) public String Tipo_montacarga="";
    @Element(required=false) public String Fecha_compra="1900-01-01T00:00:00";
    @Element(required=false) public String Fecha_inicio_operaciones="1900-01-01T00:00:00";
    @Element(required=false) public String Proximo_mantenimiento="1900-01-01T00:00:00";


    public clsBeMontacarga() {
    }

    public clsBeMontacarga(int IdMontacarga, int IdEmpresa,String Nombre, String Modelo, String Serie,
    Double Capacidad_basica, Double Desplazamiento_motor, Double Costo_Hora, String Tipo_combustible,
    String Tipo_montacarga, String Fecha_compra, String Fecha_inicio_operaciones, String Proximo_mantenimiento)
    {

        this.IdMontacarga=IdMontacarga;
        this.IdEmpresa=IdEmpresa;
        this.Nombre = Nombre;
        this.Modelo = Modelo;
        this.Serie = Serie;
        this.Capacidad_basica = Capacidad_basica;
        this.Desplazamiento_motor = Desplazamiento_motor;
        this.Costo_Hora=Costo_Hora;
        this.Tipo_combustible = Tipo_combustible;
        this.Tipo_montacarga = Tipo_montacarga;
        this.Fecha_compra = Fecha_compra;
        this.Fecha_inicio_operaciones = Fecha_inicio_operaciones;
        this.Proximo_mantenimiento = Proximo_mantenimiento;

    }


    public int getIdMontacarga() {
        return IdMontacarga;
    }
    public void setIdMontacarga(int value) {
        IdMontacarga=value;
    }

    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }


    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }

    public String getModelo() {
        return Modelo;
    }
    public void setModelo(String value) {
        Modelo=value;
    }

    public String getSerie() {
        return Serie;
    }
    public void setSerie(String value) {
        Serie=value;
    }


    public Double getCapacidad_basica() {
        return Capacidad_basica;
    }
    public void setCapacidad_basica(Double value) {
        Capacidad_basica=value;
    }

    public Double getDesplazamiento_motor() {
        return Desplazamiento_motor;
    }
    public void setDesplazamiento_motor(Double value) {
        Desplazamiento_motor=value;
    }

    public Double getCosto_Hora() {
        return Costo_Hora;
    }
    public void setCosto_Hora(Double value) {
        Costo_Hora=value;
    }


    public String getTipo_combustible() {
        return Tipo_combustible;
    }
    public void setTipo_combustible(String value) {
        Tipo_combustible=value;
    }

    public String getTipo_montacarga() {
        return Tipo_montacarga;
    }
    public void setTipo_montacarga(String value) {
        Tipo_montacarga=value;
    }

    public String getFecha_compra() {
        return Fecha_compra;
    }
    public void setFecha_compra(String value) {
        Fecha_compra=value;
    }

    public String getFecha_inicio_operaciones() {
        return Fecha_inicio_operaciones;
    }
    public void setFecha_inicio_operaciones(String value) {
        Fecha_inicio_operaciones=value;
    }

    public String getProximo_mantenimiento() {
        return Proximo_mantenimiento;
    }
    public void setProximo_mantenimiento(String value) {
        Proximo_mantenimiento=value;
    }


}
