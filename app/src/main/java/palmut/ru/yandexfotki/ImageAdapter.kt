package palmut.ru.yandexfotki

import android.net.Uri
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import palmut.ru.yandexfotki.api.Entry

private val diffCallback = object : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry) =
            oldItem == newItem
}

class ImageAdapter : ListAdapter<Entry, ImageViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ImageViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item: Entry = getItem(position)
        item.img?.get("orig")?.also { image ->
            Picasso.get().load(Uri.parse(image.href)).into(holder.imageView)
        }
    }
}
