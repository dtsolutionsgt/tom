package com.dts.classes.Transacciones.CambioUbicacion.clsBeTrans_ubic_hh_det;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTrans_ubic_hh_detList {
    @ElementList(inline=true, required = false)
    public List<clsBeTrans_ubic_hh_det> items;
}
