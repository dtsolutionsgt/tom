package com.dts.classes.Transacciones.Stock.Stock_res;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeStock_resList {
    @ElementList(inline=true,required=false)
    public List<clsBeStock_res> items;
}
