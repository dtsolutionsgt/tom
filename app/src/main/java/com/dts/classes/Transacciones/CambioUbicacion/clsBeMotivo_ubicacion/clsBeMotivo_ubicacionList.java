package com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion;

import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeMotivo_ubicacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeMotivo_ubicacion> items;
}


//--------------------------------------------------------
