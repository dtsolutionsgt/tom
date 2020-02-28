package com.dts.classes.Mantenimientos.Producto.Producto_estado;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_estadoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_estado> items;
}