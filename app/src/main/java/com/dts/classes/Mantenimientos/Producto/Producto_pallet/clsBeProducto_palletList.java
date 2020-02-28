package com.dts.classes.Mantenimientos.Producto.Producto_pallet;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProducto_palletList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_pallet> items;
}
