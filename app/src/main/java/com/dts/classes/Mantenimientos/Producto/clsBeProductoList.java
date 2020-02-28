package com.dts.classes.Mantenimientos.Producto;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProductoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto> items;
}
