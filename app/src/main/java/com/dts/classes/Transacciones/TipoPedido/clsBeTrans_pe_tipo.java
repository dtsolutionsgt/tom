package com.dts.classes.Transacciones.TipoPedido;

import org.simpleframework.xml.Element;

public class clsBeTrans_pe_tipo {

    @Element(required=false) public int IdTipoPedido=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String Descripcion="";
    @Element(required=false) public boolean Preparar=false;
    @Element(required=false) public boolean Verificar=false;
    @Element(required=false) public boolean ReservaStock=false;
    @Element(required=false) public boolean ImprimeBarrasPicking=false;
    @Element(required=false) public boolean ImprimeBarrasPacking=false;
    @Element(required=false) public boolean requerir_documento_ref =false;
    @Element(required=false) public boolean Generar_pedido_ingreso_bodega_destino =false;
    @Element(required=false) public int IdTipoIngresoOC=1;

    public clsBeTrans_pe_tipo() {
    }

    public clsBeTrans_pe_tipo(int IdTipoPedido,String Nombre,String Descripcion,boolean Preparar,
                              boolean Verificar,boolean ReservaStock,boolean ImprimeBarrasPicking,boolean ImprimeBarrasPacking,
                              boolean requerir_documento_ref,boolean Generar_pedido_ingreso_bodega_destino,int IdTipoIngresoOC
    ) {

        this.IdTipoPedido=IdTipoPedido;
        this.Nombre=Nombre;
        this.Descripcion=Descripcion;
        this.Preparar=Preparar;
        this.Verificar=Verificar;
        this.ReservaStock=ReservaStock;
        this.ImprimeBarrasPicking=ImprimeBarrasPicking;
        this.ImprimeBarrasPacking=ImprimeBarrasPacking;
        this.requerir_documento_ref = requerir_documento_ref;
        this.Generar_pedido_ingreso_bodega_destino= Generar_pedido_ingreso_bodega_destino;
        this.IdTipoIngresoOC = IdTipoIngresoOC;

    }


    public int getIdTipoPedido() {
        return IdTipoPedido;
    }
    public void setIdTipoPedido(int value) {
        IdTipoPedido=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public boolean getPreparar() {
        return Preparar;
    }
    public void setPreparar(boolean value) {
        Preparar=value;
    }
    public boolean getVerificar() {
        return Verificar;
    }
    public void setVerificar(boolean value) {
        Verificar=value;
    }
    public boolean getReservaStock() {
        return ReservaStock;
    }
    public void setReservaStock(boolean value) {
        ReservaStock=value;
    }
    public boolean getImprimeBarrasPicking() {
        return ImprimeBarrasPicking;
    }
    public void setImprimeBarrasPicking(boolean value) {
        ImprimeBarrasPicking=value;
    }
    public boolean getImprimeBarrasPacking() {
        return ImprimeBarrasPacking;
    }
    public void setImprimeBarrasPacking(boolean value) {
        ImprimeBarrasPacking=value;
    }
    public boolean getrequerir_documento_ref() {
        return requerir_documento_ref;
    }
    public void setrequerir_documento_ref(boolean value) {
        requerir_documento_ref=value;
    }
    public boolean getGenerar_pedido_ingreso_bodega_destino() {
        return Generar_pedido_ingreso_bodega_destino;
    }
    public void setGenerar_pedido_ingreso_bodega_destino(boolean value) {
        Generar_pedido_ingreso_bodega_destino=value;
    }
    public int getIdTipoIngresoOC() {
        return IdTipoPedido;
    }
    public void setIdTipoIngresoOC(int value) {
        IdTipoPedido=value;
    }

}


