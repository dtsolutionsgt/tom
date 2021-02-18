package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det;


import com.dts.classes.Mantenimientos.Arancel.clsBeArancel;
import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;

import org.simpleframework.xml.Element;

public class clsBeTrans_oc_det {

    @Element(required=false) public int IdOrdenCompraEnc;
    @Element(required=false) public int IdOrdenCompraDet;
    @Element(required=false) public int IdProductoBodega;
    @Element(required=false) public int IdArancel;
    @Element(required=false) public int IdPresentacion;
    @Element(required=false) public int IdUnidadMedidaBasica;
    @Element(required=false) public int IdMotivoDevolucion;
    @Element(required=false) public int No_Linea;
    @Element(required=false) public String Nombre_producto;
    @Element(required=false) public String Nombre_presentacion;
    @Element(required=false) public String Nombre_arancel;
    @Element(required=false) public double Porcentaje_arancel;
    @Element(required=false) public String Nombre_unidad_medida_basica;
    @Element(required=false) public double Cantidad;
    @Element(required=false) public double Peso;
    @Element(required=false) public double Peso_Recibido;
    @Element(required=false) public double Cantidad_recibida;
    @Element(required=false) public double Costo;
    @Element(required=false) public double Total_linea;
    @Element(required=false) public String User_agr;
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public String User_mod;
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:01";
    @Element(required=false) public boolean Activo;
    @Element(required=false) public String Atributo_variante_1;
    @Element(required=false) public String Codigo_Producto;
    @Element(required=false) public boolean IsNew;
    @Element(required=false) public boolean ExisteEnRecepcion;
    @Element(required=false) public double FactorPresentacion;
    @Element(required=false) public clsBeArancel Arancel=new clsBeArancel();
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();
    @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeUnidad_medida UnidadMedida=new clsBeUnidad_medida();
    @Element(required=false) public String Fecha_vence="1900-01-01T00:00:01";
    @Element(required=false) public int RowIndex;
    @Element(required=false) public double valor_aduana=0;
    @Element(required=false) public double valor_fob=0;
    @Element(required=false) public double valor_iva=0;
    @Element(required=false) public double valor_dai=0;
    @Element(required=false) public double valor_seguro=0;
    @Element(required=false) public double valor_flete=0;
    @Element(required=false) public double Peso_Neto=0;
    @Element(required=false) public double Peso_Bruto=0;

    public clsBeTrans_oc_det()
    {
    }

    public clsBeTrans_oc_det(int IdOrdenCompraEnc,int IdOrdenCompraDet,int IdProductoBodega,int IdArancel,
                             int IdPresentacion,int IdUnidadMedidaBasica,int IdMotivoDevolucion,int No_Linea,
                             String Nombre_producto,String Nombre_presentacion,String Nombre_arancel,double Porcentaje_arancel,
                             String Nombre_unidad_medida_basica,double Cantidad,double Peso,double Peso_Recibido,
                             double Cantidad_recibida,double Costo,double Total_linea,String User_agr,
                             String Fec_agr,String User_mod,String Fec_mod,boolean Activo,
                             String Atributo_variante_1,String Codigo_Producto,boolean IsNew,boolean ExisteEnRecepcion,
                             double FactorPresentacion,clsBeArancel Arancel,clsBeProducto Producto,clsBeProducto_Presentacion Presentacion,
                             clsBeUnidad_medida UnidadMedida,int RowIndex,double valor_aduana,double valor_fob,
                             double valor_iva,double valor_dai,double valor_seguro,double valor_flete,
                             double Peso_Neto, double Peso_Bruto) {

        this.IdOrdenCompraEnc=IdOrdenCompraEnc;
        this.IdOrdenCompraDet=IdOrdenCompraDet;
        this.IdProductoBodega=IdProductoBodega;
        this.IdArancel=IdArancel;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedidaBasica=IdUnidadMedidaBasica;
        this.IdMotivoDevolucion=IdMotivoDevolucion;
        this.No_Linea=No_Linea;
        this.Nombre_producto=Nombre_producto;
        this.Nombre_presentacion=Nombre_presentacion;
        this.Nombre_arancel=Nombre_arancel;
        this.Porcentaje_arancel=Porcentaje_arancel;
        this.Nombre_unidad_medida_basica=Nombre_unidad_medida_basica;
        this.Cantidad=Cantidad;
        this.Peso=Peso;
        this.Peso_Recibido=Peso_Recibido;
        this.Cantidad_recibida=Cantidad_recibida;
        this.Costo=Costo;
        this.Total_linea=Total_linea;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.Activo=Activo;
        this.Atributo_variante_1=Atributo_variante_1;
        this.Codigo_Producto=Codigo_Producto;
        this.IsNew=IsNew;
        this.ExisteEnRecepcion=ExisteEnRecepcion;
        this.FactorPresentacion=FactorPresentacion;
        this.Arancel=Arancel;
        this.Producto=Producto;
        this.Presentacion=Presentacion;
        this.UnidadMedida=UnidadMedida;
        this.RowIndex=RowIndex;
        this.valor_aduana=valor_aduana;
        this.valor_fob=valor_fob;
        this.valor_iva=valor_iva;
        this.valor_dai=valor_dai;
        this.valor_seguro=valor_seguro;
        this.valor_flete=valor_flete;
        this.Peso_Neto=Peso_Neto;
        this.Peso_Bruto=Peso_Bruto;
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
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public int getIdArancel() {
        return IdArancel;
    }
    public void setIdArancel(int value) {
        IdArancel=value;
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
    public int getIdMotivoDevolucion() {
        return IdMotivoDevolucion;
    }
    public void setIdMotivoDevolucion(int value) {
        IdMotivoDevolucion=value;
    }
    public int getNo_Linea() {
        return No_Linea;
    }
    public void setNo_Linea(int value) {
        No_Linea=value;
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
    public String getNombre_arancel() {
        return Nombre_arancel;
    }
    public void setNombre_arancel(String value) {
        Nombre_arancel=value;
    }
    public double getPorcentaje_arancel() {
        return Porcentaje_arancel;
    }
    public void setPorcentaje_arancel(double value) {
        Porcentaje_arancel=value;
    }
    public String getNombre_unidad_medida_basica() {
        return Nombre_unidad_medida_basica;
    }
    public void setNombre_unidad_medida_basica(String value) {
        Nombre_unidad_medida_basica=value;
    }
    public double getCantidad() {
        return Cantidad;
    }
    public void setCantidad(double value) {
        Cantidad=value;
    }
    public double getPeso() {
        return Peso;
    }
    public void setPeso(double value) {
        Peso=value;
    }
    public double getPeso_Recibido() {
        return Peso_Recibido;
    }
    public void setPeso_Recibido(double value) {
        Peso_Recibido=value;
    }
    public double getCantidad_recibida() {
        return Cantidad_recibida;
    }
    public void setCantidad_recibida(double value) {
        Cantidad_recibida=value;
    }
    public double getCosto() {
        return Costo;
    }
    public void setCosto(double value) {
        Costo=value;
    }
    public double getTotal_linea() {
        return Total_linea;
    }
    public void setTotal_linea(double value) {
        Total_linea=value;
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
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
    }
    public String getAtributo_variante_1() {
        return Atributo_variante_1;
    }
    public void setAtributo_variante_1(String value) {
        Atributo_variante_1=value;
    }
    public String getCodigo_Producto() {
        return Codigo_Producto;
    }
    public void setCodigo_Producto(String value) {
        Codigo_Producto=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
    }
    public boolean getExisteEnRecepcion() {
        return ExisteEnRecepcion;
    }
    public void setExisteEnRecepcion(boolean value) {
        ExisteEnRecepcion=value;
    }
    public double getFactorPresentacion() {
        return FactorPresentacion;
    }
    public void setFactorPresentacion(double value) {
        FactorPresentacion=value;
    }
    public clsBeArancel getArancel() {
        return Arancel;
    }
    public void setArancel(clsBeArancel value) {
        Arancel=value;
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
    public clsBeUnidad_medida getUnidadMedida() {
        return UnidadMedida;
    }
    public void setUnidadMedida(clsBeUnidad_medida value) {
        UnidadMedida=value;
    }
    public int getRowIndex() {
        return RowIndex;
    }
    public void setRowIndex(int value) {
        RowIndex=value;
    }
    public String getFecha_vence() {
        return Fecha_vence;
    }
    public void setFecha_vence(String value) {
        Fecha_vence=value;
    }
    public double getvalor_aduana() {
        return valor_aduana;
    }
    public void setvalor_aduana(double value) {
        valor_aduana=value;
    }
    public double getvalor_fob() {
        return valor_fob;
    }
    public void setvalor_fob(double value) {
        valor_fob=value;
    }
    public double getvalor_iva() {
        return valor_iva;
    }
    public void setvalor_iva(double value) {
        valor_iva=value;
    }
    public double getvalor_dai() {
        return valor_dai;
    }
    public void setvalor_dai(double value) {
        valor_dai=value;
    }
    public double getvalor_seguro() {
        return valor_seguro;
    }
    public void setvalor_seguro(double value) {
        valor_seguro=value;
    }
    public double getvalor_flete() {
        return valor_flete;
    }
    public void setvalor_flete(double value) {
        valor_flete=value;
    }
    public double getPeso_Neto() {
        return Peso_Neto;
    }
    public void setPeso_Neto(double value) {
        Peso_Neto=value;
    }
    public double getPeso_Bruto() {
        return Peso_Bruto;
    }
    public void setPeso_Bruto(double value) {
        Peso_Bruto=value;
    }
}

