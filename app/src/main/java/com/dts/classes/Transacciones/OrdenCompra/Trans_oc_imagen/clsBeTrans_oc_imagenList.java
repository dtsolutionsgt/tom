package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_imagen;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_imagenList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_imagen> items;
}