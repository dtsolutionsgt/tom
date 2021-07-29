package com.dts.classes.Mantenimientos.Configuracion_barra_pallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeConfiguracion_barra_palletList {
    @ElementList(inline=true)
    public List<clsBeConfiguracion_barra_pallet> items;
}