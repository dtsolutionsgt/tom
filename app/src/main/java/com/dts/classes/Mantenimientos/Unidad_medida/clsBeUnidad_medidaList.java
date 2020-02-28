package com.dts.classes.Mantenimientos.Unidad_medida;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeUnidad_medidaList {
    @ElementList(inline=true,required = false)
    public List<clsBeUnidad_medida> items;
}