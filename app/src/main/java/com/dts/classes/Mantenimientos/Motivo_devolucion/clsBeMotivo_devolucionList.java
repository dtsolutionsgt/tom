package com.dts.classes.Mantenimientos.Motivo_devolucion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeMotivo_devolucionList {
    @ElementList(inline=true,required = false)
    public List<clsBeMotivo_devolucion> items;
}
