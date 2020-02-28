package com.dts.classes.Mantenimientos.Producto.Producto_estado_ubic;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_estado_ubicList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_estado_ubic> items;
}
