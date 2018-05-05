package ru.dmpolyakov.yandexgallery.ui.viewver

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_viewver.*
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity


class ViewverActivity : BaseActivity(), ViewverView {

    override val presenter by lazy {
        ViewverPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewver)

        viewverRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewverRv.adapter = ViewverRvAdapter()

        icBack.setOnClickListener {
            presenter.onBack()
        }

        intent.apply {
            presenter.loadData(getIntExtra("selected_item_index", 0), getParcelableArrayListExtra("images"))
        }

        presenter.attachView(this)
    }

    override fun setContent(images: List<ImageFile>) {
        (viewverRv.adapter as ViewverRvAdapter).swapData(images)
    }

    override fun focustImage(position: Int){
        viewverRv.scrollToPosition(position)
    }
}
