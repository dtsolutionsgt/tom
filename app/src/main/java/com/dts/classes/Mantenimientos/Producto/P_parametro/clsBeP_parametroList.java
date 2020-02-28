package com.dts.classes.Mantenimientos.Producto.P_parametro;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeP_parametroList {
    @ElementList(inline=true,required = false)
    public List<clsBeP_parametro> items;
}