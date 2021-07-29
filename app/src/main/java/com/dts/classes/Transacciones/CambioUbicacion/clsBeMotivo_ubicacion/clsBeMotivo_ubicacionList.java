package com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacion;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeMotivo_ubicacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeMotivo_ubicacion> items;
}


//--------------------------------------------------------
