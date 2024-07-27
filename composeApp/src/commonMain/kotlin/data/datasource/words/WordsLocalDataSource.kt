package data.datasource.words

import data.datasource.SqlDriverFactory
import data.datasource.game.Game
import fr.naddev.composemultiplatformmotus.AppDatabase
import fr.naddev.composemultiplatformmotus.Words

interface WordsLocalDataSource {
    fun saveWords(words: List<Word>)
    fun getUnusedWord(): Word?
    fun getSameWordOrNull(value: String): Word?
    fun updateWord(value: String)
    fun getWordsCount(): Long
    fun removeAllWords()
}

class WordsLocalDataSourceImpl(
    sqlDriverFactory: SqlDriverFactory,
): WordsLocalDataSource {
    private val database = AppDatabase(sqlDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    override fun getUnusedWord(): Word? =
        dbQuery.selectUnusedWord()
            .executeAsOneOrNull()
            ?.let(::mapToWordOrNull)

    override fun getSameWordOrNull(value: String): Word? =
        dbQuery.selectMatchingWord(value.uppercase())
            .executeAsOneOrNull()
            ?.let(::mapToWordOrNull)

    override fun removeAllWords() =
        dbQuery.removeAllWords()

    override fun saveWords(words: List<Word>) {
        dbQuery.transaction {
            dbQuery.removeAllWords()
            words.forEach { word ->
                dbQuery.insertWord(
                    value_ = word.value,
                    used = word.used,
                )
            }
        }
    }

    override fun updateWord(value: String) =
        dbQuery.setWordByValue(value)

    override fun getWordsCount() =
        dbQuery.getWordsCount().executeAsOneOrNull() ?: 0

    private fun mapToWordOrNull(
        entity: Words?
    ): Word? {
        return entity?.let {
            Word(
                value = it.value_,
                used = it.used ?: false,
            )
        }
    }
}