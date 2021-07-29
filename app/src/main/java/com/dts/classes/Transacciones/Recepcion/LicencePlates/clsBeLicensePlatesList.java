package com.dts.classes.Transacciones.Recepcion.LicencePlates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeLicensePlatesList {
    @ElementList(inline=true,required = false)
    public List<clsBeLicensePlates> items;
}

