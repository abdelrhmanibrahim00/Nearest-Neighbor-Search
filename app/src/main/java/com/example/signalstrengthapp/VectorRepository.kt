package com.example.signalstrengthapp


import androidx.lifecycle.LiveData

class VectorRepository(private val vectorDao: VectorDao) {

    val allVectors: LiveData<List<Vectors>> = vectorDao.getAllVectors()

    suspend fun insert(vector: Vectors) {
        vectorDao.insert(vector)
    }

    suspend fun update(vector: Vectors) {
        vectorDao.update(vector)
    }

    suspend fun getLatestVector(): Vectors? {
        return vectorDao.getLatestVector()
    }

    suspend fun getAllVectors(): List<Vectors> {
        return vectorDao.getAllVectorsSync()
    }
}

