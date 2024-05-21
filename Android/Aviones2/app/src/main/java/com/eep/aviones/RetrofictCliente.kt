package com.eep.aviones

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val URL = "http://10.0.2.2:8080/"

interface ConsumirApi {
    @GET("Aviones")
    fun getTraer(): Call<List<Avion>>

    @POST("Aviones")
    fun crearAvion(@Body avion: Avion): Call<Void>

    @DELETE("Aviones/{id}")
    fun eliminarAvion(@Path("id") avionId: Int): Call<Void>

    @PUT("Aviones/{id}")
    fun actualizarAviones(@Path("id") id: Int, @Body avion: Avion): Call<Void>

}

object Servicio {
    val instancia: ConsumirApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        instancia = retrofit.create(ConsumirApi::class.java)
    }
}
