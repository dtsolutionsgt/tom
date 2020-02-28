package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_pol;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_polList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_pol> items;
}