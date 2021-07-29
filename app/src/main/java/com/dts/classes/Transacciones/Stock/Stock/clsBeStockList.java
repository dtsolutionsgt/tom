package com.dts.classes.Transacciones.Stock.Stock;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeStockList {
    @ElementList(inline=true, required = false)
    public List<clsBeStock> items;
}

