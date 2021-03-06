package ru.dmpolyakov.yandexgallery.ui.utils

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.ListPopupWindow
import android.view.View
import android.widget.ArrayAdapter
import io.reactivex.functions.Consumer
import ru.dmpolyakov.yandexgallery.R


fun Context.makePopupWindow(
        data: List<String>,
        anchorView: View,
        @LayoutRes itemLayout: Int = R.layout.item_dropdown_folder,
        onItemClickAction: Consumer<Int>): ListPopupWindow {

    val adapter = ArrayAdapter<String>(this, itemLayout)
    adapter.addAll(data)
    val window = ListPopupWindow(this)
    window.setAdapter(adapter)
    window.setOnItemClickListener { parent, spinnerItem, position, id ->
        window.dismiss()
        onItemClickAction.accept(position)
    }
    window.anchorView = anchorView
    window.verticalOffset = -(anchorView.height / 2)

//    window.
//    window.
//    window.setContentWidth(WRAP_CONTENT)
//    window.width = WRAP_CONTENT

    return window
}
