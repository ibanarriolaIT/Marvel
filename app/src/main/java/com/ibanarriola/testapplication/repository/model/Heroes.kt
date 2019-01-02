package com.ibanarriola.testapplication.repository.model

import android.os.Parcel
import android.os.Parcelable

object Heroes {
    data class DataResult(val data: Data)
    data class Data(val results: List<Hero>)
    data class Hero(val id: Int, val title: String, val description: String, val pageCount: Int, val thumbnail: Thumbnail, val prices: List<Prices>): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readInt(),
                parcel.readParcelable(Thumbnail::class.java.classLoader)!!,
                parcel.createTypedArrayList(Prices)!!)

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeInt(pageCount)
            parcel.writeParcelable(thumbnail, flags)
            parcel.writeTypedList(prices)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Hero> {
            override fun createFromParcel(parcel: Parcel): Hero {
                return Hero(parcel)
            }

            override fun newArray(size: Int): Array<Hero?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Thumbnail(val path: String, val extension: String): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
                parcel.readString()!!)

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(path)
            parcel.writeString(extension)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Thumbnail> {
            override fun createFromParcel(parcel: Parcel): Thumbnail {
                return Thumbnail(parcel)
            }

            override fun newArray(size: Int): Array<Thumbnail?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Prices(val price: Double): Parcelable {
        constructor(parcel: Parcel) : this(parcel.readDouble())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeDouble(price)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Prices> {
            override fun createFromParcel(parcel: Parcel): Prices {
                return Prices(parcel)
            }

            override fun newArray(size: Int): Array<Prices?> {
                return arrayOfNulls(size)
            }
        }
    }
}