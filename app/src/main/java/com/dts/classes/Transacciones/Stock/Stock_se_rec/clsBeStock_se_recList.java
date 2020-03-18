package com.dts.classes.Transacciones.Stock.Stock_se_rec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeStock_se_recList {
    @ElementList(inline=true)
    public List<clsBeStock_se_rec> items;
}