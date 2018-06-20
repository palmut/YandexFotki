package palmut.ru.yandexfotki

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

class ImageViewHolder(card: View) : RecyclerView.ViewHolder(card) {
    val imageView by lazy { itemView.findViewById<ImageView>(R.id.imageView) }
}
