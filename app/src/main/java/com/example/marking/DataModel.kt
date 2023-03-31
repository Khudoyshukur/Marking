package com.example.marking

import android.graphics.Color
import android.util.Range
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 2:33 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

data class DataModel(
    val number: Int,
    val studentName: String,
    val subjectMarks: List<SubjectMark>
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<DataModel>() {
            override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

data class SubjectMark(
    val subject: String,
    val mark: Mark
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SubjectMark>() {
            override fun areItemsTheSame(oldItem: SubjectMark, newItem: SubjectMark): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SubjectMark, newItem: SubjectMark): Boolean {
                return oldItem == newItem
            }
        }
    }
}

sealed interface Mark {
    val color: Int
    val actualMark: Float
    val markRange: Range<Int>

    data class One(
        override val color: Int = Color.RED,
        override val actualMark: Float = 22f,
        override val markRange: Range<Int> = Range(1, 22)
    ) : Mark

    data class Two(
        override val color: Int = Color.MAGENTA,
        override val actualMark: Float = 28f,
        override val markRange: Range<Int> = Range(23, 28)
    ) : Mark

    data class Three(
        override val color: Int = Color.BLUE,
        override val actualMark: Float = 34f,
        override val markRange: Range<Int> = Range(29, 34)
    ) : Mark

    data class Four(
        override val color: Int = Color.LTGRAY,
        override val actualMark: Float = 42f,
        override val markRange: Range<Int> = Range(35, 42)
    ) : Mark

    data class Five(
        override val color: Int = Color.GREEN,
        override val actualMark: Float = 53f,
        override val markRange: Range<Int> = Range(43, Int.MAX_VALUE)
    ) : Mark

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Mark>() {
            override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean {
                return oldItem == newItem
            }
        }
    }
}