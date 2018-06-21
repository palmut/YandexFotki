package palmut.ru.yandexfotki

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import kotlin.math.abs

class ScaleDecoration : RecyclerView.ItemDecoration() {

    private val scaleMax = 1.0f
    private val scaleMin = 0.9f

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        (0 until parent.childCount).map { parent.getChildAt(it) }.forEach { child ->
            val centerY = child.y + child.height / 2
            val offset = 2 * abs(parent.height / 2 - centerY) / parent.height
            val scale = scaleMax - (scaleMax - scaleMin) * offset
            child.scaleY = scale
            child.scaleX = scale
        }
    }
}