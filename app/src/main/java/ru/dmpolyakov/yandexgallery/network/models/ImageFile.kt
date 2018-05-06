package ru.dmpolyakov.yandexgallery.network.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json


data class ImageFile(
        @Json(name = "name") val name: String?,
        @Json(name = "created") val created: String?,
        @Json(name = "preview") val previewUrl: String?,
        var index: Int = -1
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(created)
        parcel.writeString(previewUrl)
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageFile> {
        override fun createFromParcel(parcel: Parcel): ImageFile {
            return ImageFile(parcel)
        }

        override fun newArray(size: Int): Array<ImageFile?> {
            return arrayOfNulls(size)
        }
    }

}