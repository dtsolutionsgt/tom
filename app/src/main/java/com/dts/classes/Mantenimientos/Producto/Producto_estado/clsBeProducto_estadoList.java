package com.dts.classes.Mantenimientos.Producto.Producto_estado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeProducto_estadoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_estado> items;
}