package ui.views.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composemultiplatformmotus.composeapp.generated.resources.Res
import composemultiplatformmotus.composeapp.generated.resources.invalid_entry_length
import composemultiplatformmotus.composeapp.generated.resources.title
import composemultiplatformmotus.composeapp.generated.resources.unknown_entry
import composemultiplatformmotus.composeapp.generated.resources.validate
import composemultiplatformmotus.composeapp.generated.resources.win_lose_count
import data.datasource.game.Game
import data.datasource.game.getEntryLimit
import data.datasource.game.getHiddenWord
import domain.models.EntryState
import domain.models.GameState
import org.jetbrains.compose.resources.stringResource
import ui.theme.MotusColors

@Composable
fun GameView(
    gameState: GameState.Playing,
    entryMutableState: MutableState<EntryState>,
    onValidationClick: (entry: String) -> Unit
) {
    val game by remember { mutableStateOf(gameState.game) }
    val entryState by remember { entryMutableState }
    Box(Modifier.fillMaxSize()) {
        GameContent(game, entryState, onValidationClick)
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(5.dp),
            text = stringResource(
                Res.string.win_lose_count,
                game?.winCount ?: 0, game?.loseCount ?: 0
            )
        )
    }
}

@Composable
private fun GameContent(
    game: Game?,
    entryState: EntryState,
    onValidationClick: (entry: String) -> Unit
) {
    var userEntry by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        Text(text = stringResource(Res.string.title, game?.userEntries?.size ?: 0))
        WordRow(value = game?.getHiddenWord().orEmpty())
        WordsView(
            correctWord = game?.word?.value.orEmpty(),
            entries = game?.userEntries.orEmpty(),
        )
        TextField(
            value = userEntry,
            onValueChange = {
                if (it.length <= (game?.getEntryLimit() as Int))
                    userEntry = it
            }
        )
        EntryError(game, entryState)
        Button(
            onClick = {
                onValidationClick(userEntry)
                userEntry = ""
            }
        ) {
            Text(text = stringResource(Res.string.validate))
        }
    }
}

@Composable
private fun EntryError(game: Game?, entryState: EntryState?) {
    entryState?.let {
        when(it) {
            is EntryState.InvalidLengthEntry ->
                stringResource(
                    Res.string.invalid_entry_length,
                    game.getEntryLimit()
                )
            is EntryState.UnknownEntry ->
                stringResource(Res.string.unknown_entry)
            else -> null
        }?.let { msg ->
            Text(text = msg, color = MotusColors.errorColor)
        }
    }
}