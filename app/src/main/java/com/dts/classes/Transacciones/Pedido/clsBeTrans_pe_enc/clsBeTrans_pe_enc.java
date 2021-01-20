package com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc;

import com.dts.classes.Mantenimientos.Cliente.clsBeCliente.clsBeCliente;
import com.dts.classes.Mantenimientos.Propietario.Propietario_bodega.clsBePropietario_bodega;
import com.dts.classes.Transacciones.Pedido.Trasn_pe_pol.clsBeTrans_pe_pol;
import com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_det.clsBeTrans_pe_detList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.TipoPedido.clsBeTrans_pe_tipo;

import org.simpleframework.xml.Element;

public class clsBeTrans_pe_enc {

    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public int IdBodega=0;
    @Element(required=false) public int IdCliente=0;
    @Element(required=false) public int IdMuelle=0;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdPickingEnc=0;
    @Element(required=false) public String Fecha_Pedido="";
    @Element(required=false) public String Hora_ini="";
    @Element(required=false) public String Hora_fin="";
    @Element(required=false) public String Ubicacion="";
    @Element(required=false) public String Estado="";
    @Element(required=false) public int No_despacho=0;
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public int No_documento=0;
    @Element(required=false) public boolean Local=false;
    @Element(required=false) public boolean Pallet_primero=false;
    @Element(required=false) public double Dias_cliente=0;
    @Element(required=false) public boolean Anulado=false;
    @Element(required=false) public double RoadKilometraje=0;
    @Element(required=false) public String RoadFechaEntr="";
    @Element(required=false) public String RoadDirEntrega="";
    @Element(required=false) public double RoadTotal=0;
    @Element(required=false) public double RoadDesMonto=0;
    @Element(required=false) public double RoadImpMonto=0;
    @Element(required=false) public double RoadPeso=0;
    @Element(required=false) public String RoadBandera="";
    @Element(required=false) public String RoadStatCom="";
    @Element(required=false) public String RoadCalcoBJ="";
    @Element(required=false) public int RoadImpres=0;
    @Element(required=false) public String RoadADD1="";
    @Element(required=false) public String RoadADD2="";
    @Element(required=false) public String RoadADD3="";
    @Element(required=false) public String RoadStatProc="";
    @Element(required=false) public boolean RoadRechazado=false;
    @Element(required=false) public String RoadRazon_Rechazado="";
    @Element(required=false) public boolean RoadInformado=false;
    @Element(required=false) public String RoadSucursal="";
    @Element(required=false) public int RoadIdDespacho=0;
    @Element(required=false) public int RoadIdFacturacion=0;
    @Element(required=false) public int RoadIdRuta=0;
    @Element(required=false) public int RoadIdVendedor=0;
    @Element(required=false) public int RoadIdRutaDespacho=0;
    @Element(required=false) public int RoadIdVendedorDespacho=0;
    @Element(required=false) public String Observacion="";
    @Element(required=false) public boolean PedidoRoad=false;
    @Element(required=false) public String HoraEntregaDesde="";
    @Element(required=false) public String HoraEntregaHasta="";
    @Element(required=false) public String Referencia="";
    @Element(required=false) public boolean Enviado_A_ERP=false;
    @Element(required=false) public String Referencia_Documento_Ingreso_Bodega_Destino="";
    @Element(required=false) public boolean Sync_MI3=false;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeTrans_pe_detList Detalle=new clsBeTrans_pe_detList();
    @Element(required=false) public clsBeTrans_picking_enc Picking=new clsBeTrans_picking_enc();
    @Element(required=false) public clsBePropietario_bodega PropietarioBodega=new clsBePropietario_bodega();
    @Element(required=false) public clsBeCliente Cliente=new clsBeCliente();
    @Element(required=false) public clsBeTrans_pe_tipo TipoPedido=new clsBeTrans_pe_tipo();
    @Element(required=false) public boolean Control_Ultimo_Lote=false;
    @Element(required=false) public String Serie="";
    @Element(required=false) public int Correlativo=0;
    @Element(required=false) public clsBeTrans_pe_pol ObjPoliza= new clsBeTrans_pe_pol();


    public clsBeTrans_pe_enc() {
    }

    public clsBeTrans_pe_enc(int IdPedidoEnc,int IdBodega,int IdCliente,int IdMuelle,
                             int IdPropietarioBodega,int IdPickingEnc,String Fecha_Pedido,String Hora_ini,
                             String Hora_fin,String Ubicacion,String Estado,int No_despacho,
                             boolean Activo,String User_agr,String Fec_agr,String User_mod,
                             String Fec_mod,int No_documento,boolean Local,boolean Pallet_primero,
                             double Dias_cliente,boolean Anulado,double RoadKilometraje,String RoadFechaEntr,
                             String RoadDirEntrega,double RoadTotal,double RoadDesMonto,double RoadImpMonto,
                             double RoadPeso,String RoadBandera,String RoadStatCom,String RoadCalcoBJ,
                             int RoadImpres,String RoadADD1,String RoadADD2,String RoadADD3,
                             String RoadStatProc,boolean RoadRechazado,String RoadRazon_Rechazado,boolean RoadInformado,
                             String RoadSucursal,int RoadIdDespacho,int RoadIdFacturacion,int RoadIdRuta,
                             int RoadIdVendedor,int RoadIdRutaDespacho,int RoadIdVendedorDespacho,String Observacion,
                             boolean PedidoRoad,String HoraEntregaDesde,String HoraEntregaHasta,String Referencia,
                             boolean Enviado_A_ERP,String Referencia_Documento_Ingreso_Bodega_Destino,boolean Sync_MI3,boolean IsNew,clsBeTrans_pe_detList Detalle,
                             clsBeTrans_picking_enc Picking,clsBePropietario_bodega PropietarioBodega,clsBeCliente Cliente,clsBeTrans_pe_tipo TipoPedido,
                             boolean Control_Ultimo_Lote,String Serie,int Correlativo) {

        this.IdPedidoEnc=IdPedidoEnc;
        this.IdBodega=IdBodega;
        this.IdCliente=IdCliente;
        this.IdMuelle=IdMuelle;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdPickingEnc=IdPickingEnc;
        this.Fecha_Pedido=Fecha_Pedido;
        this.Hora_ini=Hora_ini;
        this.Hora_fin=Hora_fin;
        this.Ubicacion=Ubicacion;
        this.Estado=Estado;
        this.No_despacho=No_despacho;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.No_documento=No_documento;
        this.Local=Local;
        this.Pallet_primero=Pallet_primero;
        this.Dias_cliente=Dias_cliente;
        this.Anulado=Anulado;
        this.RoadKilometraje=RoadKilometraje;
        this.RoadFechaEntr=RoadFechaEntr;
        this.RoadDirEntrega=RoadDirEntrega;
        this.RoadTotal=RoadTotal;
        this.RoadDesMonto=RoadDesMonto;
        this.RoadImpMonto=RoadImpMonto;
        this.RoadPeso=RoadPeso;
        this.RoadBandera=RoadBandera;
        this.RoadStatCom=RoadStatCom;
        this.RoadCalcoBJ=RoadCalcoBJ;
        this.RoadImpres=RoadImpres;
        this.RoadADD1=RoadADD1;
        this.RoadADD2=RoadADD2;
        this.RoadADD3=RoadADD3;
        this.RoadStatProc=RoadStatProc;
        this.RoadRechazado=RoadRechazado;
        this.RoadRazon_Rechazado=RoadRazon_Rechazado;
        this.RoadInformado=RoadInformado;
        this.RoadSucursal=RoadSucursal;
        this.RoadIdDespacho=RoadIdDespacho;
        this.RoadIdFacturacion=RoadIdFacturacion;
        this.RoadIdRuta=RoadIdRuta;
        this.RoadIdVendedor=RoadIdVendedor;
        this.RoadIdRutaDespacho=RoadIdRutaDespacho;
        this.RoadIdVendedorDespacho=RoadIdVendedorDespacho;
        this.Observacion=Observacion;
        this.PedidoRoad=PedidoRoad;
        this.HoraEntregaDesde=HoraEntregaDesde;
        this.HoraEntregaHasta=HoraEntregaHasta;
        this.Referencia=Referencia;
        this.Enviado_A_ERP=Enviado_A_ERP;
        this.Referencia_Documento_Ingreso_Bodega_Destino=Referencia_Documento_Ingreso_Bodega_Destino;
        this.Sync_MI3=Sync_MI3;
        this.IsNew=IsNew;
        this.Detalle=Detalle;
        this.Picking=Picking;
        this.PropietarioBodega=PropietarioBodega;
        this.Cliente=Cliente;
        this.TipoPedido=TipoPedido;
        this.Control_Ultimo_Lote=Control_Ultimo_Lote;
        this.Serie=Serie;
        this.Correlativo=Correlativo;

    }


    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public int getIdBodega() {
        return IdBodega;
    }
    public void setIdBodega(int value) {
        IdBodega=value;
    }
    public int getIdCliente() {
        return IdCliente;
    }
    public void setIdCliente(int value) {
        IdCliente=value;
    }
    public int getIdMuelle() {
        return IdMuelle;
    }
    public void setIdMuelle(int value) {
        IdMuelle=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdPickingEnc() {
        return IdPickingEnc;
    }
    public void setIdPickingEnc(int value) {
        IdPickingEnc=value;
    }
    public String getFecha_Pedido() {
        return Fecha_Pedido;
    }
    public void setFecha_Pedido(String value) {
        Fecha_Pedido=value;
    }
    public String getHora_ini() {
        return Hora_ini;
    }
    public void setHora_ini(String value) {
        Hora_ini=value;
    }
    public String getHora_fin() {
        return Hora_fin;
    }
    public void setHora_fin(String value) {
        Hora_fin=value;
    }
    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }
    public int getNo_despacho() {
        return No_despacho;
    }
    public void setNo_despacho(int value) {
        No_despacho=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
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
    public int getNo_documento() {
        return No_documento;
    }
    public void setNo_documento(int value) {
        No_documento=value;
    }
    public boolean getLocal() {
        return Local;
    }
    public void setLocal(boolean value) {
        Local=value;
    }
    public boolean getPallet_primero() {
        return Pallet_primero;
    }
    public void setPallet_primero(boolean value) {
        Pallet_primero=value;
    }
    public double getDias_cliente() {
        return Dias_cliente;
    }
    public void setDias_cliente(double value) {
        Dias_cliente=value;
    }
    public boolean getAnulado() {
        return Anulado;
    }
    public void setAnulado(boolean value) {
        Anulado=value;
    }
    public double getRoadKilometraje() {
        return RoadKilometraje;
    }
    public void setRoadKilometraje(double value) {
        RoadKilometraje=value;
    }
    public String getRoadFechaEntr() {
        return RoadFechaEntr;
    }
    public void setRoadFechaEntr(String value) {
        RoadFechaEntr=value;
    }
    public String getRoadDirEntrega() {
        return RoadDirEntrega;
    }
    public void setRoadDirEntrega(String value) {
        RoadDirEntrega=value;
    }
    public double getRoadTotal() {
        return RoadTotal;
    }
    public void setRoadTotal(double value) {
        RoadTotal=value;
    }
    public double getRoadDesMonto() {
        return RoadDesMonto;
    }
    public void setRoadDesMonto(double value) {
        RoadDesMonto=value;
    }
    public double getRoadImpMonto() {
        return RoadImpMonto;
    }
    public void setRoadImpMonto(double value) {
        RoadImpMonto=value;
    }
    public double getRoadPeso() {
        return RoadPeso;
    }
    public void setRoadPeso(double value) {
        RoadPeso=value;
    }
    public String getRoadBandera() {
        return RoadBandera;
    }
    public void setRoadBandera(String value) {
        RoadBandera=value;
    }
    public String getRoadStatCom() {
        return RoadStatCom;
    }
    public void setRoadStatCom(String value) {
        RoadStatCom=value;
    }
    public String getRoadCalcoBJ() {
        return RoadCalcoBJ;
    }
    public void setRoadCalcoBJ(String value) {
        RoadCalcoBJ=value;
    }
    public int getRoadImpres() {
        return RoadImpres;
    }
    public void setRoadImpres(int value) {
        RoadImpres=value;
    }
    public String getRoadADD1() {
        return RoadADD1;
    }
    public void setRoadADD1(String value) {
        RoadADD1=value;
    }
    public String getRoadADD2() {
        return RoadADD2;
    }
    public void setRoadADD2(String value) {
        RoadADD2=value;
    }
    public String getRoadADD3() {
        return RoadADD3;
    }
    public void setRoadADD3(String value) {
        RoadADD3=value;
    }
    public String getRoadStatProc() {
        return RoadStatProc;
    }
    public void setRoadStatProc(String value) {
        RoadStatProc=value;
    }
    public boolean getRoadRechazado() {
        return RoadRechazado;
    }
    public void setRoadRechazado(boolean value) {
        RoadRechazado=value;
    }
    public String getRoadRazon_Rechazado() {
        return RoadRazon_Rechazado;
    }
    public void setRoadRazon_Rechazado(String value) {
        RoadRazon_Rechazado=value;
    }
    public boolean getRoadInformado() {
        return RoadInformado;
    }
    public void setRoadInformado(boolean value) {
        RoadInformado=value;
    }
    public String getRoadSucursal() {
        return RoadSucursal;
    }
    public void setRoadSucursal(String value) {
        RoadSucursal=value;
    }
    public int getRoadIdDespacho() {
        return RoadIdDespacho;
    }
    public void setRoadIdDespacho(int value) {
        RoadIdDespacho=value;
    }
    public int getRoadIdFacturacion() {
        return RoadIdFacturacion;
    }
    public void setRoadIdFacturacion(int value) {
        RoadIdFacturacion=value;
    }
    public int getRoadIdRuta() {
        return RoadIdRuta;
    }
    public void setRoadIdRuta(int value) {
        RoadIdRuta=value;
    }
    public int getRoadIdVendedor() {
        return RoadIdVendedor;
    }
    public void setRoadIdVendedor(int value) {
        RoadIdVendedor=value;
    }
    public int getRoadIdRutaDespacho() {
        return RoadIdRutaDespacho;
    }
    public void setRoadIdRutaDespacho(int value) {
        RoadIdRutaDespacho=value;
    }
    public int getRoadIdVendedorDespacho() {
        return RoadIdVendedorDespacho;
    }
    public void setRoadIdVendedorDespacho(int value) {
        RoadIdVendedorDespacho=value;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public boolean getPedidoRoad() {
        return PedidoRoad;
    }
    public void setPedidoRoad(boolean value) {
        PedidoRoad=value;
    }
    public String getHoraEntregaDesde() {
        return HoraEntregaDesde;
    }
    public void setHoraEntregaDesde(String value) {
        HoraEntregaDesde=value;
    }
    public String getHoraEntregaHasta() {
        return HoraEntregaHasta;
    }
    public void setHoraEntregaHasta(String value) {
        HoraEntregaHasta=value;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String value) {
        Referencia=value;
    }
    public boolean getEnviado_A_ERP() {
        return Enviado_A_ERP;
    }
    public void setEnviado_A_ERP(boolean value) {
        Enviado_A_ERP=value;
    }
    public String getReferencia_Documento_Ingreso_Bodega_Destino() {
        return Referencia_Documento_Ingreso_Bodega_Destino;
    }
    public void setReferencia_Documento_Ingreso_Bodega_Destino(String value) {
        Referencia_Documento_Ingreso_Bodega_Destino=value;
    }
    public boolean getSync_MI3() {
        return Sync_MI3;
    }
    public void setSync_MI3(boolean value) {
        Sync_MI3=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public clsBeTrans_pe_detList getDetalle() {
        return Detalle;
    }
    public void setDetalle(clsBeTrans_pe_detList value) {
        Detalle=value;
    }
    public clsBeTrans_picking_enc getPicking() {
        return Picking;
    }
    public void setPicking(clsBeTrans_picking_enc value) {
        Picking=value;
    }
    public clsBePropietario_bodega getPropietarioBodega() {
        return PropietarioBodega;
    }
    public void setPropietarioBodega(clsBePropietario_bodega value) {
        PropietarioBodega=value;
    }
    public clsBeCliente getCliente() {
        return Cliente;
    }
    public void setCliente(clsBeCliente value) {
        Cliente=value;
    }
    public clsBeTrans_pe_tipo getTipoPedido() {
        return TipoPedido;
    }
    public void setTipoPedido(clsBeTrans_pe_tipo value) {
        TipoPedido=value;
    }
    public boolean getControl_Ultimo_Lote() {
        return Control_Ultimo_Lote;
    }
    public void setControl_Ultimo_Lote(boolean value) {
        Control_Ultimo_Lote=value;
    }
    public String getSerie() {
        return Serie;
    }
    public void setSerie(String value) {
        Serie=value;
    }
    public int getCorrelativo() {
        return Correlativo;
    }
    public void setCorrelativo(int value) {
        Correlativo=value;
    }

}


