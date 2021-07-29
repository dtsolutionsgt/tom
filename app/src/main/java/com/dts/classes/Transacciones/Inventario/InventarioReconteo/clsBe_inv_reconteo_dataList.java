package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.ElementList;
import java.util.List;

public class clsBe_inv_reconteo_dataList {
    @ElementList(inline=true,required=false)
    public List<clsBe_inv_reconteo_data> items;
}
