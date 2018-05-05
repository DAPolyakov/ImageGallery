package ru.dmpolyakov.yandexgallery.ui.viewver

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_viewver.*
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity


class ViewverActivity : BaseActivity(), ViewverView {

    override val presenter by lazy {
        ViewverPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewver)

        icBack.setOnClickListener {
            presenter.onBack()
        }

        presenter.attachView(this)
    }
}
