package ru.dmpolyakov.yandexgallery.ui.viewver

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING
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

        viewverRv.adapter = ViewverRvAdapter(object : ViewverRvAdapterListener {
            override fun loadMoreContent() {
                presenter.loadMoreContent()
            }
        })

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
                .subscribe({ _ ->
                    presenter.onSnap(getCurrentImageFile())
                })

        icShare.setOnClickListener {
            presenter.onShareImage()
        }

        presenter.attachView(this)
    }

    override fun getCurrentImageFile(): ImageFile {
        val position = (viewverRv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val item = (viewverRv.adapter as ViewverRvAdapter).getItem(position) as ImageFile
        return item
    }

    override fun onStop() {
        intent.putExtra("selected_item_index", getCurrentImageFile().index - 1)
        super.onStop()
    }

    override fun shareUrl(url: String?) {
        url?.let {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, it)
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share_url)))
        }
    }

    override fun updatePositionTitle(position: Int, max: Int?) {
        val title = position.toString() + " ${getString(R.string.of)} " + max
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
