package com.dts.classes.Mantenimientos.Bodega;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeBodegaList {
    @ElementList(inline=true,required=false)
    public List<clsBeBodega> items;
}
