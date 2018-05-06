package ru.dmpolyakov.yandexgallery.ui.viewver

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.R.id.position
import ru.dmpolyakov.yandexgallery.data.ImageRepository
import ru.dmpolyakov.yandexgallery.data.ImageRepository.loadMoreContent
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class ViewverPresenter : BasePresenter<ViewverView>() {

    private var initialPosition = 0
    private var isLoading = false

    override fun viewIsReady() {
        val list = ImageRepository.getImages()
        if (list.isEmpty()) {
            loadMoreContent()
        } else {
            getView()?.setContent(list)
            getView()?.focustImage(initialPosition)
        }
    }

    fun loadMoreContent() {
        if (isLoading) return

//        getView()?.hideErrorState()
        setLoadingState(true)

        ImageRepository.loadMoreContent()?.apply {
            observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate { setLoadingState(false) }
                    .subscribe({
                        getView()?.addContent(it)
                    }, {
//                        showError(R.string.error_network)
                    })
        } ?: setLoadingState(false)
    }

    fun onSnap(image: ImageFile?) {
        image?.let {
            updatePositionTitle(it.index)
        }
    }

    private fun setLoadingState(loading : Boolean){
        isLoading = loading
    }

    private fun updatePositionTitle(position : Int){
        getView()?.updatePositionTitle(position, ImageRepository.getImageInFolder())
    }

    fun loadData(selected: Int) {
        initialPosition = selected
    }

    fun onBack() {
        getView()?.back()
    }

}