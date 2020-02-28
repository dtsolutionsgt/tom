package com.dts.classes.Mantenimientos.Producto.Producto_parametros;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_parametrosList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_parametros> items;
}
