package ru.dmpolyakov.yandexgallery.network.services

import io.reactivex.Single
import ru.dmpolyakov.yandexgallery.IMAGE_PREVIEW_SIZE
import ru.dmpolyakov.yandexgallery.PUBLIC_KEY
import ru.dmpolyakov.yandexgallery.network.models.Folder

class PublicService(private val api: PublicApi) {

    fun getFolder(offset: Int): Single<Folder> {
        return api.getFolder(PUBLIC_KEY, IMAGE_PREVIEW_SIZE, offset)
    }

}