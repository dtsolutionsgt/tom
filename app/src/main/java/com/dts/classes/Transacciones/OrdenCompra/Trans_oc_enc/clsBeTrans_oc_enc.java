package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc;


import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_palletList;
import com.dts.classes.Mantenimientos.Propietario.Propietario_bodega.clsBePropietario_bodega;
import com.dts.classes.Mantenimientos.Proveedor.Proveedor_bodega.clsBeProveedor_bodega;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote.clsBeTrans_oc_det_loteList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_estado.clsBeTrans_oc_estado;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_imagen.clsBeTrans_oc_imagenList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_pol.clsBeTrans_oc_pol;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti.clsBeTrans_oc_ti;

import org.simpleframework.xml.Element;

public class clsBeTrans_oc_enc { 

    @Element(required=false) public int IdOrdenCompraEnc;
    @Element(required=false) public int IdPropietarioBodega;
    @Element(required=false) public int IdProveedorBodega;
    @Element(required=false) public int IdTipoIngresoOC;
    @Element(required=false) public int IdEstadoOC;
    @Element(required=false) public int IdMotivoDevolucion;
    @Element(required=false) public String Fecha_Creacion;
    @Element(required=false) public String Hora_Creacion;
    @Element(required=false) public String No_Documento;
    @Element(required=false) public String User_Agr;
    @Element(required=false) public String Fec_Agr ="1900-01-01T00:00:01";
    @Element(required=false) public String User_Mod;
    @Element(required=false) public String Fec_Mod="1900-01-01T00:00:01";
    @Element(required=false) public String Procedencia;
    @Element(required=false) public String No_Marchamo;
    @Element(required=false) public String Referencia;
    @Element(required=false) public String Observacion;
    @Element(required=false) public boolean Control_Poliza;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String Fecha_Recepcion;
    @Element(required=false) public String Hora_Inicio_Recepcion;
    @Element(required=false) public String Hora_Fin_Recepcion;
    @Element(required=false) public int IdMuelleRecepcion;
    @Element(required=false) public boolean Programar_Recepcion;
    @Element(required=false) public int IdMotivoAnulacionBodega;
    @Element(required=false) public boolean Enviado_A_ERP;
    @Element(required=false) public String Serie;
    @Element(required=false) public String Correlativo;
    @Element(required=false) public int IdDespachoEnc;
    @Element(required=false) public clsBeTrans_oc_detList DetalleOC=new clsBeTrans_oc_detList();
    @Element(required=false) public clsBeTrans_oc_det_loteList DetalleLotes=new clsBeTrans_oc_det_loteList();
    @Element(required=false) public clsBeI_nav_barras_palletList DetallePallets=new clsBeI_nav_barras_palletList();
    @Element(required=false) public clsBeTrans_oc_pol ObjPoliza=new clsBeTrans_oc_pol();
    @Element(required=false) public clsBeTrans_oc_imagenList ListaImg=new clsBeTrans_oc_imagenList();
    @Element(required=false) public clsBePropietario_bodega PropietarioBodega=new clsBePropietario_bodega();
    @Element(required=false) public clsBeProveedor_bodega ProveedorBodega=new clsBeProveedor_bodega();
    @Element(required=false) public clsBeTrans_oc_estado EstadoOC=new clsBeTrans_oc_estado();
    @Element(required=false) public int IdBodega;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public boolean EsDevolucion;
    @Element(required=false) public clsBeTrans_oc_ti TipoIngreso=new clsBeTrans_oc_ti();
    @Element(required=false) public boolean ExisteRecepcionNoFinalizada;
    @Element(required=false) public String No_Ticket_TMS;
    @Element(required=false) public int IdNoDocumentoRef;
    @Element(required=false) public int IdAcuerdoComercial;
    @Element(required=false) public int IdOperadorBodegaDefecto;
    @Element(required=false) public String No_Documento_Recepcion_ERP;
    @Element(required=false) public String No_Documento_Devolucion="";
    @Element(required=false) public int IdPedidoEncDevolucion=0;

    public clsBeTrans_oc_enc()
    {
    }

    public clsBeTrans_oc_enc(int IdOrdenCompraEnc,int IdPropietarioBodega,int IdProveedorBodega,int IdTipoIngresoOC,
                             int IdEstadoOC,int IdMotivoDevolucion,String Fecha_Creacion,String Hora_Creacion,
                             String No_Documento,String User_Agr,String Fec_Agr,String User_Mod,
                             String Fec_Mod,String Procedencia,String No_Marchamo,String Referencia,
                             String Observacion,boolean Control_Poliza,boolean Activo,String Fecha_Recepcion,
                             String Hora_Inicio_Recepcion,String Hora_Fin_Recepcion,int IdMuelleRecepcion,boolean Programar_Recepcion,
                             int IdMotivoAnulacionBodega,boolean Enviado_A_ERP,String Serie,String Correlativo,
                             int IdDespachoEnc,clsBeTrans_oc_detList DetalleOC,clsBeTrans_oc_det_loteList DetalleLotes,clsBeI_nav_barras_palletList DetallePallets,
                             clsBeTrans_oc_pol ObjPoliza,clsBeTrans_oc_imagenList ListaImg,clsBePropietario_bodega PropietarioBodega,clsBeProveedor_bodega ProveedorBodega,
                             clsBeTrans_oc_estado EstadoOC,int IdBodega,boolean IsNew,boolean EsDevolucion,
                             clsBeTrans_oc_ti TipoIngreso,boolean ExisteRecepcionNoFinalizada,
                             String No_Ticket_TMS,
                             int IdNoDocumentoRef,
                             int IdAcuerdoComercial,
                             String No_Documento_Devolucion,
                             int IdPedidoEncDevolucion) {

        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdProveedorBodega=IdProveedorBodega;
        this.IdTipoIngresoOC=IdTipoIngresoOC;
        this.IdEstadoOC=IdEstadoOC;
        this.IdMotivoDevolucion=IdMotivoDevolucion;
        this.Fecha_Creacion=Fecha_Creacion;
        this.Hora_Creacion=Hora_Creacion;
        this.No_Documento=No_Documento;
        this.User_Agr=User_Agr;
        this.Fec_Agr =Fec_Agr;
        this.User_Mod=User_Mod;
        this.Fec_Mod =Fec_Mod;
        this.Procedencia=Procedencia;
        this.No_Marchamo=No_Marchamo;
        this.Referencia=Referencia;
        this.Observacion=Observacion;
        this.Control_Poliza=Control_Poliza;
        this.Activo=Activo;
        this.Fecha_Recepcion=Fecha_Recepcion;
        this.Hora_Inicio_Recepcion=Hora_Inicio_Recepcion;
        this.Hora_Fin_Recepcion=Hora_Fin_Recepcion;
        this.IdMuelleRecepcion=IdMuelleRecepcion;
        this.Programar_Recepcion=Programar_Recepcion;
        this.IdMotivoAnulacionBodega=IdMotivoAnulacionBodega;
        this.Enviado_A_ERP=Enviado_A_ERP;
        this.Serie=Serie;
        this.Correlativo=Correlativo;
        this.IdDespachoEnc=IdDespachoEnc;
        this.DetalleOC=DetalleOC;
        this.DetalleLotes=DetalleLotes;
        this.DetallePallets=DetallePallets;
        this.ObjPoliza=ObjPoliza;
        this.ListaImg=ListaImg;
        this.PropietarioBodega=PropietarioBodega;
        this.ProveedorBodega=ProveedorBodega;
        this.EstadoOC=EstadoOC;
        this.IdBodega=IdBodega;
        this.IsNew=IsNew;
        this.EsDevolucion=EsDevolucion;
        this.TipoIngreso=TipoIngreso;
        this.ExisteRecepcionNoFinalizada=ExisteRecepcionNoFinalizada;
        this.No_Ticket_TMS=No_Ticket_TMS;
        this.IdNoDocumentoRef = IdNoDocumentoRef;
        this.IdAcuerdoComercial = IdAcuerdoComercial;
        this.No_Documento_Devolucion = No_Documento_Devolucion;
        this.IdPedidoEncDevolucion = IdPedidoEncDevolucion;

    }


    public int getIdOrdenCompraEnc() {
        return IdOrdenCompraEnc;
    }
    public void setIdOrdenCompraEnc(int value) {
        IdOrdenCompraEnc=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdProveedorBodega() {
        return IdProveedorBodega;
    }
    public void setIdProveedorBodega(int value) {
        IdProveedorBodega=value;
    }
    public int getIdTipoIngresoOC() {
        return IdTipoIngresoOC;
    }
    public void setIdTipoIngresoOC(int value) {
        IdTipoIngresoOC=value;
    }
    public int getIdEstadoOC() {
        return IdEstadoOC;
    }
    public void setIdEstadoOC(int value) {
        IdEstadoOC=value;
    }
    public int getIdMotivoDevolucion() {
        return IdMotivoDevolucion;
    }
    public void setIdMotivoDevolucion(int value) {
        IdMotivoDevolucion=value;
    }
    public String getFecha_Creacion() {
        return Fecha_Creacion;
    }
    public void setFecha_Creacion(String value) {
        Fecha_Creacion=value;
    }
    public String getHora_Creacion() {
        return Hora_Creacion;
    }
    public void setHora_Creacion(String value) {
        Hora_Creacion=value;
    }
    public String getNo_Documento() {
        return No_Documento;
    }
    public void setNo_Documento(String value) {
        No_Documento=value;
    }
    public String getUser_Agr() {
        return User_Agr;
    }
    public void setUser_Agr(String value) {
        User_Agr=value;
    }
    public String getFec_Agr() {
        return Fec_Agr;
    }
    public void setFec_Agr(String value) {
        Fec_Agr =value;
    }
    public String getUser_Mod() {
        return User_Mod;
    }
    public void setUser_Mod(String value) {
        User_Mod=value;
    }
    public String getFec_Mod() {
        return Fec_Mod;
    }
    public void setFec_Mod(String value) {
        Fec_Mod =value;
    }
    public String getProcedencia() {
        return Procedencia;
    }
    public void setProcedencia(String value) {
        Procedencia=value;
    }
    public String getNo_Marchamo() {
        return No_Marchamo;
    }
    public void setNo_Marchamo(String value) {
        No_Marchamo=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public boolean getControl_Poliza() {
        return Control_Poliza;
    }
    public void setControl_Poliza(boolean value) {
        Control_Poliza=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public String getFecha_Recepcion() {
        return Fecha_Recepcion;
    }
    public void setFecha_Recepcion(String value) {
        Fecha_Recepcion=value;
    }
    public String getHora_Inicio_Recepcion() {
        return Hora_Inicio_Recepcion;
    }
    public void setHora_Inicio_Recepcion(String value) {
        Hora_Inicio_Recepcion=value;
    }
    public String getHora_Fin_Recepcion() {
        return Hora_Fin_Recepcion;
    }
    public void setHora_Fin_Recepcion(String value) {
        Hora_Fin_Recepcion=value;
    }
    public int getIdMuelleRecepcion() {
        return IdMuelleRecepcion;
    }
    public void setIdMuelleRecepcion(int value) {
        IdMuelleRecepcion=value;
    }
    public boolean getProgramar_Recepcion() {
        return Programar_Recepcion;
    }
    public void setProgramar_Recepcion(boolean value) {
        Programar_Recepcion=value;
    }
    public int getIdMotivoAnulacionBodega() {
        return IdMotivoAnulacionBodega;
    }
    public void setIdMotivoAnulacionBodega(int value) {
        IdMotivoAnulacionBodega=value;
    }
    public boolean getEnviado_A_ERP() {
        return Enviado_A_ERP;
    }
    public void setEnviado_A_ERP(boolean value) {
        Enviado_A_ERP=value;
    }
    public String getSerie() {
        return Serie;
    }
    public void setSerie(String value) {
        Serie=value;
    }
    public String getCorrelativo() {
        return Correlativo;
    }
    public void setCorrelativo(String value) {
        Correlativo=value;
    }
    public int getIdDespachoEnc() {
        return IdDespachoEnc;
    }
    public void setIdDespachoEnc(int value) {
        IdDespachoEnc=value;
    }
    public clsBeTrans_oc_detList getDetalleOC() {
        return DetalleOC;
    }
    public void setDetalleOC(clsBeTrans_oc_detList value) {
        DetalleOC=value;
    }
    public clsBeTrans_oc_det_loteList getDetalleLotes() {
        return DetalleLotes;
    }
    public void setDetalleLotes(clsBeTrans_oc_det_loteList value) {
        DetalleLotes=value;
    }
    public clsBeI_nav_barras_palletList getDetallePallets() {
        return DetallePallets;
    }
    public void setDetallePallets(clsBeI_nav_barras_palletList value) {
        DetallePallets=value;
    }
    public clsBeTrans_oc_pol getObjPoliza() {
        return ObjPoliza;
    }
    public void setObjPoliza(clsBeTrans_oc_pol value) {
        ObjPoliza=value;
    }
    public clsBeTrans_oc_imagenList getListaImg() {
        return ListaImg;
    }
    public void setListaImg(clsBeTrans_oc_imagenList value) {
        ListaImg=value;
    }
    public clsBePropietario_bodega getPropietarioBodega() {
        return PropietarioBodega;
    }
    public void setPropietarioBodega(clsBePropietario_bodega value) {
        PropietarioBodega=value;
    }
    public clsBeProveedor_bodega getProveedorBodega() {
        return ProveedorBodega;
    }
    public void setProveedorBodega(clsBeProveedor_bodega value) {
        ProveedorBodega=value;
    }
    public clsBeTrans_oc_estado getEstadoOC() {
        return EstadoOC;
    }
    public void setEstadoOC(clsBeTrans_oc_estado value) {
        EstadoOC=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public boolean getEsDevolucion() {
        return EsDevolucion;
    }
    public void setEsDevolucion(boolean value) {
        EsDevolucion=value;
    }
    public clsBeTrans_oc_ti getTipoIngreso() {
        return TipoIngreso;
    }
    public void setTipoIngreso(clsBeTrans_oc_ti value) {
        TipoIngreso=value;
    }
    public boolean getExisteRecepcionNoFinalizada() {
        return ExisteRecepcionNoFinalizada;
    }
    public void setExisteRecepcionNoFinalizada(boolean value) {
        ExisteRecepcionNoFinalizada=value;
    }
    public String getNo_Ticket_TMS() {
        return No_Ticket_TMS;
    }
    public void setNo_Ticket_TMS(String value) {
        No_Ticket_TMS=value;
    }
    public int getIdNoDocumentoRef() {
        return IdNoDocumentoRef;
    }
    public void setIdNoDocumentoRef(int value) {
        IdNoDocumentoRef=value;
    }
    public int getIdAcuerdoComercial() {
        return IdAcuerdoComercial;
    }
    public void setIdAcuerdoComercial(int value) {
        IdAcuerdoComercial=value;
    }
    public int getIdOperadorBodegaDefecto() {
        return IdOperadorBodegaDefecto;
    }
    public void setIdOperadorBodegaDefecto(int value) {
        IdOperadorBodegaDefecto=value;
    }
    public String getNo_Documento_Recepcion_ERP() {
        return No_Documento_Recepcion_ERP;
    }
    public void setNo_Documento_Recepcion_ERP(String value) {
        No_Documento_Recepcion_ERP=value;
    }
    public int getIdPedidoEncDevolucion() {
        return IdPedidoEncDevolucion;
    }
    public void setIdPedidoEncDevolucion(int value) {
        IdOperadorBodegaDefecto=value;
    }
    public String getNo_Documento_Devolucion() {
        return No_Documento_Devolucion;
    }
    public void setNo_Documento_Devolucion(String value) {
        No_Documento_Devolucion=value;
    }
}