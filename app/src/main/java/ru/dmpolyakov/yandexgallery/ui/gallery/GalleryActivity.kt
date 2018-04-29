package ru.dmpolyakov.yandexgallery.ui.gallery

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.dmpolyakov.yandexgallery.data.ImageRepository
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity
import ru.dmpolyakov.yandexgallery.ui.base.BasePresenter


class GalleryActivity : BaseActivity(), GalleryView {

    override val presenter: BasePresenter<GalleryView> by lazy {
        GalleryPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImageRepository().getImages(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({images, t ->
                    val a = 1
                    val b = a + 9
                })

        presenter.attachView(this)
    }
}