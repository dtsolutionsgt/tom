package com.dts.base;

import android.app.Application;

import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodega;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;

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
    public int IdTareaUbicEnc,IdTareaUbicDet;
    public double gCantDisponible;
    public boolean Escaneo_Pallet;

    //variable y listas publicas para mainActivity
    public int IdEstadoProductoNE;
    public String CodigoBodega;
    public List<clsBeOperador_bodega> gOperadorBodega;
    public List<clsBeImpresora> gImpresora;


    //Clases para cambio de ubicación y estado.
   // public clsBetrans_ubic_hh_det tareadet;
   // public clsBetrans_ubic_hh_enc tareaenc;
    public clsBeVW_stock_res BeStockPallet;
    public int gIdProductoBuenEstadoPorDefecto;

    //Variables para recepción
    public String tipoIngreso;
    public int IdPropietario;
    public int gIdRecepcionEnc;
    public clsBeTrans_oc_detList gListDetalleOC = new clsBeTrans_oc_detList();
    public clsBeTrans_re_enc gBeRecepcion = new clsBeTrans_re_enc();
    public boolean gEscaneo_Pallet;
    public clsBeTrans_oc_det gselitem= new clsBeTrans_oc_det();
    public String CodigoRecepcion;
    public clsBeTrans_oc_detList gpListDetalleOC;
    public clsBeTrans_oc_enc gBeOrdenCompra;

    //Variables globales generales.
    public int IdBodega,IdOperador,IdEmpresa,IdImpresora;
    public int tipoTarea;


}
