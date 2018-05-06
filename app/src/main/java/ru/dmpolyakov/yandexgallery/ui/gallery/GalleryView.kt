package ru.dmpolyakov.yandexgallery.ui.gallery

import android.support.annotation.StringRes
import ru.dmpolyakov.yandexgallery.FolderType
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.MvpView
import java.util.*


interface GalleryView : MvpView {
    fun swapContent(images: List<ImageFile>)
    fun addContent(images: List<ImageFile>)

    fun showLoading()
    fun hideLoading()

    fun showEmptyState()
    fun hideEmptyState()

    fun showErrorState(@StringRes message: Int)
    fun hideErrorState()

    fun showViewver(selected: Int)
    fun showFolderSelector(@StringRes res: List<Int>)
    fun updateFolderTitle(folderType: FolderType)
}