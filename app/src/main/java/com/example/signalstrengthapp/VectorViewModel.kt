package com.example.signalstrengthapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VectorViewModel(private val repository: VectorRepository) : ViewModel() {

    // LiveData to observe all vectors
    val allVectors: LiveData<List<Vectors>> = repository.allVectors

    // Insert a vector into the database
    fun insert(vector: Vectors) {
        viewModelScope.launch {
            repository.insert(vector)
        }
    }

    // Update an existing vector in the database
    fun update(vector: Vectors) {
        viewModelScope.launch {
            repository.update(vector)
        }
    }

    // Fetch the latest vector (last inserted) from the database
    suspend fun getLatestVector(): Vectors? {
        return repository.getLatestVector()
    }

    // Fetch all vectors from the database as a List
    suspend fun getAllVectors(): List<Vectors> {
        return repository.getAllVectors()
    }
}

class VectorViewModelFactory(private val repository: VectorRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VectorViewModel::class.java)) {
            return VectorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
