package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_det_loteList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_det_lote> items;
}