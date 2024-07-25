package ui.views.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import composemultiplatformmotus.composeapp.generated.resources.Res
import composemultiplatformmotus.composeapp.generated.resources.failure_default_message
import composemultiplatformmotus.composeapp.generated.resources.failure_message
import composemultiplatformmotus.composeapp.generated.resources.game_completed
import composemultiplatformmotus.composeapp.generated.resources.success_message
import domain.models.EntryState
import domain.models.GameState
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import ui.views.tools.ErrorView
import ui.views.tools.Loader

@Composable
fun GameScreen(
    gameState: StateFlow<GameState>,
    entryState: MutableState<EntryState>,
    onValidationClick: (entry: String) -> Unit,
    onContinueClick: (lastState: GameState) -> Unit,
    onRestartGameClick: () -> Unit,
) {
    val state by gameState.collectAsState()
    when(state) {
        is GameState.Loading -> Loader()
        is GameState.Error -> ErrorView((state as GameState.Error).t)
        is GameState.Playing ->  GameView(
            gameState = state as GameState.Playing,
            entryMutableState = entryState,
        ) {
            onValidationClick(it)
        }
        is GameState.LevelFailure,
        is GameState.LevelSuccess,
        is GameState.Completed -> {
            EndView(
                message = getEndMessage(state),
            ) {
                if(state is GameState.Completed)
                    onRestartGameClick()
                else
                    onContinueClick(state)
            }
        }
        is GameState.Default -> {}
    }
}

@Composable
private fun getEndMessage(state: GameState) =
    when(state){
        is GameState.Completed -> stringResource(Res.string.game_completed)
        is GameState.LevelSuccess -> stringResource(Res.string.success_message)
        is GameState.LevelFailure -> state.game?.word?.value?.let {
            stringResource(Res.string.failure_message, it)
        } ?: stringResource(Res.string.failure_default_message)
        else -> throw IllegalStateException()
    }