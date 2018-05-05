package ru.dmpolyakov.yandexgallery.ui.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


abstract class BaseActivity : AppCompatActivity(), MvpView {

    protected abstract val presenter: BasePresenter<*>

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        presenter.viewIsReady()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showMessage(@StringRes message: Int) {
        Toast.makeText(this, resources.getString(message), Toast.LENGTH_SHORT).show()
    }

    override fun back() {
        onBackPressed()
    }
}