package ui.views.tools

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Loader() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .size(35.dp)
            .wrapContentSize(Alignment.Center)
    )
}