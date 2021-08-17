package com.dts.classes.Transacciones.Packing;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_packing_encList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_packing_enc> items;
}