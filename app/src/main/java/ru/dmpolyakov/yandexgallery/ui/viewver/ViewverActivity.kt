package ru.dmpolyakov.yandexgallery.ui.viewver

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING
import android.util.Log
import com.jakewharton.rxbinding2.support.v7.widget.scrollStateChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_viewver.*
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity
import java.util.concurrent.TimeUnit


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
            presenter.loadData(getIntExtra("selected_item_index", 0))
        }

        PagerSnapHelper().attachToRecyclerView(viewverRv)
        viewverRv.scrollStateChanges()
                .filter { state -> state < SCROLL_STATE_SETTLING }
                .sample(250, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ state ->
                    val position = (viewverRv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    presenter.onSnap((viewverRv.adapter as ViewverRvAdapter).getItem(position))
                })

        presenter.attachView(this)
    }

    override fun updatePositionTitle(position: Int, max: Int?) {
        val title = position.toString() + " ${getString(R.string.from)} " + max
        this.position.text = title
    }

    override fun setPosition(position: String) {
        this.position.text = position
    }

    override fun setContent(images: List<ImageFile>) {
        (viewverRv.adapter as ViewverRvAdapter).swapData(images)
    }

    override fun addContent(images: List<ImageFile>) {
        (viewverRv.adapter as ViewverRvAdapter).addData(images)
    }

    override fun focustImage(position: Int) {
        viewverRv.scrollToPosition(position)
        presenter.onSnap((viewverRv.adapter as ViewverRvAdapter).getItem(position))
    }
}
