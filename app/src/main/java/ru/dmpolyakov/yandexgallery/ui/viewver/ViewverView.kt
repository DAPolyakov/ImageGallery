package ru.dmpolyakov.yandexgallery.ui.viewver

import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.MvpView


interface ViewverView : MvpView {

    fun setContent(images: List<ImageFile>)
    fun addContent(images: List<ImageFile>)
    fun focustImage(position: Int)
    fun setPosition(position: String)

    fun updatePositionTitle(position: Int, max: Int?)
}