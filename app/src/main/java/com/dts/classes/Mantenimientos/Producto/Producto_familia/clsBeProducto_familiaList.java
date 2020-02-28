package com.dts.classes.Mantenimientos.Producto.Producto_familia;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_familiaList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_familia> items;
}
