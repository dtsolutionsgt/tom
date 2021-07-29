package com.dts.classes.Transacciones.Recepcion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTareasIngresoHHList {
    @ElementList(inline=true,required=false)
    public ArrayList<clsBeTareasIngresoHH> items;
}

