package com.dts.base;

import com.dts.classes.Mantenimientos.TipoEtiqueta.clsBeTipo_etiqueta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("Get_Tipo_Etiqueta_By_IdTipoEtiqueta_Json")
    Call<clsBeTipo_etiqueta> getTipoEtiqueta(
            @Query("IdTipoEtiqueta") int idTipoEtiqueta,
            @Query("IdSimbologia") int idSimbologia
    );
}