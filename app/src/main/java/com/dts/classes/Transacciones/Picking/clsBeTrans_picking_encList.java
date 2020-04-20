package com.dts.classes.Transacciones.Picking;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_picking_encList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_picking_enc> items;
}
