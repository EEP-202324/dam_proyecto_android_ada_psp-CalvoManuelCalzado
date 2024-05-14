package com.eep.aviones

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofictCliente {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://demo6523688.mockable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val consumirApi: ConsumirApi = retrofit.create(ConsumirApi::class.java)
}
