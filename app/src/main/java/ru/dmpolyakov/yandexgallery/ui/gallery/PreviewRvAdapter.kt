package ru.dmpolyakov.yandexgallery.ui.gallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile

interface PreviewRvAdapterListener {
    fun onItemClick(items: List<ImageFile>, selected: Int)
    fun loadMoreContent()
}

class PreviewRvAdapter(val listener: PreviewRvAdapterListener) : RecyclerView.Adapter<PreviewRvAdapter.ViewHolder>() {

    private var items: ArrayList<ImageFile> = ArrayList()

    fun swapData(newData: List<ImageFile>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(newData: List<ImageFile>) {
        items.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fill(items[position])

        if (position >= (itemCount * 0.7)) {
            listener.loadMoreContent()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val preview = view.findViewById<ImageView>(R.id.preview)

        init {
            preview.setOnClickListener {
                listener.onItemClick(items, adapterPosition)
            }
        }

        fun fill(item: ImageFile) {
            Picasso.get()
                    .load(item.previewUrl)
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(preview)
        }
    }
}