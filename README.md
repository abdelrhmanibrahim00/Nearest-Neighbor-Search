Signal Strength App Overview
Signal Strength App is an integrated solution for collecting, analyzing, and visualizing signal strength data. It combines a Node.js backend, MySQL database, Retrofit for network communication, and Android’s Room database for local data storage.

Features:
Node.js Backend with Express and MySQL to store and manage data.
Android Application with activities and fragments for user interaction.
Room Database integration for local storage on Android.
Retrofit for seamless communication between the Android app and the backend server.
Custom MapView for visualizing coordinates and signal strengths on the map.
Nearest Neighbor Computation to analyze signal strength data for optimal routing or positioning.
Backend (Node.js + MySQL)
Prerequisites:
Node.js
MySQL
Setup:
1- Install the necessary dependencies:
     npm install express mysql

2- Configure the MySQL connection in index.js:
  const db = mysql.createConnection({
    host: 'seklys.ila.lt',
    user: 'stud',
    password: 'vLXCDmSG6EpEnhXX',
    database: 'LDB'
});

3-Start the Node.js server:
node index.js

Endpoints
Root Route: /

Verifies the server is running.

Get Signal Strength Data: /api/signalStrength

Retrieves signal strength data from the stiprumai table.

Get All Coordinates: /api/allCoordinates

Retrieves all coordinates from the matavimai table.

Get Coordinates by ID: /api/getCoordinates/:metavimasId

Retrieves coordinates from the matavimai table by matavimas ID.

Android Application
Prerequisites
Android Studio

Gradle

Setup
1- Add Retrofit dependencies in build.gradle:
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
2- Configure Retrofit in Connector.kt:
private const val BASE_URL = "http://10.151.5.144:3000/"
3- Define API interface in SignalStrengthService.kt:
interface SignalStrengthService {
    @GET("api/signalStrength")
    suspend fun getSignalStrengthMap(): List<SignalStrengthEntry>

    @GET("api/allCoordinates")
    suspend fun getAllCoordinates(): List<Metavimai>

    @GET("api/getCoordinates/{matavimasId}")
    suspend fun getCoordinatesForMetavimasId(@Path("matavimasId") metavimasId: Int): Metavimai?
}

Activities and Fragments
MainActivity: Entry point of the app with navigation setup.

FirstFragment: Home fragment with a button to navigate to SecondFragment.

SecondFragment: Accepts MAC address input and navigates to AddSignalStrengthFragment.

AddSignalStrengthFragment: Handles input for adding signal strength data.

NearestNeighborActivity: Calculates and displays nearest neighbors based on signal strength data.

VectorsListActivity: Displays a list of vectors and navigates to NearestNeighborActivity.

Custom View
MapView: Custom view to draw a grid and plot points with labels for visualizing coordinates.

Room Database
Entities:

Vectors: Represents the vectors table with fields x, y, z.

Stiprumai: Represents the stiprumai table with fields matavimas, stiprumas, sensorius.

Metavimai: Represents the matavimai table with fields matavimas, x, y, atstumas.

DAOs:

VectorDao: Data access object for vectors.

StiprumaiDao: Data access object for stiprumai.

MatavimaiDao: Data access object for matavimai.

Database:

AppDatabase: Room database configuration.

ViewModel and Repository
VectorRepository: Handles data operations for vectors.

VectorViewModel: Manages UI-related data in a lifecycle-conscious way.

Parcelable Data Classes
Vectors: Parcelable implementation for vectors.

VectorList: Parcelable implementation for a list of vectors.

Adapter
VectorAdapter: Adapter for displaying vectors in a RecyclerView with edit functionality.

How to Use
1- Set up the Node.js backend and ensure it is running.

2- Install and configure the Android application.

3- Use the app to input signal strength data, visualize coordinates, and compute nearest neighbors.

License
This project is licensed under the MIT License.



                                    

