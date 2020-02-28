package com.dts.classes.Mantenimientos.Proveedor.Proveedor_bodega;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProveedor_bodegaList {
    @ElementList(inline=true,required = false)
    public List<clsBeProveedor_bodega> items;
}