package com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_enc;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_pe_encList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_pe_enc> items;
}

