package com.example.signalstrengthapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.signalstrengthapp.databinding.FragmentAddSignalStrengthBinding

class AddSignalStrengthFragment : Fragment() {

    private var _binding: FragmentAddSignalStrengthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSignalStrengthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddSignalStrength.setOnClickListener {
            // Get user input
            val xInput = binding.editTextX.text.toString()
            val yInput = binding.editTextY.text.toString()
            val zInput = binding.editTextZ.text.toString()

            // Validate input
            if (xInput.isNotBlank() && yInput.isNotBlank() && zInput.isNotBlank()) {
                // Ensure the input values are valid numbers
                val x = xInput.toDoubleOrNull()
                val y = yInput.toDoubleOrNull()
                val z = zInput.toDoubleOrNull()

                if (x != null && y != null && z != null) {
                    // Create a list with only the original vector based on the user's input
                    val generatedVectors = mutableListOf<Vector>()
                    generatedVectors.add(Vector(x, y, z)) // Add the original vector only

                    // Create a new VectorList with a title and the single vector
                    val listTitle = "List ${VectorData.vectorLists.size + 1}"
                    VectorData.vectorLists.add(VectorList(listTitle, generatedVectors))

                    // Update status
                    binding.textViewStatus.text = "Vector list added successfully!"

                    // Clear input fields
                    binding.editTextX.text.clear()
                    binding.editTextY.text.clear()
                    binding.editTextZ.text.clear()
                } else {
                    // Inform the user if the input is not a valid number
                    binding.textViewStatus.text = "Please enter valid numbers for x, y, and z."
                }
            } else {
                // Inform the user if all fields are not filled
                binding.textViewStatus.text = "Please enter all three coordinates."
            }
        }

        // Button to view the list of vector lists
        binding.buttonViewList.setOnClickListener {
            val intent = Intent(requireContext(), VectorsListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
