package com.dts.classes.Mantenimientos.Producto.Producto_presentacion_tarima;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "MedidasPorTarima", strict = false)
public class clsBeProducto_presentacion_tarimaList {

    @ElementList(entry = "Item", inline = true, required = false)
    public List<clsBeProducto_presentacion_tarima> items = new ArrayList<>();

    public clsBeProducto_presentacion_tarimaList() {}
}
