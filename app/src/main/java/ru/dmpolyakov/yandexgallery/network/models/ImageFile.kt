package ru.dmpolyakov.yandexgallery.network.models

import com.squareup.moshi.Json


data class ImageFile(
        @Json(name = "name") val name: String?,
        @Json(name = "created") val created: String?,
        @Json(name = "preview") val preview: String?,
        var index: Int = -1,
        var imagesInFolder: Int = 0
)