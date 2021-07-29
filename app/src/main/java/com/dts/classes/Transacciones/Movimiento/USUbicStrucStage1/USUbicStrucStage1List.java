package com.dts.classes.Transacciones.Movimiento.USUbicStrucStage1;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class USUbicStrucStage1List {
    @ElementList(inline=true,required=false)
    public List<USUbicStrucStage1> items;
}

