package com.example.myapppersonas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eep.aviones100.Aviones
import com.eep.aviones100.AvionesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SecondScreen(coroutineScope: CoroutineScope) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val nombreState = remember { mutableStateOf("") }
            val apellidoState = remember { mutableStateOf("") }
            val aeState = remember { mutableStateOf("") }

            TextField(
                value = nombreState.value,
                onValueChange = { nombreState.value = it },
                label = { Text("Nombre") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TextField(
                value = apellidoState.value,
                onValueChange = { apellidoState.value = it },
                label = { Text("Apellido") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TextField(
                value = aeState.value,
                onValueChange = { aeState.value = it },
                label = { Text("AE") },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    // Envía los datos del formulario
                    val avion = Aviones(
                        id = 0, // Supongamos que no necesitas el ID para el nuevo registro
                        name = nombreState.value,
                        apellido = apellidoState.value,
                        ae = aeState.value
                    )
                    coroutineScope.launch {
                        enviarDatos(avion)
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "Enviar")
            }
        }
    }
}

suspend fun enviarDatos(avion: Aviones) {
    try {
        // Realiza la solicitud POST usando Retrofit
        val response = AvionesApi.retrofitService.addAviones(avion)
        if (response.isSuccessful) {
            // Si la solicitud es exitosa, puedes manejar la respuesta aquí
            // Por ejemplo, mostrar un mensaje de éxito o navegar a otra pantalla
        } else {
            // Si la solicitud no es exitosa, puedes manejar el error aquí
        }
    } catch (e: Exception) {
        // Manejo de errores
    }
}
