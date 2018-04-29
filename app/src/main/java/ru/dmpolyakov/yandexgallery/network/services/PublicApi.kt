package ru.dmpolyakov.yandexgallery.network.services

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dmpolyakov.yandexgallery.network.models.Folder


interface PublicApi {

    @GET("v1/disk/public/resources")
    fun getFolder(
            @Query("public_key") publicKey: String,
            @Query("preview_size") previewSize: String,
            @Query("offset") offset: Int): Single<Folder>


}