package com.dts.classes.Mantenimientos.Producto.Producto_parametro_b;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_parametro_bList {
    @ElementList(inline=true,required=false)
    public List<clsBeProducto_parametro_b> items;
}

