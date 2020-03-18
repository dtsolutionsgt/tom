package com.dts.classes.Transacciones.Stock.Stock_res;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeVW_stock_resList {
    @ElementList(inline=true,required = false)
    public List<clsBeVW_stock_res> items;
}