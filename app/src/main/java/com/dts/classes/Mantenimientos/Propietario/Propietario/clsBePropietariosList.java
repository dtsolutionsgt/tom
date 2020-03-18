package com.dts.classes.Mantenimientos.Propietario.Propietario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBePropietariosList {
    @ElementList(inline=true,required = false)
    public List<clsBePropietarios> items ;
}
