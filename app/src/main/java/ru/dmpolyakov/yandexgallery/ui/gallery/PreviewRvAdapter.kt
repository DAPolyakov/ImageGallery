package ru.dmpolyakov.yandexgallery.ui.gallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile


class PreviewRvAdapter() : RecyclerView.Adapter<PreviewRvAdapter.ViewHolder>() {

    private var items: List<ImageFile> = emptyList()

    fun swapData(newData: List<ImageFile>) {
        items = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fill(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val preview = view.findViewById<ImageView>(R.id.preview)

        fun fill(item: ImageFile) {
            Picasso.get()
                    .load(item.previewUrl)
                    .fit()
                    .centerCrop()
                    .into(preview)
        }
    }
}