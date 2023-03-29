package com.example.marking

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

        var isFirst = true
        sheet.rowIterator().forEach { currentRow ->
            if (!isFirst) {
                val number = currentRow.getCell(0).numericCellValue.toInt()
                val studentName = currentRow.getCell(1).stringCellValue

                val subjectMarks = arrayListOf<SubjectMark>()
                for (index in 2 until currentRow.lastCellNum) {
                    val subjectName = sheet.getRow(0).getCell(index).stringCellValue
                    val subjectMark = currentRow.getCell(index).numericCellValue.toInt()
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

    private fun Int.toMark() = when (this) {
        1 -> Mark.One()
        2 -> Mark.Two()
        3 -> Mark.Three()
        4 -> Mark.Four()
        5 -> Mark.Five()
        else -> throw IllegalArgumentException("Unknown mark")
    }
}