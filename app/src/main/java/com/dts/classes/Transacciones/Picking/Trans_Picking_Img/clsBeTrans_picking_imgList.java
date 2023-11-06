package com.dts.classes.Transacciones.Picking.Trans_Picking_Img;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_picking_imgList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_picking_img> items;
}

