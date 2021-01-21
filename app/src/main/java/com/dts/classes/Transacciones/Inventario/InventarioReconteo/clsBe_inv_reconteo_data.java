package com.dts.classes.Transacciones.Inventario.InventarioReconteo;

import org.simpleframework.xml.Element;

public class clsBe_inv_reconteo_data {


    @Element(required=false) public int idinventarioenc = 0;
    @Element(required=false) public int idinvreconteo=0;
    @Element(required=false) public int IdUbicacion = 0;
    @Element(required=false) public int NoUbic=0;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public int IdProductoEstado=0;
    @Element(required=false) public String Producto_nombre="";
    @Element(required=false) public int IdPresentacion=0;
    @Element(required=false) public String UMBas="";
    @Element(required=false) public String Pres ="";
    @Element(required=false) public Double Cant_Conteo=0.0;
    @Element(required=false) public Double cantidad=0.0;
    @Element(required=false) public Double Peso_Conteo=0.0;
    @Element(required=false) public Double Cant_Stock=0.0;
    @Element(required=false) public Double Peso_Stock=0.0;
    @Element(required=false) public Double Peso=0.0;
    @Element(required=false) public String Lote="";
    @Element(required=false) public String Lote_stock="";
    @Element(required=false) public String Fecha_Vence="1900-01-01T00:00:01";
    @Element(required=false) public Boolean control_peso=false;
    @Element(required=false) public int Conteo=0;
    @Element(required=false) public String Ubic_nombre="";
    @Element(required=false) public String Estado="";
    @Element(required=false) public int IdProductoBodega=0;
    @Element(required=false) public String Tramo="";
    @Element(required=false) public int IndiceX=0;
    @Element(required=false) public int idPresentacion_nuevo=0;
    @Element(required=false) public int IdProductoEst_nuevo=0;

    @Element(required=false) public String codigo_producto="";
    @Element(required=false) public int Nivel=0;
    @Element(required=false) public String Pos="";
    @Element(required=false) public Double Factor=0.0;
    @Element(required=false) public int index=0;

    public clsBe_inv_reconteo_data(){}

    public clsBe_inv_reconteo_data( int idinventarioenc,int idinvreconteo,int NoUbic,String Codigo,String Producto_nombre,int IdProductoEstado,int IdPresentacion,String UMBas,
                                   String Pres,Double Cant_Conteo,Double cantidad,Double Peso_Conteo,Double Cant_Stock,
                                   Double Peso_Stock,Double Peso,String Lote,String Lote_stock,String Fecha_Vence, boolean control_peso,int Conteo,
                                   String Ubic_nombre,String Estado,int IdProductoBodega,String Tramo,int IndiceX,int idPresentacion_nuevo,
                                   int IdProductoEst_nuevo,String codigo_producto,
                                   int Nivel,String Pos,Double Factor, int index){

        this.idinventarioenc = idinventarioenc;
        this.idinvreconteo = idinvreconteo;
        this.NoUbic = NoUbic;
        this.Codigo = Codigo;
        this.IdProductoEstado = IdProductoEstado;
        this.Producto_nombre = Producto_nombre;
        this.IdPresentacion = IdPresentacion;
        this.UMBas = UMBas;
        this.Pres = Pres;
        this.Cant_Conteo = Cant_Conteo;
        this.cantidad = cantidad;
        this.Peso_Conteo = Peso_Conteo;
        this.Cant_Stock = Cant_Stock;
        this.Peso_Stock = Peso_Stock;
        this.Peso = Peso;
        this.Lote = Lote;
        this.Lote_stock = Lote_stock;
        this.Fecha_Vence = Fecha_Vence;
        this.control_peso = control_peso;
        this.Conteo = Conteo;
        this.Ubic_nombre = Ubic_nombre;
        this.Estado = Estado;
        this.IdProductoBodega = IdProductoBodega;
        this.Tramo = Tramo;
        this.IndiceX = IndiceX;
        this.idPresentacion_nuevo = idPresentacion_nuevo;
        this.IdProductoEst_nuevo = IdProductoEst_nuevo;
        this.codigo_producto = codigo_producto;
        this.Nivel = Nivel;
        this.Pos = Pos;
        this.Factor = Factor;
        this.index = index;
    }


    public int getidinventarioenc() {
        return idinventarioenc;
    }
    public void setidinventarioenc(int value) {
        idinventarioenc=value;
    }

    public int getidinvreconteo() {
        return idinvreconteo;
    }
    public void setidinvreconteo(int value) {
        idinvreconteo=value;
    }

    public int getNoUbic() {
        return NoUbic;
    }
    public void setNoUbic(int value) {
        NoUbic=value;
    }

    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }

    public int getIdProductoEstado() {
        return IdProductoEstado;
    }
    public void setIdProductoEstado(int value) {
        IdProductoEstado=value;
    }

    public int getIdPresentacion() {
        return IdPresentacion;
    }
    public void setIdPresentacion(int value) {
        IdPresentacion=value;
    }

    public String getProducto_nombre() {
        return Producto_nombre;
    }
    public void setProducto_nombre(String value) {
        Producto_nombre=value;
    }

    public String getUMBas() {
        return UMBas;
    }
    public void setUMBas(String value) {
        UMBas=value;
    }

    public String getPres() {
        return Pres;
    }
    public void setPres(String value) {
        Pres=value;
    }

    public Double getCant_Conteo() {
        return Cant_Conteo;
    }
    public void setCant_Conteo(Double value) {
        Cant_Conteo=value;
    }


    public Double getcantidad() {
        return cantidad;
    }
    public void setcantidad(Double value) {
        cantidad=value;
    }

    public Double getPeso_Conteo() {
        return Peso_Conteo;
    }
    public void setPeso_Conteo(Double value) {
        Peso_Conteo=value;
    }

    public Double getCant_Stock() {
        return Cant_Stock;
    }
    public void setCant_Stock(Double value) {
        Cant_Stock=value;
    }

    public Double getPeso_Stock() {
        return Peso_Stock;
    }
    public void setPeso_Stock(Double value) {
        Peso_Stock=value;
    }

    public Double getPeso() {
        return Peso;
    }
    public void setPeso(Double value) {
        Peso=value;
    }

    public String getLote_stock() {
        return Lote_stock;
    }
    public void setLote_stock(String value) {
        Lote_stock=value;
    }

    public String getLote() {
        return Lote;
    }
    public void setLote(String value) {
        Lote=value;
    }

    public boolean getcontrol_peso() {
        return control_peso;
    }
    public void setcontrol_peso(boolean value) {
        control_peso=value;
    }

    public String getFecha_Vence() {
        return Fecha_Vence;
    }
    public void setFecha_Vence(String value) {
        Fecha_Vence=value;
    }

    public Integer getConteo() {
        return Conteo;
    }
    public void setConteo(Integer value) {
        Conteo=value;
    }


    public String getUbic_nombre() {
        return Ubic_nombre;
    }
    public void setUbic_nombre(String value) {
        Ubic_nombre=value;
    }

    public String getEstado() {
        return Estado;
    }
    public void setEstado(String value) {
        Estado=value;
    }


    public Integer getIdProductoBodega() {
        return IdProductoBodega;
    }
    public void setIdProductoBodega(Integer value) {
        IdProductoBodega=value;
    }

    public String getTramo() {
        return Tramo;
    }
    public void setTramo(String value) {
        Tramo=value;
    }

    public Integer getIndiceX() {
        return IndiceX;
    }
    public void setIndiceX(Integer value) {
        IndiceX=value;
    }

    public Integer getidPresentacion_nuevo() {
        return idPresentacion_nuevo;
    }
    public void setidPresentacion_nuevo(Integer value) {
        idPresentacion_nuevo=value;
    }

    public Integer getIdProductoEst_nuevo() {
        return IdProductoEst_nuevo;
    }
    public void setIdProductoEst_nuevo(Integer value) {
        IdProductoEst_nuevo=value;
    }

    public String getcodigo_producto() {
        return codigo_producto;
    }
    public void setcodigo_producto(String value) {
        codigo_producto=value;
    }

    public Integer getNivel() {
        return Nivel;
    }
    public void setNivel(Integer value) {
        Nivel=value;
    }

    public String getPos() {
        return Pos;
    }
    public void setPos(String value) {
        Pos=value;
    }

    public Double getFactor() {
        return Factor;
    }
    public void setFactor(Double value) {
        Factor=value;
    }

    public int getindex() {
        return index;
    }
    public void setindex(int value) {
        index=value;
    }

}
