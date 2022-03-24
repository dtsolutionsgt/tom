package com.dts.classes.Mantenimientos.Producto.Producto_imagen;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_imagenList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_imagen> items;
}
