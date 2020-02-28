package com.dts.classes.Mantenimientos.Bodega;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeBodega_muellesList {
    @ElementList(inline=true,required=false)
    public List<clsBeBodega_muelles> items;
}