package ru.dmpolyakov.yandexgallery.ui.base

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>{

    private var view : V? = null

    override fun attachView(mvpView: V) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {
    }

    fun getView() : V?{
        return view
    }

    protected fun isViewAttached() : Boolean{
        return view != null
    }

}