package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_inv_enc_reconteoList {
    @ElementList(inline=true,required=false)
    public List<clsBeTrans_inv_enc_reconteo> items;
}
