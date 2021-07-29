package com.dts.classes.Mantenimientos.Resolucion_LP;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeResolucion_lp_operadorList {
    @ElementList(inline=true,required = false)
    public List<clsBeResolucion_lp_operador> items;
}