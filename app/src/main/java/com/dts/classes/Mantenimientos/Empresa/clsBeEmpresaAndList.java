package com.dts.classes.Mantenimientos.Empresa;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeEmpresaAndList {
    @ElementList(inline=true,required = false)
    public List<clsBeEmpresaAnd> items;
}
