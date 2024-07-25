package domain.models

import androidx.compose.ui.graphics.Color
import commons.extensions.isCharMatchesAtPosition
import commons.extensions.isCharPresent
import ui.theme.MotusColors

sealed class LetterCell(
    val backgroundColor: Color,
    val textColor: Color,
) {
    class Default: LetterCell(
        backgroundColor = MotusColors.defaultCellBgColor,
        textColor = MotusColors.defaultCellTextColor,
    )

    class Valid: LetterCell(
        backgroundColor = MotusColors.successCellBgColor,
        textColor = MotusColors.successCellTextColor,
    )

    class WrongPosition: LetterCell(
        backgroundColor = MotusColors.wrongPositionCellBgColor,
        textColor = MotusColors.wrongPositionCellTextColor,
    )

    companion object {
        fun fromEntries(correctWord: String?, char: Char, positionInWord: Int) =
            when {
                correctWord.isCharMatchesAtPosition(char, positionInWord) -> Valid()
                correctWord.isCharPresent(char) -> WrongPosition()
                else -> Default()
            }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LetterCell

        if (backgroundColor != other.backgroundColor) return false
        if (textColor != other.textColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + textColor.hashCode()
        return result
    }
}