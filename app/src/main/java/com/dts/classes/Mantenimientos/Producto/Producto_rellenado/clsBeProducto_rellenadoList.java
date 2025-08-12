package com.dts.classes.Mantenimientos.Producto.Producto_rellenado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeProducto_rellenadoList {

    @ElementList(inline = true, required = false)
    @SerializedName("RellenadoPorUbicacionDePicking")
    public List<clsBeProducto_rellenado> items;

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public int size() {
        return (items != null) ? items.size() : 0;
    }

    public clsBeProducto_rellenado get(int index) {
        return (items != null && index < items.size()) ? items.get(index) : null;
    }

    public void add(clsBeProducto_rellenado item) {
        if (items != null) {
            items.add(item);
        }
    }

    public List<clsBeProducto_rellenado> getItems() {
        return items;
    }

    public void setItems(List<clsBeProducto_rellenado> items) {
        this.items = items;
    }

    public void syncRellenadoToXML() {
        // placeholder si necesitas convertir entre estructuras XML/JSON
    }
}