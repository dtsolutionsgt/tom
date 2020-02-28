package com.dts.classes.Transacciones.Stock.Stock_res;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeVW_stock_resList {
    @ElementList(inline=true,required = false)
    public List<clsBeVW_stock_res> items;
}