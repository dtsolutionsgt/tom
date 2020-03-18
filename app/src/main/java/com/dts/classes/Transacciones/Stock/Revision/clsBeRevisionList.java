package com.dts.classes.Transacciones.Stock.Revision;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeRevisionList {
    @ElementList(inline=true, required = false)
    public List<clsBeRevision> items;
}
