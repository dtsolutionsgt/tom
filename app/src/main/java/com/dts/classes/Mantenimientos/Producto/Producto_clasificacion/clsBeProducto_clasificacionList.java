package com.dts.classes.Mantenimientos.Producto.Producto_clasificacion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_clasificacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_clasificacion> items;
}