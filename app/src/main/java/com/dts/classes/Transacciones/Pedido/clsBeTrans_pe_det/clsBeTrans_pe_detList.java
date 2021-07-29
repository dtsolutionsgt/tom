package com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_det;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_pe_detList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_pe_det> items;
}
