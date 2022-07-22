package com.dts.classes.Mantenimientos.Producto.Producto_parametro_a;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_parametro_aList {
    @ElementList(inline=true,required=false)
    public List<clsBeProducto_parametro_a> items;
}
