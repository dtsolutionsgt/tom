package com.dts.classes.Transacciones.Recepcion;


import com.dts.classes.Mantenimientos.Propietario.Propietario_bodega.clsBePropietario_bodega;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros.clsBeTrans_re_det_parametrosList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_fact.clsBeTrans_re_factList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_img.clsBeTrans_re_imgList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_oc.clsBeTrans_re_oc;
import com.dts.classes.Transacciones.Recepcion.Trans_re_op.clsBeTrans_re_opList;
import com.dts.classes.Mantenimientos.Bodega.clsBeBodega_muelles;

import org.simpleframework.xml.Element;

public class clsBeTrans_re_enc {

    @Element(required=false) public int IdRecepcionEnc;
    @Element(required=false) public int IdMuelle;
    @Element(required=false) public int IdUbicacionRecepcion;
    @Element(required=false) public String IdTipoTransaccion;
    @Element(required=false) public String Fecha_recepcion;
    @Element(required=false) public String Hora_ini_pc;
    @Element(required=false) public String Hora_fin_pc;
    @Element(required=false) public boolean Muestra_precio;
    @Element(required=false) public String Estado;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr;
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod;
    @Element(required=false) public String Fecha_tarea;
    @Element(required=false) public boolean Tomar_fotos;
    @Element(required=false) public boolean Escanear_rec_ubic;
    @Element(required=false) public boolean Para_por_codigo;
    @Element(required=false) public String Observacion;
    @Element(required=false) public Byte Firma_piloto;
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String NoGuia;
    @Element(required=false) public boolean CorreoEnviado;
    @Element(required=false) public boolean Revision_Inconsistencia;
    @Element(required=false) public String bloqueada_por;
    @Element(required=false) public int IdUsuarioBloqueo;
    @Element(required=false) public int IdMotivoAnulacionBodega;
    @Element(required=false) public boolean Habilitar_Stock;
    @Element(required=false) public int IdVehiculo;
    @Element(required=false) public int IdPiloto;
    @Element(required=false) public String No_Marchamo;
    @Element(required=false) public boolean Mostrar_Cantidad_Esperada;
    @Element(required=false) public clsBeTrans_re_oc OrdenCompraRec=new clsBeTrans_re_oc();
    @Element(required=false) public clsBeTrans_re_detList Detalle=new clsBeTrans_re_detList();
    @Element(required=false) public clsBeTrans_re_det_parametrosList DetalleParametros=new clsBeTrans_re_det_parametrosList();
    @Element(required=false) public clsBeTrans_re_opList DetalleOperadores=new clsBeTrans_re_opList();
    @Element(required=false) public clsBeTrans_re_imgList DetalleImagenes=new clsBeTrans_re_imgList();
    @Element(required=false) public clsBeTrans_re_factList DetalleFacturas=new clsBeTrans_re_factList();
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public String Descripcion;
    @Element(required=false) public String UbicacionRecepcion;
    @Element(required=false) public String NombrePropietario;
    @Element(required=false) public String Bodega;
    @Element(required=false) public String Usuario;
    @Element(required=false) public clsBePropietario_bodega PropietarioBodega=new clsBePropietario_bodega();
    @Element(required=false) public String PropietarioOC;
    @Element(required=false) public String Proveedor;
    @Element(required=false) public int NoOrdencompra;
    @Element(required=false) public String NoDocumentoOC;
    @Element(required=false) public String TipoTrans;
    @Element(required=false) public clsBeBodega_muelles Muelle=new clsBeBodega_muelles();
    @Element(required=false) public String MuelleRec;
    @Element(required=false) public String NOFactura;
    @Element(required=false) public clsBeTarea_hh TareaHH=new clsBeTarea_hh();


    public clsBeTrans_re_enc() {
    }

    public clsBeTrans_re_enc(int IdRecepcionEnc,int IdMuelle,int IdUbicacionRecepcion,String IdTipoTransaccion,
                             String Fecha_recepcion,String Hora_ini_pc,String Hora_fin_pc,boolean Muestra_precio,
                             String Estado,String User_agr,String Fec_agr,String User_mod,
                             String Fec_mod,String Fecha_tarea,boolean Tomar_fotos,boolean Escanear_rec_ubic,
                             boolean Para_por_codigo,String Observacion,Byte Firma_piloto,boolean Activo,
                             String NoGuia,boolean CorreoEnviado,boolean Revision_Inconsistencia,String bloqueada_por,
                             int IdUsuarioBloqueo,int IdMotivoAnulacionBodega,boolean Habilitar_Stock,int IdVehiculo,
                             int IdPiloto,String No_Marchamo,boolean Mostrar_Cantidad_Esperada,clsBeTrans_re_oc OrdenCompraRec,
                             clsBeTrans_re_detList Detalle,clsBeTrans_re_det_parametrosList DetalleParametros,clsBeTrans_re_opList DetalleOperadores,clsBeTrans_re_imgList DetalleImagenes,
                             clsBeTrans_re_factList DetalleFacturas,boolean IsNew,String Descripcion,String UbicacionRecepcion,
                             String NombrePropietario,String Bodega,String Usuario,clsBePropietario_bodega PropietarioBodega,
                             String PropietarioOC,String Proveedor,int NoOrdencompra,String NoDocumentoOC,
                             String TipoTrans,clsBeBodega_muelles Muelle,String MuelleRec,String NOFactura,
                             clsBeTarea_hh TareaHH) {

        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdMuelle=IdMuelle;
        this.IdUbicacionRecepcion=IdUbicacionRecepcion;
        this.IdTipoTransaccion=IdTipoTransaccion;
        this.Fecha_recepcion=Fecha_recepcion;
        this.Hora_ini_pc=Hora_ini_pc;
        this.Hora_fin_pc=Hora_fin_pc;
        this.Muestra_precio=Muestra_precio;
        this.Estado=Estado;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Fecha_tarea=Fecha_tarea;
        this.Tomar_fotos=Tomar_fotos;
        this.Escanear_rec_ubic=Escanear_rec_ubic;
        this.Para_por_codigo=Para_por_codigo;
        this.Observacion=Observacion;
        this.Firma_piloto=Firma_piloto;
        this.Activo=Activo;
        this.NoGuia=NoGuia;
        this.CorreoEnviado=CorreoEnviado;
        this.Revision_Inconsistencia=Revision_Inconsistencia;
        this.bloqueada_por=bloqueada_por;
        this.IdUsuarioBloqueo=IdUsuarioBloqueo;
        this.IdMotivoAnulacionBodega=IdMotivoAnulacionBodega;
        this.Habilitar_Stock=Habilitar_Stock;
        this.IdVehiculo=IdVehiculo;
        this.IdPiloto=IdPiloto;
        this.No_Marchamo=No_Marchamo;
        this.Mostrar_Cantidad_Esperada=Mostrar_Cantidad_Esperada;
        this.OrdenCompraRec=OrdenCompraRec;
        this.Detalle=Detalle;
        this.DetalleParametros=DetalleParametros;
        this.DetalleOperadores=DetalleOperadores;
        this.DetalleImagenes=DetalleImagenes;
        this.DetalleFacturas=DetalleFacturas;
        this.IsNew=IsNew;
        this.Descripcion=Descripcion;
        this.UbicacionRecepcion=UbicacionRecepcion;
        this.NombrePropietario=NombrePropietario;
        this.Bodega=Bodega;
        this.Usuario=Usuario;
        this.PropietarioBodega=PropietarioBodega;
        this.PropietarioOC=PropietarioOC;
        this.Proveedor=Proveedor;
        this.NoOrdencompra=NoOrdencompra;
        this.NoDocumentoOC=NoDocumentoOC;
        this.TipoTrans=TipoTrans;
        this.Muelle=Muelle;
        this.MuelleRec=MuelleRec;
        this.NOFactura=NOFactura;
        this.TareaHH=TareaHH;

    }


    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdMuelle() {
        return IdMuelle;
    }
    public void setIdMuelle(int value) {
        IdMuelle=value;
    }
    public int getIdUbicacionRecepcion() {
        return IdUbicacionRecepcion;
    }
    public void setIdUbicacionRecepcion(int value) {
        IdUbicacionRecepcion=value;
    }
    public String getIdTipoTransaccion() {
        return IdTipoTransaccion;
    }
    public void setIdTipoTransaccion(String value) {
        IdTipoTransaccion=value;
    }
    public String getFecha_recepcion() {
        return Fecha_recepcion;
    }
    public void setFecha_recepcion(String value) {
        Fecha_recepcion=value;
    }
    public String getHora_ini_pc() {
        return Hora_ini_pc;
    }
    public void setHora_ini_pc(String value) {
        Hora_ini_pc=value;
    }
    public String getHora_fin_pc() {
        return Hora_fin_pc;
    }
    public void setHora_fin_pc(String value) {
        Hora_fin_pc=value;
    }
    public boolean getMuestra_precio() {
        return Muestra_precio;
    }
    public void setMuestra_precio(boolean value) {
        Muestra_precio=value;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
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
    public String getFecha_tarea() {
        return Fecha_tarea;
    }
    public void setFecha_tarea(String value) {
        Fecha_tarea=value;
    }
    public boolean getTomar_fotos() {
        return Tomar_fotos;
    }
    public void setTomar_fotos(boolean value) {
        Tomar_fotos=value;
    }
    public boolean getEscanear_rec_ubic() {
        return Escanear_rec_ubic;
    }
    public void setEscanear_rec_ubic(boolean value) {
        Escanear_rec_ubic=value;
    }
    public boolean getPara_por_codigo() {
        return Para_por_codigo;
    }
    public void setPara_por_codigo(boolean value) {
        Para_por_codigo=value;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public Byte getFirma_piloto() {
        return Firma_piloto;
    }
    public void setFirma_piloto(Byte value) {
        Firma_piloto=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public String getNoGuia() {
        return NoGuia;
    }
    public void setNoGuia(String value) {
        NoGuia=value;
    }
    public boolean getCorreoEnviado() {
        return CorreoEnviado;
    }
    public void setCorreoEnviado(boolean value) {
        CorreoEnviado=value;
    }
    public boolean getRevision_Inconsistencia() {
        return Revision_Inconsistencia;
    }
    public void setRevision_Inconsistencia(boolean value) {
        Revision_Inconsistencia=value;
    }
    public String getbloqueada_por() {
        return bloqueada_por;
    }
    public void setbloqueada_por(String value) {
        bloqueada_por=value;
    }
    public int getIdUsuarioBloqueo() {
        return IdUsuarioBloqueo;
    }
    public void setIdUsuarioBloqueo(int value) {
        IdUsuarioBloqueo=value;
    }
    public int getIdMotivoAnulacionBodega() {
        return IdMotivoAnulacionBodega;
    }
    public void setIdMotivoAnulacionBodega(int value) {
        IdMotivoAnulacionBodega=value;
    }
    public boolean getHabilitar_Stock() {
        return Habilitar_Stock;
    }
    public void setHabilitar_Stock(boolean value) {
        Habilitar_Stock=value;
    }
    public int getIdVehiculo() {
        return IdVehiculo;
    }
    public void setIdVehiculo(int value) {
        IdVehiculo=value;
    }
    public int getIdPiloto() {
        return IdPiloto;
    }
    public void setIdPiloto(int value) {
        IdPiloto=value;
    }
    public String getNo_Marchamo() {
        return No_Marchamo;
    }
    public void setNo_Marchamo(String value) {
        No_Marchamo=value;
    }
    public boolean getMostrar_Cantidad_Esperada() {
        return Mostrar_Cantidad_Esperada;
    }
    public void setMostrar_Cantidad_Esperada(boolean value) {
        Mostrar_Cantidad_Esperada=value;
    }
    public clsBeTrans_re_oc getOrdenCompraRec() {
        return OrdenCompraRec;
    }
    public void setOrdenCompraRec(clsBeTrans_re_oc value) {
        OrdenCompraRec=value;
    }
    public clsBeTrans_re_detList getDetalle() {
        return Detalle;
    }
    public void setDetalle(clsBeTrans_re_detList value) {
        Detalle=value;
    }
    public clsBeTrans_re_det_parametrosList getDetalleParametros() {
        return DetalleParametros;
    }
    public void setDetalleParametros(clsBeTrans_re_det_parametrosList value) {
        DetalleParametros=value;
    }
    public clsBeTrans_re_opList getDetalleOperadores() {
        return DetalleOperadores;
    }
    public void setDetalleOperadores(clsBeTrans_re_opList value) {
        DetalleOperadores=value;
    }
    public clsBeTrans_re_imgList getDetalleImagenes() {
        return DetalleImagenes;
    }
    public void setDetalleImagenes(clsBeTrans_re_imgList value) {
        DetalleImagenes=value;
    }
    public clsBeTrans_re_factList getDetalleFacturas() {
        return DetalleFacturas;
    }
    public void setDetalleFacturas(clsBeTrans_re_factList value) {
        DetalleFacturas=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String value) {
        Descripcion=value;
    }
    public String getUbicacionRecepcion() {
        return UbicacionRecepcion;
    }
    public void setUbicacionRecepcion(String value) {
        UbicacionRecepcion=value;
    }
    public String getNombrePropietario() {
        return NombrePropietario;
    }
    public void setNombrePropietario(String value) {
        NombrePropietario=value;
    }
    public String getBodega() {
        return Bodega;
    }
    public void setBodega(String value) {
        Bodega=value;
    }
    public String getUsuario() {
        return Usuario;
    }
    public void setUsuario(String value) {
        Usuario=value;
    }
    public clsBePropietario_bodega getPropietarioBodega() {
        return PropietarioBodega;
    }
    public void setPropietarioBodega(clsBePropietario_bodega value) {
        PropietarioBodega=value;
    }
    public String getPropietarioOC() {
        return PropietarioOC;
    }
    public void setPropietarioOC(String value) {
        PropietarioOC=value;
    }
    public String getProveedor() {
        return Proveedor;
    }
    public void setProveedor(String value) {
        Proveedor=value;
    }
    public int getNoOrdencompra() {
        return NoOrdencompra;
    }
    public void setNoOrdencompra(int value) {
        NoOrdencompra=value;
    }
    public String getNoDocumentoOC() {
        return NoDocumentoOC;
    }
    public void setNoDocumentoOC(String value) {
        NoDocumentoOC=value;
    }
    public String getTipoTrans() {
        return TipoTrans;
    }
    public void setTipoTrans(String value) {
        TipoTrans=value;
    }
    public clsBeBodega_muelles getMuelle() {
        return Muelle;
    }
    public void setMuelle(clsBeBodega_muelles value) {
        Muelle=value;
    }
    public String getMuelleRec() {
        return MuelleRec;
    }
    public void setMuelleRec(String value) {
        MuelleRec=value;
    }
    public String getNOFactura() {
        return NOFactura;
    }
    public void setNOFactura(String value) {
        NOFactura=value;
    }
    public clsBeTarea_hh getTareaHH() {
        return TareaHH;
    }
    public void setTareaHH(clsBeTarea_hh value) {
        TareaHH=value;
    }

}

