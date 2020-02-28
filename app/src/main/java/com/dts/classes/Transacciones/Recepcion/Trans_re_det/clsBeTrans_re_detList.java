package com.dts.classes.Transacciones.Recepcion.Trans_re_det;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_detList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_det> items;
}