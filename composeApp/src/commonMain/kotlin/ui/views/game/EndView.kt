package ui.views.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composemultiplatformmotus.composeapp.generated.resources.Res
import composemultiplatformmotus.composeapp.generated.resources.continue_
import org.jetbrains.compose.resources.stringResource

@Composable
fun EndView(message: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = message)
        Button(
            onClick = { onClick() }
        ) {
            Text(text = stringResource(Res.string.continue_))
        }
    }
}