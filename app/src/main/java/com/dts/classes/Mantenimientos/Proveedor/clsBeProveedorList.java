package com.dts.classes.Mantenimientos.Proveedor;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeProveedorList {
    @ElementList(inline=true,required = false)
    public List<clsBeProveedor> items;
}