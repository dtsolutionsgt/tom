package com.dts.classes.Transacciones.Recepcion.Trans_re_fact;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_factList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_fact> items;
}