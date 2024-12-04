package com.example.signalstrengthapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VectorDao {

    @Query("SELECT * FROM vectors")
    fun getAllVectors(): LiveData<List<Vectors>> // Used for observing LiveData in ViewModel.

    @Query("SELECT * FROM vectors")
    suspend fun getAllVectorsSync(): List<Vectors> // Used for direct access in suspend functions.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vector: Vectors)

    @Update
    suspend fun update(vector: Vectors)

    @Query("SELECT * FROM vectors ORDER BY id DESC LIMIT 1")
    suspend fun getLatestVector(): Vectors?
}




