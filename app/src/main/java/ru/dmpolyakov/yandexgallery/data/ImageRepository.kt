package ru.dmpolyakov.yandexgallery.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.dmpolyakov.yandexgallery.network.NetworkModule
import ru.dmpolyakov.yandexgallery.network.models.Folder
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import java.util.*


object ImageRepository {

    private val publicService = NetworkModule.publicService

    private val images = ArrayList<ImageFile>()
    private var imagesInFolder: Int? = null
    private var maxImageLoaded = 0
    private var folderTitle = ""

    fun getImageInFolder(): Int? {
        return imagesInFolder
    }

    fun getImages(): List<ImageFile> {
        return images
    }

    fun loadMoreContent(): Observable<List<ImageFile>>? {
        imagesInFolder?.let {
            return if (maxImageLoaded < it) {
                loadImages(maxImageLoaded)
            } else null
        } ?: return loadImages(maxImageLoaded)
    }

    private fun loadImages(offset: Int): Observable<List<ImageFile>> {
        return getFolder(offset)
                .doOnSuccess {
                    imagesInFolder = it.embedded?.total ?: 0
                }
                .flatMapObservable { folder: Folder ->
                    getImagesFromFolder(Observable.just(folder.embedded?.items), offset)
                }
    }

    private fun getFolder(offset: Int): Single<Folder> {
        return publicService.getFolder(offset)
    }

    private fun getImagesFromFolder(observable: Observable<List<ImageFile>>, offset: Int): Observable<List<ImageFile>> {
        var imgIndex = 0
        return observable
                .observeOn(Schedulers.io())
                .concatMapIterable { it }
                .map {
                    ImageFile(
                            name = it.name,
                            created = it.created,
                            previewUrl = it.previewUrl,
                            index = offset + ++imgIndex)
                }
                .toList()
                .doOnSuccess { loadedImages ->
                    loadedImages.lastOrNull()?.index?.let { maxImageLoaded = it }
                    images.addAll(loadedImages)
                }
                .toObservable()
    }
}