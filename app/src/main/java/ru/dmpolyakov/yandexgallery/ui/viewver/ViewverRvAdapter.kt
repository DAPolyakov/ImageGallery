package ru.dmpolyakov.yandexgallery.ui.viewver

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.dmpolyakov.yandexgallery.R
import ru.dmpolyakov.yandexgallery.network.models.ImageFile


interface ViewverRvAdapterListener {
    fun loadMoreContent()
}

class ViewverRvAdapter(val listener: ViewverRvAdapterListener) : RecyclerView.Adapter<ViewverRvAdapter.ViewHolder>() {

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

    fun getItem(position: Int): ImageFile? {
        return if (items.size <= position) {
            null
        } else {
            items[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false) as ImageView
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

    inner class ViewHolder(val view: ImageView) : RecyclerView.ViewHolder(view) {
        fun fill(item: ImageFile) {
            Picasso.get()
                    .load(item.previewUrl)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.placeholder)
                    .into(view)
        }
    }
}