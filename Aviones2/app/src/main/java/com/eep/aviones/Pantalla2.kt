package com.eep.aviones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Pantalla2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla2)

        // Obtener referencia al layout contenedor
        val container = findViewById<LinearLayout>(R.id.form_container)

        // Crear EditText para ingresar el nombre
        val nombreEditText = EditText(this)
        nombreEditText.hint = "Nombre"
        container.addView(nombreEditText)

        // Crear EditText para ingresar el apellido
        val apellidoEditText = EditText(this)
        apellidoEditText.hint = "Apellido"
        container.addView(apellidoEditText)

        // Crear EditText para ingresar el AE
        val aeEditText = EditText(this)
        aeEditText.hint = "AE"
        container.addView(aeEditText)

        // Crear botón para enviar el formulario
        val enviarButton = Button(this)
        enviarButton.text = "Enviar"
        container.addView(enviarButton)

        enviarButton.setOnClickListener {
            // Acciones al hacer clic en el botón
            val nombre = nombreEditText.text.toString()
            val apellido = apellidoEditText.text.toString()
            val ae = aeEditText.text.toString()

            // Crear un nuevo objeto Aviones
            val nuevoAvion = Aviones(nombre, apellido, 0, ae)

            // Llamar a la función para crear un nuevo avión en la API
            Servicio.instancia.crearAvion(nuevoAvion).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Pantalla2, "Avión creado exitosamente", Toast.LENGTH_SHORT).show()
                        // Volver a la pantalla principal
                        val intent = Intent(this@Pantalla2, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Pantalla2, "Error al crear el avión", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@Pantalla2, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Crear botón para volver a MainActivity
        val volverButton = Button(this)
        volverButton.text = "Volver"
        container.addView(volverButton)

        volverButton.setOnClickListener {
            // Acciones al hacer clic en el botón de volver
            val intent = Intent(this@Pantalla2, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
