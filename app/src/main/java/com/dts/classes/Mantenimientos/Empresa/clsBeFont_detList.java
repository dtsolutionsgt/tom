package com.dts.classes.Mantenimientos.Empresa;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeFont_detList {
    @ElementList(inline=true,required =  false)
    public List<clsBeFont_det> items;
}