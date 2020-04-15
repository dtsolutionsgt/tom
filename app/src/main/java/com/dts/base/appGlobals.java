package com.dts.base;

import android.app.Application;

import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador_bodega;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det.clsBeTrans_ubic_hh_det;
import com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_enc.clsBeTrans_ubic_hh_enc;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_det;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det.clsBeTrans_oc_detList;
import com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc.clsBeTrans_oc_enc;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;

import java.util.Date;
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
    public List<clsBeOperador_bodega> gOperadorBodega;
    public List<clsBeImpresora> gImpresora;


    public clsBeVW_stock_res BeStockPallet;
    public int gIdProductoBuenEstadoPorDefecto;

    //Variables para recepción
    public String tipoIngreso;
    public int IdPropietario;
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
    public String gFechaVenceAnterior="";
    public boolean Carga_Producto_x_Pallet=false;
    public clsBeTrans_re_detList gListTransRecDet = new clsBeTrans_re_detList();

    //Variables globales generales.
    public int IdBodega,IdOperador,IdEmpresa,IdImpresora;
    public String gCodigoBodega;
    public int tipoTarea;
    public clsBeOperador_bodega OperadorBodega = new clsBeOperador_bodega();
    public int gCantDecDespliegue=0;
    public int gCantDecCalculo=0;
    public String deviceId="";

    public int mode=0;
}
