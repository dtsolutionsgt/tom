package com.dts.classes.Transacciones.Movimiento.USUbicSingle;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class USUbicSingleList {
    @ElementList(inline=true,required=false)
    public List<USUbicSingle> items;
}