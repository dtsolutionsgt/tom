package com.dts.classes.Transacciones.Stock.Stock_se_rec;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeStock_se_recList {
    @ElementList(inline=true)
    public List<clsBeStock_se_rec> items;
}