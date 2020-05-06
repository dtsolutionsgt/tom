package com.dts.classes.Transacciones.Inventario;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_encList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_enc> items;
}