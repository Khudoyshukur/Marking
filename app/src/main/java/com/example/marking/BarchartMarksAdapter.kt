package com.example.marking

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marking.databinding.ItemBarcharMarkBinding
import com.google.android.material.button.MaterialButton

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 4:53 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

class BarchartMarksAdapter(
    private val onItemClicked: ((anchor: MaterialButton, index: Int, mark: Mark) -> Unit)? = null
) : ListAdapter<Mark, BarchartMarksAdapter.ViewHolder>(Mark.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBarcharMarkBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return
        holder.bind(model)

        holder.binding.btnMark.setOnClickListener {
            onItemClicked?.invoke(holder.binding.btnMark, position, model)
        }
    }

    class ViewHolder(val binding: ItemBarcharMarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Mark) {
            binding.btnMark.setBackgroundColor(model.color)
            binding.btnMark.text = model.mark.toString()

            binding.btnMark.layoutParams = LinearLayout.LayoutParams(
                binding.root.context.dpToPx(40),
                model.mark * binding.root.context.dpToPx(40)
            ).also { it.gravity = Gravity.BOTTOM }
        }
    }
}