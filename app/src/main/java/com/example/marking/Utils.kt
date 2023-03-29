package com.example.marking

import android.content.Context
import android.util.TypedValue
import android.view.View
import kotlin.math.roundToInt

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 2:57 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

fun View.dpToPx(value: Int): Int {
    return context.dpToPx(value)
}

fun Context.dpToPx(value: Int): Int {
    return dpToPxFloat(value).roundToInt()
}

fun Context.dpToPxFloat(value: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(),
        resources.displayMetrics
    )
}