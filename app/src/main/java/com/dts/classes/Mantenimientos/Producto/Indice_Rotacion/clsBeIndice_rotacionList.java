package com.dts.classes.Mantenimientos.Producto.Indice_Rotacion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeIndice_rotacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeIndice_rotacion> items;
}
