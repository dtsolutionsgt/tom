package com.dts.classes.Mantenimientos.Producto.Producto_kit;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_kit_composicionList {

    @ElementList(inline=true,required = false)
    public List<clsBeProducto_kit_composicion> items;

}