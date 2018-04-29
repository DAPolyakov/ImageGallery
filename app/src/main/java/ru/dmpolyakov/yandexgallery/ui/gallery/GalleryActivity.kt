package ru.dmpolyakov.yandexgallery.ui.gallery

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_gallery.*
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class GalleryActivity : BaseActivity(), GalleryView {

    override val presenter: BasePresenter<GalleryView> by lazy {
        GalleryPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        galleryRv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        galleryRv.adapter = PreviewRvAdapter()

        presenter.attachView(this)
    }

    override fun showImages(images: List<ImageFile>) {
        (galleryRv.adapter as? PreviewRvAdapter)?.swapData(images)
    }
}