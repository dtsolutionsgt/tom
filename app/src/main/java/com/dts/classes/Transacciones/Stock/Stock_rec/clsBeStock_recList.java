package com.dts.classes.Transacciones.Stock.Stock_rec;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeStock_recList {
    @ElementList(inline=true)
    public List<clsBeStock_rec> items;
}