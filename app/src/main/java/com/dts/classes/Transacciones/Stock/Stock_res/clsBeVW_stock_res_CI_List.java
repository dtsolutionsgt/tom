package com.dts.classes.Transacciones.Stock.Stock_res;

import org.simpleframework.xml.ElementList;

import java.util.List;
import java.util.ListIterator;

public class clsBeVW_stock_res_CI_List {
    @ElementList(inline=true,required = false)
    public List<clsBeVW_stock_res_CI> items;
}
