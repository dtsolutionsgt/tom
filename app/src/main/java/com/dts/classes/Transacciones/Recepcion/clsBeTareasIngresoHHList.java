package com.dts.classes.Transacciones.Recepcion;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTareasIngresoHHList {
    @ElementList(inline=true,required=false)
    public List<clsBeTareasIngresoHH> items;
}

