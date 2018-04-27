package ru.dmpolyakov.yandexgallery.ui.base

import android.os.Bundle
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

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}