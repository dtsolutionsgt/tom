package com.dts.classes.Transacciones.Recepcion.Trans_re_oc;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_ocList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_oc> items;
}
