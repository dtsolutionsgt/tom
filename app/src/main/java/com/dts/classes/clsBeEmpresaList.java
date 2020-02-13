package com.dts.classes;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeEmpresaList {
    @ElementList(inline=true)
    public List<clsBeEmpresa> items;
}
