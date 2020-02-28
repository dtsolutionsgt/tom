package com.dts.classes.Transacciones.Recepcion.Trans_re_det_lote_num;


import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_re_det_lote_numList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_det_lote_num> items;
}