package ru.dmpolyakov.yandexgallery.ui.base

import android.support.annotation.StringRes

interface MvpView {
    fun showMessage(@StringRes message: Int)
}
