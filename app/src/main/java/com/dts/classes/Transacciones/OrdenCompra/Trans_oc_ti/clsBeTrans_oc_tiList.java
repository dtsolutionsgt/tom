package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_ti;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_tiList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_ti> items;
}