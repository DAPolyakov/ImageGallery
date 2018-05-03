package ru.dmpolyakov.yandexgallery.ui.gallery

import android.support.annotation.StringRes
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

    fun showErrorState(@StringRes message: Int)
    fun hideErrorState()
}