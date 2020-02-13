package com.dts.classes;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeEmpresaAndList {
    @ElementList(inline=true)
    public List<clsBeEmpresaAnd> items;
}
