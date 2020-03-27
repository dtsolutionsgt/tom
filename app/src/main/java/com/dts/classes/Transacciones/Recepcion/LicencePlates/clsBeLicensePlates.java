package com.dts.classes.Transacciones.Recepcion.LicencePlates;


import org.simpleframework.xml.Element;

public class clsBeLicensePlates {

    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public String LicensePlates="";
    @Element(required=false) public double CantidadUnidadBasica=0;
    @Element(required=false) public double CantidadPresentacion=0;
    @Element(required=false) public double CantidadMaximaPresentacion=0;
    @Element(required=false) public double CantidadDisponible=0;


    public clsBeLicensePlates() {
    }

    public clsBeLicensePlates(int IdRecepcionEnc,int IdProductoBodega,int IdPresentacion,String LicensePlates,
                              double CantidadUnidadBasica,double CantidadPresentacion,double CantidadMaximaPresentacion,double CantidadDisponible
    ) {

        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdProductoBodega=IdProductoBodega;
        this.IdPresentacion=IdPresentacion;
        this.LicensePlates=LicensePlates;
        this.CantidadUnidadBasica=CantidadUnidadBasica;
        this.CantidadPresentacion=CantidadPresentacion;
        this.CantidadMaximaPresentacion=CantidadMaximaPresentacion;
        this.CantidadDisponible=CantidadDisponible;

    }


    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public String getLicensePlates() {
        return LicensePlates;
    }
    public void setLicensePlates(String value) {
        LicensePlates=value;
    }
    public double getCantidadUnidadBasica() {
        return CantidadUnidadBasica;
    }
    public void setCantidadUnidadBasica(double value) {
        CantidadUnidadBasica=value;
    }
    public double getCantidadPresentacion() {
        return CantidadPresentacion;
    }
    public void setCantidadPresentacion(double value) {
        CantidadPresentacion=value;
    }
    public double getCantidadMaximaPresentacion() {
        return CantidadMaximaPresentacion;
    }
    public void setCantidadMaximaPresentacion(double value) {
        CantidadMaximaPresentacion=value;
    }
    public double getCantidadDisponible() {
        return CantidadDisponible;
    }
    public void setCantidadDisponible(double value) {
        CantidadDisponible=value;
    }

}

