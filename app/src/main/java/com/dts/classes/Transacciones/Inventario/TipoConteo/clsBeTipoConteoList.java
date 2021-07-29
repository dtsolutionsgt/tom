package com.dts.classes.Transacciones.Inventario.TipoConteo;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTipoConteoList {
    @ElementList(inline=true,required=false)
    public List<clsBeTipoConteo> items;
}