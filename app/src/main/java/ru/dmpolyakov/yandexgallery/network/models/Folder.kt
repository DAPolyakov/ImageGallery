package ru.dmpolyakov.yandexgallery.network.models

import com.squareup.moshi.Json


data class Folder(
        @Json(name = "name") val name: String?,
        @Json(name = "_embedded") val embedded: Embedded?
)

data class Embedded(
        @Json(name = "items") val items: List<ImageFile>?,
        @Json(name = "total") val total: Int?
)