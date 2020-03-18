package com.dts.classes.Mantenimientos.Producto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeProductoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto> items;
}
