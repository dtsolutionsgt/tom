package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage5;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class USUbicStrucStage5List {
    @ElementList(inline=true,required=false)
    public List<USUbicStrucStage5> items;
}
