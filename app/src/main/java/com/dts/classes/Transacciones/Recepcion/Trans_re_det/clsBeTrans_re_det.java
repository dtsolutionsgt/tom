package com.dts.classes.Transacciones.Recepcion.Trans_re_det;


import com.dts.classes.Mantenimientos.Motivo_devolucion.clsBeMotivo_devolucion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_estado.clsBeProducto_estado;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;

import org.simpleframework.xml.Element;

public class clsBeTrans_re_det {

    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUnidadMedida=0;
    @Element(required=false) public int IdProductoEstado=0;
    @Element(required=false) public int IdMotivoDevolucion=0;
    @Element(required=false) public int IdRecepcionDet=0;
    @Element(required=false) public int IdRecepcionEnc=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public int IdOperadorBodega=0;
    @Element(required=false) public int No_Linea=0;
    @Element(required=false) public double cantidad_recibida=0;
    @Element(required=false) public String Nombre_producto="";
    @Element(required=false) public String Nombre_presentacion="";
    @Element(required=false) public String Nombre_unidad_medida="";
    @Element(required=false) public String Nombre_producto_estado="";
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Fecha_vence ="1900-01-01T00:00:01";
    @Element(required=false) public String Fecha_ingreso="1900-01-01T00:00:01";
    @Element(required=false) public double Peso=0;
    @Element(required=false) public double Peso_Estadistico=0;
    @Element(required=false) public double Peso_Minimo=0;
    @Element(required=false) public double Peso_Maximo=0;
    @Element(required=false) public double peso_unitario=0;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String Observacion="";
    @Element(required=false) public int Aniada=0;
    @Element(required=false) public double Costo=0;
    @Element(required=false) public double Costo_Oc=0;
    @Element(required=false) public double Costo_Estadistico=0;
    @Element(required=false) public String Atributo_Variante_1="";
    @Element(required=false) public String Codigo_Producto="";
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();
    @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeProducto_estado ProductoEstado=new clsBeProducto_estado();
    @Element(required=false) public clsBeUnidad_medida UnidadMedida=new clsBeUnidad_medida();
    @Element(required=false) public clsBeMotivo_devolucion MotivoDevolucion=new clsBeMotivo_devolucion();
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public boolean Control_Peso=false;
    @Element(required=false) public int IdPropietarioBodega=0;
    @Element(required=false) public int IdUbicacion=0;
    @Element(required=false) public int IdUbicacionAnterior=0;
    @Element(required=false) public int IdOrdenCompraEnc=0;
    @Element(required=false) public String Fecha_Rec="1900-01-01T00:00:01";
    @Element(required=false) public String Fecha_tarea="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_ini="1900-01-01T00:00:01";
    @Element(required=false) public String Hora_Fin="1900-01-01T00:00:01";
    @Element(required=false) public String Estado_Rec="";
    @Element(required=false) public String UbicacionCompleta="";
    @Element(required=false) public String Lic_plate="";
    @Element(required=false) public double Uds_lic_plate=0;
    @Element(required=false) public boolean pallet_no_estandar= false;

    public clsBeTrans_re_det() {
    }

    public clsBeTrans_re_det(int IdPresentacion,int IdUnidadMedida,int IdProductoEstado,int IdMotivoDevolucion,
                             int IdRecepcionDet,int IdRecepcionEnc,int IdProductoBodega,int IdOperadorBodega,
                             int No_Linea,double cantidad_recibida,String Nombre_producto,String Nombre_presentacion,
                             String Nombre_unidad_medida,String Nombre_producto_estado,String Lote,String Fecha_vence,
                             String Fecha_ingreso,double Peso,double Peso_Estadistico,double Peso_Minimo,
                             double Peso_Maximo,double peso_unitario,String User_agr,String Fec_agr,
                             String Observacion,int Aniada,double Costo,double Costo_Oc,
                             double Costo_Estadistico,String Atributo_Variante_1,String Codigo_Producto,clsBeProducto Producto,
                             clsBeProducto_Presentacion Presentacion,clsBeProducto_estado ProductoEstado,clsBeUnidad_medida UnidadMedida,clsBeMotivo_devolucion MotivoDevolucion,
                             boolean IsNew,boolean Control_Peso,int IdPropietarioBodega,int IdUbicacion,
                             int IdUbicacionAnterior,int IdOrdenCompraEnc,String Fecha_Rec,String Fecha_tarea,
                             String Hora_ini,String Hora_Fin,String Estado_Rec,String UbicacionCompleta,
                             String Lic_plate,double Uds_lic_plate, boolean pallet_no_estandar) {

        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedida=IdUnidadMedida;
        this.IdProductoEstado=IdProductoEstado;
        this.IdMotivoDevolucion=IdMotivoDevolucion;
        this.IdRecepcionDet=IdRecepcionDet;
        this.IdRecepcionEnc=IdRecepcionEnc;
        this.IdProductoBodega=IdProductoBodega;
        this.IdOperadorBodega=IdOperadorBodega;
        this.No_Linea=No_Linea;
        this.cantidad_recibida=cantidad_recibida;
        this.Nombre_producto=Nombre_producto;
        this.Nombre_presentacion=Nombre_presentacion;
        this.Nombre_unidad_medida=Nombre_unidad_medida;
        this.Nombre_producto_estado=Nombre_producto_estado;
        this.Lote=Lote;
        this.Fecha_vence = Fecha_vence;
        this.Fecha_ingreso=Fecha_ingreso;
        this.Peso=Peso;
        this.Peso_Estadistico=Peso_Estadistico;
        this.Peso_Minimo=Peso_Minimo;
        this.Peso_Maximo=Peso_Maximo;
        this.peso_unitario=peso_unitario;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.Observacion=Observacion;
        this.Aniada=Aniada;
        this.Costo=Costo;
        this.Costo_Oc=Costo_Oc;
        this.Costo_Estadistico=Costo_Estadistico;
        this.Atributo_Variante_1=Atributo_Variante_1;
        this.Codigo_Producto=Codigo_Producto;
        this.Producto=Producto;
        this.Presentacion=Presentacion;
        this.ProductoEstado=ProductoEstado;
        this.UnidadMedida=UnidadMedida;
        this.MotivoDevolucion=MotivoDevolucion;
        this.IsNew=IsNew;
        this.Control_Peso=Control_Peso;
        this.IdPropietarioBodega=IdPropietarioBodega;
        this.IdUbicacion=IdUbicacion;
        this.IdUbicacionAnterior=IdUbicacionAnterior;
        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.Fecha_Rec=Fecha_Rec;
        this.Fecha_tarea=Fecha_tarea;
        this.Hora_ini=Hora_ini;
        this.Hora_Fin=Hora_Fin;
        this.Estado_Rec=Estado_Rec;
        this.UbicacionCompleta=UbicacionCompleta;
        this.Lic_plate=Lic_plate;
        this.Uds_lic_plate=Uds_lic_plate;
        this.pallet_no_estandar=pallet_no_estandar;

    }

    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }
    public int getIdUnidadMedida() {
        return IdUnidadMedida;
    }
    public void setIdUnidadMedida(int value) {
        IdUnidadMedida=value;
    }
    public int getIdProductoEstado() {
        return IdProductoEstado;
    }
    public void setIdProductoEstado(int value) {
        IdProductoEstado=value;
    }
    public int getIdMotivoDevolucion() {
        return IdMotivoDevolucion;
    }
    public void setIdMotivoDevolucion(int value) {
        IdMotivoDevolucion=value;
    }
    public int getIdRecepcionDet() {
        return IdRecepcionDet;
    }
    public void setIdRecepcionDet(int value) {
        IdRecepcionDet=value;
    }
    public int getIdRecepcionEnc() {
        return IdRecepcionEnc;
    }
    public void setIdRecepcionEnc(int value) {
        IdRecepcionEnc=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getIdOperadorBodega() {
        return IdOperadorBodega;
    }
    public void setIdOperadorBodega(int value) {
        IdOperadorBodega=value;
    }
    public int getNo_Linea() {
        return No_Linea;
    }
    public void setNo_Linea(int value) {
        No_Linea=value;
    }
    public double getcantidad_recibida() {
        return cantidad_recibida;
    }
    public void setcantidad_recibida(double value) {
        cantidad_recibida=value;
    }
    public String getNombre_producto() {
        return Nombre_producto;
    }
    public void setNombre_producto(String value) {
        Nombre_producto=value;
    }
    public String getNombre_presentacion() {
        return Nombre_presentacion;
    }
    public void setNombre_presentacion(String value) {
        Nombre_presentacion=value;
    }
    public String getNombre_unidad_medida() {
        return Nombre_unidad_medida;
    }
    public void setNombre_unidad_medida(String value) {
        Nombre_unidad_medida=value;
    }
    public String getNombre_producto_estado() {
        return Nombre_producto_estado;
    }
    public void setNombre_producto_estado(String value) {
        Nombre_producto_estado=value;
    }
    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }
    public String getFecha_vence() {
        return Fecha_vence;
    }
    public void setFecha_vence(String value) {
        Fecha_vence =value;
    }
    public String getFecha_ingreso() {
        return Fecha_ingreso;
    }
    public void setFecha_ingreso(String value) {
        Fecha_ingreso=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }
    public double getPeso_Estadistico() {
        return Peso_Estadistico;
    }
    public void setPeso_Estadistico(double value) {
        Peso_Estadistico=value;
    }
    public double getPeso_Minimo() {
        return Peso_Minimo;
    }
    public void setPeso_Minimo(double value) {
        Peso_Minimo=value;
    }
    public double getPeso_Maximo() {
        return Peso_Maximo;
    }
    public void setPeso_Maximo(double value) {
        Peso_Maximo=value;
    }
    public double getpeso_unitario() {
        return peso_unitario;
    }
    public void setpeso_unitario(double value) {
        peso_unitario=value;
    }
    public String getUser_agr() {
        return User_agr;
    }
    public void setUser_agr(String value) {
        User_agr=value;
    }
    public String getFec_agr() {
        return Fec_agr;
    }
    public void setFec_agr(String value) {
        Fec_agr=value;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String value) {
        Observacion=value;
    }
    public int getAniada() {
        return Aniada;
    }
    public void setAniada(int value) {
        Aniada=value;
    }
    public double getCosto() {
        return Costo;
    }
    public void setCosto(double value) {
        Costo=value;
    }
    public double getCosto_Oc() {
        return Costo_Oc;
    }
    public void setCosto_Oc(double value) {
        Costo_Oc=value;
    }
    public double getCosto_Estadistico() {
        return Costo_Estadistico;
    }
    public void setCosto_Estadistico(double value) {
        Costo_Estadistico=value;
    }
    public String getAtributo_Variante_1() {
        return Atributo_Variante_1;
    }
    public void setAtributo_Variante_1(String value) {
        Atributo_Variante_1=value;
    }
    public String getCodigo_Producto() {
        return Codigo_Producto;
    }
    public void setCodigo_Producto(String value) {
        Codigo_Producto=value;
    }
    public clsBeProducto getProducto() {
        return Producto;
    }
    public void setProducto(clsBeProducto value) {
        Producto=value;
    }
    public clsBeProducto_Presentacion getPresentacion() {
        return Presentacion;
    }
    public void setPresentacion(clsBeProducto_Presentacion value) {
        Presentacion=value;
    }
    public clsBeProducto_estado getProductoEstado() {
        return ProductoEstado;
    }
    public void setProductoEstado(clsBeProducto_estado value) {
        ProductoEstado=value;
    }
    public clsBeUnidad_medida getUnidadMedida() {
        return UnidadMedida;
    }
    public void setUnidadMedida(clsBeUnidad_medida value) {
        UnidadMedida=value;
    }
    public clsBeMotivo_devolucion getMotivoDevolucion() {
        return MotivoDevolucion;
    }
    public void setMotivoDevolucion(clsBeMotivo_devolucion value) {
        MotivoDevolucion=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public boolean getControl_Peso() {
        return Control_Peso;
    }
    public void setControl_Peso(boolean value) {
        Control_Peso=value;
    }
    public int getIdPropietarioBodega() {
        return IdPropietarioBodega;
    }
    public void setIdPropietarioBodega(int value) {
        IdPropietarioBodega=value;
    }
    public int getIdUbicacion() {
        return IdUbicacion;
    }
    public void setIdUbicacion(int value) {
        IdUbicacion=value;
    }
    public int getIdUbicacionAnterior() {
        return IdUbicacionAnterior;
    }
    public void setIdUbicacionAnterior(int value) {
        IdUbicacionAnterior=value;
    }
    public int getIdOrdenCompraEnc() {
        return IdOrdenCompraEnc;
    }
    public void setIdOrdenCompraEnc(int value) {
        IdOrdenCompraEnc=value;
    }
    public String getFecha_Rec() {
        return Fecha_Rec;
    }
    public void setFecha_Rec(String value) {
        Fecha_Rec=value;
    }
    public String getFecha_tarea() {
        return Fecha_tarea;
    }
    public void setFecha_tarea(String value) {
        Fecha_tarea=value;
    }
    public String getHora_ini() {
        return Hora_ini;
    }
    public void setHora_ini(String value) {
        Hora_ini=value;
    }
    public String getHora_Fin() {
        return Hora_Fin;
    }
    public void setHora_Fin(String value) {
        Hora_Fin=value;
    }
    public String getEstado_Rec() {
        return Estado_Rec;
    }
    public void setEstado_Rec(String value) {
        Estado_Rec=value;
    }
    public String getUbicacionCompleta() {
        return UbicacionCompleta;
    }
    public void setUbicacionCompleta(String value) {
        UbicacionCompleta=value;
    }
    public String getLic_plate() {
        return Lic_plate;
    }
    public void setLic_plate(String value) {
        Lic_plate=value;
    }
    public double getUds_lic_plate() {
        return Uds_lic_plate;
    }
    public void setUds_lic_plate(double value) {
        Uds_lic_plate=value;
    }
    public boolean getpallet_no_estandar() {
        return pallet_no_estandar;
    }
    public void setPallet_no_estandar(boolean value) {
        pallet_no_estandar=value;
    }
}

