package com.example.signalstrengthapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NearestNeighborActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NearestNeighborAdapter
    private lateinit var connectionStatusTextView: TextView
    private lateinit var mapView: MapView
    private lateinit var nearestNeighbors: List<Metavimai>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearest_neighbor)

        connectionStatusTextView = findViewById(R.id.textViewConnectionStatus)
        recyclerView = findViewById(R.id.recyclerViewNearestNeighbors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mapView = findViewById(R.id.mapView)

        // Check database connection and calculate nearest neighbors
        checkDatabaseConnection()
        calculateNearestNeighbors()

        // Button to show nearest neighbors on map
        val buttonShowOnMap = findViewById<Button>(R.id.buttonShowOnMap)
        buttonShowOnMap.setOnClickListener {
            // Display calculated coordinates directly on map
            displayOnMap()
        }
    }

    private fun checkDatabaseConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Attempt to fetch data
                Connector.api.getAllCoordinates()
                withContext(Dispatchers.Main) {
                    connectionStatusTextView.text = "Connected to the database successfully!"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    connectionStatusTextView.text = "Failed to connect to the database: ${e.message}"
                }
            }
        }
    }

    private fun calculateNearestNeighbors() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Step 1: Retrieve all stiprumai entries
                val allStiprumai = Connector.api.getSignalStrengthMap()
                // Step 2: Retrieve all coordinates for displaying (x, y) positions on map
                val allCoordinates = Connector.api.getAllCoordinates()

                // Calculate nearest neighbors based on stored lists of vectors
                nearestNeighbors = findNearestNeighbors(allStiprumai, allCoordinates)

                withContext(Dispatchers.Main) {
                    // Set up the adapter to display nearest neighbors
                    adapter = NearestNeighborAdapter(nearestNeighbors)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun findNearestNeighbors(
        allStiprumai: List<SignalStrengthEntry>,
        allCoordinates: List<Metavimai>
    ): List<Metavimai> {
        val nearestNeighborsList = mutableListOf<Metavimai>()

        // Group `stiprumai` entries by `matavimas` to get each set of three coordinates
        val groupedStiprumai = allStiprumai.groupBy { it.matavimas }

        for (vectorList in VectorData.vectorLists) {
            val referencePoint = vectorList.vectors[0] // Reference vector from the app's list

            // For each `matavimas`, get the closest match based on distance calculation
            val nearest = groupedStiprumai.minByOrNull { (matavimas, entries) ->
                if (entries.size >= 3) {
                    val coordinates = listOf(
                        entries[0].stiprumas, entries[1].stiprumas, entries[2].stiprumas
                    )
                    calculateDistance(referencePoint, coordinates)
                } else {
                    Float.MAX_VALUE
                }
            }?.key

            // Find (x, y) coordinates of the nearest matavimas from `matavimai` table
            val nearestCoordinate = allCoordinates.find { it.matavimas == nearest }
            nearestCoordinate?.let { nearestNeighborsList.add(it) }
        }

        return nearestNeighborsList
    }

    private fun calculateDistance(referencePoint: Vector, coordinates: List<Int>): Float {
        val dx = referencePoint.x - coordinates[0]
        val dy = referencePoint.y - coordinates[1]
        val dz = referencePoint.z - coordinates[2]
        return Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble()).toFloat()
    }

    private fun displayOnMap() {
        // Convert nearest neighbors to `StrengthPoint` list with labels
        val labeledCoordinates = nearestNeighbors.mapIndexed { index, neighbor ->
            "List ${index + 1}" to StrengthPoint(neighbor.matavimas, neighbor.x, neighbor.y)
        }

        mapView.coordinates = labeledCoordinates
    }
}
