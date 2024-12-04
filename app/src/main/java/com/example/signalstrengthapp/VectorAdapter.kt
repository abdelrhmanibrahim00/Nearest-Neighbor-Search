package com.example.signalstrengthapp

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.signalstrengthapp.databinding.ItemVectorBinding

class VectorAdapter(
    private val context: Context,
    private val vectors: List<Vectors>, // Direct list of vectors
    private val vectorViewModel: VectorViewModel // ViewModel for database operations
) : RecyclerView.Adapter<VectorAdapter.VectorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VectorViewHolder {
        val binding = ItemVectorBinding.inflate(LayoutInflater.from(context), parent, false)
        return VectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VectorViewHolder, position: Int) {
        val vector = vectors[position]
        holder.bind(vector)
    }

    override fun getItemCount(): Int = vectors.size

    inner class VectorViewHolder(private val binding: ItemVectorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vector: Vectors) {
            binding.textViewX.text = "X: ${vector.x}"
            binding.textViewY.text = "Y: ${vector.y}"
            binding.textViewZ.text = "Z: ${vector.z}"

            binding.buttonEdit.setOnClickListener {
                showEditDialog(vector)
            }
        }

        private fun showEditDialog(vector: Vectors) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_vector, null)
            val editTextX = dialogView.findViewById<EditText>(R.id.editTextX)
            val editTextY = dialogView.findViewById<EditText>(R.id.editTextY)
            val editTextZ = dialogView.findViewById<EditText>(R.id.editTextZ)

            editTextX.setText(vector.x.toString())
            editTextY.setText(vector.y.toString())
            editTextZ.setText(vector.z.toString())

            AlertDialog.Builder(context)
                .setTitle("Edit Vector")
                .setView(dialogView)
                .setPositiveButton("Update") { _, _ ->
                    val newX = editTextX.text.toString().toDoubleOrNull()
                    val newY = editTextY.text.toString().toDoubleOrNull()
                    val newZ = editTextZ.text.toString().toDoubleOrNull()

                    if (newX != null && newY != null && newZ != null) {
                        // Update vector in Room database
                        vector.x = newX
                        vector.y = newY
                        vector.z = newZ

                        vectorViewModel.update(vector) // Update in database
                        Log.d("VectorAdapter", "Updated vector: $vector")
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
