package com.dts.classes.Mantenimientos.Producto.Producto_bodega;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_bodegaList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_bodega> items;
}