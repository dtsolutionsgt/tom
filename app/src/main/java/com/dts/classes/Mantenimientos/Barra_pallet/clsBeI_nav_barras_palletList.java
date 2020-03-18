package com.dts.classes.Mantenimientos.Barra_pallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeI_nav_barras_palletList {
    @ElementList(inline=true,required = false)
    public List<clsBeI_nav_barras_pallet> items;
}