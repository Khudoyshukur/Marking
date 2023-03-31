package com.example.marking

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 2:54 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

class MarksAdapter(
    private val width: Int,
    private val height: Int,
    private val writeRange: Boolean = false,
    private val onItemClicked: ((anchor: MaterialButton, index: Int, mark: Mark) -> Unit)? = null
) : ListAdapter<Mark, MarksAdapter.ViewHolder>(Mark.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MaterialButton(parent.context)
        view.strokeWidth = parent.context.dpToPx(1)
        view.strokeColor = parent.context.getColorStateList(R.color.black)
        view.setTextColor(Color.WHITE)
        view.gravity = Gravity.CENTER
        view.layoutParams = RecyclerView.LayoutParams(width, height)
        view.maxLines = 1
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return
        holder.bind(model, writeRange)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(holder.textView, position, model)
        }
    }

    class ViewHolder(val textView: MaterialButton) : RecyclerView.ViewHolder(textView) {
        fun bind(model: Mark, writeRange: Boolean) {
            textView.setBackgroundColor(model.color)

            textView.text =if (writeRange) {
                if (model.markRange.upper == Int.MAX_VALUE) {
                    "${model.markRange.lower}+"
                } else {
                    "${model.markRange.lower}-${model.markRange.upper}"
                }
            } else {
                model.actualMark.toString()
            }
        }
    }
}