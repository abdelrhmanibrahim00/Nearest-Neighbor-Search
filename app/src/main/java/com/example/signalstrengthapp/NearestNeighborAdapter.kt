// NearestNeighborAdapter.kt
package com.example.signalstrengthapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NearestNeighborAdapter(private val neighbors: List<Metavimai>) : RecyclerView.Adapter<NearestNeighborAdapter.NeighborViewHolder>() {

    class NeighborViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCoordinate: TextView = view.findViewById(R.id.textViewCoordinate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeighborViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_neighbor, parent, false)
        return NeighborViewHolder(view)
    }

    override fun onBindViewHolder(holder: NeighborViewHolder, position: Int) {
        val neighbor = neighbors[position]
        holder.textViewCoordinate.text = "Coordinate: (${neighbor.x}, ${neighbor.y}), Distance: ${neighbor.atstumas}"
    }

    override fun getItemCount(): Int = neighbors.size
}
