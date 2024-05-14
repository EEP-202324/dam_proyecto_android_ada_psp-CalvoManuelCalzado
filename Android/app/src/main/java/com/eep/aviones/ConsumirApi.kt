package com.eep.aviones

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConsumirApi {

    @GET("Aviones")
    fun getTraer(): Call<Aviones>

    @PUT("Aviones/{id}")
    fun putAviones(@Path("id") id: Int, @Body aviones: Aviones): Call<Void>

}