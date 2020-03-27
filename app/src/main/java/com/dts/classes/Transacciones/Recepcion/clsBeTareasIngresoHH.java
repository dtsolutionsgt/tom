package com.dts.classes.Transacciones.Recepcion;


import org.simpleframework.xml.Element;

public class clsBeTareasIngresoHH {

    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdPropietario=0;
    @Element(required=false) public String NombrePropietario="";
    @Element(required=false) public int IdProveedor=0;
    @Element(required=false) public String NombreProveedor="";
    @Element(required=false) public String NoDocumentoOc="";
    @Element(required=false) public String NombreMotivoDevolucion="";
    @Element(required=false) public String NombreTipoIngresoOC="";
    @Element(required=false) public String NoReferenciaOC="";
    @Element(required=false) public int IdOrderCompraEnc=0;
    @Element(required=false) public String NombreTipoRecepcion="";


    public clsBeTareasIngresoHH() {
    }

    public clsBeTareasIngresoHH(int IdRecepcionEnc,int IdPropietarioBodega,int IdPropietario,String NombrePropietario,
                                int IdProveedor,String NombreProveedor,String NoDocumentoOc,String NombreMotivoDevolucion,
                                String NombreTipoIngresoOC,String NoReferenciaOC,int IdOrderCompraEnc,String NombreTipoRecepcion
    ) {

        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdPropietario=IdPropietario;
        this.NombrePropietario=NombrePropietario;
        this.IdProveedor=IdProveedor;
        this.NombreProveedor=NombreProveedor;
        this.NoDocumentoOc=NoDocumentoOc;
        this.NombreMotivoDevolucion=NombreMotivoDevolucion;
        this.NombreTipoIngresoOC=NombreTipoIngresoOC;
        this.NoReferenciaOC=NoReferenciaOC;
        this.IdOrderCompraEnc=IdOrderCompraEnc;
        this.NombreTipoRecepcion=NombreTipoRecepcion;

    }


    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdPropietario() {
        return IdPropietario;
    }
    public void setIdPropietario(int value) {
        IdPropietario=value;
    }
    public String getNombrePropietario() {
        return NombrePropietario;
    }
    public void setNombrePropietario(String value) {
        NombrePropietario=value;
    }
    public int getIdProveedor() {
        return IdProveedor;
    }
    public void setIdProveedor(int value) {
        IdProveedor=value;
    }
    public String getNombreProveedor() {
        return NombreProveedor;
    }
    public void setNombreProveedor(String value) {
        NombreProveedor=value;
    }
    public String getNoDocumentoOc() {
        return NoDocumentoOc;
    }
    public void setNoDocumentoOc(String value) {
        NoDocumentoOc=value;
    }
    public String getNombreMotivoDevolucion() {
        return NombreMotivoDevolucion;
    }
    public void setNombreMotivoDevolucion(String value) {
        NombreMotivoDevolucion=value;
    }
    public String getNombreTipoIngresoOC() {
        return NombreTipoIngresoOC;
    }
    public void setNombreTipoIngresoOC(String value) {
        NombreTipoIngresoOC=value;
    }
    public String getNoReferenciaOC() {
        return NoReferenciaOC;
    }
    public void setNoReferenciaOC(String value) {
        NoReferenciaOC=value;
    }
    public int getIdOrderCompraEnc() {
        return IdOrderCompraEnc;
    }
    public void setIdOrderCompraEnc(int value) {
        IdOrderCompraEnc=value;
    }
    public String getNombreTipoRecepcion() {
        return NombreTipoRecepcion;
    }
    public void setNombreTipoRecepcion(String value) {
        NombreTipoRecepcion=value;
    }

}

