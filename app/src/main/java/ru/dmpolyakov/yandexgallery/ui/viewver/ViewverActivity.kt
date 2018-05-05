package ru.dmpolyakov.yandexgallery.ui.viewver

import android.os.Bundle
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity


class ViewverActivity : BaseActivity() {

    override val presenter by lazy {
        ViewverPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewver)
    }
}
