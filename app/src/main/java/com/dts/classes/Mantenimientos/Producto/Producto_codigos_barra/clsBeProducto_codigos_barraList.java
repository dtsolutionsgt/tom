package com.dts.classes.Mantenimientos.Producto.Producto_codigos_barra;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_codigos_barraList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_codigos_barra> items;
}