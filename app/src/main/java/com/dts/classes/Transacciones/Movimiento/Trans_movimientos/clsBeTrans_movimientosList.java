package com.dts.classes.Transacciones.Movimiento.Trans_movimientos;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_movimientosList {
    @ElementList(inline=true, required = false)
    public List<clsBeTrans_movimientos> items;
}
