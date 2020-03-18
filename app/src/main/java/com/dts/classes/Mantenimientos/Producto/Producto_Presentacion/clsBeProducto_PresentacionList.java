package com.dts.classes.Mantenimientos.Producto.Producto_Presentacion;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeProducto_PresentacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_Presentacion> items;
}
