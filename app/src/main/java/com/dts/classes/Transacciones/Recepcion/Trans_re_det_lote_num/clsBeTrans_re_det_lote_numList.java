package com.dts.classes.Transacciones.Recepcion.Trans_re_det_lote_num;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTrans_re_det_lote_numList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_det_lote_num> items;
}