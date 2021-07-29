package com.dts.classes.Transacciones.Picking;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_picking_ubicList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_picking_ubic> items;
}
