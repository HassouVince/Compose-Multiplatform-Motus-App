package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

private val LightColorScheme = lightColorScheme(
    primary = MotusColors.primaryColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = MotusColors.primaryDarkColor,
)

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
fun MotusAppTheme(content: @Composable () -> Unit) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            content = {
                Surface(
                    content = content,
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 3.dp)
                )
            },
        )
    }
}