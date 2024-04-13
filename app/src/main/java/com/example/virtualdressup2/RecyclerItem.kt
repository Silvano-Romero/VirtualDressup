package com.example.virtualdressup2

// Data class representing an item in a RecyclerView
import android.os.Parcel
import android.os.Parcelable

data class RecyclerItem(
    var titleImage: Int, // Resource ID of the image associated with the item
    var heading: String, // Text heading of the item
    val garmentID: String? = null,
    val gender: String? = null,
    val category: String? = null,
    val modelID: String? = null,
    val titleImageURL: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(titleImage)
        parcel.writeString(heading)
        parcel.writeString(garmentID)
        parcel.writeString(gender)
        parcel.writeString(category)
        parcel.writeString(modelID)
        parcel.writeString(titleImageURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecyclerItem> {
        override fun createFromParcel(parcel: Parcel): RecyclerItem {
            return RecyclerItem(parcel)
        }

        override fun newArray(size: Int): Array<RecyclerItem?> {
            return arrayOfNulls(size)
        }
    }
}

