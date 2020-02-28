package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_detList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_det> items;
}