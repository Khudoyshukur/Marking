package com.example.marking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marking.databinding.ItemTableStudentBinding
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 3:04 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

class TableViewAdapter(
    private val tooltipContainer: ViewGroup
) : ListAdapter<DataModel, TableViewAdapter.ViewHolder>(DataModel.DIFF_UTIL) {

    private val toolTipsManager by lazy { ToolTipsManager() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTableStudentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return
        holder.bind(model, tooltipContainer, toolTipsManager)

        val lp = holder.itemView.layoutParams as? RecyclerView.LayoutParams
        lp?.let {
            holder.itemView.layoutParams = it.apply {
                it.width = RecyclerView.LayoutParams.MATCH_PARENT
            }
        }
    }

    class ViewHolder(private val binding: ItemTableStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DataModel, tooltipContainer: ViewGroup, toolTipsManager: ToolTipsManager) {
            binding.tvNumber.text = model.number.toString()
            binding.tvName.text = model.studentName

            val adapter = MarksAdapter(
                width = RecyclerView.LayoutParams.WRAP_CONTENT,
                height = RecyclerView.LayoutParams.WRAP_CONTENT,
                onItemClicked = { button, index, _ ->
                    val subjectMark = model.subjectMarks.getOrNull(index)

                    val toolTip = ToolTip.Builder(
                        binding.root.context,
                        button, tooltipContainer,
                        subjectMark?.subject.toString(), ToolTip.POSITION_ABOVE
                    ).build()
                    toolTipsManager.dismissAll()
                    toolTipsManager.show(toolTip)
                }
            ).also { it.submitList(model.subjectMarks.map { it.mark }) }
            binding.rvMarks.adapter = adapter
        }
    }
}