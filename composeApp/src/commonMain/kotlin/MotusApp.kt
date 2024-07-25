import androidx.compose.runtime.*
import di.appModule
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.theme.MotusAppTheme
import ui.viewmodels.GameViewModel
import ui.views.game.GameScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MotusApp() {
    KoinApplication(
        application = { modules(appModule()) }
    ) {
        MotusAppTheme {
            val viewModel: GameViewModel = koinViewModel()
            viewModel.apply {
                GameScreen(
                    gameState = gameState,
                    entryState = entryState,
                    onValidationClick = { validateEntry(it) },
                    onContinueClick = { continueGame(it) },
                    onRestartGameClick = { restartGame() },
                )
                fetchGameState()
            }
        }
    }
}