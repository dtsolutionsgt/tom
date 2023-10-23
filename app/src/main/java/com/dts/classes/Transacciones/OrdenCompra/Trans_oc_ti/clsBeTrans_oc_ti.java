package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti;


import org.simpleframework.xml.Element;

public class clsBeTrans_oc_ti {

    @Element(required=false) public int IdTipoIngresoOC;
    @Element(required=false) public String Nombre;
    @Element(required=false) public boolean Es_devolucion;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public boolean Control_Poliza=false;
    @Element(required=false) public boolean Requerir_Documento_Ref=false;
    @Element(required=false) public boolean Es_Poliza_Consolidada=false;
    @Element(required=false) public boolean Genera_Tarea_Ingreso=false;
    @Element(required=false) public boolean Requerir_Proveedor_Es_Bodega_WMS=false;
    @Element(required=false) public boolean Requerir_Documento_Ref_WMS=false;
    @Element(required=false) public boolean Requerir_Ubic_Rec_Ingreso=false;
    @Element(required=false) public boolean Marcar_Registros_Enviados_MI3=false;
    @Element(required=false) public boolean Exigir_Campo_Referencia=false;
    @Element(required=false) public boolean Preguntar_En_BackOrder=false;
    @Element(required=false) public boolean Bloquear_Lotes = false;
    @Element(required=false) public boolean Permitir_Excedente_Lotes = false;

    @Element(required=false) public boolean Permitir_Vencido_Ingreso = true;



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
                            boolean requerir_documento_ref,
                            boolean Requerir_Proveedor_Es_Bodega_WMS,
                            boolean Requerir_Documento_Ref_WMS,
                            boolean Requerir_Ubic_Rec_Ingreso,
                            boolean Bloquear_Lotes,
                            boolean Permitir_Excedente_Lotes,
                            boolean Permitir_Vencido_Ingreso
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
        this.Requerir_Proveedor_Es_Bodega_WMS = Requerir_Proveedor_Es_Bodega_WMS;
        this.Requerir_Documento_Ref_WMS = Requerir_Documento_Ref_WMS;
        this.Requerir_Ubic_Rec_Ingreso = Requerir_Ubic_Rec_Ingreso;
        this.Bloquear_Lotes = Bloquear_Lotes;
        this.Permitir_Excedente_Lotes = Permitir_Excedente_Lotes;
        this.Permitir_Vencido_Ingreso = Permitir_Vencido_Ingreso;
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

    public boolean getEs_Poliza_Consolidada() {
        return Es_Poliza_Consolidada;
    }
    public void setEs_Poliza_Consolidada(boolean value) {
        Es_Poliza_Consolidada=value;
    }

    public boolean getGenera_Tarea_Ingreso() {
        return Genera_Tarea_Ingreso;
    }
    public void setGenera_Tarea_Ingreso(boolean value) {
        Genera_Tarea_Ingreso=value;
    }

    public boolean getRequerir_Proveedor_Es_Bodega_WMS() {
        return Requerir_Proveedor_Es_Bodega_WMS;
    }
    public void setRequerir_Proveedor_Es_Bodega_WMS(boolean value) {
        Requerir_Proveedor_Es_Bodega_WMS=value;
    }

    public boolean getRequerir_Documento_Ref_WMS() {
        return Requerir_Documento_Ref_WMS;
    }
    public void setRequerir_Documento_Ref_WMS(boolean value) {
        Requerir_Documento_Ref_WMS=value;
    }

    public boolean getRequerir_Ubic_Rec_Ingreso() {
        return Requerir_Ubic_Rec_Ingreso;
    }
    public void setRequerir_Ubic_Rec_Ingreso(boolean value) {
        Requerir_Ubic_Rec_Ingreso=value;
    }

    public boolean getExigir_Campo_Referencia() {
        return Requerir_Ubic_Rec_Ingreso;
    }
    public void setExigir_Campo_Referencia(boolean value) {
        Exigir_Campo_Referencia=value;
    }

    public boolean getMarcar_Registros_Enviados_MI3() {
        return Marcar_Registros_Enviados_MI3;
    }
    public void setMarcar_Registros_Enviados_MI3(boolean value) {
        Marcar_Registros_Enviados_MI3=value;
    }

    public boolean getPreguntar_En_BackOrder() {
        return Preguntar_En_BackOrder;
    }
    public void setPreguntar_En_BackOrder(boolean value) {
        Preguntar_En_BackOrder=value;
    }
    public boolean getBloquear_Lotes() {
        return Bloquear_Lotes;
    }
    public void setBloquear_Lotes(boolean value) {
        Bloquear_Lotes=value;
    }

    public boolean getPermitir_Excedente_Lotes() {
        return Permitir_Excedente_Lotes;
    }
    public void setPermitir_Excedente_Lotes(boolean value) {
        Permitir_Excedente_Lotes=value;
    }
    public boolean getPermitir_Vencido_Ingreso() {
        return Permitir_Vencido_Ingreso;
    }
    public void setPermitir_Vencido_Ingreso(boolean value) {
        Permitir_Vencido_Ingreso=value;
    }

}