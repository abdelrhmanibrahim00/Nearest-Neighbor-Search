package com.example.signalstrengthapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.GET
import retrofit2.http.Path
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Database
import androidx.room.RoomDatabase

interface SignalStrengthService {
    @GET("api/signalStrength")
    suspend fun getSignalStrengthMap(): List<SignalStrengthEntry>

    @GET("api/allCoordinates")
    suspend fun getAllCoordinates(): List<Metavimai>

    @GET("api/getCoordinates/{matavimasId}")
    suspend fun getCoordinatesForMetavimasId(@Path("matavimasId") metavimasId: Int): Metavimai?
}

data class SignalStrengthEntry(
    val id: Int,
    val matavimas: Int,
    val stiprumas: Int,
    val sensorius: String
)

@Entity(tableName = "stiprumai")
data class Stiprumai(
    @PrimaryKey val id: Int,
    val matavimas: Int,
    val stiprumas: Int,
    val sensorius: String
)

@Entity(tableName = "matavimai")
data class Metavimai(
    @PrimaryKey val matavimas: Int,
    val x: Int,
    val y: Int,
    val atstumas: Float
)

data class StiprumaiMatavimai(
    val stiprumaiId: Int,
    val matavimas: Int,
    val stiprumas: Int,
    val sensorius: String,
    val x: Int?,
    val y: Int?,
    val atstumas: Float?
)

@Dao
interface StiprumaiDao {
    @Query("SELECT * FROM stiprumai WHERE matavimas = :matavimasId")
    fun getStrengthByMatavimasId(matavimasId: Int): List<Stiprumai>
}

@Dao
interface MatavimaiDao {
    @Query("SELECT * FROM matavimai WHERE matavimas = :matavimasId")
    fun getCoordinatesByMatavimasId(matavimasId: Int): Metavimai?
}

//@Database(entities = [Stiprumai::class, Metavimai::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun stiprumaiDao(): StiprumaiDao
//    abstract fun matavimaiDao(): MatavimaiDao
//}