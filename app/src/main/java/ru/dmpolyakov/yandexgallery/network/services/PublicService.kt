package ru.dmpolyakov.yandexgallery.network.services

import io.reactivex.Single
import ru.dmpolyakov.yandexgallery.*
import ru.dmpolyakov.yandexgallery.network.models.Folder

class PublicService(private val api: PublicApi) {

    fun getFolder(folderType: FolderType, offset: Int): Single<Folder> {

        val publicKey = when (folderType) {
            FolderType.Animals -> PUBLIC_KEY_ANIMALS
            FolderType.Nature -> PUBLIC_KEY_NATURE
            FolderType.Castles -> PUBLIC_KEY_CASTLES
        }

        return api.getFolder(publicKey, IMAGES_LIMIT, IMAGE_PREVIEW_SIZE, offset)
    }

}