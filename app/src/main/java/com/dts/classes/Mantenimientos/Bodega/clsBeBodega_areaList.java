package com.dts.classes.Mantenimientos.Bodega;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeBodega_areaList {
    @ElementList(inline=true,required=false)
    public List<clsBeBodega_area> items;
}