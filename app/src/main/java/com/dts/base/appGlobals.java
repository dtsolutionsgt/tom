package com.dts.base;

import android.app.Application;

import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodega;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_det;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_enc;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_data;
import com.dts.classes.Transacciones.Inventario.InventarioReconteo.clsBe_inv_reconteo_dataList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;

import java.util.ArrayList;
import java.util.List;

public class appGlobals extends Application {

    public String wsurl;
    public clsBeOperador_bodega seloper=new clsBeOperador_bodega();


    //Variable para mostrar cambio de estado o cambio de ubicación
    //1 cambio de ubicación
    //2 cambio de estado
    public int modo_cambio;

    //Variables para llenar el detalle del cambio de ubicación dirigida.
    public int IdOrigen;
    public int IdDestino;
    public int IdTareaUbicEnc;
    public double gCantDisponible;
    public boolean Escaneo_Pallet;

    //Clases para cambio de ubicación y estado.
    public clsBeTrans_ubic_hh_det tareadet = new clsBeTrans_ubic_hh_det();
    public clsBeTrans_ubic_hh_enc tareaenc;
    public int IdTareaUbicDet;

    //Variables de valores anteriores para cambio de ubicación y de estado
    public String gCProdAnterior  = "";
    public int gCEstadoAnterior = -1;
    public String gCNomEstadoAnterior = "";
    public String gCFechaAnterior = "01/01/1900";
    public String gCLoteAnterior = "";
    public int gCPresAnterior = -1;
    public String gCNomPresAnterior = "";
    public String gCUbicAnterior = "";

    //variable y listas publicas para mainActivity
    public int IdEstadoProductoNE;
    public String CodigoBodega;
    public String MacPrinter="";
    public List<clsBeOperador_bodega> gOperadorBodega;
    public List<clsBeImpresora> gImpresora;


    public clsBeVW_stock_res BeStockPallet;
    public int gIdProductoBuenEstadoPorDefecto;

    //Variables para recepción
    public String tipoIngreso;
    public int IdPropietario;
    public int IdPropietarioBodega;
    public int gIdRecepcionEnc;
    public int TipoOpcion;
    public clsBeTrans_oc_detList gListDetalleOC = new clsBeTrans_oc_detList();
    public clsBeTrans_re_enc gBeRecepcion = new clsBeTrans_re_enc();
    public boolean gEscaneo_Pallet;
    public clsBeTrans_oc_det gselitem= new clsBeTrans_oc_det();
    public String CodigoRecepcion;
    public clsBeTrans_oc_detList gpListDetalleOC;
    public clsBeTrans_oc_enc gBeOrdenCompra;
    public double  CantRec=0;
    public  double CantOC=0;
    public String gLoteAnterior="";
    public String gProductoAnterior = "";
    public String gFechaVenceAnterior="";
    public String gLP="";
    public boolean Carga_Producto_x_Pallet=false;
    public clsBeTrans_re_detList gListTransRecDet = new clsBeTrans_re_detList();
    public boolean gCapturaPalletNoEstandar = false;
    public boolean gCapturaEstibaIngreso = false;
    public boolean gVerifCascade = false;
    public int gVCascIdEnc;

    //Variables para picking
    public int gIdPickingEnc=0;

    //Variables para verificación
    public int pIdPedidoEnc;
    public int pIdPedidoDet;
    public int pIdPickingEnc;
    public clsBeDetallePedidoAVerificar gBePedidoDetVerif = new clsBeDetallePedidoAVerificar();
    public clsBeTrans_picking_ubicList gBePickingUbicList = new clsBeTrans_picking_ubicList();
    //gBePedidoEnc = new clsBeTrans_pe_enc;

    //Variables para packing
    public int modo_packing;
    public String paCodigo,paNombre;

    //variable para row seleccionado del inventario ciclico
    public clsBe_inv_reconteo_data inv_ciclico = new clsBe_inv_reconteo_data();
    public ArrayList<clsBe_inv_reconteo_data> reconteo_list = new ArrayList<clsBe_inv_reconteo_data>();
    public clsBe_inv_reconteo_dataList reconteo_ciclico = new clsBe_inv_reconteo_dataList();
    public int IndexCiclico;


    public clsBeProducto pprod = new clsBeProducto();
    public clsBeProducto_estadoList lista_estados = new clsBeProducto_estadoList();
    public String nuevo_producto_cic;
    public clsBeProducto pBeProductoNuevo;

    //variable para Existencias //
    public clsBeVW_stock_res_CI existencia = new clsBeVW_stock_res_CI();


    //variable para cerrar 2 activitys y regresar a la primera
    public boolean cerrarActividad2 = false;

    //Variables globales generales.
    public int IdBodega,IdOperador,IdEmpresa,IdImpresora;
    public String gCodigoBodega,gNomOperador,gNomEmpresa, gNomBodega;
    public int tipoTarea;
    public clsBeOperador_bodega OperadorBodega = new clsBeOperador_bodega();
    public int gCantDecDespliegue=0;
    public int gCantDecCalculo=0;
    public String deviceId="";
    public int mode=0;
    public boolean bloquear_lp_hh = false;
    public int IdResolucionLpOperador=0;
}
