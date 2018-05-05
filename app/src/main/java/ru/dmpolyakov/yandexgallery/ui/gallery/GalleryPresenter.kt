package ru.dmpolyakov.yandexgallery.ui.gallery

import android.support.annotation.StringRes
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.dmpolyakov.yandexgallery.R
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

    fun onItemClick(items: List<ImageFile>, selected: Int) {
//        Log.wtf("gallery", item.index.toString())
        getView()?.showViewver(items, selected)
    }

    fun loadMoreContent() {
        if (maxImageLoaded < imagesInFolder) {
            loadFolder(maxImageLoaded)
        }
    }

    private fun loadFolder(offset: Int) {
        if (!isLoading) {
            getView()?.hideEmptyState()
            getView()?.hideErrorState()
            setLoadingState(true)

            imageRepository.getFolder(offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ folder, t ->
                        if (t != null) {
                            showError(R.string.error_network)
                            setLoadingState(false)
                        } else {
                            folder?.embedded?.let {
                                imagesInFolder = it.total ?: 0
                                loadImagesFromFolder(Observable.just(it.items), offset)
                            }
                        }
                    })
        }
    }

    private fun showError(@StringRes res: Int) {
        if (maxImageLoaded == 0) {
            getView()?.showErrorState(res)
        } else {
            getView()?.showMessage(res)
        }
    }

    private fun setLoadingState(loading: Boolean) {
        isLoading = loading
        if (loading) {
            getView()?.showLoading()
        } else {
            getView()?.hideLoading()
            checkEmptyState()
        }
    }

    private fun checkEmptyState() {
        if (maxImageLoaded == 0) {
            getView()?.showEmptyState()
        } else {
            getView()?.hideEmptyState()
        }
    }

    private fun loadImagesFromFolder(observable: Observable<List<ImageFile>>, offset: Int) {
        var imgIndex = 0
        observable
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ images, t ->
                    if (t != null) {
                        showError(R.string.error_undefined)
                    } else {
                        images.lastOrNull()?.index?.let { maxImageLoaded = it }
                        getView()?.addContent(LinkedList(images))

                        if (maxImageLoaded == 0) {
                            getView()?.showEmptyState()
                        }
                    }
                    setLoadingState(false)
                })

    }

}