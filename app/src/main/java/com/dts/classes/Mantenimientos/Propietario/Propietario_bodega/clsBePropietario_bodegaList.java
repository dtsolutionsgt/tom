package com.dts.classes.Mantenimientos.Propietario.Propietario_bodega;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBePropietario_bodegaList {
    @ElementList(inline=true,required = false)
    public List<clsBePropietario_bodega> items;
}