package com.example.signalstrengthapp

import android.os.Parcel
import android.os.Parcelable

data class VectorList(
    val title: String,
    val vectors: MutableList<Vector>,
    var isExpanded: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        mutableListOf<Vector>().apply {
            parcel.readList(this as List<*>, Vector::class.java.classLoader)
        },
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeList(vectors)
        parcel.writeByte(if (isExpanded) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<VectorList> {
        override fun createFromParcel(parcel: Parcel): VectorList {
            return VectorList(parcel)
        }

        override fun newArray(size: Int): Array<VectorList?> {
            return arrayOfNulls(size)
        }
    }
}


