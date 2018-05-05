package ru.dmpolyakov.yandexgallery.ui.viewver

import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class ViewverPresenter : BasePresenter<ViewverView>() {

    private var initialPosition = 0
    private var images: List<ImageFile> = emptyList()

    override fun viewIsReady() {
        getView()?.setContent(images)
        getView()?.focustImage(initialPosition)
    }

    fun loadData(selected: Int, images: List<ImageFile>) {
        initialPosition = selected
        this.images = images
    }

    fun onBack() {
        getView()?.back()
    }

}