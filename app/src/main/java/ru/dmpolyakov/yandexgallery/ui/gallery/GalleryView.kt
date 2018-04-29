package ru.dmpolyakov.yandexgallery.ui.gallery

import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.MvpView


interface GalleryView : MvpView {

    fun showImages(images: List<ImageFile>)
}