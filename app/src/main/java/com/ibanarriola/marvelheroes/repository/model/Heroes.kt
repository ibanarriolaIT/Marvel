package com.ibanarriola.marvelheroes.repository.model

import android.os.Parcel
import android.os.Parcelable
import java.text.DecimalFormat

object Heroes {
    data class MapHero(val name: String?, val description: String?, val price: String, val smallImage: String, val bigImage: String) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(description)
            parcel.writeString(price)
            parcel.writeString(smallImage)
            parcel.writeString(bigImage)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MapHero> {
            override fun createFromParcel(parcel: Parcel): MapHero {
                return MapHero(parcel)
            }

            override fun newArray(size: Int): Array<MapHero?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class DataResult(val data: Data)
    data class Data(val results: List<Hero>)
    data class Hero(val id: Int, val title: String?, val description: String?, val pageCount: Int, val thumbnail: Thumbnail?, val prices: List<Prices>?) {
        fun convertToMapHero(): MapHero {
            val df = DecimalFormat("0.##€")
            return MapHero(
                    title,
                    description,
                    df.format(prices?.get(0)?.price),
                    thumbnail?.path + "/standard_large." + thumbnail?.extension,
                    thumbnail?.path + "/standard_fantastic." + thumbnail?.extension)
        }
    }

    data class Thumbnail(val path: String, val extension: String) : Parcelable {
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

    data class Prices(val price: Double) : Parcelable {
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