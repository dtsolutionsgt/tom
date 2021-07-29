package com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class clsBeDetallePedidoAVerificarList {
    @ElementList(inline=true,required=false)
    public List<clsBeDetallePedidoAVerificar> items;
}

