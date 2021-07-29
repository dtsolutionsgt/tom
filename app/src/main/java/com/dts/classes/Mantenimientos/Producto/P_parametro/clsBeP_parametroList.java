package com.dts.classes.Mantenimientos.Producto.P_parametro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeP_parametroList {
    @ElementList(inline=true,required = false)
    public List<clsBeP_parametro> items;
}