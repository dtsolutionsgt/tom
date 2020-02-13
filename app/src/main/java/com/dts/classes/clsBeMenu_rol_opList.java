package com.dts.classes;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeMenu_rol_opList {
    @ElementList(inline=true,required=false)
    public List<clsBeMenu_rol_op> items;
}