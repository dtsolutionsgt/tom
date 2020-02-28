package com.dts.classes.Mantenimientos.Producto.Producto_Presentacion;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_PresentacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_Presentacion> items;
}
