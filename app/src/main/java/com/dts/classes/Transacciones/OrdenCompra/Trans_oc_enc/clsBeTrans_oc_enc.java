package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc;

import com.dts.classes.Mantenimientos.Barra_pallet.clsBeI_nav_barras_palletList;
import com.dts.classes.Mantenimientos.Propietario.Propietario_bodega.clsBePropietario_bodega;
import com.dts.classes.Mantenimientos.Proveedor.Proveedor_bodega.clsBeProveedor_bodega;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote.clsBeTrans_oc_det_loteList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_estado.clsBeTrans_oc_estado;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_imagen.clsBeTrans_oc_imagenList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_pol.clsBeTrans_oc_pol;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti.clsBeTrans_oc_ti;
import com.google.gson.annotations.SerializedName;
import org.simpleframework.xml.Element;
import java.util.ArrayList;
import java.util.List;

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
    @Element(required=false) public String Fec_Agr ="1900-01-01T00:00:00";
    @Element(required=false) public String User_Mod;
    @Element(required=false) public String Fec_Mod="1900-01-01T00:00:00";
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

    @Element(required=false) public clsBeTrans_oc_detList DetalleOC = new clsBeTrans_oc_detList();
    @SerializedName("DetalleOC") private List<clsBeTrans_oc_det> DetalleOCJson = new ArrayList<>();

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
    @Element(required=false) public boolean Push_To_NAV=false;
    @Element(required=false) public String No_Documento_Ubicacion_ERP="";
    @Element(required=false) public boolean PutAway_Registrado=false;
    @Element(required=false) public String Codigo_Empresa_ERP="";
    @Element(required=false) public int IdCampana=0;

    public clsBeTrans_oc_detList getDetalleOC() {
        if (DetalleOC != null && DetalleOC.items != null && !DetalleOC.items.isEmpty()) {
            return DetalleOC;
        } else {
            clsBeTrans_oc_detList wrapper = new clsBeTrans_oc_detList();
            wrapper.items = DetalleOCJson;
            return wrapper;
        }
    }

    public void setDetalleOC(clsBeTrans_oc_detList value) {
        DetalleOC = value;
        DetalleOCJson = (value != null) ? value.items : new ArrayList<>();
    }

    public void syncDetalleOCToXml() {
        if ((DetalleOC == null || DetalleOC.items == null || DetalleOC.items.isEmpty()) && DetalleOCJson != null) {
            DetalleOC = new clsBeTrans_oc_detList();
            DetalleOC.items = DetalleOCJson;
        }
    }

    public clsBeTrans_oc_pol getObjPoliza() {
        return ObjPoliza;
    }

    public void setObjPoliza(clsBeTrans_oc_pol objPoliza) {
        this.ObjPoliza = objPoliza;
    }

    public clsBeTrans_oc_imagenList getListaImg() {
        return ListaImg;
    }

    public void setListaImg(clsBeTrans_oc_imagenList listaImg) {
        this.ListaImg = listaImg;
    }

    public clsBePropietario_bodega getPropietarioBodega() {
        return PropietarioBodega;
    }

    public void setPropietarioBodega(clsBePropietario_bodega propietarioBodega) {
        this.PropietarioBodega = propietarioBodega;
    }

    public clsBeProveedor_bodega getProveedorBodega() {
        return ProveedorBodega;
    }

    public void setProveedorBodega(clsBeProveedor_bodega proveedorBodega) {
        this.ProveedorBodega = proveedorBodega;
    }

    public clsBeTrans_oc_estado getEstadoOC() {
        return EstadoOC;
    }

    public void setEstadoOC(clsBeTrans_oc_estado estadoOC) {
        this.EstadoOC = estadoOC;
    }

    public clsBeTrans_oc_ti getTipoIngreso() {
        return TipoIngreso;
    }

    public void setTipoIngreso(clsBeTrans_oc_ti tipoIngreso) {
        this.TipoIngreso = tipoIngreso;
    }
    public int getIdTipoIngresoOC() {
        return IdTipoIngresoOC;
    }

    public void setIdTipoIngresoOC(int idTipoIngresoOC) {
        this.IdTipoIngresoOC = idTipoIngresoOC;
    }
}
