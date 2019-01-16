package com.ibanarriola.marvelheroes.repository.model

import android.os.Parcel
import android.os.Parcelable
import java.text.DecimalFormat

object Heroes {
    const val PRICE_FORMAT = "%s€"
    const val DECIMAL_FORMAT = "0.##"
    const val SMALL_IMAGE = "/standard_large."
    const val BIG_IMAGE = "/standard_fantastic."

    data class MapHero(val title: String?, val smallImage: String?, val bigImage: String?, val price: String?, val description: String?): Parcelable {

        object ModelMapper {
            fun from(form: Hero): MapHero {
                val df = DecimalFormat(DECIMAL_FORMAT)
                return MapHero(
                        form.title,
                        form.thumbnail?.path + SMALL_IMAGE + form.thumbnail?.extension,
                        form.thumbnail?.path + BIG_IMAGE + form.thumbnail?.extension,
                        String.format(PRICE_FORMAT, df.format(form.prices?.get(0)?.price)),
                        form.description)
            }
        }

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(smallImage)
            parcel.writeString(bigImage)
            parcel.writeString(price)
            parcel.writeString(description)
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
    data class Hero(val id: Int, val title: String?, val description: String?, val pageCount: Int, val thumbnail: Thumbnail?, val prices: List<Prices>?)
    data class Thumbnail(val path: String, val extension: String)
    data class Prices(val price: Double)
}