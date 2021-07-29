package com.dts.classes.Transacciones.Stock.Stock_det;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeStock_detList {
    @ElementList(inline=true,required=false)
    public List<clsBeStock_det> items;
}
