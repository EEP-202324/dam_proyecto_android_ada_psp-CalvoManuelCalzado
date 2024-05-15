package com.eep.aviones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AvionesAdapter(private val avionesList: List<Aviones>) : RecyclerView.Adapter<AvionesAdapter.AvionesViewHolder>() {

    class AvionesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val apellidoTextView: TextView = itemView.findViewById(R.id.apellidoTextView)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val aeTextView: TextView = itemView.findViewById(R.id.aeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvionesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avion, parent, false)
        return AvionesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvionesViewHolder, position: Int) {
        val avion = avionesList[position]
        holder.nameTextView.text = avion.name
        holder.apellidoTextView.text = avion.apellido
        holder.idTextView.text = avion.id.toString()
        holder.aeTextView.text = avion.ae
    }

    override fun getItemCount(): Int {
        return avionesList.size
    }
}
