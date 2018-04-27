package ru.dmpolyakov.yandexgallery.ui.gallery

import android.os.Bundle
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class GalleryActivity : BaseActivity(), GalleryView {

    override val presenter: BasePresenter<GalleryView> by lazy {
        GalleryPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attachView(this)
    }
}