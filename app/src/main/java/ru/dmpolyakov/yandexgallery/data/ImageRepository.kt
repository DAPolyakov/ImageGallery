package ru.dmpolyakov.yandexgallery.data

import io.reactivex.Single
import ru.dmpolyakov.yandexgallery.network.NetworkModule
import ru.dmpolyakov.yandexgallery.network.models.Folder


class ImageRepository {

    private val publicService = NetworkModule.publicService

    fun getFolder(offset: Int): Single<Folder> {
        return publicService.getFolder(offset)
    }
}