package com.dts.classes.Transacciones.TipoPedido;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_pe_tipoList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_pe_tipo> items;
}
