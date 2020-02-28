package com.dts.classes.Transacciones.Recepcion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_encList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_enc> items;
}