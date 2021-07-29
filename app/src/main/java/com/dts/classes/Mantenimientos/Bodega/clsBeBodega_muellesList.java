package com.dts.classes.Mantenimientos.Bodega;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeBodega_muellesList {
    @ElementList(inline=true,required=false)
    public List<clsBeBodega_muelles> items;
}