package com.dts.classes.Mantenimientos.Motivo_devolucion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeMotivo_devolucionList {
    @ElementList(inline=true,required = false)
    public List<clsBeMotivo_devolucion> items;
}
