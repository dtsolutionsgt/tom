package com.dts.classes.Mantenimientos.Producto.Producto_tipo;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_tipoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_tipo> items;
}