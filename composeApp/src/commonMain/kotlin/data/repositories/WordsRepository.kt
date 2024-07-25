package data.repositories

import commons.extensions.mapInResult
import commons.utils.NoWordsRemainingException
import commons.utils.AppResult
import commons.utils.NotFoundException
import data.datasource.game.Game
import data.datasource.words.Word
import data.datasource.words.WordsLocalDataSource
import data.datasource.words.WordsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WordsRepository {
    suspend fun getWordToPlay(game: Game? = null): Flow<AppResult<Word>>
    suspend fun removeAll(): Flow<AppResult<Unit>>
    fun getSameWordOrNull(game: Game, value: String): Word?
}

class WordsRepositoryImpl(
    private val remoteSource: WordsRemoteDataSource,
    private val localSource: WordsLocalDataSource,
) : WordsRepository {
    override suspend fun getWordToPlay(game: Game?): Flow<AppResult<Word>> {
        return if(localSource.getWordsCount() > 0) {
            game?.word?.let {
                localSource.updateWord(it.value)
            }
            getUnusedWordFromLocalOrNull()
                ?: throw NoWordsRemainingException(
                    successCount = game?.winCount ?: 0,
                    failureCount = game?.loseCount ?: 0,
                )
        } else {
            getWordsFromRemote().let {
                localSource.saveWords(it)
                getUnusedWordFromLocalOrNull() ?: throw NotFoundException()
            }
        }
    }

    override suspend fun removeAll(): Flow<AppResult<Unit>> =
        flow {
            emit(
                localSource.removeAllWords()
            )
        }.mapInResult()

    override fun getSameWordOrNull(game: Game, value: String): Word? {
        return localSource.getSameWordOrNull(value, game)
    }

    private suspend fun getUnusedWordFromLocalOrNull() =
        localSource.getUnusedWord()?.let {
            flow{
                emit(it)
            }
        }?.mapInResult()

    private suspend fun getWordsFromRemote() =
        remoteSource.fetchWords().shuffled()
}