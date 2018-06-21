package palmut.ru.yandexfotki

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import palmut.ru.yandexfotki.api.Entry
import kotlin.math.abs

private val diffCallback = object : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry) =
            oldItem == newItem
}

class ImageAdapter(private val screenWidth: Int) : ListAdapter<Entry, ImageViewHolder>(diffCallback) {

    private val emptyImage = ColorDrawable(Color.LTGRAY)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ImageViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        findClosestImageHref(getItem(position))?.also { href ->
            Picasso.get().load(Uri.parse(href)).into(holder.imageView)
        } ?: holder.imageView.also {
            it.setImageDrawable(emptyImage)
        }
    }

    private fun findClosestImageHref(item: Entry): String? {
        var selected = Pair<Int, String?>(Int.MAX_VALUE, null)
        item.img?.forEach { (_, image) ->
            val diff = abs(image.width - screenWidth)
            if (selected.first > diff) {
                selected = Pair(diff, image.href)
            }
        }
        return selected.second
    }
}
