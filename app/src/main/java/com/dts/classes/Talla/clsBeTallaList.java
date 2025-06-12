package com.dts.classes.Talla;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTallaList {
    @ElementList(inline=true,required=false)
    public List<clsBeTalla> items;
}
