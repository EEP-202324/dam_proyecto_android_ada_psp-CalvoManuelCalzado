package com.eep.aviones

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvionesAdapter(private val avionesList: MutableList<Aviones>) : RecyclerView.Adapter<AvionesAdapter.AvionesViewHolder>() {

    class AvionesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val apellidoTextView: TextView = itemView.findViewById(R.id.apellidoTextView)
        val aeTextView: TextView = itemView.findViewById(R.id.aeTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvionesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avion, parent, false)
        return AvionesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvionesViewHolder, position: Int) {
        val avion = avionesList[position]
        holder.nameTextView.text = avion.name
        holder.apellidoTextView.text = avion.apellido
        // No se asigna ningún valor a idTextView
        holder.aeTextView.text = avion.ae
        holder.deleteButton.setOnClickListener {
            // Lógica para manejar el clic en el botón de papelera
            val retrofitEliminar = Servicio.instancia.eliminarAvion(avion.id)
            retrofitEliminar.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Eliminación exitosa
                        // Eliminar el elemento de la lista y notificar al adaptador
                        avionesList.remove(avion)
                        notifyDataSetChanged()
                    } else {
                        // Error al eliminar
                        Log.e("AvionesAdapter", "Error al eliminar el avión")
                        Toast.makeText(holder.itemView.context, "Error al eliminar el avión", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Error de red
                    Log.e("AvionesAdapter", "Error de red al intentar eliminar el avión", t)
                    Toast.makeText(holder.itemView.context, "Error de red al intentar eliminar el avión", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return avionesList.size
    }
}