package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det_lote;


import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_PresentacionList;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;

import org.simpleframework.xml.Element;

public class clsBeTrans_oc_det_lote {

    @Element(required=false) public int IdOrdenCompraEnc;
    @Element(required=false) public int IdOrdenCompraDet;
    @Element(required=false) public int IdOrdenCompraDetLote;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int No_linea;
    @Element(required=false) public String Codigo_producto;
    @Element(required=false) public double Cantidad;
    @Element(required=false) public double Cantidad_recibida;
    @Element(required=false) public String Lote;
    @Element(required=false) public String Fecha_vence ="1900-01-01T00:00:00";
    @Element(required=false) public String Lic_Plate;
    @Element(required=false) public String Ubicacion;
    @Element(required=false) public Boolean IsNew=false;
    //#CKFK20220131 Campos agregados para poder saber en que unidad de medida se hizo la OC
    @Element(required=false) public int IdPresentacion = 0;
    @Element(required=false) public int IdUnidadMedidaBasica = 0;
    //#CKFK20220131 Campos de bitácora agregados
    @Element(required=false) public String User_agr = "";
    @Element(required=false) public String Fec_agr ="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod = "";
    @Element(required=false) public String Fec_mod ="1900-01-01T00:00:00";
    @Element(required=false) public clsBeProducto_Presentacion Presentacion = new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeUnidad_medida UnidadMedida = new clsBeUnidad_medida();
    @Element(required=false) public Boolean Reclasificar=false;
    @Element(required=false) public Boolean Activo=false;
    @Element(required=false) public String No_Documento="";

    public clsBeTrans_oc_det_lote() {
    }

    public clsBeTrans_oc_det_lote(int IdOrdenCompraEnc,int IdOrdenCompraDet,int IdOrdenCompraDetLote,int IdProductoBodega,
                                  int No_linea,String Codigo_producto,double Cantidad,double Cantidad_recibida,
                                  String Lote,String Fecha_vence, String Lic_Plate, String Ubicacion,
                                  int IdPresentacion,int IdUnidadMedidaBasica,String User_agr,String Fec_agr,
                                  String User_mod, String Fec_mod, clsBeProducto_Presentacion Presentacion,
                                  clsBeUnidad_medida UnidadMedida,Boolean Reclasificar) {

        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.IdOrdenCompraDet=IdOrdenCompraDet;
        this.IdOrdenCompraDetLote=IdOrdenCompraDetLote;
        this.IdProductoBodega=IdProductoBodega;
        this.No_linea=No_linea;
        this.Codigo_producto=Codigo_producto;
        this.Cantidad=Cantidad;
        this.Cantidad_recibida=Cantidad_recibida;
        this.Lote=Lote;
        this.Fecha_vence =Fecha_vence;
        this.Lic_Plate = Lic_Plate;
        this.Ubicacion = Ubicacion;
        this.IdPresentacion = IdPresentacion;
        this.IdUnidadMedidaBasica = IdUnidadMedidaBasica;
        this.User_agr = User_agr;
        this.Fec_agr = Fec_agr;
        this.User_mod = User_mod;
        this.Fec_mod = Fec_mod;
        this.Presentacion = Presentacion;
        this.UnidadMedida = UnidadMedida;
        this.Reclasificar = Reclasificar;
    }

    public int getIdOrdenCompraEnc() {
        return IdOrdenCompraEnc;
    }
    public void setIdOrdenCompraEnc(int value) {
        IdOrdenCompraEnc=value;
    }
    public int getIdOrdenCompraDet() {
        return IdOrdenCompraDet;
    }
    public void setIdOrdenCompraDet(int value) {
        IdOrdenCompraDet=value;
    }
    public int getIdOrdenCompraDetLote() {
        return IdOrdenCompraDetLote;
    }
    public void setIdOrdenCompraDetLote(int value) {
        IdOrdenCompraDetLote=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getNo_linea() {
        return No_linea;
    }
    public void setNo_linea(int value) {
        No_linea=value;
    }
    public String getCodigo_producto() {
        return Codigo_producto;
    }
    public void setCodigo_producto(String value) {
        Codigo_producto=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public double getCantidad_recibida() {
        return Cantidad_recibida;
    }
    public void setCantidad_recibida(double value) {
        Cantidad_recibida=value;
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

    public String getLic_Plate() {
        return Lic_Plate;
    }
    public void setLic_Plate(String value) {
        Lic_Plate=value;
    }

    public String getUbicacion() {
        return Ubicacion;
    }
    public void setUbicacion(String value) {
        Ubicacion=value;
    }

    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }

    public int getIdUnidadMedidaBasica() {
        return IdUnidadMedidaBasica;
    }
    public void setIdUnidadMedidaBasica(int value) {
        IdUnidadMedidaBasica=value;
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

    public String getUser_mod() {
        return User_mod;
    }
    public void setUser_mod(String value) {
        User_mod=value;
    }

    public String getFec_mod() {
        return Fec_mod;
    }
    public void setFec_mod(String value) {
        Fec_mod=value;
    }

    public clsBeUnidad_medida getUnidadMedida() {
        return UnidadMedida;
    }
    public void setUnidadMedida(clsBeUnidad_medida value) {
        UnidadMedida=value;
    }

    public clsBeProducto_Presentacion getPresentacion() {
        return Presentacion;
    }
    public void setUbicacion(clsBeProducto_Presentacion value) {
        Presentacion=value;
    }

    public Boolean getIsNew(){
        return IsNew;
    }
    public void setIsNew(boolean value){
        IsNew=value;
    }

    public Boolean getReclasificar(){
        return Reclasificar;
    }
    public void setReclasificar(boolean value){Reclasificar=value;}

    public Boolean getActivo(){
        return Activo;
    }
    public void setActivo(boolean value){Activo=value;}

    public String getNo_Documento(){
        return No_Documento;
    }
    public void setNo_Documento(String value){No_Documento=value;}

}