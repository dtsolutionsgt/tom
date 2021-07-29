package com.dts.classes.Mantenimientos.Menu_rol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeMenu_rol_opList {
    @ElementList(inline=true,required=false)
    public List<clsBeMenu_rol_op> items;
}