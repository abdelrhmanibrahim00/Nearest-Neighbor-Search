package com.example.signalstrengthapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VectorsListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VectorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vectors_list)

        // Set the toolbar as the Action Bar
      //  setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show back button

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val vectorLists = VectorData.vectorLists
        adapter = VectorAdapter(this, vectorLists)
        recyclerView.adapter = adapter

        val buttonCompute = findViewById<Button>(R.id.buttonComputeNearestNeighbor)
        buttonCompute.setOnClickListener {
            val intent = Intent(this, NearestNeighborActivity::class.java)
            intent.putParcelableArrayListExtra("vectorLists", ArrayList(vectorLists))
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish() // Go back to the previous activity
        return true
    }
}