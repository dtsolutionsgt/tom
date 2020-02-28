package com.dts.classes.Transacciones.Recepcion.Trans_re_img;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_imgList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_img> items;
}