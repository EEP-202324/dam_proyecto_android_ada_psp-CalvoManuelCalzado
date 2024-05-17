package com.eep.aviones100

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/"

interface AvionesApiService {
    @GET("Aviones")
    suspend fun getAviones(): List<Aviones>

    @PUT("Aviones/{id}")
    suspend fun updateAviones(@Path("id") id: Long, @Body aviones: Aviones): Aviones

    @DELETE("Aviones/{id}")
    suspend fun deleteAviones(@Path("id") id: Long): Unit

    @POST("Aviones")
    suspend fun addAviones(@Body aviones: Aviones): Response<Void>
}

object AvionesApi {
    val retrofitService: AvionesApiService by lazy {
        retrofit.create(AvionesApiService::class.java)
    }
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
