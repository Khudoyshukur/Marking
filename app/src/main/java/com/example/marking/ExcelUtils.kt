package com.example.marking

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

/**
 * Created by: androdev
 * Date: 27-03-2023
 * Time: 2:34 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

object ExcelUtils {
    fun extractDataModelsFrom(excelInputStream: InputStream) = buildList {
        val xlWb = WorkbookFactory.create(excelInputStream)
        val sheet = xlWb.getSheetAt(0)

        val dataFormatter = DataFormatter()

        var isFirst = true
        sheet.rowIterator().forEach { currentRow ->
            if (!isFirst) {
                val number = dataFormatter.formatCellValue(currentRow.getCell(0)).toInt()
                val studentName = dataFormatter.formatCellValue(currentRow.getCell(1)) ?: ""

                val subjectMarks = arrayListOf<SubjectMark>()
                for (index in 2 until currentRow.lastCellNum) {
                    val subjectName =
                        dataFormatter.formatCellValue(sheet.getRow(0).getCell(index)) ?: ""
                    val subjectMark = currentRow.getCell(index).numericCellValue.toFloat()
                    subjectMarks.add(SubjectMark(subjectName, subjectMark.toMark()))
                }

                this.add(
                    DataModel(
                        number = number,
                        studentName = studentName,
                        subjectMarks = subjectMarks
                    )
                )
            } else {
                isFirst = false
            }
        }
    }

    private fun Float.toMark() = when {
        this <= 22f -> Mark.One(
            actualMark = this
        )
        this in 23f..28f -> Mark.Two(
            actualMark = this
        )
        this in 29f..41f -> Mark.Three(
            actualMark = this
        )
        this in 42f..52f -> Mark.Four(
            actualMark = this
        )
        else -> Mark.Five(
            actualMark = this
        )
    }
}