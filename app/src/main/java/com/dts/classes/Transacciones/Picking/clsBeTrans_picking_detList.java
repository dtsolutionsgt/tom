package com.dts.classes.Transacciones.Picking;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_picking_detList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_picking_det> items;
}