package ui.views.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import domain.models.LetterCell
import ui.views.tools.CellText

@Composable
fun WordsView(correctWord: String, entries: List<String>) {
    Column {
        entries.forEach { word ->
            WordRow(
                value = word.uppercase(),
                correctWord = correctWord,
            )
        }
    }
}

@Composable
fun WordRow(value: String, correctWord: String? = null) {
    Row {
        value.forEachIndexed { i, it ->
            val letterCell = LetterCell.fromEntries(correctWord, it, i)
            CellText(
                value = it.toString(),
                backgroundColor = letterCell.backgroundColor,
                textColor = letterCell.textColor,
            )
        }
    }
}