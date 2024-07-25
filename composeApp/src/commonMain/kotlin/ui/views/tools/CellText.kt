package ui.views.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CellText(
    value: String,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 1.dp, vertical = 3.dp)
            .background(color = backgroundColor)
            .defaultMinSize(minWidth = 35.dp)
    ) {
        Text(
            text = value,
            color = textColor,
            fontWeight = FontWeight.W800,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
        )
    }
}