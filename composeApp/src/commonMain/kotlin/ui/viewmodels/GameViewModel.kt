package ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commons.extensions.onError
import commons.extensions.onSuccess
import data.datasource.game.Game
import data.datasource.game.validateEntry
import domain.models.EntryState
import domain.models.GameState
import domain.models.GameState.Companion.mapToGameState
import domain.usecases.game.ClearGameUseCase
import domain.usecases.game.GetGameStateUseCase
import domain.usecases.game.SaveGameUseCase
import domain.usecases.game.UpdateGameStateUseCase
import domain.usecases.game.VerifyWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameViewModel : ViewModel(), KoinComponent {

    private val getGameStateUseCase: GetGameStateUseCase by inject()
    private val updateGameStateUseCase: UpdateGameStateUseCase by inject()
    private val saveGameUseCase: SaveGameUseCase by inject()
    private val verifyWordUseCase: VerifyWordUseCase by inject()
    private val clearGameUseCase: ClearGameUseCase by inject()

    private val _gameState = MutableStateFlow<GameState>(GameState.Default())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    var entryState = mutableStateOf<EntryState>(EntryState.Success())

    fun fetchGameState() = viewModelScope.launch {
        _gameState.value = GameState.Loading()
        getGameStateUseCase().collect {
            _gameState.value = it
        }
    }

    fun continueGame(lastGameState: GameState) = viewModelScope.launch {
        getCurrentGameOrNull()?.let {
            _gameState.value = GameState.Loading()
            updateGameStateUseCase(it, lastGameState).collect{ newState ->
                entryState.value = EntryState.Success(null)
                _gameState.value = newState
            }
        }
    }

    fun restartGame() = viewModelScope.launch {
        _gameState.value = GameState.Loading()
        clearGameUseCase().collect{ res ->
            res.onSuccess { fetchGameState() }
                .onError {  _gameState.value = res.mapToGameState() }
        }
    }

    fun validateEntry(entry: String) = viewModelScope.launch {
        getCurrentGameOrNull()?.let { game ->
            verifyWordUseCase(game, entry).let { state ->
                entryState.value = state
                game.validateEntry(state).let { newState ->
                    saveGame(newState.game ?: game)
                    if(newState !is GameState.Playing)
                        _gameState.value = newState
                }
            }
        }
    }

    private fun getCurrentGameOrNull() = _gameState.value.game

    private suspend fun saveGame(game: Game, onSuccess: (() -> Unit)? = null) {
        saveGameUseCase(game).collect { res ->
            res.onSuccess { onSuccess?.let { it() } }
                .onError { t -> _gameState.value = GameState.Error(t) }
        }
    }
}