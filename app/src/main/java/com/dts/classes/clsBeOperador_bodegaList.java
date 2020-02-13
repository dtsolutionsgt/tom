package com.dts.classes;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeOperador_bodegaList {
    @ElementList(inline=true,required=false)
    public List<clsBeOperador_bodega> items;
}
