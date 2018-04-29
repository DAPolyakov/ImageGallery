package ru.dmpolyakov.yandexgallery.ui.gallery

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import ru.dmpolyakov.yandexgallery.data.ImageRepository
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class GalleryPresenter : BasePresenter<GalleryView>() {

    private val imageRepository = ImageRepository()
    private var imagesInFolder = 0

    override fun viewIsReady() {
        loadFolder(0)
    }

    private fun loadFolder(offset: Int) {
        imageRepository.getFolder(offset)
                .observeOn(Schedulers.computation())
                .subscribe({ folder, t ->
                    folder?.embedded?.let {
                        imagesInFolder = it.total ?: 0
                        loadImagesFromFolder(Observable.just(it.items), offset)
                    }
                })
    }

    private fun loadImagesFromFolder(observable: Observable<List<ImageFile>>, offset: Int) {
        var imgIndex = 0
        observable
                .concatMapIterable { it }
                .map {
                    ImageFile(
                            name = it.name,
                            created = it.created,
                            previewUrl = it.previewUrl,
                            index = offset + imgIndex++,
                            imagesInFolder = 0)
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ images, t ->
                    getView()?.showImages(images)
                })

    }

}