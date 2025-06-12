package com.dts.classes.Mantenimientos.Producto.Producto_talla_color;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_talla_colorList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_talla_color> items;
}
