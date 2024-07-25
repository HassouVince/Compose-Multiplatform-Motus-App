package fr.naddev.composemultiplatformmotus

import MotusApp
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : MotusBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotusApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MotusApp()
}