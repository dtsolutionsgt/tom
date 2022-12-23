package com.dts.base;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dts.classes.Mantenimientos.Impresora.clsBeImpresora;
import com.dts.classes.Mantenimientos.Operador.clsBeOperador;
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
import com.dts.classes.Transacciones.Packing.clsBeTrans_packing_lotes;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificar;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_enc;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Recepcion.Trans_re_det.clsBeTrans_re_detList;
import com.dts.classes.Transacciones.Recepcion.clsBeTareasIngresoHH;
import com.dts.classes.Transacciones.Recepcion.clsBeTrans_re_enc;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeVW_stock_res_CI;
import com.dts.classes.clsBeImagen;
import com.dts.tom.ForceUpdateChecker;
import com.dts.tom.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class appGlobals extends Application {

    public String wsurl = "";
    public clsBeOperador_bodega seloper = new clsBeOperador_bodega();
    public String PathDataDir = "";

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
    public String gCProdAnterior = "";
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
    public String MacPrinter = "";
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
    public clsBeTrans_oc_det gselitem = new clsBeTrans_oc_det();
    public String CodigoRecepcion;
    public clsBeTrans_oc_detList gpListDetalleOC;
    public clsBeTrans_oc_enc gBeOrdenCompra;
    public double CantRec = 0;
    public double CantOC = 0;
    public String gLoteAnterior = "";
    public String gProductoAnterior = "";
    public String gFechaVenceAnterior = "";
    public String gLP = "";
    public boolean Carga_Producto_x_Pallet = false;
    public clsBeTrans_re_detList gListTransRecDet = new clsBeTrans_re_detList();
    public boolean gCapturaPalletNoEstandar = false;
    public boolean gCapturaEstibaIngreso = false;
    public boolean gVerifCascade = false;
    public int gVCascIdEnc;
    public boolean gPriorizar_UbicRec_Sobre_UbicEst = false;
    public String gUbicMerma = "";
    public int gUbicProdNe;
    public int IdProductoEstadoNE;
    public boolean gSinPresentacion=false;
    public boolean PreguntarEnBackOrder=true;

    //Variables para picking
    public int gIdPickingEnc = 0;
    public String gReferencia = "";
    public boolean asignar_operador_linea_picking = false;

    //Variables para verificación
    public int pIdPedidoEnc;
    public int pIdPedidoDet;
    public int pIdPickingEnc;
    public clsBeDetallePedidoAVerificar gBePedidoDetVerif = new clsBeDetallePedidoAVerificar();
    public clsBeTrans_picking_ubicList gBePickingUbicList = new clsBeTrans_picking_ubicList();
    //gBePedidoEnc = new clsBeTrans_pe_enc;

    //Variables para packing
    public int modo_packing, paPickUbicId, paCant, paCamas, paLinea;
    public String paCodigo, paNombre, paBulto, filtroprod, paLote, paEstado;
    public ArrayList<clsBeTrans_packing_lotes> packlotes = new ArrayList<clsBeTrans_packing_lotes>();

    //variable para row seleccionado del inventario ciclico
    public clsBe_inv_reconteo_data inv_ciclico = new clsBe_inv_reconteo_data();
    public ArrayList<clsBe_inv_reconteo_data> reconteo_list = new ArrayList<clsBe_inv_reconteo_data>();
    public clsBe_inv_reconteo_dataList reconteo_ciclico = new clsBe_inv_reconteo_dataList();
    public int IndexCiclico;
    public boolean Es_Reconteo = false;


    public clsBeProducto pprod = new clsBeProducto();
    public clsBeProducto_estadoList lista_estados = new clsBeProducto_estadoList();
    public String nuevo_producto_cic;
    public clsBeProducto pBeProductoNuevo;

    //variable para Existencias //
    public clsBeVW_stock_res_CI existencia = new clsBeVW_stock_res_CI();


    //variable para cerrar 2 activitys y regresar a la primera
    public boolean cerrarActividad2 = false;

    //Variables globales generales.
    public int IdBodega, IdOperador, IdEmpresa, IdImpresora;
    public String gCodigoBodega, gNomOperador, gNomEmpresa, gNomBodega;
    public int tipoTarea;
    public clsBeOperador_bodega OperadorBodega = new clsBeOperador_bodega();
    public int gCantDecDespliegue = 0;
    public int gCantDecCalculo = 0;
    public String deviceId = "", devicename = "";
    public int mode = 0;
    public boolean bloquear_lp_hh = false;
    public int IdResolucionLpOperador = 0;

    //variable para diferenciar inv cealsa de cualquier otro
    public boolean multipropietario = false;

    //Variable para Operador con su rol y permisos
    public clsBeOperador beOperador = new clsBeOperador();

    //#EJC20220129: Validar si la ubicación destino tiene producto o está "libre" antes de colocar producto allí
    public boolean validar_disponibilidad_ubicaicon_destino = false;

    public boolean Mostrar_Area_En_HH=false;
    public boolean confirmar_codigo_en_picking=false;

    //#EJC20220314: CEALSA, si true, entonces en el cambio de ubicación, al escanear únicamente licencia, se coloca automáticamente la ubicación de origen.
    public boolean inferir_origen_en_cambio_ubic =false;

    //Imagen
    public int imagen;
    public ArrayList<clsBeImagen> ListImagen = new ArrayList<clsBeImagen>();

    //Recepción
    public int recepcionIdUbicacion = 0;

    //#EJC20220330_CEALSA: Si true, se envía en la HH el IdOperadorBodega para filtrar las tareas de verificación
    public boolean operador_picking_realiza_verificacion =false;

    //#CKFK20220610 Parametrizacion verificacion consolidada
    public boolean VerificacionConsolidada = false;

    //#EJC20220330_CEALSA: Si true, se permite realizar el cambio de ubicación de producto que está reservado en picking pero se actualiza el IdUbicacionTemporal.
    public boolean Permitir_Cambio_Ubic_Producto_Picking = false;

    public int sortOrd = 0;

    public boolean mostar_filtros = false;
    public String termino = "";

    private static final String TAG = MainActivity.class.getSimpleName();

    public final String version="4.7.0.1";
    public boolean VerificacionSinLoteFechaVen = false;

    //Voz Picking
    public boolean Notificacion_Voz = false;

    public boolean buscar_actualizacion_hh= false;

    public int TipoPantallaPicking = 0;
    public int TipoPantallaRecepcion = 0;
    public int TipoPantallaVerificacion = 0;

    public  boolean Permitir_Buen_Estado_En_Reemplazo =false;
    public  boolean Permitir_Decimales = false;
    public int Dias_Maximo_Vencimiento_Reemplazo = 0;
    public boolean Permitir_Repeticiones_En_Ingreso = false;
    public boolean TieneResoluciones = false;
    public boolean Calcular_Ubicacion_Sugerida_ML = false;

    @Override
    public void onCreate() {
        super.onCreate();

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set in-app defaults
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, true);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, version);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,"");

        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(1) // fetch every minutes
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "remote config is fetched.");
                        firebaseRemoteConfig.activate();
                    }
                });
        }

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }
}