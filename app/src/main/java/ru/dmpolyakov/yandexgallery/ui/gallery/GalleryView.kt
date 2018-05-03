package ru.dmpolyakov.yandexgallery.ui.gallery

import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.MvpView
import java.util.*


interface GalleryView : MvpView {
    fun swapContent(images: LinkedList<ImageFile>)
    fun addContent(images: LinkedList<ImageFile>)
    fun showLoading()
    fun hideLoading()
    fun showEmptyState()
    fun hideEmptyState()
}