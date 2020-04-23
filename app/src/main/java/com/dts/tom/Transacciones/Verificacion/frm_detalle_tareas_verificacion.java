package com.dts.tom.Transacciones.Verificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estadoList;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Transacciones.Pedido.clsBeDetallePedidoAVerificar.clsBeDetallePedidoAVerificarList;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.tom.R;

public class frm_detalle_tareas_verificacion extends AppCompatActivity {

    private int indiceDetallePedido = -1;

    private clsBeTrans_picking_ubicList plistPickingUbiList = new clsBeTrans_picking_ubicList();
    private clsBeProducto gBeProducto = new clsBeProducto();
    private clsBeDetallePedidoAVerificarList pListaPedidoDet = new clsBeDetallePedidoAVerificarList();

    private clsBeProducto_estadoList lProductoEstadoDañado = new clsBeProducto_estadoList();

    private double cantReemplazar = 0;

    //private DT_Completo As New DataTable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_detalle_tareas_verificacion);
    }
}
