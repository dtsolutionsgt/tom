package com.dts.classes.Transacciones.ControlCalidad;

public class clsBeEncuesta {

    public int IdPregunta = 0;
    public String Pregunta = "";
    public boolean Respuesta = false;
    public int Tipo = 0;

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public boolean isRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        Respuesta = respuesta;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }
}

