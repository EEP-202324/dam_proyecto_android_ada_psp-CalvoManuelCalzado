import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eep.aviones100.Aviones
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

    fun deleteAvion(avion: Aviones) {
        viewModelScope.launch {
            try {
                // Realiza la solicitud DELETE usando Retrofit
                val response = AvionesApi.retrofitService.deleteAvion(avion.id)
                if (response.isSuccessful) {
                    // Si la solicitud es exitosa, eliminamos el avión de la lista local
                    _avionesList.remove(avion)
                } else {
                    // Si la solicitud no es exitosa, puedes manejar el error aquí
                    Log.e("AvionesViewModel", "Error al eliminar el avión: ${response.code()}")
                }
            } catch (e: Exception) {
                // Manejo de errores
                Log.e("AvionesViewModel", "Error al eliminar el avión", e)
            }
        }
    }
}
