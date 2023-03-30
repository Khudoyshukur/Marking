package com.example.marking

import android.graphics.Color
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
    val mark: Int

    data class One(
        override val color: Int = Color.RED,
        override val actualMark: Float = 22f,
        override val mark: Int = 1
    ) : Mark

    data class Two(
        override val color: Int = Color.MAGENTA,
        override val actualMark: Float = 28f,
        override val mark: Int = 2
    ) : Mark

    data class Three(
        override val color: Int = Color.BLUE,
        override val actualMark: Float = 34f,
        override val mark: Int = 3
    ) : Mark

    data class Four(
        override val color: Int = Color.LTGRAY,
        override val actualMark: Float = 42f,
        override val mark: Int = 4
    ) : Mark

    data class Five(
        override val color: Int = Color.GREEN,
        override val actualMark: Float = 53f,
        override val mark: Int = 5
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