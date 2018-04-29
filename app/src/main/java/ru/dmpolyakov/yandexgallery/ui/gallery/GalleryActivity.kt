package ru.dmpolyakov.yandexgallery.ui.gallery

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_gallery.*
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity
import java.util.*


class GalleryActivity : BaseActivity(), GalleryView {

    override val presenter by lazy {
        GalleryPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        galleryRv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)

        galleryRv.adapter = PreviewRvAdapter(object : PreviewRvAdapterListener {
            override fun onItemClick(item: ImageFile) {
                presenter.onItemClick(item)
            }

            override fun loadMoreContent() {
                presenter.loadMoreContent()
            }
        })

        presenter.attachView(this)
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading(){
        progressBar.visibility = View.GONE
    }

    override fun swapContent(images: LinkedList<ImageFile>) {
        (galleryRv.adapter as? PreviewRvAdapter)?.swapData(images)
    }

    override fun addContent(images: LinkedList<ImageFile>) {
        (galleryRv.adapter as? PreviewRvAdapter)?.addData(images)
    }
}