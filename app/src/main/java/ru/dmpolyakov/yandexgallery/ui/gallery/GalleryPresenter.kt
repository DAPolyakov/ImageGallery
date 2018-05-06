package ru.dmpolyakov.yandexgallery.ui.gallery

import android.support.annotation.StringRes
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.data.ImageRepository
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class GalleryPresenter : BasePresenter<GalleryView>() {

    private var isLoading = false

    override fun viewIsReady() {
        val list = ImageRepository.getImages()
        if (list.isEmpty()) {
            loadMoreContent()
        } else {
            getView()?.swapContent(list)
        }
    }

    fun onItemClick(selected: Int) {
        getView()?.showViewver(selected)
    }

    fun loadMoreContent() {
        if (isLoading) return

        getView()?.hideEmptyState()
        getView()?.hideErrorState()
        setLoadingState(true)

        ImageRepository.loadMoreContent()?.apply {
            observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate { setLoadingState(false) }
                    .subscribe({
                        getView()?.addContent(it)
                    }, {
                        showError(R.string.error_network)
                    })
        } ?: setLoadingState(false)

    }

    private fun showError(@StringRes res: Int) {
        if (ImageRepository.getMaxImageLoaded() == 0) {
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
        if (ImageRepository.getMaxImageLoaded() == 0) {
            getView()?.showEmptyState()
        } else {
            getView()?.hideEmptyState()
        }
    }

}