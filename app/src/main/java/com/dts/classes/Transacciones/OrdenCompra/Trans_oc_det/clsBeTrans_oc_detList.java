package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det;

import com.google.gson.annotations.SerializedName;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeTrans_oc_detList {

    // Esta anotación permite compatibilidad con SimpleXML
    @ElementList(inline = true, required = false)
    // Esta anotación permite compatibilidad con GSON
    @SerializedName("DetalleOC")
    public List<clsBeTrans_oc_det> items;

    // Constructor por defecto
    public clsBeTrans_oc_detList() {}

    // Utilitarios
    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public int size() {
        return (items != null) ? items.size() : 0;
    }

    public clsBeTrans_oc_det get(int index) {
        return (items != null && index < items.size()) ? items.get(index) : null;
    }

    public void add(clsBeTrans_oc_det item) {
        if (items != null) items.add(item);
    }

    public List<clsBeTrans_oc_det> getItems() {
        return items;
    }

    public void setItems(List<clsBeTrans_oc_det> items) {
        this.items = items;
    }

    /**
     * Sincroniza la estructura para SimpleXML (placeholder).
     * Este método se puede usar si necesitas convertir entre campos en una implementación mixta XML/JSON.
     */
    public void syncDetalleOCToXML() {
        // En esta versión, no hay estructura XML separada, por lo tanto no se necesita conversión.
        // Agrega aquí lógica adicional si decides mantener un campo separado para XML.
    }
}
