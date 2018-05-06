package ru.dmpolyakov.yandexgallery.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.ListPopupWindow
import android.view.View
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_gallery.*
import ru.dmpolyakov.yandexgallery.FolderType
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile
import ru.dmpolyakov.yandexgallery.ui.base.BaseActivity
import ru.dmpolyakov.yandexgallery.ui.utils.makePopupWindow
import ru.dmpolyakov.yandexgallery.ui.viewver.ViewverActivity


class GalleryActivity : BaseActivity(), GalleryView {

    private var folderSelector: ListPopupWindow? = null

    override val presenter by lazy {
        GalleryPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        galleryRv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)

        galleryRv.adapter = PreviewRvAdapter(object : PreviewRvAdapterListener {
            override fun onItemClick(selected: Int) {
                presenter.onItemClick(selected)
            }

            override fun loadMoreContent() {
                presenter.loadMoreContent()
            }
        })

        selectedFolder.setOnClickListener {
            presenter.onFolderSelector()
        }

        presenter.attachView(this)
    }

    override fun showFolderSelector(@StringRes res: List<Int>) {
        val data = ArrayList<String>()
        for (@StringRes resId in res) {
            data.add(getString(resId))
        }

        folderSelector = makePopupWindow(
                data = data,
                anchorView = selectedFolder,
                onItemClickAction = Consumer { position ->
                    selectedFolder.text = data[position]
                    presenter.selectedFolder(when (position) {
                        0 -> FolderType.Animals
                        1 -> FolderType.Nature
                        2 -> FolderType.Castles
                        else -> null
                    })
                })
        folderSelector?.show()
    }

    override fun updateFolderTitle(folderType: FolderType) {
        selectedFolder.text = getString(when (folderType) {
            FolderType.Animals -> R.string.funny_animals
            FolderType.Nature -> R.string.amazing_nature
            FolderType.Castles -> R.string.mysterious_castles
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showEmptyState() {
        emptyState.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        emptyState.visibility = View.GONE
    }

    override fun swapContent(images: List<ImageFile>) {
        (galleryRv.adapter as? PreviewRvAdapter)?.swapData(images)
    }

    override fun addContent(images: List<ImageFile>) {
        (galleryRv.adapter as? PreviewRvAdapter)?.addData(images)
    }

    override fun showErrorState(message: Int) {
        errorState.visibility = View.VISIBLE
        errorMessage.setText(message)
    }

    override fun hideErrorState() {
        errorState.visibility = View.GONE
    }

    override fun showViewver(selected: Int) {
        val intent = Intent(this, ViewverActivity::class.java)
        intent.putExtra("selected_item_index", selected)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        folderSelector?.dismiss()
        folderSelector = null
    }
}