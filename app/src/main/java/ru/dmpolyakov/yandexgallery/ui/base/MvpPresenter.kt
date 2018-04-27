package ru.dmpolyakov.yandexgallery.ui.base

interface MvpPresenter<in V : MvpView> {
    fun attachView(mvpView: V)
    fun viewIsReady()
    fun detachView()
    fun destroy()
}