package com.dts.classes.Mantenimientos.Producto.Producto_presentacion_tarima;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_presentacion_tarimaList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_presentacion_tarima> items;
}