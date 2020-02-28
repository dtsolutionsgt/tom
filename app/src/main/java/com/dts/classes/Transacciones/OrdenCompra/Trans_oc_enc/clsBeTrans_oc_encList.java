package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_enc;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_encList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_enc> items;
}
