package com.example.signalstrengthapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.signalstrengthapp.databinding.FragmentAddSignalStrengthBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddSignalStrengthFragment : Fragment() {

    private var _binding: FragmentAddSignalStrengthBinding? = null
    private val binding get() = _binding!!

    private lateinit var vectorViewModel: VectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSignalStrengthBinding.inflate(inflater, container, false)

        // Initialize AppDatabase and VectorViewModel
        initializeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddSignalStrength.setOnClickListener {
            addSignalStrength()
        }

        binding.buttonViewList.setOnClickListener {
            navigateToVectorsList()
        }

        binding.buttonDebugFetch.setOnClickListener {
            fetchAllVectors()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViewModel() {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = VectorRepository(database.vectorDao())
        vectorViewModel = ViewModelProvider(
            this,
            VectorViewModelFactory(repository)
        )[VectorViewModel::class.java]
    }

    private fun addSignalStrength() {
        val xInput = binding.editTextX.text.toString()
        val yInput = binding.editTextY.text.toString()
        val zInput = binding.editTextZ.text.toString()

        // Validate input
        if (xInput.isNotBlank() && yInput.isNotBlank() && zInput.isNotBlank()) {
            val x = xInput.toDoubleOrNull()
            val y = yInput.toDoubleOrNull()
            val z = zInput.toDoubleOrNull()

            if (x != null && y != null && z != null) {
                val vector = Vectors(x = x, y = y, z = z)

                // Save vector to the database
                CoroutineScope(Dispatchers.IO).launch {
                    vectorViewModel.insert(vector)

                    // Log the inserted vector
                    Log.d("DatabaseDebug", "Inserted vector: $vector")

                    // Retrieve and log all vectors from the database
                    val allVectors = vectorViewModel.getAllVectors()
                    allVectors.forEach {
                        Log.d("DatabaseDebug", "Fetched Vector: id=${it.id}, x=${it.x}, y=${it.y}, z=${it.z}")
                    }

                    // Retrieve the saved vector with its auto-generated ID
                    val savedVector = vectorViewModel.getLatestVector()

                    // Pass the saved vector to the next activity
                   // navigateToVectorsList(savedVector)
                }

                // Update UI
                binding.textViewStatus.text = "Vector saved successfully!"
                clearInputFields()
            } else {
                binding.textViewStatus.text = "Please enter valid numbers for x, y, and z."
            }
        } else {
            binding.textViewStatus.text = "Please enter all three coordinates."
        }
    }

    private fun fetchAllVectors() {
        CoroutineScope(Dispatchers.IO).launch {
            val allVectors = vectorViewModel.getAllVectors()
            allVectors.forEach {
                Log.d("DatabaseDebug", "Fetched Vector: id=${it.id}, x=${it.x}, y=${it.y}, z=${it.z}")
            }
        }
    }

    private fun navigateToVectorsList(vector: Vectors? = null) {
        val intent = Intent(requireContext(), VectorsListActivity::class.java)
        vector?.let {
            intent.putExtra("vector", it)
        }
        startActivity(intent)
    }

    private fun clearInputFields() {
        binding.editTextX.text.clear()
        binding.editTextY.text.clear()
        binding.editTextZ.text.clear()
    }
}
