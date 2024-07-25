package commons.extensions

fun String?.isCharMatchesAtPosition(char: Char, index: Int) =
    this?.get(index)?.uppercase() == char.uppercase()

fun String?.isCharPresent(char: Char) =
    this?.contains(char) ?: false