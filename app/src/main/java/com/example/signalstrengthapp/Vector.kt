package com.example.signalstrengthapp

import android.os.Parcel
import android.os.Parcelable

data class Vector(var x: Double, var y: Double, var z: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(x)
        parcel.writeDouble(y)
        parcel.writeDouble(z)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vector> {
        override fun createFromParcel(parcel: Parcel): Vector {
            return Vector(parcel)
        }

        override fun newArray(size: Int): Array<Vector?> {
            return arrayOfNulls(size)
        }
    }
}
