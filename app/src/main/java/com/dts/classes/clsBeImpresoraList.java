package com.dts.classes;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeImpresoraList {
    @ElementList(inline=true,required=false)
    public List<clsBeImpresora> items;
}
