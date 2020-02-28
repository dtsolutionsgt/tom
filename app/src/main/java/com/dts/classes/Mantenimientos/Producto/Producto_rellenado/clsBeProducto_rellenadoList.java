package com.dts.classes.Mantenimientos.Producto.Producto_rellenado;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_rellenadoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_rellenado> items;
}