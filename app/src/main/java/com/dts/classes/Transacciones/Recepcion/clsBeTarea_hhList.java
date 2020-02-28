package com.dts.classes.Transacciones.Recepcion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTarea_hhList {
    @ElementList(inline=true,required = false)
    public List<clsBeTarea_hh> items;
}