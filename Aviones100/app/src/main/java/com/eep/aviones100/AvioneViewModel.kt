package com.eep.aviones100

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AvionesViewModel : ViewModel() {
    private val _avionesList = mutableStateListOf<Aviones>()
    val avionesList: List<Aviones> get() = _avionesList

    init {
        fetchAviones()
    }

    private fun fetchAviones() {
        viewModelScope.launch {
            try {
                val aviones = AvionesApi.retrofitService.getAviones()
                if (aviones.isEmpty()) {
                    Log.e("AvionesViewModel", "No se recibieron aviones de la API")
                } else {
                    _avionesList.clear()
                    _avionesList.addAll(aviones)
                    Log.d("AvionesViewModel", "Aviones recibidos: $aviones")
                }
            } catch (e: Exception) {
                Log.e("AvionesViewModel", "Error al obtener aviones", e)
            }
        }
    }
}
