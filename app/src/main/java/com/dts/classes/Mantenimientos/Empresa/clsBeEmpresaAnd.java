package com.dts.classes.Mantenimientos.Empresa;
import org.simpleframework.xml.Element;

public class clsBeEmpresaAnd {

    @Element(required=false) public int IdEmpresa=0;
    @Element(required=false) public String Nombre="";
    @Element(required=false) public String Direccion="";
    @Element(required=false) public String Telefono="";
    @Element(required=false) public String Email="";
    @Element(required=false) public String Razon_social="";
    @Element(required=false) public String Representante="";
    @Element(required=false) public int Corr_cod_barra=0;
    @Element(required=false) public String Path_printer="";
    @Element(required=false) public boolean Activo=false;
    @Element(required=false) public String User_agr="";
    @Element(required=false) public String Fec_agr="1900-01-01T00:00:00";
    @Element(required=false) public String User_mod="";
    @Element(required=false) public String Fec_mod="1900-01-01T00:00:00";
    @Element(required=false) public boolean ClienteRapido=false;
    @Element(required=false) public String Imagen="";
    @Element(required=false) public boolean Operador_logistico=false;
    @Element(required=false) public int Puerto_escaner=0;
    @Element(required=false) public boolean Control_presentaciones=false;
    @Element(required=false) public boolean Anulaciones_por_supervisor=false;
    @Element(required=false) public String Codigo="";
    @Element(required=false) public String Clave="";
    @Element(required=false) public int Intento=0;
    @Element(required=false) public int Duracionclave=0;
    @Element(required=false) public int Duracionclavetemporal=0;
    @Element(required=false) public boolean codigo_automatico=false;
    @Element(required=false) public boolean politica_contrasenas=false;
    @Element(required=false) public int IdMotivoAjusteInventario=0;


    public clsBeEmpresaAnd() {
    }

    public clsBeEmpresaAnd(int IdEmpresa,String Nombre,String Direccion,String Telefono,
                           String Email,String Razon_social,String Representante,int Corr_cod_barra,
                           String Path_printer,boolean Activo,String User_agr,String Fec_agr,
                           String User_mod,String Fec_mod,boolean ClienteRapido,String Imagen,
                           boolean Operador_logistico,int Puerto_escaner,boolean Control_presentaciones,boolean Anulaciones_por_supervisor,
                           String Codigo,String Clave,int Intento,int Duracionclave,
                           int Duracionclavetemporal,boolean codigo_automatico,boolean politica_contraseñas,int IdMotivoAjusteInventario
    ) {

        this.IdEmpresa=IdEmpresa;
        this.Nombre=Nombre;
        this.Direccion=Direccion;
        this.Telefono=Telefono;
        this.Email=Email;
        this.Razon_social=Razon_social;
        this.Representante=Representante;
        this.Corr_cod_barra=Corr_cod_barra;
        this.Path_printer=Path_printer;
        this.Activo=Activo;
        this.User_agr=User_agr;
        this.Fec_agr=Fec_agr;
        this.User_mod=User_mod;
        this.Fec_mod=Fec_mod;
        this.ClienteRapido=ClienteRapido;
        this.Imagen=Imagen;
        this.Operador_logistico=Operador_logistico;
        this.Puerto_escaner=Puerto_escaner;
        this.Control_presentaciones=Control_presentaciones;
        this.Anulaciones_por_supervisor=Anulaciones_por_supervisor;
        this.Codigo=Codigo;
        this.Clave=Clave;
        this.Intento=Intento;
        this.Duracionclave=Duracionclave;
        this.Duracionclavetemporal=Duracionclavetemporal;
        this.codigo_automatico=codigo_automatico;
        this.politica_contrasenas=politica_contrasenas;
        this.IdMotivoAjusteInventario=IdMotivoAjusteInventario;

    }


    public int getIdEmpresa() {
        return IdEmpresa;
    }
    public void setIdEmpresa(int value) {
        IdEmpresa=value;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String value) {
        Nombre=value;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String value) {
        Direccion=value;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String value) {
        Telefono=value;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String value) {
        Email=value;
    }
    public String getRazon_social() {
        return Razon_social;
    }
    public void setRazon_social(String value) {
        Razon_social=value;
    }
    public String getRepresentante() {
        return Representante;
    }
    public void setRepresentante(String value) {
        Representante=value;
    }
    public int getCorr_cod_barra() {
        return Corr_cod_barra;
    }
    public void setCorr_cod_barra(int value) {
        Corr_cod_barra=value;
    }
    public String getPath_printer() {
        return Path_printer;
    }
    public void setPath_printer(String value) {
        Path_printer=value;
    }
    public boolean getActivo() {
        return Activo;
    }
    public void setActivo(boolean value) {
        Activo=value;
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
    public boolean getClienteRapido() {
        return ClienteRapido;
    }
    public void setClienteRapido(boolean value) {
        ClienteRapido=value;
    }
    public String getImagen() {
        return Imagen;
    }
    public void setImagen(String value) {
        Imagen=value;
    }
    public boolean getOperador_logistico() {
        return Operador_logistico;
    }
    public void setOperador_logistico(boolean value) {
        Operador_logistico=value;
    }
    public int getPuerto_escaner() {
        return Puerto_escaner;
    }
    public void setPuerto_escaner(int value) {
        Puerto_escaner=value;
    }
    public boolean getControl_presentaciones() {
        return Control_presentaciones;
    }
    public void setControl_presentaciones(boolean value) {
        Control_presentaciones=value;
    }
    public boolean getAnulaciones_por_supervisor() {
        return Anulaciones_por_supervisor;
    }
    public void setAnulaciones_por_supervisor(boolean value) {
        Anulaciones_por_supervisor=value;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String value) {
        Codigo=value;
    }
    public String getClave() {
        return Clave;
    }
    public void setClave(String value) {
        Clave=value;
    }
    public int getIntento() {
        return Intento;
    }
    public void setIntento(int value) {
        Intento=value;
    }
    public int getDuracionclave() {
        return Duracionclave;
    }
    public void setDuracionclave(int value) {
        Duracionclave=value;
    }
    public int getDuracionclavetemporal() {
        return Duracionclavetemporal;
    }
    public void setDuracionclavetemporal(int value) {
        Duracionclavetemporal=value;
    }
    public boolean getcodigo_automatico() {
        return codigo_automatico;
    }
    public void setcodigo_automatico(boolean value) {
        codigo_automatico=value;
    }
    public boolean getpolitica_contrasenas() {
        return politica_contrasenas;
    }
    public void setpolitica_contrasenas(boolean value) {
        politica_contrasenas=value;
    }
    public int getIdMotivoAjusteInventario() {
        return IdMotivoAjusteInventario;
    }
    public void setIdMotivoAjusteInventario(int value) {
        IdMotivoAjusteInventario=value;
    }

}

