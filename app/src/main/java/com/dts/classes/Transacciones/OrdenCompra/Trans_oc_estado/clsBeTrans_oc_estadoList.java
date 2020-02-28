package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_estado;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_estadoList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_estado> items;
}