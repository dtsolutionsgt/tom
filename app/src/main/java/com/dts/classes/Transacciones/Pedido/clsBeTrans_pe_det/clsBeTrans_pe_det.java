package com.dts.classes.Transacciones.Pedido.clsBeTrans_pe_det;

import com.dts.classes.Mantenimientos.Producto.Producto_Presentacion.clsBeProducto_Presentacion;
import com.dts.classes.Mantenimientos.Producto.Producto_bodega.clsBeProducto_bodega;
import com.dts.classes.Mantenimientos.Producto.clsBeProducto;
import com.dts.classes.Mantenimientos.Unidad_medida.clsBeUnidad_medida;
import com.dts.classes.Transacciones.Picking.clsBeTrans_picking_ubicList;
import com.dts.classes.Transacciones.Stock.Stock_res.clsBeStock_resList;

import org.simpleframework.xml.Element;

public class clsBeTrans_pe_det {

    @Element(required=false) public int IdPedidoDet=0;
    @Element(required=false) public int IdPedidoEnc=0;
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public clsBeProducto_bodega ProductoBodega=new clsBeProducto_bodega();
    @Element(required=false) public int IdEstado=0;
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public int IdUnidadMedidaBasica=0;
    @Element(required=false) public double Cantidad=0;
    @Element(required=false) public double Peso=0;
    @Element(required=false) public double Precio=0;
    @Element(required=false) public int No_recepcion=0;
    @Element(required=false) public int Ndias=0;
    @Element(required=false) public double Cant_despachada=0;
    @Element(required=false) public double Peso_despachado=0;
    @Element(required=false) public String Nombre_producto="";
    @Element(required=false) public String Nom_presentacion="";
    @Element(required=false) public String Nom_unid_med="";
    @Element(required=false) public String Nom_estado="";
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:01";
    @Element(required=false) public boolean Fecha_especifica=false;
    @Element(required=false) public double RoadDes=0;
    @Element(required=false) public double RoadDesMon=0;
    @Element(required=false) public double RoadTotal=0;
    @Element(required=false) public double RoadPrecioDoc=0;
    @Element(required=false) public double RoadVAL1=0;
    @Element(required=false) public String RoadVAL2="";
    @Element(required=false) public double RoadCantProc=0;
    @Element(required=false) public int No_linea=0;
    @Element(required=false) public String Atributo_Variante_1="";
    @Element(required=false) public int IdStockEspecifico=0;
    @Element(required=false) public boolean EsPadre=false;
    @Element(required=false) public int IdPedidoDetPadre=0;
    @Element(required=false) public boolean IsNew=false;
    @Element(required=false) public clsBeProducto Producto=new clsBeProducto();
    @Element(required=false) public clsBeProducto_Presentacion Presentacion=new clsBeProducto_Presentacion();
    @Element(required=false) public clsBeUnidad_medida UnidadMedida=new clsBeUnidad_medida();
    @Element(required=false) public clsBeStock_resList ListaStockRes=new clsBeStock_resList();
    @Element(required=false) public clsBeTrans_picking_ubicList ListaPickingUbic=new clsBeTrans_picking_ubicList();
    @Element(required=false) public String Codigo_Producto="";
    @Element(required=false) public String NombreProducto="";
    @Element(required=false) public String ProductoPresentacion="";
    @Element(required=false) public String ProductoUnidadMedida="";
    @Element(required=false) public String ProductoEstado="";
    @Element(required=false) public String BodegaUbicacion="";
    @Element(required=false) public double CantidadFisica=0;
    @Element(required=false) public double Factor=0;
    @Element(required=false) public double CantidadReservada=0;
    @Element(required=false) public double PesoReservado=0;
    @Element(required=false) public String FechaIngreso="";
    @Element(required=false) public String FechaVence="";
    @Element(required=false) public double Peso_Bruto=0;
    @Element(required=false) public double Peso_Neto=0;
    @Element(required=false) public double Costo=0;

    @Element(required=false) public double valor_aduana = 0;
    @Element(required=false) public double valor_fob = 0;
    @Element(required=false) public double valor_iva = 0;
    @Element(required=false) public double valor_dai = 0;
    @Element(required=false) public double valor_seguro = 0;
    @Element(required=false) public double valor_flete = 0;
    @Element(required=false) public double Total_linea = 0;

    @Element(required=false) public int IdCliente=0;


    public clsBeTrans_pe_det() {
    }

    public clsBeTrans_pe_det(int IdPedidoDet, int IdPedidoEnc, int IdProductoBodega, clsBeProducto_bodega ProductoBodega,
                             int IdEstado, int IdPresentacion, int IdUnidadMedidaBasica, double Cantidad,
                             double Peso, double Precio, int No_recepcion, int Ndias,
                             double Cant_despachada, double Peso_despachado, String Nombre_producto, String Nom_presentacion,
                             String Nom_unid_med, String Nom_estado, String User_agr, String Fec_agr,
                             boolean Fecha_especifica, double RoadDes, double RoadDesMon, double RoadTotal,
                             double RoadPrecioDoc, double RoadVAL1, String RoadVAL2, double RoadCantProc,
                             int No_linea, String Atributo_Variante_1, int IdStockEspecifico, boolean EsPadre,
                             int IdPedidoDetPadre, boolean IsNew, clsBeProducto Producto, clsBeProducto_Presentacion Presentacion,
                             clsBeUnidad_medida UnidadMedida, clsBeStock_resList ListaStockRes,
                             clsBeTrans_picking_ubicList ListaPickingUbic, String Codigo_Producto,
                             String NombreProducto, String ProductoPresentacion, String ProductoUnidadMedida, String ProductoEstado,
                             String BodegaUbicacion, double CantidadFisica, double Factor, double CantidadReservada,
                             double PesoReservado, String FechaIngreso, String FechaVence,double Peso_Bruto,double Peso_Neto,
                             double Costo,double valor_aduana, double valor_fob, double valor_iva,double valor_dai,
                             double valor_seguro,double valor_flete,double Total_linea) {

        this.IdPedidoDet=IdPedidoDet;
        this.IdPedidoEnc=IdPedidoEnc;
        this.IdProductoBodega=IdProductoBodega;
        this.ProductoBodega=ProductoBodega;
        this.IdEstado=IdEstado;
        this.IdPresentacion=IdPresentacion;
        this.IdUnidadMedidaBasica=IdUnidadMedidaBasica;
        this.Cantidad=Cantidad;
        this.Peso=Peso;
        this.Precio=Precio;
        this.No_recepcion=No_recepcion;
        this.Ndias=Ndias;
        this.Cant_despachada=Cant_despachada;
        this.Peso_despachado=Peso_despachado;
        this.Nombre_producto=Nombre_producto;
        this.Nom_presentacion=Nom_presentacion;
        this.Nom_unid_med=Nom_unid_med;
        this.Nom_estado=Nom_estado;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.Fecha_especifica=Fecha_especifica;
        this.RoadDes=RoadDes;
        this.RoadDesMon=RoadDesMon;
        this.RoadTotal=RoadTotal;
        this.RoadPrecioDoc=RoadPrecioDoc;
        this.RoadVAL1=RoadVAL1;
        this.RoadVAL2=RoadVAL2;
        this.RoadCantProc=RoadCantProc;
        this.No_linea=No_linea;
        this.Atributo_Variante_1=Atributo_Variante_1;
        this.IdStockEspecifico=IdStockEspecifico;
        this.EsPadre=EsPadre;
        this.IdPedidoDetPadre=IdPedidoDetPadre;
        this.IsNew=IsNew;
        this.Producto=Producto;
        this.Presentacion=Presentacion;
        this.UnidadMedida=UnidadMedida;
        this.ListaStockRes=ListaStockRes;
        this.ListaPickingUbic=ListaPickingUbic;
        this.Codigo_Producto=Codigo_Producto;
        this.NombreProducto=NombreProducto;
        this.ProductoPresentacion=ProductoPresentacion;
        this.ProductoUnidadMedida=ProductoUnidadMedida;
        this.ProductoEstado=ProductoEstado;
        this.BodegaUbicacion=BodegaUbicacion;
        this.CantidadFisica=CantidadFisica;
        this.Factor=Factor;
        this.CantidadReservada=CantidadReservada;
        this.PesoReservado=PesoReservado;
        this.FechaIngreso=FechaIngreso;
        this.FechaVence=FechaVence;
        this.Peso_Bruto = Peso_Bruto;
        this.Peso_Neto = Peso_Neto;
        this.Costo = Costo;

        this.valor_aduana = valor_aduana;
        this.valor_fob = valor_fob;
        this.valor_iva = valor_iva;
        this.valor_dai = valor_dai;
        this.valor_seguro = valor_seguro;
        this.valor_flete = valor_flete;
        this.Total_linea = Total_linea;

    }


    public int getIdPedidoDet() {
        return IdPedidoDet;
    }
    public void setIdPedidoDet(int value) {
        IdPedidoDet=value;
    }
    public int getIdPedidoEnc() {
        return IdPedidoEnc;
    }
    public void setIdPedidoEnc(int value) {
        IdPedidoEnc=value;
    }
    public int getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(int value) {
        IdProductoBodega=value;
    }
    public clsBeProducto_bodega getProductoBodega() {
        return ProductoBodega;
    }
    public void setProductoBodega(clsBeProducto_bodega value) {
        ProductoBodega=value;
    }
    public int getIdEstado() {
        return IdEstado;
    }
    public void setIdEstado(int value) {
        IdEstado=value;
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
    public double getPrecio() {
        return Precio;
    }
    public void setPrecio(double value) {
        Precio=value;
    }
    public int getNo_recepcion() {
        return No_recepcion;
    }
    public void setNo_recepcion(int value) {
        No_recepcion=value;
    }
    public int getNdias() {
        return Ndias;
    }
    public void setNdias(int value) {
        Ndias=value;
    }
    public double getCant_despachada() {
        return Cant_despachada;
    }
    public void setCant_despachada(double value) {
        Cant_despachada=value;
    }
    public double getPeso_despachado() {
        return Peso_despachado;
    }
    public void setPeso_despachado(double value) {
        Peso_despachado=value;
    }
    public String getNombre_producto() {
        return Nombre_producto;
    }
    public void setNombre_producto(String value) {
        Nombre_producto=value;
    }
    public String getNom_presentacion() {
        return Nom_presentacion;
    }
    public void setNom_presentacion(String value) {
        Nom_presentacion=value;
    }
    public String getNom_unid_med() {
        return Nom_unid_med;
    }
    public void setNom_unid_med(String value) {
        Nom_unid_med=value;
    }
    public String getNom_estado() {
        return Nom_estado;
    }
    public void setNom_estado(String value) {
        Nom_estado=value;
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
    public boolean getFecha_especifica() {
        return Fecha_especifica;
    }
    public void setFecha_especifica(boolean value) {
        Fecha_especifica=value;
    }
    public double getRoadDes() {
        return RoadDes;
    }
    public void setRoadDes(double value) {
        RoadDes=value;
    }
    public double getRoadDesMon() {
        return RoadDesMon;
    }
    public void setRoadDesMon(double value) {
        RoadDesMon=value;
    }
    public double getRoadTotal() {
        return RoadTotal;
    }
    public void setRoadTotal(double value) {
        RoadTotal=value;
    }
    public double getRoadPrecioDoc() {
        return RoadPrecioDoc;
    }
    public void setRoadPrecioDoc(double value) {
        RoadPrecioDoc=value;
    }
    public double getRoadVAL1() {
        return RoadVAL1;
    }
    public void setRoadVAL1(double value) {
        RoadVAL1=value;
    }
    public String getRoadVAL2() {
        return RoadVAL2;
    }
    public void setRoadVAL2(String value) {
        RoadVAL2=value;
    }
    public double getRoadCantProc() {
        return RoadCantProc;
    }
    public void setRoadCantProc(double value) {
        RoadCantProc=value;
    }
    public int getNo_linea() {
        return No_linea;
    }
    public void setNo_linea(int value) {
        No_linea=value;
    }
    public String getAtributo_Variante_1() {
        return Atributo_Variante_1;
    }
    public void setAtributo_Variante_1(String value) {
        Atributo_Variante_1=value;
    }
    public int getIdStockEspecifico() {
        return IdStockEspecifico;
    }
    public void setIdStockEspecifico(int value) {
        IdStockEspecifico=value;
    }
    public boolean getEsPadre() {
        return EsPadre;
    }
    public void setEsPadre(boolean value) {
        EsPadre=value;
    }
    public int getIdPedidoDetPadre() {
        return IdPedidoDetPadre;
    }
    public void setIdPedidoDetPadre(int value) {
        IdPedidoDetPadre=value;
    }
    public boolean getIsNew() {
        return IsNew;
    }
    public void setIsNew(boolean value) {
        IsNew=value;
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
    public clsBeStock_resList getListaStockRes() {
        return ListaStockRes;
    }
    public void setListaStockRes(clsBeStock_resList value) {
        ListaStockRes=value;
    }
    public clsBeTrans_picking_ubicList getListaPickingUbic() {
        return ListaPickingUbic;
    }
    public void setListaPickingUbic(clsBeTrans_picking_ubicList value) {
        ListaPickingUbic=value;
    }
    public String getCodigo_Producto() {
        return Codigo_Producto;
    }
    public void setCodigo_Producto(String value) {
        Codigo_Producto=value;
    }
    public String getNombreProducto() {
        return NombreProducto;
    }
    public void setNombreProducto(String value) {
        NombreProducto=value;
    }
    public String getProductoPresentacion() {
        return ProductoPresentacion;
    }
    public void setProductoPresentacion(String value) {
        ProductoPresentacion=value;
    }
    public String getProductoUnidadMedida() {
        return ProductoUnidadMedida;
    }
    public void setProductoUnidadMedida(String value) {
        ProductoUnidadMedida=value;
    }
    public String getProductoEstado() {
        return ProductoEstado;
    }
    public void setProductoEstado(String value) {
        ProductoEstado=value;
    }
    public String getBodegaUbicacion() {
        return BodegaUbicacion;
    }
    public void setBodegaUbicacion(String value) {
        BodegaUbicacion=value;
    }
    public double getCantidadFisica() {
        return CantidadFisica;
    }
    public void setCantidadFisica(double value) {
        CantidadFisica=value;
    }
    public double getFactor() {
        return Factor;
    }
    public void setFactor(double value) {
        Factor=value;
    }
    public double getCantidadReservada() {
        return CantidadReservada;
    }
    public void setCantidadReservada(double value) {
        CantidadReservada=value;
    }
    public double getPesoReservado() {
        return PesoReservado;
    }
    public void setPesoReservado(double value) {
        PesoReservado=value;
    }
    public String getFechaIngreso() {
        return FechaIngreso;
    }
    public void setFechaIngreso(String value) {
        FechaIngreso=value;
    }
    public String getFechaVence() {
        return FechaVence;
    }
    public void setFechaVence(String value) {
        FechaVence=value;
    }
    public double getPeso_Bruto() {
        return Peso_Bruto;
    }
    public void setPeso_Bruto(double value) {
        Peso_Bruto=value;
    }
    public double getPeso_Neto() {
        return Peso_Neto    ;
    }
    public void setPeso_Neto(double value) {
        Peso_Neto=value;
    }
    public double getCosto() {
        return Costo ;
    }
    public void setCosto(double value) {
        Costo=value;
    }
    public double getValor_Aduana() {
        return valor_aduana ;
    }
    public void setValor_Aduana(double value) {
        valor_aduana=value;
    }
    public double getValor_Fob() {
        return valor_fob  ;
    }
    public void setValor_Fob(double value) {
        valor_fob =value;
    }
    public double getValor_Iva() {
        return valor_iva ;
    }
    public void setValor_Iva(double value) {
        valor_iva=value;
    }
    public double getValor_Dai() {
        return valor_dai ;
    }
    public void setValor_Dai(double value) {
        valor_dai=value;
    }
    public double getValor_Seguro() {
        return valor_seguro ;
    }
    public void setValor_Seguro(double value) {
        valor_seguro=value;
    }
    public double getValor_Flete() {
        return valor_flete ;
    }
    public void setValor_Flete(double value) {
        valor_flete=value;
    }
    public double getTotal_Linea() {
        return Total_linea ;
    }
    public void setTotal_Linea(double value) {
        Total_linea=value;
    }

    public int getIdCliente() {
        return IdCliente ;
    }
    public void setIdCliente(int value) {
        IdCliente=value;
    }

}


