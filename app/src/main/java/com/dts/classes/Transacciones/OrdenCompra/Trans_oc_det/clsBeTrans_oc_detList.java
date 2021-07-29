package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTrans_oc_detList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_det> items;
}