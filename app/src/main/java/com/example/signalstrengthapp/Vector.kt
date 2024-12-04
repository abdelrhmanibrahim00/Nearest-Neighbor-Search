package com.example.signalstrengthapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vectors")
data class Vectors(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var x: Double,
    var y: Double,
    var z: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(x)
        parcel.writeDouble(y)
        parcel.writeDouble(z)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Vectors> {
        override fun createFromParcel(parcel: Parcel): Vectors {
            return Vectors(parcel)
        }

        override fun newArray(size: Int): Array<Vectors?> {
            return arrayOfNulls(size)
        }
    }
}
