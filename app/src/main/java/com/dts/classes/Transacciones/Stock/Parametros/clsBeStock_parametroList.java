package com.dts.classes.Transacciones.Stock.Parametros;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeStock_parametroList {
    @ElementList(inline=true, required = false)
    public List<clsBeStock_parametro> items;
}
