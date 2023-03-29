package com.example.marking

import android.graphics.Color
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
    private val onItemClicked: ((anchor: MaterialButton, index: Int, mark: Mark) -> Unit)? = null
) : ListAdapter<Mark, MarksAdapter.ViewHolder>(Mark.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MaterialButton(parent.context)
        view.strokeWidth = parent.context.dpToPx(1)
        view.strokeColor = parent.context.getColorStateList(R.color.black)
        view.setTextColor(Color.WHITE)
        view.gravity = Gravity.CENTER
        view.layoutParams = RecyclerView.LayoutParams(width, height)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return
        holder.bind(model)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(holder.textView, position, model)
        }
    }

    class ViewHolder(val textView: MaterialButton) : RecyclerView.ViewHolder(textView) {
        fun bind(model: Mark) {
            textView.setBackgroundColor(model.color)
            textView.text = model.mark.toString()
        }
    }
}