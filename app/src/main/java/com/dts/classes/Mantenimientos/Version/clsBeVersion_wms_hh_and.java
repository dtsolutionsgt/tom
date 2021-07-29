package com.dts.classes.Mantenimientos.Version;


import org.simpleframework.xml.Element;

public class clsBeVersion_wms_hh_and {

    @Element(required=false) public int IdEmpresaVersion;
    @Element(required=false) public int IdEmpresa;
    @Element(required=false) public String Version;
    @Element(required=false) public String Notas;
    @Element(required=false) public int Fecha;


    public clsBeVersion_wms_hh_and() {
    }

    public clsBeVersion_wms_hh_and(int IdEmpresaVersion,int IdEmpresa,String Version,String Notas,
                                   int Fecha) {

        this.IdEmpresaVersion=IdEmpresaVersion;
        this.IdEmpresa=IdEmpresa;
        this.Version=Version;
        this.Notas=Notas;
        this.Fecha=Fecha;

    }


    public int getIdEmpresaVersion() {
        return IdEmpresaVersion;
    }
    public void setIdEmpresaVersion(int value) {
        IdEmpresaVersion=value;
    }
    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public String getVersion() {
        return Version;
    }
    public void setVersion(String value) {
        Version=value;
    }
    public String getNotas() {
        return Notas;
    }
    public void setNotas(String value) {
        Notas=value;
    }
    public int getFecha() {
        return Fecha;
    }
    public void setFecha(int value) {
        Fecha=value;
    }

}

