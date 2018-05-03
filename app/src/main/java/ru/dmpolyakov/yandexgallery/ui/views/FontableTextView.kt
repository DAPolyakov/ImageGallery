package ru.dmpolyakov.yandexgallery.ui.views

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import ru.dmpolyakov.yandexgallery.R
import java.lang.ref.SoftReference
import java.util.*


class FontableTextView : AppCompatTextView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let {
            setCustomFont(this, context, it,
                    R.styleable.FontableTextView,
                    R.styleable.FontableTextView_customFont)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let {
            setCustomFont(this, context, attrs,
                    R.styleable.FontableTextView,
                    R.styleable.FontableTextView_customFont)
        }
    }

    fun setCustomFont(view: View, ctx: Context, attrs: AttributeSet, attributeSet: IntArray, fontId: Int) {
        val a = ctx.obtainStyledAttributes(attrs, attributeSet)
        val customFont = a.getString(fontId)
        setCustomFont(view, ctx, customFont)
        a.recycle()
    }

    private fun setCustomFont(textView: View, ctx: Context, asset: String): Boolean {
        if (TextUtils.isEmpty(asset))
            return false
        var tf: Typeface? = null
        try {
            tf = getFont(ctx, asset)
            if (textView is TextView) {
                textView.typeface = tf
            }
        } catch (e: Exception) {
            Log.e("dima", "Could not get typeface: " + asset, e)
            return false
        }
        return true
    }

    private val fontCache: Hashtable<String, SoftReference<Typeface>> = Hashtable()

    private fun getFont(c: Context, name: String): Typeface {
        synchronized(fontCache) {
            if (fontCache[name] != null) {
                val ref = fontCache[name]
                if (ref?.get() != null) {
                    return ref.get()!!
                }
            }
            val typeface = Typeface.createFromAsset(
                    c.assets,
                    "fonts/" + name
            )
            fontCache.put(name, SoftReference(typeface))
            return typeface
        }
    }
}
