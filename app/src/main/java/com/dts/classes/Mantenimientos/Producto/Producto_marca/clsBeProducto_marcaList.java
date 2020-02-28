package com.dts.classes.Mantenimientos.Producto.Producto_marca;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_marcaList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_marca> items;
}

