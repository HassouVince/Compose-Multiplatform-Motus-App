package commons.utils

class NoWordsRemainingException(
    val successCount: Int,
    val failureCount: Int,
): Exception()

class NotFoundException: Exception("Not found")