package com.dts.base;

import android.app.Application;

import com.dts.classes.clsBeOperador_bodega;
import com.dts.classes.clsBeVW_Stock_Res;
import com.dts.classes.clsBetrans_ubic_hh_det;
import com.dts.classes.clsBetrans_ubic_hh_enc;

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

    //Clases para cambio de ubicación y estado.
    public clsBetrans_ubic_hh_det tareadet;
    public clsBetrans_ubic_hh_enc tareaenc;
    public clsBeVW_Stock_Res BeStockPallet;

    //Variables globales generales.
    public int IdBodega,IdOperador,IdEmpresa,IdImpresora;


}
