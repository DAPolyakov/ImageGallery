package ru.dmpolyakov.yandexgallery.ui.gallery

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.dmpolyakov.yandexgallery.data.ImageRepository
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter
import java.util.*


class GalleryPresenter : BasePresenter<GalleryView>() {

    private val imageRepository = ImageRepository()

    private var maxImageLoaded = 0
    private var imagesInFolder = 0

    private var isLoading = false

    override fun viewIsReady() {
        loadFolder(0)
    }

    fun onItemClick(item: ImageFile) {
        getView()?.showMessage(item.index.toString())
    }

    fun loadMoreContent() {
        if (maxImageLoaded < imagesInFolder) {
            loadFolder(maxImageLoaded)
        }
    }

    private fun loadFolder(offset: Int) {
        if (!isLoading) {
            setLoadingState(true)
            imageRepository.getFolder(offset)
                    .observeOn(Schedulers.computation())
                    .subscribe({ folder, t ->
                        folder?.embedded?.let {
                            imagesInFolder = it.total ?: 0
                            loadImagesFromFolder(Observable.just(it.items), offset)
                        }
                    })
        }
    }

    private fun setLoadingState(loading: Boolean) {
        isLoading = loading
        if (loading) {
            getView()?.showLoading()
        } else {
            getView()?.hideLoading()
        }
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
                            index = offset + ++imgIndex)
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ images, t ->
                    images.lastOrNull()?.index?.let { maxImageLoaded = it }
                    getView()?.addContent(LinkedList(images))
                    setLoadingState(false)

                    if (maxImageLoaded == 0){
                        getView()?.showEmptyState()
                    } else {
                        getView()?.hideEmptyState()
                    }
                })

    }

}