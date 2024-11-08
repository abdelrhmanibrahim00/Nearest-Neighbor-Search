package com.example.signalstrengthapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.signalstrengthapp.databinding.ItemVectorBinding
import com.example.signalstrengthapp.databinding.VectorListItemBinding

class VectorAdapter(
    private val context: Context,
    private val vectorLists: List<VectorList> // List of lists
) : RecyclerView.Adapter<VectorAdapter.VectorListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VectorListViewHolder {
        val binding = VectorListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return VectorListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VectorListViewHolder, position: Int) {
        val vectorList = vectorLists[position]
        holder.bind(vectorList)
    }

    override fun getItemCount(): Int = vectorLists.size

    inner class VectorListViewHolder(private val binding: VectorListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vectorList: VectorList) {
            // Set list title (List 1, List 2, etc.)
            binding.textViewTitle.text = vectorList.title
            binding.sublistContainer.removeAllViews() // Clear previous vector views

            // Expand/collapse vectors on click
            binding.textViewTitle.setOnClickListener {
                vectorList.isExpanded = !vectorList.isExpanded
                binding.sublistContainer.visibility = if (vectorList.isExpanded) View.VISIBLE else View.GONE
                if (vectorList.isExpanded) populateSublist(vectorList.vectors, binding.sublistContainer)
            }
        }

        // Display each vector in the expanded list
        private fun populateSublist(vectors: List<Vector>, container: LinearLayout) {
            container.removeAllViews() // Clear previous sublist views

            for (vector in vectors) {
                val vectorItemView = ItemVectorBinding.inflate(LayoutInflater.from(context), container, false)

                // Populate vector data
                vectorItemView.textViewX.text = vector.x.toString()
                vectorItemView.textViewY.text = vector.y.toString()
                vectorItemView.textViewZ.text = vector.z.toString()

                // Edit vector functionality
                vectorItemView.buttonEdit.setOnClickListener {
                    showEditDialog(vector)
                }

                container.addView(vectorItemView.root) // Add vector to sublist container
            }
        }

        // Edit dialog to modify vector values
        private fun showEditDialog(vector: Vector) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_vector, null)
            val editTextX = dialogView.findViewById<EditText>(R.id.editTextX)
            val editTextY = dialogView.findViewById<EditText>(R.id.editTextY)
            val editTextZ = dialogView.findViewById<EditText>(R.id.editTextZ)

            // Set existing values
            editTextX.setText(vector.x.toString())
            editTextY.setText(vector.y.toString())
            editTextZ.setText(vector.z.toString())

            AlertDialog.Builder(context)
                .setTitle("Edit Vector")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->
                    // Update vector values with input validation
                    vector.x = editTextX.text.toString().toDoubleOrNull() ?: vector.x
                    vector.y = editTextY.text.toString().toDoubleOrNull() ?: vector.y
                    vector.z = editTextZ.text.toString().toDoubleOrNull() ?: vector.z
                    notifyItemChanged(bindingAdapterPosition) // Update adapter
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
