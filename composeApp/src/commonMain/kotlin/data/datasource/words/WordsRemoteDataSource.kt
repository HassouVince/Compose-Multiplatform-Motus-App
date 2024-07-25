package data.datasource.words

import kotlinx.coroutines.withContext
import readTextFromUrl
import kotlin.coroutines.CoroutineContext

interface WordsRemoteDataSource {
    suspend fun fetchWords(): List<Word>
}

class WordsRemoteDataSourceImpl(
    private val coroutineContext: CoroutineContext,
) : WordsRemoteDataSource {

    override suspend fun fetchWords(): List<Word> = withContext(coroutineContext) {
        readTextFromUrl(WORDS_URL)
            ?.split(SEPARATOR)
            ?.map { Word(it.trim()) }
            .orEmpty()
    }

    companion object {
        const val WORDS_URL = "https://pastes.io/raw/iys4katchh"
        const val SEPARATOR = "\n"
    }
}