package com.dts.classes.Mantenimientos.Impresora;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeImpresoraList {
    @ElementList(inline=true,required=false)
    public List<clsBeImpresora> items;
}
