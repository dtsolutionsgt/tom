package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti;


import org.simpleframework.xml.Element;

public class clsBeTrans_oc_ti {

    @Element(required=false) public int IdTipoIngresoOC;
    @Element(required=false) public String Nombre;
    @Element(required=false) public boolean Es_devolucion;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean Control_Poliza=false;
    @Element(required=false) public boolean Requerir_Documento_Ref=false;


    public clsBeTrans_oc_ti() {
    }

    public clsBeTrans_oc_ti(int IdTipoIngresoOC,
                            String Nombre,
                            boolean Es_devolucion,
                            String User_agr,
                            String Fec_agr,
                            String User_mod,
                            String Fec_mod,
                            boolean Activo,
                            boolean Control_Poliza,
                            boolean requerir_documento_ref
    ) {

        this.IdTipoIngresoOC=IdTipoIngresoOC;
        this.Nombre=Nombre;
        this.Es_devolucion=Es_devolucion;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Control_Poliza=Control_Poliza;
        this.Requerir_Documento_Ref = requerir_documento_ref;
    }


    public int getIdTipoIngresoOC() {
        return IdTipoIngresoOC;
    }
    public void setIdTipoIngresoOC(int value) {
        IdTipoIngresoOC=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public boolean getEs_devolucion() {
        return Es_devolucion;
    }
    public void setEs_devolucion(boolean value) {
        Es_devolucion=value;
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
    public boolean getControl_Poliza() {
        return Control_Poliza;
    }
    public void setControl_Poliza(boolean value) {
        Control_Poliza=value;
    }

    public boolean getRequerir_Documento_Ref() {
        return Requerir_Documento_Ref;
    }

    public void setRequerir_Documento_Ref(boolean value) {
        Requerir_Documento_Ref=value;
    }

}

