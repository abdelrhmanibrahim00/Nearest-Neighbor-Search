package com.example.signalstrengthapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VectorsListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VectorAdapter
    private lateinit var vectorViewModel: VectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vectors_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        initializeViewModel()

        // Observe LiveData and populate RecyclerView
        vectorViewModel.allVectors.observe(this) { vectors ->
            if (vectors.isNotEmpty()) {
                Log.d("VectorData", "Fetched Vectors: $vectors")
                adapter = VectorAdapter(this, vectors, vectorViewModel)
                recyclerView.adapter = adapter
            } else {
                Log.d("VectorData", "No vectors found in the database.")
            }
        }

        // Handle Compute Button
        findViewById<Button>(R.id.buttonComputeNearestNeighbor).setOnClickListener {
            computeNearestNeighbor()
        }
    }

    private fun initializeViewModel() {
        val database = AppDatabase.getDatabase(this)
        val repository = VectorRepository(database.vectorDao())
        vectorViewModel = ViewModelProvider(
            this,
            VectorViewModelFactory(repository)
        )[VectorViewModel::class.java]
    }

    private fun computeNearestNeighbor() {
        // Fetch data and navigate to NearestNeighborActivity
        vectorViewModel.allVectors.observe(this) { vectors ->
            if (vectors.isNotEmpty()) {
                val intent = Intent(this, NearestNeighborActivity::class.java)
                intent.putParcelableArrayListExtra("vectors", ArrayList(vectors)) // Pass data
                startActivity(intent)
                Log.d("ComputeButton", "Navigating to NearestNeighborActivity with vectors: $vectors")
            } else {
                Log.d("ComputeButton", "No vectors available for computation.")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
