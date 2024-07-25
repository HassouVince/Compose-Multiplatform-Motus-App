package data.datasource.game

import commons.utils.convertJsonToDataClass
import getStringFromLocal
import saveStringOnLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface GameLocalDataSource {
    suspend fun saveGame(game: Game)
    suspend fun getGame(): Game?
}

class GameLocalDataSourceImpl(
    private val coroutineContext: CoroutineContext,
) : GameLocalDataSource {
    override suspend fun saveGame(game: Game) {
        withContext(coroutineContext) {
            val json = game.toJson()
            saveStringOnLocal(
                GAME_KEY,
                json
            )
        }
    }

    override suspend fun getGame(): Game? = withContext(Dispatchers.IO) {
       getStringFromLocal(GAME_KEY)?.convertJsonToDataClass()
    }

    companion object {
        private const val GAME_KEY = "motus_game"
    }
}