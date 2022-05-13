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
    @Element(required=false) public boolean Requerir_Documento_Ref =false;
    @Element(required=false) public boolean Generar_pedido_ingreso_bodega_destino =false;
    @Element(required=false) public int IdTipoIngresoOC=1;
    @Element(required=false) public boolean Control_Poliza=false;
    @Element(required=false) public boolean Trasladar_Lotes_Doc_Ingreso=false;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public boolean Requerir_Cliente_Es_Bodega_WMS=false;
    @Element(required=false) public boolean Marcar_Registros_Enviados_MI3=false;
    @Element(required=false) public boolean Generar_Recepcion_Auto_Bodega_Destino=false;
    @Element(required=false) public boolean Recibir_Producto_Auto_Bodega_Destino=false;
    @Element(required=false) public boolean Control_Cliente_En_Detalle=false;

    public clsBeTrans_pe_tipo() {
    }

    public clsBeTrans_pe_tipo(int IdTipoPedido,
                              String Nombre,
                              String Descripcion,
                              boolean Preparar,
                              boolean Verificar,
                              boolean ReservaStock,
                              boolean ImprimeBarrasPicking,
                              boolean ImprimeBarrasPacking,
                              boolean Requerir_Documento_Ref,
                              boolean Generar_pedido_ingreso_bodega_destino,
                              int IdTipoIngresoOC,
                              boolean Control_Poliza,
                              boolean Requerir_Cliente_Es_Bodega_WMS,
                              boolean Control_Cliente_En_Detalle
    ) {

        this.IdTipoPedido=IdTipoPedido;
        this.Nombre=Nombre;
        this.Descripcion=Descripcion;
        this.Preparar=Preparar;
        this.Verificar=Verificar;
        this.ReservaStock=ReservaStock;
        this.ImprimeBarrasPicking=ImprimeBarrasPicking;
        this.ImprimeBarrasPacking=ImprimeBarrasPacking;
        this.Requerir_Documento_Ref = Requerir_Documento_Ref;
        this.Generar_pedido_ingreso_bodega_destino= Generar_pedido_ingreso_bodega_destino;
        this.IdTipoIngresoOC = IdTipoIngresoOC;
        this.Control_Poliza = Control_Poliza;
        this.Requerir_Cliente_Es_Bodega_WMS=Requerir_Cliente_Es_Bodega_WMS;
        this.Control_Cliente_En_Detalle=Control_Cliente_En_Detalle;

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
        return Requerir_Documento_Ref;
    }
    public void setrequerir_documento_ref(boolean value) {
        Requerir_Documento_Ref=value;
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

    public boolean getControl_Poliza() {
        return Control_Poliza;
    }
    public void setControl_Poliza(boolean value) {
        Control_Poliza=value;
    }

    public boolean getTrasladar_Lotes_Doc_Ingreso() {
        return Trasladar_Lotes_Doc_Ingreso;
    }
    public void setTrasladar_Lotes_Doc_Ingreso(boolean value) {
        Trasladar_Lotes_Doc_Ingreso=value;
    }

    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }

    public boolean getRequerir_Cliente_Es_Bodega_WMS() {
        return Requerir_Cliente_Es_Bodega_WMS;
    }
    public void setRequerir_Cliente_Es_Bodega_WMS(boolean value) {
        Requerir_Cliente_Es_Bodega_WMS=value;
    }

    public boolean getMarcar_Registros_Enviados_MI3() {
        return Marcar_Registros_Enviados_MI3;
    }
    public void setMarcar_Registros_Enviados_MI3(boolean value) {
        Marcar_Registros_Enviados_MI3=value;
    }

    public boolean getGenerar_Recepcion_Auto_Bodega_Destino() {
        return Generar_Recepcion_Auto_Bodega_Destino;
    }
    public void setGenerar_Recepcion_Auto_Bodega_Destino(boolean value) {
        Generar_Recepcion_Auto_Bodega_Destino=value;
    }

    public boolean getRecibir_Producto_Auto_Bodega_Destino() {
        return Recibir_Producto_Auto_Bodega_Destino;
    }
    public void setRecibir_Producto_Auto_Bodega_Destino(boolean value) {
        Recibir_Producto_Auto_Bodega_Destino=value;
    }

    public boolean getControl_Cliente_En_Detalle() {
        return Control_Cliente_En_Detalle;
    }
    public void setControl_Cliente_En_Detalle(boolean value) {
        Control_Cliente_En_Detalle=value;
    }
}