package com.eep.aviones

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eep.aviones.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("MainActivity", "onCreate: Activity started")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofitTraer = Servicio.instancia.getTraer()
        Log.e("MainActivity", "onCreate: Retrofit call created")

        retrofitTraer.enqueue(object : Callback<List<Aviones>> {
            override fun onResponse(call: Call<List<Aviones>>, response: Response<List<Aviones>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("MainActivity", "onResponse: ${data.toString()}")
                    if (data != null) {
                        val adapter = AvionesAdapter(data)
                        binding.recyclerView.adapter = adapter
                    }
                } else {
                    Log.e("MainActivity", "onResponse: Response failed")
                }
            }

            override fun onFailure(call: Call<List<Aviones>>, t: Throwable) {
                Log.e("MainActivity", "onFailure: API call failed", t)
                Toast.makeText(this@MainActivity, "Error al consultar Api Rest", Toast.LENGTH_SHORT).show()
            }
        })

        // Agregar un OnClickListener al botón flotante
        binding.fabAdd.setOnClickListener {
            // Crear un Intent para pasar de MainActivity a Pantalla2
            val intent = Intent(this@MainActivity, Pantalla2::class.java)
            startActivity(intent)
        }
    }

}

