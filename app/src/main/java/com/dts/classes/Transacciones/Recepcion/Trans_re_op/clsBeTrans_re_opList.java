package com.dts.classes.Transacciones.Recepcion.Trans_re_op;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_opList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_op> items;
}