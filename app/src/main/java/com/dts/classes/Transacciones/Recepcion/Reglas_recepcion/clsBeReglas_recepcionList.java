package com.dts.classes.Transacciones.Recepcion.Reglas_recepcion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeReglas_recepcionList {
    @ElementList(inline=true,required = false)
    public List<clsBeReglas_recepcion> items;
}