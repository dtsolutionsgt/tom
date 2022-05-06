package com.dts.classes.Transacciones.Inventario.Productos_Sugeridos.Inv_Stock_Prod;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_stock_prod_sugList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_stock_prod_sug> items;
}
