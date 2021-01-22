package com.dts.classes.Transacciones.Pedido.Trasn_pe_pol;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_pe_polList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_pe_pol> items;
}
